package com.bomwebportal.ims.web;

//Terrence Part
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.DATE;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.CustomerDTO;
import com.bomwebportal.ims.dto.GetCOrderDTO;
import com.bomwebportal.ims.dto.ImsBlacklistCustInfoDTO;
import com.bomwebportal.ims.dto.ui.CustAddrUI;
import com.bomwebportal.ims.dto.ui.CustOptoutInfoUI;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.COrderService;
import com.bomwebportal.ims.service.GetImsCustomerService;
import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.bomwebportal.ims.service.IsImsBlacklistCustService;
import com.bomwebportal.service.ConstantLkupService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.ext.BomWebPortalApplicationFlow;
import com.google.gson.Gson;

public class ImsInstallationController extends SimpleFormController{

    protected final Log logger = LogFactory.getLog(getClass());
    private Gson gson = new Gson();
    
    private GetImsCustomerService getImsCustomerService;
    private IsImsBlacklistCustService isImsBlacklistCustService;
    private ImsAddressInfoService imsAddressInfoService;
    private COrderService cOrderService;
    private ConstantLkupService constantLkupService;
    
    public COrderService getcOrderService() {
		return cOrderService;
	}

	public void setcOrderService(COrderService cOrderService) {
		this.cOrderService = cOrderService;
	}

	public GetImsCustomerService getGetImsCustomerService() {
		return getImsCustomerService;
	}

	public void setGetImsCustomerService(GetImsCustomerService getImsCustomerService) {
		this.getImsCustomerService = getImsCustomerService;
	}

	public IsImsBlacklistCustService getIsImsBlacklistCustService() {
		return isImsBlacklistCustService;
	}

	public void setIsImsBlacklistCustService(
			IsImsBlacklistCustService isImsBlacklistCustService) {
		this.isImsBlacklistCustService = isImsBlacklistCustService;
	}

	public ImsAddressInfoService getImsAddressInfoService() {
		return imsAddressInfoService;
	}

	public void setImsAddressInfoService(ImsAddressInfoService imsAddressInfoService) {
		this.imsAddressInfoService = imsAddressInfoService;
	}

	public ImsInstallationController(){
    }
    
	private BomWebPortalApplicationFlow appFlow;
    
	public BomWebPortalApplicationFlow getAppFlow() {
		return appFlow;
	}

	public void setAppFlow(
			BomWebPortalApplicationFlow appFlow) {
		this.appFlow = appFlow;
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		logger.info("formBackingObject");
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		// Hardcode PT, CC
			//sessionOrder.setIsPT("Y");
			//sessionOrder.setIsCC("Y");
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, sessionOrder);
		// Hardcode (ends)
			
		logger.info("sessionOrder:" + gson.toJson(sessionOrder));	
		
		
		if(sessionOrder != null) logger.info("ImsOrderId: " + sessionOrder.getOrderId());
		ImsInstallationUI sessionInstallation = new ImsInstallationUI();
		
		BeanUtils.copyProperties(sessionOrder.getCustomer(), sessionInstallation);
		if((sessionOrder.getCustomer().getIdDocType() != null && !("").equals(sessionOrder.getCustomer().getIdDocType()))
				&& (sessionOrder.getCustomer().getIdDocNum() != null && !("").equals(sessionOrder.getCustomer().getIdDocNum()))){
			List<ImsInstallationUI> list = getImsCustomerService.getImsCustomer(sessionOrder.getCustomer().getIdDocType(), sessionOrder.getCustomer().getIdDocNum());
			ImsInstallationUI cust = new ImsInstallationUI();
			
			for(int i=0; i<list.size(); i++){
				cust = list.get(i);
				
//				sessionInstallation.setCustNo(cust.getCustNo());
/*				sessionInstallation.setIdDocType(cust.getIdDocType());
				sessionInstallation.setIdDocNum(cust.getIdDocNum());
				sessionInstallation.setTitle(cust.getTitle());
				sessionInstallation.setLastName(cust.getLastName());
				sessionInstallation.setFirstName(cust.getFirstName());
				sessionInstallation.setDob(cust.getDob());
*/			
			if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){ 
					if(sessionInstallation.getContact().getContactPhone() == null){ //|| sessionInstallation.getContact().getContactPhone().equals("")){
						sessionInstallation.getContact().setContactPhone(cust.getContact().getContactPhone());
					//}else if(sessionOrder.getCustomer().getContact().getContactPhone().equals("")){
					//	sessionInstallation.getContact().setContactPhone("");
					}
					if(sessionInstallation.getContact().getOtherPhone() == null){
						sessionInstallation.getContact().setOtherPhone(cust.getContact().getOtherPhone());
					}
				}
			}
			}
			sessionInstallation.setBlacklistCustInfo(checkBlacklistCust(sessionOrder.getCustomer().getIdDocType(), sessionOrder.getCustomer().getIdDocNum()));
			//ims direct sales
			if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES) && !StringUtils.isEmpty(sessionInstallation.getBlacklistCustInfo())){
				sessionInstallation.setBlacklistCustInfo("BL"); 
			}
		
		sessionInstallation.setInstallAddress(sessionOrder.getInstallAddress());
		sessionInstallation.getInstallAddress().setAddrInventory(sessionOrder.getInstallAddress().getAddrInventory());
		sessionInstallation.setDobStr(Utility.date2String(sessionOrder.getCustomer().getDob(), "dd/MM/yyyy"));
		sessionInstallation.getContact().setContactPhone(sessionOrder.getCustomer().getContact().getContactPhone());
		sessionInstallation.getContact().setOtherPhone(sessionOrder.getCustomer().getContact().getOtherPhone());
		sessionInstallation.setFixedLineNo(sessionOrder.getFixedLineNo());
		sessionInstallation.setLoginId(sessionOrder.getLoginId());
		sessionInstallation.setNowTvFormType(sessionOrder.getNowTvFormType());
		logger.info(sessionInstallation.getLoginId());
		logger.info("sessionInstallation");
		if (sessionOrder.getBillingAddress() == null) {
			sessionInstallation.setBillingAddress(new CustAddrUI());
			sessionInstallation.getBillingAddress().setAddrUsage(ImsConstants.IMS_CUST_BILLING_ADDR);
		} else {
			sessionInstallation.setBillingAddress(sessionOrder.getBillingAddress());
			sessionInstallation.setNoBillingAddress("N");
		}
		if ((sessionOrder.getCustomer().getCustNo() == null || ("").equals(sessionOrder.getCustomer().getCustNo())) 
				&& sessionOrder.getCustomer().getCustOptInfo() == null) {
			sessionInstallation.setCustOptInfo(new CustOptoutInfoUI());
		} else if ((sessionOrder.getCustomer().getCustNo() == null || ("").equals(sessionOrder.getCustomer().getCustNo()))
			&& sessionOrder.getCustomer().getCustOptInfo() != null) {
			sessionInstallation.setCustOptInfo(sessionOrder.getCustomer().getCustOptInfo());
		}else{
			sessionInstallation.setCustOptInfo(new CustOptoutInfoUI());
		}
		
		sessionInstallation.setOrderActionInd(sessionOrder.getOrderActionInd());
		sessionInstallation.setOrderStatus(sessionOrder.getOrderStatus());
		sessionInstallation.setApprovalRequested(sessionOrder.getApprovalRequested());
		sessionInstallation.setIsPT(sessionOrder.getIsPT());
		sessionInstallation.setIsCC(sessionOrder.getIsCC());
		sessionInstallation.setIsDS(sessionOrder.getIsDS()); //Added by Andy
		if("Y".equalsIgnoreCase(sessionOrder.getIsPT())){
			if("R".equalsIgnoreCase(sessionOrder.getMode())){
				sessionInstallation.setMode(sessionOrder.getMode());
			}else{
				sessionInstallation.setMode("C");
			}
		}else{
			sessionInstallation.setMode(null);
		}
		if ( "BS".equals(sessionInstallation.getIdDocType()))
		{
			if (StringUtils.isBlank(sessionInstallation.getContactPersonName()))
			sessionInstallation.setContactPersonName(sessionInstallation.getFirstName());
		}		
		//Gary put CSportal values from DB to session for recall order
		

		logger.debug("sessionOrder.getCustomer(): " + gson.toJson(sessionOrder.getCustomer()));
//		logger.debug("back getHktLoginId: "+sessionOrder.getCustomer().getCsPortalLogin());
//		logger.debug("back getClubLoginId: "+sessionOrder.getCustomer().getTheClubLogin());
//		logger.debug("back getHktClubLoginId: "+sessionOrder.getCustomer().getCsPortalTheClubLogin());
//		logger.debug("back getHktMobileNum: "+sessionOrder.getCustomer().getCsPortalMobile());
//		logger.debug("back getIsRegHKTLoginId: "+sessionOrder.getCustomer().getCsPortalInd());
//		logger.debug("back getIsRegClubLoginId: "+sessionOrder.getCustomer().getTheClubInd());
//		logger.debug("back getIsRegHKTClubLoginId: "+sessionOrder.getCustomer().getCsPortalTheClubInd());

		
		if(sessionOrder.getCustomer().getNoEmailInd()==null||sessionOrder.getCustomer().getNoEmailInd().isEmpty()){
			sessionInstallation.setNoEmailInd("N");
		}
		
		if(sessionOrder.getCustomer().getNoMobileInd()==null||sessionOrder.getCustomer().getNoMobileInd().isEmpty()){
			sessionInstallation.setNoMobileInd("N");
		}
		
		if(sessionOrder.getCustomer().getTheClubInd()!=null&&sessionOrder.getCustomer().getTheClubInd().equalsIgnoreCase("Y")){
			if(sessionOrder.getCustomer().getTheClubLogin().indexOf("@theclub.com.hk")>0){
				sessionInstallation.setNoEmailInd("Y");
			}else{
				sessionInstallation.setNoEmailInd("N");
			}
			if(sessionOrder.getCustomer().getTheClubMoblie().equals("00000000")){
				sessionInstallation.setNoMobileInd("Y");
			}else{
				sessionInstallation.setNoMobileInd("N");
			}
		}else{
			sessionInstallation.setNoEmailInd("N");
			sessionInstallation.setNoMobileInd("N");
		}
		
		if(sessionOrder.getCustomer().getCsPortalOptOutInd()!=null && sessionOrder.getCustomer().getTheClubOptOutInd() !=null){
			if(sessionOrder.getCustomer().getCsPortalOptOutInd().equalsIgnoreCase("Y") && sessionOrder.getCustomer().getTheClubOptOutInd().equalsIgnoreCase("Y")){
				sessionInstallation.setCsPortalTheClubOptOutInd("Y");
			}else if(sessionOrder.getCustomer().getCsPortalOptOutInd().equalsIgnoreCase("N") && sessionOrder.getCustomer().getTheClubOptOutInd().equalsIgnoreCase("N")){
				sessionInstallation.setCsPortalTheClubOptOutInd("N");
			}
		}else{
			sessionInstallation.setCsPortalTheClubOptOutInd("N");
		}
		
		if(sessionOrder.getCustomer().getCsPortalOptOutInd() == null || sessionOrder.getCustomer().getCsPortalOptOutInd().isEmpty()){
			sessionInstallation.setCsPortalOptOutInd("N");
		}
		
		if(sessionOrder.getCustomer().getTheClubOptOutInd() == null || sessionOrder.getCustomer().getTheClubOptOutInd().isEmpty()){
			sessionInstallation.setTheClubOptOutInd("N");
		}
		//handle dummy email/moblie case
		if(sessionInstallation.getNoEmailInd().equalsIgnoreCase("Y")||sessionInstallation.getNoMobileInd().equalsIgnoreCase("Y")){
			if(sessionOrder.getCustomer().getCsPortalInd().equalsIgnoreCase("N")){
				sessionInstallation.setIsRegHKTLoginId(sessionOrder.getCustomer().getTheClubInd());
				sessionInstallation.setHktLoginId(sessionOrder.getCustomer().getTheClubLogin());
				if(sessionOrder.getCustomer().getTheClubOptOutInd().equalsIgnoreCase("Y")){
					sessionInstallation.setCsPortalTheClubOptOutInd("Y");
				}else if(sessionOrder.getCustomer().getTheClubOptOutInd().equalsIgnoreCase("N")){
					sessionInstallation.setCsPortalTheClubOptOutInd("N");
				}
			}
		}else{
			sessionInstallation.setIsRegHKTLoginId(sessionOrder.getCustomer().getCsPortalInd());
			sessionInstallation.setHktLoginId(sessionOrder.getCustomer().getCsPortalLogin());
		}
		
		sessionInstallation.setIsRegHKTLoginId(sessionOrder.getCustomer().getCsPortalInd());
		sessionInstallation.setHktLoginId(sessionOrder.getCustomer().getCsPortalLogin());
		if("Y".equals(sessionOrder.getCustomer().getTheClubInd())){
			sessionInstallation.setHktMobileNum(sessionOrder.getCustomer().getTheClubMoblie());
		}else{
			sessionInstallation.setHktMobileNum(sessionOrder.getCustomer().getCsPortalMobile());
		}
		
		sessionInstallation.setIsRegClubLoginId(sessionOrder.getCustomer().getTheClubInd());
		sessionInstallation.setClubLoginId(sessionOrder.getCustomer().getTheClubLogin());
		
		if("A".equals(sessionOrder.getCustomer().getCsPortalInd())&&"A".equals(sessionOrder.getCustomer().getTheClubInd())){
			sessionInstallation.setHktClubLoginId("");
			sessionInstallation.setIsRegHKTClubLoginId("A");
		}else if("A".equals(sessionOrder.getCustomer().getCsPortalInd())){
			sessionInstallation.setHktClubLoginId("");
			sessionInstallation.setIsRegHKTClubLoginId("");
		}else if("A".equals(sessionOrder.getCustomer().getTheClubInd())){
			sessionInstallation.setHktClubLoginId("");
			sessionInstallation.setIsRegHKTClubLoginId("");
		}else{
			sessionInstallation.setHktClubLoginId(sessionOrder.getCustomer().getTheClubLogin());
			sessionInstallation.setIsRegHKTClubLoginId("");
		}

		String clubCutOffDateStr = constantLkupService.getImsClubCutOff();
		String careCashVisitDate = constantLkupService.getCareCashVisitDate();
		String careCashShowInd   = constantLkupService.getCareCashShowInd();
		logger.info("clubCutOffDate: "+clubCutOffDateStr);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		Date clubCutOffDate = new Date();
		try {
			clubCutOffDate = formatter.parse(clubCutOffDateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Date appDate = null;
		
		if(sessionOrder.getAppDate()!=null){
			appDate = sessionOrder.getAppDate();
		}else{
			appDate = new Date();
		}
		logger.info("appDate: "+appDate);

		String clubCutOff = "Y";

	    if(appDate.before(clubCutOffDate)){
	    	clubCutOff = "N";
        }
		
		if(clubCutOff.equalsIgnoreCase("N")){
			if("N".equals(sessionOrder.getCustomer().getCsPortalInd())&&"N".equals(sessionOrder.getCustomer().getTheClubInd())){
				sessionInstallation.setIsRegHKTClubLoginId("N");
			}
			if("Y".equals(sessionOrder.getCustomer().getCsPortalInd())&&"Y".equals(sessionOrder.getCustomer().getTheClubInd())){
				sessionInstallation.setIsRegHKTClubLoginId("Y");
			}
		}
		
		Entry<String, String>[] optoutTheClubList;
		
		optoutTheClubList = LookupTableBean.getInstance().getImsClubOptoutLookupMap().entrySet().toArray(new Entry[0]);

		request.setAttribute("optoutTheClubList", optoutTheClubList);
		
		request.getSession().setAttribute("clubCutOff", clubCutOff);
		
		request.getSession().setAttribute("careCashVisitDate", careCashVisitDate);
		request.getSession().setAttribute("careCashShowInd", careCashShowInd);
		
		request.getSession().setAttribute("mobileOfferInd", sessionOrder.getMobileOfferInd());
		
//		logger.debug("back getHktLoginId: "+sessionInstallation.getHktLoginId());
//		logger.debug("back getClubLoginId: "+sessionInstallation.getClubLoginId());
//		logger.debug("back getHktClubLoginId: "+sessionInstallation.getHktClubLoginId());
//		logger.debug("back getHktMobileNum: "+sessionInstallation.getHktMobileNum());
//		logger.debug("back getNoEmailInd: "+sessionInstallation.getNoEmailInd());
//		logger.debug("back getNoMobileInd: "+sessionInstallation.getNoMobileInd());
//		logger.debug("back getIsRegHKTLoginId: "+sessionInstallation.getIsRegHKTLoginId());
//		logger.debug("back getIsRegClubLoginId: "+sessionInstallation.getIsRegClubLoginId());
//		logger.debug("back getIsRegHKTClubLoginId: "+sessionInstallation.getIsRegHKTClubLoginId());
//		logger.debug("back CsPortalTheClubOptoutInd: "+sessionInstallation.getCsPortalTheClubOptOutInd());
//		logger.debug("back CsPortalOptoutInd: "+sessionInstallation.getCsPortalOptOutInd());
//		logger.debug("back TheCluboptoutInd: "+sessionInstallation.getTheClubOptOutInd());
		
		
		//Gary end

		//added by steven 20140207 start
//		if(sessionOrder.getCustomer().getIdDocType()!=null && sessionOrder.getCustomer().getIdDocNum()!=null){
//			sessionOrder.setHasBBDailup(getImsCustomerService.checkImsCustomer(sessionOrder.getCustomer().getIdDocType(),sessionOrder.getCustomer().getIdDocNum()));
//		}
		//added by steven 20140207 end

		sessionInstallation.setHasBBDailUp(sessionOrder.getHasBBDailup());
		
		if(sessionInstallation != null && sessionInstallation.getBillingAddress() != null){
			logger.debug("BillingAddress: " + gson.toJson(sessionInstallation.getBillingAddress()));
		}
		
		
		//IMS Direct Sales
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			sessionInstallation.setExistingCustomerConflictWithName(sessionOrder.getCustomer().getExistingCustomerConflictWithName()); 
			if (request.getSession().getAttribute("imsCustomer") == null && sessionInstallation.getCustNo() !=null) 
				request.getSession().setAttribute("imsCustomer", sessionInstallation);
		}
		//IMS Direct Sales (end)
		
		if(sessionOrder.getOptInInd()!=null){
			sessionInstallation.setOptInInd(sessionOrder.getOptInInd());
		}
		
		logger.info("optin ind in installation "+sessionInstallation.getOptInInd());
		logger.info("isRegNowId back: "+sessionOrder.getCustomer().getIsRegNowId());
		
		//***NowID***
		//check if any NowTV is selected
		if(sessionOrder.getProcessVim()!=null&&
				(sessionOrder.getProcessVim().equalsIgnoreCase("P")||sessionOrder.getProcessVim().equalsIgnoreCase("B"))){			
			
			sessionInstallation.setIsValidForNowId("Y");
			sessionInstallation.setIsRegNowId(sessionOrder.getCustomer().getIsRegNowId());
			if(sessionOrder.getCustomer().getIsRegNowId()== null)
				sessionInstallation.setIsRegNowId("Y");
			
		}else{
			sessionInstallation.setIsValidForNowId("N");	
			sessionOrder.getCustomer().setIsRegNowId("X");	
			sessionOrder.getCustomer().setNowId(null);			
		}
		//***end NowID***
		
		return sessionInstallation;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {		
		logger.info("onSubmit is called");		
		
		ImsInstallationUI customer = (ImsInstallationUI)command;		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		System.out.println(gson.toJson(sessionOrder));
			
		//Gary put CS portal values in session into DTO for later update or insert to DB

			String clubCutOff = (String) request.getSession().getAttribute("clubCutOff");
			
			customer.setCsPortalInd(customer.getIsRegHKTLoginId());	
			customer.setTheClubInd(customer.getIsRegClubLoginId());
			customer.setCsPortalTheClubInd(customer.getIsRegHKTClubLoginId());
			
			if(customer.getIsRegHKTClubLoginId().equalsIgnoreCase("A")){
				customer.setCsPortalInd("A");
				customer.setTheClubInd("A");
				customer.setCsPortalLogin("");
				customer.setTheClubLogin("");
				customer.setCsPortalTheClubLogin("");
				customer.setCsPortalMobile("");
				customer.setTheClubMoblie("");
				customer.setCsPortalOptOutInd("");
				customer.setTheClubOptOutInd("");
				customer.setOptoutTheClubReason("");
				customer.setOptoutTheClubRmk("");
			}else if(customer.getIsRegHKTLoginId().equalsIgnoreCase("A")){
				customer.setCsPortalLogin("");
				customer.setTheClubLogin(customer.getClubLoginId());
				customer.setCsPortalTheClubLogin("");
				customer.setCsPortalMobile("");
				customer.setTheClubMoblie(customer.getHktMobileNum());
				customer.setCsPortalOptOutInd("");
				
				if(clubCutOff.equalsIgnoreCase("N")){
					if(customer.getIsRegClubLoginId().equalsIgnoreCase("Y")){
						customer.setTheClubLogin(customer.getClubLoginId());
						customer.setTheClubMoblie(customer.getHktMobileNum());
						customer.setTheClubInd("Y");
					}
					if(customer.getIsRegClubLoginId().equalsIgnoreCase("N")){
						customer.setTheClubLogin("");
						customer.setCsPortalTheClubLogin("");
						customer.setTheClubMoblie("");
						customer.setTheClubInd("N");
					}
				}
			}else if(customer.getIsRegClubLoginId().equalsIgnoreCase("A")){
				if(customer.getIsRegHKTLoginId().equalsIgnoreCase("Y")){
					customer.setCsPortalLogin(customer.getHktLoginId());
					customer.setCsPortalMobile(customer.getHktMobileNum());
				}else{
					customer.setCsPortalLogin("");
					customer.setCsPortalMobile("");
					customer.setCsPortalOptOutInd("");
				}
				customer.setTheClubLogin("");
				customer.setCsPortalTheClubLogin("");
				customer.setTheClubMoblie("");
				customer.setTheClubOptOutInd("");
				customer.setOptoutTheClubReason("");
				customer.setOptoutTheClubRmk("");
			}else{
				if(customer.getIsRegHKTClubLoginId().equalsIgnoreCase("X")){
					customer.setCsPortalLogin("");
					customer.setTheClubLogin("");
					customer.setCsPortalTheClubLogin("");
					customer.setCsPortalMobile("");
					customer.setTheClubMoblie("");
					customer.setCsPortalTheClubOptOutInd("");
					customer.setCsPortalOptOutInd("");
					customer.setTheClubOptOutInd("");
					customer.setOptoutTheClubReason("");
					customer.setOptoutTheClubRmk("");
					customer.setCsPortalInd("X");
					customer.setTheClubInd("X");
				}else{
					customer.setCsPortalLogin(customer.getHktClubLoginId());
					customer.setTheClubLogin(customer.getHktClubLoginId());
					customer.setCsPortalTheClubLogin(customer.getHktClubLoginId());
					customer.setCsPortalMobile(customer.getHktMobileNum());
					customer.setTheClubMoblie(customer.getHktMobileNum());
				}
				
				if(clubCutOff.equalsIgnoreCase("N")){
					if(customer.getIsRegHKTClubLoginId().equalsIgnoreCase("Y")){
						customer.setCsPortalLogin(customer.getHktClubLoginId());
						customer.setTheClubLogin(customer.getHktClubLoginId());
						customer.setCsPortalMobile(customer.getHktMobileNum());
						customer.setTheClubMoblie(customer.getHktMobileNum());
						customer.setCsPortalInd("Y");
						customer.setTheClubInd("Y");
					}
					if(customer.getIsRegHKTClubLoginId().equalsIgnoreCase("N")){
						customer.setCsPortalLogin("");
						customer.setTheClubLogin("");
						customer.setCsPortalMobile("");
						customer.setCsPortalTheClubLogin("");
						customer.setTheClubMoblie("");
						customer.setCsPortalInd("N");
						customer.setTheClubInd("N");
					}
				}
			}
			
			if(customer.getCsPortalTheClubOptOutInd()!=null){
				if(customer.getCsPortalTheClubOptOutInd().equalsIgnoreCase("Y")){
					customer.setCsPortalOptOutInd("Y");
					customer.setTheClubOptOutInd("Y");
				}else if(customer.getCsPortalTheClubOptOutInd().equalsIgnoreCase("N")){
					customer.setCsPortalOptOutInd("N");
					customer.setTheClubOptOutInd("N");
					customer.setOptoutTheClubReason("");
					customer.setOptoutTheClubRmk("");
				}
			}
			
			if(customer.getTheClubOptOutInd()!=null&&customer.getTheClubOptOutInd().equalsIgnoreCase("N")){
				customer.setOptoutTheClubReason("");
				customer.setOptoutTheClubRmk("");
			}
			
			if(clubCutOff.equalsIgnoreCase("N")){
				if(customer.getIsRegHKTClubLoginId().equalsIgnoreCase("N")){
					customer.setCsPortalOptOutInd("");
					customer.setTheClubOptOutInd("");
					customer.setOptoutTheClubReason("");
					customer.setOptoutTheClubRmk("");
				}
				if(customer.getIsRegClubLoginId().equalsIgnoreCase("N")){
					customer.setTheClubOptOutInd("");
					customer.setOptoutTheClubReason("");
					customer.setOptoutTheClubRmk("");
				}
			}
			//handle dummy email/mobile case
			if(customer.getNoEmailInd()!=null&&customer.getNoEmailInd().equalsIgnoreCase("Y")
					||customer.getNoMobileInd()!=null&&customer.getNoMobileInd().equalsIgnoreCase("Y")){
				if(customer.getCsPortalInd().equalsIgnoreCase("Y")){
					customer.setCsPortalInd("N");
					customer.setCsPortalOptOutInd("");
					customer.setCsPortalLogin("");
					customer.setCsPortalMobile("");
				}
			}

//			logger.debug("customer: " + gson.toJson(customer));
//			logger.debug("CsPortalInd: "+customer.getCsPortalInd());
//			logger.debug("TheClubInd: "+customer.getTheClubInd());
//			logger.debug("CsPortalTheClubInd: "+customer.getCsPortalTheClubInd());
//			logger.debug("CsPortalLogin: "+customer.getCsPortalLogin());
//			logger.debug("TheClubLogin: "+customer.getTheClubLogin());
//			logger.debug("CsPortalTheClubLogin: "+customer.getCsPortalTheClubLogin());
//			logger.debug("CsPortalMobile: "+customer.getCsPortalMobile());
//			logger.debug("TheClubMobile: "+customer.getTheClubMoblie());
//			logger.debug("CsPortalTheClubOptoutInd: "+customer.getCsPortalTheClubOptOutInd());
//			logger.debug("CsPortalOptoutInd: "+customer.getCsPortalOptOutInd());
//			logger.debug("TheCluboptoutInd: "+customer.getTheClubOptOutInd());
		
		//Gary end
			
		if(StringUtils.isNotBlank(customer.getCareCashInd())&& !customer.getCareCashInd().equalsIgnoreCase("I")){
			customer.setCareCashEmail("");
			customer.setCareCashMobile("");
		}					
		
		if(customer != null && customer.getBillingAddress() != null){
			logger.debug("BillingAddress: " + gson.toJson(customer.getBillingAddress()));
		}
		
		if(("").equals(customer.getBillingQuickSearch()) && (customer.getBillingAddress().getFloorNo() != null && !("").equals(customer.getBillingAddress().getFloorNo()))){
			customer.setBillingQuickSearch("");
		}else if(("").equals(customer.getBillingQuickSearch())){
			customer.setBillingQuickSearch(null);
		}
		if(customer.getNoBillingAddress().equals("Y")){
			customer.setBillingAddress(null);
			logger.info("Y1: " + sessionOrder.getBillingAddress());
			sessionOrder.setBillingAddress(null);
		}else if(customer.getBillingAddress() != null){
			sessionOrder.setBillingAddress(customer.getBillingAddress());
		}
		if(customer.getCustNo() != null && !("").equals(customer.getCustNo())){
			logger.info(customer.getCustNo());
		}
		if("Y".equalsIgnoreCase(customer.getHasBBDailUp())){
			customer.setCustOptInfo(null);
		}
		sessionOrder.setHasBBDailup(customer.getHasBBDailUp());
		boolean isImsBlacklist = isImsBlacklistCustService.isImsBlacklistCust(customer.getIdDocType(), customer.getIdDocNum());
		if(isImsBlacklist == true){
			customer.setBlacklistInd("Y");
		}else{
			customer.setBlacklistInd("N");
		}
		
		//eSignature CR by Eric Ng @2012-10-24
		logger.info("Setting Contact Name to ContactUI: "+ customer.getLastName()+ " " + customer.getFirstName());
		customer.getContact().setContactName(customer.getLastName()+ " " + customer.getFirstName());
		logger.info("Setting Title to Contact UI: "+ customer.getTitle());
		customer.getContact().setTitle(customer.getTitle());
		customer.getContact().setContactType("CC");
		
		if ( "Y".equals(sessionOrder.getIsCC()) && !"R".equalsIgnoreCase(customer.getMode()))
		{
			customer.setIdVerified("N");
		}
		
		if ( "BS".equals(customer.getIdDocType()))
		{
			customer.setFirstName(customer.getContactPersonName());
			customer.setLastName("");
		}
		
		sessionOrder.setCustomer(customer);
		logger.info("Y2: " + sessionOrder.getBillingAddress());
		sessionOrder.setFixedLineNo(customer.getFixedLineNo());
//		if(!customer.getLoginId().equals(sessionOrder.getLoginId())) {
//			 
//		}
		sessionOrder.setMode(customer.getMode());
		
		logger.info("isRegNowId: "+customer.getIsRegNowId());		
		sessionOrder.getCustomer().setIsRegNowId(customer.getIsRegNowId());
		if(customer.getIsRegNowId().equalsIgnoreCase("Y") && customer.getNowId()!=null){
			sessionOrder.getCustomer().setNowId(customer.getNowId());
		}
		
		
		if(sessionOrder.getLoginId()!=null && (sessionOrder.getLoginId()+ "@netvigator.com").equals(sessionOrder.getCustomer().getAccount().getEmailAddr())){  
			sessionOrder.getCustomer().getAccount().setEmailAddr(customer.getLoginId() + "@netvigator.com");  
		}
		
		sessionOrder.setLoginId(customer.getLoginId());
		logger.info("Login ID: "+sessionOrder.getLoginId());
		//sessionOrder.getCustomer().getAccount().setEmailAddr(customer.getLoginId()+"@netvigator.com");
		sessionOrder.getCustomer().setDob(Utility.string2Date(customer.getDobStr()));
		System.out.println(gson.toJson(customer));
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, sessionOrder);
		
		return new ModelAndView(new RedirectView(getSuccessView()));		
	}
	
	public String checkBlacklistCust(String idDocType, String idDocNum) {

		List<ImsBlacklistCustInfoDTO> blCustInfoList = new ArrayList<ImsBlacklistCustInfoDTO>();
		String blCustInfoTxt;
		String blCustWarningMsg = "";
		
		boolean isImsBlacklist = isImsBlacklistCustService.isImsBlacklistCust(idDocType, idDocNum);
		if(isImsBlacklist == true) {
			blCustWarningMsg = "Blacklist Customer<br>";

			List<String> acctNum;
			String osAmt;
			acctNum = isImsBlacklistCustService.getImsOsBalanceAcct(idDocType, idDocNum);
			for(int j=0; j<acctNum.size(); j++){
				osAmt = isImsBlacklistCustService.getImsOsBalance(acctNum.get(j));
				blCustInfoTxt = "Account No.: " + acctNum.get(j) + " Outstanding Balance: " + osAmt + "<br>";
				blCustWarningMsg += blCustInfoTxt;
			}
		}
		
		logger.debug("Warning msg =" + blCustWarningMsg);
		return blCustWarningMsg;
	}

	public void setConstantLkupService(ConstantLkupService constantLkupService) {
		this.constantLkupService = constantLkupService;
	}

	public ConstantLkupService getConstantLkupService() {
		return constantLkupService;
	}
}
