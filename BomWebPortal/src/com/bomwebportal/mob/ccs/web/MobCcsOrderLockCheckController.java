package com.bomwebportal.mob.ccs.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.service.ReleaseLockService;

public class MobCcsOrderLockCheckController implements Controller {
	
	private static String LOCK = "Y";
	private static String UNLOCK = "N";
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ReleaseLockService releaseLockService;
	
	public ReleaseLockService getReleaseLockService() {
		return releaseLockService;
	}
	public void setReleaseLockService(ReleaseLockService releaseLockService) {
		this.releaseLockService = releaseLockService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		String orderId = request.getParameter("orderId");
		String result = LOCK;
		
		logger.debug("MobCcsOrderLockCheckController (orderId): " + orderId);
		OrderDTO orderLockInfo = releaseLockService.getOrderLockInfo(orderId);
		
		if (orderLockInfo != null && "Y".equals(orderLockInfo.getLockInd())) {
			if (user == null) {
				result = LOCK;
			} else {
				if (user.getUsername() != null
						&& user.getUsername().equalsIgnoreCase(orderLockInfo.getLastUpdateBy())) {
					result = UNLOCK;
				} else {
					result = LOCK;
				}
			}
		} else {
			result = UNLOCK;
		}
		
		JSONArray jsonArray = new JSONArray();
		jsonArray.add("{\"result\":\"" + result + "\"}");

		return new ModelAndView("ajax_search", "jsonArray", jsonArray);
	}
}
