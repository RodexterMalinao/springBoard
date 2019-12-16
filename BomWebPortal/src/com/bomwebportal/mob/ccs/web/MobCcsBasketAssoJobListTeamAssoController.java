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

import com.bomwebportal.mob.ccs.dto.BasketAssoJobListTeamAssoDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListTeamAssoUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListTeamAssoService;
import com.bomwebportal.mob.ccs.service.MobCcsCcsChannelService;

public class MobCcsBasketAssoJobListTeamAssoController extends SimpleFormController {
	private MobCcsBasketAssoJobListTeamAssoService mobCcsBasketAssoJobListTeamAssoService;
	private MobCcsCcsChannelService mobCcsCcsChannelService;
	
	public MobCcsBasketAssoJobListTeamAssoService getMobCcsBasketAssoJobListTeamAssoService() {
		return mobCcsBasketAssoJobListTeamAssoService;
	}

	public void setMobCcsBasketAssoJobListTeamAssoService(
			MobCcsBasketAssoJobListTeamAssoService mobCcsBasketAssoJobListTeamAssoService) {
		this.mobCcsBasketAssoJobListTeamAssoService = mobCcsBasketAssoJobListTeamAssoService;
	}

	public MobCcsCcsChannelService getMobCcsCcsChannelService() {
		return mobCcsCcsChannelService;
	}

	public void setMobCcsCcsChannelService(
			MobCcsCcsChannelService mobCcsCcsChannelService) {
		this.mobCcsCcsChannelService = mobCcsCcsChannelService;
	}

	public Object formBackingObject(HttpServletRequest request)
		throws ServletException {

		logger.info("MobCcsBasketAssoJobListTeamAssoController formBackingObject called");
		
		BasketAssoJobListTeamAssoUI form = new BasketAssoJobListTeamAssoUI();
		form.setJobList(request.getParameter("jobList"));
		form.setTeamCd(request.getParameter("teamCd"));
		form.setCentreCd(request.getParameter("centreCd"));
		form.setChannelCd(request.getParameter("channelCd"));
		form.setShowEnded("true".equals(request.getParameter("showEnded")));
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsBasketAssoJobListTeamAssoController ReferenceData called");

		Map<String, List<?>> referenceData = new HashMap<String, List<?>>();

		referenceData.put("existJobListALL", this.mobCcsBasketAssoJobListTeamAssoService.getExistJobListALL());
		referenceData.put("existTeamCdALL", this.mobCcsCcsChannelService.getTeamCdALL());
		referenceData.put("existCentreCdALL", this.mobCcsCcsChannelService.getCentreCdALL());
		referenceData.put("existChannelCdALL", this.mobCcsCcsChannelService.getChannelCdALL());
		referenceData.put("ccsChannelALL", this.mobCcsCcsChannelService.getCcsChannelDTOALL());
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		Map model = errors.getModel();
		model.putAll(referenceData(request));
		BasketAssoJobListTeamAssoUI form = (BasketAssoJobListTeamAssoUI) command;
		
		ModelAndView modelAndView = new ModelAndView("mobccsbasketassojoblistteamasso", model);
		BasketAssoJobListTeamAssoDTO dto = new BasketAssoJobListTeamAssoDTO();
		dto.setJobList(form.getJobList());
		dto.setTeamCd(form.getTeamCd());
		dto.setCentreCd(form.getCentreCd());
		dto.setChannelCd(form.getChannelCd());
		modelAndView.addObject("resultList", this.mobCcsBasketAssoJobListTeamAssoService.getBasketAssoJobListTeamAssoDTOBySearch(dto, form.isShowEnded()));
		return modelAndView;
	}
}
