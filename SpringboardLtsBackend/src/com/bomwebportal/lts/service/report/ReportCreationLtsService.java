package com.bomwebportal.lts.service.report;

import java.util.Map;

import com.bomwebportal.dto.report.ReportOutputDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.service.ReportCreationService;

public interface ReportCreationLtsService extends ReportCreationService {

	public static final String PATH_PATTERN_PREFIX = ":prefix";
	public static final String PATH_PATTERN_ORDER_ID = ":orderID";
	public static final String PATH_PATTERN_LANG = ":lang";
	public static final String PATH_PATTERN_PROD_SPEC = ":prodSpec";
	public static final String PATH_PATTERN_SEQ = ":seq";
	
	public static final String RPT_SET_PRINT_AF = "PRINT_AF";
	public static final String RPT_SET_PREVIEW_AF = "PREVIEW_AF";
	public static final String RPT_SET_WQ_EYE_AF = "WQ_EYE_AF";
	public static final String RPT_SET_WQ_DEL_AF = "WQ_DEL_AF";
	public static final String RPT_SET_WQ_2DEL_AF = "WQ_2DEL_AF";
	public static final String RPT_SET_APP_EYE_AF = "APP_EYE_AF";
	public static final String RPT_SET_APP_DEL_AF = "APP_DEL_AF";
	public static final String RPT_SET_AMEND_COVER = "AMEND_COVER";
	public static final String RPT_SET_SIGNOFF_AF = "SIGNOFF_AF";
	public static final String RPT_SET_SMS_AF = "SMS_AF";
	public static final String RPT_SET_WQ_PORT_IN_FORM = "PORT_IN_FORM";
	public static final String RPT_SET_SUPPORT_DOC= "SUP_DOC";	
	public static final String RPT_SET_PRINT_PS = "PRINT_PS";
	public static final String RPT_SET_PREVIEW_PS = "PREVIEW_PS";
	
	public abstract void saveReportDocFile(ReportSetDTO pReportSet, String pOrderId, int pSeq, byte[] pFileBytes, String pUser);
	
	public ReportOutputDTO getSavedReport(ReportSetDTO pRptSet, Map<String,Object> pParmMap); 
}