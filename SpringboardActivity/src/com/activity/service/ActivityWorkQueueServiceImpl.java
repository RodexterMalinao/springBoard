package com.activity.service;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import smartliving.backend.dto.OrdDocSLVDTO;
import smartliving.backend.service.ActivityDocumentService;

import com.activity.dao.ActivityTaskWqActionLookupDAO;
import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityTaskDTO;
import com.activity.dto.SbActvTaskWqActionDTO;
import com.activity.service.dataAccess.ActivityRemarkServiceImpl;
import com.activity.service.dataAccess.ActivityStatusService;
import com.activity.service.dataAccess.ActivityTaskServiceImpl;
import com.activity.util.ActivityConstants;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.dto.RemarkDTO;
import com.bomwebportal.util.PdfUtil;
import com.pccw.util.FastByteArrayOutputStream;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.schema.dto.WorkQueueAttributeDTO;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.schema.dto.WorkQueueDocumentDTO;
import com.pccw.wq.schema.dto.WorkQueueNatureDTO;
import com.pccw.wq.schema.form.WqInquiryResultFormDTO;
import com.pccw.wq.service.WorkQueueService;

public class ActivityWorkQueueServiceImpl implements ActivityWorkQueueService {
	
	@Override
	public void invokeWorkQueue(ActivityDTO pActivity, ActivityTaskDTO pTask, String pUser) throws Exception {
		SbActvTaskWqActionDTO[] sbActvTaskWqActions = SpringApplicationContext.getBean(ActivityTaskWqActionLookupDAO.class)
				.getSbActvTaskWqAction(pActivity.getActvId(), (pTask == null ? ActivityConstants.ACTV_LEVEL_SEQ: pTask.getTaskSeq()));
		
		if (ArrayUtils.isEmpty(sbActvTaskWqActions)) {
			return;
		}
		
		TreeSet<SbActvTaskWqActionDTO> createWqSbActvTaskWqActionSet = new TreeSet<SbActvTaskWqActionDTO>(new Comparator<SbActvTaskWqActionDTO>() {
			@Override
			public int compare(SbActvTaskWqActionDTO pArg0,
					SbActvTaskWqActionDTO pArg1) {
				return getCompareKey(pArg0).compareTo(getCompareKey(pArg1));
			}
			
			private String getCompareKey(SbActvTaskWqActionDTO pDto) {
				return pDto.getActvId() + "^" + pDto.getTaskSeq() + "^" + pDto.getWqAction() + "^" + pDto.getWqType() + "^" + pDto.getWqSubType() + "^" + pDto.getWqNatureId();
			}
		});
		
		for (SbActvTaskWqActionDTO sbActvTaskWqAction : sbActvTaskWqActions) {
			if (SbActvTaskWqActionDTO.WQ_ACTION_CREATE.equals(sbActvTaskWqAction.getWqAction()) 
					|| SbActvTaskWqActionDTO.WQ_ACTION_CREATE_WITH_DOC.equals(sbActvTaskWqAction.getWqAction())) {
				createWqSbActvTaskWqActionSet.add(sbActvTaskWqAction);
			} else if (StringUtils.contains(sbActvTaskWqAction.getWqAction(), SbActvTaskWqActionDTO.WQ_ACTION_CHANGE_STATUS)) {
				this.updateWorkQueueStatus(sbActvTaskWqAction, pUser);
			} else if (StringUtils.contains(sbActvTaskWqAction.getActvAction(), SbActvTaskWqActionDTO.ACTV_ACTION_CHANGE_TASK_STATUS_BY_TYPE)) {
				updateTaskStatusByType(sbActvTaskWqAction, pActivity, pUser);
			} else if (StringUtils.contains(sbActvTaskWqAction.getActvAction(), SbActvTaskWqActionDTO.ACTV_ACTION_CHANGE_TASK_STATUS)) {
				updateTaskStatus(sbActvTaskWqAction, pActivity, pTask, pUser);
			} else if (StringUtils.contains(sbActvTaskWqAction.getActvAction(), SbActvTaskWqActionDTO.ACTV_ACTION_CHANGE_RELATED_TASK_STATUS)) {
				updateRelatedTaskStatus(sbActvTaskWqAction, pActivity, pTask, pUser);
			}
		}
		
		if (createWqSbActvTaskWqActionSet.size() <= 0) {
			return;
		}
		
		WorkQueueDTO wqDTO = null;
		String wqTypeSubType = null;
		for (SbActvTaskWqActionDTO sbActvTaskWqAction : createWqSbActvTaskWqActionSet) {
			wqDTO = this.createWorkQueueDTO(pActivity, pTask, sbActvTaskWqAction, pUser, wqDTO);
			
			if (wqTypeSubType == null) {
				wqTypeSubType = sbActvTaskWqAction.getWqType() + "^" + sbActvTaskWqAction.getWqSubType();
			}
			if (!StringUtils.equals(wqTypeSubType, sbActvTaskWqAction.getWqType() + "^" + sbActvTaskWqAction.getWqSubType())) {
				submitWq(pActivity, pTask, wqDTO, pUser);
				wqDTO = null;
			}
			wqTypeSubType = sbActvTaskWqAction.getWqType() + "^" + sbActvTaskWqAction.getWqSubType();
		}
		submitWq(pActivity, pTask, wqDTO, pUser);
	}

	private void submitWq(ActivityDTO pActivity, ActivityTaskDTO pTask, WorkQueueDTO pWqDTO, String pUser) throws Exception {
		
		this.preSubmitWorkQueue(pWqDTO, pActivity, pTask);
				
		TreeSet<String> docTypeSet = new TreeSet<String>();
		if (pWqDTO.getDocuments() != null) {
			for (WorkQueueDocumentDTO wqDoc : pWqDTO.getDocuments()) {
				docTypeSet.add(wqDoc.getReportName());
			}
		}
		
		WorkQueueService wqService = SpringApplicationContext.getBean(WorkQueueService.class);
		
		wqService.createWorkQueue(pWqDTO, pWqDTO.getWorkQueueNatures(), pUser);
		if (ArrayUtils.isNotEmpty(pWqDTO.getCreatedWpWqAssignIds())) {
			if (StringUtils.isNotBlank(pTask.getWqWpAssgnId())) {
				pTask.setOriWqWpAssgnId(pTask.getWqWpAssgnId());
			}
			pTask.setWqWpAssgnId(pWqDTO.getCreatedWpWqAssignIds()[0]);
			pTask.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			RemarkDTO[] taskRemarks = pTask.getTaskRemarks();
			for (int i=0; taskRemarks!= null && i<taskRemarks.length; i++){
				if (StringUtils.isBlank(taskRemarks[i].getWqWpAssgnId())){
					taskRemarks[i].setWqWpAssgnId(pWqDTO.getCreatedWpWqAssignIds()[0]);	
					((ActivityRemarkServiceImpl)SpringApplicationContext.getBean("activityRemarkService")).updateWqWpAssgnId(pActivity.getActvId(), pTask.getTaskSeq(), pWqDTO.getCreatedWpWqAssignIds()[0]);
				}
			}
			((ActivityTaskServiceImpl) SpringApplicationContext.getBean("activityTaskService")).doSave(pTask, pUser, pActivity.getActvId());
		}

		if (setWorkQueueDtoDocuments(pWqDTO, pActivity, pTask, docTypeSet)) {
			wqService.attachWorkQueueDocument(pWqDTO, pWqDTO.getDocuments(), pUser);
		}
		
		this.postSubmitWorkQueue(pWqDTO, pActivity, pTask);
	}
	
	@Override
	public void preSubmitWorkQueue(WorkQueueDTO pWorkQueueDTO, ActivityDTO pActivityDTO, ActivityTaskDTO pTask) {
	}
	
	@Override
	public void postSubmitWorkQueue(WorkQueueDTO pWorkQueueDTO, ActivityDTO pActivityDTO, ActivityTaskDTO pTask) {		
	}
	
	protected String generateWqUrl(String pUrl, ActivityDTO pActivity, ActivityTaskDTO pTask) {
		String rtnUrl = pUrl;
		if (StringUtils.isBlank(pUrl)) {
			return null;
		}
		rtnUrl = StringUtils.replace(rtnUrl, "{orderId}", pActivity.getOrderId());
		rtnUrl = StringUtils.replace(rtnUrl, "{actvId}", pActivity.getActvId());
		rtnUrl = StringUtils.replace(rtnUrl, "{taskSeq}", (pTask == null ? ActivityConstants.ACTV_LEVEL_SEQ : pTask.getTaskSeq()));
		return rtnUrl;
	}
	
	protected WorkQueueDTO createWorkQueueDTO(ActivityDTO pActivity, ActivityTaskDTO pTask, SbActvTaskWqActionDTO pSbActvTaskWqAction, String pUser, WorkQueueDTO pWqDTO) throws Exception {
		WorkQueueDTO wqDTO = pWqDTO;
		if (wqDTO == null) {
			wqDTO = new WorkQueueDTO();
			if (StringUtils.isNotBlank(pActivity.getOrderId())) {
				wqDTO.setSbId(pActivity.getOrderId());
			} else if (StringUtils.isNotBlank(pActivity.getProfileId())) {
				wqDTO.setServiceId(pActivity.getProfileId());
			}
			wqDTO.setSbShopCode(pActivity.getShopCd());
			wqDTO.setTypeOfService(pActivity.getLob());
			wqDTO.setSbActvId(pActivity.getActvId());
			if (StringUtils.isNotBlank(pTask.getWqWpAssgnId())) {
				wqDTO.setCreatedBatchId(getWqBatchId(pTask.getWqWpAssgnId()));
			} else if (StringUtils.isNotBlank(pTask.getRelatedTaskSeq())) {
				ActivityTaskDTO relTask = pActivity.getTaskBySeq(pTask.getRelatedTaskSeq());
				if (StringUtils.isNotBlank(relTask.getWqWpAssgnId())) {
					wqDTO.setCreatedBatchId(getWqBatchId(relTask.getWqWpAssgnId()));
				}
			}
		}

		if (StringUtils.isBlank(wqDTO.getUrl())) {
			wqDTO.setUrl(this.generateWqUrl(pSbActvTaskWqAction.getUrl(), pActivity, pTask));
		}
		
		WorkQueueNatureDTO wqNatureDTO = new WorkQueueNatureDTO();
		wqNatureDTO.setWorkQueueType(pSbActvTaskWqAction.getWqType());
		wqNatureDTO.setWorkQueueSubType(pSbActvTaskWqAction.getWqSubType());
		wqNatureDTO.setWorkQueueNatureId(pSbActvTaskWqAction.getWqNatureId());
		wqDTO.addWorkQueueNature(wqNatureDTO);

		if (ArrayUtils.isNotEmpty(pSbActvTaskWqAction.getWqAttributes())) {
			for (WorkQueueAttributeDTO wqAttribute : pSbActvTaskWqAction.getWqAttributes()) {
				wqDTO.addAttribute(wqAttribute);
			}
		}
		
		RemarkDTO[] remarkDtos = pTask.getTaskRemarks();
		if (ArrayUtils.isNotEmpty(remarkDtos)) {
			com.pccw.wq.schema.dto.RemarkDTO wqRemark = null;
			for (int i = 0; i < remarkDtos.length; i++) {
				if (StringUtils.isNotBlank(remarkDtos[i].getWqWpAssgnId())){
					continue;
				}
				wqRemark = new com.pccw.wq.schema.dto.RemarkDTO();
				wqRemark.setRemarkContent(remarkDtos[i].getRmkDtl());
				wqRemark.setRemarkSequence(String.valueOf(i));
				wqDTO.addRemark(wqRemark);
			}
		}
		
		if (!SbActvTaskWqActionDTO.WQ_ACTION_CREATE_WITH_DOC.equals(pSbActvTaskWqAction.getWqAction())) {
			return wqDTO;
		}
		
		List<OrdDocSLVDTO> ordDocList = SpringApplicationContext.getBean(ActivityDocumentService.class).getCapturedOrdDoc(pActivity.getActvId(), null);
		if (ordDocList == null || ordDocList.isEmpty()) {
			return wqDTO;
		}
		
		TreeSet<String> docTypeSet = new TreeSet<String>();
		WorkQueueDocumentDTO wqDoc = null;
		for (OrdDocSLVDTO ordDoc : ordDocList) {
			if (docTypeSet.contains(ordDoc.getDocType())) {
				continue;
			}
			wqDoc = new WorkQueueDocumentDTO();
			wqDoc.setReportName(ordDoc.getDocType());
			wqDTO.addDocument(wqDoc);
			docTypeSet.add(ordDoc.getDocType());
		}
		setWorkQueueDtoDocuments(wqDTO, pActivity, pTask, null);
		
		return wqDTO;
	}
	
	private boolean setWorkQueueDtoDocuments(WorkQueueDTO pWqDTO, ActivityDTO pActivity, ActivityTaskDTO pTask, Set<String> pSubmittedDocTypeSet) throws Exception {
		ArrayList<WorkQueueDocumentDTO> wqDocList = new ArrayList<WorkQueueDocumentDTO>();

		if (pWqDTO == null || ArrayUtils.isEmpty(pWqDTO.getDocuments())) {
			return false;
		}
		List<OrdDocSLVDTO> ordDoc = SpringApplicationContext.getBean(ActivityDocumentService.class).getCapturedOrdDoc(pActivity.getActvId(), null);
		List<OrdDocSLVDTO> ordDocListByDocType = new ArrayList<OrdDocSLVDTO>();
		FastByteArrayOutputStream outPdfStream = null;
		List<InputStream> docInputStreamList = new ArrayList<InputStream>();
		File outputPdfFile = null; 
		
		for (WorkQueueDocumentDTO wqDoc : pWqDTO.getDocuments()) {
			if (pSubmittedDocTypeSet != null && pSubmittedDocTypeSet.contains(wqDoc.getReportName())) {
				continue;
			}
			ordDocListByDocType.clear();
			for (OrdDocSLVDTO ordDocSLVDTO : ordDoc) {
				if (wqDoc.getReportName().equals(ordDocSLVDTO.getDocType())) {
					if (StringUtils.equals(ordDocSLVDTO.getOrderId(), pActivity.getActvId()) || 
							(pTask!=null && StringUtils.equals(ordDocSLVDTO.getOrderId(), pActivity.getActvId() + pTask.getTaskSeq()))){
						ordDocListByDocType.add(ordDocSLVDTO);	
					}
					
				}
			}
			
			if (ordDocListByDocType.size() <= 0) {
				continue;
			}
			
			docInputStreamList.clear();
			for (OrdDocSLVDTO ordDocSLVDTO : ordDocListByDocType) {
	            if (StringUtils.isBlank(ordDocSLVDTO.getFilePathName())) {
	                continue;
	            }
	            docInputStreamList.add(FileUtils.openInputStream(SpringApplicationContext.getBean(UploadActvDocumentService.class).getFile(ordDocSLVDTO)));
			}
			if (docInputStreamList.isEmpty()) {
				continue;
			}
			outPdfStream = new FastByteArrayOutputStream();
			PdfUtil.concatPDFs(docInputStreamList, outPdfStream, false);
			outputPdfFile = SpringApplicationContext.getBean(UploadActvDocumentService.class).getWqFile(
					pActivity.getActvId(), null, wqDoc.getReportName());
			FileUtils.writeByteArrayToFile(
					outputPdfFile, 
					outPdfStream.getByteArray());
			wqDoc.setAttachmentFullPath(outputPdfFile.getPath());
			wqDocList.add(wqDoc);
	    }
		
		return !wqDocList.isEmpty();
	}
	
	private String getWqBatchId(String pWqWpAssgnId) throws Exception {
		WqInquiryResultFormDTO result = SpringApplicationContext.getBean(WorkQueueService.class).getWorkQueueDetail(pWqWpAssgnId);
		return result == null ? null : result.getWqBatchId();
	}
	
	protected void updateWorkQueueStatus(SbActvTaskWqActionDTO pSbActvTaskWqAction, String pUser) throws Exception {
		if (StringUtils.isBlank(pSbActvTaskWqAction.getWqWpAssgnId())) {
			return;
		}
		String newWqStatus = pSbActvTaskWqAction.getWqAction().split("\\|")[1];
		SpringApplicationContext.getBean(WorkQueueService.class).changeWorkQueueStatus(
				new String[] {pSbActvTaskWqAction.getWqWpAssgnId()}, 
				newWqStatus, 
				null,
				null, null, pUser);
	}

	protected void updateRelatedTaskStatus(SbActvTaskWqActionDTO pSbActvTaskWqAction, ActivityDTO pActivity, ActivityTaskDTO pTask, String pUser) throws Exception {
		updateTaskStatus(pSbActvTaskWqAction, pActivity, pTask, pUser, true);
	}

	protected void updateTaskStatus(SbActvTaskWqActionDTO pSbActvTaskWqAction, ActivityDTO pActivity, ActivityTaskDTO pTask, String pUser) throws Exception {
		updateTaskStatus(pSbActvTaskWqAction, pActivity, pTask, pUser, false);
	}

	protected void updateTaskStatusByType(SbActvTaskWqActionDTO pSbActvTaskWqAction, ActivityDTO pActivity, String pUser) throws Exception {
		String[] parameters = pSbActvTaskWqAction.getActvAction().split("\\|");
		String criteriaTaskType = parameters[1];
		String criteriaTaskStatus = parameters[2];
		String newTaskStatus = parameters[3];
		boolean pIsSkipTrigger = false;
		if (parameters.length > 4) {
			pIsSkipTrigger = "SKIP_TRIGGER".equals(parameters[4]);
		}
		ActivityTaskDTO[] tasks = pActivity.getTasksByType(criteriaTaskType, "PENDING".equals(criteriaTaskStatus));
		if (ArrayUtils.isEmpty(tasks)) {
			return;
		}
		for (ActivityTaskDTO task : tasks) {
			if (!"*".equals(criteriaTaskStatus) && !"PENDING".equals(criteriaTaskStatus) && !(StringUtils.contains(task.getTaskStatus().getStatus(), criteriaTaskStatus))) {
				continue;
			}
			SpringApplicationContext.getBean(ActivityStatusService.class).updateStatus(
					pActivity, 
					task, 
					newTaskStatus, 
					pUser, 
					pIsSkipTrigger);
		}
	}
	
	protected void updateTaskStatus(SbActvTaskWqActionDTO pSbActvTaskWqAction, ActivityDTO pActivity, ActivityTaskDTO pTask, String pUser, boolean pIsRelatedTask) throws Exception {
		String[] parameters = pSbActvTaskWqAction.getActvAction().split("\\|");
		String newTaskStatus = parameters[1];
		boolean pIsSkipTrigger = false;
		if (parameters.length > 2) {
			pIsSkipTrigger = "SKIP_TRIGGER".equals(parameters[2]);
		}
		SpringApplicationContext.getBean(ActivityStatusService.class).updateStatus(
				pActivity, 
				pIsRelatedTask ? pActivity.getTaskBySeq(pTask.getRelatedTaskSeq()) : pTask, 
				newTaskStatus, 
				pUser, 
				pIsSkipTrigger);
	}

}