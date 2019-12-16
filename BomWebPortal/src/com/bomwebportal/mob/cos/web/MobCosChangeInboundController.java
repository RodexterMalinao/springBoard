package com.bomwebportal.mob.cos.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.mob.cos.dto.MobCosChangeInboundDTO;
import com.bomwebportal.mob.cos.dto.MobCosStaffListDTO;
import com.bomwebportal.mob.cos.service.MobCosChangeInboundService;


public class MobCosChangeInboundController extends SimpleFormController {


	protected final Log logger = LogFactory.getLog(getClass());
	private MobCosChangeInboundService mobCosChangeInboundService;
	String channelCd = "";
	
	public MobCosChangeInboundService getMobCosChangeInboundService() {
		return mobCosChangeInboundService;
	}

	public void setMobCosChangeInboundService(
			MobCosChangeInboundService mobCosChangeInboundService) {
		this.mobCosChangeInboundService = mobCosChangeInboundService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		
		if (logger.isDebugEnabled()) {
			logger.debug("MobCosChangeInboundController formBackingObject called");
		}
        BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
        MobCosChangeInboundDTO sessionCosChangeInbound = (MobCosChangeInboundDTO) request.getSession().getAttribute("mobCosChangeInboundSession");
        HttpSession session = request.getSession();
      	
			if (sessionCosChangeInbound == null){
				// new, for no session saved
				MobCosChangeInboundDTO mobCosChangeInbound = new MobCosChangeInboundDTO();
				mobCosChangeInbound.setAllowAccess(true);
				if(!mobCosChangeInboundService.isAllowAccess(bomsalesuser.getUsername())){
					mobCosChangeInbound.setAllowAccess(false);
				}
				channelCd = bomsalesuser.getChannelCd();
				mobCosChangeInbound.setSelectedChannelCd(channelCd);
				mobCosChangeInbound.setSalesCd(bomsalesuser.getUsername());
				session.setAttribute("mobCosChangeInboundSession", mobCosChangeInbound);

				return mobCosChangeInbound;
			} else{
				sessionCosChangeInbound.setAllowAccess(true);
				if(!mobCosChangeInboundService.isAllowAccess(bomsalesuser.getUsername())){
					sessionCosChangeInbound.setAllowAccess(false);
				}
				return sessionCosChangeInbound;
			}
        }	

	protected ModelAndView onSubmit(
			HttpServletRequest request,	HttpServletResponse response, Object command,	BindException errors)
			throws Exception {
		
		if (logger.isDebugEnabled()) {
			logger.debug("MobCosChangeInboundController is called!");
		}
		MobCosChangeInboundDTO ui = (MobCosChangeInboundDTO) command;
		Map model = errors.getModel();
		model.putAll(referenceData(request));	 
		ModelAndView modelAndView =  new ModelAndView(new RedirectView("mobcoschangeinbound.html"));

		if ("CLEAR".equalsIgnoreCase(ui.getActionType())) {
			ui.setSelectedStaffid(null);
			ui.setSelectedCenterId(null);
			ui.setSelectedTeamId(null);
			ui.setSelectedInBoundindSt4(null);
			ui.setSelectedInBoundindOw(null);
			ui.setStaffList(null);
			}
		
		if ("HISTORY".equalsIgnoreCase(ui.getActionType())) {
			List<MobCosStaffListDTO> staffList= new ArrayList<MobCosStaffListDTO>();
			staffList=mobCosChangeInboundService.getStaffHistoryBySId(ui.getSelectedStaffid(),ui.getSelectedChannelCd());
			ui.setStaffList(staffList);	
		}
		
		if ("SEARCH".equalsIgnoreCase(ui.getActionType())) {
			if(ui.getSelectedStaffid().isEmpty()){
			/*print staff list (start)*/
			List<MobCosStaffListDTO> staffList= new ArrayList<MobCosStaffListDTO>();
			staffList=mobCosChangeInboundService.getStaffList(ui.getSelectedChannelCd(),ui.getSelectedCenterId(),ui.getSelectedTeamId(),ui.getSelectedInBoundindSt4(),ui.getSelectedInBoundindOw());

			ui.setStaffList(staffList);}
			else{
				/*print staff list (start)*/
				List<MobCosStaffListDTO> staffList= new ArrayList<MobCosStaffListDTO>();
				staffList=mobCosChangeInboundService.getStaffListBySId(ui.getSelectedStaffid(),ui.getSelectedChannelCd());
				ui.setStaffList(staffList);				
			}
			
		}
		if ("ASSIGN".equalsIgnoreCase(ui.getActionType())) {
			String errmsg=null;
			List<String> staffListError= new ArrayList<String>();
			List<String> staffError= new ArrayList<String>();

			for(MobCosStaffListDTO item: ui.getStaffList()) {
				if (item.isActionInd()&& (item.getAction().equalsIgnoreCase("Overwrite to outbound")|| item.getAction().equalsIgnoreCase("Overwrite to inbound"))) {
					item.setUpdBy(ui.getSalesCd());
					
					try {
						int result = mobCosChangeInboundService.insertInbAssign(item);
						if (result == 0) errmsg = "no record is inserted";
					} catch (Exception e) {
						//errmsg = e.getMessage();
						errmsg = "has insert error";
					}
					
					if (errmsg!=null){
						staffListError.add(errmsg);
						staffError.add(item.getStaffid());
					}
				}else if (item.isActionInd()&& item.getAction().equalsIgnoreCase("Mark overwrite end")){
					item.setUpdBy(ui.getSalesCd());
					try {
						int result = mobCosChangeInboundService.updateOverwriteEnd(item);
						if (result == 0) errmsg = "no record is updated";
					} catch (Exception e) {
						//errmsg = e.getMessage();
						errmsg = "has update error";
					}
					
					if (errmsg!=null){
						staffListError.add(errmsg);
						staffError.add(item.getStaffid());
					}
				}
				
				
			}
			modelAndView.addObject("staffListError", staffListError);
			List<MobCosStaffListDTO> staffList= new ArrayList<MobCosStaffListDTO>();
			staffList=mobCosChangeInboundService.getStaffList(ui.getSelectedChannelCd(),ui.getSelectedCenterId(),ui.getSelectedTeamId(),ui.getSelectedInBoundindSt4(),ui.getSelectedInBoundindOw());
			ui.setStaffList(staffList);
			if (staffListError !=null){
				for(int i=0;i<staffListError.size();i++) {					
					errors.rejectValue("staffList", "", staffError.get(i)+" "+staffListError.get(i));
				}
			}
			if (errors.hasErrors()) {
				return new ModelAndView("mobcoschangeinbound", model);
			}
		}

		HttpSession session = request.getSession();
		session.setAttribute("mobCosChangeInboundSession", ui);

		//return new ModelAndView(new RedirectView("mobcoschangeinbound.html"));
		return modelAndView;
	}

	protected Map referenceData(HttpServletRequest request) throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("ReferenceData called");
		}

        logger.info("ReferenceData called");
		
		Map referenceData = new HashMap<String, List<String>>();

        BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		channelCd = bomsalesuser.getChannelCd();

		List<String> center = new ArrayList<String>();
		List<String> team = new ArrayList<String>();
		center=mobCosChangeInboundService.getCenterList(channelCd);
		team=mobCosChangeInboundService.getTeamList(channelCd);


		referenceData.put("channelCd",channelCd);
		referenceData.put("center",center);
		referenceData.put("team",team);

		return referenceData;
	}
	
	
	
	
	
}
