package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class CustInfoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9192278175571442943L;
	
	private String custNo;
	private String title;
	private String firstName;
	private String lastName;
	
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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

}
