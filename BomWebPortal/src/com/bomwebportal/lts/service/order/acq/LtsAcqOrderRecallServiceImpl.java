package com.bomwebportal.lts.service.order.acq;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailSummaryDTO;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.OrderDocDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO.DuplexAction;
import com.bomwebportal.lts.dto.acq.LtsAcqPipbInfoDTO.PipbAction;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO.BaCaActionType;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO.ModemType;
import com.bomwebportal.lts.dto.form.LtsPremiumSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAccountSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBasketSelectionFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillingAddressFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumConfirmationFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqOtherVoiceServiceFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPersonalInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSalesInfoFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqSupportDocFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO.LtsAppointmentDetail;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO.BillMediaDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillingAddressFormDTO.BillingAddrDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO.Selection;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.AddressRolloutService;
import com.bomwebportal.lts.service.LtsAppointmentService;
import com.bomwebportal.lts.service.LtsBasketService;
import com.bomwebportal.lts.service.LtsCommonService;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsPaymentService;
import com.bomwebportal.lts.service.acq.LtsAcqAccountDetailService;
import com.bomwebportal.lts.service.acq.LtsAcqCustomerDetailService;
import com.bomwebportal.lts.service.acq.LtsAcqRelatedPcdOrderService;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.util.LtsAppointmentHelper;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsCsPortalBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.acq.LtsAcqSbOrderHelper;
import com.bomwebportal.lts.wsClientLts.BomCreateAccountWsClient;
import com.bomwebportal.lts.wsClientLts.BomCustProfileWsClient;
import com.bomwebportal.service.LoggingService;
import com.pccw.custProfileGateway.acctInfo.AccountDTO;
import com.pccw.custProfileGateway.custProfile.CustomerDTO;

public class LtsAcqOrderRecallServiceImpl implements LtsAcqOrderRecallService {
	
	protected final Log logger = LogFactory.getLog(getClass());

	protected LtsPaymentService ltsPaymentService;
	protected AddressRolloutService addressRolloutService;
//	protected LtsRelatedPcdOrderService ltsRelatedPcdOrderService;
	protected LtsOrderDocumentService ltsOrderDocumentService;
	protected LtsBasketService ltsBasketService;
//	protected CodeLkupCacheService relationshipCodeLkupCacheService;
	protected LtsOfferService ltsOfferService;
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	protected LoggingService loggingService;
	protected LtsAcqRelatedPcdOrderService ltsAcqRelatedPcdOrderService;
	protected BomCustProfileWsClient bomWsCreateCustClient;
	protected CustomerProfileLtsService customerProfileLtsService;
	protected LtsCommonService ltsCommonService;
	protected BomCreateAccountWsClient bomWsCreateAcctClient;
	protected LtsAcqCustomerDetailService ltsAcqCustomerDetailService;
	protected LtsAcqAccountDetailService ltsAcqAccountDetailService;
	protected LtsAppointmentService ltsAppointmentService;
	
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

/*	public LtsRelatedPcdOrderService getLtsRelatedPcdOrderService() {
		return ltsRelatedPcdOrderService;
	}

	public void setLtsRelatedPcdOrderService(
			LtsRelatedPcdOrderService ltsRelatedPcdOrderService) {
		this.ltsRelatedPcdOrderService = ltsRelatedPcdOrderService;
	}*/

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

/*	public CodeLkupCacheService getRelationshipCodeLkupCacheService() {
		return relationshipCodeLkupCacheService;
	}

	public void setRelationshipCodeLkupCacheService(
			CodeLkupCacheService relationshipCodeLkupCacheService) {
		this.relationshipCodeLkupCacheService = relationshipCodeLkupCacheService;
	}
*/
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

	public LtsAcqRelatedPcdOrderService getLtsAcqRelatedPcdOrderService() {
		return ltsAcqRelatedPcdOrderService;
	}

	public void setLtsAcqRelatedPcdOrderService(
			LtsAcqRelatedPcdOrderService ltsAcqRelatedPcdOrderService) {
		this.ltsAcqRelatedPcdOrderService = ltsAcqRelatedPcdOrderService;
	}

	// Recall Order
	public AcqOrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser) {
		SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(sbOrderId, pIsEquiry);
		this.loggingService.logRecallLtsOrder(bomSalesUser.getUsername(), sbOrder);
		AcqOrderCaptureDTO acqOrderCapture = convertSbOrderToAcqOrderCapture(sbOrder);
		if (!ltsCommonService.isNowDrgDownTime()) { 
			boolean isCustCreated = false;
			boolean isAccountCreated = false;
			if (LtsAcqSbOrderHelper.isDummyCustomer(acqOrderCapture.getCustomerDetailProfileLtsDTO().getCustNum())) {
				isCustCreated = createNewCustomer(sbOrderId, acqOrderCapture, bomSalesUser.getUsername());
			}
			if (!LtsAcqSbOrderHelper.isDummyCustomer(acqOrderCapture.getCustomerDetailProfileLtsDTO().getCustNum())
					&& LtsAcqSbOrderHelper.isDummyAccount(acqOrderCapture.getAccountDetailProfileLtsDTO()[0].getAcctNum())) {
			    isAccountCreated = createNewAccount(sbOrderId, acqOrderCapture, bomSalesUser.getUsername());
			}    
			if (isCustCreated || isAccountCreated) {
				sbOrder = orderRetrieveLtsService.retrieveSbOrder(sbOrderId, pIsEquiry);
				acqOrderCapture = convertSbOrderToAcqOrderCapture(sbOrder);
			}
		}	
		return acqOrderCapture;
	}

	private boolean createNewCustomer(String sbOrderId, AcqOrderCaptureDTO acqOrderCapture, String userId) {
		try {
			LtsAcqPersonalInfoFormDTO ltsPersonalInfoFormDTO = acqOrderCapture.getLtsAcqPersonalInfoForm();
			LtsAddressRolloutFormDTO addressRolloutDTO = acqOrderCapture.getLtsAddressRolloutForm();
			LtsAcqSalesInfoFormDTO salesInfo = acqOrderCapture.getLtsAcqSalesInfoForm();
			CustomerDetailProfileLtsDTO custDetailDTO = customerProfileLtsService.getCustByDoc(
					ltsPersonalInfoFormDTO.getDocumentType(), ltsPersonalInfoFormDTO.getDocNum(), LtsConstant.SYSTEM_ID_DRG);
			if (custDetailDTO==null) {
				CustomerDTO custDTO = bomWsCreateCustClient.ltsAcqCreateNewCust(ltsPersonalInfoFormDTO, addressRolloutDTO, salesInfo.getStaffId());
				if(custDTO != null){
					custDetailDTO = customerProfileLtsService.getCustByDoc(custDTO.getIdDocType(), custDTO.getIdDocNum(), LtsConstant.SYSTEM_ID_DRG);
				}
			} 
			if (custDetailDTO!=null) {
				acqOrderCapture.setCustomerDetailProfileLtsDTO(custDetailDTO);
				acqOrderCapture.getLtsAcqPersonalInfoForm().setCustNum(custDetailDTO.getCustNum());
				ltsAcqCustomerDetailService.updateDummyCustNum(sbOrderId, custDetailDTO.getCustNum(), userId);
				return true;
			}
						
		} catch (Exception ex) {
			logger.error("Error in createNewCustomer");
		}
		
		return false;
	}
	
	private boolean createNewAccount(String sbOrderId, AcqOrderCaptureDTO acqOrderCapture, String userId) {
		try {
			CustomerDetailProfileLtsDTO cust = acqOrderCapture.getCustomerDetailProfileLtsDTO();				 
			LtsAddressRolloutFormDTO addressRollout = acqOrderCapture.getLtsAddressRolloutForm();
			LtsAcqBillingAddressFormDTO billAddr = acqOrderCapture.getLtsAcqBillingAddressForm();
			LtsAcqSalesInfoFormDTO salesInfo = acqOrderCapture.getLtsAcqSalesInfoForm();
			LtsAcqBillMediaBillLangFormDTO billLangForm = acqOrderCapture.getLtsAcqBillMediaBillLangForm();
			AccountDTO acctDTO = bomWsCreateAcctClient.ltsAcqCreateNewAcct(cust, billLangForm.getBillMediaDtlList().get(0),
					 addressRollout, billAddr, LtsSbHelper.getLtsEyeService(acqOrderCapture.getSbOrder())!=null, salesInfo.getStaffId());
			 if(acctDTO != null){
				AccountDetailProfileLtsDTO acctInfo = customerProfileLtsService.getAccountbyAcctNum(acctDTO.getAcctNum(), LtsConstant.SYSTEM_ID_DRG);
				if(acctInfo != null){
					acctInfo.setAcctChrgType("RIAP");
					acctInfo.setPrimaryAcctInd(true);
					billLangForm.getBillMediaDtlList().get(0).setAcctNum(acctInfo.getAcctNum());
					billLangForm.getBillMediaDtlList().get(0).setPrimaryAcct(true);					
					AccountDetailProfileLtsDTO[] acctDTOs = {acctInfo};
					acqOrderCapture.setAccountDetailProfileLtsDTO(acctDTOs);
					acqOrderCapture.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList().get(0).setAcctNum(acctInfo.getAcctNum());
					ltsAcqAccountDetailService.updateDummyAcctNum(sbOrderId, acctInfo.getAcctNum(), userId);
					return true;
			    }
			 }
		} catch (Exception ex) {
			logger.error("Error in createNewAccount");
		}
		
		return false;
	}
	
	private AcqOrderCaptureDTO convertSbOrderToAcqOrderCapture(SbOrderDTO sbOrder) {
		if (sbOrder == null) {
			return null;
		}
		
		AcqOrderCaptureDTO acqOrderCapture = new AcqOrderCaptureDTO();
		
		BasketDetailDTO basketDetail = getSelectedBasketDetail(sbOrder);
		
		ServiceDetailDTO serviceDtl = LtsSbOrderHelper.getLtsService(sbOrder);
		
		ServiceDetailLtsDTO eyeSrvDtlLts = LtsSbOrderHelper.getLtsEyeService(sbOrder);
		
		ServiceDetailLtsDTO delSrvDtlLts = LtsSbOrderHelper.getDelServices(sbOrder);
		
		AccountDetailLtsDTO rAcct = new AccountDetailLtsDTO();
		
        List<AccountServiceAssignLtsDTO> acctList = new ArrayList<AccountServiceAssignLtsDTO>();
		
		for (AccountServiceAssignLtsDTO acctDtl: serviceDtl.getAccounts()){
			if (StringUtils.equals(acctDtl.getChrgType(), "R")){
				acctList.add(acctDtl);
			}
		}
		
		rAcct = acctList.toArray(new AccountServiceAssignLtsDTO[acctList.size()])[0].getAccount();

		for (AccountServiceAssignLtsDTO acctDtl: serviceDtl.getAccounts()){
			if (StringUtils.equals(acctDtl.getChrgType(), "I") && !StringUtils.equals(acctList.get(0).getAccount().getAcctNo(), acctDtl.getAccount().getAcctNo())){
				acctList.add(acctDtl);
			}	
		}
		
		AccountServiceAssignLtsDTO[] accts = acctList.toArray(new AccountServiceAssignLtsDTO[acctList.size()]);
		
		
//		acqOrderCapture.setLtsServiceProfile(createDummyServiceDetailProfile(sbOrder));
		acqOrderCapture.setAccountDetailProfileLtsDTO(createAccountDetailProfileLts(serviceDtl));
		acqOrderCapture.setCustomerDetailProfileLtsDTO(createCustomerDetailProfileLts(rAcct));
		acqOrderCapture.setSecondDelServiceProfile(createDummy2ndDelServiceDetailProfile(sbOrder));
		acqOrderCapture.setLtsAcqAccountSelectionForm(createDummyLtsAcqAccountSelectionForm(sbOrder));
		acqOrderCapture.setLtsAcqPaymentMethodFormDTO(createLtsAcqPaymentMethodForm(sbOrder, acqOrderCapture.getAccountDetailProfileLtsDTO(), accts));//TODO
		acqOrderCapture.setLtsAcqBillMediaBillLangForm(createLtsAcqBillMediaBillLangForm(sbOrder, acqOrderCapture.getAccountDetailProfileLtsDTO()));
		acqOrderCapture.setLtsAcqBillingAddressForm(createLtsAcqBillingAddressForm(sbOrder, accts));
		acqOrderCapture.setLtsAcqSalesInfoForm(createLtsAcqSalesInfoForm(sbOrder));
		acqOrderCapture.setLtsAcqOtherVoiceServiceForm(createLtsAcqOtherVoiceServiceForm(sbOrder));
		acqOrderCapture.setLtsModemArrangementForm(createLtsModemArrangementForm(sbOrder));
		acqOrderCapture.setLtsAcqPersonalInfoForm(createLtsAcqPersonalInfoForm(sbOrder));
//		orderCapture.setLtsCustomerIdentificationForm(createLtsCustomerIdentificationForm(sbOrder));
		acqOrderCapture.setLtsAddressRolloutForm(createLtsAddressRolloutForm(sbOrder, serviceDtl, rAcct)); 
//		orderCapture.setLtsMiscellaneousForm(createLtsMiscellaneousForm(sbOrder));
		acqOrderCapture.setLtsAcqSupportDocForm(createLtsAcqSupportDocForm(sbOrder));
		acqOrderCapture.setAddressRollout(createAddressRollout(sbOrder));
		acqOrderCapture.setModemTechnologyAssign(createModemTechnologyAssign(sbOrder));
		acqOrderCapture.setRelatedPcdOrder(createRelatedPcdOrder(sbOrder, acqOrderCapture));
		acqOrderCapture.setSbOrder(sbOrder);
		acqOrderCapture.setOrderAction(LtsConstant.ORDER_ACTION_RECALL);
//		orderCapture.setLtsDeviceSelectionForm(createDeviceSelection(sbOrder, basketDetail));
		acqOrderCapture.setLtsAcqBasketSelectionForm(createBasketSelection(sbOrder, basketDetail));
		acqOrderCapture.setLtsPremiumSelectionForm(createPremiumSelection(sbOrder));
/*		orderCapture.setLtsRecontractForm(createLtsRecontractForm(sbOrder));
		orderCapture.setLtsWqRemarkForm(createLtsWqRemarkForm(sbOrder));*/
		
		acqOrderCapture.setLtsAcqNumSelectionAndPipbForm(createLtsAcqNumSelectionAndPipbForm(sbOrder));
		acqOrderCapture.setLtsAcqNumConfirmationForm(createLtsAcqConfirmationForm(sbOrder));
		acqOrderCapture.setSelectedBasket(basketDetail);		
		acqOrderCapture.setSuspendRemark(getRemark(sbOrder, LtsConstant.REMARK_TYPE_SUSPEND_REMARK));
		acqOrderCapture.setLtsDsOrderInfo(sbOrder.getLtsDsOrderInfo());
		if (eyeSrvDtlLts == null){
			acqOrderCapture.setEyeOrder(false);
			SetInd(sbOrder, acqOrderCapture, delSrvDtlLts.getItemDtls());
		}else{
			acqOrderCapture.setEyeOrder(true);
			SetInd(sbOrder, acqOrderCapture, eyeSrvDtlLts.getItemDtls());
		}
		acqOrderCapture.setLtsAcqAppointmentForm(createLtsAcqAppointmentForm(sbOrder, acqOrderCapture));

		return acqOrderCapture;
	}
	
	private void SetInd(SbOrderDTO sbOrder, AcqOrderCaptureDTO acqOrderCapture, ItemDetailLtsDTO[] items){
		
		String[] ITEM_VAS_PRE_WIRING = new String[] {LtsBackendConstant.ITEM_TYPE_VAS_PRE_WIRING};
		String[] ITEM_PREPAYMENT = new String[] {LtsConstant.ITEM_TYPE_PREPAY};
		String[] ITEM_IDD_0060 = new String[] {LtsConstant.ITEM_TYPE_IDD, LtsConstant.ITEM_TYPE_0060E};
		String[] ITEM_FFP = new String[] {LtsBackendConstant.ITEM_TYPE_FFP_HOT, LtsBackendConstant.ITEM_TYPE_FFP_OTHER, LtsBackendConstant.ITEM_TYPE_FFP_STAFF, LtsBackendConstant.ITEM_TYPE_VAS_FFP};
		String[] ITEM_VAS_PRE_INSTALL = new String[] {LtsBackendConstant.ITEM_TYPE_VAS_PRE_INSTALL};
		
		if (items == null) {
			return;
		}
		Map<String, ItemDetailLtsDTO> itemIdMap = new HashMap<String, ItemDetailLtsDTO>();
		
		for (int i=0; items!=null && i<items.length; ++i) {
			itemIdMap.put(items[i].getItemId(), items[i]);
		}
		List<ItemDetailDTO> itemDescList =  this.ltsOfferService.getItemWithAttb(itemIdMap.keySet().toArray(new String[itemIdMap.size()]), LtsConstant.DISPLAY_TYPE_ITEM_SELECT, LtsConstant.LOCALE_ENG, false);
		ItemDetailSummaryDTO itemDtlSummary = null;
		
		for (int i=0; i<itemDescList.size(); ++i) {
			itemDtlSummary = this.generateItemSummaryDTO(itemDescList.get(i), itemIdMap.get(itemDescList.get(i).getItemId()));
			itemIdMap.remove(itemDtlSummary.getItemId());
			
			if (!StringUtils.equals(LtsConstant.IO_IND_OUT, itemDtlSummary.getIoInd())
					&& !StringUtils.equals(LtsConstant.IO_IND_SPACE, itemDtlSummary.getIoInd())) {
				if (LtsSbOrderHelper.isContainString(ITEM_VAS_PRE_WIRING, itemDtlSummary.getItemType())) {
					acqOrderCapture.setContainPrewiringVAS(true);
				}
				if (LtsSbOrderHelper.isContainString(ITEM_VAS_PRE_INSTALL, itemDtlSummary.getItemType())) {
					acqOrderCapture.setContainPreInstallVAS(true);
				}
				if (LtsSbOrderHelper.isContainString(ITEM_PREPAYMENT, itemDtlSummary.getItemType())) {
					acqOrderCapture.setContainPrepayment(true);
				}
				if (LtsSbOrderHelper.isContainString(ITEM_IDD_0060, itemDtlSummary.getItemType())) {
					acqOrderCapture.setContainIddVAS(true);
				}
				if (LtsSbOrderHelper.isContainString(ITEM_FFP, itemDtlSummary.getItemType())) {
					acqOrderCapture.setContainFfpVAS(true);
				}				
			}
		}
	}
	
    private ItemDetailSummaryDTO generateItemSummaryDTO(ItemDetailDTO pItemDesc, ItemDetailLtsDTO pItem) {
		
		ItemDetailSummaryDTO itemSummary = new ItemDetailSummaryDTO();
		BeanUtils.copyProperties(pItemDesc, itemSummary);
		itemSummary.setBasketId(pItem.getBasketId());
		itemSummary.setItemQty(pItem.getItemQty());
		itemSummary.setIoInd(pItem.getIoInd());
		itemSummary.setPaidVas(StringUtils.equals("Y", pItem.getPaidVasInd()));
		itemSummary.setItemAttbs(this.generateItemAttbSummaryDTOs(pItemDesc.getItemAttbs(), pItem.getItemAttbs()));
		itemSummary.setPenaltyHandling(pItem.getPenaltyHandling());

		try {
			if (StringUtils.isNotBlank(pItem.getExistEndDate())) {
				Date sysdate = new Date();
				Date tpExpDate = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH).parse(pItem.getExistEndDate());
				itemSummary.setExpired(tpExpDate.before(sysdate));
			} else {
				itemSummary.setExpired(false);
			}
		} catch (ParseException e) {}
		return itemSummary;
	}
    
	private ItemAttbDTO[] generateItemAttbSummaryDTOs(ItemAttbDTO[] pAttbDescs, ItemAttributeDetailLtsDTO[] pAttbValues) {
		
		if (ArrayUtils.isEmpty(pAttbValues)) {
			return null;
		}
		List<ItemAttbDTO> attbList = new ArrayList<ItemAttbDTO>();
		ItemAttbDTO attbSummary = null;
		
		for (int i=0; i < pAttbValues.length; ++i) {
			for (int j=0; pAttbDescs!=null && j<pAttbDescs.length; ++j) {
				if (StringUtils.equals(pAttbDescs[j].getAttbID(), pAttbValues[i].getAttbCd())) {
					attbSummary = new ItemAttbDTO();
					BeanUtils.copyProperties(pAttbDescs[j], attbSummary);
					attbSummary.setAttbValue(pAttbValues[i].getAttbValue());
					attbList.add(attbSummary);
					break;
				}
			}
		}
		return attbList.toArray(new ItemAttbDTO[attbList.size()]);
	}
	
	private BasketDetailDTO getSelectedBasketDetail(SbOrderDTO sbOrder) {
		
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(sbOrder);
		String displayType = LtsConstant.DISPLAY_TYPE_RP_PROMOT;					
		
		for (ItemDetailLtsDTO itemDetail : ltsServiceDetail.getItemDtls()) {
			if (StringUtils.isNotBlank(itemDetail.getBasketId())) {
				return ltsBasketService.getBasket(itemDetail.getBasketId(), LtsConstant.LOCALE_ENG, displayType);
			}
		}
		return null;
	}
	
/*	private AccountDetailProfileLtsDTO[] createAccounts(ServiceDetailDTO pServiceDtl) {
		
		return createAccountDetailProfileLts(pServiceDtl);
		
	}*/
	
	private ServiceDetailProfileLtsDTO createDummy2ndDelServiceDetailProfile(SbOrderDTO sbOrder) {
	
		ServiceDetailProfileLtsDTO serviceProfile = new ServiceDetailProfileLtsDTO();
		
		ServiceDetailLtsDTO new2ndDetailService = null;
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			
			if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
				continue;
			}
			
			if (LtsConstant.LTS_SRV_TYPE_2DEL.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())) {
				new2ndDetailService = (ServiceDetailLtsDTO)serviceDetail;
				break;
			}
		}
		
		if (new2ndDetailService == null) {
			return null;
		}
		
		
		AccountServiceAssignLtsDTO[] accountServiceAssignLtsDTO = new AccountServiceAssignLtsDTO[new2ndDetailService.getAccounts().length];
		accountServiceAssignLtsDTO = new2ndDetailService.getAccounts().clone();
		AccountDetailLtsDTO rAcct = new AccountDetailLtsDTO();
		
		for (int i=0; i<accountServiceAssignLtsDTO.length; i++){
			if(StringUtils.equals(accountServiceAssignLtsDTO[i].getChrgType(), "R")){
				rAcct = accountServiceAssignLtsDTO[i].getAccount();
			}
		}
		
		serviceProfile.setSrvNum(new2ndDetailService.getSrvNum());
		serviceProfile.setCcSrvMemNum(new2ndDetailService.getCcServiceMemNum());
		serviceProfile.setDnExchFrozen(StringUtils.equals(((ServiceDetailLtsDTO)new2ndDetailService).getFrozenExchInd(), "Y"));
		serviceProfile.setTwoNBuildInd(StringUtils.equals(((ServiceDetailLtsDTO)new2ndDetailService).getTwoNInd(), "Y"));
		serviceProfile.setCcSrvId(new2ndDetailService.getCcServiceId());
		serviceProfile.setAccounts(createAccountDetailProfileLts(new2ndDetailService));
		serviceProfile.setLtsTenure(StringUtils.isNotEmpty(new2ndDetailService.getTenure()) ? Integer.parseInt(new2ndDetailService.getTenure()) : 0 );
		serviceProfile.setPendingOcid(new2ndDetailService.getPendingOcid());
		serviceProfile.setPendingOcSrd(new2ndDetailService.getPendingOcidSrd());
		serviceProfile.setPrimaryCust(createCustomerDetailProfileLts(rAcct));
		serviceProfile.setSrvType(new2ndDetailService.getTypeOfSrv());
		serviceProfile.setSharedBsn(StringUtils.equals(((ServiceDetailLtsDTO)new2ndDetailService).getSharedBsnInd(), "Y"));
		return serviceProfile;
	}
	
	private AccountDetailProfileLtsDTO[] createAccountDetailProfileLts(ServiceDetailDTO serviceDetail) {
		AccountServiceAssignLtsDTO[] accountServiceAssignLtsDTO = new AccountServiceAssignLtsDTO[serviceDetail.getAccounts().length];
		accountServiceAssignLtsDTO = serviceDetail.getAccounts().clone();
//		AccountDetailLtsDTO[] accountDetailLtsDTO = new AccountDetailLtsDTO[serviceDetail.getAccounts().length];
		List<AccountDetailProfileLtsDTO> accountDetailProfileLtsList = new ArrayList<AccountDetailProfileLtsDTO>();
		String oldChrgType = null;
		String newChrgType = null;
		for (int i=0; i<accountServiceAssignLtsDTO.length; i++){
			
			if (accountDetailProfileLtsList.isEmpty()){
		        PaymentMethodDetailLtsDTO profilePaymentMethod = getExistingPaymentMethod(accountServiceAssignLtsDTO[i].getAccount().getPaymethods());
		        AccountDetailProfileLtsDTO accountDetailProfileLts = new AccountDetailProfileLtsDTO();
		
//		profilePaymentMethod = getExistingPaymentMethod(serviceDetail.getAccount().getPaymethods());
		        accountDetailProfileLts.setAcctNum(accountServiceAssignLtsDTO[i].getAccount().getAcctNo());
		        accountDetailProfileLts.setBillFreq(accountServiceAssignLtsDTO[i].getAccount().getBillFreq());
		        accountDetailProfileLts.setBillLang(accountServiceAssignLtsDTO[i].getAccount().getBillLang());
		        accountDetailProfileLts.setBillMedia(accountServiceAssignLtsDTO[i].getAccount().getExistBillMedia());
		        accountDetailProfileLts.setAcctChrgType(accountServiceAssignLtsDTO[i].getChrgType());
		
		/*profilePaymentMethod could be null if it is recontract order*/
	 	        if(profilePaymentMethod != null){
			        accountDetailProfileLts.setPayMethod(profilePaymentMethod.getPayMtdType());
			        accountDetailProfileLts.setBankAcctNum(profilePaymentMethod.getBankAcctNo());
			        accountDetailProfileLts.setBankCd(profilePaymentMethod.getBankCd());
			        accountDetailProfileLts.setBranchCd(profilePaymentMethod.getBranchCd());
			        accountDetailProfileLts.setCreditCardNum(profilePaymentMethod.getCcNum());
		        }
/*	 	       System.out.println("i round " + i);
	 	       System.out.println("acct no added " + accountServiceAssignLtsDTO[i].getAccount().getAcctNo());
	 	       System.out.println("  ");*/
	 	       accountDetailProfileLtsList.add(accountDetailProfileLts);
		    }else{
		    	for (int j=0; j<accountDetailProfileLtsList.size(); j++){
		    		
/*	    			System.out.println("i round " + i);
	    			System.out.println("j round " + j);
	    			System.out.println("j acct num " + j + " " + accountDetailProfileLtsList.get(j).getAcctNum());
	    			System.out.println("j ChrgType " + j + " " + accountDetailProfileLtsList.get(j).getAcctChrgType());
	    			System.out.println("i acct num " + i + " " + accountServiceAssignLtsDTO[i].getAccount().getAcctNo());
	    			System.out.println("i ChrgType " + i + " " + accountServiceAssignLtsDTO[i].getChrgType());
	    			System.out.println("  ");*/
	    			
		    		if(StringUtils.equals(accountDetailProfileLtsList.get(j).getAcctNum(), accountServiceAssignLtsDTO[i].getAccount().getAcctNo())){
		    			oldChrgType = accountDetailProfileLtsList.get(j).getAcctChrgType();
		    			newChrgType = accountServiceAssignLtsDTO[i].getChrgType();
		    			if (!StringUtils.equals(oldChrgType, newChrgType)){
		    			accountDetailProfileLtsList.get(j).setAcctChrgType(oldChrgType + newChrgType); 
		    			}
		    		}else if (j == accountDetailProfileLtsList.size()-1){
		    			PaymentMethodDetailLtsDTO profilePaymentMethod = getExistingPaymentMethod(accountServiceAssignLtsDTO[i].getAccount().getPaymethods());
				        AccountDetailProfileLtsDTO accountDetailProfileLts = new AccountDetailProfileLtsDTO();
				
//				profilePaymentMethod = getExistingPaymentMethod(serviceDetail.getAccount().getPaymethods());
				        accountDetailProfileLts.setAcctNum(accountServiceAssignLtsDTO[i].getAccount().getAcctNo());
				        accountDetailProfileLts.setBillFreq(accountServiceAssignLtsDTO[i].getAccount().getBillFreq());
				        accountDetailProfileLts.setBillLang(accountServiceAssignLtsDTO[i].getAccount().getBillLang());
				        accountDetailProfileLts.setBillMedia(accountServiceAssignLtsDTO[i].getAccount().getExistBillMedia());
				        accountDetailProfileLts.setAcctChrgType(accountServiceAssignLtsDTO[i].getChrgType());

				
				/*profilePaymentMethod could be null if it is recontract order*/
			 	        if(profilePaymentMethod != null){
					        accountDetailProfileLts.setPayMethod(profilePaymentMethod.getPayMtdType());
					        accountDetailProfileLts.setBankAcctNum(profilePaymentMethod.getBankAcctNo());
					        accountDetailProfileLts.setBankCd(profilePaymentMethod.getBankCd());
					        accountDetailProfileLts.setBranchCd(profilePaymentMethod.getBranchCd());
					        accountDetailProfileLts.setCreditCardNum(profilePaymentMethod.getCcNum());
				        }
			 	        accountDetailProfileLtsList.add(accountDetailProfileLts);
		    		}
		    	}
		    }
		}
		
		AccountDetailProfileLtsDTO[] accountDetailProfileLtsDTO = accountDetailProfileLtsList.toArray(new AccountDetailProfileLtsDTO[accountDetailProfileLtsList.size()]);
		

		 final String ORDER= "RIAP";
		 String unSortChrgType[];
		 String sortedChrgType;

		 // sort chrgtype tp "RIAP"
		 for (int i=0; i<accountDetailProfileLtsDTO.length; i++){
			 
			 unSortChrgType = accountDetailProfileLtsDTO[i].getAcctChrgType().split("");		 
			 sortedChrgType = "";

		     Arrays.sort(unSortChrgType, new Comparator<String>() {
		         public int compare(String str1, String str2) {
		            return ORDER.indexOf(str1) -  ORDER.indexOf(str2) ;
		         }
		     });
		     
		     for (int j=0; j<unSortChrgType.length; j++){
				 sortedChrgType = sortedChrgType +  unSortChrgType[j];
			 }
		     
		     accountDetailProfileLtsDTO[i].setAcctChrgType(sortedChrgType);
		     
		     // set primary ind "true" for R account
		     if (accountDetailProfileLtsDTO[i].getAcctChrgType().contains("R")){
		    	 accountDetailProfileLtsDTO[i].setPrimaryAcctInd(true);
		     }
		 
		 }
		
		return accountDetailProfileLtsDTO;
	}
	
	private PaymentMethodDetailLtsDTO getExistingPaymentMethod(PaymentMethodDetailLtsDTO[] paymentMethods) {
		
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
	
/*	private CustomerDetailProfileLtsDTO createCustomerDetailProfileLts(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO serviceDtl = LtsSbOrderHelper.getLtsService(sbOrder);
		AccountServiceAssignLtsDTO[] accountServiceAssignLtsDTO = new AccountServiceAssignLtsDTO[serviceDtl.getAccounts().length];
		accountServiceAssignLtsDTO = serviceDtl.getAccounts().clone();
		
		for (int i=0; i<accountServiceAssignLtsDTO.length; i++)
		if(accountServiceAssignLtsDTO[i].getAccount().getChrgType().contains("R")){
		    serviceDtl.setAccount(accountServiceAssignLtsDTO[i].getAccount());
		}
		
		return createCustomerDetailProfileLts(serviceDtl);
	}*/
	
	private CustomerDetailProfileLtsDTO createCustomerDetailProfileLts(AccountDetailLtsDTO pRAcct) {
		
		
		CustomerDetailLtsDTO customerDetailLts = pRAcct.getCustomer();
		
		if (customerDetailLts == null) {
			return null;
		}
		
//		CustomerDetailProfileLtsDTO profileCustomer = new CustomerDetailProfileLtsDTO();
		CustomerDetailProfileLtsDTO profileCustomer = customerProfileLtsService.getCustByCustNum(customerDetailLts.getCustNo(), LtsConstant.SYSTEM_ID_DRG);
		
		if(profileCustomer == null){
			profileCustomer = new CustomerDetailProfileLtsDTO();
		}
		
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
	
	public LtsAcqAppointmentFormDTO createLtsAcqAppointmentForm(SbOrderDTO sbOrder, AcqOrderCaptureDTO acqOrderCapture) {

		LtsAcqAppointmentFormDTO ltsAppointmentForm = null;
		
		ServiceDetailLtsDTO serviceDtl = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailDTO secDelService = LtsSbOrderHelper.get2ndDelService(sbOrder);
		ServiceDetailDTO portLaterService = LtsSbHelper.getAcqPortLaterService(sbOrder);
		
		if (serviceDtl == null || serviceDtl.getAppointmentDtl() == null) {
			return null;
		}
		
		ltsAppointmentForm = new LtsAcqAppointmentFormDTO();
		AppointmentDetailLtsDTO appointment = serviceDtl.getAppointmentDtl();
		ltsAppointmentForm.setPreBookSerialNum(appointment.getSerialNum());
		//upgrade
		if(StringUtils.isNotEmpty(appointment.getAppntStartTime()) && StringUtils.isNotEmpty(appointment.getAppntEndTime())){
			LtsAppointmentDetail instDtl = createAppntDtl(appointment, ltsAppointmentForm);
			ltsAppointmentForm.setInstallAppntDtl(instDtl);
			ltsAppointmentForm.setConfirmedInd(true);
		}else{
			ltsAppointmentForm.setConfirmedInd(false);
		}

		// prewiring
		if (StringUtils.isNotEmpty(appointment.getPreWiringStartTime())
				&& StringUtils.isNotEmpty(appointment.getPreWiringEndTime())) {
			String startDateTime = appointment.getPreWiringStartTime();
			String endDateTime = appointment.getPreWiringEndTime();
			
			LtsAppointmentDetail prewiringDtl = ltsAppointmentForm.new LtsAppointmentDetail();
			prewiringDtl.setAppntDate(startDateTime.split(" ")[0]);
			prewiringDtl.setAppntTimeSlotAndType(startDateTime.split(" ")[1].concat("-").concat(endDateTime.split(" ")[1])
					+ LtsAppointmentHelper.TIMESLOT_DELIMITER + appointment.getAppntType());
			ltsAppointmentForm.setPreWiringAppntDtl(prewiringDtl);
		}

		ltsAppointmentForm.setCustomerContactMobileNum(appointment.getCustContactMobile());
		ltsAppointmentForm.setCustomerContactFixLineNum(appointment.getCustContactFix());
		ltsAppointmentForm.setInstallationContactName(appointment.getInstContactName());
		ltsAppointmentForm.setInstallationContactNum(appointment.getInstContactNum());
		ltsAppointmentForm.setInstallationMobileSMSAlert(appointment.getInstSmsNum());

		if(secDelService != null){
			AppointmentDetailLtsDTO secDel = secDelService.getAppointmentDtl();
			ltsAppointmentForm.setSecDelInstallAppntDtl(createAppntDtl(secDel, ltsAppointmentForm));
		}
		
		if(portLaterService != null){
			AppointmentDetailLtsDTO portLater = portLaterService.getAppointmentDtl();
			ltsAppointmentForm.setPortLaterAppntDtl(createAppntDtl(portLater, ltsAppointmentForm));
		}
		
		if(sbOrder.getLtsDsOrderInfo() != null){
			//could be null
			if(StringUtils.equals(sbOrder.getLtsDsOrderInfo().getPeLink(), "Y")){
				ltsAppointmentForm.setPeLinkInd(true);
			}else if(StringUtils.equals(sbOrder.getLtsDsOrderInfo().getPeLink(), "N")){
				ltsAppointmentForm.setPeLinkInd(false);
			}
			
			ltsAppointmentForm.setWaiveCoolingOffInd(StringUtils.equals(sbOrder.getLtsDsOrderInfo().getWaiveCloff(), "Y"));
		}
		
		ltsAppointmentForm.setRemarks(createRemark(sbOrder, LtsConstant.REMARK_TYPE_ADD_ON_REMARK));
		
		//BOM2018061
		ltsAppointmentForm.setEpdItemList(
				ltsAppointmentService.getEpdItemList(
						acqOrderCapture.getBasketChannelId(), 
						LtsDateFormatHelper.getSysDate("dd/MM/yyyy"), LtsConstant.LOCALE_ENG));
		
		ItemDetailLtsDTO epdOrdItem = null;
		for(ItemDetailLtsDTO item: serviceDtl.getItemDtls()){
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
		
		return ltsAppointmentForm;
	}
	
	private LtsAppointmentDetail createAppntDtl(AppointmentDetailLtsDTO appntDtlLts, LtsAcqAppointmentFormDTO form){
		if (StringUtils.isEmpty(appntDtlLts.getAppntStartTime()) || StringUtils.isEmpty(appntDtlLts.getAppntEndTime())) {
			return null;
		}
		
		String startDateTime = appntDtlLts.getAppntStartTime();
		String endDateTime = appntDtlLts.getAppntEndTime();
		LtsAppointmentDetail appntDtl = form.new LtsAppointmentDetail();
		appntDtl.setAppntDate(startDateTime.split(" ")[0]);
		appntDtl.setAppntTimeSlotAndType(startDateTime.split(" ")[1].concat("-").concat(endDateTime.split(" ")[1])
				+ LtsAppointmentHelper.TIMESLOT_DELIMITER + appntDtlLts.getAppntType());
		
		//cutOver
		if (StringUtils.isNotEmpty(appntDtlLts.getCutOverStartTime()) && StringUtils.isNotEmpty(appntDtlLts.getCutOverEndTime())) {
			startDateTime = appntDtlLts.getCutOverStartTime();
			endDateTime = appntDtlLts.getCutOverEndTime();
			appntDtl.setCutOverDate(startDateTime.split(" ")[0]);
			appntDtl.setCutOverTime(startDateTime.split(" ")[1].concat("-").concat(endDateTime.split(" ")[1]));
		}
		
		return appntDtl;
	}
	
	public String createRemark(SbOrderDTO sbOrder, String remarkType) {
		
		ServiceDetailDTO serviceDtl = LtsSbHelper.getLtsService(sbOrder);
		
		if (serviceDtl.getRemarks() == null || ArrayUtils.isEmpty(serviceDtl.getRemarks())) {
			return null;
		}
		
		return serviceDtl.remarkToString(LtsConstant.REMARK_TYPE_ADD_ON_REMARK);
	}
	
    public String getRemark(SbOrderDTO sbOrder, String remarkType) {
		
		ServiceDetailDTO serviceDtl = LtsSbHelper.getLtsService(sbOrder);
		
		if (serviceDtl.getRemarks() == null || ArrayUtils.isEmpty(serviceDtl.getRemarks())) {
			return null;
		}
		
		return serviceDtl.remarkToString(remarkType);
	}
	
	public LtsAcqPaymentMethodFormDTO createLtsAcqPaymentMethodForm(SbOrderDTO sbOrder, AccountDetailProfileLtsDTO[] pAcctDetailProfile, AccountServiceAssignLtsDTO[] pAccts) {
		
		
		LtsAcqPaymentMethodFormDTO ltsPaymentForm = new LtsAcqPaymentMethodFormDTO();
		
		AccountDetailLtsDTO acct = new AccountDetailLtsDTO();
		
		ServiceDetailLtsDTO serviceDtl = LtsSbHelper.getLtsService(sbOrder);
/*		AccountServiceAssignLtsDTO[] accountServiceAssignLtsDTO = new AccountServiceAssignLtsDTO[serviceDtl.getAccounts().length];
		accountServiceAssignLtsDTO = serviceDtl.getAccounts().clone();*/
		
		List<PaymentMethodDtl> paymentMethodDtlList = new ArrayList<PaymentMethodDtl>();
		
		for (int i=0; i<pAcctDetailProfile.length; i++){
			
			for (int j=0; j<pAccts.length; j++){
				if (StringUtils.equals(pAcctDetailProfile[i].getAcctNum(), pAccts[j].getAccount().getAcctNo())){
						acct = pAccts[j].getAccount();
				}
			}
				
		    PaymentMethodDetailLtsDTO[] payments = acct.getPaymethods();
		
		    if (ArrayUtils.isEmpty(payments)) {
			    return null;
		    }

//		ltsPaymentForm.setSelectButton("");
//		ltsPaymentForm.setExistBillAccNum(serviceDtl.getAccount().getAcctNo());

		    PaymentMethodDtl payMtdDtl = ltsPaymentForm.new PaymentMethodDtl();

		    payMtdDtl.setAcctNum(pAcctDetailProfile[i].getAcctNum());
		    payMtdDtl.setAcctProfile(pAcctDetailProfile[i]);
	
		
		    for (PaymentMethodDetailLtsDTO payment : payments) {
			
			if (StringUtils.equals(LtsConstant.IO_IND_INSTALL, payment.getAction())
					|| "Y".equals(serviceDtl.getRecontractInd())) {
				payMtdDtl.setNewPayMethodType(payment.getPayMtdType());
				payMtdDtl.setSalesMemoNum(payment.getSalesMemoNum());
				if(StringUtils.equals(payment.getPayMtdType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
					payMtdDtl.setApplicationDate(payment.getAutopayAppDate());
					payMtdDtl.setAutoPayUpperLimit(payment.getAutopayUpLimAmt());
					payMtdDtl.setBankAccHolderDocNum(payment.getBankAcctHoldNum());
					payMtdDtl.setBankAccHolderDocType(payment.getBankAcctHoldType());
					payMtdDtl.setBankAccHolderName(payment.getBankAcctHoldName());
					payMtdDtl.setBankAccNum(payment.getBankAcctNo());
					payMtdDtl.setBankCode(payment.getBankCd());
					payMtdDtl.setBranchCode(payment.getBranchCd());
					payMtdDtl.setBranchCodeHidden(payment.getBranchCd());
					payMtdDtl.setThirdPartyBankAccount(StringUtils.equals("Y", payment.getThirdPartyInd()));
//					payMtdDtl.setSelectButton(LtsConstant.PAYMENT_TYPE_AUTO_PAY);					
				}
				else if(StringUtils.equals(payment.getPayMtdType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
					payMtdDtl.setCardHolderDocNum(payment.getCcIdDocNo());
					payMtdDtl.setCardHolderDocType(payment.getCcIdDocType());
					payMtdDtl.setCardHolderName(payment.getCcHoldName());
					payMtdDtl.setCardNum(payment.getCcNum());
					payMtdDtl.setCardType(payment.getCcType());
					payMtdDtl.setCardVerified(StringUtils.equals("Y", payment.getCcVerifiedInd()));
					payMtdDtl.setThirdPartyCard(StringUtils.equals("Y", payment.getThirdPartyInd()));
					String expDate = payment.getCcExpDate();
					if (StringUtils.isNotEmpty(expDate)) {
						payMtdDtl.setExpireMonth(Integer.parseInt(expDate.split("/")[0]));
						payMtdDtl.setExpireYear(Integer.parseInt(expDate.split("/")[1]));
					}
//					payMtdDtl.setSelectButton(LtsConstant.PAYMENT_TYPE_CREDIT_CARD);
				}
			}
			else {
				if (!StringUtils.equals(LtsConstant.IO_IND_OUT, payment.getAction())){
					payMtdDtl.setExistingPayMethodType(payment.getPayMtdType());
					payMtdDtl.setNewPayMethodType(payment.getPayMtdType());
					payMtdDtl.setSalesMemoNum(payment.getSalesMemoNum());
				}
			}
			
		}

//		List<ItemDetailDTO> erChargeItemList = new ArrayList<ItemDetailDTO>();
		
		for(ItemDetailLtsDTO itemDtl: serviceDtl.getItemDtls()){
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
			}
			if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PREPAY)){
				paymentFormItem.setSelected(true);
				if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(payMtdDtl.getNewPayMethodType())){
					payMtdDtl.setCreditCardPrePayItem(paymentFormItem);
				}else if(LtsConstant.PAYMENT_TYPE_AUTO_PAY.equals(payMtdDtl.getNewPayMethodType())){
					payMtdDtl.setAutopayPrePayItem(paymentFormItem);
				}else if(LtsConstant.PAYMENT_TYPE_CASH.equals(payMtdDtl.getNewPayMethodType())){
					payMtdDtl.setCashPrePayItem(paymentFormItem);
				}
			}
		}
			
			
			
			// moved to createLtsAcqBillMediaBillLangForm(sbOrder)
/*			if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL)
					|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE)
							|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_EBILL)){
				ltsPaymentForm.setSelectedBillItemId(itemDtl.getItemId());
				ltsPaymentForm.setBillItemList(Lists.newArrayList(paymentFormItem));
			}
			
			if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_MYHKT_BILL)
					|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL)){
				ltsPaymentForm.setCsPortalItem(paymentFormItem);
			}
			
			if (StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_ER_CHARGE)) {
				paymentFormItem.setPenaltyHandling(itemDtl.getPenaltyHandling());
				erChargeItemList.add(paymentFormItem);
			}
			
		}
		

		if("Y".equals(serviceDtl.getAccount().getCustomer().getCsPortalInd())){
			ltsPaymentForm.setCspNewReg(true);
			ltsPaymentForm.setCspEmail(serviceDtl.getAccount().getCustomer().getCsPortalLogin());
			ltsPaymentForm.setCspMobile(serviceDtl.getAccount().getCustomer().getCsPortalMobile());
		}*/
		
		//moved to createLtsAcqBillingAddressForm(sbOrder)
/*		BillingAddressLtsDTO billAddrLts = serviceDtl.getAccount().getBillingAddress();

		if(billAddrLts != null
				&& !"Y".equals(serviceDtl.getErInd())
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
			ltsPaymentForm.setBillingAddrDtlList(addrSb.toString());
			ltsPaymentForm.setInstantUpdateBa("Y".equals(billAddrLts.getInstantUpdateInd()));
		}*/
		
		paymentMethodDtlList.add(payMtdDtl);
		}
		ltsPaymentForm.setPaymentMethodDtlList(paymentMethodDtlList);
		
		return ltsPaymentForm;
	}
	
	private LtsAcqAccountSelectionFormDTO createDummyLtsAcqAccountSelectionForm(SbOrderDTO sbOrder){
		LtsAcqAccountSelectionFormDTO form = new LtsAcqAccountSelectionFormDTO();

		ServiceDetailLtsDTO serviceDtl = LtsSbHelper.getLtsService(sbOrder);
		form.setNewAccountSelected("Y".equals(serviceDtl.getAccount().getIsNew()));
		
		return form;
	}
	
	private LtsAcqBillMediaBillLangFormDTO createLtsAcqBillMediaBillLangForm(SbOrderDTO sbOrder, AccountDetailProfileLtsDTO[] acctDetailProfile) {
		
		LtsAcqBillMediaBillLangFormDTO ltsAcqBillMediaBillLangForm = new LtsAcqBillMediaBillLangFormDTO();
		
		ServiceDetailLtsDTO serviceDtl = LtsSbHelper.getLtsService(sbOrder);
		List<BillMediaDtl> billMediaDtlList = new ArrayList<BillMediaDtl>();
		AccountServiceAssignLtsDTO[] accountServiceAssignLtsDTO = new AccountServiceAssignLtsDTO[serviceDtl.getAccounts().length];
		accountServiceAssignLtsDTO = serviceDtl.getAccounts().clone();
		
		
		ltsAcqBillMediaBillLangForm.setDummyEmailMobInd("N");
		
		for (int i=0; i < acctDetailProfile.length; i++)	{	
			BillMediaDtl billMediaDtl = ltsAcqBillMediaBillLangForm.new BillMediaDtl();
		
			billMediaDtl.setAcctNum(acctDetailProfile[i].getAcctNum());
			billMediaDtl.setBillLang(acctDetailProfile[i].getBillLang());
            if (acctDetailProfile[i].getAcctChrgType().contains("R")){
            	billMediaDtl.setPrimaryAcct(true);
            }
			
			for(ItemDetailLtsDTO itemDtl: serviceDtl.getItemDtls()){
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
				}
		        
				for (int j=0; j<accountServiceAssignLtsDTO.length; j++){
					if (StringUtils.equals(acctDetailProfile[i].getAcctNum(), accountServiceAssignLtsDTO[j].getAccount().getAcctNo())){
						if (StringUtils.equals(accountServiceAssignLtsDTO[j].getChrgType(), "I") ||
								StringUtils.equals(accountServiceAssignLtsDTO[j].getChrgType(), "R")){
				            if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL)
				            		|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_MYHKT_BILL)
				            	    || StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_THE_CLUB_BILL)
				            	    || StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL)){
			            		billMediaDtl.setCsPortalItem(paymentFormItem);
		    	                if ("Y".equals(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getCsPortalInd())) {
		    	                	billMediaDtl.setCspEmail(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getCsPortalLogin());
			    	    	        billMediaDtl.setCspMobile(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getCsPortalMobile());
			    	    	        billMediaDtl.setOptInOutInd(StringUtils.equals("Y", accountServiceAssignLtsDTO[j].getAccount().getCustomer().getHktOptOut()) ? LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_ALL : LtsCsPortalBackendConstant.LTS_CSP_OPT_IN_ALL);
			    	    	        if(StringUtils.contains(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getCsPortalLogin(), LtsBackendConstant.CSP_DUMMY_EMAIL_DOMAIN) ||
			    	    	        		StringUtils.equals(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getCsPortalMobile(), LtsBackendConstant.CSP_DUMMY_MOBILE_NUM)){
			    	    	        	ltsAcqBillMediaBillLangForm.setDummyEmailMobInd("Y");
			    	    	        }
		    	                }
		    	                if ("Y".equals(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getTheClubInd())) {
		    	                	billMediaDtl.setClubEmail(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getTheClubLogin());
		    	    	            billMediaDtl.setClubMobile(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getTheClubMobile());
		    	    	            billMediaDtl.setOptInOutInd(StringUtils.equals("Y", accountServiceAssignLtsDTO[j].getAccount().getCustomer().getClubOptOut()) ? LtsCsPortalBackendConstant.LTS_CSP_OPT_OUT_ALL : LtsCsPortalBackendConstant.LTS_CSP_OPT_IN_ALL);
		    	    	            billMediaDtl.setOptOutReason(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getClubOptRea());
		    	    	            billMediaDtl.setOptOutRemarks(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getClubOptRmk());
		    	    	            if(StringUtils.contains(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getTheClubLogin(), LtsBackendConstant.CSP_DUMMY_EMAIL_DOMAIN) ||
			    	    	        		StringUtils.equals(accountServiceAssignLtsDTO[j].getAccount().getCustomer().getTheClubMobile(), LtsBackendConstant.CSP_DUMMY_MOBILE_NUM)){
		    	    	            	ltsAcqBillMediaBillLangForm.setDummyEmailMobInd("Y");
			    	    	        }
		    	                }
				            }
						}
						
						if(StringUtils.equals(accountServiceAssignLtsDTO[j].getAccount().getBillMedia(), "P")){
						    if(StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL)
						    		|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL_BR)
						    		|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE)
						    		|| StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE)){
					            billMediaDtl.setPaperBillItem(paymentFormItem);
								if (StringUtils.equals(acctDetailProfile[i].getAcctNum(), accountServiceAssignLtsDTO[j].getAccount().getAcctNo())){
						            billMediaDtl.setSelectedWaiveReason(accountServiceAssignLtsDTO[j].getAccount().getWaivePaperReaCd());
						            billMediaDtl.setPaperBillWaiveOtherStaffId(accountServiceAssignLtsDTO[j].getAccount().getWaivePaperRemarks());
								}
				            }
					    }else if(StringUtils.equals(accountServiceAssignLtsDTO[j].getAccount().getBillMedia(), "S")){		    
				           if (StringUtils.equals(itemDtl.getItemType(), LtsConstant.ITEM_TYPE_EMAIL_BILL)){
					           billMediaDtl.setEmailBillItem(paymentFormItem);
				           }
						}
		            }
				}
			}
		    
		    billMediaDtlList.add(billMediaDtl);
		}
		
	    ltsAcqBillMediaBillLangForm.setBillMediaDtlList(billMediaDtlList);
		return ltsAcqBillMediaBillLangForm;
	}
	
	private LtsAcqBillingAddressFormDTO createLtsAcqBillingAddressForm(SbOrderDTO sbOrder, AccountServiceAssignLtsDTO[] pAccts) {
		
		LtsAcqBillingAddressFormDTO ltsAcqBillingAddressForm = new LtsAcqBillingAddressFormDTO();
		
		
		List<BillingAddrDtl> billingAddrDtlList = new ArrayList<BillingAddrDtl>();
		
		for ( int i=0; i<pAccts.length; i++){
			AccountDetailLtsDTO acct = pAccts[i].getAccount();						
		    BillingAddressLtsDTO billAddrLts = acct.getBillingAddress();
		    BillingAddrDtl billingAddrDtl = ltsAcqBillingAddressForm.new BillingAddrDtl();
		    
		    billingAddrDtl.setAcctNum(acct.getAcctNo());
		
		    StringBuilder addrSb = new StringBuilder();
		    addrSb.append(billAddrLts.getAddrLine1());
		    addrSb.append(billAddrLts.getAddrLine2());
		    addrSb.append(billAddrLts.getAddrLine3());
		    addrSb.append(billAddrLts.getAddrLine4());
		    addrSb.append(billAddrLts.getAddrLine5());
	    	addrSb.append(billAddrLts.getAddrLine6());
		
		    billingAddrDtl.setAcctBillingAddress(addrSb.toString());
		    
		    billingAddrDtl.setAddrLine1(billAddrLts.getAddrLine1());
		    billingAddrDtl.setAddrLine2(billAddrLts.getAddrLine2());
		    billingAddrDtl.setAddrLine3(billAddrLts.getAddrLine3());
		    billingAddrDtl.setAddrLine4(billAddrLts.getAddrLine4());
		    billingAddrDtl.setAddrLine5(billAddrLts.getAddrLine5());
		    billingAddrDtl.setAddrLine6(billAddrLts.getAddrLine6());
		    
		    billingAddrDtl.setBaCaAction(billAddrLts.getAction());
		    if (pAccts[i].getChrgType().contains("R")){
		        billingAddrDtl.setPrimaryAcct(true);
		    }
		    billingAddrDtlList.add(billingAddrDtl);
//		ltsAcqBillingAddressForm.setChangeBa(true);
		}
//		ltsAcqBillingAddressForm.setBillingAddrDtlList(Lists.newArrayList(billingAddrDtl));
		ltsAcqBillingAddressForm.setBillingAddrDtlList(billingAddrDtlList);
		
		return ltsAcqBillingAddressForm;
	}
	
	private LtsAcqSalesInfoFormDTO createLtsAcqSalesInfoForm(SbOrderDTO sbOrder) {
		
		LtsAcqSalesInfoFormDTO ltsSalesInfoForm = null;
		
		if (StringUtils.isNotEmpty(sbOrder.getStaffNum())) {
			ltsSalesInfoForm = new LtsAcqSalesInfoFormDTO();
			ltsSalesInfoForm.setSalesChannel(sbOrder.getSalesChannel());
			ltsSalesInfoForm.setSalesCode(sbOrder.getSalesCd());
			ltsSalesInfoForm.setSalesContact(sbOrder.getSalesContactNum());
			ltsSalesInfoForm.setSalesTeam(sbOrder.getSalesTeam());
			ltsSalesInfoForm.setStaffId(sbOrder.getStaffNum());
			ltsSalesInfoForm.setStaffName(sbOrder.getSalesName());
		}
		
		return ltsSalesInfoForm;
	}
	
	private LtsAcqOtherVoiceServiceFormDTO createLtsAcqOtherVoiceServiceForm(SbOrderDTO sbOrder) {
		
		LtsAcqOtherVoiceServiceFormDTO ltsAcqOtherVoiceServiceForm = new LtsAcqOtherVoiceServiceFormDTO();
		ltsAcqOtherVoiceServiceForm.setCreate2ndDel(false);
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			if (!(serviceDetail instanceof ServiceDetailLtsDTO)) {
				continue;
			}
			
			if (LtsConstant.LTS_PRODUCT_TYPE_2DEL.equals(serviceDetail.getToProd())) {
				if (LtsConstant.LTS_PRODUCT_TYPE_DEL.equals(serviceDetail.getFromProd())) {
					ltsAcqOtherVoiceServiceForm.setNew2ndDelDn(serviceDetail.getSrvNum());
					ltsAcqOtherVoiceServiceForm.setNew2ndDelSrvStatus(LtsConstant.INVENT_STS_WORKING);	
				}
				if (LtsConstant.LTS_PRODUCT_TYPE_NEW.equals(serviceDetail.getFromProd())) {
					ltsAcqOtherVoiceServiceForm.setNew2ndDelDn(serviceDetail.getSrvNum());
					ltsAcqOtherVoiceServiceForm.setNew2ndDelSrvStatus(LtsConstant.INVENT_STS_RESERVED);	
				}
			}
/*			else if (LtsConstant.LTS_SRV_TYPE_SIP.equals(((ServiceDetailLtsDTO) serviceDetail).getToSrvType())
					|| LtsConstant.LTS_SRV_TYPE_PE.equals(((ServiceDetailLtsDTO) serviceDetail).getToSrvType())) {
				
				if (LtsConstant.LTS_SRV_TYPE_DNX.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
					ltsAcqOtherVoiceServiceForm.setDuplexXSrvType(DuplexSrvType.UPGRADE);
				}
				if (LtsConstant.LTS_SRV_TYPE_DNY.equals(((ServiceDetailLtsDTO) serviceDetail).getFromSrvType())) {
					ltsAcqOtherVoiceServiceForm.setDuplexYSrvType(DuplexSrvType.UPGRADE);
				}
			}*/
			
			if (LtsSbOrderHelper.is2ndDelService(serviceDetail)) {
//				ltsAcqOtherVoiceServiceForm.setSecondDelBaCaChange(StringUtils.equals("Y", serviceDetail.getCopyErIaToBa()));
//				ltsOtherVoiceServiceForm.setSecondDelConfirmSameIa(secondDelConfirmSameIa)
//				ltsOtherVoiceServiceForm.setSecondDelDiffAddress(secondDelDiffAddress)
//				ltsOtherVoiceServiceForm.setSecondDelDiffCust(secondDelDiffCust)
				ltsAcqOtherVoiceServiceForm.setSecondDelDocNum(serviceDetail.getAccount().getCustomer().getIdDocNum());
				ltsAcqOtherVoiceServiceForm.setSecondDelDocType(serviceDetail.getAccount().getCustomer().getIdDocType());
				ltsAcqOtherVoiceServiceForm.setSecondDelDummyDoc(StringUtils.equals("Y", serviceDetail.getDummyDocIdInd()));
				ltsAcqOtherVoiceServiceForm.setSecondDelEr(StringUtils.equals("Y", serviceDetail.getErInd()));
				ltsAcqOtherVoiceServiceForm.setSecondDelThirdPartyAppl(StringUtils.equals("Y", serviceDetail.getThirdPartyAppln()));
				ltsAcqOtherVoiceServiceForm.setCreate2ndDel(true);
			}
		}
		
		return ltsAcqOtherVoiceServiceForm;
	}
	
	private LtsModemArrangementFormDTO createLtsModemArrangementForm(SbOrderDTO sbOrder) {
		LtsModemArrangementFormDTO ltsModemArrangementForm = new LtsModemArrangementFormDTO();
		
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		
		if (imsService == null || StringUtils.isEmpty(imsService.getShareFsaType())) {
			return null;
		}
		
		ltsModemArrangementForm.setModemType(ModemType.valueOf(imsService.getShareFsaType()));
		List<FsaDetailDTO> fsaDetailList = new ArrayList<FsaDetailDTO>();
		boolean lostModem = false;
		if(imsService.getLostModem() != null)
		{
			lostModem = imsService.getLostModem().equalsIgnoreCase("Y");
		}
		
		if (ltsModemArrangementForm.getModemType() == ModemType.SHARE_EX_FSA) {
			fsaDetailList.add(createSelectedFsaDetail(imsService));
			ltsModemArrangementForm.setExistingFsaList(fsaDetailList);			
			ltsModemArrangementForm.setLostModem(lostModem);
		}
		if (ltsModemArrangementForm.getModemType() == ModemType.SHARE_OTHER_FSA) {
			fsaDetailList.add(createSelectedFsaDetail(imsService));
			ltsModemArrangementForm.setOtherFsaList(fsaDetailList);
			ltsModemArrangementForm.setLostModem(lostModem);
		}
		if (ltsModemArrangementForm.getModemType() == ModemType.SHARE_BUNDLE || ltsModemArrangementForm.getModemType() == ModemType.SHARE_PCD) {
			ltsModemArrangementForm.setInputPcdSbOrderId(imsService.getRelatedSbOrderId());
		}
		
		ltsModemArrangementForm.setRentalRouterInd(null);
		if(StringUtils.equals(imsService.getShareRentalRouter(), "Y")){
			ltsModemArrangementForm.setRentalRouterInd(LtsConstant.ROUTER_OPTION_SHARE_RENTAL_ROUTER);
		}else if(StringUtils.equals(imsService.getShareRentalRouter(), "N")){
			ltsModemArrangementForm.setRentalRouterInd(LtsConstant.ROUTER_OPTION_BRM);
		}
		
		return ltsModemArrangementForm;
	}
	
	private FsaDetailDTO createSelectedFsaDetail(ServiceDetailOtherLtsDTO imsServiceDetail) {
		
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
		return selectedFsaDetail;
	}
	
	private LtsAcqPersonalInfoFormDTO createLtsAcqPersonalInfoForm (SbOrderDTO sbOrder) {
		
		LtsAcqPersonalInfoFormDTO ltsAcqPersonalInfoForm = new LtsAcqPersonalInfoFormDTO();
		ServiceDetailDTO serviceDtl = LtsSbHelper.getLtsService(sbOrder);
		CustomerDetailLtsDTO[] custInfo = sbOrder.getCustomers();
		ContactLtsDTO contactInfo = sbOrder.getContact();
		
		ltsAcqPersonalInfoForm.setThirdParty(StringUtils.equals("Y", serviceDtl.getThirdPartyAppln()));
		
		if (StringUtils.isNotEmpty(serviceDtl.getActualDocId())
				&& StringUtils.isNotEmpty(serviceDtl.getActualDocType())) {
			ltsAcqPersonalInfoForm.setDocNum(serviceDtl.getActualDocId());
			ltsAcqPersonalInfoForm.setDocumentType(serviceDtl.getActualDocType());
			
			if(LtsConstant.DOC_TYPE_HKID.equals(serviceDtl.getActualDocType())
					  || LtsConstant.DOC_TYPE_PASSPORT.equals(serviceDtl.getActualDocType())) {
				ltsAcqPersonalInfoForm.setGivenName(custInfo[0].getFirstName());
				ltsAcqPersonalInfoForm.setFamilyName(custInfo[0].getLastName());
				ltsAcqPersonalInfoForm.setTitle(custInfo[0].getTitle());
			} else {
				ltsAcqPersonalInfoForm.setCompanyName(custInfo[0].getCompanyName());				
			}
			ltsAcqPersonalInfoForm.setVerified(StringUtils.equals("Y", custInfo[0].getIdVerifiedInd()));
			if(StringUtils.equals("Y", custInfo[0].getMismatchInd())){
				ltsAcqPersonalInfoForm.setMatchWithBomName(false);
			}else{
				ltsAcqPersonalInfoForm.setMatchWithBomName(true);
			}
			if (contactInfo!=null) {
				ltsAcqPersonalInfoForm.setContactEmail(contactInfo.getEmailAddr());
				ltsAcqPersonalInfoForm.setMobileNo(contactInfo.getContactMobile());
				ltsAcqPersonalInfoForm.setFixedLineNo(contactInfo.getContactFixedLine());
			}
			if (serviceDtl.getThirdPartyDtl()!=null) {
				ltsAcqPersonalInfoForm.setThirdPartyGivenName(serviceDtl.getThirdPartyDtl().getAppntFirstName());
				ltsAcqPersonalInfoForm.setThirdPartyFamilyName(serviceDtl.getThirdPartyDtl().getAppntLastName());
				ltsAcqPersonalInfoForm.setThirdPartyTitle(serviceDtl.getThirdPartyDtl().getTitle());
				ltsAcqPersonalInfoForm.setThirdPartyDocId(serviceDtl.getThirdPartyDtl().getAppntDocId());
				ltsAcqPersonalInfoForm.setThirdPartyDoctype(serviceDtl.getThirdPartyDtl().getAppntDocType());
				ltsAcqPersonalInfoForm.setThirdPartyRelationship(serviceDtl.getThirdPartyDtl().getRelationshipCode());
				ltsAcqPersonalInfoForm.setThirdPartyAppIdVerify(StringUtils.equals("Y", serviceDtl.getThirdPartyDtl().getAppntIdVerifiedInd()));
				ltsAcqPersonalInfoForm.setThirdPartyContactNum(serviceDtl.getThirdPartyDtl().getAppntContactNum());
			}
			
		}
		
		ServiceDetailDTO new2ndDelService = LtsSbOrderHelper.getNew2ndDelService(sbOrder);
		
		if (new2ndDelService != null) {
			
//			ltsAcqPersonalInfoForm.setSecDelThirdPartyApplication(StringUtils.equals("Y", new2ndDelService.getThirdPartyAppln()));
			
			if (StringUtils.isNotEmpty(new2ndDelService.getActualDocId())
					&& StringUtils.isNotEmpty(new2ndDelService.getActualDocType())) {
				ltsAcqPersonalInfoForm.setSecondDelDocNum(new2ndDelService.getActualDocId());
//				ltsAcqPersonalInfoForm.setSecDelDocType(serviceDtl.getActualDocType());				
			}	
		}
		return ltsAcqPersonalInfoForm;
		
	}
	
	private LtsAddressRolloutFormDTO createLtsAddressRolloutForm(SbOrderDTO sbOrder, ServiceDetailDTO serviceDtl, AccountDetailLtsDTO pRAcct) {
		LtsAddressRolloutFormDTO ltsAddressRolloutForm = new LtsAddressRolloutFormDTO();
		
		AddressDetailLtsDTO addressDetailLts = sbOrder.getAddress();
		ltsAddressRolloutForm.setBaCaAction(null);
//		ltsAddressRolloutForm.setExternalRelocate(StringUtils.equals("Y", serviceDtl.getErInd()));
		
//		if (StringUtils.equals("Y", serviceDtl.getErInd())) {
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
			
//			ltsAddressRolloutForm.setBaCaAction(StringUtils.isEmpty(serviceDtl.getCopyErIaToBa())? BaCaActionType.SAME_AS_NEW_IA: BaCaActionType.REMAIN_UNCHANGE);
			
			//if er and change to another billing address
			BillingAddressLtsDTO billAddrLts = pRAcct.getBillingAddress();

			//TBC
			if(billAddrLts != null
						&& "Y".equals(serviceDtl.getErInd())
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
//		}
		return ltsAddressRolloutForm;
	}
	
	private LtsAcqSupportDocFormDTO createLtsAcqSupportDocForm(SbOrderDTO sbOrder) {
		LtsAcqSupportDocFormDTO ltsAcqSupportDocForm = new LtsAcqSupportDocFormDTO();
		ltsAcqSupportDocForm.setCollectMethod(sbOrder.getCollectMethod());
		ltsAcqSupportDocForm.setDistributeEmail(sbOrder.getEsigEmailAddr());
		ltsAcqSupportDocForm.setDistributeSms(sbOrder.getSmsNo());
		ltsAcqSupportDocForm.setSendEmail(StringUtils.isNotBlank(sbOrder.getEsigEmailAddr()));
		ltsAcqSupportDocForm.setSendSms(StringUtils.isNotBlank(sbOrder.getSmsNo()));
		ltsAcqSupportDocForm.setDistributeLang(sbOrder.getEsigEmailLang());
		ltsAcqSupportDocForm.setDistributionMode(sbOrder.getDisMode());
		ltsAcqSupportDocForm.setSupportDocumentList(createSupportDocumentList(sbOrder));
		ltsAcqSupportDocForm.setSignoffMode(sbOrder.getSignoffMode());
		return ltsAcqSupportDocForm;
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
	
	private AddressRolloutDTO createAddressRollout(SbOrderDTO sbOrder) {
		AddressDetailLtsDTO addressDetail = sbOrder.getAddress();
		return addressRolloutService.getAddressRollout(addressDetail.getSerbdyno(), addressDetail.getUnitNo(), addressDetail.getFloorNo());
	}
	
	private ModemTechnologyAissgnDTO createModemTechnologyAssign(SbOrderDTO sbOrder) {
		
		ServiceDetailOtherLtsDTO imsServiceDetail =  (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		
		if (imsServiceDetail == null) {
			return null;
		}
		
		ModemTechnologyAissgnDTO modemTechnologyAissgn = new ModemTechnologyAissgnDTO();
		modemTechnologyAissgn.setBandwidth(imsServiceDetail.getAssgnBandwidth());
		modemTechnologyAissgn.setModemArrangment(imsServiceDetail.getModemArrangement());
		modemTechnologyAissgn.setNewImsService(imsServiceDetail.getNewSrvTypeCd());
		modemTechnologyAissgn.setTechnology(imsServiceDetail.getAssgnTechnology());
		modemTechnologyAissgn.setBbShortage(StringUtils.equals(sbOrder.getAddress()
						.getAddrInventory().getResourceShortageInd(), "Y"));
		return modemTechnologyAissgn;
	}
	
	private ImsSbOrderDTO createRelatedPcdOrder(SbOrderDTO sbOrder, AcqOrderCaptureDTO acqOrderCapture) {
		ServiceDetailOtherLtsDTO imsService = (ServiceDetailOtherLtsDTO)LtsSbOrderHelper.getImsService(sbOrder);
		if (imsService == null || StringUtils.isEmpty(imsService.getRelatedSbOrderId())) {
			return null;
		}
		
		//return ltsRelatedPcdOrderService.retrieveAcqPcdSbOrder(imsService.getRelatedSbOrderId(), acqOrderCapture, false);
		return ltsAcqRelatedPcdOrderService.retrievePcdSbOrder(imsService.getRelatedSbOrderId(), acqOrderCapture, false);
	}
	
	private LtsAcqBasketSelectionFormDTO createBasketSelection(SbOrderDTO sbOrder, BasketDetailDTO basketDetail) {
		LtsAcqBasketSelectionFormDTO form = new LtsAcqBasketSelectionFormDTO();
		form.setSelectedBasketId(basketDetail.getBasketId());
		form.setPcdSbid(getPcdSbidFromSbOrder(sbOrder,"PLAN"));
		form.setDelFreeBundle(getPcdBundleFreeFromSbOrder(sbOrder,"PLAN"));
		return form;
	}
	
	private LtsPremiumSelectionFormDTO createPremiumSelection(SbOrderDTO sbOrder) {
		LtsPremiumSelectionFormDTO form = new LtsPremiumSelectionFormDTO();
		form.setPcdSbid(getPcdSbidFromSbOrder(sbOrder,"PREMIUM"));
		return form;
	}
	
	private boolean getPcdBundleFreeFromSbOrder(SbOrderDTO sbOrder, String itemType) {
		String pcdBundleFree = "";
		
		if(sbOrder != null && sbOrder.getSrvDtls() != null)
		{
			ServiceDetailDTO[] tempSrvDtls = sbOrder.getSrvDtls();
			for (int i=0; i<tempSrvDtls.length; i++)
			{
				if(tempSrvDtls[i] instanceof ServiceDetailLtsDTO)
				{
					ServiceDetailLtsDTO tempSrvLtsDtls = (ServiceDetailLtsDTO) tempSrvDtls[i];					
					if(tempSrvLtsDtls.getItemDtls() != null)
					{
						ItemDetailLtsDTO[] tempItemDetails = tempSrvLtsDtls.getItemDtls();
						for (int j=0; j<tempItemDetails.length; j++)
						{
							if(tempItemDetails[j].getItemType().equalsIgnoreCase(itemType))
							{
								if(tempItemDetails[j].getPcdBundleFree()!=null && !tempItemDetails[j].getPcdBundleFree().equals(""))
								{
									pcdBundleFree = tempItemDetails[j].getPcdBundleFree();
								}
							}							
						}
					}
				}
			}				
		}
		
		return pcdBundleFree!=null&&pcdBundleFree.equalsIgnoreCase("Y")?true:false;		
	}
	
	private String getPcdSbidFromSbOrder(SbOrderDTO sbOrder, String itemType) {
		String pcdSbid = "";
		
		if(sbOrder != null && sbOrder.getSrvDtls() != null)
		{
			ServiceDetailDTO[] tempSrvDtls = sbOrder.getSrvDtls();
			for (int i=0; i<tempSrvDtls.length; i++)
			{
				if(tempSrvDtls[i] instanceof ServiceDetailLtsDTO)
				{
					ServiceDetailLtsDTO tempSrvLtsDtls = (ServiceDetailLtsDTO) tempSrvDtls[i];					
					if(tempSrvLtsDtls.getItemDtls() != null)
					{
						ItemDetailLtsDTO[] tempItemDetails = tempSrvLtsDtls.getItemDtls();
						for (int j=0; j<tempItemDetails.length; j++)
						{
							if(tempItemDetails[j].getItemType().equalsIgnoreCase(itemType))
							{
								if(tempItemDetails[j].getBundlePcdOrderId()!=null && !tempItemDetails[j].getBundlePcdOrderId().equals(""))
								{
									pcdSbid = tempItemDetails[j].getBundlePcdOrderId();
								}
							}							
						}
					}
				}
			}				
		}
		
		return pcdSbid;		
	}
	
	private LtsAcqNumSelectionAndPipbFormDTO createLtsAcqNumSelectionAndPipbForm(SbOrderDTO sbOrder) {
		LtsAcqNumSelectionAndPipbFormDTO ltsAcqNumSelectionAndPipbFormDTO = new LtsAcqNumSelectionAndPipbFormDTO();
		LtsAcqNumberSelectionInfoDTO numberSelectionDTO = new LtsAcqNumberSelectionInfoDTO();
		List<LtsAcqNumberSelectionInfoDTO> dnList = new ArrayList<LtsAcqNumberSelectionInfoDTO>();
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		ServiceDetailLtsDTO pipbService = LtsSbOrderHelper.getAcqPipbService(sbOrder.getSrvDtls());
		ServiceDetailLtsDTO lts2ndDelServiceDetail = LtsSbOrderHelper.get2ndDelServices(sbOrder.getSrvDtls());
		Selection numSelection = pipbService==null? Selection.USE_NEW_DN:
			StringUtils.equals(pipbService.getPipb().getPipbAction(), PipbAction.NEW_DN.toString())?
					Selection.USE_NEW_DN_AND_PIPB_DN:Selection.USE_PIPB_DN;
		ltsAcqNumSelectionAndPipbFormDTO.setNumSelectionRadio(numSelection==Selection.USE_NEW_DN? LtsAcqNumSelectionAndPipbFormDTO.USE_NEW_DN_RADIO_BUTTON:
			numSelection==Selection.USE_NEW_DN_AND_PIPB_DN? LtsAcqNumSelectionAndPipbFormDTO.USE_NEW_DN_AND_PIPB_DN_RADIO_BUTTON
					:LtsAcqNumSelectionAndPipbFormDTO.USE_PIPB_DN_RADIO_BUTTON);
		ltsAcqNumSelectionAndPipbFormDTO.setNumSelection(numSelection);
		ltsAcqNumSelectionAndPipbFormDTO.setIncludeSrvNumOnExDir("Y".equals(ltsServiceDetail.getExDirInd())?true:false);
		ltsAcqNumSelectionAndPipbFormDTO.setDirectoryName(ltsServiceDetail.getExDirName());
		
		if (numSelection==Selection.USE_NEW_DN || numSelection==Selection.USE_NEW_DN_AND_PIPB_DN) {			
			for (int n=0; sbOrder.getResDn()!=null && n<sbOrder.getResDn().length; n++) {
				if(LtsConstant.DN_SOURCE_DN_RESERVED.equals(sbOrder.getResDn()[n].getDnSource())){						
					ltsAcqNumSelectionAndPipbFormDTO.setReservedSrvNum(sbOrder.getResDn()[n].getSrvNum());
					ltsAcqNumSelectionAndPipbFormDTO.setReservedAccountCd(ltsServiceDetail.getNrpAccountCd());
					ltsAcqNumSelectionAndPipbFormDTO.setReservedBoc(ltsServiceDetail.getNrpBoc());
					ltsAcqNumSelectionAndPipbFormDTO.setReservedProjectCd(ltsServiceDetail.getNrpProjectCd());
					ltsAcqNumSelectionAndPipbFormDTO.setDnSource(sbOrder.getResDn()[n].getDnSource());
					ltsAcqNumSelectionAndPipbFormDTO.setSessionId(sbOrder.getResDn()[n].getSessionId());
					ltsAcqNumSelectionAndPipbFormDTO.setDnStatus(ltsServiceDetail.getDnStatus());
					ltsAcqNumSelectionAndPipbFormDTO.setSearchMethodRadio(LtsAcqNumSelectionAndPipbFormDTO.RESERVED_DN_RADIO_BUTTON);
				    break;
				} 
				if (LtsConstant.DN_SOURCE_DN_POOL.equals(sbOrder.getResDn()[n].getDnSource())){
					for(int i=0 ; i<sbOrder.getResDn().length; i++){
						numberSelectionDTO = new LtsAcqNumberSelectionInfoDTO();
						numberSelectionDTO.setSrvNum(sbOrder.getResDn()[i].getSrvNum());
						numberSelectionDTO.setDisplaySrvNum(LtsSbHelper.getDisplaySrvNum(sbOrder.getResDn()[i].getSrvNum()));
						dnList.add(numberSelectionDTO);							
					}
					ltsAcqNumSelectionAndPipbFormDTO.setReservedDnList(dnList);
					ltsAcqNumSelectionAndPipbFormDTO.setDnSource(sbOrder.getResDn()[n].getDnSource());
					ltsAcqNumSelectionAndPipbFormDTO.setSessionId(sbOrder.getResDn()[n].getSessionId());
					ltsAcqNumSelectionAndPipbFormDTO.setDnStatus(ltsServiceDetail.getDnStatus());
					ltsAcqNumSelectionAndPipbFormDTO.setSearchMethodRadio(LtsAcqNumSelectionAndPipbFormDTO.SEARCH_NO_CRITERIA_RADIO_BUTTON);
				    break;
				}
			}
		}		
		if (numSelection==Selection.USE_NEW_DN_AND_PIPB_DN || numSelection==Selection.USE_PIPB_DN) {	
			for (int n=0; sbOrder.getResDn()!=null && n<sbOrder.getResDn().length; n++) {
				if(LtsConstant.DN_SOURCE_DN_PIPB.equals(sbOrder.getResDn()[n].getDnSource())){						
					LtsAcqPipbInfoDTO acqPipb = new LtsAcqPipbInfoDTO();
					acqPipb.setPipbSrvNum(sbOrder.getResDn()[n].getSrvNum());
					acqPipb.setDnSource(sbOrder.getResDn()[n].getDnSource());
					acqPipb.setPipbAccountCd(pipbService.getNrpAccountCd());
					acqPipb.setPipbBoc(pipbService.getNrpBoc());
					acqPipb.setPipbProjectCd(pipbService.getNrpProjectCd());
					acqPipb.setDnStatus(pipbService.getDnStatus());					
					//acqPipb.setPortBack("Y".equals(pipbService.getPipb().getIsPortBack())?true:false);
					acqPipb.setPortBack(LtsSbHelper.isPortBackForPipb(pipbService)?true:false);
					acqPipb.setPipbAction(StringUtils.equals(pipbService.getPipb().getPipbAction(),
							PipbAction.NEW_DN.toString())? PipbAction.NEW_DN:PipbAction.PIPB_DN);
					acqPipb.setPortingFrom(pipbService.getPipb().getOperator2n());
					acqPipb.setReuseExSocket("Y".equals(pipbService.getPipb().getReuseExSocketInd())?true:false);
					if (acqPipb.isReuseExSocket()) {
						acqPipb.setReuseSocketType(pipbService.getPipb().getReuseSocketType());
					}
					acqPipb.setWaiveDnChrg("Y".equals(pipbService.getPipb().getWaiveDnChangeInd())?true:false);
					acqPipb.setPortFromDiffCust("Y".equals(pipbService.getPipb().getFromDiffCustInd())?true:false);
					if (acqPipb.isPortFromDiffCust()) {
						acqPipb.setDocType(pipbService.getPipb().getIdDocType());
						acqPipb.setDocNum(pipbService.getPipb().getIdDocNum());
						if (LtsConstant.DOC_TYPE_HKBR.equals(acqPipb.getDocType())) {
							acqPipb.setCompanyName(pipbService.getPipb().getCompanyName());
						} else {
							acqPipb.setTitle(pipbService.getPipb().getTitle());
							acqPipb.setGivenName(pipbService.getPipb().getFirstName());
							acqPipb.setFamilyName(pipbService.getPipb().getLastName());
						}
					}
					acqPipb.setPortFromDiffAddr("Y".equals(pipbService.getPipb().getFromDiffAddrInd())?true:false);					
					acqPipb.getAddress().setFlat(pipbService.getPipb().getUnitNo());
					acqPipb.getAddress().setFloor(pipbService.getPipb().getFloorNo());
					acqPipb.getAddress().setBlock(pipbService.getPipb().getBlockNo());
					acqPipb.getAddress().setLotNum(pipbService.getPipb().getHiLotNo());
					acqPipb.getAddress().setBuildingName(pipbService.getPipb().getBuildNo());
					acqPipb.getAddress().setStreetNum(pipbService.getPipb().getStrNo());
					acqPipb.getAddress().setStreetName(pipbService.getPipb().getStrName());
					acqPipb.getAddress().setStreetCatgCode(pipbService.getPipb().getStrCatCd());
					acqPipb.getAddress().setStreetCatgDesc(pipbService.getPipb().getStrCatDesc());
					acqPipb.getAddress().setSectionCode(pipbService.getPipb().getSectCd());
					acqPipb.getAddress().setSectionDesc(pipbService.getPipb().getSectDesc());
					acqPipb.getAddress().setDistrictCode(pipbService.getPipb().getDistNo());
					acqPipb.getAddress().setDistrictDesc(pipbService.getPipb().getDistDesc());
					acqPipb.getAddress().setAreaCode(pipbService.getPipb().getAreaCd());
					acqPipb.getAddress().setAreaDesc(pipbService.getPipb().getAreaDesc());
					acqPipb.getAddress().setServiceBoundary(pipbService.getPipb().getSerbdyno());
					acqPipb.setDuplexInd("Y".equals(pipbService.getPipb().getDuplexInd())?true:false);
					if (acqPipb.isDuplexInd()) {
						acqPipb.setDuplexAction(StringUtils.equals(pipbService.getPipb().getDuplexAction(),DuplexAction.DISCONNECT.toString())?DuplexAction.DISCONNECT
								:StringUtils.equals(pipbService.getPipb().getDuplexAction(),DuplexAction.RETAIN.toString())?DuplexAction.RETAIN:
									DuplexAction.PORT_IN_TOGETHER);
						acqPipb.setDuplexRadio(StringUtils.equals(pipbService.getPipb().getDuplexAction(),DuplexAction.DISCONNECT.toString())?LtsAcqNumSelectionAndPipbFormDTO.DISCONNECT_RADIO_BUTTON
								:StringUtils.equals(pipbService.getPipb().getDuplexAction(),DuplexAction.RETAIN.toString())?LtsAcqNumSelectionAndPipbFormDTO.RETAIN_RADIO_BUTTON:
									LtsAcqNumSelectionAndPipbFormDTO.PORT_IN_TOGETHER_RADIO_BUTTON);
						acqPipb.setDuplexDn(pipbService.getPipb().getDuplexDn());
						if (StringUtils.equals(pipbService.getPipb().getDuplexAction(),DuplexAction.PORT_IN_TOGETHER.toString())
								&& lts2ndDelServiceDetail!=null) {
							acqPipb.setDuplexDnStatus(lts2ndDelServiceDetail.getDnStatus());
						}
					}
					ltsAcqNumSelectionAndPipbFormDTO.setPipbInfo(acqPipb);
					ltsAcqNumSelectionAndPipbFormDTO.setSessionId(sbOrder.getResDn()[n].getSessionId());
				    break;
				} 
			}	
		}
		
		return ltsAcqNumSelectionAndPipbFormDTO;
	}
	
	private LtsAcqNumConfirmationFormDTO createLtsAcqConfirmationForm(SbOrderDTO sbOrder) {
		LtsAcqNumConfirmationFormDTO ltsAcqNumConfirmationFormDTO = new LtsAcqNumConfirmationFormDTO();
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		if (ltsServiceDetail!=null) {
			if (ltsServiceDetail.getSrvNum()!=null) {
				if (LtsConstant.DN_SOURCE_DN_POOL.equals(ltsServiceDetail.getDnSource())
						|| LtsConstant.DN_SOURCE_DN_RESERVED.equals(ltsServiceDetail.getDnSource())) {
					ltsAcqNumConfirmationFormDTO.setNewDn(ltsServiceDetail.getSrvNum());
				}
				if (LtsConstant.DN_SOURCE_DN_PIPB.equals(ltsServiceDetail.getDnSource())) {
					ltsAcqNumConfirmationFormDTO.setPipbDn(ltsServiceDetail.getSrvNum());
				}
			}
		}
		ServiceDetailLtsDTO portLaterservice = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
		if (portLaterservice!=null) {
			if (portLaterservice.getSrvNum()!=null) {
				ltsAcqNumConfirmationFormDTO.setPipbDn(portLaterservice.getSrvNum());
			}
		}
		return ltsAcqNumConfirmationFormDTO;
	}

	/**
	 * @return the bomWsCreateCustClient
	 */
	public BomCustProfileWsClient getBomWsCreateCustClient() {
		return bomWsCreateCustClient;
	}

	/**
	 * @param bomWsCreateCustClient the bomWsCreateCustClient to set
	 */
	public void setBomWsCreateCustClient(
			BomCustProfileWsClient bomWsCreateCustClient) {
		this.bomWsCreateCustClient = bomWsCreateCustClient;
	}

	/**
	 * @return the customerProfileLtsService
	 */
	public CustomerProfileLtsService getCustomerProfileLtsService() {
		return customerProfileLtsService;
	}

	/**
	 * @param customerProfileLtsService the customerProfileLtsService to set
	 */
	public void setCustomerProfileLtsService(
			CustomerProfileLtsService customerProfileLtsService) {
		this.customerProfileLtsService = customerProfileLtsService;
	}

	/**
	 * @return the ltsCommonService
	 */
	public LtsCommonService getLtsCommonService() {
		return ltsCommonService;
	}

	/**
	 * @param ltsCommonService the ltsCommonService to set
	 */
	public void setLtsCommonService(LtsCommonService ltsCommonService) {
		this.ltsCommonService = ltsCommonService;
	}

	/**
	 * @return the bomWsCreateAcctClient
	 */
	public BomCreateAccountWsClient getBomWsCreateAcctClient() {
		return bomWsCreateAcctClient;
	}

	/**
	 * @param bomWsCreateAcctClient the bomWsCreateAcctClient to set
	 */
	public void setBomWsCreateAcctClient(
			BomCreateAccountWsClient bomWsCreateAcctClient) {
		this.bomWsCreateAcctClient = bomWsCreateAcctClient;
	}

	/**
	 * @return the ltsAcqCustomerDetailService
	 */
	public LtsAcqCustomerDetailService getLtsAcqCustomerDetailService() {
		return ltsAcqCustomerDetailService;
	}

	/**
	 * @param ltsAcqCustomerDetailService the ltsAcqCustomerDetailService to set
	 */
	public void setLtsAcqCustomerDetailService(
			LtsAcqCustomerDetailService ltsAcqCustomerDetailService) {
		this.ltsAcqCustomerDetailService = ltsAcqCustomerDetailService;
	}

	/**
	 * @return the ltsAcqAccountDetailService
	 */
	public LtsAcqAccountDetailService getLtsAcqAccountDetailService() {
		return ltsAcqAccountDetailService;
	}

	/**
	 * @param ltsAcqAccountDetailService the ltsAcqAccountDetailService to set
	 */
	public void setLtsAcqAccountDetailService(
			LtsAcqAccountDetailService ltsAcqAccountDetailService) {
		this.ltsAcqAccountDetailService = ltsAcqAccountDetailService;
	}

	public LtsAppointmentService getLtsAppointmentService() {
		return ltsAppointmentService;
	}

	public void setLtsAppointmentService(LtsAppointmentService ltsAppointmentService) {
		this.ltsAppointmentService = ltsAppointmentService;
	}
	
	
}
