package com.pccw.rpt.service;

import java.io.OutputStream;
import java.util.Map;

import com.pccw.rpt.dao.BomwebRptTemplateVDAO;
import com.pccw.rpt.dao.WItemRptTemplateVDAO;
import com.pccw.rpt.schema.dto.ReportDTO;

public interface ReportService {

	public static final int EXPORT_TYPE_PDF = 0;
	
	public static final int EXPORT_TYPE_RTF = 1;
	
	public static final int EXPORT_TYPE_RPT_DTO_JAVA_OBJ = 2;
	
	public String generateEditButtonHtml(WItemRptTemplateVDAO pDao);

	public String generateEditButtonHtml(BomwebRptTemplateVDAO pDao);
	
	public String generateNewEditButtonHtml(String pReportName,	String pAttribute, String pLanguage);
	
	public ReportDTO generateReport(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, OutputStream pOutputStream) throws Exception;

	public ReportDTO generateReport(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, OutputStream pOutputStream, int pExportType) throws Exception;

	public void generateReport(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO, OutputStream pOutputStream, int pExportType) throws Exception;

	public void reGenerateReport(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO,
			OutputStream pOutputStream, int pExportType) throws Exception;
	
	public void generateSWReport(Map<String, String> pRptParamMap, Map<String, byte[]> pRptImgMap, String lang, OutputStream os) throws Exception;
}