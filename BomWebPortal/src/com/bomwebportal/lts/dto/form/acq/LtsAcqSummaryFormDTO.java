package com.bomwebportal.lts.dto.form.acq;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.bomwebportal.lts.dto.acq.LtsAcqServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;

public class LtsAcqSummaryFormDTO implements Serializable {

	private static final long serialVersionUID = -3892336266451539260L;

	public static final int BUTTON_SIGNOFF = 0;
	public static final int BUTTON_APPROVE = 1;
	public static final int BUTTON_CANCEL = 2;
	public static final int BUTTON_DISABLE = 3;
	public static final int BUTTON_SUSPEND = 4;
	public static final int BUTTON_SIMPLE_SUSPEND = 5;
	public static final int BUTTON_REFRESH = 6;
	
	private String orderId = null;
	private List<LtsAcqServiceSummaryDTO> serviceSummaryList = null;
	private int buttonPressed = -1;
	private int requireButton = -1;

	private String salesChannelId = null;
	private String salesChannel = null;
	private String salesCd = null;
	private String salesTeam = null;
	private String staffNum = null;
	private String salesPosition = null;
	private String salesJob = null;
	private String salesProcessDate = null;
	private String salesContactNum = null;
	private String suspendReason = null;
	private String suspendRemarks = null;
	private String smsNo = null;
	private String createBy = null;
	private boolean dsOrderSubmit = false;
	
	private String legacyId = null;
	
	private List<String> messageList = new ArrayList<String>();
	private StringBuilder promptAlertMessage = new StringBuilder();
	
	private List<OrderStatusDTO> statusHistList = null;

	private String distributeMode = null;
	private String langPref = null;
	
	private boolean isOnlineAccqOrder = false;
	private boolean allowUpdateEdfRef = false;
	private boolean isOrderGeneratePenalty = false;
	private boolean payMtdTypeN = false;
	private boolean allowResendNameNotMatchWQ = false;
	
	private boolean containPrepayment;
	private String prepayAccountNumber = null;
	private String prepayAmount = null;
	private String prepayShopCode = null;
	private String prepayTransactionCode = null;
	private String prepayCancelReasonCode = null;
	
	private String qcCallTimePeriod = null;
	private String waiveQc = null;	
	private String mustQc = null;
	private String workQueueType = null;
	
	private String prepayDateYear = null;
	private String prepayDateMonth = null;
	private String prepayDateDay = null;
	private String prepayTimeHour = null;
	private String prepayTimeMin = null;
	private String prepayTimeSec = null;
	
	private String prepayDate = null;
		
	private String pcdSbid = null;
	private String redemMedia = null;
	private String redemptionEmail = null;
	private String redemptionSms = null;
	private String retypeDocNum = null;
	
	private String carecashInd = null;
	private String carecashDmInd = null;
	private String carecashEmail = null;
	private String carecashContactNum = null;
	
	private String preEnquiry = null;
	private String prepayStatus = null;
	
	private String signoffMode = null;
	
	public List<LtsAcqServiceSummaryDTO> getServiceSummaryList() {
		return serviceSummaryList;
	}

	public void setServiceSummaryList(List<LtsAcqServiceSummaryDTO> serviceSummaryList) {
		this.serviceSummaryList = serviceSummaryList;
	}

	public int getButtonPressed() {
		return buttonPressed;
	}

	public void setButtonPressed(int buttonPressed) {
		this.buttonPressed = buttonPressed;
	}

	public int getRequireButton() {
		return requireButton;
	}

	public void setRequireButton(int requireButton) {
		this.requireButton = requireButton;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSalesChannel() {
		return salesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		this.salesChannel = salesChannel;
	}

	public String getSalesCd() {
		return salesCd;
	}

	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}

	public String getSalesTeam() {
		return salesTeam;
	}

	public void setSalesTeam(String salesTeam) {
		this.salesTeam = salesTeam;
	}

	public String getStaffNum() {
		return staffNum;
	}

	public void setStaffNum(String staffNum) {
		this.staffNum = staffNum;
	}

	public String getSalesContactNum() {
		return salesContactNum;
	}

	public void setSalesContactNum(String salesContactNum) {
		this.salesContactNum = salesContactNum;
	}

	public List<String> getMessageList() {
		return this.messageList;
	}

	public void setMessageList(List<String> pMessageList) {
		this.messageList = pMessageList;
	}
	
	public void appendMessage(List<String> pMessageList) {
		this.messageList.addAll(pMessageList);
	}

	public String getPromptAlertMessage() {
		return StringEscapeUtils.escapeJavaScript(this.promptAlertMessage.toString());
	}
	
	public void appendPromptAlertMessage(String promptAlertMessage) {
		this.promptAlertMessage.append(promptAlertMessage);
	}

	public List<OrderStatusDTO> getStatusHistList() {
		return statusHistList;
	}

	public void setStatusHistList(List<OrderStatusDTO> statusHistList) {
		this.statusHistList = statusHistList;
	}
	
	public String getSuspendReason() {
		return suspendReason;
	}
	
	public void setSuspendReason(String suspendReason) {
		this.suspendReason = suspendReason;
	}

	public String getDistributeMode() {
		return distributeMode;
	}

	public void setDistributeMode(String distributeMode) {
		this.distributeMode = distributeMode;
	}

	public String getLangPref() {
		return langPref;
	}

	public void setLangPref(String langPref) {
		this.langPref = langPref;
	}

	public boolean isOnlineAccqOrder() {
		return isOnlineAccqOrder;
	}

	public void setOnlineAccqOrder(boolean isOnlineAccqOrder) {
		this.isOnlineAccqOrder = isOnlineAccqOrder;
	}

	public String getSalesPosition() {
		return salesPosition;
	}

	public void setSalesPosition(String salesPosition) {
		this.salesPosition = salesPosition;
	}

	public String getSalesJob() {
		return salesJob;
	}

	public void setSalesJob(String salesJob) {
		this.salesJob = salesJob;
	}

	public String getSalesProcessDate() {
		return salesProcessDate;
	}

	public void setSalesProcessDate(String salesProcessDate) {
		this.salesProcessDate = salesProcessDate;
	}

	public boolean isAllowUpdateEdfRef() {
		return allowUpdateEdfRef;
	}

	public void setAllowUpdateEdfRef(boolean allowUpdateEdfRef) {
		this.allowUpdateEdfRef = allowUpdateEdfRef;
	}

	public String getSmsNo() {
		return smsNo;
	}

	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public boolean isOrderGeneratePenalty() {
		return isOrderGeneratePenalty;
	}

	public void setOrderGeneratePenalty(boolean isOrderGeneratePenalty) {
		this.isOrderGeneratePenalty = isOrderGeneratePenalty;
	}

	public boolean isPayMtdTypeN() {
		return payMtdTypeN;
	}

	public void setPayMtdTypeN(boolean payMtdTypeN) {
		this.payMtdTypeN = payMtdTypeN;
	}

	public boolean isContainPrepayment() {
		return containPrepayment;
	}

	public void setContainPrepayment(boolean containPrepayment) {
		this.containPrepayment = containPrepayment;
	}

	public boolean isDsOrderSubmit() {
		return dsOrderSubmit;
	}

	public void setDsOrderSubmit(boolean dsOrderSubmit) {
		this.dsOrderSubmit = dsOrderSubmit;
	}

	public String getPrepayAccountNumber() {
		return prepayAccountNumber;
	}

	public void setPrepayAccountNumber(String prepayAccountNumber) {
		this.prepayAccountNumber = prepayAccountNumber;
	}

	public String getPrepayAmount() {
		return prepayAmount;
	}

	public void setPrepayAmount(String prepayAmount) {
		this.prepayAmount = prepayAmount;
	}

	public String getPrepayShopCode() {
		return prepayShopCode;
	}

	public void setPrepayShopCode(String prepayShopCode) {
		this.prepayShopCode = prepayShopCode;
	}

	public String getPrepayTransactionCode() {
		return prepayTransactionCode;
	}

	public void setPrepayTransactionCode(String prepayTransactionCode) {
		this.prepayTransactionCode = prepayTransactionCode;
	}

	public String getPrepayCancelReasonCode() {
		return prepayCancelReasonCode;
	}

	public void setPrepayCancelReasonCode(String prepayCancelReasonCode) {
		this.prepayCancelReasonCode = prepayCancelReasonCode;
	}

	public String getSalesChannelId() {
		return salesChannelId;
	}

	public void setSalesChannelId(String salesChannelId) {
		this.salesChannelId = salesChannelId;
	}

	public String getQcCallTimePeriod() {
		return qcCallTimePeriod;
	}

	public void setQcCallTimePeriod(String qcCallTimePeriod) {
		this.qcCallTimePeriod = qcCallTimePeriod;
	}

	public String getWaiveQc() {
		return waiveQc;
	}

	public void setWaiveQc(String waiveQc) {
		this.waiveQc = waiveQc;
	}

	public String getMustQc() {
		return mustQc;
	}

	public void setMustQc(String mustQc) {
		this.mustQc = mustQc;
	}

	public String getWorkQueueType() {
		return workQueueType;
	}

	public void setWorkQueueType(String workQueueType) {
		this.workQueueType = workQueueType;
	}

	public String getPrepayDateYear() {
		return prepayDateYear;
	}

	public void setPrepayDateYear(String prepayDateYear) {
		this.prepayDateYear = prepayDateYear;
	}

	public String getPrepayDateMonth() {
		return prepayDateMonth;
	}

	public void setPrepayDateMonth(String prepayDateMonth) {
		this.prepayDateMonth = prepayDateMonth;
	}

	public String getPrepayDateDay() {
		return prepayDateDay;
	}

	public void setPrepayDateDay(String prepayDateDay) {
		this.prepayDateDay = prepayDateDay;
	}

	public String getPrepayTimeHour() {
		return prepayTimeHour;
	}

	public void setPrepayTimeHour(String prepayTimeHour) {
		this.prepayTimeHour = prepayTimeHour;
	}

	public String getPrepayTimeMin() {
		return prepayTimeMin;
	}

	public void setPrepayTimeMin(String prepayTimeMin) {
		this.prepayTimeMin = prepayTimeMin;
	}

	public String getPrepayTimeSec() {
		return prepayTimeSec;
	}

	public void setPrepayTimeSec(String prepayTimeSec) {
		this.prepayTimeSec = prepayTimeSec;
	}

	public String getSuspendRemarks() {
		return suspendRemarks;
	}

	public void setSuspendRemarks(String suspendRemarks) {
		this.suspendRemarks = suspendRemarks;
	}

	public String getPrepayDate() {
		return prepayDate;
	}

	public void setPrepayDate(String prepayDate) {
		this.prepayDate = prepayDate;
	}

	public String getLegacyId() {
		return legacyId;
	}

	public void setLegacyId(String legacyId) {
		this.legacyId = legacyId;
	}
	
	public boolean isAllowResendNameNotMatchWQ() {
		return allowResendNameNotMatchWQ;
	}

	public void setAllowResendNameNotMatchWQ(boolean allowResendNameNotMatchWQ) {
		this.allowResendNameNotMatchWQ = allowResendNameNotMatchWQ;
	}

	public String getPcdSbid() {
		return pcdSbid;
	}

	public void setPcdSbid(String pcdSbid) {
		this.pcdSbid = pcdSbid;
	}

	public String getRedemMedia() {
		return redemMedia;
	}

	public void setRedemMedia(String redemMedia) {
		this.redemMedia = redemMedia;
	}

	public String getRedemptionEmail() {
		return redemptionEmail;
	}

	public void setRedemptionEmail(String redemptionEmail) {
		this.redemptionEmail = redemptionEmail;
	}

	public String getRedemptionSms() {
		return redemptionSms;
	}

	public void setRedemptionSms(String redemptionSms) {
		this.redemptionSms = redemptionSms;
	}

	public String getRetypeDocNum() {
		return retypeDocNum;
	}

	public void setRetypeDocNum(String retypeDocNum) {
		this.retypeDocNum = retypeDocNum;
	}

	public String getCarecashInd() {
		return carecashInd;
	}

	public void setCarecashInd(String carecashInd) {
		this.carecashInd = carecashInd;
	}

	public String getCarecashDmInd() {
		return carecashDmInd;
	}

	public void setCarecashDmInd(String carecashDmInd) {
		this.carecashDmInd = carecashDmInd;
	}

	public String getCarecashEmail() {
		return carecashEmail;
	}

	public void setCarecashEmail(String carecashEmail) {
		this.carecashEmail = carecashEmail;
	}

	public String getCarecashContactNum() {
		return carecashContactNum;
	}

	public void setCarecashContactNum(String carecashContactNum) {
		this.carecashContactNum = carecashContactNum;
	}

	public String getPreEnquiry() {
		return preEnquiry;
	}

	public void setPreEnquiry(String preEnquiry) {
		this.preEnquiry = preEnquiry;
	}

	public String getPrepayStatus() {
		return prepayStatus;
	}

	public void setPrepayStatus(String prepayStatus) {
		this.prepayStatus = prepayStatus;
	}

	public String getSignoffMode() {
		return signoffMode;
	}

	public void setSignoffMode(String signoffMode) {
		this.signoffMode = signoffMode;
	}
	
}
