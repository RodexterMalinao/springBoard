package com.bomwebportal.dto;

public class WCreateItemDtlDTO {
	
	private String batchNo;
	private String type;
	private String contractPeriod;
	private String recurrentAmt;
	private String displayEng;
	private String displayChi;
	private String displaySSEng;
	private String displaySSChi;
	private String itemId;
	private String errCd;
	private String errMsg;
	private String createBy;
	private String createDate;
	private String lastUpdBy;
	private String lastUpdDate;
	private String itemDesc;
	//added by F.Chan 20141118
	private String oneTimeAmt;
	private String effStartDate;
	
	private String rebateAmt;
	private String rebateDisplayEng;
	private String rebateDisplayChi;
	private String checkOnlineInd;
	private String mipBrand;
	private String mipSimType;
	
	public String getRebateDisplayEng() {
		return rebateDisplayEng;
	}
	public void setRebateDisplayEng(String rebateDisplayEng) {
		this.rebateDisplayEng = rebateDisplayEng;
	}
	public String getRebateDisplayChi() {
		return rebateDisplayChi;
	}
	public void setRebateDisplayChi(String rebateDisplayChi) {
		this.rebateDisplayChi = rebateDisplayChi;
	}
	public String getOnlineInd() {
		return checkOnlineInd;
	}
	public void setOnlineInd(String checkOnlineInd) {
		this.checkOnlineInd = checkOnlineInd;
	}

	public String getRebateAmt() {
		return rebateAmt;
	}
	public void setRebateAmt(String rebateAmt) {
		this.rebateAmt = rebateAmt;
	}
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getOneTimeAmt() {
		return oneTimeAmt;
	}
	public void setOneTimeAmt(String oneTimeAmt) {
		this.oneTimeAmt = oneTimeAmt;
	}
	public String getRecurrentAmt() {
		return recurrentAmt;
	}
	public void setRecurrentAmt(String recurrentAmt) {
		this.recurrentAmt = recurrentAmt;
	}
	public String getDisplayEng() {
		return displayEng;
	}
	public void setDisplayEng(String displayEng) {
		this.displayEng = displayEng;
	}
	public String getDisplayChi() {
		return displayChi;
	}
	public void setDisplayChi(String displayChi) {
		this.displayChi = displayChi;
	}
	public String getDisplaySSEng() {
		return displaySSEng;
	}
	public void setDisplaySSEng(String displaySSEng) {
		this.displaySSEng = displaySSEng;
	}
	public String getDisplaySSChi() {
		return displaySSChi;
	}
	public void setDisplaySSChi(String displaySSChi) {
		this.displaySSChi = displaySSChi;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getErrCd() {
		return errCd;
	}
	public void setErrCd(String errCd) {
		this.errCd = errCd;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public String getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(String lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getEffStartDate() {
		return effStartDate;
	}
	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}
	public String getMipBrand() {
		return mipBrand;
	}
	public void setMipBrand(String mipBrand) {
		this.mipBrand = mipBrand;
	}
	public String getMipSimType() {
		return mipSimType;
	}
	public void setMipSimType(String mipSimType) {
		this.mipSimType = mipSimType;
	}
	
	

}
