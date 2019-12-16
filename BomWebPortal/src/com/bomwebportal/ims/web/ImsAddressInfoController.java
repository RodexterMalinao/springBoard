package com.bomwebportal.ims.web;

import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustomerBasicInfoDTO;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.ims.dto.AddrInventoryDTO;
import com.bomwebportal.ims.dto.CustomerDTO;
import com.bomwebportal.ims.dto.ImsBlacklistCustInfoDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.dto.ui.AddressInfoUI;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.ContactUI;
import com.bomwebportal.ims.dto.ui.CustomerUI;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.service.CCSalesInfoService;
import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.bomwebportal.service.ConstantLkupService;
import com.bomwebportal.util.Utility;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.*;

import com.bomwebportal.ims.dto.ImsAddressDTO;
import com.google.gson.Gson;

public class ImsAddressInfoController extends SimpleFormController{
	protected final Log logger = LogFactory.getLog(getClass());
	
    private ImsAddressInfoService service;
    private ConstantLkupService constantLkupService;
    private String blacklistCustInd;
    private String prevSbNo;
	private Gson gson = new Gson();
    
	public void setService(ImsAddressInfoService service) {
		this.service = service;
	}
	public ImsAddressInfoService getService() {
		return service;
	}
	
	public ConstantLkupService getConstantLkupService() {
		return constantLkupService;
	}
	public void setConstantLkupService(ConstantLkupService constantLkupService) {
		this.constantLkupService = constantLkupService;
	}
	
	public ImsAddressInfoController() {
	}
	
	public String checkBlacklistCust(String idDocType, String idDocNum) {

		List<ImsBlacklistCustInfoDTO> blCustInfoList = new ArrayList<ImsBlacklistCustInfoDTO>();
		String blCustInfoTxt;
		String blCustWarningMsg = "";
		
		blacklistCustInd = service.isBlacklistCust(idDocType, idDocNum);
		if (blacklistCustInd.equals("Y")) {
			blCustWarningMsg = "<p> Blacklist Customer</p>";
			blCustInfoList = service.getBlacklistCustInfo(idDocType, idDocNum);
			for (int i=0; i < blCustInfoList.size(); i++) {
				blCustInfoTxt = "<p> account no=" + blCustInfoList.get(i).getAcctNo() + "; outstanding amount=" + blCustInfoList.get(i).getCurOsAmt() + "</p>";
				blCustWarningMsg += blCustInfoTxt;
			}
		}
		
		logger.debug("Warning msg =" + blCustWarningMsg);
		return blCustWarningMsg;

	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		Locale locale = RequestContextUtils.getLocale(request);
		logger.info("user:" + gson.toJson(user));
		if(user.getChannelId()==1){
			if(!service.isShopCodeValid(user.getBomShopCd())){
				request.getSession().setAttribute("isShopCodeValid", "N");
				request.getSession().setAttribute("inValidShopCode", user.getBomShopCd());
			}else{
				request.getSession().setAttribute("isShopCodeValid", "Y");
			}
		}else{
			request.getSession().setAttribute("isShopCodeValid", "Y");
		}
		request.getSession().setAttribute("isShopCodeValid", "Y");//20150312 fallback
		
		order.setIsCC(user.getChannelId()==2||user.getChannelId()==3?"Y":"N");
		order.setIsPT(user.getChannelId()==3?"Y":"N");
		order.setIsDS("DS".equals(order.getImsOrderType())?"Y":"N");//added by Andy
		order.setChannelCd(user.getChannelCd());
		//order.setSalesTeam(user.get)
		if(request.getSession().getAttribute(ImsConstants.IMS_ORDER) != null) {
			BomSalesUserDTO sales = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
			if (sales.getSalesType() != null)
				order.setDsType(sales.getSalesType());
			if (sales.getLocation()!= null)
				order.setDsLocation(sales.getLocation());
		}
		
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
		
		if (request.getSession().getAttribute("custSearchResultListSession") == null) {
//			logger.debug("No Customer record found");
			// use hardcode customer in DevService.getNewOrder()
		} else {
			List<CustomerBasicInfoDTO> customerList = (List<CustomerBasicInfoDTO>) request.getSession().getAttribute("custSearchResultListSession");
//			logger.debug(customerList.size() + " Customer record found");
			
			// order.CustomerUI
			if (order.getCustomer() == null) {
				CustomerUI imsCust = new CustomerUI();
				order.setCustomer(imsCust);
			}
			
			for (int i=0; i < customerList.size(); i++) {
				if (customerList.get(i).getSystemId().equalsIgnoreCase("IMS")) {
					order.getCustomer().setIdDocType(customerList.get(i).getIdDocType());
					order.getCustomer().setIdDocNum(customerList.get(i).getIdDocNum());
					order.getCustomer().setIdVerified(customerList.get(i).getIdVerifyInd());
					order.getCustomer().setTitle(customerList.get(i).getTitle());
					order.getCustomer().setLastName(customerList.get(i).getLastName());
					order.getCustomer().setFirstName(customerList.get(i).getFirstName());
					if (customerList.get(i).getDob() != null) {
						order.getCustomer().setDob(Utility.string2Date(customerList.get(i).getDob()));
					}
					order.getCustomer().setLob(customerList.get(i).getSystemId());
					
					if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){ 
						if (customerList.get(i).getDob() != null) {
							order.getCustomer().setDob(Utility.string2Date(customerList.get(i).getDob()));
						}
						order.getCustomer().setIdVerified(customerList.get(i).getIdVerifyInd());
						order.getCustomer().setTitle(customerList.get(i).getTitle());
					}
				}
			}
			
			// order.ContactUI
			if (order.getCustomer().getContact() == null) {
				ContactUI imsContact = new ContactUI();
				order.getCustomer().setContact(imsContact);
			}
			
		}
			
		AddressInfoUI addrInfo = new AddressInfoUI();
		addrInfo.setCustomer(order.getCustomer());
		if (order.getInstallAddress() == null) {
			logger.debug("No install address info");
			prevSbNo = "";
			addrInfo.setPrevSbNo(prevSbNo);
		} else {
			logger.debug("Get install address info" + order.getInstallAddress().getSerbdyno());
			prevSbNo = order.getInstallAddress().getSerbdyno();
			addrInfo.setPrevSbNo(prevSbNo);
			addrInfo.setPrevFloor(order.getInstallAddress().getFloorNo());
			addrInfo.setPrevFlat(order.getInstallAddress().getFlat());
			
			addrInfo.setPhInd("N");
			if (order.getInstallAddress().getHousingTypeList() != null) {
				for (int i=0;i < order.getInstallAddress().getHousingTypeList().size(); i++) {
					if (order.getInstallAddress().getHousingTypeList().get(i).getHousingType().substring(0,2).equalsIgnoreCase("PH")) {
						addrInfo.setPhInd("Y");
					}
				}
			}
			
			order.getInstallAddress().setSbFilterVas(service.checkSpecialSBFilterVas(prevSbNo));
			
			addrInfo.setInstallAddress(order.getInstallAddress());
			if (order.getOrderActionInd() != null && order.getOrderActionInd().equalsIgnoreCase("W")) {  // recall order
				ImsAddressDTO address = new ImsAddressDTO();
				address.setFlat(order.getInstallAddress().getUnitNo());
				address.setFloor(order.getInstallAddress().getFloorNo());
				address.setBuildingName(order.getInstallAddress().getBuildNo());
				address.setLotNum(order.getInstallAddress().getHiLotNo());
				address.setHouseLotNum(order.getInstallAddress().getStrNo());				
				address.setStreetName(order.getInstallAddress().getStrName());
				address.setStreetCatgDesc(order.getInstallAddress().getStrCatDesc());
				address.setSectionDesc(order.getInstallAddress().getSectDesc());
				address.setDistrictDesc(order.getInstallAddress().getDistDesc());
				address.setAreaDesc(order.getInstallAddress().getAreaDesc());
				addrInfo.getInstallAddress().setQuickSearch(address.getSingleLineAddress());
			}
		}

		logger.debug("LOB:" + addrInfo.getCustomer().getLob());
		logger.debug("Customer Name:" + order.getCustomer().getLastName() + ", " + order.getCustomer().getFirstName());
		
		if (addrInfo.getCustomer().getLob() != null && addrInfo.getCustomer().getLob().equalsIgnoreCase("IMS")) {
			logger.debug("IMS customer, Check blacklist Cust Info");
			addrInfo.setBlacklistCustInfo(checkBlacklistCust(order.getCustomer().getIdDocType(), order.getCustomer().getIdDocNum()));
			addrInfo.getCustomer().setBlacklistInd(blacklistCustInd);
		} else {
			logger.debug("No need to check blacklist Cust Info");
			addrInfo.getCustomer().setBlacklistInd("N");
		}
		
		addrInfo.setDobStr(Utility.date2String(addrInfo.getCustomer().getDob(), "yyyy/MM/dd"));
		
		if (order.getInstallAddress()== null) {
			InstallAddrUI installAddr = new InstallAddrUI();
			addrInfo.setInstallAddress(installAddr);
		} else {
			addrInfo.setInstallAddress(order.getInstallAddress());
		}
		
		addrInfo.setApprovalRequested(order.getApprovalRequested());
		
		//ims direct sales
		if((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES) && !StringUtils.isEmpty(addrInfo.getBlacklistCustInfo())){
			addrInfo.setBlacklistCustInfo("BL");
		}
		if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
			try {
				addrInfo.setCslOfferEnableInd(service.getCslOfferEnableInd(user.getUsername()));
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		if(addrInfo.getCustomer().getMobileCutomerInfo()== null){
			CustomerDTO cslCustInfo = new CustomerDTO();
			addrInfo.getCustomer().setMobileCutomerInfo(cslCustInfo);			
		}
		if(StringUtils.isEmpty(order.getMobileOfferInd())){
			order.setMobileOfferInd("N");
		}
		if("Y".equalsIgnoreCase(order.getMobileOfferInd())){
			addrInfo.getCustomer().getMobileCutomerInfo().setIdDocType(order.getCustomer().getIdDocType());
			addrInfo.getCustomer().getMobileCutomerInfo().setIdDocNum(order.getCustomer().getIdDocNum());
			addrInfo.getCustomer().getMobileCutomerInfo().setLastName(order.getCustomer().getLastName());
			addrInfo.getCustomer().getMobileCutomerInfo().setFirstName(order.getCustomer().getFirstName());
		}
		logger.info("order celia:"+gson.toJson(order));
		request.setAttribute("language", locale.toString().toUpperCase());
		
		Map<String, Map<String,String>> imsFrontendAlertMsg = 
			(Map<String, Map<String,String>>) request.getSession().getAttribute(ImsConstants.IMS_FRONTEND_ALERT_MSG);
		if (imsFrontendAlertMsg == null || imsFrontendAlertMsg.size() == 0) {
			imsFrontendAlertMsg = constantLkupService.getImsServPlanStaticReportWords("ims/BomWebPortal/SectionJ%", "servPlan%");
			request.getSession().setAttribute(ImsConstants.IMS_FRONTEND_ALERT_MSG, imsFrontendAlertMsg);
		}

		return addrInfo;
	}
	
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException, AppRuntimeException {
		
			String nextView = "imsbasketselect.html";
			logger.info("Next View: " + nextView);
			
			AddressInfoUI addrInfo = (AddressInfoUI)command;
			
			OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
			
			order.getCustomer().setBlacklistInd(blacklistCustInd);
			
			String h264Ind = null;
			
			String adsl18mInd = null;

			String ltsServiceInd = "N";
			String bypassLtsSrvChecking = "N";
			
			bypassLtsSrvChecking = service.getBypassLtsCheckInd();
			
			h264Ind = service.getH264Ind(addrInfo.getInstallAddress().getSerbdyno());
			adsl18mInd = service.getAdsl18mInd(addrInfo.getInstallAddress().getSerbdyno());
			if(!"Y".equalsIgnoreCase(bypassLtsSrvChecking)){
				ltsServiceInd = service.withDelCheckAddr(addrInfo.getInstallAddress().getSerbdyno(),addrInfo.getInstallAddress().getUnitNo(),addrInfo.getInstallAddress().getFloorNo());
			}
				
			order.setLtsServiceInd(ltsServiceInd);
			
			if (prevSbNo == null || prevSbNo.isEmpty() || (
					addrInfo.getInstallAddress().getSerbdyno().equalsIgnoreCase(prevSbNo)
					&& (
							order.getInstallAddress().getFloorNo()!=null?
								order.getInstallAddress().getFloorNo().equalsIgnoreCase(addrInfo.getPrevFloor()):
									order.getInstallAddress().getFloorNo()==null || order.getInstallAddress().getFloorNo().isEmpty()
						)
					&& (  order.getInstallAddress().getFlat()!=null?
							order.getInstallAddress().getFlat().equalsIgnoreCase(addrInfo.getPrevSbNo()):
								order.getInstallAddress().getFlat()==null || order.getInstallAddress().getFlat().isEmpty()	)   
						)
			) {
				logger.debug("Same SB No");
			} else {
				logger.debug("Change SB No");
				order.setSubscribedItems(null);
				order.setSubscribedChannels(null);
				order.setComponents(null);
				request.getSession().setAttribute("selectedIMSVasList", null);
				request.getSession().setAttribute("selectedIMSChannelList", null);
				request.getSession().setAttribute("selectedIMSNowVasList", null);
				request.getSession().setAttribute("IMS_IsCouponBasket", null);
				request.getSession().setAttribute("IMS_ContractPeriod", null);	
				request.getSession().setAttribute("IMS_BasketID", null);
				request.getSession().setAttribute("IMSTVType", null);
				request.getSession().setAttribute("type30F6", null);
				request.getSession().setAttribute(ImsConstants.IMS_CUST_SIGN, null);// make all sign null
				request.getSession().setAttribute(ImsConstants.IMS_CREDIT_CARD_SIGN, null);// make all sign null
				request.getSession().setAttribute(ImsConstants.IMS_3_PARTY_SIGN, null);// make all sign null
				request.getSession().setAttribute(ImsConstants.IMS_TDO_MOOV_SIGN, null);// make all sign null
				request.getSession().setAttribute(ImsConstants.IMS_TDO_TV_SIGN, null);// make all sign null
				request.getSession().setAttribute(ImsConstants.IMS_TDO_PCD_SIGN, null);// make all sign null
				//order.setBillingAddress(null);
				AppointmentUI appointment = new AppointmentUI();
				appointment.setActionInd("A");
				order.setAppointment(appointment);
			}

			// below value are updated in ImsAddressInfoLookUpController and put into session
			AddressInfoUI tmpAddrInfo = (AddressInfoUI) request.getSession().getAttribute("IMS_AddressInfotmp");
			logger.info("addrInfo:" + gson.toJson(addrInfo));
			logger.info("tmpAddrInfo:" + gson.toJson(tmpAddrInfo));
			addrInfo.getInstallAddress().setBandwidthList(tmpAddrInfo.getInstallAddress().getBandwidthList());
			addrInfo.getInstallAddress().setHousingTypeList(tmpAddrInfo.getInstallAddress().getHousingTypeList());
			addrInfo.getInstallAddress().setServiceDetailList(tmpAddrInfo.getInstallAddress().getServiceDetailList());
			if (addrInfo.getInstallAddress().getAddrInventory() == null) {
				AddrInventoryDTO addrInventory = new AddrInventoryDTO();
				addrInventory.setN2BuildingInd(tmpAddrInfo.getInstallAddress().getAddrInventory().getN2BuildingInd());
				addrInventory.setBuildingType(addrInfo.getInstallAddress().getHousingTypeList().get(0).getHousingType());	//jacky 20141127
				addrInventory.setH264Ind(h264Ind);
				addrInventory.setAdsl18MInd(adsl18mInd);
				addrInfo.getInstallAddress().setAddrInventory(addrInventory);
			} else {
				addrInfo.getInstallAddress().getAddrInventory().setN2BuildingInd(tmpAddrInfo.getInstallAddress().getAddrInventory().getN2BuildingInd());
				addrInfo.getInstallAddress().getAddrInventory().setBuildingType(addrInfo.getInstallAddress().getHousingTypeList().get(0).getHousingType());	//jacky 20141127
				addrInfo.getInstallAddress().getAddrInventory().setH264Ind(h264Ind);
				addrInfo.getInstallAddress().getAddrInventory().setAdsl18MInd(adsl18mInd);
				addrInfo.getInstallAddress().getAddrInventory().setN2BuildingInd(tmpAddrInfo.getInstallAddress().getAddrInventory().getN2BuildingInd());
			}

			String isSupreme = "N";
			isSupreme = service.isSupremeHousingType(addrInfo.getInstallAddress().getAddrInventory().getBuildingType());
			order.setSupremeFsInd(isSupreme);
			
			order.setInstallAddress(addrInfo.getInstallAddress());
			//order.setOtChrgType(addrInfo.getOtChrgType());
//			order.setPonOTChrgType(addrInfo.getPonOTChrgType());
//			order.setAdslOTChrgType(addrInfo.getAdslOTChrgType());
//			order.setVdslOTChrgType(addrInfo.getVdslOTChrgType());
//			order.setVectorOTChrgType(addrInfo.getVectorOTChrgType());
			
			logger.debug("first name===" + order.getCustomer().getFirstName());
			logger.debug("building name ===" + order.getInstallAddress().getBuildNo());
			logger.debug("service detail ===" + order.getInstallAddress().getServiceDetailList().get(0).getTechnology());
			
			if(addrInfo.getMobileOfferInd()!=null && addrInfo.getMobileOfferInd().equalsIgnoreCase("Y")){
				logger.info("addrInfo.getMobileOfferInd: "+addrInfo.getCustomer().getMobileCutomerInfo().getMobCustNo());
				order.setMobileOfferInd("Y");
				order.setMrt(addrInfo.getCustomer().getMobileCutomerInfo().getMobCustNo());
				order.getCustomer().getMobileCutomerInfo().setIdDocType(addrInfo.getCustomer().getMobileCutomerInfo().getIdDocType());
				order.getCustomer().getMobileCutomerInfo().setIdDocNum(addrInfo.getCustomer().getMobileCutomerInfo().getIdDocNum());
				order.getCustomer().getMobileCutomerInfo().setFirstName(addrInfo.getCustomer().getMobileCutomerInfo().getFirstName());
				order.getCustomer().getMobileCutomerInfo().setLastName(addrInfo.getCustomer().getMobileCutomerInfo().getLastName());

				order.getCustomer().setIdDocType(addrInfo.getCustomer().getMobileCutomerInfo().getIdDocType());
				order.getCustomer().setIdDocNum(addrInfo.getCustomer().getMobileCutomerInfo().getIdDocNum());
				if(addrInfo.getCustomer().getMobileCutomerInfo().getLastName()!=null&&!addrInfo.getCustomer().getMobileCutomerInfo().getLastName().isEmpty()){
					order.getCustomer().setLastName(addrInfo.getCustomer().getMobileCutomerInfo().getLastName());
				}
				if(addrInfo.getCustomer().getMobileCutomerInfo().getFirstName()!=null&&!addrInfo.getCustomer().getMobileCutomerInfo().getFirstName().isEmpty()){
					order.getCustomer().setFirstName(addrInfo.getCustomer().getMobileCutomerInfo().getFirstName());
				}
				
			}else{
				order.setMobileOfferInd("N");
			}
			
			if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)){
				BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
				try {
					order.setCslShopCustInd(service.getCslShopCustInd(user.getUsername()));
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
			}
			
			if(!(Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES) && !"Y".equalsIgnoreCase(order.getIsCC())){
				if("Y".equalsIgnoreCase(order.getCslShopCustInd())){
					order.setSalesType("CSL");
				}else{
					order.setSalesType("HKT");
				}
			}else{
				order.setSalesType(null);
			}
			
			request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);

			return new ModelAndView(new RedirectView(nextView));
	}	

	//SimpleFormController
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called aaa"); 
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		String locale = RequestContextUtils.getLocale(request).toString();
		
		Map<String, Map<String,String>> imsFrontendAlertMsg = 
			(Map<String, Map<String,String>>) request.getSession().getAttribute(ImsConstants.IMS_FRONTEND_ALERT_MSG);
		String relatedPonSBMsg = imsFrontendAlertMsg.get(locale).get("servPlan10ponSB");
		referenceData.put("relatedPonSBMsg", relatedPonSBMsg);

		return referenceData;
	}
	

}
