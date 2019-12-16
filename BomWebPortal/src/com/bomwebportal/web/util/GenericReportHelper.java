package com.bomwebportal.web.util;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.dto.report.RptGenericFormTemplateDTO;
import com.bomwebportal.service.ItemDisplayService;
import com.bomwebportal.service.ReportService;
import com.bomwebportal.service.ReportService.Form;
import com.bomwebportal.util.Utility;

public class GenericReportHelper {
	
	public static final String GENERIC_REPORT_TEMPLATE = "GenericReportTemplate";
	
	public static final String TRAVEL_INSURANCE_FORM_FORM_ID = "TR001";
	public static final String HELPERCARE_INSURANCE_FORM_FORM_ID = "HC001";
	
	public static final String TRAVEL_INSURANCE_FORM_FORM_NAME = "TravelInsuranceForm";
	public static final String HELPERCARE_INSURANCE_FORM_FORM_NAME = "HelperCareInsuranceForm";
	
	public static final String TRAVEL_INSURANCE_FORM_CCS_FORM_ABBREVIATION = "R";
	public static final String HELPERCARE_INSURANCE_FORM_CCS_FORM_ABBREVIATION = "H";
	
	public static final String TRAVEL_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID = "6666666665";
	public static final String HELPERCARE_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID = "6666666664";
	
	public static final String TRAVEL_INSURANCE_FORM_TNC_PDF = "travelcare${travelInsuranceDays}_tnc.pdf";
	public static final String HELPERCARE_INSURANCE_FORM_TNC_PDF = "helpercare_tnc.pdf";
	
	public static final String REPORT_DATA_MAP_KEY_ORDER_ID = "orderId";
	public static final String REPORT_DATA_MAP_KEY_CUST_LAST_NAME = "custLastName";
	public static final String REPORT_DATA_MAP_KEY_CUST_FIRST_NAME = "custFirstName";
	public static final String REPORT_DATA_MAP_KEY_ID_DOC_NUM = "idDocNum";
	public static final String REPORT_DATA_MAP_KEY_EMAIL_ADDR = "emailAddr";
	public static final String REPORT_DATA_MAP_KEY_MSISDN = "msisdn";
	public static final String REPORT_DATA_MAP_KEY_APP_IN_DATE = "appInDate";
	public static final String REPORT_DATA_MAP_KEY_SERVICE_REQ_DATE = "serviceReqDate";
	public static final String REPORT_DATA_MAP_KEY_SHOP_CODE = "shopCode";
	
	public static final String REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_PROD_CD = "trProdCd";
	public static final String REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_PROD_CD = "hcProdCd";
	public static final String REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DISPLAY = "trDisplay";
	public static final String REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DISPLAY = "hcDisplay";
	public static final String REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_CONTRACT_PERIOD = "trContract";
	public static final String REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_CONTRACT_PERIOD = "hcContract";
	public static final String REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_STAFF_ID = "trStaffId";
	public static final String REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_STAFF_ID = "hcStaffId";
	public static final String REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DM_IND = "trDMInd";
	public static final String REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DM_IND = "hcDMInd";
	public static final String REPORT_DATA_MAP_KEY_CUSTOMER_TITLE = "customerTitle";
	public static final String REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_ITEM_ID = "trItemId";
	
	public static final String REPORT_DATA_MAP_KEY_ORDER_BRAND="brand";
	
	public enum GenericForm {
		TRAVEL_INSURANCE_FORM(
				TRAVEL_INSURANCE_FORM_FORM_ID, 
				Form.TRAVEL_INSURANCE_FORM,
				TRAVEL_INSURANCE_FORM_FORM_NAME,
				TRAVEL_INSURANCE_FORM_CCS_FORM_ABBREVIATION,
				TRAVEL_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID,
				TRAVEL_INSURANCE_FORM_TNC_PDF
				)
		, HELPERCARE_INSURANCE_FORM(
				HELPERCARE_INSURANCE_FORM_FORM_ID, 
				Form.HELPERCARE_INSURANCE_FORM,
				HELPERCARE_INSURANCE_FORM_FORM_NAME,
				HELPERCARE_INSURANCE_FORM_CCS_FORM_ABBREVIATION,
				HELPERCARE_INSURANCE_FORM_ITEM_SELECTTION_GROUP_ID,
				HELPERCARE_INSURANCE_FORM_TNC_PDF
				)
		;
		
		GenericForm(String formId, Form form, String formName, String ccsformAbbreviation,
				String itemSelectionGroupId, String tncPdf) {
			this.formId = formId;
			this.form = form;
			this.formName = formName;
			this.ccsformAbbreviation = ccsformAbbreviation;
			this.itemSelectionGroupId = itemSelectionGroupId;
			this.tncPdf = tncPdf;
		}
		
		private String formId;
		private Form form;
		private String formName;
		private String ccsformAbbreviation;
		private String itemSelectionGroupId;
		private String tncPdf;
		
		public String getFormId() {
			return this.formId;
		}
		public Form getForm() {
			return this.form;
		}
		public String getFormName() {
			return this.formName;
		}
		public String getCcsformAbbreviation() {
			return this.ccsformAbbreviation;
		}
		public String getItemSelectionGroupId() {
			return this.itemSelectionGroupId;
		}
		public String getTncPdf() {
			return this.tncPdf;
		}
	}
	
	private ReportService reportService;
	private ItemDisplayService itemDisplayService;
	
	public ReportService getReportService() {
		return reportService;
	}

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public ItemDisplayService getItemDisplayService() {
		return itemDisplayService;
	}

	public void setItemDisplayService(ItemDisplayService itemDisplayService) {
		this.itemDisplayService = itemDisplayService;
	}

	public RptGenericFormTemplateDTO getRptGenericFormTemplateDTO(
			GenericForm genericForm,
			String pdfLang,
			Map<String, String> genericReportDataMap,
			Map<String, InputStream> genericReportSignatureMap) {
		
		if (genericForm == null || CollectionUtils.isEmpty(genericReportDataMap)) {
			return null;
		}
		
		pdfLang = "en"; // bilingual form but hardcode as eng for locale, align with BOMWEB_MOB_APPFORM_CONTENT
		String formId = genericForm.formId;
		Map<String, Object> templateParams = new HashMap<String, Object>();
		String travelInsuranceDays = itemDisplayService.getTravelInsuranceDays(getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_ITEM_ID));
		templateParams.put("travelInsuranceDays", travelInsuranceDays);
		
		RptGenericFormTemplateDTO dto = new RptGenericFormTemplateDTO();
		dto.setJasperName(GenericReportHelper.GENERIC_REPORT_TEMPLATE);
		
		dto.setSection1(dto.getImageFilePath() + "/" + getTemplateFromDB(formId, pdfLang, "section01", templateParams));
		
		String section2Template = getTemplateFromDB(formId, pdfLang, "section02", templateParams);
		String orderId = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_ORDER_ID);
		dto.setSection2(section2Template + orderId);
		
		dto.setSection3(getTemplateFromDB(formId, pdfLang, "section03", templateParams));
		
		dto.setSection4(getTemplateFromDB(formId, pdfLang, "section04", templateParams));
		
		dto.setSection62(getTemplateFromDB(formId, pdfLang, "section062", templateParams));
		
		dto.setSection64(getTemplateFromDB(formId, pdfLang, "section064", templateParams));
		
		String brand = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_ORDER_BRAND);
		if (StringUtils.equalsIgnoreCase(brand,"1010")) {
			dto.setSection61("X");
		} else if (StringUtils.equalsIgnoreCase(brand,"csl")) {
			dto.setSection63("X");
		}
		
		String section51Template = getTemplateFromDB(formId, pdfLang, "section051", templateParams);
		String section51Value1 = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_CUSTOMER_TITLE);
		String section51Value2 = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_CUST_LAST_NAME);
		dto.setSection51(getTemplateAndValuePadWithUnderline(section51Template, section51Value1 + " " + section51Value2));
		
		String section52Template = getTemplateFromDB(formId, pdfLang, "section052", templateParams);
		String section52Value = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_CUST_FIRST_NAME);
		dto.setSection52(getTemplateAndValuePadWithUnderline(section52Template, section52Value));
		
		String section53Template = getTemplateFromDB(formId, pdfLang, "section053", templateParams);
		String section53Value = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_ID_DOC_NUM);
		dto.setSection53(getTemplateAndValuePadWithUnderline(section53Template, section53Value));
		
		String section54Template = getTemplateFromDB(formId, pdfLang, "section054", templateParams);
		String section54Value = getValueFromMap(genericReportDataMap, "");
		dto.setSection54(getTemplateAndValuePadWithUnderline(section54Template, section54Value));
		
		String section55Template = getTemplateFromDB(formId, pdfLang, "section055", templateParams);
		String section55Value = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_EMAIL_ADDR);
		dto.setSection55(getTemplateAndValuePadWithUnderline(section55Template, section55Value));
		
		String section56Template = getTemplateFromDB(formId, pdfLang, "section056", templateParams);
		String section56Value = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_MSISDN);
		dto.setSection56(getTemplateAndValuePadWithUnderline(section56Template, section56Value));
		
		dto.setSection6(getTemplateFromDB(formId, pdfLang, "section06", templateParams));
		
		String section72Template = getTemplateFromDB(formId, pdfLang, "section072", templateParams);
		String section72Value = "";
		if (StringUtils.equalsIgnoreCase(formId, TRAVEL_INSURANCE_FORM_FORM_ID)) {
			dto.setTravelInsuranceDays(travelInsuranceDays);
			section72Value = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_PROD_CD);
		} else if (StringUtils.equalsIgnoreCase(formId, HELPERCARE_INSURANCE_FORM_FORM_ID)) {
			section72Value = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_PROD_CD);
		}
		dto.setSection72(getTemplateAndValuePadWithUnderline(section72Template, section72Value));
		
		dto.setSection73(getTemplateFromDB(formId, pdfLang, "section073", templateParams));
		
		String section74Template = getTemplateFromDB(formId, pdfLang, "section074", templateParams);
		String section74Value = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_SERVICE_REQ_DATE);
		dto.setSection74(getTemplateAndValuePadWithUnderline(section74Template, section74Value));
		
		String section75Template = getTemplateFromDB(formId, pdfLang, "section075", templateParams);
		String section75Value = "";
		
		String serviceReqDateStr = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_SERVICE_REQ_DATE);
		if (StringUtils.isNotBlank(serviceReqDateStr)) {
			Date serviceReqDate = Utility.string2Date(serviceReqDateStr);
			if (serviceReqDate != null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(serviceReqDate);
				
				String vasContractPeriodStr = "";
				if (StringUtils.equalsIgnoreCase(formId, TRAVEL_INSURANCE_FORM_FORM_ID)) {
					vasContractPeriodStr = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_CONTRACT_PERIOD);
				} else if (StringUtils.equalsIgnoreCase(formId, HELPERCARE_INSURANCE_FORM_FORM_ID)) {
					vasContractPeriodStr = getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_CONTRACT_PERIOD);
				}
				Integer vasContractPeriod = null;
				if (StringUtils.isNotBlank(vasContractPeriodStr) && StringUtils.isNumeric(vasContractPeriodStr)) {
					vasContractPeriod = Integer.parseInt(vasContractPeriodStr);
				}
				if (vasContractPeriod != null) {
					cal.add(Calendar.MONTH, vasContractPeriod);
					section75Value = Utility.date2String(cal.getTime());
				} else {
					section75Value = "NA";
				}
			} else {
				section75Value = "NA";
			}
		}
		
		dto.setSection75(getTemplateAndValuePadWithUnderline(section75Template, section75Value));
		
		String section71Template = getTemplateFromDB(formId, pdfLang, "section071", templateParams);
		String section71Value = getValueFromMap(genericReportDataMap, "shopCode");
		section71Value = StringUtils.isBlank(section71Value)? "" : "P" + section71Value;
		dto.setSection71(getTemplateAndValuePadWithUnderline(section71Template, section71Value));
		
		String section76Template = getTemplateFromDB(formId, pdfLang, "section076", templateParams);
		String section76Value = "";
		if (StringUtils.equalsIgnoreCase(formId, TRAVEL_INSURANCE_FORM_FORM_ID)) {
			section76Value =  getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_STAFF_ID);
		} else if (StringUtils.equalsIgnoreCase(formId, HELPERCARE_INSURANCE_FORM_FORM_ID)) {
			section76Value =  getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_STAFF_ID);
		}
		dto.setSection76(getTemplateAndValuePadWithUnderline(section76Template, section76Value));
		
		dto.setSection8(getTemplateFromDB(formId, pdfLang, "section08", templateParams));
		
		dto.setSection9(getTemplateFromDB(formId, pdfLang, "section09", templateParams));
		
		dto.setSection10(getTemplateFromDB(formId, pdfLang, "section10", templateParams));
		
		dto.setSection11(getTemplateFromDB(formId, pdfLang, "section11", templateParams));
		
		
		String section12Value = "";
		if (StringUtils.equalsIgnoreCase(formId, TRAVEL_INSURANCE_FORM_FORM_ID)) {
			section12Value =  getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_TRAVEL_INSURANCE_DM_IND);
		} else if (StringUtils.equalsIgnoreCase(formId, HELPERCARE_INSURANCE_FORM_FORM_ID)) {
			section12Value =  getValueFromMap(genericReportDataMap, REPORT_DATA_MAP_KEY_HELPERCARE_INSURANCE_DM_IND);
		}
		dto.setSection12(section12Value);
		
		dto.setSection13(getTemplateFromDB(formId, pdfLang, "section13", templateParams));
		
		dto.setSection14(getTemplateFromDB(formId, pdfLang, "section14", templateParams));
		
		boolean isCallCenterOrder = StringUtils.startsWithIgnoreCase(orderId, "C");
		if (isCallCenterOrder) {
			dto.setSection15(null);
			dto.setSection16(null);
		} else {
			dto.setSection15(getTemplateFromDB(formId, pdfLang, "section15", templateParams));
			
			if (genericReportSignatureMap != null) {
				dto.setSection16(genericReportSignatureMap.get(genericForm.formName));
			}
		}
		
		String section17Template = getTemplateFromDB(formId, pdfLang, "section17", templateParams);
		String section17Value = getValueFromMap(genericReportDataMap, "appInDate");
		dto.setSection17(section17Template + section17Value);
		
		dto.setSection18(getTemplateFromDB(formId, pdfLang, "section18", templateParams));		
		
		return dto;
	}
	
	public Form getGenericForm(String genericFormName) {
		for (GenericForm genericForm : GenericForm.values()) {
		  	if (StringUtils.equalsIgnoreCase(genericForm.formName, genericFormName)) {
		  		return genericForm.form;
		  	}
		}
		return null;
	}
	
	public String getGenericFormFileName(String orderId, String msisdn, Form form) {
		
		String orderIdPart = "";
		if (StringUtils.isNotBlank(orderId)) {
			orderIdPart = orderId + "_";
		}
		
		String msisdnPart = "";
		if (StringUtils.isNotBlank(msisdn)) {
			msisdnPart = msisdn + "_";
		}
		
		String genericFormIdPart = "";
		String genericFormId = getCorrespondingGenericFormId(form);
		if (StringUtils.isNotBlank(genericFormId)) {
			genericFormIdPart = genericFormId;
		} else {
			if (form == Form.PROJECT_EAGLE_FORM) {
				genericFormIdPart = ProjectEagleReportHelper.FORM_ID;
			}
		}
		
		String genericFormFileNameFormat = "%s%s%s.pdf";
		return String.format(genericFormFileNameFormat, orderIdPart, msisdnPart, genericFormIdPart);
		
	}
	
	public GenericForm getCorrespondingGenericForm(Form form) {
		for (GenericForm genericForm : GenericForm.values()) {
		  	if (genericForm.form == form) {
		  		return genericForm;
		  	}
		}
		return null;
	}
	
	public String getCorrespondingGenericFormId(Form form) {
		for (GenericForm genericForm : GenericForm.values()) {
		  	if (genericForm.form == form) {
		  		return genericForm.formId;
		  	}
		}
		return null;
	}
	
	private String getTemplateAndValue(String template, String value) {
		return String.format("%s<u>%s</u>", template, value);
	}
	
	private String padWithUnderline(String content) {
		return StringUtils.rightPad(content, 50, "_");
	}
	
	private String getTemplateAndValuePadWithUnderline(String template, String value) {
		if (StringUtils.isBlank(template) && StringUtils.isBlank(value)) {
			return "";
		}
		return padWithUnderline(getTemplateAndValue(template, value));
	}
	
	private String getTemplateFromDB(String formId, String pdfLang, String sectionNumber, Map<String, Object> templateParams) {
		String template = reportService.getReportContentHtml(formId, pdfLang, sectionNumber);
		template = Utility.fillInVariables(template, templateParams);
		return StringUtils.isBlank(template)? "" : template;
	}
	
	private String getValueFromMap(Map<String, String> genericReportDataMap, String key) {
		String value = genericReportDataMap.get(key);
		return StringUtils.isBlank(value)? "" : value;
	}
	
}
