package com.pccw.wq.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.service.AcknowledgeSbService;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.util.email.EmailUtil;
import com.pccw.util.email.EmailUtil.EmailMessage;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.dao.WorkQueueInquiryDAOImpl;
import com.pccw.wq.schema.dto.RemarkDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueDocumentDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.schema.dto.WorkQueueTriggerDTO;

public class WorkQueueTriggerServiceImpl implements WorkQueueTriggerService {

	private static final Logger logger = Logger.getLogger(WorkQueueTriggerServiceImpl.class);

	public static final String FROM_EMAIL_ADDRESS = "\"Work Queue Alert\" <sbladmin@pccw.com>";
	
	@Override
	public void handleTrigger(WorkQueueTriggerDTO pWorkQueueTrigger, String pUserId) throws Exception {
		try {
			if (HNDL_METHOD_SEND_EMAIL.equals(pWorkQueueTrigger.getHandleMethod())) {
				triggerSendEmail(pWorkQueueTrigger);
			} else if (HNDL_METHOD_ROUTE_WQ.equals(pWorkQueueTrigger.getHandleMethod())) {
				triggerRouteWq(pWorkQueueTrigger);
			} else if (HNDL_METHOD_CHANGE_STATUS.equals(pWorkQueueTrigger.getHandleMethod())) {
				triggerChangeStatus(pWorkQueueTrigger);
			} else if (HNDL_METHOD_ACK_SPRINGBOARD.equals(pWorkQueueTrigger.getHandleMethod())) {
				triggerAcknowledgeSb(pWorkQueueTrigger, pUserId);
			} else if (HNDL_METHOD_HOLD_N_TRANSFER.equals(pWorkQueueTrigger.getHandleMethod())) {
				triggerHoldAndTransfer(new WorkQueueTriggerDTO[] {pWorkQueueTrigger}, pUserId);
			} else if (HNDL_METHOD_RESUME_ON_HOLD.equals(pWorkQueueTrigger.getHandleMethod())) {
				triggerResumeOnHold(pWorkQueueTrigger, pUserId);
			} else if (HNDL_METHOD_CHANGE_ON_HOLD_WQ_NATURE.equals(pWorkQueueTrigger.getHandleMethod())) {
				triggerChangeOnHoldWqNature(pWorkQueueTrigger);
			} else if (HNDL_METHOD_FOLLOWUP_REPLIED.equals(pWorkQueueTrigger.getHandleMethod())) {
				triggerFollowReplied(pWorkQueueTrigger, pUserId);
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	private void triggerSendEmail(WorkQueueTriggerDTO pWorkQueueTrigger) throws Exception {
		EmailMessage msg = new EmailMessage();
		msg.setContentFormat(EmailMessage.CONTENT_FORMAT_HTML);
		String[] emails = getEmailAddress(pWorkQueueTrigger, "FROM_EMAIL_ADDR");
		if (ArrayUtils.isNotEmpty(emails)) {
			try {
				msg.setSender(emails[0]);
			} catch (Exception ignore) {
				msg.setSender(FROM_EMAIL_ADDRESS);
			}
			
		} else {
			msg.setSender(FROM_EMAIL_ADDRESS);
		}

		emails = getEmailAddress(pWorkQueueTrigger, "REPLY_TO_EMAIL_ADDR");
		if (ArrayUtils.isNotEmpty(emails)) {
			try {
				msg.setReplyTo(emails[0]);
			} catch (Exception ignore) {}
		}
		
		boolean hasEmailAddr = false;
		emails = getEmailAddress(pWorkQueueTrigger, "TO_EMAIL_ADDR");
		if (ArrayUtils.isNotEmpty(emails)) {
			msg.setReceipantTo(emails);
			hasEmailAddr = true;
		}

		emails = getEmailAddress(pWorkQueueTrigger, "CC_EMAIL_ADDR");
		if (ArrayUtils.isNotEmpty(emails)) {
			msg.setReceipantCc(emails);	
			hasEmailAddr = true;
		}
		
		emails = getEmailAddress(pWorkQueueTrigger, "BCC_EMAIL_ADDR");
		if (ArrayUtils.isNotEmpty(emails)) {
			msg.setReceipantBcc(emails);	
			hasEmailAddr = true;
		}

		if (!hasEmailAddr) {
			return;
		}
		
		msg.setSubject(pWorkQueueTrigger.getAttributeValue("EMAIL_SUBJECT"));
		msg.setContent(pWorkQueueTrigger.getAttributeValue("EMAIL_CONTENT"));
		
		String attachment = pWorkQueueTrigger.getAttributeValue("EMAIL_ATTACHMENT");
		if (StringUtils.isNotBlank(attachment)) {
			TreeSet<String> docTypeSet = new TreeSet<String>();
			docTypeSet.addAll(Arrays.asList(attachment.split(";")));
			if (docTypeSet.contains("COVER")) {
				List<byte[]> coverSheetList = SpringApplicationContext.getBean(WorkQueueService.class).getWqCoverSheets(pWorkQueueTrigger.getWqId(), pWorkQueueTrigger.getWqBatchId());
				for (byte[] coverSheet : coverSheetList) {
					msg.addAttachment("WQ_COVER_SHEET_" + pWorkQueueTrigger.getWqSerial()  + ".pdf", coverSheet);
				}
			}
			WorkQueueDocumentDTO[] wqDocs = SpringApplicationContext.getBean(WorkQueueService.class).downloadWorkQueueDocuments(pWorkQueueTrigger.getWqWpAssgnId(), "WQ_TRIGGER");
			if (ArrayUtils.isNotEmpty(wqDocs)){
				for (WorkQueueDocumentDTO wqDoc : wqDocs) {
					if (!docTypeSet.contains(wqDoc.getDocumentTypeId()) && !docTypeSet.contains("ALL")) {
						continue;
					}
					msg.addAttachment(wqDoc.getAttachmentFullPath().substring(wqDoc.getAttachmentFullPath().lastIndexOf("/") + 1), FileUtils.readFileToByteArray(new File(wqDoc.getAttachmentFullPath())));
				}
			}
		}
		
		EmailUtil.sendEmail(null, msg);
	}
	
	private String[] getEmailAddress(WorkQueueTriggerDTO pWorkQueueTrigger, String pAttbName) throws Exception {
		String emailAddrString = pWorkQueueTrigger.getAttributeValue(pAttbName);
		if (StringUtils.isBlank(emailAddrString)) {
			return new String[0];
		}
		String[] emails = emailAddrString.split(";");
		String email = null;
		for (int i = 0; emails != null && i < emails.length; i++) {
			email = emails[i];
			if (StringUtils.isEmpty(email)) {
				continue;
			}
			email = WorkQueueTriggerDTO.handleBindingValue(pWorkQueueTrigger, email);
			emails[i] = email;
		}
		return emails;
	}
	
	private void triggerRouteWq(WorkQueueTriggerDTO pWorkQueueTrigger) throws Exception {
		WorkQueueDTO wqDTO = new WorkQueueDTO();
		wqDTO.setWqId(pWorkQueueTrigger.getWqId());
		wqDTO.setCreatedBatchId(pWorkQueueTrigger.getWqBatchId());

		WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
		wqNatureDTO.setWorkQueueType(pWorkQueueTrigger.getAttributeValue("TO_WQ_TYPE"));
		wqNatureDTO.setWorkQueueSubType(pWorkQueueTrigger.getAttributeValue("TO_WQ_SUBTYPE"));
		wqNatureDTO.setWorkQueueNatureId(pWorkQueueTrigger.getAttributeValue("TO_WQ_NATURE"));

		SpringApplicationContext.getBean(WorkQueueService.class).createWorkQueue(wqDTO, new WorkQueueNatureDTO[] {wqNatureDTO}, "SYSTEM");
		SpringApplicationContext.getBean(WorkQueueDataService.class).deliverWorkQueueDocument(pWorkQueueTrigger.getWqId());
	}
	
	private void triggerChangeStatus(WorkQueueTriggerDTO pWorkQueueTrigger) throws Exception {
		int cnt = SpringApplicationContext.getBean(WorkQueueService.class).changeWorkQueueStatus(
				new String[] {pWorkQueueTrigger.getWqWpAssgnId()}, 
				pWorkQueueTrigger.getAttributeValue("NEW_WQ_STATUS"), 
				pWorkQueueTrigger.getAttributeValue("NEW_REASON_CD"),
				null, null, "SYSTEM");
		if (cnt != 1) {
			throw new Exception("CHANGE STATUS FAILED");
		}
	}
	
	private static String CHK_ALL_APPROVAL_SQL = 
			 " select COUNT(1) from q_wq_wp_search_id_v "
            + " where wq_nature_id in (SELECT WQ_NATURE_ID FROM Q_WQ_NATURE WHERE WQ_NATURE_TYPE = 'APPROVAL') "
            + "   AND status_cd IN ('40', '60', '120') "
            + "   AND WQ_ID = TO_NUMBER(?)";
	
	private void triggerAcknowledgeSb(WorkQueueTriggerDTO pWorkQueueTrigger, String pUserId) throws Exception {
		String ackMethod = pWorkQueueTrigger.getAttributeValue("ACK_METHOD");
		AcknowledgeSbService ackService = SpringApplicationContext.getBean(AcknowledgeSbService.class);
		if ("sbOrderApprovalWqApproved".equals(ackMethod)) {
			String strCount = OracleSelectHelper.getSqlFirstRowColumnString(
					"WorkQueueDS", CHK_ALL_APPROVAL_SQL, new String[] { pWorkQueueTrigger.getWqId() });
			if (Integer.parseInt(strCount) <= 0) {
				ackService.sbOrderApprovalWqApproved(pWorkQueueTrigger.getSbId(), pWorkQueueTrigger.getSbDtlId());
			}
		} else if ("sbOrderApprovalWqRejected".equals(ackMethod)) {
			ackService.sbOrderApprovalWqRejected(pWorkQueueTrigger.getSbId(), pWorkQueueTrigger.getSbDtlId());
		} else if ("sbOrderFullWqCompleted".equals(ackMethod)) {
			ackService.sbOrderFullWqCompleted(pWorkQueueTrigger.getSbId(), pWorkQueueTrigger.getSbDtlId(), pWorkQueueTrigger.getBomOcId());
		} else if ("onlineSalesDiffNameSignOff".equals(ackMethod)) {
			ackService.onlineSalesDiffNameSignOff(pWorkQueueTrigger.getSbId(), pWorkQueueTrigger.getSbDtlId());
		} else if ("onlineSalesDiffNameCancel".equals(ackMethod)) {
			ackService.onlineSalesDiffNameCancel(pWorkQueueTrigger.getSbId(), pWorkQueueTrigger.getSbDtlId());
		}  else if ("onlineSalesCOrderClosed".equals(ackMethod)) {
			ackService.onlineSalesCOrderClosed(pWorkQueueTrigger.getSbId(), pWorkQueueTrigger.getSbDtlId(), pWorkQueueTrigger.getBomOcId());
		} else if ("onlineSalesLtsDiffNameSignOff".equals(ackMethod)) {
			ackService.onlineSalesLtsDiffNameSignOff(pWorkQueueTrigger.getSbId());
		} else if ("onlineSalesLtsDiffNameCancel".equals(ackMethod)) {
			ackService.onlineSalesLtsDiffNameCancel(pWorkQueueTrigger.getSbId(), pWorkQueueTrigger.getSbDtlId());
		} else if ("cOrderIgored".equals(ackMethod)) {
			ackService.updateCOrderIgnored(pWorkQueueTrigger.getSbId(), pWorkQueueTrigger.getSbDtlId(), pWorkQueueTrigger.getBomOcId());
		}else if ("imsDSWaiveQcAlertSettled".equals(ackMethod)) {
			ackService.imsDSWaiveQCAlertSettled(pWorkQueueTrigger.getSbId());
		}else if ("imsDSMisMatchSignoff".equals(ackMethod)) {
			ackService.imsDSMisMatchSignoff(pWorkQueueTrigger.getSbId());
		}else if ("imsDSMisMatchCancel".equals(ackMethod)) {
			ackService.imsDSMisMatchCancel(pWorkQueueTrigger.getSbId());
		}else if ("imsDSBlackListAddressSettled".equals(ackMethod)) {
			ackService.imsDSBlackListAddressSettled(pWorkQueueTrigger.getSbId());
		}else if ("imsDSFSPendingSettled".equals(ackMethod)) {
			ackService.imsDSFSPendingSettled(pWorkQueueTrigger.getSbId());
		}else if ("imsDSFSPendingCancelled".equals(ackMethod)) {
			ackService.imsDSFSPendingCancelled(pWorkQueueTrigger.getSbId());
		}else if ("imsDSWaiveQCAlertCancelled".equals(ackMethod)) {
			ackService.imsDSWaiveQCAlertCancelled(pWorkQueueTrigger.getSbId());
		}else if ("imsDSCashPrepaySettled".equals(ackMethod)) {
			ackService.imsDSCashPrepaySettled(pWorkQueueTrigger.getSbId());
		}else if ("imsVerDocFailed".equals(ackMethod)) {
			ackService.imsVerDocFailed(pWorkQueueTrigger.getSbId());
		}else if ("imsVerDocApproved".equals(ackMethod)) {
			ackService.imsVerDocApproved(pWorkQueueTrigger.getSbId());
		} else if ("wqStatusChanged".equals(ackMethod)) {
			String[] wqNatureIds = OracleSelectHelper.getSqlFirstColumnStrings("WorkQueueDS", 
					"select WQ_NATURE_ID from q_wq_wp_search_id_v where wq_wp_assgn_id = TO_NUMBER(?) ",
					new Object[] {pWorkQueueTrigger.getWqWpAssgnId()});
			RemarkDTO[] remarkDTOs = SpringApplicationContext.getBean(WorkQueueDataService.class).getWorkQueRemarksByWqWpAssgnId(pWorkQueueTrigger.getWqWpAssgnId(), null);
			StringBuffer sbRemarks = new StringBuffer();
			if (ArrayUtils.isNotEmpty(remarkDTOs)) {
				for (RemarkDTO remarkDTO : remarkDTOs) {
					sbRemarks.append(remarkDTO.getRemarkContent());
					sbRemarks.append("\n");
				}
			}
			
			ackService.wqStatusChanged(pWorkQueueTrigger.getWqWpAssgnId(),
					pWorkQueueTrigger.getSbId(),
					pWorkQueueTrigger.getSbDtlId(),
					pWorkQueueTrigger.getSbActvId(),
					pWorkQueueTrigger.getWqStatus(), wqNatureIds,
					sbRemarks.toString(),
					pUserId);
		}	
	}

	private static String onHoldWqWqNatureAssgnSQL = 
			"UPDATE q_wq_wq_nature_assgn " +
			"   SET wq_batch_id = -wq_batch_id, last_upd_by = 'WQTRIGGER', last_upd_date = SYSDATE " + 
            " WHERE wq_id = TO_NUMBER(?) " +
            "   AND wq_batch_id = TO_NUMBER(?)";

	private static String onHoldWqRemarksSQL = 
			"UPDATE q_wq_remarks " +
			"   SET wq_batch_id = -wq_batch_id, last_upd_by = 'WQTRIGGER', last_upd_date = SYSDATE " + 
            " WHERE wq_id = TO_NUMBER(?) " +
            "   AND wq_batch_id = TO_NUMBER(?)";

	private static String getOnHoldWqNatureSQL =
			"select dcl_wq_type.description || ' / ' || dcl_wq_subtype.description || ' / ' || wn.WQ_NATURE_DESC" +
			"  from q_wq_wq_nature_assgn wwna, q_dic_code_lkup dcl_wq_type, q_dic_code_lkup dcl_wq_subtype, q_wq_nature wn" +
			" where wwna.wq_nature_id = wn.wq_nature_id " +
			"   and dcl_wq_type.GRP_ID = 'WQ_TYPE' " +
			"   and dcl_wq_type.code = wwna.WQ_TYPE " +
			"   and dcl_wq_subtype.GRP_ID = 'WQ_SUB_TYPE' " +
			"   and dcl_wq_subtype.code = wwna.WQ_SUBTYPE " +
			"   and wwna.wq_id = TO_NUMBER(?) " +
			"   and wwna.wq_batch_id = -1 * TO_NUMBER(?)";
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void triggerHoldAndTransfer(WorkQueueTriggerDTO[] pWorkQueueTriggers, String pUser) throws Exception {
		
		for (WorkQueueTriggerDTO workQueueTrigger : pWorkQueueTriggers) {
			if (!HNDL_METHOD_HOLD_N_TRANSFER.equals(workQueueTrigger.getHandleMethod())) {
				throw new Exception("WORK QUEUE HANDLE METHOD ONLY ACCEPT [HOLD_N_TRANSFER] - HANDLE METHOD: " + workQueueTrigger.getHandleMethod());
			}
		}
		
		JdbcTemplate jdbcTemplate = ((WorkQueueInquiryDAOImpl) SpringApplicationContext.getBean("com.pccw.wq.dao.WorkQueueInquiryDAO")).getJdbcTemplate();

        try {

        	jdbcTemplate.update(onHoldWqWqNatureAssgnSQL, new Object[] {pWorkQueueTriggers[0].getWqId(), pWorkQueueTriggers[0].getWqBatchId()});
        	jdbcTemplate.update(onHoldWqRemarksSQL, new Object[] {pWorkQueueTriggers[0].getWqId(), pWorkQueueTriggers[0].getWqBatchId()});
        	
    		WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
    		wqNatureDTO.setWorkQueueType(pWorkQueueTriggers[0].getAttributeValue("TO_WQ_TYPE"));
    		wqNatureDTO.setWorkQueueSubType(pWorkQueueTriggers[0].getAttributeValue("TO_WQ_SUBTYPE"));
    		wqNatureDTO.setWorkQueueNatureId(pWorkQueueTriggers[0].getAttributeValue("TO_WQ_NATURE"));
    		
    		String[] natures = OracleSelectHelper.getSqlFirstColumnStrings(
					"WorkQueueDS",
					getOnHoldWqNatureSQL,
					new Object[] { pWorkQueueTriggers[0].getWqId(), pWorkQueueTriggers[0].getWqBatchId() });
    		if (!ArrayUtils.isEmpty(natures)) {
        		StringBuilder sb = new StringBuilder("Original Work Queue Nature(s):");
    			for (String nature : natures) {
					sb.append("\n");
					sb.append(nature);
				}
    			RemarkDTO remark = new RemarkDTO();
    			remark.setRemarkContent(sb.toString());
    			wqNatureDTO.addRemark(remark);
    		}

    		ArrayList<WorkQueueNatureDTO> wqNatureList = new ArrayList<WorkQueueNatureDTO>();
    		wqNatureList.add(wqNatureDTO);
    		for (int i = 1; i < pWorkQueueTriggers.length; i++) {
    			wqNatureDTO = new WorkQueueNatureDTO();
        		wqNatureDTO.setWorkQueueType(pWorkQueueTriggers[i].getAttributeValue("TO_WQ_TYPE"));
        		wqNatureDTO.setWorkQueueSubType(pWorkQueueTriggers[i].getAttributeValue("TO_WQ_SUBTYPE"));
        		wqNatureDTO.setWorkQueueNatureId(pWorkQueueTriggers[i].getAttributeValue("TO_WQ_NATURE"));
        		wqNatureList.add(wqNatureDTO);
    		}
    		
    		WorkQueueDataService wqDataService = SpringApplicationContext.getBean(WorkQueueDataService.class);
   		
    		wqDataService.insertWqNature(pWorkQueueTriggers[0].getWqId(), pWorkQueueTriggers[0].getWqBatchId(), "WQTRIGGER", wqNatureList.toArray(new WorkQueueNatureDTO[0]));
    		wqDataService.dispatchWorkQueue(pWorkQueueTriggers[0].getWqId(), pWorkQueueTriggers[0].getWqBatchId(), pWorkQueueTriggers[0].getUrl(), pUser);
    		wqDataService.deliverWorkQueueDocument(pWorkQueueTriggers[0].getWqId());

        } catch (Exception e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
            throw e;

        }
	}
	
	private static String resumeOnHoldWqWqNatureAssgnSQL = 
			"UPDATE q_wq_wq_nature_assgn " +
			"   SET wq_batch_id = TO_NUMBER(?), last_upd_by = 'WQTRIGGER', last_upd_date = SYSDATE " + 
            " WHERE wq_id = TO_NUMBER(?) " +
            "   AND wq_batch_id = -1 * TO_NUMBER(?)";

	private static String resumeOnHoldWqRemarksSQL = 
			"UPDATE q_wq_remarks " +
			"   SET wq_batch_id = TO_NUMBER(?), last_upd_by = 'WQTRIGGER', last_upd_date = SYSDATE " + 
            " WHERE wq_id = TO_NUMBER(?) " +
            "   AND wq_batch_id = -1 * TO_NUMBER(?)";

	@Transactional(rollbackFor={Exception.class})
	private void triggerResumeOnHold(WorkQueueTriggerDTO pWorkQueueTrigger, String pUser) throws Exception {
		JdbcTemplate jdbcTemplate = ((WorkQueueInquiryDAOImpl) SpringApplicationContext.getBean("com.pccw.wq.dao.WorkQueueInquiryDAO")).getJdbcTemplate();

        try {

        	int numOfRow = jdbcTemplate.update(resumeOnHoldWqWqNatureAssgnSQL, new Object[] {pWorkQueueTrigger.getWqBatchId(), pWorkQueueTrigger.getWqId(), pWorkQueueTrigger.getWqBatchId()});
        	if (numOfRow <= 0) {
        		return;
        	}
        	
        	jdbcTemplate.update(resumeOnHoldWqRemarksSQL, new Object[] {pWorkQueueTrigger.getWqBatchId(), pWorkQueueTrigger.getWqId(), pWorkQueueTrigger.getWqBatchId()});
        	
    		WorkQueueDataService wqDataService = SpringApplicationContext.getBean(WorkQueueDataService.class);

    		wqDataService.dispatchWorkQueue(pWorkQueueTrigger.getWqId(), pWorkQueueTrigger.getWqBatchId(), pWorkQueueTrigger.getUrl(), pUser);
    		wqDataService.deliverWorkQueueDocument(pWorkQueueTrigger.getWqId());

        } catch (Exception e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
            throw e;
        }
	}
	
	private static String changeOnHoldWqNatureSQL = 
			"UPDATE q_wq_wq_nature_assgn " +
			"   SET WQ_NATURE_ID = TO_NUMBER(?), last_upd_by = 'WQTRIGGER', last_upd_date = SYSDATE " + 
            " WHERE wq_id = TO_NUMBER(?) " +
            "   AND wq_batch_id = -1 * TO_NUMBER(?)" +
            "   AND WQ_NATURE_ID = TO_NUMBER(?) ";

	@Transactional(rollbackFor={Exception.class})
	private void triggerChangeOnHoldWqNature(WorkQueueTriggerDTO pWorkQueueTrigger) throws Exception {
		JdbcTemplate jdbcTemplate = ((WorkQueueInquiryDAOImpl) SpringApplicationContext.getBean("com.pccw.wq.dao.WorkQueueInquiryDAO")).getJdbcTemplate();

        try {
        	int cnt = 0;
        	String attbName = "FROM_WQ_NATURE_ID[0]";
        	String fromValue = null;
        	String toValue = null;
        	while (StringUtils.isNotBlank(pWorkQueueTrigger.getAttributeValue(attbName))) {
        		fromValue = pWorkQueueTrigger.getAttributeValue(attbName);
        		attbName = "TO_WQ_NATURE_ID[" + String.valueOf(cnt) + "]";
        		toValue = pWorkQueueTrigger.getAttributeValue(attbName);
        		jdbcTemplate.update(changeOnHoldWqNatureSQL, new Object[] {toValue, pWorkQueueTrigger.getWqId(), pWorkQueueTrigger.getWqBatchId(), fromValue});
        		cnt++;
        		attbName = "FROM_WQ_NATURE_ID[" + String.valueOf(cnt) + "]";
        	}
        } catch (Exception e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
            throw e;
        }
	}
	
	private static final String SQL_GET_SRC_WQ_WP_ASSGN_ID = 
			  "SELECT ATTB_VALUE "
			+ "  FROM ( SELECT DENSE_RANK() OVER (PARTITION BY ATTB_NAME ORDER BY CREATE_DATE DESC) AS RANK, ATTB_VALUE "
			+ "           from q_wq_attb "
			+ "          where WQ_ID = TO_NUMBER(?) "
			+ "            AND WQ_BATCH_ID = TO_NUMBER(?) "
			+ "            AND ATTB_NAME = 'SRC_WQ_WP_ASSGN_ID') "
			+ "  WHERE RANK = 1";
	
	private static final String SQL_FOLLOW_REPLIED_UPDATE_DISPATCH_WQ_WP_ASSGN_ID =
			  "UPDATE q_wq_wq_nature_assgn "
			+ "   set DISPATCH_WQ_WP_ASSGN_ID = NULL "
			+ " where (wq_id, wq_batch_id) in (select wq_id, wq_batch_id from q_wq_wp_search_id_v where wq_wp_assgn_id = TO_NUMBER(?)) "
			+ "   and DISPATCH_WQ_WP_ASSGN_ID = TO_NUMBER(?) ";
	
	@Transactional(rollbackFor={Exception.class})
	private void triggerFollowReplied(WorkQueueTriggerDTO pWorkQueueTrigger, String pUser) throws Exception {
		JdbcTemplate jdbcTemplate = ((WorkQueueInquiryDAOImpl) SpringApplicationContext.getBean("com.pccw.wq.dao.WorkQueueInquiryDAO")).getJdbcTemplate();

        try {

        	String srcWqWpAssgnId = OracleSelectHelper.getSqlFirstRowColumnString(
			    						"WorkQueueDS",
			    						SQL_GET_SRC_WQ_WP_ASSGN_ID,
			    						new Object[] { pWorkQueueTrigger.getWqId(), pWorkQueueTrigger.getWqBatchId() });

        	
        	int numOfRow = jdbcTemplate.update(SQL_FOLLOW_REPLIED_UPDATE_DISPATCH_WQ_WP_ASSGN_ID, new Object[] {srcWqWpAssgnId, srcWqWpAssgnId});
        	if (numOfRow <= 0) {
        		return;
        	}
        	
    		WorkQueueDataService wqDataService = SpringApplicationContext.getBean(WorkQueueDataService.class);

    		wqDataService.dispatchWorkQueue(pWorkQueueTrigger.getWqId(), pWorkQueueTrigger.getWqBatchId(), pWorkQueueTrigger.getUrl(), pUser);
    		wqDataService.deliverWorkQueueDocument(pWorkQueueTrigger.getWqId());

    		String srcWqNewStatus = pWorkQueueTrigger.getAttributeValue("SRC_NEW_STATUS");
    		if (StringUtils.isNotBlank(srcWqNewStatus)) {
    			SpringApplicationContext.getBean(WorkQueueService.class).changeWorkQueueStatus(
    					new String[] {srcWqWpAssgnId}, 
    					pWorkQueueTrigger.getAttributeValue("SRC_NEW_STATUS"), 
    					pWorkQueueTrigger.getAttributeValue("SRC_NEW_REASON_CD"),
    					null, null, "SYSTEM");
    		}
    		
    		String newStatus = pWorkQueueTrigger.getAttributeValue("NEW_STATUS");
    		if (StringUtils.isNotBlank(newStatus)) {
    			SpringApplicationContext.getBean(WorkQueueService.class).changeWorkQueueStatus(
    					new String[] {pWorkQueueTrigger.getWqWpAssgnId()}, 
    					pWorkQueueTrigger.getAttributeValue("NEW_STATUS"), 
    					pWorkQueueTrigger.getAttributeValue("NEW_REASON_CD"),
    					null, null, "SYSTEM");
    		}
    		
        } catch (Exception e) {
            logger.error(ExceptionUtils.getFullStackTrace(e));
            throw e;
        }
	}
}