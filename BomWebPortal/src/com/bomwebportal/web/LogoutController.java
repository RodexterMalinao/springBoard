package com.bomwebportal.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.service.ReleaseLockService;

public class LogoutController implements Controller{

    protected final Log logger = LogFactory.getLog(getClass());

	private LoginService service;
	
	private ReleaseLockService releaseLockService;

	public ReleaseLockService getReleaseLockService() {
		return releaseLockService;
	}
	public void setReleaseLockService(ReleaseLockService releaseLockService) {
		this.releaseLockService = releaseLockService;
	}
	
	public LoginService getService() {
		return service;
	}

	public void setService(LoginService service) {
		this.service = service;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String orderId = (String) request.getSession().getAttribute("orderIdSession");
				
		//Logic of lock order release
    	OrderDTO lockOrderDTO  = releaseLockService.getOrderLockInfo(orderId);
    	if (lockOrderDTO != null) {
    		
    		if ("Y".equalsIgnoreCase(lockOrderDTO.getLockInd())) {
    			if(user.getUsername().equalsIgnoreCase(lockOrderDTO.getLastUpdateBy())) {
    				releaseLockService.updateOrderLockInd(orderId, "", user.getUsername());
    			}
    		}
	    }
    	
    	//update db session id to null 
		String remoteAddress = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes())
	       .getRequest().getRemoteAddr();
		logger.info("bomSalesUserDTO.getUsername():"+user.getUsername());
		logger.info("remoteAddress:"+remoteAddress);
		service.updateSessionId(user.getUsername(),  null,  remoteAddress);
		//end update db session id to null 
		
		request.getSession().invalidate();
						
		request.setAttribute("bomsalesuser", new BomSalesUserDTO());
		
		List<String> shopCdList = service.getShopList();
		
		request.setAttribute("shopCdList", shopCdList);
		
		/////LookupTableBean.getInstance().setAuthorizeList(null);
		
		return new ModelAndView("login");
	}
}
