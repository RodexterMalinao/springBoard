package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AddressInventoryDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelAttbLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ImsL2JobDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.NowtvDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDTO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDetailDTO;
import com.bomwebportal.lts.dto.order.OrderAttbDTO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.lts.dto.order.PipbLtsDTO;
import com.bomwebportal.lts.dto.order.RecontractLtsDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ResDnLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.Service0060DetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceCallPlanDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.order.StaffInfoLtsDTO;
import com.bomwebportal.lts.dto.order.ThirdPartyDetailLtsDTO;
import com.bomwebportal.service.ServiceActionBase;
import com.pccw.util.spring.SpringApplicationContext;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerIguardRegDTO;

public class SaveSbOrderLtsServiceImpl implements SaveSbOrderLtsService {

//	private ServiceActionBase sbOrderService;
//	private ServiceActionBase serviceDetailService;
//	private ServiceActionBase serviceDetailLtsService;
//	private ServiceActionBase serviceDetailOtherService;
//	private ServiceActionBase channelLtsService;
//	private ServiceActionBase nowtvDetailLtsService;
//	private ServiceActionBase itemLtsService;
//	private ServiceActionBase itemAttributeLtsService;
//	private ServiceActionBase appointmentLtsService;
//	private ServiceActionBase thirdPartyApplnService;
//	private ServiceActionBase remarkLtsService;
//	
//	private ServiceActionBase accountLtsService;
//	private ServiceActionBase customerLtsService;
//	private ServiceActionBase paymentLtsService;
//	private ServiceActionBase accountSeviceAssgnService;
//	private ServiceActionBase addressInventoryLtsService;
//	private ServiceActionBase channelAttbLtsService;
//	private ServiceActionBase allOrdDocAssgnLtsService;
//	private ServiceActionBase custOptOutInfoLtsService;
//	private ServiceActionBase imsOfferDetailService;
//	private ServiceActionBase billingAddressLtsService;
//	private ServiceActionBase recontractLtsService;
//	private ServiceActionBase accountServiceAssignLtsService;
//	private ServiceActionBase staffInfoLtsService;
//	private ServiceActionBase contactLtsService;
//	private ServiceActionBase service0060DetailLtsService;
//	private ServiceActionBase serviceCallPlanDetailLtsService;
//	private ServiceActionBase resDnLtsService;
	
	@Transactional
	public void saveService(ServiceDetailDTO pSrvDtl, String pOrderId, String pUser) {
		this.saveServiceDetail(pSrvDtl, pOrderId, pUser);
	}
	
	@Transactional
	public void saveAddress(AddressDetailLtsDTO pAddress, String pOrderId, String pUser) {
		if (pAddress == null) {
			return;
		}
		ServiceActionBase addressLtsService = SpringApplicationContext.getBean("addressLtsService");
		addressLtsService.doSave(pAddress, pUser, pOrderId);
		this.setAddressRelatedAction(pAddress);
		this.saveAddressInventory(pAddress.getAddrInventory(), pOrderId, pUser);
	}
	
	@Transactional
    public void savePipb(PipbLtsDTO pPipb, String pOrderId, String pDtlId, String pUser) {
		
		if (pPipb == null) {
			return;
		}
		ServiceActionBase pipbLtsService = SpringApplicationContext.getBean("pipbLtsService");
		pipbLtsService.doSave(pPipb, pUser, pOrderId, pDtlId);
		
	}

	@Transactional
    public void saveCustomer(CustomerDetailLtsDTO pCustomer, String pOrderId, String custNo, String pUser) {

		if (pCustomer == null) {
			return;
		}
		ServiceActionBase customerLtsService = SpringApplicationContext.getBean("customerLtsService");
		customerLtsService.doSave(pCustomer, pUser, pOrderId);
		this.setCustomerRelatedAction(pCustomer);
		this.saveCustOptOutInfo(pCustomer.getCustOptOutInfo(), pOrderId, custNo, pUser);
	}
	
	@Transactional
	public void saveCustomerIguardReg(CustomerIguardRegDTO pCustomerIguardReg, String pOrderId, String pUser) {
		
		if (pCustomerIguardReg == null) {
			return;
		}
		ServiceActionBase customerIguardRegService = SpringApplicationContext.getBean("customerIguardRegService");
		customerIguardRegService.doSave(pCustomerIguardReg, pUser, pOrderId);
	}
	
	@Transactional
	public void saveSbOrder(SbOrderDTO pSbOrder, String pUser) {

		if (pSbOrder == null) {
			return;
		}
		
		if(pSbOrder.getLastServiceInd() == null)
		{
			pSbOrder.setLastServiceInd("N");
		}
		ServiceActionBase sbOrderService = SpringApplicationContext.getBean("sbOrderService");
		sbOrderService.doSave(pSbOrder, pUser);
		this.updateSbOrderRelatedAction(pSbOrder);
		ServiceDetailDTO[] srvDtls = (ServiceDetailDTO[])this.sortSubmitSeq(pSbOrder.getSrvDtls());

		for (int i = 0; srvDtls != null && i < srvDtls.length; ++i) {
			this.saveServiceDetail(srvDtls[i], pSbOrder.getOrderId(), pUser);
		}
		AccountDetailLtsDTO[] accounts = (AccountDetailLtsDTO[])this.sortSubmitSeq(pSbOrder.getAccounts());
		
		for (int i=0; accounts!=null && i<accounts.length; ++i) {
			this.saveAccount(accounts[i], pSbOrder.getOrderId(), pUser);
		}
		CustomerDetailLtsDTO[] customers = (CustomerDetailLtsDTO[])this.sortSubmitSeq(pSbOrder.getCustomers());
		
		for (int i=0; customers!=null && i<customers.length; ++i) {
			this.saveCustomer(customers[i], pSbOrder.getOrderId(), customers[i].getCustNo(), pUser);
		}		
		AllOrdDocAssgnLtsDTO[] allOrdDocAssgn = (AllOrdDocAssgnLtsDTO[])this.sortSubmitSeq(pSbOrder.getAllOrdDocAssgns());
		
		for (int i=0; allOrdDocAssgn!=null && i<allOrdDocAssgn.length; ++i) {
			this.saveAllOrdDocAssgn(allOrdDocAssgn[i], pSbOrder.getOrderId(), pUser);
		}
		this.saveAddress(pSbOrder.getAddress(), pSbOrder.getOrderId(), pUser);
		this.saveBillingAddress(pSbOrder, pUser);
		this.saveStaffInfo(pSbOrder.getStaffInfo(), pSbOrder.getOrderId(), pUser);
		this.saveContact(pSbOrder.getContact(), pSbOrder.getOrderId(), pUser);
		this.saveSrv0060Detail(pSbOrder.getSrv0060Dtls(), pSbOrder.getOrderId(), pUser);
		this.saveResDn(pSbOrder.getResDn(), pSbOrder.getOrderId(), pUser);
		this.saveDsStaffInfo(pSbOrder.getLtsDsOrderInfo(), pSbOrder.getOrderId(), pUser);
		this.savePrepay(pSbOrder.getPrepay(), pSbOrder.getOrderId(), pUser);
		this.saveCustomerIguardReg(pSbOrder.getCustIguardReg(), pSbOrder.getOrderId(), pUser);
	}
	
	@Transactional
    public void saveDsStaffInfo(LtsDsOrderInfoDTO pDsStaffInfo, String pOrderId, String pUser) {

		if (pDsStaffInfo == null) {
			return;
		}
		this.setObjectAction(pDsStaffInfo, ObjectActionBaseDTO.ACTION_DELETE);
		ServiceActionBase ltsDsOrderInfoService = SpringApplicationContext.getBean("ltsDsOrderInfoService");
		ltsDsOrderInfoService.doSave(pDsStaffInfo, pUser, pOrderId);
		
		this.setObjectAction(pDsStaffInfo, ObjectActionBaseDTO.ACTION_ADD);
		ltsDsOrderInfoService = SpringApplicationContext.getBean("ltsDsOrderInfoService");
		ltsDsOrderInfoService.doSave(pDsStaffInfo, pUser, pOrderId);
	}
	
    private void savePrepay(PrepayLtsDTO[] pPrepay, String pOrderId, String pUser) {
		
		if (pPrepay == null) {
			return;
		}
		this.setObjectAction(pPrepay, ObjectActionBaseDTO.ACTION_DELETE);
		ServiceActionBase prepayLtsService = SpringApplicationContext.getBean("prepayLtsService");
		if(pPrepay[0]!=null){
			prepayLtsService.doSave(pPrepay[0], pUser, pOrderId);
		}
		
		this.setObjectAction(pPrepay, ObjectActionBaseDTO.ACTION_ADD);
		for(int i =0; i < pPrepay.length; i++){
			if(pPrepay[i]!=null){
			prepayLtsService = SpringApplicationContext.getBean("prepayLtsService");
			prepayLtsService.doSave(pPrepay[i], pUser, pOrderId);
			}
		}
	}
	
	private void updateSbOrderRelatedAction(SbOrderDTO pSbOrder) {
		
		if (pSbOrder.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		this.setObjectAction(pSbOrder.getSrvDtls(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSbOrder.getAccounts(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSbOrder.getAddress(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSbOrder.getStaffInfo(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSbOrder.getContact(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSbOrder.getSrv0060Dtls(), ObjectActionBaseDTO.ACTION_DELETE);
	}

	private void saveServiceDetail(ServiceDetailDTO pSrvDtl, String pOrderId, String pUser) {

		if (pSrvDtl == null) {
			return;
		}
		
		if (ObjectActionBaseDTO.ACTION_DELETE == pSrvDtl.getObjectAction()) {
			AccountServiceAssignLtsDTO[] accts = (AccountServiceAssignLtsDTO[]) this.sortSubmitSeq(pSrvDtl.getAccounts());
			this.setObjectAction(pSrvDtl.getAccounts(), ObjectActionBaseDTO.ACTION_DELETE);
			for (int i=0; accts!=null && i<accts.length; ++i) {
				this.saveAcctSrvAssgn(accts[i], pOrderId, pSrvDtl.getDtlId(), pUser);	
			}	
		}		
		ServiceActionBase serviceDetailService = SpringApplicationContext.getBean("serviceDetailService");
		serviceDetailService.doSave(pSrvDtl, pUser, pOrderId, null);
		this.updateServiceDetailRelatedAction(pSrvDtl);
		
		if (pSrvDtl instanceof ServiceDetailLtsDTO) {
			this.saveServiceDetailLts((ServiceDetailLtsDTO) pSrvDtl, pOrderId, pUser);
		} else if (pSrvDtl instanceof ServiceDetailOtherLtsDTO) {
			this.saveServiceDetailOtherLts((ServiceDetailOtherLtsDTO) pSrvDtl, pOrderId, pUser);
		}
		RemarkDetailLtsDTO[] remarks = (RemarkDetailLtsDTO[])this.sortSubmitSeq(pSrvDtl.getRemarks());

		for (int i = 0; remarks != null && i < remarks.length; ++i) {
			this.saveRemark(remarks[i], pOrderId, pSrvDtl.getDtlId(), pUser);
		}
		ServiceCallPlanDetailLtsDTO[] callPlan = (ServiceCallPlanDetailLtsDTO[])this.sortSubmitSeq(pSrvDtl.getSrvCallPlanDtls());

		for (int i = 0; callPlan != null && i < callPlan.length; ++i) {
			this.saveSrvCallPlanDetail(callPlan[i], pOrderId, pSrvDtl.getDtlId(), pUser);
		}
		
		if (ObjectActionBaseDTO.ACTION_DELETE != pSrvDtl.getObjectAction()) {
			AccountServiceAssignLtsDTO[] accts = (AccountServiceAssignLtsDTO[]) this.sortSubmitSeq(pSrvDtl.getAccounts());
			for (int i=0; accts!=null && i<accts.length; ++i) {
				this.saveAcctSrvAssgn(accts[i], pOrderId, pSrvDtl.getDtlId(), pUser);	
			}
		}
		
		this.saveAppointmentDetail(pSrvDtl.getAppointmentDtl(), pOrderId, pSrvDtl.getDtlId(), pUser);
		this.saveThirdPartyDetail(pSrvDtl.getThirdPartyDtl(), pOrderId, pSrvDtl.getDtlId(), pUser);
		this.saveRecontract(pSrvDtl.getRecontract(), pOrderId, pSrvDtl.getDtlId(), pUser);
		this.savePipb(pSrvDtl.getPipb(), pOrderId, pSrvDtl.getDtlId(), pUser);
		
		OrderAttbDTO[] orderAttbs = pSrvDtl.getOrderAttbs();
		for(int i = 0; orderAttbs != null && i<orderAttbs.length ; ++i){
			this.saveOrderAttb(orderAttbs[i], pOrderId, pSrvDtl.getDtlId(), pUser);
		}
	}
	
	
	
	private void updateServiceDetailRelatedAction(ServiceDetailDTO pSrvDtl) {
		
		if (pSrvDtl.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		this.setObjectAction(pSrvDtl.getAccounts(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSrvDtl.getRemarks(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSrvDtl.getAppointmentDtl(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSrvDtl.getThirdPartyDtl(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSrvDtl.getRecontract(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSrvDtl.getPipb(), ObjectActionBaseDTO.ACTION_DELETE);
	}
	
	private void saveAcctSrvAssgn(AccountServiceAssignLtsDTO pAcct, String pOrderId, String pDtlId, String pUser) {
		
		if (pAcct == null) {
			return;
		}
		ServiceActionBase accountServiceAssignLtsService = SpringApplicationContext.getBean("accountServiceAssignLtsService");
		accountServiceAssignLtsService.doSave(pAcct, pUser, pOrderId, pDtlId);
	}
	
	private void saveBillingAddress(SbOrderDTO pSbOrder, String pUser) {
		
		AccountDetailLtsDTO[] accounts = pSbOrder.getAccounts();
		List<BillingAddressLtsDTO> billAddrList = new ArrayList<BillingAddressLtsDTO>();
		
		for (int i=0; accounts!=null && i<accounts.length; ++i) {
			if (accounts[i].getBillingAddress() != null) {
				accounts[i].getBillingAddress().setAcctNo(accounts[i].getAcctNo());
				billAddrList.add(accounts[i].getBillingAddress());
			}
		}
//		ServiceDetailDTO[] srvDtls = pSbOrder.getSrvDtls();
//		
//		for (int i=0; srvDtls!=null && i<srvDtls.length; ++i) {
//			if (srvDtls[i].getRecontract() != null) {
//				billAddrMap.put(srvDtls[i].getRecontract().getAcctNum(), srvDtls[i].getRecontract().getBillingAddress());
//			}
//		}
		
		BillingAddressLtsDTO[] billAddresss = (BillingAddressLtsDTO[])this.sortSubmitSeq(billAddrList.toArray(new BillingAddressLtsDTO[billAddrList.size()]));
		
		for (BillingAddressLtsDTO billAddress : billAddresss) {
			ServiceActionBase billingAddressLtsService = SpringApplicationContext.getBean("billingAddressLtsService");
			billingAddressLtsService.doSave(billAddress, pUser, pSbOrder.getOrderId(), billAddress.getAcctNo());
		}
	}

	private void saveAccount(AccountDetailLtsDTO pAccount, String pOrderId, String pUser) {

		if (pAccount == null) {
			return;
		}
		String custNum = pAccount.getCustomer().getCustNo();
		PaymentMethodDetailLtsDTO[] paymentMethods = (PaymentMethodDetailLtsDTO[])this.sortSubmitSeq(pAccount.getPaymethods());
		ServiceActionBase accountLtsService = SpringApplicationContext.getBean("accountLtsService");
		accountLtsService.doSave(pAccount, pUser, pOrderId, custNum);
		this.updateAccountRelatedAction(pAccount);
//		this.saveBillingAddress(pAccount.getBillingAddress(), pOrderId, pAccount.getAcctNo());
		
		for (int i = 0; paymentMethods != null && i < paymentMethods.length; ++i) {
			savePaymentMethod(paymentMethods[i], pOrderId, pAccount.getAcctNo(), custNum, pUser);
		}
	}
	
	private void updateAccountRelatedAction(AccountDetailLtsDTO pAccount) {
		
		if (pAccount.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		this.setObjectAction(pAccount.getBillingAddress(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pAccount.getPaymethods(), ObjectActionBaseDTO.ACTION_DELETE);
	}
	
	private void saveRecontract(RecontractLtsDTO pRecontract, String pOrderId, String pDtlId, String pUser) {
		
		if (pRecontract == null) {
			return;
		}
		ServiceActionBase recontractLtsService = SpringApplicationContext.getBean("recontractLtsService");
		recontractLtsService.doSave(pRecontract, pUser, pOrderId, pDtlId);
//		this.updateRecontactRelatedAction(pRecontract);
//		this.saveBillingAddress(pRecontract.getBillingAddress(), pOrderId, pRecontract.getAcctNum());
		
		PaymentMethodDetailLtsDTO[] payments = pRecontract.getPaymethods();
		
		for (int i=0; payments!=null && i<payments.length; ++i) {
			this.savePaymentMethod(payments[i], pOrderId, pRecontract.getAcctNum(), pRecontract.getCustNum(), pUser);	
		}
	}
	
//	private void updateRecontactRelatedAction(RecontractLtsDTO pRecontract) {
//		
//		if (pRecontract.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
//			return;
//		}
//		this.setObjectAction(pRecontract.getPaymethods(), ObjectActionBaseDTO.ACTION_DELETE);
//		this.setObjectAction(pRecontract.getBillingAddress(), ObjectActionBaseDTO.ACTION_DELETE);
//	}

	private void savePaymentMethod(PaymentMethodDetailLtsDTO pPayMethod,
			String pOrderId, String pAcctNum, String pCustNum, String pUser) {

		if (pPayMethod == null) {
			return;
		}
		ServiceActionBase paymentLtsService = SpringApplicationContext.getBean("paymentLtsService");
		paymentLtsService.doSave(pPayMethod, pUser, pOrderId, pAcctNum, pCustNum);
	}

	private void setAddressRelatedAction(AddressDetailLtsDTO pAddress) {
		if (pAddress.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		
		this.setObjectAction(pAddress.getAddrInventory(), ObjectActionBaseDTO.ACTION_DELETE);
	}
	
	
	private void saveAllOrdDocAssgn(AllOrdDocAssgnLtsDTO allOrdDocAssgn, String pOrderId, String pUser) {
		
		if (allOrdDocAssgn == null) {
			return;
		}
		ServiceActionBase allOrdDocAssgnLtsService = SpringApplicationContext.getBean("allOrdDocAssgnLtsService");
		allOrdDocAssgnLtsService.doSave(allOrdDocAssgn, pUser, pOrderId);
	}
	
	private void saveAddressInventory(AddressInventoryDetailLtsDTO pAddrInv, String pOrderId, String pUser) {
		
		if (pAddrInv == null) {
			return;
		}
		ServiceActionBase addressInventoryLtsService = SpringApplicationContext.getBean("addressInventoryLtsService");
		addressInventoryLtsService.doSave(pAddrInv, pUser, pOrderId);
	}
	
	private void setCustomerRelatedAction(CustomerDetailLtsDTO pCustomer) {
		if (pCustomer.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		
		this.setObjectAction(pCustomer.getCustOptOutInfo(), ObjectActionBaseDTO.ACTION_DELETE);
	}

	private void saveCustOptOutInfo(CustOptOutInfoLtsDTO pCustOptOutInfo, String pOrderId, String custNo, String pUser) {
		
		if (pCustOptOutInfo == null) {
			return;
		}
		ServiceActionBase custOptOutInfoLtsService = SpringApplicationContext.getBean("custOptOutInfoLtsService");
		custOptOutInfoLtsService.doSave(pCustOptOutInfo, pUser, pOrderId, custNo);
	}
	
	private void saveRemark(RemarkDetailLtsDTO pRemark, String pOrderId, String pDtlId, String pUser) {

		if (pRemark == null) {
			return;
		}
		ServiceActionBase remarkLtsService = SpringApplicationContext.getBean("remarkLtsService");
		remarkLtsService.doSave(pRemark, pUser, pOrderId, pDtlId);
	}
	private void saveSrvCallPlanDetail(ServiceCallPlanDetailLtsDTO pCallPlan, String pOrderId, String pDtlId, String pUser) {

		if (pCallPlan == null) {
			return;
		}
		ServiceActionBase serviceCallPlanDetailLtsService = SpringApplicationContext.getBean("serviceCallPlanDetailLtsService");
		serviceCallPlanDetailLtsService.doSave(pCallPlan, pUser, pOrderId, pDtlId);
	}
	private void saveThirdPartyDetail(ThirdPartyDetailLtsDTO pThirdPartyDtl, String pOrderId, String pDtlId, String pUser) {

		if (pThirdPartyDtl == null) {
			return;
		}
		ServiceActionBase thirdPartyApplnService = SpringApplicationContext.getBean("thirdPartyApplnService");
		thirdPartyApplnService.doSave(pThirdPartyDtl, pUser, pOrderId, pDtlId);
	}

	private void saveServiceDetailLts(ServiceDetailLtsDTO pSrvDtlLts, String pOrderId, String pUser) {

		if (pSrvDtlLts == null) {
			return;
		}
		ServiceActionBase serviceDetailLtsService = SpringApplicationContext.getBean("serviceDetailLtsService");
		serviceDetailLtsService.doSave(pSrvDtlLts, pUser, pOrderId);
		this.updateServiceDetailLtsRelatedAction(pSrvDtlLts);
		ItemDetailLtsDTO[] items = (ItemDetailLtsDTO[])this.sortSubmitSeq(pSrvDtlLts.getItemDtls());

		for (int i = 0; items != null && i < items.length; ++i) {
			this.saveItem(items[i], pOrderId, pSrvDtlLts.getDtlId(), pUser);
		}
		
	}
	
	private void updateServiceDetailLtsRelatedAction(ServiceDetailLtsDTO pSrvDtlLts) {
		
		if (pSrvDtlLts.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		this.setObjectAction(pSrvDtlLts.getItemDtls(), ObjectActionBaseDTO.ACTION_DELETE);
	}

	private void saveServiceDetailOtherLts(ServiceDetailOtherLtsDTO pSrvDtlOther, String pOrderId, String pUser) {
		
		if (pSrvDtlOther == null) {
			return;
		}
		ServiceActionBase serviceDetailOtherService = SpringApplicationContext.getBean("serviceDetailOtherService");
		serviceDetailOtherService.doSave(pSrvDtlOther, pUser, pOrderId);
		this.updateServiceDetailOtherAction(pSrvDtlOther);
		ChannelDetailLtsDTO[] channels = pSrvDtlOther.getChannels();

		for (int i = 0; channels != null && i < channels.length; ++i) {
			this.saveChannels(channels[i], pOrderId, pSrvDtlOther.getDtlId(), pUser);
		}
		ImsOfferDetailDTO[] offers = pSrvDtlOther.getOfferDtls();
		
		for (int i=0; offers!=null && i<offers.length; ++i) {
			this.saveImsOfferDetails(offers[i], pOrderId, pSrvDtlOther.getDtlId(), pUser);
		}
		this.saveNowtvDetail(pSrvDtlOther.getNowtvDetail(), pOrderId, pSrvDtlOther.getDtlId(), pUser);
		
		ImsL2JobDTO[] imsL2Jobs = pSrvDtlOther.getImsL2Jobs();
		for (int i=0; imsL2Jobs!=null && i<imsL2Jobs.length; ++i) {
			this.saveImsL2Job(imsL2Jobs[i], pOrderId, pSrvDtlOther.getDtlId(),  pUser);
		}
		
	}
	
	private void updateServiceDetailOtherAction(ServiceDetailOtherLtsDTO pSrvDtlOther) {
		
		if (pSrvDtlOther.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		this.setObjectAction(pSrvDtlOther.getNowtvDetail(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSrvDtlOther.getChannels(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSrvDtlOther.getOfferDtls(), ObjectActionBaseDTO.ACTION_DELETE);
		this.setObjectAction(pSrvDtlOther.getImsL2Jobs(), ObjectActionBaseDTO.ACTION_DELETE);
	}
	
	private void saveImsL2Job(ImsL2JobDTO imsL2Job, String orderId, String dtlId, String user) {
		
		if (imsL2Job == null) {
			return;
		}
		
		ServiceActionBase imsL2JobService = SpringApplicationContext.getBean("imsL2JobService");
		imsL2JobService.doSave(imsL2Job, user, orderId, dtlId);
	}
	

	private void saveOrderAttb(OrderAttbDTO orderAttb, String orderId, String dtlId, String user) {
		
		if (orderAttb == null) {
			return;
		}
		
		ServiceActionBase orderAttbService = SpringApplicationContext.getBean("orderAttbService");
		orderAttbService.doSave(orderAttb, user, orderId, dtlId);
	}
	
	private void saveChannels(ChannelDetailLtsDTO pChannel, String pOrderId, String pDtlId, String pUser) {

		if (pChannel == null) {
			return;
		}
		ServiceActionBase channelLtsService = SpringApplicationContext.getBean("channelLtsService");
		channelLtsService.doSave(pChannel, pUser, pOrderId, pDtlId);
		this.updateChannelAction(pChannel);
		ChannelAttbLtsDTO[] channelAttbs = pChannel.getChannelAttbs();
		
		for (int i=0; channelAttbs!=null && i<channelAttbs.length; ++i) {
			this.saveChannelAttb(channelAttbs[i], pOrderId, pDtlId, pChannel.getChannelId(), pUser);
		}
	}
	
	private void updateChannelAction(ChannelDetailLtsDTO pChannel) {
		
		if (pChannel.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		this.setObjectAction(pChannel.getChannelAttbs(), ObjectActionBaseDTO.ACTION_DELETE);
	}
	
	private void saveChannelAttb(ChannelAttbLtsDTO pChannelAttb, String pOrderId, String pDtlId, String pChannelId, String pUser) {
		
		if (pChannelAttb == null) {
			return;
		}
		ServiceActionBase channelAttbLtsService = SpringApplicationContext.getBean("channelAttbLtsService");
		channelAttbLtsService.doSave(pChannelAttb, pUser, pOrderId, pDtlId, pChannelId);
	}

	private void saveNowtvDetail(NowtvDetailLtsDTO pNowtvDtl, String pOrderId, String pDtlId, String pUser) {

		if (pNowtvDtl == null) {
			return;
		}
		ServiceActionBase nowtvDetailLtsService = SpringApplicationContext.getBean("nowtvDetailLtsService");
		nowtvDetailLtsService.doSave(pNowtvDtl, pUser, pOrderId, pDtlId);
	}

	private void saveItem(ItemDetailLtsDTO pItem, String pOrderId, String pDtlId, String pUser) {

		if (pItem == null) {
			return;
		}
		ServiceActionBase itemLtsService = SpringApplicationContext.getBean("itemLtsService");
		itemLtsService.doSave(pItem, pUser, pOrderId, pDtlId);
		this.updateItemRelatedAction(pItem);
		ItemAttributeDetailLtsDTO[] itemAttbs = (ItemAttributeDetailLtsDTO[])this.sortSubmitSeq(pItem.getItemAttbs());

		for (int i = 0; itemAttbs != null && i < itemAttbs.length; ++i) {
			this.saveItemAttb(itemAttbs[i], pOrderId, pDtlId, pItem.getSrvItemSeq(), pUser);
		}
	}
	
	private void updateItemRelatedAction(ItemDetailLtsDTO pItem) {
		
		if (pItem.getObjectAction() != ObjectActionBaseDTO.ACTION_DELETE) {
			return;
		}
		this.setObjectAction(pItem.getItemAttbs(), ObjectActionBaseDTO.ACTION_DELETE);
	}

	private void saveItemAttb(ItemAttributeDetailLtsDTO pItemAttb,
			String pOrderId, String pDtlId, String pItemSeq, String pUser) {

		if (pItemAttb == null) {
			return;
		}
		ServiceActionBase itemAttributeLtsService = SpringApplicationContext.getBean("itemAttributeLtsService");
		itemAttributeLtsService.doSave(pItemAttb, pUser, pOrderId, pDtlId, pItemSeq);
	}
	
	private void saveImsOfferDetails(ImsOfferDetailDTO pOfferDtl, String pOrderId, String pDtlId, String pUser) {
		
		if (pOfferDtl == null) {
			return;
		}
		ServiceActionBase imsOfferDetailService = SpringApplicationContext.getBean("imsOfferDetailService");
		imsOfferDetailService.doSave(pOfferDtl, pUser, pOrderId, pDtlId);
	}
	
	@Transactional
	public void saveAppointmentDetail(AppointmentDetailLtsDTO pAppntDtl,
			String pOrderId, String pDtlId, String pUser) {

		if (pAppntDtl == null) {
			return;
		}
		ServiceActionBase appointmentLtsService = SpringApplicationContext.getBean("appointmentLtsService");
		appointmentLtsService.doSave(pAppntDtl, pUser, pOrderId, pDtlId);
	}
	
	private void saveStaffInfo(StaffInfoLtsDTO pStaffInfo, String pOrderId, String pUser) {

		if (pStaffInfo == null) {
			return;
		}
		ServiceActionBase staffInfoLtsService = SpringApplicationContext.getBean("staffInfoLtsService");
		staffInfoLtsService.doSave(pStaffInfo, pUser, pOrderId);
	}
	
	private void saveContact(ContactLtsDTO pContact, String pOrderId, String pUser) {
		
		if (pContact == null) {
			return;
		}
		ServiceActionBase contactLtsService = SpringApplicationContext.getBean("contactLtsService");
		contactLtsService.doSave(pContact, pUser, pOrderId);
	}

	private void saveSrv0060Detail(Service0060DetailLtsDTO[] pSrv0060Dtls, String pOrderId, String pUser) {
		
		if (ArrayUtils.isEmpty(pSrv0060Dtls)) {
			return; 
		}
		
		for (Service0060DetailLtsDTO srv0060Dtl : pSrv0060Dtls) {
			ServiceActionBase service0060DetailLtsService = SpringApplicationContext.getBean("service0060DetailLtsService");
			service0060DetailLtsService.doSave(srv0060Dtl, pUser, pOrderId);
		}
	}
	
    private void saveResDn(ResDnLtsDTO[] pResDn, String pOrderId, String pUser) {
		
		if (ArrayUtils.isEmpty(pResDn)) {
			return;
		}
		
		this.setObjectAction(pResDn, ObjectActionBaseDTO.ACTION_DELETE);
		ServiceActionBase resDnLtsService = SpringApplicationContext.getBean("resDnLtsService");
		resDnLtsService.doSave(pResDn[0], pUser, pOrderId);
		//System.out.println("pResDn[0]: " + (pResDn[0]==null?null:pResDn[0].getSrvNum()));
		this.setObjectAction(pResDn, ObjectActionBaseDTO.ACTION_ADD);

		for (ResDnLtsDTO resDnLts : pResDn) {
			//System.out.println("resDnLts: " + (resDnLts==null?null:resDnLts.getSrvNum()));
			resDnLtsService = SpringApplicationContext.getBean("resDnLtsService");
			resDnLtsService.doSave(resDnLts, pUser, pOrderId);
		}
		
	}
	
	private void setObjectAction(ObjectActionBaseDTO[] pDtos, int pAction) {
		
		for (int i=0; pDtos!=null && i<pDtos.length; ++i) {
			this.setObjectAction(pDtos[i], pAction);
		}
	}
	
	private void setObjectAction(ObjectActionBaseDTO pDto, int pAction) {
		
		if (pDto != null) {
			pDto.setObjectAction(pAction);	
		}
	}
	
	private ObjectActionBaseDTO[] sortSubmitSeq(ObjectActionBaseDTO[] pObjActionDTOs) {
		
		if (pObjActionDTOs == null) {
			return null;
		}
		Arrays.sort(pObjActionDTOs, new Comparator<ObjectActionBaseDTO>() {
			public int compare(ObjectActionBaseDTO o1, ObjectActionBaseDTO o2) {
				return String.valueOf(o1.getObjectAction()).compareTo(String.valueOf(o2.getObjectAction())) * -1;
			}
		});
		return pObjActionDTOs;
	}
	
	@Transactional
    public void saveAmendmentAuditLog(OrderLtsAmendDTO orderLtsAmendDTO, String pOrderId, String pDtlId, String batchSeq, String pUser) {
		
		if (orderLtsAmendDTO == null) {
			return;
		}
		ServiceActionBase orderLtsAmendService = SpringApplicationContext.getBean("orderLtsAmendService");
		orderLtsAmendService.doSave(orderLtsAmendDTO, pUser, pOrderId, pDtlId, batchSeq);
		
	}
	
	@Transactional
    public void saveAmendmentDetailAuditLog(OrderLtsAmendDetailDTO orderLtsAmendDetailDTO, String pOrderId, String pDtlId, String batchSeq, String itemSeq, String pUser) {
		
		if (orderLtsAmendDetailDTO == null) {
			return;
		}
		ServiceActionBase orderLtsAmendHistoryService = SpringApplicationContext.getBean("orderLtsAmendDetailService");
		orderLtsAmendHistoryService.doSave(orderLtsAmendDetailDTO, pUser, pOrderId, pDtlId, batchSeq, itemSeq);
		
	}
	
}
