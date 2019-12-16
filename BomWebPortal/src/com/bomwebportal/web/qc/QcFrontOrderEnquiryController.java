package com.bomwebportal.web.qc;

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
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ui.DsQCImsOrderEnquiryUI;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.google.gson.Gson;
import com.bomwebportal.service.qc.ImsDSQCService;


public class QcFrontOrderEnquiryController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private Gson gson = new Gson();

	private ImsDSQCService imsDSQCService;
	private ImsOrderAmendService imsOrderAmendservice;
	
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

	public String getRetentionRecallUrl() {
		return retentionRecallUrl;
	}

	public void setRetentionRecallUrl(String retentionRecallUrl) {
		this.retentionRecallUrl = retentionRecallUrl;
	}

	public String getRetentionViewUrl() {
		return retentionViewUrl;
	}

	public void setRetentionViewUrl(String retentionViewUrl) {
		this.retentionViewUrl = retentionViewUrl;
	}

	public String getTerminationRecallUrl() {
		return terminationRecallUrl;
	}

	public void setTerminationRecallUrl(String terminationRecallUrl) {
		this.terminationRecallUrl = terminationRecallUrl;
	}

	public String getTerminationViewUrl() {
		return terminationViewUrl;
	}

	public void setTerminationViewUrl(String terminationViewUrl) {
		this.terminationViewUrl = terminationViewUrl;
	}

	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}

	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}
	
	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}

	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {	
		
		logger.info("QcFrontOrderEnquiryController formBackingObject is called");
		
		logger.info("retentionRecallUrl:"+retentionRecallUrl);
		logger.info("retentionViewUrl:"+retentionViewUrl);
		logger.info("terminationRecallUrl:"+terminationRecallUrl);
		logger.info("terminationViewUrl:"+terminationViewUrl);
		logger.info("ntvDomain:"+ntvDomain);
		request.getSession().setAttribute("retentionRecallUrl", retentionRecallUrl);
		request.getSession().setAttribute("retentionViewUrl", retentionViewUrl);
		request.getSession().setAttribute("terminationRecallUrl", terminationRecallUrl);
		request.getSession().setAttribute("terminationViewUrl", terminationViewUrl);
		request.getSession().setAttribute("ntvDomain", ntvDomain);
		
		DsQCImsOrderEnquiryUI enquiry = (DsQCImsOrderEnquiryUI)request.getSession().getAttribute("qcfrontorderenquiry");

		if(enquiry==null || "Y".equals(enquiry.getReset())){
			enquiry = new DsQCImsOrderEnquiryUI();	
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
		
		
			Map<String, String> teamCds = new LinkedHashMap<String, String>();
			List<String> list =null;
		
			list = imsDSQCService.getTeamCodeList(bomSalesUserDTO.getChannelId());
			for(String item:list)
			{
				if ( item != null && !"".equals(item))
					teamCds.put(item, item);
			}
			request.getSession().setAttribute("teamCds", teamCds);
		
		List<ImsAlertMsgDTO> imsOrderList =imsDSQCService.getQcFrontOrderEnquiryInfo(enquiry, bomSalesUserDTO);

		List<String> roleCodeList = imsDSQCService.getRoleCodeByUserIDLkupCode(bomSalesUserDTO.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_WRITE, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION);
		List<String> channelCodeList = imsDSQCService.getChannelCodeListByChannelID(bomSalesUserDTO.getChannelId());
		List<String> teamCodeList = imsOrderAmendservice.getTeamCodeListOfCentreCd(bomSalesUserDTO.getUsername());

		for(ImsAlertMsgDTO temp:imsOrderList){
			temp.checkRecallAmend(roleCodeList,	bomSalesUserDTO, channelCodeList, teamCodeList);
		}
		request.getSession().setAttribute("imsOrderList",imsOrderList);
		
		List <String> orderStatusList = new ArrayList<String>();
		orderStatusList=imsDSQCService.getOrderStatusList();
		request.getSession().setAttribute("orderStatusList", orderStatusList);
		
		return enquiry;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
		logger.info("QcOrderEnquiryController onSubmit is called");
		DsQCImsOrderEnquiryUI enquiry = (DsQCImsOrderEnquiryUI)command;
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		if(enquiry.getOrderId()!=null){
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
		if(enquiry.getSerNum()!=null){		
			enquiry.setSerNum(enquiry.getSerNum());
		}
		if(enquiry.getTeamSearch()!=null){
			enquiry.setTeamSearch(enquiry.getTeamSearch());
		}
		if(enquiry.getSalesNum()!=null){
			enquiry.setSalesNum(enquiry.getSalesNum().toUpperCase());
		}	
		if(enquiry.getCreateStaff()!=null){
			enquiry.setCreateStaff(enquiry.getCreateStaff().toUpperCase());
		}
		
		logger.info("enquiry: " + gson.toJson(enquiry));

		if(!"Y".equalsIgnoreCase(enquiry.getReset())){
			
			List<ImsAlertMsgDTO> imsOrderList =imsDSQCService.getQcFrontOrderEnquiryInfo(enquiry, bomSalesUserDTO);
			
			List<String> roleCodeList = imsDSQCService.getRoleCodeByUserIDLkupCode(bomSalesUserDTO.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_WRITE, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION);
			List<String> channelCodeList = imsDSQCService.getChannelCodeListByChannelID(bomSalesUserDTO.getChannelId());
			List<String> teamCodeList = imsOrderAmendservice.getTeamCodeListOfCentreCd(bomSalesUserDTO.getUsername());
				
			for(ImsAlertMsgDTO temp:imsOrderList){
				temp.checkRecallAmend(roleCodeList,	bomSalesUserDTO, channelCodeList,teamCodeList);
			}
		
			request.getSession().setAttribute("imsOrderList",imsOrderList);
		}else{
			request.getSession().setAttribute("imsOrderList",null);
		}
		
		request.getSession().setAttribute("qcfrontorderenquiry", enquiry);
		return new ModelAndView(new RedirectView("qcfrontorderenquiry.html"));
	}
	

	
}
