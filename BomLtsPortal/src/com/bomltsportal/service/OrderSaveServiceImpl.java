package com.bomltsportal.service;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dto.order.AccountDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.CustomerDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderIdDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.service.order.OrderDetailService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.SaveSbOrderLtsService;
import com.bomwebportal.lts.util.LtsSbHelper;

public class OrderSaveServiceImpl implements OrderSaveService {

	private OrderDetailService orderDetailService;
	private SaveSbOrderLtsService saveSbOrderLtsService;
	private OrderStatusService orderStatusService;
	
	public OrderDetailService getOrderDetailService() {
		return orderDetailService;
	}

	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	public SaveSbOrderLtsService getSaveSbOrderLtsService() {
		return saveSbOrderLtsService;
	}

	public void setSaveSbOrderLtsService(SaveSbOrderLtsService saveSbOrderLtsService) {
		this.saveSbOrderLtsService = saveSbOrderLtsService;
	}

	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	@Transactional
	public SbOrderDTO saveNewOrder(SbOrderDTO sbOrder) {
		initializeOrderDetail(sbOrder);
		initializeServices(sbOrder);
		initializeStatus(sbOrder);
		saveSbOrderLtsService.saveSbOrder(sbOrder, BomLtsConstant.USER_ID);	
		updateStatusAfterSuspend(sbOrder);
		return sbOrder;
	}
	
	@Transactional
	public SbOrderDTO saveExistingOrder(SbOrderDTO existingOrder, SbOrderDTO newOrder) {
		deleteSbOrder(existingOrder);
		saveSbOrderLtsService.saveSbOrder(existingOrder, BomLtsConstant.USER_ID);
		
		existingOrder.setSrvDtls(newOrder.getSrvDtls());
		existingOrder.setAccounts(newOrder.getAccounts());
		existingOrder.setCustomers(newOrder.getCustomers());
		existingOrder.setAddress(newOrder.getAddress());
		existingOrder.setEsigEmailAddr(newOrder.getEsigEmailAddr());
		existingOrder.setEsigEmailLang(newOrder.getEsigEmailLang());
		existingOrder.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		
		initializeServices(existingOrder);
		saveSbOrderLtsService.saveSbOrder(existingOrder, BomLtsConstant.USER_ID);
		updateStatusAfterSuspend(existingOrder);
		return existingOrder;
	}
	
	@Transactional
	public void saveSbOrder(SbOrderDTO sbOrder) {
		saveSbOrderLtsService.saveSbOrder(sbOrder, BomLtsConstant.USER_ID);
	}
	
	private void deleteSbOrder(SbOrderDTO sbOrder) {
		
		sbOrder.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		
		// BOMWEB_ACCT
		AccountDetailLtsDTO[] accountDetails = sbOrder.getAccounts();
		if (ArrayUtils.isNotEmpty(accountDetails)) {
			for (AccountDetailLtsDTO accountDetail : accountDetails) {
				accountDetail.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			}
		}
		
		// BOMWEB_CUST_ADDR
		AddressDetailLtsDTO addressDetail = sbOrder.getAddress();
		if (addressDetail != null) {
			addressDetail.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
		}
		
		
		// BOMWEB_CUST
		CustomerDetailLtsDTO[] customerDetails = sbOrder.getCustomers();
		if (ArrayUtils.isNotEmpty(customerDetails)) {
			for (CustomerDetailLtsDTO customerDetail : customerDetails) {
				customerDetail.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			}
		}
		
		// BOMWEB_SERVICE
		ServiceDetailDTO[] serviceDetails = sbOrder.getSrvDtls();
		if (ArrayUtils.isNotEmpty(serviceDetails)) {
			for (ServiceDetailDTO serviceDetail : serviceDetails) {
				serviceDetail.setObjectAction(ObjectActionBaseDTO.ACTION_DELETE);
			}
		}
		
	}
	
	private void initializeOrderDetail(SbOrderDTO sbOrder) {
		
		if (StringUtils.isNotEmpty(sbOrder.getOrderId())) {
			return;
		}
		ServiceDetailDTO ltsEyeService = LtsSbHelper.getLtsEyeService(sbOrder);
		OrderIdDTO orderid = this.orderDetailService.generateOrderId(BomLtsConstant.OLS_SHOP_CD, BomLtsConstant.USER_ID, 
				BomLtsConstant.ORDER_MODE_ONLINE_SALES, ltsEyeService == null ? "D" : "E");
		sbOrder.setOrderId(orderid.getOrderId());
		sbOrder.setBoc(orderid.getBoc());
	}
	
	private void initializeStatus(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvs = sbOrder.getSrvDtls();
		
		for (int i=0; srvs!=null && i<srvs.length; ++i) {
			if (StringUtils.isEmpty(srvs[i].getSbStatus())) {
				this.orderStatusService.initializeStatus(sbOrder.getOrderId(), srvs[i].getDtlId(), BomLtsConstant.USER_ID);
			}
		}
	}
	
	private void initializeServices(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		
		for (int i=0; i<srvDtls.length; ++i) {
			if (StringUtils.isEmpty(srvDtls[i].getDtlId())) {
				srvDtls[i].setDtlId(getMaxDtlId(sbOrder));
			}
		}
	}
	
	private String getMaxDtlId(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvDtls = sbOrder.getSrvDtls();
		int maxDtlId = 0;
		int curDtlId = 0;
		
		for (int i=0; i<srvDtls.length; ++i) {
			if (StringUtils.isEmpty(srvDtls[i].getDtlId())) {
				continue;
			}
			curDtlId = Integer.parseInt(srvDtls[i].getDtlId());
			if (maxDtlId < curDtlId) {
				maxDtlId = curDtlId;
			}
		}
		return String.valueOf(maxDtlId+1);
	}
	
	private void updateStatusAfterSuspend(SbOrderDTO sbOrder) {
		
		ServiceDetailDTO[] srvs = sbOrder.getSrvDtls();
		
		for (int i=0; srvs!=null && i<srvs.length; ++i) {
			if (!StringUtils.equals(BomLtsConstant.ORDER_STATUS_APPROVAL_REJECTED, srvs[i].getSbStatus())) {
				this.orderStatusService.setSuspendStatus(sbOrder.getOrderId(), srvs[i].getDtlId(), null, BomLtsConstant.USER_ID, srvs[i].getWorkQueueType());
			}
		}
	}
}
