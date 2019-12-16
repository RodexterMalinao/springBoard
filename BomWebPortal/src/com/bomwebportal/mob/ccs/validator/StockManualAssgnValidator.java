package com.bomwebportal.mob.ccs.validator;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.mob.ccs.dto.ui.StockManualAssgnUI;
import com.bomwebportal.mob.ccs.service.MobCcsUtilService;
import com.bomwebportal.mob.ccs.service.StockManualAssgnService;

public class StockManualAssgnValidator implements Validator{
	
	private static final String MANUAL_ASSIGN_ERR_MSG = "Manual (De)Assign is not allowed!";

	private MobCcsUtilService mobCcsUtilService;
	private StockManualAssgnService stockManualAssgnService;

	public MobCcsUtilService getMobCcsUtilService() {
		return mobCcsUtilService;
	}
	public void setMobCcsUtilService(MobCcsUtilService mobCcsUtilService) {
		this.mobCcsUtilService = mobCcsUtilService;
	}
	
	public StockManualAssgnService getStockManualAssgnService() {
		return stockManualAssgnService;
	}
	public void setStockManualAssgnService(StockManualAssgnService stockManualAssgnService) {
		this.stockManualAssgnService = stockManualAssgnService;
	}

	public boolean supports(Class clazz) {
		return clazz.equals(StockManualAssgnUI.class);
	}
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	public void validate(Object obj, Errors errors) {
		
		StockManualAssgnUI dto = (StockManualAssgnUI)obj;
		
		List<Object[]> orderInfo = stockManualAssgnService.getOrderInfo(dto.getOrderId());
		
		if (orderInfo != null && orderInfo.size() > 0) {
			/* 0 order id
			 * 1 order type
			 * 2 order status
			 * 3 check point
			 * 4 reason code
			 * 5 delivery date
			 * 6 b4_1500
			* */
			
			String orderId = (orderInfo.get(0)[0]).toString();
			String orderType = (orderInfo.get(0)[1]).toString();
			String orderStatus = (orderInfo.get(0)[2]).toString();
			String checkPoint = (orderInfo.get(0)[3]).toString();
			String reasonCode = (orderInfo.get(0)[4]).toString();
			Date deliveryDate = (Date)(orderInfo.get(0)[5]);
			String b4_1500 = (orderInfo.get(0)[6]).toString();
			
			Date today = new Date();
			Date nextDelivery = mobCcsUtilService.getNextNDeliveryDay(today);
			
			if (orderType != null &&
					("PEND".equalsIgnoreCase(orderType) || "COS".equalsIgnoreCase(orderType) || "BRM".equalsIgnoreCase(orderType) || "TOO1".equalsIgnoreCase(orderType))  ) {
				if (orderStatus != null &&
						"99".equalsIgnoreCase(orderStatus)) {
					if (checkPoint != null &&
							"999".equalsIgnoreCase(checkPoint)) {
						if (reasonCode != null &&
								("NULL".equalsIgnoreCase(reasonCode)
									|| "A001".equalsIgnoreCase(reasonCode)
									|| "A002".equalsIgnoreCase(reasonCode)
									|| "A003".equalsIgnoreCase(reasonCode)
									|| "N002".equalsIgnoreCase(reasonCode)
									|| "N004".equalsIgnoreCase(reasonCode)
									|| "G002".equalsIgnoreCase(reasonCode)
									|| "G004".equalsIgnoreCase(reasonCode)
									|| "M001".equalsIgnoreCase(reasonCode))) {
							return;
						} else {
							errors.rejectValue("errMsg", "dummy", MANUAL_ASSIGN_ERR_MSG);
							return;
						}
					} else {
						errors.rejectValue("errMsg", "dummy", MANUAL_ASSIGN_ERR_MSG);
						return;
					}
				} else if (orderStatus != null &&
						"01".equalsIgnoreCase(orderStatus)) {
					if (checkPoint != null
							&& Integer.parseInt(checkPoint) >= 199
							&& Integer.parseInt(checkPoint) <= 399) {
						if (deliveryDate.after(nextDelivery)
								|| ((today.before(deliveryDate))
										&& (((deliveryDate.before(nextDelivery))
												|| deliveryDate.equals(nextDelivery))
									&& "Y".equalsIgnoreCase(b4_1500)))) {
							return;
						} else {
							errors.rejectValue("errMsg", "dummy", MANUAL_ASSIGN_ERR_MSG);
							return;
						}
					} else {
						errors.rejectValue("errMsg", "dummy", MANUAL_ASSIGN_ERR_MSG);
						return;
					}
				} else {
					errors.rejectValue("errMsg", "dummy", MANUAL_ASSIGN_ERR_MSG);
					return;
				}
			} else {
			
				// error
				errors.rejectValue("errMsg", "dummy", MANUAL_ASSIGN_ERR_MSG);
				return;
			}

		}

	}

}
