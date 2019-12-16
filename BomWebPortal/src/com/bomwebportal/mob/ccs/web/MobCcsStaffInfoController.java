package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;
import com.bomwebportal.mob.ccs.service.MobCcsSalesInfoService;
import com.bomwebportal.mob.ccs.service.StaffInfoService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.util.Utility;

public class MobCcsStaffInfoController extends SimpleFormController {
	
	private StaffInfoService staffInfoService;
	private MobCcsSalesInfoService mobCcsSalesInfoService;

	public void setStaffInfoService(StaffInfoService staffInfoService) {
		this.staffInfoService = staffInfoService;
	}
	public StaffInfoService getStaffInfoService() {
		return staffInfoService;
	}

	public MobCcsSalesInfoService getMobCcsSalesInfoService() {
		return mobCcsSalesInfoService;
	}
	public void setMobCcsSalesInfoService(MobCcsSalesInfoService mobCcsSalesInfoService) {
		this.mobCcsSalesInfoService = mobCcsSalesInfoService;
	}
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		logger.info("MobCcsStaffInfoController@formBackingObject called");
		
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String appDate = (String) request.getSession().getAttribute("appDate");
		
		String workStatus = (String) MobCcsSessionUtil.getSession(request, "workStatus");
		request.setAttribute("workStatus", workStatus);
		
		StaffInfoUI sessionStaffInfoDto = (StaffInfoUI) MobCcsSessionUtil
				.getSession(request, "staffInfo");//get session from hashMap session
		
		if (sessionStaffInfoDto == null) {
			logger.info("MobCcsStaffInfoController@formBackingObject called , sessionStaffInfoDto is null");
			sessionStaffInfoDto = new StaffInfoUI();

			if (user != null) {
				sessionStaffInfoDto.setSalesId(user.getUsername());
				sessionStaffInfoDto.setSalesName(user.getStaffName());
			}
			
			Date defaultDateNTime = new Date();
			String dateNTime = Utility.date2String(defaultDateNTime, "dd/MM/yyyy HH:mm");
			
			sessionStaffInfoDto.setCallDateStr(dateNTime.substring(0, 10));
			sessionStaffInfoDto.setCallTimeStr(dateNTime.substring(11));
			sessionStaffInfoDto.setCallTimeHour(dateNTime.substring(11, 13));
			sessionStaffInfoDto.setCallTimeMin(dateNTime.substring(14));
			
			// retrieve call list desc & id from previous basket session
			BasketDTO basketDto = null;
			basketDto = (BasketDTO) MobCcsSessionUtil.getSession(request, "basket");
			
			String callListDesc = null;
			String callListId = null;
			
			if (basketDto != null) {
				callListDesc =basketDto.getCallListDesc();
				callListId =  basketDto.getCallListCd();
			}
			
			sessionStaffInfoDto.setCallList(callListId);
			sessionStaffInfoDto.setCallListDesc(callListDesc);
			sessionStaffInfoDto.setAppDate(appDate);
			sessionStaffInfoDto.setLoginChannelCd(user.getChannelCd());
			
			String centre = null;
			String team = null;
			
			List<SalesInfoDTO> tempDtoList = new ArrayList<SalesInfoDTO>();
			
			if (StringUtils.isNotBlank(sessionStaffInfoDto.getSalesId())) {
				tempDtoList = mobCcsSalesInfoService.getSalesInfoDTOByID(sessionStaffInfoDto.getSalesId().toUpperCase().trim(), appDate);
				if (tempDtoList != null && !tempDtoList.isEmpty()) {
					centre = tempDtoList.get(0).getCentreCd();
					team = tempDtoList.get(0).getTeamCd();
				}
			}
			
			sessionStaffInfoDto.setSalesCentre(centre);
			sessionStaffInfoDto.setSalesTeam(team);
			
		} else {
			String salesName = null, refSalesName = null;
			
			String centre = null, refSalesCentre = null;
			String team = null, refSalesTeam = null;
			
			List<SalesInfoDTO> tempDtoList = new ArrayList<SalesInfoDTO>();
			List<SalesInfoDTO> refTempDtoList = new ArrayList<SalesInfoDTO>();
			
			if (StringUtils.isNotBlank(sessionStaffInfoDto.getSalesId())) {
				salesName = staffInfoService.getStaffName(sessionStaffInfoDto.getSalesId().toUpperCase().trim(), appDate);
				tempDtoList = mobCcsSalesInfoService.getSalesInfoDTOByID(sessionStaffInfoDto.getSalesId().toUpperCase().trim(), appDate);
				if (tempDtoList != null && tempDtoList.size() > 0) {
					centre = tempDtoList.get(0).getCentreCd();
					team = tempDtoList.get(0).getTeamCd();
				}
			}
			if (sessionStaffInfoDto.getRefSalesId() != null && !sessionStaffInfoDto.isManualInputBool()) {
				refSalesName = staffInfoService.getStaffName(sessionStaffInfoDto.getRefSalesId().toUpperCase().trim(), appDate);
				refTempDtoList = mobCcsSalesInfoService.getSalesInfoDTOByID(sessionStaffInfoDto.getRefSalesId().toUpperCase().trim(), appDate);
				if (refTempDtoList != null && refTempDtoList.size() > 0) {
					refSalesCentre = refTempDtoList.get(0).getCentreCd();
					refSalesTeam = refTempDtoList.get(0).getTeamCd();
					sessionStaffInfoDto.setRefSalesName(refSalesName);
					sessionStaffInfoDto.setRefSalesCentre(refSalesCentre);
					sessionStaffInfoDto.setRefSalesTeam(refSalesTeam);
				}			
			}
			
			sessionStaffInfoDto.setSalesName(salesName);
			//sessionStaffInfoDto.setRefSalesName(refSalesName);
			sessionStaffInfoDto.setSalesCentre(centre);
			//sessionStaffInfoDto.setRefSalesCentre(refSalesCentre);
			sessionStaffInfoDto.setSalesTeam(team);
			//sessionStaffInfoDto.setRefSalesTeam(refSalesTeam);
			
			if (sessionStaffInfoDto.getCallTimeStr() != null) {
				sessionStaffInfoDto.setCallTimeHour(sessionStaffInfoDto.getCallTimeStr().substring(0, 2));
				sessionStaffInfoDto.setCallTimeMin(sessionStaffInfoDto.getCallTimeStr().substring(3));
			}
			
			sessionStaffInfoDto.setAppDate(appDate);
			sessionStaffInfoDto.setLoginChannelCd(user.getChannelCd());
			
			MobCcsSessionUtil.setSession(request, "staffInfo", sessionStaffInfoDto);
		}
		
		sessionStaffInfoDto.setWorkStatus(workStatus);
		
		return sessionStaffInfoDto;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		String nextView = (String) request.getAttribute("nextView");
		logger.info("Next View: " + nextView);
		StaffInfoUI staffInfoDto = (StaffInfoUI) command;
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String appDate = (String) request.getSession().getAttribute("appDate");
		
		String workStatus = (String) MobCcsSessionUtil.getSession(request, "workStatus");
		request.setAttribute("workStatus", workStatus);
		
		staffInfoDto.setCreateBy(user.getUsername());
		staffInfoDto.setLastUpdBy(user.getUsername());
		
		if (staffInfoDto.getSalesId() == null || "".equals(staffInfoDto.getSalesId().trim())) {
			staffInfoDto.setSalesType(null);
		} else {
			staffInfoDto.setSalesType("S");
		}
		
		if (staffInfoDto.getRefSalesId() == null || "".equals(staffInfoDto.getRefSalesId().trim())) {
			staffInfoDto.setRefSalesType(null);
		} else {
			staffInfoDto.setRefSalesType("S");
		}
		
		//////////////////////// have concerns
		staffInfoDto.setLockInd("N");
		
		staffInfoDto.setWorkStatus(workStatus);
		
		staffInfoDto.setCallTimeStr(staffInfoDto.getCallTimeHour() + ":" + staffInfoDto.getCallTimeMin());
		
		staffInfoDto.setAppDate(appDate);
		staffInfoDto.setLoginChannelCd(user.getChannelCd());
		
		MobCcsSessionUtil.setSession(request, "staffInfo", staffInfoDto);// save session to hashMap session
		
		if ("REFRESH".equalsIgnoreCase(staffInfoDto.getActionType())) {
			return new ModelAndView(new RedirectView("mobccsstaffinfo.html"));
		}

		return new ModelAndView(new RedirectView(nextView));
	}
	
	public Map referenceData(HttpServletRequest request) throws Exception {
		Map referenceData = new HashMap<String, List<String>>();
		List<String> hourList = new ArrayList<String>();
		List<String> minList = new ArrayList<String>();
		
		for (int i = 0; i < 24; i++) {
			if (i < 10) {
				hourList.add("0" + String.valueOf(i));
			} else {
				hourList.add(String.valueOf(i));
			}
		}
		
		for (int i = 0; i < 60; i++) {
			if (i < 10) {
				minList.add("0" + String.valueOf(i));
			} else {
				minList.add(String.valueOf(i));
			}
		}
		
		referenceData.put("hourList", hourList);
		referenceData.put("minList", minList);
		
		return referenceData;
	}
	
}
