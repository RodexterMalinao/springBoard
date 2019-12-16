package com.bomwebportal.quartz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.BomOrderPposDTO;
import com.bomwebportal.mob.ccs.service.BomOrderPposService;
import com.bomwebportal.service.OrderService;

public class MobCcsBomOrderAutoProcess extends AutoProcessBase{
	
protected final Log logger = LogFactory.getLog(getClass());
	
	OrderService orderService;
	
	BomOrderPposService bomOrderPposService;
	
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
	protected void trigger() {
		logger.info("MobCcsBomOrderAutoProcess() starts");
		
		String ccsMode = bomOrderPposService.getEnvironmentCcsMode("CCS_CODE");		
		List<BomOrderPposDTO> pposDTOs = bomOrderPposService.getUpdatedBomOrder(ccsMode);
		
		// Add All Direct Sales orders
		List<String> dsShopList = orderService.getCodeIdList("DS_CH");
		for (String dsShop: dsShopList) {
			List<BomOrderPposDTO> tempPposDTOs = bomOrderPposService.getUpdatedBomOrder(dsShop);
			pposDTOs.addAll(tempPposDTOs);
		}
		
		for (BomOrderPposDTO result : pposDTOs) {
			
			String orderId = orderService.getOrderId(result.getOcid());
			
			if (orderId == null || orderId.isEmpty()) {
				continue;
			}
			
			result.setOrderId(orderId);
			
			boolean isExist = bomOrderPposService.isBomOrderPposExist(result.getOrderId());
				
			if (isExist) {				
				bomOrderPposService.updateBomOrder(result);
			} else {
				bomOrderPposService.insertBomOrder(result);
			}
		}
	}
	
}
