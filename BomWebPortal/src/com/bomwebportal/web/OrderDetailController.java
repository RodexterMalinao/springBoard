package com.bomwebportal.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.AccountDTO;
import com.bomwebportal.dto.AllDocDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.dto.AllDocDTO.Type;
import com.bomwebportal.dto.AllOrdDocAssgnDTO;
import com.bomwebportal.dto.AllOrdDocWaiveDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ClubPointDetailDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.DepositDTO;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.IGuardDTO;
import com.bomwebportal.dto.ItemDisplayDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobBillMediaDTO;//Paper bill Athena 20130925
import com.bomwebportal.dto.MobBuoQuotaDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.QuotaPlanInfoUI;
import com.bomwebportal.dto.ServicePlanReportDTO;
import com.bomwebportal.dto.SignoffDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.VasMrtSelectionDTO;
import com.bomwebportal.dto.VasOnetimeAmtDTO;
import com.bomwebportal.dto.ui.DepositUI;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.dto.ui.SupportDocUI.GenerateSpringboardForm;
import com.bomwebportal.dto.ui.SupportDocUI.SpringboardForm;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockAssgnDTO;
import com.bomwebportal.mob.ccs.service.MobCcsApprovalLogService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO;
import com.bomwebportal.mob.ds.dto.MobDsQaRecordDTO;
import com.bomwebportal.mob.ds.service.MobDsMrtManagementService;
import com.bomwebportal.mob.ds.service.MobDsOrderService;
import com.bomwebportal.mob.ds.service.MobDsQaRecordService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.DepositService;
import com.bomwebportal.service.IGuardService;
import com.bomwebportal.service.ItemDisplayService;
import com.bomwebportal.service.ItemFuncMobService;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.MobileSimInfoService;
import com.bomwebportal.service.MultiSimInfoService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.QuotaPlanInfoService;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;


public class OrderDetailController implements Controller {
	
	private static final String ITEM_SELECTION_GROUP_ID_HELPER_CARE = "6666666664";
	private static final String ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE= "6666666665";
	private static final String PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID = "6666666663";

	protected final Log logger = LogFactory.getLog(getClass());
	private static final String LOB = "MOB";

	private OrderService orderService;
	private CustomerProfileService customerProfileService;
	private VasDetailService vasDetailService;
	private MobileDetailService mobileDetailService;
	private SupportDocService supportDocService;
	private MobCcsOrderRemarkService remarkService;
	private MobDsOrderService mobDsOrderService;
	private MobDsQaRecordService mobDsQaRecordService;
	private MobDsMrtManagementService mobDsMrtManagementService;
	private IGuardService iGuardService;
	private DepositService depositService;
	private MultiSimInfoService multiSimInfoService;
	private QuotaPlanInfoService quotaPlanInfoService;
	private ItemDisplayService itemDisplayService;
	private ItemFuncMobService itemFuncMobService;
	private MobCcsApprovalLogService mobCcsApprovalLogService;
	private MobileSimInfoService mobileSimInfoService;
	public IGuardService getiGuardService() {
		return iGuardService;
	}

	public void setiGuardService(IGuardService iGuardService) {
		this.iGuardService = iGuardService;
	}

	public MultiSimInfoService getMultiSimInfoService() {
		return multiSimInfoService;
	}

	public void setMultiSimInfoService(MultiSimInfoService multiSimInfoService) {
		this.multiSimInfoService = multiSimInfoService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
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

	public MobCcsOrderRemarkService getRemarkService() {
		return remarkService;
	}
	public void setRemarkService(MobCcsOrderRemarkService remarkService) {
		this.remarkService = remarkService;
	}
	
	public MobDsOrderService getMobDsOrderService() {
		return mobDsOrderService;
	}

	public void setMobDsOrderService(MobDsOrderService mobDsOrderService) {
		this.mobDsOrderService = mobDsOrderService;
	}
	
	public MobDsQaRecordService getMobDsQaRecordService() {
		return mobDsQaRecordService;
	}

	public void setMobDsQaRecordService(MobDsQaRecordService mobDsQaRecordService) {
		this.mobDsQaRecordService = mobDsQaRecordService;
	}

	public MobDsMrtManagementService getMobDsMrtManagementService() {
		return mobDsMrtManagementService;
	}
	public void setMobDsMrtManagementService(MobDsMrtManagementService mobDsMrtManagementService) {
		this.mobDsMrtManagementService = mobDsMrtManagementService;
	}
	
	public DepositService getDepositService() {
		return depositService;
	}

	public void setDepositService(DepositService depositService) {
		this.depositService = depositService;
	}
	
	public QuotaPlanInfoService getQuotaPlanInfoService() {
		return quotaPlanInfoService;
	}

	public void setQuotaPlanInfoService(QuotaPlanInfoService quotaPlanInfoService) {
		this.quotaPlanInfoService = quotaPlanInfoService;
	}
	
	public ItemDisplayService getItemDisplayService() {
		return itemDisplayService;
	}

	public void setItemDisplayService(ItemDisplayService itemDisplayService) {
		this.itemDisplayService = itemDisplayService;
	}
	
	public ItemFuncMobService getItemFuncMobService() {
		return itemFuncMobService;
	}

	public void setItemFuncMobService(ItemFuncMobService itemFuncMobService) {
		this.itemFuncMobService = itemFuncMobService;
	}
	
	public MobCcsApprovalLogService getMobCcsApprovalLogService() {
		return mobCcsApprovalLogService;
	}

	public void setMobCcsApprovalLogService(MobCcsApprovalLogService mobCcsApprovalLogService) {
		this.mobCcsApprovalLogService = mobCcsApprovalLogService;
	}

	public MobileSimInfoService getMobileSimInfoService() {
		return mobileSimInfoService;
	}

	public void setMobileSimInfoService(MobileSimInfoService mobileSimInfoService) {
		this.mobileSimInfoService = mobileSimInfoService;
	}

	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("OrderDetailController handleRequest called ");

		String locale = RequestContextUtils.getLocale(request).toString();
		// get info from Parameter
		String orderId = (String) request.getParameter("orderId");
		String orderStatus = (String) request.getParameter("status");
		String sbuid = (String) request.getParameter("sbuid");
		String paymentCheck = (String) request.getParameter("paymentCB");
		String supportDocCheck = (String) request.getParameter("supportDocCB");
		
		// get info from session
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession()
				.getAttribute("bomsalesuser");
		
		if (paymentCheck != null && paymentCheck.length() == 1) {
			//Save to bomweb_order
			orderService.updateDsPaymentCheck(orderId, salesUserDto.getUsername(), paymentCheck);
		}
		
		if (supportDocCheck != null && supportDocCheck.length() == 1) {
			//Save to bomweb_order
			orderService.updateDsSupoortDocCheck(orderId, salesUserDto.getUsername(), supportDocCheck);
		}
		
		/* QA disable sign off */
		List<MobDsQaRecordDTO> mobDsQaRecordDTOList = mobDsQaRecordService.getQARecord(orderId);
		if ((mobDsQaRecordDTOList != null) && (mobDsQaRecordDTOList.size() > 0)) {
			MobDsQaRecordDTO mobDsQaRecordDTO = mobDsQaRecordDTOList.get(0);
			if ("Y".equalsIgnoreCase(mobDsQaRecordDTO.getMustQ()) ||
					mobDsQaRecordDTO.getQaOption() != 3) {
				request.getSession().setAttribute("qaDisableSignOff", true);
			}
		}

		
		
		// get info from DB
		OrderDTO orderDto = orderService.getOrder(orderId);// (OrderDTO)request.getSession().getAttribute("orderDto");
		request.getSession().setAttribute("orderDtoOriginal", orderDto);
		CustomerProfileDTO customerInfoDto = customerProfileService.getCustomerProfile(orderId);// (CustomerProfileDTO)request.getSession().getAttribute("customer");
		MnpDTO mnpDto = orderService.getMnp(orderId);// (MnpDTO)request.getSession().getAttribute("MNP");
		logger.info("mnpDTO check is white list: " + mnpDto.getCheckIsWhiteList());
		customerInfoDto.setCheckIsWhiteList(mnpDto.getCheckIsWhiteList());
		
		mnpDto.setMsisdnLvl(orderDto.getMsisdnLvl());
		
		mnpDto.setMnpExtendAuthInd(mobCcsApprovalLogService.isApproval(orderId,"AU19")?"Y":null);
		
		
		VasMrtSelectionDTO vasMrtSelectionDTO = orderService.getVasMrtSelectionDTO(orderId);
		VasMrtSelectionDTO ssMrtSelectionDTO = orderService.getSsMrtSelectionDTO(orderId);
		
		PaymentDTO paymentDto = orderService.getPayment(orderId);// (PaymentDTO)request.getSession().getAttribute("payment");
		MobileSimInfoDTO mobileSimInfo = orderService.getSim(orderId);// (MobileSimInfoDTO)request.getSession().getAttribute("MobileSimInfo");
		
		/*Add by whitney 20160826*/
		mobileSimInfo.setRefSalesCentre(orderDto.getRefSalesCentre());
		mobileSimInfo.setRefSalesId(orderDto.getRefSalesId());
		mobileSimInfo.setRefSalesName(orderDto.getRefSalesName());
		mobileSimInfo.setRefSalesTeam(orderDto.getRefSalesTeam());
		
		customerInfoDto.setAmend(false);
		mnpDto.setAmend(false);
		mobileSimInfo.setAmend(false);
		mobileSimInfo.setStockAssgnList(orderService.getStockAssgnListByOrderId(orderId));
		mobileSimInfo.setChannelId(salesUserDto.getChannelId());
		MobDsPaymentUpfrontDTO paymentUpfrontDto = orderService.getPaymentUpfront(orderId);
		String salesChannelId = mobDsOrderService.getSalesChannelId(orderId);
		request.getSession().setAttribute("salesChannelId", salesChannelId);
		String dsApprovable = mobDsOrderService.isOrderSupportApprovable(orderId);
		request.getSession().setAttribute("dsApprovable", dsApprovable);
		AccountDTO accountDto = orderService.getAccount(orderId);
		customerInfoDto.setBillLang(accountDto.getBillLang());
		List<ComponentDTO> componentList = orderService
				.getComponentList(orderId);
		String[] basketbrandModel = orderService
				.getBasketBrandModelInfoList(orderId);
		List<MultiSimInfoDTO> multiSimInfoDTOs = multiSimInfoService.getMultiSimInfoDTO(orderId, locale, Utility.date2String(orderDto.getAppInDate(), "dd/MM/yyyy"), salesChannelId, orderDto.getShopCode(), customerInfoDto.getBrand(), customerInfoDto.getSimType());
		List<ClubPointDetailDTO> clubPointDetailDTO = orderService.getClubPointDetailsByOrderId(orderId);
		if ("N".equalsIgnoreCase(accountDto.getIsNew())) {// combine account
			customerInfoDto.setAcctType("current");
			customerInfoDto.setAcctNum(accountDto.getAcctNum());
			customerInfoDto.setAcctName(accountDto.getAcctName());
			customerInfoDto.setActiveMobileNum(accountDto.getSrvNum());
			customerInfoDto.setBomCustNum(orderDto.getBomCustNum());
			customerInfoDto.setMobCustNum(orderDto.getMobCustNum());
			customerInfoDto.setBillPeriod(accountDto.getBillPeriod());
		} 
		else
		{
			customerInfoDto.setAcctType("new");
		}
			
		
	
				
		VasDetailDTO vasDetail = orderService.getVasDetailDTO(orderId, orderDto.getAppInDate());
		mobileSimInfo.setSimWaiveReason(vasDetail.getSimWaiveReason());
		mobileSimInfo.setSimCharge(vasDetail.getSimCharge());
		mobileSimInfo.setSimWaivable(vasDetail.isSimWaivable());
		mobileSimInfo.setRpWaiveReason(vasDetail.getRpWaiveReason());
		mobileSimInfo.setRpCharge(vasDetail.getRpCharge());
		OrderMobDTO orderMobDTO = this.orderService.getOrderMobDTO(orderId);
		if (orderMobDTO != null) {
			orderDto.setNfcInd(orderMobDTO.getNfcInteger());
			vasDetail.setSimExtraFunction(orderMobDTO.getNfcInteger());
			customerInfoDto.setCareDmSupInd(orderMobDTO.getCareDmSupInd());
			customerInfoDto.setCareOptInd(orderMobDTO.getCareOptInd());
			customerInfoDto.setCareStatus(orderMobDTO.getCareStatus());
			if (StringUtils.isNotEmpty(orderMobDTO.getCareStatus())){
				if ("I".equals(orderMobDTO.getCareStatus())){
					customerInfoDto.setCustomercareDmSupInd(true);
				}else{
					customerInfoDto.setCustomercareDmSupInd(false);
				}
			}
			mobileSimInfo.setSalesLocation(orderMobDTO.getLocationCd());
			customerInfoDto.setHkbnInd(orderMobDTO.getHkbnInd());
		}
		
		String selectedBasketId = basketbrandModel[0];
		String selectedBrandId = basketbrandModel[1];
		String selectedModelId = basketbrandModel[2];
		String selectedColorId = basketbrandModel[3];

		orderDto.setBasketId(selectedBasketId);// add 20110815
		
		String applicationDate = Utility.date2String(orderDto.getAppInDate(),"dd/MM/yyyy");
		List<VasDetailDTO> vasHSRPList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasReportUseDetailList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasGifsReportUseDetailList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasOptionalReportUseList = new ArrayList<VasDetailDTO>();
		List<VasDetailDTO> vasHSRPReportUseList = new ArrayList<VasDetailDTO>();
		// add by eliot 20110527
		HSTradeDescDTO hsTradeDesc = new HSTradeDescDTO();
		/************************ Bill media option (Start) Paper bill Athena 20130925************************/

		List<MobBillMediaDTO> billMediaList  = LookupTableBean.getInstance().getBillMediaOption();
		List<MobBillMediaDTO> selectedBillMediaList = new ArrayList<MobBillMediaDTO>(); 
		for(MobBillMediaDTO mbm: billMediaList) {
			for (String billCd: customerInfoDto.getSelectedBillMedia()) {
				if (billCd.equals(mbm.getCodeId())) {
					selectedBillMediaList.add(mbm);
				}
			}
		}
		/************************ Bill media option (End) ************************/
		
		ModelDTO mobileDetail = new ModelDTO();
		if (!"".equals(selectedBrandId) && !"".equals(selectedModelId)) {
			if (selectedBrandId != null && selectedModelId != null) {
				// get selectedmodelDetail
				mobileDetail = mobileDetailService.getMobileDetail(locale,
						selectedBrandId, selectedModelId, applicationDate);

			}
		}
		// selectedBasketId

		if (!"".equals(selectedBasketId) && selectedBasketId != null) {

			// HTML HS+VAS
			vasHSRPList = vasDetailService.getVasDetailSelectedList(locale,
					orderId);

			// REPORT use HS
			vasReportUseDetailList = vasDetailService.getReportUseRPHSRPList(
					locale, selectedBasketId, "SS_FORM_RP", orderId); // rate
																		// plan

			// REPORT use VAS
			vasHSRPReportUseList = vasDetailService.getReportUseVasDetailtList(
					locale, orderId, selectedBasketId);

			// REPORT use optional VAS
			vasOptionalReportUseList = vasDetailService
					.getReportUseVasOptionalDetailtList(locale, orderId,
							selectedBasketId);

			// REPORT use free Gifs VAS
			vasGifsReportUseDetailList = vasDetailService
					.getReportUseFreeGifsDetailtList(locale, orderId,
							selectedBasketId);

		}
		// add by eliot 20110527
		hsTradeDesc = mobileDetailService.getHSTradeDesc(orderId);
		// add by eliot 20110627
		mobileSimInfo.setSalesName(orderDto.getSalesName());

		// add by herbert 20110707, for advance order
		if (orderDto.getAoInd() == null
				|| orderDto.getAoInd().equalsIgnoreCase("N")) {
			mobileSimInfo.setAoInd(null);
		} else {
			mobileSimInfo.setAoInd("Y");
		}

		/*String applicationDate = Utility.date2String(orderDto.getAppInDate(),
				"dd/MM/yyyy");*/
		// mnpDto.setServiceReqDate(orderDto.getSrvReqDate());
		// 20110615
		// mnpDto.setServiceReqDateStr(Utility.date2String(mnpDto.getServiceReqDate(),
		// "dd/MM/yyyy"));

		/************************ Create AccountDTO *****************************/

		/************************ Create AccountDTO (End) ************************/
		/************************ Create SupportDocUI *****************************/
		SupportDocUI supportDocUI = new SupportDocUI();
		// TODO: setGenerateSpringForms
		String[] gSelectedItemList = (String[]) orderService.getSelectedItemList(orderId).toArray(new String[0]);
		List<GenerateSpringboardForm> generateSpringboardForms = new ArrayList<GenerateSpringboardForm>();
		for (SpringboardForm springboardForm : SpringboardForm.values()) {
			GenerateSpringboardForm generateSpringboardForm = new GenerateSpringboardForm();
			boolean required = false;
			switch (springboardForm) {
			case MOBILE_APPLICATION_FORM:
			case SERVICE_GUIDE:
				// By Default
				required = true;
				break;
			case TRADE_DESCRIPTIONS_FOR_ELECTIONIC_PRODUCTS: {
				// Device description defined in w_hs_trade_desc
				if (StringUtils.isNotBlank(selectedBasketId)) {
					required = this.mobileDetailService.isTradeDescExist(selectedBasketId, new Date());
					if(required==false){
						required =vasDetailService.hasProductionInfo(gSelectedItemList);//check VAS item
					}
				}
				break;
			}
			case PCCW_CONCIERGE_SERVICE_FORM: {
				// select Rate plan Category : Concierge
				BasketDTO basketDTO = vasDetailService.getBasketAttribute(
						selectedBasketId, Utility.date2String(
								orderDto.getAppInDate(),
								BomWebPortalConstant.DATE_FORMAT));
				required = "6".equals(basketDTO.getBasketTypeId());
				break;
			}
			case APPLICATION_IN_RESPECT_OF_MOBILE_SECRETARIAL_SERVICE: {
				// Basket offer inculde secretarial service
				String[] selectedItemList = (String[]) orderService
						.getSelectedItemList(orderId).toArray(new String[0]);
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
			case MNP_APPLICATION_FORM: {
				// MNP in Mobile number
				// MnpDTO mnpDto = (MnpDTO)
				// request.getSession().getAttribute("MNP");
				if (mnpDto != null) {
					//For Direct Sales Only, 20131002
					if (salesUserDto.getChannelId() == 10 || salesUserDto.getChannelId() == 11){
						required = isMnp(mnpDto) || mnpDto.isFutherMnp();
					} else {
						required = isMnp(mnpDto);
					}
				}
				break;
			}
			case CHANGE_OF_SERVICE_FORM: {
				// MNP's owner doc ID different from customer or
				// New MRT + MNP
				// MnpDTO mnpDto = (MnpDTO)
				// request.getSession().getAttribute("MNP");
				if (mnpDto != null) {
					if (isMnp(mnpDto)) {
						if ("Y".equals(mnpDto.getMnp())) {
							if (StringUtils.isBlank(mnpDto.getCustIdDocNum())) {
								required = true;
							} else {
								// CustomerProfileDTO customerInfoDto =
								// (CustomerProfileDTO)
								// request.getSession().getAttribute("customer");
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
				break;
			}
			case THRID_PARTY_AUTOPAY_FORM: {
				// Payment method, choose Autopay with Third party
				// PaymentDTO paymentDto = (PaymentDTO)
				// request.getSession().getAttribute("payment");
				if (paymentDto != null) {
					if ("A".equals(paymentDto.getPayMethodType())) {
						if ("Y".equals(paymentDto.getThirdPartyInd())) {
							required = true;
						}
					}
				}
				break;
			}
			case IGUARD_FORM_LDS: {
				if (StringUtils.isNotBlank(orderDto.getiGuardSerialNo())) {
					required = true;
				}
			}
				break;
				
			case IGUARD_FORM_AD: {
				if (StringUtils.isNotBlank(orderDto.getiGuardSerialNo())) {
					required = true;
				}
			}
				break;
				
			case IGUARD_FORM_UAD: {
				if (StringUtils.isNotBlank(orderDto.getiGuardSerialNo())) {
					required = true;
				}
			}
				break;
				
			case MOBILE_SAFETY_PHONE: {
				String[] selectedItemList = (String[]) orderService.getSelectedItemList(orderId).toArray(new String[0]);
				if (this.vasDetailService.isItemsInGroup(selectedBasketId, selectedItemList, Utility.string2Date(applicationDate), "100000103")) {
					required = true;
					orderDto.setMobileSafetyPhone(true);				
				}
				break;	
			}
			case NFC_SIM: {
				if (this.vasDetailService.isExtraFunctionSim(mobileSimInfo.getItemCd())) {
					required = true;
					//orderDto.setNfcSim(true);				
				}
				break;
			}
			case TRAVEL_INSURANCE_FORM:
			{
				String[] selectedItemList = (String[]) orderService
						.getSelectedItemList(orderId).toArray(new String[0]);
				if (selectedItemList!= null && this.vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE,selectedItemList)) {
					required = true;
				}
				break;
			}
			case HELPERCARE_INSURANCE_FORM:
			{	
				String[] selectedItemList = (String[]) orderService
						.getSelectedItemList(orderId).toArray(new String[0]);
				if (selectedItemList!= null && this.vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_HELPER_CARE,selectedItemList)) {
					required = true;
				}
				break;
			}
			case PROJECT_EAGLE_FORM:
			{	
				String[] selectedItemList = (String[]) orderService
						.getSelectedItemList(orderId).toArray(new String[0]);
				if (selectedItemList!= null && this.vasDetailService.existInSelectionGrpList(PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID,selectedItemList)) {
					required = true;
				}
				break;
			}
			/*case OCTOPUS_SIM: {
				if (this.vasDetailService.hasOctopusSim(orderId)) {
					required = true;
					//orderDto.setOctopusSim(true);				
				}
				break;
			}*/
			}
			generateSpringboardForm.setSpringboardForm(springboardForm);
			generateSpringboardForm.setRequired(required);
			generateSpringboardForms.add(generateSpringboardForm);
		}
		supportDocUI.setGenerateSpringboardForms(generateSpringboardForms);
		supportDocUI.setCollectMethod(orderDto.getCollectMethod());
		supportDocUI.setAllOrdDocAssgnDTOs(this.supportDocService
				.getInUseAllOrdDocAssgnDTOALL(orderId));
		supportDocUI.setDisMode(orderDto.getDisMode());
		supportDocUI.setEsigEmailAddr(orderDto.getEsigEmailAddr());
		supportDocUI.setEsigEmailLang(orderDto.getEsigEmailLang());
		if (orderMobDTO !=null ){
			orderDto.setManualAfNo(orderMobDTO.getManualAfNo());
			supportDocUI.setManualAfNo(orderMobDTO.getManualAfNo());
		}
		/************************ Create SupportDocUI (End) ************************/

		/*********** START get ServicePlanReportDTO **************/
		ServicePlanReportDTO servicePlanReport = new ServicePlanReportDTO();

		servicePlanReport.setMainServDtls(vasReportUseDetailList);
		servicePlanReport.setVasDtls(vasHSRPReportUseList);
		servicePlanReport.setVasFreeGifsDtls(vasGifsReportUseDetailList);
		servicePlanReport.setVasOptionalDtls(vasOptionalReportUseList);
		servicePlanReport.setHandsetDeviceAmount(Long.toString(orderService
				.getHandsetDeviceAmount(orderId)));
		servicePlanReport.setFirstMonthFee(Long.toString(orderService
				.getFirstMonthFee(orderId)));
		servicePlanReport
				.setFirstMonthServiceLicenceFee(Long.toString(orderService
						.getFirstMonthServiceLicenceFee(orderId)));
		
		List<VasOnetimeAmtDTO> vasOnetimeAmtList
			= orderService.getVasOnetimeAmtList(locale, orderId);
		int vasOnetimeAmtFee = 0;
		servicePlanReport.setVasOnetimeAmtList(vasOnetimeAmtList);
		if (vasOnetimeAmtList != null
				&& vasOnetimeAmtList.size() >= 0) {
			for (VasOnetimeAmtDTO dto: vasOnetimeAmtList) {
				vasOnetimeAmtFee += Integer.parseInt(dto.getVasOnetimeAmt());
			}
		}
		servicePlanReport.setVasOnetimeAmtFee("" + vasOnetimeAmtFee);
		
		servicePlanReport.setRebateList(orderService.getRebateList(locale,
				orderId));

		int billPeriod = Integer.parseInt(accountDto.getBillPeriod());
		servicePlanReport.setBillPeriod("" + (billPeriod - 1));// edit 20110621,
																// past to
																// report value
																// =
																// accountDto.getBillPeriod()-1

		servicePlanReport.setPenaltyinfo(orderService
				.getPenaltyInfoList(orderId));// [0]contract_period,
												// [1]penalty_type
												// [2]penalty_amt
		// System.out.println("selectedModelId:"+selectedModelId);
		if (!"".equals(selectedModelId) && selectedModelId != null) {
			servicePlanReport.setHandsetDeviceInfo(orderService
					.getHandsetDeviceDescription(locale, selectedBrandId,
							selectedModelId, selectedColorId));
		}
		servicePlanReport.setSecSrvContractPeriod(orderService
				.getSecSrvContractPeriod(orderId));

		// add 20110615, set setConciergeInd
		/*
		 * if (("".equals(mnpDto.getCutoverDateStr()) ||
		 * mnpDto.getCutoverDateStr()==null) &&
		 * ("".equals(mnpDto.getServiceReqDateStr()) ||
		 * mnpDto.getServiceReqDateStr()==null)){ logger.info("ConciergeInd=Y");
		 * servicePlanReport.setConciergeInd("Y");
		 * 
		 * }else{ logger.info("ConciergeInd=N");
		 * servicePlanReport.setConciergeInd("N"); }
		 */
		servicePlanReport
				.setConciergeInd(orderService.getConciergeInd(orderId));// edit
																		// 20110620
		logger.info("ConciergeInd:" + servicePlanReport.getConciergeInd());

		// add by Eliot 20110822
		servicePlanReport.setBasketType(orderService.getBasketType(orderId));// edit
																				// 20110822
		logger.info("BasketType:" + servicePlanReport.getBasketType());

		/*********** END get ServicePlanReportDTO **************/

		/******************* for testing create BOM order(END) ***************************/
		logger.info("orderId:" + orderId);
		logger.info("orderDto.getOrderStatus():" + orderDto.getOrderStatus());

		/************************ Update AllOrdDocAssgnDTO ************************/
		if (!this.isEmpty(supportDocUI.getAllOrdDocAssgnDTOs())) {
			for (AllOrdDocAssgnDTO dto : supportDocUI.getAllOrdDocAssgnDTOs()) {
				dto.setOrderId(orderId);
			}
		}
		List<AllDocDTO> allDocDtos = this.supportDocService
				.getAllDocDTOKnownByTypeAndAppDate(LOB, Type.I,
						orderDto.getAppInDate());
		Map<DocType, List<AllOrdDocWaiveDTO>> waiveReasons = new HashMap<DocType, List<AllOrdDocWaiveDTO>>();
		for (DocType docType : DocType.values()) {
			List<AllOrdDocWaiveDTO> reasons = this.supportDocService
					.getAllOrdDocWaiveDTOALL(LOB, docType);
			if (!this.isEmpty(reasons)) {
				waiveReasons.put(docType, reasons);
			}
		}
		/************************ Update AllOrdDocAssgnDTO (End) ************************/

		/********************** DepositDTO *********************************/
		List<DepositDTO> depositDTOList = depositService.findDepositDTOByOrderId(orderId);
		DepositUI depositUI = new DepositUI();
		depositUI.setDepositDTOList(depositDTOList);
		
		/********************** DepositDTO *********************************/

		SuperOrderDTO superOrderDto = new SuperOrderDTO();
		superOrderDto.setOrderSalesCd(orderDto.getSalesCd());
		superOrderDto.setOrderId(orderDto.getOrderId());
		superOrderDto.setOrderShopCd(orderDto.getShopCode());
		
		request.getSession().setAttribute("superOrderDto", superOrderDto);
		
		if ((BomWebPortalConstant.BWP_ORDER_INITIAL.equals(orderStatus)
				&& BomWebPortalConstant.BWP_ORDER_INITIAL.equals(orderDto
						.getOrderStatus())) || 
				(orderId.startsWith("D") && 
						(BomWebPortalConstant.BWP_ORDER_REVIEWING.equals(orderDto.getOrderStatus())
						|| BomWebPortalConstant.BWP_ORDER_SUCCESS.equals(orderDto.getOrderStatus())
						|| BomWebPortalConstant.BWP_ORDER_REJECTED.equals(orderDto.getOrderStatus())
						|| BomWebPortalConstant.BWP_ORDER_QAPROCESS.equals(orderDto.getOrderStatus())
						|| BomWebPortalConstant.BWP_ORDER_FAILED.equals(orderDto.getOrderStatus()))) 
				&& !"ENQUIRY".equalsIgnoreCase(request.getParameter("action"))
				&& !"REVIEW".equalsIgnoreCase(request.getParameter("action"))) {
			BasketDTO basketDTO = vasDetailService.getBasketAttribute(
					selectedBasketId, Utility.date2String(
							orderDto.getAppInDate(),
							BomWebPortalConstant.DATE_FORMAT));
			
			if (salesUserDto.getChannelId() == 10 || salesUserDto.getChannelId() == 11) {
				if (StringUtils.isNotBlank(basketDTO.getHsPosItemCd())) {
					if (mobileSimInfo.getStockAssgnList() != null) {
						for (StockAssgnDTO stockAssgn: mobileSimInfo.getStockAssgnList()) {
							if (basketDTO.getHsPosItemCd().equals(stockAssgn.getItemCode()) 
									&&  (stockAssgn.getAoInd() == null || !"Y".equalsIgnoreCase(stockAssgn.getAoInd()))) {
								mobileSimInfo.setImei(stockAssgn.getItemSerial());
							}
						}
					}
				}
			}
			
			
	/*		*//***********QuotaPlanInfo**************************//*
			List<MobBuoQuotaDTO> mobBuoQuotaDtoList =quotaPlanInfoService.getBomWebBuoQuota(orderId);
			Map<String,QuotaPlanInfoUI> quotaPlanInfoMap = new HashMap<String,QuotaPlanInfoUI>();
			for (MobBuoQuotaDTO mobBuoQuotaDto : mobBuoQuotaDtoList){
				QuotaPlanInfoUI quotaPlanInfoUI = new QuotaPlanInfoUI();
				ItemDisplayDTO  itemDisplayDTO = itemDisplayService.getItemDisplay(Integer.parseInt(mobBuoQuotaDto.getItemId()), "en", "ITEM_SELECT");
				quotaPlanInfoUI.setAutoTopUpInd(mobBuoQuotaDto.getAutoTopUpInd());
				quotaPlanInfoUI.setMaxTopUpTimes(mobBuoQuotaDto.getMaxCntAutoTopUp());
				quotaPlanInfoUI.setItemId(mobBuoQuotaDto.getItemId());
				quotaPlanInfoUI.setSelectedQuotaPlanOption(mobBuoQuotaDto.getBuoId());
				quotaPlanInfoUI.setSelectedQuotaPlanOption(mobBuoQuotaDto.getBuoId());
				quotaPlanInfoUI.setEngDesc(mobBuoQuotaDto.getEngDesc());
				quotaPlanInfoUI.setItemDisplayDTO(itemDisplayDTO);
				quotaPlanInfoMap.put(mobBuoQuotaDto.getItemId(), quotaPlanInfoUI);
				
			}
			request.getSession().setAttribute("quotaPlanInfoMapSession", quotaPlanInfoMap);			
			
			*//***********QuotaPlanInfoEnd***********************//*
			*/
			
			  
		    /***********QuotaPlanInfo**************************/
			List<MobBuoQuotaDTO> mobBuoQuotaDtoList =quotaPlanInfoService.getBomWebBuoQuota(orderId);
			//System.out.println("order id in saved seesion " + orderId);
			//System.out.println("List size " + mobBuoQuotaDtoList.size());
			Map<String,QuotaPlanInfoUI> quotaPlanInfoMap = new HashMap<String,QuotaPlanInfoUI>();
			for (MobBuoQuotaDTO mobBuoQuotaDto : mobBuoQuotaDtoList){
				QuotaPlanInfoUI quotaPlanInfoUI = new QuotaPlanInfoUI();
				ItemDisplayDTO  itemDisplayDTO = itemDisplayService.getItemDisplay(Integer.parseInt(mobBuoQuotaDto.getItemId()), "en", "ITEM_SELECT");
				quotaPlanInfoUI.setAutoTopUpInd(mobBuoQuotaDto.getAutoTopUpInd());
				quotaPlanInfoUI.setMaxTopUpTimes(mobBuoQuotaDto.getMaxCntAutoTopUp());
				quotaPlanInfoUI.setItemId(mobBuoQuotaDto.getItemId());
				quotaPlanInfoUI.setSelectedQuotaPlanOption(mobBuoQuotaDto.getBuoId());
				quotaPlanInfoUI.setEngDesc(mobBuoQuotaDto.getEngDesc());
				quotaPlanInfoUI.setItemDisplayDTO(itemDisplayDTO);
				quotaPlanInfoMap.put(mobBuoQuotaDto.getItemId(), quotaPlanInfoUI);
				
			}
			request.getSession().setAttribute("quotaPlanInfoMapSession", quotaPlanInfoMap);			
			
			/***********QuotaPlanInfoEnd***********************/
			
			/******** SAVE recall session ******************************/
			request.getSession().setAttribute("orderIdSession", orderId);
			request.getSession().setAttribute("numType", customerInfoDto.getNumType()); //DENNIS MIP3
			request.getSession().setAttribute("simType", customerInfoDto.getSimType());
			request.getSession().setAttribute("brandType", customerInfoDto.getBrand());
			
			
			request.getSession().setAttribute("selectedBillMediaList", selectedBillMediaList);  //Paper bill Athena 20130925
			request.getSession().setAttribute("customer", customerInfoDto);
			//System.out.println("customerInfoDto.getAcctName"+customerInfoDto.getAcctName());
			//System.out.println("customerInfoDto.getAcctName2"+customerInfoDto.getBillPeriod());
			//System.out.println("customerInfoDto.getAcctName3"+customerInfoDto.getCustomerNum());
			//System.out.println("customerInfoDto.getAcctName4"+customerInfoDto.getAcctNum());
			Boolean originalPrvicyInd= customerInfoDto.isPrivacyInd();
			request.getSession().setAttribute("originalPrvicyInd", originalPrvicyInd);//add 20130322
			request.getSession().setAttribute("ServicePlanReport",
					servicePlanReport);
			request.getSession().setAttribute("payment", paymentDto);
			request.getSession().setAttribute("MNP", mnpDto);
			request.getSession().setAttribute("MobileSimInfo", mobileSimInfo);
			request.getSession().setAttribute("MultiSimInfoList", multiSimInfoDTOs);
			request.getSession().setAttribute("clubPointDetailSession", clubPointDetailDTO);
			request.getSession().setAttribute("theClubLogin", customerInfoDto.getTheClubLogin());
			request.getSession().setAttribute("OrderDto", orderDto);
			request.getSession().setAttribute("bomsalesuser", salesUserDto);
			if ("10".equals(salesChannelId) || "11".equals(salesChannelId)) {
				String staffId = orderDto.getSalesCd();
				String salesChannelCd = mobDsMrtManagementService.getSalesChannelCd(staffId, orderDto.getAppInDate());
				request.getSession().setAttribute("salesChannelCd", salesChannelCd);
				request.getSession().setAttribute("paymentUpfront", paymentUpfrontDto);
			}
			request.getSession().setAttribute("brandSession", selectedBrandId);
			request.getSession().setAttribute("modelSession", selectedModelId);
			request.getSession().setAttribute("colorSession", selectedColorId);
			request.getSession().setAttribute("basketSession", selectedBasketId);

			String[] selectedItemList = (String[]) orderService.getSelectedItemList(orderId).toArray(new String[0]);
			request.getSession().setAttribute("selectedVasItemList", selectedItemList);
			
			if (selectedItemList != null) {
				for (String itemId : selectedItemList) {
					if (itemFuncMobService.getItemFuncAssgnMobDTO(itemId, "EX06") != null) {
						if (vasMrtSelectionDTO!=null){
							vasMrtSelectionDTO.setVasItemId(itemId);
							vasMrtSelectionDTO.setFuncId("EX06");
							request.getSession().setAttribute("vasMrtSelectionSession", vasMrtSelectionDTO);
						}
					}
					
					if (itemFuncMobService.getItemFuncAssgnMobDTO(itemId, "EX07") != null) {
						if (ssMrtSelectionDTO!=null){
							ssMrtSelectionDTO.setVasItemId(itemId);
							ssMrtSelectionDTO.setFuncId("EX07");
							request.getSession().setAttribute("ssMrtSelectionSession", ssMrtSelectionDTO);
						}
					}
				}
			}
			
			request.getSession().setAttribute("componentList", componentList);

			// add by eliot 20110527
			request.getSession().setAttribute("hsTradeDescByRecall",
					hsTradeDesc);
			// for process with original button

			MobCcsSessionUtil.setSession(request, "basket", basketDTO);
			request.getSession().setAttribute("baskettypeSession",
					basketDTO.getBasketTypeId());
			request.getSession().setAttribute(
					"appDate",
					Utility.date2String(orderDto.getAppInDate(),
							BomWebPortalConstant.DATE_FORMAT));
			// supportDoc
			request.getSession().setAttribute(
					SupportDocController.SESSION_SUPPORTDOC, supportDocUI);
			
			// Deposit Info ...
			MobCcsSessionUtil.setSession(request, "depositInfo", depositUI);
			
			/* test uid */
			String uid = Utility.getUid();
			request.getSession().setAttribute("sbuid", uid);
			request.setAttribute("sbuid", uid);
			MobCcsSessionUtil.setSession(request, "vasDetail", vasDetail);
			/* test uid */
			
			if("AMEND".equalsIgnoreCase(request.getParameter("action"))){
				logUid(uid, salesUserDto.getUsername(), orderId, "AMEND");
				return new ModelAndView(new RedirectView("summary.html?sbuid=" + uid + "&action=AMEND"));
			} else {
				logUid(uid, salesUserDto.getUsername(), orderId, "RECALL");
				return new ModelAndView(new RedirectView("summary.html?sbuid=" + uid));
			}
		} else {

			// preview
			/***********QuotaPlanInfo**************************/
			List<MobBuoQuotaDTO> mobBuoQuotaDtoList =quotaPlanInfoService.getBomWebBuoQuota(orderId);
			System.out.println("order id in saved seesion " + orderId);
			System.out.println("List size " + mobBuoQuotaDtoList.size());
			Map<String,QuotaPlanInfoUI> quotaPlanInfoMap = new HashMap<String,QuotaPlanInfoUI>();
			for (MobBuoQuotaDTO mobBuoQuotaDto : mobBuoQuotaDtoList){
				QuotaPlanInfoUI quotaPlanInfoUI = new QuotaPlanInfoUI();
				ItemDisplayDTO  itemDisplayDTO = itemDisplayService.getItemDisplay(Integer.parseInt(mobBuoQuotaDto.getItemId()), "en", "ITEM_SELECT");
				quotaPlanInfoUI.setAutoTopUpInd(mobBuoQuotaDto.getAutoTopUpInd());
				quotaPlanInfoUI.setMaxTopUpTimes(mobBuoQuotaDto.getMaxCntAutoTopUp());
				quotaPlanInfoUI.setItemId(mobBuoQuotaDto.getItemId());
				quotaPlanInfoUI.setSelectedQuotaPlanOption(mobBuoQuotaDto.getBuoId());
				quotaPlanInfoUI.setEngDesc(mobBuoQuotaDto.getEngDesc());
				quotaPlanInfoUI.setItemDisplayDTO(itemDisplayDTO);
				quotaPlanInfoMap.put(mobBuoQuotaDto.getItemId(), quotaPlanInfoUI);
				
			}
			request.getSession().setAttribute("quotaPlanInfoMapSession", quotaPlanInfoMap);			
			/*************************Quota Plan END **********************/
			
			request.getSession().setAttribute("orderdetailCustomer",
					customerInfoDto);
			request.getSession().setAttribute("orderdetailServicePlanReport",
					servicePlanReport);
			request.getSession().setAttribute("orderdetailPayment", paymentDto);
			request.getSession().setAttribute("orderdetailMNP", mnpDto);
			request.getSession().setAttribute("orderdetailMobileSimInfo",
					mobileSimInfo);
			request.getSession().setAttribute("orderdetailOrderDto", orderDto);
			request.getSession().setAttribute("MultiSimInfoList", multiSimInfoDTOs);
			request.getSession().setAttribute("clubPointDetailSession", clubPointDetailDTO);
			request.getSession().setAttribute("theClubLogin", customerInfoDto.getTheClubLogin());
			request.getSession().setAttribute("orderdetailBomsalesuser",
					salesUserDto);
			if ("10".equals(salesChannelId)) {
				String staffId = orderDto.getSalesCd();
				String salesChannelCd = mobDsMrtManagementService.getSalesChannelCd(staffId, orderDto.getAppInDate());
				request.getSession().setAttribute("orderdetailSalesChannelCd", salesChannelCd);
				request.getSession().setAttribute("orderdetailPaymentUpfront", paymentUpfrontDto);
			}
			// iguard
			request.getSession().setAttribute("orderdetailComponentList", componentList);
			// add by eliot 20110527
			request.getSession().setAttribute("orderdetailHsTradeDescByRecall",
					hsTradeDesc);
			// request.getSession().setAttribute("basketSession",
			// selectedBasketId);// mark for error
			// 20110815
			// request.getSession().setAttribute("orderIdSession",
			// orderId);// error for error 20110815

			request.getSession().setAttribute("orderdetailSupportDoc",
					supportDocUI);
			BasketDTO basketDTO = vasDetailService.getBasketAttribute(
					selectedBasketId, Utility.date2String(
							orderDto.getAppInDate(),
							BomWebPortalConstant.DATE_FORMAT));
			
		
			
			MobCcsSessionUtil.setSession(request, "orderdetailBasket", basketDTO);
			request.getSession().setAttribute("vasMrtSelectionSession", vasMrtSelectionDTO);
			request.getSession().setAttribute("ssMrtSelectionSession", ssMrtSelectionDTO);
			/**************************************/
			ModelAndView myView = new ModelAndView("orderdetail");
			myView.addObject("orderId", orderId);
			
			myView.addObject("basket", basketDTO);

			myView.addObject("selectedModelId", selectedModelId);
			myView.addObject("mobileDetail", mobileDetail); // return
															// mobileDetail for
															// image

			myView.addObject("vasHSRPList", vasHSRPList);
			myView.addObject("selectedBillMediaList", selectedBillMediaList); //Paper bill Athena 20130925
			myView.addObject("customerInfoDto", customerInfoDto); // return
																	// customer
																	// profile
																	// DTO
			
			if (StringUtils.isNotEmpty(customerInfoDto.getBillPeriod())) {
				int billDate = Integer.parseInt(customerInfoDto.getBillPeriod()) - 1;
				myView.addObject("billDate", billDate);
			}
			
			myView.addObject("mnpDto", mnpDto);
			myView.addObject("paymentDto", paymentDto);
			myView.addObject("applicationDate", applicationDate);

			myView.addObject("salesUserDto", salesUserDto);// for
			// report use
			myView.addObject("servicePlanReport", servicePlanReport);

			if ("10".equals(salesChannelId)) {
				String staffId = orderDto.getSalesCd();
				String salesChannelCd = mobDsMrtManagementService.getSalesChannelCd(staffId, orderDto.getAppInDate());
				myView.addObject("salesChannelCd", salesChannelCd);
				myView.addObject("paymentUpfrontDto", paymentUpfrontDto);
			}
			//add by nancy to show payment method for DS
			
			// for signatureUrl
			// String signatureUrl = "signoff.html";
			String signatureUrl = "customersign.html";

			String userAgent = StringUtils.defaultString(request
					.getHeader("user-agent"));
			logger.info("**** - user-agent ************"
					+ request.getHeader("user-agent"));
			if (userAgent.toUpperCase().indexOf("ANDROID") != -1) {
				signatureUrl = StringUtils.left(request.getRequestURL()
						.toString(), request.getRequestURL().toString()
						.indexOf(request.getRequestURI()))
						+ StringUtils.left(request.getRequestURI(), request
								.getRequestURI().lastIndexOf("/"))
						+ "/androidsignoff.html;jsessionid="
						+ request.getSession().getId() + "?orderId=" + orderId;
				logger.info("**** - signatureUrl ************" + signatureUrl);

				signatureUrl = "intent:#Intent;action=biz.binarysolutions.signature.CAPTURE;S.biz.binarysolutions.signature.Title="
						+ "Please%20sign%20-%20Order%20Confirmation%20["
						+ orderId
						+ "]"
						+ ";i.biz.binarysolutions.signature.StrokeWidth=6;S.biz.binarysolutions.signature.InvoiceId="
						+ orderId
						+ ";S.biz.binarysolutions.signature.UploadURL="
						+ URLEncoder.encode(signatureUrl, "UTF-8") + ";end";
			}

			logger.info("**** - signatureUrl ************" + signatureUrl);

			String sessionId = RequestContextHolder.currentRequestAttributes()
					.getSessionId();
			myView.addObject("currentSessionId", sessionId);

			myView.addObject("signatureURL", signatureUrl);
			// end for signatureUrl
			logger.debug("OrderDetailController() End");

			myView.addObject("orderDto", orderDto); // add by herbert 20110707,
													// for advance order
			myView.addObject("mobileSimInfo", mobileSimInfo);
			myView.addObject("supportDoc", supportDocUI);

			myView.addObject("allDocDtos", allDocDtos);
			myView.addObject("waiveReasons", waiveReasons);
			String uid = Utility.getUid();
			request.getSession().setAttribute("sbuid", uid);
			request.setAttribute("sbuid", uid);
			
			logUid(uid, salesUserDto.getUsername(), orderId, "PREVIEW");

			myView.addObject("sbuid", uid);
			
			String salesLocation = "";
			Map<String,String> dsLocationList = mobileSimInfoService.getDSLocationList();
			if (mobileSimInfo.getSalesLocation()!=null) {
				salesLocation = dsLocationList.get(mobileSimInfo.getSalesLocation());
			}
			
			myView.addObject("salesLocation", salesLocation);
			
			/*iGuard */
			
			SignoffDTO iGuardSignDto = new SignoffDTO();
			IGuardDTO iGuardDto = new IGuardDTO();
			IGuardDTO iGuardADDto = new IGuardDTO();
			IGuardDTO iGuardUADDto = new IGuardDTO();
			String enableUAD = "N";
		
			List<String> vasList = new ArrayList<String>();
			for (VasDetailDTO vas :vasOptionalReportUseList) {
				vasList.add(vas.getItemId());
			}

			String [] vasGuardList = (String[]) vasList.toArray(new String[2]);
			List<String> srvPlanList=iGuardService.isIGuardOrder(basketDTO.getBasketId(), vasGuardList, orderDto.getAppInDate());
		    for(int i=0; i<srvPlanList.size();i++){
		    	String srvPlanType=srvPlanList.get(i);
	 		 	if("LDS".equalsIgnoreCase(srvPlanType)){
			    	iGuardDto = iGuardService.getRsIGuardDTO(customerInfoDto,
							basketDTO, mobileSimInfo, iGuardSignDto, mnpDto,
							orderDto, componentList,srvPlanType);
		    	}else if("AD".equalsIgnoreCase(srvPlanType)){
		    		iGuardADDto = iGuardService.getRsIGuardDTO(customerInfoDto,
							basketDTO, mobileSimInfo, iGuardSignDto, mnpDto,
							orderDto, componentList,srvPlanType);
		    	}else if ("UAD".equalsIgnoreCase(srvPlanType)){
		    		enableUAD = "Y";
		    		iGuardUADDto = iGuardService.getRsIGuardDTO(customerInfoDto,
							basketDTO, mobileSimInfo, iGuardSignDto, mnpDto,
							orderDto, componentList,srvPlanType);
		    	}
		    }
		    myView.addObject("iGuardList", srvPlanList);
			myView.addObject("iGuardDto", iGuardDto);
			myView.addObject("iGuardADDto", iGuardADDto);
			myView.addObject("iGuardUadDto",iGuardUADDto);
			myView.addObject("enableUAD",enableUAD);
			/*iGuard */
			
			/************************ HKTcare validation *************************/
			boolean isTravelInsurance=false;
			boolean isHelperCareInsurance=false;
			String travelInsuranceDays = "";
			String[] selectedItemList = (String[]) orderService.getSelectedItemList(orderId).toArray(new String[0]);
			if(selectedItemList!=null){
				String travelInsuranceItemId = vasDetailService.getItemIdExistInSelectionGrpList(ITEM_SELECTION_GROUP_ID_TRAVEL_INSURANCE, selectedItemList);
				isTravelInsurance = StringUtils.isNotBlank(travelInsuranceItemId);
				if (isTravelInsurance) {
					travelInsuranceDays = itemDisplayService.getTravelInsuranceDays(travelInsuranceItemId);
				}
		    	isHelperCareInsurance =vasDetailService.existInSelectionGrpList(ITEM_SELECTION_GROUP_ID_HELPER_CARE,selectedItemList);
			}
			myView.addObject("isTravelInsurance", isTravelInsurance);
			myView.addObject("travelInsuranceDays", travelInsuranceDays);
	    	myView.addObject("isHelperCareInsurance", isHelperCareInsurance);
	    	/************************ Project Eagle validation *************************/
	    	boolean isProjectEagle=false;
	    	if(selectedItemList!=null){
	    		isProjectEagle =vasDetailService.existInSelectionGrpList(PROJECT_EAGLE_ITEM_SELECTTION_GROUP_ID,selectedItemList);
	    	}
	    	myView.addObject("isProjectEagle", isProjectEagle);
	    	
	    	boolean enableHKTCareEmail = Utility.isCodeExist("ENABLE_HKTCARE_EMAIL", "Y");
	    	myView.addObject("enableHKTCareEmail", enableHKTCareEmail);
	    	
			boolean amendAF = orderService.getBOMandSBOrderInfo(orderId);
			myView.addObject("toggleAmendAF", amendAF);

			myView.addObject("depositInfo", depositUI);
			myView.addObject("componentList",componentList);
	
			logger.info("OrderDetailController() End");
			
			myView.addObject("theClubLogin", customerInfoDto.getTheClubLogin());

			return myView;

		}

	}

	private boolean isMnp(MnpDTO mnpDto) {
		return "Y".equals(mnpDto.getMnp()) || "A".equals(mnpDto.getMnp());
	}

	private boolean changedCustomer(MnpDTO mnpDto,
			CustomerProfileDTO customerInfoDto) {
		if (mnpDto == null) {
			return false;
		}
		if (customerInfoDto == null) {
			return false;
		}
		if ("Prepaid Sim".equals(mnpDto.getCustName())) {
			return false;
		}
		if (!"HKID".equals(customerInfoDto.getIdDocType())) {
			return false;
		}
		if (StringUtils.isBlank(mnpDto.getCustIdDocNum())
				|| StringUtils.isBlank(customerInfoDto.getIdDocNum())) {
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

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	
	private int logUid (String uid, String userName, String orderId, String callFmWhere) {
		boolean logUid = false;
		
		List<CodeLkupDTO> checkUidCdLkupList
			= LookupTableBean.getInstance().getCodeLkupList().get("LOG_UID");
		
		if (checkUidCdLkupList != null
				&& !checkUidCdLkupList.isEmpty()) {
			logUid = "Y".equalsIgnoreCase(checkUidCdLkupList.get(0).getCodeDesc()) ?
						true : false;
		} else {
			logUid = false;
		}
		
		if (logUid) {
			String msg = "ORDER DETAIL - " + callFmWhere + " UID: " + uid;
			return remarkService.insertOrderRemark(userName, orderId, msg);
		}
		
		return 0;
	}
	
}
