package com.bomwebportal.mob.ds.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtStatusService.MsisdnStatus;
import com.bomwebportal.mob.ccs.service.StockService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.ds.dto.MobDsMrtManagementDTO;
import com.bomwebportal.mob.ds.service.MobDsMrtManagementService;

public class MobDsMrtManagementController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobDsMrtManagementService mobDsMrtManagementService;
	private StockService stockService;
	private CodeLkupService codeLkupService;
	
	public MobDsMrtManagementService getMobDsMrtManagementService() {
		return mobDsMrtManagementService;
	}
	public void setMobDsMrtManagementService(
			MobDsMrtManagementService mobDsMrtManagementService) {
		this.mobDsMrtManagementService = mobDsMrtManagementService;
	}
	public StockService getStockService() {
		return stockService;
	}
	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}
	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		MobDsMrtManagementDTO sessionMrtDto = (MobDsMrtManagementDTO) MobCcsSessionUtil.getSession(request, "mrtManagement");
		if (sessionMrtDto == null) {
			sessionMrtDto = new MobDsMrtManagementDTO();
			if (salesUserDto.getChannelId() == 10) {
				sessionMrtDto.setStaffId(salesUserDto.getUsername());
				sessionMrtDto.setSearchStatus("2");
				
				sessionMrtDto.setSearchStoreCode(salesUserDto.getAreaCd());
				sessionMrtDto.setSearchTeamCode(salesUserDto.getShopCd());
				}
		}
		
		MobCcsSessionUtil.setSession(request, "mrtManagement", sessionMrtDto);
		return sessionMrtDto;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {
		
		MobDsMrtManagementDTO dto = (MobDsMrtManagementDTO) command;
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		if ("SEARCH".equals(dto.getActionType())){
			dto.setSelectMsisdn(null);
			List<MobDsMrtManagementDTO> mrtSummaryList = mobDsMrtManagementService.getMrtSummaryList(dto, salesUserDto.getUsername(), salesUserDto.getCategory(), salesUserDto.getChannelCd());
			dto.setMrtSummaryList(mrtSummaryList);
			
		} else if ("ASSIGN".equals(dto.getActionType())) {
			int result = mobDsMrtManagementService.updateMrtInventory(dto, salesUserDto.getUsername());
			if (result == dto.getSelectMsisdn().size()) {
				dto.setActionType("SUCCESS");
				dto.setSelectMsisdn(null);
			} else {
				dto.setActionType("ERROR");
			}
			List<MobDsMrtManagementDTO> mrtSummaryList = mobDsMrtManagementService.getMrtSummaryList(dto, salesUserDto.getUsername(), salesUserDto.getCategory(), salesUserDto.getChannelCd());
			dto.setMrtSummaryList(mrtSummaryList);
		}
		
		MobCcsSessionUtil.setSession(request, "mrtManagement", dto);
		return new ModelAndView(new RedirectView("mobdsmrtmanagement.html"));
	}
	
	public Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		
		Map<String, Object> referenceData = new HashMap<String, Object>();
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		
		MobDsMrtManagementDTO sessionMrtDto = (MobDsMrtManagementDTO) MobCcsSessionUtil.getSession(request, "mrtManagement");
		
		if (sessionMrtDto == null) {
			sessionMrtDto = new MobDsMrtManagementDTO();
		}
		
		//Store Code and Team Code
		referenceData.put("storeList", stockService.getUserMultiStoreCode(salesUserDto.getUsername(), salesUserDto.getChannelId()));
		referenceData.put("teamListAll", stockService.getUSerMultiStoreTeamCode(salesUserDto.getUsername(), salesUserDto.getChannelId()));
		referenceData.put("msisdnlvlList", mobDsMrtManagementService.getMsisdnlvlList());
		referenceData.put("msisdnStatusList", Arrays.asList(MsisdnStatus.values()));
		referenceData.put("numTypeList", codeLkupService.getCodeLkupDTOALL("MIP_NUM_TYPE"));
		
		if ("SUPERVISOR".equalsIgnoreCase(salesUserDto.getCategory()) && "MDV".equalsIgnoreCase(salesUserDto.getChannelCd())) {
			referenceData.put("mdvTeamList", mobDsMrtManagementService.getMdvTeamList()); 
		}
		
		return referenceData;
	}

}
