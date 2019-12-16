package com.pccw.rpt.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;

import com.bomwebportal.service.ReportDataService;
import com.pccw.rpt.dao.WItemRptTemplateVDAO;
import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.schema.dto.eye.eyeAppForm.EyeAppFormRptDTO;
import com.pccw.rpt.service.ReportService;
import com.pccw.rpt.util.ReportUtil;
import com.pccw.util.spring.SpringApplicationContext;

public class DummyDataReportDataServiceImpl implements ReportDataService {

	@Override
	public void fillReportData(ReportDTO pReportDTO, Map<String, Object> pRptParamMap) {
		String reportName = (String) pRptParamMap.get(ReportDataService.PARAM_RPT_NAME);
		if ("lts/eye/delUpgradeEye2AppForm".equals(reportName)) {
			String dataSource = (String) pRptParamMap.get("DATA_SOURCE");
			if ("HARDCODE".equals(dataSource)) {
				fillReportDataEye(pReportDTO, pRptParamMap);
			} else if ("ALL_ITEM".equals(dataSource)) {
				fillReportDataEyeAllItem(pReportDTO, pRptParamMap);
			}
		} else if ("wq/wqCoverSheet".equals(reportName)) {
			fillReportDataWq(pReportDTO, pRptParamMap);
		}
	}

	private void fillReportDataEye(ReportDTO pReportDTO, Map<String, Object> pArg1) {
		EyeAppFormRptDTO rptDTO = (EyeAppFormRptDTO) pReportDTO;

		try {
			BeanUtils.setProperty(rptDTO, "applicationNumber", "TTW1231235");
			BeanUtils.setProperty(rptDTO, "applicationDate", "18/01/2012");
			BeanUtils.setProperty(rptDTO, "eyeApplication", "TRUE");
			BeanUtils.setProperty(rptDTO, "resTelApplication", "FALSE");
			BeanUtils.setProperty(rptDTO, "customerCopy", "FALSE");
			BeanUtils.setProperty(rptDTO, "thirdPartyInd", "TRUE");
			BeanUtils.setProperty(rptDTO, "printTermsCondition", "FALSE");
			
			BeanUtils.setProperty(rptDTO, "sectionA.customer.idVerifiedInd", "Y");
			BeanUtils.setProperty(rptDTO, "sectionA.customer.idDocType", "HKID");
			BeanUtils.setProperty(rptDTO, "sectionA.iaInd", "Y");
			
			BeanUtils.setProperty(rptDTO, "sectionA.address.unitNo", "11");
			BeanUtils.setProperty(rptDTO, "sectionA.address.floorNo", "14");
			BeanUtils.setProperty(rptDTO, "sectionA.address.blockNo", "Heng Hoi");
			BeanUtils.setProperty(rptDTO, "sectionA.address.buildNo", "Heng On Estate");
			BeanUtils.setProperty(rptDTO, "sectionA.address.strNo", "112");
			BeanUtils.setProperty(rptDTO, "sectionA.address.strName", "ABC Street");
			BeanUtils.setProperty(rptDTO, "sectionA.address.strCatDesc", "hah");
			BeanUtils.setProperty(rptDTO, "sectionA.address.sectDesc", "Ma On Shan");
			BeanUtils.setProperty(rptDTO, "sectionA.address.distDesc", "NT");
			BeanUtils.setProperty(rptDTO, "sectionA.address.areaDesc", "HK");
			BeanUtils.setProperty(rptDTO, "sectionA.baInd", "N");
			
			rptDTO.getSectionC().addEyePlan("EYE HOME SMARTPHONE BASIC PLAN 1 with 100 IDD060 minutes per month", "$128 / Mth", "$128 / Mth");
			
			/*
			rptDTO.getSectionD().addEyeSubscribedItem("BB-RENTAL", "<span style=\"font-weight: bold;\">$30 Line Rental</span>", "$30", "$30");
			
			rptDTO.getSectionD().addEyeSubscribedItem("PE-FREE", "<span style=\"font-weight: bold;\">Parallel Phone Line Service</span><br />(Include one Free Socket Installation Fee)<span style=\"font-weight: bold;\"></span>", "Waived", "$30");
			rptDTO.getSectionD().addEyeSubscribedItem("PE-SOCKET", "<span style=\"font-weight: bold;\">Additional Socket(s) Installation ($200 per socket)</span>", 2, "N/A", "N/A");
			
			rptDTO.getSectionD().addEyeSubscribedItem("CONTENT", "<span style=\"font-weight: bold;\">Pre-school Pack 1</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addEyeSubscribedItem("CONTENT", "<span style=\"font-weight: bold;\">Primary School Pack 1</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addEyeSubscribedItem("CONTENT", "<span style=\"font-weight: bold;\">Life Style Lite 1</span>", "$18 / Mth", "$18 / Mth");
			
			rptDTO.getSectionD().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV New Releases Channel</span>", "$30 / Mth", "$30 / Mth");
			rptDTO.getSectionD().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Hits Channel</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Kids Channel</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Oldies Channel</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Instrumental Channel</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Pack</span>", "$18 / Mth", "$18 / Mth");
			
			rptDTO.getSectionD().addEyeSubscribedItem("NOWTV-FREE", "<span style=\"font-weight: bold;\">now TV Starter Pack</span><br /> &bull; Traffic Channel &bull; CCTV-4 &bull; CCTV News &bull; Creation TV &bull; Dim Sum TV &bull; Phoenix Chinese Channel &bull; Phoenix InfoNews &bull; ETTV Asia News &bull; Southern Television &bull; NHK World TV &bull; BLOOMBERG TELEVISION &bull; Movie ailer Channel &bull; AI Jazeera English &bull; Da Ai Television &bull; Australia Network &bull; France 24 &bull; Jiangsu Satellite TV &bull; GOOD TV 2 &bull; Pearl River Channel &bull; HKS &bull; Phoenix Hong Kong Channel &bull; CTI Asia Channel<span style=\"font-weight: bold;\"></span>", "N/A", "N/A");
			rptDTO.getSectionD().addEyeSubscribedItem("NOWTV-FREE", "<span style=\"font-weight: bold;\">now TV Free Promotional Channels x 24 Mths</span><br /><span style=\"font-weight: bold;\">(now BNC &amp; now Direct, now News &amp; nowHK)</span><br />", "N/A", "N/A");
			rptDTO.getSectionD().addEyeSubscribedItem("NOWTV-SPEC", "<span style=\"font-weight: bold;\"></span><span style=\"font-weight: bold;\">3 SD Special Pay Channel</span><br />&bull; Ch No.443 Cartoon Network<br />&bull; Ch No. 637 NOW SPORTS 1,3,7<br />&bull; Ch No. 904 Playboy TV (Adult Channel)<br />", "$48 / Mth", "$48 / Mth");
			rptDTO.getSectionD().addEyeSubscribedItem("NOWTV-CELE", "<span style=\"font-weight: bold;\"></span><span style=\"font-weight: bold;\">Additional subscription to CELESTIAL MOVIES (HK$10 X 24months)</span><br />", "$10 / Mth", "$10 / Mth");
			BeanUtils.setProperty(rptDTO, "sectionD.eyeViewAvInd", "Y");
			
			rptDTO.getSectionD().addEyeSubscribedItem("NO-BILL", "View Bill on device<br />", "N/A", "N/A");
			
			rptDTO.getSectionD().addEyeSubscribedItem("TV-EMAIL", "View Bill on device<br />", "N/A", "N/A");
			
			rptDTO.getSectionD().addEyeSubscribedItem("EXIST-VAS", "(only applicable to Upgrade Customers)<br />With the exception of the services set out below, all your existing paid Value-added Service except the Duplex Ringing or Call Waiting II or Do Not Disturb II feature will continue under the terms of your current Contract at the current monthly charges and for the fixed term (if applicable) of the relevant Service (i.e. PCCW Residential Telephone Line Service, eye Multimedia Service and/or Samsung Video Infotainment Service) you are upgrading from.&nbsp; Your existing paid standalone Duplex Ringing or Call Waiting II or Do Not Disturb II feature will be terminated.&nbsp; Your existing paid Value-added Service Package with Duplex Ringing or Call Waiting II or Do Not Disturb II feature will be terminated and you must select another Value-added Service feature or package.<br />", "", "");

			rptDTO.getSectionD().addEyeSubscribedItem("REPLAC-VAS", "Your existing Residential Telephone Line Service Plan includes the following free Value-added Services:-<br /><br />&bull; 12-mth TP with 11 Rebate + Fax Package<br />", "", "");
			rptDTO.getSectionD().addEyeSubscribedItem("REPLAC-VAS", "will be replaced by the following new free Value-added Services upon upgrading to eye Home Smartphone Package:-<br /><br />&bull; Fax Package<br />", "", "");
			
			rptDTO.getSectionD().addEyeSubscribedItem("VAS-HOT", "24-mth Voice Package for eye ($11 x 24-mth)<br />", "", "");
			rptDTO.getSectionD().addEyeSubscribedItem("IDD", "IDD 0060 & 001 Service Registration<br />", "Charges are subject to change, please call customer service hotline 10060 for details.", "Charges are subject to change, please call customer service hotline 10060 for details.");
			rptDTO.getSectionD().addEyeSubscribedItem("0060E", "0060 everywhere (3 Months Free Trial Plan)<br />", "N/A", "N/A");
			rptDTO.getSectionD().addEyeSubscribedItem("PREM-PAY", "SONY 46\" KDL-46EX500<br />", "", "$14,999");
			*/
			
			//Section D resTel
			rptDTO.getSectionD().addResTelSubscribedItem("BB-RENTAL", "<span style=\"font-weight: bold;\">$30 Line Rental</span>", "$30", "$30");
			
			rptDTO.getSectionD().addResTelSubscribedItem("PE-FREE", "<span style=\"font-weight: bold;\">Parallel Phone Line Service</span><br />(Include one Free Socket Installation Fee)<span style=\"font-weight: bold;\"></span>", "Waived", "$30");
			rptDTO.getSectionD().addResTelSubscribedItem("PE-SOCKET", "<span style=\"font-weight: bold;\">Additional Socket(s) Installation ($200 per socket)</span>", 2, "N/A", "N/A");
			
			rptDTO.getSectionD().addResTelSubscribedItem("CONTENT", "<span style=\"font-weight: bold;\">Pre-school Pack 1</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addResTelSubscribedItem("CONTENT", "<span style=\"font-weight: bold;\">Primary School Pack 1</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addResTelSubscribedItem("CONTENT", "<span style=\"font-weight: bold;\">Life Style Lite 1</span>", "$18 / Mth", "$18 / Mth");
			
			rptDTO.getSectionD().addResTelSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV New Releases Channel</span>", "$30 / Mth", "$30 / Mth");
			rptDTO.getSectionD().addResTelSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Hits Channel</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addResTelSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Kids Channel</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addResTelSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Oldies Channel</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addResTelSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Instrumental Channel</span>", "$18 / Mth", "$18 / Mth");
			rptDTO.getSectionD().addResTelSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Pack</span>", "$18 / Mth", "$18 / Mth");
			
			rptDTO.getSectionD().addResTelSubscribedItem("NOWTV-FREE", "<span style=\"font-weight: bold;\">now TV Starter Pack</span><br /> &bull; Traffic Channel &bull; CCTV-4 &bull; CCTV News &bull; Creation TV &bull; Dim Sum TV &bull; Phoenix Chinese Channel &bull; Phoenix InfoNews &bull; ETTV Asia News &bull; Southern Television &bull; NHK World TV &bull; BLOOMBERG TELEVISION &bull; Movie ailer Channel &bull; AI Jazeera English &bull; Da Ai Television &bull; Australia Network &bull; France 24 &bull; Jiangsu Satellite TV &bull; GOOD TV 2 &bull; Pearl River Channel &bull; HKS &bull; Phoenix Hong Kong Channel &bull; CTI Asia Channel<span style=\"font-weight: bold;\"></span>", "N/A", "N/A");
			rptDTO.getSectionD().addResTelSubscribedItem("NOWTV-FREE", "<span style=\"font-weight: bold;\">now TV Free Promotional Channels x 24 Mths</span><br /><span style=\"font-weight: bold;\">(now BNC &amp; now Direct, now News &amp; nowHK)</span><br />", "N/A", "N/A");
			rptDTO.getSectionD().addResTelSubscribedItem("NOWTV-SPEC", "<span style=\"font-weight: bold;\"></span><span style=\"font-weight: bold;\">3 SD Special Pay Channel</span><br />&bull; Ch No.443 Cartoon Network<br />&bull; Ch No. 637 NOW SPORTS 1,3,7<br />&bull; Ch No. 904 Playboy TV (Adult Channel)<br />", "$48 / Mth", "$48 / Mth");
			rptDTO.getSectionD().addResTelSubscribedItem("NOWTV-CELE", "<span style=\"font-weight: bold;\"></span><span style=\"font-weight: bold;\">Additional subscription to CELESTIAL MOVIES (HK$10 X 24months)</span><br />", "$10 / Mth", "$10 / Mth");
			BeanUtils.setProperty(rptDTO, "sectionD.resTelViewAvInd", "Y");
			
			rptDTO.getSectionD().addResTelSubscribedItem("NO-BILL", "View Bill on device<br />", "N/A", "N/A");
			
			rptDTO.getSectionD().addResTelSubscribedItem("TV-EMAIL", "View Bill on device<br />", "N/A", "N/A");
			
			rptDTO.getSectionD().addResTelSubscribedItem("EXIST-VAS", "(only applicable to Upgrade Customers)<br />With the exception of the services set out below, all your existing paid Value-added Service except the Duplex Ringing or Call Waiting II or Do Not Disturb II feature will continue under the terms of your current Contract at the current monthly charges and for the fixed term (if applicable) of the relevant Service (i.e. PCCW Residential Telephone Line Service, eye Multimedia Service and/or Samsung Video Infotainment Service) you are upgrading from.&nbsp; Your existing paid standalone Duplex Ringing or Call Waiting II or Do Not Disturb II feature will be terminated.&nbsp; Your existing paid Value-added Service Package with Duplex Ringing or Call Waiting II or Do Not Disturb II feature will be terminated and you must select another Value-added Service feature or package.<br />", "", "");

			rptDTO.getSectionD().addResTelSubscribedItem("REPLAC-VAS", "Your existing Residential Telephone Line Service Plan includes the following free Value-added Services:-<br /><br />&bull; 12-mth TP with 11 Rebate + Fax Package<br />", "", "");
			rptDTO.getSectionD().addResTelSubscribedItem("REPLAC-VAS", "will be replaced by the following new free Value-added Services upon upgrading to eye Home Smartphone Package:-<br /><br />&bull; Fax Package<br />", "", "");
			
			rptDTO.getSectionD().addResTelSubscribedItem("VAS-2DEL-H", "24-mth Voice Package for eye ($11 x 24-mth)<br />", "", "");
			rptDTO.getSectionD().addResTelSubscribedItem("IDD", "IDD 0060 & 001 Service Registration<br />", "Charges are subject to change, please call customer service hotline 10060 for details.", "Charges are subject to change, please call customer service hotline 10060 for details.");
			rptDTO.getSectionD().addResTelSubscribedItem("0060E", "0060 everywhere (3 Months Free Trial Plan)<br />", "N/A", "N/A");
			rptDTO.getSectionD().addResTelSubscribedItem("PREM-PAY", "SONY 46\" KDL-46EX500<br />", "", "$14,999");
			
			rptDTO.getSectionD().addEyeNowTvChannel("1111");
			rptDTO.getSectionD().addEyeNowTvChannel("2222");
			
			rptDTO.getSectionE().addEyePremiumItem("Installation Premium - PCCW Touch Phone<br />", "$988");
			
			//BeanUtils.setProperty(rptDTO, "sectionK.existPaymentMethodInd", "C");
			BeanUtils.setProperty(rptDTO, "sectionK.existPaymentStatement", "Statment here");
			
			BeanUtils.setProperty(rptDTO, "sectionK.paymentMethodInd", "C");
			BeanUtils.setProperty(rptDTO, "sectionK.cardType", "VISA Card");
			BeanUtils.setProperty(rptDTO, "sectionK.cardHolderName", "Chan Tai Man");
			BeanUtils.setProperty(rptDTO, "sectionK.cardNum", "411111AAAABm1111");
			BeanUtils.setProperty(rptDTO, "sectionK.expiryDate", "08/2015");
			BeanUtils.setProperty(rptDTO, "sectionK.cardVerifiedInd", "Y");
			
			rptDTO.getSectionG().addEyeBillPreference("NO-BILL", "View Bill on device");
			rptDTO.getSectionG().addEyeBillPreference("TV-PRINT", "TV-PRINT");
			rptDTO.getSectionG().addEyeBillPreference("TV-EMAIL", "TV-EMAIL");
			rptDTO.getSectionG().addResTelBillPreference("NO-BILL", "View Bill on device");

			rptDTO.getSectionJ().addEyeSubscribedItem("PLAN", "<span style=\"font-weight: bold;\">eye Home Smartphone </span><br /><span style=\"font-weight: bold;\">Basic Plan 1 (PE)</span><br /><span style=\"font-weight: bold;\">$110 x 27months </span><br />- Free use of eye Home Smartphone (Customer should return the phone after the service ends)<br />- Unlimited local video call minutes<br />- Unlimited free local sms<br />- Designated information services (basic) <br />- Pre-installed apps (Youtube, Facebook, RTHK on the Go, Mobile Bus Info (No LBS), DBC Radio, Chess, LunarCal, HK Railways, Unit Converter, KiLi Notes, Fruit Ninja, F-Secure, QPM-HK, Weibo, Taobao, Group Buy, Kids Learn to Read Free, Kids ABC Phonics Lite)<br />- Parallel Phone Line");
	        rptDTO.getSectionJ().addEyeSubscribedItem("BB-RENTAL", "<span style=\"font-weight: bold;\">$30 Line Rental</span>");
			
			rptDTO.getSectionJ().addEyeSubscribedItem("PE-FREE", "<span style=\"font-weight: bold;\">Parallel Phone Line Service</span><br />(Include one Free Socket Installation Fee)<span style=\"font-weight: bold;\"></span>");
			rptDTO.getSectionJ().addEyeSubscribedItem("PE-SOCKET", "<span style=\"font-weight: bold;\">Additional Socket(s) Installation ($200 per socket)</span>");
			
			rptDTO.getSectionJ().addEyeSubscribedItem("CONTENT", "<span style=\"font-weight: bold;\">Pre-school Pack 1</span>");
			rptDTO.getSectionJ().addEyeSubscribedItem("CONTENT", "<span style=\"font-weight: bold;\">Primary School Pack 1</span>");
			rptDTO.getSectionJ().addEyeSubscribedItem("CONTENT", "<span style=\"font-weight: bold;\">Life Style Lite 1</span>");
			
			rptDTO.getSectionJ().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV New Releases Channel</span>");
			rptDTO.getSectionJ().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Hits Channel</span>");
			rptDTO.getSectionJ().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Kids Channel</span>");
			rptDTO.getSectionJ().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Oldies Channel</span>");
			rptDTO.getSectionJ().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Instrumental Channel</span>");
			rptDTO.getSectionJ().addEyeSubscribedItem("MOOV", "<span style=\"font-weight: bold;\">MOOV Pack</span>");
			
			rptDTO.getSectionJ().addEyeSubscribedItem("NOWTV-FREE", "<span style=\"font-weight: bold;\">now TV Starter Pack</span><br /> &bull; Traffic Channel &bull; CCTV-4 &bull; CCTV News &bull; Creation TV &bull; Dim Sum TV &bull; Phoenix Chinese Channel &bull; Phoenix InfoNews &bull; ETTV Asia News &bull; Southern Television &bull; NHK World TV &bull; BLOOMBERG TELEVISION &bull; Movie ailer Channel &bull; AI Jazeera English &bull; Da Ai Television &bull; Australia Network &bull; France 24 &bull; Jiangsu Satellite TV &bull; GOOD TV 2 &bull; Pearl River Channel &bull; HKS &bull; Phoenix Hong Kong Channel &bull; CTI Asia Channel<span style=\"font-weight: bold;\"></span>");
			rptDTO.getSectionJ().addEyeSubscribedItem("NOWTV-FREE", "<span style=\"font-weight: bold;\">now TV Free Promotional Channels x 24 Mths</span><br /><span style=\"font-weight: bold;\">(now BNC &amp; now Direct, now News &amp; nowHK)</span><br />");
			rptDTO.getSectionJ().addEyeSubscribedItem("NOWTV-SPEC", "<span style=\"font-weight: bold;\"></span><span style=\"font-weight: bold;\">3 SD Special Pay Channel</span><br />&bull; Ch No.443 Cartoon Network<br />&bull; Ch No. 637 NOW SPORTS 1,3,7<br />&bull; Ch No. 904 Playboy TV (Adult Channel)<br />");
			rptDTO.getSectionJ().addEyeSubscribedItem("NOWTV-CELE", "<span style=\"font-weight: bold;\"></span><span style=\"font-weight: bold;\">Additional subscription to CELESTIAL MOVIES (HK$10 X 24months)</span><br />");
			BeanUtils.setProperty(rptDTO, "SectionJ.eyeViewAvInd", "Y");
			
			rptDTO.getSectionJ().addEyeSubscribedItem("NO-BILL", "View Bill on device<br />");
			
			rptDTO.getSectionJ().addEyeSubscribedItem("TV-EMAIL", "View Bill on device<br />");
			
			rptDTO.getSectionJ().addEyeSubscribedItem("EXIST-VAS", "(only applicable to Upgrade Customers)<br />With the exception of the services set out below, all your existing paid Value-added Service except the Duplex Ringing or Call Waiting II or Do Not Disturb II feature will continue under the terms of your current Contract at the current monthly charges and for the fixed term (if applicable) of the relevant Service (i.e. PCCW Residential Telephone Line Service, eye Multimedia Service and/or Samsung Video Infotainment Service) you are upgrading from.&nbsp; Your existing paid standalone Duplex Ringing or Call Waiting II or Do Not Disturb II feature will be terminated.&nbsp; Your existing paid Value-added Service Package with Duplex Ringing or Call Waiting II or Do Not Disturb II feature will be terminated and you must select another Value-added Service feature or package.<br />");

			rptDTO.getSectionJ().addEyeSubscribedItem("REPLAC-VAS", "Your existing Residential Telephone Line Service Plan includes the following free Value-added Services:-<br /><br />&bull; 12-mth TP with 11 Rebate + Fax Package<br />");
			rptDTO.getSectionJ().addEyeSubscribedItem("REPLAC-VAS", "will be replaced by the following new free Value-added Services upon upgrading to eye Home Smartphone Package:-<br /><br />&bull; Fax Package<br />");
			
			rptDTO.getSectionJ().addEyeSubscribedItem("VAS-HOT", "24-mth Voice Package for eye ($11 x 24-mth)<br />");
			rptDTO.getSectionJ().addEyeSubscribedItem("IDD", "IDD 0060 & 001 Service Registration<br />");
			rptDTO.getSectionJ().addEyeSubscribedItem("0060E", "0060 everywhere (3 Months Free Trial Plan)<br />");
			rptDTO.getSectionJ().addEyeSubscribedItem("PREM-PAY", "SONY 46\" KDL-46EX500<br />");
			
			
			//rptDTO.getSectionInternalUse().addOptOutDtl("aaaaa111111");
			//rptDTO.getSectionInternalUse().addOptOutDtl("aaaaa2222222");
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void fillReportDataEyeAllItem(ReportDTO pReportDTO, Map<String, Object> pRptParamMap) {
		EyeAppFormRptDTO rptDTO = (EyeAppFormRptDTO) pReportDTO;
		String language = (String) pRptParamMap.get(ReportDataService.PARAM_RPT_LANGUAGE);

		try {
			BeanUtils.setProperty(rptDTO, "applicationNumber", "TTW1231235");
			BeanUtils.setProperty(rptDTO, "eyeApplication", "TRUE");
			BeanUtils.setProperty(rptDTO, "resTelApplication", "FALSE");

			BeanUtils.setProperty(rptDTO, "sectionA.customer.idVerifiedInd", "Y");
			BeanUtils.setProperty(rptDTO, "sectionA.customer.idDocType", "HKID");
			BeanUtils.setProperty(rptDTO, "sectionA.iaType", "Same as existing address");
			
			BeanUtils.setProperty(rptDTO, "sectionA.address.unitNo", "11");
			BeanUtils.setProperty(rptDTO, "sectionA.address.floorNo", "14");
			BeanUtils.setProperty(rptDTO, "sectionA.address.blockNo", "Heng Hoi");
			BeanUtils.setProperty(rptDTO, "sectionA.address.buildNo", "Heng On Estate");
			BeanUtils.setProperty(rptDTO, "sectionA.address.strDesc", "ABC Street");
			BeanUtils.setProperty(rptDTO, "sectionA.address.sectDesc", "Ma On Shan");
			BeanUtils.setProperty(rptDTO, "sectionA.address.distDesc", "NT");
			BeanUtils.setProperty(rptDTO, "sectionA.address.areaDesc", "HK");
			BeanUtils.setProperty(rptDTO, "sectionA.baType", "Same as installation address");
			
			WItemRptTemplateVDAO criteria = SpringApplicationContext.getBean(WItemRptTemplateVDAO.class);
			criteria.setSearchKey("itemType", "PLAN");
			criteria.setSearchKey("displayType", "ITEM_DESC");
			criteria.setSearchKey("lob", "LTS");
			criteria.setSearchKey("locale", language);
			WItemRptTemplateVDAO[] daos = (WItemRptTemplateVDAO[]) criteria.doSelect(null, null);
			StringBuilder sbContents = null;
			
			for (WItemRptTemplateVDAO dao : daos) {
				sbContents = new StringBuilder(ReportUtil.defaultString(dao.getHtml()));
				if ("Y".equals(pRptParamMap.get("EDIT_BUTTON"))) {
					sbContents.append(SpringApplicationContext.getBean(ReportService.class).generateEditButtonHtml(dao));
					dao.setHtml(sbContents.toString());
				}
				rptDTO.getSectionC().addEyePlan(dao.getHtml(), dao.getFixTermRate(), dao.getMthToMthRate());
			}
			
			BeanUtils.setProperty(rptDTO, "sectionD.eyeViewAvInd", "Y");
			
			criteria = SpringApplicationContext.getBean(WItemRptTemplateVDAO.class);
			criteria.setSearchKey("displayType", "ITEM_DESC");
			criteria.setSearchKey("lob", "LTS");
			criteria.setSearchKey("locale", language);
			daos = (WItemRptTemplateVDAO[]) criteria.doSelect(null, null);
			
			for (WItemRptTemplateVDAO dao : daos) {
				sbContents = new StringBuilder(ReportUtil.defaultString(dao.getHtml()));
				if ("Y".equals(pRptParamMap.get("EDIT_BUTTON"))) {
					sbContents.append(SpringApplicationContext.getBean(ReportService.class).generateEditButtonHtml(dao));
					dao.setHtml(sbContents.toString());
				}
				rptDTO.getSectionD().addEyeSubscribedItem(dao.getItemType(), dao.getHtml(), dao.getFixTermRate(), dao.getMthToMthRate());
				rptDTO.getSectionG().addEyeBillPreference(dao.getItemType(), dao.getHtml());
			}
			
			criteria = SpringApplicationContext.getBean(WItemRptTemplateVDAO.class);
			criteria.setSearchKey("itemType", "PREMIUM");
			criteria.setSearchKey("displayType", "ITEM_DESC");
			criteria.setSearchKey("lob", "LTS");
			criteria.setSearchKey("locale", language);
			daos = (WItemRptTemplateVDAO[]) criteria.doSelect(null, null);
			
			for (WItemRptTemplateVDAO dao : daos) {
				sbContents = new StringBuilder(ReportUtil.defaultString(dao.getHtml()));
				if ("Y".equals(pRptParamMap.get("EDIT_BUTTON"))) {
					sbContents.append(SpringApplicationContext.getBean(ReportService.class).generateEditButtonHtml(dao));
					dao.setHtml(sbContents.toString());
				}
				rptDTO.getSectionE().addEyePremiumItem(dao.getHtml(), dao.getPenaltyAmt());
			}

			criteria = SpringApplicationContext.getBean(WItemRptTemplateVDAO.class);
			criteria.setSearchKey("itemType", "PLAN");
			criteria.setSearchKey("lob", "LTS");
			criteria.setSearchKey("locale", language);
			criteria.setSearchKeyIn("displayType", new String[] {"ITEM_RMK", "ITEM_RMK_TITLE"});
			daos = (WItemRptTemplateVDAO[]) criteria.doSelect(null, null, null, "ITEM_ID ASC, DISPLAY_TYPE DESC");
			
			String itemId = null;
			for (WItemRptTemplateVDAO dao : daos) {
				if (!StringUtils.equals(dao.getItemId(), itemId)) {
					if (StringUtils.isNotBlank(itemId)) {
						rptDTO.getSectionJ().addEyeSubscribedItem("PLAN", sbContents.toString());
					}
					itemId = dao.getItemId();
					sbContents = new StringBuilder();
				}
				
				if (StringUtils.equals(dao.getItemId(), itemId)) {
					if (sbContents.length() > 0) {
						sbContents.append("<br/>");
					}
				}

				sbContents.append(dao.getHtml());
				if ("Y".equals(pRptParamMap.get("EDIT_BUTTON"))) {
					sbContents.append(SpringApplicationContext.getBean(ReportService.class).generateEditButtonHtml(dao));
				}
			}
			if (StringUtils.isNotBlank(itemId)) {
				rptDTO.getSectionJ().addEyeSubscribedItem("PLAN", sbContents.toString());
			}
			
			criteria = SpringApplicationContext.getBean(WItemRptTemplateVDAO.class);
			criteria.setSearchKey("displayType", "ITEM_RMK");
			criteria.setSearchKey("lob", "LTS");
			criteria.setSearchKey("locale", language);
			daos = (WItemRptTemplateVDAO[]) criteria.doSelect(null, null, "ITEM_TYPE != 'PLAN'");
			
			for (WItemRptTemplateVDAO dao : daos) {
				sbContents = new StringBuilder(ReportUtil.defaultString(dao.getHtml()));
				if ("Y".equals(pRptParamMap.get("EDIT_BUTTON"))) {
					sbContents.append(SpringApplicationContext.getBean(ReportService.class).generateEditButtonHtml(dao));
					dao.setHtml(sbContents.toString());
				}
				rptDTO.getSectionJ().addEyeSubscribedItem(dao.getItemType(), dao.getHtml());
			}
			
			//BeanUtils.setProperty(rptDTO, "sectionK.existPaymentMethodInd", "C");
			BeanUtils.setProperty(rptDTO, "sectionK.existPaymentStatement", "Statment here");
			
			BeanUtils.setProperty(rptDTO, "sectionK.paymentMethodInd", "C");
			BeanUtils.setProperty(rptDTO, "sectionK.cardType", "VISA Card");
			BeanUtils.setProperty(rptDTO, "sectionK.cardHolderName", "Chan Tai Man");
			BeanUtils.setProperty(rptDTO, "sectionK.cardNum", "411111AAAABm1111");
			BeanUtils.setProperty(rptDTO, "sectionK.expiryDate", "08/2015");
			BeanUtils.setProperty(rptDTO, "sectionK.cardVerifiedInd", "Y");

			criteria = SpringApplicationContext.getBean(WItemRptTemplateVDAO.class);
			criteria.setSearchKey("itemType", "PREPAY");
			criteria.setSearchKey("displayType", "ITEM_RMK");
			criteria.setSearchKey("lob", "LTS");
			criteria.setSearchKey("locale", language);
			daos = (WItemRptTemplateVDAO[]) criteria.doSelect(null, null);
			
			for (WItemRptTemplateVDAO dao : daos) {
				sbContents = new StringBuilder(ReportUtil.defaultString(dao.getHtml()));
				if ("Y".equals(pRptParamMap.get("EDIT_BUTTON"))) {
					sbContents.append(SpringApplicationContext.getBean(ReportService.class).generateEditButtonHtml(dao));
					dao.setHtml(sbContents.toString());
				}
				rptDTO.getSectionK().addPrepayment(dao.getHtml());
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getFullStackTrace(e));
		}
	}
	
	public void fillReportDataWq(ReportDTO pReportDTO, Map<String, Object> pRptParamMap) {
		
	}
}