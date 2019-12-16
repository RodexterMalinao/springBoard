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
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListBskAssoDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListTeamAssoDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListBskAssoService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListTeamAssoService;

public class MobCcsBasketAssoJobListRemoveController extends SimpleFormController {
	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;
	private MobCcsBasketAssoJobListTeamAssoService mobCcsBasketAssoJobListTeamAssoService;
	private MobCcsBasketAssoJobListBskAssoService mobCcsBasketAssoJobListBskAssoService;
	
	public MobCcsBasketAssoJobListService getMobCcsBasketAssoJobListService() {
		return mobCcsBasketAssoJobListService;
	}

	public void setMobCcsBasketAssoJobListService(
			MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService) {
		this.mobCcsBasketAssoJobListService = mobCcsBasketAssoJobListService;
	}

	public MobCcsBasketAssoJobListTeamAssoService getMobCcsBasketAssoJobListTeamAssoService() {
		return mobCcsBasketAssoJobListTeamAssoService;
	}

	public void setMobCcsBasketAssoJobListTeamAssoService(
			MobCcsBasketAssoJobListTeamAssoService mobCcsBasketAssoJobListTeamAssoService) {
		this.mobCcsBasketAssoJobListTeamAssoService = mobCcsBasketAssoJobListTeamAssoService;
	}

	public MobCcsBasketAssoJobListBskAssoService getMobCcsBasketAssoJobListBskAssoService() {
		return mobCcsBasketAssoJobListBskAssoService;
	}

	public void setMobCcsBasketAssoJobListBskAssoService(
			MobCcsBasketAssoJobListBskAssoService mobCcsBasketAssoJobListBskAssoService) {
		this.mobCcsBasketAssoJobListBskAssoService = mobCcsBasketAssoJobListBskAssoService;
	}

	public Object formBackingObject(HttpServletRequest request)
		throws ServletException {

		logger.info("MobCcsBasketAssoJobListRemoveController formBackingObject called");
		
		BasketAssoJobListUI form = new BasketAssoJobListUI();
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsBasketAssoJobListRemoveController ReferenceData called");

		Map<String, List<String>> referenceData = new HashMap<String, List<String>>();

		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsbasketassojoblist.html"));
		
		String rowId = request.getParameter("rowId");
		if (StringUtils.isNotBlank(rowId)) {
			BasketAssoJobListDTO dto = this.mobCcsBasketAssoJobListService.getBasketAssoJobListDTO(rowId);
			if (dto != null) {
				boolean recordAssoNotYetEnded = true;
				List<BasketAssoJobListTeamAssoDTO> teamList = this.mobCcsBasketAssoJobListTeamAssoService.getBasketAssoJobListTeamAssoInRunningDTOByJobList(dto.getJobList());
				List<BasketAssoJobListBskAssoDTO> bskList = this.mobCcsBasketAssoJobListBskAssoService.getBasketAssoJobListBskAssoInRunningDTOByJobList(dto.getJobList());
				if (isEmpty(teamList) && isEmpty(bskList)) {
					recordAssoNotYetEnded = false;
					this.mobCcsBasketAssoJobListService.deleteBasketAssoJobListDTO(rowId);
				}
				modelAndView.addObject("recordAssoNotYetEnded", recordAssoNotYetEnded);
				modelAndView.addObject("recordJobList", dto.getJobList());
			}
		}
		return modelAndView;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
