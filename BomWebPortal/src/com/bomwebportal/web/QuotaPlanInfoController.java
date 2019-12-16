package com.bomwebportal.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.ItemDisplayDTO;
import com.bomwebportal.dto.QuotaPlanInfoDTO;
import com.bomwebportal.dto.QuotaPlanInfoUI;
import com.bomwebportal.service.ItemDisplayService;
import com.bomwebportal.service.QuotaPlanInfoService;



public class QuotaPlanInfoController extends SimpleFormController {
	
	private QuotaPlanInfoService quotaPlanInfoService;
	private ItemDisplayService itemDisplayService;

	public QuotaPlanInfoService getQuotaPlanInfoService() {
		return quotaPlanInfoService;
	}



	public void setQuotaPlanInfoService(QuotaPlanInfoService quotaPlanInfoService) {
		this.quotaPlanInfoService = quotaPlanInfoService;
	}



	public ItemDisplayService getItemDisplayService() {
		return itemDisplayService;
	}



	public void setItemDisplayService(ItemDisplayService itemDisplayService) {
		this.itemDisplayService = itemDisplayService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		Map<String,QuotaPlanInfoUI> quotaPlanInfoMap= (Map<String,QuotaPlanInfoUI>) request.getSession().getAttribute("quotaPlanInfoMapSession");
		QuotaPlanInfoUI quotaPlanInfoUI = null;
		String appDate = (String) request.getSession().getAttribute("appDate");
		String vasitemId = (String) request.getParameter("vasitem");
		
		if (MapUtils.isEmpty(quotaPlanInfoMap) || !quotaPlanInfoMap.containsKey(vasitemId)){
			
		quotaPlanInfoUI = new QuotaPlanInfoUI();
		quotaPlanInfoUI.setItemId(vasitemId);
		
		}
		else{
			quotaPlanInfoUI = quotaPlanInfoMap.get(vasitemId);
		}
		List<QuotaPlanInfoDTO> quotaPlanInfoDtoList= quotaPlanInfoService.getQuotaPlanOptions(vasitemId, appDate);
		ItemDisplayDTO  itemDisplayDTO = itemDisplayService.getItemDisplay(Integer.parseInt(vasitemId), "en", "ITEM_SELECT");
		quotaPlanInfoUI.setQuotaPlanInfoDTO(quotaPlanInfoDtoList);
		quotaPlanInfoUI.setItemDisplayDTO(itemDisplayDTO);
		for (QuotaPlanInfoDTO quotaPlanInfoDto: quotaPlanInfoDtoList){
			if ("P".equals(quotaPlanInfoDto.getAutoTopupInd())){
				quotaPlanInfoUI.setIotPlan("Y");
				quotaPlanInfoUI.setMaxTopUpTimes(quotaPlanInfoDto.getMaxCount());
				quotaPlanInfoUI.setSuppressLocal(quotaPlanInfoDto.getSupressLocal());
				quotaPlanInfoUI.setSuppressRoam(quotaPlanInfoDto.getSupressRoam());
				quotaPlanInfoUI.setEngDesc(quotaPlanInfoDto.getEngDesc());
				quotaPlanInfoUI.setSelectedQuotaPlanOption(quotaPlanInfoDto.getBuoId());
				break;
			}
		}
		return quotaPlanInfoUI;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		QuotaPlanInfoUI quotaPlanInfoUI= (QuotaPlanInfoUI)command;
		
		if (!"Y".equalsIgnoreCase(quotaPlanInfoUI.getAutoTopUpInd()) && (!"Y".equals(quotaPlanInfoUI.getIotPlan()) && !"P".equalsIgnoreCase(quotaPlanInfoUI.getAutoTopUpInd()))){
			quotaPlanInfoUI.setAutoTopUpInd("N");
			quotaPlanInfoUI.setMaxTopUpTimes("");
			quotaPlanInfoUI.setSelectedQuotaPlanOption("");
			quotaPlanInfoUI.setEngDesc("");
		}
		Map<String,QuotaPlanInfoUI> quotaPlanInfoMap= (Map<String,QuotaPlanInfoUI>) request.getSession().getAttribute("quotaPlanInfoMapSession");
		
		if (quotaPlanInfoMap == null) {
			quotaPlanInfoMap = new HashMap<String, QuotaPlanInfoUI>();
		}
		
		if (StringUtils.isNotBlank(quotaPlanInfoUI.getMaxTopUpTimes())) {
			Integer maxTopUpTimes = Integer.parseInt(quotaPlanInfoUI.getMaxTopUpTimes());
			quotaPlanInfoUI.setMaxTopUpTimes(String.valueOf(maxTopUpTimes));
		}
		
		for (QuotaPlanInfoDTO quotaPlanInfoDTO : quotaPlanInfoUI.getQuotaPlanInfoDTO()) {
			if (quotaPlanInfoDTO.getBuoId().equals(quotaPlanInfoUI.getSelectedQuotaPlanOption())) {
				quotaPlanInfoUI.setEngDesc(quotaPlanInfoDTO.getEngDesc());
			}
		}
		quotaPlanInfoMap.put(quotaPlanInfoUI.getItemId(), quotaPlanInfoUI);
		request.getSession().setAttribute("quotaPlanInfoMapSession", quotaPlanInfoMap);
		
		return new ModelAndView(new RedirectView("closepopup.jsp"));
	}
}


