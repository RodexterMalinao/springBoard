package com.bomwebportal.mob.ccs.dto;

import java.util.Date;
import java.util.List;

public class MobCcsOrderRescueDTO {
	public String getIncidentNo() {
		return incidentNo;
	}
	public void setIncidentNo(String incidentNo) {
		this.incidentNo = incidentNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getHandleBy() {
		return handleBy;
	}
	public void setHandleBy(String handleBy) {
		this.handleBy = handleBy;
	}
	public Date getHandleTime() {
		return handleTime;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	public String getSerialCancel() {
		return serialCancel;
	}
	public void setSerialCancel(String serialCancel) {
		this.serialCancel = serialCancel;
	}
	public String getVisitMt1() {
		return visitMt1;
	}
	public void setVisitMt1(String visitMt1) {
		this.visitMt1 = visitMt1;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCourierStaffId() {
		return courierStaffId;
	}
	public void setCourierStaffId(String courierStaffId) {
		this.courierStaffId = courierStaffId;
	}
	public String getAmendment() {
		return amendment;
	}
	public void setAmendment(String amendment) {
		this.amendment = amendment;
	}
	public String getOrderSaved() {
		return orderSaved;
	}
	public void setOrderSaved(String orderSaved) {
		this.orderSaved = orderSaved;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getDoa() {
		return doa;
	}
	public void setDoa(String doa) {
		this.doa = doa;
	}
	public String getDelContractOnly() {
		return delContractOnly;
	}
	public void setDelContractOnly(String delContractOnly) {
		this.delContractOnly = delContractOnly;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}
	public String getCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(String checkPoint) {
		this.checkPoint = checkPoint;
	}
	public String getCheckPointDesc() {
		return checkPointDesc;
	}
	public void setCheckPointDesc(String checkPointDesc) {
		this.checkPointDesc = checkPointDesc;
	}
	public String getSalesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeliveryTimeSlot() {
		return deliveryTimeSlot;
	}
	public void setDeliveryTimeSlot(String deliveryTimeSlot) {
		this.deliveryTimeSlot = deliveryTimeSlot;
	}
	public String getDeliveryTimeFrom() {
		return deliveryTimeFrom;
	}
	public void setDeliveryTimeFrom(String deliveryTimeFrom) {
		this.deliveryTimeFrom = deliveryTimeFrom;
	}
	public String getDeliveryTimeTo() {
		return deliveryTimeTo;
	}
	public void setDeliveryTimeTo(String deliveryTimeTo) {
		this.deliveryTimeTo = deliveryTimeTo;
	}
	public Date getSrvReqDate() {
		return srvReqDate;
	}
	public void setSrvReqDate(Date srvReqDate) {
		this.srvReqDate = srvReqDate;
	}
	public String getLocationDesc() {
		return locationDesc;
	}
	public void setLocationDesc(String locationDesc) {
		this.locationDesc = locationDesc;
	}
	public String getDeliveryCentreDesc() {
		return deliveryCentreDesc;
	}
	public void setDeliveryCentreDesc(String deliveryCentreDesc) {
		this.deliveryCentreDesc = deliveryCentreDesc;
	}
	public List<MobCcsIncidentCauseDTO> getIncidentCauses() {
		return incidentCauses;
	}
	public void setIncidentCauses(List<MobCcsIncidentCauseDTO> incidentCauses) {
		this.incidentCauses = incidentCauses;
	}
	public String getReasonCd() {
		return reasonCd;
	}
	public void setReasonCd(String reasonCd) {
		this.reasonCd = reasonCd;
	}
	public boolean isSalesFalloutFlag() {
		return salesFalloutFlag;
	}
	public void setSalesFalloutFlag(boolean salesFalloutFlag) {
		this.salesFalloutFlag = salesFalloutFlag;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}


	private String incidentNo;
	private String orderId;
	private String handleBy;
	private Date handleTime;
	private String serialCancel;
	private String visitMt1;
	private String remark;
	private String action;
	private Date createDate;
	private String courierStaffId;
	private String amendment;
	private String orderSaved;
	private String rowId;
	private String doa;
	private String delContractOnly;

	private String msisdn;
	private String orderStatus;
	private String orderStatusDesc;
	private String checkPoint;
	private String checkPointDesc;
	private String salesName;
	private Date deliveryDate;
	private String deliveryTimeSlot;
	private String deliveryTimeFrom;
	private String deliveryTimeTo;
	private Date srvReqDate;
	private String locationDesc;
	private String deliveryCentreDesc;
	private List<MobCcsIncidentCauseDTO> incidentCauses;
	
	private String reasonCd;
	private boolean salesFalloutFlag;
	private String shopCode;
}
