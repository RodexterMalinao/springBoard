package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.List;

public class ValidationResultDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7967048137534715197L;
	
	public enum Status {
		VALID, INVAILD
	}
	
	private List<String> messageList;
	private Status status;
	private Object validateObject;
	
	public ValidationResultDTO () {
		super();
	}
	
	public ValidationResultDTO(Status status, List<String> messageList, Object validateObject) {
		this.status = status;
		this.messageList = messageList;
		this.validateObject = validateObject;
	}
	
	public List<String> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<String> messageList) {
		this.messageList = messageList;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Object getValidateObject() {
		return validateObject;
	}
	public void setValidateObject(Object validateObject) {
		this.validateObject = validateObject;
	}
	
	
	
}
