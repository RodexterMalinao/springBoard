package com.bomwebportal.dto.report;

import java.io.Serializable;

public class MiscellaneousChargeDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9007418822660477114L;
	private String miscellaneousChargeDesc;
	private String miscellaneousCharge;
	
	public MiscellaneousChargeDTO(){
		super();
	}
	
	public MiscellaneousChargeDTO(String MiscellaneousChargeDesc, String MiscellaneousCharge){
		this.miscellaneousChargeDesc = MiscellaneousChargeDesc;
		this.miscellaneousCharge = MiscellaneousCharge;
	}
	
	
	public String getMiscellaneousChargeDesc() {
		return miscellaneousChargeDesc;
	}
	public void setMiscellaneousChargeDesc(String miscellaneousChargeDesc) {
		this.miscellaneousChargeDesc = miscellaneousChargeDesc;
	}
	public String getMiscellaneousCharge() {
		return miscellaneousCharge;
	}
	public void setMiscellaneousCharge(String miscellaneousCharge) {
		this.miscellaneousCharge = miscellaneousCharge;
	}
	
	

}
