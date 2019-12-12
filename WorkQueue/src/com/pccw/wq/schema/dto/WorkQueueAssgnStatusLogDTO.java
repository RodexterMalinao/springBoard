/*
 * Created on Nov 1, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.schema.dto;

import java.io.Serializable;

public class WorkQueueAssgnStatusLogDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1914969039881116619L;
				
	private String statusCd;
	private String reasonCd;
	private String assignee;
	private String statusDateTime;
	private String createBy;
	
	public String getStatusCd() {
		return statusCd;
	}
	
	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}
	
	public String getReasonCd() {
		return reasonCd;
	}
	
	public void setReasonCd(String pReasonCd) {
		this.reasonCd = pReasonCd;
	}
	
	public String getAssignee() {
		return assignee;
	}
	
	public void setAssignee(String pAssignee) {
		assignee = pAssignee;
	}

	public String getStatusDateTime() {
		return statusDateTime;
	}

	public void setStatusDateTime(String statusDateTime) {
		this.statusDateTime = statusDateTime;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}
}
