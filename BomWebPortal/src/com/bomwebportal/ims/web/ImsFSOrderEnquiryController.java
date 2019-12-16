package com.bomwebportal.ims.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ui.CcLtsImsOrderEnquiryUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.CCSalesInfoService;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.google.gson.Gson;


public class ImsFSOrderEnquiryController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();

	private ImsOrderAmendService imsOrderAmendservice;
	private CCSalesInfoService ccsiService;
	private String retentionRecallUrl;
	private String retentionViewUrl;
	private String terminationRecallUrl;
	private String terminationViewUrl;
	private String ntvDomain;
	
	public String getNtvDomain() {
		return ntvDomain;
	}

	public void setNtvDomain(String ntvDomain) {
		this.ntvDomain = ntvDomain;
	}
	
	public void setRetentionViewUrl(String retentionViewUrl) {
		this.retentionViewUrl = retentionViewUrl;
	}

	public String getRetentionViewUrl() {
		return retentionViewUrl;
	}

	public void setRetentionRecallUrl(String retentionRecallUrl) {
		this.retentionRecallUrl = retentionRecallUrl;
	}

	public String getRetentionRecallUrl() {
		return retentionRecallUrl;
	}

	public Object formBackingObject(HttpServletRequest request)
	throws ServletException {	
		logger.info("ImsFSOrderEnquiryController formBackingObject is called");
		logger.info("retentionRecallUrl:"+retentionRecallUrl);
		logger.info("retentionViewUrl:"+retentionViewUrl);
		logger.info("terminationRecallUrl:"+terminationRecallUrl);
		logger.info("terminationViewUrl:"+terminationViewUrl);
		request.getSession().setAttribute("retentionRecallUrl", retentionRecallUrl);
		request.getSession().setAttribute("retentionViewUrl", retentionViewUrl);
		request.getSession().setAttribute("terminationRecallUrl", terminationRecallUrl);
		request.getSession().setAttribute("terminationViewUrl", terminationViewUrl);
		request.getSession().setAttribute("ntvDomain", ntvDomain);
		CcLtsImsOrderEnquiryUI enquiry = (CcLtsImsOrderEnquiryUI)request.getSession().getAttribute("imsfsorderenquiry");
		if(enquiry==null || "Y".equals(enquiry.getReset())){
			enquiry = new CcLtsImsOrderEnquiryUI();	
			enquiry.setLob("IMS");
			enquiry.setDateType("A");
			enquiry.setDocType("HKID");
			SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date current = new Date();
			enquiry.setEndDate(sdFormat.format(current));
			Date sevenDaysBefore = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(sevenDaysBefore); 
			c.add(Calendar.DATE, -7);
			sevenDaysBefore = c.getTime();
			enquiry.setStartDate(sdFormat.format(sevenDaysBefore));
		}
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		List <String> orderStatusList =imsOrderAmendservice.getOrderStatusList();
		List <String> endingStatusList =imsOrderAmendservice.getSBEndingStatus();
		request.getSession().setAttribute("orderStatusList", orderStatusList);
		request.getSession().setAttribute("endingStatusList", endingStatusList);
		
		return enquiry;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
		logger.info("ImsFSOrderEnquiryController onSubmit is called");
		CcLtsImsOrderEnquiryUI enquiry = (CcLtsImsOrderEnquiryUI)command;
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		if(enquiry.getOrderId()!=null){// for mantis 5790, upper case search
			enquiry.setOrderId(enquiry.getOrderId().toUpperCase());
		}
		logger.info("enquiry: " + gson.toJson(enquiry));

		if(!"Y".equalsIgnoreCase(enquiry.getReset())){
			List<ImsAlertMsgDTO> imsOrderList =imsOrderAmendservice.getLtsImsOrderEnquiryListInfo(enquiry, bomSalesUserDTO);
			List<String> roleCodeList = imsOrderAmendservice.getRoleCodeByUserIDLkupCode(bomSalesUserDTO.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_WRITE, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION);
			List<String> channelCodeList = imsOrderAmendservice.getChannelCodeListByChannelID(bomSalesUserDTO.getChannelId());
			for(ImsAlertMsgDTO temp:imsOrderList){
				temp.checkRecallAmend(roleCodeList,	bomSalesUserDTO, channelCodeList);
			}
			request.getSession().setAttribute("imsOrderList",imsOrderList);
		}else{
			request.getSession().setAttribute("imsOrderList",null);
		}
		request.getSession().setAttribute("imsfsorderenquiry", enquiry);
		return new ModelAndView(new RedirectView("imsfsorderenquiry.html"));
	}

	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}

	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}
	
	public CCSalesInfoService getCcsiService() {
		return ccsiService;
	}

	public void setCcsiService(CCSalesInfoService ccsiService) {
		this.ccsiService = ccsiService;
	}

	public void setTerminationRecallUrl(String terminationRecallUrl) {
		this.terminationRecallUrl = terminationRecallUrl;
	}

	public String getTerminationRecallUrl() {
		return terminationRecallUrl;
	}

	public void setTerminationViewUrl(String terminationViewUrl) {
		this.terminationViewUrl = terminationViewUrl;
	}

	public String getTerminationViewUrl() {
		return terminationViewUrl;
	}
}
