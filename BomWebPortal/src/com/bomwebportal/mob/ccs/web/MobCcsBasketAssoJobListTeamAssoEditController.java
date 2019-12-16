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
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListTeamAssoDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListTeamAssoEditUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListTeamAssoService;
import com.bomwebportal.mob.ccs.service.MobCcsCcsChannelService;
import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.Utility;

public class MobCcsBasketAssoJobListTeamAssoEditController extends SimpleFormController {
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

		logger.info("MobCcsBasketAssoJobListTeamAssoEditController formBackingObject called");
		
		BasketAssoJobListTeamAssoEditUI form = new BasketAssoJobListTeamAssoEditUI();
		String rowId = request.getParameter("rowId");
		if (StringUtils.isNotBlank(rowId)) {
			BasketAssoJobListTeamAssoDTO dto = this.mobCcsBasketAssoJobListTeamAssoService.getBasketAssoJobListTeamAssoDTO(rowId);
			if (dto != null) {
				form.setJobList(dto.getJobList());
				form.setChannelCd(dto.getChannelCd());
				form.setTeamCd(dto.getTeamCd());
				form.setCentreCd(dto.getCentreCd());
				if (dto.getStartDate() != null) {
					form.setStartDate(Utility.date2String(dto.getStartDate(), BomWebPortalConstant.DATE_FORMAT));
				}
				if (dto.getEndDate() != null) {
					form.setEndDate(Utility.date2String(dto.getEndDate(), BomWebPortalConstant.DATE_FORMAT));
				}
				form.setRowId(rowId);
			}
		}
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsBasketAssoJobListTeamAssoEditController ReferenceData called");

		Map<String, List<String>> referenceData = new HashMap<String, List<String>>();

		referenceData.put("existJobListALL", this.mobCcsBasketAssoJobListTeamAssoService.getExistJobListALL());
		referenceData.put("existTeamCdALL", this.mobCcsCcsChannelService.getTeamCdALL());
		referenceData.put("existCentreCdALL", this.mobCcsCcsChannelService.getCentreCdALL());
		referenceData.put("existChannelCdALL", this.mobCcsCcsChannelService.getChannelCdALL());
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		BasketAssoJobListTeamAssoEditUI form = (BasketAssoJobListTeamAssoEditUI) command;

		Date endDate = null;
		if (StringUtils.isNotBlank(form.getEndDate())) {
			endDate = Utility.string2Date(form.getEndDate());
		}
		boolean recordUpdated = false;
		final Date now = new Date();
		BasketAssoJobListTeamAssoDTO dto = this.mobCcsBasketAssoJobListTeamAssoService.getBasketAssoJobListTeamAssoDTO(form.getRowId());
		if (endDate != null && dto != null && dto.getStartDate() != null
				&& ((dto.getEndDate() == null && endDate.after(dto.getStartDate()))
						|| (dto.getEndDate() != null && dto.getEndDate().after(now)))
					) {
			dto.setEndDate(endDate);
			dto.setLastUpdBy(user.getUsername());
			dto.setLastUpdDate(now);
			this.mobCcsBasketAssoJobListTeamAssoService.updateBasketAssoJobListTeamAssoDTOForEndAsso(dto);
			recordUpdated = true;
		}
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsbasketassojoblistteamassoedit.html"));
		modelAndView.addObject("rowId", form.getRowId());
		modelAndView.addObject("recordUpdated", recordUpdated);
		return modelAndView;
	}
}
