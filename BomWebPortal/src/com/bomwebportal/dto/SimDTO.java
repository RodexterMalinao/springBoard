package com.bomwebportal.dto;
public class SimDTO {
	private String iccid;
	private String imsi;
	private String puk1;
	private String puk2;
	private String itemCode;
	private String orderId;
	private String itemId;
	private boolean waivable;
	private double charge;
	private int hwInvStatus;
	private String shopCd;
	private String simType;
	private String itemDisplay;
	private int extraFunction;
	private String chargeItemCd;
	private int stockCount;
	
	private String mipSimType;
	
	public String getIccid() {
		return iccid;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public String getImsi() {
		return imsi;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public String getPuk1() {
		return puk1;
	}
	public void setPuk1(String puk1) {
		this.puk1 = puk1;
	}
	public String getPuk2() {
		return puk2;
	}
	public void setPuk2(String puk2) {
		this.puk2 = puk2;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getItemId() {
		return itemId;
	}
	public boolean isWaivable() {
		return waivable;
	}
	public double getCharge() {
		return charge;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public void setWaivable(boolean waivable) {
		this.waivable = waivable;
	}
	public void setCharge(double charge) {
		this.charge = charge;
	}
	public int getHwInvStatus() {
		return hwInvStatus;
	}
	public String getShopCd() {
		return shopCd;
	}
	public void setHwInvStatus(int hwInvStatus) {
		this.hwInvStatus = hwInvStatus;
	}
	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}
	public String getSimType() {
		return simType;
	}
	public void setSimType(String simType) {
		this.simType = simType;
	}
	public String getItemDisplay() {
		return itemDisplay;
	}
	public int getExtraFunction() {
		return extraFunction;
	}
	public String getChargeItemCd() {
		return chargeItemCd;
	}
	public int getStockCount() {
		return stockCount;
	}
	public void setItemDisplay(String itemDisplay) {
		this.itemDisplay = itemDisplay;
	}
	public void setExtraFunction(int extraFunction) {
		this.extraFunction = extraFunction;
	}
	public void setChargeItemCd(String chargeItemCd) {
		this.chargeItemCd = chargeItemCd;
	}
	public void setStockCount(int stockCount) {
		this.stockCount = stockCount;
	}
	public String getMipSimType() {
		return mipSimType;
	}
	public void setMipSimType(String mipSimType) {
		this.mipSimType = mipSimType;
	}
	
}
