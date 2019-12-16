package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;

public class LtsAppointmentFormDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2444410676978573412L;

	private String PCDOrderId;
	private boolean confirmIa;
	private boolean confirmCust;
	private String iaDiffInd;
	private String custDiffInd;
	private String allowCustConfirmInd;
	private String allowIaConfirmInd;
	
	private String edfRefNum;
	
	private LtsSRDDTO earliestSRD;
	private String earliestSrdReason;
	private LtsSRDDTO secDelEarliestSRD;
	private String secDelEarliestSrdReason;
	
	private String preBookSerialNum;
	private String preWiringType;
	private String preWiringDate;
	private String preWiringTime;
	private String installationType;
	private String installationDate;
	private String installationTime;
	
	private String cutOverDate;
	private String cutOverTime;

	private String secDelInstallationType;
	private String secDelInstallationDate;
	private String secDelInstallationTime;

	private String secDelCutOverDate;
	private String secDelCutOverTime;
	
	private String installationContactName;
	private String installationContactNum;
	private String installationMobileSMSAlert;
	
	private String bmoRemark;
	private String bmoAlertMsg;
	private String errorMsg;
	
	private String remarks;
	
	//indicators
	private String confirmedInd;
	private String sharePCDInd;
	private String cutOverInd;
	private String secDelInd;
	private String secDelCutOverInd;
	private String bbShortageInd;
	private String pcdOrderExistInd;
	private String tentativeInd;
	
	//suspend
	private String suspendReason;
	private String submitInd;
	
	
	// Adjustment Calculator
	private boolean allowAdjustment;
	private String adjAmount;
	private String adjResult;
	private String adjStartDate;
	private String adjEndDate;
	
	private boolean fieldVisitRequired;
	private boolean secDelFieldVisitRequired;

	//BOM2018061
	private String defaultContactName;
	private String defaultContactNum;
	private int epdLeadDay;
	private List<ItemDetailDTO> epdItemList;
	
	public String getPCDOrderId() {
		return PCDOrderId;
	}
	public void setPCDOrderId(String pCDOrderId) {
		this.PCDOrderId = pCDOrderId;
	}
	public String getPreBookSerialNum() {
		return preBookSerialNum;
	}
	public void setPreBookSerialNum(String preBookSerialNum) {
		this.preBookSerialNum = preBookSerialNum;
	}
	public String getPreWiringDate() {
		return preWiringDate;
	}
	public void setPreWiringDate(String preWiringDate) {
		this.preWiringDate = preWiringDate;
	}
	public String getPreWiringTime() {
		return preWiringTime;
	}
	public void setPreWiringTime(String preWiringTime) {
		this.preWiringTime = preWiringTime;
	}
	public String getInstallationDate() {
		return installationDate;
	}
	public void setInstallationDate(String installationDate) {
		this.installationDate = installationDate;
	}
	public String getInstallationTime() {
		return installationTime;
	}
	public void setInstallationTime(String installationTime) {
		this.installationTime = installationTime;
	}
	public String getInstallationContactName() {
		return installationContactName;
	}
	public void setInstallationContactName(String installationContactName) {
		this.installationContactName = installationContactName;
	}
	public String getInstallationContactNum() {
		return installationContactNum;
	}
	public void setInstallationContactNum(String installationContactNum) {
		this.installationContactNum = installationContactNum;
	}
	public String getInstallationMobileSMSAlert() {
		return installationMobileSMSAlert;
	}
	public void setInstallationMobileSMSAlert(String installationMobileSMSAlert) {
		this.installationMobileSMSAlert = installationMobileSMSAlert;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public boolean isConfirmIa() {
		return confirmIa;
	}
	public void setConfirmIa(boolean confirmIa) {
		this.confirmIa = confirmIa;
	}
	public boolean isConfirmCust() {
		return confirmCust;
	}
	public void setConfirmCust(boolean confirmCust) {
		this.confirmCust = confirmCust;
	}
	public String getCutOverDate() {
		return cutOverDate;
	}
	public void setCutOverDate(String cutOverDate) {
		this.cutOverDate = cutOverDate;
	}
	public String getCutOverTime() {
		return cutOverTime;
	}
	public void setCutOverTime(String cutOverTime) {
		this.cutOverTime = cutOverTime;
	}
	public String getConfirmedInd() {
		return confirmedInd;
	}
	public void setConfirmedInd(String confirmedInd) {
		this.confirmedInd = confirmedInd;
	}
	public String getSharePCDInd() {
		return sharePCDInd;
	}
	public void setSharePCDInd(String sharePCDInd) {
		this.sharePCDInd = sharePCDInd;
	}
	public String getSecDelInstallationDate() {
		return secDelInstallationDate;
	}
	public void setSecDelInstallationDate(String secDelInstallationDate) {
		this.secDelInstallationDate = secDelInstallationDate;
	}
	public String getSecDelInstallationTime() {
		return secDelInstallationTime;
	}
	public void setSecDelInstallationTime(String secDelInstallationTime) {
		this.secDelInstallationTime = secDelInstallationTime;
	}
	public String getSecDelCutOverDate() {
		return secDelCutOverDate;
	}
	public void setSecDelCutOverDate(String secDelCutOverDate) {
		this.secDelCutOverDate = secDelCutOverDate;
	}
	public String getSecDelCutOverTime() {
		return secDelCutOverTime;
	}
	public void setSecDelCutOverTime(String secDelCutOverTime) {
		this.secDelCutOverTime = secDelCutOverTime;
	}
	public String getCutOverInd() {
		return cutOverInd;
	}
	public void setCutOverInd(String cutOverInd) {
		this.cutOverInd = cutOverInd;
	}
	public String getSecDelInd() {
		return secDelInd;
	}
	public void setSecDelInd(String secDelInd) {
		this.secDelInd = secDelInd;
	}
	public String getSecDelCutOverInd() {
		return secDelCutOverInd;
	}
	public void setSecDelCutOverInd(String secDelCutOverInd) {
		this.secDelCutOverInd = secDelCutOverInd;
	}
	public LtsSRDDTO getEarliestSRD() {
		return earliestSRD;
	}
	public void setEarliestSRD(LtsSRDDTO earliestSRD) {
		this.earliestSRD = earliestSRD;
	}
	public String getBbShortageInd() {
		return bbShortageInd;
	}
	public void setBbShortageInd(String bbShortageInd) {
		this.bbShortageInd = bbShortageInd;
	}
	public String getPcdOrderExistInd() {
		return pcdOrderExistInd;
	}
	public void setPcdOrderExistInd(String pcdOrderExistInd) {
		this.pcdOrderExistInd = pcdOrderExistInd;
	}
	public String getSuspendReason() {
		return suspendReason;
	}
	public void setSuspendReason(String suspendReason) {
		this.suspendReason = suspendReason;
	}
	public String getSubmitInd() {
		return submitInd;
	}
	public void setSubmitInd(String submitInd) {
		this.submitInd = submitInd;
	}
	public String getPreWiringType() {
		return preWiringType;
	}
	public void setPreWiringType(String preWiringType) {
		this.preWiringType = preWiringType;
	}
	public String getInstallationType() {
		return installationType;
	}
	public void setInstallationType(String installationType) {
		this.installationType = installationType;
	}
	public String getSecDelInstallationType() {
		return secDelInstallationType;
	}
	public void setSecDelInstallationType(String secDelInstallationType) {
		this.secDelInstallationType = secDelInstallationType;
	}
	public String getAllowCustConfirmInd() {
		return allowCustConfirmInd;
	}
	public void setAllowCustConfirmInd(String allowCustConfirmInd) {
		this.allowCustConfirmInd = allowCustConfirmInd;
	}
	public String getAllowIaConfirmInd() {
		return allowIaConfirmInd;
	}
	public void setAllowIaConfirmInd(String allowIaConfirmInd) {
		this.allowIaConfirmInd = allowIaConfirmInd;
	}
	public String getBmoRemark() {
		return bmoRemark;
	}
	public void setBmoRemark(String bmoRemark) {
		this.bmoRemark = bmoRemark;
	}
	public String getBmoAlertMsg() {
		return bmoAlertMsg;
	}
	public void setBmoAlertMsg(String bmoAlertMsg) {
		this.bmoAlertMsg = bmoAlertMsg;
	}
	public String getEarliestSrdReason() {
		return earliestSrdReason;
	}
	public void setEarliestSrdReason(String earliestSrdReason) {
		this.earliestSrdReason = earliestSrdReason;
	}
	public String getIaDiffInd() {
		return iaDiffInd;
	}
	public void setIaDiffInd(String iaDiffInd) {
		this.iaDiffInd = iaDiffInd;
	}
	public String getCustDiffInd() {
		return custDiffInd;
	}
	public void setCustDiffInd(String custDiffInd) {
		this.custDiffInd = custDiffInd;
	}
	public String getTentativeInd() {
		return tentativeInd;
	}
	public void setTentativeInd(String tentativeInd) {
		this.tentativeInd = tentativeInd;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public LtsSRDDTO getSecDelEarliestSRD() {
		return secDelEarliestSRD;
	}
	public void setSecDelEarliestSRD(LtsSRDDTO secDelEarliestSRD) {
		this.secDelEarliestSRD = secDelEarliestSRD;
	}
	public String getSecDelEarliestSrdReason() {
		return secDelEarliestSrdReason;
	}
	public void setSecDelEarliestSrdReason(String secDelEarliestSrdReason) {
		this.secDelEarliestSrdReason = secDelEarliestSrdReason;
	}
	/**
	 * @return the edfRefNum
	 */
	public String getEdfRefNum() {
		return edfRefNum;
	}
	/**
	 * @param edfRefNum the edfRefNum to set
	 */
	public void setEdfRefNum(String edfRefNum) {
		this.edfRefNum = edfRefNum;
	}
	public boolean isAllowAdjustment() {
		return allowAdjustment;
	}
	public void setAllowAdjustment(boolean allowAdjustment) {
		this.allowAdjustment = allowAdjustment;
	}
	public String getAdjAmount() {
		return adjAmount;
	}
	public void setAdjAmount(String adjAmount) {
		this.adjAmount = adjAmount;
	}
	public String getAdjResult() {
		return adjResult;
	}
	public void setAdjResult(String adjResult) {
		this.adjResult = adjResult;
	}
	public String getAdjStartDate() {
		return adjStartDate;
	}
	public void setAdjStartDate(String adjStartDate) {
		this.adjStartDate = adjStartDate;
	}
	public String getAdjEndDate() {
		return adjEndDate;
	}
	public void setAdjEndDate(String adjEndDate) {
		this.adjEndDate = adjEndDate;
	}
	public boolean isSecDelFieldVisitRequired() {
		return secDelFieldVisitRequired;
	}
	public void setSecDelFieldVisitRequired(boolean secDelFieldVisitRequired) {
		this.secDelFieldVisitRequired = secDelFieldVisitRequired;
	}
	public boolean isFieldVisitRequired() {
		return fieldVisitRequired;
	}
	public void setFieldVisitRequired(boolean fieldVisitRequired) {
		this.fieldVisitRequired = fieldVisitRequired;
	}
	public List<ItemDetailDTO> getEpdItemList() {
		return epdItemList;
	}
	public void setEpdItemList(List<ItemDetailDTO> epdItemList) {
		this.epdItemList = epdItemList;
	}
	public String getDefaultContactName() {
		return defaultContactName;
	}
	public void setDefaultContactName(String defaultContactName) {
		this.defaultContactName = defaultContactName;
	}
	public String getDefaultContactNum() {
		return defaultContactNum;
	}
	public void setDefaultContactNum(String defaultContactNum) {
		this.defaultContactNum = defaultContactNum;
	}
	public int getEpdLeadDay() {
		return epdLeadDay;
	}
	public void setEpdLeadDay(int epdLeadDay) {
		this.epdLeadDay = epdLeadDay;
	}

	
}
