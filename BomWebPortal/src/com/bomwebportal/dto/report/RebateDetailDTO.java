package com.bomwebportal.dto.report;

public class RebateDetailDTO {
	
	private String rebateType;
	private String rebateFormula;
	private String rebateAmount;
	
	public RebateDetailDTO(){
		super();
	}
	
	public RebateDetailDTO(String prebateType, String prebateFormula, String prebateAmount){
		this.rebateType = prebateType;
		this.rebateFormula = prebateFormula;
		this.rebateFormula = prebateAmount;
	}
	public String getRebateType() {
		return rebateType;
	}
	public void setRebateType(String rebateType) {
		this.rebateType = rebateType;
	}
	public String getRebateFormula() {
		return rebateFormula;
	}
	public void setRebateFormula(String rebateFormula) {
		this.rebateFormula = rebateFormula;
	}
	public String getRebateAmount() {
		return rebateAmount;
	}
	public void setRebateAmount(String rebateAmount) {
		this.rebateAmount = rebateAmount;
	}
	
}
