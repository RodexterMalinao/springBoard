package com.bomwebportal.web.util;

import com.bomwebportal.service.ReportService.Form;

public class ProjectEagleReportHelper {
	
	public static final String FORM_ID = "PE001";
	public static final String FORM_NAME = "ProjectEagle";
	public static final String ITEM_SELECTTION_GROUP_ID = "6666666663";
	public static final String SERVICE_LINE_GROUP_CATEGORY = "RESTART";
	public static final String TNC_PDF = "project_eagle_tnc.pdf";
	public static final String RESTART_SERVICE_FORM_CCS_FORM_ABBREVIATION = "A";
	public static final String ITEM_PROJECT_EAGLE_INSURANCE_DM_CODE_TYPE = "PROJECT_EAGLE_INSURANCE_DM_ATTB_ID";
	
	public static String getFileName(String orderId, String msisdn, Form form) {
		String fileNameFormat = "%s_%s_%s.pdf";
		return String.format(fileNameFormat, orderId, msisdn, FORM_ID);
	}
	
}