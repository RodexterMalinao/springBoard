package com.bomwebportal.mob.cos.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.mob.cos.dto.MobCosCampaignVasDTO;
import com.bomwebportal.mob.cos.dto.ui.EditCampaignVasUI;
import com.bomwebportal.mob.cos.service.MobCosCampaignService;

public class EditCampaignVasController extends SimpleFormController{
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
		EditCampaignVasUI ui = new EditCampaignVasUI();
		String basketId = request.getParameter("basketId");
		String locale = RequestContextUtils.getLocale(request).toString();
		List<MobCosCampaignVasDTO> basVasList = (List<MobCosCampaignVasDTO>)request.getSession().getAttribute("basVasList_" + basketId);
		if(CollectionUtils.isEmpty(basVasList)) {
			//Get whole vas list
			basVasList = mobCosCampaignService.getCpgVasList(locale);
			//Get DB basket vas list
			List<MobCosCampaignVasDTO> dbBasVasList = new ArrayList<MobCosCampaignVasDTO> ();
			if(!StringUtils.isEmpty(basketId)) {
				dbBasVasList = mobCosCampaignService.getBasVasList(basketId);
			}
			//save vas action according to DB
			for(MobCosCampaignVasDTO basVas : basVasList) {
				boolean action = false;
				if(!CollectionUtils.isEmpty(dbBasVasList)) {
					for(MobCosCampaignVasDTO dbVas : dbBasVasList) {
						if(basVas.getItemId().equals(dbVas.getItemId()))	action = true;
					}
					basVas.setAction(action);
				} else {
					basVas.setAction(false);
				}
			}
		}
		
		ui.setBasVasList(basVasList);
		return ui;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {
		String basketId = request.getParameter("basketId");
		EditCampaignVasUI ui = (EditCampaignVasUI) command;
		
		List<MobCosCampaignVasDTO> basVasList = ui.getBasVasList();
		for(MobCosCampaignVasDTO vas : basVasList) {
			vas.setActionType(null);
		}
		
		//Get FE basket vas list
		List<MobCosCampaignVasDTO> sbBasVasList = new ArrayList<MobCosCampaignVasDTO> ();
		for(MobCosCampaignVasDTO vas : basVasList) {
			if(vas.getAction()) sbBasVasList.add(vas);
		}
		
		//Get DB basket vas list
		List<MobCosCampaignVasDTO> dbBasVasList = new ArrayList<MobCosCampaignVasDTO> ();
		if(!StringUtils.isEmpty(basketId)) {
			dbBasVasList = mobCosCampaignService.getBasVasList(basketId);
		}
		
		//Set action type to change vas
		List<MobCosCampaignVasDTO> chgVasList = new ArrayList<MobCosCampaignVasDTO> ();
		if(!CollectionUtils.isEmpty(dbBasVasList) && !CollectionUtils.isEmpty(sbBasVasList)) {
			List<String> dbvasItemList = new ArrayList<String> ();
			List<String> sbvasItemList = new ArrayList<String> ();
			for(MobCosCampaignVasDTO dbVas : dbBasVasList) {
				dbvasItemList.add(dbVas.getItemId());
			}
			for(MobCosCampaignVasDTO sbVas : sbBasVasList) {
				sbvasItemList.add(sbVas.getItemId());
			}
			
			for(MobCosCampaignVasDTO dbVas : dbBasVasList) {
				MobCosCampaignVasDTO vas = dbVas;
				if(!sbvasItemList.contains(dbVas.getItemId())) {
					vas.setActionType("D");
					vas.setAction(false);
					chgVasList.add(vas);
				}
			}
			for(MobCosCampaignVasDTO sbVas : sbBasVasList) {
				MobCosCampaignVasDTO vas = sbVas;
				if(!dbvasItemList.contains(sbVas.getItemId())) {
					vas.setActionType("A");
					vas.setAction(true);
					chgVasList.add(vas);
				}
			}
		} else if (!CollectionUtils.isEmpty(dbBasVasList) && CollectionUtils.isEmpty(sbBasVasList)) {
			for(MobCosCampaignVasDTO dbVas : dbBasVasList) {
				MobCosCampaignVasDTO vas = dbVas;
				vas.setActionType("D");
				vas.setAction(false);
				chgVasList.add(vas);
			}
		} else if (!CollectionUtils.isEmpty(sbBasVasList) && CollectionUtils.isEmpty(dbBasVasList)) {
			for(MobCosCampaignVasDTO sbVas : sbBasVasList) {
				MobCosCampaignVasDTO vas = sbVas;
				vas.setActionType("A");
				vas.setAction(true);
				chgVasList.add(vas);
			}
		}
		
		//save change vas
		if(!CollectionUtils.isEmpty(chgVasList)) {
			for(int i = 0; i < basVasList.size(); i++) {
				MobCosCampaignVasDTO basVas = basVasList.get(i);
				for(MobCosCampaignVasDTO chgVas : chgVasList) {
					if(basVas.getItemId().equals(chgVas.getItemId())) {
						basVasList.set(i, chgVas);
					}
				}
			}
		}
		
		request.getSession().setAttribute("basVasList_" + basketId, basVasList);
		return new ModelAndView(new RedirectView("closepopup.jsp"));
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		String locale = RequestContextUtils.getLocale(request).toString();
		List<MobCosCampaignVasDTO> cpgVasList = mobCosCampaignService.getCpgVasList(locale);
		referenceData.put("cpgVasList", cpgVasList);
		return referenceData;
	}
}
