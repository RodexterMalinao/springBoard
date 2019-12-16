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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsAlertMsgDTO;
import com.bomwebportal.ims.dto.ui.ImsQcComOrderSearchUI;
import com.bomwebportal.ims.service.ImsOrderAmendService;
import com.bomwebportal.lts.service.qc.LtsDsQcService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.service.qc.ImsDSQCService;


public class ImsQcComOrderSearchController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());

	private ImsOrderAmendService imsOrderAmendservice;
	private LtsDsQcService ltsDsQcService;
	private ImsDSQCService imsDSQCService;
	
	public Object formBackingObject(HttpServletRequest request)
	throws ServletException {	
		logger.info("ImsQcComOrderSearchController formBackingObject is called");
		ImsQcComOrderSearchUI enquiry = (ImsQcComOrderSearchUI)request.getSession().getAttribute("imsqccomordersearch");
		if(enquiry==null || "Y".equals(enquiry.getReset())){
			enquiry = new ImsQcComOrderSearchUI();	
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

		//Celia		
		Map<String, String> teamCds = new LinkedHashMap<String, String>();
		List<String> list =null;
		
		list = imsDSQCService.getTeamCodeList(bomSalesUserDTO.getChannelId());
		for(String item:list)
		{
			if ( item != null && !"".equals(item))
				teamCds.put(item, item);
		}
		request.getSession().setAttribute("teamCds", teamCds);
		
		List <String> orderStatusList = new ArrayList<String>();
		orderStatusList=imsDSQCService.getOrderStatusList();
		request.getSession().setAttribute("orderStatusList", orderStatusList);

		List <String> QCStatusList = new ArrayList<String>();
		QCStatusList=imsDSQCService.getQcStatusList();
		request.getSession().setAttribute("QCStatusList", QCStatusList);
	    
		List <String> HousingTypeList = new ArrayList<String>();
		HousingTypeList= imsDSQCService.getQCHousingType();
		request.getSession().setAttribute("HousingTypeList", HousingTypeList);
	    logger.info("Housing Type List "+ HousingTypeList);
	    
		List <String> StaffTypeList = new ArrayList<String>();
		StaffTypeList= imsDSQCService.getQCStaffType();
		request.getSession().setAttribute("StaffTypeList", StaffTypeList);
	    logger.info("Staff Type List "+ StaffTypeList);
	    
		return enquiry;
	}
	
	/*Added by Melody Tam*/
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request){
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		referenceData.put("orderStatusList", imsDSQCService.getOrderStatusList());
		referenceData.put("qcStatusList", imsDSQCService.getQcStatusList());
		referenceData.put("ltsOrderStatusList", ltsDsQcService.getLtsQcOrderStatusGroupList());
		referenceData.put("ltsQcStatusList", ltsDsQcService.getLtsQcStatusList());
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
		logger.info("ImsQcComOrderSearchController onSubmit is called");
		ImsQcComOrderSearchUI enquiry = (ImsQcComOrderSearchUI)command;
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		//enquiry.setOrderStatus(""); 
		if(imsDSQCService.checkIfSalesManager(bomSalesUserDTO.getUsername())){
			request.getSession().setAttribute("IsSalesManager", "Y");
		}
		
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
		if(enquiry.getDocNum()!=null){
			enquiry.setDocType(enquiry.getDocType().toUpperCase());
			enquiry.setDocNum(enquiry.getDocNum());
		}
		if(enquiry.getFirstName()!=null){
			enquiry.setFirstName(enquiry.getFirstName());
		}
		if(enquiry.getLastName()!=null){
			enquiry.setLastName(enquiry.getLastName());
		}
		if(enquiry.getLoginId()!=null){
			enquiry.setLoginId(enquiry.getLoginId());
		}
		if(enquiry.getOrderStatus()!=null){
			enquiry.setOrderStatus(enquiry.getOrderStatus());
		}
		if(enquiry.getQcStatus()!=null){
			enquiry.setQcStatus(enquiry.getQcStatus());
		}
		if(enquiry.getCreateStaff()!=null){
			enquiry.setCreateStaff(enquiry.getCreateStaff());
		}
		if(enquiry.getTeamSearch()!=null){
			enquiry.setTeamSearch(enquiry.getTeamSearch());
		}
		if(enquiry.getSalesNum()!=null){
			enquiry.setSalesNum(enquiry.getSalesNum().toUpperCase());
		}		
		if(enquiry.getPaymentMethod()!=null){
			enquiry.setPaymentMethod(enquiry.getPaymentMethod().toUpperCase());
		}
		if(enquiry.getIs3rdParty()!=null){
			enquiry.setIs3rdParty(enquiry.getIs3rdParty().toUpperCase());
		}
		if(enquiry.getHousingType()!=null){
			enquiry.setHousingType(enquiry.getHousingType().toUpperCase());
		}
		if(enquiry.getStaffType()!=null){
			enquiry.setStaffType(enquiry.getStaffType().toUpperCase());
		}

		if(!"Y".equalsIgnoreCase(enquiry.getReset())){
			if(LtsConstant.LOB_IMS.equals(enquiry.getLob())){
//				List<ImsAlertMsgDTO> imsOrderList =imsOrderAmendservice.getLtsImsOrderEnquiryListInfo(enquiry, bomSalesUserDTO);
				List<ImsAlertMsgDTO> imsOrderList =imsDSQCService.getImsDSQCOrderEnquiryListInfo(enquiry, bomSalesUserDTO);
				//logger.info("getImsDSQCOrderEnquiryListInfo:  " + imsOrderList);
				//logger.info("enquiry:  "+enquiry);
			
				List<String> roleCodeList = imsOrderAmendservice.getRoleCodeByUserIDLkupCode(bomSalesUserDTO.getUsername(), ImsConstants.CC_LTS_IMS_ENQUIRY_WRITE, ImsConstants.CC_LTS_IMS_ENQUIRY_FUNCTION);
				List<String> channelCodeList = imsOrderAmendservice.getChannelCodeListByChannelID(bomSalesUserDTO.getChannelId());
				List<String> teamCodeList = imsOrderAmendservice.getTeamCodeListOfCentreCd(bomSalesUserDTO.getUsername());
				for(ImsAlertMsgDTO temp:imsOrderList){
				temp.checkRecallAmend(roleCodeList,	bomSalesUserDTO, channelCodeList, teamCodeList);
				}
				request.getSession().setAttribute("imsOrderList",imsOrderList);
			}else if(LtsConstant.LOB_LTS.equals(enquiry.getLob())){
				 /*Added by Melody Tam*/
				 List<ImsAlertMsgDTO> ltsOrderList = ltsDsQcService.getLtsDsQcOrderEnquiryInfo(enquiry, bomSalesUserDTO);
				 
				if(ltsOrderList != null && ltsOrderList.size()>0){ 
					for(ImsAlertMsgDTO ordDto : ltsOrderList){
					 
						if(StringUtils.equals(ordDto.getIs3rdParty(),"Y") && StringUtils.equals(ordDto.getPaymentMtd(),"C")){
							ordDto.setIs3rdParty("Y");
						}else{
							ordDto.setIs3rdParty("N");
						}
					 
						if(StringUtils.equals(ordDto.getPaymentMtd(),"M")){
							ordDto.setPaymentMtd("Cash");
						}else if(StringUtils.equals(ordDto.getPaymentMtd(),"C")){
							ordDto.setPaymentMtd("Credit Card");
						}else if(StringUtils.equals(ordDto.getPaymentMtd(),"A")){
							ordDto.setPaymentMtd("AutoPay");
						}
					}
				}
				 
				request.getSession().setAttribute("imsOrderList",ltsOrderList);
				
				 ModelAndView mv = new ModelAndView("imsqccomordersearch");
				 mv.addObject("imsOrderList",ltsOrderList);
				 mv.addObject("imsQcComOrderSearchUI",enquiry);
				 mv.addAllObjects(referenceData(request));
				 return mv;
			}
		}else{
			request.getSession().setAttribute("imsOrderList",null);
		}
		request.getSession().setAttribute("imsqccomordersearch", enquiry);
		return new ModelAndView(new RedirectView("imsqccomordersearch.html"));
	}

	public void setImsOrderAmendservice(ImsOrderAmendService imsOrderAmendservice) {
		this.imsOrderAmendservice = imsOrderAmendservice;
	}

	public ImsOrderAmendService getImsOrderAmendservice() {
		return imsOrderAmendservice;
	}

	public void setImsDSQCService(ImsDSQCService imsDSQCService) {
		this.imsDSQCService = imsDSQCService;
	}

	public ImsDSQCService getImsDSQCService() {
		return imsDSQCService;
	}

	public LtsDsQcService getLtsDsQcService() {
		return ltsDsQcService;
	}

	public void setLtsDsQcService(LtsDsQcService ltsDsQcService) {
		this.ltsDsQcService = ltsDsQcService;
	}
}
