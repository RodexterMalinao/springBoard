package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;

import org.apache.axis.utils.StringUtils;
import org.joda.time.format.DateTimeFormat;

import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import java.util.List;

public class LtsAcqAppointmentFormDTO  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7170932875690795779L;

	private String errorMsg;
	private String bmoAlertMsg;
	private String bmoRemark;
	
	private String preBookSerialNum;
	private LtsAppointmentDetail preWiringAppntDtl = null;
	private LtsAppointmentDetail installAppntDtl = null;
	private LtsAppointmentDetail secDelInstallAppntDtl = null;
	private LtsAppointmentDetail portLaterAppntDtl = null;
	private String maxDate;

	//indicators
	private boolean confirmedInd;
	private boolean bbShortageInd;
	private boolean tentativeInd;
	private boolean allowAppntInd;
	private boolean secDelMustSameDateInd;
	
	
	//pcd order
	private boolean sharePcdInd;
	private boolean pcdOrderExistInd;
	private String pcdOrderId;
	private boolean chkPcdActivationDate;
	private String pcdActivationDate;
	
	//contact info
	private String installationContactName;
	private String installationContactNum;
	private String installationMobileSMSAlert;
	private String customerContactMobileNum;
	private String customerContactFixLineNum;
	
	private String remarks;
	private String qcRemarks;
	
	//suspend
	private String suspendReason;
	private String submitInd;	
	private String suspendRemarks;
	
	private boolean containPrewireVas;
	
	//DS
	private boolean dsDoorKnockedInd;
	private boolean waiveCoolingOffInd;
	private Boolean peLinkInd;
	private boolean peLinkMandatory;
	private String earliestCoolOffSRD;
	private String earliestPeLinkSRD;

	private boolean mustQc;
	private String mustQcReasonCd;
	private boolean qcPassedInd;

	private String dsMinDate;
	
	private boolean isPcdResourceShortage = false;
	private boolean isDelFree = false;
	private boolean isPcdBundleBasket = false;
	private boolean isPcdBundlePremium = false;
	private boolean isPreWiring = false;
	
	//BOM2018061
	private String defaultContactName;
	private String defaultContactNum;
	private List<ItemDetailDTO> epdItemList;
	private int epdLeadDay;
	
	public LtsAcqAppointmentFormDTO(){
		this.installAppntDtl = new LtsAppointmentDetail();
	}
	
	public class LtsAppointmentDetail implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = 446858728857197742L;

		private LtsSRDDTO earliestSRD;
		private String earliestSRDReason;
		private String appntDate;
		private String appntTimeSlotAndType;
		private boolean cutOverInd;
		private String cutOverDate;
		private String cutOverTime;
		
		public LtsSRDDTO getEarliestSRD() {
			return earliestSRD;
		}

		public void setEarliestSRD(LtsSRDDTO earliestSRD) {
			this.earliestSRD = earliestSRD;
		}

		public String getEarliestSRDReason() {
			return earliestSRDReason;
		}

		public void setEarliestSRDReason(String earliestSRDReason) {
			this.earliestSRDReason = earliestSRDReason;
		}
		
		public String getEarliestSrdDesc(){
			//set description for earliest srd in format: dd/MM/yyyy (EEE)
			if(earliestSRD == null){
				return null;
			}
			return earliestSRD.getSRDate().toString(DateTimeFormat.forPattern("dd/MM/yyyy (EEE)"));
		}

		public String getAppntDate() {
			return appntDate;
		}

		public void setAppntDate(String appntDate) {
			this.appntDate = appntDate;
		}

		public String getAppntTimeSlot() {
			if(StringUtils.isEmpty(this.appntTimeSlotAndType)){
				return null;
			}else{
				String[] timeSlotAndType = this.appntTimeSlotAndType.split(LtsAppointmentHelper.TIMESLOT_DELIMITER); //StringUtils.split(this.appntTimeSlotAndType, "|");
				return timeSlotAndType[0];
			}
		}

		public String getAppntTimeSlotType() {
			if(StringUtils.isEmpty(this.appntTimeSlotAndType)){
				return null;
			}else{
				String[] timeSlotAndType = this.appntTimeSlotAndType.split(LtsAppointmentHelper.TIMESLOT_DELIMITER);
				if(timeSlotAndType.length > 1){
					return timeSlotAndType[1];
				}else{
					return null;
				}
			}
		}

		public String getAppntTimeSlotAndType() {
			return appntTimeSlotAndType;
		}

		public void setAppntTimeSlotAndType(String appntTimeSlotAndType) {
			this.appntTimeSlotAndType = appntTimeSlotAndType;
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

		public boolean isCutOverInd() {
			return cutOverInd;
		}

		public void setCutOverInd(boolean cutOverInd) {
			this.cutOverInd = cutOverInd;
		}
		
		
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getBmoAlertMsg() {
		return bmoAlertMsg;
	}

	public void setBmoAlertMsg(String bmoAlertMsg) {
		this.bmoAlertMsg = bmoAlertMsg;
	}

	public String getBmoRemark() {
		return bmoRemark;
	}

	public void setBmoRemark(String bmoRemark) {
		this.bmoRemark = bmoRemark;
	}

	public String getPreBookSerialNum() {
		return preBookSerialNum;
	}

	public void setPreBookSerialNum(String preBookSerialNum) {
		this.preBookSerialNum = preBookSerialNum;
	}
	
	public LtsAppointmentDetail getInstallAppntDtl() {
		return installAppntDtl;
	}

	public void setInstallAppntDtl(LtsAppointmentDetail installAppntDtl) {
		this.installAppntDtl = installAppntDtl;
	}

	public LtsAppointmentDetail getPreWiringAppntDtl() {
		return preWiringAppntDtl;
	}

	public void setPreWiringAppntDtl(LtsAppointmentDetail preWiringAppntDtl) {
		this.preWiringAppntDtl = preWiringAppntDtl;
	}

	public LtsAppointmentDetail getSecDelInstallAppntDtl() {
		return secDelInstallAppntDtl;
	}

	public void setSecDelInstallAppntDtl(LtsAppointmentDetail secDelInstallAppntDtl) {
		this.secDelInstallAppntDtl = secDelInstallAppntDtl;
	}

	public boolean isConfirmedInd() {
		return confirmedInd;
	}

	public void setConfirmedInd(boolean confirmedInd) {
		this.confirmedInd = confirmedInd;
	}

	public boolean isBbShortageInd() {
		return bbShortageInd;
	}

	public void setBbShortageInd(boolean bbShortageInd) {
		this.bbShortageInd = bbShortageInd;
	}

	public boolean isTentativeInd() {
		return tentativeInd;
	}

	public void setTentativeInd(boolean tentativeInd) {
		this.tentativeInd = tentativeInd;
	}

	public boolean isAllowAppntInd() {
		return allowAppntInd;
	}

	public void setAllowAppntInd(boolean allowAppntInd) {
		this.allowAppntInd = allowAppntInd;
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

	public String getCustomerContactMobileNum() {
		return customerContactMobileNum;
	}

	public void setCustomerContactMobileNum(String customerContactMobileNum) {
		this.customerContactMobileNum = customerContactMobileNum;
	}

	public String getCustomerContactFixLineNum() {
		return customerContactFixLineNum;
	}

	public void setCustomerContactFixLineNum(String customerContactFixLineNum) {
		this.customerContactFixLineNum = customerContactFixLineNum;
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

	public boolean isSharePcdInd() {
		return sharePcdInd;
	}

	public void setSharePcdInd(boolean sharePcdInd) {
		this.sharePcdInd = sharePcdInd;
	}

	public boolean isPcdOrderExistInd() {
		return pcdOrderExistInd;
	}

	public void setPcdOrderExistInd(boolean pcdOrderExistInd) {
		this.pcdOrderExistInd = pcdOrderExistInd;
	}

	public String getPcdOrderId() {
		return pcdOrderId;
	}

	public void setPcdOrderId(String pcdOrderId) {
		this.pcdOrderId = pcdOrderId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public boolean isContainPrewireVas() {
		return containPrewireVas;
	}

	public void setContainPrewireVas(boolean containPrewireVas) {
		this.containPrewireVas = containPrewireVas;
	}

	public LtsAppointmentDetail getPortLaterAppntDtl() {
		return portLaterAppntDtl;
	}

	public void setPortLaterAppntDtl(LtsAppointmentDetail portLaterAppntDtl) {
		this.portLaterAppntDtl = portLaterAppntDtl;
	}

	public boolean isSecDelMustSameDateInd() {
		return secDelMustSameDateInd;
	}

	public void setSecDelMustSameDateInd(boolean secDelMustSameDateInd) {
		this.secDelMustSameDateInd = secDelMustSameDateInd;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public boolean isDsDoorKnockedInd() {
		return dsDoorKnockedInd;
	}

	public void setDsDoorKnockedInd(boolean dsDoorKnockedInd) {
		this.dsDoorKnockedInd = dsDoorKnockedInd;
	}

	public boolean isWaiveCoolingOffInd() {
		return waiveCoolingOffInd;
	}

	public void setWaiveCoolingOffInd(boolean waiveCoolingOffInd) {
		this.waiveCoolingOffInd = waiveCoolingOffInd;
	}

	public Boolean getPeLinkInd() {
		return peLinkInd;
	}

	public void setPeLinkInd(Boolean peLinkInd) {
		this.peLinkInd = peLinkInd;
	}

	public boolean isPeLinkMandatory() {
		return peLinkMandatory;
	}

	public void setPeLinkMandatory(boolean peLinkMandatory) {
		this.peLinkMandatory = peLinkMandatory;
	}

	public String getQcRemarks() {
		return qcRemarks;
	}

	public void setQcRemarks(String qcRemarks) {
		this.qcRemarks = qcRemarks;
	}

	public String getEarliestCoolOffSRD() {
		return earliestCoolOffSRD;
	}

	public void setEarliestCoolOffSRD(String earliestCoolOffSRD) {
		this.earliestCoolOffSRD = earliestCoolOffSRD;
	}

	public String getEarliestPeLinkSRD() {
		return earliestPeLinkSRD;
	}

	public void setEarliestPeLinkSRD(String earliestPeLinkSRD) {
		this.earliestPeLinkSRD = earliestPeLinkSRD;
	}
	
	public String getSuspendRemarks() {
		return suspendRemarks;
	}

	public void setSuspendRemarks(String suspendRemarks) {
		this.suspendRemarks = suspendRemarks;
	}

	public boolean isMustQc() {
		return mustQc;
	}
	
	public void setMustQc(boolean mustQc) {
		this.mustQc = mustQc;
	}

	public String getMustQcReasonCd() {
		return mustQcReasonCd;
	}

	public void setMustQcReasonCd(String mustQcReasonCd) {
		this.mustQcReasonCd = mustQcReasonCd;
	}

	public String getDsMinDate() {
		return dsMinDate;
	}

	public void setDsMinDate(String dsMinDate) {
		this.dsMinDate = dsMinDate;
	}

	public boolean isQcPassedInd() {
		return qcPassedInd;
	}

	public void setQcPassedInd(boolean qcPassedInd) {
		this.qcPassedInd = qcPassedInd;
	}

	public boolean isPcdResourceShortage() {
		return isPcdResourceShortage;
	}

	public void setPcdResourceShortage(boolean isPcdResourceShortage) {
		this.isPcdResourceShortage = isPcdResourceShortage;
	}

	public boolean isDelFree() {
		return isDelFree;
	}

	public void setDelFree(boolean isDelFree) {
		this.isDelFree = isDelFree;
	}

	public boolean isPcdBundleBasket() {
		return isPcdBundleBasket;
	}

	public void setPcdBundleBasket(boolean isPcdBundleBasket) {
		this.isPcdBundleBasket = isPcdBundleBasket;
	}

	public boolean isPcdBundlePremium() {
		return isPcdBundlePremium;
	}

	public void setPcdBundlePremium(boolean isPcdBundlePremium) {
		this.isPcdBundlePremium = isPcdBundlePremium;
	}

	public boolean isPreWiring() {
		return isPreWiring;
	}

	public void setPreWiring(boolean isPreWiring) {
		this.isPreWiring = isPreWiring;
	}

	public boolean isChkPcdActivationDate() {
		return chkPcdActivationDate;
	}

	public void setChkPcdActivationDate(boolean chkPcdActivationDate) {
		this.chkPcdActivationDate = chkPcdActivationDate;
	}

	public String getPcdActivationDate() {
		return pcdActivationDate;
	}

	public void setPcdActivationDate(String pcdActivationDate) {
		this.pcdActivationDate = pcdActivationDate;
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

	public List<ItemDetailDTO> getEpdItemList() {
		return epdItemList;
	}

	public void setEpdItemList(List<ItemDetailDTO> epdItemList) {
		this.epdItemList = epdItemList;
	}

	public int getEpdLeadDay() {
		return epdLeadDay;
	}

	public void setEpdLeadDay(int epdLeadDay) {
		this.epdLeadDay = epdLeadDay;
	}
	

	
}
