package com.bomwebportal.mob.ccs.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.util.BomWebPortalCcsConstant;
import com.bomwebportal.service.ReleaseLockService;

public class MobCcsOrderLockController implements Controller{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ReleaseLockService releaseLockService;
	
	public ReleaseLockService getReleaseLockService() {
		return releaseLockService;
	}
	public void setReleaseLockService(ReleaseLockService releaseLockService) {
		this.releaseLockService = releaseLockService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.debug("handleRequest@MobCcsOrderLockController");
		
		String orderId = request.getParameter("orderId");
		
		OrderDTO orderInfo = releaseLockService.getOrderLockInfo(orderId);
		
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		if (orderInfo != null) {
			if ("Y".equals(orderInfo.getLockInd())) {
				releaseLockService.updateOrderLockInd(
						orderId, BomWebPortalCcsConstant.ORDER_LOCK_IND_UNLOCK, user.getUsername());
			} else {
				releaseLockService.updateOrderLockInd(
						orderId, BomWebPortalCcsConstant.ORDER_LOCK_IND_LOCK, user.getUsername());
			}
		}
		
		JSONArray jsonArray = new JSONArray();
		
		return new ModelAndView("ajax_mobCcsOrderLock", "jsonArray", jsonArray);
		
	}
	
}
