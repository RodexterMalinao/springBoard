package com.bomwebportal.mob.ccs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.ui.CancellationUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsCancelService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mobquota.service.MobQuotaService;
import com.bomwebportal.service.OrderHsrmService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.ReleaseLockService;
import com.bomwebportal.service.ReleaseLockService.LockResult;

public class MobCcsCancellationController extends SimpleFormController {
	private static final String CODE_TYPE = "ORD_CANCEL_CODE";
	
	protected final Log logger = LogFactory.getLog(getClass());
	private MobCcsCancelService mobCcsCancelService;
	private OrderService orderService;
	private CodeLkupService codeLkupService;
	private ReleaseLockService releaseLockService;
	private MobQuotaService mobQuotaService;
	private OrderHsrmService orderHsrmService;
	
	public MobCcsCancelService getMobCcsCancelService() {
		return mobCcsCancelService;
	}

	public void setMobCcsCancelService(MobCcsCancelService mobCcsCancelService) {
		this.mobCcsCancelService = mobCcsCancelService;
	}

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

	public MobQuotaService getMobQuotaService() {
		return mobQuotaService;
	}

	public void setMobQuotaService(MobQuotaService mobQuotaService) {
		this.mobQuotaService = mobQuotaService;
	}	
	public OrderHsrmService getOrderHsrmService() {
		return orderHsrmService;
	}

	public void setOrderHsrmService(OrderHsrmService orderHsrmService) {
		this.orderHsrmService = orderHsrmService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		CancellationUI form = new CancellationUI();
		OrderDTO orderDTO = this.orderService.getOrder(ServletRequestUtils.getRequiredStringParameter(request, "orderId"));
		form.setOrderId(orderDTO.getOrderId());
		form.setLob(orderDTO.getOrderSumLob());
		form.setOrderRecreateInd("N");
		form.setPaymentTransferInd("N");
		form.setCreditCardTrxInd("Y".equals(orderDTO.getCreditCardTrxInd()) ? "Y" : "N");
		form.setOrderStatus(orderDTO.getOrderStatus());
		request.setAttribute("isMultiSimOrNot", orderDTO.isMultiSim());		//for multi-sim
		form.setReserveMrtInd(orderDTO.getReserveMrtInd());
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		form.setValue("bomsalesuser", user);
		
		LockResult lockresult =null;
		lockresult= this.releaseLockService.getOrderLockInfo(orderDTO.getOrderId(), user.getUsername());
		if (LockResult.LOCKED_BY_OTHER_USER==lockresult){
			
			form.setLockInd( "Y" );// lock by other, for frontend display error
		}else {
			this.releaseLockService.updateOrderLockInd(orderDTO.getOrderId(), "Y", user.getUsername());//lock this order
			form.setLockInd( "N" );//  
		}
		
		
		List<ComponentDTO> componentList = orderService.getComponentList(orderDTO.getOrderId());
		if (orderHsrmService.isPrj7AttbExists(componentList)){
			if (orderHsrmService.isOrderCompletedHsrmLogExist(orderDTO.getOrderId()) && !orderHsrmService.hsrmAllowRecreate()){
				form.setPreRegInd( "Y" );
			}
		}
		request.setAttribute("isPreRegOrNot", "Y".equals(form.getPreRegInd()));	
		/*if (orderHsrmService.hsrmCompleted(orderDTO.getOrderId()) || orderHsrmService.hsrmConfirmed(orderDTO.getOrderId())){
		form.setPreRegInd( "Y" );
		}*/
		
					
		
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		List<CodeLkupDTO> cancelReasons = codeLkupService.getCodeLkupDTOALL(CODE_TYPE);
		referenceData.put("cancelReasons", cancelReasons);
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		CancellationUI form = (CancellationUI) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		try {
			mobQuotaService.cancelQuotaUsage("S", form.getOrderId(), user.getUsername());
		} catch (Exception e) {
			logger.error("Exception caught in cancelQuotaUsage", e);
		}
		
		if ("Y".equals(form.getOrderRecreateInd())) {
			MobCcsSessionUtil.setSession(request, "cancellation", form);
			
			ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccssummary.html"), model);
			modelAndView.addObject("orderId", form.getOrderId());
			this.releaseLockService.updateOrderLockInd(form.getOrderId(), null, user.getUsername());//release lock
			return modelAndView;
		} else {
			this.mobCcsCancelService.cancelMobCcsOrder(form.getOrderId()
					, form.getCodeId()
					, user.getUsername() + " CANCEL ORDER" + (StringUtils.isBlank(form.getRemark()) ? "" : "(" + form.getRemark() + ")")
					, user.getUsername());
			ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccspreview.html"), model);
			modelAndView.addObject("orderId", form.getOrderId());
			modelAndView.addObject("action", "ENQUIRY");
			this.releaseLockService.updateOrderLockInd(form.getOrderId(), null, user.getUsername());//release lock
			return modelAndView;
		}
	}
}
