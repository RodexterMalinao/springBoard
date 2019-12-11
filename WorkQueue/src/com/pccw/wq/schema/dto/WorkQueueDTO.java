package com.pccw.wq.schema.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;

public class WorkQueueDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5414409840571312072L;

	private String wqId;
	private String sbId;
	private String sbDtlId;
	private String sbShopCode;
	private String typeOfService;
	private String serviceId;
	private String relatedSrvType;
	private String relatedSrvNum;
	private String srd;
	private String bomOcId;
	private String bomDtlId;
	private String bomDtlSeq;
	private String bomStatus;
	private String bomLegacyOrderStatus;
	private String sbActvId;
	private String url;
	private RemarkDTO[] remarks;
	private WorkQueueNatureDTO[] workQueueNatures;
	private WorkQueueAssignedWorkPartyDTO[] assignedWorkParties;
	private WorkQueueDocumentDTO[] documents;
	private WorkQueueAttributeDTO[] attributes;
	private String createdBatchId;
	private String[] createdWpWqAssignIds;
	
	public String getWqId() {
		return this.wqId;
	}

	public void setWqId(String pWqId) {
		this.wqId = pWqId;
	}

	public String getSbId() {
		return sbId;
	}

	public void setSbId(String pSbId) {
		sbId = pSbId;
	}

	public String getSbDtlId() {
		return sbDtlId;
	}

	public void setSbDtlId(String pSbDtlId) {
		sbDtlId = pSbDtlId;
	}

	public String getSbShopCode() {
		return sbShopCode;
	}

	public void setSbShopCode(String pSbShopCode) {
		sbShopCode = pSbShopCode;
	}

	public String getTypeOfService() {
		return typeOfService;
	}

	public void setTypeOfService(String pTypeOfService) {
		typeOfService = pTypeOfService;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String pServiceId) {
		serviceId = pServiceId;
	}

	public String getSrd() {
		return srd;
	}

	public void setSrd(String pSrd) {
		srd = pSrd;
	}

	public String getBomOcId() {
		return bomOcId;
	}

	public void setBomOcId(String pBomOcId) {
		bomOcId = pBomOcId;
	}

	public String getBomDtlId() {
		return bomDtlId;
	}

	public void setBomDtlId(String pBomDtlId) {
		bomDtlId = pBomDtlId;
	}

	public String getBomDtlSeq() {
		return bomDtlSeq;
	}

	public void setBomDtlSeq(String pBomDtlSeq) {
		bomDtlSeq = pBomDtlSeq;
	}

	public String getBomStatus() {
		return this.bomStatus;
	}

	public void setBomStatus(String pBomStatus) {
		this.bomStatus = pBomStatus;
	}

	public String getBomLegacyOrderStatus() {
		return bomLegacyOrderStatus;
	}

	public void setBomLegacyOrderStatus(String pBomLegacyOrderStatus) {
		bomLegacyOrderStatus = pBomLegacyOrderStatus;
	}

	public String getRelatedSrvType() {
		return this.relatedSrvType;
	}

	public void setRelatedSrvType(String pRelatedSrvType) {
		this.relatedSrvType = pRelatedSrvType;
	}

	public String getRelatedSrvNum() {
		return this.relatedSrvNum;
	}

	public void setRelatedSrvNum(String pRelatedSrvNum) {
		this.relatedSrvNum = pRelatedSrvNum;
	}

	public RemarkDTO[] getRemarks() {
		return remarks;
	}

	public void setRemarks(RemarkDTO[] pRemarks) {
		remarks = pRemarks;
	}
	
	public String getSbActvId() {
		return this.sbActvId;
	}

	public void setSbActvId(String pSbActvId) {
		this.sbActvId = pSbActvId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String pUrl) {
		this.url = pUrl;
	}

	private Set<RemarkDTO> getRemarksSet() {
		TreeSet<RemarkDTO> rtnSet = new TreeSet<RemarkDTO>();
		if (!ArrayUtils.isEmpty(this.remarks)) {
			rtnSet.addAll(Arrays.asList(this.remarks));
		}
		return rtnSet;
	}
	
	private void updateRemarks(Set<RemarkDTO> pRemarksSet) {
		this.remarks = pRemarksSet.toArray(new RemarkDTO[0]);
	}
	
	public void addRemark(RemarkDTO pRemark) {
		Set<RemarkDTO> remarksSet = this.getRemarksSet();
		remarksSet.add(pRemark);
		this.updateRemarks(remarksSet);
	}
	
	public void removeRemark(RemarkDTO pRemark) {
		Set<RemarkDTO> remarksSet = this.getRemarksSet();
		remarksSet.remove(pRemark);
		this.updateRemarks(remarksSet);
	}
	
	public WorkQueueNatureDTO[] getWorkQueueNatures() {
		return workQueueNatures;
	}

	public void setWorkQueueNatures(WorkQueueNatureDTO[] pWorkQueueNatures) {
		workQueueNatures = pWorkQueueNatures;
	}

	private Set<WorkQueueNatureDTO> getWorkQueueNatureSet() {
		TreeSet<WorkQueueNatureDTO> rtnSet = new TreeSet<WorkQueueNatureDTO>();
		if (!ArrayUtils.isEmpty(this.workQueueNatures)) {
			rtnSet.addAll(Arrays.asList(this.workQueueNatures));
		}
		return rtnSet;
	}
	
	private void updateWorkQueueNature(Set<WorkQueueNatureDTO> pWorkQueueNatureSet) {
		this.workQueueNatures = pWorkQueueNatureSet.toArray(new WorkQueueNatureDTO[0]);
	}
	
	public void addWorkQueueNature(WorkQueueNatureDTO pWorkQueueNature) {
		Set<WorkQueueNatureDTO> wqNatureSet = this.getWorkQueueNatureSet();
		wqNatureSet.add(pWorkQueueNature);
		this.updateWorkQueueNature(wqNatureSet);
	}
	
	public void removeWorkQueueNature(WorkQueueNatureDTO pWorkQueueNature) {
		Set<WorkQueueNatureDTO> wqNatureSet = this.getWorkQueueNatureSet();
		wqNatureSet.remove(pWorkQueueNature);
		this.updateWorkQueueNature(wqNatureSet);
	}
	
	public WorkQueueAssignedWorkPartyDTO[] getAssignedWorkParties() {
		return assignedWorkParties;
	}

	public void setAssignedWorkParties(
			WorkQueueAssignedWorkPartyDTO[] pAssignedWorkParty) {
		assignedWorkParties = pAssignedWorkParty;
	}
	
	private Set<WorkQueueAssignedWorkPartyDTO> getAssignedWorkPartiesSet() {
		TreeSet<WorkQueueAssignedWorkPartyDTO> rtnSet = new TreeSet<WorkQueueAssignedWorkPartyDTO>();
		if (!ArrayUtils.isEmpty(this.assignedWorkParties)) {
			rtnSet.addAll(Arrays.asList(this.assignedWorkParties));
		}
		return rtnSet;
	}
	
	private void updateAssignedWorkParties(Set<WorkQueueAssignedWorkPartyDTO> pWorkQueueAssignedWorkParty) {
		this.assignedWorkParties = pWorkQueueAssignedWorkParty.toArray(new WorkQueueAssignedWorkPartyDTO[0]);
	}
	
	public void addAssignedWorkParty(WorkQueueAssignedWorkPartyDTO pWorkQueueNature) {
		Set<WorkQueueAssignedWorkPartyDTO> wqWorkPartySet = this.getAssignedWorkPartiesSet();
		wqWorkPartySet.add(pWorkQueueNature);
		this.updateAssignedWorkParties(wqWorkPartySet);
	}
	
	public void removeAssignedWorkParty(WorkQueueAssignedWorkPartyDTO pWorkQueueNature) {
		Set<WorkQueueAssignedWorkPartyDTO> wqWorkPartySet = this.getAssignedWorkPartiesSet();
		wqWorkPartySet.remove(pWorkQueueNature);
		this.updateAssignedWorkParties(wqWorkPartySet);
	}
	
	public WorkQueueDocumentDTO[] getDocuments() {
		return documents;
	}

	public void setDocuments(WorkQueueDocumentDTO[] pDocuments) {
		this.documents = pDocuments;
	}

	private Set<WorkQueueDocumentDTO> getDocumentsSet() {
		TreeSet<WorkQueueDocumentDTO> rtnSet = new TreeSet<WorkQueueDocumentDTO>();
		if (!ArrayUtils.isEmpty(this.documents)) {
			rtnSet.addAll(Arrays.asList(this.documents));
		}
		return rtnSet;
	}
	
	private void updateDocuments(Set<WorkQueueDocumentDTO> pDocumentsSet) {
		this.documents = pDocumentsSet.toArray(new WorkQueueDocumentDTO[0]);
	}
	
	public void addDocument(WorkQueueDocumentDTO pWorkQueueDocument) {
		Set<WorkQueueDocumentDTO> documentsSet = this.getDocumentsSet();
		documentsSet.add(pWorkQueueDocument);
		this.updateDocuments(documentsSet);
	}

	public void removeDocument(WorkQueueDocumentDTO pWorkQueueDocument) {
		Set<WorkQueueDocumentDTO> documentsSet = this.getDocumentsSet();
		documentsSet.remove(pWorkQueueDocument);
		this.updateDocuments(documentsSet);
	}

	public String getCreatedBatchId() {
		return createdBatchId;
	}

	public void setCreatedBatchId(String createdBatchId) {
		this.createdBatchId = createdBatchId;
	}

	public String[] getCreatedWpWqAssignIds() {
		return this.createdWpWqAssignIds;
	}

	public void setCreatedWpWqAssignIds(String[] pCreatedWpWqAssignIds) {
		this.createdWpWqAssignIds = pCreatedWpWqAssignIds;
	}

	public WorkQueueAttributeDTO[] getAttributes() {
		return this.attributes;
	}

	public void setAttributes(WorkQueueAttributeDTO[] pAttributes) {
		this.attributes = pAttributes;
	}
	
	private Set<WorkQueueAttributeDTO> getAttributesSet() {
		TreeSet<WorkQueueAttributeDTO> rtnSet = new TreeSet<WorkQueueAttributeDTO>();
		if (!ArrayUtils.isEmpty(this.attributes)) {
			rtnSet.addAll(Arrays.asList(this.attributes));
		}
		return rtnSet;
	}
	
	private void updateAttributes(Set<WorkQueueAttributeDTO> pAttbSet) {
		this.attributes = pAttbSet.toArray(new WorkQueueAttributeDTO[0]);
	}
	
	public void addAttribute(WorkQueueAttributeDTO pAttribute) {
		Set<WorkQueueAttributeDTO> attbSet = this.getAttributesSet();
		attbSet.add(pAttribute);
		this.updateAttributes(attbSet);
	}

	public void removeAttribute(WorkQueueAttributeDTO pAttribute) {
		Set<WorkQueueAttributeDTO> attbSet = this.getAttributesSet();
		attbSet.remove(pAttribute);
		this.updateAttributes(attbSet);
	}
}
