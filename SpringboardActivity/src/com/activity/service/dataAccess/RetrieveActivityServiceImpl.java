package com.activity.service.dataAccess;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.transaction.annotation.Transactional;

import com.activity.dao.dataAccess.ActivityTaskDAO;
import com.activity.dto.ActivityAddressDTO;
import com.activity.dto.ActivityAttachDocDTO;
import com.activity.dto.ActivityAttributeDTO;
import com.activity.dto.ActivityCustomerDtlDTO;
import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityStatusDTO;
import com.activity.dto.ActivityTaskDTO;
import com.activity.dto.DocumentDTO;
import com.activity.dto.DocumentDetailDTO;
import com.activity.util.ActivityConstants;
import com.bomwebportal.dto.RemarkDTO;
import com.bomwebportal.service.ServiceActionBase;
import com.pccw.util.db.DaoBase;

public class RetrieveActivityServiceImpl implements RetrieveActivityService {

	private ServiceActionBase activityService = null;
	private ServiceActionBase documentService = null;
	private ServiceActionBase documentDetailService = null;
	private ServiceActionBase activityTaskService = null;
	private ServiceActionBase activityAttachDocService = null;
	private ServiceActionBase activityRemarkService = null;
	private ServiceActionBase activityAttributeService = null;
	protected ActivityStatusService activityStatusService = null;
	private ServiceActionBase activityAddressService = null;
	private ServiceActionBase activityCustDtlService = null;
	
	protected String user = null;
	
	
	@Transactional
	public ActivityDTO retrieveActivity(String pActvId, String pUser) {
		this.user = pUser;
		return this.getActivity(pActvId);
	}
	
	protected ActivityDTO getActivity(String pActvId) {
		return this.getActivity(new ActivityDTO(null), pActvId);
	}
	
	protected ActivityDTO getActivity(ActivityDTO pActv, String pActvId) {

		DaoBase[] actvDaos = this.activityService.doRetrieve(pActvId);
		
		if (ArrayUtils.isEmpty(actvDaos)) {
			return null;
		}
		ActivityDTO actv = (ActivityDTO)this.activityService.convertToDto(pActv, actvDaos[0]);
		actv.setCustomerDtls(this.getCustomerDtls(actv.getActvId()));
		actv.setAddress(this.getAddress(actv.getActvId()));
		actv.setTasks(this.getTasks(actv.getActvId()));
		actv.setStatusHistory(this.getStatus(actv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ));
		actv.setActvAttbs(this.getAttributes(actv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ));
		actv.setActvRemarks(this.getRemarks(actv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ, ActivityConstants.TASK_LEVEL_SEQ));
		return actv;
	}
	
	protected ActivityTaskDTO[] getTasks(String pActvid) {
		return this.getTasks(new HashMap<String,ActivityTaskDTO>(), pActvid);
	}
	
	protected ActivityTaskDTO[] getTasks(Map<String,ActivityTaskDTO> pTaskMap, String pActvid) {
		
		DaoBase[] taskDaos = this.activityTaskService.doRetrieve(pActvid);
		
		if (ArrayUtils.isEmpty(taskDaos)) {
			return null;
		}
		ActivityTaskDTO task = null;
		
		for (int i=0; i<taskDaos.length; ++i) {
			if (pTaskMap.containsKey(((ActivityTaskDAO)taskDaos[i]).getTaskSeq())) {
				task = pTaskMap.get(((ActivityTaskDAO)taskDaos[i]).getTaskSeq());
				this.activityTaskService.convertToDto(task, taskDaos[i]); 
			} else {
				task = (ActivityTaskDTO)this.activityTaskService.convertToDto(taskDaos[i]);
				pTaskMap.put(task.getTaskSeq(), task);
			}
			task.setAttachDocs(this.getAttachedDocs(pActvid, task.getTaskSeq()));
			task.setTaskRemarks(this.getRemarks(pActvid, task.getTaskSeq(), ActivityConstants.TASK_LEVEL_SEQ));
			task.setDocuments(this.getDocuments(pActvid, task.getTaskSeq()));
			task.setTaskAttbs(this.getAttributes(pActvid, task.getTaskSeq(), ActivityConstants.TASK_LEVEL_SEQ));
			task.setStatusHistory(this.getStatus(pActvid, task.getTaskSeq()));
		}
		
		return pTaskMap.values().toArray(new ActivityTaskDTO[pTaskMap.size()]);
	}
	
	public ActivityAttachDocDTO[] getAttachedDocs(String pActvid, String pTaskSeq) {
		
		DaoBase[] docDaos = this.activityAttachDocService.doRetrieve(pActvid, pTaskSeq);
		
		if (ArrayUtils.isEmpty(docDaos)) {
			return null;
		}
		ActivityAttachDocDTO[] docs = new ActivityAttachDocDTO[docDaos.length];
		
		for (int i=0; i<docDaos.length; ++i) {
			docs[i] = (ActivityAttachDocDTO)this.activityAttachDocService.convertToDto(docDaos[i]);
		}
		return docs;
	}
	
	private DocumentDTO[] getDocuments(String pActvid, String pTaskSeq) {
		
		DaoBase[] docDaos = this.documentService.doRetrieve(pActvid + pTaskSeq);
		
		if (ArrayUtils.isEmpty(docDaos)) {
			return null;
		}
		DocumentDTO[] docs = new DocumentDTO[docDaos.length];
		
		for (int i=0; i<docDaos.length; ++i) {
			docs[i] = (DocumentDTO)this.documentService.convertToDto(docDaos[i]);
			docs[i].setDocDetails(this.getDocumentDetails(pActvid, pTaskSeq, docs[i].getDocType()));
		}
		return docs;
	}
	
	private DocumentDetailDTO[] getDocumentDetails(String pActvid, String pTaskSeq, String pDocType) {
		
		DaoBase[] docDtlDaos = this.documentDetailService.doRetrieve(pActvid + pTaskSeq, pDocType);
		
		if (ArrayUtils.isEmpty(docDtlDaos)) {
			return null;
		}
		DocumentDetailDTO[] docDtls = new DocumentDetailDTO[docDtlDaos.length];
		
		for (int i=0; i<docDtlDaos.length; ++i) {
			docDtls[i] = (DocumentDetailDTO)this.documentDetailService.convertToDto(docDtlDaos[i]);
		}
		return docDtls;
		
	}
	
	private ActivityStatusDTO[] getStatus(String pActvid, String pTaskSeq) {
		
		ActivityStatusDTO[] statuses =  this.activityStatusService.getStatusHistory(pActvid, pTaskSeq);
		
		for (int i=0; statuses!=null && i<statuses.length; ++i) {
			statuses[i].setRemarks(this.getRemarks(pActvid, pTaskSeq, statuses[i].getStatusSeq()));
			statuses[i].setAttributes(this.getAttributes(pActvid, pTaskSeq, statuses[i].getStatusSeq()));
		}
		return statuses;
	}
	
	public RemarkDTO[] getRemarks(String pActvid, String pTaskSeq, String pStatusSeq) {
		
		DaoBase[] remarkDaos = this.activityRemarkService.doRetrieve(pActvid, pTaskSeq, pStatusSeq);
		
		if (ArrayUtils.isEmpty(remarkDaos)) {
			return null;
		}
		RemarkDTO[] remarks = new RemarkDTO[remarkDaos.length];
		
		for (int i=0; i<remarkDaos.length; ++i) {
			remarks[i] = (RemarkDTO)this.activityRemarkService.convertToDto(remarkDaos[i]);
		}
		return remarks;
	}
	
	
	private ActivityAttributeDTO[] getAttributes(String pActvid, String pTaskSeq) {
		
		DaoBase[] attbDaos = this.activityAttributeService.doRetrieve(pActvid, pTaskSeq);
		
		if (ArrayUtils.isEmpty(attbDaos)) {
			return null;
		}
		ActivityAttributeDTO[] attbs = new ActivityAttributeDTO[attbDaos.length];
		
		for (int i=0; i<attbDaos.length; ++i) {
			attbs[i] = (ActivityAttributeDTO)this.activityAttributeService.convertToDto(attbDaos[i]);
		}
		return attbs;
	}	
	
	private ActivityAttributeDTO[] getAttributes(String pActvid, String pTaskSeq, String pStatusSeq) {
		
		DaoBase[] attbDaos = this.activityAttributeService.doRetrieve(pActvid, pTaskSeq, pStatusSeq);
		
		if (ArrayUtils.isEmpty(attbDaos)) {
			return null;
		}
		ActivityAttributeDTO[] attbs = new ActivityAttributeDTO[attbDaos.length];
		
		for (int i=0; i<attbDaos.length; ++i) {
			attbs[i] = (ActivityAttributeDTO)this.activityAttributeService.convertToDto(attbDaos[i]);
		}
		return attbs;
	}
	
	private ActivityCustomerDtlDTO[] getCustomerDtls(String pActvId) {
		
		if (this.activityCustDtlService == null) {
			return null;
		}
		
		DaoBase[] custDtlDaos = this.activityCustDtlService.doRetrieve(pActvId);
		
		if (ArrayUtils.isEmpty(custDtlDaos)) {
			return null;
		}
		ActivityCustomerDtlDTO[] custDtls = new ActivityCustomerDtlDTO[custDtlDaos.length];
		
		for (int i=0; i<custDtlDaos.length; ++i) {
			custDtls[i] = (ActivityCustomerDtlDTO)this.activityCustDtlService.convertToDto(custDtlDaos[i]);
		}
		return custDtls;
	}	
	
	private ActivityAddressDTO[] getAddress(String pActvId) {
		
		if (this.activityAddressService == null) {
			return null;
		}
		
		DaoBase[] addressDaos = this.activityAddressService.doRetrieve(pActvId);
		
		if (ArrayUtils.isEmpty(addressDaos)) {
			return null;
		}
		ActivityAddressDTO[] addrDTOs = new ActivityAddressDTO[addressDaos.length];
		
		for (int i=0; i<addressDaos.length; ++i) {
			addrDTOs[i] = (ActivityAddressDTO)this.activityAddressService.convertToDto(addressDaos[i]);
		}
		return addrDTOs;
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

	public ServiceActionBase getDocumentDetailService() {
		return documentDetailService;
	}

	public void setDocumentDetailService(ServiceActionBase documentDetailService) {
		this.documentDetailService = documentDetailService;
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

	public ActivityStatusService getActivityStatusService() {
		return activityStatusService;
	}

	public void setActivityStatusService(ActivityStatusService activityStatusService) {
		this.activityStatusService = activityStatusService;
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
