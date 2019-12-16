package com.bomwebportal.web;

import java.util.ArrayList;
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

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.service.SalesService;

public class ChangeShopCodeController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LoginService service;
	
	private SalesService salesservice;

	public LoginService getService() {
		return service;
	}
	public void setService(LoginService service) {
		this.service = service;
	}
	
	public SalesService getSalesservice() {
		return salesservice;
	}
	public void setSalesservice(SalesService salesservice) {
		this.salesservice = salesservice;
	}
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		logger.info("ChangeShopCodeController formBackingObject called");
			
		BomSalesUserDTO dto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
	
		if (dto == null) {
			dto = new BomSalesUserDTO();
		} else {
			if ("UPDATE".equalsIgnoreCase(dto.getActionType())) {
				BomSalesUserDTO tempDTO = service.getCentreCdFromTeamCd(dto.getShopCd());
				dto.setAreaCd(tempDTO.getAreaCd());
				dto.setBomShopCd(tempDTO.getBomShopCd());
				// modified 20111215, add pilot status to distinguish active shop(s) for IMS
				dto.setPilotStatus(tempDTO.getPilotStatus());
				dto.setLtsPilotStatus(tempDTO.getLtsPilotStatus());
				dto.setMobPilotStatus(tempDTO.getMobPilotStatus());
				salesservice.updateShopCd(dto.getShopCd(),dto.getUsername(),dto.getAreaCd());
			}
		}
		
		request.getSession().setAttribute("bomsalesuser", dto);

		return dto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
	
		logger.info("ChangeShopCodeController is called!");
		
		BomSalesUserDTO dto = (BomSalesUserDTO) command;
		
		if ("UPDATE".equalsIgnoreCase(dto.getActionType())) {
			return new ModelAndView(new RedirectView("changeshopcode.html"));// this
		} else {
			return new ModelAndView(new RedirectView("welcome.html"));// this
		}
	}

	protected Map referenceData(HttpServletRequest request)
			throws Exception {

		logger.info("ReferenceData called");
		
		BomSalesUserDTO dto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");

		Map referenceData = new HashMap();

		// shopCode
		List<String> shopCdList = new ArrayList<String>();
		shopCdList = service.getShopList();

		referenceData.put("shopCdList", shopCdList);
		
		if (dto != null) {
			referenceData.put("actionType", dto.getActionType());
		}

		return referenceData;
	}
}
