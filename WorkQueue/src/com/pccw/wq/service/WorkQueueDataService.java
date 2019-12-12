package com.pccw.wq.service;

import org.springframework.transaction.annotation.Transactional;

import com.pccw.util.search.Criteria;
import com.pccw.util.search.SearchResult;
import com.pccw.wq.schema.dto.RemarkDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueDocumentDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.schema.dto.WorkQueueTriggerDTO;
import com.pccw.wq.schema.dto.WorkingPartyDTO;
import com.pccw.wq.schema.form.SbWqInquiryResultFormDTO;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;
import com.pccw.wq.schema.form.WqStatusLogFormDTO;

public interface WorkQueueDataService {

	@Transactional(readOnly=true)
	public String[] getWqEndingStatus() throws Exception;
	
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> searchWorkQueue(Criteria pCriteria) throws Exception;
	
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> searchSbIdsWithWq(Criteria[] pCriterias) throws Exception;
	
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> searchSbActvIdsWithWq(Criteria[] pCriterias) throws Exception;
	
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> serachWqNatureStatusWithSbId(Criteria pCriteria) throws Exception;
	
	@Transactional
	public WorkQueueDTO createWorkQueue(WorkQueueDTO pWorkQueue,
			String pCreateBy) throws Exception;

	@Transactional
	public WorkQueueDTO createWorkQueue(WorkQueueDTO pWorkQueue,
			String pCreateBy, boolean pIsHandleBeforeCreateTrigger) throws Exception;
	
	@Transactional
	public void insertWqNature(String pWqId, String pWqBatchId, String pCreateBy, WorkQueueNatureDTO[] pWorkQueueNatures) throws Exception;
	
	@Transactional
	public void dispatchWorkQueue(String pWqId, String pWqBatchId, String pUrl, String pUser) throws Exception;
	
	@Transactional
	public void updateWorkQueue(WorkQueueDTO pWorkQueue, String pCreateBy) throws Exception;
	
	@Transactional(readOnly=true)
	public String [] getValidWorkQueueWorkPartyStaff(String [] wqWpAssgnId, String pUser) throws Exception;
	
	@Transactional
	public int assignWorkQueueNature(String[] pWqWpAssgnId,
			String pWpStaffId, String pRemarks, String pUser) throws Exception;	
	
	@Transactional
	public int changeWorkQueueStatus(String[] pWqWpAssgnId,
			String pStatusCd, String pReasonCd, String pOcId,
			String pRemarks, String pUser) throws Exception;
	
	@Transactional(readOnly=true)
	public RemarkDTO [] getWorkQueRemarksByWqWpAssgnId(String pWqWpAssgnId, String pUser) throws Exception;

	@Transactional(readOnly=true)
	public RemarkDTO[] getWorkQueRemarksByWqWpAssgnId(String pWqWpAssgnId,
			String[] pExtractCondition, String pUser) throws Exception;
	
	@Transactional
	public boolean saveRemark(String pWqWpAssgnId, String pRemarks, String pUser) throws Exception;
	
	@Transactional(readOnly=true)
	public WqStatusLogFormDTO [] getWorkQueueStatusLog(String pWqWpAssgnId, String pUser) throws Exception;
	
	@Transactional(readOnly=true)
	public WorkQueueDocumentDTO[] getWorkQueueRequiredDocuments(
			WorkQueueDTO pWorkQueue, String pUser) throws Exception;
	
	@Transactional
	public void attachWorkQueueDocument(WorkQueueDTO pWorkQueue,
			WorkQueueDocumentDTO[] pWorkQueueDocuments,
			String pUser)
			throws Exception;
	
	@Transactional
	public void deliverWorkQueueDocument(String pWqId) throws Exception;
	
	@Transactional
	public WorkQueueDocumentDTO downloadWorkQueueDocument(String pWqWpAssgnId,
			String pWqWpAssgnDocId, String pUser) throws Exception;
	
	@Transactional
	public WorkQueueDocumentDTO[] downloadWorkQueueDocuments(String pWqWpAssgnId, String pUser) throws Exception;

	@Transactional(readOnly=true)
	public WorkingPartyDTO[] getWorkingPartyByStaffId(String pLoginId, String pAccessType) throws Exception;
	
	@Transactional(readOnly=true)
	public WqInquiryResultFormDTO getWorkQueueDetail(String pWqWpAssgnId) throws Exception;
	
	@Transactional(readOnly=true)
	public String getHandlingAttributeValue(WorkQueueTriggerDTO pWorkQueueTrigger, String pAttbName) throws Exception;
	
	@Transactional(readOnly=true)
	public SbWqInquiryResultFormDTO[] getWorkQueueViewBySbId(String pSbId, String pUser) throws Exception;
	
	@Transactional(readOnly=true)
	public SbWqInquiryResultFormDTO[] getWorkQueueViewBySbId(String pSbId, String[] pExtractCondition, String pUser) throws Exception;
	
	public WorkQueueTriggerDTO[] getOnReceiveWorkQueueTriggers(String pWqId, String pBatchId, String pMaxWqWpAssgnId) throws Exception;
	
	public WorkQueueTriggerDTO[] getOnStatusChangeWorkQueueTriggers(String pWqWpAssignId) throws Exception;
}