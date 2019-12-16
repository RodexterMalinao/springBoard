package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ModemTechnologyAissgnDTO extends ModemAssignDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4277117549418322401L;
	
	public ModemTechnologyAissgnDTO() {
		super();
	}
	
	public ModemTechnologyAissgnDTO(String newImsService,
			String modemArrangment, String technology, String bandwidth,
			String imsOrderType, boolean isBbShortage) {
		super(newImsService, modemArrangment, technology, bandwidth, imsOrderType,
				isBbShortage);
		// TODO Auto-generated constructor stub
	}
	
}
