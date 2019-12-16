package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO.BillMediaDtl;

public class LtsAcqBillingAddressFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -26624160700449803L;
	/**
	 * 
	 */
	List<BillingAddrDtl> billingAddrDtlList;
	
	public class BillingAddrDtl {
		private String acctNum;
		private boolean primaryAcct;
		private String acctBillingAddress;
		private String billingAddressTextArea;
		private String addrLine1;
		private String addrLine2;
		private String addrLine3;
		private String addrLine4;
		private String addrLine5;
		private String addrLine6;
		private String baCaAction;

		public String getAcctNum() {
			return acctNum;
		}
		public void setAcctNum(String acctNum) {
			this.acctNum = acctNum;
		}
		public String getAcctBillingAddress() {
			return acctBillingAddress;
		}
		public void setAcctBillingAddress(String acctBillingAddress) {
			this.acctBillingAddress = acctBillingAddress;
		}
		public String getBillingAddressTextArea() {
			return billingAddressTextArea;
		}
		public void setBillingAddressTextArea(String billingAddressTextArea) {
			this.billingAddressTextArea = billingAddressTextArea;
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
		public String getBaCaAction() {
			return baCaAction;
		}
		public void setBaCaAction(String baCaAction) {
			this.baCaAction = baCaAction;
		}
		public boolean isPrimaryAcct() {
			return primaryAcct;
		}
		public void setPrimaryAcct(boolean primaryAcct) {
			this.primaryAcct = primaryAcct;
		}

	}

	public List<BillingAddrDtl> getBillingAddrDtlList() {
		return billingAddrDtlList;
	}

	public void setBillingAddrDtlList(List<BillingAddrDtl> billingAddrDtlList) {
		this.billingAddrDtlList = billingAddrDtlList;
	}

	public BillingAddrDtl getPrimaryBillingAddrDtl(){
		if(billingAddrDtlList.size() == 1){
			return billingAddrDtlList.get(0);
		}
		
		for(BillingAddrDtl dtl: this.billingAddrDtlList){
			if (dtl.isPrimaryAcct()) return dtl; 
		}
		return null;
	}
	
	
}
