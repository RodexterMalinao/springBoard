package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MobSupportDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3350718365310289162L;
	
	private String orderId;
	private String orderIdFmDropDown;
	private String orderIdFmTextField;
	private String oOcid;
	private String nOcid;
	private String oOrderStatus;
	private String nOrderStatus;
	private String oCheckPoint;
	private String oReasonCd;
	private String oAppDate;
	private Date nAppDate;
	private String errMsg;
	private String nErrMsg;
	private String sim;
	private String oSimStatus;
	private String oSimStatusDesc;
	private String nSimStatus;
	private String bomSimStatus;
	private String mnpInd;
	private String mrt;
	private String oMrtStatus;
	private String oMrtStatusDesc;
	private String nMrtStatus;
	private String bomMrtStatus;
	private String shopCode;
	private String salesName;
	private String shopTel;
	private String shopEmail;
	private String actionType;
	private String channelId;
	private List<String[]> conflictOrder;
	private String conflictOrderId;
	private String cloneOrderId;
	private String reserveId;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderIdFmDropDown() {
		return orderIdFmDropDown;
	}
	public void setOrderIdFmDropDown(String orderIdFmDropDown) {
		this.orderIdFmDropDown = orderIdFmDropDown;
	}
	public String getOrderIdFmTextField() {
		return orderIdFmTextField;
	}
	public void setOrderIdFmTextField(String orderIdFmTextField) {
		this.orderIdFmTextField = orderIdFmTextField;
	}
	public String getoOcid() {
		return oOcid;
	}
	public void setoOcid(String oOcid) {
		this.oOcid = oOcid;
	}
	public String getnOcid() {
		return nOcid;
	}
	public void setnOcid(String nOcid) {
		this.nOcid = nOcid;
	}
	public String getoOrderStatus() {
		return oOrderStatus;
	}
	public void setoOrderStatus(String oOrderStatus) {
		this.oOrderStatus = oOrderStatus;
	}
	public String getoCheckPoint() {
		return oCheckPoint;
	}
	public void setoCheckPoint(String oCheckPoint) {
		this.oCheckPoint = oCheckPoint;
	}
	public String getoReasonCd() {
		return oReasonCd;
	}
	public void setoReasonCd(String oReasonCd) {
		this.oReasonCd = oReasonCd;
	}
	public String getnOrderStatus() {
		return nOrderStatus;
	}
	public void setnOrderStatus(String nOrderStatus) {
		this.nOrderStatus = nOrderStatus;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getoAppDate() {
		return oAppDate;
	}
	public void setoAppDate(String oAppDate) {
		this.oAppDate = oAppDate;
	}
	public Date getnAppDate() {
		return nAppDate;
	}
	public void setnAppDate(Date nAppDate) {
		this.nAppDate = nAppDate;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public String getnErrMsg() {
		return nErrMsg;
	}
	public void setnErrMsg(String nErrMsg) {
		this.nErrMsg = nErrMsg;
	}
	public String getSim() {
		return sim;
	}
	public void setSim(String sim) {
		this.sim = sim;
	}
	public String getoSimStatus() {
		return oSimStatus;
	}
	public void setoSimStatus(String oSimStatus) {
		this.oSimStatus = oSimStatus;
	}
	public String getoSimStatusDesc() {
		return oSimStatusDesc;
	}
	public void setoSimStatusDesc(String oSimStatusDesc) {
		this.oSimStatusDesc = oSimStatusDesc;
	}
	public String getnSimStatus() {
		return nSimStatus;
	}
	public void setnSimStatus(String nSimStatus) {
		this.nSimStatus = nSimStatus;
	}
	public String getBomSimStatus() {
		return bomSimStatus;
	}
	public void setBomSimStatus(String bomSimStatus) {
		this.bomSimStatus = bomSimStatus;
	}
	public String getMnpInd() {
		return mnpInd;
	}
	public void setMnpInd(String mnpInd) {
		this.mnpInd = mnpInd;
	}
	public String getMrt() {
		return mrt;
	}
	public void setMrt(String mrt) {
		this.mrt = mrt;
	}
	public String getoMrtStatus() {
		return oMrtStatus;
	}
	public void setoMrtStatus(String oMrtStatus) {
		this.oMrtStatus = oMrtStatus;
	}
	public String getoMrtStatusDesc() {
		return oMrtStatusDesc;
	}
	public void setoMrtStatusDesc(String oMrtStatusDesc) {
		this.oMrtStatusDesc = oMrtStatusDesc;
	}
	public String getnMrtStatus() {
		return nMrtStatus;
	}
	public void setnMrtStatus(String nMrtStatus) {
		this.nMrtStatus = nMrtStatus;
	}
	public String getBomMrtStatus() {
		return bomMrtStatus;
	}
	public void setBomMrtStatus(String bomMrtStatus) {
		this.bomMrtStatus = bomMrtStatus;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public String getShopTel() {
		return shopTel;
	}
	public void setShopTel(String shopTel) {
		this.shopTel = shopTel;
	}
	public String getShopEmail() {
		return shopEmail;
	}
	public void setShopEmail(String shopEmail) {
		this.shopEmail = shopEmail;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public List<String[]> getConflictOrder() {
		return conflictOrder;
	}
	public void setConflictOrder(List<String[]> conflictOrder) {
		this.conflictOrder = conflictOrder;
	}
	public String getConflictOrderId() {
		return conflictOrderId;
	}
	public void setConflictOrderId(String conflictOrderId) {
		this.conflictOrderId = conflictOrderId;
	}
	public String getCloneOrderId() {
		return cloneOrderId;
	}
	public void setCloneOrderId(String cloneOrderId) {
		this.cloneOrderId = cloneOrderId;
	}
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
}
