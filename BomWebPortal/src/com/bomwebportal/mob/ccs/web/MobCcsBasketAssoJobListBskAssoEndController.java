package com.bomwebportal.mob.ccs.web;

import java.util.Date;
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
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListBskAssoEditUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListBskAssoService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;
import com.bomwebportal.util.Utility;

public class MobCcsBasketAssoJobListBskAssoEndController extends SimpleFormController {

	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;
	private MobCcsBasketAssoJobListBskAssoService mobCcsBasketAssoJobListBskAssoService;
	
	public MobCcsBasketAssoJobListService getMobCcsBasketAssoJobListService() {
		return mobCcsBasketAssoJobListService;
	}

	public void setMobCcsBasketAssoJobListService(
			MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService) {
		this.mobCcsBasketAssoJobListService = mobCcsBasketAssoJobListService;
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

		logger.info("MobCcsBasketAssoJobListBskAssoEndController formBackingObject called");
		
		BasketAssoJobListBskAssoEditUI form = new BasketAssoJobListBskAssoEditUI();
		form.setJobList(request.getParameter("editJobList"));

		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsBasketAssoJobListBskAssoEndController ReferenceData called");

		Map<String, Object> referenceData = new HashMap<String, Object>();

		//referenceData.put("existJobListALL", mobCcsBasketAssoJobListService.getExistJobListALL());

		String jobList = request.getParameter("editJobList");
		List<BasketAssoJobListBskAssoDTO> resultList = null;
		if (StringUtils.isNotBlank(jobList)) {
			resultList = this.mobCcsBasketAssoJobListBskAssoService.getBasketAssoJobListBskAssoInRunningDTOByJobList(jobList);
		}
		referenceData.put("resultList", resultList);
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		BasketAssoJobListBskAssoEditUI form = (BasketAssoJobListBskAssoEditUI) command;
		
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsbasketassojoblistbskassoend.html"));
		if (StringUtils.isNotBlank(form.getJobList())) {
			modelAndView.addObject("editJobList", form.getJobList());
		}
		String[] rowIds = form.getRowIds();
		if (rowIds != null) {
			Date endDate = Utility.string2Date(form.getEndDate());
			for (String rowId : rowIds) {
				// validate through validator
				BasketAssoJobListBskAssoDTO dto = this.mobCcsBasketAssoJobListBskAssoService.getBasketAssoJobListBskAssoDTO(rowId);
				dto.setEndDate(endDate);
				dto.setLastUpdBy(user.getUsername());
				dto.setLastUpdDate(new Date());
				this.mobCcsBasketAssoJobListBskAssoService.updateBasketAssoJobListBskAssoDTOForEndAsso(dto);
			}
		}
		return modelAndView;
	}
}
