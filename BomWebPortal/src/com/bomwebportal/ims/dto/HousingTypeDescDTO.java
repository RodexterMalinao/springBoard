package com.bomwebportal.ims.dto;

import java.io.Serializable;


public class HousingTypeDescDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 530327011975513116L;
	
	public HousingTypeDescDTO(){
		
	}

	private String housingTypeDesc;
	
	public String getHousingTypeDesc() {
		return housingTypeDesc;
	}
	public void setHousingTypeDesc(String housingTypeDesc) {
		this.housingTypeDesc = housingTypeDesc;
	}
	
	@Override
	public String toString() {
		return "HousingTypeDescDTO [housingTypeDesc=" + housingTypeDesc + "]";
	}
	
}
