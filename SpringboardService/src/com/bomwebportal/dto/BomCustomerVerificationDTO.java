package com.bomwebportal.dto;

public class BomCustomerVerificationDTO implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1403189457511187982L;
	
	private String bomCustNum;
	private String bomIdDocType;
	private String bomIdDocNum;
	private String bomFirstName;
	private String bomLastName;
	private String bomSystemId;
	private boolean containLtsProfile = false;
	
	private String inputIdDocType;
	private String inputIdDocNum;
	private String inputFirstName;
	private String inputLastName;
	
	private String ltsCustNum;
	
	private String alertMessage;
	private boolean matchWithBomName;

	public String getBomIdDocType() {
		return bomIdDocType;
	}

	public void setBomIdDocType(String bomIdDocType) {
		this.bomIdDocType = bomIdDocType;
	}

	public String getBomIdDocNum() {
		return bomIdDocNum;
	}

	public void setBomIdDocNum(String bomIdDocNum) {
		this.bomIdDocNum = bomIdDocNum;
	}

	public String getBomFirstName() {
		return bomFirstName;
	}

	public void setBomFirstName(String bomFirstName) {
		this.bomFirstName = bomFirstName;
	}

	public String getBomLastName() {
		return bomLastName;
	}

	public void setBomLastName(String bomLastName) {
		this.bomLastName = bomLastName;
	}

	public String getInputIdDocType() {
		return inputIdDocType;
	}

	public void setInputIdDocType(String inputIdDocType) {
		this.inputIdDocType = inputIdDocType;
	}

	public String getInputIdDocNum() {
		return inputIdDocNum;
	}

	public void setInputIdDocNum(String inputIdDocNum) {
		this.inputIdDocNum = inputIdDocNum;
	}

	public String getInputFirstName() {
		return inputFirstName;
	}

	public void setInputFirstName(String inputFirstName) {
		this.inputFirstName = inputFirstName;
	}

	public String getInputLastName() {
		return inputLastName;
	}

	public void setInputLastName(String inputLastName) {
		this.inputLastName = inputLastName;
	}

	public boolean isMatchWithBomName() {
		return matchWithBomName;
	}

	public void setMatchWithBomName(boolean matchWithBomName) {
		this.matchWithBomName = matchWithBomName;
	}

	public String getBomCustNum() {
		return bomCustNum;
	}

	public void setBomCustNum(String bomCustNum) {
		this.bomCustNum = bomCustNum;
	}

	public String getAlertMessage() {
		return alertMessage;
	}

	public void setAlertMessage(String alertMessage) {
		this.alertMessage = alertMessage;
	}

	public String getBomSystemId() {
		return bomSystemId;
	}

	public void setBomSystemId(String bomSystemId) {
		this.bomSystemId = bomSystemId;
	}

	public boolean isContainLtsProfile() {
		return containLtsProfile;
	}

	public void setContainLtsProfile(boolean containLtsProfile) {
		this.containLtsProfile = containLtsProfile;
	}

	public String getLtsCustNum() {
		return ltsCustNum;
	}

	public void setLtsCustNum(String ltsCustNum) {
		this.ltsCustNum = ltsCustNum;
	}	
	
}
