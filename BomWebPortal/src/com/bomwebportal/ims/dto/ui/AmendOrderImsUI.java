package com.bomwebportal.ims.dto.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AmendmentModeDTO;
import com.bomwebportal.ims.dto.OrderImsDTO;

public class AmendOrderImsUI{
	
	/**
	 * ims steven created this file
	 */
	private static final long serialVersionUID = 5248514987697312232L;
	protected final Log logger = LogFactory.getLog(getClass());

	private String canAmendAppointment;
	private String twoAutoWQs;
	private String registerName;
	
	private String isDsSupport;
	
	private String fsTimeSlot;
	private String fsTimeSlotDisplay;
	private String fsPreBkSerialNum;
	private String fsDelayReason;
	private String fsDelayReasonCode;
	private String fsTargetInstallDate;
	private String fsAddtionalRemarks;
	private String fsTargetCommDate;
	
	private String fsCancelReason;
	private String fsCancelReasonCode;
	private String fsCancelRemark;
	private String fsCancelType;
	
	private String cancelReason;
	private String cancelReasonCode;
	private String delayReason;
	private String delayReasonCode;
	private String cancelRemark;
	private OrderImsUI orderImsUI;
	private String expiryMonth;
	private String expiryYear;
	private String ccExpDate;
	private String ccType;
	private String ccHolderName;
	private String ccNum;
	private String applicantName;
	private String thirdPartyInd;
	private String faxSerialNum;
	
	private String applicantContactNo;
	private String applicantRelationship;

	private String amendNature1;
	private String amendNature2;
	private String amendNature3;
	private String amendRemark1;
	private String amendRemark2;
	private String amendRemark3;
	private BomSalesUserDTO bomSalesUserDTO;
	private String imsAmendJustDone;
	private String preBkSerialNum;
	private String timeSlotType;

	private List<String> wqNatureIDList;
	private String isDOrderSelfPickEnabled;
	private String isCancelOrderEnabled;
	private String isAppointmentEnabled;
	private String isFsAmendEnabled;
	private String isUpdateCreditEnabled;
	private String isChangeProgOfferEnabled;
	private RegAmendSRDUI regAmendSRDUI;
	
	private String TimeSlot;
	private String TimeSlotDisplay;
	private String createdBy;
	
	private String commonErrorArea;
	private String isPaymentMethodIsCC;
	
	private String bomSRD;
	
	private String progOfferChangeRemark;
	private String allowProgOfferChange;
	private String allowTvContentChange;
	
	
	//celia 
	private DisMode disMode;
	private String esigEmailAddr;
	private EsigEmailLang esigEmailLang;
	private String emailAddr;
	private String SMSno;
	private String seqNo;
	private String NotificationSendConfirmInd;
	
	
	private String loginId;
	private String contactEmail;
	private String isLoginIdEnabled;
	private String isContactEmailEnabled;
	private int autoWQCount = 0;
	private String isPreInstCommDateEnabled;
	private String isPreInstApptEnabled;

	private String isContactMobileEnabled;
	private String contactMobile;
	
	private String allowAmendStateAppear;
	
	private AmendmentModeDTO amendModeDto;
	
	private String dorderFsTimeSlot;
	private String dorderFsTimeSlotDisplay;
	private String dorderFsPreBkSerialNum;
	private String dorderFsDelayReason;
	private String dorderFsDelayReasonCode;
	private String dorderFsTargetInstallDate;
	private String dorderFsAddtionalRemarks;
	private String dorderFsTargetCommDate;
	private String termDorderSelfReturn;
	private String isQualifiedCategory;
	private String isDorder;
	private String isCollectRequestClicked;
	private String isVisitWithChargeClicked;
	private String isVisitWithoutChargeClicked;
	
	
	public String getApplicantName() {
		return applicantName;
	}

	public void setApplicantName(String applicantName) {
		this.applicantName = applicantName;
	}

	public String getApplicantContactNo() {
		return applicantContactNo;
	}

	public void setApplicantContactNo(String applicantContactNo) {
		this.applicantContactNo = applicantContactNo;
	}

	public String getApplicantRelationship() {
		return applicantRelationship;
	}

	public void setApplicantRelationship(String applicantRelationship) {
		this.applicantRelationship = applicantRelationship;
	}
	public String getTimeSlot() {
		return TimeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		TimeSlot = timeSlot;
	}

	public String getTimeSlotDisplay() {
		return TimeSlotDisplay;
	}

	public void setTimeSlotDisplay(String timeSlotDisplay) {
		TimeSlotDisplay = timeSlotDisplay;
	}
	

	public String getIsCancelOrderEnabled() {
		return isCancelOrderEnabled;
	}

	public void setIsCancelOrderEnabled(String isCancelOrderEnabled) {
		this.isCancelOrderEnabled = isCancelOrderEnabled;
	}

	public String getIsAppointmentEnabled() {
		return isAppointmentEnabled;
	}

	public void setIsAppointmentEnabled(String isAppointmentEnabled) {
		this.isAppointmentEnabled = isAppointmentEnabled;
	}

	public String getIsFsAmendEnabled() {
		return isFsAmendEnabled;
	}

	public void setIsFsAmendEnabled(String isFsAmendEnabled) {
		this.isFsAmendEnabled = isFsAmendEnabled;
	}

	public String getIsUpdateCreditEnabled() {
		return isUpdateCreditEnabled;
	}

	public void setIsUpdateCreditEnabled(String isUpdateCreditEnabled) {
		this.isUpdateCreditEnabled = isUpdateCreditEnabled;
	}

	public void setOrderImsUI(OrderImsUI orderImsUI) {
		this.orderImsUI = orderImsUI;
	}

	public OrderImsUI getOrderImsUI() {
		return orderImsUI;
	}

	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}

	public String getCancelRemark() {
		return cancelRemark;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}

	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	public void setCcExpDate(String ccExpDate) {
		this.ccExpDate = ccExpDate;
	}

	public String getCcExpDate() {
		return ccExpDate;
	}

	public void setCcType(String ccType) {
		this.ccType = ccType;
	}

	public String getCcType() {
		return ccType;
	}

	public String isFS() {
		if("Y".equalsIgnoreCase(this.getIsFsAmendEnabled())|| // F&S or jacky new WQ
		        "Y".equalsIgnoreCase(this.getIsChangeProgOfferEnabled())){
			return "Y";
		}
		return "N";
	}
	public void printAmend( ) {		
		this.setWqNatureIDList(new ArrayList<String>());
		this.setTwoAutoWQs("N");
		this.setAutoWQCount(0);
		if("Y".equalsIgnoreCase(this.getIsCancelOrderEnabled())){ // Cancel order alone
			this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_CancelOrder);
		}else if("Y".equals(this.getIsDOrderSelfPickEnabled())){// d order self pick swap alone
			this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_DorderSwapSelfPickFs);			
		}else if("Y".equals(this.isFS())){			
			if(this.getAmendNature1()!=null && !this.getAmendNature1().equals("")){
				this.getWqNatureIDList().add(this.getAmendNature1());
			}
			if(this.getAmendNature2()!=null && !this.getAmendNature2().equals("")){
				this.getWqNatureIDList().add(this.getAmendNature2());
			}
			if(this.getAmendNature3()!=null && !this.getAmendNature3().equals("")){
				this.getWqNatureIDList().add(this.getAmendNature3());
			}
			if("Y".equalsIgnoreCase(this.getIsChangeProgOfferEnabled())){
				if("NTV-A".equals(this.orderImsUI.getOrderType())||this.orderImsUI.isOrderTypeNowRet()){
					this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_TV_CONTENT_CHANGE);						
				}else{
					this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_DS_offer_Plan_VAS_Premium);					
				}
			}
			if("Y".equalsIgnoreCase(this.getIsUpdateCreditEnabled())){
				if( !ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC_FS.equalsIgnoreCase(this.getAmendNature1())&&
					!ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC_FS.equalsIgnoreCase(this.getAmendNature2())&&
					!ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC_FS.equalsIgnoreCase(this.getAmendNature3()))
					{
						this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC_FS);
					}
			}
			if("Y".equalsIgnoreCase(this.getIsAppointmentEnabled())){
				if( !ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS.equalsIgnoreCase(this.getAmendNature1())&&
					!ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS.equalsIgnoreCase(this.getAmendNature2())&&
					!ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS.equalsIgnoreCase(this.getAmendNature3()))
						{
							this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS);
						}
			}
		}else if("Y".equalsIgnoreCase(this.getIsUpdateCreditEnabled())||"Y".equalsIgnoreCase(this.getIsAppointmentEnabled())
				||"Y".equalsIgnoreCase(this.getIsLoginIdEnabled())||"Y".equalsIgnoreCase(this.getIsContactEmailEnabled())){
			if("Y".equalsIgnoreCase(this.getIsUpdateCreditEnabled())){
				this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC);
				this.setAutoWQCount(this.getAutoWQCount()+1);
			}
			if("Y".equalsIgnoreCase(this.getIsAppointmentEnabled())){
				this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD);
				this.setAutoWQCount(this.getAutoWQCount()+1);
			}
			if("Y".equalsIgnoreCase(this.getIsLoginIdEnabled())){
				this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_ChangeLoginId);
				this.setAutoWQCount(this.getAutoWQCount()+1);
			}
			if("Y".equalsIgnoreCase(this.getIsContactEmailEnabled())){
				this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateContEmail);
				this.setAutoWQCount(this.getAutoWQCount()+1);
			}
			if("Y".equalsIgnoreCase(this.getIsContactMobileEnabled())){
				this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateContMobile);
				this.setAutoWQCount(this.getAutoWQCount()+1);
			}
		}
		
		/*
		else if("Y".equalsIgnoreCase(this.getIsUpdateCreditEnabled())&&"Y".equalsIgnoreCase(this.getIsAppointmentEnabled())){
			this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC);
			this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD);
			this.setTwoAutoWQs("Y");
		}else if("Y".equalsIgnoreCase(this.getIsUpdateCreditEnabled())){ // CC alone
			this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_UpdateCC);
		}else {
			this.getWqNatureIDList().add(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD); // Appointment alone
		} */
		// for wq nature end

		if(!"Y".equals(this.getIsUpdateCreditEnabled())){
			this.setThirdPartyInd(null);
		}		
	}

	public String getAmendRemark1() {
		return amendRemark1;
	}

	public void setAmendRemark1(String amendRemark1) {
		this.amendRemark1 = amendRemark1;
	}

	public String getAmendRemark2() {
		return amendRemark2;
	}

	public void setAmendRemark2(String amendRemark2) {
		this.amendRemark2 = amendRemark2;
	}

	public String getAmendRemark3() {
		return amendRemark3;
	}

	public void setAmendRemark3(String amendRemark3) {
		this.amendRemark3 = amendRemark3;
	}

	public void setRegAmendSRDUI(RegAmendSRDUI regAmendSRDUI) {
		this.regAmendSRDUI = regAmendSRDUI;
	}

	public RegAmendSRDUI getRegAmendSRDUI() {
		return regAmendSRDUI;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setDelayReason(String delayReason) {
		this.delayReason = delayReason;
	}

	public String getDelayReason() {
		return delayReason;
	}

	public void setCommonErrorArea(String commonErrorArea) {
		this.commonErrorArea = commonErrorArea;
	}

	public String getCommonErrorArea() {
		return commonErrorArea;
	}

	public String getAmendNature1() {
		return amendNature1;
	}

	public void setAmendNature1(String amendNature1) {
		this.amendNature1 = amendNature1;
	}

	public String getAmendNature2() {
		return amendNature2;
	}

	public void setAmendNature2(String amendNature2) {
		this.amendNature2 = amendNature2;
	}

	public String getAmendNature3() {
		return amendNature3;
	}

	public void setAmendNature3(String amendNature3) {
		this.amendNature3 = amendNature3;
	}

	public void setBomSalesUserDTO(BomSalesUserDTO bomSalesUserDTO) {
		this.bomSalesUserDTO = bomSalesUserDTO;
	}

	public BomSalesUserDTO getBomSalesUserDTO() {
		return bomSalesUserDTO;
	}

	public void setDelayReasonCode(String delayReasonCode) {
		this.delayReasonCode = delayReasonCode;
	}

	public String getDelayReasonCode() {
		return delayReasonCode;
	}

	public void setImsAmendJustDone(String imsAmendJustDone) {
		this.imsAmendJustDone = imsAmendJustDone;
	}

	public String getImsAmendJustDone() {
		return imsAmendJustDone;
	}

	public void setPreBkSerialNum(String preBkSerialNum) {
		this.preBkSerialNum = preBkSerialNum;
	}

	public String getPreBkSerialNum() {
		return preBkSerialNum;
	}

	public String getCcHolderName() {
		return ccHolderName;
	}

	public void setCcHolderName(String ccHolderName) {
		this.ccHolderName = ccHolderName;
	}

	public String getCcNum() {
		return ccNum;
	}

	public void setCcNum(String ccNum) {
		this.ccNum = ccNum;
	}

	public void setIsPaymentMethodIsCC(String isPaymentMethodIsCC) {
		this.isPaymentMethodIsCC = isPaymentMethodIsCC;
	}

	public String getIsPaymentMethodIsCC() {
		return isPaymentMethodIsCC;
	}

	public void setWqNatureIDList(List<String> wqNatureIDList) {
		this.wqNatureIDList = wqNatureIDList;
	}

	public List<String> getWqNatureIDList() {
		return wqNatureIDList;
	}

	public void setCancelReasonCode(String cancelReasonCode) {
		this.cancelReasonCode = cancelReasonCode;
	}

	public String getCancelReasonCode() {
		return cancelReasonCode;
	}

	public void setThirdPartyInd(String thirdPartyInd) {
		this.thirdPartyInd = thirdPartyInd;
	}

	public String getThirdPartyInd() {
		return thirdPartyInd;
	}

	public void setFaxSerialNum(String faxSerialNum) {
		this.faxSerialNum = faxSerialNum;
	}

	public String getFaxSerialNum() {
		return faxSerialNum;
	}

	public void setBomSRD(String bomSRD) {
		this.bomSRD = bomSRD;
	}

	public String getBomSRD() {
		return bomSRD;
	}

	public boolean isFSSrd() {
		for(String id:this.getWqNatureIDList()){
			if(ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD_FS.equals(id)){
				return true;
			}
		}
		return false;
	}

	public boolean isAutoFlowSrd() {
		if(this.getWqNatureIDList().size()==1 && ImsConstants.IMS_AMEND_WQ_NATURE_AmendSRD.equals(this.getWqNatureIDList().get(0))){
				return true;
		}
		return false;
	}

	public void setTimeSlotType(String timeSlotType) {
		this.timeSlotType = timeSlotType;
	}

	public String getTimeSlotType() {
		return timeSlotType;
	}

	public String getFsTimeSlot() {
		return fsTimeSlot;
	}

	public void setFsTimeSlot(String fsTimeSlot) {
		this.fsTimeSlot = fsTimeSlot;
	}

	public String getFsTimeSlotDisplay() {
		return fsTimeSlotDisplay;
	}

	public void setFsTimeSlotDisplay(String fsTimeSlotDisplay) {
		this.fsTimeSlotDisplay = fsTimeSlotDisplay;
	}

	public String getFsPreBkSerialNum() {
		return fsPreBkSerialNum;
	}

	public void setFsPreBkSerialNum(String fsPreBkSerialNum) {
		this.fsPreBkSerialNum = fsPreBkSerialNum;
	}

	public String getFsDelayReason() {
		return fsDelayReason;
	}

	public void setFsDelayReason(String fsDelayReason) {
		this.fsDelayReason = fsDelayReason;
	}

	public String getFsDelayReasonCode() {
		return fsDelayReasonCode;
	}

	public void setFsDelayReasonCode(String fsDelayReasonCode) {
		this.fsDelayReasonCode = fsDelayReasonCode;
	}

	public String getFsTargetInstallDate() {
		return fsTargetInstallDate;
	}

	public void setFsTargetInstallDate(String fsTargetInstallDate) {
		this.fsTargetInstallDate = fsTargetInstallDate;
	}
	
	public String getFsAddtionalRemarks() {
		return fsAddtionalRemarks;
	}
	
	public void setFsAddtionalRemarks(String fsAddtionalRemarks) {
		this.fsAddtionalRemarks = fsAddtionalRemarks;
	}

	public String getFsCancelReason() {
		return fsCancelReason;
	}

	public void setFsCancelReason(String fsCancelReason) {
		this.fsCancelReason = fsCancelReason;
	}

	public String getFsCancelReasonCode() {
		return fsCancelReasonCode;
	}

	public void setFsCancelReasonCode(String fsCancelReasonCode) {
		this.fsCancelReasonCode = fsCancelReasonCode;
	}

	public String getFsCancelRemark() {
		return fsCancelRemark;
	}

	public void setFsCancelRemark(String fsCancelRemark) {
		this.fsCancelRemark = fsCancelRemark;
	}

	public String getFsCancelType() {
		return fsCancelType;
	}

	public void setFsCancelType(String fsCancelType) {
		this.fsCancelType = fsCancelType;
	}

	public void setCanAmendAppointment(String canAmendAppointment) {
		this.canAmendAppointment = canAmendAppointment;
	}

	public String getCanAmendAppointment() {
		return canAmendAppointment;
	}

	public void setProgOfferChangeRemark(String progOfferChangeRemark) {
		this.progOfferChangeRemark = progOfferChangeRemark;
	}

	public String getProgOfferChangeRemark() {
		return progOfferChangeRemark;
	}

	public void setIsChangeProgOfferEnabled(String isChangeProgOfferEnabled) {
		this.isChangeProgOfferEnabled = isChangeProgOfferEnabled;
	}

	public String getIsChangeProgOfferEnabled() {
		return isChangeProgOfferEnabled;
	}

	public void setDisMode(DisMode disMode) {
		this.disMode = disMode;
	}

	public DisMode getDisMode() {
		return disMode;
	}

	public void setEsigEmailAddr(String esigEmailAddr) {
		this.esigEmailAddr = esigEmailAddr;
	}

	public String getEsigEmailAddr() {
		return esigEmailAddr;
	}

	public void setEsigEmailLang(EsigEmailLang esigEmailLang) {
		this.esigEmailLang = esigEmailLang;
	}

	public EsigEmailLang getEsigEmailLang() {
		return esigEmailLang;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setSMSno(String sMSno) {
		SMSno = sMSno;
	}

	public String getSMSno() {
		return SMSno;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getSeqNo() {
		return seqNo;
	}

	public void setTwoAutoWQs(String twoAutoWQs) {
		this.twoAutoWQs = twoAutoWQs;
	}

	public String getTwoAutoWQs() {
		return twoAutoWQs;
	}

	public void setRegisterName(String registerName) {
		this.registerName = registerName;
	}

	public String getRegisterName() {
		return registerName;
	}

	public void setNotificationSendConfirmInd(String notificationSendConfirmInd) {
		NotificationSendConfirmInd = notificationSendConfirmInd;
	}

	public String getNotificationSendConfirmInd() {
		return NotificationSendConfirmInd;
	}

	public void setAllowProgOfferChange(String allowProgOfferChange) {
		this.allowProgOfferChange = allowProgOfferChange;
	}

	public String getAllowProgOfferChange() {
		return allowProgOfferChange;
	}

	public void setIsDsSupport(String isDsSupport) {
		this.isDsSupport = isDsSupport;
	}

	public String getIsDsSupport() {
		return isDsSupport;
	}

	public void setAllowTvContentChange(String allowTvContentChange) {
		this.allowTvContentChange = allowTvContentChange;
	}

	public String getAllowTvContentChange() {
		return allowTvContentChange;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	public String getLoginId() {
		return loginId;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setIsLoginIdEnabled(String isLoginIdEnabled) {
		this.isLoginIdEnabled = isLoginIdEnabled;
	}

	public String getIsLoginIdEnabled() {
		return isLoginIdEnabled;
	}

	public void setIsContactEmailEnabled(String isContactEmailEnabled) {
		this.isContactEmailEnabled = isContactEmailEnabled;
	}

	public String getIsContactEmailEnabled() {
		return isContactEmailEnabled;
	}

	public void setAutoWQCount(int autoWQCount) {
		this.autoWQCount = autoWQCount;
	}

	public int getAutoWQCount() {
		return autoWQCount;
	}	
	public void setIsPreInstCommDateEnabled(String isPreInstCommDateEnabled) {
		this.isPreInstCommDateEnabled = isPreInstCommDateEnabled;
	}

	public String getIsPreInstCommDateEnabled() {
		return isPreInstCommDateEnabled;
	}

	public void setIsPreInstApptEnabled(String isPreInstApptEnabled) {
		this.isPreInstApptEnabled = isPreInstApptEnabled;
	}

	public String getIsPreInstApptEnabled() {
		return isPreInstApptEnabled;
	}

	public void setFsTargetCommDate(String fsTargetCommDate) {
		this.fsTargetCommDate = fsTargetCommDate;
	}

	public String getFsTargetCommDate() {
		return fsTargetCommDate;
	}

	public void setAllowAmendStateAppear(String allowAmendStateAppear) {
		this.allowAmendStateAppear = allowAmendStateAppear;
	}

	public String getAllowAmendStateAppear() {
		return allowAmendStateAppear;
	}

	public void setAmendModeDto(AmendmentModeDTO amendModeDto) {
		this.amendModeDto = amendModeDto;
	}

	public AmendmentModeDTO getAmendModeDto() {
		return amendModeDto;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public String getContactMobile() {
		return contactMobile;
	}

	public void setIsContactMobileEnabled(String isContactMobileEnabled) {
		this.isContactMobileEnabled = isContactMobileEnabled;
	}

	public String getIsContactMobileEnabled() {
		return isContactMobileEnabled;
	}

	public void setIsDOrderSelfPickEnabled(String isDOrderSelfPickEnabled) {
		this.isDOrderSelfPickEnabled = isDOrderSelfPickEnabled;
	}

	public String getIsDOrderSelfPickEnabled() {
		return isDOrderSelfPickEnabled;
	}

	public String getDorderFsTimeSlot() {
		return dorderFsTimeSlot;
	}

	public void setDorderFsTimeSlot(String dorderFsTimeSlot) {
		this.dorderFsTimeSlot = dorderFsTimeSlot;
	}

	public String getDorderFsTimeSlotDisplay() {
		return dorderFsTimeSlotDisplay;
	}

	public void setDorderFsTimeSlotDisplay(String dorderFsTimeSlotDisplay) {
		this.dorderFsTimeSlotDisplay = dorderFsTimeSlotDisplay;
	}

	public String getDorderFsPreBkSerialNum() {
		return dorderFsPreBkSerialNum;
	}

	public void setDorderFsPreBkSerialNum(String dorderFsPreBkSerialNum) {
		this.dorderFsPreBkSerialNum = dorderFsPreBkSerialNum;
	}

	public String getDorderFsDelayReason() {
		return dorderFsDelayReason;
	}

	public void setDorderFsDelayReason(String dorderFsDelayReason) {
		this.dorderFsDelayReason = dorderFsDelayReason;
	}

	public String getDorderFsDelayReasonCode() {
		return dorderFsDelayReasonCode;
	}

	public void setDorderFsDelayReasonCode(String dorderFsDelayReasonCode) {
		this.dorderFsDelayReasonCode = dorderFsDelayReasonCode;
	}

	public String getDorderFsTargetInstallDate() {
		return dorderFsTargetInstallDate;
	}

	public void setDorderFsTargetInstallDate(String dorderFsTargetInstallDate) {
		this.dorderFsTargetInstallDate = dorderFsTargetInstallDate;
	}

	public String getDorderFsAddtionalRemarks() {
		return dorderFsAddtionalRemarks;
	}

	public void setDorderFsAddtionalRemarks(String dorderFsAddtionalRemarks) {
		this.dorderFsAddtionalRemarks = dorderFsAddtionalRemarks;
	}

	public String getDorderFsTargetCommDate() {
		return dorderFsTargetCommDate;
	}

	public void setDorderFsTargetCommDate(String dorderFsTargetCommDate) {
		this.dorderFsTargetCommDate = dorderFsTargetCommDate;
	}

	public void setTermDorderSelfReturn(String termDorderSelfReturn) {
		this.termDorderSelfReturn = termDorderSelfReturn;
	}

	public String getTermDorderSelfReturn() {
		return termDorderSelfReturn;
	}

	public void setIsQualifiedCategory(String isQualifiedCategory) {
		this.isQualifiedCategory = isQualifiedCategory;
	}

	public String getIsQualifiedCategory() {
		return isQualifiedCategory;
	}

	public void setIsDorder(String isDorder) {
		this.isDorder = isDorder;
	}

	public String getIsDorder() {
		return isDorder;
	}

	public String getIsCollectRequestClicked() {
		return isCollectRequestClicked;
	}

	public void setIsCollectRequestClicked(String isCollectRequestClicked) {
		this.isCollectRequestClicked = isCollectRequestClicked;
	}

	public String getIsVisitWithChargeClicked() {
		return isVisitWithChargeClicked;
	}

	public void setIsVisitWithChargeClicked(String isVisitWithChargeClicked) {
		this.isVisitWithChargeClicked = isVisitWithChargeClicked;
	}

	public String getIsVisitWithoutChargeClicked() {
		return isVisitWithoutChargeClicked;
	}

	public void setIsVisitWithoutChargeClicked(String isVisitWithoutChargeClicked) {
		this.isVisitWithoutChargeClicked = isVisitWithoutChargeClicked;
	}



}
