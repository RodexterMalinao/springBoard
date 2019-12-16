package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.Date;

public class PcdOrderCheckAttrDTO implements Serializable{
/**
	 * 
	 */
	//pkg_SB_IMS_LTS.get_pcd_sb_order_detail
	/**
	 * 
	 */

	private static final long serialVersionUID = 9205645034454794025L;

	private String checkAttribute;
	private String result;

	public String getCheckAttribute() {
		return checkAttribute;
	}
	public void setCheckAttribute(String checkAttribute) {
		this.checkAttribute = checkAttribute;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
		
}
