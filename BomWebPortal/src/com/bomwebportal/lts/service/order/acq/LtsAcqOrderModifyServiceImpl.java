package com.bomwebportal.lts.service.order.acq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ItemAttbBaseDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumConfirmationFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqNumSelectionAndPipbFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqBillMediaBillLangFormDTO.BillMediaDtl;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PipbLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.google.common.collect.Lists;

public class LtsAcqOrderModifyServiceImpl extends LtsAcqOrderCreateServiceImpl implements LtsAcqOrderModifyService {

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
	
	public SbOrderDTO modifySbOrder(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		modifySupportDoc(acqOrderCapture, sbOrder);
		modifyPaymentMethod(acqOrderCapture, sbOrder);		
		modifyAppointment(acqOrderCapture, sbOrder);
		modifyRemark(acqOrderCapture, sbOrder);
		modifyCoreServiceDetail(acqOrderCapture, sbOrder);
		modifyImsServiceDetail(acqOrderCapture, sbOrder);
		modifyPortLaterService(acqOrderCapture, sbOrder);
		modifyPipbInfo(acqOrderCapture, sbOrder);
		modifyDnSelectedList(acqOrderCapture, sbOrder);
		modifyDsInfo(acqOrderCapture, sbOrder, bomSalesUser);
		modifyPrePay(acqOrderCapture, sbOrder);
		if (acqOrderCapture.getLtsAcqSupportDocForm() != null) {
			sbOrder.setDisMode(acqOrderCapture.getLtsAcqSupportDocForm().getDistributionMode());
			if (StringUtils.equals(LtsConstant.DISTRIBUTE_MODE_ESIGNATURE, 
					acqOrderCapture.getLtsAcqSupportDocForm().getDistributionMode())) {
				if (acqOrderCapture.getLtsAcqSupportDocForm().isSendSms()) {
					sbOrder.setSmsNo(acqOrderCapture.getLtsAcqSupportDocForm().getDistributeSms());
				}
				if (acqOrderCapture.getLtsAcqSupportDocForm().isSendEmail()) {
					sbOrder.setEsigEmailAddr(acqOrderCapture.getLtsAcqSupportDocForm().getDistributeEmail());
				}
			}
			sbOrder.setEsigEmailLang(acqOrderCapture.getLtsAcqSupportDocForm().getDistributeLang());	
			sbOrder.setCollectMethod(acqOrderCapture.getLtsAcqSupportDocForm().getCollectMethod());
			sbOrder.setSignoffMode(acqOrderCapture.getLtsAcqSupportDocForm().getSignoffMode());
			sbOrder.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			modifyAllOrdDocAssgnLts(acqOrderCapture, sbOrder, bomSalesUser);	
		}
		
		return sbOrder;
	}
	
	private void modifySupportDoc(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {
		if (isCreditCardChanged(sbOrder, acqOrderCapture)) {
			uncollectSupportDoc(sbOrder, LtsConstant.ORDER_DOC_TYPE_CREDIT_CARD_COPY);
			ltsOrderDocumentService.updateOutdatedInd(sbOrder.getOrderId(), LtsConstant.ORDER_DOC_TYPE_CREDIT_CARD_COPY, true);
		}
	}
	
	private boolean isCreditCardChanged(SbOrderDTO sbOrder, AcqOrderCaptureDTO acqOrderCapture) {
		
		ServiceDetailDTO ltsServiceDetail =  LtsSbOrderHelper.getLtsService(sbOrder);;
		
		PaymentMethodDetailLtsDTO[] paymentMethods = ltsServiceDetail.getAccount().getPaymethods();
		LtsAcqPaymentMethodFormDTO ltsPaymentForm = acqOrderCapture.getLtsAcqPaymentMethodFormDTO();
		
		if (ArrayUtils.isEmpty(paymentMethods) || ltsPaymentForm == null) {
			return false;
		}
		
		for (PaymentMethodDetailLtsDTO paymentMethod : paymentMethods) {
			if (StringUtils.equals(LtsConstant.PAYMENT_TYPE_CREDIT_CARD, paymentMethod.getPayMtdType())
					&& StringUtils.equals(LtsConstant.IO_IND_INSTALL, paymentMethod.getAction())) {
				if (StringUtils.equals(ltsPaymentForm.getPrimaryPaymentMethodDtl().getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)
						&& StringUtils.isNotEmpty(paymentMethod.getCcNum())
						&& !StringUtils.equals(ltsPaymentForm.getPrimaryPaymentMethodDtl().getCardNum(), paymentMethod.getCcNum())) {
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
		
	private String getAttbValueByAttbTypeAndKey(ItemAttbBaseDTO[] attbArray, String attbType, String attbKey){
		for(ItemAttbBaseDTO attb: attbArray){
			if(attbType.equals(attb.getInputFormat()) && attbKey.equals(attb.getAttbInfoKey())){
				return attb.getAttbValue();
			}
		}		
		return null;
	}
	
	private void modifyPaymentMethod(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {
		
		List<PaymentMethodDetailLtsDTO> paymentMethodDetailList = null;
		AccountDetailProfileLtsDTO profileAccount = null;
		ServiceDetailProfileLtsDTO serviceProfile = null;
		
		
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			
			if (LtsSbOrderHelper.isImsService(serviceDetail)) {
				continue;
			}
			
			if (LtsSbOrderHelper.isAcqPortLaterService(serviceDetail)) {
				continue;
			}
			
			for(AccountServiceAssignLtsDTO acct : serviceDetail.getAccounts()){
			
			paymentMethodDetailList = new ArrayList<PaymentMethodDetailLtsDTO>();
			
			if (acct.getAccount() != null
					&& acct.getAccount().getPaymethods() != null) {
				PaymentMethodDetailLtsDTO[] payments = acct.getAccount().getPaymethods();
				for (PaymentMethodDetailLtsDTO paymentMethodDetailLts : payments) {
					paymentMethodDetailLts.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
					paymentMethodDetailList.add(paymentMethodDetailLts);
				}
			}
/*			if("Y".equals(serviceDetail.getRecontractInd())
					&& serviceDetail.getRecontract() != null
					&& serviceDetail.getRecontract().getPaymethods() != null){
				PaymentMethodDetailLtsDTO[] payments = serviceDetail.getRecontract().getPaymethods();
				for (PaymentMethodDetailLtsDTO paymentMethodDetailLts : payments) {
					paymentMethodDetailLts.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
					paymentMethodDetailList.add(paymentMethodDetailLts);
				}
			}*/
			profileAccount = null;
			serviceProfile = null;
			
/*			if (LtsSbOrderHelper.isUpgradeService(serviceDetail) 
					|| LtsSbOrderHelper.isRenewalService(serviceDetail)
					|| LtsSbOrderHelper.isDuplexService(serviceDetail)) {*/
//				serviceProfile = acqOrderCapture.getLtsServiceProfile();
			
			if (LtsSbOrderHelper.isNewAcqEyeService(serviceDetail) 
					|| LtsSbOrderHelper.isNewAcqDelService(serviceDetail)){
				
				/*MyHKT (CsPortal) update*/
				CustomerDetailLtsDTO custDtlLts = acct.getAccount().getCustomer();
				custDtlLts.setCsPortalInd("N");
				custDtlLts.setCsPortalLogin(null);
				custDtlLts.setCsPortalMobile(null);
				custDtlLts.setTheClubInd("N");
		        custDtlLts.setTheClubLogin(null);
		        custDtlLts.setTheClubMobile(null);
				if(acqOrderCapture.getLtsAcqBillMediaBillLangForm() != null){
					for(BillMediaDtl billMediaDtl : acqOrderCapture.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList()){
					    if (billMediaDtl.getAcctNum().equals(acct.getAccount().getAcctNo())){
						    ItemDetailDTO cspItem = billMediaDtl.getCsPortalItem();
						    if(cspItem != null && cspItem.isSelected() && cspItem.getItemAttbs() != null){
							    if(StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL, cspItem.getItemType())){
							        custDtlLts.setCsPortalInd("Y");
							        String cspEmail = getAttbValueByAttbTypeAndKey(cspItem.getItemAttbs(), LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL, LtsConstant.ITEM_ATTB_INFO_KEY_HKT);
							        String cspMobile = getAttbValueByAttbTypeAndKey(cspItem.getItemAttbs(), LtsConstant.CHANNEL_ATTB_FORMAT_MOBILE_NUM, LtsConstant.ITEM_ATTB_INFO_KEY_HKT);
							        custDtlLts.setCsPortalLogin(cspEmail);
							        custDtlLts.setCsPortalMobile(cspMobile);							        
						        }
							    if(StringUtils.equals(LtsConstant.ITEM_TYPE_THE_CLUB_BILL, cspItem.getItemType())){							        
							        custDtlLts.setTheClubInd("Y");
							        String clubEmail = getAttbValueByAttbTypeAndKey(cspItem.getItemAttbs(), LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL, LtsConstant.ITEM_ATTB_INFO_KEY_CLUB);
							        String clubMobile = getAttbValueByAttbTypeAndKey(cspItem.getItemAttbs(), LtsConstant.CHANNEL_ATTB_FORMAT_MOBILE_NUM, LtsConstant.ITEM_ATTB_INFO_KEY_CLUB);
							        custDtlLts.setTheClubLogin(clubEmail);
							        custDtlLts.setTheClubMobile(clubMobile);
						        }
						        if(StringUtils.equals(LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL, cspItem.getItemType())){
							        custDtlLts.setCsPortalInd("Y");
							        String cspEmail = ltsCommonService.getAttbValueByInputFormat(cspItem.getItemAttbs(), LtsConstant.CHANNEL_ATTB_FORMAT_EMAIL);
							        String cspMobile = ltsCommonService.getAttbValueByInputFormat(cspItem.getItemAttbs(), LtsConstant.CHANNEL_ATTB_FORMAT_MOBILE_NUM);
							        custDtlLts.setCsPortalLogin(cspEmail);
							        custDtlLts.setCsPortalMobile(cspMobile);
							        custDtlLts.setTheClubInd("Y");
							        custDtlLts.setTheClubLogin(cspEmail);
							        custDtlLts.setTheClubMobile(cspMobile);
						        }
					        }
					    }
					}
				}
				custDtlLts.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
				
				/*Billing Address update*/
//				LtsAcqPaymentMethodFormDTO paymentForm = acqOrderCapture.getLtsAcqPaymentMethodFormDTO();
//				LtsAddressRolloutFormDTO addressRolloutForm  = acqOrderCapture.getLtsAddressRolloutForm();
				
/*				if(!addressRolloutForm.isExternalRelocate() && paymentForm != null && paymentForm.isAllowChangeBa() && paymentForm.isChangeBa()){
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
				}*/
				
			}
			else if (LtsSbOrderHelper.isNew2ndDelService(serviceDetail)) {
				serviceProfile = acqOrderCapture.getSecondDelServiceProfile();
				
			}
			

			
			/*recontract*/
/*			if(!LtsSbOrderHelper.isNew2ndDelService(serviceDetail) 
					&& "Y".equals(serviceDetail.getRecontractInd())
					&& acqOrderCapture.getLtsRecontractForm() != null){
				AccountDetailProfileLtsDTO recontractAccount = acqOrderCapture.getLtsRecontractForm().getAccountDetailProfile();
				PaymentMethodDetailLtsDTO[] paymentMethodDetails = createRecontractPaymentMethodDetailLts(acqOrderCapture, recontractAccount);
				ArrayList<PaymentMethodDetailLtsDTO> recontractPaymentMethodDetailList = new ArrayList<PaymentMethodDetailLtsDTO>();
				recontractPaymentMethodDetailList.addAll(paymentMethodDetailList);
				for (int i=0; paymentMethodDetails != null && i < paymentMethodDetails.length; i++) {
					recontractPaymentMethodDetailList.add(paymentMethodDetails[i]);
				}
				serviceDetail.getRecontract().setPaymethods(recontractPaymentMethodDetailList.toArray(new PaymentMethodDetailLtsDTO[recontractPaymentMethodDetailList.size()]));
				
				LtsPaymentFormDTO payment = acqOrderCapture.getLtsPaymentForm();
				serviceDetail.getRecontract().setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
				if(payment != null){
					if(StringUtils.isNotBlank(payment.getRecontractAccountNo())){
						serviceDetail.getRecontract().setAcctNum(payment.getRecontractAccountNo());
					}else if(StringUtils.isNotBlank(payment.getExistBillAccNum())){
						serviceDetail.getRecontract().setAcctNum(payment.getExistBillAccNum());
					}
				}
				
			}else{*/

//				if (serviceProfile != null) {
/*			if (acqOrderCapture.getAccountDetailProfileLtsDTO() != null) {
					profilePrimaryAccount = LtsSbOrderHelper.getAcqPrimaryAccount(acqOrderCapture.getAccountDetailProfileLtsDTO());
				}*/
			if (acqOrderCapture.getAccountDetailProfileLtsDTO() != null) {
				for(AccountDetailProfileLtsDTO profileAcct : acqOrderCapture.getAccountDetailProfileLtsDTO()){
					if (profileAcct.getAcctNum().equals(acct.getAccount().getAcctNo())){
						profileAccount = profileAcct;
					}
				}
			}
			
			CreateServiceType createServiceType = CreateServiceType.CREATE_SRV_TYPE_ACQ_EYE;
			
			if(acqOrderCapture.isEyeOrder()){
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_ACQ_EYE;
			}
			else{
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_ACQ_DEL;
			}
				
				PaymentMethodDetailLtsDTO[] paymentMethodDetails =  createPaymentMethodDetailLts(acqOrderCapture, profileAccount, createServiceType);

				for (int i=0; paymentMethodDetails != null && i < paymentMethodDetails.length; i++) {
					paymentMethodDetailList.add(paymentMethodDetails[i]);
				}
				
				acct.getAccount().setPaymethods(paymentMethodDetailList.toArray(new PaymentMethodDetailLtsDTO[paymentMethodDetailList.size()]));
				
//			}
			}
			modifyPaymentItem(acqOrderCapture, sbOrder);
		    
		}
		
	}
	
	private void modifyPaymentItem(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {
		List<ItemDetailLtsDTO> ItemDetailList = new ArrayList<ItemDetailLtsDTO>();

		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		
		for(AccountServiceAssignLtsDTO acct : ltsService.getAccounts()){
			
			for (BillMediaDtl billMediaDtl : acqOrderCapture.getLtsAcqBillMediaBillLangForm().getBillMediaDtlList()){
				
				if (billMediaDtl.getAcctNum().equals(acct.getAccount().getAcctNo())){
					
				    String basketId = null;
				    BasketDetailDTO selectedBasket = null;
			 	    for (ItemDetailLtsDTO selectedItem : ltsService.getItemDtls()) {
					    if (StringUtils.equals(LtsConstant.ITEM_TYPE_PREPAY, selectedItem.getItemType()) 
						    	|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL, selectedItem.getItemType())
								|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL_BR, selectedItem.getItemType())
								|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE, selectedItem.getItemType())
								|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_EBILL, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_THE_CLUB_BILL, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_ER_CHARGE, selectedItem.getItemType())
						    	|| StringUtils.equals(LtsConstant.ITEM_TYPE_EMAIL_BILL, selectedItem.getItemType())) {
						    selectedItem.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
					    }
					    if (StringUtils.equals(LtsConstant.ITEM_TYPE_PLAN, selectedItem.getItemType())) {
						    basketId = selectedItem.getBasketId();
					    }
					    ItemDetailList.add(selectedItem);
				    }
				    if (StringUtils.isNotEmpty(basketId)) {
					    selectedBasket = ltsBasketService.getBasket(basketId, LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
					    if (billMediaDtl.getPaperBillItem() != null) {
					        addItemDetail(selectedBasket, Lists.newArrayList(billMediaDtl.getPaperBillItem()), ItemDetailList);
					    }
					    if (billMediaDtl.getEmailBillItem() != null) {
					        addItemDetail(selectedBasket, Lists.newArrayList(billMediaDtl.getEmailBillItem()), ItemDetailList);
					    }
					    
					    for (PaymentMethodDtl payMtdDtl : acqOrderCapture.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList()){
							
							if (payMtdDtl.getAcctNum().equals(acct.getAccount().getAcctNo())){
					            if (payMtdDtl.getSelectedBasketPrepayItem() != null) {
						            addItemDetail(selectedBasket, Lists.newArrayList(payMtdDtl.getSelectedBasketPrepayItem()), ItemDetailList);
					            }
					        }
					    }
					    if(billMediaDtl.getCsPortalItem() != null){
						    addItemDetail(selectedBasket, Lists.newArrayList(billMediaDtl.getCsPortalItem()), ItemDetailList);
					    }
		//			    addItemDetail(selectedBasket, acqOrderCapture.getLtsPaymentForm().getErChargeItemList(), ItemDetailList);
				    }
				    ltsService.setItemDtls(ItemDetailList.toArray(new ItemDetailLtsDTO[ItemDetailList.size()]));
				
				    acct.getAccount().setBillMedia(getBillMedia(acqOrderCapture, acct.getAccount().getAcctNo())); //temp use 0
			    	if(LtsConstant.PAPER_BILL_WAIVE_REASON_OTHER.equals(billMediaDtl.getSelectedWaiveReason())){
			    		 acct.getAccount().setWaivePaperRemarks(billMediaDtl.getPaperBillWaiveOtherStaffId());	
			    	}		
				    acct.getAccount().setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
				
				    for (ItemDetailLtsDTO selectedItem : ltsService.getItemDtls()) {
					    if (StringUtils.equals(LtsConstant.ITEM_TYPE_EMAIL_BILL, selectedItem.getItemType())) {
						    ltsService.getAccount().setRedemptionEmailAddr(billMediaDtl.getRedemMediaEmail());
					    }
					    else {
						    ltsService.getAccount().setRedemptionEmailAddr(null);
					    }
				    }
				    
				    
				    /*---Second Del Service modify START---*/
				    ServiceDetailLtsDTO secondDelService = LtsSbOrderHelper.get2ndDelService(sbOrder);
				
				    if (secondDelService == null) {
					    return;
				    }
				
				    if (!StringUtils.equals(ltsService.getAccount().getAcctNo(), secondDelService.getAccount().getAcctNo())) {
					    return;
				    }
				
				    List<ItemDetailLtsDTO> secondDelItemDetailList = new ArrayList<ItemDetailLtsDTO>();
				
				    for (ItemDetailLtsDTO selectedItem : secondDelService.getItemDtls()) {
					    if (StringUtils.equals(LtsConstant.ITEM_TYPE_PREPAY, selectedItem.getItemType()) 
						    	|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL, selectedItem.getItemType())
								|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL_BR, selectedItem.getItemType())
								|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL_GENERATE, selectedItem.getItemType())
								|| StringUtils.equals(LtsConstant.ITEM_TYPE_PAPER_BILL_WAIVE, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_VIEW_ON_DEVICE, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_EBILL, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_MYHKT_BILL, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_EXIST_MYHKT_BILL, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_THE_CLUB_BILL, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_HKT_THE_CLUB_BILL, selectedItem.getItemType())
							    || StringUtils.equals(LtsConstant.ITEM_TYPE_EMAIL_BILL, selectedItem.getItemType())) {
						    selectedItem.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
					    }
					    if (StringUtils.isNotEmpty(selectedItem.getBasketId())) {
						    basketId = selectedItem.getBasketId();
					    }
					    secondDelItemDetailList.add(selectedItem);
				    }
				    
				    if (billMediaDtl.getPaperBillItem() != null) {
				        addItemDetail(selectedBasket, Lists.newArrayList(billMediaDtl.getPaperBillItem()), secondDelItemDetailList);
				    }
		            if (billMediaDtl.getEmailBillItem() != null) {		
			            addItemDetail(selectedBasket, Lists.newArrayList(billMediaDtl.getEmailBillItem()), secondDelItemDetailList);
		            }
		            if(billMediaDtl.getCsPortalItem() != null){
			            addItemDetail(selectedBasket, Lists.newArrayList(billMediaDtl.getCsPortalItem()), ItemDetailList);
		            }
		            

				    /*---Second Del Service modify END---*/
		    
				}
		    
			}
		
		}

	}
	
	private void modifyItemDtls(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {

		ServiceDetailLtsDTO ltsService = LtsSbOrderHelper.getLtsService(sbOrder);
		String basketId = null;
	    BasketDetailDTO selectedBasket = null;
	    List<ItemDetailLtsDTO> ItemDetailList = new ArrayList<ItemDetailLtsDTO>();
	    
		//BOM2018061
    	if(acqOrderCapture.getLtsAcqAppointmentForm() != null){
    		if(ltsService.getItemDtls() != null){
				for (ItemDetailLtsDTO selectedItem : ltsService.getItemDtls()) {
					if (StringUtils.equals(LtsConstant.ITEM_TYPE_EPD_ACCEPT, selectedItem.getItemType()) 
							|| StringUtils.equals(LtsConstant.ITEM_TYPE_EPD_FORFEIT, selectedItem.getItemType())
							|| StringUtils.equals(LtsConstant.ITEM_TYPE_EPD_UNDER_CONSIDERATION, selectedItem.getItemType())) {
						selectedItem.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
					}
	
					if (StringUtils.isBlank(basketId) && StringUtils.isNotBlank(selectedItem.getBasketId())) {
						basketId = selectedItem.getBasketId();
					}
					
				}
			
		    	ItemDetailList = new ArrayList<ItemDetailLtsDTO>(Arrays.asList(ltsService.getItemDtls()));
    		}
			if (selectedBasket == null && StringUtils.isNotBlank(basketId)) {
				selectedBasket = ltsBasketService.getBasket(basketId, LtsConstant.LOCALE_ENG, LtsConstant.DISPLAY_TYPE_RP_PROMOT);
			}
			
			addItemDetail(selectedBasket, acqOrderCapture.getLtsAcqAppointmentForm().getEpdItemList(), ItemDetailList);
			
			ltsService.setItemDtls(ItemDetailList.toArray(new ItemDetailLtsDTO[ItemDetailList.size()]));

    	}

		
	}
	
	public void modifyAppointment(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {
		if (acqOrderCapture.getLtsAcqAppointmentForm() == null
				|| acqOrderCapture.getLtsAcqAppointmentForm().getInstallAppntDtl().getEarliestSRD() == null) {
			return;
		}
		CreateServiceType createServiceType = null;
		for (ServiceDetailDTO serviceDetail : sbOrder.getSrvDtls()) {
			
			if (LtsSbOrderHelper.isNewAcqEyeService(serviceDetail)) {
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_ACQ_EYE;
			}
			if (LtsSbOrderHelper.isNewAcqDelService(serviceDetail)) {
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_ACQ_DEL;
			}
			if (LtsSbOrderHelper.is2ndDelService(serviceDetail)) {
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_ACQ_2DEL;
			}
			if (LtsSbOrderHelper.isImsService(serviceDetail)) {
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_FSA;
			}
			if (LtsSbOrderHelper.isAcqPortLaterService(serviceDetail)) {
				createServiceType = CreateServiceType.CREATE_SRV_TYPE_ACQ_PORT_LATER;
			}
			
			AppointmentDetailLtsDTO newAppointmentDetailLts = createAppointmentDetailLts(createServiceType, acqOrderCapture);

//			if (LtsSbOrderHelper.isUpgradeService(serviceDetail)) {
				modifyServiceSuggestedSrd(serviceDetail, acqOrderCapture);
//			}
			
			
			if (newAppointmentDetailLts == null) {
				continue;
			}
			
			if (serviceDetail.getAppointmentDtl() != null) {
				newAppointmentDetailLts.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			}
			serviceDetail.setAppointmentDtl(newAppointmentDetailLts);			
		}
		//2018061
		modifyItemDtls(acqOrderCapture, sbOrder);
	}
	
	private void modifyServiceSuggestedSrd(ServiceDetailDTO serviceDetail, AcqOrderCaptureDTO acqOrderCapture) {
		
		LtsAcqAppointmentFormDTO ltsAppointmentForm = acqOrderCapture.getLtsAcqAppointmentForm();
		
		if (ltsAppointmentForm != null) {
			serviceDetail.setSuggestSrd(ltsAppointmentForm.getInstallAppntDtl().getEarliestSRD().getDateString());
			serviceDetail.setSuggestSrdReasonCd(ltsAppointmentForm.getInstallAppntDtl().getEarliestSRD().getReasonCd());
			serviceDetail.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		}	
		
	}
	
	private void modifyRemark(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {
		if (acqOrderCapture.getLtsAcqAppointmentForm() == null) {
			return;
		}
		
		ServiceDetailDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);;
		
		if (ltsServiceDetail.getRemarks() != null && ArrayUtils.isNotEmpty(ltsServiceDetail.getRemarks())) {
			for (RemarkDetailLtsDTO remarkDetailLts : ltsServiceDetail.getRemarks()) {
				remarkDetailLts.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			}
		}
		List<RemarkDetailLtsDTO> addonRemarkList = createAddonRemarkDetailLts(acqOrderCapture);
		if (addonRemarkList != null && !addonRemarkList.isEmpty()) {
			if(ltsServiceDetail.getRemarks()==null){
				ltsServiceDetail.setRemarks(addonRemarkList.toArray(new RemarkDetailLtsDTO[addonRemarkList.size()]));
			}else{
				List<RemarkDetailLtsDTO> rmklist = new ArrayList<RemarkDetailLtsDTO>(Arrays.asList(ltsServiceDetail.getRemarks()));
				for (RemarkDetailLtsDTO addonRemark : addonRemarkList) {
					rmklist.add(addonRemark);	
				}
				ltsServiceDetail.setRemarks(rmklist.toArray(new RemarkDetailLtsDTO[rmklist.size()]));
			}
		}
		
	}
	
	private void modifyAllOrdDocAssgnLts(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		
		AllOrdDocAssgnLtsDTO[] existingAllOrdDocAssgns = orderRetrieveLtsService.retrieveAllOrdDocAssgn(sbOrder.getOrderId()); 
		AllOrdDocAssgnLtsDTO[] newAllOrdDocAssgns = createAllOrdDocAssgnLts(acqOrderCapture, bomSalesUser);
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
	
	public int updateEdfRef(String orderId, String dtlId, String edfRef, String userId)  {
		try {
			return orderModifyDao.updateEdfRef(orderId, dtlId, edfRef, userId);
		}
		catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e);
		}
	}
	
	private void modifyCoreServiceDetail(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {		
		LtsAcqNumSelectionAndPipbFormDTO numSelectionAndPipbForm = acqOrderCapture.getLtsAcqNumSelectionAndPipbForm();
		LtsAcqNumConfirmationFormDTO acqNumConfirmationForm = acqOrderCapture.getLtsAcqNumConfirmationForm();
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		// to get the primary service (TO_PROD = EYE3A / DEL), 
		// determine the selection which is either "User New DN" or "Use PIPB DN" depends the DN Source		
		if (LtsConstant.DN_SOURCE_DN_POOL.equals(ltsServiceDetail.getDnSource())
				|| LtsConstant.DN_SOURCE_DN_RESERVED.equals(ltsServiceDetail.getDnSource())) {
			if (acqNumConfirmationForm.getNewDn()==null) {
				ltsServiceDetail.setSrvNum(null);
			} else {
				ltsServiceDetail.setSrvNum(acqNumConfirmationForm.getNewDn());
			}
			if (LtsConstant.DN_SOURCE_DN_POOL.equals(numSelectionAndPipbForm.getDnSource())) {			
				ltsServiceDetail.setNrpAccountCd(null);
				ltsServiceDetail.setNrpBoc(null);
				ltsServiceDetail.setNrpProjectCd(null);
				ltsServiceDetail.setDnSource(LtsConstant.DN_SOURCE_DN_POOL);
			}
			if (LtsConstant.DN_SOURCE_DN_RESERVED.equals(numSelectionAndPipbForm.getDnSource())) {
				ltsServiceDetail.setNrpAccountCd(numSelectionAndPipbForm.getReservedAccountCd());
				ltsServiceDetail.setNrpBoc(numSelectionAndPipbForm.getReservedBoc());
				ltsServiceDetail.setNrpProjectCd(numSelectionAndPipbForm.getReservedProjectCd());
				ltsServiceDetail.setDnSource(LtsConstant.DN_SOURCE_DN_RESERVED);			
			}
			ltsServiceDetail.setDnStatus(numSelectionAndPipbForm.getDnStatus());
		} else if (LtsConstant.DN_SOURCE_DN_PIPB.equals(ltsServiceDetail.getDnSource())) {
			if (acqNumConfirmationForm.getPipbDn()==null) {
				ltsServiceDetail.setSrvNum(null);
			} else {
				ltsServiceDetail.setSrvNum(acqNumConfirmationForm.getPipbDn());
			}
			if (LtsConstant.DN_SOURCE_DN_PIPB.equals(numSelectionAndPipbForm.getPipbInfo().getDnSource())) {
				if (StringUtils.equals(LtsConstant.INVENT_STS_RESERVED, 
						acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getDnStatus())) {
					ltsServiceDetail.setNrpAccountCd(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getPipbAccountCd());
					ltsServiceDetail.setNrpBoc(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getPipbBoc());
					ltsServiceDetail.setNrpProjectCd(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getPipbInfo().getPipbProjectCd());
			    } else {
			    	ltsServiceDetail.setNrpAccountCd(null);
					ltsServiceDetail.setNrpBoc(null);
					ltsServiceDetail.setNrpProjectCd(null);
			    }
			}
			ltsServiceDetail.setDnStatus(numSelectionAndPipbForm.getPipbInfo().getDnStatus());
		}
		ltsServiceDetail.setExDirInd(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().isIncludeSrvNumOnExDir()?"Y":"N");
		ltsServiceDetail.setExDirName(acqOrderCapture.getLtsAcqNumSelectionAndPipbForm().getDirectoryName());
		ltsServiceDetail.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
	}
	
	private void modifyImsServiceDetail(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {
		ServiceDetailOtherLtsDTO srvDtlIms = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());		
		if (srvDtlIms!=null) {
			LtsAcqNumConfirmationFormDTO acqNumConfirmationForm = acqOrderCapture.getLtsAcqNumConfirmationForm();
			ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
			if (LtsConstant.DN_SOURCE_DN_POOL.equals(ltsServiceDetail.getDnSource())
					|| LtsConstant.DN_SOURCE_DN_RESERVED.equals(ltsServiceDetail.getDnSource())) {
				srvDtlIms.setLoginId(LtsSbOrderHelper.generateWgLoginId(acqNumConfirmationForm.getNewDn()));					
			} else if (LtsConstant.DN_SOURCE_DN_PIPB.equals(ltsServiceDetail.getDnSource())) {
				srvDtlIms.setLoginId(LtsSbOrderHelper.generateWgLoginId(acqNumConfirmationForm.getPipbDn()));			
			}
			srvDtlIms.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		}
	}
		
	private void modifyPortLaterService(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {
		LtsAcqNumSelectionAndPipbFormDTO numSelectionAndPipbForm = acqOrderCapture.getLtsAcqNumSelectionAndPipbForm();
		LtsAcqNumConfirmationFormDTO acqNumConfirmationForm = acqOrderCapture.getLtsAcqNumConfirmationForm();
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
		if (ltsServiceDetail!=null && LtsConstant.DN_SOURCE_DN_PIPB.equals(ltsServiceDetail.getDnSource())) {
			if (acqNumConfirmationForm.getPipbDn()==null) {
				ltsServiceDetail.setSrvNum(null);
			} else {
				ltsServiceDetail.setSrvNum(acqNumConfirmationForm.getPipbDn());
			}
			if (LtsConstant.DN_SOURCE_DN_PIPB.equals(numSelectionAndPipbForm.getPipbInfo().getDnSource())) {				
		    	ltsServiceDetail.setNrpAccountCd(null);
				ltsServiceDetail.setNrpBoc(null);
				ltsServiceDetail.setNrpProjectCd(null);
			}			
			ltsServiceDetail.setDnStatus(numSelectionAndPipbForm.getPipbInfo().getDnStatus());
			ltsServiceDetail.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		}
	}
	
	private void modifyPipbInfo(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getAcqPipbService(sbOrder.getSrvDtls());
		if (ltsServiceDetail!=null) {
			PipbLtsDTO pipbLtsDTO = getPipbInfo(acqOrderCapture);
			pipbLtsDTO.setCreateBy(ltsServiceDetail.getPipb().getCreateBy());
			ltsServiceDetail.setPipb(pipbLtsDTO);			
			ltsServiceDetail.getPipb().setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
			ltsServiceDetail.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		}
	}
	
	private void modifyDsInfo(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
			setDsInfo(sbOrder, acqOrderCapture, bomSalesUser);
	}
	
	private void modifyPrePay(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {
		setPrepay(sbOrder, acqOrderCapture);
    }
	
	private void modifyDnSelectedList(AcqOrderCaptureDTO acqOrderCapture, SbOrderDTO sbOrder) {	
		setResDn(sbOrder, acqOrderCapture);
	}
	
}
