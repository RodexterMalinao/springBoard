package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.lts.dao.order.AccountLtsDAO;
import com.bomwebportal.lts.dao.order.AccountServiceAssignLtsDAO;
import com.bomwebportal.lts.dao.order.AddressInventoryLtsDAO;
import com.bomwebportal.lts.dao.order.AddressLtsDAO;
import com.bomwebportal.lts.dao.order.AppointmentLtsDAO;
import com.bomwebportal.lts.dao.order.BillingAddressLtsDAO;
import com.bomwebportal.lts.dao.order.ChannelAttbLtsDAO;
import com.bomwebportal.lts.dao.order.ChannelLtsDAO;
import com.bomwebportal.lts.dao.order.CustOptOutInfoLtsDAO;
import com.bomwebportal.lts.dao.order.ImsL2JobDAO;
import com.bomwebportal.lts.dao.order.ImsOfferDetailDAO;
import com.bomwebportal.lts.dao.order.ItemAttributeLtsDAO;
import com.bomwebportal.lts.dao.order.ItemLtsDAO;
import com.bomwebportal.lts.dao.order.LtsDsOrderInfoDAO;
import com.bomwebportal.lts.dao.order.NowtvDetailLtsDAO;
import com.bomwebportal.lts.dao.order.OrderAttbDAO;
import com.bomwebportal.lts.dao.order.PipbLtsDAO;
import com.bomwebportal.lts.dao.order.RecontractLtsDAO;
import com.bomwebportal.lts.dao.order.RemarkLtsDAO;
import com.bomwebportal.lts.dao.order.ServiceCallPlanDetailLtsDAO;
import com.bomwebportal.lts.dao.order.ServiceDetailDAO;
import com.bomwebportal.lts.dao.order.ThirdPartyApplnDAO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AccountServiceAssignLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AddressInventoryDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocAssgnLtsDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BillingAddressLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelAttbLtsDTO;
import com.bomwebportal.lts.dto.order.ChannelDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ContactLtsDTO;
import com.bomwebportal.lts.dto.order.CustOptOutInfoLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerIguardRegDTO;
import com.bomwebportal.lts.dto.order.ImsL2JobDTO;
import com.bomwebportal.lts.dto.order.ImsOfferDetailDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.LtsDsOrderInfoDTO;
import com.bomwebportal.lts.dto.order.NowtvDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderAttbDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
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
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.service.ServiceActionBase;
import com.pccw.util.db.DaoBase;
import com.pccw.util.spring.SpringApplicationContext;
import com.bomwebportal.lts.dto.order.PrepayLtsDTO;


public class OrderRetrieveLtsServiceImpl implements OrderRetrieveLtsService {

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
//	private ServiceActionBase addressLtsService;
//	private ServiceActionBase accountLtsService;
//	private ServiceActionBase customerLtsService;
//	private ServiceActionBase paymentLtsService;
//	private ServiceActionBase addressInventoryLtsService;
//	private ServiceActionBase imsOfferDetailService;
//	private ServiceActionBase channelAttbLtsService;
//	private ServiceActionBase allOrdDocAssgnLtsService;
//	private ServiceActionBase custOptOutInfoLtsService;
//	private ServiceActionBase allOrdDocLtsService;	
//
//	private ServiceActionBase billingAddressLtsService;
//	private ServiceActionBase recontractLtsService;
//	private ServiceActionBase staffInfoLtsService;
//	private ServiceActionBase contactLtsService;
//	private ServiceActionBase service0060DetailLtsService;
//	private ServiceActionBase serviceCallPlanDetailLtsService;
//	private ServiceActionBase accountServiceAssignLtsService;
//	private ServiceActionBase resDnLtsService;
//	private ServiceActionBase pipbLtsService;
	
	private OrderStatusService orderStatusService;
	
	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	@Transactional
	public SbOrderDTO retrieveSbOrder(String pOrderId, boolean pIsEquiry) {
		return this.getSbOrder(pOrderId, pIsEquiry);
	}
	
	public AllOrdDocLtsDTO[] retrieveAllOrdDocs(String pOrderId) {
		ServiceActionBase allOrdDocLtsService = SpringApplicationContext.getBean("allOrdDocLtsService");
		DaoBase[] allOrdDocDaos = allOrdDocLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(allOrdDocDaos)) {
			return null;
		}
		AllOrdDocLtsDTO[] allOrdDocs = new AllOrdDocLtsDTO[allOrdDocDaos.length];
		
		for (int i=0; i<allOrdDocDaos.length; ++i) {
			allOrdDocs[i] = (AllOrdDocLtsDTO)allOrdDocLtsService.convertToDto(allOrdDocDaos[i]);
		}
		return allOrdDocs;
	}
	
	private SbOrderDTO getSbOrder(String pOrderId, boolean isEquiry) {
		ServiceActionBase sbOrderService = SpringApplicationContext.getBean("sbOrderService");
		DaoBase[] sbOrderDao = sbOrderService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(sbOrderDao)) {
			return null;
		}
		SbOrderDTO sbOrder = (SbOrderDTO)sbOrderService.convertToDto(sbOrderDao[0]);
		Map<String, BillingAddressLtsDTO> billAddrMap = this.getBillingAddress(pOrderId);
		this.getCustomer(sbOrder);
		this.getAccount(sbOrder, billAddrMap);
		this.getServiceDetail(sbOrder, billAddrMap, isEquiry);
		this.getAddress(sbOrder);
		this.getAllOrdDocAssgn(sbOrder);
		this.getStaffInfo(sbOrder);
		this.getContact(sbOrder);
		this.getService0060Detail(sbOrder);
		this.getResDnLts(sbOrder);
		this.getLtsDsOrderInfo(sbOrder); 
		this.getPrepayLts(sbOrder);
		this.getCustomerIguardReg(sbOrder);
		
		return sbOrder;
	}
	
	private void getPrepayLts(SbOrderDTO pSbOrder) {
		ServiceActionBase prepayLtsService = SpringApplicationContext.getBean("prepayLtsService");
		DaoBase[] prepayDaos = prepayLtsService.doRetrieve(pSbOrder.getOrderId());
		
		PrepayLtsDTO[] prepay = new PrepayLtsDTO[prepayDaos.length];
		
		if (!ArrayUtils.isEmpty(prepayDaos)) {
			for(int i=0; i<prepayDaos.length; i++){
				prepay[i]=(PrepayLtsDTO)prepayLtsService.convertToDto(prepayDaos[i]);
			}	
			pSbOrder.setPrepay(prepay);
		}
	}
	
	private void getCustomerIguardReg(SbOrderDTO pSbOrder) {
		ServiceActionBase customerIguardRegService = SpringApplicationContext.getBean("customerIguardRegService");
		DaoBase[] customerIguardRegDaos = customerIguardRegService.doRetrieve(pSbOrder.getOrderId());
		
		if (ArrayUtils.isEmpty(customerIguardRegDaos)) {
			return;
		}
		
		CustomerIguardRegDTO custIguardRegDTO = (CustomerIguardRegDTO) customerIguardRegService.convertToDto(customerIguardRegDaos[0]);
		pSbOrder.setCustIguardReg(custIguardRegDTO);
		
	}
	
	private void getService0060Detail(SbOrderDTO pSbOrder) {
		String orderId = pSbOrder.getOrderId();
		ServiceActionBase service0060DetailLtsService = SpringApplicationContext.getBean("service0060DetailLtsService");
		DaoBase[] srv0060DtlLtsDaos = service0060DetailLtsService.doRetrieve(orderId);
		
		if (ArrayUtils.isEmpty(srv0060DtlLtsDaos)) {
			return;
		}
		
		Service0060DetailLtsDTO[] srv0060Dtls = new Service0060DetailLtsDTO[srv0060DtlLtsDaos.length];
		
		for (int i=0; i<srv0060DtlLtsDaos.length; ++i) {
			srv0060Dtls[i] = (Service0060DetailLtsDTO)service0060DetailLtsService.convertToDto(srv0060DtlLtsDaos[i]);
		}
		
		pSbOrder.setSrv0060Dtls(srv0060Dtls);
		
	}

	
	
	private void getAccount(SbOrderDTO pSbOrder, Map<String, BillingAddressLtsDTO> pBillAddrMap) {
		
		String orderId = pSbOrder.getOrderId();
		ServiceActionBase accountLtsService = SpringApplicationContext.getBean("accountLtsService");
		DaoBase[] acctDaos = accountLtsService.doRetrieve(orderId);
		
		if (ArrayUtils.isEmpty(acctDaos)) {
			return;
		}
		AccountDetailLtsDTO[] accounts = new AccountDetailLtsDTO[acctDaos.length];
		
		for (int i=0; i<acctDaos.length; ++i) {
			accounts[i] = (AccountDetailLtsDTO)accountLtsService.convertToDto(acctDaos[i]);
			accounts[i].setCustomer(this.getCustomerByCustNum(pSbOrder.getCustomers(), ((AccountLtsDAO)acctDaos[i]).getCustNo()));
			
			if (pBillAddrMap != null && !pBillAddrMap.isEmpty()) {
				accounts[i].setBillingAddress(pBillAddrMap.get(accounts[i].getAcctNo()));	
			}
			accounts[i].setPaymethods(this.getPaymentMethods(orderId, accounts[i].getAcctNo()));
		}	
		pSbOrder.setAccounts(accounts);
	}
	
	private PaymentMethodDetailLtsDTO[] getPaymentMethods(String pOrderId, String pAcctNum) {
		ServiceActionBase paymentLtsService = SpringApplicationContext.getBean("paymentLtsService");
		DaoBase[] paymentDaos = paymentLtsService.doRetrieve(pOrderId, pAcctNum);
		
		if (ArrayUtils.isEmpty(paymentDaos)) {
			return null;
		}
		PaymentMethodDetailLtsDTO[] paymentMethods = new PaymentMethodDetailLtsDTO[paymentDaos.length];
		
		for (int i=0; i<paymentDaos.length; ++i) {
			paymentMethods[i] = (PaymentMethodDetailLtsDTO)paymentLtsService.convertToDto(paymentDaos[i]);
		}
		return paymentMethods;
	}
	
	private void getCustomer(SbOrderDTO pSbOrder) {
		ServiceActionBase customerLtsService = SpringApplicationContext.getBean("customerLtsService");
		DaoBase[] custDaos = customerLtsService.doRetrieve(pSbOrder.getOrderId());
		
		if (ArrayUtils.isEmpty(custDaos)) {
			return;
		}
		List<CustomerDetailLtsDTO> custList = new ArrayList<CustomerDetailLtsDTO>();
		
		for (int i=0; i<custDaos.length; ++i) {
			CustomerDetailLtsDTO cust = (CustomerDetailLtsDTO)customerLtsService.convertToDto(custDaos[i]);
			custList.add(cust);
			this.getCustOptOutInfo(pSbOrder.getOrderId(), cust);
		}
		pSbOrder.setCustomers(custList.toArray(new CustomerDetailLtsDTO[0]));
	}

	private void getCustOptOutInfo(String pOrderId, CustomerDetailLtsDTO pCustomerDetail){
		
		ServiceActionBase custOptOutInfoLtsService = SpringApplicationContext.getBean("custOptOutInfoLtsService");
		CustOptOutInfoLtsDAO[] custOptOutInfoLtsDao = (CustOptOutInfoLtsDAO[])custOptOutInfoLtsService.doRetrieve(pOrderId);

		if (ArrayUtils.isEmpty(custOptOutInfoLtsDao)) {
			return;
		}
		
		pCustomerDetail.setCustOptOutInfo((CustOptOutInfoLtsDTO)custOptOutInfoLtsService.convertToDto(custOptOutInfoLtsDao[0]));
	}
	
	private void getAddress(SbOrderDTO pSbOrder) {
		
		String orderId = pSbOrder.getOrderId();
		ServiceActionBase addressLtsService = SpringApplicationContext.getBean("addressLtsService");
		AddressLtsDAO[] addressDaos = (AddressLtsDAO[])addressLtsService.doRetrieve(orderId);
		
		if (ArrayUtils.isEmpty(addressDaos)) {
			return;
		}
		AddressDetailLtsDTO address = (AddressDetailLtsDTO)addressLtsService.convertToDto(addressDaos[0]);
		this.getAddressInventory(orderId, address);
		pSbOrder.setAddress(address);		
	}
	
	private void getAddressInventory(String pOrderId, AddressDetailLtsDTO pAddress) {
		
		ServiceActionBase addressInventoryLtsService = SpringApplicationContext.getBean("addressInventoryLtsService");
		AddressInventoryLtsDAO[] addrInventoryLtsDao = (AddressInventoryLtsDAO[])addressInventoryLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(addrInventoryLtsDao)) {
			return;
		}
		pAddress.setAddrInventory((AddressInventoryDetailLtsDTO)addressInventoryLtsService.convertToDto(addrInventoryLtsDao[0]));
	}
	
	private void getAllOrdDocAssgn(SbOrderDTO pSbOrder) {
		pSbOrder.setAllOrdDocAssgns(retrieveAllOrdDocAssgn(pSbOrder.getOrderId()));
	}
	
	public AllOrdDocAssgnLtsDTO[] retrieveAllOrdDocAssgn(String orderId) {
		ServiceActionBase allOrdDocAssgnLtsService = SpringApplicationContext.getBean("allOrdDocAssgnLtsService");
		DaoBase[] allOrdDocAssgnLtsDaos = allOrdDocAssgnLtsService.doRetrieve(orderId);
		
		if (ArrayUtils.isEmpty(allOrdDocAssgnLtsDaos)) {
			return null;
		}
		List<AllOrdDocAssgnLtsDTO> ordDocAssgnList = new ArrayList<AllOrdDocAssgnLtsDTO>();
		
		for (int i=0; i<allOrdDocAssgnLtsDaos.length; ++i) {
			ordDocAssgnList.add((AllOrdDocAssgnLtsDTO)allOrdDocAssgnLtsService.convertToDto(allOrdDocAssgnLtsDaos[i]));
		}
		
		if (ordDocAssgnList.isEmpty()) {
			return null;
		}
		
		return ordDocAssgnList.toArray(new AllOrdDocAssgnLtsDTO[ordDocAssgnList.size()]);
	}
	
	private void getStaffInfo(SbOrderDTO pSbOrder) {
		ServiceActionBase staffInfoLtsService = SpringApplicationContext.getBean("staffInfoLtsService");
		DaoBase[] staffInfoDaos = staffInfoLtsService.doRetrieve(pSbOrder.getOrderId());
		
		if (!ArrayUtils.isEmpty(staffInfoDaos)) {
			pSbOrder.setStaffInfo((StaffInfoLtsDTO)staffInfoLtsService.convertToDto(staffInfoDaos[0]));
		}
	}
	
	private void getContact(SbOrderDTO pSbOrder) {
		ServiceActionBase contactLtsService = SpringApplicationContext.getBean("contactLtsService");
		DaoBase[] contactDaos = contactLtsService.doRetrieve(pSbOrder.getOrderId());
		
		if (!ArrayUtils.isEmpty(contactDaos)) {
			pSbOrder.setContact((ContactLtsDTO)contactLtsService.convertToDto(contactDaos[0]));
		}
	}
	
	private void getServiceDetail(SbOrderDTO pSbOrder, Map<String, BillingAddressLtsDTO> pBillAddrMap, boolean isEquiry) {
		
		String orderId = pSbOrder.getOrderId();
		ServiceActionBase serviceDetailLtsService = SpringApplicationContext.getBean("serviceDetailLtsService");
		DaoBase[] srvDtlLtsDaos = serviceDetailLtsService.doRetrieve(orderId);
		ServiceActionBase serviceDetailOtherService = SpringApplicationContext.getBean("serviceDetailOtherService");
		DaoBase[] srvDtlOtherDaos = serviceDetailOtherService.doRetrieve(orderId);
		
		Map<String, ServiceDetailDTO> srvDtlMap = new HashMap<String, ServiceDetailDTO>();
		
		if (!ArrayUtils.isEmpty(srvDtlLtsDaos)) {
			ServiceDetailLtsDTO[] srvLtsDtls = new ServiceDetailLtsDTO[srvDtlLtsDaos.length];
			
			for (int i=0; i<srvDtlLtsDaos.length; ++i) {
				srvLtsDtls[i] = (ServiceDetailLtsDTO)serviceDetailLtsService.convertToDto(srvDtlLtsDaos[i]);
				srvDtlMap.put(srvLtsDtls[i].getDtlId(), srvLtsDtls[i]);
			}
		}
		if (!ArrayUtils.isEmpty(srvDtlOtherDaos)) {
			ServiceDetailOtherLtsDTO[] srvDtlOthers = new ServiceDetailOtherLtsDTO[srvDtlOtherDaos.length];
			
			for (int i=0; i<srvDtlOtherDaos.length; ++i) {
				srvDtlOthers[i] = (ServiceDetailOtherLtsDTO)serviceDetailOtherService.convertToDto(srvDtlOtherDaos[i]);
				srvDtlMap.put(srvDtlOthers[i].getDtlId(), srvDtlOthers[i]);
			}
		}
		ServiceActionBase serviceDetailService = SpringApplicationContext.getBean("serviceDetailService");
		ServiceDetailDAO[] srvDtlDaos = (ServiceDetailDAO[])serviceDetailService.doRetrieve(orderId);
		ServiceDetailDTO srvDtl = null;
		
		if (!ArrayUtils.isEmpty(srvDtlDaos)) {
			for (int i=0; i<srvDtlDaos.length; ++i) {
				
				if (!srvDtlMap.containsKey(srvDtlDaos[i].getDtlId())) {
					continue;
				}
				srvDtl = srvDtlMap.get(srvDtlDaos[i].getDtlId());
				((ServiceDetailServiceImpl)serviceDetailService).convertToDto(srvDtl, srvDtlDaos[i]);
//				srvDtl.setAccount(this.getAccoutByAccountNum(pSbOrder.getAccounts(), srvDtlDaos[i].getAcctNo()));
				srvDtl.setAccounts(this.getAcctSrvAssgn(pSbOrder, srvDtl.getDtlId()));
			}
		}
		this.getStatus(orderId, srvDtlMap, isEquiry);
		this.getAppointment(orderId, srvDtlMap);
		this.getRemarks(orderId, srvDtlMap);
		this.getThirdPartyDetail(orderId, srvDtlMap);
		this.getItems(orderId, srvDtlMap);
		this.getChannels(orderId, srvDtlMap);
		this.getNowtvDetails(orderId, srvDtlMap);
		this.getRecontract(orderId, srvDtlMap, pBillAddrMap);
		this.getImsOfferDetails(orderId, srvDtlMap);
		this.getServiceCallPlanDetail(orderId, srvDtlMap);
		this.getPipbLts(orderId, srvDtlMap);
		this.getImsL2Job(orderId, srvDtlMap);
		this.getOrderAttb(orderId, srvDtlMap);
		pSbOrder.setSrvDtls(srvDtlMap.values().toArray(new ServiceDetailDTO[srvDtlMap.size()]));
	}

	private AccountDetailLtsDTO getAccoutByAccountNum(AccountDetailLtsDTO[] pAccounts, String pAcctNum) {
		
		for (int i=0; pAccounts!=null && i<pAccounts.length; ++i) {
			if (StringUtils.equals(pAcctNum, pAccounts[i].getAcctNo())) {
				return pAccounts[i];
			}
		}
		return null;
	}
	
	private AccountServiceAssignLtsDTO[] getAcctSrvAssgn(SbOrderDTO pSbOrder, String pDtlId) {
		ServiceActionBase accountServiceAssignLtsService = SpringApplicationContext.getBean("accountServiceAssignLtsService");
		AccountServiceAssignLtsDAO[] acctSrvDaos = (AccountServiceAssignLtsDAO[])accountServiceAssignLtsService.doRetrieve(pSbOrder.getOrderId(), pDtlId);
		
		if (ArrayUtils.isEmpty(acctSrvDaos)) {
			return null;
		}
		AccountServiceAssignLtsDTO[] accts = new AccountServiceAssignLtsDTO[acctSrvDaos.length];
		
		for (int i=0; i<acctSrvDaos.length; ++i) {
			accts[i] = (AccountServiceAssignLtsDTO)accountServiceAssignLtsService.convertToDto(acctSrvDaos[i]);
			accts[i].setAccount(this.getAccoutByAccountNum(pSbOrder.getAccounts(), acctSrvDaos[i].getAcctNo()));
		}
		return accts;
	}
	
	
	private CustomerDetailLtsDTO getCustomerByCustNum(CustomerDetailLtsDTO[] pCustomers, String pCustNum) {
		
		for (int i=0; pCustomers!=null && i<pCustomers.length; ++i) {
			if (StringUtils.equals(pCustNum, pCustomers[i].getCustNo())) {
				return pCustomers[i];
			}
		}
		return null;
	}
	
	private void getRecontract(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap, Map<String, BillingAddressLtsDTO> pBillAddrMap) {
		ServiceActionBase recontractLtsService = SpringApplicationContext.getBean("recontractLtsService");
		RecontractLtsDAO[] recontractLtsDaos = (RecontractLtsDAO[])recontractLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(recontractLtsDaos) || !pSrvDtlMap.containsKey(recontractLtsDaos[0].getDtlId())) {
			return;
		}
		RecontractLtsDTO recontract = (RecontractLtsDTO) recontractLtsService.convertToDto(recontractLtsDaos[0]);
		recontract.setBillingAddress(pBillAddrMap.get(recontract.getAcctNum()));
		recontract.setPaymethods(this.getPaymentMethods(pOrderId, recontract.getAcctNum()));
		pSrvDtlMap.get(recontractLtsDaos[0].getDtlId()).setRecontract(recontract);
	}
	
	private void getStatus(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap, boolean isEquiry) {
		
		OrderStatusDTO[] status = this.orderStatusService.getStatus(pOrderId);
		ServiceDetailDTO srv = null;
		
		for (int i=0; status!=null && i<status.length; ++i) {
			if (StringUtils.equals(LtsBackendConstant.ORDER_STATUS_CANCELLED, status[i].getSbStatus()) && !isEquiry) {
				pSrvDtlMap.remove(status[i].getDtlId());
			} else {
				srv = pSrvDtlMap.get(status[i].getDtlId());
				srv.setSbStatus(status[i].getSbStatus());
				srv.setSbReasonCd(status[i].getReasonCd());
				srv.setWorkQueueType(status[i].getWqType());
				srv.setStatusDate(status[i].getStatusDate());
			}
		}
	}
	
	private void getChannels(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
		ServiceActionBase channelLtsService = SpringApplicationContext.getBean("channelLtsService");
		ChannelLtsDAO[] channelDaos = (ChannelLtsDAO[])channelLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(channelDaos)) {
			return;
		}
		ChannelDetailLtsDTO channel = null;
		ServiceDetailDTO srvDtl = null;
		Map<String, List<ChannelDetailLtsDTO>> channelMap = new HashMap<String, List<ChannelDetailLtsDTO>>();
		List<ChannelDetailLtsDTO> channelList = null;
		
		for (int i=0; i<channelDaos.length; ++i) {
			channel = (ChannelDetailLtsDTO)channelLtsService.convertToDto(channelDaos[i]);
			
			if (channelMap.containsKey(channelDaos[i].getDtlId())) {
				channelList = channelMap.get(channelDaos[i].getDtlId());
			} else {
				channelList = new ArrayList<ChannelDetailLtsDTO>();
				channelMap.put(channelDaos[i].getDtlId(), channelList);
			}
			channelList.add(channel);
		}
		String[] dtlIds = (String[])channelMap.keySet().toArray(new String[channelMap.size()]);
		
		for (int i=0; dtlIds!=null && i<dtlIds.length; ++i) {
			if (!pSrvDtlMap.containsKey(dtlIds[i])) {
				continue;
			}
			srvDtl = pSrvDtlMap.get(dtlIds[i]);
			
			if (srvDtl != null) {
				((ServiceDetailOtherLtsDTO)srvDtl).setChannels((ChannelDetailLtsDTO[])channelMap.get(dtlIds[i]).toArray(new ChannelDetailLtsDTO[0]));
			}
		}
		this.getChannelAttributes(pOrderId, channelMap);
	}
	
	private void getChannelAttributes(String pOrderId, Map<String, List<ChannelDetailLtsDTO>> pChannelMap) {
		ServiceActionBase channelAttbLtsService = SpringApplicationContext.getBean("channelAttbLtsService");
		ChannelAttbLtsDAO[] channelAttbDaos = (ChannelAttbLtsDAO[])channelAttbLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(channelAttbDaos)) {
			return;
		}
		ChannelAttbLtsDTO channelAttb = null;
		List<ChannelDetailLtsDTO> channelList = null;

		for (int i=0; i<channelAttbDaos.length; ++i) {		
			if (!pChannelMap.containsKey(channelAttbDaos[i].getDtlId())) {
				continue;
			}
			channelAttb = (ChannelAttbLtsDTO)channelAttbLtsService.convertToDto(channelAttbDaos[i]);
			channelList = pChannelMap.get(channelAttbDaos[i].getDtlId());
			
			for (int j=0; j<channelList.size(); ++j) {
				if (StringUtils.equals(channelAttbDaos[i].getChannelId(), channelList.get(j).getChannelId())) {
					channelList.get(j).addChannelAttb(channelAttb);
				}
			}
		}
	}
		
	private Map<String, BillingAddressLtsDTO> getBillingAddress(String pOrderId) {
		ServiceActionBase billingAddressLtsService = SpringApplicationContext.getBean("billingAddressLtsService");
		BillingAddressLtsDAO[] billingAddressLtsDaos = (BillingAddressLtsDAO[])billingAddressLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(billingAddressLtsDaos)) {
			return null;
		}
		Map<String, BillingAddressLtsDTO> billAddrMap = new HashMap<String, BillingAddressLtsDTO>();
		
		for (int i=0; i<billingAddressLtsDaos.length; ++i) {
			billAddrMap.put(billingAddressLtsDaos[i].getAcctNo(), (BillingAddressLtsDTO)billingAddressLtsService.convertToDto(billingAddressLtsDaos[i]));
		}
		return billAddrMap;
	}
	
	private void getNowtvDetails(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
		ServiceActionBase nowtvDetailLtsService = SpringApplicationContext.getBean("nowtvDetailLtsService");
		NowtvDetailLtsDAO[] nowtvDtlDaos = (NowtvDetailLtsDAO[])nowtvDetailLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(nowtvDtlDaos)) {
			return;
		}
		NowtvDetailLtsDTO nowtvDtl = null;
		ServiceDetailDTO srvDtl = null;
		
		for (int i=0; i<nowtvDtlDaos.length; ++i) {
			if (!pSrvDtlMap.containsKey(nowtvDtlDaos[i].getDtlId())) {
				continue;
			}
			nowtvDtl = (NowtvDetailLtsDTO)nowtvDetailLtsService.convertToDto(nowtvDtlDaos[i]);
			srvDtl = pSrvDtlMap.get(nowtvDtlDaos[i].getDtlId());
			((ServiceDetailOtherLtsDTO)srvDtl).setNowtvDetail(nowtvDtl);
		}
	}
	
	private void getImsOfferDetails(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
		ServiceActionBase imsOfferDetailService = SpringApplicationContext.getBean("imsOfferDetailService");
		ImsOfferDetailDAO[] offerDtlDaos = (ImsOfferDetailDAO[])imsOfferDetailService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(offerDtlDaos)) {
			return;
		}
		ImsOfferDetailDTO offerDtl = null;
		ServiceDetailDTO srvDtl = null;
		Map<String, List<ImsOfferDetailDTO>> offerMap = new HashMap<String, List<ImsOfferDetailDTO>>();
		List<ImsOfferDetailDTO> offerList = null;
		
		for (int i=0; i<offerDtlDaos.length; ++i) {
			offerDtl = (ImsOfferDetailDTO)imsOfferDetailService.convertToDto(offerDtlDaos[i]);
			
			if (offerMap.containsKey(offerDtlDaos[i].getDtlId())) {
				offerList = offerMap.get(offerDtlDaos[i].getDtlId());
			} else {
				offerList = new ArrayList<ImsOfferDetailDTO>();
				offerMap.put(offerDtlDaos[i].getDtlId(), offerList);
			}
			offerList.add(offerDtl);
		}
		String[] dtlIds = (String[])offerMap.keySet().toArray(new String[0]);
		
		for (int i=0; dtlIds!=null && i<dtlIds.length; ++i) {
			if (!pSrvDtlMap.containsKey(dtlIds[i])) {
				continue;
			}
			srvDtl = pSrvDtlMap.get(dtlIds[i]);
			
			if (srvDtl != null) {
				((ServiceDetailOtherLtsDTO)srvDtl).setOfferDtls((ImsOfferDetailDTO[])offerMap.get(dtlIds[i]).toArray(new ImsOfferDetailDTO[0]));
			}
		}
	}
	
	private void getAppointment(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
		ServiceActionBase appointmentLtsService = SpringApplicationContext.getBean("appointmentLtsService");
		AppointmentLtsDAO[] appointDaos = (AppointmentLtsDAO[])appointmentLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(appointDaos)) {
			return;
		}
		AppointmentDetailLtsDTO appointment = null;
		ServiceDetailDTO srvDtl = null;
		
		for (int i=0; i<appointDaos.length; ++i) {
			appointment = (AppointmentDetailLtsDTO)appointmentLtsService.convertToDto(appointDaos[i]);
			
			if (!pSrvDtlMap.containsKey(appointDaos[i].getDtlId())) {
				continue;
			}
			srvDtl = pSrvDtlMap.get(appointDaos[i].getDtlId());
			srvDtl.setAppointmentDtl(appointment);
		}
	}
	
	private void getThirdPartyDetail(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
		
		ServiceActionBase thirdPartyApplnService = SpringApplicationContext.getBean("thirdPartyApplnService");
		ThirdPartyApplnDAO[] thirdPartyDtlDaos = (ThirdPartyApplnDAO[])thirdPartyApplnService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(thirdPartyDtlDaos)) {
			return;
		}
		ThirdPartyDetailLtsDTO thirdPartyDtl = null;
		ServiceDetailDTO srvDtl = null;
		
		for (int i=0; i<thirdPartyDtlDaos.length; ++i) {
			if (!pSrvDtlMap.containsKey(thirdPartyDtlDaos[i].getDtlId())) {
				continue;
			}
			thirdPartyDtl = (ThirdPartyDetailLtsDTO)thirdPartyApplnService.convertToDto(thirdPartyDtlDaos[i]);
			srvDtl = pSrvDtlMap.get(thirdPartyDtlDaos[i].getDtlId());
			srvDtl.setThirdPartyDtl(thirdPartyDtl);
		} 
	}
	
	private void getRemarks(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
		ServiceActionBase remarkLtsService = SpringApplicationContext.getBean("remarkLtsService");
		RemarkLtsDAO[] remarkDaos = (RemarkLtsDAO[])remarkLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(remarkDaos)) {
			return;
		}
		RemarkDetailLtsDTO remark = null;
		ServiceDetailDTO srvDtl = null;
		Map<String, List<RemarkDetailLtsDTO>> remarkMap = new HashMap<String, List<RemarkDetailLtsDTO>>();
		List<RemarkDetailLtsDTO> remarkList = null;
		
		for (int i=0; i<remarkDaos.length; ++i) {
			remark = (RemarkDetailLtsDTO)remarkLtsService.convertToDto(remarkDaos[i]);
			
			if (remarkMap.containsKey(remarkDaos[i].getDtlId())) {
				remarkList = remarkMap.get(remarkDaos[i].getDtlId());
			} else {
				remarkList = new ArrayList<RemarkDetailLtsDTO>();
				remarkMap.put(remarkDaos[i].getDtlId(), remarkList);
			}
			remarkList.add(remark);
		}
		String[] dtlIds = (String[])remarkMap.keySet().toArray(new String[remarkMap.size()]);
		
		for (int i=0; dtlIds!=null && i<dtlIds.length; ++i) {
			if (!pSrvDtlMap.containsKey(dtlIds[i])) {
				continue;
			}
			srvDtl = pSrvDtlMap.get(dtlIds[i]);
			
			if (srvDtl != null) {
				srvDtl.setRemarks((RemarkDetailLtsDTO[])remarkMap.get(dtlIds[i]).toArray(new RemarkDetailLtsDTO[0]));
			}
		}
	}
	
	private void getServiceCallPlanDetail(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
		ServiceActionBase serviceCallPlanDetailLtsService = SpringApplicationContext.getBean("serviceCallPlanDetailLtsService");
		ServiceCallPlanDetailLtsDAO[] srvCallPlanDtlLtsDaos = (ServiceCallPlanDetailLtsDAO[])serviceCallPlanDetailLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(srvCallPlanDtlLtsDaos)) {
			return;
		}
		ServiceCallPlanDetailLtsDTO callPlanDtl = null;
		ServiceDetailDTO srvDtl = null;
		Map<String, List<ServiceCallPlanDetailLtsDTO>> callPlanMap = new HashMap<String, List<ServiceCallPlanDetailLtsDTO>>();
		List<ServiceCallPlanDetailLtsDTO> callPlanList = null;
		
		for (int i=0; i<srvCallPlanDtlLtsDaos.length; ++i) {
			callPlanDtl = (ServiceCallPlanDetailLtsDTO)serviceCallPlanDetailLtsService.convertToDto(srvCallPlanDtlLtsDaos[i]);
			
			if (callPlanMap.containsKey(srvCallPlanDtlLtsDaos[i].getDtlId())) {
				callPlanList = callPlanMap.get(srvCallPlanDtlLtsDaos[i].getDtlId());
			} else {
				callPlanList = new ArrayList<ServiceCallPlanDetailLtsDTO>();
				callPlanMap.put(srvCallPlanDtlLtsDaos[i].getDtlId(), callPlanList);
			}
			callPlanList.add(callPlanDtl);
		}
		String[] dtlIds = (String[])callPlanMap.keySet().toArray(new String[callPlanMap.size()]);
		
		for (int i=0; dtlIds!=null && i<dtlIds.length; ++i) {
			if (!pSrvDtlMap.containsKey(dtlIds[i])) {
				continue;
			}
			srvDtl = pSrvDtlMap.get(dtlIds[i]);
			
			if (srvDtl != null) {
				srvDtl.setSrvCallPlanDtls(((ServiceCallPlanDetailLtsDTO[])callPlanMap.get(dtlIds[i]).toArray(new ServiceCallPlanDetailLtsDTO[0])));
			}
		}
	}
	
private void getItems(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
	ServiceActionBase itemLtsService = SpringApplicationContext.getBean("itemLtsService");
		ItemLtsDAO[] itemDaos = (ItemLtsDAO[])itemLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(itemDaos)) {
			return;
		}
		ItemDetailLtsDTO item = null;
		ServiceDetailDTO srvDtl = null;
		Map<String, List<ItemDetailLtsDTO>> itemMap = new HashMap<String, List<ItemDetailLtsDTO>>();
		List<ItemDetailLtsDTO> itemList = null;
		
		for (int i=0; i<itemDaos.length; ++i) {
			item = (ItemDetailLtsDTO)itemLtsService.convertToDto(itemDaos[i]);
			
			if (itemMap.containsKey(itemDaos[i].getDtlId())) {
				itemList = itemMap.get(itemDaos[i].getDtlId());
			} else {
				itemList = new ArrayList<ItemDetailLtsDTO>();
				itemMap.put(itemDaos[i].getDtlId(), itemList);
			}
			itemList.add(item);
			
		}
		
		String[] dtlIds = (String[])itemMap.keySet().toArray(new String[itemMap.size()]);
		
		this.getItemAttributes(pOrderId, itemMap);
		
		for (int i=0; dtlIds!=null && i<dtlIds.length; ++i) {
			if (!pSrvDtlMap.containsKey(dtlIds[i])) {
				continue;
			}
			
			srvDtl = pSrvDtlMap.get(dtlIds[i]);
			
			if (srvDtl != null && srvDtl instanceof ServiceDetailLtsDTO) {		
				((ServiceDetailLtsDTO)srvDtl).setItemDtls((ItemDetailLtsDTO[])itemMap.get(dtlIds[i]).toArray(new ItemDetailLtsDTO[0]));
			}
		}		
	}	
		
	private void getItemAttributes(String pOrderId, Map<String, List<ItemDetailLtsDTO>> pItemMap) {
		ServiceActionBase itemAttributeLtsService = SpringApplicationContext.getBean("itemAttributeLtsService");
		ItemAttributeLtsDAO[] itemAttbDaos = (ItemAttributeLtsDAO[])itemAttributeLtsService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(itemAttbDaos)) {
			return;
		}
		
		ItemAttributeDetailLtsDTO itemAttb = null;
		Map<String, List<ItemAttributeDetailLtsDTO>> attbMap = null;
		List<ItemAttributeDetailLtsDTO> attbList = null;		
		List<ItemDetailLtsDTO> itemList = null;		
		String[] dtlIds = (String[])pItemMap.keySet().toArray(new String[pItemMap.size()]);
		String[] itemSeq = null;
		
		for (int n = 0; dtlIds != null && n < dtlIds.length; ++n) {
			attbMap = new HashMap<String, List<ItemAttributeDetailLtsDTO>>();			
			for (int i = 0; i < itemAttbDaos.length; ++i) {
				if (StringUtils.equals(dtlIds[n], itemAttbDaos[i].getDtlId())) {
					itemAttb = (ItemAttributeDetailLtsDTO) itemAttributeLtsService.convertToDto(itemAttbDaos[i]);
					if (attbMap.containsKey(itemAttbDaos[i].getSrvItemSeq())) {
						attbList = attbMap.get(itemAttbDaos[i].getSrvItemSeq());
					} else {
						attbList = new ArrayList<ItemAttributeDetailLtsDTO>();
						attbMap.put(itemAttbDaos[i].getSrvItemSeq(), attbList);
					}
					attbList.add(itemAttb);
				}
			}

			itemSeq = (String[]) attbMap.keySet().toArray(new String[attbMap.size()]);
			itemList = pItemMap.get(dtlIds[n]);

			for (int k = 0; itemSeq != null && k < itemSeq.length; ++k) {
				for (ItemDetailLtsDTO item : itemList) {
					if (StringUtils.equals(item.getSrvItemSeq(), itemSeq[k])) {
						item.setItemAttbs(attbMap.get(itemSeq[k]).toArray(
								new ItemAttributeDetailLtsDTO[attbMap.get(itemSeq[k]).size()]));
					}
				}
			}			
		}		
	}
	
	private void getResDnLts(SbOrderDTO pSbOrder) {
		ServiceActionBase resDnLtsService = SpringApplicationContext.getBean("resDnLtsService");
		DaoBase[] resDnLtsDaos = resDnLtsService.doRetrieve(pSbOrder.getOrderId());
		
		ResDnLtsDTO[] resDns = new ResDnLtsDTO[resDnLtsDaos.length];
		
		for (int i=0; i<resDnLtsDaos.length; ++i) {
			resDns[i] = (ResDnLtsDTO)resDnLtsService.convertToDto(resDnLtsDaos[i]);
		}
		pSbOrder.setResDn(resDns);

	}
	
	private void getImsL2Job(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
		ServiceActionBase imsL2JobService = SpringApplicationContext.getBean("imsL2JobService");
		ImsL2JobDAO[] imsL2JobDaos = (ImsL2JobDAO[])imsL2JobService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(imsL2JobDaos)) {
			return;
		}
		
		if (!pSrvDtlMap.containsKey(imsL2JobDaos[0].getDtlId())) {
			return;
		}
		
		ImsL2JobDTO[] imsL2Jobs = new ImsL2JobDTO[imsL2JobDaos.length];
		
		for (int i=0; i<imsL2JobDaos.length; ++i) {
			imsL2Jobs[i] = (ImsL2JobDTO) imsL2JobService.convertToDto(imsL2JobDaos[i]);
		}

		if(pSrvDtlMap.get(imsL2JobDaos[0].getDtlId()) instanceof ServiceDetailOtherLtsDTO){
			ServiceDetailOtherLtsDTO srvDtl = (ServiceDetailOtherLtsDTO) pSrvDtlMap.get(imsL2JobDaos[0].getDtlId());
			srvDtl.setImsL2Jobs(imsL2Jobs);
		}
		
	}
	
    private void getPipbLts(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
    	ServiceActionBase pipbLtsService = SpringApplicationContext.getBean("pipbLtsService");
    	PipbLtsDAO[] pipbLtsDaos = (PipbLtsDAO[])pipbLtsService.doRetrieve(pOrderId);
	
		if (ArrayUtils.isEmpty(pipbLtsDaos)) {
			return;
		}
		
		PipbLtsDTO pipbLts = null;
		ServiceDetailDTO srvDtl = null;
		
		for (int i=0; i<pipbLtsDaos.length; ++i) {
			pipbLts = (PipbLtsDTO)pipbLtsService.convertToDto(pipbLtsDaos[i]);
			
			if (!pSrvDtlMap.containsKey(pipbLtsDaos[i].getDtlId())) {
				continue;
			}
			srvDtl = pSrvDtlMap.get(pipbLtsDaos[i].getDtlId());
			srvDtl.setPipb(pipbLts);
		}

	}

	private void getOrderAttb(String pOrderId, Map<String, ServiceDetailDTO> pSrvDtlMap) {
		ServiceActionBase orderAttbService = SpringApplicationContext.getBean("orderAttbService");
		OrderAttbDAO[] orderAttbDaos = (OrderAttbDAO[])orderAttbService.doRetrieve(pOrderId);
		
		if (ArrayUtils.isEmpty(orderAttbDaos)) {
			return;
		}
		
		if (!pSrvDtlMap.containsKey(orderAttbDaos[0].getDtlId())) {
			return;
		}
		
		OrderAttbDTO[] orderAttbJobs = new OrderAttbDTO[orderAttbDaos.length];
		
		for (int i=0; i<orderAttbDaos.length; ++i) {
			orderAttbJobs[i] = (OrderAttbDTO) orderAttbService.convertToDto(orderAttbDaos[i]);
		}

		ServiceDetailDTO srvDtl = pSrvDtlMap.get(orderAttbDaos[0].getDtlId());
		srvDtl.setOrderAttbs(orderAttbJobs);
		
	}
	
	private void getLtsDsOrderInfo(SbOrderDTO pSbOrder) {
		ServiceActionBase ltsDsOrderInfoService = SpringApplicationContext.getBean("ltsDsOrderInfoService");
    	LtsDsOrderInfoDAO[] LtsDsOrderInfoDaos = (LtsDsOrderInfoDAO[])ltsDsOrderInfoService.doRetrieve(pSbOrder.getOrderId());
	
		if (ArrayUtils.isEmpty(LtsDsOrderInfoDaos)) {
			return;
		}
		
		pSbOrder.setLtsDsOrderInfo((LtsDsOrderInfoDTO)ltsDsOrderInfoService.convertToDto(LtsDsOrderInfoDaos[0]));
	}	
}
