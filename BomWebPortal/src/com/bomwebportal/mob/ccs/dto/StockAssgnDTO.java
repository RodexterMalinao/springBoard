package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.List;

public class StockAssgnDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7451056986576889179L;

	private String orderId;
	private String itemId;
	private String type;
	private String itemCode;
	private String itemSerial;
	private List<String> itemSerialList;
	private String salesMemoNum;
	private String salesMemoNum2;
	private String salesMemoInd;
	private String aoInd;
	private String itemType;
	private String itemDesc;
	private String statusId;
	private String applyDate;
	private String createBy;
	private String createDate;
	private String lastUpdBy;
	private String lastUpdDate;
	private String errorMsg;
	private String aoReportSent;
	private String memberNum;
	private String eagleValidate;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemSerial() {
		return itemSerial;
	}
	public void setItemSerial(String itemSerial) {
		this.itemSerial = itemSerial;
	}
	public List<String> getItemSerialList(){
		return itemSerialList;
	}
	public void setItemSerialList(List<String> itemSerialList) {
		this.itemSerialList = itemSerialList;
	}
	public String getSalesMemoNum() {
		return salesMemoNum;
	}
	public void setSalesMemoNum(String salesMemoNum) {
		this.salesMemoNum = salesMemoNum;
	}
	public String getSalesMemoNum2() {
		return salesMemoNum2;
	}
	public void setSalesMemoNum2(String salesMemoNum2) {
		this.salesMemoNum2 = salesMemoNum2;
	}
	public String getSalesMemoInd() {
		return salesMemoInd;
	}
	public void setSalesMemoInd(String salesMemoInd) {
		this.salesMemoInd = salesMemoInd;
	}
	public String getAoInd() {
		return aoInd;
	}
	public void setAoInd(String aoInd) {
		this.aoInd = aoInd;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getStatusId() {
		return statusId;
	}
	public void setStatusId(String statusId) {
		this.statusId = statusId;
	}
	public String getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
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
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getAoReportSent() {
		return aoReportSent;
	}
	public void setAoReportSent(String aoReportSent) {
		this.aoReportSent = aoReportSent;
	}
	public String getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}
	public String getEagleValidate() {
		return eagleValidate;
	}
	public void setEagleValidate(String eagleValidate) {
		this.eagleValidate = eagleValidate;
	}
}
