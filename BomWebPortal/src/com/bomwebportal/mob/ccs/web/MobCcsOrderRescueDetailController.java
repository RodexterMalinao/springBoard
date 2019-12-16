package com.bomwebportal.mob.ccs.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsIncidentCauseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsOrderRescueDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsIncidentCauseService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRescueService;

public class MobCcsOrderRescueDetailController implements Controller {
	private CodeLkupService codeLkupService;
	private MobCcsOrderRescueService mobCcsOrderRescueService;
	private MobCcsIncidentCauseService mobCcsIncidentCauseService;

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public MobCcsOrderRescueService getMobCcsOrderRescueService() {
		return mobCcsOrderRescueService;
	}

	public void setMobCcsOrderRescueService(
			MobCcsOrderRescueService mobCcsOrderRescueService) {
		this.mobCcsOrderRescueService = mobCcsOrderRescueService;
	}

	public MobCcsIncidentCauseService getMobCcsIncidentCauseService() {
		return mobCcsIncidentCauseService;
	}

	public void setMobCcsIncidentCauseService(
			MobCcsIncidentCauseService mobCcsIncidentCauseService) {
		this.mobCcsIncidentCauseService = mobCcsIncidentCauseService;
	}
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView modelAndView = new ModelAndView("mobccsorderrescuedetail");
		
		List<CodeLkupDTO> causeCodes = codeLkupService.getCodeLkupDTOALL("DEL_FAIL");
		modelAndView.addObject("causeCodes", causeCodes);
		
		String incidentNo = ServletRequestUtils.getStringParameter(request, "recordIncidentNo");
		
		MobCcsOrderRescueDTO record = this.mobCcsOrderRescueService.getMobCcsOrderRescueDTOByIncidentNo(incidentNo);
		modelAndView.addObject("record", record);
		if (record != null && record.getIncidentNo() != null) {
			List<MobCcsIncidentCauseDTO> incidentCauses = this.mobCcsIncidentCauseService.getMobCcsIncidentCause(record.getIncidentNo());
			modelAndView.addObject("incidentCauses", incidentCauses);
		}
		
		return modelAndView;
	}

}
