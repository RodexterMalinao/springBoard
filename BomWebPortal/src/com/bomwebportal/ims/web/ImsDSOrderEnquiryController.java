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
import com.bomwebportal.service.OrdEmailReqService;
import com.google.gson.Gson;


public class ImsDSOrderEnquiryController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();
	private String ntvDomain;
	private ImsOrderAmendService imsOrderAmendservice;
//	private CCSalesInfoService ccsiService;
	private String retentionRecallUrl;
	private String retentionViewUrl;
	private String terminationRecallUrl;
	private String terminationViewUrl;
	private OrdEmailReqService ordEmailReqService;
	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
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
		logger.info("ImsDSOrderEnquiry formBackingObject is called");
		logger.info("retentionRecallUrl:"+retentionRecallUrl);
		logger.info("retentionViewUrl:"+retentionViewUrl);
		logger.info("terminationRecallUrl:"+terminationRecallUrl);
		logger.info("terminationViewUrl:"+terminationViewUrl);
		request.getSession().setAttribute("retentionRecallUrl", retentionRecallUrl);
		request.getSession().setAttribute("retentionViewUrl", retentionViewUrl);
		request.getSession().setAttribute("terminationRecallUrl", terminationRecallUrl);
		request.getSession().setAttribute("terminationViewUrl", terminationViewUrl);
		CcLtsImsOrderEnquiryUI enquiry = (CcLtsImsOrderEnquiryUI)request.getSession().getAttribute("imsdsorderenquiry");
		//if(enquiry==null || "Y".equals(enquiry.getReset())){
		if(enquiry==null || "Y".equals(enquiry.getReset())){
			enquiry = new CcLtsImsOrderEnquiryUI();	
			enquiry.setLob("IMS");
			enquiry.setDateType("A");
			enquiry.setDocType("HKID");
			//SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");
			//Date current = new Date();
			//enquiry.setEndDate(sdFormat.format(current));
			//Date sevenDaysBefore = new Date();
			//Calendar c = Calendar.getInstance(); 
			//c.setTime(sevenDaysBefore); 
			//c.add(Calendar.DATE, -7);
			//sevenDaysBefore = c.getTime();
			//enquiry.setStartDate(sdFormat.format(sevenDaysBefore));
		}
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

//		if(imsOrderAmendservice.checkIfSalesManager(bomSalesUserDTO.getUsername())){
//			request.getSession().setAttribute("IsSalesManager", "Y");
//			
//			Map<String, String> teamCds = new LinkedHashMap<String, String>();
//			List<String> list =null;
//			try {
//				list = ccsiService.getCCManagerTeamCds(bomSalesUserDTO.getUsername());
//			} catch (DAOException e) {
//				e.printStackTrace();
//			}
//			for(String item:list)
//			{
//				if ( item != null && !"".equals(item))
//					teamCds.put(item, item);
//			}
//			request.getSession().setAttribute("teamCds", teamCds);
//			
//		}else{
//			request.getSession().setAttribute("IsSalesManager", "N");
//		}
		Map<String, String> teamCds = new LinkedHashMap<String, String>();
		List<String> list = ordEmailReqService.getTeamCdsByRole(bomSalesUserDTO);
		if ( list == null || list.size() == 0)
			if (bomSalesUserDTO.getShopCd() != null)
			teamCds.put(bomSalesUserDTO.getShopCd(), bomSalesUserDTO.getShopCd());
			else
			teamCds.put("","");
		else
		{
			for(String item:list)
			{
				if ( item != null && !"".equals(item))
					teamCds.put(item, item);
			}
		}
		request.getSession().setAttribute("teamCds", teamCds);

		request.setAttribute("ntvdomain", this.getNtvDomain());
		List <String> orderStatusList = new ArrayList<String>();
		orderStatusList=imsOrderAmendservice.getOrderStatusList();
		request.getSession().setAttribute("orderStatusList", orderStatusList);
		List <String> endingStatusList =imsOrderAmendservice.getSBEndingStatus();
		request.getSession().setAttribute("endingStatusList", endingStatusList);
		
		return enquiry;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
		logger.info("CcLtsImsOrderEnquiry onSubmit is called");
		CcLtsImsOrderEnquiryUI enquiry = (CcLtsImsOrderEnquiryUI)command;
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		if(enquiry.getOrderId()!=null){// for mantis 5790, upper case search
			enquiry.setOrderId(enquiry.getOrderId().toUpperCase());
		}
		if(enquiry.getDateType()!=null){
			enquiry.setDateType(enquiry.getDateType().toUpperCase());
		}
		if(enquiry.getStartDate()!=null){
			enquiry.setStartDate(enquiry.getStartDate().toUpperCase());
		}
		if(enquiry.getEndDate()!=null){
			enquiry.setEndDate(enquiry.getEndDate().toUpperCase());
		}		
		if(enquiry.getDocNum()!=null){
			enquiry.setDocType(enquiry.getDocType().toUpperCase());
			enquiry.setDocNum(enquiry.getDocNum());
		}
		if(enquiry.getSerNum()!=null){		
			enquiry.setSerNum(enquiry.getSerNum());
		}	
		if(enquiry.getLoginId()!=null){
			enquiry.setLoginId(enquiry.getLoginId());
		}
		if(enquiry.getOrderStatus()!=null){
			enquiry.setOrderStatus(enquiry.getOrderStatus());
		}
		if(enquiry.getOrderType()!=null){
			enquiry.setOrderType(enquiry.getOrderType());
		}
		if(enquiry.getTeamSearch()!=null){
//			logger.info("team code:  "+ enquiry.getTeamSearch());
			enquiry.setTeamSearch(enquiry.getTeamSearch());
		}
		if(enquiry.getSalesNum()!=null){
			enquiry.setSalesNum(enquiry.getSalesNum().toUpperCase());
		}	
		if(enquiry.getCreateStaff()!=null){
			enquiry.setCreateStaff(enquiry.getCreateStaff().toUpperCase());
		}		
	
		logger.info("enquiry: " + gson.toJson(enquiry));
//		if(!"Y".equalsIgnoreCase(enquiry.getReset())&&!(enquiry.getLob().equalsIgnoreCase("LTS"))){
		if(!"Y".equalsIgnoreCase(enquiry.getReset())){
//			List<ImsAlertMsgDTO> imsOrderList =imsOrderAmendservice.getLtsImsOrderEnquiryListInfo(enquiry, bomSalesUserDTO);
			List<ImsAlertMsgDTO> imsOrderList =imsOrderAmendservice.getImsDSOrderEnquiryListInfo(enquiry, bomSalesUserDTO);
			logger.info("imsOrderList: "+gson.toJson(imsOrderList));
	
			List<String> roleCodeList = imsOrderAmendservice.getRoleCodeByUserIDLkupCode(bomSalesUserDTO.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_WRITE, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION);
			List<String> channelCodeList = imsOrderAmendservice.getChannelCodeListByChannelID(bomSalesUserDTO.getChannelId());
			List<String> teamCodeList = imsOrderAmendservice.getTeamCodeListOfCentreCd(bomSalesUserDTO.getUsername());
			
			for(ImsAlertMsgDTO temp:imsOrderList){
				temp.checkRecallAmend(roleCodeList,	bomSalesUserDTO, channelCodeList,teamCodeList);
			}
			request.getSession().setAttribute("imsOrderList",imsOrderList);
		}else{
			request.getSession().setAttribute("imsOrderList",null);
		}
		request.getSession().setAttribute("imsdsorderenquiry", enquiry);
		return new ModelAndView(new RedirectView("imsdsorderenquiry.html"));
	}

	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}

	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}
	
//	public CCSalesInfoService getCcsiService() {
//		return ccsiService;
//	}
//
//	public void setCcsiService(CCSalesInfoService ccsiService) {
//		this.ccsiService = ccsiService;
//	}

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

	public void setNtvDomain(String ntvDomain) {
		this.ntvDomain = ntvDomain;
	}

	public String getNtvDomain() {
		return ntvDomain;
	}
}
