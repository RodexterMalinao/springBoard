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
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;

public class StsDOADeliveryNoteDTO extends ReportDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5252863841979291374L;

	public static final String JASPER_TEMPLATE_DN = "StsDOADeliveryNote";

	// header, from controller
	private String customerCopyInd;

	private OrderDTO orderDto;
	private MnpDTO mnpDto;
	private DeliveryUI deliveryUi;
	private StaffInfoUI staffInfoUi;	
	private BasketDTO basketDto;
	private List<StockDTO> stockList;
	private List<StockDTO> doaStockList;
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
	
	public List<StockDTO> getDoaStockList() {
		return doaStockList;
	}

	public void setDoaStockList(List<StockDTO> doaStockList) {
		this.doaStockList = doaStockList;
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

	public Double getOsBalance() {
		return osBalance;
	}

	public void setOsBalance(Double osBalance) {
		this.osBalance = osBalance;
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
	
}
