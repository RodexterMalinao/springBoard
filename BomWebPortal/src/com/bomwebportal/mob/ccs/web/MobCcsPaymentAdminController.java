package com.bomwebportal.mob.ccs.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentAdminUI;
import com.bomwebportal.mob.ccs.service.MobCcsPaymentAdminService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.util.Utility;

public class MobCcsPaymentAdminController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private MobCcsPaymentAdminService mobCcsPaymentAdminService;

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		logger.info("MobCcsPaymentAdminController formBackingObject called");
		MobCcsPaymentAdminUI paymentAdmin = (MobCcsPaymentAdminUI) MobCcsSessionUtil
				.getSession(request, "paymentAdmin");
		if (paymentAdmin == null) {
			paymentAdmin = new MobCcsPaymentAdminUI();
			paymentAdmin.setPayMethodList(mobCcsPaymentAdminService.getCodeLkupDTOALL("PAY_METHOD"));
		} else {
			if ("QUERY".equalsIgnoreCase(paymentAdmin.getActionType())) {
				
				String sdStr = paymentAdmin.getStartDateStr();
				String edStr = paymentAdmin.getEndDateStr();
				
				if(sdStr != null){
					paymentAdmin.setStartDate(Utility.string2Date(sdStr));
				}
				if(edStr != null){
					paymentAdmin.setEndDate(Utility.string2Date(edStr));
				}
				
				paymentAdmin
						.setMobCcsPaymentTransDTOList(mobCcsPaymentAdminService
								.getMobCcsPaymentTransDTOList(
										paymentAdmin.getStartDate(),
										paymentAdmin.getEndDate(),
										paymentAdmin.getPayMethod(),
										paymentAdmin.getMrt()));				
				paymentAdmin.setValidated(true);
			}
		}
		
		MobCcsSessionUtil.setSession(request, "paymentAdmin", paymentAdmin);
		return paymentAdmin;

	}
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		logger.info("MobCcsPaymentAdminController is called!");

		return new ModelAndView(new RedirectView("mobccspaymentadmin.html"));
	}	

	
	public MobCcsPaymentAdminService getMobCcsPaymentAdminService() {
		return mobCcsPaymentAdminService;
	}

	public void setMobCcsPaymentAdminService(
			MobCcsPaymentAdminService mobCcsPaymentAdminService) {
		this.mobCcsPaymentAdminService = mobCcsPaymentAdminService;
	}

}
