package com.bomwebportal.dto;

public class ItemPricingDTO {
	
	
	
	//W_ITEM_PRICING
	private int id	;//	NUMBER
	private String effStartDate	;//	DATE
	private String effEndDate	;//	DATE
	private String onetimeType	;//	VARCHAR2 (1 Byte)
	private  String 	onetimeAmt	;//	NUMBER (10,2)
	private  String 	recurrentAmt	;//	NUMBER (10,2)
	private  String 	rebateAmt	;//	NUMBER (10,2) //not using
	//createBy	;//	VARCHAR2 (10 Byte)
	//createDate	;//	DATE
	//lastUpdBy	;//	VARCHAR2 (10 Byte)
	//lastUpdDate	;//	DATE
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEffStartDate() {
		return effStartDate;
	}
	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}
	public String getEffEndDate() {
		return effEndDate;
	}
	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}
	public String getOnetimeType() {
		return onetimeType;
	}
	public void setOnetimeType(String onetimeType) {
		this.onetimeType = onetimeType;
	}
	public String getOnetimeAmt() {
		return onetimeAmt;
	}
	public void setOnetimeAmt(String onetimeAmt) {
		this.onetimeAmt = onetimeAmt;
	}
	public String getRecurrentAmt() {
		return recurrentAmt;
	}
	public void setRecurrentAmt(String recurrentAmt) {
		this.recurrentAmt = recurrentAmt;
	}
	public void setRebateAmt(String rebateAmt) {
		this.rebateAmt = rebateAmt;
	}
	public String getRebateAmt() {
		return rebateAmt;
	}
	
	

}
