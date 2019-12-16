package com.bomwebportal.dto;

import java.io.Serializable;

public class ImsCustomerDTO implements Serializable {

	
	private static final long serialVersionUID = -672131444458078974L;
	private String lastName;
	private String firstName; 
	private String ID_DOC_NUM;
	private String ID_DOC_TYPE;
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getID_DOC_NUM() {
		return ID_DOC_NUM;
	}
	public void setID_DOC_NUM(String iD_DOC_NUM) {
		ID_DOC_NUM = iD_DOC_NUM;
	}
	public void setID_DOC_TYPE(String iD_DOC_TYPE) {
		ID_DOC_TYPE = iD_DOC_TYPE;
	}
	public String getID_DOC_TYPE() {
		return ID_DOC_TYPE;
	} 
	
	
	
	
}