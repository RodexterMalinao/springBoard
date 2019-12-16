package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bomwebportal.lts.dto.LtsAppointmentDetail;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbAddressDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.AmendOrderRecDTO;
import com.bomwebportal.lts.dto.order.PipbLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;

public class LtsOrderAmendmentFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2242410158342416826L;

	private String orderType;
	private String sbOrderNum;
	private String bomOcId;
	private String serNum;
	private String pcdLoginId;
	private String serviceType;
	
	private ServiceDetailProfileLtsDTO ltsServiceProfile;
	
	private String staffNum;
	private String salesId;
	private String salesChannel;
	private String shopCode;
	private String salesContactNum;
	
	private String name;
	private String contactNum;
	private String relationship;
	private String noticeSmsNum;
	private String noticeEmailAddr;

	private String cancelAllowReason;
	private String cancelType;
	private String cancelReason;
	private String cancelRemark;

	private LtsSRDDTO earliestSRD;
	private String bmoRemark;
	private String bmoAlertMsg;
	private String errorMsg;
	private String preBookSerialNum;
	private String originalPreWiringDate;
	private String originalPreWiringTime;
	private String originalInstallationDate;
	private String originalInstallationTime;
	private String originalSecDelInstallationDate;
	private String originalSecDelInstallationTime;
	private String originalPortLaterDate;
	private String originalPortLaterTime;
	private String delayReasonCode;
	private String pipbMaxLeadTime;
	
	private LtsAppointmentDetail preWiringAppntDtl;
	private LtsAppointmentDetail coreSrvAppntDtl;
	private LtsAppointmentDetail secDelAppntDtl;
	private LtsAppointmentDetail portLaterAppntDtl;
	
	private Boolean thirdPartyCard;
	private String cardHolderName;
	private String cardHolderDocType;
	private String cardHolderDocNum;
	private String cardNum;
	private String cardType;
	private int expireMonth;
	private int expireYear;
	private boolean cardVerified;
	
	private boolean category1Ind;
	private String category1;
	private String content1;
	private boolean category2Ind;
	private String category2;
	private String content2;
	private boolean category3Ind;
	private String category3;
	private String content3;

	private boolean secDelCategory1Ind;
	private String secDelCategory1;
	private String secDelContent1;
	private boolean secDelCategory2Ind;
	private String secDelCategory2;
	private String secDelContent2;
	private boolean secDelCategory3Ind;
	private String secDelCategory3;
	private String secDelContent3;
	
	//ACQ PIPB Amendment fields
	private boolean acqOrderCompleted;
	private boolean pipbWqRejectedOrNotExist;
	private boolean pipbSrdDaysEnough;
	private boolean pipbAmendAfterCutOver;
	private PipbLtsDTO pipbInfo;
	private boolean pipbAmendCustNameInd;
	private boolean pipbAmendFlatFloorInd;
	private boolean pipbAmendDiffCustInd;
	private boolean pipbAmendDiffAddrInd;
	private boolean brCust;
	private String pipbCompanyName;
	private String pipbTitle;
	private String pipbFirstName;
	private String pipbLastName;
	private String pipbFlat;
	private String pipbFloor;
	private String pipbDiffAddr;
	private String pipbDiffCustTitle;
	private String pipbDiffCustFirstName;
	private String pipbDiffCustLastName;
	private LtsAcqPipbAddressDTO address;
	private LtsSRDDTO pipbTwoNBWSrd;
	private String pipbTwoNBWSrdReason;
	private int pipbPortLaterleadtime;
	private String pcdActivationDate;
	
	//ind
	private boolean cancelOrderInd;
	private boolean cancelAllowInd;
	private boolean amendAppointmentInd;
	private boolean penaltyWaivedCaseInd;
	private boolean allowAppointmentAmendInd;
	private boolean amendCreditCardInd;
	private boolean allowCreditCardAmendInd;
	private boolean amendAcqPipbInd;
	private boolean allowAcqPipbAmendInd;
	private boolean amendDocInd;
	private boolean allowDocAmendInd;
	private boolean allowOtherCategoryAmendInd;

	//LTS Max
    private boolean investigationInd;
     
	private String categorySelected;
	private String confirmedInd;
	private String amendedInd;
	private String sharePCDInd;
//	private String cutOverInd;
//	private String secDelInd;
//	private String secDelCutOverInd;
	private String bbShortageInd;
	private String pcdOrderExistInd;
	private String submitInd;
	
	private AmendOrderRecDTO[] amendHistory;
	
	private boolean firstMonthCcPrepaymentInd;
	private boolean showPrepaymentRejectInd;
	private boolean prepaymentRejectInd;
	
	private String faxSerialNum;
	
	private boolean isCreditCard;
	
	/*for amendment submission*/
	private AmendLtsDTO amendLtsDTO;
//	private List<String[]> requiredDocList;
	private Map<String, String> requiredDocMap;
	
	private List<String> alertMsgList = new ArrayList<String>();
	
	
	public String getSbOrderNum() {
		return sbOrderNum;
	}
	public void setSbOrderNum(String sbOrderNum) {
		this.sbOrderNum = sbOrderNum;
	}
	public String getBomOcId() {
		return bomOcId;
	}
	public void setBomOcId(String bomOcId) {
		this.bomOcId = bomOcId;
	}
	public String getSerNum() {
		return serNum;
	}
	public void setSerNum(String serNum) {
		this.serNum = serNum;
	}
	public String getPcdLoginId() {
		return pcdLoginId;
	}
	public void setPcdLoginId(String pcdLoginId) {
		this.pcdLoginId = pcdLoginId;
	}
	public String getStaffNum() {
		return staffNum;
	}
	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}
	public String getSalesId() {
		return salesId;
	}
	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}
	public String getSalesChannel() {
		return salesChannel;
	}
	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getSalesContactNum() {
		return salesContactNum;
	}
	public void setSalesContactNum(String salesContactNum) {
		this.salesContactNum = salesContactNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactNum() {
		return contactNum;
	}
	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	public boolean isCancelOrderInd() {
		return cancelOrderInd;
	}
	public void setCancelOrderInd(boolean cancelOrderInd) {
		this.cancelOrderInd = cancelOrderInd;
	}
	public boolean isAmendAppointmentInd() {
		return amendAppointmentInd;
	}
	public void setAmendAppointmentInd(boolean amendAppointmentInd) {
		this.amendAppointmentInd = amendAppointmentInd;
	}
	public boolean isAmendCreditCardInd() {
		return amendCreditCardInd;
	}
	public void setAmendCreditCardInd(boolean amendCreditCardInd) {
		this.amendCreditCardInd = amendCreditCardInd;
	}
	public String getCancelType() {
		return cancelType;
	}
	public void setCancelType(String cancelType) {
		this.cancelType = cancelType;
	}
	public String getPreBookSerialNum() {
		return preBookSerialNum;
	}
	public void setPreBookSerialNum(String preBookSerialNum) {
		this.preBookSerialNum = preBookSerialNum;
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
	public String getDelayReasonCode() {
		return delayReasonCode;
	}
	public void setDelayReasonCode(String delayReasonCode) {
		this.delayReasonCode = delayReasonCode;
	}
	public Boolean getThirdPartyCard() {
		return thirdPartyCard;
	}
	public void setThirdPartyCard(Boolean thirdPartyCard) {
		this.thirdPartyCard = thirdPartyCard;
	}
	
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getCardHolderDocType() {
		return cardHolderDocType;
	}
	public void setCardHolderDocType(String cardHolderDocType) {
		this.cardHolderDocType = cardHolderDocType;
	}
	public String getCardHolderDocNum() {
		return cardHolderDocNum;
	}
	public void setCardHolderDocNum(String cardHolderDocNum) {
		this.cardHolderDocNum = cardHolderDocNum;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public int getExpireMonth() {
		return expireMonth;
	}
	public void setExpireMonth(int expireMonth) {
		this.expireMonth = expireMonth;
	}
	public int getExpireYear() {
		return expireYear;
	}
	public void setExpireYear(int expireYear) {
		this.expireYear = expireYear;
	}
	public boolean isCardVerified() {
		return cardVerified;
	}
	public void setCardVerified(boolean cardVerified) {
		this.cardVerified = cardVerified;
	}
	public String getCategory1() {
		return category1;
	}
	public void setCategory1(String category1) {
		this.category1 = category1;
	}
	public String getContent1() {
		return content1;
	}
	public void setContent1(String content1) {
		this.content1 = content1;
	}
	public String getCategory2() {
		return category2;
	}
	public void setCategory2(String category2) {
		this.category2 = category2;
	}
	public String getContent2() {
		return content2;
	}
	public void setContent2(String content2) {
		this.content2 = content2;
	}
	public String getCategory3() {
		return category3;
	}
	public void setCategory3(String category3) {
		this.category3 = category3;
	}
	public String getContent3() {
		return content3;
	}
	public void setContent3(String content3) {
		this.content3 = content3;
	}
	public String getSubmitInd() {
		return submitInd;
	}
	public void setSubmitInd(String submitInd) {
		this.submitInd = submitInd;
	}
	public LtsSRDDTO getEarliestSRD() {
		return earliestSRD;
	}
	public void setEarliestSRD(LtsSRDDTO earliestSRD) {
		this.earliestSRD = earliestSRD;
	}
	public String getCancelRemark() {
		return cancelRemark;
	}
	public void setCancelRemark(String cancelRemark) {
		this.cancelRemark = cancelRemark;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getSecDelCategory1() {
		return secDelCategory1;
	}
	public void setSecDelCategory1(String secDelCategory1) {
		this.secDelCategory1 = secDelCategory1;
	}
	public String getSecDelContent1() {
		return secDelContent1;
	}
	public void setSecDelContent1(String secDelContent1) {
		this.secDelContent1 = secDelContent1;
	}
	public String getSecDelCategory2() {
		return secDelCategory2;
	}
	public void setSecDelCategory2(String secDelCategory2) {
		this.secDelCategory2 = secDelCategory2;
	}
	public String getSecDelContent2() {
		return secDelContent2;
	}
	public void setSecDelContent2(String secDelContent2) {
		this.secDelContent2 = secDelContent2;
	}
	public String getSecDelCategory3() {
		return secDelCategory3;
	}
	public void setSecDelCategory3(String secDelCategory3) {
		this.secDelCategory3 = secDelCategory3;
	}
	public String getSecDelContent3() {
		return secDelContent3;
	}
	public void setSecDelContent3(String secDelContent3) {
		this.secDelContent3 = secDelContent3;
	}
	public String getAmendedInd() {
		return amendedInd;
	}
	public void setAmendedInd(String amendedInd) {
		this.amendedInd = amendedInd;
	}
	public boolean isCategory1Ind() {
		return category1Ind;
	}
	public void setCategory1Ind(boolean category1Ind) {
		this.category1Ind = category1Ind;
	}
	public boolean isCategory2Ind() {
		return category2Ind;
	}
	public void setCategory2Ind(boolean category2Ind) {
		this.category2Ind = category2Ind;
	}
	public boolean isCategory3Ind() {
		return category3Ind;
	}
	public void setCategory3Ind(boolean category3Ind) {
		this.category3Ind = category3Ind;
	}
	public boolean isSecDelCategory1Ind() {
		return secDelCategory1Ind;
	}
	public void setSecDelCategory1Ind(boolean secDelCategory1Ind) {
		this.secDelCategory1Ind = secDelCategory1Ind;
	}
	public boolean isSecDelCategory2Ind() {
		return secDelCategory2Ind;
	}
	public void setSecDelCategory2Ind(boolean secDelCategory2Ind) {
		this.secDelCategory2Ind = secDelCategory2Ind;
	}
	public boolean isSecDelCategory3Ind() {
		return secDelCategory3Ind;
	}
	public void setSecDelCategory3Ind(boolean secDelCategory3Ind) {
		this.secDelCategory3Ind = secDelCategory3Ind;
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
	public boolean isCancelAllowInd() {
		return cancelAllowInd;
	}
	public void setCancelAllowInd(boolean cancelAllowInd) {
		this.cancelAllowInd = cancelAllowInd;
	}
	public String getCancelAllowReason() {
		return cancelAllowReason;
	}
	public void setCancelAllowReason(String cancelAllowReason) {
		this.cancelAllowReason = cancelAllowReason;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public AmendOrderRecDTO[] getAmendHistory() {
		return this.amendHistory;
	}
	public void setAmendHistory(AmendOrderRecDTO[] pAmendHistory) {
		this.amendHistory = pAmendHistory;
	}
	public boolean isFirstMonthCcPrepaymentInd() {
		return firstMonthCcPrepaymentInd;
	}
	public void setFirstMonthCcPrepaymentInd(boolean firstMonthCcPrepaymentInd) {
		this.firstMonthCcPrepaymentInd = firstMonthCcPrepaymentInd;
	}
	public boolean isShowPrepaymentRejectInd() {
		return showPrepaymentRejectInd;
	}
	public void setShowPrepaymentRejectInd(boolean showPrepaymentRejectInd) {
		this.showPrepaymentRejectInd = showPrepaymentRejectInd;
	}
	public boolean isPrepaymentRejectInd() {
		return prepaymentRejectInd;
	}
	public void setPrepaymentRejectInd(boolean prepaymentRejectInd) {
		this.prepaymentRejectInd = prepaymentRejectInd;
	}
	public String getOriginalPreWiringDate() {
		return originalPreWiringDate;
	}
	public void setOriginalPreWiringDate(String originalPreWiringDate) {
		this.originalPreWiringDate = originalPreWiringDate;
	}
	public String getOriginalPreWiringTime() {
		return originalPreWiringTime;
	}
	public void setOriginalPreWiringTime(String originalPreWiringTime) {
		this.originalPreWiringTime = originalPreWiringTime;
	}
	public String getOriginalInstallationDate() {
		return originalInstallationDate;
	}
	public void setOriginalInstallationDate(String originalInstallationDate) {
		this.originalInstallationDate = originalInstallationDate;
	}
	public String getOriginalInstallationTime() {
		return originalInstallationTime;
	}
	public void setOriginalInstallationTime(String originalInstallationTime) {
		this.originalInstallationTime = originalInstallationTime;
	}
	public String getOriginalSecDelInstallationDate() {
		return originalSecDelInstallationDate;
	}
	public void setOriginalSecDelInstallationDate(
			String originalSecDelInstallationDate) {
		this.originalSecDelInstallationDate = originalSecDelInstallationDate;
	}
	public String getOriginalSecDelInstallationTime() {
		return originalSecDelInstallationTime;
	}
	public void setOriginalSecDelInstallationTime(
			String originalSecDelInstallationTime) {
		this.originalSecDelInstallationTime = originalSecDelInstallationTime;
	}
	public String getCategorySelected() {
		return categorySelected;
	}
	public void setCategorySelected(String categorySelected) {
		this.categorySelected = categorySelected;
	}
	public String getCancelReason() {
		return this.cancelReason;
	}
	public void setCancelReason(String pCancelReason) {
		this.cancelReason = pCancelReason;
	}
	public String getFaxSerialNum() {
		return this.faxSerialNum;
	}
	public void setFaxSerialNum(String pFaxSerialNum) {
		this.faxSerialNum = pFaxSerialNum;
	}
	public ServiceDetailProfileLtsDTO getLtsServiceProfile() {
		return ltsServiceProfile;
	}
	public void setLtsServiceProfile(ServiceDetailProfileLtsDTO ltsServiceProfile) {
		this.ltsServiceProfile = ltsServiceProfile;
	}
	public LtsAppointmentDetail getPreWiringAppntDtl() {
		return preWiringAppntDtl;
	}
	public void setPreWiringAppntDtl(LtsAppointmentDetail preWiringAppntDtl) {
		this.preWiringAppntDtl = preWiringAppntDtl;
	}
	public LtsAppointmentDetail getCoreSrvAppntDtl() {
		return coreSrvAppntDtl;
	}
	public void setCoreSrvAppntDtl(LtsAppointmentDetail coreSrvAppntDtl) {
		this.coreSrvAppntDtl = coreSrvAppntDtl;
	}
	public LtsAppointmentDetail getSecDelAppntDtl() {
		return secDelAppntDtl;
	}
	public void setSecDelAppntDtl(LtsAppointmentDetail secDelAppntDtl) {
		this.secDelAppntDtl = secDelAppntDtl;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public boolean isPipbAmendCustNameInd() {
		return pipbAmendCustNameInd;
	}
	public void setPipbAmendCustNameInd(boolean pipbAmendCustNameInd) {
		this.pipbAmendCustNameInd = pipbAmendCustNameInd;
	}
	public boolean isPipbAmendFlatFloorInd() {
		return pipbAmendFlatFloorInd;
	}
	public void setPipbAmendFlatFloorInd(boolean pipbAmendFlatFloorInd) {
		this.pipbAmendFlatFloorInd = pipbAmendFlatFloorInd;
	}
	public String getPipbTitle() {
		return pipbTitle;
	}
	public void setPipbTitle(String pipbTitle) {
		this.pipbTitle = pipbTitle;
	}
	public String getPipbFirstName() {
		return pipbFirstName;
	}
	public void setPipbFirstName(String pipbFirstName) {
		this.pipbFirstName = pipbFirstName;
	}
	public String getPipbLastName() {
		return pipbLastName;
	}
	public void setPipbLastName(String pipbLastName) {
		this.pipbLastName = pipbLastName;
	}
	public String getPipbFlat() {
		return pipbFlat;
	}
	public void setPipbFlat(String pipbFlat) {
		this.pipbFlat = pipbFlat;
	}
	public String getPipbFloor() {
		return pipbFloor;
	}
	public void setPipbFloor(String pipbFloor) {
		this.pipbFloor = pipbFloor;
	}
	public boolean isAmendAcqPipbInd() {
		return amendAcqPipbInd;
	}
	public void setAmendAcqPipbInd(boolean amendAcqPipbInd) {
		this.amendAcqPipbInd = amendAcqPipbInd;
	}
	public String getPipbDiffAddr() {
		return pipbDiffAddr;
	}
	public void setPipbDiffAddr(String pipbDiffAddr) {
		this.pipbDiffAddr = pipbDiffAddr;
	}
	public String getPipbDiffCustTitle() {
		return pipbDiffCustTitle;
	}
	public void setPipbDiffCustTitle(String pipbDiffCustTitle) {
		this.pipbDiffCustTitle = pipbDiffCustTitle;
	}
	public String getPipbDiffCustFirstName() {
		return pipbDiffCustFirstName;
	}
	public void setPipbDiffCustFirstName(String pipbDiffCustFirstName) {
		this.pipbDiffCustFirstName = pipbDiffCustFirstName;
	}
	public String getPipbDiffCustLastName() {
		return pipbDiffCustLastName;
	}
	public void setPipbDiffCustLastName(String pipbDiffCustLastName) {
		this.pipbDiffCustLastName = pipbDiffCustLastName;
	}
	public boolean isPipbAmendDiffCustInd() {
		return pipbAmendDiffCustInd;
	}
	public void setPipbAmendDiffCustInd(boolean pipbAmendDiffCustInd) {
		this.pipbAmendDiffCustInd = pipbAmendDiffCustInd;
	}
	public boolean isPipbAmendDiffAddrInd() {
		return pipbAmendDiffAddrInd;
	}
	public void setPipbAmendDiffAddrInd(boolean pipbAmendDiffAddrInd) {
		this.pipbAmendDiffAddrInd = pipbAmendDiffAddrInd;
	}
	public PipbLtsDTO getPipbInfo() {
		return pipbInfo;
	}
	public void setPipbInfo(PipbLtsDTO pipbInfo) {
		this.pipbInfo = pipbInfo;
	}
	public LtsAppointmentDetail getPortLaterAppntDtl() {
		return portLaterAppntDtl;
	}
	public void setPortLaterAppntDtl(LtsAppointmentDetail portLaterAppntDtl) {
		this.portLaterAppntDtl = portLaterAppntDtl;
	}
	public boolean isPipbSrdDaysEnough() {
		return pipbSrdDaysEnough;
	}
	public void setPipbSrdDaysEnough(boolean pipbSrdDaysEnough) {
		this.pipbSrdDaysEnough = pipbSrdDaysEnough;
	}
	public boolean isAmendDocInd() {
		return amendDocInd;
	}
	public void setAmendDocInd(boolean amendDocInd) {
		this.amendDocInd = amendDocInd;
	}
	public LtsAcqPipbAddressDTO getAddress() {
		return address;
	}
	public void setAddress(LtsAcqPipbAddressDTO address) {
		this.address = address;
	}
	public AmendLtsDTO getAmendLtsDTO() {
		return amendLtsDTO;
	}
	public void setAmendLtsDTO(AmendLtsDTO amendLtsDTO) {
		this.amendLtsDTO = amendLtsDTO;
	}
//	public List<String[]> getRequiredDocList() {
//		return requiredDocList;
//	}
//	public void setRequiredDocList(List<String[]> requiredDocList) {
//		this.requiredDocList = requiredDocList;
//	}
	public Map<String, String> getRequiredDocMap() {
		return requiredDocMap;
	}
	public void setRequiredDocMap(Map<String, String> requiredDocMap) {
		this.requiredDocMap = requiredDocMap;
	}
	public boolean isPipbAmendAfterCutOver() {
		return pipbAmendAfterCutOver;
	}
	public void setPipbAmendAfterCutOver(boolean pipbAmendAfterCutOver) {
		this.pipbAmendAfterCutOver = pipbAmendAfterCutOver;
	}
	public boolean isAcqOrderCompleted() {
		return acqOrderCompleted;
	}
	public void setAcqOrderCompleted(boolean acqOrderCompleted) {
		this.acqOrderCompleted = acqOrderCompleted;
	}
	public String getOriginalPortLaterDate() {
		return originalPortLaterDate;
	}
	public void setOriginalPortLaterDate(String originalPortLaterDate) {
		this.originalPortLaterDate = originalPortLaterDate;
	}
	public String getOriginalPortLaterTime() {
		return originalPortLaterTime;
	}
	public void setOriginalPortLaterTime(String originalPortLaterTime) {
		this.originalPortLaterTime = originalPortLaterTime;
	}
	public boolean isBrCust() {
		return brCust;
	}
	public void setBrCust(boolean brCust) {
		this.brCust = brCust;
	}
	public String getPipbCompanyName() {
		return pipbCompanyName;
	}
	public void setPipbCompanyName(String pipbCompanyName) {
		this.pipbCompanyName = pipbCompanyName;
	}
	public boolean isPipbWqRejectedOrNotExist() {
		return pipbWqRejectedOrNotExist;
	}
	public void setPipbWqRejectedOrNotExist(boolean pipbWqRejectedOrNotExist) {
		this.pipbWqRejectedOrNotExist = pipbWqRejectedOrNotExist;
	}
	public boolean isCreditCard() {
		return isCreditCard;
	}
	public void setCreditCard(boolean isCreditCard) {
		this.isCreditCard = isCreditCard;
	}
	/**
	 * @return the noticeSmsNum
	 */
	public String getNoticeSmsNum() {
		return noticeSmsNum;
	}
	/**
	 * @param noticeSmsNum the noticeSmsNum to set
	 */
	public void setNoticeSmsNum(String noticeSmsNum) {
		this.noticeSmsNum = noticeSmsNum;
	}
	/**
	 * @return the noticeEmailAddr
	 */
	public String getNoticeEmailAddr() {
		return noticeEmailAddr;
	}
	/**
	 * @param noticeEmailAddr the noticeEmailAddr to set
	 */
	public void setNoticeEmailAddr(String noticeEmailAddr) {
		this.noticeEmailAddr = noticeEmailAddr;
	}
	public String getPipbMaxLeadTime() {
		return pipbMaxLeadTime;
	}
	public void setPipbMaxLeadTime(String pipbMaxLeadTime) {
		this.pipbMaxLeadTime = pipbMaxLeadTime;
	}
	
	public LtsSRDDTO getPipbTwoNBWSrd() {
		return pipbTwoNBWSrd;
	}
	public void setPipbTwoNBWSrd(LtsSRDDTO pipbTwoNBWSrd) {
		this.pipbTwoNBWSrd = pipbTwoNBWSrd;
	}
	public String getPipbTwoNBWSrdReason() {
		return pipbTwoNBWSrdReason;
	}
	public void setPipbTwoNBWSrdReason(String pipbTwoNBWSrdReason) {
		this.pipbTwoNBWSrdReason = pipbTwoNBWSrdReason;
	}
	public boolean isAllowAppointmentAmendInd() {
		return allowAppointmentAmendInd;
	}
	public void setAllowAppointmentAmendInd(boolean allowAppointmentAmendInd) {
		this.allowAppointmentAmendInd = allowAppointmentAmendInd;
	}
	public boolean isAllowCreditCardAmendInd() {
		return allowCreditCardAmendInd;
	}
	public void setAllowCreditCardAmendInd(boolean allowCreditCardAmendInd) {
		this.allowCreditCardAmendInd = allowCreditCardAmendInd;
	}
	public boolean isAllowAcqPipbAmendInd() {
		return allowAcqPipbAmendInd;
	}
	public void setAllowAcqPipbAmendInd(boolean allowAcqPipbAmendInd) {
		this.allowAcqPipbAmendInd = allowAcqPipbAmendInd;
	}
	public boolean isAllowDocAmendInd() {
		return allowDocAmendInd;
	}
	public void setAllowDocAmendInd(boolean allowDocAmendInd) {
		this.allowDocAmendInd = allowDocAmendInd;
	}
	public boolean isInvestigationInd() {
		return investigationInd;
	}
	public void setInvestigationInd(boolean investigationInd) {
		this.investigationInd = investigationInd;
	}
	public boolean isAllowOtherCategoryAmendInd() {
		return allowOtherCategoryAmendInd;
	}
	public void setAllowOtherCategoryAmendInd(boolean allowOtherCategoryAmendInd) {
		this.allowOtherCategoryAmendInd = allowOtherCategoryAmendInd;
	}
	public boolean isPenaltyWaivedCaseInd() {
		return penaltyWaivedCaseInd;
	}
	public void setPenaltyWaivedCaseInd(boolean penaltyWaivedCaseInd) {
		this.penaltyWaivedCaseInd = penaltyWaivedCaseInd;
	}
	public int getPipbPortLaterleadtime() {
		return pipbPortLaterleadtime;
	}
	public void setPipbPortLaterleadtime(int pipbPortLaterleadtime) {
		this.pipbPortLaterleadtime = pipbPortLaterleadtime;
	}
	public List<String> getAlertMsgList() {
		return alertMsgList;
	}
	public void setAlertMsgList(List<String> alertMsgList) {
		this.alertMsgList = alertMsgList;
	}
	public String getPcdActivationDate() {
		return pcdActivationDate;
	}
	public void setPcdActivationDate(String pcdActivationDate) {
		this.pcdActivationDate = pcdActivationDate;
	}
	
}
