package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;

import com.bomwebportal.lts.dto.ServiceSummaryDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;

public class LtsSummaryFormDTO implements Serializable {

	private static final long serialVersionUID = -3892336266451539260L;

	public static final int BUTTON_SIGNOFF = 0;
	public static final int BUTTON_APPROVE = 1;
	public static final int BUTTON_CANCEL = 2;
	public static final int BUTTON_DISABLE = 3;
	public static final int BUTTON_SUSPEND = 4;
	public static final int BUTTON_SIMPLE_SUSPEND = 5;
	public static final int BUTTON_REFRESH = 6;
	
	private String orderId = null;
	private List<ServiceSummaryDTO> serviceSummaryList = null;
	private int buttonPressed = -1;
	private int requireButton = -1;

	private String salesChannel = null;
	private String salesCd = null;
	private String salesTeam = null;
	private String staffNum = null;
	private String salesPosition = null;
	private String salesJob = null;
	private String salesProcessDate = null;
	private String salesContactNum = null;
	private String suspendReason = null;
	private String smsNo = null;
	private String createBy = null;
	private boolean containPrepayment;
	
	
	private List<String> messageList = new ArrayList<String>();
	private StringBuilder promptAlertMessage = new StringBuilder();
	
	private List<OrderStatusDTO> statusHistList = null;

	private String distributeMode = null;
	private String langPref = null;
	
	private boolean isOnlineAccqOrder = false;
	private boolean allowUpdateEdfRef = false;
	private boolean isOrderGeneratePenalty = false;

	private String signoffMode = null;
	
	public List<ServiceSummaryDTO> getServiceSummaryList() {
		return serviceSummaryList;
	}

	public void setServiceSummaryList(List<ServiceSummaryDTO> serviceSummaryList) {
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

	public boolean isContainPrepayment() {
		return containPrepayment;
	}

	public void setContainPrepayment(boolean containPrepayment) {
		this.containPrepayment = containPrepayment;
	}

	public String getSignoffMode() {
		return signoffMode;
	}

	public void setSignoffMode(String signoffMode) {
		this.signoffMode = signoffMode;
	}
	
}
