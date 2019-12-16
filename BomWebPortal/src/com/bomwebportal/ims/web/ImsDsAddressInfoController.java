package com.bomwebportal.ims.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.AddrInventoryDTO;
import com.bomwebportal.ims.dto.HousTypeOTChrgDTO;
import com.bomwebportal.ims.dto.ImsAddressDTO;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;
import com.bomwebportal.ims.dto.ui.AddressInfoUI;
import com.bomwebportal.ims.dto.ui.AppointmentUI;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsAddressInfoService;
import com.bomwebportal.ims.service.ImsAddressSearchService;
import com.bomwebportal.ims.service.ImsAppointmentService;
import com.bomwebportal.util.Utility;
import com.google.gson.Gson;


public class ImsDsAddressInfoController extends SimpleFormController {

	private ImsAddressInfoService service;
	private ImsAddressSearchService searchService;
	private ImsAppointmentService appntService;
	
    private String blacklistCustInd;
    private String prevSbNo;

	@Override
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		order.setIsCC(user.getChannelId()==2||user.getChannelId()==3?"Y":"N");
		order.setIsPT(user.getChannelId()==3?"Y":"N");
		order.setIsDS((Boolean) request.getSession().getAttribute(ImsConstants.IMS_DIRECT_SALES)?"Y":"N");//added by Andy 
		
		List<String> relatedSBList = service.getRelatedSBList(order.getOrderImg().getInstallAddress().getSerbdyno());
		
		if(StringUtils.equals(order.getSrc(), "CO")) {	//not allow to amend flat/ floor for ride on FSA case
			order.setHasRelatedSB("N");
			AddressInfoUI addrInfo = getAddrInfoBySb(order, order.getOrderImg().getInstallAddress().getSerbdyno(), order.getOrderImg().getInstallAddress().getFloorNo(), order.getOrderImg().getInstallAddress().getUnitNo());
			order.setInstallAddress(addrInfo.getInstallAddress());
			
			return new ModelAndView(new RedirectView("imsbasketselect.html"));
		}
		else { 
			if(relatedSBList!=null && relatedSBList.size()>0) {
				order.setHasRelatedSB("Y");
			} else {
				order.setHasRelatedSB("N");
			}
			request.getSession().setAttribute("relatedSBList", relatedSBList);
			return super.handleRequest(request, response);
		}
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
//		List<String> relatedSBList = service.getRelatedSBList(order.getOrderImg().getInstallAddress().getSerbdyno());
		List<String> relatedSBList = (List<String>) request.getSession().getAttribute("relatedSBList");
		AddressInfoUI addrInfo = getAddrInfoBySb(order, order.getOrderImg().getInstallAddress().getSerbdyno(), order.getOrderImg().getInstallAddress().getFloorNo(), order.getOrderImg().getInstallAddress().getUnitNo());
		addrInfo.setSbSelected(order.getInstallAddress().getSerbdyno()); 
		
		List<AddressInfoUI> relatedSbList = new ArrayList<AddressInfoUI>();
		
		for (String s: relatedSBList){
			AddressInfoUI tmp = getAddrInfoBySb(order, s, order.getOrderImg().getInstallAddress().getFloorNo(), order.getOrderImg().getInstallAddress().getUnitNo());
			relatedSbList.add(tmp);
		}
		addrInfo.setRelatedSbList(relatedSbList);
		
		logger.debug("formBackingObject" + new Gson().toJson(addrInfo));
		
//		logger.debug("relatedSbList" + new Gson().toJson(addrInfo));
		return addrInfo;
	}
	
	public String getPrevSbNo() {
		return prevSbNo;
	}

	public void setPrevSbNo(String prevSbNo) {
		this.prevSbNo = prevSbNo;
	}
	
	public ImsAddressInfoService getService() {
		return service;
	}

	public void setService(ImsAddressInfoService service) {
		this.service = service;
	}

	public String getBlacklistCustInd() {
		return blacklistCustInd;
	}

	public void setBlacklistCustInd(String blacklistCustInd) {
		this.blacklistCustInd = blacklistCustInd;
	}

	public void setSearchService(ImsAddressSearchService searchService) {
		this.searchService = searchService;
	}

	public ImsAddressSearchService getSearchService() {
		return searchService;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {

		String nextView = "imsbasketselect.html";
		logger.info("Next View: " + nextView);
		
		AddressInfoUI addrInfo = (AddressInfoUI)command;
		
		OrderImsUI order = (OrderImsUI) request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		if(StringUtils.equals(addrInfo.getSbSelected(), addrInfo.getInstallAddress().getSerbdyno())){
			order.setInstallAddress(addrInfo.getInstallAddress());
		} else {
			for (AddressInfoUI a: addrInfo.getRelatedSbList()){
				if(StringUtils.equals(addrInfo.getSbSelected(), a.getInstallAddress().getSerbdyno())){
					InstallAddrUI instaddr=a.getInstallAddress();
					if (StringUtils.isNotBlank(addrInfo.getInstallAddress().getUnitNo())){
						instaddr.setUnitNo(addrInfo.getInstallAddress().getUnitNo());
					}
					if (StringUtils.isNotBlank(addrInfo.getInstallAddress().getFloorNo())){
						instaddr.setFloorNo(addrInfo.getInstallAddress().getFloorNo());
					}
					order.setInstallAddress(instaddr);
//					logger.info("instaddr.getInstallAddress()    "+ new Gson().toJson(instaddr));
				}
			}		
		}
//		logger.info("order.getInstallAddress:    "+ new Gson().toJson(order.getInstallAddress()));
		if (StringUtils.equals(order.getInstallAddress().getSerbdyno(), addrInfo.getSbSelected()))
		 {
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
		
		order.setPonOTChrgType(addrInfo.getPonOTChrgType());
		order.setAdslOTChrgType(addrInfo.getAdslOTChrgType());
		order.setVdslOTChrgType(addrInfo.getVdslOTChrgType());
		order.setVectorOTChrgType(addrInfo.getVectorOTChrgType());
		
		order.getInstallAddress().setSbFilterVas(service.checkSpecialSBFilterVas(prevSbNo));
		request.getSession().setAttribute(ImsConstants.IMS_ORDER, order);
		
		return new ModelAndView(new RedirectView(nextView));
	}

	public AddressInfoUI getAddrInfoBySb(OrderImsUI order, String sb, String floor, String flat){
		
		AddressInfoUI addrinfo = new AddressInfoUI();
		InstallAddrUI installaddr = new InstallAddrUI();
		
		String[] sbList = new String[20];
		sbList[0] = sb;
		
		Boolean ponSupported = true;
		Boolean vdslSupported = true;
		Boolean adslSupported = true;
		Boolean vectorSupported = true;
		
		ImsAddressDTO tmp = (searchService.getAddressBySB(sbList)).get(0);
		ImsServiceSrdDTO tmp2 = appntService.getServiceSrdComparedWithBMO(order.getInstallAddress().getBlacklistInd(), order.getCustomer().getBlacklistInd(), "N", null, sb, flat, floor, order.getIsCC(), order.getIsPT(), order.getIsDS(),
				"Y","N",order.getCustomer().getExistingCustomerConflictWithName(),"N","N",null,null, "", null);
		String hosTypeDesc = "";
		String phInd = "N";
		String hosType = "";
		for(int i=0; i < tmp2.getHousingTypeList().size(); i++){
			if (tmp2.getHousingTypeList().get(i).getHousingType().substring(0,2).equalsIgnoreCase("PH")){
				phInd = "Y";
			}
			if (i==0) {
				
				hosTypeDesc = tmp2.getHousingTypeDescList().get(i).getHousingTypeDesc();
			} else {
				
				hosTypeDesc = hosTypeDesc + ", " + tmp2.getHousingTypeDescList().get(i).getHousingTypeDesc();
			}
			hosType = tmp2.getHousingTypeList().get(i).getHousingType();
		}
		AddressInfoUI tmp3 = service.getHousTypeOTChrgList(hosType.trim(), sb, floor, flat);
		
		String ponSrd = "";
		String adslSrd = "";
		String vdslSrd = "";
		String vectorSrd = "";
		String ponErrorMsg = "";
		String adslErrorMsg = "";
		String vdslErrorMsg = "";
		
		String vectorErrorMsg = "";
		String ponInstFee ="";
		String ponInstFeeComp ="";//gary	
		String ponOTChrgType="";		
		String vdslInstFee =""; 
		String vdslInstFeeComp ="";//gary
		String vdslOTChrgType =""; 
		String vectorInstFee =""; 
		String vectorInstFeeComp ="";
		String vectorOTChrgType =""; 
		String adslInstFee ="";
		String adslInstFeeComp ="";//gary
		String adslOTChrgType ="";
		String bomDesc ="";
		
		for (HousTypeOTChrgDTO i:tmp3.getHousTypeOTChrgList())
		{
			if (i.getOTChrgProdType().equalsIgnoreCase("PON"))
			{
				ponInstFee = i.getOTChrg();
				ponOTChrgType = i.getOTChrgType();
				ponInstFeeComp = i.getInstallOTDesc_en();
			}
			else if (i.getOTChrgProdType().equalsIgnoreCase("VDSL"))
			{
				vdslInstFee = i.getOTChrg();
				vdslOTChrgType = i.getOTChrgType();
				vdslInstFeeComp = i.getInstallOTDesc_en();
			}
			else if (i.getOTChrgProdType().equalsIgnoreCase("ADSL"))
			{
				adslInstFee = i.getOTChrg();
				adslOTChrgType = i.getOTChrgType();
				adslInstFeeComp = i.getInstallOTDesc_en();
			}
			else if (i.getOTChrgProdType().equalsIgnoreCase("Vectoring"))
			{
				vectorInstFee = i.getOTChrg();
				vectorOTChrgType = i.getOTChrgType();
				vectorInstFeeComp = i.getInstallOTDesc_en();
			}
		}
		
		for (int j=0; j < tmp2.getServiceDetailList().size(); j++) {
			if (tmp2.getServiceDetailList().get(j).getTechnology().equals("PON") )  
			{
				
				
				if (tmp2.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					tmp2.getServiceDetailList().get(j).getIsDeadCase().equals("N")) 
				{
					ponSrd = Utility.date2String(tmp2.getServiceDetailList().get(j).getEstEarliestSrd(),"yyyy/MM/dd");
					ponSrd = ponSrd + " (Ref: " + tmp2.getServiceDetailList().get(j).getRtnDesc() + ")";
				} else {
					logger.debug("PON not supported");
					ponSrd = "N/A";
					ponSupported = false;
				}
				
				if (tmp2.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					(tmp2.getServiceDetailList().get(j).getIsDeadCase().equals("Y") ||
					 tmp2.getServiceDetailList().get(j).getIsResrcShort().equals("Y")		) ) 
				{
						ponErrorMsg = "<p>" + tmp2.getServiceDetailList().get(j).getRtnCd() + ":" + tmp2.getServiceDetailList().get(j).getRtnDesc() + ".</p>";
//						ponErrorCd = tmp2.getServiceDetailList().get(j).getRtnCd();
				}
				
				if ( ponInstFee.equalsIgnoreCase("TBC") )
				{
					tmp2.getServiceDetailList().get(j).setTechnologySupported("N");
				}
			} 
			if (tmp2.getServiceDetailList().get(j).getTechnology().equals("ADSL") )  
			{
						
				if (tmp2.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					tmp2.getServiceDetailList().get(j).getIsDeadCase().equals("N")) 
				{
						adslSrd = Utility.date2String(tmp2.getServiceDetailList().get(j).getEstEarliestSrd(),"yyyy/MM/dd");
						adslSrd = adslSrd + " (Ref: " + tmp2.getServiceDetailList().get(j).getRtnDesc() + ")";
				} else {
						logger.debug("ADSL not supported");
						adslSrd = "N/A";
						adslSupported = false;
				}
				
				if (tmp2.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					(tmp2.getServiceDetailList().get(j).getIsDeadCase().equals("Y") ||
					 tmp2.getServiceDetailList().get(j).getIsResrcShort().equals("Y")		) ) 
				{
						adslErrorMsg = "<p>" + tmp2.getServiceDetailList().get(j).getRtnCd() + ":" + tmp2.getServiceDetailList().get(j).getRtnDesc() + ".</p>";
//						adslErrorCd = tmp2.getServiceDetailList().get(j).getRtnCd();
				}
				
				if ( adslInstFee.equalsIgnoreCase("TBC") )
				{
					tmp2.getServiceDetailList().get(j).setTechnologySupported("N");
				}
			}
			if (tmp2.getServiceDetailList().get(j).getTechnology().equals("VDSL") )  
			{
						
				if (tmp2.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					tmp2.getServiceDetailList().get(j).getIsDeadCase().equals("N")) 
				{
						vdslSrd = Utility.date2String(tmp2.getServiceDetailList().get(j).getEstEarliestSrd(),"yyyy/MM/dd");
						vdslSrd = vdslSrd + " (Ref: " + tmp2.getServiceDetailList().get(j).getRtnDesc() + ")";
				} else {
						logger.debug("VDSL not supported");
						vdslSrd = "N/A";
						vdslSupported = false;
				}
				
				if (tmp2.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					(tmp2.getServiceDetailList().get(j).getIsDeadCase().equals("Y") ||
					 tmp2.getServiceDetailList().get(j).getIsResrcShort().equals("Y")		) ) 
				{
						vdslErrorMsg = "<p>" + tmp2.getServiceDetailList().get(j).getRtnCd() + ":" + tmp2.getServiceDetailList().get(j).getRtnDesc() + ".</p>";
//						vdslErrorCd = tmp2.getServiceDetailList().get(j).getRtnCd();
				}
				
				if ( vdslInstFee.equalsIgnoreCase("TBC") )
				{
					tmp2.getServiceDetailList().get(j).setTechnologySupported("N");
				}
			}
			if (tmp2.getServiceDetailList().get(j).getTechnology().equals("Vectoring") )  
			{
						
				if (tmp2.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					tmp2.getServiceDetailList().get(j).getIsDeadCase().equals("N")) 
				{
						vectorSrd = Utility.date2String(tmp2.getServiceDetailList().get(j).getEstEarliestSrd(),"yyyy/MM/dd");
						vectorSrd = vectorSrd + " (Ref: " + tmp2.getServiceDetailList().get(j).getRtnDesc() + ")";
				} else {
						logger.debug("Vectoring not supported");
						vectorSrd = "N/A";
						vectorSupported = false;
				}
				
				if (tmp2.getServiceDetailList().get(j).getTechnologySupported().equals("Y") &&
					(tmp2.getServiceDetailList().get(j).getIsDeadCase().equals("Y") ||
					 tmp2.getServiceDetailList().get(j).getIsResrcShort().equals("Y")		) ) 
				{
						vectorErrorMsg = "<p>" + tmp2.getServiceDetailList().get(j).getRtnCd() + ":" + tmp2.getServiceDetailList().get(j).getRtnDesc() + ".</p>";
//						vdslErrorCd = tmp2.getServiceDetailList().get(j).getRtnCd();
				}
				
				if ( vectorInstFee.equalsIgnoreCase("TBC") )
				{
					tmp2.getServiceDetailList().get(j).setTechnologySupported("N");
				}
			}
		}
		
		if (ponInstFee == "" || ponSrd == "N/A"){
			ponInstFee = "N/A";
		}
		
		if (vdslInstFee == ""|| vdslSrd == "N/A"){
			vdslInstFee = "N/A";
		}
		
		if (vectorInstFee == ""|| vectorSrd == "N/A"){
			vectorInstFee = "N/A";
		}
		
		if (adslInstFee == ""|| adslSrd == "N/A"){
			adslInstFee = "N/A";
		}
		
		installaddr.setAddrUsage("IA");
		installaddr.setFlat(flat);
		installaddr.setUnitNo(flat);
		installaddr.setFloorNo(floor);
		installaddr.setSbFlat(tmp.getFlat());
		installaddr.setSbFloor(tmp.getFloor());
		installaddr.setSerbdyno(tmp.getServiceBoundaryNum());
		installaddr.setBuildNo(tmp.getBuildingName());
		installaddr.setStrName(tmp.getStreetName());
		installaddr.setDistNo(tmp.getDistrictCode());
		installaddr.setDistDesc(tmp.getDistrictDesc());
		installaddr.setSectDesc(tmp.getSectionDesc());
		installaddr.setSectCd(tmp.getSectionCode());
		installaddr.setCoverPe(tmp.getCoverPe());
		installaddr.setCoverEyeX(tmp.getCoverEyeX());
		installaddr.setAreaCd(tmp.getAreaCode());
		installaddr.setStrCatCd(tmp.getStreetCatgCode());
		installaddr.setStrCatDesc(tmp.getStreetCatgDesc());
		installaddr.setAreaDesc(tmp.getAreaDesc());
		
		addrinfo.setPonSrd(ponSrd);
		addrinfo.setVdslSrd(vdslSrd);
		addrinfo.setAdslSrd(adslSrd);
		addrinfo.setVectorSrd(vectorSrd);
		addrinfo.setPonInstFee(ponInstFee);
		addrinfo.setVdslInstFee(vdslInstFee);
		addrinfo.setAdslInstFee(adslInstFee);
		addrinfo.setVectorInstFee(vectorInstFee);
		addrinfo.setPonOTChrgType(ponOTChrgType);
		addrinfo.setVdslOTChrgType(vdslOTChrgType);
		addrinfo.setAdslOTChrgType(adslOTChrgType);
		addrinfo.setVectorOTChrgType(vectorOTChrgType);
		
		installaddr.setHousingTypeList(tmp2.getHousingTypeList());
		installaddr.setBandwidthList(tmp2.getBandwidthList());
		installaddr.setServiceDetailList(tmp2.getServiceDetailList());
		
		AddrInventoryDTO addrInventory = new AddrInventoryDTO();
		addrInventory.setN2BuildingInd(tmp2.getIs2NBuilding());
		
		installaddr.setAddrInventory(addrInventory);
		installaddr.setSbFilterVas(service.checkSpecialSBFilterVas(sb));
		
		addrinfo.setInstallAddress(installaddr);
		addrinfo.setHosType(hosType);
		
		return addrinfo;
	}

	public void setAppntService(ImsAppointmentService appntService) {
		this.appntService = appntService;
	}

	public ImsAppointmentService getAppntService() {
		return appntService;
	}
}
