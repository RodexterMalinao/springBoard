package com.bomwebportal.mob.ccs.validator;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ui.CancellationUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.ReleaseLockService;

public class MobCcsCancellationValidator implements Validator {
	private static final String CODE_TYPE = "ORD_CANCEL_CODE";
	
	enum OrderStatus {
		CANCELLING("03")
		, CANCELLED("04")
		;
		OrderStatus(String status) {
			this.status = status;
		}
		public String getStatus() {
			return status;
		}
		private String status;
	}
	
	private OrderService orderService;
	private CodeLkupService codeLkupService;
	private ReleaseLockService releaseLockService;

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	
	public ReleaseLockService getReleaseLockService() {
		return releaseLockService;
	}

	public void setReleaseLockService(ReleaseLockService releaseLockService) {
		this.releaseLockService = releaseLockService;
	}

	public boolean supports(Class clazz) {
		return CancellationUI.class.equals(clazz);
	}

	public void validate(Object command, Errors errors) {
		CancellationUI form = (CancellationUI) command;
		if (StringUtils.isBlank(form.getCodeId())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "codeId", "codeId.required");
		} else {
			List<CodeLkupDTO> cancelReasons = this.codeLkupService.getCodeLkupDTOALL(CODE_TYPE);
			boolean foundCodeId = false;
			if (cancelReasons != null) {
				for (CodeLkupDTO dto : cancelReasons) {
					if (dto.getCodeId().equals(form.getCodeId())) {
						foundCodeId = true;
						break;
					}
				}
			}
			if (!foundCodeId) {
				errors.rejectValue("codeId", "codeId.required");
			}
		}

		if (StringUtils.isNotBlank(form.getRemark()) && form.getRemark().length() > 150) {
			errors.rejectValue("remark", "dummy", "Remarks over 150 characters");
		}
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orderId", "orderId.required");
		
		
		
		
		BomSalesUserDTO user = (BomSalesUserDTO) form.getValue("bomsalesuser");
		OrderDTO orderDTO = this.orderService.getOrder(form.getOrderId());
		
		if ("Y".equals(form.getOrderRecreateInd())) {
			if ("Y".equals(form.getReserveMrtInd())){
				errors.rejectValue("orderId", "dummy", "Reserve MRT found, Not allow to cancel recreate order (allow cancel only)!!");
			}else if ("Y".equals(orderDTO.isMultiSim())){
				errors.rejectValue("orderId", "dummy", "Multi SIM order found, Not allow to cancel recreate order (allow cancel only)!!");
			}
		}
		
		if ("Y".equals(form.getOrderRecreateInd()) && "Y".equals(form.getPaymentTransferInd()) && StringUtils.isNotEmpty(orderDTO.getCloneOrderId())){
            errors.rejectValue("orderId", "dummy", "Clone order found, Not allow to cancel recreate/clone order (allow cancel only)!!");
		}	

		
		List<CodeLkupDTO> sboCancelChannelList = this.codeLkupService.getCodeLkupDTOALL("SBO_CANCEL_CHANNEL");
		boolean allowToCancelSboOrder = false;
		for (CodeLkupDTO sboCancelChannel :sboCancelChannelList){
			if (sboCancelChannel.getCodeId().equalsIgnoreCase(user.getChannelCd())){
				allowToCancelSboOrder = true;
				break;
			}
				
		}
		
		if (!errors.hasErrors()) {
				
			if (user!=null && !user.getChannelCd().equalsIgnoreCase(orderDTO.getShopCode())){
				if ( !("SBO".equalsIgnoreCase(orderDTO.getShopCode()) && allowToCancelSboOrder)){
					errors.rejectValue("orderId", "dummy", "Different channel code found. Not allow to cancel order.");
				}
			}
			
			if (orderDTO == null) {
				errors.rejectValue("orderId", "dummy", "Unknown Order ID");
			} else {
				if (!"MOB".equals(orderDTO.getOrderSumLob())) {
					errors.rejectValue("orderId", "dummy", "Order does not belong to MOB");
				} else if (OrderStatus.CANCELLING.getStatus().equals(orderDTO.getOrderStatus())) {
					errors.rejectValue("orderId", "dummy", "Order in CANCELLING status");
				} else if (OrderStatus.CANCELLED.getStatus().equals(orderDTO.getOrderStatus())) {
					errors.rejectValue("orderId", "dummy", "Order in CANCELLED status");
				}
				/* no need to check order lock in Validator
				 * OrderDTO orderLockInfo = this.releaseLockService.getOrderLockInfo(orderDTO.getOrderId());
				if (orderLockInfo != null && "Y".equals(orderLockInfo.getLockInd())) {
					
					errors.rejectValue("orderId", "dummy", "Order is locked");
				}*/
			}
		}
	}

}
