package com.bomwebportal.mob.ds.web;

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
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.dto.MnpDTO;
import com.bomwebportal.dto.MobileSimInfoDTO;
import com.bomwebportal.dto.MultiSimInfoDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.OrderMobDTO;
import com.bomwebportal.dto.PaymentDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.dto.ui.SupportDocUI;
import com.bomwebportal.dto.ui.SupportDocUI.GenerateSpringboardForm;
import com.bomwebportal.dto.ui.SupportDocUI.SpringboardForm;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.service.StockService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ds.dto.MobDsPaymentUpfrontDTO;
import com.bomwebportal.mob.ds.dto.MobDsStockItemDTO;
import com.bomwebportal.mob.ds.service.MobDsOrderService;
import com.bomwebportal.mob.ds.ui.MobDsCancelOrderUI;
import com.bomwebportal.mobquota.service.MobQuotaService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.MobileDetailService;
import com.bomwebportal.service.MultiSimInfoService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.service.VasDetailService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;
import com.bomwebportal.web.SupportDocController;

public class MobDsCancelOrderController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private MobDsOrderService mobDsOrderService;
	private StockService stockService;
	private OrderService orderService;
	private CustomerProfileService customerProfileService;
	private MobileDetailService mobileDetailService;
	private VasDetailService vasDetailService;
	private SupportDocService supportDocService;
	private MultiSimInfoService multiSimInfoService;
	private MobCcsOrderRemarkService remarkService;
	private MobQuotaService mobQuotaService;
	
	public StockService getStockService() {
		return stockService;
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public MobDsOrderService getMobDsOrderService() {
		return mobDsOrderService;
	}

	public void setMobDsOrderService(MobDsOrderService mobDsOrderService) {
		this.mobDsOrderService = mobDsOrderService;
	}
	   
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public OrderService getOrderService() {
		return orderService;
	}

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}

	public MobileDetailService getMobileDetailService() {
		return mobileDetailService;
	}

	public void setMobileDetailService(MobileDetailService mobileDetailService) {
		this.mobileDetailService = mobileDetailService;
	}

	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}

	public MultiSimInfoService getMultiSimInfoService() {
		return multiSimInfoService;
	}

	public void setMultiSimInfoService(MultiSimInfoService multiSimInfoService) {
		this.multiSimInfoService = multiSimInfoService;
	}

	public MobCcsOrderRemarkService getRemarkService() {
		return remarkService;
	}

	public void setRemarkService(MobCcsOrderRemarkService remarkService) {
		this.remarkService = remarkService;
	}

	public MobQuotaService getMobQuotaService() {
		return mobQuotaService;
	}

	public void setMobQuotaService(MobQuotaService mobQuotaService) {
		this.mobQuotaService = mobQuotaService;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		MobDsCancelOrderUI ui = (MobDsCancelOrderUI) command;
		String actionType = ui.getActionType();
		String nextView = "mobdsordersearch.html";
		OrderDTO orderDto = orderService.getOrder(ui.getOrderId());
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String userName = salesUserDto.getUsername();
		
		orderService.backupOrder(ui.getOrderId());
		
		try {
			mobQuotaService.cancelQuotaUsage("S", ui.getOrderId(), salesUserDto.getUsername());
		} catch (Exception e) {
			logger.error("Exception caught in cancelQuotaUsage", e);
		}
		
		if ("RELEASE".equalsIgnoreCase(actionType)) {
			remarkService.insertOrderRemark(userName, ui.getOrderId(), "CANCEL RELEASE ORDER");
			orderService.updateBomWebOrderStatus(ui.getOrderId(), BomWebPortalConstant.BWP_ORDER_CANCELLED, "N", "N", "C500");
			if (this.orderService.isMultiSimOrder(ui.getOrderId())){
				this.orderService.updateMultiSimMemberStatus(ui.getOrderId(), "ALL", BomWebPortalConstant.BWP_MULTISIM_ORDER_CANCELLED, null, null, "AP");
			}
			for (MobDsStockItemDTO stockItem : ui.getStockItemList()) {
				stockService.updateStockInventory(stockItem.getItemCode(), stockItem.getItemSerial(), "27");
				stockService.updateBomWebStockAssgnStatus("24", ui.getOrderId(), stockItem.getItemCode(), stockItem.getItemSerial());
			}
			orderService.updateDsMsisdnStatus(ui.getMsisdn(), 2, ui.getOrderId(), orderDto.getSalesCd(), orderDto.getAppInDate());
		} else if ("CANCEL".equalsIgnoreCase(actionType)) {
			remarkService.insertOrderRemark(userName, ui.getOrderId(), "CANCEL REFUND ORDER");
			orderService.updateBomWebOrderStatus(ui.getOrderId(), BomWebPortalConstant.BWP_ORDER_CANCELLING, "N", "N", 
					StringUtils.isBlank(orderDto.getOcid()) ? "C400" : "C500");
			if (this.orderService.isMultiSimOrder(ui.getOrderId())){
				this.orderService.cancelMultiSimMember(ui.getOrderId(),"AP");
			}
			for (MobDsStockItemDTO stockItem : ui.getStockItemList()) {
				stockService.updateStockInventory(stockItem.getItemCode(), stockItem.getItemSerial(), stockItem.getStatusId());
				stockService.updateBomWebStockAssgnStatus("25", ui.getOrderId(), stockItem.getItemCode(), stockItem.getItemSerial());
			}
			orderService.updateDsMsisdnStatus(ui.getMsisdn(), 2, ui.getOrderId(), orderDto.getSalesCd(), orderDto.getAppInDate());
		} else if ("CLONE".equalsIgnoreCase(actionType)) {
			remarkService.insertOrderRemark(userName, ui.getOrderId(), "CANCEL RECREATE ORDER");
			String orderId = ui.getOrderId();
			////////////////////////////////////////////////////////////////////////
			//Load Original Order Details
			CustomerProfileDTO customerInfoDto = customerProfileService.getCustomerProfile(orderId);
			MobDsPaymentUpfrontDTO paymentUpfrontDto = orderService.getPaymentUpfront(orderId);
			MnpDTO mnpDto = orderService.getMnp(orderId);
			PaymentDTO paymentDto = orderService.getPayment(orderId);
			MobileSimInfoDTO mobileSimInfo = orderService.getSim(orderId);
			mobileSimInfo.setStockAssgnList(orderService.getStockAssgnListByOrderId(orderId));
			String salesChannelId = mobDsOrderService.getSalesChannelId(orderId);
			List<ComponentDTO> componentList = orderService.getComponentList(orderId);
			String[] basketbrandModel = orderService.getBasketBrandModelInfoList(orderId);
			VasDetailDTO vasDetail = orderService.getVasDetailDTO(orderId, orderDto.getAppInDate());
			OrderMobDTO orderMobDTO = this.orderService.getOrderMobDTO(orderId);
			String locale = RequestContextUtils.getLocale(request).toString();
			List<MultiSimInfoDTO> multiSimInfoDTOs = multiSimInfoService.getMultiSimInfoDTO(orderId, locale, Utility.date2String(orderDto.getAppInDate(), "dd/MM/yyyy"), salesChannelId, orderDto.getShopCode(), customerInfoDto.getBrand(), customerInfoDto.getSimType());
			if (orderMobDTO != null) {
				orderDto.setNfcInd(orderMobDTO.getNfcInteger());
				vasDetail.setSimExtraFunction(orderMobDTO.getNfcInteger());
			}
			String selectedBasketId = basketbrandModel[0];
			String selectedBrandId = basketbrandModel[1];
			String selectedModelId = basketbrandModel[2];
			String selectedColorId = basketbrandModel[3];
			HSTradeDescDTO hsTradeDesc = mobileDetailService.getHSTradeDesc(orderId);
			SupportDocUI supportDocUI = this.getSupportDocUI(orderId, selectedBasketId, orderDto, mnpDto, customerInfoDto, paymentDto, mobileSimInfo);
			
			////////////////////////////////////////////////////////////////////////
			//Cancel Original Order Details
			if (StringUtils.isBlank(orderDto.getOcid()) 
					&& (BomWebPortalConstant.BWP_ORDER_QAPROCESS.equals(orderDto.getOrderStatus()) 
					|| BomWebPortalConstant.BWP_ORDER_REVIEWING.equals(orderDto.getOrderStatus())
					|| BomWebPortalConstant.BWP_ORDER_INITIAL.equals(orderDto.getOrderStatus()) 
					|| BomWebPortalConstant.BWP_ORDER_REJECTED.equals(orderDto.getOrderStatus()))) {
				orderService.updateBomWebOrderStatus(ui.getOrderId(), BomWebPortalConstant.BWP_ORDER_CANCELLED, "N", "N", "C500");
				for (MobDsStockItemDTO stockItem : ui.getStockItemList()) {
					stockService.updateStockInventory(stockItem.getItemCode(), stockItem.getItemSerial(), "27");
					stockService.updateBomWebStockAssgnStatus("24", ui.getOrderId(), stockItem.getItemCode(), stockItem.getItemSerial());
				}
			} else {
				orderService.updateBomWebOrderStatus(ui.getOrderId(), BomWebPortalConstant.BWP_ORDER_CANCELLING, "N", "N", "C500");
				for (MobDsStockItemDTO stockItem : ui.getStockItemList()) {
					stockService.updateStockInventory(stockItem.getItemCode(), stockItem.getItemSerial(), "27");
					stockService.updateBomWebStockAssgnStatus("25", ui.getOrderId(), stockItem.getItemCode(), stockItem.getItemSerial());
				}
			}
			if (this.orderService.isMultiSimOrder(ui.getOrderId())){
				this.orderService.cancelMultiSimMember(ui.getOrderId(),"AP");
			}
			
			orderService.updateDsMsisdnStatus(ui.getMsisdn(), 2, ui.getOrderId(), orderDto.getSalesCd(), orderDto.getAppInDate());
			orderService.updateBomWebPaymentTransStatus(orderId, "TRANSFERRED");
			
			////////////////////////////////////////////////////////////////////////
			//Prepare New Order Details
			orderDto.setCloneOrderId(orderDto.getOrderId());
			orderDto.setOrderId("");
			orderDto.setOcid("");
			orderDto.setOrderStatus(BomWebPortalConstant.BWP_ORDER_INITIAL);
			
			////////////////////////////////////////////////////////////////////////
			//Save Order Details into DB			
			request.getSession().setAttribute("orderIdSession", "");
			request.getSession().setAttribute("cloneOrderId", orderId);
			request.getSession().setAttribute("customer", customerInfoDto);
			
			Boolean originalPrvicyInd= customerInfoDto.isPrivacyInd();
			request.getSession().setAttribute("originalPrvicyInd", originalPrvicyInd);//add 20130322
			request.getSession().setAttribute("payment", paymentDto);
			request.getSession().setAttribute("MNP", mnpDto);
			request.getSession().setAttribute("MobileSimInfo", mobileSimInfo);
			request.getSession().setAttribute("orderDtoOriginal", orderDto);
			request.getSession().setAttribute("bomsalesuser", salesUserDto);
			if ("10".equals(salesChannelId) || "11".equals(salesChannelId)) {
				request.getSession().setAttribute("paymentUpfront", paymentUpfrontDto);
			}
			request.getSession().setAttribute("brandSession", selectedBrandId);
			request.getSession().setAttribute("modelSession", selectedModelId);
			request.getSession().setAttribute("colorSession", selectedColorId);
			request.getSession().setAttribute("basketSession", selectedBasketId);
			request.getSession().setAttribute("MultiSimInfoList", multiSimInfoDTOs);

			String[] selectedItemList = (String[]) orderService.getSelectedItemList(orderId).toArray(new String[0]);
			request.getSession().setAttribute("selectedVasItemList", selectedItemList);
			request.getSession().setAttribute("componentList", componentList);

			// add by eliot 20110527
			request.getSession().setAttribute("hsTradeDescByRecall",hsTradeDesc);
			// add by wilson 20120413 for process with original button
			BasketDTO basketDTO = vasDetailService.getBasketAttribute(
					selectedBasketId, Utility.date2String(
							orderDto.getAppInDate(),
							BomWebPortalConstant.DATE_FORMAT));
			MobCcsSessionUtil.setSession(request, "basket", basketDTO);
			request.getSession().setAttribute("baskettypeSession",
					basketDTO.getBasketTypeId());// wilson 20120417PRD
			request.getSession().setAttribute(
					"appDate",
					Utility.date2String(orderDto.getAppInDate(),
							BomWebPortalConstant.DATE_FORMAT));
			request.getSession().setAttribute(SupportDocController.SESSION_SUPPORTDOC, supportDocUI);
			MobCcsSessionUtil.setSession(request, "vasDetail", vasDetail);

			String uid = Utility.getUid();
			request.getSession().setAttribute("sbuid", uid);
			request.setAttribute("sbuid", uid);

			return new ModelAndView(new RedirectView("summary.html?sbuid="
					+ uid));
			
			
		}
		String attrUid=(String)request.getParameter("sbuid");
		request.getSession().removeAttribute("sessionOrderList");
		ModelAndView modelAndView =  new ModelAndView(new RedirectView(nextView));
		modelAndView.addObject("sbuid", attrUid);
		
		return modelAndView;
	}
	
	protected Object formBackingObject(HttpServletRequest request) throws ServletException{		
		String orderId = (String) request.getParameter("orderId");
		MobDsCancelOrderUI ui = mobDsOrderService.getMobDsCancelOrderUI(orderId);
		return ui;
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		referenceData.put("bomsalesuser", salesUserDto);
		return referenceData;
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
		if (!mnpDto.getCustIdDocNum().equals(customerInfoDto.getIdDocNum())) {
			return true;
		}
		return false;
	}
	
	private SupportDocUI getSupportDocUI(String orderId, String selectedBasketId, OrderDTO orderDto, 
			MnpDTO mnpDto, CustomerProfileDTO customerInfoDto, PaymentDTO paymentDto, MobileSimInfoDTO mobileSimInfo) {
		/************************ Create SupportDocUI *****************************/
		SupportDocUI supportDocUI = new SupportDocUI();
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
					required = this.mobileDetailService.isTradeDescExist(
							selectedBasketId, new Date());
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
					required = isMnp(mnpDto);
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
				
			case MOBILE_SAFETY_PHONE: {
				String[] selectedItemList = (String[]) orderService.getSelectedItemList(orderId).toArray(new String[0]);
				if (this.vasDetailService.isItemsInGroup(selectedBasketId, selectedItemList, orderDto.getAppInDate(), "100000103")) {
					required = true;
					orderDto.setMobileSafetyPhone(true);				
				}
				break;	
			}
			case NFC_SIM: {
				//if (this.vasDetailService.hasNFCSim(orderId)) {
				if (this.vasDetailService.isExtraFunctionSim(mobileSimInfo.getItemCd())) {
					required = true;
					//orderDto.setNfcSim(true);
				}
				break;
			}
			/*case OCTOPUS_SIM: {
				if (this.vasDetailService.hasOctopusSim(orderId)) {
					required = true;
					orderDto.setOctopusSim(true);				
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
		/************************ Create SupportDocUI (End) ************************/
		return supportDocUI;
	}
}
