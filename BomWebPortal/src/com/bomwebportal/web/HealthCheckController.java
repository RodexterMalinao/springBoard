package com.bomwebportal.web;

import java.util.Map;

import javax.servlet.http.HttpSession;

//import javax.ws.rs.WebApplicationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bomwebportal.csportal.object.CsldInqArq;
import com.bomwebportal.csportal.service.CsPortalService;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.wsclient.CCUnifiedInterfaceClient;
import com.bomwebportal.wsclient.CnmClient;
import com.bomwebportal.wsclient.MvnoWSClient;
import com.bomwebportal.wsclient.exception.WsClientException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tng.api.dto.VerifyStudentPlanCardResponse;
import com.tng.api.service.PaymentServiceGateway;

import bom.mob.schema.javabean.si.xsd.CheckNoChannelDTO;
import bom.mob.schema.javabean.si.xsd.CslNoStatusDTO;
import bom.mob.schema.javabean.si.xsd.GetCurrentDNODTO;


@Controller
public class HealthCheckController {

	@Autowired
	private OrderService orderService;
	

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@Autowired
	private CsPortalService csPortalService;

	public CsPortalService getCsPortalService() {
		return csPortalService;
	}

	public void setCsPortalService(CsPortalService csPortalService) {
		this.csPortalService = csPortalService;
	}
	
	@Autowired
	private MvnoWSClient mvnoWSClient;
	
	@Autowired
	private CnmClient cnmClient;
	
	private PaymentServiceGateway tngPsgService;
	
	public PaymentServiceGateway getTngPsgService() {
		return tngPsgService;
	}


	public void setTngPsgService(PaymentServiceGateway tngPsgService) {
		this.tngPsgService = tngPsgService;
	}
	
	private CCUnifiedInterfaceClient ccUnifiedInterfaceClient;

	public CCUnifiedInterfaceClient getCcUnifiedInterfaceClient() {
		return ccUnifiedInterfaceClient;
	}

	public void setCcUnifiedInterfaceClient(CCUnifiedInterfaceClient ccUnifiedInterfaceClient) {
		this.ccUnifiedInterfaceClient = ccUnifiedInterfaceClient;
	}

	@RequestMapping(value = "/mnp/healthchecksbdb", method = RequestMethod.GET)
	public String getsbdbResult(String tel, Model model) {
		try {
			String result = orderService.getOrderIdUsingSameMRT(tel, "RTTWMXXX");
			model.addAttribute("result", "Pass");
			model.addAttribute("input", result);
			return "ajax_service";
			
		} catch (Exception e) {
			model.addAttribute("result", "Fail");
			model.addAttribute("error", e.toString());
			return "ajax_service";
		}
	}
	
	@RequestMapping(value = "/mnp/healthcheckcsportal", method = RequestMethod.GET)
	public String getcsPortalResult(String id, Model model) {
		try {
			CsldInqArq reqObj=csPortalService.idCheck("HKID", id, "", "");
			String cspResult = toString(reqObj);
			model.addAttribute("result", "Pass");
			model.addAttribute("input",cspResult);
			return "ajax_service";
			
		} catch (Exception e) {
			model.addAttribute("result", "Fail");
			model.addAttribute("error", e.toString());
			return "ajax_service";
		}
	}
	
	@RequestMapping(value = "/mnp/getcurrdno", method = RequestMethod.GET)
	public String getCurrDNO(String msisdn,
			Model model, HttpSession session) throws WsClientException {
		
		System.out.println("getCurrDNO");
		
		String result = null;
		
		try {
			GetCurrentDNODTO currentDNODTO =mvnoWSClient.getCurrDNO(msisdn);
			
			result = "Dno:"+currentDNODTO.getDno()+"/ActDNO:"+currentDNODTO.getActDNO();
			model.addAttribute("result", "Pass");
			model.addAttribute("input", result);
		} catch (Exception e){
			result = e.toString();
			model.addAttribute("result", "Fail");
			model.addAttribute("error", e.toString());
		}

	    return "ajax_service";
	}
	
	@RequestMapping(value = "/mnp/checkcentralnumpoolmsisdn", method = RequestMethod.GET)
	public String checkCentralNumPoolMsisdn(String msisdn,
			Model model, HttpSession session) throws WsClientException {
		
		System.out.println("checkCentralNumPoolMsisdnMIP");
		
		String result = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			MnpDTO mnpDTO =cnmClient.checkCentralNumPoolMsisdnMIP(msisdn);
			
			result = gson.toJson(mnpDTO);
			model.addAttribute("result", "Pass");
			model.addAttribute("input", result);
		} catch (Exception e){
			result = e.toString();
			model.addAttribute("result", "Fail");
			model.addAttribute("error", e.toString());
		}

	    return "ajax_service";
	}
	
	@RequestMapping(value = "/mnp/checknochannel", method = RequestMethod.GET)
	public String checkNoChannel(String msisdn,
			Model model, HttpSession session) throws WsClientException {
		
		System.out.println("checkNoChannel");
		
		String result = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			CheckNoChannelDTO checkNoChannelDTO =mvnoWSClient.checkNoChannel(msisdn);
			
			result = gson.toJson(checkNoChannelDTO);
			model.addAttribute("result", "Pass");
			model.addAttribute("input", result);
		} catch (Exception e){
			result = e.toString();
			model.addAttribute("result", "Fail");
			model.addAttribute("error", e.toString());
		}

	    return "ajax_service";
	}
	
	@RequestMapping(value = "/mnp/checknostatusincinv", method = RequestMethod.GET)
	public String checkNoStatusInCInv(String msisdn,
			Model model, HttpSession session) throws WsClientException {
		
		System.out.println("checkNoStatusInCInv");
		
		String result = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			CslNoStatusDTO cslNoStatusDTO =mvnoWSClient.checkNoStatusInCInv(msisdn);
			
			result = gson.toJson(cslNoStatusDTO);
			model.addAttribute("result", "Pass");
			model.addAttribute("input", result);
		} catch (Exception e){
			result = e.toString();
			model.addAttribute("result", "Fail");
			model.addAttribute("error", e.toString());
		}

	    return "ajax_service";
	}
	
	public String toString (CsldInqArq object){
		String result = "{\"apiTy\":\"" +object.apiTy + "\""
				+ ",\"reply\":\"" +object.reply +"\""
				+ ",\"clnVer\":\"" +object.clnVer +"\""
				+ ",\"sysId\":\"" +object.sysId +"\""
				+ ",\"sysPwd\":\"" +object.sysPwd +"\""
				+ ",\"userId\":\"" +object.userId +"\""
				+ ",\"psnTy\":\"" +object.psnTy+"\""
				+ ",\"iDocTy\":\"" +object.iDocTy +"\""
				+ ",\"iDocNum\":\"" +object.iDocNum +"\""
				+ ",\"iLi4MyHkt\":\"" +object.iLi4MyHkt +"\""
				+ ",\"iLi4Club\":\"" +object.iLi4Club+"\""
				+ ",\"oIdStatus\":\"" +object.oIdStatus +"\""
				+ ",\"oCorrMyHktLi\":\"" +object.oCorrMyHktLi +"\""
				+ ",\"oCorrClubLi\":\"" +object.oCorrClubLi+"\""
				+ ",\"oCorrClubMbrId\":\"" +object.oCorrClubMbrId+"\""
				+ ",\"oMyHktLiStatus\":\"" +object.oMyHktLiStatus+"\""
				+ ",\"oClubLiStatus\":\"" +object.oClubLiStatus +"\"}";
		return result;
	}
	
	
	@RequestMapping(value = "/studentplan/verifystudentplancard", method = RequestMethod.GET)
	public String verifyStudentPlanCard(String idDocNum, String idDocType, String fullPan,
			Model model) {
		
		System.out.println("verifyStudentPlanCard");
		String maskedPan = fullPan.substring(0, 6) + "******" + fullPan.substring(12, 16);
		System.out.println(maskedPan);
		
		String result = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			VerifyStudentPlanCardResponse verifyStudentPlanCardResponse =	tngPsgService.verifyStudentPlanCard(idDocNum, idDocType, maskedPan, PaymentServiceGateway.OPER_INFO);
			
			result = gson.toJson(verifyStudentPlanCardResponse);
			model.addAttribute("result", "Pass");
			model.addAttribute("input", result);
		} catch (Exception e){
			result = e.toString();
			model.addAttribute("result", "Fail");
			model.addAttribute("error", e.toString());
		}

	    return "ajax_service";
	}
	
	@RequestMapping(value = "/mnp/getCardInformation", method = RequestMethod.GET)
	public String getMsisdnInformation(String msisdn ,Model model) {
		
		System.out.println("getMnPMsisdnInformation");
		
		String result = null;
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		try {
			Map<String, String> getCardInformationResult  =	ccUnifiedInterfaceClient.getCardInformation(msisdn);
			
			result = gson.toJson(getCardInformationResult);
			System.out.println(result);
			model.addAttribute("result", "Pass");
			model.addAttribute("input", result);
		} catch (Exception e){
			result = e.toString();
			model.addAttribute("result", "Fail");
			model.addAttribute("error", e.toString());
		}

	    return "ajax_service";
	}
}
