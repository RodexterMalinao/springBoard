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
import com.bomwebportal.ims.dto.ui.ImsDsQCProcessUI;
import com.bomwebportal.ims.service.CCSalesInfoService;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.lts.service.qc.LtsDsQcService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.qc.ImsDSQCService;

public class ImsDsQCProcessController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());

	private ImsDSQCService imsDSQCService;
	private CCSalesInfoService ccsiService;
	private ImsOrderAmendService imsOrderAmendservice;
	private LtsDsQcService ltsDsQcService;
	
	public Object formBackingObject(HttpServletRequest request)
	throws ServletException {	
		logger.info("ImsDsQCProcessEnquiry formBackingObject is called");
		ImsDsQCProcessUI enquiry = (ImsDsQCProcessUI)request.getSession().getAttribute("ImsDsQCProcess");
		if(enquiry==null || "Y".equals(enquiry.getReset())){
			enquiry = new ImsDsQCProcessUI();	
			enquiry.setLob("IMS");
			enquiry.setDateType("A");
			enquiry.setDocType("HKID");
			SimpleDateFormat sdFormat = new SimpleDateFormat("dd/MM/yyyy");
			Date current = new Date();
			enquiry.setEndDate(sdFormat.format(current));
			Date today = new Date();
			Calendar c = Calendar.getInstance(); 
			c.setTime(today); 
			//c.add(Calendar.DATE, -7);
			today = c.getTime();
			enquiry.setStartDate(sdFormat.format(today));
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

		//for loaded page
		//if(!StringUtils.isEmpty(request.getParameter("_q"))) {
			List<ImsAlertMsgDTO> imsOrderList =imsDSQCService.getImsDsQcProcessEnquiryInfo(enquiry, bomSalesUserDTO);
			request.getSession().setAttribute("imsOrderList",imsOrderList);
		//}
		
		
		List <String> orderStatusList = new ArrayList<String>();
		orderStatusList=imsDSQCService.getOrderStatusList();
		request.getSession().setAttribute("orderStatusList", orderStatusList);
		
		List <String> qcStatusList = new ArrayList<String>();
		qcStatusList=imsDSQCService.getQcStatusList();
		request.getSession().setAttribute("qcStatusList", qcStatusList);
		
		//kinman
		Map<String,String> qcOrderTypeSelectionList = new LinkedHashMap<String,String>();
		List<Map<String,String>> map = imsDSQCService.getQcOrderTypeSelectionList();
		
		for(int i = 0; i < map.size();i++){
			logger.info(map.get(i).get("type"));
			qcOrderTypeSelectionList.put(map.get(i).get("type"), map.get(i).get("label"));
		}
		
		logger.info(qcOrderTypeSelectionList);
		request.getSession().setAttribute("qcOrderTypeSelectionList", qcOrderTypeSelectionList);
		
		
		
		return enquiry;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
		logger.info("ImsDsQCProcessEnquiry onSubmit is called");
		ImsDsQCProcessUI enquiry = (ImsDsQCProcessUI)command;
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		if(enquiry.getOrderId()!=null){// for mantis 5790, upper case search
			enquiry.setOrderId(enquiry.getOrderId().toUpperCase());
		}
		enquiry.print();
		
		if(!"Y".equalsIgnoreCase(enquiry.getReset()) && !StringUtils.isEmpty(enquiry.getLob())){
			if(LtsConstant.LOB_IMS.equals(enquiry.getLob())){
			    List<ImsAlertMsgDTO> imsOrderList =imsDSQCService.getImsDsQcProcessEnquiryInfo(enquiry, bomSalesUserDTO);
				List<String> roleCodeList = imsDSQCService.getRoleCodeByUserIDLkupCode(bomSalesUserDTO.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_WRITE, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION);
				List<String> channelCodeList = imsDSQCService.getChannelCodeListByChannelID(bomSalesUserDTO.getChannelId());
				List<String> teamCodeList = imsOrderAmendservice.getTeamCodeListOfCentreCd(bomSalesUserDTO.getUsername());
				for(ImsAlertMsgDTO temp:imsOrderList){
					temp.checkRecallAmend(roleCodeList,	bomSalesUserDTO, channelCodeList, teamCodeList);
				}
				request.getSession().setAttribute("imsOrderList",imsOrderList);
			}else if(LtsConstant.LOB_LTS.equals(enquiry.getLob())){
				 /*Added by Felix Lee*/
				 List<ImsAlertMsgDTO> ltsOrderList = ltsDsQcService.getLtsDsQcProcessEnquiryInfo(enquiry, bomSalesUserDTO);
				 
				 ModelAndView mv = new ModelAndView("imsdsqcprocess");
				 mv.addObject("imsOrderList",ltsOrderList);
				 mv.addObject("ImsDsQCProcessUI",enquiry);
				 mv.addAllObjects(referenceData(request));
				 return mv;
			}
		}else{
			request.getSession().setAttribute("imsOrderList",null);
		} 
		request.getSession().setAttribute("ImsDsQCProcess", enquiry);
		
		return new ModelAndView(new RedirectView("imsdsqcprocess.html"));
	}

	/*Added by Felix Lee*/
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request){
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		referenceData.put("orderStatusList", imsDSQCService.getOrderStatusList());
		referenceData.put("qcStatusList", imsDSQCService.getQcStatusList());
		referenceData.put("ltsOrderStatusList", ltsDsQcService.getLtsQcOrderStatusGroupList());
		referenceData.put("ltsQcStatusList", ltsDsQcService.getLtsQcStatusList());
		
		return referenceData;
	}
	
	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}

	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}
	
	public CCSalesInfoService getCcsiService() {
		return ccsiService;
	}

	public void setCcsiService(CCSalesInfoService ccsiService) {
		this.ccsiService = ccsiService;
	}
	

	
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

	
}
