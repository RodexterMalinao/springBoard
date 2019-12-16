package com.bomwebportal.mob.ds.ui;

import java.util.List;
import com.bomwebportal.mob.ds.dto.MobDsStockItemDTO;

public class MobDsCancelOrderUI {
	private String orderId;
	private String ocid;
	private String orderStatus;
	private String mnpInd;
	private String msisdn;
	private int msisdnStatus;
	private String msisdnStatusDesc;
	private List<MobDsStockItemDTO> stockItemList;
	private String actionType;
	private boolean multiSim;
	private String cloneOrderId;
	private String cloneOrderStatus;
	private String preRegInd;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOcid() {
		return ocid;
	}
	public void setOcid(String ocid) {
		this.ocid = ocid;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getMnpInd() {
		return mnpInd;
	}
	public void setMnpInd(String mnpInd) {
		this.mnpInd = mnpInd;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public int getMsisdnStatus() {
		return msisdnStatus;
	}
	public void setMsisdnStatus(int msisdnStatus) {
		this.msisdnStatus = msisdnStatus;
	}
	public String getMsisdnStatusDesc() {
		return msisdnStatusDesc;
	}
	public void setMsisdnStatusDesc(String msisdnStatusDesc) {
		this.msisdnStatusDesc = msisdnStatusDesc;
	}
	public List<MobDsStockItemDTO> getStockItemList() {
		return stockItemList;
	}
	public void setStockItemList(List<MobDsStockItemDTO> stockItemList) {
		this.stockItemList = stockItemList;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public boolean isMultiSim() {
		return multiSim;
	}
	public void setMultiSim(boolean multiSim) {
		this.multiSim = multiSim;
	}
	public String getCloneOrderId() {
		return cloneOrderId;
	}
	public void setCloneOrderId(String cloneOrderId) {
		this.cloneOrderId = cloneOrderId;
	}
	public String getCloneOrderStatus() {
		return cloneOrderStatus;
	}
	public void setCloneOrderStatus(String cloneOrderStatus) {
		this.cloneOrderStatus = cloneOrderStatus;
	}
	public String getPreRegInd() {
		return preRegInd;
	}
	public void setPreRegInd(String preRegInd) {
		this.preRegInd = preRegInd;
	} 
	

}
