package com.bomltsportal.service;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.RemarkHelper;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.CsPortalIdRegrArqDTO;
import com.bomwebportal.lts.dto.DeviceDetailDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.CsPortalService;
import com.bomwebportal.lts.service.LtsDeviceService;
import com.bomwebportal.lts.service.order.ImsSbOrderService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.OrderSignoffLtsServiceImpl;
import com.bomwebportal.lts.service.order.RemarkLtsServiceImpl;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;

public class OrderSignoffServiceImpl extends OrderSignoffLtsServiceImpl implements OrderSignoffService {

	private ImsSbOrderService imsSbOrderService;
	private DnPoolService dnPoolService;
	private OrderPostSubmitService orderPostSubmitService;
	private BasketDetailService basketDetailService;
	private LtsDeviceService ltsDeviceService;
	private RemarkLtsServiceImpl remarkLtsService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private ItemDetailService itemDetailService;
	private CsPortalService csPortalService;
	
	public CsPortalService getCsPortalService() {
		return csPortalService;
	}

	public void setCsPortalService(CsPortalService csPortalService) {
		this.csPortalService = csPortalService;
	}

	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}

	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public DnPoolService getDnPoolService() {
		return dnPoolService;
	}

	public void setDnPoolService(DnPoolService dnPoolService) {
		this.dnPoolService = dnPoolService;
	}

	public ImsSbOrderService getImsSbOrderService() {
		return imsSbOrderService;
	}

	public void setImsSbOrderService(ImsSbOrderService imsSbOrderService) {
		this.imsSbOrderService = imsSbOrderService;
	}
	
	public OrderPostSubmitService getOrderPostSubmitService() {
		return orderPostSubmitService;
	}

	public void setOrderPostSubmitService(OrderPostSubmitService orderPostSubmitService) {
		this.orderPostSubmitService = orderPostSubmitService;
	}		

	public BasketDetailService getBasketDetailService() {
		return basketDetailService;
	}

	public void setBasketDetailService(BasketDetailService basketDetailService) {
		this.basketDetailService = basketDetailService;
	}

	public LtsDeviceService getLtsDeviceService() {
		return ltsDeviceService;
	}

	public void setLtsDeviceService(LtsDeviceService ltsDeviceService) {
		this.ltsDeviceService = ltsDeviceService;
	}

	public RemarkLtsServiceImpl getRemarkLtsService() {
		return remarkLtsService;
	}

	public void setRemarkLtsService(RemarkLtsServiceImpl remarkLtsService) {
		this.remarkLtsService = remarkLtsService;
	}

	@Transactional
	public SbOrderDTO signoffOrder(OrderCaptureDTO orderCapture, String sessionId) {
		
//		this.initialize(this.orderRetrieveLtsService.retrieveSbOrder(orderCapture.getSbOrder().getOrderId(), false), BomLtsConstant.USER_ID, BomLtsConstant.OLS_SHOP_CD);
		SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(orderCapture.getSbOrder().getOrderId(), false);
		
		if (!verifySignoff(sbOrder)) {
			return null;
		}
		preSignofferOrder(orderCapture, sbOrder);
		determineWorkQueueNatureForSignoff(sbOrder);
		submitToWorkQueue(sbOrder, BomLtsConstant.USER_ID, BomLtsConstant.OLS_SHOP_CD);
		postSubmitSignoff(sbOrder, sessionId, orderCapture);
		return sbOrder;
	}
	
	private void preSignofferOrder(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {
		initializeSignoff(sbOrder, BomLtsConstant.USER_ID);
		assignSelectedDn(orderCapture);
		generateCustomerRemark(orderCapture, sbOrder, BomLtsConstant.USER_ID);
		generateOrderRemark(sbOrder, BomLtsConstant.USER_ID);
		clearFollowupWorkQueue(sbOrder, BomLtsConstant.USER_ID);
		getServiceActions(sbOrder, BomLtsConstant.USER_ID);
		updateSuspendBomOrder(sbOrder, BomLtsConstant.USER_ID);
		setBuildingMarkerToOrder(orderCapture, sbOrder);
	}

	private void setBuildingMarkerToOrder(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {
		sbOrder.setBuildingMarker(orderCapture.getAddressLookupForm().getBuildingMarker());
	}
	
	private void assignSelectedDn(OrderCaptureDTO orderCapture) {
		dnPoolService.assignDn(orderCapture.getApplicantInfoForm().getSelectedNum());
	}
	
	private void releaseDnList(String sessionId) {
		dnPoolService.releaseDnStatus(sessionId);
	}
	
	private void postSubmitSignoff(SbOrderDTO sbOrder, String sessionId, OrderCaptureDTO orderCapture) {
		if (LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls()) != null )  {
			this.imsSbOrderService.createImsEyeOrder(sbOrder, BomLtsConstant.USER_ID);	
		}
		updateSignoffStatus(sbOrder, BomLtsConstant.USER_ID);
		sbOrder.setSignOffDate(this.orderDetailService.updateSignoffDate(sbOrder.getOrderId()));
		releaseDnList(sessionId);
		
		this.orderPostSubmitService.saveAndSendApplicationForm(sbOrder);	
		
		
		String smsNum = orderCapture.getApplicantInfoForm().getContactMobileNum();
		String lang = orderCapture.getLang();
		//String orderId = sbOrder.getOrderId();
		this.orderPostSubmitService.sendSMSToCustomer(smsNum, lang, sbOrder, orderCapture.getServiceTypeInd());
//		return regMyHktTheClub(orderCapture.getLang());
	}
	
	
	public String regMyHktTheClub (SbOrderDTO sbOrder, String locale) {
		String regCsTargetAcct = null;
		boolean isPremier = StringUtils.equals(sbOrder.getAddress().getAddrInventory().getHktPremier(), "Y");
		if (StringUtils.equals(sbOrder.getCustomers()[0].getCsPortalInd(), "Y") && StringUtils.equals(sbOrder.getCustomers()[0].getTheClubInd(), "Y")) {
			regCsTargetAcct = LtsCsPortalBackendConstant.MY_HKT_AND_THE_CLUB_REGISTER;
		}
		else if (StringUtils.equals(sbOrder.getCustomers()[0].getCsPortalInd(), "Y")) {
			regCsTargetAcct = LtsCsPortalBackendConstant.MY_HKT_REGISTER_ONLY;
		}
		else if (StringUtils.equals(sbOrder.getCustomers()[0].getTheClubInd(), "Y")) {
			regCsTargetAcct = LtsCsPortalBackendConstant.THE_CLUB_REGISTER_ONLY;
		}
		
		if(StringUtils.isEmpty(regCsTargetAcct)){
			return null;
		}
		
		try {
			CsPortalIdRegrArqDTO response = (CsPortalIdRegrArqDTO)csPortalService.regCsIdForTheClubAndHkt(sbOrder, BomLtsConstant.USER_ID, LtsCsPortalBackendConstant.SOURCE_SYSTEM_SPRINGBOARD_ONLINE_SALES, isPremier, regCsTargetAcct);
			if (response == null) {
				logger.warn("regMyHktTheClub response is null SBID:" + sbOrder.getOrderId() + " regCsTargetAcct:" + regCsTargetAcct);
				return null;
			}
			
			StringBuilder sb = new StringBuilder();
			
			if (!LtsCsPortalBackendConstant.CSP_REPLY_SUCCESS.equals(response.getReply())) {
				sb.append(response.getReply() + "\n");
			}
			if (BomLtsConstant.LOCALE_ENG.equals(locale) && StringUtils.isNotBlank(response.getoMyHktResEnMsg())) {
				sb.append(response.getoMyHktResEnMsg() + "\n");
			}
			else if (!BomLtsConstant.LOCALE_ENG.equals(locale) && StringUtils.isNotBlank(response.getoMyHktResZhMsg())) {
				sb.append(response.getoMyHktResZhMsg() + "\n");
			}
			
			if (StringUtils.isNotBlank(response.getoClubResMsg())) {
				sb.append(response.getoClubResMsg() + "\n");
			}
			if (sb.length() == 0) {
				return null;
			}
			return sb.toString();
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return null;
		}
		
	}
	
	private BasketDetailDTO getSelectedBasketDetail(ServiceDetailLtsDTO upgradeServiceDetail) {
		
		for (ItemDetailLtsDTO itemDetail : upgradeServiceDetail.getItemDtls()) {
			if (StringUtils.isNotBlank(itemDetail.getBasketId())) {
				return basketDetailService.getBasket(itemDetail.getBasketId(), BomLtsConstant.LOCALE_ENG, BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC);
			}
		}
		return null;
	}
	
	
	public void generateCustomerRemark(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder, String pUser) {
		
		ServiceDetailLtsDTO eyeServiceDetail = LtsSbHelper.getLtsEyeService(sbOrder);
		ServiceDetailLtsDTO delServiceDetail = LtsSbHelper.getDelServices(sbOrder);
		ServiceDetailLtsDTO ltsServiceDetail = (eyeServiceDetail != null ? eyeServiceDetail : delServiceDetail);
		
		BasketDetailDTO selectedBasket = getSelectedBasketDetail(ltsServiceDetail); 
		DeviceDetailDTO selectedDevice = ltsDeviceService.getDevice(selectedBasket.getType(), orderCapture.getChannelId(), BomLtsConstant.LOCALE_ENG);
		RemarkDetailLtsDTO[] ltsCustomerRemarks = RemarkHelper.createRemarkDetails(RemarkHelper.generateLtsCustomerRemark(sbOrder, 
				selectedDevice, selectedBasket), 
				BomLtsConstant.REMARK_TYPE_CUST_REMARK);
		
		if (ArrayUtils.isNotEmpty(ltsCustomerRemarks)) {
			ltsServiceDetail.appendRemarks(ltsCustomerRemarks);
			for (RemarkDetailLtsDTO remarkDetailLts : ltsCustomerRemarks) {
				remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), ltsServiceDetail.getDtlId());	

			}
		}
	}
	
	public void generateOrderRemark(SbOrderDTO sbOrder, String pUser) {
		
		ServiceDetailLtsDTO eyeServiceDetail = LtsSbHelper.getLtsEyeService(sbOrder);
		ServiceDetailLtsDTO delServiceDetail = LtsSbHelper.getDelServices(sbOrder);
		ServiceDetailLtsDTO ltsServiceDetail = (eyeServiceDetail != null ? eyeServiceDetail : delServiceDetail);
		ServiceDetailOtherLtsDTO imsServiceDetail = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());
		
		BasketDetailDTO selectedBasket = getSelectedBasketDetail(ltsServiceDetail); 
		DeviceDetailDTO selectedDevice = ltsDeviceService.getEyeDevice(selectedBasket.getType(), BomLtsConstant.LOCALE_ENG);
		
		String prepayAmtTxt = getPrepayment(ltsServiceDetail);
		
		RemarkDetailLtsDTO[] ltsOrderRemarks = RemarkHelper.createRemarkDetails(RemarkHelper.generateLtsOrderRemark(sbOrder, 
				selectedDevice, selectedBasket, prepayAmtTxt), BomLtsConstant.REMARK_TYPE_ORDER_REMARK);

		if (ArrayUtils.isNotEmpty(ltsOrderRemarks)) {
			ltsServiceDetail.appendRemarks(ltsOrderRemarks);
			for (RemarkDetailLtsDTO remarkDetailLts : ltsOrderRemarks) {
				remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), ltsServiceDetail.getDtlId());	

			}
		}
		
		if (imsServiceDetail != null) {
			
			RemarkDetailLtsDTO[] imsOrderRemarks = RemarkHelper.createRemarkDetails(RemarkHelper.generateImsOrderRemark(sbOrder, selectedDevice), 
					BomLtsConstant.REMARK_TYPE_ORDER_REMARK);
			
			if (ArrayUtils.isNotEmpty(imsOrderRemarks)) {
				imsServiceDetail.appendRemarks(imsOrderRemarks);
				for (RemarkDetailLtsDTO remarkDetailLts : imsOrderRemarks) {
					remarkLtsService.doSave(remarkDetailLts, pUser, sbOrder.getOrderId(), imsServiceDetail.getDtlId());

				}
			}
		}
	}
	
	private String getPrepayment(ServiceDetailDTO ltsServiceDetail) {
//		for (ItemDetailLtsDTO itemDetail : ltsServiceDetail.getItemDtls()) {
//			if (StringUtils.equals(BomLtsConstant.ITEM_TYPE_PREPAY, itemDetail.getItemType())) {
//				List<ItemDetailDTO> itemDetailList = 
//					itemDetailService.getItems(new String[] { itemDetail.getItemId() }, BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, BomLtsConstant.LOCALE_ENG, false, false);
//				
//				if (itemDetailList != null && !itemDetailList.isEmpty()) {
//					return itemDetailList.get(0).getOneTimeAmtTxt();
//				}
//			}
//		}
		
		if (ltsServiceDetail.getAccount() != null) {
			if (ArrayUtils.isNotEmpty(ltsServiceDetail.getAccount().getPaymethods())) {
				for (PaymentMethodDetailLtsDTO paymentMethod : ltsServiceDetail.getAccount().getPaymethods()) {
					if (StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, paymentMethod.getAction())) {
						return paymentMethod.getPrepayAmt();
					}
				}
			}
		}
		
		return null;
	}
}
