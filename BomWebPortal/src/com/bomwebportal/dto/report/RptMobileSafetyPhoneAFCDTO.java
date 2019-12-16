package com.bomwebportal.dto.report;

public class RptMobileSafetyPhoneAFCDTO extends RptMobileSafetyPhoneDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final String JASPER_TEMPLATE = "MobileSafetyPhone_AFC";
	
	public RptMobileSafetyPhoneAFCDTO() {
		this.setJasperName(JASPER_TEMPLATE);
	}
}
