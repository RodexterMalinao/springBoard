package com.bomwebportal.lts.dto.order;

public class AmendAppointmentLtsDTO extends AmendCategoryLtsDTO {

	private static final long serialVersionUID = 6946363983915464228L;
	
	private String preWiringStartTime = null;
	private String preWiringEndTime = null;
	private String fromPreWiringStartTime = null;
	private String fromPreWiringEndTime = null;
	private String cutOverStartTime = null;
	private String cutOverEndTime = null;
	private String appntStartTime = null;
	private String appntEndTime = null;
	private String fromAppntStartTime = null;
	private String fromAppntEndTime = null;
	private String serialNum = null;
	private String delayReasonCd = null;
	private String appntType = null;
	private String preWiringType = null;
	private AppointmentDetailLtsDTO referenceOrdAppnt = null;
	
	//ACQ PIPB
	private String pipbSrvDtlId;
	private String pipbAppntStartTime = null;
	private String pipbAppntEndTime = null;
	private String fromPipbAppntStartTime = null;
	private String fromPipbAppntEndTime = null;
	private String pipbCutOverStartTime = null;
	private String pipbCutOverEndTime = null;
	private String pipbAppntType = null;
	private AppointmentDetailLtsDTO referenceOrdPipbAppnt = null;
	private String primarySrvStatus;
	private boolean isPreInstall = false;
	
	public String getPreWiringStartTime() {
		return preWiringStartTime;
	}

	public void setPreWiringStartTime(String preWiringStartTime) {
		this.preWiringStartTime = preWiringStartTime;
	}

	public String getPreWiringEndTime() {
		return preWiringEndTime;
	}

	public void setPreWiringEndTime(String preWiringEndTime) {
		this.preWiringEndTime = preWiringEndTime;
	}

	public String getCutOverStartTime() {
		return cutOverStartTime;
	}

	public void setCutOverStartTime(String cutOverStartTime) {
		this.cutOverStartTime = cutOverStartTime;
	}

	public String getCutOverEndTime() {
		return cutOverEndTime;
	}

	public void setCutOverEndTime(String cutOverEndTime) {
		this.cutOverEndTime = cutOverEndTime;
	}

	public String getAppntStartTime() {
		return appntStartTime;
	}

	public void setAppntStartTime(String appntStartTime) {
		this.appntStartTime = appntStartTime;
	}

	public String getAppntEndTime() {
		return appntEndTime;
	}

	public void setAppntEndTime(String appntEndTime) {
		this.appntEndTime = appntEndTime;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getDelayReasonCd() {
		return delayReasonCd;
	}

	public void setDelayReasonCd(String delayReasonCd) {
		this.delayReasonCd = delayReasonCd;
	}

	public String getFromAppntStartTime() {
		return fromAppntStartTime;
	}

	public void setFromAppntStartTime(String fromAppntStartTime) {
		this.fromAppntStartTime = fromAppntStartTime;
	}

	public String getFromAppntEndTime() {
		return fromAppntEndTime;
	}

	public void setFromAppntEndTime(String fromAppntEndTime) {
		this.fromAppntEndTime = fromAppntEndTime;
	}

	public AppointmentDetailLtsDTO getReferenceOrdAppnt() {
		return referenceOrdAppnt;
	}

	public void setReferenceOrdAppnt(AppointmentDetailLtsDTO referenceOrdAppnt) {
		this.referenceOrdAppnt = referenceOrdAppnt;
	}

	public String getPipbAppntStartTime() {
		return pipbAppntStartTime;
	}

	public void setPipbAppntStartTime(String pipbAppntStartTime) {
		this.pipbAppntStartTime = pipbAppntStartTime;
	}

	public String getPipbAppntEndTime() {
		return pipbAppntEndTime;
	}

	public void setPipbAppntEndTime(String pipbAppntEndTime) {
		this.pipbAppntEndTime = pipbAppntEndTime;
	}

	public String getFromPipbAppntStartTime() {
		return fromPipbAppntStartTime;
	}

	public void setFromPipbAppntStartTime(String fromPipbAppntStartTime) {
		this.fromPipbAppntStartTime = fromPipbAppntStartTime;
	}

	public String getFromPipbAppntEndTime() {
		return fromPipbAppntEndTime;
	}

	public void setFromPipbAppntEndTime(String fromPipbAppntEndTime) {
		this.fromPipbAppntEndTime = fromPipbAppntEndTime;
	}

	public String getPipbCutOverStartTime() {
		return pipbCutOverStartTime;
	}

	public void setPipbCutOverStartTime(String pipbCutOverStartTime) {
		this.pipbCutOverStartTime = pipbCutOverStartTime;
	}

	public String getPipbCutOverEndTime() {
		return pipbCutOverEndTime;
	}

	public void setPipbCutOverEndTime(String pipbCutOverEndTime) {
		this.pipbCutOverEndTime = pipbCutOverEndTime;
	}

	public AppointmentDetailLtsDTO getReferenceOrdPipbAppnt() {
		return referenceOrdPipbAppnt;
	}

	public void setReferenceOrdPipbAppnt(
			AppointmentDetailLtsDTO referenceOrdPipbAppnt) {
		this.referenceOrdPipbAppnt = referenceOrdPipbAppnt;
	}

	public String getPipbSrvDtlId() {
		return pipbSrvDtlId;
	}

	public void setPipbSrvDtlId(String pipbSrvDtlId) {
		this.pipbSrvDtlId = pipbSrvDtlId;
	}

	public String getPrimarySrvStatus() {
		return primarySrvStatus;
	}

	public void setPrimarySrvStatus(String primarySrvStatus) {
		this.primarySrvStatus = primarySrvStatus;
	}

	public String getAppntType() {
		return appntType;
	}

	public void setAppntType(String appntType) {
		this.appntType = appntType;
	}

	public String getPipbAppntType() {
		return pipbAppntType;
	}

	public void setPipbAppntType(String pipbAppntType) {
		this.pipbAppntType = pipbAppntType;
	}

	public String getPreWiringType() {
		return preWiringType;
	}

	public void setPreWiringType(String preWiringType) {
		this.preWiringType = preWiringType;
	}

	public String getFromPreWiringStartTime() {
		return fromPreWiringStartTime;
	}

	public void setFromPreWiringStartTime(String fromPreWiringStartTime) {
		this.fromPreWiringStartTime = fromPreWiringStartTime;
	}

	public String getFromPreWiringEndTime() {
		return fromPreWiringEndTime;
	}

	public void setFromPreWiringEndTime(String fromPreWiringEndTime) {
		this.fromPreWiringEndTime = fromPreWiringEndTime;
	}

	public boolean isPreInstall() {
		return isPreInstall;
	}

	public void setPreInstall(boolean isPreInstall) {
		this.isPreInstall = isPreInstall;
	}
	
	
}
