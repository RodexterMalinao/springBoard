package com.bomwebportal.ims.web;

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

import com.bomwebportal.dto.AuthorizeDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.service.AuthorizeService;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.util.ConfigProperties;

public class ImsLoginController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LoginService service;
	private AuthorizeService authorizeService;
	
	private String environment;
	private String singleLogin;
	private boolean singleLoginReplaceConfirm;
	private static final String MOB = "MOB";
	
	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}

	public AuthorizeService getAuthorizeService() {
		return authorizeService;
	}

	public void setAuthorizeService(AuthorizeService authorizeService) {
		this.authorizeService = authorizeService;
	}

	public LoginService getService() {
		return service;
	}

	public void setService(LoginService service) {
		this.service = service;
	}
	
	public String getSingleLogin() {
		return singleLogin;
	}

	public void setSingleLogin(String singleLogin) {
		this.singleLogin = singleLogin;
	}
	
	public boolean isSingleLoginReplaceConfirm() {
		return singleLoginReplaceConfirm;
	}

	public void setSingleLoginReplaceConfirm(boolean singleLoginReplaceConfirm) {
		this.singleLoginReplaceConfirm = singleLoginReplaceConfirm;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		logger.debug("ImsLoginController  formBackingObject" );
		request.getSession().setAttribute("IMS_SSO_BYPASS", "1");
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		logger.debug("ImsLoginController  formBackingObject2" );
		return new BomSalesUserDTO();
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO) command;
		logger.debug("ImsLoginController  onSubmit" );
		request.getSession().setAttribute("IMS_SSO_BYPASS", "1");
		return this.login(request, bomSalesUserDTO, errors);
	}
	
	private ModelAndView login(HttpServletRequest request, BomSalesUserDTO bomSalesUserDTO, BindException errors) {
		String nextView = (String)request.getAttribute("nextView");
		String currentView = (String)request.getParameter("currentView");
		
		if (logger.isInfoEnabled()) {
			logger.info("nextView: " + nextView);
			logger.info("currentView: " + currentView);
			logger.info("username :" + bomSalesUserDTO.getUsername());
		}
		
		//update db session record
		String remoteAddress = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest().getRemoteAddr();
		String sessionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		if (logger.isInfoEnabled()) {
			logger.info("bomSalesUserDTO.getUsername():" + bomSalesUserDTO.getUsername());
			logger.info("remoteAddress:" + remoteAddress);
			logger.info("sessionId:" + sessionId);
		}
		service.updateSessionId(bomSalesUserDTO.getUsername(),  sessionId,  remoteAddress);
		request.getSession().setAttribute("bomsalesuser", service.getSalesAssign(bomSalesUserDTO.getUsername()));
		
		BomSalesUserDTO loginedUser = service.getSalesAssign(bomSalesUserDTO.getUsername());

		Map<String, List<AuthorizeDTO>> authorize = authorizeService.getAuthorizeList(loginedUser.getUsername(), loginedUser.getCategory(), String.format("%02d", loginedUser.getChannelId()));
		RequestContextHolder
				.currentRequestAttributes()
				.setAttribute("authorizationInfo", authorize, RequestAttributes.SCOPE_GLOBAL_SESSION);
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	private ModelAndView loginConfirm(HttpServletRequest request, BomSalesUserDTO bomSalesUserDTO, BindException errors, String dbRecordSessionId) {
		ModelAndView modelAndView = new ModelAndView("loginConfirm", errors.getModel());
		modelAndView.addObject("dbRecordSessionId", dbRecordSessionId);
		modelAndView.addObject("loginLogDto", this.service.getLoginLogDTO(bomSalesUserDTO.getUsername(), dbRecordSessionId));
		return modelAndView;
	}
	
	private ModelAndView logout(HttpServletRequest request, BomSalesUserDTO bomSalesUserDTO, BindException errors) {
		request.getSession().invalidate();
		return new ModelAndView(new RedirectView("imslogin.html"));
	}
	
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
		return super.showForm(request, response, errors);		
	}
}
