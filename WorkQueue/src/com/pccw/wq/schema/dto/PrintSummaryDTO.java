package com.pccw.wq.schema.dto;

import java.io.Serializable;

public class PrintSummaryDTO implements Serializable, Comparable<PrintSummaryDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5801293483605125543L;

	private String wqSerial;
	private String wqType;
	private String wq;
	private String shopCode;
	private String totalPage;
	private String assignee;
	private String printStatus;
	private String printSummarySequence;
	private String srvType;
	private String srvNum;
	
	public String getWqSerial() {
		return wqSerial;
	}

	public void setWqSerial(String wqSerial) {
		this.wqSerial = wqSerial;
		this.printSummarySequence = this.wqType + this.wqSerial; 
	}

	public String getWqType() {
		return wqType;
	}

	public void setWqType(String wqType) {
		this.wqType = wqType;
		this.printSummarySequence = this.wqType + this.wqSerial;
	}

	public String getWq() {
		return wq;
	}

	public void setWq(String wq) {
		this.wq = wq;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
	}

	public String getPrintSummarySequence() {
		return printSummarySequence;
	}

	public void setPrintSummarySequence(String printSummarySequence) {
		this.printSummarySequence = printSummarySequence;
	}

	@Override
	public int compareTo(PrintSummaryDTO pPrintSummary) {
		return this.printSummarySequence.compareTo(
				pPrintSummary.getPrintSummarySequence());
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