package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
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
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListTeamAssoDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListTeamAssoEditUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListTeamAssoService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoSalesAssignService;
import com.bomwebportal.util.Utility;

public class MobCcsBasketAssoJobListTeamAssoCreateController extends SimpleFormController {
	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;
	private MobCcsBasketAssoJobListTeamAssoService mobCcsBasketAssoJobListTeamAssoService;
	private MobCcsBasketAssoSalesAssignService mobCcsBasketAssoSalesAssignService;
	
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

	public MobCcsBasketAssoSalesAssignService getMobCcsBasketAssoSalesAssignService() {
		return mobCcsBasketAssoSalesAssignService;
	}

	public void setMobCcsBasketAssoSalesAssignService(
			MobCcsBasketAssoSalesAssignService mobCcsBasketAssoSalesAssignService) {
		this.mobCcsBasketAssoSalesAssignService = mobCcsBasketAssoSalesAssignService;
	}
	
	public Object formBackingObject(HttpServletRequest request)
		throws ServletException {

		logger.info("MobCcsBasketAssoJobListTeamAssoEditController formBackingObject called");
		
		BasketAssoJobListTeamAssoEditUI form = new BasketAssoJobListTeamAssoEditUI();
		String jobList = request.getParameter("recordJobList");
		if (StringUtils.isNotBlank(jobList)) {
			BasketAssoJobListDTO jobListDto = this.mobCcsBasketAssoJobListService.getBasketAssoJobListDTOByJobList(jobList);
			form.setChannelCd(jobListDto.getChannelCd());
		}
		String centreCd = request.getParameter("recordCentreCd");
		String teamCd = request.getParameter("recordTeamCd");
		
		form.setJobList(jobList);
		form.setCentreCd(centreCd);
		form.setTeamCd(teamCd);
		
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsBasketAssoJobListTeamAssoEditController ReferenceData called");

		Map<String, Object> referenceData = new HashMap<String, Object>();

		referenceData.put("existJobListALL", this.mobCcsBasketAssoJobListService.getExistJobListALL());

		String jobList = request.getParameter("recordJobList");
		//String channelCd = request.getParameter("recordChannelCd");
		String centreCd = request.getParameter("recordCentreCd");
		String teamCd = request.getParameter("recordTeamCd");
		
		if (StringUtils.isBlank(teamCd) && StringUtils.isBlank(centreCd) && StringUtils.isBlank(jobList)) {
			
		} else {
			BasketAssoJobListDTO jobListDto = this.mobCcsBasketAssoJobListService.getBasketAssoJobListDTOByJobList(jobList);
			if (StringUtils.isBlank(centreCd) && StringUtils.isNotBlank(jobList)) {
				List<String> centreCdALL = this.mobCcsBasketAssoSalesAssignService.getExistSalesAssignCentreCdALL(jobListDto.getChannelCd());
				centreCdALL.add(0, "ALL");
				referenceData.put("existSalesAssignCentreCdALL", centreCdALL);
			} else {
				List<String> centreCdALL = this.mobCcsBasketAssoSalesAssignService.getExistSalesAssignCentreCdALL(jobListDto.getChannelCd());
				centreCdALL.add(0, "ALL");
				referenceData.put("existSalesAssignCentreCdALL", centreCdALL);
				
				List<String> teamCdALL = new ArrayList<String>();
				if ("ALL".equals(centreCd)) {
					teamCdALL = new ArrayList<String>();
				} else {
					teamCdALL = this.mobCcsBasketAssoSalesAssignService.getExistSalesAssignTeamCdALL(jobListDto.getChannelCd(), centreCd);
				}
				teamCdALL.add(0, "ALL");
				referenceData.put("existSalesAssignTeamCdALL", teamCdALL);
			}
		}
//		referenceData.put("existChannelCdALL", this.mobCcsBasketAssoJobListService.getExistChannelCdALL());
		
//		referenceData.put("existSalesAssignTeamCdALL", this.mobCcsBasketAssoSalesAssignService.getExistSalesAssignTeamCdALL());
//		referenceData.put("existSalesAssignCentreCdALL", this.mobCcsBasketAssoSalesAssignService.getExistSalesAssignCentreCdALL());
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		BasketAssoJobListTeamAssoEditUI form = (BasketAssoJobListTeamAssoEditUI) command;

		ModelAndView modelAndView;
		
		if (this.isFormCompleted(form)) {
			Date now = new Date();
			BasketAssoJobListDTO jobListDto = this.mobCcsBasketAssoJobListService.getBasketAssoJobListDTOByJobList(form.getJobList());

			BasketAssoJobListTeamAssoDTO dto = new BasketAssoJobListTeamAssoDTO();
			dto.setJobList(form.getJobList());
			dto.setChannelCd(jobListDto.getChannelCd());
			dto.setCentreCd(form.getCentreCd());
			dto.setTeamCd(form.getTeamCd());
			dto.setStartDate(Utility.string2Date(form.getStartDate()));
			if (StringUtils.isNotBlank(form.getEndDate())) {
				dto.setEndDate(Utility.string2Date(form.getEndDate()));
			}
			dto.setCreateBy(user.getUsername());
			dto.setCreateDate(now);
			dto.setLastUpdBy(user.getUsername());
			dto.setLastUpdDate(now);
			
			this.mobCcsBasketAssoJobListTeamAssoService.insertBasketAssoJobListTeamAssoDTO(dto);
			
			List<BasketAssoJobListTeamAssoDTO> list = this.mobCcsBasketAssoJobListTeamAssoService.getBasketAssoJobListTeamAssoDTOBySearch(dto, true);
			if (list != null && list.size() == 1) {
				BasketAssoJobListTeamAssoDTO createdDto = list.get(0);
				modelAndView = new ModelAndView(new RedirectView("mobccsbasketassojoblistteamassoedit.html"));
				modelAndView.addObject("rowId", createdDto.getRowId());
				modelAndView.addObject("recordInsert", true);
			} else {
				modelAndView = new ModelAndView(new RedirectView("mobccsbasketassojoblistteamassocreate.html"));
				modelAndView.addObject("recordInsert", true);
			}
		} else {
			modelAndView = new ModelAndView(new RedirectView("mobccsbasketassojoblistteamassocreate.html"));
			modelAndView.addObject("recordJobList", form.getJobList());
			modelAndView.addObject("recordChannelCd", form.getChannelCd());
			modelAndView.addObject("recordTeamCd", form.getTeamCd());
			modelAndView.addObject("recordCentreCd", form.getCentreCd());
		}
		return modelAndView;
	}
	
	private boolean isFormCompleted(BasketAssoJobListTeamAssoEditUI form) {
		return StringUtils.isNotBlank(form.getJobList()) && StringUtils.isNotBlank(form.getCentreCd()) && StringUtils.isNotBlank(form.getTeamCd());
	}
}
