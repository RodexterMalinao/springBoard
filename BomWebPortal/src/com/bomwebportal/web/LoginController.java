package com.bomwebportal.web;

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

public class LoginController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LoginService service;
	private AuthorizeService authorizeService;
	
	private String environment;
	private String singleLogin;
	private boolean singleLoginReplaceConfirm;
	private static final String MOB = "MOB";
	private static final String LTS = "LTS";
	private static final String LTS_BACKDOOR_VIEW = "ltsbackdoor.html";
	
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
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return new BomSalesUserDTO();
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO) command;
		
		if ("N".equals(singleLogin)) {
			return this.login(request, bomSalesUserDTO, errors);
		}
		
		if (!singleLoginReplaceConfirm) {
			return this.login(request, bomSalesUserDTO, errors);
		}

		String dbRecordSessionId = service.getDbRecordSessionId(bomSalesUserDTO.getUsername());
		// user already logout / no active session
		if (StringUtils.isBlank(dbRecordSessionId)) {
			return this.login(request, bomSalesUserDTO, errors);
		}
		
		String confirm = request.getParameter("confirm");
		if ("true".equals(confirm)) {
			// another user login system during waiting confirm in page -> db session id change
			if (!dbRecordSessionId.equals(request.getParameter("dbRecordSessionId"))) {
				return this.loginConfirm(request, bomSalesUserDTO, errors, dbRecordSessionId);
			}
			return this.login(request, bomSalesUserDTO, errors);
		}
		if ("false".equals(confirm)) {
			return this.logout(request, bomSalesUserDTO, errors);
		}
		
		return this.loginConfirm(request, bomSalesUserDTO, errors, dbRecordSessionId);
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
		//LookupTableBean.getInstance().setAuthorizeList(authorize);
		RequestContextHolder
				.currentRequestAttributes()
				.setAttribute("authorizationInfo", authorize, RequestAttributes.SCOPE_GLOBAL_SESSION);
		
		if (StringUtils.equals(environment, "dev") 
				&& StringUtils.contains(bomSalesUserDTO.getUsername(), LTS)) {
			return new ModelAndView(new RedirectView(LTS_BACKDOOR_VIEW));		
		}
		
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
		return new ModelAndView(new RedirectView("login.html"));
	}
	
	protected ModelAndView showForm(HttpServletRequest request, HttpServletResponse response, BindException errors) throws Exception {
		if ("true".equals(ConfigProperties.getPropertyByEnvironment("sso.development.bypass"))) {
			return super.showForm(request, response, errors);
		} else {
			return new ModelAndView("redirect:/closepopup.jsp");
		}
		
	}
}
