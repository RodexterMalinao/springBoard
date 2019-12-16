package com.bomwebportal.dto.report;

import java.util.Date;
import java.util.List;

public class RptRetCountRejDTO extends ReportDTO {
	private static final long serialVersionUID = 1997984608288960685L;
	
	public static final String JASPER_TEMPLATE = "MobRetCountRej";	

	private String custName;
	private String mobileNo;
	private String firstDeliveryDate;
	private String requestClaimDate;
	private String handsetModel;
	private String imei;
	private String orderId;
	private String specialReuqest; //remarks
	
	private List<String> hsDefect;
	private String hsDefectOthers;
	
	private List<String> acDefect;
	private String acDefectOthers;
	private String acDefectReason;
	
	private String changeImei;
	private Date deliveryDate;
	
	private String staffName;
	private String staffId;
	private String staffLoc;
	private String approverName;
	private String approverId;
	private String approverLoc;
	
	private String approvalSerial;
	private String mktSerialId;
	private String acctCode;
	private String ccc;
	
	private String customerCopyInd;//"Y" for copy

	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}


	public String getHandsetModel() {
		return handsetModel;
	}
	public void setHandsetModel(String handsetModel) {
		this.handsetModel = handsetModel;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSpecialReuqest() {
		return specialReuqest;
	}
	public void setSpecialReuqest(String specialReuqest) {
		this.specialReuqest = specialReuqest;
	}
	public List<String> getHsDefect() {
		return hsDefect;
	}
	public void setHsDefect(List<String> hsDefect) {
		this.hsDefect = hsDefect;
	}
	public String getHsDefectOthers() {
		return hsDefectOthers;
	}
	public void setHsDefectOthers(String hsDefectOthers) {
		this.hsDefectOthers = hsDefectOthers;
	}
	public List<String> getAcDefect() {
		return acDefect;
	}
	public void setAcDefect(List<String> acDefect) {
		this.acDefect = acDefect;
	}
	public String getAcDefectOthers() {
		return acDefectOthers;
	}
	public void setAcDefectOthers(String acDefectOthers) {
		this.acDefectOthers = acDefectOthers;
	}
	public String getAcDefectReason() {
		return acDefectReason;
	}
	public void setAcDefectReason(String acDefectReason) {
		this.acDefectReason = acDefectReason;
	}
	public String getChangeImei() {
		return changeImei;
	}
	public void setChangeImei(String changeImei) {
		this.changeImei = changeImei;
	}
	public Date getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getStaffName() {
		return staffName;
	}
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getStaffLoc() {
		return staffLoc;
	}
	public void setStaffLoc(String staffLoc) {
		this.staffLoc = staffLoc;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getApproverId() {
		return approverId;
	}
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	public String getApproverLoc() {
		return approverLoc;
	}
	public void setApproverLoc(String approverLoc) {
		this.approverLoc = approverLoc;
	}
	public String getApprovalSerial() {
		return approvalSerial;
	}
	public void setApprovalSerial(String approvalSerial) {
		this.approvalSerial = approvalSerial;
	}
	public String getMktSerialId() {
		return mktSerialId;
	}
	public void setMktSerialId(String mktSerialId) {
		this.mktSerialId = mktSerialId;
	}
	public String getAcctCode() {
		return acctCode;
	}
	public void setAcctCode(String acctCode) {
		this.acctCode = acctCode;
	}
	public String getCcc() {
		return ccc;
	}
	public void setCcc(String ccc) {
		this.ccc = ccc;
	}
	public String getCustomerCopyInd() {
		return customerCopyInd;
	}
	public void setCustomerCopyInd(String customerCopyInd) {
		this.customerCopyInd = customerCopyInd;
	}

	public String getFirstDeliveryDate() {
		return firstDeliveryDate;
	}
	public void setFirstDeliveryDate(String firstDeliveryDate) {
		this.firstDeliveryDate = firstDeliveryDate;
	}
	public String getRequestClaimDate() {
		return requestClaimDate;
	}
	public void setRequestClaimDate(String requestClaimDate) {
		this.requestClaimDate = requestClaimDate;
	}
}
