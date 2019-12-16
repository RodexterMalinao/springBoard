package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class BmoDetailOutput implements Serializable {

	private static final long serialVersionUID = 7944821557088827359L;
	
	private BmoPermitDetail[] bmoDtls = null;
	private String returnValue = null;
	private String errorCd = null;
	private String errorMsg = null;

	
	public BmoPermitDetail[] getBmoDtls() {
		return this.bmoDtls;
	}

	public void setBmoDtls(BmoPermitDetail[] pBmoDtls) {
		this.bmoDtls = pBmoDtls;
	}

	public String getReturnValue() {
		return this.returnValue;
	}

	public void setReturnValue(String pReturnValue) {
		this.returnValue = pReturnValue;
	}

	public String getErrorCd() {
		return this.errorCd;
	}

	public void setErrorCd(String pErrorCd) {
		this.errorCd = pErrorCd;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public void setErrorMsg(String pErrorMsg) {
		this.errorMsg = pErrorMsg;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" returnValue = ");
		sb.append(this.returnValue);
		sb.append(" errorCd = ");
		sb.append(this.errorCd);
		sb.append(" errorMsg = ");
		sb.append(this.errorMsg);
		
		for (BmoPermitDetail bmoDtl : this.bmoDtls) {
			if (bmoDtl != null) {
				sb.append(bmoDtl.toString());
			}
		}
		return sb.toString();
	}
}
