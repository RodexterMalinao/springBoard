package com.bomwebportal.quartz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.mob.ccs.service.BomOrderPposService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;

public class SynBomCancelledOrderAutoProcess extends AutoProcessBase{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private static String CANCELING = "03";
	
	private static String CANCELLED = "04";
	
	private static String CN_CANCELLING_WAIT_FOR_BOM="C500";//CN20--Cancel waitting BOM cancellation
	
	BomOrderPposService bomOrderPposService;
	
	OrderService orderService;

	/**
	 * @return the bomOrderPposService
	 */
	public BomOrderPposService getBomOrderPposService() {
		return bomOrderPposService;
	}

	/**
	 * @param bomOrderPposService the bomOrderPposService to set
	 */
	public void setBomOrderPposService(BomOrderPposService bomOrderPposService) {
		this.bomOrderPposService = bomOrderPposService;
	}

	/**
	 * @return the orderService
	 */
	public OrderService getOrderService() {
		return orderService;
	}

	/**
	 * @param orderService the orderService to set
	 */
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	@Override
	public void trigger() {
		List<String> orderIdList = bomOrderPposService.getCrossBomAndMobOrderStatus(CANCELING, CANCELLED);
		
		if (orderIdList != null && !orderIdList.isEmpty()) {
			for (String s : orderIdList) {
				orderService.synBomOrderStatus(s, CANCELLED, "AutoProcess");
				orderService.cancelOrderReleaseStock(s);//release stock only
			}
		}
		
		//For Direct Sales
		orderIdList = bomOrderPposService.getCrossBomAndMobOrderStatus(BomWebPortalConstant.BWP_ORDER_CANCELLING, CANCELLED);
		
		if (orderIdList != null && !orderIdList.isEmpty()) {
			for (String s : orderIdList) {
				orderService.synBomOrderStatus(s, BomWebPortalConstant.BWP_ORDER_CANCELLED, "AutoProcess");
				orderService.cancelDSOrderReleaseStock(s);//release stock only
			}
		}
	}
	
}
