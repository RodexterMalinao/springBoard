package com.bomwebportal.dto.report;

public class MiscellaneousChargeDTO {
	
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
