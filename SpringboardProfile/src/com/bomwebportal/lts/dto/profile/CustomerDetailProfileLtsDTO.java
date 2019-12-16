package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class CustomerDetailProfileLtsDTO implements Serializable {

	private static final long serialVersionUID = -1154502281925504693L;
	
	private String custNum = null;
	private String docNum = null;
	private String docType = null;
	private String firstName = null;
	private String lastName = null;
	private String dob = null;
	private String companyName = null;
	private String parentCustNum = null;
	private boolean idVerifyInd = false;
	private String wipInd = null;
	private boolean blacklistCustInd = false;
	private String title = null;
	private String wipMessage = null;
	private String custCatg = null;
	private boolean specialHandle = false;
	private String premierType = null;
	private String paperBill = null;
	private CustomerRemarkProfileLtsDTO[] custRemarks = null;
	private CustomerRemarkProfileLtsDTO[] custSpecialRemarks = null;

	
	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	public String getDocNum() {
		return docNum;
	}

	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getParentCustNum() {
		return parentCustNum;
	}

	public void setParentCustNum(String parentCustNum) {
		this.parentCustNum = parentCustNum;
	}

	public boolean isIdVerifyInd() {
		return idVerifyInd;
	}

	public void setIdVerifyInd(boolean idVerifyInd) {
		this.idVerifyInd = idVerifyInd;
	}

	public String getWipInd() {
		return wipInd;
	}

	public void setWipInd(String wipInd) {
		this.wipInd = wipInd;
	}

	public boolean isBlacklistCustInd() {
		return blacklistCustInd;
	}

	public void setBlacklistCustInd(boolean blacklistCustInd) {
		this.blacklistCustInd = blacklistCustInd;
	}

	public CustomerRemarkProfileLtsDTO[] getCustRemarks() {
		return custRemarks;
	}

	public void setCustRemarks(CustomerRemarkProfileLtsDTO[] custRemarks) {
		this.custRemarks = custRemarks;
	}
	
	public String getCustRemarkContent() {
		return getRemarkConent(this.custRemarks);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getWipMessage() {
		return wipMessage;
	}

	public void setWipMessage(String wipMessage) {
		this.wipMessage = wipMessage;
	}

	public CustomerRemarkProfileLtsDTO[] getCustSpecialRemarks() {
		return custSpecialRemarks;
	}

	public void setCustSpecialRemarks(
			CustomerRemarkProfileLtsDTO[] custSpecialRemarks) {
		this.custSpecialRemarks = custSpecialRemarks;
	}
	
	public String getCustSpecialRemarkContent() {
		return getRemarkConent(this.custSpecialRemarks);
	}
	
	public String getCustCatg() {
		return custCatg;
	}

	public void setCustCatg(String custCatg) {
		this.custCatg = custCatg;
	}

	public boolean isSpecialHandle() {
		return specialHandle;
	}

	public void setSpecialHandle(boolean specialHandle) {
		this.specialHandle = specialHandle;
	}

	public String getPremierType() {
		return premierType;
	}

	public void setPremierType(String premierType) {
		this.premierType = premierType;
	}

	public String getPaperBill() {
		return paperBill;
	}

	public void setPaperBill(String paperBill) {
		this.paperBill = paperBill;
	}

	private String getRemarkConent(CustomerRemarkProfileLtsDTO[] pRemarks) {
		
		if (pRemarks == null || pRemarks.length == 0) {
			return null;
		}
		StringBuilder remarkSb = new StringBuilder();
		
		for (int i=0; i<pRemarks.length; ++i) {
			remarkSb.append(pRemarks[i].getRemarks());
		}
		return remarkSb.toString();
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
}
