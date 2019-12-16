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

public class MobCcsDeliveryNoteDTO extends ReportDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3753789705743062874L;

	public static final String JASPER_TEMPLATE_DN = "DeliveryNote";

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

	// add by Joyce 20120306
	private Double osBalance;
	private String paymentMethod; // add by Joyce 20120313
	private String depositTotal; //20140220 deposit report Athena	
	private String iGuardRemark;

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
	
}
