package com.bomltsportal.dto.email;
public class SimDTO {
	private String iccid;
	private String imsi;
	private String puk1;
	private String puk2;
	private String itemCode;
	private String orderId;
	
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
	
}
