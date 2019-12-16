package com.bomwebportal.web;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.WelcomePageBomSalesUserDTO;
import com.bomwebportal.exception.DuplicateOrderException;
import com.bomwebportal.exception.ImsDSStaffMobMisMatchException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.service.ImsOrderService;
import com.bomwebportal.lts.service.LtsAlertMessageService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.mob.ccs.dto.MappingLkupDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MappingLkupService;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.service.ReleaseLockService;
import com.bomwebportal.service.WelcomeService;
import com.bomwebportal.util.ConfigProperties;
import com.bomwebportal.web.ext.BomWebPortalApplicationFlow;
import com.bomwebportal.web.util.ReportSessionName;

public class WelcomeController implements Controller {

	protected final Log logger = LogFactory.getLog(getClass());

	private BomWebPortalApplicationFlow appFlow;

	private WelcomeService welcomeService;
	
	private ImsOrderService imsOrderService;
	
	private ReleaseLockService releaseLockService;
	
	private MappingLkupService mappingLkupService;
	
	private CodeLkupService codeLkupService;
	
	private String ntvDomain;	
	
	private LoginService loginService;
	
	private LtsAlertMessageService ltsAlertMessageService;

	public ReleaseLockService getReleaseLockService() {
		return releaseLockService;
	}
	public void setReleaseLockService(ReleaseLockService releaseLockService) {
		this.releaseLockService = releaseLockService;
	}
	public BomWebPortalApplicationFlow getAppFlow() {
		return appFlow;
	}
	public void setAppFlow(BomWebPortalApplicationFlow appFlow) {
		this.appFlow = appFlow;
	}

	public void setWelcomeService(WelcomeService welcomeService) {
		this.welcomeService = welcomeService;
	}
	public WelcomeService getWelcomeService() {
		return welcomeService;
	}
	
	public MappingLkupService getMappingLkupService() {
		return mappingLkupService;
	}
	public void setMappingLkupService(MappingLkupService mappingLkupService) {
		this.mappingLkupService = mappingLkupService;
	}
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	
	public LtsAlertMessageService getLtsAlertMessageService() {
		return this.ltsAlertMessageService;
	}
	public void setLtsAlertMessageService(
			LtsAlertMessageService pLtsAlertMessageService) {
		this.ltsAlertMessageService = pLtsAlertMessageService;
	}

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException,
			Exception {
		
		String httpsRedirect = request.getParameter("isRedirect");
		
		if (StringUtils.isEmpty(httpsRedirect)) {
			BomSalesUserDTO user = (BomSalesUserDTO) request.getSession()
					.getAttribute("bomsalesuser");
			if (user == null) {
				user = new BomSalesUserDTO();
			}
			logger.info("userId:" + user.getUsername());
	
			//To release order lock
			String orderId = (String) request.getSession().getAttribute("orderIdSession");				
			
			/******************* Update order status and remove session *******************************/
			// Delete Session
	
			request.getSession().removeAttribute("customer");
			request.getSession().removeAttribute("payment");
			request.getSession().removeAttribute("MNP");
			request.getSession().removeAttribute("MobileSimInfo");
			request.getSession().removeAttribute("orderIdSession");
			request.getSession().removeAttribute("customerTierSession");
			request.getSession().removeAttribute("baskettypeSession");
			request.getSession().removeAttribute("rptypeSession");
			request.getSession().removeAttribute("selectedVasItemList");
			request.getSession().removeAttribute("componentList");
			request.getSession().removeAttribute("signoffDtoSession");
			request.getSession().removeAttribute("custSearchResultListSession");
			request.getSession().removeAttribute("custServInUseListSession");
			request.getSession().removeAttribute("custOrderHistoryListSession");
			request.getSession().removeAttribute("customerInformationDTOSession");
			request.getSession().removeAttribute("sessionCcsOrderHash");
			request.getSession().removeAttribute("selectedCustInfoSession");
			request.getSession().removeAttribute("selectedServiceLineSession");
			request.getSession().removeAttribute("appDate");
			request.getSession().removeAttribute("customerInfoQuota");
			request.getSession().removeAttribute("basketSession");
			request.getSession().removeAttribute("orderType");
			request.getSession().removeAttribute("supportDoc");
			
			request.getSession().removeAttribute("customerSignSession");
			request.getSession().removeAttribute("bankSignSession");
			request.getSession().removeAttribute("mnpSignSession");
			request.getSession().removeAttribute("conciergeSignSession");
			
			request.getSession().removeAttribute("sessionOrderList");
			request.getSession().removeAttribute("sbuid");
			request.getSession().removeAttribute("iGuardSignSession");
			request.getSession().removeAttribute(ReportSessionName.SIGNOFF.getTravelInsuranceSignDtoName());
			request.getSession().removeAttribute(ReportSessionName.SIGNOFF.getHelperCareInsuranceSignDtoName());
			request.getSession().removeAttribute(ReportSessionName.SIGNOFF.getProjectEagleInsuranceSignDtoName());
			request.getSession().removeAttribute("approvedSrvLineExceed");
			request.getSession().removeAttribute("tdoSignSession"); //20130709
	
			/******************* Update order status and remove session(END) ***************************/
	
			user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
			//channelCd ==> appMode
			String channelCd = user.getChannelCd();
			String appMode = "shop";
			
			MappingLkupDTO mappingLkupDTO = this.mappingLkupService.getMappingLkupDTO("MOB_APP_MODE", user.getChannelId() + "");
			if (mappingLkupDTO == null) {
				throw new DuplicateOrderException(
						"channelId appMode mapping error(w_mapping_lkup)" +
								" :Username=" + user.getUsername() +
								" :channelId=" + user.getChannelId() +
								"<br> Please contact Springboard support!!");
			}
			appMode = mappingLkupDTO.getMapTo();
			
			request.getSession().setAttribute("appMode", appMode);
			request.getSession().setAttribute("channelCd", channelCd);
			logger.info("save appMode session: " + appMode);
			
			// commented 20120611
			// Display usable maint function only, replace by function below
	//		List<String> usableMaintId 
	//			= welcomeService.getUsableMaintId(""+user.getChannelCd(), user.getCategory());
	//		logger.debug("Possible Maint Function: " + usableMaintId);
	//		logger.debug("BomSalesUser: " + user.getChannelCd() + ", " + user.getCategory());
	//		
	//		if (usableMaintId != null && usableMaintId.size() > 0) {
	//			for (String m: usableMaintId) {
	//				if ("01".equals(m)) {
	//					request.setAttribute("show1", true);
	//				} else if ("02".equals(m)) {
	//					request.setAttribute("show2", true);
	//				} else if ("03".equals(m)) {
	//					request.setAttribute("show3", true);
	//				} else if ("04".equals(m)) {
	//					request.setAttribute("show4", true);
	//				} else if ("05".equals(m)) {
	//					request.setAttribute("show5", true);
	//				} else if ("06".equals(m)) {
	//					request.setAttribute("show6", true);
	//				} else if ("07".equals(m)) {
	//					request.setAttribute("show7", true);
	//				} else if ("08".equals(m)) {
	//					request.setAttribute("show8", true);
	//				} else if ("09".equals(m)) {
	//					request.setAttribute("show9", true);
	//				} else if ("10".equals(m)) {
	//					request.setAttribute("show10", true);
	//				} else if ("11".equals(m)) {
	//					request.setAttribute("show11", true);
	//				}
	//			}
	//		}
			
			//Logic of lock order release
	    	OrderDTO lockOrderDTO  = releaseLockService.getOrderLockInfo(orderId);
	    	if (lockOrderDTO != null) {
	    		
	    		if ("Y".equalsIgnoreCase(lockOrderDTO.getLockInd())) {
	    			if(user.getUsername().equalsIgnoreCase(lockOrderDTO.getLastUpdateBy())) {
	    				releaseLockService.updateOrderLockInd(orderId, "", user.getUsername());
	    			}
	    		}
		    }
	    	WelcomePageBomSalesUserDTO dtoUser= new WelcomePageBomSalesUserDTO();
	    	
	    	BeanUtils.copyProperties(dtoUser, user);
	
	    	if ("2".equals(""+user.getChannelId())){
		    	dtoUser.setFalloutOrderCount(welcomeService.getOrderEnquiryCount(null, null, 
						"ALL", "ALERT", null, user.getChannelCd(), 
						user.getUsername(), "ALL", user.getCategory(), 
						user.getAreaCd(), user.getShopCd(), null));
	    	}
	    	
	    	if (("10".equals(""+user.getChannelId()) && "SUPERVISOR".equals(user.getCategory()))
	    			|| ("11".equals(""+user.getChannelId()) && "ORDER SUPPORT".equals(user.getCategory()))) {
	    		dtoUser.setReviewOrderCount(welcomeService.getDsOrderReviewCount(user.getUsername(), (""+user.getChannelId()), user.getChannelCd(), 
	    				 user.getCategory()));
	    		//System.out.println(dtoUser.getReviewOrderCount());
	    	}
	    	
	    	/*
	    	if (user.getChannelId()== 12 || user.getChannelId()== 13){
	    		if (welcomeService.getDSinfo(user).toString().length()>0){ 
	    			dtoUser.setSalesType(welcomeService.getDSinfo(user).getSalesType());
	    		}
	    		dtoUser.setLocation(welcomeService.getDSinfo(user).getLocation());
	    	}*/
	    	//logger.debug("dtoUser.getSalesType()"+ dtoUser.getSalesType());
	    	
	    	/**/
	    	//added by karen
	    	if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)) {
	    		dtoUser.setLtsAlertCount(this.ltsAlertMessageService.getAlertCount(LtsConstant.ALERT_MSG_SRV_TYPE_TEL, user.getUsername()));
	    		dtoUser.setImsAlertCount(this.ltsAlertMessageService.getAlertCount(LtsConstant.ALERT_MSG_SRV_TYPE_IMS, user.getUsername()));
	    	}
	    	
			for (Enumeration e = request.getSession().getAttributeNames(); e.hasMoreElements();) {
				String attrib = new String(e.nextElement().toString());
			
				if (!"bomsalesuser".equals(attrib) 
						&& !"appMode".equals(attrib)
						&& !"channelCd".equals(attrib) 
						&& !"authorizationInfo".equals(attrib) 
						&& !"ssoAuthContext".equals(attrib)
						&& !"imsMobileOfferUiList".equals(attrib)
						&& !ImsConstants.IMS_DIRECT_SALES.equals(attrib)					
						&& !ImsConstants.IMS_DS_MOB_NUM.equals(attrib)				
						&& !"IMS_SSO_BYPASS".equals(attrib)
						) {	
					logger.debug("DELETE SessionAttribute: " + attrib);
					request.getSession().removeAttribute(attrib);
					
				}else{
					
					logger.debug("KEEP SessionAttribute: " + attrib);
				}
			}
			
			request.setAttribute("maintFuncInfoList", welcomeService.getUsableMaintFuncInfo(user.getChannelId(), user.getChannelCd(), user.getCategory()));
			request.setAttribute("channelFlow", this.codeLkupService.getCodeDesc("CHANNEL_FLOW", user.getChannelCd()));
			
			String lob = request.getParameter("lob");
			
			if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)) {	
				String isMatchRole = imsOrderService.isMatchRole( user.getChannelCd(),	user.getCategory());
				if(12==user.getChannelId()){
				if("DS5".equals(user.getChannelCd())){
					String isDs5DummyStaff = imsOrderService.isDs5DummyStaff(user.getOrgStaffId());
					if (isDs5DummyStaff!=null && "Y".equals(isDs5DummyStaff)){
						request.getSession().setAttribute("isDs5DummyStaff", isDs5DummyStaff);
					}
					if("Y".equalsIgnoreCase(isMatchRole)){
						if(request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)!=null 
								&&!"".equals((String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM))){
							 if("N".equals(imsOrderService.isDsMobileAgent(user.getShopCd(), 
										(String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)))){
									throw new ImsDSStaffMobMisMatchException();
							}
						}else						
							throw new ImsDSStaffMobMisMatchException();
					}else if(request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)!=null 
								&&!"".equals((String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM))){
						 if("N".equals(imsOrderService.isDsMobileAgent(user.getShopCd(), 
									(String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)))){
								throw new ImsDSStaffMobMisMatchException();
						}
					}
				}else if("DS6".equals(user.getChannelCd())){
					if(request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)!=null 
							&&!"".equals((String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM))){
					 if("N".equals(imsOrderService.isDsStaffMobbile(user.getUsername(), (String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)))){
							throw new ImsDSStaffMobMisMatchException();
					 }
					}
				}else{				
					if("Y".equalsIgnoreCase(isMatchRole)){
						if(request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)!=null  && 
								!"".equals((String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM))){
							if("N".equals(imsOrderService.isDsStaffMobbile(user.getUsername(), (String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)))){
								throw new ImsDSStaffMobMisMatchException();
							}
						}else
							throw new ImsDSStaffMobMisMatchException();
					}else if(request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)!=null 
							&&!"".equals((String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM))){
						if("N".equals(imsOrderService.isDsStaffMobbile(user.getUsername(), (String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)))){
							throw new ImsDSStaffMobMisMatchException();
						}
					}				
					}
					return new ModelAndView("dswelcome", "user", dtoUser);
				}else if (13==user.getChannelId()){		
					if(request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)!=null 
							&&!"".equals((String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM))){
					 if("N".equals(imsOrderService.isDsStaffMobbile(user.getUsername(), (String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)))){
							throw new ImsDSStaffMobMisMatchException();
					 }
					}
					return new ModelAndView(new RedirectView(ntvDomain +"ntvdswelcome.html?_al=new"));
				} else {
					throw new ServletException("channel ID "+ user.getChannelId() + " cannot be identified");
				}
			}else if("Y".equalsIgnoreCase(imsOrderService.isDS6User(user.getUsername()))){
				user.setUsername("P"+user.getUsername().substring(1));	
				user = loginService.getSalesAssign(user.getUsername());
				request.getSession().setAttribute("bomsalesuser", user);
				request.setAttribute("maintFuncInfoList", welcomeService.getUsableMaintFuncInfo(user.getChannelId(), user.getChannelCd(), user.getCategory()));
				request.getSession().setAttribute(ImsConstants.IMS_DIRECT_SALES,true);
				if(request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)!=null 
						&&!"".equals((String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM))){
				 if("N".equals(imsOrderService.isDsStaffMobbile(user.getUsername(), (String) request.getSession().getAttribute(ImsConstants.IMS_DS_MOB_NUM)))){
						throw new ImsDSStaffMobMisMatchException();
				 }
				}
				return new ModelAndView("dswelcome", "user", dtoUser);
			}else if( 1 == user.getChannelId() && "NOWTV".equals(lob)){
				return new ModelAndView(new RedirectView(ntvDomain +"ntvorderdetail.html?_al=new"));
			}else if ("true".equals(ConfigProperties.getPropertyByEnvironment("sso.development.bypass"))
					||!StringUtils.isEmpty((String)request.getSession().getAttribute("IMS_SSO_BYPASS"))) {
				return new ModelAndView("welcome", "user", dtoUser);
			} else {
				return new ModelAndView("redirect:/closepopup.jsp");
			}
		}else {
			return new ModelAndView("welcome");
		}
	}
	public void setImsOrderService(ImsOrderService imsOrderService) {
		this.imsOrderService = imsOrderService;
	}
	public ImsOrderService getImsOrderService() {
		return imsOrderService;
	}
	public void setNtvDomain(String ntvDomain) {
		this.ntvDomain = ntvDomain;
	}
	public String getNtvDomain() {
		return ntvDomain;
	}

}
