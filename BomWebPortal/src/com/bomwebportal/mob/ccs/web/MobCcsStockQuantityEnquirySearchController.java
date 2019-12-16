package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockQuantityEnquiryDTO;
import com.bomwebportal.mob.ccs.service.MobCcsMaintParmLkupService;
import com.bomwebportal.mob.ccs.service.StockQuantityEnquiryService;

public class MobCcsStockQuantityEnquirySearchController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockQuantityEnquiryService stockQuantityEnquiryService;
	private MobCcsMaintParmLkupService mobCcsMaintParmLkupService;

	public StockQuantityEnquiryService getStockQuantityEnquiryService() {
		return stockQuantityEnquiryService;
	}
	public void setStockQuantityEnquiryService(StockQuantityEnquiryService stockQuantityEnquiryService) {
		this.stockQuantityEnquiryService = stockQuantityEnquiryService;
	}
	public MobCcsMaintParmLkupService getMobCcsMaintParmLkupService() {
		return mobCcsMaintParmLkupService;
	}
	public void setMobCcsMaintParmLkupService(MobCcsMaintParmLkupService mobCcsMaintParmLkupService) {
		this.mobCcsMaintParmLkupService = mobCcsMaintParmLkupService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		String stockPool = null;
		
		stockPool = request.getParameter("stockPool");
		String type = request.getParameter("type");
		String staffId = user.getUsername(); //request.getParameter("staffId");
		String itemCode = StringUtils.trim(request.getParameter("itemCode"));
		String model = StringUtils.trim(request.getParameter("model"));
		
		if (model != null) {
			if (model.contains("*")) {
				model = StringUtils.replaceChars(model, '*', '%');
			}
		} else {
			model = "";
		}
		if (logger.isDebugEnabled()) {
			logger.debug("stockPool: " + stockPool + ", type: " + type + ", staffId: " + staffId + ", itemCode: " + itemCode + ", model: " + model);
		}
		
		List<StockQuantityEnquiryDTO> list = new ArrayList<StockQuantityEnquiryDTO>();
		list = stockQuantityEnquiryService.getStockQuantityEnquiry(stockPool, staffId, type, itemCode, model);
		
		if (list == null) {
			list = Collections.emptyList();
		}
		
		JSONArray jsonArray = JSONArray.fromObject(list);

		return new ModelAndView("ajax_search", "jsonArray", jsonArray);
	}
	
	private boolean isAdmin(BomSalesUserDTO user) {
		for (int channelId : new int[] { 2, 68, 67 }) {
			if (user.getChannelId() == channelId) {
				return false;
			}
		}
		return true;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
