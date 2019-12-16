package com.bomwebportal.service;

import java.util.Map;

import com.bomwebportal.dto.report.ReportOutputDTO;
import com.bomwebportal.dto.report.ReportSetDTO;

public interface ReportCreationService {
	
	public static final String PARM_LANG = "LANG";
	
	public static final String LANG_ORDER = "ORD";
	public static final String LANG_ENG = "ENG";
	public static final String LANG_CHI = "CHN";
	
	public static final String COPY_INTERNAL_ONLY = "I";
	public static final String COPY_BOTH = "B";
	
	public static final String PERMISSION_SCREEN_READ = "S";
	public static final String PERMISSION_PRINTABLE = "P";
	public static final String RPT_KEY_NAME = "RPT_NAME";
	
	public abstract ReportOutputDTO generateReport(ReportSetDTO pRptSet, Map<String, Object> pParmMap);
	
	public abstract ReportOutputDTO generateReportWithWaterMark(ReportSetDTO pRptSet, Map<String,Object> pParmMap, String pWaterMark);
		

}