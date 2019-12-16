package com.bomwebportal.mob.ccs.web;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.ContactDTO;
import com.bomwebportal.mob.ccs.dto.CsiCaseDTO;
import com.bomwebportal.mob.ccs.dto.CsiCaseLogDTO;
import com.bomwebportal.mob.ccs.dto.CsiSmsLogDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ccs.service.DeliveryService;
import com.bomwebportal.mob.ccs.service.MobCcsCsiCaseLogService;
import com.bomwebportal.mob.ccs.service.MobCcsCsiCaseService;
import com.bomwebportal.mob.ccs.service.MobCcsCsiSmsLogService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.Utility;

public class MobCcsCsiCaseCreateController extends SimpleFormController {
	private MobCcsCsiCaseService mobCcsCsiCaseService;
	private MobCcsCsiCaseLogService mobCcsCsiCaseLogService;
	private CustomerProfileService customerProfileService;
	private CodeLkupService codeLkupService;
    private OrderService orderService;
    private DeliveryService deliveryService;
    private MobCcsCsiSmsLogService mobCcsCsiSmsLogService;
    

	public MobCcsCsiCaseService getMobCcsCsiCaseService() {
		return mobCcsCsiCaseService;
	}

	public void setMobCcsCsiCaseService(MobCcsCsiCaseService mobCcsCsiCaseService) {
		this.mobCcsCsiCaseService = mobCcsCsiCaseService;
	}

	public MobCcsCsiCaseLogService getMobCcsCsiCaseLogService() {
		return mobCcsCsiCaseLogService;
	}

	public void setMobCcsCsiCaseLogService(
			MobCcsCsiCaseLogService mobCcsCsiCaseLogService) {
		this.mobCcsCsiCaseLogService = mobCcsCsiCaseLogService;
	}

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}

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

	public DeliveryService getDeliveryService() {
		return deliveryService;
	}

	public void setDeliveryService(DeliveryService deliveryService) {
		this.deliveryService = deliveryService;
	}

	public MobCcsCsiSmsLogService getMobCcsCsiSmsLogService() {
		return mobCcsCsiSmsLogService;
	}

	public void setMobCcsCsiSmsLogService(
			MobCcsCsiSmsLogService mobCcsCsiSmsLogService) {
		this.mobCcsCsiSmsLogService = mobCcsCsiSmsLogService;
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		CsiCaseDTO form = new CsiCaseDTO();
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		String caseNo = ServletRequestUtils.getRequiredStringParameter(request, "caseNo");
		
		if (StringUtils.isNotBlank(caseNo)) {// Update the CSI Case
			CsiCaseDTO recallDto = mobCcsCsiCaseService.getCsiCaseALLByCaseNo(caseNo);
			form.setOrderId(orderId);
			form.setCaseNo(caseNo);
			form.setFollowUpType(recallDto.getFollowUpType());
			form.setCaseStatus(recallDto.getCaseStatus());
			form.setContactCount(recallDto.getContactCount());
			form.setSmsCount(recallDto.getSmsCount());
			form.setNextCallSchDate(recallDto.getNextCallSchDate());
			form.setNextCallSchDateStr(Utility.date2String(form.getNextCallSchDate(),"dd/MM/yyyy"));
			form.setOnsiteVisitInd(recallDto.getOnsiteVisitInd());
			form.setRemark(recallDto.getRemark());
			form.setCreateBy(recallDto.getCreateBy());
			form.setCreateDate(recallDto.getCreateDate());
			form.setLastUpdDate(recallDto.getLastUpdDate());
			form.setLastUpdBy(recallDto.getLastUpdBy());
			
			form.setOnsiteVisitResult(recallDto.getOnsiteVisitResult());
			form.setFalloutReportDateStr(Utility.date2String(recallDto.getFalloutReportDate(),"dd/MM/yyyy"));
			form.setFalloutReportDate(recallDto.getFalloutReportDate());
			form.setFalloutReportTSlot(recallDto.getFalloutReportTSlot());
			form.setOnsiteVisitInd(recallDto.getOnsiteVisitInd());
			form.setOVICreateDate(recallDto.getOVICreateDate());
			
			form.setNextCallSchTime(recallDto.getNextCallSchTime());
			
		} else { //Create New CSI Case
			if (StringUtils.isNotBlank(orderId)) {
				CustomerProfileDTO dto = this.customerProfileService.getCustomerProfileAll(orderId);
				if (dto != null) {
					/*form.setContactName(dto.getContactName());
					form.setContactPhone(dto.getContactPhone());
					form.setContactEmail(dto.getEmailAddr());*/
					form.setOrderId(orderId);
				}
			}
			OrderDTO orderDTO = orderService.getOrderWithPaidAmount(orderId);
			form.setFollowUpType(getFollowUpType(orderDTO));
			if (form.getFollowUpType().equalsIgnoreCase("FU08")) {//Others
				form.setFollowUpTypeisLock(false);
			}
			form.setCreateBy(user.getUsername());
		}
		
		return form;
	}
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		logger.info("MobCcsCsiCaseCreateController ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		String caseNo = ServletRequestUtils.getRequiredStringParameter(request, "caseNo");
		
		CustomerProfileDTO customerProfile = customerProfileService.getCustomerProfileAll(orderId);
		referenceData.put("customerProfile", customerProfile);
		OrderDTO orderDTO = orderService.getOrderWithPaidAmount(orderId);
		referenceData.put("orderDTO", orderDTO);
		List<ContactDTO> contact = this.deliveryService.findContactDTOList(orderId);
		referenceData.put("contact", contact);
		DeliveryUI deliveryUI = deliveryService.getMobCcsDelivery(orderId);
		referenceData.put("deliveryUI", deliveryUI);

		Map<String,String> falloutReportTSlotLst = new LinkedHashMap<String, String>();
		falloutReportTSlotLst.put("0900", "09:00");
		falloutReportTSlotLst.put("1200", "12:00");
		falloutReportTSlotLst.put("1500", "15:00");
		falloutReportTSlotLst.put("1700", "17:00");
		referenceData.put("falloutReportTSlotLst",falloutReportTSlotLst);
		
		Map<String,String> nextCallSchTimeLst = new LinkedHashMap<String, String>();
		nextCallSchTimeLst.put("", "----");
		for (int i=0; i<24 ; i++){
			NumberFormat formatter = new DecimalFormat("00");
			nextCallSchTimeLst.put(formatter.format(i)+"00", formatter.format(i)+":00");
		}
		referenceData.put("nextCallSchTimeLst",nextCallSchTimeLst);
		
		referenceData.put("followUpTypeLst", this.codeLkupService.getCodeLkupDTOALL("FOLLOW_UP_TYPE"));
		referenceData.put("csiCaseStatusLst", this.codeLkupService.getCodeLkupDTOALL("CSI_CASE_STATUS"));
		
		if (caseNo!= null && StringUtils.isNotBlank(caseNo)){
			List<CsiCaseLogDTO> resultCallLogList = mobCcsCsiCaseLogService.getCsiCaseLogALLByCaseNo(caseNo);
			referenceData.put("resultCallLogList", resultCallLogList);
			
			List<CsiSmsLogDTO> resultSmsLogList = mobCcsCsiSmsLogService.getCsiSmsLogALLByCaseId(caseNo);
			referenceData.put("resultSmsLogList", resultSmsLogList);
		}
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");//add by wilson 20121218, for create button control
		referenceData.put("user", salesUserDto);//add by wilson 20121218, for create button control

		return referenceData;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		CsiCaseDTO form = (CsiCaseDTO) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobccscsicase.html"));
		CustomerProfileDTO customerProfile = this.customerProfileService.getCustomerProfileAll(form.getOrderId());
		
		if (customerProfile != null) {
			CsiCaseDTO dto = new CsiCaseDTO();
			//New Case
			if (form.getCaseNo() == null || StringUtils.isBlank(form.getCaseNo())){
				dto.setOrderId(StringUtils.trim(form.getOrderId()));
				dto.setFollowUpType(StringUtils.trim(form.getFollowUpType()));
				dto.setCaseStatus(StringUtils.trim(form.getCaseStatus()));
				//System.out.println("form.getNextCallSchDateStr():"+form.getNextCallSchDateStr());
				dto.setNextCallSchDate(Utility.string2Date(form.getNextCallSchDateStr()));
				//System.out.println("dto.getNextCallSchDate():"+dto.getNextCallSchDate());
				dto.setRemark(StringUtils.trim(form.getRemark()));
				dto.setCreateBy(user.getUsername());
				dto.setLastUpdBy(user.getUsername());

				//System.out.println("form.getFalloutReportDateStr()" + form.getFalloutReportDateStr());
				dto.setFalloutReportDate(Utility.string2Date(form.getFalloutReportDateStr()));
				dto.setFalloutReportTSlot(form.getFalloutReportTSlot());
				
				dto.setNextCallSchTime(form.getNextCallSchTime());
				mobCcsCsiCaseService.insertCsiCaseDTO(dto);
				modelAndView.addObject("caseNo", "");
			} else {
			//Recall & Update the case
				dto.setCaseNo(form.getCaseNo());
				dto.setOrderId(StringUtils.trim(form.getOrderId()));
				dto.setFollowUpType(StringUtils.trim(form.getFollowUpType()));
				dto.setCaseStatus(StringUtils.trim(form.getCaseStatus()));
				dto.setNextCallSchDate(Utility.string2Date(form.getNextCallSchDateStr()));
				dto.setRemark(StringUtils.trim(form.getRemark()));
				dto.setLastUpdBy(user.getUsername());
				
				//System.out.println("form.getFalloutReportDateStr()" + form.getFalloutReportDateStr());
				dto.setFalloutReportDate(Utility.string2Date(form.getFalloutReportDateStr()));
				dto.setFalloutReportTSlot(form.getFalloutReportTSlot());
				dto.setOnsiteVisitInd(form.getOnsiteVisitInd());
				
				if (!form.getOnsiteVisitInd()){
					dto.setOVICreateDate(null);
				}
				
				//System.out.println("form.getOVICreateDate()" + form.getOVICreateDate());
				dto.setOVICreateDate(form.getOVICreateDate());
				//System.out.println("form.getNextCallSchTime()" + form.getNextCallSchTime());
				dto.setNextCallSchTime(form.getNextCallSchTime());
				
				mobCcsCsiCaseService.updateCsiCaseDTO(dto);
				
				modelAndView.addObject("caseNo", form.getCaseNo());
			}
			modelAndView.addObject("recordCreated", true);
		}
		modelAndView.addObject("orderId", form.getOrderId());
		
		
		return modelAndView;
	}
	
	public String getFollowUpType(OrderDTO orderDTO ){
		String result = "";

		List<String> codeTypes = Arrays.asList("STOCK_FAIL","DOC_FAIL","PAY_FAIL","DEL_FAIL","ON_DOA","MNP_REJ","DOA","FS_FAIL") ;
		
		if (orderDTO.getOrderStatus().equalsIgnoreCase("99")){
			String codeType = this.codeLkupService.getCodeTypeById(codeTypes, orderDTO.getReasonCd());
			//System.out.println("codeType:"+codeType);
				if (codeType.equalsIgnoreCase("STOCK_FAIL")){
					result = "FU01"; //Support Document
				}else if (codeType.equalsIgnoreCase("DOC_FAIL")){
					result = "FU02"; //Out of Stock
				}else if (codeType.equalsIgnoreCase("PAY_FAIL")){
					result = "FU03"; //Payment Reject
				}else if (codeType.equalsIgnoreCase("DEL_FAIL")){
					result = "FU04"; //Delivery Failed
				}else if (codeType.equalsIgnoreCase("ON_DOA")){
					result = "FU05"; //On-site DOA
				}else if (codeType.equalsIgnoreCase("MNP_REJ")){
					result = "FU06"; //MNP Rejected
				}else if (codeType.equalsIgnoreCase("DOA")){
					result = "FU07"; //DOA after delivery
				}else if (codeType.equalsIgnoreCase("FS_FAIL")){
					result = "FU09"; //F&S Fail
				}else {
					result = "FU08"; //Others
				}
		} else {
			result = "FU08"; //Others
		}
		//System.out.println("result:"+result);
		return result;
	}
}
