package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openuri.www.CustomerBasicInfoDTO;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.CustomerInformationQuotaUI;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRemarkService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.CustomerInformationQuotaService;
import com.bomwebportal.util.Utility;

public class CustomerInformationQuotaController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	private CustomerInformationQuotaService customerInformationQuotaService;
	private MobCcsOrderRemarkService remarkService;


	public CustomerInformationQuotaService getCustomerInformationQuotaService() {
		return customerInformationQuotaService;
	}

	public void setCustomerInformationQuotaService(
			CustomerInformationQuotaService customerInformationQuotaService) {
		this.customerInformationQuotaService = customerInformationQuotaService;
	}
	
	public MobCcsOrderRemarkService getRemarkService() {
		return remarkService;
	}

	public void setRemarkService(MobCcsOrderRemarkService remarkService) {
		this.remarkService = remarkService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		logger.info("CustomerInformationQuotaController formBackingObject called");

		CustomerBasicInfoDTO customer = (CustomerBasicInfoDTO) request
				.getSession().getAttribute("selectedCustInfoSession");
		CustomerInformationQuotaUI custInfoQuota = (CustomerInformationQuotaUI) request
				.getSession().getAttribute("customerInfoQuota");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession()
				.getAttribute("bomsalesuser");

		if (custInfoQuota == null) {
			custInfoQuota = new CustomerInformationQuotaUI();
		}
		
		if (customer != null) {			
			custInfoQuota = customerInformationQuotaService.getCustomerInformationQuotaUI(customer);
		}
		
		if (user != null) {
			custInfoQuota.setChannelId(user.getChannelId());
		}
		
		request.getSession().setAttribute("customerInfoQuota", custInfoQuota);
		MobCcsSessionUtil.setSession(request, "customerInfoQuota", custInfoQuota);
			
		return custInfoQuota;

	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		logger.info("CustomerInformationQuotaController is called!");
		String uid="";
		String nextView = "customerinformationquota.html";
		CustomerInformationQuotaUI custQuota = (CustomerInformationQuotaUI) request
				.getSession().getAttribute("customerInfoQuota");
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		if (custQuota != null && custQuota.getOrderStatus() != null) {
			if ("RETAILORDER".equalsIgnoreCase(custQuota.getOrderStatus())) {
			
				uid=Utility.getUid();
				request.getSession().setAttribute("sbuid", uid);
				request.setAttribute("sbuid", uid);
				nextView = "customerprofile.html";
				boolean logUid = false;
				
				List<CodeLkupDTO> checkUidCdLkupList
					= LookupTableBean.getInstance().getCodeLkupList().get("LOG_UID");
				
				if (checkUidCdLkupList != null
						&& !checkUidCdLkupList.isEmpty()) {
					logUid = "Y".equalsIgnoreCase(checkUidCdLkupList.get(0).getCodeDesc()) ?
								true : false;
				} else {
					logUid = false;
				}
				
				if (logUid) {
					String msg = "CustomerInformationQuotaController UID: " + uid;
					remarkService.insertOrderRemark(user.getUsername(), user.getUsername(), msg);
				}
			} else {
				if (custQuota.isMrtThresholdOverflow()) {
					request.getSession().setAttribute("approvedSrvLineExceed", true);
				}
				nextView = "customerprofile.html";
			}
		}
		
		//save orderType, remove by wilson 20120314
		//request.getSession().setAttribute("orderType", custQuota.getOrderStatus());
		//MobCcsSessionUtil.setSession(request, "orderType",	custQuota.getOrderStatus());

		//return new ModelAndView(new RedirectView(nextView));
		
		/* test uid*/
		
		ModelAndView modelAndView =  new ModelAndView(new RedirectView(nextView));
		modelAndView.addObject("sbuid", uid);
		/* test uid*/
		return modelAndView;
	}

}
