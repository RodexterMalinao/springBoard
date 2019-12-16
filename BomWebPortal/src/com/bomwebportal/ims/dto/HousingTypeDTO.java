package com.bomwebportal.ims.dto;

import java.io.Serializable;


public class HousingTypeDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 530327011975513116L;
	
	public HousingTypeDTO(){
		
	}

	private String housingType;
	
	public String getHousingType() {
		return housingType;
	}
	public void setHousingType(String housingType) {
		this.housingType = housingType;
	}
	
	@Override
	public String toString() {
		return "HousingTypeDTO [housingType=" + housingType + "]";
	}
	
}
