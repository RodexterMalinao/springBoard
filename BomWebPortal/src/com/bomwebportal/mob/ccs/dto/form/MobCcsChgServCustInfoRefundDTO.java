package com.bomwebportal.mob.ccs.dto.form;

import java.io.InputStream;

import com.bomwebportal.dto.report.ReportDTO;

public class MobCcsChgServCustInfoRefundDTO extends ReportDTO{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8980672078304167391L;

    public static final String JASPER_TEMPLATE_CS = "ChgServCustInfoRefund";
    
    private String orderId;
    private String idDocType;
    private String idDocNum;
    private String iccid;
    private String customerName;
    private String mnpMsisdn;
    private String newMsisdn;
    private String appInDateStr;
    private String staffcodeName;
    private String customerNameLabelDisp;
    private String contactPhone;
    private String customerCopyInd;
    private String transferOwnershipTargetCommDateStr;
    private String changeMobNumTargetCommDateStr;
    private boolean ownershipFormInd;
    private boolean changeOfMobileNumInd;
    private boolean handsetind;
    private boolean iccidInd;
    private String transferee;
    private String transfereeIdNum;
    private InputStream custSignature; // add by Joyce 20120723
    private InputStream transfereeSignature; // add by Joyce 20120723
    
    public String getTransferee() {
		return transferee;
	}
	public void setTransferee(String transferee) {
		this.transferee = transferee;
	}
	public String getTransfereeIdNum() {
		return transfereeIdNum;
	}
	public void setTransfereeIdNum(String transfereeIdNum) {
		this.transfereeIdNum = transfereeIdNum;
	}
	public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getIdDocType() {
        return idDocType;
    }
    public void setIdDocType(String idDocType) {
        this.idDocType = idDocType;
    }    
    public String getIccid() {
        return iccid;
    }
    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }   
    public String getAppInDateStr() {
        return appInDateStr;
    }
    public void setAppInDateStr(String appInDateStr) {
        this.appInDateStr = appInDateStr;
    }
    public String getStaffcodeName() {
        return staffcodeName;
    }
    public void setStaffcodeName(String staffcodeName) {
        this.staffcodeName = staffcodeName;
    }
    public String getCustomerNameLabelDisp() {
        return customerNameLabelDisp;
    }
    public void setCustomerNameLabelDisp(String customerNameLabelDisp) {
        this.customerNameLabelDisp = customerNameLabelDisp;
    }
    public String getCustomerCopyInd() {
        return customerCopyInd;
    }
    public void setCustomerCopyInd(String customerCopyInd) {
        this.customerCopyInd = customerCopyInd;
    }
    public String getIdDocNum() {
        return idDocNum;
    }
    public void setIdDocNum(String idDocNum) {
        this.idDocNum = idDocNum;
    }
    public String getTransferOwnershipTargetCommDateStr() {
        return transferOwnershipTargetCommDateStr;
    }
    public void setTransferOwnershipTargetCommDateStr(
    	String transferOwnershipTargetCommDateStr) {
        this.transferOwnershipTargetCommDateStr = transferOwnershipTargetCommDateStr;
    }
    public String getChangeMobNumTargetCommDateStr() {
        return changeMobNumTargetCommDateStr;
    }
    public void setChangeMobNumTargetCommDateStr(
    	String changeMobNumTargetCommDateStr) {
        this.changeMobNumTargetCommDateStr = changeMobNumTargetCommDateStr;
    }
    public boolean isOwnershipFormInd() {
        return ownershipFormInd;
    }
    public void setOwnershipFormInd(boolean ownershipFormInd) {
        this.ownershipFormInd = ownershipFormInd;
    }
    public boolean isChangeOfMobileNumInd() {
        return changeOfMobileNumInd;
    }
    public void setChangeOfMobileNumInd(boolean changeOfMobileNumInd) {
        this.changeOfMobileNumInd = changeOfMobileNumInd;
    }
    public boolean isHandsetind() {
        return handsetind;
    }
    public void setHandsetind(boolean handsetind) {
        this.handsetind = handsetind;
    }
    public boolean isIccidInd() {
        return iccidInd;
    }
    public void setIccidInd(boolean iccidInd) {
        this.iccidInd = iccidInd;
    }
    public String getMnpMsisdn() {
        return mnpMsisdn;
    }
    public void setMnpMsisdn(String mnpMsisdn) {
        this.mnpMsisdn = mnpMsisdn;
    }
    public String getNewMsisdn() {
        return newMsisdn;
    }
    public void setNewMsisdn(String newMsisdn) {
        this.newMsisdn = newMsisdn;
    }
    public String getContactPhone() {
        return contactPhone;
    }
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
	/**
	 * @return the custSignature
	 */
	public InputStream getCustSignature() {
		return custSignature;
	}
	/**
	 * @param custSignature the custSignature to set
	 */
	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}
	/**
	 * @return the transfereeSignature
	 */
	public InputStream getTransfereeSignature() {
		return transfereeSignature;
	}
	/**
	 * @param transfereeSignature the transfereeSignature to set
	 */
	public void setTransfereeSignature(InputStream transfereeSignature) {
		this.transfereeSignature = transfereeSignature;
	}
    
}
