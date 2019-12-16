package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class AccountServiceLtsDTO implements Serializable {

	private static final long serialVersionUID = 4964852191643676149L;

	private String acctType = null;
	private String acctNum = null;
	private String chargeType = null;
	private String custNum = null;
	private String acctName = null;
	private String effStartDate = null;
	private String effEndStart = null;

	
	public String getAcctType() {
		return acctType;
	}

	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}

	public String getAcctNum() {
		return acctNum;
	}

	public void setAcctNum(String acctNum) {
		this.acctNum = acctNum;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public String getEffStartDate() {
		return effStartDate;
	}

	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}

	public String getEffEndStart() {
		return effEndStart;
	}

	public void setEffEndStart(String effEndStart) {
		this.effEndStart = effEndStart;
	}
}
