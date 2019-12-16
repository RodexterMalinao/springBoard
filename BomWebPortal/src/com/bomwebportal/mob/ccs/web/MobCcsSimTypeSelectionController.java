package com.bomwebportal.mob.ccs.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.SuperOrderDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.VasDetailService;

public class MobCcsSimTypeSelectionController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private VasDetailService vasDetailService;

	/**
	 * @return the vasDetailService
	 */
	public VasDetailService getVasDetailService() {
		return vasDetailService;
	}

	/**
	 * @param vasDetailService the vasDetailService to set
	 */
	public void setVasDetailService(VasDetailService vasDetailService) {
		this.vasDetailService = vasDetailService;
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		return new VasDetailDTO();
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
		    HttpServletResponse response, Object command, BindException errors)
		    throws ServletException, AppRuntimeException {
		
		String nextView = (String) request.getAttribute("nextView");
		logger.info("Next View: " + nextView);
		
		VasDetailDTO dto = (VasDetailDTO) command;
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("ReferenceData called");
		Map referenceData = new HashMap<String, List<String>>();

		SuperOrderDTO superOrderDto = (SuperOrderDTO) request.getSession().getAttribute("superOrderDto");
		
		// MIP.P4 modification
		String basketId=request.getParameter("basket");
		String appDate = (String) request.getSession().getAttribute("appDate");
		VasDetailDTO vasDetailSession = (VasDetailDTO)MobCcsSessionUtil.getSession(request, "vasDetail");
		BasketDTO basketDto =vasDetailService.getBasketAttribute(basketId, appDate);
		String nature = basketDto.getNature();
		List<VasDetailDTO> simSelectedDetailList = vasDetailService.getSimTypeSelection("en", "14/02/2012", "100000408", "", superOrderDto.getOrderShopCd() , null, null, nature);
		
		referenceData.put("vasDetailList", simSelectedDetailList);
		
		return referenceData;
	}
}
