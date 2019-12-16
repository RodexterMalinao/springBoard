package com.bomwebportal.web.qc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
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
import com.bomwebportal.ims.service.CCSalesInfoService;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.lts.service.qc.LtsDsQcService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.qc.ImsDSQCService;


public class DsQCImsOrderEnquiryController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());

	//private ImsOrderAmendService imsOrderAmendservice;
	private String ntvDomain;
	
	public String getNtvDomain() {
		return ntvDomain;
	}

	public void setNtvDomain(String ntvDomain) {
		this.ntvDomain = ntvDomain;
	}

	private ImsOrderAmendService imsOrderAmendservice;
	private ImsDSQCService imsDSQCService;
	private LtsDsQcService ltsDsQcService;
	
	public LtsDsQcService getLtsDsQcService() {
		return ltsDsQcService;
	}

	public void setLtsDsQcService(LtsDsQcService ltsDsQcService) {
		this.ltsDsQcService = ltsDsQcService;
	}
	
	
	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}

	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}

	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}

	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}

	private CCSalesInfoService ccsiService;
	
	public Object formBackingObject(HttpServletRequest request)
	throws ServletException {	
		logger.info("DsQCImsOrderEnquiry formBackingObject is called");
		DsQCImsOrderEnquiryUI enquiry = (DsQCImsOrderEnquiryUI)request.getSession().getAttribute("dsqcimsorderenquiry");
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

		if(imsDSQCService.checkIfSalesManager(bomSalesUserDTO.getUsername())){
			request.getSession().setAttribute("IsSalesManager", "Y");
			
			Map<String, String> teamCds = new LinkedHashMap<String, String>();
			List<String> list =null;
			//list = ccsiService.getCCManagerTeamCds(bomSalesUserDTO.getUsername());
			list = imsDSQCService.getTeamCodeList(bomSalesUserDTO.getChannelId());
			for(String item:list)
			{
				if ( item != null && !"".equals(item))
					teamCds.put(item, item);
			}
			request.getSession().setAttribute("teamCds", teamCds);
			
		}else{
			request.getSession().setAttribute("IsSalesManager", "N");
		}

		////jacky tmp 20140925
			//if(!StringUtils.isEmpty(request.getParameter("_q"))) {
			List<ImsAlertMsgDTO> imsOrderList =imsDSQCService.getImsDsQcOrderEnquiryInfo(enquiry, bomSalesUserDTO);
			request.getSession().setAttribute("imsOrderList",imsOrderList);
			//}
		
		
		////jacky tmp 20140925(end)
		

		
		
		request.getSession().setAttribute("ntvDomain", ntvDomain);
		
		return enquiry;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
		logger.info("DsQCImsOrderEnquiry onSubmit is called");
		DsQCImsOrderEnquiryUI enquiry = (DsQCImsOrderEnquiryUI)command;
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		if(enquiry.getOrderId()!=null){// for mantis 5790, upper case search
			enquiry.setOrderId(enquiry.getOrderId().toUpperCase());
		}
		enquiry.print();
//		if(!"Y".equalsIgnoreCase(enquiry.getReset())&&!(enquiry.getLob().equalsIgnoreCase("LTS"))){
	/*
	if(bomSalesUserDTO.getChannelCd().equals("DS")){
			List<ImsAlertMsgDTO> a = new ArrayList<ImsAlertMsgDTO>();
			ImsAlertMsgDTO b = new ImsAlertMsgDTO();
			b.setOrderId("SSSHP101546");
			b.setSalesTeam("SSH");
			b.setOcid("21428107");
			b.setCustName("TEST ONE");
			b.setServiceNum("60087803");
			b.setLoginId("dddd22e3e"); 
			b.setAppDate("2014/09/01 15:22:54");
			b.setOrderStatus("02");
			b.setSalesCd("TIMS9I99");
			b.setSalesChannel("CSL");
			b.setRecall("Y");
			b.setReasonCD("S000");
			b.setCreateBy("TIMS9I99");
			a.add(b);
			request.getSession().setAttribute("imsOrderList",a); 
		}
		else*/ if(!"Y".equalsIgnoreCase(enquiry.getReset()) && !StringUtils.isEmpty(enquiry.getLob())){
			     if(LtsConstant.LOB_IMS.equals(enquiry.getLob())){
			    	 //logger.info("DsQCImsOrderEnquiry enquiry.getReset():" + (!"Y".equalsIgnoreCase(enquiry.getReset())));
			    	 List<ImsAlertMsgDTO> imsOrderList =imsDSQCService.getImsDsQcOrderEnquiryInfo(enquiry, bomSalesUserDTO);
			    	 List<String> roleCodeList = imsDSQCService.getRoleCodeByUserIDLkupCode(bomSalesUserDTO.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_WRITE, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION);
			    	 List<String> channelCodeList = imsDSQCService.getChannelCodeListByChannelID(bomSalesUserDTO.getChannelId());
					 List<String> teamCodeList = imsOrderAmendservice.getTeamCodeListOfCentreCd(bomSalesUserDTO.getUsername());
			    	 for(ImsAlertMsgDTO temp:imsOrderList){
			    		 temp.checkRecallAmend(roleCodeList,	bomSalesUserDTO, channelCodeList, teamCodeList);
			    	 }
			    	 request.getSession().setAttribute("imsOrderList",imsOrderList);
			     }else if(LtsConstant.LOB_LTS.equals(enquiry.getLob())){
					 /*Added by Felix Lee*/
					 List<ImsAlertMsgDTO> ltsOrderList = ltsDsQcService.getLtsDsQcAssignEnquiryInfo(enquiry, bomSalesUserDTO);
					 
					 ModelAndView mv = new ModelAndView("dsqcimsorderenquiry");
					 mv.addObject("imsOrderList",ltsOrderList);
					 mv.addObject("DsQCImsOrderEnquiryUI",enquiry);
					 mv.addAllObjects(referenceData(request));
					 return mv;
			     }
			    }else{
			    	 request.getSession().setAttribute("imsOrderList",null);
			     }
		
		request.getSession().setAttribute("dsqcimsorderenquiry", enquiry);
		
		return new ModelAndView(new RedirectView("dsqcimsorderenquiry.html"));
	}
	
	/*Added by Melody Tam*/
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request){
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		referenceData.put("orderStatusList", imsDSQCService.getOrderStatusList());
		referenceData.put("ltsOrderStatusList", ltsDsQcService.getLtsQcOrderStatusGroupList());
		
		return referenceData;
	}

	public CCSalesInfoService getCcsiService() {
		return ccsiService;
	}

	public void setCcsiService(CCSalesInfoService ccsiService) {
		this.ccsiService = ccsiService;
	}
}
