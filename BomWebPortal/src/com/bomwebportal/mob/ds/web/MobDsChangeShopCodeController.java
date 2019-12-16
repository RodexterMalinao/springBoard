package com.bomwebportal.mob.ds.web;

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
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.service.StockService;
import com.bomwebportal.service.LoginService;

public class MobDsChangeShopCodeController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LoginService service;
	private StockService stockService;

	public LoginService getService() {
		return service;
	}
	public void setService(LoginService service) {
		this.service = service;
	}
	
	public StockService getStockService() {
		return stockService;
	}

	public void setStockService(StockService stockService) {
		this.stockService = stockService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		logger.info("ChangeShopCodeController formBackingObject called");
			
		BomSalesUserDTO dto = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		if (dto == null) {
			dto = new BomSalesUserDTO();
		}
		
		return dto;
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
	
		logger.info("ChangeShopCodeController is called!");
		
		BomSalesUserDTO dto = (BomSalesUserDTO) command;
		if ("UPDATE".equalsIgnoreCase(dto.getActionType())) {
			BomSalesUserDTO tempDTO = service.getCentreCdFromTeamCd(dto.getShopCd());
			dto.setAreaCd(tempDTO.getAreaCd());
			// modified by Joyce 20111215, add pilot status to distinguish active shop(s) for IMS
			dto.setPilotStatus(tempDTO.getPilotStatus());
			dto.setLtsPilotStatus(tempDTO.getLtsPilotStatus());
			dto.setMobPilotStatus(tempDTO.getMobPilotStatus()); // add by Joyce 20120903
			
			stockService.updateSalesShopCode(dto.getUsername(), dto.getAreaCd(), dto.getShopCd());
			BomSalesUserDTO sessionDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
			sessionDTO.setBomShopCd(tempDTO.getBomShopCd());
			sessionDTO.setAreaCd(dto.getAreaCd());
			sessionDTO.setShopCd(dto.getShopCd());
			sessionDTO.setChannelCd(tempDTO.getChannelCd());
			
			request.getSession().setAttribute("bomsalesuser", dto);
			
			return new ModelAndView(new RedirectView("mobdschangeshopcode.html"));// this
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
		List<StockDTO> shopCdList = new ArrayList<StockDTO>();
		//shopCdList = service.getShopList();
		shopCdList = stockService.getTeamList("SIS");

		referenceData.put("shopCdList", shopCdList);
		
		if (dto != null) {
			referenceData.put("actionType", dto.getActionType());
		}

		return referenceData;
	}
}
