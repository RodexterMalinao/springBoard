package com.bomwebportal.mob.ccs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;

public class MobCcsBasketAssoJobListController extends SimpleFormController {
	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;
	private CodeLkupService codeLkupService;
	
	public MobCcsBasketAssoJobListService getMobCcsBasketAssoJobListService() {
		return mobCcsBasketAssoJobListService;
	}

	public void setMobCcsBasketAssoJobListService(
			MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService) {
		this.mobCcsBasketAssoJobListService = mobCcsBasketAssoJobListService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public Object formBackingObject(HttpServletRequest request)
		throws ServletException {

		logger.info("MobCcsBasketAssoJobListController formBackingObject called");
		
		BasketAssoJobListUI form = new BasketAssoJobListUI();
		form.setJobList(request.getParameter("jobList"));
		form.setChannelCd(request.getParameter("channelCd"));
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsBasketAssoJobListController ReferenceData called");

		Map<String, Object> referenceData = new HashMap<String, Object>();

		referenceData.put("existJobListALL", this.mobCcsBasketAssoJobListService.getExistJobListALL());

		referenceData.put("channelCdALL", this.codeLkupService.getCodeLkupDTOALL("CCS_CH"));
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		BasketAssoJobListUI form = (BasketAssoJobListUI) command;
		
		ModelAndView modelAndView = new ModelAndView("mobccsbasketassojoblist", model);
		if (StringUtils.isNotBlank(form.getJobList()) || StringUtils.isNotBlank(form.getChannelCd())) {
			List<BasketAssoJobListDTO> resultList = this.mobCcsBasketAssoJobListService.getBasketAssoJobListDTOByJobListAndChannelCd(form.getJobList(), form.getChannelCd());
			modelAndView.addObject("resultList", resultList);
		}
		return modelAndView;
	}
}
