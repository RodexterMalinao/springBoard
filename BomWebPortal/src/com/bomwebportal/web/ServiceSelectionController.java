package com.bomwebportal.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ChannalBasketDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.EligibilityDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.CustomerInformationQuotaService;
import com.bomwebportal.service.ServiceSelectionService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class ServiceSelectionController extends SimpleFormController { 

	protected final Log logger = LogFactory.getLog(getClass());
	private ServiceSelectionService serviceSelectionService;
	private CustomerInformationQuotaService customerInformationQuotaService;

	public void setServiceSelectionService(
			ServiceSelectionService serviceSelectionService) {
		this.serviceSelectionService = serviceSelectionService;
	}

	public ServiceSelectionService getServiceSelectionService() {
		return serviceSelectionService;
	}

	public CustomerInformationQuotaService getCustomerInformationQuotaService() {
		return customerInformationQuotaService;
	}

	public void setCustomerInformationQuotaService(
			CustomerInformationQuotaService customerInformationQuotaService) {
		this.customerInformationQuotaService = customerInformationQuotaService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String nextView = (String) request.getAttribute("nextView");
		logger.info("nextView: " + nextView);
		
		String message = "";
		
		String orderType = (String) request.getSession().getAttribute("orderType");
		String locale = RequestContextUtils.getLocale(request).toString();
		String appDateStr = (String) request.getSession().getAttribute("appDate");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		BasketDTO basketDto = (BasketDTO) MobCcsSessionUtil.getSession(request,	"basket");// add basket info to front end
		int channelId=(user.getChannelId() == 11 ? 10 : user.getChannelId());//get channelId , convert CFM(66), CPA(68) => IBS, OBS(2)
		/*if (60>=user.getChannelId()){
			channelId =2;//convert channel ID
		}*/
		if (LookupTableBean.getInstance().getAllowFalloutChannelList()!=null && LookupTableBean.getInstance().getAllowFalloutChannelList().length>0 && Utility.isContainString(LookupTableBean.getInstance().getAllowFalloutChannelList(), user.getChannelCd())){
			channelId =2;//convert channel ID
		}
		MobCcsSessionUtil.setSession(request, "originalBasket", basketDto);
		
		
		
		ChannalBasketDTO channelBasketDto = null;
		if (user != null && basketDto != null && appDateStr != null) {
			channelBasketDto = serviceSelectionService.getChannelBasketStatus(
					channelId+"", basketDto.getBasketId(), appDateStr);
		}

		String currentDate = Utility.date2String(new Date(),BomWebPortalConstant.DATE_FORMAT);
		if (null == appDateStr) {
			appDateStr = Utility.date2String(new Date(), BomWebPortalConstant.DATE_FORMAT);
		}
		

		if (orderType != null && "DRAF".equals(orderType) && !currentDate.equals(appDateStr)) {
			String origialAppDate = appDateStr;
			appDateStr = Utility.date2String(new Date(),	BomWebPortalConstant.DATE_FORMAT);
			logger.info("update appDate to current Date: " + appDateStr);
			message = "Updated application Date to Current Date, from "	+ origialAppDate + " to " + appDateStr;
		}
		request.getSession().setAttribute("appDate", appDateStr);//save appDate to session

		List<EligibilityDTO> eligList = new ArrayList<EligibilityDTO>();

		
		CustomerProfileDTO customerProfileDTO = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		if (customerProfileDTO != null) {
			logger.info("Search Eligibility by customerProfileDTO");
			eligList = customerInformationQuotaService.getEligibilityDTOWithDefaultValuesList(
							customerProfileDTO.getIdDocType(),
							customerProfileDTO.getIdDocNum());
		}

		// jsp pulldown list
		OrderDTO orderDTO= (OrderDTO)MobCcsSessionUtil.getSession(request, "orderDTO");
		String originalSaleCd="";
		if(orderDTO !=null){
			originalSaleCd=orderDTO.getSalesCd();
		}else{
			originalSaleCd=user.getUsername();
		}
		List<String[]> callList = serviceSelectionService.getCallList(	appDateStr, originalSaleCd);
		
		
		
		
		String appMode = (String) request.getSession().getAttribute("appMode");
		// two session for mob/mobccs
		customerProfileDTO = (CustomerProfileDTO) request
				.getSession().getAttribute("customer");

		if ("mobccs".equals(appMode)) {
			customerProfileDTO = (CustomerProfileDTO) MobCcsSessionUtil
					.getSession(request, "customer");
		}
		/*if (user.getChannelId() == 2) {
			customerProfileDTO = (CustomerProfileDTO) MobCcsSessionUtil.getSession(request, "customer");
		} else {
			customerProfileDTO = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		}*/
		List<String[]> customerTierList = new ArrayList<String[]>();
		if (customerProfileDTO.isStudentPlanSubInd()){
			List<CodeLkupDTO> cdLkupList = LookupTableBean.getInstance().getCodeLkupList().get("STUDENT_PLAN_CUSTOMER_TIER");
			if (CollectionUtils.isNotEmpty(cdLkupList)){
				for (CodeLkupDTO codelkup : cdLkupList){
					String[] tier = new String[2];
		        	tier[0]=codelkup.getCodeId();
		        	tier[1]=codelkup.getCodeDesc();
		        	customerTierList.add(tier);
				}
			}
			
		}else{
			customerTierList = serviceSelectionService.getEligibilityCustomerTierListByPeriorty(locale, eligList, channelId);//change to new method, 20120813
		}

		CustomerProfileDTO sessionCustomer = (CustomerProfileDTO) request
				.getSession().getAttribute("customer");
		
		if (sessionCustomer != null){
			Boolean checkIsWhiteList = sessionCustomer.getCheckIsWhiteList();
			
	        if (checkIsWhiteList){
	            List<CodeLkupDTO> cdLkupList = LookupTableBean.getInstance().getCodeLkupList().get("C_RETENTION_PLAN_CUSTOMER_TIER");
	            if (CollectionUtils.isNotEmpty(cdLkupList)){
	                   for (CodeLkupDTO codelkup : cdLkupList){
	                          String[] tier = new String[2];
	                    tier[0]=codelkup.getCodeId();
	                    tier[1]=codelkup.getCodeDesc();
	                    customerTierList.add(tier);
	                   }
	            }
	        }
		}
		
		//List<String[]> customerTierList = serviceSelectionService.getEligibilityCustomerTierList(locale, eligList,	channelId);
		List<String[]> basketTypeList = serviceSelectionService.getSelectList(locale, "BASKET_TYPE");
		List<String[]> rpTypeList = serviceSelectionService.getSelectList(locale, "RP_TYPE");
		
		List<VasDetailDTO> vasHSRPList =(List<VasDetailDTO>)MobCcsSessionUtil.getSession(request, "vasHSRPList");
		
		List<MultiSimInfoDTO> multiSimInfos = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		
		ModelAndView myView = new ModelAndView("serviceselection");
		myView.addObject("customerTierList", customerTierList);
		myView.addObject("basketTypeList", basketTypeList);
		myView.addObject("ratePlanTypeList", rpTypeList);
		myView.addObject("callList", callList);
		
		
		myView.addObject("channelId", channelId);
		myView.addObject("channelCd", user.getChannelCd());
		myView.addObject("channelBasket", channelBasketDto);

		myView.addObject("basket", basketDto);
		myView.addObject("message", message);
		myView.addObject("order", orderDTO);
		myView.addObject("vasHSRPList", vasHSRPList);
		myView.addObject("multiSimInfos", multiSimInfos);
		// add 20110609 for concierge
		MnpDTO mnp = (MnpDTO) request.getSession().getAttribute("MNP");
		if (mnp != null) {
			myView.addObject("sessionCutoverDateStr", mnp.getCutoverDateStr());
			myView.addObject("sessionServiceReqDateStr",
					mnp.getServiceReqDateStr());
		}
		
		/* test uid*/
		String attrUid=(String)request.getParameter("sbuid");
		myView.addObject("sbuid", attrUid);
		/* test uid*/

		myView.addObject("brand", customerProfileDTO.getBrand());

		return myView;
	}

}
