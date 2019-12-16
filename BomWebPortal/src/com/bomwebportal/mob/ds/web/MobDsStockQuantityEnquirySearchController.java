package com.bomwebportal.mob.ds.web;

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
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.StockQuantityEnquiryDTO;
import com.bomwebportal.mob.ccs.service.MobCcsMaintParmLkupService;
import com.bomwebportal.mob.ccs.service.StockQuantityEnquiryService;

public class MobDsStockQuantityEnquirySearchController implements Controller {
	
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
		
		StockDTO dto = new StockDTO();
		
		dto.setStockPool(request.getParameter("stockPool"));
		
		String eventCode = null;
		eventCode = request.getParameter("eventCode");
		dto.setEventCode(eventCode);
		
		dto.setType(request.getParameter("type"));
		if ("FRONTLINE".equalsIgnoreCase(user.getCategory())) {
			dto.setStaffId(user.getUsername());
		} else {
			dto.setStoreCode(request.getParameter("storeCode"));
			dto.setTeamCode(request.getParameter("teamCode"));
		}
		
		String itemCode = StringUtils.trim(request.getParameter("itemCode"));
		dto.setItemCode(itemCode);
		
		String model = StringUtils.trim(request.getParameter("model"));
		
		if (model != null) {
			if (model.contains("*")) {
				model = StringUtils.replaceChars(model, '*', '%');
			}
			dto.setModel(model);
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("stockPool: " + dto.getStockPool() + ", type: " + dto.getType() + 
				", staffId: " + dto.getStaffId() + ", itemCode: " + dto.getItemCode() + ", model: " + dto.getModel() + ", eventCode: " + dto.getEventCode());
		}
		
		dto.setChannelId(user.getChannelId());
		dto.setCategory(user.getCategory());
		dto.setStaffId(user.getUsername());
		
		List<StockQuantityEnquiryDTO> list = new ArrayList<StockQuantityEnquiryDTO>();
		list = stockQuantityEnquiryService.getDsStockQuantityEnquiry(dto);
		
		if (list == null || list.isEmpty()) {
			list = Collections.emptyList();
		}
		
		JSONArray jsonArray = JSONArray.fromObject(list);

		return new ModelAndView("ajax_search", "jsonArray", jsonArray);
	}
	
}
