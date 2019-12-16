package com.pccw.rpt.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.bomwebportal.configuration.BomPropertyPlaceholderConfigurer;
import com.bomwebportal.service.ReportCreationService;
import com.bomwebportal.service.ReportDataService;
import com.bomwebportal.util.PdfUtil;
import com.bomwebportal.util.ReportUtil;
import com.pccw.rpt.dao.BomwebRptTemplateVDAO;
import com.pccw.rpt.dao.WItemRptTemplateVDAO;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.concertTicketForm.ConcertTicketRptDTO;
import com.pccw.util.spring.SpringApplicationContext;

public class ReportServiceImpl implements ReportService {
	
	private static Logger logger = Logger.getLogger(ReportServiceImpl.class);
	private String storePathPrefix = null;
	
	@Override
	public ReportDTO generateReport(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, OutputStream pOutputStream) throws Exception {
		return generateReport(pReportName, pLanguage, pRptParamMap, pOutputStream, EXPORT_TYPE_PDF);
	}

	@Override
	public String generateEditButtonHtml(WItemRptTemplateVDAO pDao) {
		return generateEditButtonHtml("REPORT", "W_ITEM", pDao.getDisplayType(), pDao.getItemId(), pDao.getLocale(), "EDIT");
	}

	@Override
	public String generateEditButtonHtml(BomwebRptTemplateVDAO pDao) {
		return generateEditButtonHtml("REPORT", pDao.getRptName(), pDao.getAttribute(), "-1", pDao.getLanguage(), "EDIT");
	}
	
	@Override
	public String generateNewEditButtonHtml(String pReportName,	String pAttribute, String pLanguage) {
		return generateEditButtonHtml("REPORT", pReportName, pAttribute, "-1", pLanguage, "Add " + pAttribute);
	}
	
	private String generateEditButtonHtml(String pEditType, String pReportName,
			String pAttribute, String pItemId,
			String pLanguage, String pButtonCaption) {
		StringBuilder sbHtml = new StringBuilder("<a style=\"color:blue;\" href=\"");
		try {
			sbHtml.append(((BomPropertyPlaceholderConfigurer) SpringApplicationContext.getBean("propertyConfigurer")).getPropertyByEnvironment("searchReportTemplateUrl"));
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
		}
		sbHtml.append("?");
		sbHtml.append("paramString={editType,rptName,itemId,attribute,language};{");
		sbHtml.append(pEditType);
		sbHtml.append(",");
		sbHtml.append(pReportName);
		sbHtml.append(",");
		sbHtml.append(pItemId);
		sbHtml.append(",");
		sbHtml.append(pAttribute);
		sbHtml.append(",");
		sbHtml.append(pLanguage);
		sbHtml.append(",");
		sbHtml.append("}\">&nbsp;[");
		sbHtml.append(pButtonCaption);
		sbHtml.append("]</a>");
		return sbHtml.toString();
	}
	
	@Override
	public ReportDTO generateReport(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, OutputStream pOutputStream, int pExportType) throws Exception {
		
		String reportDtoBeanId = null;
		
		if(pReportName.startsWith("pdfTemplate/lts")){
			if(pReportName.endsWith("productSpec")){
				reportDtoBeanId = "ReportDTO-pdfTemplate.lts.productSpec";
			}
			if(pReportName.endsWith("itemSpec")){
				reportDtoBeanId = "ReportDTO-pdfTemplate.lts.itemSpec";
			}
		}
		
		if(reportDtoBeanId == null){
			reportDtoBeanId = "ReportDTO-" + pReportName.replaceAll("/", ".");
		}
		
		ReportDTO reportDTO = SpringApplicationContext.getBean(reportDtoBeanId);
		
			//added by ims steven 20130208
			System.out.println("Using Bean: " + reportDtoBeanId);
			//added by ims steven 20130208 end
			
		generateReport(pReportName, pLanguage, pRptParamMap, reportDTO, pOutputStream, pExportType);
		return reportDTO;
	}

	@Override
	public void generateReport(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO,
			OutputStream pOutputStream, int pExportType) throws Exception {
		generateReport(pReportName, pLanguage, pRptParamMap, pReportDTO, pOutputStream, pExportType, false);
	}
	
	@Override
	public void reGenerateReport(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO,
			OutputStream pOutputStream, int pExportType) throws Exception {
		generateReport(pReportName, pLanguage, pRptParamMap, pReportDTO, pOutputStream, pExportType, true);
	}

	private void generateReport(String pReportName, String pLanguage,
			Map<String, Object> pRptParamMap, ReportDTO pReportDTO,
			OutputStream pOutputStream, int pExportType, boolean pIsRptDtoRetrieved) throws Exception {
		if (StringUtils.isBlank(pLanguage)) {
			pLanguage = "en";
		}
		//added by ims steven 20130208
		System.out.println("pReportName:"+pReportName);
		if(pReportName.indexOf("IMS") == 3){
			String temp = pReportName.replace("IMS", "");
			System.out.println("temp:"+temp);
			pRptParamMap.put(ReportDataService.PARAM_RPT_NAME, temp);
		}else{
		//added by ims steven 20130208 end
			pRptParamMap.put(ReportDataService.PARAM_RPT_NAME, pReportName);
		}
		pRptParamMap.put(ReportDataService.PARAM_RPT_LANGUAGE, pLanguage);
		if (!pIsRptDtoRetrieved) {
			SpringApplicationContext.getBean(ReportFixedDataService.class).fillReportFixedData(pReportName, pLanguage, pRptParamMap, pReportDTO);
			SpringApplicationContext.getBean(ReportDataService.class).fillReportData(pReportDTO, pRptParamMap);
			SpringApplicationContext.getBean(ReportFixedDataService.class).postFillReportData(pReportName, pLanguage, pRptParamMap, pReportDTO);
		}
		if (pExportType == EXPORT_TYPE_RPT_DTO_JAVA_OBJ) {
			ObjectOutputStream out = new ObjectOutputStream(pOutputStream);
            out.writeObject(pReportDTO);
            out.flush();
			return;
		}
		
		
		if (pReportName.contains("pdfTemplate")) {
			fillInPdf(pReportName, pLanguage, pReportDTO, pOutputStream);
			return;
		}
		
		generateJasperReport(pReportName, pLanguage, pReportDTO, pOutputStream, pExportType);
	}

	
	private void generateJasperReport(String pReportName, String pLanguage,
			ReportDTO pReportDTO, OutputStream pOutputStream, int pExportType) throws Exception {
		ArrayList<ReportDTO> dataList = new ArrayList<ReportDTO>();
		dataList.add(pReportDTO);
		JRDataSource ds = new JRBeanCollectionDataSource(dataList);
		
		String reportClassName = pReportName.substring(pReportName.lastIndexOf("/") + 1);
		reportClassName = StringUtils.left(reportClassName, 1).toUpperCase() + 
				reportClassName.substring(1);
		reportClassName = pReportName + "/" + pLanguage + "/" + reportClassName;
		//added by ims steven 20130208
		System.out.println("reportClassName:"+reportClassName);
		//added by ims steven 20130208 end
		
		if (pExportType == EXPORT_TYPE_RTF) {
			ReportUtil.generateRtfReport(reportClassName, ds, pOutputStream);
		} else if (pExportType == EXPORT_TYPE_PDF) {
			ReportUtil.generatePdfReport(reportClassName, ds, pOutputStream);
		}
	}
	
	private void fillInPdf(String pReportName, String pLanguage,
			ReportDTO pReportDTO, OutputStream pOutputStream) throws Exception {
		String pdfTemplateFileName = pReportName.substring(pReportName.lastIndexOf("/") + 1);
		if (!(pReportDTO instanceof ConcertTicketRptDTO )) {
			pdfTemplateFileName = StringUtils.left(pdfTemplateFileName, 1).toUpperCase() + pdfTemplateFileName.substring(1);
		}
		
		pdfTemplateFileName = pReportName + "/" + pLanguage + "/" + pdfTemplateFileName + ".pdf";
		
		TreeMap<String, String> formValueMap = new TreeMap<String, String>();
		TreeMap<String, byte[]> formImgValueMap = new TreeMap<String, byte[]>();
		byte[] image = null;
		
		Field[] rptFields = pReportDTO.getClass().getDeclaredFields();
		for (Field rptField : rptFields) {
			try {
				
				if (String.class.equals(rptField.getType())) {
					formValueMap.put(rptField.getName(), BeanUtils.getProperty(pReportDTO, rptField.getName()));
				} else if (byte[].class.equals(rptField.getType())) {
					image = (byte[]) PropertyUtils.getProperty(pReportDTO, rptField.getName());
					if (!ArrayUtils.isEmpty(image)) {
						formImgValueMap.put(rptField.getName(), image);	
					}
				}
			} catch (Exception ignore) {
			}
		}
		formValueMap.putAll(pReportDTO.getFieldCssMap());
		
		InputStream formPdfInputStream = ReportServiceImpl.class.getResourceAsStream("/" + pdfTemplateFileName);
		
		try{
			if(pReportName.contains("lts")
					&& (pdfTemplateFileName.contains("ProductSpec") || pdfTemplateFileName.contains("ItemSpec"))
					&& storePathPrefix != null){
				formPdfInputStream = new FileInputStream(storePathPrefix + "/" + pdfTemplateFileName);
			}
		}catch (Exception ignore) {
		}
		
	    PdfUtil.fillPdfForm(formPdfInputStream, formValueMap, formImgValueMap, pOutputStream);
	}

	@Override
	public void generateSWReport(Map<String, String> pRptParamMap, Map<String, byte[]> pRptImgMap, String pLang, OutputStream pOs) throws Exception {
		String path = null;
		boolean amendment = pRptParamMap.get("oProduct1") != null;
		if (ReportCreationService.LANG_ENG.endsWith(pLang)) {
			if (amendment) {
				path = "/pdfTemplate/lts/smartWrtyForm/en/SWAF_AMEND.pdf";
			} else {
				path = "/pdfTemplate/lts/smartWrtyForm/en/SWAF.pdf";
			}
		} else if (ReportCreationService.LANG_CHI.endsWith(pLang)) {

			if (amendment) {
				path = "/pdfTemplate/lts/smartWrtyForm/zh_HK/SWAF_AMEND.pdf";
			} else {
				path = "/pdfTemplate/lts/smartWrtyForm/zh_HK/SWAF.pdf";
			}
		}
		InputStream io = ReportServiceImpl.class.getResourceAsStream(path);
		PdfUtil.fillPdfForm(io, pRptParamMap, pRptImgMap, pOs);
	}

	public String getStorePathPrefix() {
		return storePathPrefix;
	}

	public void setStorePathPrefix(String storePathPrefix) {
		this.storePathPrefix = storePathPrefix;
	}
}