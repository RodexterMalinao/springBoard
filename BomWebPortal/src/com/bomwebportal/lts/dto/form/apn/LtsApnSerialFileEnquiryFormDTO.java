package com.bomwebportal.lts.dto.form.apn;

import java.io.File;
import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.apn.LtsApnFileDTO;
import com.bomwebportal.lts.dto.form.apn.LtsApnSerialFileMainFormDTO.Action;
import com.bomwebportal.lts.util.LtsConstant;


public class LtsApnSerialFileEnquiryFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5369155684025747615L;

	private Action formAction;
	private String searchFileDnDtlList;
	private String searchUploadDateFrom;
	private String searchUploadDateTo;
	private String searchByStatus;
	private boolean searchPerformed;
	private String totalNumOfResults;	
	private List<LtsApnFileDTO> searchResult;
	
	public enum Action {
		SEARCH, UPDATE, RETURN, MATCH, PROBLEM, NOTMATCH, IGNORE, ALL;
	}
	
	public Action getFormAction() {
		return formAction;
	}
	public void setFormAction(Action formAction) {
		this.formAction = formAction;
	}
	public String getSearchUploadDateFrom() {
		return searchUploadDateFrom;
	}
	public void setSearchUploadDateFrom(String searchUploadDateFrom) {
		this.searchUploadDateFrom = searchUploadDateFrom;
	}
	public String getSearchUploadDateTo() {
		return searchUploadDateTo;
	}
	public void setSearchUploadDateTo(String searchUploadDateTo) {
		this.searchUploadDateTo = searchUploadDateTo;
	}
	public String getSearchByStatus() {
		return searchByStatus;
	}
	public void setSearchByStatus(String searchByStatus) {
		this.searchByStatus = searchByStatus;
	}
	public List<LtsApnFileDTO> getSearchResult() {
		return searchResult;
	}
	public void setSearchResult(List<LtsApnFileDTO> searchResult) {
		this.searchResult = searchResult;
	}
	public boolean isSearchPerformed() {
		return searchPerformed;
	}
	public void setSearchPerformed(boolean searchPerformed) {
		this.searchPerformed = searchPerformed;
	}
	public String getTotalNumOfResults() {
		return totalNumOfResults;
	}
	public void setTotalNumOfResults(String totalNumOfResults) {
		this.totalNumOfResults = totalNumOfResults;
	}
	public String getSearchFileDnDtlList() {
		return searchFileDnDtlList;
	}
	public void setSearchFileDnDtlList(String searchFileDnDtlList) {
		this.searchFileDnDtlList = searchFileDnDtlList;
	}	
}
