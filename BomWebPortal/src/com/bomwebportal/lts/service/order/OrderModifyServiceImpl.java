package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.order.OrderModifyDAO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO.BaCaActionType;
import com.bomwebportal.lts.dto.form.LtsAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.form.LtsRecontractFormDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderAttbDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.order.OrderCreateServiceImpl.CreateServiceType;
import com.bomwebportal.lts.util.LtsCommonHelper;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsConstant.UpgradeOrderAttb;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.google.common.collect.Lists;

public class OrderModifyServiceImpl extends OrderCreateServiceImpl implements OrderModifyService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected LtsOrderDocumentService ltsOrderDocumentService;
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	protected OrderModifyDAO orderModifyDao;
	
	public OrderModifyDAO getOrderModifyDao() {
		return orderModifyDao;
	}

	public void setOrderModifyDao(OrderModifyDAO orderModifyDao) {
		this.orderModifyDao = orderModifyDao;
	}

	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}
	
	public SbOrderDTO modifySbOrder(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		modifySupportDoc(orderCapture, sbOrder);
		modifyAccountPayment(orderCapture, sbOrder);
		modifyItems(orderCapture, sbOrder);
		modifyAppointment(orderCapture, sbOrder);
		modifyRemark(orderCapture, sbOrder);
		modifyPenaltyForRenewalEffectiveDate(orderCapture);
		modifyOrderAttbs(orderCapture, sbOrder);
		
		if (orderCapture.getLtsSupportDocForm() != null) {
			sbOrder.setDisMode(orderCapture.getLtsSupportDocForm().getDistributionMode());
			if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE, 
					orderCapture.getLtsSupportDocForm().getDistributionMode())) {
				if (orderCapture.getLtsSupportDocForm().isSendSms()) {
					sbOrder.setSmsNo(orderCapture.getLtsSupportDocForm().getDistributeSms());
				}
				if (orderCapture.getLtsSupportDocForm().isSendEmail()) {
					sbOrder.setEsigEmailAddr(orderCapture.getLtsSupportDocForm().getDistributeEmail());
				}
			}
			sbOrder.setEsigEmailLang(orderCapture.getLtsSupportDocForm().getDistributeLang());	
			sbOrder.setCollectMethod(orderCapture.getLtsSupportDocForm().getCollectMethod());
			sbOrder.setSignoffMode(orderCapture.getLtsSupportDocForm().getSignoffMode());
			sbOrder.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			modifyAllOrdDocAssgnLts(orderCapture, sbOrder, bomSalesUser);	
		}
		return sbOrder;
	}
	
	private void modifySupportDoc(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {
		if (isCreditCardChanged(sbOrder, orderCapture)) {
			uncollectSupportDoc(sbOrder, LtsConstant.ORDER_DOC_TYPE_CREDIT_CARD_COPY);
			ltsOrderDocumentService.updateOutdatedInd(sbOrder.getOrderId(), LtsConstant.ORDER_DOC_TYPE_CREDIT_CARD_COPY, true);
		}
	}
	
	private void modifyRemark(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {
		if (orderCapture.getLtsAppointmentForm() == null) {
			return;
		}
		
		ServiceDetailDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);;
		
		if (ltsServiceDetail.getRemarks() != null && ArrayUtils.isNotEmpty(ltsServiceDetail.getRemarks())) {
			for (RemarkDetailLtsDTO remarkDetailLts : ltsServiceDetail.getRemarks()) {
				if (!LtsConstant.REMARK_TYPE_ADD_ON_REMARK.equals(remarkDetailLts.getRmkType())) {
						continue;
				}
				remarkDetailLts.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			}
		}
		
		List<RemarkDetailLtsDTO> addonRemarkList = createAddonRemarkDetailLts(orderCapture);
		if (addonRemarkList != null && !addonRemarkList.isEmpty()) {
			for (RemarkDetailLtsDTO addonRemark : addonRemarkList) {
				ArrayUtils.add(ltsServiceDetail.getRemarks(), addonRemark);	
			}
		}
	}
	
	private void modifyAdjustAmount(ServiceDetailDTO serviceDetail, OrderCaptureDTO orderCapture) {
		
		LtsAppointmentFormDTO ltsAppointmentForm = orderCapture.getLtsAppointmentForm();
		if (ltsAppointmentForm != null) {
			setAdjustmentDtl(orderCapture, (ServiceDetailLtsDTO)serviceDetail);
			serviceDetail.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		}
	}
	
	
	private void modifyServiceSuggestedSrd(ServiceDetailDTO serviceDetail, OrderCaptureDTO orderCapture) {
		
		LtsAppointmentFormDTO ltsAppointmentForm = orderCapture.getLtsAppointmentForm();
		
		if (ltsAppointmentForm != null) {
			serviceDetail.setSuggestSrd(ltsAppointmentForm.getEarliestSRD().getDateString());
//			serviceDetail.setSuggestSrdReasonCd(ltsAppointmentForm.getEarliestSrdReason());
			serviceDetail.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		}	
		
	}
	
	private void modifyAppointment(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {
		if (orderCapture.getLtsAppointmentForm() == null
				|| orderCapture.getLtsAppointmentForm().getEarliestSRD() == null) {
			return;
		}
		CreateServiceType createServiceType = null;
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			
			if (LtsSbOrderHelper.is2ndDelService(serviceDetail)) {
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_NEW_2DEL;
				serviceDetail.setForceFieldVisitInd(orderCapture.getLtsAppointmentForm().isSecDelFieldVisitRequired() ? "Y" : null);
			}
			if (LtsSbOrderHelper.isUpgradeService(serviceDetail)) {
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_UPGRADE;
				serviceDetail.setForceFieldVisitInd("Y");
			}
			if (LtsSbOrderHelper.isRenewalService(serviceDetail)) {
				serviceDetail.setForceFieldVisitInd(orderCapture.getLtsAppointmentForm().isFieldVisitRequired() ? "Y" : null);
				if (StringUtils.isEmpty(serviceDetail.getGrpType())) {
					createServiceType = CreateServiceType.CREATE_SRV_TYPE_RENEW_DEL;	
				}
				else {
					createServiceType = CreateServiceType.CREATE_SRV_TYPE_RENEW_EYE;
				}
			}
			
			if (LtsSbOrderHelper.isImsService(serviceDetail)) {
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_FSA;
				if (LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())) {
					serviceDetail.setForceFieldVisitInd(orderCapture.getLtsAppointmentForm().isFieldVisitRequired() ? "Y" : null);
				}
			}
			
			if(isSipRemoveService(serviceDetail)){
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_SIP_REMOVE;
			}
			
			if(createServiceType != null){
				AppointmentDetailLtsDTO newAppointmentDetailLts = createAppointmentDetailLts(createServiceType, orderCapture);
				
				if (LtsSbOrderHelper.isUpgradeService(serviceDetail)) {
					modifyServiceSuggestedSrd(serviceDetail, orderCapture);
				}
				
				
				if (newAppointmentDetailLts == null) {
					continue;
				}
				
				if (serviceDetail.getAppointmentDtl() != null) {
					newAppointmentDetailLts.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
				}
				serviceDetail.setAppointmentDtl(newAppointmentDetailLts);
			}
		}
		
		if (LtsConstant.ORDER_TYPE_SB_RETENTION.equals(sbOrder.getOrderType())) {
			modifyAdjustAmount(LtsSbOrderHelper.getLtsService(sbOrder), orderCapture);
		}
		
	}
	
	private void modifyItems(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {

		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		List<ItemDetailLtsDTO> upgradeItemDetailList = new ArrayList<ItemDetailLtsDTO>();
		String basketId = null;
		BasketDetailDTO selectedBasket = null;
		
		if (orderCapture.getLtsPaymentForm() != null) {
	
			for (ItemDetailLtsDTO selectedItem : ltsService.getItemDtls()) {
				if (StringUtils.equals(LtsConstant.ITEM_TYPE_PREPAY, selectedItem.getItemType()) 
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL, selectedItem.getItemType())
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE, selectedItem.getItemType())
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE, selectedItem.getItemType())
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL_BR, selectedItem.getItemType())
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE, selectedItem.getItemType())
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_EBILL, selectedItem.getItemType())
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL, selectedItem.getItemType())
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL, selectedItem.getItemType())
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_ER_CHARGE, selectedItem.getItemType())
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_EMAIL_BILL, selectedItem.getItemType())) {
					selectedItem.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
				}
				if (StringUtils.isNotEmpty(selectedItem.getBasketId())) {
					basketId = selectedItem.getBasketId();
				}
				upgradeItemDetailList.add(selectedItem);
			}
			if (StringUtils.isNotEmpty(basketId)) {
				selectedBasket = ltsBasketService.getBasket(basketId, LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
				addItemDetail(selectedBasket, orderCapture.getLtsPaymentForm().getBillItemList(), upgradeItemDetailList);
				if (orderCapture.getLtsPaymentForm().getPrePayItem() != null) {
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getPrePayItem()), upgradeItemDetailList);
				}
				if(orderCapture.getLtsPaymentForm().getViewBillItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getViewBillItem()), upgradeItemDetailList);
				}
				if(orderCapture.getLtsPaymentForm().getCsPortalItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getCsPortalItem()), upgradeItemDetailList);
				}
				addItemDetail(selectedBasket, orderCapture.getLtsPaymentForm().getErChargeItemList(), upgradeItemDetailList);
			}
			
			
			ServiceDetailLtsDTO secondDelService = LtsSbOrderHelper.get2ndDelService(sbOrder);
			
			if (secondDelService != null
					&& StringUtils.equals(ltsService.getAccount().getAcctNo(), secondDelService.getAccount().getAcctNo())) {
				
				List<ItemDetailLtsDTO> secondDelItemDetailList = new ArrayList<ItemDetailLtsDTO>();
				
				for (ItemDetailLtsDTO selectedItem : secondDelService.getItemDtls()) {
					if (StringUtils.equals(LtsConstant.ITEM_TYPE_PREPAY, selectedItem.getItemType()) 
							|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL, selectedItem.getItemType())
							|| StringUtils.equals(LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE, selectedItem.getItemType())
							|| StringUtils.equals(LtsConstant.ITEM_TYPE_EBILL, selectedItem.getItemType())
							|| StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL, selectedItem.getItemType())
							|| StringUtils.equals(LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL, selectedItem.getItemType())
							|| StringUtils.equals(LtsConstant.ITEM_TYPE_EMAIL_BILL, selectedItem.getItemType())) {
						selectedItem.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
					}
					if (StringUtils.isEmpty(basketId) && StringUtils.isNotEmpty(selectedItem.getBasketId())) {
						basketId = selectedItem.getBasketId();
					}
					secondDelItemDetailList.add(selectedItem);
				}

				addItemDetail(selectedBasket, orderCapture.getLtsPaymentForm().getBillItemList(), secondDelItemDetailList);
				if(orderCapture.getLtsPaymentForm().getViewBillItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getViewBillItem()), secondDelItemDetailList);
				}
				if(orderCapture.getLtsPaymentForm().getCsPortalItem() != null){
					addItemDetail(selectedBasket, Lists.newArrayList(orderCapture.getLtsPaymentForm().getCsPortalItem()), secondDelItemDetailList);
				}
				
				secondDelService.setItemDtls(secondDelItemDetailList.toArray(new ItemDetailLtsDTO[secondDelItemDetailList.size()]));
			}
			
		
		}
		
		//BOM2018061
		if(orderCapture.getLtsAppointmentForm() != null){
			for (ItemDetailLtsDTO selectedItem : ltsService.getItemDtls()) {
				if (StringUtils.equals(LtsConstant.ITEM_TYPE_EPD_ACCEPT, selectedItem.getItemType()) 
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_EPD_FORFEIT, selectedItem.getItemType())
						|| StringUtils.equals(LtsConstant.ITEM_TYPE_EPD_UNDER_CONSIDERATION, selectedItem.getItemType())) {
					selectedItem.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
				}

				if (StringUtils.isEmpty(basketId) && StringUtils.isNotEmpty(selectedItem.getBasketId())) {
					basketId = selectedItem.getBasketId();
				}
				
				upgradeItemDetailList.add(selectedItem);
			}
			
			if (selectedBasket == null && StringUtils.isNotEmpty(basketId)) {
				selectedBasket = ltsBasketService.getBasket(basketId, LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
			}
			
			addItemDetail(selectedBasket, orderCapture.getLtsAppointmentForm().getEpdItemList(), upgradeItemDetailList);
			
		}
		
		ltsService.setItemDtls(upgradeItemDetailList.toArray(new ItemDetailLtsDTO[upgradeItemDetailList.size()]));
		
		
		if (ltsService.getRecontract() == null) {
			ltsService.getAccount().setBillMedia(getBillMedia(orderCapture));
	    	if(LtsConstant.PAPER_BILL_WAIVE_REASON_OTHER.equals(orderCapture.getLtsPaymentForm().getPaperWaiveReason())){
	    		ltsService.getAccount().setWaivePaperRemarks(orderCapture.getLtsPaymentForm().getPaperBillWaiveOtherStaffId());	
	    	}
			ltsService.getAccount().setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			setRedemptionMediaDetail(orderCapture, ltsService.getAccount());
			for (ItemDetailLtsDTO selectedItem : ltsService.getItemDtls()) {
				if (StringUtils.equals(LtsConstant.ITEM_TYPE_EMAIL_BILL, selectedItem.getItemType())
						&& selectedItem.getObjectAction() == ObjectActionBaseDTO.ACTION_ADD) {
					ltsService.getAccount().setRedemptionEmailAddr(orderCapture.getLtsPaymentForm().getEmailBillAddress());
					break;
				}
				else {
					ltsService.getAccount().setRedemptionEmailAddr(null);
				}
			}	
		}
		
	}
	
	
	protected AccountDetailLtsDTO createRecontractAccountDetailLts(OrderCaptureDTO orderCapture, ServiceDetailDTO serviceDetail, 
			Map<String, AccountDetailLtsDTO> accountDetailLtsMap, CustomerDetailLtsDTO customerDetailLts) {
		
		LtsRecontractFormDTO recontractForm = orderCapture.getLtsRecontractForm();
		AccountDetailProfileLtsDTO profileAcct = null;
		String acctNum = getRecontractAccountNum(orderCapture);
		
		if (recontractForm != null && recontractForm.getAccountDetailProfile() != null) {
			profileAcct = recontractForm.getAccountDetailProfile();
		}
		
		if (accountDetailLtsMap.containsKey(acctNum)) {
			AccountDetailLtsDTO account = accountDetailLtsMap.get(acctNum);
			return account;
		}
		
		AccountDetailLtsDTO recontractAcct = new AccountDetailLtsDTO();
		accountDetailLtsMap.put(acctNum, recontractAcct);
		recontractAcct.setAcctNo(acctNum);
		recontractAcct.setCustomer(customerDetailLts);
		recontractAcct.setBillingAddress(createBillingAddressLtsDTO(orderCapture, CreateServiceType.CREATE_SRV_TYPE_UPGRADE));
		
		if (LtsConstant.DUMMY_ACCT_NO.equals(acctNum)) {
			return recontractAcct;
		}
		
		recontractAcct.setAcctName(profileAcct.getAcctName());
		recontractAcct.setAutopayStatementInd(profileAcct.isAutopayStatementInd() ? "Y" : null);
		recontractAcct.setBillFmt(profileAcct.getBillFmt());
		recontractAcct.setBillFreq(profileAcct.getBillFreq());
		recontractAcct.setBillLang(profileAcct.getBillLang());
		recontractAcct.setBillMedia(getBillMedia(orderCapture));
		recontractAcct.setExistBillMedia(profileAcct.getBillMedia());
		recontractAcct.setPaymethods(createRecontractPaymentMethodDetailLts(orderCapture, profileAcct));
		
		if(orderCapture.getLtsPaymentForm() != null){
			recontractAcct.setAcctWaivePaperInd(StringUtils.equals(orderCapture.getLtsPaymentForm().getPaperBillCharge(), "NONE")? null : orderCapture.getLtsPaymentForm().getPaperBillCharge());
			recontractAcct.setWaivePaperReaCd(orderCapture.getLtsPaymentForm().getPaperWaiveReason());
	    	if(LtsConstant.PAPER_BILL_WAIVE_REASON_OTHER.equals(orderCapture.getLtsPaymentForm().getPaperWaiveReason())){
	    		recontractAcct.setWaivePaperRemarks(orderCapture.getLtsPaymentForm().getPaperBillWaiveOtherStaffId());	
	    	}
		}
		
		setRedemptionMediaDetail(orderCapture, recontractAcct);
		return recontractAcct;
	}
	
	
	private void modifyAccountPayment(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder) {
		
		List<PaymentMethodDetailLtsDTO> paymentMethodDetailList = null;
		List<AccountServiceAssignLtsDTO> accountSrvAssgnList = null;
		Map<String, AccountDetailLtsDTO> newAccountDetailLtsMap = new HashMap<String, AccountDetailLtsDTO> ();
		AccountDetailProfileLtsDTO profilePrimaryAccount = LtsSbOrderHelper.getPrimaryAccount(orderCapture.getLtsServiceProfile());
		
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			
			if (LtsSbOrderHelper.isImsService(serviceDetail)) {
				continue;
			}
			
			accountSrvAssgnList = new ArrayList<AccountServiceAssignLtsDTO>();
			
			if (ArrayUtils.isNotEmpty(serviceDetail.getAccounts())) {
				for (AccountServiceAssignLtsDTO acctSrvAssgn : serviceDetail.getAccounts()) {
					
					accountSrvAssgnList.add(acctSrvAssgn);
					
					if (LtsConstant.IO_IND_INSTALL.equals(acctSrvAssgn.getAction())) {
						if (orderCapture.getLtsRecontractForm() != null) {
							acctSrvAssgn.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
							acctSrvAssgn.getAccount().setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
							
							AccountServiceAssignLtsDTO recontractSrvAccountAssign = new AccountServiceAssignLtsDTO();
							recontractSrvAccountAssign.setAccount(createRecontractAccountDetailLts(orderCapture, serviceDetail, newAccountDetailLtsMap, acctSrvAssgn.getAccount().getCustomer()));
							recontractSrvAccountAssign.setAction(LtsConstant.IO_IND_INSTALL);
							recontractSrvAccountAssign.setChrgType(LtsConstant.ACCOUNT_CHARGE_TYPE_R);
							accountSrvAssgnList.add(recontractSrvAccountAssign);
							
							if (serviceDetail.getRecontract() != null) {
								serviceDetail.getRecontract().setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
								serviceDetail.getRecontract().setAcctNum(recontractSrvAccountAssign.getAccount().getAcctNo());
							}
						}
					}
					
					if (LtsConstant.IO_IND_SPACE.equals(acctSrvAssgn.getAction())) {
						PaymentMethodDetailLtsDTO[] payments = acctSrvAssgn.getAccount().getPaymethods();
						if (ArrayUtils.isNotEmpty(payments)) {
							paymentMethodDetailList = new ArrayList<PaymentMethodDetailLtsDTO>();
							for (PaymentMethodDetailLtsDTO paymentMethodDetailLts : payments) {
								paymentMethodDetailLts.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
								paymentMethodDetailList.add(paymentMethodDetailLts);
							}		
						}
						if (LtsSbOrderHelper.isUpgradeService(serviceDetail) 
								|| LtsSbOrderHelper.isRenewalService(serviceDetail)
								|| LtsSbOrderHelper.isDuplexService(serviceDetail)) {
							modifyBillingAddress(orderCapture, serviceDetail);	
						}
						else if (LtsSbOrderHelper.is2ndDelService(serviceDetail) && orderCapture.getSecondDelServiceProfile() != null) {
							profilePrimaryAccount = LtsSbOrderHelper.getPrimaryAccount(orderCapture.getSecondDelServiceProfile());
						}
						PaymentMethodDetailLtsDTO[] paymentMethodDetails =  createPaymentMethodDetailLts(orderCapture, profilePrimaryAccount);
						for (int i=0; paymentMethodDetails != null && i < paymentMethodDetails.length; i++) {
							paymentMethodDetailList.add(paymentMethodDetails[i]);
						}
						acctSrvAssgn.getAccount().setPaymethods(paymentMethodDetailList.toArray(new PaymentMethodDetailLtsDTO[paymentMethodDetailList.size()]));
					}
					
				}
			}
			serviceDetail.setAccounts(accountSrvAssgnList.toArray(new AccountServiceAssignLtsDTO[accountSrvAssgnList.size()]));
			if (LtsSbOrderHelper.isUpgradeService(serviceDetail) 
					|| LtsSbOrderHelper.isRenewalService(serviceDetail)
					|| LtsSbOrderHelper.isDuplexService(serviceDetail)) {
				modifyCsPortalDetail(orderCapture, serviceDetail);
			}
			
			
		}
		sbOrder.setAccounts((AccountDetailLtsDTO[])ArrayUtils.addAll(sbOrder.getAccounts(), newAccountDetailLtsMap.values().toArray(new AccountDetailLtsDTO[newAccountDetailLtsMap.size()])));
	}
	
	private void modifyBillingAddress(OrderCaptureDTO orderCapture, ServiceDetailDTO serviceDetail) {
		/*Billing Address update*/
		LtsPaymentFormDTO paymentForm = orderCapture.getLtsPaymentForm();
		LtsAddressRolloutFormDTO addressRolloutForm  = orderCapture.getLtsAddressRolloutForm();
		
		if(!addressRolloutForm.isExternalRelocate() && paymentForm != null && paymentForm.isAllowChangeBa() && paymentForm.isChangeBa()){
			String fullAddr = null;
			BillingAddressLtsDTO billingAddressLts = serviceDetail.getAccount().getBillingAddress();//new BillingAddressLtsDTO();
			if("Y".equals(serviceDetail.getRecontractInd())){
				billingAddressLts = serviceDetail.getRecontract().getBillingAddress();
			}
			
			if(billingAddressLts != null){
				billingAddressLts.setAction(BaCaActionType.DIFFERENT_FROM_NEW_OLD_IA.getCode());
				fullAddr = paymentForm.getBillingAddress();
				billingAddressLts.setInstantUpdateInd(paymentForm.isInstantUpdateBa()? "Y" : "N");
				
				if(StringUtils.isNotBlank(fullAddr)){
					String[] addrLines = fullAddr.split("\n");
					billingAddressLts.setAddrLine1(addrLines[0]);
					billingAddressLts.setAddrLine2(addrLines.length > 1? addrLines[1] :null);
					billingAddressLts.setAddrLine3(addrLines.length > 2? addrLines[2] :null);
					billingAddressLts.setAddrLine4(addrLines.length > 3? addrLines[3] :null);
					billingAddressLts.setAddrLine5(addrLines.length > 4? addrLines[4] :null);
					billingAddressLts.setAddrLine6(addrLines.length > 5? addrLines[5] :null);
				}
				billingAddressLts.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			}
		}
	}
	
	private void modifyCsPortalDetail(OrderCaptureDTO orderCapture, ServiceDetailDTO serviceDetail) {
		
		/*MyHKT (CsPortal) update*/
		CustomerDetailLtsDTO custDtlLts = serviceDetail.getAccount().getCustomer();
		
		if (custDtlLts == null || orderCapture.getLtsPaymentForm() == null) {
			return;
		}
		
		if(orderCapture.getLtsPaymentForm().getCsPortalItem() != null){

			String itemType = orderCapture.getLtsPaymentForm().getCsPortalItem().getItemType();
			
			if((StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL,itemType )
						||StringUtils.equals(LtsConstant.ITEM_TYPE_THE_CLUB_BILL,itemType )
						||StringUtils.equals(LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL,itemType ))
				&& orderCapture.getLtsPaymentForm().getCsPortalItem().isSelected()){

				setHktClubItemAttb(custDtlLts, orderCapture.getLtsPaymentForm());
			}
		}else if(orderCapture.getLtsPaymentForm().getCsPortalItem() == null
				&& orderCapture.getLtsPaymentForm().getViewBillItem() != null
				&& StringUtils.equals(LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL, orderCapture.getLtsPaymentForm().getViewBillItem().getItemType())
				&& orderCapture.getLtsPaymentForm().getViewBillItem().isSelected()){
			
			setHktClubItemAttb(custDtlLts, orderCapture.getLtsPaymentForm());
		}else{
			//custDtlLts.setCsPortalInd("N");
			custDtlLts.setCsPortalLogin(null);
			custDtlLts.setCsPortalMobile(null);
			custDtlLts.setTheClubLogin(null);
			custDtlLts.setTheClubMobile(null);
			custDtlLts.setClubOptOut(null);
			custDtlLts.setClubOptRea(null);
			custDtlLts.setClubOptRmk(null);
			custDtlLts.setHktOptOut(null);
			
		}
		
		this.setHktClubInd(orderCapture.getLtsPaymentForm(), custDtlLts);
		
		custDtlLts.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		

	}
	
	private void modifyAllOrdDocAssgnLts(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		
		AllOrdDocAssgnLtsDTO[] existingAllOrdDocAssgns = orderRetrieveLtsService.retrieveAllOrdDocAssgn(sbOrder.getOrderId()); 
		AllOrdDocAssgnLtsDTO[] newAllOrdDocAssgns = createAllOrdDocAssgnLts(orderCapture, bomSalesUser);
		List<AllOrdDocAssgnLtsDTO> allOrdDocAssgnList = new ArrayList<AllOrdDocAssgnLtsDTO>();
		
		if (ArrayUtils.isNotEmpty(existingAllOrdDocAssgns)) {
			for (AllOrdDocAssgnLtsDTO allOrdDocAssgnLts : existingAllOrdDocAssgns) {
				allOrdDocAssgnLts.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			}
			allOrdDocAssgnList.addAll(Arrays.asList(existingAllOrdDocAssgns));
		}
		if (ArrayUtils.isNotEmpty(newAllOrdDocAssgns)) {
			// Set Collected Ind
			if (ArrayUtils.isNotEmpty(existingAllOrdDocAssgns)) {
				for (AllOrdDocAssgnLtsDTO newAllOrdDocAssgn : newAllOrdDocAssgns) {
					for (AllOrdDocAssgnLtsDTO existingAllOrdDocAssgn : existingAllOrdDocAssgns) {
						if (StringUtils.equals(existingAllOrdDocAssgn.getDocType(), newAllOrdDocAssgn.getDocType())) {
							newAllOrdDocAssgn.setCollectedInd(existingAllOrdDocAssgn.getCollectedInd());	
						}
					}
				}	
			}
			allOrdDocAssgnList.addAll(Arrays.asList(newAllOrdDocAssgns));
		}
		
		sbOrder.setAllOrdDocAssgns(allOrdDocAssgnList.toArray(new AllOrdDocAssgnLtsDTO[allOrdDocAssgnList.size()]));
	}
	
	private boolean isCreditCardChanged(SbOrderDTO sbOrder, OrderCaptureDTO orderCapture) {
		
		ServiceDetailDTO ltsServiceDetail =  LtsSbOrderHelper.getLtsService(sbOrder);;
		
		PaymentMethodDetailLtsDTO[] paymentMethods = ltsServiceDetail.getAccount().getPaymethods();
		LtsPaymentFormDTO ltsPaymentForm = orderCapture.getLtsPaymentForm();
		
		if (ArrayUtils.isEmpty(paymentMethods) || ltsPaymentForm == null) {
			return false;
		}
		
		for (PaymentMethodDetailLtsDTO paymentMethod : paymentMethods) {
			if (StringUtils.equals(LtsConstant.PAYMENT_TYPE_CREDIT_CARD, paymentMethod.getPayMtdType())
					&& StringUtils.equals(LtsConstant.IO_IND_INSTALL, paymentMethod.getAction())) {
				if (StringUtils.equals(ltsPaymentForm.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)
						&& StringUtils.isNotEmpty(paymentMethod.getCcNum())
						&& !StringUtils.equals(ltsPaymentForm.getCardNum(), paymentMethod.getCcNum())) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	private void uncollectSupportDoc(SbOrderDTO sbOrder, String docType) {
		
		AllOrdDocAssgnLtsDTO[] allOrdDocAssgns = sbOrder.getAllOrdDocAssgns();
		
		if (ArrayUtils.isNotEmpty(allOrdDocAssgns)) {
			for (AllOrdDocAssgnLtsDTO allOrdDocAssgn : allOrdDocAssgns) {
				if (StringUtils.equals(allOrdDocAssgn.getDocType(), docType)) {
					allOrdDocAssgn.setCollectedInd("N");
					allOrdDocAssgn.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
				}
			}
		}
	}
	
	public int updateEdfRef(String orderId, String dtlId, String edfRef, String userId)  {
		try {
			return orderModifyDao.updateEdfRef(orderId, dtlId, edfRef, userId);
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e);
		}
	}
	
	private void modifyPenaltyForRenewalEffectiveDate(OrderCaptureDTO orderCapture) {
		revisePenaltyForRenewalEffectiveDate(orderCapture);
	}
	
	private boolean isSipRemoveService(ServiceDetailDTO serviceDetail){
		if(serviceDetail instanceof ServiceDetailLtsDTO){
			if(LtsConstant.LTS_SRV_TYPE_SIP.equals(((ServiceDetailLtsDTO)serviceDetail).getFromSrvType())
					&& LtsConstant.LTS_SRV_TYPE_REMOVE.equals(((ServiceDetailLtsDTO)serviceDetail).getToSrvType())){
				return true;
			}
		}
		
		return false;
	}
	
	private void modifyOrderAttbs(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder){
		
		if(LtsConstant.ORDER_TYPE_SB_UPGRADE.equals(sbOrder.getOrderType())){
			List<OrderAttbDTO> orderAttbList = new ArrayList<OrderAttbDTO>();
			ServiceDetailLtsDTO ltsSrvDtl = LtsSbOrderHelper.getLtsService(sbOrder);
			OrderAttbDTO[] orgAttbs = ltsSrvDtl.getOrderAttbs();
			OrderAttbDTO[] newAttbs = createOrderAttbs(CreateServiceType.CREATE_SRV_TYPE_UPGRADE, orderCapture);
			
			if(ArrayUtils.isNotEmpty(orgAttbs)){
				for(OrderAttbDTO orderAttb: orgAttbs){
					if(LtsCommonHelper.isValidEnum(UpgradeOrderAttb.class, orderAttb.getAttbName())){
						switch(UpgradeOrderAttb.valueOf(orderAttb.getAttbName())){
							case ALLOW_SELF_PICKUP_DEVICE:
								orderAttb.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
								break;
							default:
								orderAttb.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
						}
					}
				}
				orderAttbList.addAll(Arrays.asList(orgAttbs));
			}
			
			if(ArrayUtils.isNotEmpty(newAttbs)){
				orderAttbList.addAll(Arrays.asList(newAttbs));
			}
			
			ltsSrvDtl.setOrderAttbs(orderAttbList.toArray(new OrderAttbDTO[0]));
			ltsSrvDtl.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		}
		
	}
	
}
