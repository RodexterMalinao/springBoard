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
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.DoaItemDTO;
import com.bomwebportal.mob.ccs.dto.DoaRequestDTO;
import com.bomwebportal.mob.ccs.dto.DoaRequestSelectedCdDTO;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;
import com.bomwebportal.mob.ccs.dto.StockDTO;
import com.bomwebportal.mob.ccs.dto.ui.DoaRequestUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.MobCcsDoaRequestService;
import com.bomwebportal.mob.ccs.util.MobCcsSessionUtil;
import com.bomwebportal.service.OrderService;

public class MobCcsDoaRequestController extends SimpleFormController {
	//private static final String ORDER_STATUS_FALLOUT = "99";
	
	//private static final String CHECK_POINT_FALLOUT = "999";
	
	private static final String ACTION_NAME = "AU04";
	
	private static final String REASON_CD_DOA_SAVED = "N000";
	private static final String REASON_CD_DOA_AFTER_DELIVERY = "N001";
	//private static final String REASON_CD_DOA_COMPLETED = "02";

	private static final String DOA_TYPE_ONLINE = "DOA";
	
	private OrderService orderService;
	private MobCcsDoaRequestService mobCcsDoaRequestService;
	private CodeLkupService codeLkupService;
	
	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public MobCcsDoaRequestService getMobCcsDoaRequestService() {
		return mobCcsDoaRequestService;
	}

	public void setMobCcsDoaRequestService(
			MobCcsDoaRequestService mobCcsDoaRequestService) {
		this.mobCcsDoaRequestService = mobCcsDoaRequestService;
	}

	public CodeLkupService getCodeLkupService() {
		return codeLkupService;
	}

	public void setCodeLkupService(CodeLkupService codeLkupService) {
		this.codeLkupService = codeLkupService;
	}

	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		DoaRequestUI form = new DoaRequestUI();
		
		OrderDTO orderDto = this.orderService.getOrder(ServletRequestUtils.getRequiredStringParameter(request, "orderId"));
		DoaRequestDTO dto = null;
		List<DoaRequestSelectedCdDTO> selectedCdDTOALL = null;
		List<DoaItemDTO> doaItemDTOALL = null;

		if (mobCcsDoaRequestService.isInitNewDoaRequest(orderDto)) {
			dto = this.mobCcsDoaRequestService.getInitDoaRequestDTO(orderDto.getOrderId());
		} else {
			int seqNo = this.mobCcsDoaRequestService.getLastSeqNo(orderDto.getOrderId());
			dto = this.mobCcsDoaRequestService.getDoaRequestDTOByOrderIdAndSeqNo(orderDto.getOrderId(), seqNo);
			selectedCdDTOALL = this.mobCcsDoaRequestService.getDoaRequestSelectedCdDTOALL(orderDto.getOrderId(), seqNo);
			doaItemDTOALL = this.mobCcsDoaRequestService.getDoaItemDTOList(orderDto.getOrderId(), seqNo);
		}
		
		if (dto != null) {
			form.setOrderId(dto.getOrderId());
			form.setSeqNo(dto.getSeqNo());
			form.setContactName(dto.getContactName());
			form.setMsisdn(dto.getMsisdn());
			form.setMktSerialId(dto.getMktSerialId());
			form.setDeliveryDate(dto.getDeliveryDate());
			form.setRequestDate(dto.getRequestDate());
			form.setRemarks(dto.getRemarks());
			form.setRowId(dto.getRowId());
			
			if (selectedCdDTOALL != null) {
				List<String> reasons = new ArrayList<String>();
				for (DoaRequestSelectedCdDTO s : selectedCdDTOALL) {
					reasons.add(s.getCodeId());
				}
				form.setReasons(reasons.toArray(new String[0]));
			}
			if (doaItemDTOALL != null) {
				List<String> stocks = new ArrayList<String>();
				for (DoaItemDTO d : doaItemDTOALL) {
					stocks.add(d.getDoaItemSerial());
				}
				form.setStocks(stocks.toArray(new String[0]));
			}
			form.setApproveByManager(this.mobCcsDoaRequestService.approveByManager(orderDto.getOrderId()));
		}
		
		form.setOrderStatus(orderDto.getOrderStatus());
		form.setCheckPoint(orderDto.getCheckPoint());
		form.setReasonCd(orderDto.getReasonCd());
		
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		List<CodeLkupDTO> requestReasons = this.codeLkupService.getCodeLkupDTOALL("DOA_REQUEST_REASON");
		referenceData.put("requestReasons", requestReasons);
		
		List<StockDTO> stockList = orderService.getStockAssignment(ServletRequestUtils.getRequiredStringParameter(request, "orderId"));
		referenceData.put("stockList", stockList);
		OrderDTO orderDto = this.orderService.getOrder(ServletRequestUtils.getRequiredStringParameter(request, "orderId"));
		if (!mobCcsDoaRequestService.isInitNewDoaRequest(orderDto) && !this.mobCcsDoaRequestService.isUpdateDoaRequest(orderDto)) {
			int seqNo = this.mobCcsDoaRequestService.getLastSeqNo(orderDto.getOrderId());
			List<DoaItemDTO> doaItemDTOALL = this.mobCcsDoaRequestService.getDoaItemDTOList(orderDto.getOrderId(), seqNo);
			if (stockList != null && doaItemDTOALL != null) {
				for (StockDTO stockDto : stockList) {
					for (DoaItemDTO doaItemDto : doaItemDTOALL) {
						if (doaItemDto.getDoaItemSerial().equals(stockDto.getDoaItemSerial())) {
							stockDto.setItemSerial(doaItemDto.getDoaItemSerial());
							break;
						}
					}
				}
			}
		}
		
		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccsdoarequest.html"));
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		Date now = new Date();
		
		DoaRequestUI form = (DoaRequestUI) command;
		
		OrderDTO orderDto = this.orderService.getOrder(form.getOrderId());
		boolean saveAsDraft = true;
		boolean errorStockChange = false;
		
		if (mobCcsDoaRequestService.isInitNewDoaRequest(orderDto)) {
			DoaRequestDTO dto = this.mobCcsDoaRequestService.getInitDoaRequestDTO(form.getOrderId());
			final int seqNo = mobCcsDoaRequestService.getLastSeqNo(form.getOrderId()) + 1;
			saveAsDraft = !this.doaRequestApprove(dto, form, request);

			dto.setSeqNo(seqNo);
			dto.setDoaType(DOA_TYPE_ONLINE);
			dto.setStatus(saveAsDraft ? REASON_CD_DOA_SAVED : REASON_CD_DOA_AFTER_DELIVERY);
			dto.setRemarks(form.getRemarks());
			dto.setCreateBy(user.getUsername());
			dto.setCreateDate(now);
			dto.setLastUpdBy(user.getUsername());
			dto.setLastUpdDate(now);

			List<DoaRequestSelectedCdDTO> selectedCds = this.getDoaRequestSelectedCds(form, seqNo, user.getUsername());
			List<DoaItemDTO> doaItems = this.getDoaItems(form, seqNo, user.getUsername());
			OrderRemarkDTO orderRemark = this.getOrderRemark(form, user.getUsername());
			
			try {
				this.mobCcsDoaRequestService.insertDoa(dto, selectedCds, doaItems, orderRemark, saveAsDraft, user.getUsername());
			} catch (AppRuntimeException ae) {
				logger.error("AppRuntimeException found", ae);
				errorStockChange = true;
			}
		} else if (this.mobCcsDoaRequestService.isUpdateDoaRequest(orderDto)) {
			DoaRequestDTO dto = this.mobCcsDoaRequestService.getDoaRequestDTO(form.getRowId());
			final int seqNo = dto.getSeqNo();
			saveAsDraft = !this.doaRequestApprove(dto, form, request);

			dto.setStatus(saveAsDraft ? REASON_CD_DOA_SAVED : REASON_CD_DOA_AFTER_DELIVERY);
			dto.setRemarks(form.getRemarks());
			dto.setLastUpdBy(user.getUsername());
			dto.setLastUpdDate(now);
			
			List<DoaRequestSelectedCdDTO> selectedCds = this.getDoaRequestSelectedCds(form, seqNo, user.getUsername());
			List<DoaItemDTO> doaItems = this.getDoaItems(form, seqNo, user.getUsername());
			OrderRemarkDTO orderRemark = this.getOrderRemark(form, user.getUsername());

			try {
				this.mobCcsDoaRequestService.updateDoa(dto, selectedCds, doaItems, orderRemark, saveAsDraft, user.getUsername());
			} catch (AppRuntimeException ae) {
				logger.error("AppRuntimeException found", ae);
				errorStockChange = true;
			}
		}

		// clean up and return view
		MobCcsSessionUtil.setSession(request, ACTION_NAME, null);
		modelAndView.addObject("orderId", form.getOrderId());
		if (errorStockChange) {
			modelAndView.addObject("errorStockChange", errorStockChange);
		} else {
			modelAndView.addObject("recordUpdated", true);
		}
		modelAndView.addObject("saveAsDraft", saveAsDraft);
		return modelAndView;
	}
	
	private List<DoaRequestSelectedCdDTO> getDoaRequestSelectedCds(DoaRequestUI form, int seqNo, String username) {
		List<DoaRequestSelectedCdDTO> list = new ArrayList<DoaRequestSelectedCdDTO>();
		if (form.getReasons() != null) {
			Date now = new Date();
			for (String reason : form.getReasons()) {
				DoaRequestSelectedCdDTO cdDto = new DoaRequestSelectedCdDTO();
				cdDto.setOrderId(form.getOrderId());
				cdDto.setSeqNo(seqNo);
				cdDto.setCodeId(reason);
				cdDto.setCreateBy(username);
				cdDto.setCreateDate(now);
				cdDto.setLastUpdBy(username);
				cdDto.setLastUpdDate(now);
				
				list.add(cdDto);
			}
		}
		return list;
	}
	
	private List<DoaItemDTO> getDoaItems(DoaRequestUI form, int seqNo, String username) {
		List<DoaItemDTO> list = new ArrayList<DoaItemDTO>();
		if (form.getStocks() != null) {
			Date now = new Date();
			List<StockDTO> stockList = orderService.getStockAssignment(form.getOrderId());
			for (String stock : form.getStocks()) {
				StockDTO stockDto = this.getStockDTO(stockList, stock);
				if (stockDto != null) {
					DoaItemDTO doaItemDto = new DoaItemDTO();
					doaItemDto.setOrderId(form.getOrderId());
					doaItemDto.setSeqNo(seqNo);
					doaItemDto.setDoaItemCode(stockDto.getItemCode());
					doaItemDto.setCreateBy(username);
					doaItemDto.setCreateDate(now);
					doaItemDto.setLastUpdBy(username);
					doaItemDto.setLastUpdDate(now);
					doaItemDto.setDoaItemSerial(stock);
					
					list.add(doaItemDto);
				}
			}
		}
		return list;
	}
	
	private StockDTO getStockDTO(List<StockDTO> stockList, String doaItemSerial) {
		for (StockDTO stockDto : stockList) {
			if (stockDto.getItemSerial().equals(doaItemSerial)) {
				return stockDto;
			}
		}
		return null;
	}
	
	private OrderRemarkDTO getOrderRemark(DoaRequestUI form, String username) {
		Date now = new Date();
		OrderRemarkDTO orderRemark = new OrderRemarkDTO();
		orderRemark.setOrderId(form.getOrderId());
		orderRemark.setRemark(username + " DOA REQUEST");
		orderRemark.setCreateBy(username);
		orderRemark.setCreateDate(now);
		orderRemark.setLastUpdBy(username);
		orderRemark.setLastUpdDate(now);

		return orderRemark;
	}
	
	private boolean doaRequestApprove(DoaRequestDTO dto, DoaRequestUI form, HttpServletRequest request) {
		if (form.isApproved()) {
			if (this.mobCcsDoaRequestService.approveByManager(form.getOrderId())) {
				if (MobCcsSessionUtil.getSession(request, ACTION_NAME) instanceof String) {
					dto.setAuthorizedId((String) MobCcsSessionUtil.getSession(request, ACTION_NAME));
					return true;
				}
			} else {
				dto.setMktSerialId(StringUtils.trim(form.getMktSerialId()));
				if (StringUtils.isNotBlank(dto.getMktSerialId())) {
					return true;
				}
			}
		}
		return false;
	}
}
