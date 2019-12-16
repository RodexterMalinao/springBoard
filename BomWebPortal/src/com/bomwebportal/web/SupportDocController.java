package com.bomwebportal.web;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocAssgnDTO.CollectedInd;
import com.bomwebportal.dto.AllOrdDocWaiveDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoMemberDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderDTO.CollectMethod;
import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.OrderDTO.EsigEmailLang;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.ui.DepositUI;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.dto.ui.SupportDocUI.GenerateSpringboardForm;
import com.bomwebportal.dto.ui.SupportDocUI.SpringboardForm;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class SupportDocController extends SimpleFormController {
	public static final String SESSION_SUPPORTDOC = "supportDoc";
	private static final String LOB = "MOB";
	private static final String ITEM_SELECTION_GROUP_ID_HELPER_CARE = "6666666664";
	private static final String ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE= "6666666665";
	private static final String PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID = "6666666663";
	
	private MobileDetailService mobileDetailService;
	private SupportDocService supportDocService;
	private VasDetailService vasDetailService;
	
	private IGuardService iGuardService;
    

	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}
	
	public MobileDetailService getMobileDetailService() {
		return mobileDetailService;
	}

	public void setMobileDetailService(MobileDetailService mobileDetailService) {
		this.mobileDetailService = mobileDetailService;
	}

	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		SupportDocUI form;
		if (request.getSession().getAttribute(SESSION_SUPPORTDOC) instanceof SupportDocUI) {
			form = (SupportDocUI) request.getSession().getAttribute(SESSION_SUPPORTDOC);
			List<AllOrdDocAssgnDTO> currentAllOrdDocAssgnDTOs = form.getAllOrdDocAssgnDTOs();
			List<AllOrdDocAssgnDTO> newAllOrdDocAssgnDTOs = this.retrieveAllOrdDocAssgnDTOs(request);
			
			if (!isEmpty(currentAllOrdDocAssgnDTOs) && !isEmpty(newAllOrdDocAssgnDTOs)) {
				for (AllOrdDocAssgnDTO currentDto : currentAllOrdDocAssgnDTOs) {
					for (AllOrdDocAssgnDTO newDto : newAllOrdDocAssgnDTOs) {
						if (currentDto.getDocTypeMob().equals(newDto.getDocTypeMob())) {
							// BeanUtilsHelper.copyProperties(currentDto, newDto, new String[] { "waiveReason", "waiveBy", "collectedInd", "markDelInd" });
							newDto.setWaiveReason(currentDto.getWaiveReason());
							newDto.setWaivedBy(currentDto.getWaivedBy());
							newDto.setCollectedInd(currentDto.getCollectedInd());
							newDto.setMarkDelInd(currentDto.getMarkDelInd());
							break;
						}
					}
				}
			}
			form.setAllOrdDocAssgnDTOs(newAllOrdDocAssgnDTOs);
		} else {
			form = new SupportDocUI();
			form.setCollectMethod(CollectMethod.D);
			form.setAllOrdDocAssgnDTOs(this.retrieveAllOrdDocAssgnDTOs(request));
			form.setDisMode(DisMode.E);
			CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) request.getSession().getAttribute("customer");
			
			EsigEmailLang esigEmailLang = EsigEmailLang.CHN;
			if (customerInfoDto != null) {
				form.setEsigEmailAddr(customerInfoDto.getEmailAddr());
				if ("00".equals(customerInfoDto.getSmsLang())) {
					esigEmailLang = EsigEmailLang.CHN;
				} else if ("02".equals(customerInfoDto.getSmsLang())) {
					esigEmailLang = EsigEmailLang.ENG;
				}
			}
			form.setEsigEmailLang(esigEmailLang);
		}
		form.setGenerateSpringboardForms(this.retrieveGenerateSpringboardForms(request));
		
		// check if only allow paper disMode
		MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
		if (this.onlyAllowPaperDisMode(mnpDto)) {
			form.setCollectMethod(CollectMethod.P);
			form.setDisMode(DisMode.P);
			form.setEsigEmailAddr(null);
			form.setEsigEmailLang(null);
		}
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		if(user.getChannelId() == 10 || user.getChannelId() == 11){
			CustomerProfileDTO customer = (CustomerProfileDTO)request.getSession().getAttribute("customer");
			form.setDsMissingDocReason(customer.getDsMissDoc());
		}
		return form;
	}
	
	public Map<String, Object> referenceData(HttpServletRequest request) {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		boolean subscribedUADVas = false;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		referenceData.put("salesUserDTO", user);
		
		Date appDate;
		if (request.getSession().getAttribute("OrderDto") instanceof OrderDTO) {
			appDate = ((OrderDTO) request.getSession().getAttribute("OrderDto")).getAppInDate();
		} else {
			appDate = new Date();
		}
		List<AllDocDTO> allDocDTOs = supportDocService.getAllDocDTOKnownByTypeAndAppDate(LOB, Type.I, appDate);
		
		Map<String, List<AllOrdDocWaiveDTO>> waiveReasons = new HashMap<String, List<AllOrdDocWaiveDTO>>();
		for (AllDocDTO allDocDTO : allDocDTOs) {
			List<AllOrdDocWaiveDTO> reasons = this.supportDocService.getAllOrdDocWaiveDTOALL(LOB, allDocDTO.getDocTypeMob());
			if (!this.isEmpty(reasons)) {
				waiveReasons.put(allDocDTO.getDocTypeMob(), reasons);
			}
		}
		referenceData.put("waiveReasons", waiveReasons);
		
		// check if only allow paper disMode
		List<DisMode> disModes = new ArrayList<DisMode>();
		MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
		if (!this.onlyAllowPaperDisMode(mnpDto)) {
			disModes.add(DisMode.E);
		}
		disModes.add(DisMode.P);
		referenceData.put("disModes", disModes);
		
		//get DS Missing doc reason list
		if(user.getChannelId() == 10 || user.getChannelId() == 11){
			List<String> reasonList = this.supportDocService.getMissingDocReasonList();
			referenceData.put("missingDocReason", reasonList);
		}
		
		String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
		List<String> iguardUADItem = vasDetailService.getItemSelectionGrpList("6666666669");
		for (String iGuardUADItem:iguardUADItem){
			if (ArrayUtils.contains(selectedItemList, iGuardUADItem)){
				subscribedUADVas = true;
			}
		}
		referenceData.put("iGuardUAD", subscribedUADVas);
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		SupportDocUI form = (SupportDocUI) command;
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
    	if (!this.isEmpty(form.getAllOrdDocAssgnDTOs())) {
    		Date now = new Date();
    		for (Iterator<AllOrdDocAssgnDTO> it = form.getAllOrdDocAssgnDTOs().iterator(); it.hasNext(); ) {
    			AllOrdDocAssgnDTO dto = it.next();
    			if (StringUtils.isBlank(dto.getWaiveReason())) {
        			dto.setCollectedInd(CollectedInd.Y.equals(dto.getCollectedInd()) ? CollectedInd.Y : CollectedInd.N);
    			} else {
    				dto.setCollectedInd(CollectedInd.N);
    			}
    			dto.setWaivedBy(StringUtils.isBlank(dto.getWaiveReason()) ? null : salesUserDto.getUsername());
    			//dto.setMarkDelInd(MarkDelInd.Y.equals(dto.getMarkDelInd()) ? MarkDelInd.Y : MarkDelInd.N);
    			dto.setCreateBy(salesUserDto.getUsername());
    			dto.setCreateDate(now);
    			dto.setLastUpdBy(salesUserDto.getUsername());
    			dto.setLastUpdDate(now);

    			if (DocType.M010.equals(dto.getDocType())) {
    				if (DisMode.P.equals(form.getDisMode())) {
    					it.remove();
    				}
    			}
    		}
    	}
		// check if only allow paper disMode
		MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
		if (this.onlyAllowPaperDisMode(mnpDto)) {
			form.setCollectMethod(CollectMethod.P);
			form.setDisMode(DisMode.P);
			form.setEsigEmailAddr(null);
			form.setEsigEmailLang(null);
		}
		
		request.getSession().setAttribute(SESSION_SUPPORTDOC, form);
		
		if (this.isConcierge(request.getSession())) {
			if (DisMode.E.equals(form.getDisMode()) 
					&& !(request.getSession().getAttribute("conciergeSignSession") instanceof SignoffDTO)) {
				ModelAndView modelAndView = new ModelAndView(new RedirectView("supportdoc.html"));
				/* test uid*/
				String attrUid=(String)request.getParameter("sbuid");
				modelAndView.addObject("sbuid", attrUid);
				/* test uid*/
				modelAndView.addObject("conciergeSignPresent", false);
				return modelAndView;
			}
		}

		if(salesUserDto.getChannelId() == 10 || salesUserDto.getChannelId() == 11){
			CustomerProfileDTO customer = (CustomerProfileDTO)request.getSession().getAttribute("customer");
			customer.setDsMissDoc(form.getDsMissingDocReason());
		}
		String nextView = (String) request.getAttribute("nextView");
		//return new ModelAndView(new RedirectView(nextView));
		/* test uid*/
		String attrUid=(String)request.getParameter("sbuid");
		ModelAndView modelAndView =  new ModelAndView(new RedirectView(nextView));
		modelAndView.addObject("sbuid", attrUid);
		/* test uid*/
		return modelAndView;
	}

	private List<GenerateSpringboardForm> retrieveGenerateSpringboardForms(HttpServletRequest request) {
		List<GenerateSpringboardForm> generateSpringboardForms = new ArrayList<GenerateSpringboardForm>();
		for (SpringboardForm springboardForm : SpringboardForm.values()) {
			GenerateSpringboardForm generateSpringboardForm = new GenerateSpringboardForm();
			String[] gSelectedItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
			boolean required = false;
			switch (springboardForm) {
			case MOBILE_APPLICATION_FORM:
			case SERVICE_GUIDE:
				// By Default
				required = true;
				break;
			case TRADE_DESCRIPTIONS_FOR_ELECTIONIC_PRODUCTS:
			{
				// Device description defined in w_hs_trade_desc
				String basketId = (String) request.getSession().getAttribute("basketSession");
				if (StringUtils.isNotBlank(basketId)) {
					required = this.mobileDetailService.isTradeDescExist(basketId, new Date());
					if(required==false){
						required =vasDetailService.hasProductionInfo(gSelectedItemList);//check VAS item
					}
				}
				break;
			}
			case PCCW_CONCIERGE_SERVICE_FORM:
			{
				// select Rate plan Category : Concierge
				required = isConcierge(request.getSession());
				break;
			}
			case APPLICATION_IN_RESPECT_OF_MOBILE_SECRETARIAL_SERVICE:
			{
				// Basket offer inculde secretarial service
				String[] selectedItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
				if (selectedItemList != null) {
					for (String itemId : selectedItemList) {
						if (this.vasDetailService.isSecretarialItem(itemId)) {
							required = true;
							break;
						}
					}
				}
				break;
			}
			case MNP_APPLICATION_FORM:
			{
				// MNP in Mobile number
				MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
				BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
				if (mnpDto != null) {
					//For Direct Sales Only, 20131002
					if (salesUserDto.getChannelId() == 10 || salesUserDto.getChannelId() == 11){
						required = isMnp(mnpDto) || mnpDto.isFutherMnp();
					} else {
					required = isMnp(mnpDto);
				}
				}
				if (!required) {
					required = isMNPMultiSIM(request);
				}
				break;
			}
			case CHANGE_OF_SERVICE_FORM:
			{
				// MNP's owner doc ID different from customer or
				// New MRT + MNP
				MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
				if (mnpDto != null) {
					if (isMnp(mnpDto)) {
						if ("Y".equals(mnpDto.getMnp())) {
							if (StringUtils.isBlank(mnpDto.getCustIdDocNum())) {
								required = true;
							} else {
						     	CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) request.getSession().getAttribute("customer");
								if (changedCustomer(mnpDto, customerInfoDto)) {
									required = true;
								}
							}
						} else {
							required = true;
						}
					} else {
						// further MNP
						if (mnpDto.isFutherMnp()) {
							required = true;
						}
					}
				}
				if (!required) {
					required = isMNPTransferMultiSIM(request);
				}
				break;
			}
			case THRID_PARTY_AUTOPAY_FORM:
			{
				// Payment method, choose credit card with Third party
				PaymentDTO paymentDto = (PaymentDTO) request.getSession().getAttribute("payment");
				if (paymentDto != null) {
					if ("C".equals(paymentDto.getPayMethodType())) {
						if ("Y".equals(paymentDto.getThirdPartyInd())) {
							required = true;
						}
					}
				}
				break;
			}
			case IGUARD_FORM_LDS:
			{
				// IGUARD_FORM_LDS
				String appDateStr=(String) request.getSession().getAttribute("appDate");
				Date appDate = Utility.string2Date(appDateStr);
				BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
				String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
				
				List<String> iGuardType=iGuardService.isIGuardOrder(basketDTO.getBasketId(), selectedItemList, appDate);
				for(int i=0;i<iGuardType.size();i++){
					if ("LDS".equals(iGuardType.get(i))){	
					 required = true;
					}
				}
				break;
			}
			case IGUARD_FORM_AD:
			{
				// IGUARD_FORM_AD
				String appDateStr=(String) request.getSession().getAttribute("appDate");
				Date appDate = Utility.string2Date(appDateStr);
				BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
				String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
				
				List<String> iGuardType=iGuardService.isIGuardOrder(basketDTO.getBasketId(), selectedItemList, appDate);
				for(int i=0;i<iGuardType.size();i++){
					if("AD".equals(iGuardType.get(i))){
					 required = true;
					}
				}
				break;
			}
			
			case MOBILE_SAFETY_PHONE:
			{
				// MOBILE_SAFETY_PHONE
				String appDateStr=(String) request.getSession().getAttribute("appDate");
				Date appDate = Utility.string2Date(appDateStr);
				BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
				String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
				if (this.vasDetailService.isItemsInGroup(basketDTO.getBasketId(), selectedItemList, appDate, "100000103")) {
					required = true;
				}
				break;
			}
			case NFC_SIM:
			{
				MobileSimInfoDTO mobileSimInfoDTO = (MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo");
				if (mobileSimInfoDTO!= null && mobileSimInfoDTO.getItemCd() != null && this.vasDetailService.isExtraFunctionSim((mobileSimInfoDTO.getItemCd()))   ) {
					required = true;
				}
				
				
				
				/*// NFC_SIM
				//String orderId = (String)request.getSession().getAttribute("orderIdSession");
				//if (orderId == null) {
					MobileSimInfoDTO mobileSimInfoDTO = (MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo");
					if (this.vasDetailService.isNFCSim(mobileSimInfoDTO.getItemCd())) {
						required = true;
					}
				} else {
					if (this.vasDetailService.hasNFCSim(orderId)) {
						required = true;
					}
				}*/
				break;
			}
			case IGUARD_FORM_UAD:
			{
				// IGUARD_FORM_AD
				String appDateStr=(String) request.getSession().getAttribute("appDate");
				Date appDate = Utility.string2Date(appDateStr);
				BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
				String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
				
				List<String> iGuardType=iGuardService.isIGuardOrder(basketDTO.getBasketId(), selectedItemList, appDate);
				for(int i=0;i<iGuardType.size();i++){
					if("UAD".equals(iGuardType.get(i))){
					 required = true;
					}
				}
				break;
			}
			case TRAVEL_INSURANCE_FORM:
			{
				String[] selectedItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
				if (selectedItemList!= null && this.vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE,selectedItemList)) {
					required = true;
				}
				break;
			}
			case HELPERCARE_INSURANCE_FORM:
			{	
				String[] selectedItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
				if (selectedItemList!= null && this.vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_HELPER_CARE,selectedItemList)) {
					required = true;
				}
				break;
			}
			case PROJECT_EAGLE_FORM:
			{	
				String[] selectedItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
				if (selectedItemList!= null && this.vasDetailService.existInSelectionGrpList(PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID,selectedItemList)) {
					required = true;
				}
				break;
			}
			/*case OCTOPUS_SIM:
			{
				// OCTOPUS_SIM
				//String orderId = (String)request.getSession().getAttribute("orderIdSession");
				//if (orderId == null) {
					MobileSimInfoDTO mobileSimInfoDTO = (MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo");
					if (this.vasDetailService.isOctopusSim(mobileSimInfoDTO.getItemCd())) {
						required = true;
					}
					} else {
					if (this.vasDetailService.hasOctopusSim(orderId)) {
						required = true;
					}
				}
				break;
			}*/
			}
			generateSpringboardForm.setSpringboardForm(springboardForm);
			generateSpringboardForm.setRequired(required);
			generateSpringboardForms.add(generateSpringboardForm);
		}
		return generateSpringboardForms;
	}
	
	private List<AllOrdDocAssgnDTO> retrieveAllOrdDocAssgnDTOs(HttpServletRequest request) {
		Date appDate;
		if (request.getSession().getAttribute("OrderDto") instanceof OrderDTO) {
			appDate = ((OrderDTO) request.getSession().getAttribute("OrderDto")).getAppInDate();
		} else {
			appDate = new Date();
		}
		List<AllDocDTO> allDocDTOs = supportDocService.getAllDocDTOKnownByTypeAndAppDate(LOB, Type.I, appDate);
		if (this.isEmpty(allDocDTOs)) {
			return Collections.emptyList();
		}
		
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) request.getSession().getAttribute("customer");

		List<AllOrdDocAssgnDTO> allOrdDocAssgnDTOs = new ArrayList<AllOrdDocAssgnDTO>();
		for (AllDocDTO dto : allDocDTOs) {
			boolean required = false;
			switch (dto.getDocType()) {
			case M001:
				// Springboard Mobile Form (Out form)
				break;
			case M002:
			{
				// Customer Doc type is HKID or Passport (in Customer Info page)
				if (customerInfoDto != null) {
					if ("HKID".equals(customerInfoDto.getIdDocType()) || "PASS".equals(customerInfoDto.getIdDocType())
							|| "HKID".equals(customerInfoDto.getRepIdDocType()) || "PASS".equals(customerInfoDto.getRepIdDocType())) {
						required = true;
					}
				}
				break;
			}
			case M003: // address proof
			{
				if (customerInfoDto != null) {
					if ("1".equals(customerInfoDto.getAddrProofInd()) || "5".equals(customerInfoDto.getAddrProofInd())) {
						required = true;
					}
				}
				break;
			}
			case M004:
			{
				// Customer Doc type is Biz Registration (BR)
				if (customerInfoDto != null) {
					required = "BS".equals(customerInfoDto.getIdDocType());
				}
				break;
			}
			case M005:
			{
				// Customer Doc type is Biz Registration (BR) and selected Name Card (in Customer Info page)
				if (customerInfoDto != null) {
					required = ("BS".equals(customerInfoDto.getIdDocType()) && "C".equalsIgnoreCase(customerInfoDto.getCompanyDoc()));
				}
				break;
			}
			case M006:
			{
				// Customer Doc type is HKID with prefix 'W' and mark as Domestic Helpers (in Customer Info page)
				if (customerInfoDto != null) {
					if ("HKID".equals(customerInfoDto.getIdDocType())) {
						if (StringUtils.isNotBlank(customerInfoDto.getIdDocNum()) && customerInfoDto.getIdDocNum().startsWith("W")) {
							required = customerInfoDto.isForeignDomesticHelperInd();
						}
					}
				}
				break;
			}
			case M007:
			{
				// MNP with pre-paid SIM (in MNP / New phone page)
				MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
				if (mnpDto != null) {
					if (isMnp(mnpDto)) {
						required = "Prepaid Sim".equals(mnpDto.getCustName());
					}
				}
				if (!required) {
					required = isMNPTransferPrepaidMultiSIM(request);
				}
				break;
			}
			case M008:
			{
				// Monthly payment by Credit Card (in Payment Method)
				PaymentDTO paymentDto = (PaymentDTO) request.getSession().getAttribute("payment");
				if (paymentDto != null) {
					required = "C".equals(paymentDto.getPayMethodType());
				}
				break;
			}
			case M009:
			{
				// MNP owner's Doc ID different with Customer's Doc ID (in MNP / New phone page)
				MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
				if (mnpDto != null) {
					if (isMnp(mnpDto)) {
						if (StringUtils.isBlank(mnpDto.getCustIdDocNum())) {
							required = true;
						} else {
							if (changedCustomer(mnpDto, customerInfoDto)) {
								required = true;
							}
						}
					}
					
					BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
					if((salesUserDto.getChannelId() == 10 || salesUserDto.getChannelId() == 11) && mnpDto.isFutherMnp()) {
						if (StringUtils.isBlank(mnpDto.getFuthercustIdDocNum())) {
							required = true;
						} else {
							if (changedCustomer(mnpDto, customerInfoDto)) {
								required = true;
							}
						}
					}
				}
				if (!required) {
					required = isMNPTransferMultiSIM(request);
				}
				break;
			}
			case M010:
			{
				MobileSimInfoDTO mobileSimInfo = (MobileSimInfoDTO) request.getSession().getAttribute("MobileSimInfo");
				if (mobileSimInfo != null) {
					if (isConcierge(request.getSession()) && "Y".equals(mobileSimInfo.getAoInd())) {
						required = true;
					}
				}
				break;
			}
			case M034:
			{
				// Customer Doc type is Biz Registration (BR) and selected Authorized Letter (in Customer Info page)
				if (customerInfoDto != null) {
					required = ("BS".equals(customerInfoDto.getIdDocType()) && "L".equalsIgnoreCase(customerInfoDto.getCompanyDoc()));
				}
				break;
			}
			default:
				break;
			}
			//Check if supportDoc required in bomweb_support_doc_control for selected VAS
			if (!required) {
				String[] vasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
				String basketId = (String) request.getSession().getAttribute("basketSession");
				if (supportDocService.isSupportDocRequired(dto.getDocTypeMob(), basketId, vasItemList, "RS")) {
					required = true;
				}
			}
			//Check if
//			if ("M017".equals(dto.getDocTypeMob())) {
//				BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
//				if ((salesUserDto.getChannelId()==10 || salesUserDto.getChannelId()==11) && "MDV".equals(salesUserDto.getChannelCd())) {
//					MobDsPaymentUpfrontDTO paymentUpfront = (MobDsPaymentUpfrontDTO)request.getSession().getAttribute("paymentUpfront");
//					for (MobDsPaymentTransDTO paymentTrans: paymentUpfront.getPaymentTransList()) {
//						if ("M".equals(paymentTrans.getPaymentType()) && paymentTrans.getPaymentAmount() > 0) {
//							required = true;
//							break;
//						}
//					}
//				}
//			}
//			if ("M018".equals(dto.getDocTypeMob())) {
//				BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
//				if (salesUserDto.getChannelId()==10 || salesUserDto.getChannelId()==11) {
//					MobDsPaymentUpfrontDTO paymentUpfront = (MobDsPaymentUpfrontDTO)request.getSession().getAttribute("paymentUpfront");
//					for (MobDsPaymentTransDTO paymentTrans: paymentUpfront.getPaymentTransList()) {
//						if (("I".equals(paymentTrans.getPaymentType()) || "C".equals(paymentTrans.getPaymentType())) && paymentTrans.getPaymentAmount() > 0) {
//							required = true;
//							break;
//						}
//					}
//				}
//			}
			if ("M044".equals(dto.getDocTypeMob())) {
				BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
				if ((salesUserDto.getChannelId()==10 || salesUserDto.getChannelId()==11) && "MDV".equals(salesUserDto.getChannelCd())) {
					BasketDTO basketDTO = (BasketDTO) MobCcsSessionUtil.getSession(request,	"basket");
					String [] selectedItemList = (String [])request.getSession().getAttribute("selectedVasItemList");
					DepositUI depositUI = (DepositUI) MobCcsSessionUtil.getSession(request, "depositInfo");
					List<DepositDTO> depositList = depositUI.getDepositDTOList();
					
					
					double totalUpfrontAmt = Double.parseDouble(basketDTO.getPrePaymentAmt()) 
							+ Double.parseDouble(basketDTO.getUpfrontAmt()) 
							+ vasDetailService.getVasAmt(selectedItemList, Utility.date2String(appDate, BomWebPortalConstant.DATE_FORMAT))
							+ (Double)computeDepositAmount(depositList).doubleValue();
					
					if (totalUpfrontAmt > 0){
						required = true;
					}
				}
			}
			BomSalesUserDTO salesUserDto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
			if ((salesUserDto.getChannelId()==10) || (salesUserDto.getChannelId()==11)) {
				/*if ("M019".equals(dto.getDocTypeMob())) {
					required= true;
				}*/
				if ("M020".equals(dto.getDocTypeMob())) {
					required= true;
				}
				if ("M021".equals(dto.getDocTypeMob())) {
					MobileSimInfoDTO mobileSimInfoDTO = (MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo");
					List<String> smList = new ArrayList<String>();
					if (mobileSimInfoDTO != null) {
						for (StockAssgnDTO saDTO: mobileSimInfoDTO.getStockAssgnList()) {
							if (saDTO.getSalesMemoNum() != null && saDTO.getSalesMemoNum().length() > 0) {
								if (!smList.contains(saDTO.getSalesMemoNum())) {
									smList.add(saDTO.getSalesMemoNum());
								}
							}
							if (saDTO.getSalesMemoNum2() != null && saDTO.getSalesMemoNum2().length() > 0) {
								if (!smList.contains(saDTO.getSalesMemoNum2())) {
									smList.add(saDTO.getSalesMemoNum2());
								}
							}
						}
					}
					if (smList.size() >= 2) {
						required= true;
					}
				}
				if ("M022".equals(dto.getDocTypeMob())) {
					MnpDTO mnpDto = (MnpDTO) request.getSession().getAttribute("MNP");
					if (mnpDto.getMsisdnLvl() != null) {
						if (mnpDto.getMsisdnLvl().equals("GA") 
								|| mnpDto.getMsisdnLvl().equals("GB") 
								|| mnpDto.getMsisdnLvl().equals("GC") 
								|| mnpDto.getMsisdnLvl().equals("GD")) {
							required= true;
						}
					}
				}
				
				if ("M023".equals(dto.getDocTypeMob())) {
					required= true;
				}
				if ("M024".equals(dto.getDocTypeMob())) {
					required= true;
				}
			}
			if (required) {
				AllOrdDocAssgnDTO allOrdDocAssgnDTO = new AllOrdDocAssgnDTO();
				allOrdDocAssgnDTO.setDocType(dto.getDocType());
				allOrdDocAssgnDTO.setDocTypeMob(dto.getDocTypeMob());
				allOrdDocAssgnDTO.setDocName(dto.getDocName());
				//set default waive reason, M003W006 : Domestic Helpers - Refer Employment Contract Copy
				if (customerInfoDto != null && customerInfoDto.isForeignDomesticHelperInd() && "M003".equals(dto.getDocTypeMob())) {
					allOrdDocAssgnDTO.setWaiveReason("M003W006");
				}
				
				allOrdDocAssgnDTOs.add(allOrdDocAssgnDTO);
			}
		}
		return allOrdDocAssgnDTOs;
	}
	
	private boolean isMnp(MnpDTO mnpDto) {
		return "Y".equals(mnpDto.getMnp()) || "A".equals(mnpDto.getMnp());
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	private boolean changedCustomer(MnpDTO mnpDto, CustomerProfileDTO customerInfoDto) {
		if (mnpDto == null) {
			return false;
		}
		if (customerInfoDto == null) {
			return false;
		}
		if ("Prepaid Sim".equals(mnpDto.getCustName())) {
			return false;
		}
		if("Y".equals(mnpDto.getMnp())) {
		if (!mnpDto.getCustIdDocNum().equals(customerInfoDto.getIdDocNum())) {
			return true;
		}
		} else if (mnpDto.isFutherMnp()) {
			if (!mnpDto.getFuthercustIdDocNum().equals(customerInfoDto.getIdDocNum())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean onlyAllowPaperDisMode(MnpDTO mnpDto) {
		if (mnpDto == null) {
			return false;
		}
		if ("10".equals(mnpDto.getChannelId()) || "11".equals(mnpDto.getChannelId())) {
			return false;
		}
		if (this.isMnp(mnpDto)) {
			if (StringUtils.isBlank(mnpDto.getCutoverDateStr())) {
				return true;
			}
		} else {
			if (StringUtils.isBlank(mnpDto.getServiceReqDateStr())) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isConcierge(HttpSession session) {
		return "6".equals(session.getAttribute("baskettypeSession"));
	}
	
	private boolean isMNPMultiSIM(HttpServletRequest request) {
		boolean required = false;
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		List<String> vasList = vasDetailService.getSubscribedVASList(basketDTO.getBasketId(), selectedVasItemList, customerInfoDto.getBrand(), customerInfoDto.getSimType());
		if (vasList != null) {
			if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
				for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
					if (vasList.contains(msi.getItemId()) && msi.getMembers() != null) {
						for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
							if ("A".equals(msim.getMnpInd())) {
								required = true;
							}
						}
					}
				}
			}
		}
		return required;
	}
	
	private boolean isMNPTransferPrepaidMultiSIM(HttpServletRequest request) {
		boolean required = false;
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		List<String> vasList = vasDetailService.getSubscribedVASList(basketDTO.getBasketId(), selectedVasItemList, customerInfoDto.getBrand(), customerInfoDto.getSimType());
		if (vasList != null) {
			if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
				for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
					if (vasList.contains(msi.getItemId()) && msi.getMembers() != null) {
						for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
							if ("A".equals(msim.getMnpInd()) && "Prepaid Sim".equals(msim.getMnpCustName())) {
								required = true;
							}
						}
					}
				}
			}
		}
		return required;
	}
	
	private boolean isMNPTransferMultiSIM(HttpServletRequest request) {
		boolean required = false;
		List<MultiSimInfoDTO> multiSimInfoDTOs = (List<MultiSimInfoDTO>) request.getSession().getAttribute("MultiSimInfoList");
		String[] selectedVasItemList = (String[]) request.getSession().getAttribute("selectedVasItemList");
		BasketDTO basketDTO = (BasketDTO)MobCcsSessionUtil.getSession(request, "basket" );
		CustomerProfileDTO customerInfoDto = (CustomerProfileDTO) request.getSession().getAttribute("customer");
		
		List<String> vasList = vasDetailService.getSubscribedVASList(basketDTO.getBasketId(), selectedVasItemList, customerInfoDto.getBrand(), customerInfoDto.getSimType());
		if (vasList != null) {
			if (multiSimInfoDTOs != null && multiSimInfoDTOs.size() > 0) {
				for (MultiSimInfoDTO msi: multiSimInfoDTOs) {
					if (vasList.contains(msi.getItemId()) && msi.getMembers() != null) {
						for (MultiSimInfoMemberDTO msim: msi.getMembers()) {
							if ("A".equals(msim.getMnpInd()) && !"Prepaid Sim".equals(msim.getMnpCustName())) {
								if (StringUtils.isBlank(msim.getMnpDocNo())) {
									required = true;
								} else {
									if (msim.getMnpDocNo() != null && customerInfoDto != null && !msim.getMnpDocNo().equals(customerInfoDto.getIdDocNum())) {
										required = true;
									}
								}
							}
						}
					}
				}
			}
		}
		return required;
	}
	
	private BigDecimal computeDepositAmount(List<DepositDTO> depositList) {
		BigDecimal amount = BigDecimal.ZERO;
		if (depositList == null) 
			return amount;
		
		for (DepositDTO deposit: depositList) {
			BigDecimal itemAmt = deposit.getDepositAmount();
			if (!"Y".equals(deposit.getWaiveInd())) {
				amount = amount.add(itemAmt);
			}
		}
		return amount;
	}
}
