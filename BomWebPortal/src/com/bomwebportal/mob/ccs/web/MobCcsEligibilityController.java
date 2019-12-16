package com.bomwebportal.mob.ccs.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.MobCcsEligibilityDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsEligibilityDTO.IdDocType;
import com.bomwebportal.mob.ccs.dto.ui.MobCcsEligibilityUI;
import com.bomwebportal.mob.ccs.service.MobCcsEligibilityService;
import com.bomwebportal.util.Utility;

public class MobCcsEligibilityController extends SimpleFormController {
	private MobCcsEligibilityService mobCcsEligibilityService;
	
	public MobCcsEligibilityService getMobCcsEligibilityService() {
		return mobCcsEligibilityService;
	}

	public void setMobCcsEligibilityService(MobCcsEligibilityService mobCcsEligibilityService) {
		this.mobCcsEligibilityService = mobCcsEligibilityService;
	}

	public Object formBackingObject(HttpServletRequest request) {
		MobCcsEligibilityUI form = new MobCcsEligibilityUI();
		if (StringUtils.isNotBlank(request.getParameter("idDocType"))) {
			try {
				form.setIdDocType(IdDocType.valueOf(request.getParameter("idDocType")));
			} catch (Exception e) {}
		}
		form.setIdDocNum(request.getParameter("idDocNum"));
		return form;
	}
	
	public Map<String, Object> referenceData(HttpServletRequest request) {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		referenceData.put("idDocTypes", IdDocType.values());

		try {
			IdDocType idDocType = IdDocType.valueOf(request.getParameter("idDocType"));
			String idDocNum = request.getParameter("idDocNum");
			if (idDocType != null && StringUtils.isNotBlank(idDocNum)) {
				boolean idDocNumValid;
				switch (idDocType) {
				case HKID:
					idDocNumValid = Utility.validateHKID(idDocNum);
					break;
				case PASS:
					idDocNumValid = Utility.validatePassport(idDocNum);
					break;
				case BS:
					idDocNumValid = Utility.validateHKBR(idDocNum);
					break;
				default:
					idDocNumValid = false;
				}
				referenceData.put("idDocNumValid", idDocNumValid);
				if (idDocNumValid) {
					List<MobCcsEligibilityDTO> resultList = this.mobCcsEligibilityService.getMobCcsEligibilityDTOALL(idDocType, idDocNum);
					referenceData.put("resultList", resultList);
				}
			}
		} catch (Exception e) {}
		
		referenceData.put("eligibilities", this.mobCcsEligibilityService.getMobCcsValuePropAssgnDTOALL());
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		MobCcsEligibilityUI form = (MobCcsEligibilityUI) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccseligibility.html"));
		
		Date now = new Date();
		MobCcsEligibilityDTO dto = new MobCcsEligibilityDTO();
		dto.setIdDocType(form.getIdDocType());
		dto.setIdDocNum(form.getIdDocNum());
		dto.setValuePropId(form.getEligibility());
		dto.setCreateBy(user.getUsername());
		dto.setCreateDate(now);
		dto.setLastUpdBy(user.getUsername());
		dto.setLastUpdDate(now);
		
		try {
			this.mobCcsEligibilityService.insertMobCcsEligibilityDTO(dto);
		} catch (AppRuntimeException e) {
			modelAndView.addObject("exception", true);
		}
		modelAndView.addObject("idDocType", form.getIdDocType() == null ? null : form.getIdDocType().toString());
		modelAndView.addObject("idDocNum", form.getIdDocNum());
		return modelAndView;
	}
}
