package com.bomwebportal.mob.ccs.web;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BasketParmDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.MobSponsorshipDTO;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.VasDetailService;

public class MobStaffSponsorshipInfoController extends SimpleFormController {
	

	private VasDetailService vasDetailService;
	
    public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
    	return null;
    }
    
    public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
    	
    	String action = request.getParameter("action");
    	String basketId = request.getParameter("basket");
    	if (StringUtils.isEmpty(basketId)) {
    		BasketDTO	basketDTO =(BasketDTO)MobCcsSessionUtil.getSession(request, "basket");
    		if (basketDTO != null) {
    			basketId = basketDTO.getBasketId();
    		}
    	}
    	
		MobSponsorshipDTO sessionSponsorshipDTO = (MobSponsorshipDTO)MobCcsSessionUtil.getSession(request, "mobSponsorshipDTO");
		MobSponsorshipDTO mobSponsorshipDTO = new MobSponsorshipDTO();
		if (sessionSponsorshipDTO != null) {
			try {
				BeanUtils.copyProperties(mobSponsorshipDTO, sessionSponsorshipDTO);
			} catch (Exception e) {	}
		}
		
		if (StringUtils.isNotEmpty(basketId)) {
			if (basketId.equals(mobSponsorshipDTO.getBasketId())) {
				return mobSponsorshipDTO;
			} else {
				mobSponsorshipDTO = new MobSponsorshipDTO();
				mobSponsorshipDTO.setBasketId(basketId);
				BasketParmDTO basketParm = vasDetailService.getBasketParmByType(basketId, "SPONSOR_LEVEL");
				if (basketParm != null) {
					String typeId = String.format("%02d",basketParm.getParmTypeId());
					String typeDesc = basketParm.getParmTypeVal();
					mobSponsorshipDTO.setSponsorLevel(typeId);
					mobSponsorshipDTO.setSponsorLevelDesc(typeDesc);
				}
				
			}
		}
		MobCcsSessionUtil.setSession(request, "mobSponsorshipDTO", mobSponsorshipDTO);
		if (!"ENQUIRY".equalsIgnoreCase(action)) {
			mobSponsorshipDTO.setDirty(true);
		}
		return mobSponsorshipDTO;
    	
    }
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		MobSponsorshipDTO mobSponsorshipDTO = (MobSponsorshipDTO)command;
		if (!errors.hasErrors()) {
			MobCcsSessionUtil.setSession(request, "mobSponsorshipDTO", mobSponsorshipDTO);
		}
		mobSponsorshipDTO.setDirty(false);

		return new ModelAndView(new RedirectView("closepopup.jsp"));
	}
}
