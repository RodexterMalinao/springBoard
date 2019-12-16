package com.bomwebportal.ims.dto.ui;


import java.util.List;

import com.bomwebportal.dto.OnlineSalesRequestDTO;
import com.bomwebportal.dto.DisplayPreRegDto;

public class PreRegistrationUI{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1171494807542250381L;
	
	
	private String docType;
	private String docNum;
	private String firstName;
	private String lastName;
	
	
	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	private String bandwidth;
	private String instAddr;
	private String serbdynoAddr;
	private String phHosInd;
	private OnlineSalesRequestDTO onlineSalesReq;
	private List<DisplayPreRegDto> preRegSearchList;
	private Action action;
	private String searched;
	private String submitted;
	
	
	
	public static enum Action {
		SEARCH
		, SUBMIT
	}
	;

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}


	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}

	public String getBandwidth() {
		return bandwidth;
	}

	public void setInstAddr(String instAddr) {
		this.instAddr = instAddr;
	}

	public String getInstAddr() {
		return instAddr;
	}

	public void setPhHosInd(String phHosInd) {
		this.phHosInd = phHosInd;
	}

	public String getPhHosInd() {
		return phHosInd;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getDocType() {
		return docType;
	}

	public void setSearched(String searched) {
		this.searched = searched;
	}

	public String getSearched() {
		return searched;
	}

	public void setSubmitted(String submitted) {
		this.submitted = submitted;
	}

	public String getSubmitted() {
		return submitted;
	}

	public void setPreRegSearchList(List<DisplayPreRegDto> preRegSearchList) {
		this.preRegSearchList = preRegSearchList;
	}

	public List<DisplayPreRegDto> getPreRegSearchList() {
		return preRegSearchList;
	}

	public void setOnlineSalesReq(OnlineSalesRequestDTO onlineSalesReq) {
		this.onlineSalesReq = onlineSalesReq;
	}

	public OnlineSalesRequestDTO getOnlineSalesReq() {
		return onlineSalesReq;
	}

	public void setSerbdynoAddr(String serbdynoAddr) {
		this.serbdynoAddr = serbdynoAddr;
	}

	public String getSerbdynoAddr() {
		return serbdynoAddr;
	}



}
