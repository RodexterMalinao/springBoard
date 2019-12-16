package com.bomwebportal.mob.ccs.dto;

public class BasketAssoWBasketAttrBrandModelDTO {
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getBrandModelDesc() {
		return brandModelDesc;
	}
	public void setBrandModelDesc(String brandModelDesc) {
		this.brandModelDesc = brandModelDesc;
	}
	private String brandId;
	private String modelId;
	private String brandModelDesc;
}
