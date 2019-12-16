package com.bomwebportal.dto.report;

import java.io.Serializable;

public class AdditionalChargeDTO implements Serializable {	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3768807999421409489L;
	
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
