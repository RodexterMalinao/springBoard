package com.bomwebportal.lts.dto.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class BillingAddressLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = 6104685411518960336L;

	private String action = null;
	private String instantUpdateInd = null;
	private String addrLine1 = null;
	private String addrLine2 = null;
	private String addrLine3 = null;
	private String addrLine4 = null;
	private String addrLine5 = null;
	private String addrLine6 = null;
	private String acctNo = null;
	

	public String getAcctNo() {
		return acctNo;
	}

	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getInstantUpdateInd() {
		return instantUpdateInd;
	}

	public void setInstantUpdateInd(String instantUpdateInd) {
		this.instantUpdateInd = instantUpdateInd;
	}

	public String getAddrLine1() {
		return addrLine1;
	}

	public void setAddrLine1(String addrLine1) {
		this.addrLine1 = addrLine1;
	}

	public String getAddrLine2() {
		return addrLine2;
	}

	public void setAddrLine2(String addrLine2) {
		this.addrLine2 = addrLine2;
	}

	public String getAddrLine3() {
		return addrLine3;
	}

	public void setAddrLine3(String addrLine3) {
		this.addrLine3 = addrLine3;
	}

	public String getAddrLine4() {
		return addrLine4;
	}

	public void setAddrLine4(String addrLine4) {
		this.addrLine4 = addrLine4;
	}

	public String getAddrLine5() {
		return addrLine5;
	}

	public void setAddrLine5(String addrLine5) {
		this.addrLine5 = addrLine5;
	}

	public String getAddrLine6() {
		return addrLine6;
	}

	public void setAddrLine6(String addrLine6) {
		this.addrLine6 = addrLine6;
	}

	public String[] getAddrLines() {
		if(StringUtils.isNotBlank(addrLine1)){
			List<String> addrLines = new ArrayList<String>(); 
			addrLines.add(addrLine1);
			if(StringUtils.isNotBlank(addrLine2)){
				addrLines.add(addrLine2);
			}
			if(StringUtils.isNotBlank(addrLine3)){
				addrLines.add(addrLine3);
			}
			if(StringUtils.isNotBlank(addrLine4)){
				addrLines.add(addrLine4);
			}
			if(StringUtils.isNotBlank(addrLine5)){
				addrLines.add(addrLine5);
			}
			if(StringUtils.isNotBlank(addrLine6)){
				addrLines.add(addrLine6);
			}
			return addrLines.toArray(new String[0]);
		}

		return null;
	}
	
	public String getFullBillAddr() {
		return StringUtils.defaultIfEmpty(addrLine1, "")
				+ StringUtils.defaultIfEmpty(addrLine2, "")
				+ StringUtils.defaultIfEmpty(addrLine3, "")
				+ StringUtils.defaultIfEmpty(addrLine4, "")
				+ StringUtils.defaultIfEmpty(addrLine5, "")
				+ StringUtils.defaultIfEmpty(addrLine6, "");
	}
}
