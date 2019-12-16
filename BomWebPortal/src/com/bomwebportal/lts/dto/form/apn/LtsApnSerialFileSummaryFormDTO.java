package com.bomwebportal.lts.dto.form.apn;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.apn.LtsApnDnDTO;
import com.bomwebportal.lts.dto.apn.LtsApnFileDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileMainFormDTO.Action;


public class LtsApnSerialFileSummaryFormDTO implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7004512179105502824L;
	
	private LtsApnFileDTO apnFileSummary;
	private String totalNumOfRecords;
	private String numDnMatchedWithoutProblem;
	private String numDnMatchedWithProblem;
	private String numDnNotMatchedButNN;
	private String numIgnored;

	private Action formAction;

	public enum Action {
		RETURN, MATCH, PROBLEM, NOTMATCH, IGNORE, ALL;
	}
	
	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}

	public LtsApnFileDTO getApnFileSummary() {
		return apnFileSummary;
	}

	public void setApnFileSummary(LtsApnFileDTO apnFileSummary) {
		this.apnFileSummary = apnFileSummary;
	}
	public String getNumDnMatchedWithoutProblem() {
		return numDnMatchedWithoutProblem;
	}
	public void setNumDnMatchedWithoutProblem(String numDnMatchedWithoutProblem) {
		this.numDnMatchedWithoutProblem = numDnMatchedWithoutProblem;
	}
	public String getNumDnMatchedWithProblem() {
		return numDnMatchedWithProblem;
	}
	public void setNumDnMatchedWithProblem(String numDnMatchedWithProblem) {
		this.numDnMatchedWithProblem = numDnMatchedWithProblem;
	}
	public String getNumDnNotMatchedButNN() {
		return numDnNotMatchedButNN;
	}
	public void setNumDnNotMatchedButNN(String numDnNotMatchedButNN) {
		this.numDnNotMatchedButNN = numDnNotMatchedButNN;
	}
	public String getNumIgnored() {
		return numIgnored;
	}
	public void setNumIgnored(String numIgnored) {
		this.numIgnored = numIgnored;
	}
	public String getTotalNumOfRecords() {
		return totalNumOfRecords;
	}
	public void setTotalNumOfRecords(String totalNumOfRecords) {
		this.totalNumOfRecords = totalNumOfRecords;
	}
}
