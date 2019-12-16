package com.bomwebportal.mob.ccs.web;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTStockUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtStatusService.MsisdnStatus;
import com.bomwebportal.mob.ccs.service.MrtInventoryService;
import com.bomwebportal.mob.ccs.service.MrtInventoryService.FunctionCd;
import com.bomwebportal.mob.ccs.service.MrtInventoryService.ParmType;

public class MobCcsMRTStockInController extends SimpleFormController {
    protected final Log logger = LogFactory.getLog(getClass());
    
    private MrtInventoryService mrtInventoryService;
    private CodeLkupService codeLkupService;
    private MobCcsMrtService mobCcsMrtService;
    
	/**
	 * @return the mobCcsMrtService
	 */
	public MobCcsMrtService getMobCcsMrtService() {
		return mobCcsMrtService;
	}

	/**
	 * @param mobCcsMrtService the mobCcsMrtService to set
	 */
	public void setMobCcsMrtService(MobCcsMrtService mobCcsMrtService) {
		this.mobCcsMrtService = mobCcsMrtService;
	}

	public MrtInventoryService getMrtInventoryService() {
		return mrtInventoryService;
	}

	public void setMrtInventoryService(MrtInventoryService mrtInventoryService) {
		this.mrtInventoryService = mrtInventoryService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		MRTStockUI form = new MRTStockUI();
		
		return form;
	}

	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		referenceData.put("numTypeList", this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, ParmType.NUM_TYPE));
		referenceData.put("serviceTypeList", this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, ParmType.SERVICE_TYPE)); 
		referenceData.put("msisdnlvlList", this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, ParmType.GRADE));
		referenceData.put("channelCodeList", this.mrtInventoryService.getMaintParmValue(user.getChannelCd(), FunctionCd.MRT_INVENTORY, ParmType.CHANNEL));
		referenceData.put("goldenNumChannelCodeList", codeLkupService.getCodeLkupDTOALL("CCS_CODE"));
		referenceData.put("msisdnStatusList", Arrays.asList(MsisdnStatus.FREE, MsisdnStatus.RESERVE));
		referenceData.put("cityCodeList", codeLkupService.getCodeLkupDTOALL("CITY_CODE"));
		
		referenceData.put("mrtControlJson", mrtInventoryService.getMrtControlJson(user.getChannelCd()));
		
		return referenceData;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		MRTStockUI form = (MRTStockUI) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		MrtInventoryDTO record = new MrtInventoryDTO();
		Date now = new Date();
		record.setNumType(form.getNumType());
		record.setSrvNumType(form.getServiceType());
		record.setMsisdn(form.getMsisdn());
		record.setMsisdnlvl(form.getMsisdnlvl());
		record.setMsisdnStatus(form.getMsisdnStatus());
		record.setCityCd(form.getCityCode());
		record.setChannelCd(form.getChannelCode());
		record.setStockInDate(now);
		record.setCreatedBy(user.getUsername());
		record.setCreatedDate(now);
		record.setLastUpdBy(user.getUsername());
		record.setLastUpdDate(now);
		this.mrtInventoryService.insertMrtInventory(record);
		
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsmrtstockin.html"));
		modelAndView.addObject("recordCreated", true);
		modelAndView.addObject("msisdnCreated", form.getMsisdn());
		return modelAndView;
	}
}
