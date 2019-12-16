package com.bomwebportal.lts.dto.form;

import java.io.Serializable;

public class LtsDeviceSelectionFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9043654907241171939L;
	
	private String selectedDeviceType;

	public String getSelectedDeviceType() {
		return selectedDeviceType;
	}

	public void setSelectedDeviceType(String selectedDeviceType) {
		this.selectedDeviceType = selectedDeviceType;
	}
	
	

}
