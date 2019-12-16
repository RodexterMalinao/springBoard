package com.pccw.rpt.schema.dto.wq;

import java.io.Serializable;

public class BpDetailsRptDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3423658020805393835L;
	private String number;
	private String wqSerial;
	private String wqType;
	private String wq;
	private String srvType;
	private String srvNum;
	private String shopCode;
	private String totalPage;
	private String assignee;
	private String printStatus;
	
	public BpDetailsRptDTO(String pNumber, String pWqSerial, String pSrvType,
			String pSrvNum, String pWqType, String pWq, String pShopCode,
			String pTotalPage, String pAssignee, String pPrintStatus) {
		this.number = pNumber;
		this.wqSerial = pWqSerial;
		this.srvType = pSrvType;
		this.srvNum = pSrvNum;
		this.wqType = pWqType;
		this.wq = pWq;
		this.shopCode = pShopCode;
		this.totalPage = pTotalPage;
		this.assignee = pAssignee;
		this.printStatus = pPrintStatus;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String pNumber) {
		this.number = pNumber;
	}

	public String getWqSerial() {
		return this.wqSerial;
	}

	public void setWqSerial(String pWqSerial) {
		this.wqSerial = pWqSerial;
	}

	public String getWqType() {
		return this.wqType;
	}

	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	public String getWq() {
		return this.wq;
	}

	public void setWq(String pWq) {
		this.wq = pWq;
	}

	public String getShopCode() {
		return this.shopCode;
	}

	public void setShopCode(String pShopCode) {
		this.shopCode = pShopCode;
	}

	public String getTotalPage() {
		return this.totalPage;
	}

	public void setTotalPage(String pTotalPage) {
		this.totalPage = pTotalPage;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setAssignee(String pAssignee) {
		this.assignee = pAssignee;
	}

	public String getPrintStatus() {
		return this.printStatus;
	}

	public void setPrintStatus(String pPrintStatus) {
		this.printStatus = pPrintStatus;
	}

	public String getSrvType() {
		return this.srvType;
	}

	public void setSrvType(String pSrvType) {
		this.srvType = pSrvType;
	}

	public String getSrvNum() {
		return this.srvNum;
	}

	public void setSrvNum(String pSrvNum) {
		this.srvNum = pSrvNum;
	}
}
