package com.bomwebportal.mob.ccs.web;

import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListCreateUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsBasketAssoJobListEditController extends SimpleFormController {
	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;
	
	public MobCcsBasketAssoJobListService getMobCcsBasketAssoJobListService() {
		return mobCcsBasketAssoJobListService;
	}

	public void setMobCcsBasketAssoJobListService(
			MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService) {
		this.mobCcsBasketAssoJobListService = mobCcsBasketAssoJobListService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		logger.info("MobCcsBasketAssoJobListEditController formBackingObject called");
		
		String rowId = ServletRequestUtils.getRequiredStringParameter(request, "rowId");

		BasketAssoJobListCreateUI form = new BasketAssoJobListCreateUI();
		if (StringUtils.isNotBlank(rowId)) {
			BasketAssoJobListDTO jobListDto = this.mobCcsBasketAssoJobListService.getBasketAssoJobListDTO(rowId);
			if (jobListDto != null) {
				form.setJobList(jobListDto.getJobList());
				form.setJobListDesc(jobListDto.getJobListDesc());
				form.setChannelCd(jobListDto.getChannelCd());
				form.setCreateBy(jobListDto.getCreateBy());
				form.setCreateDate(jobListDto.getCreateDate());
				form.setLastUpdBy(jobListDto.getLastUpdBy());
				form.setLastUpdDate(jobListDto.getLastUpdDate());
				form.setRowId(jobListDto.getRowId());
			}
		}
		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsbasketassojoblistedit.html"));
		
		BasketAssoJobListCreateUI form = (BasketAssoJobListCreateUI) command;
		BasketAssoJobListDTO dto = this.mobCcsBasketAssoJobListService.getBasketAssoJobListDTO(form.getRowId());
		
		if (dto != null) {
			Date now = new Date();
			dto.setJobListDesc(form.getJobListDesc());
			dto.setLastUpdBy(user.getUsername());
			dto.setLastUpdDate(now);
			this.mobCcsBasketAssoJobListService.updateBasketAssoJobListDTODesc(dto);
			modelAndView.addObject("rowId", dto.getRowId());
			modelAndView.addObject("recordUpdated", true);
		}
		
		return modelAndView;
	}

}
