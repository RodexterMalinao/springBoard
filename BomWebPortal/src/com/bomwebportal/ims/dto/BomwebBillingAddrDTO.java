package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class BomwebBillingAddrDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7405696800733830L;

	private String OrderId;
	private String AcctNo;
	private String Action;
	private String InstantUpdateInd;
	private String AddrLine1="";
	private String AddrLine2="";
	private String AddrLine3="";
	private String AddrLine4="";
	private String AddrLine5="";
	private String AddrLine6="";
	private String CreateBy;
	private Date CreateDate;
	private String LastUpdBy;
	private Date LastUpdDate;
	private String AddrDtl;
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getAcctNo() {
		return AcctNo;
	}
	public void setAcctNo(String acctNo) {
		AcctNo = acctNo;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public String getInstantUpdateInd() {
		return InstantUpdateInd;
	}
	public void setInstantUpdateInd(String instantUpdateInd) {
		InstantUpdateInd = instantUpdateInd;
	}
	public String getAddrLine1() {
		return AddrLine1;
	}
	public void setAddrLine1(String addrLine1) {
		AddrLine1 = addrLine1;
	}
	public String getAddrLine2() {
		return AddrLine2;
	}
	public void setAddrLine2(String addrLine2) {
		AddrLine2 = addrLine2;
	}
	public String getAddrLine3() {
		return AddrLine3;
	}
	public void setAddrLine3(String addrLine3) {
		AddrLine3 = addrLine3;
	}
	public String getAddrLine4() {
		return AddrLine4;
	}
	public void setAddrLine4(String addrLine4) {
		AddrLine4 = addrLine4;
	}
	public String getAddrLine5() {
		return AddrLine5;
	}
	public void setAddrLine5(String addrLine5) {
		AddrLine5 = addrLine5;
	}
	public String getAddrLine6() {
		return AddrLine6;
	}
	public void setAddrLine6(String addrLine6) {
		AddrLine6 = addrLine6;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getAddrDtl() {
		return AddrDtl;
	}
	public void setAddrDtl(String addrDtl) {
		AddrDtl = addrDtl;
	}
	public String getFullAddr() {
		String fullAddr="";
		if(this.getAddrLine1()!=null&&!"".equals(this.getAddrLine1())){
			fullAddr+=this.getAddrLine1();
		}
		if(this.getAddrLine2()!=null&&!"".equals(this.getAddrLine2())){
			fullAddr+=this.getAddrLine2();
		}
		if(this.getAddrLine3()!=null&&!"".equals(this.getAddrLine3())){
			fullAddr+=this.getAddrLine3();
		}
		if(this.getAddrLine4()!=null&&!"".equals(this.getAddrLine4())){
			fullAddr+=this.getAddrLine4();
		}
		if(this.getAddrLine5()!=null&&!"".equals(this.getAddrLine5())){
			fullAddr+=this.getAddrLine5();
		}
		if(this.getAddrLine6()!=null&&!"".equals(this.getAddrLine6())){
			fullAddr+=this.getAddrLine6();
		}
		return fullAddr;
	}


	
}
