package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.order.AllOrdDocDTO;
import com.bomwebportal.lts.dto.order.AmendOrderRecDTO;

public class OrderAmendmentFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1285396745297497445L;
	
	private String sbOrderNum;
	private String bomOcId;
	private int outstandingWqCount;
	private List<AllOrdDocDTO> amendFormList;
	
	private AmendOrderRecDTO[] amendHistory;
		
	public String getSbOrderNum() {
		return sbOrderNum;
	}
	public void setSbOrderNum(String sbOrderNum) {
		this.sbOrderNum = sbOrderNum;
	}
	public String getBomOcId() {
		return bomOcId;
	}
	public void setBomOcId(String bomOcId) {
		this.bomOcId = bomOcId;
	}
	public AmendOrderRecDTO[] getAmendHistory() {
		return this.amendHistory;
	}
	public void setAmendHistory(AmendOrderRecDTO[] pAmendHistory) {
		this.amendHistory = pAmendHistory;
	}
	public int getOutstandingWqCount() {
		return this.outstandingWqCount;
	}
	public void setOutstandingWqCount(int pOutstandingWqCount) {
		this.outstandingWqCount = pOutstandingWqCount;
	}
	public List<AllOrdDocDTO> getAmendFormList() {
		return amendFormList;
	}
	public void setAmendFormList(List<AllOrdDocDTO> amendFormList) {
		this.amendFormList = amendFormList;
	}

}
