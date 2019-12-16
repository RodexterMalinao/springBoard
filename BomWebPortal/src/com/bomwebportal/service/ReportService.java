package com.bomwebportal.service;

import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.report.RptProjectEagleDTO;

public interface ReportService {	
	public enum Form {
		IGUARD_LDS
		,IGUARD_AD
		, MOBILE_SAFETY_PHONE
		, NFC_SIM
		, OCTOPUS_SIM
		, NFC_SIM_SET
		, AUTH_LETTER
		, IGUARD_CARECASH
		, IGUARD_UAD
		, HELPERCARE_INSURANCE_FORM
		, TRAVEL_INSURANCE_FORM
		, PROJECT_EAGLE_FORM
		;
	}

	void generatePdfReports(List<Object> pReportArrList, OutputStream pOutputStream, String pLang, String orderId, String brand);
	void generatePdfReports(List<Object> pReportArrList, OutputStream pOutputStream, String pLang, String orderId, List<Form> forms, String brand);
	String savePdfReports(List<Object> pReportArrList, String pLang, String orderId, String brand);
	String saveIGuardPdfReports(List<Object> pReportArrList, String orderId, String msisdn, String serialNo, String iGuardType, String brand);
	String saveMobileSafetyPhoneReports(List<Object> pReportArrList, String pLang, String orderId, String brand);
	String saveIGuardCareCashPdfReports(List<Object> pReportArrList, String pLang ,String orderId, String msisdn,String brand);
	String saveNFCSimReports(List<Object> pReportArrList, String pLang, String orderId, String brand);
	//void saveOctopusSimReports(List<Object> pReportArrList, String pLang, String orderId);
	public boolean isGenerateM2Report(Date appDate);
	Map<String, String> getM2Mapping();
	String getReportContentHtml(String formId, String locale, String variableId);
	public String maskedDocNum (String docNum);
	VasDetailDTO getVasDetail(String itemId);
	public String getCodeId(String codeType);
	public String getAttbValue(List<ComponentDTO> list, String attbId);
	String saveHKTCarePdfReports(List<Object> pReportArrList, String pLang, String orderId, String msisdn, String brand, Form form);
	public RptProjectEagleDTO patchWithLabels(RptProjectEagleDTO rptProjectEagleDTO);
	public void generateAndSaveDigitalModePdf(HttpServletRequest request, boolean isTemp);
	public void deleteTempDigitalModePdf(String orderId);
}
