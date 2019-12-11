package com.pccw.wq.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.util.db.OracleSpHelper;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.search.Criteria;
import com.pccw.util.search.SearchResult;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.dao.QDicHndlMethodDtlDAO;
import com.pccw.wq.dao.QWorkQueueDAO;
import com.pccw.wq.dao.QWpStaffAssgnDAO;
import com.pccw.wq.dao.QWqAttbDAO;
import com.pccw.wq.dao.QWqDocumentDAO;
import com.pccw.wq.dao.QWqRemarksDAO;
import com.pccw.wq.dao.QWqWpAssgnDAO;
import com.pccw.wq.dao.QWqWpAssgnStatusLogDAO;
import com.pccw.wq.dao.QWqWpDocumentDAO;
import com.pccw.wq.dao.QWqWpSearchVDAO;
import com.pccw.wq.dao.QWqWqNatureAssgnDAO;
import com.pccw.wq.dao.WorkQueueInquiryDAO;
import com.pccw.wq.schema.dto.RemarkDTO;
import com.pccw.wq.schema.dto.WorkQueueAttributeDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueDocumentDTO;
import com.pccw.wq.schema.dto.WorkQueueInquiryCriteriaDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.schema.dto.WorkQueueTriggerDTO;
import com.pccw.wq.schema.dto.WorkingPartyDTO;
import com.pccw.wq.schema.dto.WpRoleStructureDTO;
import com.pccw.wq.schema.form.SbWqInquiryResultFormDTO;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;
import com.pccw.wq.schema.form.WqStatusLogFormDTO;

public class WorkQueueDataServiceImpl implements WorkQueueDataService {

	private static final Logger logger = Logger.getLogger(WorkQueueDataService.class);

	private static String SP_DISPATCH_WQ = "Q_WORK_QUEUE_PKG.dispatch_work_queue";

	private static String SP_DELIVER_DOC = "Q_WORK_QUEUE_PKG.deliver_work_queue_document";
	
	private static String[] wqEndingStatus = null;
		
	static OracleSpHelper getOracleSpHelper() {
		return SpringApplicationContext.getBean("OracleSpHelper-WorkQueueDS");
	}

	static OracleSelectHelper getOracleSelectHelper() {
		return SpringApplicationContext.getBean("OracleSelectHelper-WorkQueueDS");
	}

	
	private static Mapper getMapper() {
		return SpringApplicationContext.getBean(Mapper.class.getName());
	}
	
	@Override
	@Transactional(readOnly=true)
	public String[] getWqEndingStatus() throws Exception {
		if (wqEndingStatus == null) {
			wqEndingStatus = OracleSelectHelper.getSqlFirstColumnStrings(
					"WorkQueueDS",
					"select code from q_dic_code_lkup where GRP_ID = 'WQ_ENDING_STATUS' order by code");
		}
		return wqEndingStatus;
	}
	
	@Override
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> searchWorkQueue(Criteria pCriteria) throws Exception {
		return SpringApplicationContext.getBean(WorkQueueInquiryDAO.class).searchWorkQueue(pCriteria);
	}

	@Override
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> searchSbIdsWithWq(Criteria[] pCriterias) throws Exception {
		return SpringApplicationContext.getBean(WorkQueueInquiryDAO.class).searchSbIdsWithWq(pCriterias);
	}

	@Override
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> searchSbActvIdsWithWq(Criteria[] pCriterias) throws Exception {
		return SpringApplicationContext.getBean(WorkQueueInquiryDAO.class).searchSbActvIdsWithWq(pCriterias);
	}
	
	@Override
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> serachWqNatureStatusWithSbId(Criteria pCriteria) throws Exception {
		return SpringApplicationContext.getBean(WorkQueueInquiryDAO.class).serachWqNatureStatusWithSbId(pCriteria);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueDTO createWorkQueue(WorkQueueDTO pWorkQueue, String pCreateBy) throws Exception {
		return this.createWorkQueue(pWorkQueue, pCreateBy, true);
	}
		
	@Override
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueDTO createWorkQueue(WorkQueueDTO pWorkQueue,
			String pCreateBy, boolean pIsHandleBeforeCreateTrigger) throws Exception {
		QWorkQueueDAO wqDao = SpringApplicationContext.getBean(QWorkQueueDAO.class);
		DTO2DAO(pWorkQueue, wqDao);
		String wqId = pWorkQueue.getWqId();
		
		if (StringUtils.isEmpty(wqId)) {
			StringBuffer sbSql = new StringBuffer("SELECT TO_CHAR(WQ_ID) FROM Q_WORK_QUEUE WHERE 1 = 1 ");
			ArrayList<String> paramList = new ArrayList<String>();
			
			if (StringUtils.isNotBlank(pWorkQueue.getSbId())) {
				sbSql.append(" AND SB_ID = ? ");
				paramList.add(pWorkQueue.getSbId());
			}

			if (StringUtils.isNotBlank(pWorkQueue.getSbDtlId())) {
				sbSql.append(" AND SB_DTL_ID = TO_NUMBER(?) ");
				paramList.add(pWorkQueue.getSbDtlId());
			}

			if (StringUtils.isNotBlank(pWorkQueue.getSbActvId())) {
				sbSql.append(" AND SB_ACTV_ID = ? ");
				paramList.add(pWorkQueue.getSbActvId());
			}

			if (StringUtils.isNotBlank(pWorkQueue.getBomOcId())) {
				sbSql.append(" AND BOM_OC_ID = ? ");
				paramList.add(pWorkQueue.getBomOcId());
			}
			
			wqId = OracleSelectHelper.getSqlFirstRowColumnString(
						"WorkQueueDS",
						sbSql.toString(),
						paramList.toArray());
		}
		
		if (StringUtils.isEmpty(wqDao.getRelatedSrvNum())) {
			wqDao.setRelatedSrvType(null);
		}
		
		if (StringUtils.isEmpty(wqId)) {
			wqDao.setCreateBy(pCreateBy);
			wqDao.setLastUpdBy(pCreateBy);
			wqDao.doInsert();
		} else {
			updateWorkQueue(pWorkQueue, pCreateBy, true);
			wqDao.setWqId(wqId);
			wqDao.doSelect();
		}

		logger.info("Create WQ - WQ_ID:" + wqId);
		String batchId = "1";
		
		if (StringUtils.isNotBlank(pWorkQueue.getCreatedBatchId())) {
			batchId = pWorkQueue.getCreatedBatchId();
		} else if (StringUtils.isNotBlank(wqId)) {
			batchId = OracleSelectHelper.getSqlFirstRowColumnString(
							"WorkQueueDS",
							"SELECT TO_CHAR(NVL(MAX(WQ_BATCH_ID),0) + 1) FROM Q_WQ_WQ_NATURE_ASSGN WHERE WQ_ID = TO_NUMBER(?) ",
							new Object[] { wqId });
		}
		
		QWqRemarksDAO remarkDAO = null;
		if (pWorkQueue.getRemarks() != null) {
			for (RemarkDTO remark : pWorkQueue.getRemarks()) {
				remarkDAO = SpringApplicationContext.getBean(QWqRemarksDAO.class);
				remarkDAO.setWqId(wqDao.getWqId());
				remarkDAO.setWqBatchId(batchId);
				remarkDAO.setRemarks(remark.getRemarkContent());
				remarkDAO.setCreateBy(pCreateBy);
				remarkDAO.setLastUpdBy(pCreateBy);
				remarkDAO.doInsert();
			}
		}

		QWqAttbDAO wqAttbDAO = null;
		if (pWorkQueue.getAttributes() != null) {
			for (WorkQueueAttributeDTO attbDTO : pWorkQueue.getAttributes()) {
				wqAttbDAO = SpringApplicationContext.getBean(QWqAttbDAO.class);
				wqAttbDAO.setWqId(wqDao.getWqId());
				wqAttbDAO.setWqBatchId(batchId);
				wqAttbDAO.setAttbName(attbDTO.getAttbName());
				wqAttbDAO.setAttbValue(attbDTO.getAttbValue());
				wqAttbDAO.setCreateBy(pCreateBy);
				wqAttbDAO.setLastUpdBy(pCreateBy);
				wqAttbDAO.doInsert();
			}
		}
		
		insertWqNature(wqDao.getWqId(), batchId, pCreateBy, pWorkQueue.getWorkQueueNatures());

		WorkQueueTriggerDTO[] triggers = null;
		if (pIsHandleBeforeCreateTrigger) {
			triggers = getBeforeCreateWorkQueueTriggers(wqDao.getWqId(), batchId);
			if (ArrayUtils.isNotEmpty(triggers)) {
				if (triggers.length == 1) {
					SpringApplicationContext.getBean(WorkQueueTriggerService.class).handleTrigger(triggers[0], pCreateBy);
				} else {
					ArrayList<WorkQueueTriggerDTO> holdAndTransferList = new ArrayList<WorkQueueTriggerDTO>();
					ArrayList<WorkQueueTriggerDTO> otherTriggerList = new ArrayList<WorkQueueTriggerDTO>();
					
					for (WorkQueueTriggerDTO workQueueTrigger : triggers) {
						if (WorkQueueTriggerService.HNDL_METHOD_HOLD_N_TRANSFER.equals(workQueueTrigger.getHandleMethod())) {
							holdAndTransferList.add(workQueueTrigger);
							continue;
						}
						otherTriggerList.add(workQueueTrigger);
					}

					if (!holdAndTransferList.isEmpty()) {
						SpringApplicationContext.getBean(WorkQueueTriggerService.class).triggerHoldAndTransfer(holdAndTransferList.toArray(new WorkQueueTriggerDTO[0]), pCreateBy);
					}
					
					if (!otherTriggerList.isEmpty()) {
						for (WorkQueueTriggerDTO workQueueTrigger : otherTriggerList) {
							SpringApplicationContext.getBean(WorkQueueTriggerService.class).handleTrigger(workQueueTrigger, pCreateBy);
						}
					}
					
				}
			}
		}
		
		String maxWqWpAssgnId = getMaxWqWpAssgnId(wqDao.getWqId(), batchId);
		
		pWorkQueue.setWqId(wqDao.getWqId());
		dispatchWorkQueue(wqDao.getWqId(), batchId, pWorkQueue.getUrl(), pCreateBy, pWorkQueue);				
		pWorkQueue.setCreatedBatchId(batchId);
		logger.info("Create WQ - WQ_ID:" + wqId + " - setCreatedWpWqAssignIds() - START");
		pWorkQueue.setCreatedWpWqAssignIds(OracleSelectHelper.getSqlFirstColumnStrings(
												((DaoBaseImpl) wqDao).getJdbcTemplate().getDataSource(),
												"SELECT DISTINCT DISPATCH_WQ_WP_ASSGN_ID FROM q_wq_wq_nature_assgn  WHERE wq_id = TO_NUMBER(?) and wq_batch_id = TO_NUMBER(?) and DISPATCH_WQ_WP_ASSGN_ID > TO_NUMBER(?)",
												new Object[] {wqDao.getWqId(), batchId, maxWqWpAssgnId}));
		logger.info("Create WQ - WQ_ID:" + wqId + " - FINISH");
		return pWorkQueue;
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void insertWqNature(String pWqId, String pWqBatchId, String pCreateBy, WorkQueueNatureDTO[] pWorkQueueNatures) throws Exception {
		QWqWqNatureAssgnDAO wqWqNatureAssgnDAO = null;
		QWqRemarksDAO remarkDAO = null;
		for (WorkQueueNatureDTO wqNature : pWorkQueueNatures) {
			wqWqNatureAssgnDAO = SpringApplicationContext.getBean(QWqWqNatureAssgnDAO.class);
			DTO2DAO(wqNature, wqWqNatureAssgnDAO);
			wqWqNatureAssgnDAO.setWqId(pWqId);
			wqWqNatureAssgnDAO.setWqBatchId(pWqBatchId);
			wqWqNatureAssgnDAO.setCreateBy(pCreateBy);
			wqWqNatureAssgnDAO.setLastUpdBy(pCreateBy);
			wqWqNatureAssgnDAO.doInsert();
			if (wqNature.getRemarks() == null) {
				continue;
			}
			for (RemarkDTO remark : wqNature.getRemarks()) {
				remarkDAO = SpringApplicationContext.getBean(QWqRemarksDAO.class);
				remarkDAO.setWqId(pWqId);
				remarkDAO.setWqBatchId(pWqBatchId);
				remarkDAO.setWqNatureId(wqNature.getWorkQueueNatureId());
				remarkDAO.setRemarks(remark.getRemarkContent());
				remarkDAO.setCreateBy(pCreateBy);
				remarkDAO.setLastUpdBy(pCreateBy);
				remarkDAO.doInsert();
			}
		}
	}
	
	private String getMaxWqWpAssgnId(String pWqId, String pWqBatchId) throws Exception {
		return OracleSelectHelper.getSqlFirstRowColumnString(
					"WorkQueueDS",
					"select NVL(max(WQ_WP_ASSGN_ID), 0) FROM q_wq_wp_search_id_v  WHERE wq_id = TO_NUMBER(?) and wq_batch_id = TO_NUMBER(?) ",
					new Object[] {pWqId, pWqBatchId});
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void dispatchWorkQueue(String pWqId, String pWqBatchId, String pUrl, String pUserId) throws Exception{
		this.dispatchWorkQueue(pWqId, pWqBatchId, pUrl, pUserId, null);
	}
	
	@Transactional(rollbackFor={Exception.class})
	public void dispatchWorkQueue(String pWqId, String pWqBatchId, String pUrl, String pUserId, WorkQueueDTO pWorkQueue) throws Exception {
		
		String maxWqWpAssgnId = getMaxWqWpAssgnId(pWqId, pWqBatchId);
		OracleSpHelper spHelper = getOracleSpHelper();
		spHelper.setSpName(SP_DISPATCH_WQ);
		ArrayList<Object> inputParamList = new ArrayList<Object>();
		inputParamList.add(pWqId);
		inputParamList.add(pWqBatchId);
		inputParamList.add(pUrl);
		spHelper.setInputParamList(inputParamList);
		logger.info("WQ_ID:" + pWqId + "/BATCH_ID:" + pWqBatchId + " - execSp()");
		if (!spHelper.execSp()) {
			logger.error("WQ_ID:" + pWqId + "/BATCH_ID:" + pWqBatchId + spHelper.getErrMsg() + "\nspReturnCode: " + spHelper.getSpReturnCode() + " / spErrorCode:" + spHelper.getSpErrorCode() + " / spErrorMsg" + spHelper.getSpErrMsg());
			throw new Exception(spHelper.getErrMsg() + "\nspReturnCode: " + spHelper.getSpReturnCode() + " / spErrorCode:" + spHelper.getSpErrorCode() + " / spErrorMsg" + spHelper.getSpErrMsg());
		}
		
		if (pWorkQueue != null && ArrayUtils.isNotEmpty(pWorkQueue.getDocuments())) {
			logger.info("WQ_ID:" + pWqId + "/BATCH_ID:" + pWqBatchId + " - attachWorkQueueDocument()");
			attachWorkQueueDocument(pWorkQueue, pWorkQueue.getDocuments(), pUserId);
		}
		deliverWorkQueueDocument(pWqId);
		
		WorkQueueTriggerDTO[] triggers = getOnReceiveWorkQueueTriggers(pWqId, pWqBatchId, maxWqWpAssgnId);
		if (ArrayUtils.isNotEmpty(triggers)) {
			for (WorkQueueTriggerDTO workQueueTrigger : triggers) {
				SpringApplicationContext.getBean(WorkQueueTriggerService.class).handleTrigger(workQueueTrigger, pUserId);
			}
		}
		logger.info("WQ_ID:" + pWqId + "/BATCH_ID:" + pWqBatchId + " - END");
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void updateWorkQueue(WorkQueueDTO pWorkQueue, String pCreateBy) throws Exception {
		this.updateWorkQueue(pWorkQueue, pCreateBy, false);
	}
	
	private void updateWorkQueue(WorkQueueDTO pWorkQueue, String pCreateBy, boolean pIsSkipInsertRemarks) throws Exception {
		QWorkQueueDAO wqDao = SpringApplicationContext.getBean(QWorkQueueDAO.class);
		DTO2DAO(pWorkQueue, wqDao);
		String wqId = null;
		
		if (StringUtils.isNotBlank(pWorkQueue.getSbActvId())) {
			wqId = OracleSelectHelper.getSqlFirstRowColumnString(
					"WorkQueueDS",
					"SELECT TO_CHAR(WQ_ID) FROM Q_WORK_QUEUE WHERE SB_ACTV_ID = ?",
					new Object[] { pWorkQueue.getSbActvId() });
		} else if (StringUtils.isNotBlank(pWorkQueue.getSbId())) {
			wqId = OracleSelectHelper.getSqlFirstRowColumnString(
					"WorkQueueDS",
					"SELECT TO_CHAR(WQ_ID) FROM Q_WORK_QUEUE WHERE SB_ID = ? AND SB_DTL_ID = TO_NUMBER(?) ",
					new Object[] { pWorkQueue.getSbId(), pWorkQueue.getSbDtlId() });
		}
		
		if (StringUtils.isEmpty(wqId)) {
			return;
		}

		wqDao.setWqId(wqId);
		wqDao.doSelect();

		wqDao.setLastUpdBy(pCreateBy);
		if (StringUtils.isNotBlank(pWorkQueue.getBomOcId())) {
			wqDao.setBomOcId(pWorkQueue.getBomOcId());
		}
		if (StringUtils.isNotBlank(pWorkQueue.getBomDtlId())) {
			wqDao.setBomDtlId(pWorkQueue.getBomDtlId());
		}
		if (StringUtils.isNotBlank(pWorkQueue.getBomDtlSeq())) {
			wqDao.setBomDtlSeq(pWorkQueue.getBomDtlSeq());
		}
		if (StringUtils.isNotBlank(pWorkQueue.getBomLegacyOrderStatus())) {
			wqDao.setBomLegacyOrdStatus(pWorkQueue.getBomLegacyOrderStatus());
		}
		if (StringUtils.isNotBlank(pWorkQueue.getBomStatus())) {
			wqDao.setBomStatus(pWorkQueue.getBomStatus());
		}
		if (StringUtils.isNotBlank(pWorkQueue.getSrd())) {
			wqDao.setSrd(pWorkQueue.getSrd());
		}
		if (StringUtils.isNotBlank(pWorkQueue.getRelatedSrvType())
				&& StringUtils.isNotBlank(pWorkQueue.getRelatedSrvNum())) {
			wqDao.setRelatedSrvType(pWorkQueue.getRelatedSrvType());
			wqDao.setRelatedSrvNum(pWorkQueue.getRelatedSrvNum());
		}
		
		wqDao.doUpdate();
		
		if (!pIsSkipInsertRemarks) {
			QWqRemarksDAO remarkDAO = null;
			if (pWorkQueue.getRemarks() != null) {
				for (RemarkDTO remark : pWorkQueue.getRemarks()) {
					remarkDAO = SpringApplicationContext.getBean(QWqRemarksDAO.class);
					remarkDAO.setWqId(wqDao.getWqId());
					remarkDAO.setRemarks(remark.getRemarkContent());
					remarkDAO.setCreateBy(pCreateBy);
					remarkDAO.setLastUpdBy(pCreateBy);
					remarkDAO.doInsert();
				}
			}
		}
	}

	
	@Override
	@Transactional(readOnly=true)
	public String [] getValidWorkQueueWorkPartyStaff(String [] pWqWpAssgnId, String pUser) throws Exception {
		if (ArrayUtils.isEmpty(pWqWpAssgnId)) {
			return null;
		}

		List <String> resultList = new ArrayList<String>();		 
				
		// Get all WP_ID from WQ_WP_ASSGN_ID
		QWqWpAssgnDAO cWqWpAssgnDAO =(QWqWpAssgnDAO)SpringApplicationContext.getBean(QWqWpAssgnDAO.class);
		cWqWpAssgnDAO.addIncludeColumn("wpId");
		cWqWpAssgnDAO.setSearchKeyIn("wqWpAssgnId",pWqWpAssgnId);
	    QWqWpAssgnDAO [] rsWqWpAssgnDAO =(QWqWpAssgnDAO [])cWqWpAssgnDAO.doSelect(null,null);
	    if (ArrayUtils.isEmpty(rsWqWpAssgnDAO)) {
	    	return null;
	    } else {
			for (int i = 0; i < rsWqWpAssgnDAO.length;i++) {
				resultList.add(rsWqWpAssgnDAO[i].getWpId());			
			}	    	
	    }	    
	    cWqWpAssgnDAO = null;
	    
		// Get all STAFF_ID from WP_ID
	    QWpStaffAssgnDAO cWqWpStaffAssgnDAO = SpringApplicationContext.getBean(QWpStaffAssgnDAO.class);
	    cWqWpStaffAssgnDAO.addIncludeColumn("staffId");
	    cWqWpStaffAssgnDAO.setSearchKeyIn("wpId",(String [])resultList.toArray(new String[resultList.size()]));	    
		QWpStaffAssgnDAO [] rsWqWpStaffAssgnDAO = (QWpStaffAssgnDAO [])cWqWpStaffAssgnDAO.doSelect(null,null);
		resultList.clear();
		for (int i = 0; i < rsWqWpStaffAssgnDAO.length;i++) {
			resultList.add(rsWqWpStaffAssgnDAO[i].getStaffId());			
		}
		
		return (String [])resultList.toArray(new String[resultList.size()]);        		
	}
	

	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public int assignWorkQueueNature(String[] pWqWpAssgnId,
			String pWpStaffId, String pRemarks, String pUser) throws Exception {
		
		String sStatusCd = null;
		String sReasonCd = null;
		
		if (ArrayUtils.isEmpty(pWqWpAssgnId)) {
			return 0;
		}

		//Get the WQ_WP_ASSGN to get the WQ_ID
		QWqWpAssgnDAO cWqWpAssgnDAO =(QWqWpAssgnDAO)SpringApplicationContext.getBean(QWqWpAssgnDAO.class);
		cWqWpAssgnDAO.addIncludeColumn("wqId");
		cWqWpAssgnDAO.addIncludeColumn("wqWpAssgnId");
		cWqWpAssgnDAO.setSearchKeyIn("wqWpAssgnId", pWqWpAssgnId);
	    QWqWpAssgnDAO [] rsWqWpAssgnDAO =(QWqWpAssgnDAO [])cWqWpAssgnDAO.doSelect(null,null);
	    if (ArrayUtils.isEmpty(rsWqWpAssgnDAO)) {
	    	return 0;
	    }    
	    	    
    	QWqWpAssgnStatusLogDAO iWqWpAssgnStatusLogDAO =(QWqWpAssgnStatusLogDAO)SpringApplicationContext.getBean(QWqWpAssgnStatusLogDAO.class);
    	WorkQueueTriggerDTO[] triggers = null;
	    for (int i = 0; i < rsWqWpAssgnDAO.length; i++) {
	    	if (StringUtils.isNotBlank(pRemarks)) {
		    	//Insert remark with a new remark seq
		    	QWqRemarksDAO iWqRemarksDAO =(QWqRemarksDAO)SpringApplicationContext.getBean(QWqRemarksDAO.class);
		    	iWqRemarksDAO.setWqId(rsWqWpAssgnDAO[i].getWqId());
		    	iWqRemarksDAO.setWqWpAssgnId(rsWqWpAssgnDAO[i].getWqWpAssgnId());
		    	iWqRemarksDAO.setRemarks(pRemarks);
		    	iWqRemarksDAO.setCreateBy(pUser);
		    	iWqRemarksDAO.setLastUpdBy(pUser);
		    	iWqRemarksDAO.doInsert();
	    	}
	    	
			//Get the last status
	    	QWqWpAssgnStatusLogDAO cWqWpAssgnStatusLogDAO =(QWqWpAssgnStatusLogDAO)SpringApplicationContext.getBean(QWqWpAssgnStatusLogDAO.class);
	    	cWqWpAssgnStatusLogDAO.setSearchKey("wqWpAssgnId",rsWqWpAssgnDAO[i].getWqWpAssgnId());
	    	cWqWpAssgnStatusLogDAO.setSearchKey("latestStatusInd","Y");
	    	QWqWpAssgnStatusLogDAO [] rWqWpAssgnStatusLogDAO =(QWqWpAssgnStatusLogDAO [])cWqWpAssgnStatusLogDAO.doSelect(null, null);
	    	String sTargetStatus = null;
	    	if (!ArrayUtils.isEmpty(rWqWpAssgnStatusLogDAO)) {
	    		sStatusCd = rWqWpAssgnStatusLogDAO[0].getStatusCd();
	    		sReasonCd = rWqWpAssgnStatusLogDAO[0].getReasonCd();
	    	}
	    	
    		sTargetStatus = OracleSelectHelper.getSqlFirstRowColumnString(
					"WorkQueueDS",
					"select description from q_dic_code_lkup where GRP_ID = 'WQ_ASSIGN_ACTION_STS' and code = ?",
					new Object[] {sStatusCd});

	    	if (StringUtils.isEmpty(sTargetStatus)) {
    			sTargetStatus = sStatusCd;
    		}
	    	
	    	//Insert assignment with last status 
	    	iWqWpAssgnStatusLogDAO.setWqWpAssgnId(rsWqWpAssgnDAO[i].getWqWpAssgnId());
	    	iWqWpAssgnStatusLogDAO.setAssignee(pWpStaffId);
	    	iWqWpAssgnStatusLogDAO.setReasonCd(sReasonCd);
	    	iWqWpAssgnStatusLogDAO.setStatusCd(sTargetStatus);
	    	iWqWpAssgnStatusLogDAO.setLatestStatusInd("Y");
	    	iWqWpAssgnStatusLogDAO.setCreateBy(pUser);
	    	iWqWpAssgnStatusLogDAO.setLastUpdBy(pUser);
	    	iWqWpAssgnStatusLogDAO.doInsert();
	    	
	    	if (!StringUtils.equals(sTargetStatus, sStatusCd)) {
				triggers = getOnStatusChangeWorkQueueTriggers(rsWqWpAssgnDAO[i].getWqWpAssgnId());
				if (ArrayUtils.isNotEmpty(triggers)) {
					for (WorkQueueTriggerDTO workQueueTrigger : triggers) {
						SpringApplicationContext.getBean(WorkQueueTriggerService.class).handleTrigger(workQueueTrigger, pUser);
					}
				}
	    		
	    	}
	    }
				
		return rsWqWpAssgnDAO.length;
	}	

	@Override
	@Transactional(rollbackFor={Exception.class})
	public int changeWorkQueueStatus(String[] pWqWpAssgnId,
			String pStatusCd, String pReasonCd, String pOcId,
			String pRemarks, String pUser) throws Exception {
		
		String sAssignee = null;
		
		if (ArrayUtils.isEmpty(pWqWpAssgnId) || pStatusCd == null) {
			return 0;
		}

		//Get the WQ_WP_ASSGN to get the WQ_ID
		QWqWpAssgnDAO cWqWpAssgnDAO =(QWqWpAssgnDAO)SpringApplicationContext.getBean(QWqWpAssgnDAO.class);
		cWqWpAssgnDAO.addIncludeColumn("wqId");
		cWqWpAssgnDAO.addIncludeColumn("wqWpAssgnId");
		cWqWpAssgnDAO.setSearchKeyIn("wqWpAssgnId",pWqWpAssgnId);
	    QWqWpAssgnDAO [] rsWqWpAssgnDAO =(QWqWpAssgnDAO [])cWqWpAssgnDAO.doSelect(null, null);
	    if (ArrayUtils.isEmpty(rsWqWpAssgnDAO)) {
	    	return 0;
	    }  
	    
	    if (pOcId != null) {
	    	QWorkQueueDAO cWorkQueueDAO = (QWorkQueueDAO)SpringApplicationContext.getBean(QWorkQueueDAO.class);
	    	cWorkQueueDAO.setSearchKey("wqId",rsWqWpAssgnDAO[0].getWqId());
	    	QWorkQueueDAO [] rWorkQueueDAO =(QWorkQueueDAO [])cWorkQueueDAO.doSelect(null,null);
	    	if (ArrayUtils.isEmpty(rWorkQueueDAO)) {
	    		throw new Exception("Missing WORK_QUEUE!");
	    	} else if (StringUtils.isBlank(rWorkQueueDAO[0].getBomOcId())) {
		    	rWorkQueueDAO[0].setBomOcId(pOcId);
		    	rWorkQueueDAO[0].setLastUpdBy(pUser);
		    	ArrayList<String> conditionList = new ArrayList<String>();
		    	conditionList.add("wqId");
		    	rWorkQueueDAO[0].doUpdate(conditionList);	    		
	    	}
	    }
	    
	    WorkQueueTriggerDTO[] triggers = null;
	    QWqRemarksDAO iWqRemarksDAO = null;
    	QWqWpAssgnStatusLogDAO iWqWpAssgnStatusLogDAO =(QWqWpAssgnStatusLogDAO)SpringApplicationContext.getBean(QWqWpAssgnStatusLogDAO.class);
	    for (int i = 0; i < rsWqWpAssgnDAO.length; i++) {
	    	//Insert remark with a new remark seq
	    	if (StringUtils.isNotBlank(pRemarks)) {
		    	iWqRemarksDAO =(QWqRemarksDAO)SpringApplicationContext.getBean(QWqRemarksDAO.class);
		    	iWqRemarksDAO.setWqId(rsWqWpAssgnDAO[i].getWqId());
		    	iWqRemarksDAO.setWqWpAssgnId(rsWqWpAssgnDAO[i].getWqWpAssgnId());
		    	iWqRemarksDAO.setRemarks(pRemarks);
		    	iWqRemarksDAO.setCreateBy(pUser);
		    	iWqRemarksDAO.setLastUpdBy(pUser);
		    	iWqRemarksDAO.doInsert();
	    	}
	    	
			//Get the last status
	    	QWqWpAssgnStatusLogDAO cWqWpAssgnStatusLogDAO =(QWqWpAssgnStatusLogDAO)SpringApplicationContext.getBean(QWqWpAssgnStatusLogDAO.class);
	    	cWqWpAssgnStatusLogDAO.setSearchKey("wqWpAssgnId", rsWqWpAssgnDAO[i].getWqWpAssgnId());
	    	cWqWpAssgnStatusLogDAO.setSearchKey("latestStatusInd", "Y");
	    	QWqWpAssgnStatusLogDAO [] rWqWpAssgnStatusLogDAO =(QWqWpAssgnStatusLogDAO [])cWqWpAssgnStatusLogDAO.doSelect(null,null);	    	
	    	if (!ArrayUtils.isEmpty(rWqWpAssgnStatusLogDAO)) {
	    		sAssignee = rWqWpAssgnStatusLogDAO[0].getAssignee();
	    	} else {
	    		throw new Exception("Missing latest status!");
	    	}
	    	
	    	//Insert assignment with last status 
	    	iWqWpAssgnStatusLogDAO.setWqWpAssgnId(rsWqWpAssgnDAO[i].getWqWpAssgnId());
	    	iWqWpAssgnStatusLogDAO.setAssignee(sAssignee);
	    	iWqWpAssgnStatusLogDAO.setReasonCd(pReasonCd);
	    	iWqWpAssgnStatusLogDAO.setStatusCd(pStatusCd);
	    	iWqWpAssgnStatusLogDAO.setLatestStatusInd("Y");
	    	iWqWpAssgnStatusLogDAO.setCreateBy(pUser);
	    	iWqWpAssgnStatusLogDAO.setLastUpdBy(pUser);
	    	iWqWpAssgnStatusLogDAO.doInsert();
	    	
			triggers = getOnStatusChangeWorkQueueTriggers(rsWqWpAssgnDAO[i].getWqWpAssgnId());
			if (ArrayUtils.isNotEmpty(triggers)) {
				for (WorkQueueTriggerDTO workQueueTrigger : triggers) {
					SpringApplicationContext.getBean(WorkQueueTriggerService.class).handleTrigger(workQueueTrigger, pUser);
				}
			}
	    }

		return rsWqWpAssgnDAO.length;
	}	
	
	@Override
	@Transactional(readOnly=true)
	public RemarkDTO[] getWorkQueRemarksByWqWpAssgnId(String pWqWpAssgnId, String pUser) throws Exception {
		return getWorkQueRemarksByWqWpAssgnId(pWqWpAssgnId, null, pUser);
	}
	
	@Override
	@Transactional(readOnly=true)
	public RemarkDTO[] getWorkQueRemarksByWqWpAssgnId(String pWqWpAssgnId,
			String[] pExtractCondition, String pUser) throws Exception {
		return this.getWorkQueRemarksByWqWpAssgnId(pWqWpAssgnId, pExtractCondition, pUser, true);
	}
	
	public RemarkDTO[] getWorkQueRemarksByWqWpAssgnId(String pWqWpAssgnId,
			String[] pExtractCondition, String pUser, boolean pIsDesendingOrder) throws Exception {
		if (pWqWpAssgnId == null) {
			return null;
		}

		//Use the WQ_WP_ASSGN to get the WQ_ID
		QWqWpAssgnDAO wqWpAssgnDAO =(QWqWpAssgnDAO)SpringApplicationContext.getBean(QWqWpAssgnDAO.class);
		wqWpAssgnDAO.setWqWpAssgnId(pWqWpAssgnId);
		wqWpAssgnDAO.doSelect();
	    if (StringUtils.isBlank(wqWpAssgnDAO.getOracleRowID())) {
	    	return null;
	    }  		

		QWqRemarksDAO cWqRemarksDAO = (QWqRemarksDAO) SpringApplicationContext
				.getBean(QWqRemarksDAO.class);
		cWqRemarksDAO.setSearchKey("wqId", wqWpAssgnDAO.getWqId());
		
		boolean isSearchByWqBatchId = false;
		if (ArrayUtils.isNotEmpty(pExtractCondition)) {
			for (String field : pExtractCondition) {
				if ("wqBatchId".equals(field)) {
					isSearchByWqBatchId = true;
					continue;
				}
				cWqRemarksDAO.setSearchKey(field, BeanUtils.getProperty(wqWpAssgnDAO, field));
			}
		}
		
		QWqRemarksDAO[] rWqRemarksDAO = (QWqRemarksDAO[]) cWqRemarksDAO.doSelect(null, null, null, "TO_CHAR(CREATE_DATE, 'YYYYMMDDHH24MISS') DESC, NVL(WQ_NATURE_ID, 999999) ASC, REMARK_SEQ " + (pIsDesendingOrder ? "DESC" : "ASC"));
		if (ArrayUtils.isEmpty(rWqRemarksDAO)) {
			return null;
		}

		String[] wpWpAssignIds = null;
		TreeMap<String, String> wpWpAssignIdMap = new TreeMap<String, String>();
		if (isSearchByWqBatchId) {
			wpWpAssignIds = OracleSelectHelper.getSqlFirstColumnStrings("WorkQueueDS", 
					"SELECT DISTINCT WQ_WP_ASSGN_ID from q_wq_wp_search_id_v where wq_id = TO_NUMBER(?) and wq_batch_id = TO_NUMBER(?) ", 
					new Object[] {wqWpAssgnDAO.getWqId(), wqWpAssgnDAO.getWqBatchId()});
		} else {
			wpWpAssignIds = new String[] {pWqWpAssgnId};
		}
		
		for (String wpWpAssignId : wpWpAssignIds) {
			wpWpAssignIdMap.put(wpWpAssignId, OracleSelectHelper.getSqlFirstRowColumnString("WorkQueueDS", "SELECT WP_DESC from q_wq_wp_search_v where WQ_WP_ASSGN_ID = TO_NUMBER(?) ", new Object[] {wpWpAssignId}));
		}
		
		RemarkDTO remarkDTO = null;
		ArrayList<RemarkDTO> remarkDTOList = new ArrayList<RemarkDTO>();
		for (int i = 0; i < rWqRemarksDAO.length; i++) {
			if ((isSearchByWqBatchId && StringUtils.isNotBlank(rWqRemarksDAO[i].getWqBatchId()) && !(wqWpAssgnDAO.getWqBatchId().equals(rWqRemarksDAO[i].getWqBatchId())))
					|| (StringUtils.isNotBlank(rWqRemarksDAO[i].getWqWpAssgnId()) && !wpWpAssignIdMap.containsKey(rWqRemarksDAO[i].getWqWpAssgnId()))) {
				continue;
			}
			remarkDTO = new RemarkDTO();
			DAO2DTO(rWqRemarksDAO[i],remarkDTO);
			if (StringUtils.isNotBlank(rWqRemarksDAO[i].getWqWpAssgnId())) {
				remarkDTO.setCreateBy(wpWpAssignIdMap.get(rWqRemarksDAO[i].getWqWpAssgnId()) + " (" + remarkDTO.getCreateBy() + ")");
			}
			
			remarkDTOList.add(remarkDTO);
		}
    	return (RemarkDTO[]) remarkDTOList.toArray(new RemarkDTO[remarkDTOList.size()]);
	}

	@Override
	@Transactional(readOnly=true)
	public SbWqInquiryResultFormDTO[] getWorkQueueViewBySbId(String pSbId, String pUser) throws Exception {
		return getWorkQueueViewBySbId(pSbId, null, pUser);
	}

	
	@Override
	@Transactional(readOnly=true)
	public SbWqInquiryResultFormDTO[] getWorkQueueViewBySbId(String pSbId,
			String[] pExtractCondition, String pUser) throws Exception {
		if (pSbId == null) {
			return null;
		}
		
		WorkingPartyDTO[] wpDTOs = this.getWorkingPartyByStaffId(pUser, "EDITABLE");
		TreeMap<String, Integer> wpMap = new TreeMap<String, Integer>();
		if (ArrayUtils.isNotEmpty(wpDTOs)) {
			for (WorkingPartyDTO wpDTO : wpDTOs) {
				wpMap.put(wpDTO.getWpId(), this.getRoleLevel(wpDTO.getEditableLevel()));
			}
		}
		
		QWorkQueueDAO cQWorkQueueDAO = SpringApplicationContext.getBean(QWorkQueueDAO.class);
		cQWorkQueueDAO.setSearchKey("sbId", pSbId);
		QWorkQueueDAO[] qWorkQueueDAOs = (QWorkQueueDAO[]) cQWorkQueueDAO.doSelect(null, null, null, null);
		if (ArrayUtils.isEmpty(qWorkQueueDAOs)) {
			return null;
		}

		WorkQueueInquiryCriteriaDTO wqInqCriteria = null;
		wqInqCriteria = new WorkQueueInquiryCriteriaDTO();
		wqInqCriteria.setSbIds(new String[] {pSbId});
		Criteria criteria = new Criteria();
		criteria.orderBy("RECEIVE_DATE", Criteria.Order.DESC);
		SearchResult<WqInquiryResultFormDTO> searchResult = SpringApplicationContext.getBean(WorkQueueService.class).searchWorkQueue(wqInqCriteria, criteria, pUser);
		TreeMap<String, WqInquiryResultFormDTO> wqWpAssignIdMap = new TreeMap<String, WqInquiryResultFormDTO>();
		WqInquiryResultFormDTO wqDTO = null;
		for (WqInquiryResultFormDTO dto : searchResult.getResult()) {
			wqWpAssignIdMap.put(dto.getWqWpAssgnId(), dto);
		}
		
		QWqRemarksDAO cWqRemarksDAO = null;
		QWqRemarksDAO[] rWqRemarksDAO = null;
		SbWqInquiryResultFormDTO sbWqInquiryResultFormDTO = null;
		RemarkDTO remarkDTO = null;
		
		WorkQueueCacheService workQueueCacheService = (WorkQueueCacheService) SpringApplicationContext.getBean("WQNatureCache");
		ArrayList<SbWqInquiryResultFormDTO> rtnList = new ArrayList<SbWqInquiryResultFormDTO>();
		TreeMap<String, Object[]> wqBatchIdMap = new TreeMap<String, Object[]>();
		String wqBatchId = null;
		StringBuffer sbRemarks = null;
		DateFormat displayDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		DateFormat dbDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		Object[] tmp = null;
		String lastDateString = null;
		String dateString = null;
		
		for (QWorkQueueDAO qWorkQueueDAO : qWorkQueueDAOs) {
			wqBatchIdMap.clear();
			for (WqInquiryResultFormDTO dto : searchResult.getResult()) {
				if (!dto.getWqId().equals(qWorkQueueDAO.getWqId())) {
					continue;
				}
				
				sbWqInquiryResultFormDTO = new SbWqInquiryResultFormDTO();
				DAO2DTO(qWorkQueueDAO, sbWqInquiryResultFormDTO);
				BeanUtils.copyProperties(sbWqInquiryResultFormDTO, wqWpAssignIdMap.get(dto.getWqWpAssgnId()));
				sbWqInquiryResultFormDTO.setAllowChangeStatus(
						(StringUtils.isNotBlank(sbWqInquiryResultFormDTO.getNextStatus())
								&& wpMap.containsKey(sbWqInquiryResultFormDTO.getWpId())
								&& (wpMap.get(sbWqInquiryResultFormDTO.getWpId()) < 4
										|| pUser.equals(sbWqInquiryResultFormDTO.getAssignee()))));

				tmp = wqBatchIdMap.get(dto.getWqBatchId());
				if (tmp == null) {
					wqBatchIdMap.put(dto.getWqBatchId(), new Object[] {sbWqInquiryResultFormDTO, null});
					continue;
				}
				
				wqDTO = (WqInquiryResultFormDTO) tmp[0];
				
				if (StringUtils.isBlank(dto.getNextStatus()) && StringUtils.isNotBlank(wqDTO.getNextStatus())) {
					// DO NOTHING
				} else if (StringUtils.isBlank(wqDTO.getNextStatus()) && StringUtils.isNotBlank(dto.getNextStatus())) {
					wqBatchIdMap.put(dto.getWqBatchId(), new Object[] {sbWqInquiryResultFormDTO, null});
				} else if (wqDTO.getWqStatusDate().compareTo(dto.getWqStatusDate()) < 0) {
					wqBatchIdMap.put(dto.getWqBatchId(), new Object[] {sbWqInquiryResultFormDTO, null});
				}
			}
			
			cWqRemarksDAO = (QWqRemarksDAO) SpringApplicationContext.getBean(QWqRemarksDAO.class);
			cWqRemarksDAO.setSearchKey("wqId", qWorkQueueDAO.getWqId());
			
			if (ArrayUtils.isNotEmpty(pExtractCondition)) {
				for (String field : pExtractCondition) {
					cWqRemarksDAO.setSearchKey(field, BeanUtils.getProperty(qWorkQueueDAO, field));
				}
			}
			
			rWqRemarksDAO = (QWqRemarksDAO[]) cWqRemarksDAO.doSelect(null, null, null, "TO_CHAR(CREATE_DATE, 'YYYYMMDDHH24MISS') DESC, NVL(WQ_NATURE_ID, 999999) ASC, REMARK_SEQ DESC");
			if (!ArrayUtils.isEmpty(rWqRemarksDAO)) {
				for (QWqRemarksDAO qWqRemarksDAO : rWqRemarksDAO) {
					sbRemarks = new StringBuffer();
					wqBatchId = qWqRemarksDAO.getWqBatchId();
					if (StringUtils.isBlank(wqBatchId) && StringUtils.isNotBlank(qWqRemarksDAO.getWqWpAssgnId())) {
						wqBatchId = wqWpAssignIdMap.get(qWqRemarksDAO.getWqWpAssgnId()).getWqBatchId();
					}
					if (StringUtils.isBlank(wqBatchId)) {
						wqBatchId = "-1";
					}
					
					tmp = wqBatchIdMap.get(wqBatchId);
					if (tmp == null) {
						sbWqInquiryResultFormDTO = new SbWqInquiryResultFormDTO();
						DAO2DTO(qWorkQueueDAO, sbWqInquiryResultFormDTO);
						tmp = new Object[] {sbWqInquiryResultFormDTO, null};
						wqBatchIdMap.put(wqBatchId, tmp);
					} else {
						sbWqInquiryResultFormDTO = (SbWqInquiryResultFormDTO) tmp[0];
						lastDateString = (String) tmp[1];
					}
					
					remarkDTO = new RemarkDTO();
					DAO2DTO(qWqRemarksDAO, remarkDTO);
					if (ArrayUtils.isNotEmpty(sbWqInquiryResultFormDTO.getRemarks())) {
						remarkDTO = sbWqInquiryResultFormDTO.getRemarks()[0];
						sbRemarks.append(remarkDTO.getRemarkContent());
					} else {
						sbWqInquiryResultFormDTO.addRemark(remarkDTO);	
					}
	
					dateString = displayDateFormat.format(dbDateFormat.parse(qWqRemarksDAO.getCreateDate()))
							   + " BY " + qWqRemarksDAO.getCreateBy();

					if (sbRemarks.length() > 0) {
						sbRemarks.append("\n\n");
					}
					
					if (!StringUtils.equals(lastDateString, dateString)) {
						if (sbRemarks.length() > 0) {
							sbRemarks.append("--------------------\n");
						}
						sbRemarks.append(dateString);
						sbRemarks.append("\n");
					}

					if (StringUtils.isNotBlank(qWqRemarksDAO.getWqNatureId())) {
						sbRemarks.append((String)workQueueCacheService.get(qWqRemarksDAO.getWqNatureId()));
						sbRemarks.append("\n");
					}
					
					sbRemarks.append(qWqRemarksDAO.getRemarks());
					remarkDTO.setRemarkContent(sbRemarks.toString());
					
					if (remarkDTO.getRemarkDate().compareTo(qWqRemarksDAO.getCreateDate()) < 0) {
						remarkDTO.setRemarkDate(qWqRemarksDAO.getCreateDate());
					}
					tmp[1] = dateString;
				}
			}

			for (Object[] values : wqBatchIdMap.values()) {
				rtnList.add((SbWqInquiryResultFormDTO) values[0]);	
			}
		}
		return rtnList.toArray(new SbWqInquiryResultFormDTO[0]);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public boolean saveRemark(String pWqWpAssgnId, String pRemarks, String pUser) throws Exception {
		
		//Use the WQ_WP_ASSGN_ID to get the WQ_ID
		QWqWpAssgnDAO cWqWpAssgnDAO =(QWqWpAssgnDAO)SpringApplicationContext.getBean(QWqWpAssgnDAO.class);
		cWqWpAssgnDAO.addIncludeColumn("wqId");
		cWqWpAssgnDAO.addIncludeColumn("wqWpAssgnId");
		cWqWpAssgnDAO.setSearchKey("wqWpAssgnId",pWqWpAssgnId);
	    QWqWpAssgnDAO [] rsWqWpAssgnDAO =(QWqWpAssgnDAO [])cWqWpAssgnDAO.doSelect(null,null);
	    if (ArrayUtils.isEmpty(rsWqWpAssgnDAO)) {
	    	return false;
	    }  		
		
    	QWqRemarksDAO iWqRemarksDAO =(QWqRemarksDAO)SpringApplicationContext.getBean(QWqRemarksDAO.class);
    	iWqRemarksDAO.setWqId(rsWqWpAssgnDAO[0].getWqId());
    	iWqRemarksDAO.setWqWpAssgnId(rsWqWpAssgnDAO[0].getWqWpAssgnId());
    	iWqRemarksDAO.setRemarks(pRemarks);
    	iWqRemarksDAO.setCreateBy(pUser);
    	iWqRemarksDAO.setLastUpdBy(pUser);
    	iWqRemarksDAO.doInsert();
    	
    	return true;
	}	
	
	@Override	
	@Transactional(readOnly=true)
	public WqStatusLogFormDTO [] getWorkQueueStatusLog(String pWqWpAssgnId, String pUser) throws Exception {
    	QWqWpAssgnStatusLogDAO cWqWpAssgnStatusLogDAO =(QWqWpAssgnStatusLogDAO)SpringApplicationContext.getBean(QWqWpAssgnStatusLogDAO.class);
    	cWqWpAssgnStatusLogDAO.setSearchKey("wqWpAssgnId",pWqWpAssgnId);
    	QWqWpAssgnStatusLogDAO [] rWqWpAssgnStatusLogDAO =(QWqWpAssgnStatusLogDAO [])cWqWpAssgnStatusLogDAO.doSelect(null, null, null, " decode(LATEST_STATUS_IND,'Y','0','1'), CREATE_DATE DESC");	    	
    	if (ArrayUtils.isEmpty(rWqWpAssgnStatusLogDAO)) {
    		return null;
    	}; 
    	
    	WqStatusLogFormDTO wqStatusLogFormDTO = null;
		ArrayList<WqStatusLogFormDTO> wqStatusLogFormDTOList = new ArrayList<WqStatusLogFormDTO>();    	
    	for (int i = 0;i < rWqWpAssgnStatusLogDAO.length; i++) {
    		wqStatusLogFormDTO = new WqStatusLogFormDTO();
    		DAO2DTO(rWqWpAssgnStatusLogDAO[i],wqStatusLogFormDTO);
			wqStatusLogFormDTOList.add(wqStatusLogFormDTO);    		
    	}
    	
    	return (WqStatusLogFormDTO[]) wqStatusLogFormDTOList
				.toArray(new WqStatusLogFormDTO[wqStatusLogFormDTOList.size()]);    	

	}	

	private static final String getWorkQueueRequiredDocumentSQL = 
			  "select TO_CHAR(DOCUMENT_TYPE_ID) \"documentTypeId\", RPT_NAME \"reportName\" "
			+ "  from q_dic_document "
			+ " where document_type_id in ( select DISTINCT DOCUMENT_TYPE_ID from TABLE(Q_WORK_QUEUE_PKG.get_required_document(?)) "
			+ "                              minus "
			+ "                             select DOCUMENT_TYPE_ID from q_wq_document "
			+ "                              where wq_id = TO_NUMBER(?) "
			+ "                              )";
	
	@Override
	@Transactional(readOnly=true)
	public WorkQueueDocumentDTO[] getWorkQueueRequiredDocuments(
			WorkQueueDTO pWorkQueue, String pUser) throws Exception {
		return OracleSelectHelper.getSqlResultObjects(
				"WorkQueueDS", getWorkQueueRequiredDocumentSQL, 
				new Object[] {pWorkQueue.getWqId(), pWorkQueue.getWqId()},WorkQueueDocumentDTO.class);
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void attachWorkQueueDocument(WorkQueueDTO pWorkQueue,
			WorkQueueDocumentDTO[] pWorkQueueDocuments,
			String pUser)
			throws Exception {
		
		QWorkQueueDAO wqDAO = SpringApplicationContext.getBean(QWorkQueueDAO.class);
		wqDAO.setWqId(pWorkQueue.getWqId());
		wqDAO.doSelect();
		
		QWqDocumentDAO dao = null;
		for (WorkQueueDocumentDTO workQueueDocumentDTO : pWorkQueueDocuments) {
			if (StringUtils.isEmpty(workQueueDocumentDTO.getAttachmentFullPath())) {
				continue;
			}
			
			dao = SpringApplicationContext.getBean(QWqDocumentDAO.class);
			if (StringUtils.isBlank(workQueueDocumentDTO.getDocumentTypeId())) {
				workQueueDocumentDTO.setDocumentTypeId(OracleSelectHelper.getSqlFirstRowColumnString("WorkQueueDS", 
						"select DOCUMENT_TYPE_ID from q_dic_document WHERE RPT_NAME = ? ",
						new Object[] {workQueueDocumentDTO.getReportName()}));
			}
			if (StringUtils.isBlank(workQueueDocumentDTO.getDocumentId())) {
				dao.setSearchKey("wqId", pWorkQueue.getWqId());
				dao.setSearchKey("documentTypeId", workQueueDocumentDTO.getDocumentTypeId());
			} else {
				dao.setSearchKey("wqDocumentId", workQueueDocumentDTO.getDocumentId());
			}
			try {
				dao.doSelect();
			} catch (Exception ignore) {}

			dao.setWqId(pWorkQueue.getWqId());
			if (StringUtils.isBlank(dao.getOracleRowID())) {
				DTO2DAO(workQueueDocumentDTO, dao);
				dao.setCreateBy(pUser);
				dao.setLastUpdBy(pUser);
				dao.doInsert();
			} else {
				workQueueDocumentDTO.setDocumentId(dao.getWqDocumentId());
				DTO2DAO(workQueueDocumentDTO, dao);
				dao.setLastUpdBy(pUser);
				dao.doUpdate();
			}
		}

		WorkQueueTriggerDTO[] triggers = getOnAttachWqDocumentTriggers(pWorkQueue.getWqId()); // MUST GET TRIGGER BEFORE EXECUTE SP
		this.deliverWorkQueueDocument(pWorkQueue.getWqId());
		
		if (ArrayUtils.isNotEmpty(triggers)) {
			for (WorkQueueTriggerDTO workQueueTrigger : triggers) {
				SpringApplicationContext.getBean(WorkQueueTriggerService.class).handleTrigger(workQueueTrigger, pUser);
			}
		}
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public void deliverWorkQueueDocument(String pWqId) throws Exception {
		OracleSpHelper spHelper = getOracleSpHelper();
		spHelper.setSpName(SP_DELIVER_DOC);
		ArrayList<Object> inputParamList = new ArrayList<Object>();
		inputParamList.add(pWqId);
		spHelper.setInputParamList(inputParamList);
		spHelper.execSp();
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueDocumentDTO downloadWorkQueueDocument(String pWqWpAssgnId,
			String pWqWpAssgnDocId, String pUser) throws Exception {
		QWqWpDocumentDAO wqWpDocDao = SpringApplicationContext.getBean(QWqWpDocumentDAO.class);
		wqWpDocDao.setWqWpAssgnId(pWqWpAssgnId);
		wqWpDocDao.setWqWpAssgnDocId(pWqWpAssgnDocId);
		wqWpDocDao.doSelect();
		wqWpDocDao.getDownloadDateORACLE().setUpdateUsing(OraDate.UPD_BY_SYS_DATE);
		wqWpDocDao.setLastUpdBy(pUser);
		wqWpDocDao.doUpdate();
		
		QWqDocumentDAO wqDocDao = SpringApplicationContext.getBean(QWqDocumentDAO.class);
		wqDocDao.setWqDocumentId(wqWpDocDao.getWqDocumentId());
		wqDocDao.doSelect();
		
		WorkQueueDocumentDTO dto = new WorkQueueDocumentDTO();
		DAO2DTO(wqDocDao, dto);
		return dto;
	}

	@Override
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueDocumentDTO[] downloadWorkQueueDocuments(String pWqWpAssgnId, String pUser) throws Exception {
		QWqWpDocumentDAO criteria = SpringApplicationContext.getBean(QWqWpDocumentDAO.class);
		criteria.setSearchKey("wqWpAssgnId", pWqWpAssgnId);		
		QWqWpDocumentDAO[] wqWpDocDaos = (QWqWpDocumentDAO[]) criteria.doSelect(null, null);
		if (ArrayUtils.isEmpty(wqWpDocDaos)) {
			return null;
		}
		
		ArrayList<WorkQueueDocumentDTO> rtnList = new ArrayList<WorkQueueDocumentDTO>();
		WorkQueueDocumentDTO dto = null;
		for (QWqWpDocumentDAO wqWpDocDao : wqWpDocDaos) {
			wqWpDocDao.getDownloadDateORACLE().setUpdateUsing(OraDate.UPD_BY_SYS_DATE);
			wqWpDocDao.setLastUpdBy(pUser);
			wqWpDocDao.doUpdate();
			
			QWqDocumentDAO wqDocDao = SpringApplicationContext.getBean(QWqDocumentDAO.class);
			wqDocDao.setWqDocumentId(wqWpDocDao.getWqDocumentId());
			wqDocDao.doSelect();
						
			dto = new WorkQueueDocumentDTO();
			DAO2DTO(wqDocDao, dto);
			rtnList.add(dto);
		}
		return rtnList.toArray(new WorkQueueDocumentDTO[0]);
	}

	@Override
	@Transactional(readOnly=true)
	public WorkingPartyDTO[] getWorkingPartyByStaffId(String pLoginId, String pAccessType) throws Exception {
		
		ArrayList<WorkingPartyDTO> rtnList = new ArrayList<WorkingPartyDTO>();
		TreeSet<String> wpIdSet = new TreeSet<String>();
		WorkingPartyDTO wpTemplate = new WorkingPartyDTO();
		wpTemplate.setAlertLevel("TEAM_CD");
		wpTemplate.setReadonlyLevel("TEAM_CD");
		wpTemplate.setEditableLevel("TEAM_CD");

		WorkingPartyDTO[] wpDTOs = OracleSelectHelper.getSqlResultObjects(
				"WorkQueueDS", 
				"       select wp.wp_id \"wpId\", wp.description \"description\", "
					+ "        'TEAM_CD' \"alertLevel\", 'TEAM_CD' \"readonlyLevel\", 'TEAM_CD' \"editableLevel\" "
					+ "   from q_working_party wp, q_wp_staff_assgn wsa "
					+ "  where wp.wp_id = wsa.wp_id"
					+ "    and wsa.staff_id = ? ",
				new Object[] { pLoginId },
				WorkingPartyDTO.class);

		if (ArrayUtils.isNotEmpty(wpDTOs)) {
			for (WorkingPartyDTO wpDTO : wpDTOs) {
				wpIdSet.add(wpDTO.getWpId());
				rtnList.add(wpDTO);
			}
		}
		
		WpRoleStructureDTO wpRole = getAccessRole(pLoginId);
		
		WorkingPartyDTO[] wpTemplates = OracleSelectHelper.getSqlResultObjects(
				"WorkQueueDS", 
				"   select readonly_level  \"readonlyLevel\", editable_level \"editableLevel\", alert_level \"alertLevel\", role_id \"roleId\" "
				+ "   from Q_SEC_ROLE"
				+ "  WHERE CHANNEL_CD = ? AND ROLE = ? ", new Object[] { wpRole.getChannelCd(), wpRole.getRole() }, WorkingPartyDTO.class);
		
		if (ArrayUtils.isNotEmpty(wpTemplates)) {
			wpTemplate = wpTemplates[0];
			int level = 999;
			
			if ("ALERT".equals(pAccessType)) {
				level = getRoleLevel(wpTemplate.getAlertLevel());
			} else if ("EDITABLE".equals(pAccessType)) {
				level = getRoleLevel(wpTemplate.getEditableLevel());
			} else if ("ACCESS".equals(pAccessType)) {
				level = Math.min(getRoleLevel(wpTemplate.getReadonlyLevel()), getRoleLevel(wpTemplate.getEditableLevel()));
			}
			
			wpDTOs = getWorkingPartyByAccessLevel(wpTemplate, wpRole, level);
			if (ArrayUtils.isNotEmpty(wpDTOs)) {
				for (WorkingPartyDTO wpDTO : wpDTOs) {
					if (wpIdSet.contains(wpDTO.getWpId())) {
						continue;
					}
					wpIdSet.add(wpDTO.getWpId());
					rtnList.add(wpDTO);
				}
			}
			
			wpDTOs = getWorkingPartyBySvcFollowupStructure(pLoginId);
			if (ArrayUtils.isNotEmpty(wpDTOs)) {
				for (WorkingPartyDTO wpDTO : wpDTOs) {
					if (wpIdSet.contains(wpDTO.getWpId())) {
						continue;
					}
					wpIdSet.add(wpDTO.getWpId());
					rtnList.add(wpDTO);
				}
			}
		}
		return rtnList.toArray(new WorkingPartyDTO[0]);
	}
	
	private WpRoleStructureDTO getAccessRole(String pLoginId) throws Exception {
		String tmpStr = OracleSelectHelper.getSqlFirstRowColumnString (
				"WorkQueueDS",
			 	"  select CHANNEL_CD || '^' || CASE WHEN CHANNEL_CD IN ('RS', 'SC') THEN bs.SALES_CHANNEL ELSE NULL END || '^' || bsa.CENTRE_CD || '^' || bsa.TEAM_CD || '^' || CATEGORY || '^' || bsa.channel_id "
				+ "  from bomweb_sales_profile bsp,"
				+ "       bomweb_sales_assignment bsa,"
				+ "       bomweb_shop bs"
				+ " where bsp.staff_id = bsa.staff_id"
				+ "   and (bsp.end_date is null or bsp.end_date >= trunc(sysdate)) "
				+ "   and (bsa.end_date is null or bsa.end_date >= trunc(sysdate)) "
				+ "   and bsa.staff_id = ?"
				+ "   and bsa.team_cd = bs.shop_cd(+)",
				new Object[] { pLoginId });
		
		WpRoleStructureDTO rtnObj = new WpRoleStructureDTO();
		
		if (StringUtils.isNotBlank(tmpStr)) {
			String[] values = tmpStr.split("\\^");
			rtnObj.setChannelCd(values[0]);
			rtnObj.setSalesChannel(values[1]);
			rtnObj.setCentreCd(values[2]);
			rtnObj.setTeamCd(values[3]);
			rtnObj.setRole(values[4]);
			rtnObj.setChannelId(values[5]);
		}
		return rtnObj;
	}
	
	private WorkingPartyDTO[] getWorkingPartyByAccessLevel(
			WorkingPartyDTO wpTemplate, WpRoleStructureDTO pWpRoleStructure, int level) throws Exception {
		
		ArrayList<String> bindingValueList = new ArrayList<String>();

		StringBuilder sbSql = new StringBuilder();
		sbSql.append("select wp_id \"wpId\", description \"description\", ");
		sbSql.append("       ? \"alertLevel\", ");
		sbSql.append("       ? \"readonlyLevel\", ");
		sbSql.append("       ? \"editableLevel\" ");
		sbSql.append("  from q_working_party ");
		sbSql.append("  WHERE NVL(ACCESS_MIN_ROLE_ID, 0) <= TO_NUMBER(?)");
		sbSql.append("    AND CHANNEL_CD IN ('*', ?) ");
		bindingValueList.add(wpTemplate.getAlertLevel());
		bindingValueList.add(wpTemplate.getReadonlyLevel());
		bindingValueList.add(wpTemplate.getEditableLevel());
		bindingValueList.add(wpTemplate.getRoleId());
		bindingValueList.add(pWpRoleStructure.getChannelCd());
		
		if (StringUtils.isNotBlank(pWpRoleStructure.getSalesChannel()) && level >= 1) {
			sbSql.append(" AND SALES_CHANNEL IN ('*', ?) ");
			bindingValueList.add(pWpRoleStructure.getSalesChannel());
		}

		if (StringUtils.isNotBlank(pWpRoleStructure.getCentreCd()) && level >= 2) {
			sbSql.append(" AND CENTRE_CD IN ('*', ?) ");
			bindingValueList.add(pWpRoleStructure.getCentreCd());
		}
		
		if (StringUtils.isNotBlank(pWpRoleStructure.getTeamCd()) && level >= 3) {
			sbSql.append(" AND TEAM_CD IN ('*', ?) ");
			bindingValueList.add(pWpRoleStructure.getTeamCd());
		}

		return OracleSelectHelper.getSqlResultObjects(
				"WorkQueueDS", 
				sbSql.toString(),
				bindingValueList.toArray(),
				WorkingPartyDTO.class);
	}

	private WorkingPartyDTO[] getWorkingPartyBySvcFollowupStructure(String pStaffId) throws Exception {
		
		StringBuilder sbSql = new StringBuilder();
		WorkingPartyDTO[] wps = null;
		ArrayList<WorkingPartyDTO> rtnList = new ArrayList<WorkingPartyDTO>();
		String[] viewNames = new String[] {"Q_SVC_FOLLOWUP_WP_V", "Q_SVC_FU_WP_CTR_CHILD_WP_V", "Q_SVC_FU_WP_DEPT_CHILD_WP_V"};
		for (String viewName : viewNames) {
			sbSql.setLength(0);
			sbSql.append("select wp_id \"wpId\", description \"description\", ");
			sbSql.append("       ALERT_LEVEL \"alertLevel\", ");
			sbSql.append("       READONLY_LEVEL \"readonlyLevel\", ");
			sbSql.append("       EDITABLE_LEVEL \"editableLevel\" ");
			sbSql.append("  from ");
			sbSql.append(viewName);
			sbSql.append("  WHERE staff_id = ? ");
			
			wps = OracleSelectHelper.getSqlResultObjects(
						"WorkQueueDS", 
						sbSql.toString(),
						new Object[] {pStaffId},
						WorkingPartyDTO.class);
			
			if (ArrayUtils.isNotEmpty(wps)) {
				rtnList.addAll(Arrays.asList(wps));
			}
		}
		return rtnList.toArray(new WorkingPartyDTO[0]);
	}
	
	private int getRoleLevel(String pFieldName) {
		int level = -1;

		if ((level == -1) && "CHANNEL_CD".equals(pFieldName)) {
			level = 0;
		}

		if ((level == -1) && "SALES_CHANNEL".equals(pFieldName)) {
			level = 1;
		}

		if ((level == -1) && "CENTRE_CD".equals(pFieldName)) {
			level = 2;
		}

		if ((level == -1) && "TEAM_CD".equals(pFieldName)) {
			level = 3;
		}

		if ((level == -1) && "ASSIGNEE".equals(pFieldName)) {
			level = 4;
		}
		
		return level;
	}

	@Override
	@Transactional(readOnly=true)
	public WqInquiryResultFormDTO getWorkQueueDetail(String pWqWpAssgnId) throws Exception {
		QWqWpSearchVDAO dao = SpringApplicationContext.getBean(QWqWpSearchVDAO.class);
		dao.setWqWpAssgnId(pWqWpAssgnId);
		dao.doSelect();
		
		// DETERMINE RECORD NOT FOUND, MUST HAVE WP_ID because NO oracleRowId for view 
		if (StringUtils.isEmpty(dao.getWpId())) {
			return null;
		}
		WqInquiryResultFormDTO rtnDto = new WqInquiryResultFormDTO();
		DAO2DTO(dao, rtnDto);
		return rtnDto;
	}
	
	private static final String getTriggerSQL = 
			"SELECT DISTINCT " +
			"       wwsiv.WQ_ID \"wqId\", " +
			"       SB_ID \"sbId\", SB_DTL_ID \"sbDtlId\", SB_ACTV_ID \"sbActvId\", SB_SHOP_CD \"sbShopCode\", TYPE_OF_SRV \"typeOfService\", SRV_ID \"serviceId\", " +
			"       SRD \"srd\", BOM_OC_ID \"bomOcId\", BOM_DTL_ID \"bomDtlId\", BOM_DTL_SEQ \"bomDtlSeq\", BOM_STATUS \"bomStatus\", " +
			"       BOM_LEGACY_ORD_STATUS \"bomLegacyOrderStatus\", WQ_WP_ASSGN_ID \"wqWpAssgnId\", WQ_SERIAL \"wqSerial\", " +
			"       wwsiv.WQ_TYPE \"wqType\", wwsiv.WQ_SUBTYPE \"wqSubType\", " +
			"       RECEIVE_DATE \"wqReceiveDate\", ASSIGNEE \"assignee\", REASON_CD \"reasonCode\", WQ_BATCH_ID \"wqBatchId\", " +
			"       STATUS_CD \"wqStatus\", " +
			"       wwsiv.WP_ID \"wpId\", wwsiv.URL \"url\", " +
			"       HANDLE_METHOD_ID \"handleMethodId\", TRIGGER_WHEN \"triggerWhen\", WQ_STATUS \"triggerWhenWqStatus\", HANDLE_METHOD \"handleMethod\" " +
			"  from q_wq_wp_search_id_v wwsiv, q_dic_wp_wq_nature_hndl_v wwnhv " +
			" where wwsiv.wq_nature_id = wwnhv.wq_nature_id ";
	
//	private static final String getTriggerSqlOrderBy = " order by wwnhv.HNDL_SEQ";
	
	private static final String getTriggerOnReceiveSqlWhere = 
	 	    "   and wwnhv.TRIGGER_WHEN = 'ON_RECEIVE' " +
			"   and wwnhv.WP_WQ_NATURE_ASSGN_ID IN (SELECT WP_WQ_NATURE_ASSGN_ID " +
            "                                         FROM TABLE(q_work_queue_pkg.get_q_dic_wp_wq_nature_assgn(TO_NUMBER(?), TO_NUMBER(?), 'Y'))) " +
	 	    "   and wwsiv.wq_id = TO_NUMBER(?) " +
	 	    "   and wwsiv.wq_batch_id = TO_NUMBER(?) " +
	 	    "   and wwsiv.WQ_WP_ASSGN_ID > TO_NUMBER(?)";
	
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueTriggerDTO[] getOnReceiveWorkQueueTriggers(String pWqId, String pBatchId, String pMaxWqWpAssgnId) throws Exception {
		StringBuilder sql = new StringBuilder(getTriggerSQL);
		sql.append(getTriggerOnReceiveSqlWhere);
//		sql.append(getTriggerSqlOrderBy);
		
		WorkQueueTriggerDTO[] workQueueTriggerDTOs = OracleSelectHelper.getSqlResultObjects (
				"WorkQueueDS", sql.toString(),
				new Object[] { pWqId, pBatchId, pWqId, pBatchId, pMaxWqWpAssgnId },
				WorkQueueTriggerDTO.class);
		
		if (ArrayUtils.isEmpty(workQueueTriggerDTOs)) {
			return null;
		}
		
		setHandlingAttributes(workQueueTriggerDTOs);
		
		return workQueueTriggerDTOs;
	}
	
	private static final String getTriggerOnStatusChangeSqlWhere = 
	 	    "   and wwnhv.TRIGGER_WHEN = 'ON_STATUS_CHANGE' " +
	 	    "   and wwnhv.WQ_STATUS = wwsiv.STATUS_CD " +
	 	    "   and wwsiv.wq_wp_assgn_id = TO_NUMBER(?) " +
			"   and wwnhv.WP_WQ_NATURE_ASSGN_ID IN (SELECT WP_WQ_NATURE_ASSGN_ID " +
            "                                         FROM TABLE(q_work_queue_pkg.get_q_dic_wp_wq_nature_assgn(wwsiv.wq_id, wwsiv.wq_batch_id, wwsiv.wq_wp_assgn_id, 'Y'))) ";

	
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueTriggerDTO[] getOnStatusChangeWorkQueueTriggers(String pWqWpAssignId) throws Exception {
		StringBuilder sql = new StringBuilder(getTriggerSQL);
		sql.append(getTriggerOnStatusChangeSqlWhere);
//		sql.append(getTriggerSqlOrderBy);
		
		WorkQueueTriggerDTO[] workQueueTriggerDTOs = OracleSelectHelper.getSqlResultObjects (
				"WorkQueueDS", sql.toString(),
				new Object[] { pWqWpAssignId },
				WorkQueueTriggerDTO.class);
		
		if (ArrayUtils.isEmpty(workQueueTriggerDTOs)) {
			return null;
		}
		
		setHandlingAttributes(workQueueTriggerDTOs);
		
		return workQueueTriggerDTOs;
	}

	private static final String getTriggerBeforeCreateSql = 
		"	SELECT DISTINCT " + 
		"       wq.WQ_ID \"wqId\", " +  
		"       wq.SB_ID \"sbId\", wq.SB_DTL_ID \"sbDtlId\", wq.SB_ACTV_ID \"sbActvId\", wq.SB_SHOP_CD \"sbShopCode\", wq.TYPE_OF_SRV \"typeOfService\", wq.SRV_ID \"serviceId\", " +  
		"       wq.SRD \"srd\", wq.BOM_OC_ID \"bomOcId\", wq.BOM_DTL_ID \"bomDtlId\", wq.BOM_DTL_SEQ \"bomDtlSeq\", wq.BOM_STATUS \"bomStatus\",  " + 
		"       wq.BOM_LEGACY_ORD_STATUS \"bomLegacyOrderStatus\", " +
		"       ? \"wqBatchId\", " +
		"       wwnhv.wq_type \"wqType\", wwnhv.wq_subtype \"wqSubType\", " +
		"       null \"wqWpAssgnId\", null \"wqSerial\",  " + 
		"       null \"wqReceiveDate\", null \"assignee\", null \"reasonCode\",  " + 
		"       HANDLE_METHOD_ID \"handleMethodId\", TRIGGER_WHEN \"triggerWhen\", WQ_STATUS \"triggerWhenWqStatus\", HANDLE_METHOD \"handleMethod\" " +  
		"  from q_work_queue wq, q_dic_wp_wq_nature_hndl_v wwnhv  " + 
		" where TRIGGER_WHEN = 'BEFORE_CREATE' " + 
		"   and wwnhv.WP_WQ_NATURE_ASSGN_ID IN (SELECT WP_WQ_NATURE_ASSGN_ID " + 
        "                                         FROM TABLE(q_work_queue_pkg.get_q_dic_wp_wq_nature_assgn(TO_NUMBER(?), TO_NUMBER(?), 'N'))) " +
		"   and wq.wq_id = TO_NUMBER(?) ";   
	
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueTriggerDTO[] getBeforeCreateWorkQueueTriggers(String pWqId, String pWqBatchId) throws Exception {

		WorkQueueTriggerDTO[] workQueueTriggerDTOs = OracleSelectHelper.getSqlResultObjects (
				"WorkQueueDS", getTriggerBeforeCreateSql,
				new Object[] { pWqBatchId, pWqId, pWqBatchId, pWqId },
				WorkQueueTriggerDTO.class);
		
		if (ArrayUtils.isEmpty(workQueueTriggerDTOs)) {
			return null;
		}
		
		setHandlingAttributes(workQueueTriggerDTOs);
		
		return workQueueTriggerDTOs;
	}

	private static final String getTriggerOnAttachWqDocumentSqlWhere = 
	 	    "   and wwnhv.TRIGGER_WHEN = 'ON_ATTACH_DOC' " +
			"   and wwnhv.WP_WQ_NATURE_ASSGN_ID IN (SELECT WP_WQ_NATURE_ASSGN_ID " +
            "                                         FROM TABLE(q_work_queue_pkg.get_q_dic_wp_wq_nature_assgn(TO_NUMBER(?), TO_NUMBER(?), 'Y'))) " +
	 	    "   and wwsiv.wq_id = TO_NUMBER(?) " +
	 	    "   and wwsiv.wq_batch_id = TO_NUMBER(?) ";
	
	private static final String getWqWithDocumentToDeliverSql = 
			"select WQ_ID \"wqId\", WQ_WP_ASSGN_ID \"wqWpAssgnId\", WQ_BATCH_ID \"wqBatchId\", " + 
			"       (SELECT 'Y' FROM DUAL WHERE EXISTS  (select wwnd.DOCUMENT_TYPE_ID " + 
			"                                             from q_wp_wq_nature_doc wwnd, " +
			"                                                  q_wq_document wd  " +
			"                                            where wwnd.wp_wq_nature_assgn_id IN (select wp_wq_nature_assgn_id from TABLE(Q_WORK_QUEUE_PKG.get_q_dic_wp_wq_nature_assgn (qwwa.WQ_WP_ASSGN_ID)) " +
			"                                                                                  UNION " +
			"                                                                                 select REF_wp_wq_nature_assgn_id from TABLE(Q_WORK_QUEUE_PKG.get_q_dic_wp_wq_nature_assgn (qwwa.WQ_WP_ASSGN_ID))) " +
			"                                              AND wwnd.document_type_id = wd.document_type_id " +
			"                                              AND wd.wq_id = qwwa.wq_id " +
			"                                           MINUS " +
			"                                           select DOCUMENT_TYPE_ID from Q_WQ_WP_DOCUMENT qwwd, q_wq_document wd " + 
			"                                            WHERE qwwd.WQ_WP_ASSGN_ID = qwwa.WQ_WP_ASSGN_ID " +
			"                                              AND qwwd.WQ_DOCUMENT_ID = wd.WQ_DOCUMENT_ID) " +
			"      ) \"wqDocumentIdString\" " +
			" from Q_WQ_WP_ASSGN qwwa " +
		    "WHERE qwwa.wq_id = TO_NUMBER(?) ";
	
	@Transactional(rollbackFor={Exception.class})
	public WorkQueueTriggerDTO[] getOnAttachWqDocumentTriggers(String pWqId) throws Exception {
		WorkQueueTriggerDTO[] criteriaDtos = OracleSelectHelper.getSqlResultObjects (
												"WorkQueueDS", getWqWithDocumentToDeliverSql,
												new Object[] { pWqId },
												WorkQueueTriggerDTO.class);

		if (ArrayUtils.isEmpty(criteriaDtos)) {
			return null;
		}

		StringBuilder sql = new StringBuilder(getTriggerSQL);
		sql.append(getTriggerOnAttachWqDocumentSqlWhere);
		TreeSet<WorkQueueTriggerDTO> rtnSet = new TreeSet<WorkQueueTriggerDTO>(new Comparator<WorkQueueTriggerDTO>() {
			@Override
			public int compare(WorkQueueTriggerDTO pO1, WorkQueueTriggerDTO pO2) {
				return getCompareKey(pO1).compareTo(getCompareKey(pO2));
			}
			
			private String getCompareKey(WorkQueueTriggerDTO pO1) {
				return pO1.getWqWpAssgnId() + "^" + pO1.getHandleMethodId();
			}
		});
		WorkQueueTriggerDTO[] workQueueTriggerDTOs = null;

		for (WorkQueueTriggerDTO criteriaDto : criteriaDtos) {
			if (!"Y".equals(criteriaDto.getWqDocumentIdString())) {
				continue;
			}
			
			workQueueTriggerDTOs = OracleSelectHelper.getSqlResultObjects (
					"WorkQueueDS", sql.toString(),
					new Object[] { pWqId, criteriaDto.getWqBatchId(), pWqId, criteriaDto.getWqBatchId() },
					WorkQueueTriggerDTO.class);
			
			if (ArrayUtils.isEmpty(workQueueTriggerDTOs)) {
				continue;
			}

			rtnSet.addAll(Arrays.asList(workQueueTriggerDTOs));
		}
		
		workQueueTriggerDTOs = rtnSet.toArray(new WorkQueueTriggerDTO[0]);
		
		if (ArrayUtils.isEmpty(workQueueTriggerDTOs)) {
			return null;
		}
		
		setHandlingAttributes(workQueueTriggerDTOs);
		
		return workQueueTriggerDTOs;
	}
	
	private void setHandlingAttributes(WorkQueueTriggerDTO[] workQueueTriggerDTOs) throws Exception {
		QDicHndlMethodDtlDAO criteria = null;
		for (WorkQueueTriggerDTO workQueueTriggerDTO : workQueueTriggerDTOs) {
			criteria = SpringApplicationContext.getBean(QDicHndlMethodDtlDAO.class);
			criteria.setSearchKey("handleMethodId", workQueueTriggerDTO.getHandleMethodId());
			
			workQueueTriggerDTO.setHandlingAttributes((QDicHndlMethodDtlDAO[]) criteria.doSelect(null, false));
		}
	}

	private static final String getHndlAttbNatureDescriptionSQL = 
			"select wn.wq_nature_desc " +
			"  from q_wq_wp_search_id_v wwsiv, Q_DIC_WP_WQ_NATURE_HNDL_V wwnhv, q_wq_nature wn " +
			" where wwsiv.wq_wp_assgn_id = TO_NUMBER(?) " + 
			"   and wwnhv.WP_WQ_NATURE_ASSGN_ID IN (SELECT WP_WQ_NATURE_ASSGN_ID " +
            "                                         FROM TABLE(q_work_queue_pkg.get_q_dic_wp_wq_nature_assgn(wwsiv.wq_id, wwsiv.wq_batch_id, wwsiv.wq_wp_assgn_id, 'Y'))) " +
            "   and wwsiv.wq_nature_id = wwnhv.wq_nature_id " + 
            "   and wwsiv.wq_nature_id = wn.wq_nature_id " +
			"   and HANDLE_METHOD_ID = TO_NUMBER(?) ";

	private static final String getHndlAttbNatureRemarksSQL = 
			"select wr.remarks " +
			"  from q_wq_wp_search_id_v wwsiv, Q_DIC_WP_WQ_NATURE_HNDL_V wwnhv, q_wq_remarks wr " +
			" where wwsiv.wq_wp_assgn_id = TO_NUMBER(?) " + 
			"   and wwnhv.WP_WQ_NATURE_ASSGN_ID IN (SELECT WP_WQ_NATURE_ASSGN_ID " +
            "                                         FROM TABLE(q_work_queue_pkg.get_q_dic_wp_wq_nature_assgn(wwsiv.wq_id, wwsiv.wq_batch_id, wwsiv.wq_wp_assgn_id, 'Y'))) " +
            "   and wwsiv.wq_nature_id = wwnhv.wq_nature_id " + 
			"   and wwsiv.wq_id = wr.wq_id " +
			"   and wwsiv.wq_nature_id = wr.wq_nature_id " +
			"   and HANDLE_METHOD_ID = TO_NUMBER(?) ";

	private static final String getWqAttbSQL = 
			"SELECT ATTB_VALUE FROM Q_WQ_ATTB WHERE WQ_ID = TO_NUMBER(?) AND WQ_BATCH_ID = TO_NUMBER(?) AND (wq_wp_assgn_id = TO_NUMBER(?) OR WQ_WP_ASSGN_ID IS NULL) AND ATTB_NAME = ? ORDER BY WQ_WP_ASSGN_ID, ATTB_SEQ ";

	private static final String getWqIssueByFromWhereSQL = 
			"  FROM BOMWEB_SALES_LKUP_V bslv, " +
			"       (" + getWqAttbSQL + ") sib " +
			" WHERE (bslv.STAFF_ID = sib.ATTB_VALUE OR bslv.ORG_STAFF_ID = sib.ATTB_VALUE)"; 

	@Override
	@Transactional(readOnly=true)
	public String getHandlingAttributeValue(WorkQueueTriggerDTO pWorkQueueTrigger, String pAttbName) throws Exception {
		String[] strings = null;
		StringBuilder rtnString = new StringBuilder();
		if (pAttbName.startsWith("Q_WQ_ATTB[")) {
			pAttbName = pAttbName.substring(10, pAttbName.length() - 1);
			int index = 0;
			if (pAttbName.indexOf("[") > 0
					&& "]".equals(StringUtils.right(pAttbName, 1))) {
				index = Integer.parseInt(pAttbName.substring(pAttbName.indexOf("[") + 1, pAttbName.length() - 1));
				pAttbName = pAttbName.substring(0, pAttbName.indexOf("["));
			}
			strings = OracleSelectHelper.getSqlFirstColumnStrings (
					"WorkQueueDS", getWqAttbSQL, 
					new Object[] {pWorkQueueTrigger.getWqId(), pWorkQueueTrigger.getWqBatchId(), pWorkQueueTrigger.getWqWpAssgnId(), pAttbName});
			if (ArrayUtils.isEmpty(strings)) {
				return "";
			}
			if (strings.length <= index) {
				return "";
			}
			return strings[index];
		} else if ("NATURE_DESCRIPTION".equals(pAttbName)) {
			strings = OracleSelectHelper.getSqlFirstColumnStrings (
					"WorkQueueDS", getHndlAttbNatureDescriptionSQL, 
					new Object[] {pWorkQueueTrigger.getWqWpAssgnId(), pWorkQueueTrigger.getHandleMethodId()});
			if (ArrayUtils.isEmpty(strings)) {
				return "";
			}
			for (String nature : strings) {
				rtnString.append(nature);
				rtnString.append(", ");
			}
			rtnString.setLength(rtnString.length() - 2);
			return rtnString.toString();
		} else if ("NATURE_REMARK".equals(pAttbName)
						|| "NATURE_REMARK_HTML".equals(pAttbName)) {
			strings = OracleSelectHelper.getSqlFirstColumnStrings (
					"WorkQueueDS", getHndlAttbNatureRemarksSQL, 
					new Object[] {pWorkQueueTrigger.getWqWpAssgnId(), pWorkQueueTrigger.getHandleMethodId()});
			if (ArrayUtils.isEmpty(strings)) {
				return "";
			}
			for (String remark : strings) {
				rtnString.append("NATURE_REMARK_HTML".equals(pAttbName) ? StringUtils.defaultIfEmpty(remark, "").replaceAll("\n", "<br />") : remark);
				rtnString.append("NATURE_REMARK_HTML".equals(pAttbName) ? "<br />\n" : "\n");
			}
			return rtnString.toString();
		} else if ("WQ_REMARK".equals(pAttbName)
				|| "WQ_REMARK_HTML".equals(pAttbName)
				|| "WQ_BATCH_REMARK".equals(pAttbName)
				|| "WQ_BATCH_REMARK_HTML".equals(pAttbName)) {
			RemarkDTO[] remarks = null;
			if (pAttbName.contains("WQ_BATCH")) {
				remarks = this.getWorkQueRemarksByWqWpAssgnId(pWorkQueueTrigger.getWqWpAssgnId(), new String[] {"wqBatchId"}, "", false);
			} else {
				remarks = this.getWorkQueRemarksByWqWpAssgnId(pWorkQueueTrigger.getWqWpAssgnId(), "");
			}
			
			if (ArrayUtils.isEmpty(remarks)) {
				return "";
			}			
			for (RemarkDTO remark : remarks) {
				rtnString.append(pAttbName.contains("HTML") ? StringUtils.defaultIfEmpty(remark.getRemarkContent(), "").replaceAll("\n", "<br />") : remark.getRemarkContent());
				rtnString.append(pAttbName.contains("HTML") ? "<br />\n" : "\n");
			}
			return rtnString.toString();
		} else if ("SHOP_EMAIL".equals(pAttbName)) {
			return OracleSelectHelper.getSqlFirstRowColumnString(
						"WorkQueueDS", 
						"SELECT EMAIL_ADDR from bomweb_shop where shop_cd = ?",
						new Object[] {pWorkQueueTrigger.getSbShopCode()});
		} else if ("SHOP_CONTACT_PHONE".equals(pAttbName)) {
			return OracleSelectHelper.getSqlFirstRowColumnString(
						"WorkQueueDS", 
						"SELECT CONTACT_PHONE from bomweb_shop where shop_cd = ?",
						new Object[] {pWorkQueueTrigger.getSbShopCode()});
		} else if ("WP_EMAIL".equals(pAttbName)) {
			return OracleSelectHelper.getSqlFirstRowColumnString(
						"WorkQueueDS", 
						"SELECT EMAIL_ADDR from q_working_party where wp_id = TO_NUMBER(?)",
						new Object[] {pWorkQueueTrigger.getWpId()});
		} else if ("WP_PERSON_IN_CHARGE".equals(pAttbName)) {
			return OracleSelectHelper.getSqlFirstRowColumnString(
					"WorkQueueDS", 
					"SELECT PERSON_IN_CHARGE from q_working_party where wp_id = TO_NUMBER(?)",
					new Object[] {pWorkQueueTrigger.getWpId()});
		} else if ("WP_CONTACT_PHONE".equals(pAttbName)) {
			return OracleSelectHelper.getSqlFirstRowColumnString(
					"WorkQueueDS", 
					"SELECT CONTACT_NUM from q_working_party where wp_id = TO_NUMBER(?)",
					new Object[] {pWorkQueueTrigger.getWpId()});
		} else if (pAttbName.startsWith("SB_ISSUE_BY")) {
			String selectFieldName = "STAFF_ID";
			String[] tmp = pAttbName.split("\\.");
			if (!ArrayUtils.isEmpty(tmp) && tmp.length >= 2) {
				selectFieldName = tmp[1];
			}
			return OracleSelectHelper.getSqlFirstRowColumnString(
					"WorkQueueDS", 
					new StringBuffer()
						.append("SELECT ")
						.append(selectFieldName)
						.append(getWqIssueByFromWhereSQL)
						.toString(),
					new Object[] {pWorkQueueTrigger.getWqId(), pWorkQueueTrigger.getWqBatchId(), pWorkQueueTrigger.getWqWpAssgnId(), "SB_ISSUE_BY"});
		}
		
		try {
			return BeanUtils.getProperty(pWorkQueueTrigger, pAttbName);
		} catch (Exception e) {
			return "";
		}
	}
	
	private static void DTO2DAO(Object pDTO, DaoBase pDAO) throws Exception {
		BeanUtils.copyProperties(pDAO, pDTO);
		getMapper().map(pDTO, pDAO);
	}
	
	private static void DAO2DTO(DaoBase pDAO, Object pDTO) throws Exception {
		BeanUtils.copyProperties(pDTO, pDAO);
		getMapper().map(pDAO,pDTO);
	}	
}