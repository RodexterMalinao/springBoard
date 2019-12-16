package com.bomwebportal.quartz;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.BomOrderPposDTO;
import com.bomwebportal.mob.ccs.service.BomOrderPposService;
import com.bomwebportal.service.OrderService;

public class MobCcsBomOrderInfoAutoProcess extends AutoProcessBase{

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
		logger.info("MobCcsBomOrderInfoAutoProcess() starts");
		
		String ccsMode = bomOrderPposService.getEnvironmentCcsMode("CCS_CODE");
		
		List<BomOrderPposDTO> pposDTOs = bomOrderPposService.getBomOrderPpos(ccsMode);
		
		if (pposDTOs != null && !pposDTOs.isEmpty()) {
			
			//to get bom order status
//			List<String> ocidList = new ArrayList<String>();
//			for (BomOrderPposDTO dto : pposDTOs) {
//				if (dto.getOcid() != null && !dto.getOcid().isEmpty()) {
//					ocidList.add(dto.getOcid());
//				}
//			}
//			
//			Map<String, BomOrderPposDTO> bomMap = bomOrderPposService.getBomOrderPposMap(ocidList);
			//end of to get bom order status
			
			for (BomOrderPposDTO result : pposDTOs) {
				
				String orderId = orderService.getOrderId(result.getOcid());
				
				if (orderId == null || orderId.isEmpty()) {
					continue;
				}
				
				result.setOrderId(orderId);
				
//				result.setBomStatus(bomMap.get(orderId).getBomStatus());
//				result.setBomStatusDesc(bomMap.get(orderId).getBomStatusDesc());
				
				boolean isExist = bomOrderPposService.isBomOrderPposInfoExist(result.getOcid());
					
				if (isExist) {				
					bomOrderPposService.updateBomOrderInfo(result);
				} else {
					bomOrderPposService.insertBomOrderInfo(result);
				}
			}
			
		}	
	}
	
}
