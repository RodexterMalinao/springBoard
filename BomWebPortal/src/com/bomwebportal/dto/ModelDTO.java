package com.bomwebportal.dto;

import java.util.List;


public class ModelDTO implements java.io.Serializable{
	
	String brandId;
	
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	String modelId;
	String modelName;
	String modelImagePath;
	String modelHtml;
	
	//[start] wilson add 20110104
	List<ColorDTO> colorDto ;
	List<BasketDTO> basketDto;
	public List<ColorDTO> getColorDto() {
		return colorDto;
	}
	public void setColorDto(List<ColorDTO> colorDto) {
		this.colorDto = colorDto;
	}
	public List<BasketDTO> getBasketDto() {
		return basketDto;
	}
	public void setBasketDto(List<BasketDTO> basketDto) {
		this.basketDto = basketDto;
	}
	
	

	
	//[end] wilson add 20110104
	
	
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getModelImagePath() {
		return modelImagePath;
	}
	public void setModelImagePath(String modelImagePath) {
		this.modelImagePath = modelImagePath;
	}
	public String getModelHtml() {
		return modelHtml;
	}
	public void setModelHtml(String modelHtml) {
		this.modelHtml = modelHtml;
	}
	

}

