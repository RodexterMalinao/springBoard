package com.bomwebportal.mob.cos.web;

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
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.cos.dto.ui.AddCampaignBasketUI;
import com.bomwebportal.mob.cos.service.MobCosCampaignService;

public class AddCampaignBasketController extends SimpleFormController{
	protected final Log logger = LogFactory.getLog(getClass());
	private MobCosCampaignService mobCosCampaignService;
	
	public MobCosCampaignService getMobCosCampaignService() {
		return mobCosCampaignService;
	}
	public void setMobCosCampaignService(MobCosCampaignService mobCosCampaignService) {
		this.mobCosCampaignService = mobCosCampaignService;
	}
	
	public AddCampaignBasketUI formBackingObject(HttpServletRequest request)
			throws ServletException {
		AddCampaignBasketUI ui = new AddCampaignBasketUI();
		return ui;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		AddCampaignBasketUI ui = (AddCampaignBasketUI) command;
		String cpgId = request.getParameter("campId");
		String basketId = ui.getBasketId();
		String basketDesc = ui.getBasketDesc();
		String tier = "";
		String userId = bomsalesuser.getUsername();
		mobCosCampaignService.createCpgBasket(cpgId, basketId, basketDesc, tier, userId);
		return new ModelAndView(new RedirectView("closepopup.jsp"));
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		List<CodeLkupDTO> rpList = mobCosCampaignService.getRpList();
		referenceData.put("rpList", rpList);
		return referenceData;
	}
}