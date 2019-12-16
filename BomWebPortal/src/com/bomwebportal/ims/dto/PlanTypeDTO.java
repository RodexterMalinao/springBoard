package com.bomwebportal.ims.dto;

public class PlanTypeDTO {
	private String planType;
	private String planTypeDesc;
	
	public PlanTypeDTO(){}; 
	
	public PlanTypeDTO(String a, String b){
		planType = a; 
		planTypeDesc = b;
	}
	
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanTypeDesc(String planTypeDesc) {
		this.planTypeDesc = planTypeDesc;
	}
	public String getPlanTypeDesc() {
		return planTypeDesc;
	}
	
}
