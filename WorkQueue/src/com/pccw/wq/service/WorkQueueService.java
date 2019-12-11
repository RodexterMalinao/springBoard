package com.pccw.wq.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.util.FastByteArrayOutputStream;
import com.pccw.util.search.Criteria;
import com.pccw.util.search.SearchResult;
import com.pccw.wq.schema.dto.RemarkDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueDocumentDTO;
import com.pccw.wq.schema.dto.WorkQueueInquiryCriteriaDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.schema.dto.WorkingPartyDTO;
import com.pccw.wq.schema.form.SbWqInquiryResultFormDTO;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;
import com.pccw.wq.schema.form.WqStatusLogFormDTO;

public interface WorkQueueService {

	public final String WQ_TYPE_SB_LTS = "SB-LTS";

	public final String WQ_TYPE_SB_DEL = "SB-DEL";

	public final String WQ_TYPE_SB_EYE = "SB-EYE";

	public final String WQ_SUBTYPE_PARTIAL_WQ = "PARTIAL_WQ";
	
	public final String WQ_SUBTYPE_FULL_WQ = "FULL_WQ";
	
	public SearchResult<WqInquiryResultFormDTO> searchWorkQueue(
			WorkQueueInquiryCriteriaDTO pWqInquiryCriteria, Criteria pPagingCriteria, String pUser) throws Exception;
	
	@Transactional(readOnly=true)
	public SearchResult<WqInquiryResultFormDTO> searchSbIdsWithOutstandingWq(String pSrvType, 
			 String pUser) throws Exception;
	
	@Transactional
	public WorkQueueDTO createWorkQueue(WorkQueueDTO pWorkQueue,
			WorkQueueNatureDTO[] pWorkQueueNatures,
			String pUser)
			throws Exception;

	@Transactional
	public void updateWorkQueue(WorkQueueDTO pWorkQueue,
			String pUser)
			throws Exception;
	
	public WorkQueueDocumentDTO[] getWorkQueueRequiredDocuments(WorkQueueDTO pWorkQueue, String pUser) throws Exception;

	@Transactional
	public void attachWorkQueueDocument(WorkQueueDTO pWorkQueue,
			WorkQueueDocumentDTO[] pWorkQueueDocuments,
			String pUser)
			throws Exception;

	
	@Transactional(readOnly=true)
	public String [] getValidWorkQueueWorkPartyStaff(String [] wqWpAssgnId, String pUser) throws Exception;
	
	@Transactional
	public int assignWorkQueueNature(String[] pWqWpAssgnId,
			String pWpStaffId, String pRemarks, String pUser) throws Exception;
		
	@Transactional
	public int changeWorkQueueStatus(String[] pWqWpAssgnId,
			String pWorkQueueStatusCd, String pReasonCd, String pOcId,
			String pRemarks, String pUser) throws Exception;
	
	@Transactional(readOnly=true)
	public RemarkDTO [] getWorkQueRemarks(String pWqWpAssgnId, String pUser) throws Exception;
	
	@Transactional
	public boolean saveRemark(String pWqWpAssgnId, String pRemarks, String pUser) throws Exception;;
	
	public WqStatusLogFormDTO[] getWorkQueueStatusLog(String pWqWpAssgnId, String pUser) throws Exception;
	
	@Transactional
	public WorkQueueDocumentDTO downloadWorkQueueDocument(String pWqWpAssgnId,
			String pWqWpAssgnDocId, String pUser) throws Exception;
	
	@Transactional
	public WorkQueueDocumentDTO[] downloadWorkQueueDocuments(String pWqWpAssgnId, String pUser) throws Exception;

	@Transactional(readOnly=true)
	public WorkingPartyDTO[] getWorkingPartyByStaffId(String pLoginId) throws Exception;

	@Transactional(readOnly=true)
	public WqInquiryResultFormDTO getWorkQueueDetail(String pWqWpAssgnId) throws Exception;
	
	@Transactional(readOnly=true)
	public List<WqInquiryResultFormDTO> getWorkQueueDetail(String pWqId, String pBatchId) throws Exception;
	
	@Transactional(readOnly=true)
	public String getOutstandingWQInquiryUrl(String pSbId, String pUser) throws Exception ;
	
	@Transactional(rollbackFor={Exception.class})
	public void sbOrderCancelled(String pSbId, boolean pIsSystemCancel, String pUser) throws Exception;
	
	@Transactional(rollbackFor={Exception.class})
	public void changeWqStatusBySbAction(String pSbId, String pAction, String pUser) throws Exception;

	public String getBomOcId(String pSbId, String pSrvType, String pSrvNum) throws Exception;
	
	public FastByteArrayOutputStream genWqCoverSheet(WqInquiryResultFormDTO pWqDetail) throws Exception;
	
	@Transactional(readOnly=true)
	public List<byte[]> getWqCoverSheets(String pWqId, String pBatchId) throws Exception;
	
	public void fillReportDataWqCoverSheet(ReportDTO pReportDTO, Map<String, Object> pRptParamMap) throws Exception;
	
	@Transactional(readOnly=true)
	public SbWqInquiryResultFormDTO[] getWorkQueueViewBySbId(String pSbId, String pUser) throws Exception;
	
	@Transactional(readOnly=true)
	public String getWqEndingStatusString() throws Exception;
	
	@Transactional(readOnly=true)
	public boolean isInputBomOcIdRequired(String[] pWqWpAssgnIds, String pTargetStatusCode) throws Exception;
	
	public void genBulkPrintSummary(ArrayList<WqInquiryResultFormDTO> pWqResultList, String pWqPrintOption, String pWqPrinter, String pUser) throws Exception;
	
	public void fillReportDataBulkPrintSummary(ReportDTO pReportDTO, Map<String, Object> pRptParamMap) throws Exception;
	
	//METHOD TO BE REMOVE !!!
	public void sbOrderFollowUpCompleted(String pSbId, String pUser) throws Exception;
	
	public List<WqInquiryResultFormDTO> getWqNaturestatus(String pSbId) throws Exception;
	
	public String getUrlByAssignId(String pAssignId, WorkingPartyDTO[] pWorkingPartyDTO) throws Exception;
	
	public String getAttachmentHeaderString(WqInquiryResultFormDTO pWqDetail) throws Exception;

	public List<String> imsGetPdfPaths(String sbid) throws Exception;
}