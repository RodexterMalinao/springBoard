package com.bomwebportal.quartz;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.BomOrderPposDTO;
import com.bomwebportal.mob.ccs.service.BomOrderPposService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.Utility;

public class SynRetailShopOrderBomStatusAutoProcess extends AutoProcessBase{
	
protected final Log logger = LogFactory.getLog(getClass());
	
	private static FastDateFormat df = FastDateFormat.getInstance("dd/MM/yyyy");
	
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

	

	@Override
	protected void trigger() {
		logger.info("SynRetailShopOrderBomStatusAutoProcess() starts");
		
		String ddMMyyyy = df.format(new Date());
		
		List<BomOrderPposDTO> pposDTOs = bomOrderPposService.getRetailShopBomOrderStatus(ddMMyyyy);
		
		for (BomOrderPposDTO result : pposDTOs) {

			boolean isExist = bomOrderPposService.isBomOrderPposExist(result.getOrderId());
				
			if (isExist) {				
				bomOrderPposService.updateBomOrder(result);
			} else {
				bomOrderPposService.insertBomOrder(result);
			}
		}
	}
	
}
