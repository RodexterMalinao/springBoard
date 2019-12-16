/**
 * 
 */
package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.ApprovalLoginFormDTO;
import com.bomwebportal.dto.AuthorizeDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.acq.LtsAcqNumberSelectionInfoDTO;
import com.bomwebportal.lts.dto.form.LtsDnChangeFormDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.AuthorizeService;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.util.ConfigProperties;

// @author MAX.R.MENG LTS
public class ApprovalLoginController extends SimpleFormController{
protected final Log logger = LogFactory.getLog(getClass());
	
	private LoginService service;

	public LoginService getService() {
		return service;
	}
	public void setService(LoginService service) {
		this.service = service;
	}
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);  
		if(orderCapture.getApprovalLoginForm() !=null){
			return orderCapture.getApprovalLoginForm();
		}
		return new ApprovalLoginFormDTO();
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		ApprovalLoginFormDTO approvalUser = (ApprovalLoginFormDTO) command;
		//String dbRecordSessionId = service.getDbRecordSessionId(bomSalesUserDTO.getUsername());
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);	
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		BomSalesUserDTO loginedUser = service.getSalesAssign(approvalUser.getUsername());
		approvalUser.setApprovalInd(false);
		if(StringUtils.equals(loginedUser.getCategory(), "SALES MANAGER")
				|| StringUtils.equals(loginedUser.getCategory(), "MANAGER")){
			approvalUser.setApprovalInd(true);

		    orderCapture.setApprovalLoginForm(approvalUser);
			return new ModelAndView(new RedirectView("closepopup.jsp"));
		}   
	    return new ModelAndView(new RedirectView("approvallogin.html"));
	}
}
