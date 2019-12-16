package com.bomwebportal.mob.ccs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListCreateUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoSalesAssignService;
import com.bomwebportal.util.Utility;

public class MobCcsBasketAssoJobListCreateController extends SimpleFormController {
	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;
	private MobCcsBasketAssoSalesAssignService mobCcsBasketAssoSalesAssignService;
	private CodeLkupService codeLkupService;
	
	public MobCcsBasketAssoJobListService getMobCcsBasketAssoJobListService() {
		return mobCcsBasketAssoJobListService;
	}

	public void setMobCcsBasketAssoJobListService(
			MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService) {
		this.mobCcsBasketAssoJobListService = mobCcsBasketAssoJobListService;
	}

	public MobCcsBasketAssoSalesAssignService getMobCcsBasketAssoSalesAssignService() {
		return mobCcsBasketAssoSalesAssignService;
	}

	public void setMobCcsBasketAssoSalesAssignService(
			MobCcsBasketAssoSalesAssignService mobCcsBasketAssoSalesAssignService) {
		this.mobCcsBasketAssoSalesAssignService = mobCcsBasketAssoSalesAssignService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public Object formBackingObject(HttpServletRequest request)
		throws ServletException {

		logger.info("MobCcsBasketAssoJobListCreateController formBackingObject called");
		
		BasketAssoJobListCreateUI form = new BasketAssoJobListCreateUI();
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsBasketAssoJobListCreateController ReferenceData called");

		Map<String, Object> referenceData = new HashMap<String, Object>();

		referenceData.put("channelCdALL", this.codeLkupService.getCodeLkupDTOALL("CCS_CH"));
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsbasketassojoblistcreate.html"));
		
		BasketAssoJobListCreateUI form = (BasketAssoJobListCreateUI) command;
		BasketAssoJobListDTO searchDto = new BasketAssoJobListDTO();
		searchDto.setJobList(form.getJobList());

		Date now = new Date();
		BasketAssoJobListDTO dto = new BasketAssoJobListDTO();
		dto.setJobList(form.getJobList());
		dto.setJobListDesc(form.getJobListDesc());
		dto.setChannelCd(form.getChannelCd());
		dto.setCreateBy(user.getUsername());
		dto.setCreateDate(now);
		dto.setLastUpdBy(user.getUsername());
		dto.setLastUpdDate(now);
		this.mobCcsBasketAssoJobListService.insertBasketAssoJobListDTO(dto);
		modelAndView.addObject("recordInsert", true);
		
		return modelAndView;
	}
}
