package com.pccw.wq.schema.dto;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang.ArrayUtils;

public class WorkQueueNatureDTO implements Serializable, Comparable<WorkQueueNatureDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5867349197641693815L;

	private String workQueueType;
	private String workQueueSubType;
	private String workQueueNatureId;
	private RemarkDTO[] remarks;


	public String getWorkQueueType() {
		return workQueueType;
	}

	public void setWorkQueueType(String pWorkQueueType) {
		this.workQueueType = pWorkQueueType;
	}

	public String getWorkQueueSubType() {
		return workQueueSubType;
	}

	public void setWorkQueueSubType(String pWorkQueueSubType) {
		this.workQueueSubType = pWorkQueueSubType;
	}

	public String getWorkQueueNatureId() {
		return workQueueNatureId;
	}

	public void setWorkQueueNatureId(String pWorkQueueNatureId) {
		this.workQueueNatureId = pWorkQueueNatureId;
	}

	public RemarkDTO[] getRemarks() {
		return remarks;
	}

	public void setRemarks(RemarkDTO[] pRemarks) {
		this.remarks = pRemarks;
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
		
	@Override
	public int compareTo(WorkQueueNatureDTO pWorkQueueNature) {
		return this.getCompareKey().compareTo(pWorkQueueNature.getCompareKey());
	}
	
	public String getCompareKey() {
		return this.workQueueType + "^" + this.workQueueSubType + "^" + this.workQueueNatureId;
	}
}
