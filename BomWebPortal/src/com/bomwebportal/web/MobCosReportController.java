package com.bomwebportal.web;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.SignoffDTO.SignatureTypeEnum;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.service.MobCosReportService;
import com.bomwebportal.util.FastByteArrayOutputStream;
import com.google.gson.Gson;


public class MobCosReportController extends SimpleFormController{
	private MobCosReportService mobCosReportService;

	public MobCosReportService getMobCosReportService() {
		return mobCosReportService;
	}

	public void setMobCosReportService(MobCosReportService mobCosReportService) {
		this.mobCosReportService = mobCosReportService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		Date startTime = new Date();
		logger.debug("Start generate pdf: " + startTime);
		System.out.println("Start generate pdf: " + startTime);
		
		/************  Start getting parameters ********************/
		/*** Neccessary Parameters ***/
		String orderId = request.getParameter("orderId");
		String pdfLang = request.getParameter("pdfLang");// en, zh->zh_HK
		if ("zh".equalsIgnoreCase(pdfLang)) {
			pdfLang = BomWebPortalCcsConstant.REPORT_LOCALE_ZH_HK;
		}
		String nature = request.getParameter("nature");
		String channel = request.getParameter("channel");
		
		if (orderId == null || pdfLang == null || nature == null || channel == null) {
			logger.error("Not enough parameters");
			return null;
		}
		if ("BRM".equals(nature) || "TOO1".equals(nature)){
			nature = "COS";
		}
		
		/*** Selective Parameters ***/
		String companyCopyInd = this.markNIfNotY(request.getParameter("companyCopyInd"));
		String signJson = request.getParameter("sign");
		String copInd = this.markNIfNotY(request.getParameter("copInd"));
		String delInd = this.markNIfNotY(request.getParameter("delInd"));
		String dmsInd = this.markNIfNotY(request.getParameter("dmsInd"));
		String save = this.markNIfNotY(request.getParameter("save")); //Y - generate File, others - get Output Stream Only
		String filePath = ("null".equalsIgnoreCase(request.getParameter("filePath"))) ? "" : request.getParameter("filePath");
		String watermark = request.getParameter("watermark");
		if (watermark == null || "".equals(watermark) || "null".equalsIgnoreCase(watermark) || "N".equalsIgnoreCase(watermark)) {
			watermark = "N";
		}
		String updateStatus = this.markNIfNotY(request.getParameter("updateStatus"));
		String formIdsParm = request.getParameter("formIds");
		List<String> formIds = null;
		if (!(formIdsParm == null || "".equals(formIdsParm) || "null".equalsIgnoreCase(formIdsParm))) {
			formIds = new ArrayList<String>();
			Pattern afPatthern = Pattern.compile("AF[0-9]{3}");
			Matcher match = afPatthern.matcher(formIdsParm);
			while(match.find()) {
				formIds.add(match.group());
			}
			Pattern travelInsuranceFormPatthern = Pattern.compile("TR001");
			Matcher gtravelInsuranceFormMatcher = travelInsuranceFormPatthern.matcher(formIdsParm);
			while(gtravelInsuranceFormMatcher.find()) {
				formIds.add(gtravelInsuranceFormMatcher.group());
			}
			Pattern helperCareInsuranceFormPatthern = Pattern.compile("HC001");
			Matcher helperCareInsuranceFormMatcher = helperCareInsuranceFormPatthern.matcher(formIdsParm);
			while(helperCareInsuranceFormMatcher.find()) {
				formIds.add(helperCareInsuranceFormMatcher.group());
			}
			Pattern projectEagleInsuranceFormPatthern = Pattern.compile("PE001");
			Matcher projectEagleInsuranceFormMatcher = projectEagleInsuranceFormPatthern.matcher(formIdsParm);
			while(projectEagleInsuranceFormMatcher.find()) {
				formIds.add(projectEagleInsuranceFormMatcher.group());
			}
		}
		/************  End getting parameters ********************/
		
		Map<String, SignoffDTO> signOffMap = this.SignatureJson2Map(signJson);
		
		response.setContentType("application/pdf");
		response.addHeader("Content-disposition", "attachment; filename="+ this.getFileName(orderId, pdfLang));
		response.setHeader("Cache-Control", "private");
		
		OutputStream pOutputStream = response.getOutputStream();
		FastByteArrayOutputStream baosMerged = new FastByteArrayOutputStream();
		try {
			byte[] pdfData = mobCosReportService.generateMobAppForm(
													baosMerged, orderId, pdfLang, save, nature, channel, 
													companyCopyInd, signOffMap, copInd, delInd, 
													dmsInd, filePath, watermark, updateStatus, formIds
												 );
			pOutputStream.write(pdfData);
			pOutputStream.flush();
	
			baosMerged.close();
			pOutputStream.close();
			
			logger.debug("End generate pdf");
			System.out.println("End generate pdf");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Exception caught in MobCosReportController: " + e.getMessage());
		}
		return null;
	}
	
	private String markNIfNotY(String parm) {
		return "Y".equalsIgnoreCase(parm) ? "Y" : "N";
	}
	
	private Map<String, SignoffDTO> SignatureJson2Map(String signJson) {
		Map<String, SignoffDTO> signOffMap = new HashMap<String, SignoffDTO>();
		if (signJson != null && !"null".equalsIgnoreCase(signJson)) {
			Gson gson = new Gson();
			SignoffDTO[] signoffDTOArray = gson.fromJson(signJson, SignoffDTO[].class);
			
			for (int i = 0; i < signoffDTOArray.length; i++) {
				if (signoffDTOArray[i] != null) {
					if (SignatureTypeEnum.MULTISIM_MNP_COO_SIGN.equals(signoffDTOArray[i].getSignatureType())) {
						signOffMap.put(signoffDTOArray[i].getOrderId(), signoffDTOArray[i]);
					} else {
						signOffMap.put(signoffDTOArray[i].getSignatureType().toString(), signoffDTOArray[i]);
					}
					
				}
			}
		}
		return signOffMap;
	}
	
	private String getFileName(String orderId, String locale) {
		String filename = orderId;
		if (orderId!= null) {

			if (BomWebPortalCcsConstant.REPORT_LOCALE_ZH_HK.equals(locale)) {
				filename = filename + BomWebPortalCcsConstant.REPORT_CHI;
			} else {
				if (locale == null) {
					locale = "en";
				}
				filename = filename + BomWebPortalCcsConstant.REPORT_EN;
			}
		}
		return filename;
	}
	
}
