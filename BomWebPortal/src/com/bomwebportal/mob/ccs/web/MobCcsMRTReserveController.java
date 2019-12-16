package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.MrtReserveDTO;
import com.bomwebportal.mob.ccs.dto.MrtStatusDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTReserveUI;
import com.bomwebportal.mob.ccs.service.MobCcsMrtService;
import com.bomwebportal.mob.ccs.service.MobCcsMrtStatusService;
import com.bomwebportal.mob.ccs.service.MrtInventoryService;
import com.bomwebportal.mob.ccs.service.MrtReserveService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;

public class MobCcsMRTReserveController extends SimpleFormController {
	
	private MobCcsMrtService mrtService;
	private MrtReserveService mrtReserveService;
	private MrtInventoryService mrtInventoryService;
	
	public void setMrtInventoryService(MrtInventoryService mrtInventoryService) {
		this.mrtInventoryService = mrtInventoryService;
	}
	public MrtInventoryService getMrtInventoryService() {
		return mrtInventoryService;
	}
	public MrtReserveService getMrtReserveService() {
		return mrtReserveService;
	}
	public void setMrtReserveService(MrtReserveService mrtReserveService) {
		this.mrtReserveService = mrtReserveService;
	}
	public void setMrtService(MobCcsMrtService mrtService) {
		this.mrtService = mrtService;
	}
	public MobCcsMrtService getMrtService() {
		return mrtService;
	}
	
	public Object formBackingObject(HttpServletRequest request)
		throws ServletException {

		logger.info("MobCcsMrtReserveController@formBackingObject called");
		
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		logger.debug("Start call handleExpiredMRT");
		boolean handleExpiredMRTResult = mrtService.handleExpiredMRT(user.getUsername());
		logger.debug("End call handleExpiredMRT, result: " + handleExpiredMRTResult);
		
		logger.debug("Start call handleFrozenMRT");
		boolean handleFrozenMRTResult = mrtService.handleFrozenMRT();
		logger.debug("End call handleFrozenMRT, result: " + handleFrozenMRTResult);
		
		MRTReserveUI sessionMrtReserve = (MRTReserveUI) MobCcsSessionUtil
				.getSession(request, "mrtReserve");//get session from hashMap session
		
		if (sessionMrtReserve == null) {
			
			logger.info("MobCcsMrtReserveController@formBackingObject called , sessionMrtReserve is null");
			sessionMrtReserve = new MRTReserveUI();
			List<MRTReserveUI> reserveMRTList = mrtReserveService.getReserveMRTList(user.getUsername());
			sessionMrtReserve.setReserveMRTList(reserveMRTList);
			request.setAttribute("reserveMRTList", reserveMRTList);
			if (reserveMRTList.size() >= 3) {
				sessionMrtReserve.setIs3reserved(true);
				request.setAttribute("is3reserved", true);
			} else {
				sessionMrtReserve.setIs3reserved(false);
				request.setAttribute("is3reserved", false);
			}
			
			MobCcsSessionUtil.setSession(request, "mrtReserve", sessionMrtReserve);
			
		} else if ("INSERT".equalsIgnoreCase(sessionMrtReserve.getActionType())) {
			
			List<MRTReserveUI> checkReserveMRTList = mrtReserveService.getReserveMRTList(user.getUsername());
			sessionMrtReserve.setReserveMRTList(checkReserveMRTList);
			request.setAttribute("reserveMRTList", checkReserveMRTList);
			
			//modify by Eliot 20120312
			if (checkReserveMRTList.size() >= 3) {
				sessionMrtReserve.setIs3reserved(true);
				request.setAttribute("is3reserved", true);
				sessionMrtReserve.setActionType("MORE3");
				MobCcsSessionUtil.setSession(request, "mrtReserve", sessionMrtReserve);
				request.setAttribute("actionType", "MORE3");
				logger.debug("MRT RESERVE NOT SUCCESS, more than 3");
				return sessionMrtReserve;
			} else {
				int result = -1;
				
				List<MrtInventoryDTO> typeNLvlDto = mrtInventoryService.getMrtInventoryDTOALL(sessionMrtReserve.getMrtPool());
				
				MrtReserveDTO dto = new MrtReserveDTO();
				
				if (typeNLvlDto != null && typeNLvlDto.size() > 0) {
					dto.setSrvNumType(typeNLvlDto.get(0).getSrvNumType());
					dto.setMsisdnlvl(typeNLvlDto.get(0).getMsisdnlvl());
				} else {
					dto.setSrvNumType(null);
					dto.setMsisdnlvl(null);
				}
				
				dto.setStaffId(user.getUsername());
				dto.setMsisdn(sessionMrtReserve.getMrtPool());
				dto.setCreatedBy(user.getUsername());
				dto.setLastUpdBy(user.getUsername());
				
				MrtInventoryDTO mrtInDto = new MrtInventoryDTO();
				mrtInDto.setLastUpdBy(user.getUsername());
				mrtInDto.setMsisdn(sessionMrtReserve.getMrtPool());
				
				String mrtStatus = mrtInventoryService.getStsFmMRT(dto.getMsisdn());
				
				if ("2".equals(mrtStatus) || "02".equals(mrtStatus)) {
					result = mrtReserveService.insertMrtReserve(dto);
					mrtInventoryService.updateMrtInventoryStatus(mrtInDto);
				} else {
					result = -1;
				}
				
				if (result == 1) {
					sessionMrtReserve.setActionType("SUCCESS");
					request.setAttribute("actionType", "SUCCESS");
					logger.debug("MRT RESERVE SUCCESS, result: " + result);
					List<MRTReserveUI> reserveMRTList = mrtReserveService.getReserveMRTList(user.getUsername());
					sessionMrtReserve.setReserveMRTList(reserveMRTList);
					request.setAttribute("reserveMRTList", reserveMRTList);
					
					if (reserveMRTList.size() >= 3) {
						sessionMrtReserve.setIs3reserved(true);
						request.setAttribute("is3reserved", true);
					} else {
						sessionMrtReserve.setIs3reserved(false);
						request.setAttribute("is3reserved", false);
					}
					
					MobCcsSessionUtil.setSession(request, "mrtReserve", sessionMrtReserve);
					logger.debug("reserveMRTList: " + reserveMRTList.size());
				} else if (result == -1) {
					sessionMrtReserve.setActionType("CHOOSEOTHER");
					MobCcsSessionUtil.setSession(request, "mrtReserve", sessionMrtReserve);
					request.setAttribute("actionType", "CHOOSEOTHER");
					logger.debug("MRT STATUS IS NOT FREE, result: " + result);
				} else {
					sessionMrtReserve.setActionType("UNSUCCESS");
					MobCcsSessionUtil.setSession(request, "mrtReserve", sessionMrtReserve);
					request.setAttribute("actionType", "UNSUCCESS");
					logger.debug("MRT RESERVE NOT SUCCESS, result: " + result);
				}
			}
		} else {
			List<MRTReserveUI> reserveMRTList = mrtReserveService.getReserveMRTList(user.getUsername());
			sessionMrtReserve.setReserveMRTList(reserveMRTList);
			request.setAttribute("reserveMRTList", reserveMRTList);
			if (reserveMRTList.size() >= 3) {
				sessionMrtReserve.setIs3reserved(true);
				request.setAttribute("is3reserved", true);
			} else {
				sessionMrtReserve.setIs3reserved(false);
				request.setAttribute("is3reserved", false);
			}
			
			MobCcsSessionUtil.setSession(request, "mrtReserve", sessionMrtReserve);
		}

		return sessionMrtReserve;

	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		logger.info("MobCcsMrtReserveController onSubmit called");
		
		MRTReserveUI ui = (MRTReserveUI) command;
			
		if ("INSERT".equalsIgnoreCase(ui.getActionType()) 
				|| "SUCCESS".equalsIgnoreCase(ui.getActionType())
				|| "UNSUCCESS".equalsIgnoreCase(ui.getActionType())) {
			return new ModelAndView(new RedirectView("mobccsmrtreserve.html"));
		}

		return new ModelAndView(new RedirectView("welcome.html"));

	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {

		logger.info("MobCcsMrtReserveController ReferenceData called");
		
		MRTReserveUI dto 
			= (MRTReserveUI) MobCcsSessionUtil.getSession(request, "mrtReserve");
		BomSalesUserDTO user = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		
		Map referenceData = new HashMap<String, List<String>>();
		
		List<String[]> mob3GMrtListTemp = mrtService.getPCCW3GMrtNumByChannelCd(user.getChannelCd());
		
		referenceData.put("mob3GMrtList", mob3GMrtListTemp);
		
		List<MRTReserveUI> reserveMRTList = mrtReserveService.getReserveMRTList(user.getUsername());
		request.setAttribute("reserveMRTList", reserveMRTList);
		
		if (reserveMRTList.size() >= 3) {
			request.setAttribute("is3reserved", true);
		} else {
			request.setAttribute("is3reserved", false);
		}

		return referenceData;
	}
	
}
