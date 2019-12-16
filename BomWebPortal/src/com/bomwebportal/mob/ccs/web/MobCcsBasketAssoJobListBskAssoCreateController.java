package com.bomwebportal.mob.ccs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListBskAssoDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketDTO;
import com.bomwebportal.mob.ccs.dto.ui.BasketAssoJobListBskAssoEditUI;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListBskAssoService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoJobListService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoWBasketAttrService;
import com.bomwebportal.mob.ccs.service.MobCcsBasketAssoWBasketService;
import com.bomwebportal.util.Utility;

public class MobCcsBasketAssoJobListBskAssoCreateController extends SimpleFormController {
	private Log logger = LogFactory.getLog(getClass());

	private MobCcsBasketAssoJobListService mobCcsBasketAssoJobListService;
	private MobCcsBasketAssoJobListBskAssoService mobCcsBasketAssoJobListBskAssoService;
	private MobCcsBasketAssoWBasketService mobCcsBasketAssoWBasketService;
	private MobCcsBasketAssoWBasketAttrService mobCcsBasketAssoWBasketAttrService;
	
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

	public MobCcsBasketAssoWBasketService getMobCcsBasketAssoWBasketService() {
		return mobCcsBasketAssoWBasketService;
	}

	public void setMobCcsBasketAssoWBasketService(
			MobCcsBasketAssoWBasketService mobCcsBasketAssoWBasketService) {
		this.mobCcsBasketAssoWBasketService = mobCcsBasketAssoWBasketService;
	}

	public MobCcsBasketAssoWBasketAttrService getMobCcsBasketAssoWBasketAttrService() {
		return mobCcsBasketAssoWBasketAttrService;
	}

	public void setMobCcsBasketAssoWBasketAttrService(
			MobCcsBasketAssoWBasketAttrService mobCcsBasketAssoWBasketAttrService) {
		this.mobCcsBasketAssoWBasketAttrService = mobCcsBasketAssoWBasketAttrService;
	}

	public Object formBackingObject(HttpServletRequest request)
		throws ServletException {

		logger.info("MobCcsBasketAssoJobListBskAssoCreateController formBackingObject called");
		
		BasketAssoJobListBskAssoEditUI form = new BasketAssoJobListBskAssoEditUI();
		form.setJobList(request.getParameter("editJobList"));

		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsBasketAssoJobListBskAssoCreateController ReferenceData called");

		Map<String, Object> referenceData = new HashMap<String, Object>();

		//referenceData.put("existJobListALL", mobCcsBasketAssoJobListService.getExistJobListALL());

		String jobList = request.getParameter("editJobList");
		List<BasketAssoJobListBskAssoDTO> resultList = null;
		if (StringUtils.isNotBlank(jobList)) {
			resultList = this.mobCcsBasketAssoJobListBskAssoService.getBasketAssoJobListBskAssoInRunningDTOByJobList(jobList);
		}
		referenceData.put("resultList", resultList);
		
		//referenceData.put("wBasketALL", this.mobCcsBasketAssoWBasketService.getMobCcsBasketAssoWBasketDTOALL());
		referenceData.put("customerTierALL", this.mobCcsBasketAssoWBasketAttrService.getWBasketAttrCustomerTierDTOALL());
		referenceData.put("basketTypeALL", this.mobCcsBasketAssoWBasketAttrService.getWBasketAttrBasketTypeDTOALL());
		//referenceData.put("ratePlanALL", this.mobCcsBasketAssoWBasketAttrService.getWBasketAttrRatePlanDTOALL());
		//referenceData.put("brandModelALL", this.mobCcsBasketAssoWBasketAttrService.getWBasketAttrBrandModelDTOALL());
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
					throws Exception {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		BasketAssoJobListBskAssoEditUI form = (BasketAssoJobListBskAssoEditUI) command;

		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsbasketassojoblistbskassocreate.html"));
		if (StringUtils.isNotBlank(form.getJobList())) {
			modelAndView.addObject("editJobList", form.getJobList());
		}
		List<BasketAssoJobListBskAssoDTO> runningList = this.mobCcsBasketAssoJobListBskAssoService.getBasketAssoJobListBskAssoInRunningDTOByJobList(form.getJobList());
		for (String basketId : form.getBasketIds()) {
			if (StringUtils.isNotBlank(basketId)) {
				BasketAssoWBasketDTO wBasketDto = this.mobCcsBasketAssoWBasketService.getMobCcsBasketAssoWBasketDTO(basketId);
				if (wBasketDto != null) {
					if (!runningBasket(runningList, basketId)) {
						Date now = new Date();
						BasketAssoJobListBskAssoDTO dto = new BasketAssoJobListBskAssoDTO();
						dto.setJobList(form.getJobList());
						//dto.setCampaignId(form.getCampaignId());
						dto.setBasketId(wBasketDto.getBasketId());
						dto.setBasketDesc(wBasketDto.getBasketDesc());
						dto.setStartDate(Utility.string2Date(form.getStartDate()));
						dto.setEndDate(Utility.string2Date(form.getEndDate()));
						dto.setCreateBy(user.getUsername());
						dto.setCreateDate(now);
						dto.setLastUpdBy(user.getUsername());
						dto.setLastUpdDate(now);
						this.mobCcsBasketAssoJobListBskAssoService.insertBasketAssoJobListBskAssoDTO(dto);
					}
				}
			}
		}
		return modelAndView;
	}
	
	private boolean runningBasket(List<BasketAssoJobListBskAssoDTO> runningList, String basketId) {
		if (runningList != null) {
			for (BasketAssoJobListBskAssoDTO dto : runningList) {
				if (dto.getBasketId().equals(basketId)) {
					return true;
				}
			}
		}
		return false;
	}
}
