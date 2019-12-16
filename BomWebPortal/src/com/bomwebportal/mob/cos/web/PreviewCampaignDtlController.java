package com.bomwebportal.mob.cos.web;

import java.util.Date;
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
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.mob.cos.service.MobCosCampaignService;
import com.bomwebportal.util.Utility;

public class PreviewCampaignDtlController extends SimpleFormController{
	protected final Log logger = LogFactory.getLog(getClass());
	private MobCosCampaignService mobCosCampaignService;
	
	public MobCosCampaignService getMobCosCampaignService() {
		return mobCosCampaignService;
	}
	public void setMobCosCampaignService(MobCosCampaignService mobCosCampaignService) {
		this.mobCosCampaignService = mobCosCampaignService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		return new Object();
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		return new ModelAndView(new RedirectView("closepopup.jsp"));
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		//BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		String locale = RequestContextUtils.getLocale(request).toString();
		String basketId = request.getParameter("basketId");
		String appDate = Utility.date2String(new Date(), "dd/MM/yyyy");
		//String channelId = String.valueOf(bomsalesuser.getChannelId());
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		// MIP.P4 modification 
		BasketDTO basketDto =mobCosCampaignService.getBasketAttribute(basketId, appDate);
		String nature = basketDto.getNature();
		
		List<VasDetailDTO> bundleList = mobCosCampaignService.getPreviewBundleList(basketId, locale, appDate, user.getChannelCd(),nature);
		List<VasDetailDTO> simList = mobCosCampaignService.getPreviewSimList(basketId, locale, appDate, user.getChannelCd(), nature);
		//List<VasDetailDTO> optionList = mobCosCampaignService.getPreviewOptionList(basketId, locale, appDate, channelId, nature); // modified for MIP.P4

		referenceData.put("bundleList", bundleList);
		referenceData.put("simList", simList);
		//referenceData.put("optionList", optionList);
		return referenceData;
	}
}
