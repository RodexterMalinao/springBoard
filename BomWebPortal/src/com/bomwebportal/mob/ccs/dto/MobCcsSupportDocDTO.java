package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class MobCcsSupportDocDTO implements Serializable {
	private String docId;
	private String docValue;
	private String docDesc;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private boolean mandatory;
	private boolean faxMandatory;
	private boolean onsiteMandatory;   //20130827
	private boolean receivedByFax;
	// The session one
	private String faxServerSerialNo;
	// The database one
	private String storedFaxServerSerialNo;
	private String verified;
	private String verifyResult;
	private boolean removable;
	private boolean recalled;

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocValue() {
		return docValue;
	}

	public void setDocValue(String docValue) {
		this.docValue = docValue;
	}

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public boolean isMandatory() {
		return mandatory;
	}

	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;

		if (mandatory && faxMandatory) {
			setReceivedByFax(true);
		} else if (!removable) {
			setReceivedByFax(false);
		}
		updateVerifyResult();
	}

	public void updateVerifyResult() {
		if (!mandatory) {
			verifyResult = "N/A";
		} else {
			if (verified == null
					|| (verified != null && verified.trim().isEmpty())) {
				if (mandatory) {
					verifyResult = "Pending";
				} else {
					verifyResult = "N/A";
				}

			} else if ("Y".equalsIgnoreCase(verified)) {
				verifyResult = "Pass";
			} else if ("N".equalsIgnoreCase(verified)) {
				verifyResult = "Fail";
			} else {
				verifyResult = "Error";
			}
		}
	}

	public boolean isFaxMandatory() {
		return faxMandatory;
	}

	public void setFaxMandatory(boolean faxMandatory) {
		this.faxMandatory = faxMandatory;
	}
	
	public boolean isOnsiteMandatory() {
		return onsiteMandatory;
	}

	public void setOnsiteMandatory(boolean onsiteMandatory) {
		this.onsiteMandatory = onsiteMandatory;
	}

	public boolean isReceivedByFax() {
		return receivedByFax;
	}

	public void setReceivedByFax(boolean receivedByFax) {
		this.receivedByFax = receivedByFax;
	}

	public String getFaxServerSerialNo() {
		return faxServerSerialNo;
	}

	public void setFaxServerSerialNo(String faxServerSerialNo) {
		this.faxServerSerialNo = faxServerSerialNo;
	}

	public String getStoredFaxServerSerialNo() {
		return storedFaxServerSerialNo;
	}

	// This variable is used to compare the different between session and
	// database
	// record. It should only be called when data was retrieved from database
	public void setStoredFaxServerSerialNo(String storedFaxServerSerialNo) {
		this.storedFaxServerSerialNo = storedFaxServerSerialNo;
	}

	public String getVerified() {
		return verified;
	}

	public void setVerified(String verified) {
		this.verified = verified;
		updateVerifyResult();
	}

	public String getVerifyResult() {
		return verifyResult;
	}

	public void setVerifyResult(String verifyResult) {
		this.verifyResult = verifyResult;
	}

	public boolean isRemovable() {
		return removable;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

	public boolean isRecalled() {
		return recalled;
	}

	public void setRecalled(boolean recalled) {
		this.recalled = recalled;
	}

}
