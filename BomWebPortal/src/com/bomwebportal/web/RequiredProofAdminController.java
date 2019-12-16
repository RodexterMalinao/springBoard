package com.bomwebportal.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.AllOrdDocWaiveDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.OrdDocAssgnAdminDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.dto.AllDocDTO.DocType;
import com.bomwebportal.lts.dto.DocWaiveReasonDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.service.OrdDocService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.SupportDocAdminService;
import com.bomwebportal.service.SupportDocService;

public class RequiredProofAdminController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private enum Lob {
		MOB
		, LTS
		, IMS
		;
	}

	private SupportDocAdminService supportDocAdminService;
	private OrderService orderService;
	private OrdDocService ordDocService;
	private SupportDocService supportDocService;
	private LtsOrderDocumentService ltsOrderDocumentService;

	public RequiredProofAdminController() {
		setCommandClass(FormBean.class);
		setCommandName("form");
	}
	
	public OrderService getOrderService() {
		return orderService;
	}
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public OrdDocService getOrdDocService() {
		return ordDocService;
	}
	
	public void setOrdDocService(OrdDocService ordDocService) {
		this.ordDocService = ordDocService;
	}

	public SupportDocAdminService getSupportDocAdminService() {
		return supportDocAdminService;
	}

	public void setSupportDocAdminService(
			SupportDocAdminService supportDocAdminService) {
		this.supportDocAdminService = supportDocAdminService;
	}

	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(
			SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}
	
	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}
	
	private boolean stringEquals(String value, String value2) {
		if (StringUtils.isBlank(value)) {
			return StringUtils.isBlank(value2);
		}
		return value.equals(value2);
	}

	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors) throws Exception {

		//String orderId = request.getParameter("orderId");

		BomSalesUserDTO bomsalesuser = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		String username = bomsalesuser.getUsername();
		String actionType = ((String) request.getParameter("actionType") != null) ? 
				(String) request.getParameter("actionType"): "NONE";
		FormBean form = (FormBean) command;

		// only update WAIVE_REASON in this phase
		List<OrdDocAssgnAdminDTO> ordDocAssgnList = supportDocAdminService.getRequiredProofTypes(form.getOrderId()); // original value
		List<RequiredProof> requiredProofList = form.getRequiredProofList(); // new list value
		List<OrdDocAssgnAdminDTO> updateList = new ArrayList<OrdDocAssgnAdminDTO>();
		if (!this.isEmpty(ordDocAssgnList) && !this.isEmpty(requiredProofList)) {
			for (OrdDocAssgnAdminDTO assgn : ordDocAssgnList) {
				for (RequiredProof dto : requiredProofList) {
					if (assgn.getDocType().equals(dto.getData().getDocType())) {
						if (!stringEquals(assgn.getWaiveReason(), dto.getWaiveReason())) {
							OrdDocAssgnAdminDTO updateDTO = new OrdDocAssgnAdminDTO();
							updateDTO.setOrderId(form.getOrderId());
							updateDTO.setDocType(dto.getData().getDocType());
							updateDTO.setWaiveReason(dto.getWaiveReason());
							updateDTO.setLastUpdBy(username);
							updateList.add(updateDTO);
						}
						break;
					}
				}
			}
		}
		this.supportDocAdminService.updateWaiveReason(updateList);

		ModelAndView modelAndView = new ModelAndView(new RedirectView("requiredproofadmin.html"));
		modelAndView.addObject("orderId", form.getOrderId());
		modelAndView.addObject("updatedCount", updateList.size());
		if (!"NONE".equals(actionType)) {
			modelAndView.addObject("actionType", actionType);
		}
		return modelAndView;
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		logger.debug("Creating form backing object");
		
		FormBean form = new FormBean();
		String orderId = request.getParameter("orderId");
		form.setOrderId(orderId);
		
		OrderDTO order = orderService.getOrder(orderId);
		
		List<OrdDocAssgnAdminDTO> ordDocAssgnList = supportDocAdminService.getRequiredProofTypes(orderId);
		
		ArrayList<RequiredProof> requiredProof = new ArrayList<RequiredProof>();
		for (OrdDocAssgnAdminDTO assgn : ordDocAssgnList) {
			RequiredProof rp = new RequiredProof(assgn);//createRequiredProofData(assgn);
			requiredProof.add(rp);
		}

		form.setOrder(order);
		form.setRequiredProofs(requiredProof);
		
		return form;
	}
	
	public Map<String, Object> referenceData(HttpServletRequest request) {
		Map<String, Object> referenceData = new HashMap<String, Object>();

		String orderId = request.getParameter("orderId");
		OrderDTO orderDto = this.orderService.getOrder(orderId);
		Lob lob = Lob.valueOf(orderDto.getOrderSumLob());
		referenceData.put("lob", lob);
		String actionType = ((String) request.getParameter("actionType") != null) ? 
				(String) request.getParameter("actionType"): "NONE";
		referenceData.put("actionType", actionType);
		
		List<OrdDocAssgnAdminDTO> ordDocAssgnList = supportDocAdminService.getRequiredProofTypes(orderId);
		switch (lob) {
		case LTS:
			Map<String, List<DocWaiveReasonDTO>> ltsWaiveReasons = new HashMap<String, List<DocWaiveReasonDTO>>();
			for (OrdDocAssgnAdminDTO assgn : ordDocAssgnList) {
				List<DocWaiveReasonDTO> reasons = this.ltsOrderDocumentService.getWaiveReasonList(assgn.getDocType());
				if (!this.isEmpty(reasons)) {
					ltsWaiveReasons.put(assgn.getDocType(), reasons);
				}
			}
			referenceData.put("ltsWaiveReasons", ltsWaiveReasons);
			break;
		case MOB:
		case IMS:
		default:
			// ordDocAssgnList save `docType` in String type
			Map<String, List<AllOrdDocWaiveDTO>> waiveReasons = new HashMap<String, List<AllOrdDocWaiveDTO>>();
			for (OrdDocAssgnAdminDTO assgn : ordDocAssgnList) {
				//DocType docType = DocType.valueOf(assgn.getDocType());
				List<AllOrdDocWaiveDTO> reasons = this.supportDocService.getAllOrdDocWaiveDTOALL(orderDto.getOrderSumLob(), assgn.getDocType());
				if (!this.isEmpty(reasons)) {
					waiveReasons.put(assgn.getDocType(), reasons);
				}
			}
			referenceData.put("waiveReasons", waiveReasons);
			break;
		}
		
		referenceData.put("capturedRecordList", ordDocService.getOrdDoc(orderId));
		
		return referenceData;
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
	/*
	private RequiredProof createRequiredProofData(OrdDocAssgnAdminDTO dto) {
		//List<AllOrdDocWaiveDTO> waives = supportDocService.getAllOrdDocWaiveDTOALL(dto.getLob(), DocType.valueOf(dto.getDocType()));
		RequiredProof rp = new RequiredProof(dto);
		//rp.setAvailableReasons(waives);
		
		return rp;
	}
	*/
	
	
	
	
	public static class FormBean {//implements Serializable {
		
		private String orderId;
		
		private OrderDTO order;
		private List<RequiredProof> requiredProofList;


		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}

		public OrderDTO getOrder() {
			return order;
		}

		public void setOrder(OrderDTO order) {
			this.order = order;
		}

		public List<RequiredProof> getRequiredProofList() {
			return requiredProofList;
		}

		public void setRequiredProofs(List<RequiredProof> requiredProofList) {
			this.requiredProofList = requiredProofList;

		}
		
	}
	
	
	public static class RequiredProof {// implements Serializable {
		
		private OrdDocAssgnAdminDTO data;


		//private List<AllOrdDocWaiveDTO> availableReasons;
		//private boolean override = false;
		private String waiveReason;
		private boolean collected;
		
		private RequiredProof() {}
		
		public RequiredProof(OrdDocAssgnAdminDTO dto) {
			setData(dto);
		}
		

		
		public OrdDocAssgnAdminDTO getData() {
			return data;
		}
		public void setData(OrdDocAssgnAdminDTO data) {
			this.data = data;
			setWaiveReason(data.getWaiveReason());
			setCollected("Y".equals(data.getCollectedInd()));

		}

		/*
		public List<AllOrdDocWaiveDTO> getAvailableReasons() {
			return availableReasons;
		}
		public void setAvailableReasons(List<AllOrdDocWaiveDTO> availableReasons) {
			this.availableReasons = availableReasons;
		}
		*/
		/*
		public boolean isOverride() {
			return override;
		}
		public void setOverride(boolean override) {
			this.override = override;
		}*/
		public String getWaiveReason() {
			return waiveReason;
		}
		public void setWaiveReason(String waiveReason) {
			this.waiveReason = waiveReason;
		}
		public boolean isCollected() {
			return collected;
		}
		public void setCollected(boolean collected) {
			this.collected = collected;
		}
		
	}
	

}
