package com.bomwebportal.dto.report;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;

public class RptRetCosDeliveryNoteDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3753789705743062874L;

	public static final String JASPER_TEMPLATE = "MobRetDeliveryNote";

	// header, from controller
	private String customerCopyInd;

	private String orderId;
	private String msisdn;
	private String contactPhone;
	private String contactName;
	private String title;
	private String salesName;
	private String salesCd;
	private String basketType;
	private String creditCardNum;
	private String creditCardHolderName;
	private String sContactPhone;
	private DeliveryUI deliveryUi;
	private PaymentUpFrontUI paymentUpFrontUi;
	private List<StockDTO> stockList;
	private String appInDateStr;
	private String collectionType;
	private String deliveryDateAndTimeSlot;
	private String deliveryAddress;
	private BasketDTO basketDto;
	private String osBalance;
	private String paymentMethod;
	private String depositTotal; //20140220 deposit report Athena
	private String hsPayment;
	private String prePayment;
	private String adminCharge;
	private String paidAmt;
	private String iGuardRemark;
	private String hsDefectInd;
	private String acDefectInd;
	private String fullPackageInd;
	private String contactType;
	private String hotLine;

	public String getCustomerCopyInd() {
		return customerCopyInd;
	}

	public void setCustomerCopyInd(String customerCopyInd) {
		this.customerCopyInd = customerCopyInd;
	}



	public DeliveryUI getDeliveryUi() {
		return deliveryUi;
	}

	public void setDeliveryUi(DeliveryUI deliveryUi) {
		this.deliveryUi = deliveryUi;
	}



	public PaymentUpFrontUI getPaymentUpFrontUi() {
		return paymentUpFrontUi;
	}

	public void setPaymentUpFrontUi(PaymentUpFrontUI paymentUpFrontUi) {
		this.paymentUpFrontUi = paymentUpFrontUi;
	}

	public void setBasketDto(BasketDTO basketDto) {
		this.basketDto = basketDto;
	}

	public BasketDTO getBasketDto() {
		return basketDto;
	}

	public void setStockList(List<StockDTO> stockList) {
		this.stockList = stockList;
	}

	public List<StockDTO> getStockList() {
		return stockList;
	}

	public String getAppInDateStr() {
		return appInDateStr;
	}

	public void setAppInDateStr(String appInDateStr) {
		this.appInDateStr = appInDateStr;
	}



	public String getCollectionType() {
	    return collectionType;
	}

	public void setCollectionType(String collectionType) {
	    this.collectionType = collectionType;
	}

	public String getDeliveryDateAndTimeSlot() {
	    return deliveryDateAndTimeSlot;
	}

	public void setDeliveryDateAndTimeSlot(String deliveryDateAndTimeSlot) {
	    this.deliveryDateAndTimeSlot = deliveryDateAndTimeSlot;
	}

	public String getDeliveryAddress() {
	    return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
	    this.deliveryAddress = deliveryAddress;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * Format: DD/MM/YYYY HH:MM:SS
	 * @return the printDateNTime
	 */
	public String getPrintDateNTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return sdf.format(new Date());
	}

	public String getiGuardRemark() {
		return iGuardRemark;
	}

	public void setiGuardRemark(String iGuardRemark) {
		this.iGuardRemark = iGuardRemark;
	}

	public String getDepositTotal() {
		return depositTotal;
	}

	public void setDepositTotal(String depositTotal) {
		this.depositTotal = depositTotal;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getSalesCd() {
		return salesCd;
	}

	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}

	public String getBasketType() {
		return basketType;
	}

	public void setBasketType(String basketType) {
		this.basketType = basketType;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getsContactPhone() {
		return sContactPhone;
	}

	public void setsContactPhone(String sContactPhone) {
		this.sContactPhone = sContactPhone;
	}

	public String getCreditCardNum() {
		return creditCardNum;
	}

	public void setCreditCardNum(String creditCardNum) {
		this.creditCardNum = creditCardNum;
	}

	public String getCreditCardHolderName() {
		return creditCardHolderName;
	}

	public void setCreditCardHolderName(String creditCardHolderName) {
		this.creditCardHolderName = creditCardHolderName;
	}

	public String getOsBalance() {
		return osBalance;
	}

	public void setOsBalance(String osBalance) {
		this.osBalance = osBalance;
	}

	public String getHsPayment() {
		return hsPayment;
	}

	public void setHsPayment(String hsPayment) {
		this.hsPayment = hsPayment;
	}

	public String getPrePayment() {
		return prePayment;
	}

	public void setPrePayment(String prePayment) {
		this.prePayment = prePayment;
	}

	public String getAdminCharge() {
		return adminCharge;
	}

	public void setAdminCharge(String adminCharge) {
		this.adminCharge = adminCharge;
	}

	public String getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(String paidAmt) {
		this.paidAmt = paidAmt;
	}

	public String getHsDefectInd() {
		return hsDefectInd;
	}

	public void setHsDefectInd(String hsDefectInd) {
		this.hsDefectInd = hsDefectInd;
	}

	public String getAcDefectInd() {
		return acDefectInd;
	}

	public void setAcDefectInd(String acDefectInd) {
		this.acDefectInd = acDefectInd;
	}

	public String getFullPackageInd() {
		return fullPackageInd;
	}

	public void setFullPackageInd(String fullPackageInd) {
		this.fullPackageInd = fullPackageInd;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getHotLine() {
		return hotLine;
	}

	public void setHotLine(String hotLine) {
		this.hotLine = hotLine;
	}

}
