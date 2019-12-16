package com.bomwebportal.mob.ccs.dto.form;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.report.ReportDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;

public class StsDeliveryNoteDTO extends ReportDTO {

	/**
	 * Online sales Sts Delivery Note ,Athena 20131111 online sales report
	 */
	private static final long serialVersionUID = -8278409253010988326L;
	public static final String JASPER_TEMPLATE_DN = "StsDeliveryNote";

	// header, from controller
	private String customerCopyInd;

	private OrderDTO orderDto;
	private MnpDTO mnpDto;
	private DeliveryUI deliveryUi;
	private StaffInfoUI staffInfoUi;
	private PaymentUpFrontUI paymentUpFrontUi;
	private BasketDTO basketDto;
	private List<StockDTO> stockList;
	private String appInDateStr;
	private String collectionType;
	private String deliveryDateAndTimeSlot;
	private String deliveryAddress;
	
	private String custName;
	private String contactNum;
	private Double osBalance;
	private Double paidAmt;
	private Double totalPayment;
	private String smNo;	
	private String paymentMethod;
	private Double deliveryPayment;
	private String deliveryPaymentInd;
	private String couponNum;
	private Double couponFaceVal;
	

	public String getCustomerCopyInd() {
		return customerCopyInd;
	}

	public void setCustomerCopyInd(String customerCopyInd) {
		this.customerCopyInd = customerCopyInd;
	}

	public OrderDTO getOrderDto() {
		return orderDto;
	}

	public void setOrderDto(OrderDTO orderDto) {
		this.orderDto = orderDto;
	}

	public MnpDTO getMnpDto() {
		return mnpDto;
	}

	public void setMnpDto(MnpDTO mnpDto) {
		this.mnpDto = mnpDto;
	}

	public DeliveryUI getDeliveryUi() {
		return deliveryUi;
	}

	public void setDeliveryUi(DeliveryUI deliveryUi) {
		this.deliveryUi = deliveryUi;
	}

	public void setStaffInfoUi(StaffInfoUI staffInfoUi) {
		this.staffInfoUi = staffInfoUi;
	}

	public StaffInfoUI getStaffInfoUi() {
		return staffInfoUi;
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

	public void setOsBalance(Double osBalance) {
		this.osBalance = osBalance;
	}

	public Double getOsBalance() {
		return osBalance;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
	}

	public Double getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(Double paidAmt) {
		this.paidAmt = paidAmt;
	}

	public Double getTotalPayment() {
		return totalPayment;
	}

	public void setTotalPayment(Double totalPayment) {
		this.totalPayment = totalPayment;
	}

	public String getSmNo() {
		return smNo;
	}

	public void setSmNo(String smNo) {
		this.smNo = smNo;
	}

	public Double getDeliveryPayment() {
		return deliveryPayment;
	}

	public void setDeliveryPayment(Double deliveryPayment) {
		this.deliveryPayment = deliveryPayment;
	}

	public String getDeliveryPaymentInd() {
		return deliveryPaymentInd;
	}

	public void setDeliveryPaymentInd(String deliveryPaymentInd) {
		this.deliveryPaymentInd = deliveryPaymentInd;
	}

	public String getCouponNum() {
		return couponNum;
	}

	public void setCouponNum(String couponNum) {
		this.couponNum = couponNum;
	}

	public Double getCouponFaceVal() {
		return couponFaceVal;
	}

	public void setCouponFaceVal(Double double1) {
		this.couponFaceVal = double1;
	}
	
}
