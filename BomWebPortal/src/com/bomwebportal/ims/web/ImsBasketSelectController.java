package com.bomwebportal.ims.web;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.BandwidthDTO;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ImsBasketDTO;
import com.bomwebportal.ims.dto.ServiceDetailDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.DevService;
import com.bomwebportal.ims.service.ImsBasketSelectService;
import com.bomwebportal.service.ConstantLkupService;
import com.google.gson.Gson;

public class ImsBasketSelectController extends SimpleFormController{

    protected final Log logger = LogFactory.getLog(getClass());
    
    private Gson gson = new Gson();
  
    private ImsBasketSelectService service;
    private ConstantLkupService constantLkupService;
    
    public ImsBasketSelectService getService() {
		return service;
	}
	public void setService(ImsBasketSelectService service) {
		this.service = service;
	}
	
	public ConstantLkupService getConstantLkupService() {
		return constantLkupService;
	}
	public void setConstantLkupService(ConstantLkupService constantLkupService) {
		this.constantLkupService = constantLkupService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {

		logger.debug(request.getSession().getAttribute(ImsConstants.IMS_ORDER)==null?"NULL":"ORDER FOUND");
		/*if (request.getSession().getAttribute(ImsConstants.IMS_ORDER) == null) {
			OrderImsUI tmpOrder = DevService.getNewOrder();
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, tmpOrder);
		}*/
		
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		String locale = RequestContextUtils.getLocale(request).toString();
		String customerTier=request.getParameter("customertier");
		List<BandwidthDTO> bandwidthList = order.getInstallAddress().getBandwidthList();
		List<HousingTypeDTO> housingTypeList = order.getInstallAddress().getHousingTypeList();
		List<ServiceDetailDTO> ServiceDetailList = order.getInstallAddress().getServiceDetailList();
		String bandwidthStr = "";
		String housingTypeStr = "";	
		String technologyStr = "";
		String IsPON = "";
		String basketID = "";
		String appDate ="sysdate";
		
		List<String> imsPlanTypeList = new ArrayList<String>();
		List<String> imsPreTickPlanTypeList = new ArrayList<String>();

		imsPlanTypeList =service.getImsPlanTypeList(locale);
		imsPreTickPlanTypeList =service.getImsPreTickPlanTypeList(locale);

		
		if(request.getSession().getAttribute("IMS_ApprovalRequested")!=null){
			request.getSession().setAttribute("IMS_ApprovalRequested", null);
		}	
		
		if(order.getAppDate()!=null){
			SimpleDateFormat dateyyyyMMdd = new SimpleDateFormat("yyyyMMdd");
			appDate = 
				"to_date('" + 
				dateyyyyMMdd.format(order.getAppDate())+
				"','yyyymmdd')";
			
		}
		logger.info("appDate: "+appDate);
		
		if(order.getSubscribedItems()!=null&&order.getSubscribedItems().length>0){
			System.out.println("order.getSubscribedItems()[0].getBasketId()"+order.getSubscribedItems()[0].getBasketId());
			for (int i=0;i<order.getSubscribedItems().length;i++){
				if(order.getSubscribedItems()[i].getType().equalsIgnoreCase("PROG")){
					if(order.getSubscribedItems()[i].getBasketId()!=null){
						if(!order.getSubscribedItems()[i].getBasketId().equals(""))
							basketID = order.getSubscribedItems()[i].getBasketId();
					}
				}
			}
		}
		
		String[] bandwidthItemList = (String[])request.getParameterValues("bandwidthItem");
		List<String> bwItemList = new ArrayList<String>();
		if (bandwidthItemList!=null){
			for(int k=0; k<bandwidthItemList.length;k++)
			{
				bwItemList.add(k, bandwidthItemList[k]);
			}
		}
		String[] planTypeItemList = (String[])request.getParameterValues("planTypeBox");
		List<String> selectedPtItemList = (List<String>)request.getSession().getAttribute("selectedPtItemList");
		List<String> ptItemList = new ArrayList<String>();
		if (planTypeItemList!=null){
			for(int k=0; k<planTypeItemList.length;k++)
			{
				ptItemList.add(k, planTypeItemList[k]);
			}
		}else if(selectedPtItemList!=null){
			for(int m=0; m<selectedPtItemList.size();m++)
			{
				ptItemList.add(m, selectedPtItemList.get(m));
			}
		}else{
			ptItemList = imsPreTickPlanTypeList;
		}
		
		System.out.println("ptItemList:" + ptItemList);
		System.out.println("imsPlanTypeList:" + imsPlanTypeList);
		
		String bandwidthParam = request.getParameter("bandwidthParam");
		
		List<String> bandwidthParamList = null;
		List<String> bandwidthInPonParamList = null;
		
		Map<String, ArrayList<String>> bandwidthDescMapping = new HashMap<String, ArrayList<String>>();
		
		if(bandwidthList!=null){
			if(bandwidthList.size()>0){
				if(Float.valueOf(bandwidthList.get(0).getBandwidth())<Float.valueOf(bandwidthList.get(bandwidthList.size()-1).getBandwidth())){
					Collections.reverse(bandwidthList);
				}		
				for(int i=0; i<bandwidthList.size(); i++){
					if(i!=(bandwidthList.size()-1)){
						bandwidthStr = bandwidthStr + bandwidthList.get(i).getBandwidth() + ",";
					}else{
						bandwidthStr = bandwidthStr + bandwidthList.get(i).getBandwidth();
					}
				}
			}
		}
		if(housingTypeList!=null){
			if(housingTypeList.size()>0){	
				for(int i=0; i<housingTypeList.size(); i++){
					if(i!=(housingTypeList.size()-1)){
						housingTypeStr = housingTypeStr + housingTypeList.get(i).getHousingType() + ",";
					}else{
						housingTypeStr = housingTypeStr + housingTypeList.get(i).getHousingType();
					}
				}
			}
		}
		
		String isPonShort = "N";
		String isVdslShort = "N";
		String isVectorShort = "N";
		String isAdslShort = "N";
		
		if (ServiceDetailList!=null){
			for(int l=0; l<ServiceDetailList.size();l++)
			{	
				if(!ServiceDetailList.get(l).getTechnology().equals("PON")
						&&ServiceDetailList.get(l).getTechnologySupported().equals("Y")){
					if(l==0){
						technologyStr = ServiceDetailList.get(l).getTechnology();
					}else{
						technologyStr = technologyStr + "," + ServiceDetailList.get(l).getTechnology();
					}
				}else{
					if(ServiceDetailList.get(l).getTechnologySupported().equals("Y"))
					IsPON = ServiceDetailList.get(l).getTechnology();
				}
				
				if(ServiceDetailList.get(l).getTechnology().equals("PON")&&ServiceDetailList.get(l).getTechnologySupported().equals("Y")
						&&"Y".equals(ServiceDetailList.get(l).getIsResrcShort())){
					isPonShort = "Y";
				}
				if(ServiceDetailList.get(l).getTechnology().equals("VDSL")&&ServiceDetailList.get(l).getTechnologySupported().equals("Y")
						&&"Y".equals(ServiceDetailList.get(l).getIsResrcShort())){
					isVdslShort = "Y";
				}
				if(ServiceDetailList.get(l).getTechnology().equals("Vectoring")&&ServiceDetailList.get(l).getTechnologySupported().equals("Y")
						&&"Y".equals(ServiceDetailList.get(l).getIsResrcShort())){
					isVectorShort = "Y";
				}
				if(ServiceDetailList.get(l).getTechnology().equals("ADSL")&&ServiceDetailList.get(l).getTechnologySupported().equals("Y")
						&&"Y".equals(ServiceDetailList.get(l).getIsResrcShort())){
					isAdslShort = "Y";
				}
				
			}
		}
		
		logger.info("ServiceDetailList:" + ServiceDetailList + "; bandwidthStr:" + bandwidthStr + "; technologyStr:" + technologyStr + "; housingTypeStr:" + housingTypeStr + "; IsPON:" + IsPON + "; locale:" + locale);
		logger.debug("ImsBasketSelectController.handleRequest() Start");
		
    	BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	if (user==null){
    		user = new BomSalesUserDTO();
    	}
    	logger.info("userId:" + user.getUsername());
    	
    	String channelTeamCd = "";
    	String staffGroup = "";
    	
    	channelTeamCd = user.getChannelCd() + "-" + user.getShopCd();
    	staffGroup = service.getStaffGroup(user.getUsername());
		
    	order.setChannelTeamCd(channelTeamCd);
    	order.setStaffGroup(staffGroup);
    	
    	if(bandwidthParam!=null&&!"".equals(bandwidthStr)){
    		bandwidthStr = bandwidthParam;
    	}
    	
		if(customerTier!=null&&!"".equals(customerTier)){
			request.getSession().setAttribute("customerTierSession", customerTier);
		}

		//get select list from DB
		
		List<ImsBasketDTO> imsBasketList = new ArrayList<ImsBasketDTO>();

		
		String salesChannel = "";
		

		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			salesChannel = "DS";
		}else if(order.getIsPT().equals("Y")){
			salesChannel = "CC";
		}else if(order.getIsCC().equals("Y")){
			salesChannel = "CC";
		}else{
			salesChannel = "RS";
		}
		
		List<String> serviceCodeList = service.getServiceCodeList(order.getInstallAddress().getSerbdyno());
		String serviceCdStr = "";
		if(serviceCodeList!=null && serviceCodeList.size()>0){				
			for(int i=0; i<serviceCodeList.size(); i++){
				if(i!=(serviceCodeList.size()-1)){
					serviceCdStr += serviceCodeList.get(i) + ",";
				}else{
					serviceCdStr += serviceCodeList.get(i);
				}
			}			
		}		
		
		order.setServiceCodeStr(serviceCdStr);
		
		imsBasketList =service.getImsBasketList(locale, bandwidthStr, housingTypeStr, technologyStr, IsPON, appDate,salesChannel,user.getShopCd(),order.getInstallAddress().getSbFilterVas(),order.getMobileOfferInd(),order.getInstallAddress().getSerbdyno(),order.getLtsServiceInd(),
				channelTeamCd,staffGroup,serviceCdStr);
		
		String bandwidthCheck = "";
		String bandwidthDescCheck = "";
		if(imsBasketList!=null && bandwidthParamList==null){
			bandwidthParamList = new ArrayList<String>();
			bandwidthInPonParamList = new ArrayList<String>();
			for(int i=0; i<imsBasketList.size(); i++){

				if(bandwidthCheck.equals(imsBasketList.get(i).getBandwidth())&& bandwidthDescCheck.equals(imsBasketList.get(i).getBandwidth_desc())){
					bandwidthCheck = imsBasketList.get(i).getBandwidth();
				}else{
					if(Float.valueOf(imsBasketList.get(i).getBandwidth())<10){
						bandwidthParamList.add(imsBasketList.get(i).getBandwidth_desc());
						if(bandwidthDescMapping.get(imsBasketList.get(i).getBandwidth())==null){
							bandwidthDescMapping.put(imsBasketList.get(i).getBandwidth(), new ArrayList<String>());
							bandwidthDescMapping.get(imsBasketList.get(i).getBandwidth()).add(imsBasketList.get(i).getBandwidth_desc());
						}else{
							bandwidthDescMapping.get(imsBasketList.get(i).getBandwidth()).add(imsBasketList.get(i).getBandwidth_desc());
						}
						if(imsBasketList.get(i).getTechnology().equals("PON"))
							bandwidthInPonParamList.add(imsBasketList.get(i).getBandwidth_desc());
						break;
					}else {
						bandwidthParamList.add(imsBasketList.get(i).getBandwidth_desc());
						if(bandwidthDescMapping.get(imsBasketList.get(i).getBandwidth())==null){
							bandwidthDescMapping.put(imsBasketList.get(i).getBandwidth(), new ArrayList<String>());
							bandwidthDescMapping.get(imsBasketList.get(i).getBandwidth()).add(imsBasketList.get(i).getBandwidth_desc());
						}else{
							bandwidthDescMapping.get(imsBasketList.get(i).getBandwidth()).add(imsBasketList.get(i).getBandwidth_desc());
						}
						if(imsBasketList.get(i).getTechnology().equals("PON"))
							bandwidthInPonParamList.add(imsBasketList.get(i).getBandwidth_desc());
						bandwidthCheck = imsBasketList.get(i).getBandwidth();
					}
				}
				bandwidthDescCheck = imsBasketList.get(i).getBandwidth_desc();
			}
		}
		
		if(imsBasketList!=null)
		{
			for(int i =0;i<imsBasketList.size();i++){
				imsBasketList.get(i).setSummary(imsBasketList.get(i).getSummary().replaceAll(((char)10)+"", "<br>"));
			}
		}

		
		 
		if(bwItemList.size()==0){
			if(order.getIsCC().equals("Y") || ((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES))) 
			{
				if(("PON").equals(IsPON) && bandwidthInPonParamList.size()>0)
					bwItemList = bandwidthInPonParamList;
				else 
				{
					float maxBandwidth = -1;
					for(String bw: bandwidthDescMapping.keySet())
					{
						if(maxBandwidth < Float.valueOf(bw))
							maxBandwidth = Float.valueOf(bw);
					}
					bwItemList = new ArrayList<String>();
					String maxBandwidthStr ="0";
					if(maxBandwidth>0){
					maxBandwidthStr = String.valueOf(maxBandwidth);
					if( maxBandwidthStr.substring(maxBandwidthStr.indexOf(".")+1).equals("0"))// is integer
						bwItemList.addAll(bandwidthDescMapping.get(String.valueOf((int)maxBandwidth)));
					else
						bwItemList.addAll(bandwidthDescMapping.get(maxBandwidthStr));
					}
				}
			}
			else
			{
				bwItemList = bandwidthParamList;
			}
		}
		
		//return new ModelAndView
		ModelAndView myView =new ModelAndView("imsbasketselect");
		
		request.getSession().setAttribute("bandwidthParamList", bandwidthParamList);
		
		myView.addObject( "imsPlanTypeList", null);
		
		myView.addObject( "ImsBasketList", imsBasketList);
		myView.addObject( "customerTier", customerTier);
		myView.addObject( "bandwidthParamList", bandwidthParamList);
		myView.addObject( "bwItemList", bwItemList);
		myView.addObject("ptItemList", ptItemList);
		myView.addObject( "imsPlanTypeList", imsPlanTypeList);
		myView.addObject( "isPonShort", isPonShort);
		myView.addObject( "isVdslShort", isVdslShort);
		myView.addObject( "isVectorShort", isVectorShort);
		myView.addObject( "isAdslShort", isAdslShort);
		
		Map<String, Map<String,String>> imsFrontendAlertMsg = 
			(Map<String, Map<String,String>>) request.getSession().getAttribute(ImsConstants.IMS_FRONTEND_ALERT_MSG);
		if (imsFrontendAlertMsg == null || imsFrontendAlertMsg.size() == 0) {
			imsFrontendAlertMsg = constantLkupService.getImsServPlanStaticReportWords("ims/BomWebPortal/SectionJ%", "servPlan%");
			request.getSession().setAttribute(ImsConstants.IMS_FRONTEND_ALERT_MSG, imsFrontendAlertMsg);
		}
		
		if (imsFrontendAlertMsg != null && imsFrontendAlertMsg.size() > 0) {
			String preInstSubmitAnchor = imsFrontendAlertMsg.get("en").get("servPlan07") + "<br/><br/>" + 
											imsFrontendAlertMsg.get("zh_HK").get("servPlan07");
			String preInstSubmitAnchor_googleRouter = imsFrontendAlertMsg.get("en").get("servPlan09") + "<br/><br/>" +
											imsFrontendAlertMsg.get("zh_HK").get("servPlan09");
			String preUseSubmitAnchor = imsFrontendAlertMsg.get("en").get("servPlan08") + "<br/><br/>" + 
											imsFrontendAlertMsg.get("zh_HK").get("servPlan08");
			myView.addObject("preInstSubmitAnchor_Msg", preInstSubmitAnchor);
			myView.addObject("preInstSubmitAnchor_googleRouter_Msg", preInstSubmitAnchor_googleRouter);
			myView.addObject("preUseSubmitAnchor_Msg", preUseSubmitAnchor);
		}
		

		logger.info("ImsBasketList: " + imsBasketList);
		logger.info("customerTier:" + customerTier);
		logger.info("bandwidthParamList:" + bandwidthParamList);
		logger.info("bwItemList:" + bwItemList);
		logger.info("ptItemList:" + ptItemList);
		
		request.getSession().setAttribute("IMS_BasketID", basketID);
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
		request.getSession().setAttribute("IMS_ApprovalRequested", order.getApprovalRequested());
		request.getSession().setAttribute("selectedPtItemList", ptItemList);
		
		logger.info(gson.toJson(order));
		
		return myView;
	}	
}



