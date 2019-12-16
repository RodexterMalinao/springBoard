package com.bomwebportal.mob.cos.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignDtlDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignHdrDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignVasDTO;
import com.bomwebportal.mob.cos.dto.ui.MobCosCampaignUI;
import com.bomwebportal.mob.cos.service.MobCosCampaignService;
import com.bomwebportal.util.Utility;

public class MobCosCampaignController extends SimpleFormController {

	protected final Log logger = LogFactory.getLog(getClass());
	private MobCosCampaignService mobCosCampaignService;
	
	public MobCosCampaignService getMobCosCampaignService() {
		return mobCosCampaignService;
	}
	public void setMobCosCampaignService(MobCosCampaignService mobCosCampaignService) {
		this.mobCosCampaignService = mobCosCampaignService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		MobCosCampaignUI campaignUI = (MobCosCampaignUI) request.getSession().getAttribute("mobCosCampaignSession");
	    HttpSession session = request.getSession();
	    
	    if (campaignUI == null){
	    	campaignUI = new MobCosCampaignUI();
	    	session.setAttribute("mobCosCampaignSession", campaignUI);
		} else{
			if(campaignUI.getCpgHdrDTO() == null)	campaignUI.setCpgHdrDTO(new MobCosCampaignHdrDTO());
			if(campaignUI.getCpgDtlDTOList() == null)	campaignUI.setCpgDtlDTOList(new ArrayList<MobCosCampaignDtlDTO>());
		}
	    return campaignUI;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, 
			Object command, BindException errors) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("MobCosCampaignController onSubmit is called!");
		}
		BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		MobCosCampaignUI ui = (MobCosCampaignUI) command;
		Map model = errors.getModel();
		model.putAll(referenceData(request));	 
		ModelAndView modelAndView =  new ModelAndView(new RedirectView("mobcoscampaign.html"));
		request.getSession().setAttribute("success", "");
		
		if ("SELECT".equalsIgnoreCase(ui.getActionType())) {
			loadCampaign(ui);
		} else if ("CREATE".equalsIgnoreCase(ui.getActionType())) {
			//Prepare parameter
			String cpgTitle = ui.getSecCpgTitle().trim();
			String cpgName = ui.getSecCpgName().trim();
			String handsetDesc = StringUtils.isEmpty(ui.getSecHandset()) ? "" : ui.getSecHandset().trim();
			String channelId = String.valueOf(bomsalesuser.getChannelId());
			String userId = bomsalesuser.getUsername();
			//Save in DB
			int nCpgId = mobCosCampaignService.createCpgHdr(cpgTitle, cpgName, handsetDesc, bomsalesuser.getUsername());
			mobCosCampaignService.createCpgChannelAssgn(channelId, String.valueOf(nCpgId), null, userId);
			ui.setCurCpgId(String.valueOf(nCpgId));
			loadCampaign(ui);
		} else if ("GEN_BASKET_ID".equalsIgnoreCase(ui.getActionType())) {
			validateCampaign(ui, errors, request);
			if (!errors.hasErrors()) {
				saveCampaign(ui, request);
				MobCosCampaignDtlDTO curDtlDTO = ui.getCpgDtlDTOList().get(Integer.valueOf(ui.getCurBasketSeq()) - 1);
				//Save in DB
				String genBasketId = mobCosCampaignService.genBasketId(ui.getCurCpgId(), ui.getCurBasketSeq(), curDtlDTO.getSourceBasketId(), 
						curDtlDTO.getBasketDesc(), bomsalesuser.getUsername());
				if (genBasketId != null) {
					request.getSession().setAttribute("success", "Basket is generated.");
				} else {
					request.getSession().setAttribute("success", "Problem found when generating basket. Basket is not generated.");
				}
				loadCampaign(ui);
			} else {
				request.getSession().setAttribute("success", "Error found and Basket is not generated.");
			}
		} else if ("ACTIVE_BASKET".equalsIgnoreCase(ui.getActionType())) {
			validateCampaign(ui, errors, request);
			if (!errors.hasErrors()) {
				saveCampaign(ui, request);
				MobCosCampaignDtlDTO curDtlDTO = ui.getCpgDtlDTOList().get(Integer.valueOf(ui.getCurBasketSeq()) - 1);
				boolean activeSuccess = mobCosCampaignService.activeCpgBasket(ui.getCurCpgId(), curDtlDTO.getBasketId(), bomsalesuser.getUsername());
				//curDtlDTO.setActiveInd(activeSuccess ? "Y" : "N");
				//ui.getCpgDtlDTOList().set(Integer.valueOf(ui.getCurBasketSeq()) - 1, curDtlDTO);
				if (activeSuccess) {
					request.getSession().setAttribute("success", "Basket is Activated.");
				} else {
					request.getSession().setAttribute("success", "Problem found when activating. Basket is not activated.");
				}
				loadCampaign(ui);
			} else {
				MobCosCampaignDtlDTO curDtlDTO = ui.getCpgDtlDTOList().get(Integer.valueOf(ui.getCurBasketSeq()) - 1);
				curDtlDTO.setActiveInd("N");
				request.getSession().setAttribute("success", "Error found and Basket is not Activated.");
			}
		} else if ("DEACTIVE_BASKET".equalsIgnoreCase(ui.getActionType())) {
			MobCosCampaignDtlDTO curDtlDTO = ui.getCpgDtlDTOList().get(Integer.valueOf(ui.getCurBasketSeq()) - 1);
			boolean deactiveSuccess = mobCosCampaignService.deactiveCpgBasket(ui.getCurCpgId(), curDtlDTO.getBasketId(), bomsalesuser.getUsername());
			curDtlDTO.setActiveInd("N");
			//ui.getCpgDtlDTOList().set(Integer.valueOf(ui.getCurBasketSeq()) - 1, curDtlDTO);
			request.getSession().setAttribute("success", "Basket is Deactivated.");
			if (!deactiveSuccess) {
				loadCampaign(ui);
			}
		}
		
		//Update Campaign
		else if("UPDATE".equalsIgnoreCase(ui.getActionType())){
			validateCampaign(ui, errors, request);
			if (errors.hasErrors()) {
				request.getSession().setAttribute("success", "Error found and Campaign is not saved.");
			} else {
				saveCampaign(ui, request);
				request.getSession().setAttribute("success", "Campaign is saved successfully.");
			}
		} else if ("ADDBASKET".equalsIgnoreCase(ui.getActionType())) {
			TimeUnit.MILLISECONDS.sleep(500); //Dirty Fix to synchronize Main Controller and Add Basket Controller. Wait until basket is inserted in DB
			loadCampaign(ui);
		}
		
		request.getSession().setAttribute("mobCosCampaignSession", ui);
		
		if (errors.hasErrors()) {
			return new ModelAndView("mobcoscampaign", model);
		} 
		return modelAndView;
	}
	
	private void validateCampaign(MobCosCampaignUI ui, BindException errors, HttpServletRequest request) {
		
		if(StringUtils.isEmpty(ui.getCpgHdrDTO().getCampaignTitle())) {
			errors.rejectValue("cpgHdrDTO.campaignTitle", "", "Please input Campaign Title");
		}
		if(StringUtils.isEmpty(ui.getCpgHdrDTO().getCampaignName())) {
			errors.rejectValue("cpgHdrDTO.campaignName", "", "Please input Campaign Name");
		}

		Map<String, List<MobCosCampaignDtlDTO>> actAeffTierList = new HashMap<String, List<MobCosCampaignDtlDTO>>();
		for(MobCosCampaignDtlDTO dtl : ui.getCpgDtlDTOList()) {
			String basketId = dtl.getBasketId();
			String tier = dtl.getTier();
			
			if("Y".equals(dtl.getActiveInd()) && !StringUtils.isEmpty(basketId)) {
				Date startDate = Utility.string2Date(dtl.getEffStartDate());
				if (startDate == null) {
					errors.rejectValue("cpgDtlDTOList[" + ui.getCpgDtlDTOList().indexOf(dtl) + "].tier", "dummy", "Please select effect start date for basket seq " + dtl.getCampaignBasketSeq() + " first!");
				} else if(StringUtils.isEmpty(tier)) {
					errors.rejectValue("cpgDtlDTOList[" + ui.getCpgDtlDTOList().indexOf(dtl) + "].tier", "dummy", "Please select tier for basket " + dtl.getCampaignBasketSeq() + " first!");
				} else {
					if (!actAeffTierList.containsKey(tier)) {
						actAeffTierList.put(tier, new ArrayList<MobCosCampaignDtlDTO>());
					}
					actAeffTierList.get(tier).add(dtl);
				}
			}
		}
		for (String tier: actAeffTierList.keySet()) {
			for (int i=0; i<actAeffTierList.get(tier).size(); i++) {
				MobCosCampaignDtlDTO dtl1 = actAeffTierList.get(tier).get(i);
				for (int j=i+1; j<actAeffTierList.get(tier).size(); j++) {
					if (i==j) continue;
					MobCosCampaignDtlDTO dtl2 = actAeffTierList.get(tier).get(j);
					
					Calendar startDate1 = Calendar.getInstance();
					startDate1.setTime(Utility.string2Date(dtl1.getEffStartDate()));
					
					Calendar startDate2 = Calendar.getInstance();
					startDate2.setTime(Utility.string2Date(dtl2.getEffStartDate()));
					startDate2.add(Calendar.SECOND, -1);
					
					Calendar endDate1 = Calendar.getInstance();
					if (dtl1.getEffEndDate() != null && !"".equals(dtl1.getEffEndDate())) {
						endDate1.setTime(Utility.string2Date(dtl1.getEffEndDate()));
					} else {
						endDate1.setTime(Utility.string2Date("31/12/2099"));
					}
					
					Calendar endDate2 = Calendar.getInstance();
					if (dtl2.getEffEndDate() != null && !"".equals(dtl2.getEffEndDate())) {
						endDate2.setTime(Utility.string2Date(dtl2.getEffEndDate()));
					} else {
						endDate2.setTime(Utility.string2Date("31/12/2099"));
					}
					endDate2.add(Calendar.SECOND, 1);
					if ((startDate2.after(startDate1) && startDate2.before(endDate1)) 
							|| (endDate2.after(startDate1) && endDate2.before(endDate1))
							|| (startDate1.after(startDate2) && startDate1.before(endDate2)) 
							|| (endDate1.after(startDate2) && endDate1.before(endDate2))) {
						errors.rejectValue("cpgDtlDTOList[" + ui.getCpgDtlDTOList().indexOf(dtl1) + "].tier", "dummy", 
								"Overlapped Effective period for basket " + dtl1.getCampaignBasketSeq() + " and " + dtl2.getCampaignBasketSeq() + ".");
					}
				}
			}
		}
		
	}
	
	private void saveCampaign(MobCosCampaignUI ui, HttpServletRequest request) {
		BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String cpgId = ui.getCurCpgId();
		String cpgTitle = ui.getCpgHdrDTO().getCampaignTitle();
		String cpgName = ui.getCpgHdrDTO().getCampaignName();
		String handset = ui.getCpgHdrDTO().getHandsetDesc();
		String channelId = String.valueOf(bomsalesuser.getChannelId());
		String userId = bomsalesuser.getUsername();
		
		Date effStartDate = null;
		Date effEndDate = null;
		if(!StringUtils.isEmpty(ui.getCpgHdrDTO().getEffStartDate())) {
			 effStartDate = Utility.string2Date(ui.getCpgHdrDTO().getEffStartDate());
		} 
		if(!StringUtils.isEmpty(ui.getCpgHdrDTO().getEffEndDate())) {
			effEndDate = Utility.string2Date(ui.getCpgHdrDTO().getEffEndDate());
		}
		mobCosCampaignService.updCpgHdr(cpgId, cpgTitle, cpgName, handset, effStartDate, effEndDate, userId);
		mobCosCampaignService.updCpgChannelAssgn(channelId, cpgId, null, userId);
		
		for(MobCosCampaignDtlDTO dtl : ui.getCpgDtlDTOList()) {
			String basketId = dtl.getBasketId();
			String basketDesc = dtl.getBasketDesc();
			String seq = dtl.getCampaignBasketSeq();
			String tier = dtl.getTier();
			Date basStartDate = null;
			Date basEndDate = null;
			if(!StringUtils.isEmpty(dtl.getEffStartDate())) {
				basStartDate = Utility.string2Date(dtl.getEffStartDate());
			}
			if(!StringUtils.isEmpty(dtl.getEffEndDate())) {
				basEndDate = Utility.string2Date(dtl.getEffEndDate());
			}
			
			mobCosCampaignService.updCpgDtl(cpgId, basketId, basketDesc, seq, tier, basStartDate, basEndDate, userId);
			List<MobCosCampaignVasDTO> basVasList = (List<MobCosCampaignVasDTO>)request.getSession().getAttribute("basVasList_" + basketId);
			if(!CollectionUtils.isEmpty(basVasList)) {
				for(MobCosCampaignVasDTO basVas : basVasList) {
					String itemId = basVas.getItemId();
					String action = basVas.getActionType();
					if(!StringUtils.isEmpty(basVas.getActionType())) {
						mobCosCampaignService.updCpgVas(basketId, itemId, action, userId);
					} 
				}
			}
		}
	}
	
	
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		return referenceData;
	}
	
	private void loadCampaign(MobCosCampaignUI ui) {
		MobCosCampaignHdrDTO cpgHdrDTO = mobCosCampaignService.getCpgHdr(ui.getCurCpgId());
		ui.setCpgHdrDTO(cpgHdrDTO);
		if (cpgHdrDTO != null) {
			List<MobCosCampaignDtlDTO> cpgDtlDTOList = mobCosCampaignService.getCpgDtl(ui.getCurCpgId());
			ui.setCpgDtlDTOList(CollectionUtils.isEmpty(cpgDtlDTOList) ? new ArrayList<MobCosCampaignDtlDTO>() : cpgDtlDTOList);
		} else {
			ui.setCpgDtlDTOList(new ArrayList<MobCosCampaignDtlDTO>());
		}
		ui.setSecCpgName(null);
		ui.setSecCpgTitle(null);
		ui.setSecHandset(null);
		ui.setSecResult(null);
		return;
	}
}
