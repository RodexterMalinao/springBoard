package com.bomwebportal.mob.ccs.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsIncidentCauseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsOrderRescueDTO;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsIncidentCauseService;
import com.bomwebportal.mob.ccs.service.MobCcsOrderRescueService;
import com.bomwebportal.service.OrderService;

public class MobCcsOrderRescueCreateController implements Controller {
	private CodeLkupService codeLkupService;
	private OrderService orderService;
	private MobCcsOrderRescueService mobCcsOrderRescueService;
	
	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MobCcsOrderRescueService getMobCcsOrderRescueService() {
		return mobCcsOrderRescueService;
	}

	public void setMobCcsOrderRescueService(
			MobCcsOrderRescueService mobCcsOrderRescueService) {
		this.mobCcsOrderRescueService = mobCcsOrderRescueService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");

		if (StringUtils.isEmpty(request.getParameter("orderId"))) {
			ModelAndView modelAndView = new ModelAndView("mobccsorderrescuecreate");

			OrderDTO orderDto = this.orderService.getOrder(request.getParameter("recordOrderId"));
			modelAndView.addObject("orderDto", orderDto);
			
			List<CodeLkupDTO> causeCodes = codeLkupService.getCodeLkupDTOALL("DEL_FAIL");
			modelAndView.addObject("causeCodes", causeCodes);
			
			if (StringUtils.isNotBlank(orderDto.getOrderStatus())) {
				List<CodeLkupDTO> orderStatuses = codeLkupService.getCodeLkupDTOALL("ORD_STATUS");
				if (orderStatuses != null) {
					for (CodeLkupDTO dto : orderStatuses) {
						if (orderDto.getOrderStatus().equals(dto.getCodeId())) {
							modelAndView.addObject("orderStatusDesc", dto.getCodeDesc());
							break;
						}
					}
				}
			}
			
			if (StringUtils.isNotBlank(orderDto.getCheckPoint())) {
				List<CodeLkupDTO> orderCheckPoints = codeLkupService.getCodeLkupDTOALL("ORD_CHECK_POINT");
				if (orderCheckPoints != null) {
					for (CodeLkupDTO dto : orderCheckPoints) {
						if (orderDto.getCheckPoint().equals(dto.getCodeId())) {
							modelAndView.addObject("checkPointDesc", dto.getCodeDesc());
							break;
						}
					}
				}
			}

			List<MobCcsOrderRescueDTO> list = this.mobCcsOrderRescueService.getMobCcsOrderRescueDTOByOrderId(orderDto.getOrderId());
			if (list != null && !list.isEmpty()) {
				modelAndView.addObject("locationDesc", list.get(0).getLocationDesc());
			}
			
			return modelAndView;
		} else {
			ModelAndView modelAndView = null;
			MobCcsOrderRescueDTO dtoRequest = this.getMobCcsOrderRescueDTO(request);
			
			String recordIncidentNo = null;
			boolean insertSuccess = false;
			
			OrderDTO orderDto = this.orderService.getOrder(dtoRequest.getOrderId());
			
			if (orderDto == null) {
				
			} else if (!"MOB".equals(orderDto.getOrderSumLob()))  {
				// not MOB
			} else if (!"QOM".equals(user.getChannelCd()) && !"CFM".equals(user.getChannelCd())) {
				// not QOM team
			} else if ("01".equals(orderDto.getOrderStatus()) && "599".equals(orderDto.getCheckPoint())) {
				this.mobCcsOrderRescueService.insertMobCcsOrderRescueDTO(dtoRequest);
				recordIncidentNo = dtoRequest.getIncidentNo();
				insertSuccess = true;
			}
			
			if (insertSuccess) {
				modelAndView = new ModelAndView(new RedirectView("mobccsorderrescuedetail.html"));
				
				modelAndView.addObject("recordIncidentNo", recordIncidentNo);
				modelAndView.addObject("insertSuccess", insertSuccess);
			} else {
				modelAndView = new ModelAndView(new RedirectView("mobccsorderrescuecreate.html"));

				modelAndView.addObject("recordOrderId", dtoRequest.getOrderId());
				modelAndView.addObject("statusReadonly", true);
			}
			return modelAndView;
		}
	}
	
	private MobCcsOrderRescueDTO getMobCcsOrderRescueDTO(HttpServletRequest request) throws ServletRequestBindingException {
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		Date now = new Date();
		
		MobCcsOrderRescueDTO dto = new MobCcsOrderRescueDTO();
		dto.setOrderId(ServletRequestUtils.getRequiredStringParameter(request, "orderId"));
		dto.setHandleBy(user.getUsername());
		dto.setHandleTime(now);
		dto.setSerialCancel("true".equals(ServletRequestUtils.getStringParameter(request, "serialCancel")) ? "Y" : "N");
		dto.setVisitMt1("true".equals(ServletRequestUtils.getStringParameter(request, "visitMt1")) ? "Y" : "N");
		dto.setRemark(StringUtils.trim(ServletRequestUtils.getStringParameter(request, "remark")));
		dto.setAction(StringUtils.trim(ServletRequestUtils.getStringParameter(request, "action")));
		dto.setCourierStaffId(StringUtils.trim(ServletRequestUtils.getRequiredStringParameter(request, "courierStaffId")));
		dto.setAmendment("true".equals(ServletRequestUtils.getStringParameter(request, "amendment")) ? "Y" : "N");
		dto.setOrderSaved("true".equals(ServletRequestUtils.getStringParameter(request, "orderSaved")) ? "Y" : "N");
		dto.setDoa("true".equals(ServletRequestUtils.getStringParameter(request, "doa")) ? "Y" : "N");
		dto.setDelContractOnly("true".equals(ServletRequestUtils.getStringParameter(request, "delContractOnly")) ? "Y" : "N");
		dto.setCreateDate(now);
		
		List<CodeLkupDTO> causeCodes = codeLkupService.getCodeLkupDTOALL("DEL_FAIL");
		List<MobCcsIncidentCauseDTO> incidentCauses = new ArrayList<MobCcsIncidentCauseDTO>();
		for (String causeCode : ServletRequestUtils.getRequiredStringParameters(request, "causeCodes")) {
			for (CodeLkupDTO clDto : causeCodes) {
				if (clDto.getCodeId().equals(causeCode)) {
					MobCcsIncidentCauseDTO icDto = new MobCcsIncidentCauseDTO();
					icDto.setCauseCode(causeCode);
					incidentCauses.add(icDto);
					break;
				}
			}
		}
		dto.setIncidentCauses(incidentCauses);
		
		return dto;
	}

}
