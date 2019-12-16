package com.bomwebportal.dto.report;

public class AdditionalChargeDTO {	
	
	private String additionalChargeDesc;
	private String additionalChargePrice;
	
	public AdditionalChargeDTO(){
		super();
	}
	
	public AdditionalChargeDTO(String additionalChargeDesc, String additionalChargePrice){
		this.additionalChargeDesc = additionalChargeDesc;
		this.additionalChargePrice = additionalChargePrice;
	}
	
	public String getAdditionalChargeDesc() {
		return additionalChargeDesc;
	}
	public void setAdditionalChargeDesc(String additionalChargeDesc) {
		this.additionalChargeDesc = additionalChargeDesc;
	}
	public String getAdditionalChargePrice() {
		return additionalChargePrice;
	}
	public void setAdditionalChargePrice(String additionalChargePrice) {
		this.additionalChargePrice = additionalChargePrice;
	}
	
	
}
