package com.bomwebportal.htmlpdf.bean;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bomwebportal.dto.report.MobPreActReqDTO;
import com.bomwebportal.htmlpdf.IPDFReport;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.MobPreActReqService;
import com.bomwebportal.util.BomWebPortalConstant;

@Component("pdf.mob.ActivationLetter")
public class ActivationLetterBean implements IPDFReport {
	private final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private CustomerProfileService customerProfileService;
	@Autowired
	private MobPreActReqService mobPreActReqService;
	
	public String getView(HttpServletRequest request) {
		String templateType = request.getParameter("templateType");
		String lang = request.getParameter("lang");
		logger.debug("ActivationLetterBean getView templateType = " + templateType + ", lang = " + lang);
		
		if ("zh_HK".equalsIgnoreCase(lang)) {
			if ("R".equalsIgnoreCase(templateType)) {
				return "report/activationletterrszh";
			} else if ("D".equalsIgnoreCase(templateType)) {
				return "report/activationletterdszh";
			} else {
				return "report/activationletterccszh";
			}
		} else {
			if ("R".equalsIgnoreCase(templateType)) {
				return "report/activationletterrs";
			} else if ("D".equalsIgnoreCase(templateType)) {
				return "report/activationletterds";
			} else {
				return "report/activationletterccs";
			}
		}
	}

	public void fillModel(HttpServletRequest request, Map<String, Object> model) {
		Format dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		/************  Start getting parameters ********************/
		String msisdn = request.getParameter("msisdn");
		String filepath = request.getParameter("filepath");
		String filename = request.getParameter("filename");
		String templateType = request.getParameter("templateType");
		String lang = "zh_HK".equalsIgnoreCase(request.getParameter("lang")) ? "zh_HK" : "en";

		if (msisdn == null || filepath == null || filename == null) {
			logger.error("Not enough parameters");
			return;
		}
		logger.debug("ActivationLetterBean msisdn = " + msisdn + ", filepath = " + filepath + ", filename = " + filename + ", lang = " + lang + ", templateType = " + templateType);
		/************  End getting parameters ********************/
		
		MobPreActReqDTO mobPreActReqDTO = mobPreActReqService.getProcessActivationLetterDetail(msisdn);
		mobPreActReqDTO.setSmsLang(lang);
		mobPreActReqDTO.setTemplateType(templateType);
		
		if ("0".equalsIgnoreCase(mobPreActReqDTO.getBrand())) {
			mobPreActReqDTO.setCompanyLogo(BomWebPortalConstant.COMPANY_LOGO_TOP_1010);
			mobPreActReqDTO.setBrandString("1O1O");
			mobPreActReqDTO.setServiceString("服務");
			mobPreActReqDTO.setShopString("1O1O Center");
		} else {
			mobPreActReqDTO.setCompanyLogo(BomWebPortalConstant.COMPANY_LOGO_TOP_M2);
			if ("zh_HK".equalsIgnoreCase(lang)){
				mobPreActReqDTO.setBrandString("香港移動通訊有限公司之\"csl流動電話服務\"");
				mobPreActReqDTO.setServiceString("\"csl流動電話服務\"");
				mobPreActReqDTO.setShopString("csl店舖");
			}
			else{
				mobPreActReqDTO.setBrandString("csl");
				mobPreActReqDTO.setShopString("shop");
			}
		}
		
		if ("zh_HK".equalsIgnoreCase(lang)) {
			mobPreActReqDTO.setCompanyBottomLogo(BomWebPortalConstant.COMPANY_LOGO_BOTTOM_RT_CHI_HTML2PDF);
		} else {
			mobPreActReqDTO.setCompanyBottomLogo(BomWebPortalConstant.COMPANY_LOGO_BOTTOM_RT_ENG_HTML2PDF);
		}
		
		if ("R".equalsIgnoreCase(templateType)) {
			mobPreActReqDTO.setShopDTO(mobPreActReqService.getShopDetail(mobPreActReqDTO.getShopCd()));
		}
		
		String srdDateBefore = mobPreActReqDTO.getSrdDate();
        Format inFormat = new SimpleDateFormat("ddMMyyyy");
        String srdDate = "";
        try {
			srdDate = dateFormatter.format(((SimpleDateFormat)inFormat).parse(srdDateBefore));
        } catch (ParseException e) {
        	srdDate = srdDateBefore.substring(0,2)+"/"+srdDateBefore.substring(2,4)+"/"+srdDateBefore.substring(4,8);
		}

		if (mobPreActReqDTO != null) {
			model.put("msisdn", mobPreActReqDTO.getMsisdn());
			model.put("agreementNum", mobPreActReqDTO.getAgreementNum());
			model.put("activationCode", mobPreActReqDTO.getActivationCode());
			model.put("custName", mobPreActReqDTO.getCustName());
			model.put("addrLn1", mobPreActReqDTO.getAddrLn1());
			model.put("addrLn2", mobPreActReqDTO.getAddrLn2());
			model.put("addrLn3", mobPreActReqDTO.getAddrLn3());
			model.put("addrLn4", mobPreActReqDTO.getAddrLn4());
			model.put("addrLn5", mobPreActReqDTO.getAddrLn5());
			model.put("sendDate",dateFormatter.format(new Date()));
			model.put("templateType",mobPreActReqDTO.getTemplateType());
			/*model.put("shopEmail", mobPreActReqDTO.getShopEmail());
			model.put("shopFax", mobPreActReqDTO.getShopFax());*/
			model.put("srdDate",srdDate);
			model.put("companyLogo", "images/" + mobPreActReqDTO.getCompanyLogo());
			model.put("companyLogoBottom", "images/" + mobPreActReqDTO.getCompanyBottomLogo());
			model.put("shopInfo", mobPreActReqDTO.getShopDTO());
			model.put("brandString",mobPreActReqDTO.getBrandString());
			model.put("serviceString",mobPreActReqDTO.getServiceString());
			model.put("shopString", mobPreActReqDTO.getShopString());
		}
	}

}
