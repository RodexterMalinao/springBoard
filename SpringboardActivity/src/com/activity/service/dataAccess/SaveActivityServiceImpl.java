package com.activity.service.dataAccess;

import org.springframework.transaction.annotation.Transactional;

import com.activity.dto.ActivityAddressDTO;
import com.activity.dto.ActivityAttachDocDTO;
import com.activity.dto.ActivityAttributeDTO;
import com.activity.dto.ActivityCustomerDtlDTO;
import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityTaskDTO;
import com.activity.dto.DocumentDTO;
import com.activity.service.DocumentSubmissionService;
import com.activity.util.ActivityConstants;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.dto.RemarkDTO;
import com.bomwebportal.service.SaveService;
import com.bomwebportal.service.ServiceActionBase;

public class SaveActivityServiceImpl extends SaveService implements SaveActivityService {

	private ServiceActionBase activityService = null;
	private ServiceActionBase documentService = null;
	private ServiceActionBase activityTaskService = null;
	private ServiceActionBase activityAttachDocService = null;
	private ServiceActionBase activityRemarkService = null;
	private ServiceActionBase activityAttributeService = null;
	private ServiceActionBase activityAddressService = null;
	private ServiceActionBase activityCustDtlService = null;
	
	private DocumentSubmissionService documentSubmissionService = null;
	
	protected SaveDocumentService saveDocumentService = null;
	
	@Transactional
	public void saveActivity(ActivityDTO pActv, String pUser) {
		this.user = pUser;
		this.saveActivity(pActv);
	}
	
	@Transactional
	public void saveActivityTask(String pActvId, ActivityTaskDTO pTask, String pUser) {
		this.user = pUser;
		this.saveTask(pTask, pActvId);
	}
	
	protected void saveActivity(ActivityDTO pActv) {
		
		if (pActv == null) {
			return;
		}
		this.activityService.doSave(pActv, this.user);
		this.updateActvRelatedAction(pActv);
		ActivityTaskDTO[] tasks = pActv.getTasks();
		
		for (int i=0; tasks!=null && i<tasks.length; ++i) {
			this.saveTask(tasks[i], pActv.getActvId());
		}
		
		ActivityAttributeDTO[] attbs = pActv.getActvAttbs();
		for (int i=0; attbs!=null && i<attbs.length; ++i) {
			this.saveAttribute(attbs[i], pActv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ);
		}
		
		ActivityAddressDTO[] address = pActv.getAddress();
		for (int i=0; address!=null && i<address.length; ++i) {
			this.saveAddress(address[i], pActv.getActvId());
		}
		
		ActivityCustomerDtlDTO[] custDtls = pActv.getCustomerDtls();
		for (int i=0; custDtls!=null && i<custDtls.length; ++i) {
			this.saveCustomer(custDtls[i], pActv.getActvId());
		}		
		
		RemarkDTO[] remarks = pActv.getActvRemarks();
		
		for (int i=0; remarks!=null && i<remarks.length; ++i) {
			this.saveRemark(remarks[i], pActv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ, ActivityConstants.TASK_LEVEL_SEQ);
		}
	}
	
	private void updateActvRelatedAction(ActivityDTO pActv) {
		
		if (pActv.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		this.setObjectAction(pActv.getTasks(), ObjectActionBaseDTO.ACTION_DELETE);
	}
	
	protected void saveTask(ActivityTaskDTO pTask, String pActvId) {
		
		if (pTask == null) {
			return;
		}
		this.activityTaskService.doSave(pTask, this.user, pActvId);
		this.updateTaskRelatedAction(pTask);
		
		ActivityAttachDocDTO[] attachDocs = pTask.getAttachDocs();
		if (attachDocs != null){
			saveDocumentService.saveAttachDocList(attachDocs, pActvId, pTask.getTaskSeq(), this.user);
		}
//		for (int i=0; attachDocs!=null && i<attachDocs.length; ++i) {
//			this.saveAttacheDoc(attachDocs[i], pActvId, pTask.getTaskSeq());
//		}
		RemarkDTO[] remarks = pTask.getTaskRemarks();
		
		for (int i=0; remarks!=null && i<remarks.length; ++i) {
			this.saveRemark(remarks[i], pActvId, pTask.getTaskSeq(), ActivityConstants.TASK_LEVEL_SEQ);
		}
		DocumentDTO[] docs = pTask.getDocuments();
		
		for (int i=0; docs!=null && i<docs.length; ++i) {
			this.saveDocument(docs[i], pActvId, pTask.getTaskSeq());
		}
		ActivityAttributeDTO[] attbs = pTask.getTaskAttbs();
		
		for (int i=0; attbs!=null && i<attbs.length; ++i) {
			this.saveAttribute(attbs[i], pActvId, pTask.getTaskSeq(), ActivityConstants.TASK_LEVEL_SEQ);
		}
	}
	
	private void updateTaskRelatedAction(ActivityTaskDTO pTask) {
		
		if (pTask.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		this.setObjectAction(pTask.getAttachDocs(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pTask.getTaskRemarks(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pTask.getDocuments(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pTask.getTaskAttbs(), ObjectActionBaseDTO.ACTION_DELETE);
	}
	

	
	private void saveRemark(RemarkDTO pRemark, String pActvId, String pTaskSeq, String pStatusSeq) {
		
		if (pRemark == null) {
			return;
		}
		this.activityRemarkService.doSave(pRemark, this.user, pActvId, pTaskSeq, pStatusSeq);
	}
	
	private void saveAttribute(ActivityAttributeDTO pAttb, String pActvId, String pTaskSeq) {
		
		if (pAttb == null) {
			return;
		}
		this.activityAttributeService.doSave(pAttb, this.user, pActvId, pTaskSeq);
	}	
	
	private void saveAttribute(ActivityAttributeDTO pAttb, String pActvId, String pTaskSeq, String pStatusSeq) {
		
		if (pAttb == null) {
			return;
		}
		this.activityAttributeService.doSave(pAttb, this.user, pActvId, pTaskSeq, pStatusSeq);
	}
	
	private void saveDocument(DocumentDTO pDoc, String pActvId, String pTaskSeq) {
		
		if (pDoc == null) {
			return;
		}
		this.documentService.doSave(pDoc, this.user, pActvId + pTaskSeq); 
		this.updateDocumentRelatedAction(pDoc);
		this.documentSubmissionService.submitDocument(pActvId + pTaskSeq, pDoc, this.user);
	}
	
	private void updateDocumentRelatedAction(DocumentDTO pDoc) {
		
		if (pDoc.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		this.setObjectAction(pDoc.getDocDetails(), ObjectActionBaseDTO.ACTION_DELETE);
	}
	
	private void saveAddress(ActivityAddressDTO pAddr, String pActvId) {
		
		if (pAddr == null) {
			return;
		}
		this.activityAddressService.doSave(pAddr, this.user, pActvId);
	}		
	
	private void saveCustomer(ActivityCustomerDtlDTO pCustDtl, String pActvId) {
		
		if (pCustDtl == null) {
			return;
		}
		this.activityCustDtlService.doSave(pCustDtl, this.user, pActvId);
	}		
	public ServiceActionBase getActivityService() {
		return activityService;
	}

	public void setActivityService(ServiceActionBase activityService) {
		this.activityService = activityService;
	}

	public ServiceActionBase getDocumentService() {
		return documentService;
	}

	public void setDocumentService(ServiceActionBase documentService) {
		this.documentService = documentService;
	}

	public ServiceActionBase getActivityTaskService() {
		return activityTaskService;
	}

	public void setActivityTaskService(ServiceActionBase activityTaskService) {
		this.activityTaskService = activityTaskService;
	}

	public ServiceActionBase getActivityAttachDocService() {
		return activityAttachDocService;
	}

	public void setActivityAttachDocService(
			ServiceActionBase activityAttachDocService) {
		this.activityAttachDocService = activityAttachDocService;
	}

	public ServiceActionBase getActivityRemarkService() {
		return activityRemarkService;
	}

	public void setActivityRemarkService(ServiceActionBase activityRemarkService) {
		this.activityRemarkService = activityRemarkService;
	}

	public ServiceActionBase getActivityAttributeService() {
		return activityAttributeService;
	}

	public void setActivityAttributeService(
			ServiceActionBase activityAttributeService) {
		this.activityAttributeService = activityAttributeService;
	}

	public DocumentSubmissionService getDocumentSubmissionService() {
		return documentSubmissionService;
	}

	public void setDocumentSubmissionService(
			DocumentSubmissionService documentSubmissionService) {
		this.documentSubmissionService = documentSubmissionService;
	}

	public SaveDocumentService getSaveDocumentService() {
		return saveDocumentService;
	}

	public void setSaveDocumentService(SaveDocumentService saveDocumentService) {
		this.saveDocumentService = saveDocumentService;
	}

	public ServiceActionBase getActivityAddressService() {
		return this.activityAddressService;
	}

	public void setActivityAddressService(ServiceActionBase pActivityAddressService) {
		this.activityAddressService = pActivityAddressService;
	}

	public ServiceActionBase getActivityCustDtlService() {
		return this.activityCustDtlService;
	}

	public void setActivityCustDtlService(ServiceActionBase pActivityCustDtlService) {
		this.activityCustDtlService = pActivityCustDtlService;
	}
	
	
}
