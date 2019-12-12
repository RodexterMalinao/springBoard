package com.pccw.wq.schema.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;

public class WorkQueueAssignedWorkPartyDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5848894307330823934L;
	private String wqWpAssgnId;
	private String workQueueType;
	private String workQueueSubType;
	private String workQueueSerial;
	private String wqBatchId;
	private String workPartyId;
	private String receiveDate;
	private WorkQueueAssgnStatusDTO status;
	private WorkQueueNatureDTO[] assignedWorkQueueNatures;

	public String getWqWpAssgnId() {
		return wqWpAssgnId;
	}

	public void setWqWpAssgnId(String wqWpAssgnId) {
		this.wqWpAssgnId = wqWpAssgnId;
	}

	public String getWorkQueueType() {
		return workQueueType;
	}

	public void setWorkQueueType(String workQueueType) {
		this.workQueueType = workQueueType;
	}

	public String getWorkQueueSubType() {
		return workQueueSubType;
	}

	public void setWorkQueueSubType(String workQueueSubType) {
		this.workQueueSubType = workQueueSubType;
	}

	public String getWorkQueueSerial() {
		return workQueueSerial;
	}

	public void setWorkQueueSerial(String workQueueSerial) {
		this.workQueueSerial = workQueueSerial;
	}

	public String getWqBatchId() {
		return wqBatchId;
	}

	public void setWqBatchId(String wqBatchId) {
		this.wqBatchId = wqBatchId;
	}

	public String getWorkPartyId() {
		return workPartyId;
	}

	public void setWorkPartyId(String workPartyId) {
		this.workPartyId = workPartyId;
	}

	public String getReceiveDate() {
		return receiveDate;
	}

	public void setReceiveDate(String receiveDate) {
		this.receiveDate = receiveDate;
	}

	public WorkQueueNatureDTO[] getAssignedWorkQueueNatures() {
		return assignedWorkQueueNatures;
	}

	public void setAssignedWorkQueueNatures(
			WorkQueueNatureDTO[] assignedWorkQueueNatures) {
		this.assignedWorkQueueNatures = assignedWorkQueueNatures;
	}

	private Set<WorkQueueNatureDTO> getAssignedWorkQueueNatureSet() {
		TreeSet<WorkQueueNatureDTO> rtnSet = new TreeSet<WorkQueueNatureDTO>();
		if (!ArrayUtils.isEmpty(this.assignedWorkQueueNatures)) {
			rtnSet.addAll(Arrays.asList(this.assignedWorkQueueNatures));
		}
		return rtnSet;
	}
	
	private void updateAssignedWorkQueueNature(Set<WorkQueueNatureDTO> pAssignedWorkQueueNatureSet) {
		this.assignedWorkQueueNatures = pAssignedWorkQueueNatureSet.toArray(new WorkQueueNatureDTO[0]);
	}
	
	public void addAssignedWorkQueueNature(WorkQueueNatureDTO pWorkQueueNature) {
		Set<WorkQueueNatureDTO> wqNatureSet = this.getAssignedWorkQueueNatureSet();
		wqNatureSet.add(pWorkQueueNature);
		this.updateAssignedWorkQueueNature(wqNatureSet);
	}
	
	public void removeAssignedWorkQueueNature(WorkQueueNatureDTO pWorkQueueNature) {
		Set<WorkQueueNatureDTO> wqNatureSet = this.getAssignedWorkQueueNatureSet();
		wqNatureSet.remove(pWorkQueueNature);
		this.updateAssignedWorkQueueNature(wqNatureSet);
	}

	public WorkQueueAssgnStatusDTO getStatus() {
		return status;
	}

	public void setStatus(WorkQueueAssgnStatusDTO pStatus) {
		status = pStatus;
	}
}