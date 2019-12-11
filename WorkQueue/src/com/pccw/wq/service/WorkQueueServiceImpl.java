package com.pccw.wq.service;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.util.PdfHeaderFooter;
import com.bomwebportal.util.PdfUtil;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.wq.BulkPrintRptDTO;
import com.pccw.rpt.schema.dto.wq.WorkQueueRptDTO;
import com.pccw.rpt.service.ReportService;
import com.pccw.util.FastByteArrayInputStream;
import com.pccw.util.FastByteArrayOutputStream;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.util.db.stringOracleType.OraArrayNumber;
import com.pccw.util.ftp.HpDirectPdfPrinter;
import com.pccw.util.search.Criteria;
import com.pccw.util.search.SearchResult;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.schema.dto.PrintSummaryDTO;
import com.pccw.wq.schema.dto.RemarkDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueDocumentDTO;
import com.pccw.wq.schema.dto.WorkQueueInquiryCriteriaDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.schema.dto.WorkingPartyDTO;
import com.pccw.wq.schema.form.SbWqInquiryResultFormDTO;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;
import com.pccw.wq.schema.form.WqStatusLogFormDTO;

public class WorkQueueServiceImpl implements WorkQueueService {
	
	private static Logger logger = Logger.getLogger(WorkQueueServiceImpl.class);
	
	private DateFormat feDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

	private DateFormat dbDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

	private DateFormat attachmentHeaderDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	private DateFormat srdDateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Override
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> searchWorkQueue(
			WorkQueueInquiryCriteriaDTO pWqInquiryCriteria, Criteria pPagingCriteria, String pUser) throws Exception {
		return SpringApplicationContext.getBean(WorkQueueDataService.class).searchWorkQueue(genSearchWorkQueueCriteria(pWqInquiryCriteria, pPagingCriteria));
	}
	
	@Override
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> searchSbIdsWithOutstandingWq(String pSrvType, 
			 String pUser) throws Exception {

		WorkingPartyDTO[] wpDTOs = SpringApplicationContext.getBean(WorkQueueDataService.class).getWorkingPartyByStaffId(pUser, "ALERT");
		if (ArrayUtils.isEmpty(wpDTOs)) {
			SearchResult<WqInquiryResultFormDTO> rtnObj = new SearchResult<WqInquiryResultFormDTO>();
			rtnObj.setTotalCount(0);
			return rtnObj;
		}
		
		TreeSet<String> teamLvlWpIdSet = new TreeSet<String>();
		TreeSet<String> assigneeLvlWpIdSet = new TreeSet<String>();
		
		for (WorkingPartyDTO wpDTO : wpDTOs) {
			if ("ASSIGNEE".equals(wpDTO.getAlertLevel())) {
				assigneeLvlWpIdSet.add(wpDTO.getWpId());
			} else {
				teamLvlWpIdSet.add(wpDTO.getWpId());
			}
		}

		ArrayList<Criteria> criteriaList = new ArrayList<Criteria>(); 
		
		WorkQueueInquiryCriteriaDTO wqInquiryCriteria = null;
		Criteria pagingCriteria = null;

		if (!assigneeLvlWpIdSet.isEmpty()) {
			pagingCriteria = new Criteria();
			pagingCriteria.orderBy("\"sbId\"");
			pagingCriteria.limit(9999);
			
			wqInquiryCriteria = new WorkQueueInquiryCriteriaDTO();
			wqInquiryCriteria.setSrvType(pSrvType);
			wqInquiryCriteria.setRelatedSrvType("ZZZ");
			wqInquiryCriteria.setWqStatus(ConstantLkupService.WQ_STATUS_CODE_OUTSTANDING);
			wqInquiryCriteria.setAssignee(pUser);
			wqInquiryCriteria.setWpIds(assigneeLvlWpIdSet.toArray(new String[0]));
			criteriaList.add(genSearchWorkQueueCriteria(wqInquiryCriteria, pagingCriteria));
		}
		
		if (!teamLvlWpIdSet.isEmpty()) {
			pagingCriteria = new Criteria();
			pagingCriteria.orderBy("\"sbId\"");
			pagingCriteria.limit(9999);
			
			wqInquiryCriteria = new WorkQueueInquiryCriteriaDTO();
			wqInquiryCriteria.setSrvType(pSrvType);
			wqInquiryCriteria.setRelatedSrvType("ZZZ");
			wqInquiryCriteria.setWqStatus(ConstantLkupService.WQ_STATUS_CODE_OUTSTANDING);
			wqInquiryCriteria.setWpIds(teamLvlWpIdSet.toArray(new String[0]));
			criteriaList.add(genSearchWorkQueueCriteria(wqInquiryCriteria, pagingCriteria));
		}
		
		SearchResult<WqInquiryResultFormDTO> rtnResult = SpringApplicationContext.getBean(WorkQueueDataService.class).searchSbIdsWithWq(criteriaList.toArray(new Criteria[0]));
		
		if ("SLV".equals(pSrvType)) {
			for (Criteria criteria : criteriaList) {
				criteria.getOrderByMap().clear();
				criteria.orderBy("\"sbActvId\"");
			}
			SearchResult<WqInquiryResultFormDTO> actvResult = SpringApplicationContext.getBean(WorkQueueDataService.class).searchSbActvIdsWithWq(criteriaList.toArray(new Criteria[0]));
			rtnResult.getResult().addAll(actvResult.getResult());
			rtnResult.setTotalCount(rtnResult.getTotalCount() + actvResult.getTotalCount());
		}
		
		return rtnResult;
	}
	
	private Criteria genSearchWorkQueueCriteria (
			WorkQueueInquiryCriteriaDTO pWqInquiryCriteria, Criteria pPagingCriteria) throws Exception {
		Criteria daoCriteria = pPagingCriteria;
		if (daoCriteria == null) {
			daoCriteria = new Criteria();
			daoCriteria.orderBy("RECEIVE_DATE");
		}

		if (StringUtils.isNotBlank(pWqInquiryCriteria.getWqWpAssgnId())) {
			daoCriteria.andEqual("WQ_WP_ASSGN_ID", pWqInquiryCriteria.getWqWpAssgnId());
		}
		
		if (StringUtils.isNotBlank(pWqInquiryCriteria.getWpId())) {
			daoCriteria.andEqual("WP_ID", pWqInquiryCriteria.getWpId());
		}
		
		if (ArrayUtils.isNotEmpty(pWqInquiryCriteria.getWpIds())) {
			daoCriteria.andIn("WP_ID", (Object[]) pWqInquiryCriteria.getWpIds());
		}
		
		if (StringUtils.isNotBlank(pWqInquiryCriteria.getSbShopTeamCode())) {
			daoCriteria.andEqual("SB_SHOP_CD", pWqInquiryCriteria.getSbShopTeamCode());
		}

		if (StringUtils.isNotBlank(pWqInquiryCriteria.getBomOcId())) {
			daoCriteria.andEqual("BOM_OC_ID", pWqInquiryCriteria.getBomOcId());
		}

		if (StringUtils.isNotBlank(pWqInquiryCriteria.getSbActvId())) {
			daoCriteria.andEqual("SB_ACTV_ID", pWqInquiryCriteria.getSbActvId());
		}
		if (StringUtils.isNotBlank(pWqInquiryCriteria.getSrvType())) {
			if (StringUtils.isNotBlank(pWqInquiryCriteria.getRelatedSrvType())) {
				if ("IMS".equals(pWqInquiryCriteria.getSrvType()) || "FSA".equals(pWqInquiryCriteria.getSrvType())) {
					daoCriteria.andIn("TYPE_OF_SRV", (Object[]) new String[] {"IMS", "FSA"});
				} else {
					daoCriteria.andEqual("TYPE_OF_SRV", pWqInquiryCriteria.getSrvType());
				}
			} else {
				if ("IMS".equals(pWqInquiryCriteria.getSrvType()) || "FSA".equals(pWqInquiryCriteria.getSrvType())) {
					daoCriteria.andEqual(new String[] {"TYPE_OF_SRV", "RELATED_SRV_TYPE"}, (Object[]) new String[] {"IMS", "FSA"});
				} else {
					daoCriteria.andEqual(new String[] {"TYPE_OF_SRV", "RELATED_SRV_TYPE"}, pWqInquiryCriteria.getSrvType());
				}
			}
		}

		if (StringUtils.isNotBlank(pWqInquiryCriteria.getSrvNum())) {
			if (StringUtils.isNotBlank(pWqInquiryCriteria.getRelatedSrvNum())) {
				daoCriteria.andEqual("SRV_ID", pWqInquiryCriteria.getSrvNum());
			} else {
				daoCriteria.andEqual(new String[] {"SRV_ID", "RELATED_SRV_NUM"}, pWqInquiryCriteria.getSrvNum());
			}
		}

		if (StringUtils.isNotBlank(pWqInquiryCriteria.getRelatedSrvType()) && !"ZZZ".equals(pWqInquiryCriteria.getRelatedSrvType())) {
			if ("IMS".equals(pWqInquiryCriteria.getRelatedSrvType()) || "FSA".equals(pWqInquiryCriteria.getRelatedSrvType())) {
				daoCriteria.andIn("RELATED_SRV_TYPE", (Object[]) new String[] {"IMS", "FSA"});
			} else {
				daoCriteria.andEqual("RELATED_SRV_TYPE", pWqInquiryCriteria.getSrvType());
			}
		}

		if (StringUtils.isNotBlank(pWqInquiryCriteria.getRelatedSrvNum())) {
			daoCriteria.andEqual("RELATED_SRV_NUM", pWqInquiryCriteria.getRelatedSrvNum());
		}

		if (StringUtils.isNotBlank(pWqInquiryCriteria.getWqType())) {
			daoCriteria.andEqual("WQ_TYPE", pWqInquiryCriteria.getWqType());
		}

		if (StringUtils.isNotBlank(pWqInquiryCriteria.getWqSubType())) {
			daoCriteria.andEqual("WQ_SUBTYPE", pWqInquiryCriteria.getWqSubType());
		}

		if (StringUtils.isNotBlank(pWqInquiryCriteria.getWqStatus())) {
			if (StringUtils.equals(pWqInquiryCriteria.getWqStatus(), ConstantLkupService.WQ_STATUS_CODE_OUTSTANDING)) {
				daoCriteria.andNotIn("STATUS_CD", (Object[]) SpringApplicationContext.getBean(WorkQueueDataService.class).getWqEndingStatus());
			} else {
				daoCriteria.andEqual("STATUS_CD", pWqInquiryCriteria.getWqStatus());
			}
		}
		
		if (ArrayUtils.isNotEmpty(pWqInquiryCriteria.getSbIds())) {
			daoCriteria.andIn("SB_ID", (Object[]) pWqInquiryCriteria.getSbIds());
		}
		
		if (ArrayUtils.isNotEmpty(pWqInquiryCriteria.getWqNature())) {
			daoCriteria.andIn("WQ_NATURE_ID", (Object[]) pWqInquiryCriteria.getWqNature());
		}
		
		if (StringUtils.isNotBlank(pWqInquiryCriteria.getAssignee())) {
			daoCriteria.andEqual("ASSIGNEE", pWqInquiryCriteria.getAssignee());
		}
		
		addDateRangeCriteria(daoCriteria, "SRD", 
				pWqInquiryCriteria.getSrdRangeFrom(), pWqInquiryCriteria.getSrdRangeTo(), 
				pWqInquiryCriteria.getDatePattern());

		addDateRangeCriteria(daoCriteria, "RECEIVE_DATE", 
				pWqInquiryCriteria.getWqReceiveDateRangeFrom(), pWqInquiryCriteria.getWqReceiveDateRangeTo(), 
				pWqInquiryCriteria.getDatePattern());

		
        if (StringUtils.isNotBlank(pWqInquiryCriteria.getWqSerialRangeFrom()) && StringUtils.isNotBlank(pWqInquiryCriteria.getWqSerialRangeTo())) {
			daoCriteria.andBetween("WQ_SERIAL", pWqInquiryCriteria.getWqSerialRangeFrom().toUpperCase(), pWqInquiryCriteria.getWqSerialRangeTo().toUpperCase() + "/99");
		} else if (StringUtils.isNotBlank(pWqInquiryCriteria.getWqSerialRangeFrom())) {
			daoCriteria.andGreaterEqual("WQ_SERIAL", pWqInquiryCriteria.getWqSerialRangeFrom().toUpperCase());
        } else if (StringUtils.isNotBlank(pWqInquiryCriteria.getWqSerialRangeTo())) {
			daoCriteria.andLessEqual("WQ_SERIAL", pWqInquiryCriteria.getWqSerialRangeTo().toUpperCase() + "/99");
        }

        return daoCriteria;
	}
	
	private void addDateRangeCriteria(Criteria pCriteria, String pDbFieldName,
			String pDateRangeFrom, String pDateRangeTo, String pDateFormat)
			throws Exception {
		try {
	        if (StringUtils.isNotBlank(pDateRangeFrom) && StringUtils.isNotBlank(pDateRangeTo)) {
				pCriteria.andTruncDateBetween(pDbFieldName, 
						DateUtils.parseDate(pDateRangeFrom, new String[] {pDateFormat}), 
						DateUtils.parseDate(pDateRangeTo, new String[] {pDateFormat}));
			} else if (StringUtils.isNotBlank(pDateRangeFrom)) {
				pCriteria.andGreaterEqual(pDbFieldName, DateUtils.parseDate(pDateRangeFrom, new String[] {pDateFormat}));
			} else if (StringUtils.isNotBlank(pDateRangeTo)) {
				pCriteria.andLessEqual(pDbFieldName, DateUtils.parseDate(pDateRangeTo, new String[] {pDateFormat}));
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	private static final String getWqNatureIdsByTypeSQL = 
			  "SELECT RTRIM(XMLAGG(XMLELEMENT(e, wn.wq_nature_id || '^' || ',') ORDER BY wn.wq_nature_id).EXTRACT('//text()'),',') AS wq_nature_id "
			+ "  FROM Q_WQ_NATURE wn "
            + " WHERE WQ_NATURE_ID MEMBER OF ? "
            + " GROUP BY CASE WHEN (wn.wq_nature_type LIKE 'AMEND%') THEN 'AMEND' ELSE wn.wq_nature_type END";

	@Override
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueDTO createWorkQueue(WorkQueueDTO pWorkQueue,
			WorkQueueNatureDTO[] pWorkQueueNatures,
			String pUser)
			throws Exception {
		
		if (ArrayUtils.isEmpty(pWorkQueueNatures)) {
			throw new Exception("WORK QUEUE NATURE IS EMPTY");
		}
		
		ArrayList<WorkQueueNatureDTO> tmpWqList = new ArrayList<WorkQueueNatureDTO>(pWorkQueueNatures.length);
		ArrayList<String> wqNatureIdList = new ArrayList<String>(pWorkQueueNatures.length);
		boolean isFullWqExists = false;
		for (WorkQueueNatureDTO wqNature : pWorkQueueNatures) {
			wqNatureIdList.add(wqNature.getWorkQueueNatureId());
			if (WQ_SUBTYPE_PARTIAL_WQ.equals(wqNature.getWorkQueueSubType())) {
				tmpWqList.add(wqNature);
			} else if (WQ_SUBTYPE_FULL_WQ.equals(wqNature.getWorkQueueSubType())) {
				isFullWqExists = true;
			}
			
			if (WQ_TYPE_SB_LTS.equals(wqNature.getWorkQueueType()) && StringUtils.isNotBlank(pWorkQueue.getSbId())) {
				wqNature.setWorkQueueType("D".equals(pWorkQueue.getSbId().substring(4, 5)) ? WQ_TYPE_SB_DEL : WQ_TYPE_SB_EYE );
			}
		}
		
		if (isFullWqExists && tmpWqList.size() > 0) {
			for (WorkQueueNatureDTO wqNature : tmpWqList) {
				wqNature.setWorkQueueSubType(WQ_SUBTYPE_FULL_WQ);
			}
		}
		
		if (StringUtils.isBlank(pWorkQueue.getBomOcId())) {
			pWorkQueue.setBomOcId(this.getBomOcId(pWorkQueue.getSbId(), pWorkQueue.getTypeOfService(), pWorkQueue.getServiceId()));
		}

		ArrayList<ArrayList<WorkQueueNatureDTO>> wqNatureListByWqNatureType = new ArrayList<ArrayList<WorkQueueNatureDTO>>(); 
		tmpWqList.clear();
		if (pWorkQueueNatures.length == 1) {
			tmpWqList.addAll(Arrays.asList(pWorkQueueNatures));
			wqNatureListByWqNatureType.add(tmpWqList);
		} else {
			String[] wqNatureIdsByType = OracleSelectHelper.getSqlFirstColumnStrings("WorkQueueDS", getWqNatureIdsByTypeSQL, 
					new Object[] {new OraArrayNumber("OPS$CNM.TABLE_NUMBER", wqNatureIdList.toArray(new String[0]))});
			if (wqNatureIdsByType.length == 1) {
				tmpWqList.addAll(Arrays.asList(pWorkQueueNatures));
				wqNatureListByWqNatureType.add(tmpWqList);
			} else {
				for (String wqIdString : wqNatureIdsByType) {
					tmpWqList = new ArrayList<WorkQueueNatureDTO>();
					for (WorkQueueNatureDTO wqNature : pWorkQueueNatures) {
						if (wqIdString.contains(wqNature.getWorkQueueNatureId() + "^")) {
							tmpWqList.add(wqNature);
						}
					}
					wqNatureListByWqNatureType.add(tmpWqList);
				}
			}
		}
		
		WorkQueueDataService workQueueDataService = SpringApplicationContext.getBean(WorkQueueDataService.class);
		String oriWqBatchId = pWorkQueue.getCreatedBatchId();
		WorkQueueDocumentDTO[] wqDocs = null;
		TreeSet<WorkQueueDocumentDTO> wqDocList = new TreeSet<WorkQueueDocumentDTO>(new Comparator<WorkQueueDocumentDTO>() {
			@Override
			public int compare(WorkQueueDocumentDTO pO1,
					WorkQueueDocumentDTO pO2) {
				return pO1.getDocumentTypeId().compareTo(pO2.getDocumentTypeId());
			}
		});
		TreeSet<String> createdWqBatchIdSet = new TreeSet<String>();
		TreeSet<String> createdWqWpAssgnIdSet = new TreeSet<String>();
		
		for (ArrayList<WorkQueueNatureDTO> wqNatureGroup : wqNatureListByWqNatureType) {
			try {
				pWorkQueue.setWorkQueueNatures(wqNatureGroup.toArray(new WorkQueueNatureDTO[0]));
				pWorkQueue.setCreatedBatchId(oriWqBatchId);
				
				pWorkQueue = workQueueDataService.createWorkQueue(pWorkQueue, pUser);
				createdWqBatchIdSet.add(pWorkQueue.getCreatedBatchId());
				if (ArrayUtils.isNotEmpty(pWorkQueue.getCreatedWpWqAssignIds())) {
					createdWqWpAssgnIdSet.addAll(Arrays.asList(pWorkQueue.getCreatedWpWqAssignIds()));
				}
				
				wqDocs = getWorkQueueRequiredDocuments(pWorkQueue, pUser);
				if (ArrayUtils.isNotEmpty(wqDocs)) {
					wqDocList.addAll(Arrays.asList(wqDocs));
				}
			} catch (Exception e) {
				logger.error(ExceptionUtils.getFullStackTrace(e));
				throw e;
			}
		}
		
		StringBuffer sb = new StringBuffer();
		for (String wqBatchId : createdWqBatchIdSet) {
			if (sb.length() > 0) {
				sb.append(",");
			}
			sb.append(wqBatchId);
		}
		
		pWorkQueue.setCreatedBatchId(sb.toString());
		pWorkQueue.setCreatedWpWqAssignIds(createdWqWpAssgnIdSet.toArray(new String[0]));
		
		if (!wqDocList.isEmpty()) {
			pWorkQueue.setDocuments(wqDocList.toArray(new WorkQueueDocumentDTO[0]));
		}
		return pWorkQueue;
	}

	@Override
	@Transactional
	public void updateWorkQueue(WorkQueueDTO pWorkQueue, String pUser) throws Exception {
		try {
			WorkQueueDataService workQueueDataService = SpringApplicationContext.getBean(WorkQueueDataService.class);
			workQueueDataService.updateWorkQueue(pWorkQueue, pUser);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	
	/* (non-Javadoc)
	 * @see com.pccw.wq.service.WorkQueueService#getWorkQueueRequiredDocuments(com.pccw.wq.schema.WorkQueueNatureDTO[])
	 */
	@Override
	public WorkQueueDocumentDTO[] getWorkQueueRequiredDocuments(
			WorkQueueDTO pWorkQueue, String pUser) throws Exception {
		return SpringApplicationContext.getBean(WorkQueueDataService.class).getWorkQueueRequiredDocuments(pWorkQueue, pUser);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void attachWorkQueueDocument(WorkQueueDTO pWorkQueue,
			WorkQueueDocumentDTO[] pWorkQueueDocuments,
			String pUser)
			throws Exception {
		if (ArrayUtils.isEmpty(pWorkQueueDocuments)) {
			throw new Exception("WorkQueueDocumentDTO[] IS EMPTY");
		}
		SpringApplicationContext.getBean(WorkQueueDataService.class).attachWorkQueueDocument(pWorkQueue, pWorkQueueDocuments, pUser);
	}
	
	@Override
	@Transactional(readOnly=true)
	public String [] getValidWorkQueueWorkPartyStaff(String [] wqWpAssgnId, String pUser) throws Exception {
		try {
			return SpringApplicationContext.getBean(WorkQueueDataService.class).getValidWorkQueueWorkPartyStaff(wqWpAssgnId, pUser);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	@Override	
	@Transactional(rollbackFor={Exception.class})
	public int assignWorkQueueNature(String[] pWqWpAssgnId,
			String pWpStaffId, String pRemarks, String pUser) throws Exception {
		try {
			return SpringApplicationContext.getBean(WorkQueueDataService.class).assignWorkQueueNature(pWqWpAssgnId,pWpStaffId,pRemarks, pUser);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
		
	@Override	
	@Transactional(rollbackFor={Exception.class})
	public int changeWorkQueueStatus(String[] pWqWpAssgnId, String pStatusCd,
			String pReasonCd, String pOcId, String pRemarks, String pUser) throws Exception {
		try {
			return SpringApplicationContext.getBean(WorkQueueDataService.class)
					.changeWorkQueueStatus(pWqWpAssgnId, pStatusCd, pReasonCd,
							pOcId, pRemarks, pUser);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public RemarkDTO [] getWorkQueRemarks(String pWqWpAssgnId, String pUser) throws Exception {
		try {
			return SpringApplicationContext.getBean(WorkQueueDataService.class).getWorkQueRemarksByWqWpAssgnId(
					pWqWpAssgnId, new String[] {"wqBatchId"}, pUser);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	@Override	
	@Transactional(rollbackFor={Exception.class})
	public boolean saveRemark(String pWqWpAssgnId, String pRemarks, String pUser) throws Exception {
		try {
			return SpringApplicationContext.getBean(WorkQueueDataService.class).saveRemark(pWqWpAssgnId,pRemarks,pUser);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	@Override	
	@Transactional(readOnly=true)
	public WqStatusLogFormDTO [] getWorkQueueStatusLog(String pWqWpAssgnId, String pUser) throws Exception {
		try {
			return SpringApplicationContext.getBean(WorkQueueDataService.class).getWorkQueueStatusLog(pWqWpAssgnId,pUser);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueDocumentDTO downloadWorkQueueDocument(String pWqWpAssgnId,
			String pWqWpAssgnDocId, String pUser) throws Exception {
		try {
			return SpringApplicationContext.getBean(WorkQueueDataService.class).downloadWorkQueueDocument(pWqWpAssgnId, pWqWpAssgnDocId, pUser);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueDocumentDTO[] downloadWorkQueueDocuments(String pWqWpAssgnId, String pUser) throws Exception {
		try {
			return SpringApplicationContext.getBean(WorkQueueDataService.class).downloadWorkQueueDocuments(pWqWpAssgnId, pUser);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}

	
	@Override
	@Transactional(readOnly=true)
	public WorkingPartyDTO[] getWorkingPartyByStaffId(String pLoginId) throws Exception {
		return SpringApplicationContext.getBean(WorkQueueDataService.class).getWorkingPartyByStaffId(pLoginId, "ACCESS");
	}

	@Override
	@Transactional(readOnly=true)
	public WqInquiryResultFormDTO getWorkQueueDetail(String pWqWpAssgnId)
			throws Exception {
		
		Criteria daoCriteria = new Criteria();
		daoCriteria.orderBy("RECEIVE_DATE");
		daoCriteria.andEqual("WQ_WP_ASSGN_ID", pWqWpAssgnId);
		
		SearchResult<WqInquiryResultFormDTO> result = SpringApplicationContext.getBean(WorkQueueDataService.class)
				.searchWorkQueue(daoCriteria);

		if (result.getTotalCount() <= 0) {
			return null;
		}

		return result.getResult().get(0);
	}

	
	@Transactional(readOnly=true)
	public List<WqInquiryResultFormDTO> getWorkQueueDetail(String pWqId, String pBatchId)
			throws Exception {
		
		Criteria daoCriteria = new Criteria();
		daoCriteria.orderBy("RECEIVE_DATE");
		daoCriteria.andEqual("WQ_ID", pWqId);
		daoCriteria.andEqual("WQ_BATCH_ID", pBatchId);
		
		SearchResult<WqInquiryResultFormDTO> result = SpringApplicationContext.getBean(WorkQueueDataService.class)
				.searchWorkQueue(daoCriteria);

		if (result.getTotalCount() <= 0) {
			return new ArrayList<WqInquiryResultFormDTO>();
		}

		return result.getResult();
	}

	
	@Transactional(readOnly=true)
	public List<WqInquiryResultFormDTO> getWqNaturestatus(String pSbId)
			throws Exception {
		
		Criteria daoCriteria = new Criteria();
		daoCriteria.orderBy("STATUS_DATE");
		daoCriteria.andEqual("wq.sb_id", pSbId);
		
		SearchResult<WqInquiryResultFormDTO> result = SpringApplicationContext.getBean(WorkQueueDataService.class)
				.serachWqNatureStatusWithSbId(daoCriteria);

		if (result.getTotalCount() <= 0) {
			return new ArrayList<WqInquiryResultFormDTO>();
		}

		return result.getResult();
	}
	
	
	@Override
	@Transactional(readOnly=true)
	public String getOutstandingWQInquiryUrl(String pSbId, String pUser) throws Exception {
		WorkQueueInquiryCriteriaDTO criteria = new WorkQueueInquiryCriteriaDTO();
		criteria.setSbIds(new String[] {pSbId});
		criteria.setWqStatus(ConstantLkupService.WQ_STATUS_CODE_OUTSTANDING);
		if (searchWorkQueue(criteria, null, pUser).getTotalCount() > 0) {
			return "/qm/ShowWQ?showResult=true&wqStatus=" + ConstantLkupService.WQ_STATUS_CODE_OUTSTANDING + "&sbId=" + pSbId + "\"";
		}
		return null;
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public void sbOrderCancelled(String pSbId, boolean pIsSystemCancel, String pUser) throws Exception {
		WorkQueueInquiryCriteriaDTO criteria = new WorkQueueInquiryCriteriaDTO();
		criteria.setSbIds(new String[] {pSbId});
		criteria.setWqStatus(ConstantLkupService.WQ_STATUS_CODE_OUTSTANDING);
		SearchResult<WqInquiryResultFormDTO> searchResult = searchWorkQueue(criteria, null, pUser);
		if (searchResult.getTotalCount() <= 0) {
			return;
		}
		TreeSet<String> wqWpAssignIdSet = new TreeSet<String>(); 
		for (WqInquiryResultFormDTO dto : searchResult.getResult()) {
			wqWpAssignIdSet.add(dto.getWqWpAssgnId());
		}
		this.changeWorkQueueStatus(wqWpAssignIdSet.toArray(new String[0]), 
				pIsSystemCancel ? ConstantLkupService.WQ_STATUS_SYSTEM_CANCELLED : ConstantLkupService.WQ_STATUS_CANCELLED, 
				null, null, null, pUser);
	}
	
	private static final String getChangeWqStatusBySbActionSql = "select substr(grp_id, 18, 20) || '^' || DESCRIPTION from Q_DIC_CODE_LKUP where GRP_ID like 'CHG_WQ_STS_BY_SB-%' and code = ?";
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void changeWqStatusBySbAction(String pSbId, String pAction, String pUser) throws Exception {

		String[] changeStatusKeys = OracleSelectHelper.getSqlFirstColumnStrings("WorkQueueDS", getChangeWqStatusBySbActionSql, new Object[] {pAction});
		if (ArrayUtils.isEmpty(changeStatusKeys)) {
			return;
		}
		
		String[] tmp = null;
		String targetStatus = null;
		WorkQueueInquiryCriteriaDTO criteria = null;
		SearchResult<WqInquiryResultFormDTO> searchResult = null;
		TreeSet<String> wqWpAssignIdSet = new TreeSet<String>();
		for (String changeStatusKey : changeStatusKeys) {
			tmp = changeStatusKey.split("\\^");
			if (tmp.length < 2) {
				continue;
			}

			targetStatus = tmp[0];
			criteria = new WorkQueueInquiryCriteriaDTO();
			criteria.setSbIds(new String[] {pSbId});
			criteria.setWqStatus(ConstantLkupService.WQ_STATUS_CODE_OUTSTANDING);
			criteria.setWqNature(tmp[1].split(","));
			searchResult = searchWorkQueue(criteria, null, pUser);
			if (searchResult.getTotalCount() <= 0) {
				continue;
			}
			
			wqWpAssignIdSet.clear();
			for (WqInquiryResultFormDTO dto : searchResult.getResult()) {
				wqWpAssignIdSet.add(dto.getWqWpAssgnId());
			}

			this.changeWorkQueueStatus(wqWpAssignIdSet.toArray(new String[0]), targetStatus, null, null, null, pUser);
		}
	}

	private static final String getBomOcIdSql = "SELECT TO_CHAR(max(od.oc_id)) " +
			                                    "  FROM b_ord_dtl od, b_ord_srv os " +
                                                " WHERE od.oc_id = os.oc_id " +
                                                "   AND od.dtl_id = os.dtl_id " +
                                                "   AND od.agreement_num = ? " +
                                                "   AND od.type_of_srv = ? " +
                                                "   AND os.ex_srv_id = ?";
	
	private static final String getImsCustNumLoginIdSql = 
			  "select bc.cust_no || '^' || boi.LOGIN_ID "
			+ "  from bomweb_customer bc, bomweb_order_ims boi"
			+ " where boi.order_id=bc.order_id"
			+ "   AND boi.order_id=?";
	
	private static final String getImsBomOcIdSql = 
			  "select max(od.oc_id) "
			+ "  from b_ord_member om,b_ord_dtl od, b_ord_srv_acct osa, b_account a, b_customer c "
			+ " where c.cust_num = ? "
			+ "   and c.system_id='IMS' "
			+ "   and c.cust_level = 0 "
			+ "   and a.cust_num = c.cust_num "
			+ "   and a.system_Id = 'IMS' "
			+ "   and osa.acct_num = to_number(a.acct_num) "
			+ "   and osa.oc_id = od.oc_id "
			+ "   and osa.dtl_id=od.dtl_id "
			+ "   and od.agreement_num = ? "
			+ "   and om.oc_id = od.oc_id "
			+ "   and om.dtl_id = od.dtl_id "
			+ "   and om.login_id= ?";
	
	@Override
	public String getBomOcId(String pSbId, String pSrvType, String pSrvNum) throws Exception {
		if ("TEL".equals(pSrvType)) {
			return OracleSelectHelper.getSqlFirstRowColumnString("BomDS", getBomOcIdSql, new Object[] {pSbId, pSrvType, pSrvNum});
		} else if ("IMS".equals(pSrvType) || "FSA".equals(pSrvType)) {
			String tmpStr = OracleSelectHelper.getSqlFirstRowColumnString("BomWebPortalDS", getImsCustNumLoginIdSql, new Object[] { pSbId });
			if (StringUtils.isEmpty(tmpStr)) {
				return null;
			}
			String[] values = tmpStr.split("\\^");
			if (values.length > 1) {
				return OracleSelectHelper.getSqlFirstRowColumnString("BomDS", getImsBomOcIdSql, new Object[] { values[0], pSbId, values[1] });
			} else {
				return null;				
			}
		}
		return null;
	}
	
	@Override
	public FastByteArrayOutputStream genWqCoverSheet(WqInquiryResultFormDTO pWqDetail) throws Exception {
		FastByteArrayOutputStream coverSheetBaos = new FastByteArrayOutputStream();

		Map<String, Object> rptParamMap = new TreeMap<String, Object>();
		rptParamMap.put(WorkQueueReportDataServiceImpl.PARAM_WQ_DETAIL, pWqDetail);
		
		SpringApplicationContext.getBean(ReportService.class).generateReport(
				"wq/wqCoverSheet", "en", 
				rptParamMap, 
				coverSheetBaos, ReportService.EXPORT_TYPE_PDF);
		return coverSheetBaos;
	}
	
	@Override
	@Transactional(readOnly=true)
	public List<byte[]> getWqCoverSheets(String pWqId, String pBatchId) throws Exception {
		ArrayList<byte[]> rtnList = new ArrayList<byte[]>();
		
		for ( WqInquiryResultFormDTO wqDetail : this.getWorkQueueDetail(pWqId, pBatchId)) {
			rtnList.add(this.genWqCoverSheet(wqDetail).getByteArray());
		}
		
		return rtnList;
	}
	
	@Override
	public void fillReportDataWqCoverSheet(ReportDTO pReportDTO, Map<String, Object> pRptParamMap) throws Exception {
		WqInquiryResultFormDTO wqDetail = (WqInquiryResultFormDTO) pRptParamMap.get(WorkQueueReportDataServiceImpl.PARAM_WQ_DETAIL);
		if (wqDetail == null) {
	    	wqDetail = (WqInquiryResultFormDTO) SpringApplicationContext.getBean(WorkQueueService.class)
	    					.getWorkQueueDetail((String) pRptParamMap.get(WorkQueueReportDataServiceImpl.PARAM_WP_WQ_ASSGN_ID));
		}
		BeanUtils.copyProperties(pReportDTO, wqDetail);
		BeanUtils.setProperty(pReportDTO, "wqReceiveDate", feDateFormat.format(dbDateFormat.parse(wqDetail.getWqReceiveDate())));
		BeanUtils.setProperty(pReportDTO, "printDate", feDateFormat.format(new Date()));
		BeanUtils.setProperty(pReportDTO, "wqStatusDate", feDateFormat.format(dbDateFormat.parse(wqDetail.getWqStatusDate())));
		if (StringUtils.isNotBlank(wqDetail.getSrd())) {
			BeanUtils.setProperty(pReportDTO, "srd", srdDateFormat.format(dbDateFormat.parse(wqDetail.getSrd())));
		}
		
		RemarkDTO[] remarks = SpringApplicationContext.getBean(WorkQueueDataService.class)
			.getWorkQueRemarksByWqWpAssgnId(wqDetail.getWqWpAssgnId(), new String[] {"wqBatchId"}, "");
		if (ArrayUtils.isEmpty(remarks)) {
			return;
		}	
		
		WorkQueueCacheService workQueueCacheService = (WorkQueueCacheService) SpringApplicationContext.getBean("WQNatureCache");
		
		for (RemarkDTO remarkDTO : remarks) {
			((WorkQueueRptDTO) pReportDTO).addRemarks(
					remarkDTO.getRemarkSequence(), 
					feDateFormat.format(dbDateFormat.parse(remarkDTO.getRemarkDate())), 
					remarkDTO.getCreateBy(), 
					remarkDTO.getRemarkNatureId(), 
					(String)workQueueCacheService.get(remarkDTO.getRemarkNatureId()), 
					remarkDTO.getRemarkContentHtml());
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public SbWqInquiryResultFormDTO[] getWorkQueueViewBySbId(String pSbId, String pUser) throws Exception {
		return SpringApplicationContext.getBean(WorkQueueDataService.class).getWorkQueueViewBySbId(pSbId, pUser);		
	}
	
	private static String WQ_ENDING_STATUS_STRING = null;
	
	@Transactional(readOnly=true)
	@Override
	public String getWqEndingStatusString() throws Exception {
		if (WQ_ENDING_STATUS_STRING == null) {
			WQ_ENDING_STATUS_STRING = OracleSelectHelper.getSqlFirstRowColumnString("WorkQueueDS", 
					"select replace(RTRIM(XMLAGG(XMLELEMENT(e, chr(39) || code || chr(39) || ',') ORDER BY code).EXTRACT('//text()'), ','), chr(38) || 'apos;', '''')"
					+ "from q_dic_code_lkup where grp_id = 'WQ_ENDING_STATUS'");
		}
		return WQ_ENDING_STATUS_STRING;
	}
	
	private static final String SQL_IS_INPUT_BOM_OCID_REQUIRED = 
			  "select TO_CHAR(count(*)) "
			+ "  from q_wq_nature wn, q_dic_code_lkup dcl "
			+ " where wn.wq_nature_id in (SELECT WQ_NATURE_ID FROM q_wq_wp_search_id_v where BOM_OC_ID IS NULL AND wq_wp_assgn_id MEMBER OF ?) "
			+ "   AND dcl.grp_id = 'REQ_OCID_NATURE_TYPE' "
			+ "   AND dcl.code = wn.WQ_NATURE_TYPE "
			+ "   AND INSTR(dcl.description, ?) > 0";
	
	@Transactional(readOnly=true)
	@Override
	public boolean isInputBomOcIdRequired(String[] pWqWpAssgnIds, String pTargetStatusCode) throws Exception {
		String value = OracleSelectHelper.getSqlFirstRowColumnString("WorkQueueDS", SQL_IS_INPUT_BOM_OCID_REQUIRED, 
				new Object[] { new OraArrayNumber("OPS$CNM.TABLE_NUMBER", pWqWpAssgnIds), pTargetStatusCode});
		return !("0".equals(value));
	}
	
	@Override
	public void genBulkPrintSummary(ArrayList<WqInquiryResultFormDTO> pWqResultList, String pWqPrintOption, String pWqPrinter,  String pUser) throws Exception {
		try{
			if (StringUtils.isNotBlank(pWqPrintOption) && StringUtils.isNotBlank(pWqPrinter)) {
				boolean isSupportDirectPdfPrint = "Y".equals(OracleSelectHelper.getSqlFirstRowColumnString("WorkQueueDS", "SELECT DIRECT_PDF_PRINT FROM Q_PRINTER WHERE IP_ADDRESS = ? ", new Object[] {pWqPrinter}));
				List<PrintSummaryDTO> printSummaryDTOList = new ArrayList<PrintSummaryDTO>(); 
				List<InputStream>  inputStreamList = null;
				List<InputStream>  attachmentInputStreamList = null;
				WorkQueueDocumentDTO[] wqDocDtoList = null;
				StringBuffer sbTotalPage = null;
				byte[] attachment = null;
				FastByteArrayOutputStream baos = new FastByteArrayOutputStream();
				PdfHeaderFooter pdfheaderFooter = new PdfHeaderFooter();
				pdfheaderFooter.setHeaderAlign(PdfHeaderFooter.ALIGN_RIGHT);
				pdfheaderFooter.setHeaderFontSize(8);
				ArrayList<InputStream> tmpInputStreamList = new ArrayList<InputStream>();
				
				for ( WqInquiryResultFormDTO wqDetail : pWqResultList) {
					inputStreamList = new ArrayList<InputStream>();
					attachmentInputStreamList = new ArrayList<InputStream>();
					
					sbTotalPage = new StringBuffer();
					PrintSummaryDTO printSummaryDTO = new PrintSummaryDTO();
					printSummaryDTO.setAssignee(wqDetail.getAssignee());
					printSummaryDTO.setShopCode(wqDetail.getSbShopCode());
					printSummaryDTO.setWq(wqDetail.getWqSubTypeDesc());
					printSummaryDTO.setWqSerial(wqDetail.getWqSerial());
					printSummaryDTO.setWqType(wqDetail.getWqTypeDesc());
					printSummaryDTO.setSrvType(StringUtils.defaultString(wqDetail.getTypeOfService()));
					printSummaryDTO.setSrvNum(StringUtils.defaultString(wqDetail.getServiceId()));


					if (StringUtils.contains(pWqPrintOption, "COVER")) {
						try {
							inputStreamList.add(this.genWqCoverSheet(wqDetail).getInputStream());
							sbTotalPage.append("COVER");
						} catch (Exception ignore) {
							logger.error(ExceptionUtils.getFullStackTrace(ignore));
							printSummaryDTO.setPrintStatus("Failed");
						}
					}
					
					if (StringUtils.contains(pWqPrintOption, "ATTACH")) {
						try {
							wqDocDtoList = this.downloadWorkQueueDocuments(wqDetail.getWqWpAssgnId(), pUser);
							if (wqDocDtoList != null){
								for ( WorkQueueDocumentDTO wqDocDto : wqDocDtoList) {
									attachment = FileUtils.readFileToByteArray(new File(wqDocDto.getAttachmentFullPath()));
									
									baos = new FastByteArrayOutputStream();
									pdfheaderFooter.setHeaderText(this.getAttachmentHeaderString(wqDetail));
									tmpInputStreamList.clear();
									tmpInputStreamList.add(new FastByteArrayInputStream(attachment, attachment.length));
									PdfUtil.concatPDFs(tmpInputStreamList, baos, false, pdfheaderFooter);
									
									inputStreamList.add(baos.getInputStream());
									attachmentInputStreamList.add(baos.getInputStream());
									attachment = null;
								}
							}
							
							if (sbTotalPage.length() > 0) {
								sbTotalPage.append(" + ");
							}
							sbTotalPage.append(Integer.toString(PdfUtil.countPdfPage(attachmentInputStreamList)));
						} catch (Exception ignore) {
							logger.error(ExceptionUtils.getFullStackTrace(ignore));
							printSummaryDTO.setPrintStatus("Failed");
						}
					}
					
					printSummaryDTO.setTotalPage(sbTotalPage.toString());
					
					try {
						HpDirectPdfPrinter.printPdf(pWqPrinter, (InputStream[]) inputStreamList.toArray(new InputStream[inputStreamList.size()]), isSupportDirectPdfPrint);
					} catch (Exception ignore) {
						printSummaryDTO.setPrintStatus("Failed");
					}

					if (StringUtils.isBlank(printSummaryDTO.getPrintStatus())) {
						printSummaryDTO.setPrintStatus("Success");
					}
					printSummaryDTOList.add(printSummaryDTO);
				}
						
				if (StringUtils.contains(pWqPrintOption, "SUM")) {
					FastByteArrayOutputStream summarySheetBaos = new FastByteArrayOutputStream();

					Map<String, Object> rptParamMap = new TreeMap<String, Object>();
					rptParamMap.put(WorkQueueReportDataServiceImpl.PARAM_WQ_BULK_PRINT_SUMMARY, printSummaryDTOList);
					
					SpringApplicationContext.getBean(ReportService.class).generateReport(
							"wq/bulkPrintCoverSheet", "en", 
							rptParamMap, 
							summarySheetBaos, ReportService.EXPORT_TYPE_PDF);
					
					HpDirectPdfPrinter.printPdf(pWqPrinter, summarySheetBaos.getInputStream(), isSupportDirectPdfPrint);
				}
			}
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw e;
		}
	}
	
	@Override
	public String getAttachmentHeaderString(WqInquiryResultFormDTO pWqDetail) throws Exception {
		StringBuffer header = new StringBuffer("WQ Serial: {0}");
		ArrayList<String> parameterList = new ArrayList<String>();
		parameterList.add(pWqDetail.getWqSerial());

		String receiveDate = null;
		if (StringUtils.isNotBlank(pWqDetail.getWqReceiveDate())) {
			receiveDate = this.attachmentHeaderDateFormat.format(this.dbDateFormat.parse(pWqDetail.getWqReceiveDate()));
		}

		if (StringUtils.isNotBlank(receiveDate)) {
			header.append(" / Receive Date: {");
			header.append(parameterList.size());
			header.append("}");
			parameterList.add(receiveDate);
		}
		
		if (StringUtils.isNotBlank(pWqDetail.getSbId())) {
			header.append(" / SB ID: {");
			header.append(parameterList.size());
			header.append("}");
			parameterList.add(pWqDetail.getSbId());
		}
		
		if (StringUtils.isNotBlank(pWqDetail.getSbActvId())) {
			header.append(" / SB ACTV ID: {");
			header.append(parameterList.size());
			header.append("}");
			parameterList.add(pWqDetail.getSbActvId());
		}

		if (StringUtils.isNotBlank(pWqDetail.getBomOcId())) {
			header.append(" / OC ID: {");
			header.append(parameterList.size());
			header.append("}");
			parameterList.add(pWqDetail.getBomOcId());
		}

		return MessageFormat.format(
					header.toString(), 
					parameterList.toArray()); 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void fillReportDataBulkPrintSummary(ReportDTO pReportDTO, Map<String, Object> pRptParamMap) throws Exception {
		BeanUtils.setProperty(((BulkPrintRptDTO) pReportDTO), "printOutDate", dbDateFormat.format(new Date()));
		
		List<PrintSummaryDTO> printSummaryDTOList =(ArrayList<PrintSummaryDTO>) pRptParamMap.get(WorkQueueReportDataServiceImpl.PARAM_WQ_BULK_PRINT_SUMMARY);
				
		if (printSummaryDTOList == null || printSummaryDTOList.isEmpty() || printSummaryDTOList.size() == 0) {
			return;
		}	
		
		for (PrintSummaryDTO printSummaryDTO : printSummaryDTOList) {
			((BulkPrintRptDTO) pReportDTO).addDetails(
					printSummaryDTO.getWqSerial(),
					printSummaryDTO.getSrvType(),
					printSummaryDTO.getSrvNum(),
					printSummaryDTO.getWqType(), 
					printSummaryDTO.getWq(), 
					printSummaryDTO.getShopCode(), 
					printSummaryDTO.getTotalPage(), 
					printSummaryDTO.getAssignee(), 
					printSummaryDTO.getPrintStatus()
					);
		}
	}
		
	@Override
	public void sbOrderFollowUpCompleted(String pSbId, String pUser) throws Exception {
		//METHOD TO BE REMOVE !!!
	}
	
	@Override
	public String getUrlByAssignId(String pAssignId,
			WorkingPartyDTO[] pWorkingPartyDTO) throws Exception {

		if (pAssignId != null && !"null".equals(pAssignId)) {
			WorkQueueInquiryCriteriaDTO workQueueInquiryCriteriaDTO = new WorkQueueInquiryCriteriaDTO();
			workQueueInquiryCriteriaDTO.setWqWpAssgnId(pAssignId);
			SearchResult<WqInquiryResultFormDTO> result = searchWorkQueue(
					workQueueInquiryCriteriaDTO, null, null);
			if (result.getResult().size() > 0) {
				if (result.getResult().get(0).getUrl().length() > 0	&& pWorkingPartyDTO != null) {
					for (WorkingPartyDTO workingPartyDTO : pWorkingPartyDTO) {
						if (workingPartyDTO.getWpId().equals(
								result.getResult().get(0).getWpId())) {
							return result.getResult().get(0).getUrl();
						}
					}

				}
			}
		}
		return null;

	}
	

	
	private static final String SQL_IMS_GET_PDF_PATHS = 
			  "select file_path_name from bomweb_all_ord_doc where order_id = ? and DOC_TYPE in ('I008','I009','I010') order by seq_num desc";
	
	@Transactional(readOnly=true)
	@Override
	public List<String> imsGetPdfPaths(String sbid) throws Exception {
		String[] value = OracleSelectHelper.getSqlFirstColumnStrings("WorkQueueDS", SQL_IMS_GET_PDF_PATHS,new Object[]{ sbid});
		return  new ArrayList<String>(Arrays.asList(value));
	}
}