package com.bomwebportal.dto;

import java.io.Serializable;

public class AddressCategoryDTO implements Serializable {

	private static final long serialVersionUID = 2363227765779990480L;

	private String CategoryCode;//W_ADDRCATEGORY.STCATGCD
	private String CategoryDesc;//W_ADDRCATEGORY.STCATDSC
	
	public void setCategoryCode(String categoryCode) {
		CategoryCode = categoryCode;
	}
	public String getCategoryCode() {
		return CategoryCode;
	}
	public void setCategoryDesc(String categoryDesc) {
		CategoryDesc = categoryDesc;
	}
	public String getCategoryDesc() {
		return CategoryDesc;
	}
	
	
}
