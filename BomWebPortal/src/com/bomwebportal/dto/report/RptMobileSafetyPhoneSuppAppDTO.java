package com.bomwebportal.dto.report;

public class RptMobileSafetyPhoneSuppAppDTO extends RptMobileSafetyPhoneDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String JASPER_TEMPLATE = "MobileSafetyPhone_Supp_App";
	
	public RptMobileSafetyPhoneSuppAppDTO() {
		this.setJasperName(JASPER_TEMPLATE);
	}
}
