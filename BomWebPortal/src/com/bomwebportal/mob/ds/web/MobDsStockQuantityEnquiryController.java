package com.bomwebportal.mob.ds.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.StockQuantityEnquiryUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsMaintParmLkupService;
import com.bomwebportal.mob.ccs.service.StockService;

public class MobDsStockQuantityEnquiryController extends SimpleFormController {
	
	private CodeLkupService codeLkupService;
	private MobCcsMaintParmLkupService mobCcsMaintParmLkupService;
	private StockService stockService;

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	public MobCcsMaintParmLkupService getMobCcsMaintParmLkupService() {
		return mobCcsMaintParmLkupService;
	}
	public void setMobCcsMaintParmLkupService(
			MobCcsMaintParmLkupService mobCcsMaintParmLkupService) {
		this.mobCcsMaintParmLkupService = mobCcsMaintParmLkupService;
	}
	public StockService getStockService() {
		return stockService;
	}
	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		return new StockQuantityEnquiryUI();

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		return new ModelAndView(new RedirectView("mobccsstockquantityenquiry.html"));
	}
	
	protected Map<?, ?> referenceData(HttpServletRequest request) throws Exception {
		logger.info("MobCcsStockQuantityEnquiryController ReferenceData called");
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		CodeLkupDTO all = new CodeLkupDTO();
		all.setCodeDesc("ALL");
		all.setCodeId(null);

		Map<String, Object> referenceData = new HashMap<String, Object>();

		// type
		List<CodeLkupDTO> typeList = codeLkupService.getCodeLkupDTOALL("STOCK_TYPE");
		typeList.add(0, all);
		referenceData.put("typeList", typeList);
		
		// stock pool list
		referenceData.put("stockPoolList", this.mobCcsMaintParmLkupService.getMaintParmLkupDTO(user.getChannelCd(), "MOB", "STOCK QUANTITY ENQUIRY", "STOCK_POOL"));
		
		//Event List
		referenceData.put("eventList", stockService.getEventList());
		
		//Store Code and Team Code
		referenceData.put("storeList", stockService.getUserMultiStoreCode(user.getUsername(), user.getChannelId()));
		referenceData.put("teamListAll", stockService.getUSerMultiStoreTeamCode(user.getUsername(), user.getChannelId()));
		
		return referenceData;
	}
}
