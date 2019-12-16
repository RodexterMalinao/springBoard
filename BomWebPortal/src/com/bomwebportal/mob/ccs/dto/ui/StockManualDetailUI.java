package com.bomwebportal.mob.ccs.dto.ui;

import java.io.Serializable;

public class StockManualDetailUI implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8365360540922674541L;
	
	private String orderId;
	private String actionType;
	private String deliveryDateStr;
	private String deliveryTimeSlot;
	private String srvReqDateStr;
	private String mnpTicketNum;
	private String mnpInd;
	
	// add by Joyce 20120507
	private String itemCode; // for deassign per item
	private String itemSerial; // for deassign per item
	
	private String stockPool;
	
	private String errMsg;
	
	/**
	 * @return the mnpInd
	 */
	public String getMnpInd() {
		return mnpInd;
	}
	/**
	 * @param mnpInd the mnpInd to set
	 */
	public void setMnpInd(String mnpInd) {
		this.mnpInd = mnpInd;
	}
	/**
	 * @return the mnpTicketNum
	 */
	public String getMnpTicketNum() {
		return mnpTicketNum;
	}
	/**
	 * @param mnpTicketNum the mnpTicketNum to set
	 */
	public void setMnpTicketNum(String mnpTicketNum) {
		this.mnpTicketNum = mnpTicketNum;
	}
	/**
	 * @return the srvReqDateStr
	 */
	public String getSrvReqDateStr() {
		return srvReqDateStr;
	}
	/**
	 * @param srvReqDateStr the srvReqDateStr to set
	 */
	public void setSrvReqDateStr(String srvReqDateStr) {
		this.srvReqDateStr = srvReqDateStr;
	}
	/**
	 * @return the deliveryDateStr
	 */
	public String getDeliveryDateStr() {
		return deliveryDateStr;
	}
	/**
	 * @param deliveryDateStr the deliveryDateStr to set
	 */
	public void setDeliveryDateStr(String deliveryDateStr) {
		this.deliveryDateStr = deliveryDateStr;
	}
	/**
	 * @return the deliveryTimeSlot
	 */
	public String getDeliveryTimeSlot() {
		return deliveryTimeSlot;
	}
	/**
	 * @param deliveryTimeSlot the deliveryTimeSlot to set
	 */
	public void setDeliveryTimeSlot(String deliveryTimeSlot) {
		this.deliveryTimeSlot = deliveryTimeSlot;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getActionType() {
		return actionType;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
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
	public String getStockPool() {
		return stockPool;
	}
	public void setStockPool(String stockPool) {
		this.stockPool = stockPool;
	}

}
