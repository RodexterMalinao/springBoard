package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO.BaCaActionType;
import com.bomwebportal.lts.dto.form.LtsAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketSelectionFormDTO;
import com.bomwebportal.lts.dto.form.LtsBasketServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerIdentificationFormDTO;
import com.bomwebportal.lts.dto.form.LtsDeviceSelectionFormDTO;
import com.bomwebportal.lts.dto.form.LtsMiscellaneousFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.form.LtsOtherVoiceServiceFormDTO.DuplexSrvType;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.form.LtsRecontractFormDTO;
import com.bomwebportal.lts.dto.form.LtsSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.LtsSupportDocFormDTO;
import com.bomwebportal.lts.dto.form.LtsWqRemarkFormDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderAttbDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RecontractLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.service.AddressRolloutService;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsPaymentService;
import com.bomwebportal.lts.service.LtsRelatedPcdOrderService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.bom.ImsServiceProfileAccessService;
import com.bomwebportal.lts.service.bom.OfferProfileLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.LoggingService;
import com.google.common.collect.Lists;

public class OrderRecallServiceImpl implements OrderRecallService {
	
	protected final Log logger = LogFactory.getLog(getClass());

	protected LtsPaymentService ltsPaymentService;
	protected AddressRolloutService addressRolloutService;
	protected LtsRelatedPcdOrderService ltsRelatedPcdOrderService;
	protected LtsOrderDocumentService ltsOrderDocumentService;
	protected LtsBasketService ltsBasketService;
	protected CodeLkupCacheService relationshipCodeLkupCacheService;
	protected LtsOfferService ltsOfferService;
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	protected LoggingService loggingService;
	protected CustomerProfileLtsService customerProfileLtsService;
	protected OfferProfileLtsService offerProfileLtsService;
	private OfferItemService offerItemService;
	private LtsAppointmentService ltsAppointmentService;
	protected ImsServiceProfileAccessService imsServiceProfileAccessService;
	
	
	public OfferProfileLtsService getOfferProfileLtsService() {
		return offerProfileLtsService;
	}

	public void setOfferProfileLtsService(
			OfferProfileLtsService offerProfileLtsService) {
		this.offerProfileLtsService = offerProfileLtsService;
	}

	public OfferItemService getOfferItemService() {
		return offerItemService;
	}

	public void setOfferItemService(OfferItemService offerItemService) {
		this.offerItemService = offerItemService;
	}

	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	public LtsPaymentService getLtsPaymentService() {
		return ltsPaymentService;
	}

	public void setLtsPaymentService(LtsPaymentService ltsPaymentService) {
		this.ltsPaymentService = ltsPaymentService;
	}

	public AddressRolloutService getAddressRolloutService() {
		return addressRolloutService;
	}

	public void setAddressRolloutService(AddressRolloutService addressRolloutService) {
		this.addressRolloutService = addressRolloutService;
	}

	public LtsRelatedPcdOrderService getLtsRelatedPcdOrderService() {
		return ltsRelatedPcdOrderService;
	}

	public void setLtsRelatedPcdOrderService(
			LtsRelatedPcdOrderService ltsRelatedPcdOrderService) {
		this.ltsRelatedPcdOrderService = ltsRelatedPcdOrderService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	public LtsBasketService getLtsBasketService() {
		return ltsBasketService;
	}

	public void setLtsBasketService(LtsBasketService ltsBasketService) {
		this.ltsBasketService = ltsBasketService;
	}

	public CodeLkupCacheService getRelationshipCodeLkupCacheService() {
		return relationshipCodeLkupCacheService;
	}

	public void setRelationshipCodeLkupCacheService(
			CodeLkupCacheService relationshipCodeLkupCacheService) {
		this.relationshipCodeLkupCacheService = relationshipCodeLkupCacheService;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}
	
	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public LoggingService getLoggingService() {
		return loggingService;
	}

	public void setLoggingService(LoggingService loggingService) {
		this.loggingService = loggingService;
	}

	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}

	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}


	public ImsServiceProfileAccessService getImsServiceProfileAccessService() {
		return imsServiceProfileAccessService;
	}

	public void setImsServiceProfileAccessService(
			ImsServiceProfileAccessService imsServiceProfileAccessService) {
		this.imsServiceProfileAccessService = imsServiceProfileAccessService;
	}

	// Recall Order
	public OrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser) {
		SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(sbOrderId, pIsEquiry);
		this.loggingService.logRecallLtsOrder(bomSalesUser.getUsername(), sbOrder);
		return convertSbOrderToOrderCapture(sbOrder, bomSalesUser);
	}

	private OrderCaptureDTO convertSbOrderToOrderCapture(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		if (sbOrder == null) {
			return null;
		}
		
		OrderCaptureDTO orderCapture = new OrderCaptureDTO();
		
		orderCapture.setOrderType(sbOrder.getOrderType());
		orderCapture.setOrderSubType(sbOrder.getOrderSubType());
		
		BasketDetailDTO basketDetail = getSelectedBasketDetail(sbOrder);
		
		orderCapture.setLtsServiceProfile(createDummyServiceDetailProfile(sbOrder));
		orderCapture.setSecondDelServiceProfile(createDummy2ndDelServiceDetailProfile(sbOrder));
		orderCapture.setLtsPaymentForm(createLtsPaymentForm(sbOrder));
		orderCapture.setLtsSalesInfoForm(createLtsSalesInfoForm(sbOrder));
		orderCapture.setLtsOtherVoiceServiceForm(createLtsOtherVoiceServiceForm(sbOrder));
		orderCapture.setLtsModemArrangementForm(createLtsModemArrangementForm(sbOrder, bomSalesUser));
		orderCapture.setLtsCustomerIdentificationForm(createLtsCustomerIdentificationForm(sbOrder));
		orderCapture.setLtsAddressRolloutForm(createLtsAddressRolloutForm(sbOrder)); 
		orderCapture.setLtsMiscellaneousForm(createLtsMiscellaneousForm(sbOrder));
		orderCapture.setLtsSupportDocForm(createLtsSupportDocForm(sbOrder));
		orderCapture.setNewAddressRollout(createAddressRollout(sbOrder));
		orderCapture.setModemTechnologyAissgn(createModemTechnologyAssign(sbOrder));
		orderCapture.setRelatedPcdOrder(createRelatedPcdOrder(sbOrder, orderCapture));
		orderCapture.setSbOrder(sbOrder);
		orderCapture.setOrderAction(LtsConstant.ORDER_ACTION_RECALL);
		orderCapture.setLtsDeviceSelectionForm(createDeviceSelection(sbOrder, basketDetail));
		orderCapture.setLtsBasketSelectionForm(createBasketSelection(sbOrder, basketDetail));
		orderCapture.setLtsRecontractForm(createLtsRecontractForm(sbOrder));
		orderCapture.setLtsWqRemarkForm(createLtsWqRemarkForm(sbOrder));
		orderCapture.setLtsBasketServiceForm(createLtsBasketServiceForm(sbOrder));
		orderCapture.setLtsAppointmentForm(createLtsAppointmentForm(sbOrder, orderCapture, bomSalesUser));
		orderCapture.setSelectedBasket(basketDetail);
		
		return orderCapture;
	}
	
	private LtsWqRemarkFormDTO createLtsWqRemarkForm(SbOrderDTO sbOrder) {
		String wqRemark = createRemark(sbOrder, LtsConstant.REMARK_TYPE_APPROVL_REMARK);
		
		if (StringUtils.isBlank(wqRemark)) {
			return null;
		}

		LtsWqRemarkFormDTO form = new LtsWqRemarkFormDTO();
		form.setWqRemark(wqRemark);
		return form;
	}
	
	public LtsBasketServiceFormDTO createLtsBasketServiceForm(SbOrderDTO sbOrder) {
		LtsBasketServiceFormDTO form = new LtsBasketServiceFormDTO();
		
		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		ItemDetailLtsDTO planItem = LtsSbOrderHelper.getPlanItem(ltsService);
		
		if (planItem != null) {
			form.setPlanItemList(ltsOfferService.getItem(new String[]{planItem.getItemId()}, LtsConstant.DISPLAY_TYPE_ITEM_SELECT, LtsConstant.LOCALE_ENG, true));
		}
		
		for(int i = 0; ltsService.getOrderAttbs() != null && i < ltsService.getOrderAttbs().length; i++){
			OrderAttbDTO attb = ltsService.getOrderAttbs()[i];
			switch(LtsConstant.UpgradeOrderAttb.valueOf(attb.getAttbName())){
				case ALLOW_SELF_PICKUP_DEVICE:
					form.setAllowSelfPickup("Y".equals(attb.getAttbValue()));
			}
			
		}
		
		return form;
	}
	
	public LtsRecontractFormDTO createLtsRecontractForm(SbOrderDTO sbOrder){
		if(LtsSbHelper.isOnlineAcquistionOrder(sbOrder)){
			return null;
		}
		
		ServiceDetailDTO upgradeService = LtsSbOrderHelper.getLtsService(sbOrder);
		if("Y".equals(upgradeService.getRecontractInd()) && upgradeService.getRecontract() != null){
			LtsRecontractFormDTO ltsRecontractForm = new LtsRecontractFormDTO();
			ltsRecontractForm.setDocType(upgradeService.getRecontract().getIdDocType());
			ltsRecontractForm.setDocId(upgradeService.getRecontract().getIdDocNum());
			ltsRecontractForm.setEmailAddr(upgradeService.getRecontract().getEmailAddr());
			ltsRecontractForm.setFirstName(upgradeService.getRecontract().getCustFirstName());
			ltsRecontractForm.setLastName(upgradeService.getRecontract().getCustLastName());
			ltsRecontractForm.setMobileNum(upgradeService.getRecontract().getContactNum());
			ltsRecontractForm.setRelationship(upgradeService.getRecontract().getRelationship());
			ltsRecontractForm.setCustDetailProfile(customerProfileLtsService.getCustByDoc(upgradeService.getRecontract().getIdDocType(), 
					upgradeService.getRecontract().getIdDocNum(), "DRG"));
			
			ltsRecontractForm.setAccountDetailProfile(createRecontractAccountDetailProfileLts(sbOrder, upgradeService.getRecontract()));
			
			ltsRecontractForm.setRecontractMode(upgradeService.getRecontract().getTransMode());
			ltsRecontractForm.setOptOut(StringUtils.equals(upgradeService.getRecontract().getOptOut(), "Y")? true : false);
			
			ltsRecontractForm.setRefundType(((ServiceDetailLtsDTO)upgradeService).getDeceaseType());
			ltsRecontractForm.setDeceasedCase(StringUtils.isNotBlank(((ServiceDetailLtsDTO)upgradeService).getDeceaseType()));
			AccountDetailLtsDTO profileAccount = upgradeService.getProfileAccount();
			if (profileAccount != null) {
				ltsRecontractForm.setNewBillingName(profileAccount.getAcctName());
				ltsRecontractForm.setNewBillingAddress(profileAccount.getBillingAddress().getFullBillAddr());
			}
			ltsRecontractForm.setUpdateDnProfile(((ServiceDetailLtsDTO)upgradeService).getUpdateDnProfile());
			
			return ltsRecontractForm;
			
		}
		return null;
	}
	
	public LtsPaymentFormDTO createLtsPaymentForm(SbOrderDTO sbOrder) {
		
		
		LtsPaymentFormDTO ltsPaymentForm = new LtsPaymentFormDTO();
		
		ServiceDetailLtsDTO upgradeService = LtsSbHelper.getLtsService(sbOrder);
		
				
		PaymentMethodDetailLtsDTO[] payments = upgradeService.getAccount().getPaymethods();
		if("Y".equals(upgradeService.getRecontractInd())){
			payments = upgradeService.getRecontract().getPaymethods();
		}
		
		if (ArrayUtils.isEmpty(payments)) {
			return null;
		}
		
		ltsPaymentForm.setSelectButton("");
		if("Y".equals(upgradeService.getRecontractInd())){
			if (!LtsConstant.DUMMY_ACCT_NO.equals(upgradeService.getRecontract().getAcctNum())) {
				ltsPaymentForm.setExistBillAccNum(upgradeService.getRecontract().getAcctNum());	
			}
		}else{
			ltsPaymentForm.setExistBillAccNum(upgradeService.getAccount().getAcctNo());
		}
		
		for (PaymentMethodDetailLtsDTO payment : payments) {
			
			if (StringUtils.equals(LtsConstant.IO_IND_INSTALL, payment.getAction())
					|| "Y".equals(upgradeService.getRecontractInd())) {
				ltsPaymentForm.setNewPayMethodType(payment.getPayMtdType());
				ltsPaymentForm.setSalesMemoNum(payment.getSalesMemoNum());
				if(StringUtils.equals(payment.getPayMtdType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
					ltsPaymentForm.setApplicationDate(payment.getAutopayAppDate());
					ltsPaymentForm.setAutoPayUpperLimit(payment.getAutopayUpLimAmt());
					ltsPaymentForm.setBankAccHolderDocNum(payment.getBankAcctHoldNum());
					ltsPaymentForm.setBankAccHolderDocType(payment.getBankAcctHoldType());
					ltsPaymentForm.setBankAccHolderName(payment.getBankAcctHoldName());
					ltsPaymentForm.setBankAccNum(payment.getBankAcctNo());
					ltsPaymentForm.setBankCode(payment.getBankCd());
					ltsPaymentForm.setBranchCode(payment.getBranchCd());
					ltsPaymentForm.setBranchCodeHidden(payment.getBranchCd());
					ltsPaymentForm.setThirdPartyBankAccount(StringUtils.equals("Y", payment.getThirdPartyInd()));
					ltsPaymentForm.setSelectButton(LtsConstant.PAYMENT_TYPE_AUTO_PAY);
					
				}
				else if(StringUtils.equals(payment.getPayMtdType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
					ltsPaymentForm.setCardHolderDocNum(payment.getCcIdDocNo());
					ltsPaymentForm.setCardHolderDocType(payment.getCcIdDocType());
					ltsPaymentForm.setCardHolderName(payment.getCcHoldName());
					ltsPaymentForm.setCardNum(payment.getCcNum());
					ltsPaymentForm.setCardType(payment.getCcType());
					ltsPaymentForm.setCardVerified(StringUtils.equals("Y", payment.getCcVerifiedInd()));
					ltsPaymentForm.setThirdPartyCard(StringUtils.equals("Y", payment.getThirdPartyInd()));
					String expDate = payment.getCcExpDate();
					if (StringUtils.isNotEmpty(expDate)) {
						ltsPaymentForm.setExpireMonth(Integer.parseInt(expDate.split("/")[0]));
						ltsPaymentForm.setExpireYear(Integer.parseInt(expDate.split("/")[1]));
					}
					ltsPaymentForm.setSelectButton(LtsConstant.PAYMENT_TYPE_CREDIT_CARD);
				}
			}
			else {
				ltsPaymentForm.setExistingPayMethodType(payment.getPayMtdType());
				ltsPaymentForm.setNewPayMethodType(payment.getPayMtdType());
				ltsPaymentForm.setSalesMemoNum(payment.getSalesMemoNum());
			}
			
		}

		List<ItemDetailDTO> erChargeItemList = new ArrayList<ItemDetailDTO>();
		
		if(upgradeService.getItemDtls() != null){
			for(ItemDetailLtsDTO itemDtl: upgradeService.getItemDtls()){
				ItemDetailDTO paymentFormItem = ltsPaymentService.getItemDetail(itemDtl.getItemId(), LtsConstant.DISPLAY_TYPE_ITEM_SELECT, LtsConstant.LOCALE_ENG, itemDtl.getItemType());
				if(paymentFormItem != null){
					paymentFormItem.setSelected(true);
					if(itemDtl.getItemAttbs() != null && itemDtl.getItemAttbs().length > 0){
						ltsOfferService.getItemAttbByItemAttbAssign(paymentFormItem);
						for(ItemAttbDTO formAttb : paymentFormItem.getItemAttbs()){
							for(ItemAttributeDetailLtsDTO dtlAttb: itemDtl.getItemAttbs()){
								if(StringUtils.equals(formAttb.getAttbID(), dtlAttb.getAttbCd())){
									formAttb.setAttbValue(dtlAttb.getAttbValue());
									break;
								}
							}
						}
					}
					if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PREPAY)){
						if(StringUtils.equals(ltsPaymentForm.getNewPayMethodType(), ltsPaymentForm.getExistingPayMethodType())){
							//if new == exist pay mtd, selected item => exist page
							ltsPaymentForm.setPrepayItemSelectedE(true);
						}else {
							ltsPaymentForm.setPrepayItemSelectedE(false);
							if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(ltsPaymentForm.getNewPayMethodType())){
								ltsPaymentForm.setPrepayItemSelectedC(true);
								ltsPaymentForm.setPrepayItemSelectedA(false);
							}else{
								ltsPaymentForm.setPrepayItemSelectedA(true);
								ltsPaymentForm.setPrepayItemSelectedC(false);
							}
						}
						ltsPaymentForm.setPrePayItem(paymentFormItem);
					}else if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL)
							|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE)
							|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE)
							|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL_BR)
							|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE)
							|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_EBILL)
							|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_EMAIL_BILL)){
						ltsPaymentForm.setSelectedBillItemId(itemDtl.getItemId());
						ltsPaymentForm.setBillItemList(Lists.newArrayList(paymentFormItem));
					}else if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_MYHKT_BILL)
							|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_THE_CLUB_BILL)
							|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL)){
						ltsPaymentForm.setCsPortalItem(paymentFormItem);
					}else if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL)){
						ltsPaymentForm.setViewBillItem(paymentFormItem);
					}else if (StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_ER_CHARGE)) {
						paymentFormItem.setPenaltyHandling(itemDtl.getPenaltyHandling());
						erChargeItemList.add(paymentFormItem);
					}
				}
			}
		}
		
		if(StringUtils.isNotBlank(upgradeService.getAccount().getEmailAddr())){
			ltsPaymentForm.setEmailBillAddress(upgradeService.getAccount().getEmailAddr());
		}
		
		if (!erChargeItemList.isEmpty()) {
			ltsPaymentForm.setErChargeItemList(erChargeItemList);
		}
		
		if("Y".equals(upgradeService.getAccount().getCustomer().getCsPortalInd())
			|| "R".equals(upgradeService.getAccount().getCustomer().getCsPortalInd())){
			ltsPaymentForm.setCspNewReg(true);
			ltsPaymentForm.setCspEmail(upgradeService.getAccount().getCustomer().getCsPortalLogin());
			ltsPaymentForm.setCspMobile(upgradeService.getAccount().getCustomer().getCsPortalMobile());
			ltsPaymentForm.setOptOut(upgradeService.getAccount().getCustomer().getHktOptOut());
		}
		if("Y".equals(upgradeService.getAccount().getCustomer().getTheClubInd())
			||"R".equals(upgradeService.getAccount().getCustomer().getTheClubInd())){
			
			ltsPaymentForm.setClubEmail(upgradeService.getAccount().getCustomer().getTheClubLogin());
			ltsPaymentForm.setClubMobile(upgradeService.getAccount().getCustomer().getTheClubMobile());
			ltsPaymentForm.setOptOut(upgradeService.getAccount().getCustomer().getClubOptOut());
			ltsPaymentForm.setOptOutReason(upgradeService.getAccount().getCustomer().getClubOptRea());
			ltsPaymentForm.setOptOutRemark(upgradeService.getAccount().getCustomer().getClubOptRmk());
		}
		
		
		BillingAddressLtsDTO billAddrLts = upgradeService.getAccount().getBillingAddress();
		if("Y".equals(upgradeService.getRecontractInd())){
			billAddrLts = upgradeService.getRecontract().getBillingAddress();
		}
		if(billAddrLts != null
				&& !"Y".equals(upgradeService.getErInd())
				&& BaCaActionType.DIFFERENT_FROM_NEW_OLD_IA.getCode().equals(billAddrLts.getAction())){
			StringBuilder addrSb = new StringBuilder();
			
			final String[] addrLines = {billAddrLts.getAddrLine1(), billAddrLts.getAddrLine2(),
					billAddrLts.getAddrLine3(), billAddrLts.getAddrLine4(), billAddrLts.getAddrLine5(), billAddrLts.getAddrLine6()};
			for(String line: addrLines){
				if(StringUtils.isNotEmpty(line)){
					addrSb.append(line + "\n");
				}
			}
			
			ltsPaymentForm.setChangeBa(true);
			ltsPaymentForm.setBillingAddress(addrSb.toString());
			ltsPaymentForm.setInstantUpdateBa("Y".equals(billAddrLts.getInstantUpdateInd()));
		}
		
		if(upgradeService.getRecontract() != null 
				&& !LtsConstant.DUMMY_ACCT_NO.equals(upgradeService.getRecontract().getAcctNum())){
			ltsPaymentForm.setRecontractAccountNo(upgradeService.getRecontract().getAcctNum());
		}
		
		
		if (StringUtils.isNotBlank(upgradeService.getAccount().getRedemptionMedia())) {
			ltsPaymentForm.setRedemptionMedia(upgradeService.getAccount().getRedemptionMedia());
			ltsPaymentForm.setRedemMediaEmailAddr(upgradeService.getAccount().getRedemptionEmailAddr());
			ltsPaymentForm.setRedemMediaSmsNum(upgradeService.getAccount().getRedemptionSmsNo());
		}
		
		ltsPaymentForm.setPaperWaiveReason(upgradeService.getAccount().getWaivePaperReaCd());
		ltsPaymentForm.setPaperBillWaiveOtherStaffId(upgradeService.getAccount().getWaivePaperRemarks());
		
		return ltsPaymentForm;
	}

	
	public String createRemark(SbOrderDTO sbOrder, String remarkType) {
		
		ServiceDetailDTO upgradeService = LtsSbHelper.getLtsService(sbOrder);
		
		if (upgradeService.getRemarks() == null || ArrayUtils.isEmpty(upgradeService.getRemarks())) {
			return null;
		}
		
		return upgradeService.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK);
	}
	
	public LtsAppointmentFormDTO createLtsAppointmentForm(SbOrderDTO sbOrder, OrderCaptureDTO orderCapture, BomSalesUserDTO bomSalesUser) {

		LtsAppointmentFormDTO ltsAppointmentForm = null;
		
		ServiceDetailLtsDTO upgradeService = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailDTO secDelService = LtsSbOrderHelper.get2ndDelService(sbOrder);
			
		
		if (upgradeService == null || upgradeService.getAppointmentDtl() == null) {
			return null;
		}
		
		ltsAppointmentForm = new LtsAppointmentFormDTO();
		AppointmentDetailLtsDTO appointment = upgradeService.getAppointmentDtl();
		ltsAppointmentForm.setPreBookSerialNum(appointment.getSerialNum());
		//upgrade
		String startDateTime = appointment.getAppntStartTime();
		String endDateTime = appointment.getAppntEndTime();
		if(StringUtils.isNotEmpty(startDateTime) && StringUtils.isNotEmpty(endDateTime)){
			ltsAppointmentForm.setInstallationDate(startDateTime.split(" ")[0]);
			ltsAppointmentForm.setInstallationTime(startDateTime.split(" ")[1].concat("-").concat(endDateTime.split(" ")[1]));
			ltsAppointmentForm.setInstallationType(appointment.getAppntType());
			ltsAppointmentForm.setConfirmedInd(LtsConstant.TRUE_IND);
		}else{
			ltsAppointmentForm.setConfirmedInd(LtsConstant.FALSE_IND);
		}

		// prewiring
		if (StringUtils.isNotEmpty(appointment.getPreWiringStartTime())
				&& StringUtils.isNotEmpty(appointment.getPreWiringEndTime())) {
			startDateTime = appointment.getPreWiringStartTime();
			endDateTime = appointment.getPreWiringEndTime();
			ltsAppointmentForm.setPreWiringDate(startDateTime.split(" ")[0]);
			ltsAppointmentForm.setPreWiringTime(startDateTime.split(" ")[1].concat("-").concat(endDateTime.split(" ")[1]));
		}

		//cutOver
		if (StringUtils.isNotEmpty(appointment.getCutOverStartTime())
				&& StringUtils.isNotEmpty(appointment.getCutOverEndTime())) {
			startDateTime = appointment.getCutOverStartTime();
			endDateTime = appointment.getCutOverEndTime();
			ltsAppointmentForm.setCutOverDate(startDateTime.split(" ")[0]);
			ltsAppointmentForm.setCutOverTime(startDateTime.split(" ")[1].concat("-").concat(endDateTime.split(" ")[1]));
		}
		
		ltsAppointmentForm.setInstallationContactName(appointment.getInstContactName());
		ltsAppointmentForm.setInstallationContactNum(appointment.getInstContactNum());
		ltsAppointmentForm.setInstallationMobileSMSAlert(appointment.getInstSmsNum());
		ltsAppointmentForm.setAdjAmount(upgradeService.getAdjustRate());
		ltsAppointmentForm.setAdjResult(upgradeService.getAdjustAmount());
		ltsAppointmentForm.setAdjStartDate(upgradeService.getAdjustStartDate());
		ltsAppointmentForm.setAdjEndDate(upgradeService.getAdjustEndDate());
		
		ltsAppointmentForm.setSecDelInd(secDelService != null ? LtsConstant.TRUE_IND : LtsConstant.FALSE_IND);
		
		if(secDelService != null){
			AppointmentDetailLtsDTO secDel = secDelService.getAppointmentDtl();
			
			if (StringUtils.isNotEmpty(secDel.getAppntStartTime()) && StringUtils.isNotEmpty(secDel.getAppntEndTime())) {
				startDateTime = secDel.getAppntStartTime();
				endDateTime = secDel.getAppntEndTime();
				ltsAppointmentForm.setSecDelInstallationDate(startDateTime.split(" ")[0]);
				ltsAppointmentForm.setSecDelInstallationTime(startDateTime.split(" ")[1].concat("-").concat(endDateTime.split(" ")[1]));
				ltsAppointmentForm.setSecDelInstallationType(secDel.getAppntType());
			}
			
			//cutOver
			if (StringUtils.isNotEmpty(secDel.getCutOverStartTime()) && StringUtils.isNotEmpty(secDel.getCutOverEndTime())) {
				startDateTime = secDel.getCutOverStartTime();
				endDateTime = secDel.getCutOverEndTime();
				ltsAppointmentForm.setSecDelCutOverDate(startDateTime.split(" ")[0]);
				ltsAppointmentForm.setSecDelCutOverTime(startDateTime.split(" ")[1].concat("-").concat(endDateTime.split(" ")[1]));	
			}
			
			ltsAppointmentForm.setSecDelFieldVisitRequired(StringUtils.equals("Y", secDelService.getForceFieldVisitInd()));
		}
		

		//BOM2018061
		if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(orderCapture.getOrderType()) 
				|| ltsOfferService.isRenewOfferWithNewDevice(orderCapture)){
			ltsAppointmentForm.setEpdItemList(
					ltsAppointmentService.getEpdItemList(
							LtsSbOrderHelper.getBasketChannelId(bomSalesUser, orderCapture), 
							LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), LtsConstant.LOCALE_ENG));
		}
		
		
		ItemDetailLtsDTO epdOrdItem = null;
		for(ItemDetailLtsDTO item: upgradeService.getItemDtls()){
			if(StringUtils.equals(item.getItemType(), LtsConstant.ITEM_TYPE_EPD_ACCEPT)
					|| StringUtils.equals(item.getItemType(), LtsConstant.ITEM_TYPE_EPD_FORFEIT)
					|| StringUtils.equals(item.getItemType(), LtsConstant.ITEM_TYPE_EPD_UNDER_CONSIDERATION)){
				epdOrdItem = item;
			}
		}
		
		if(epdOrdItem != null && ltsAppointmentForm.getEpdItemList() != null){
			for(ItemDetailDTO epdItemDtl: ltsAppointmentForm.getEpdItemList()){
				if(StringUtils.equals(epdOrdItem.getItemType(), epdItemDtl.getItemType())){
					epdItemDtl.setSelected(true);
					if(epdOrdItem.getItemAttbs() != null && epdOrdItem.getItemAttbs().length > 0){
						for(ItemAttributeDetailLtsDTO epdOrdItemAttb: epdOrdItem.getItemAttbs()){
							for(ItemAttbDTO epdItemAttb: epdItemDtl.getItemAttbs()){
								if(StringUtils.equals(epdItemAttb.getAttbID(), epdOrdItemAttb.getAttbCd())){
									epdItemAttb.setAttbValue(epdOrdItemAttb.getAttbValue());
									break;
								}
							}
						}
					}
					break;
				}
			}
		}
		
		
		ltsAppointmentForm.setRemarks(createRemark(sbOrder, LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
		
		
		return ltsAppointmentForm;
	}
	
	private LtsSalesInfoFormDTO createLtsSalesInfoForm(SbOrderDTO sbOrder) {
		
		LtsSalesInfoFormDTO ltsSalesInfoForm = null;
		
		if (StringUtils.isNotEmpty(sbOrder.getStaffNum())) {
			ltsSalesInfoForm = new LtsSalesInfoFormDTO();
			ltsSalesInfoForm.setSalesChannel(sbOrder.getSalesChannel());
			ltsSalesInfoForm.setSalesCode(sbOrder.getSalesCd());
			ltsSalesInfoForm.setSalesContact(sbOrder.getSalesContactNum());
			ltsSalesInfoForm.setSalesTeam(sbOrder.getSalesTeam());
			ltsSalesInfoForm.setStaffId(sbOrder.getStaffNum());
			ltsSalesInfoForm.setStaffName(sbOrder.getSalesName());
		}
		
		return ltsSalesInfoForm;
	}
	
	private LtsOtherVoiceServiceFormDTO createLtsOtherVoiceServiceForm(SbOrderDTO sbOrder) {
		
		LtsOtherVoiceServiceFormDTO ltsOtherVoiceServiceForm = new LtsOtherVoiceServiceFormDTO();
		ltsOtherVoiceServiceForm.setCreate2ndDel(false);
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
				continue;
			}
			
			if (LtsConstant.LTS_PRODUCT_TYPE_2DEL.equals(serviceDetail.getToProd())) {
				if (LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(serviceDetail.getFromProd())) {
					ltsOtherVoiceServiceForm.setNew2ndDelDn(serviceDetail.getSrvNum());
					ltsOtherVoiceServiceForm.setNew2ndDelSrvStatus(LtsConstant.INVENT_STS_WORKING);	
				}
				if (LtsConstant.LTS_PRODUCT_TYPE_NEW.equals(serviceDetail.getFromProd())) {
					ltsOtherVoiceServiceForm.setNew2ndDelDn(serviceDetail.getSrvNum());
					if (StringUtils.equals("Y", ((ServiceDetailLtsDTO)serviceDetail).getReservedDnInd())) {
						ltsOtherVoiceServiceForm.setNew2ndDelSrvStatus(LtsConstant.INVENT_STS_RESERVED);	
					}
					else {
						ltsOtherVoiceServiceForm.setNew2ndDelSrvStatus(LtsConstant.INVENT_STS_PRE_ASSIGN);
					}	
				}
				if (LtsConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
					ltsOtherVoiceServiceForm.setDuplexXSrvType(DuplexSrvType.NEW_2ND_DEL);
				}
				if (LtsConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
					ltsOtherVoiceServiceForm.setDuplexYSrvType(DuplexSrvType.NEW_2ND_DEL);
				}	
			}
			else if (LtsConstant.LTS_PRODUCT_TYPE_REMOVE.equals(serviceDetail.getToProd())) {
				if (LtsConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
					ltsOtherVoiceServiceForm.setDuplexXSrvType(DuplexSrvType.CANCEL);
				}
				if (LtsConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
					ltsOtherVoiceServiceForm.setDuplexYSrvType(DuplexSrvType.CANCEL);
				}				
			}
			else if (LtsConstant.LTS_SRV_TYPE_SIP.equals(((ServiceDetailLtsDTO) serviceDetail).getToSrvType())
					|| LtsConstant.LTS_SRV_TYPE_PE.equals(((ServiceDetailLtsDTO) serviceDetail).getToSrvType())) {
				
				if (LtsConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
					ltsOtherVoiceServiceForm.setDuplexXSrvType(DuplexSrvType.UPGRADE);
				}
				if (LtsConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
					ltsOtherVoiceServiceForm.setDuplexYSrvType(DuplexSrvType.UPGRADE);
				}
			}
			else if (LtsConstant.LTS_SRV_TYPE_DEL.equals(serviceDetail.getToProd())) {
				if (LtsConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
					ltsOtherVoiceServiceForm.setDuplexXSrvType(DuplexSrvType.RETAIN);
				}
				if (LtsConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
					ltsOtherVoiceServiceForm.setDuplexYSrvType(DuplexSrvType.RETAIN);
				}
			}
			
			if (LtsConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())
					|| LtsConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
				ltsOtherVoiceServiceForm.setDuplexProfile(true);
			}
			
			
			if (LtsSbOrderHelper.is2ndDelService(serviceDetail)) {
				ltsOtherVoiceServiceForm.setSecondDelBaCaChange(StringUtils.equals("Y", serviceDetail.getCopyErIaToBa()));
//				ltsOtherVoiceServiceForm.setSecondDelConfirmSameIa(secondDelConfirmSameIa)
//				ltsOtherVoiceServiceForm.setSecondDelDiffAddress(secondDelDiffAddress)
//				ltsOtherVoiceServiceForm.setSecondDelDiffCust(secondDelDiffCust)
				ltsOtherVoiceServiceForm.setSecondDelDocNum(serviceDetail.getAccount().getCustomer().getIdDocNum());
				ltsOtherVoiceServiceForm.setSecondDelDocType(serviceDetail.getAccount().getCustomer().getIdDocType());
				ltsOtherVoiceServiceForm.setSecondDelDummyDoc(StringUtils.equals("Y", serviceDetail.getDummyDocIdInd()));
				ltsOtherVoiceServiceForm.setSecondDelEr(StringUtils.equals("Y", serviceDetail.getErInd()));
				ltsOtherVoiceServiceForm.setSecondDelThirdPartyAppl(StringUtils.equals("Y", serviceDetail.getThirdPartyAppln()));
				ltsOtherVoiceServiceForm.setCreate2ndDel(true);
			}
		}
		
		return ltsOtherVoiceServiceForm;
	}
	
	private LtsModemArrangementFormDTO createLtsModemArrangementForm(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		LtsModemArrangementFormDTO ltsModemArrangementForm = new LtsModemArrangementFormDTO();
		
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		String existEyeType = "";
		if(!LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(ltsService.getFromProd())
				&& !LtsConstant.LTS_PRODUCT_TYPE_NEW.equals(ltsService.getFromProd())){
			existEyeType = StringUtils.lowerCase(ltsService.getFromProd());
		}
		
		if (imsService == null || StringUtils.isEmpty(imsService.getShareFsaType())) {
			return null;
		}
		
		ltsModemArrangementForm.setModemType(ModemType.valueOf(imsService.getShareFsaType()));
		List<FsaDetailDTO> fsaDetailList = new ArrayList<FsaDetailDTO>();
		if (ltsModemArrangementForm.getModemType() == ModemType.SHARE_EX_FSA) {
			fsaDetailList.add(createSelectedFsaDetail(imsService, existEyeType, bomSalesUser));
			ltsModemArrangementForm.setExistingFsaList(fsaDetailList);
		}
		if (ltsModemArrangementForm.getModemType() == ModemType.SHARE_OTHER_FSA) {
			fsaDetailList.add(createSelectedFsaDetail(imsService, existEyeType, bomSalesUser));
			ltsModemArrangementForm.setOtherFsaList(fsaDetailList);
		}
		
		ltsModemArrangementForm.setLostModem(StringUtils.equals(imsService.getLostModem(), "Y"));

		ltsModemArrangementForm.setRentalRouterExistFsaVas(false);
		ltsModemArrangementForm.setRentalRouterInd(null);
		if(StringUtils.equals(imsService.getShareRentalRouter(), "E")){
			ltsModemArrangementForm.setRentalRouterExistFsaVas(true);
		}else if(StringUtils.equals(imsService.getShareRentalRouter(), "Y")){
			ltsModemArrangementForm.setRentalRouterInd(LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER);
		}else if(StringUtils.equals(imsService.getShareRentalRouter(), "N")){
			ltsModemArrangementForm.setRentalRouterInd(LtsConstant.ROUTER_OPTION_BRM);
		}
		
		return ltsModemArrangementForm;
	}
	
	private FsaDetailDTO createSelectedFsaDetail(ServiceDetailOtherLtsDTO imsServiceDetail, String existEyeType, BomSalesUserDTO bomSalesUser) {
		
		FsaDetailDTO selectedFsaDetail = new FsaDetailDTO();
		selectedFsaDetail.setSelected(true);
		selectedFsaDetail.setFsa(StringUtils.isNotEmpty(imsServiceDetail.getSrvNum()) ? Integer.parseInt(imsServiceDetail.getSrvNum()) : 0);
//		selectedFsaDetail.setAddressDetail(addressDetail)
//		selectedFsaDetail.setAllowConfirmSameIa(allowConfirmSameIa)
//		selectedFsaDetail.setBandwidth(imsServiceDetail.getExistBandwidth());
//		selectedFsaDetail.setCustFirstName(custFirstName)
//		selectedFsaDetail.setCustLastName(custLastName)
//		selectedFsaDetail.setDeactNowTv(imsServiceDetail.getDeactNowTvInd());
//		selectedFsaDetail.setDifferentIa(isDifferentIa)
		selectedFsaDetail.setTechnology(imsServiceDetail.getExistTechnology());
		
		FsaServiceDetailOutputDTO fsaProfile = imsServiceProfileAccessService.getServiceDetailByFSA(imsServiceDetail.getSrvNum(), bomSalesUser.getUsername());
		imsServiceProfileAccessService.getFsaOfferProfile(fsaProfile, existEyeType);
		if(fsaProfile != null){
			selectedFsaDetail.setFsaProfile(fsaProfile);
		}
		
		return selectedFsaDetail;
	}
	
	
	
	private ServiceDetailProfileLtsDTO createDummy2ndDelServiceDetailProfile(SbOrderDTO sbOrder) {

		
		if (LtsSbOrderHelper.isOnlineAcquistionOrder(sbOrder)) {
			return null;
		}
		
		ServiceDetailProfileLtsDTO serviceProfile = new ServiceDetailProfileLtsDTO();
		
		ServiceDetailLtsDTO new2ndDetailService = null;
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			
			if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
				continue;
			}
			
			if (LtsConstant.LTS_SRV_TYPE_DEL.equals(((ServiceDetailLtsDTO)serviceDetail).getFromSrvType())
					|| LtsConstant.LTS_SRV_TYPE_2DEL.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())) {
				new2ndDetailService = (ServiceDetailLtsDTO)serviceDetail;
				break;
			}
		}
		
		if (new2ndDetailService == null) {
			return null;
		}
		serviceProfile.setSrvNum(new2ndDetailService.getSrvNum());
		serviceProfile.setCcSrvMemNum(new2ndDetailService.getCcServiceMemNum());
		serviceProfile.setDnExchFrozen(StringUtils.equals(((ServiceDetailLtsDTO)new2ndDetailService).getFrozenExchInd(), "Y"));
		serviceProfile.setTwoNBuildInd(StringUtils.equals(((ServiceDetailLtsDTO)new2ndDetailService).getTwoNInd(), "Y"));
		serviceProfile.setCcSrvId(new2ndDetailService.getCcServiceId());
		serviceProfile.setAccounts(new AccountDetailProfileLtsDTO[] {createAccountDetailProfileLts(new2ndDetailService)});
		serviceProfile.setLtsTenure(StringUtils.isNotEmpty(new2ndDetailService.getTenure()) ? Integer.parseInt(new2ndDetailService.getTenure()) : 0 );
		serviceProfile.setPendingOcid(new2ndDetailService.getPendingOcid());
		serviceProfile.setPendingOcSrd(new2ndDetailService.getPendingOcidSrd());
		serviceProfile.setPrimaryCust(createCustomerDetailProfileLts(new2ndDetailService));
		serviceProfile.setSrvType(new2ndDetailService.getTypeOfSrv());
		serviceProfile.setSharedBsn(StringUtils.equals(((ServiceDetailLtsDTO)new2ndDetailService).getSharedBsnInd(), "Y"));
		return serviceProfile;
	}
	
	
	public ServiceDetailProfileLtsDTO createDummyServiceDetailProfile(SbOrderDTO sbOrder) {
		
		if (LtsSbOrderHelper.isOnlineAcquistionOrder(sbOrder)) {
			return null;
		}
		
		ServiceDetailProfileLtsDTO serviceProfile = new ServiceDetailProfileLtsDTO();
		
		serviceProfile.setAddress(createAddressDetailProfileLts(sbOrder));
		
		ServiceDetailDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailDTO duplexService = LtsSbOrderHelper.getDuplexService(sbOrder);
		ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		
		if (LtsConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO) ltsService).getFromSrvType())) {
			
			serviceProfile.setDuplexNum(ltsService.getSrvNum());
			serviceProfile.setDuplexCcSrvMemNum(ltsService.getCcServiceMemNum());
			serviceProfile.setDuplexDnExchFrozen(StringUtils.equals(((ServiceDetailLtsDTO)ltsService).getFrozenExchInd(), "Y"));
			serviceProfile.setDuplexTwoNBuildInd(StringUtils.equals(((ServiceDetailLtsDTO)ltsService).getTwoNInd(), "Y"));
			
			if (duplexService != null) {
				serviceProfile.setSrvNum(duplexService.getSrvNum());
				serviceProfile.setCcSrvMemNum(duplexService.getCcServiceMemNum());
				serviceProfile.setDnExchFrozen(StringUtils.equals(((ServiceDetailLtsDTO)duplexService).getFrozenExchInd(), "Y"));
				serviceProfile.setTwoNBuildInd(StringUtils.equals(((ServiceDetailLtsDTO)duplexService).getTwoNInd(), "Y"));
			}
		
		}
		else {
			serviceProfile.setSrvNum(ltsService.getSrvNum());
			serviceProfile.setCcSrvMemNum(ltsService.getCcServiceMemNum());
			serviceProfile.setDnExchFrozen(StringUtils.equals(((ServiceDetailLtsDTO)ltsService).getFrozenExchInd(), "Y"));
			serviceProfile.setTwoNBuildInd(StringUtils.equals(((ServiceDetailLtsDTO)ltsService).getTwoNInd(), "Y"));
			
			if (duplexService != null) {
				serviceProfile.setDuplexNum(duplexService.getSrvNum());
				serviceProfile.setDuplexCcSrvMemNum(duplexService.getCcServiceMemNum());
				serviceProfile.setDuplexDnExchFrozen(StringUtils.equals(((ServiceDetailLtsDTO)duplexService).getFrozenExchInd(), "Y"));
				serviceProfile.setDuplexTwoNBuildInd(StringUtils.equals(((ServiceDetailLtsDTO)duplexService).getTwoNInd(), "Y"));
			}
		}
		serviceProfile.setCcSrvId(ltsService.getCcServiceId());
		serviceProfile.setAccounts(new AccountDetailProfileLtsDTO[] {createAccountDetailProfileLts(ltsService)});

		serviceProfile.setLtsTenure(StringUtils.isNotEmpty(ltsService.getTenure()) ? Integer.parseInt(ltsService.getTenure()) : 0);
		serviceProfile.setPendingOcid(ltsService.getPendingOcid());
		serviceProfile.setPendingOcSrd(ltsService.getPendingOcidSrd());
		serviceProfile.setPendingOrderOverdue(StringUtils.isNotEmpty(ltsService
						.getPendingOcidSrd()) ? LtsDateFormatHelper
						.isDateOverdue(ltsService.getPendingOcidSrd(),
								"dd/MM/yyyy") : false);
		serviceProfile.setPrimaryCust(createCustomerDetailProfileLts(ltsService));
		serviceProfile.setSearchCriteriaDn(ltsService.getSrvNum());
		serviceProfile.setSrvType(ltsService.getTypeOfSrv());
		serviceProfile.setSharedBsn(StringUtils.equals(((ServiceDetailLtsDTO)ltsService).getSharedBsnInd(), "Y"));
		
		if(!LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(ltsService.getFromProd())
				&& !LtsConstant.LTS_PRODUCT_TYPE_NEW.equals(ltsService.getFromProd())){
			serviceProfile.setExistEyeType(StringUtils.lowerCase(ltsService.getFromProd()));
		}
		
		if (imsService != null) {
			serviceProfile.setImsTenure(StringUtils.isNotEmpty(imsService.getTenure()) ? Integer.parseInt(imsService.getTenure()) : 0);	
		}
		serviceProfile.setEndedOfferProfiles(this.offerProfileLtsService.getLtsEndedOfferProfile(ltsService.getCcServiceId(), sbOrder.getAppDate()));
		serviceProfile.setEndedItemDetails(this.offerItemService.getOfferItemMapping(serviceProfile.getEndedOfferProfiles()));
		if (ArrayUtils.isNotEmpty(serviceProfile.getEndedItemDetails())) {
			for (ItemDetailProfileLtsDTO itemDetailProfile : serviceProfile.getEndedItemDetails()) {
				if (StringUtils.isEmpty(itemDetailProfile.getItemId())) {
					continue;
				}
				itemDetailProfile.setItemDetail(ltsOfferService.getTpVasItemDetail(
						itemDetailProfile.getItemId(), LtsConstant.LOCALE_ENG));
			}	
		}
		return serviceProfile;
	}
	
	
	private AddressRolloutDTO createAddressRollout(SbOrderDTO sbOrder) {
		AddressDetailLtsDTO addressDetail = sbOrder.getAddress();
		return addressRolloutService.getAddressRollout(addressDetail.getSerbdyno(), addressDetail.getUnitNo(), addressDetail.getFloorNo());
	}
	
	private AddressDetailProfileLtsDTO createAddressDetailProfileLts(SbOrderDTO sbOrder) {
		AddressDetailProfileLtsDTO profileAddress = new AddressDetailProfileLtsDTO();
		
		AddressDetailLtsDTO addressDetail = sbOrder.getAddress();
		
		if (addressDetail == null) {
			return null;
		}
		
		profileAddress.setAddressRollout(createAddressRollout(sbOrder));
		profileAddress.setArea(addressDetail.getAreaDesc());
		profileAddress.setAreaCd(addressDetail.getAreaCd());
		profileAddress.setBuildName(addressDetail.getBuildNo());
		profileAddress.setDistrict(addressDetail.getDistDesc());
		profileAddress.setDistrictCd(addressDetail.getDistNo());
		profileAddress.setFloorNum(addressDetail.getFloorNo());
		profileAddress.setHlotNum(addressDetail.getHiLotNo());
		profileAddress.setSectCd(addressDetail.getSectCd());
		profileAddress.setSectDesc(addressDetail.getSectDesc());
		profileAddress.setSrvBdry(addressDetail.getSerbdyno());
		profileAddress.setStreetCat(addressDetail.getStrCatDesc());
		profileAddress.setStreetCatCd(addressDetail.getStrCatCd());
		profileAddress.setStreetNum(addressDetail.getStrNo());
		profileAddress.setStreetName(addressDetail.getStrName());
		profileAddress.setUnitNum(addressDetail.getUnitNo());
		
		return profileAddress;
	}
	
	private CustomerDetailProfileLtsDTO createCustomerDetailProfileLts(ServiceDetailDTO serviceDetail) {
		
		CustomerDetailLtsDTO customerDetailLts = serviceDetail.getAccount().getCustomer();
		
		if (customerDetailLts == null) {
			return null;
		}
		
		CustomerDetailProfileLtsDTO profileCustomer = new CustomerDetailProfileLtsDTO();
		profileCustomer.setBlacklistCustInd(StringUtils.equals("Y", customerDetailLts.getBlacklistInd()) );
		profileCustomer.setCustNum(customerDetailLts.getCustNo());
//		profileCustomer.setCustRemarks(custRemarks)
		profileCustomer.setDocNum(customerDetailLts.getIdDocNum());
		profileCustomer.setDocType(customerDetailLts.getIdDocType());
		profileCustomer.setFirstName(customerDetailLts.getFirstName());
		profileCustomer.setIdVerifyInd(StringUtils.equals("Y", customerDetailLts.getIdVerifiedInd()));
		profileCustomer.setLastName(customerDetailLts.getLastName());
//		profileCustomer.setParentCustNum(parentCustNum)
		profileCustomer.setTitle(customerDetailLts.getTitle());
//		profileCustomer.setWipInd()
//		profileCustomer.setWipMessage(wipMessage)
		return profileCustomer;
	}
	
	
	private PaymentMethodDetailLtsDTO getExistingPaymentMethod(PaymentMethodDetailLtsDTO[] paymentMethods) {
//		PaymentMethodDetailLtsDTO[] paymentMethods = serviceDetail.getAccount().getPaymethods();
		
		if (paymentMethods == null || ArrayUtils.isEmpty(paymentMethods)) {
			return null;
		}
		for (PaymentMethodDetailLtsDTO paymentMethodDetailLts : paymentMethods) {
			if (StringUtils.equals(LtsConstant.IO_IND_SPACE, paymentMethodDetailLts.getAction())
					|| StringUtils.equals(LtsConstant.IO_IND_OUT, paymentMethodDetailLts.getAction())) {
				return paymentMethodDetailLts;
			}
		}
		return null;
	}
	
	
	private AccountDetailProfileLtsDTO createAccountDetailProfileLts(ServiceDetailDTO serviceDetail) {

		PaymentMethodDetailLtsDTO profilePaymentMethod = getExistingPaymentMethod(serviceDetail.getAccount().getPaymethods());
		AccountDetailProfileLtsDTO accountDetailProfileLts = new AccountDetailProfileLtsDTO();
		
		profilePaymentMethod = getExistingPaymentMethod(serviceDetail.getAccount().getPaymethods());
		accountDetailProfileLts.setAcctNum(serviceDetail.getAccount().getAcctNo());
		accountDetailProfileLts.setBillFreq(serviceDetail.getAccount().getBillFreq());
		accountDetailProfileLts.setBillLang(serviceDetail.getAccount().getBillLang());
		accountDetailProfileLts.setBillMedia(serviceDetail.getAccount().getExistBillMedia());
		accountDetailProfileLts.setPrimaryAcctInd(true);
		
		/*profilePaymentMethod could be null if it is recontract order*/
		if(profilePaymentMethod != null){
			accountDetailProfileLts.setPayMethod(profilePaymentMethod.getPayMtdType());
			accountDetailProfileLts.setBankAcctNum(profilePaymentMethod.getBankAcctNo());
			accountDetailProfileLts.setBankCd(profilePaymentMethod.getBankCd());
			accountDetailProfileLts.setBranchCd(profilePaymentMethod.getBranchCd());
			accountDetailProfileLts.setCreditCardNum(profilePaymentMethod.getCcNum());
		}
		
		return accountDetailProfileLts;
	}
	
	private AccountDetailProfileLtsDTO createRecontractAccountDetailProfileLts(SbOrderDTO sbOrder, RecontractLtsDTO recontract) {
		if(LtsConstant.DUMMY_ACCT_NO.equals(recontract.getAcctNum())){
			return null;
		}
		
		/*Retrieve recontract acct from BOM*/
		AccountDetailProfileLtsDTO recontractProfileAccount = customerProfileLtsService.getAccountbyAcctNum(recontract.getAcctNum(), LtsConstant.SYSTEM_ID_DRG);
		
		/*update acct detail from suspended order*/
		for(AccountDetailLtsDTO orderAcct: sbOrder.getAccounts()){
			if(StringUtils.equals(orderAcct.getAcctNo(), recontractProfileAccount.getAcctNum())){
				recontractProfileAccount.setBillFreq(orderAcct.getBillFreq());
			}
		}
		
		return recontractProfileAccount;
	}
	
	private ImsSbOrderDTO createRelatedPcdOrder(SbOrderDTO sbOrder, OrderCaptureDTO orderCapture) {
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		if (imsService == null || StringUtils.isEmpty(imsService.getRelatedSbOrderId())) {
			return null;
		}
		
		return ltsRelatedPcdOrderService.retrievePcdSbOrder(imsService.getRelatedSbOrderId(), orderCapture, false);
	}
	
	private LtsCustomerIdentificationFormDTO createLtsCustomerIdentificationForm (SbOrderDTO sbOrder) {
		
		LtsCustomerIdentificationFormDTO ltsCustomerIdentificationForm = new LtsCustomerIdentificationFormDTO();
		ServiceDetailDTO upgradeService = LtsSbHelper.getLtsService(sbOrder);
		 
		if (upgradeService.getAccount() != null && upgradeService.getAccount().getCustomer() != null) {
			CustomerDetailLtsDTO custDtl = upgradeService.getAccount().getCustomer();
			if(StringUtils.isNotBlank(custDtl.getDob())){
				ltsCustomerIdentificationForm.setDateOfBirth(custDtl.getDob());	
			}else{
				CustomerDetailProfileLtsDTO custProfile = customerProfileLtsService.getCustByDoc(custDtl.getIdDocType(), custDtl.getIdDocNum(), "DRG");
				ltsCustomerIdentificationForm.setDateOfBirth(custProfile.getDob());
				ltsCustomerIdentificationForm.setDobBom(custProfile.getDob());
			}
			
			
		}
		
		ltsCustomerIdentificationForm.setThirdPartyApplication(StringUtils.equals("Y", upgradeService.getThirdPartyAppln()));
		
		if (StringUtils.isNotEmpty(upgradeService.getActualDocId())
				&& StringUtils.isNotEmpty(upgradeService.getActualDocType())) {
			ltsCustomerIdentificationForm.setId(upgradeService.getActualDocId());
			ltsCustomerIdentificationForm.setDocType(upgradeService.getActualDocType());
		}
		
		ServiceDetailDTO new2ndDelService = LtsSbOrderHelper.getNew2ndDelService(sbOrder);
		
		if (new2ndDelService != null) {
			
			ltsCustomerIdentificationForm.setSecDelThirdPartyApplication(StringUtils.equals("Y", new2ndDelService.getThirdPartyAppln()));
			
			if (StringUtils.isNotEmpty(new2ndDelService.getActualDocId())
					&& StringUtils.isNotEmpty(new2ndDelService.getActualDocType())) {
				ltsCustomerIdentificationForm.setSecDelId(new2ndDelService.getActualDocId());
				ltsCustomerIdentificationForm.setSecDelDocType(upgradeService.getActualDocType());
			}	
		}
		
		ContactLtsDTO contactLts = sbOrder.getContact();
		if (contactLts != null) {
			ltsCustomerIdentificationForm.setCustomerContactEmail(contactLts.getEmailAddr());
			ltsCustomerIdentificationForm.setCustomerContactFixLineNum(contactLts.getContactFixedLine());
			ltsCustomerIdentificationForm.setCustomerContactMobileNum(contactLts.getContactMobile());
		}
		
		return ltsCustomerIdentificationForm;
		
	}
	
	private LtsAddressRolloutFormDTO createLtsAddressRolloutForm(SbOrderDTO sbOrder) {
		LtsAddressRolloutFormDTO ltsAddressRolloutForm = new LtsAddressRolloutFormDTO();
		ServiceDetailDTO upgradeService = LtsSbHelper.getLtsService(sbOrder);
		
		AddressDetailLtsDTO addressDetailLts = sbOrder.getAddress();
		ltsAddressRolloutForm.setBaCaAction(null);
		ltsAddressRolloutForm.setExternalRelocate(StringUtils.equals("Y", upgradeService.getErInd()));
		
		if (StringUtils.equals("Y", upgradeService.getErInd())) {
			ltsAddressRolloutForm.setAreaCode(addressDetailLts.getAreaCd());
			ltsAddressRolloutForm.setAreaDesc(addressDetailLts.getAreaDesc());
//			ltsAddressRolloutForm.setBaCaRemainUnchange(StringUtils.isEmpty(upgradeService.getCopyErIaToBa()));
			ltsAddressRolloutForm.setBuildingName(addressDetailLts.getBuildNo());
			ltsAddressRolloutForm.setDistrictCode(addressDetailLts.getDistNo());
			ltsAddressRolloutForm.setDistrictDesc(addressDetailLts.getDistDesc());
			ltsAddressRolloutForm.setFlat(addressDetailLts.getUnitNo());
			ltsAddressRolloutForm.setFloor(addressDetailLts.getFloorNo());
			ltsAddressRolloutForm.setLotNum(addressDetailLts.getHiLotNo());
			ltsAddressRolloutForm.setSectionCode(addressDetailLts.getSectCd());
			ltsAddressRolloutForm.setSectionDesc(addressDetailLts.getSectDesc());
			ltsAddressRolloutForm.setServiceBoundary(addressDetailLts.getSerbdyno());
			ltsAddressRolloutForm.setStreetCatgCode(addressDetailLts.getStrCatCd());
			ltsAddressRolloutForm.setStreetCatgDesc(addressDetailLts.getStrCatDesc());
			ltsAddressRolloutForm.setStreetName(addressDetailLts.getStrName());
			ltsAddressRolloutForm.setStreetNum(addressDetailLts.getStrNo());
			
			ltsAddressRolloutForm.setBaCaAction(StringUtils.isEmpty(upgradeService.getCopyErIaToBa())? BaCaActionType.SAME_AS_NEW_IA: BaCaActionType.REMAIN_UNCHANGE);
			
			//if er and change to another billing address
			BillingAddressLtsDTO billAddrLts = upgradeService.getAccount().getBillingAddress();
			if("Y".equals(upgradeService.getRecontractInd())){
				billAddrLts = upgradeService.getRecontract().getBillingAddress();
			}
			
			if(billAddrLts != null
						&& "Y".equals(upgradeService.getErInd())
						&& BaCaActionType.DIFFERENT_FROM_NEW_OLD_IA.getCode().equals(billAddrLts.getAction())){
				StringBuilder addrSb = new StringBuilder();
				ltsAddressRolloutForm.setBaCaAction(BaCaActionType.DIFFERENT_FROM_NEW_OLD_IA);
				final String[] addrLines = {billAddrLts.getAddrLine1(), billAddrLts.getAddrLine2(),
						billAddrLts.getAddrLine3(), billAddrLts.getAddrLine4(), billAddrLts.getAddrLine5(), billAddrLts.getAddrLine6()};
				for(String line: addrLines){
					if(StringUtils.isNotEmpty(line)){
						addrSb.append(line + "\n");
					}
				}
				
				ltsAddressRolloutForm.setBillingAddress(addrSb.toString());
				ltsAddressRolloutForm.setInstantUpdateBa("Y".equals(billAddrLts.getInstantUpdateInd()));
			
			}
		}
		return ltsAddressRolloutForm;
	}
	
	public ModemTechnologyAissgnDTO createModemTechnologyAssign(SbOrderDTO sbOrder) {
		
		ServiceDetailOtherLtsDTO imsServiceDetail =  (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		
		if (imsServiceDetail == null) {
			return null;
		}
		
		ModemTechnologyAissgnDTO modemTechnologyAissgn = new ModemTechnologyAissgnDTO();
		modemTechnologyAissgn.setBandwidth(imsServiceDetail.getAssgnBandwidth());
		modemTechnologyAissgn.setModemArrangment(imsServiceDetail.getModemArrangement());
		modemTechnologyAissgn.setNewImsService(imsServiceDetail.getNewSrvTypeCd());
		modemTechnologyAissgn.setTechnology(imsServiceDetail.getAssgnTechnology());
		if(sbOrder.getAddress().getAddrInventory() != null){
			modemTechnologyAissgn.setBbShortage(StringUtils.equals(sbOrder.getAddress()
							.getAddrInventory().getResourceShortageInd(), "Y"));
		}
		modemTechnologyAissgn.setAutoUpgraded(StringUtils.equals("Y", imsServiceDetail.getAutoUpgraded()));
		modemTechnologyAissgn.setImsOrderType(imsServiceDetail.getOrderType());
		return modemTechnologyAissgn;
	}
	
	private LtsMiscellaneousFormDTO createLtsMiscellaneousForm(SbOrderDTO sbOrder) {
		ServiceDetailDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
		LtsMiscellaneousFormDTO ltsMiscellaneousForm = new LtsMiscellaneousFormDTO();
		ltsMiscellaneousForm.setApplicationDate(sbOrder.getAppDate());
		ltsMiscellaneousForm.setRedeemPrevPremium(StringUtils.equals("Y",
				((ServiceDetailLtsDTO) ltsService).getRedeemPremiumInd()));
		ltsMiscellaneousForm.setRecontract("Y".equals(ltsService.getRecontractInd()));
		
		if (StringUtils.equals("Y", sbOrder.getBackDateInd())) {
			ltsMiscellaneousForm.setBackDateOrder(true);
			ltsMiscellaneousForm.setBackDate(sbOrder.getAppDate());
		}
		ltsMiscellaneousForm.setSwitchTp(StringUtils.equals("Y", ((ServiceDetailLtsDTO)ltsService).getSwitchPlanInd()));
		return ltsMiscellaneousForm;
	}
	
	private LtsSupportDocFormDTO createLtsSupportDocForm(SbOrderDTO sbOrder) {
		LtsSupportDocFormDTO ltsSupportDocForm = new LtsSupportDocFormDTO();
		ltsSupportDocForm.setCollectMethod(sbOrder.getCollectMethod());
		ltsSupportDocForm.setDistributeEmail(sbOrder.getEsigEmailAddr());
		ltsSupportDocForm.setDistributeSms(sbOrder.getSmsNo());
		ltsSupportDocForm.setSendEmail(StringUtils.isNotBlank(sbOrder.getEsigEmailAddr()));
		ltsSupportDocForm.setSendSms(StringUtils.isNotBlank(sbOrder.getSmsNo()));
		ltsSupportDocForm.setDistributeLang(sbOrder.getEsigEmailLang());
		ltsSupportDocForm.setDistributionMode(sbOrder.getDisMode());
		ltsSupportDocForm.setSupportDocumentList(createSupportDocumentList(sbOrder));
		ltsSupportDocForm.setSignoffMode(sbOrder.getSignoffMode());
		return ltsSupportDocForm;
	}
	
	private List<OrderDocDTO> createSupportDocumentList(SbOrderDTO sbOrder) {
		if (ArrayUtils.isEmpty(sbOrder.getAllOrdDocAssgns())) {
			return null;
		}
		
		List<OrderDocDTO> supportDocumentList = new ArrayList<OrderDocDTO>();
		OrderDocDTO orderDoc = null;
		for (AllOrdDocAssgnLtsDTO allOrdDocAssgnLts : sbOrder.getAllOrdDocAssgns()) {
			orderDoc = ltsOrderDocumentService.getOrderDoc(allOrdDocAssgnLts.getDocType());
			
			if (orderDoc == null) {
				continue;
			}
			
			orderDoc.setCollected(StringUtils.equals("Y", allOrdDocAssgnLts.getCollectedInd()));
			orderDoc.setMarkDelete(StringUtils.equals("Y", allOrdDocAssgnLts.getMarkDelInd()));
			orderDoc.setWaiveReasonCd(allOrdDocAssgnLts.getWaiveReason());
			orderDoc.setWaiveReasonBy(allOrdDocAssgnLts.getWaivedBy());
			supportDocumentList.add(orderDoc);
		}
		return supportDocumentList;
	}
	
	private LtsBasketSelectionFormDTO createBasketSelection(SbOrderDTO sbOrder, BasketDetailDTO basketDetail) {
		if(LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())){
			return null;
		}
		
		LtsBasketSelectionFormDTO form = new LtsBasketSelectionFormDTO();
		form.setSelectedBasketId(basketDetail.getBasketId());
		return form;
	}
	
	private LtsDeviceSelectionFormDTO createDeviceSelection(SbOrderDTO sbOrder, BasketDetailDTO basketDetail){
		if(LtsConstant.ORDER_TYPE_SB_DISCONNECT.equals(sbOrder.getOrderType())){
			return null;
		}
		
		LtsDeviceSelectionFormDTO form = new LtsDeviceSelectionFormDTO();
		form.setSelectedDeviceType(basketDetail.getType());
		return form;
	}
	
	private BasketDetailDTO getSelectedBasketDetail(SbOrderDTO sbOrder) {
		
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(sbOrder);
		String displayType = LtsSbOrderHelper.getBasketDisplayType(sbOrder);
		String basketId = LtsSbOrderHelper.getBasketId(ltsServiceDetail);
		
        if (StringUtils.isNotBlank(basketId)) {
			return ltsBasketService.getBasket(basketId, LtsConstant.LOCALE_ENG, displayType);
		}
		return null;
	}
	
	
}
