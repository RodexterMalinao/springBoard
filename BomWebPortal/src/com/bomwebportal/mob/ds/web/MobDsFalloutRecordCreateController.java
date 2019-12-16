package com.bomwebportal.mob.ds.web;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

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
import com.bomwebportal.mob.ccs.service.CodeLkupService;
import com.bomwebportal.mob.ds.dto.MobDsFalloutRecordDTO;
import com.bomwebportal.mob.ds.dto.MobDsFalloutLogDTO;
import com.bomwebportal.mob.ds.service.MobDsFalloutLogService;
import com.bomwebportal.mob.ds.service.MobDsFalloutRecordService;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;

public class MobDsFalloutRecordCreateController extends SimpleFormController  {
	
	static final String DSORDERSTATUS_INITIAL = BomWebPortalConstant.BWP_ORDER_INITIAL;
	static final String DSORDERSTATUS_FAILED = BomWebPortalConstant.BWP_ORDER_FAILED;
	static final String DSORDERSTATUS_FALLOUT = BomWebPortalConstant.BWP_ORDER_FALLOUT;
	
	private MobDsFalloutRecordService mobDsFalloutRecordService;
	private CodeLkupService codeLkupService;
	private OrderService orderService;
	private MobDsFalloutLogService mobDsFalloutLogService;
	private CustomerProfileService customerProfileService;
	
	public MobDsFalloutRecordService getMobDsFalloutRecordService() {
		return mobDsFalloutRecordService;
	}
	public void setMobDsFalloutRecordService(
			MobDsFalloutRecordService mobDsFalloutRecordService) {
		this.mobDsFalloutRecordService = mobDsFalloutRecordService;
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
	public MobDsFalloutLogService getMobDsFalloutLogService() {
		return mobDsFalloutLogService;
	}
	public void setMobDsFalloutLogService(
			MobDsFalloutLogService mobDsFalloutLogService) {
		this.mobDsFalloutLogService = mobDsFalloutLogService;
	}
	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}
	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}
	
	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {
		MobDsFalloutRecordDTO form = new MobDsFalloutRecordDTO();
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		String caseNo = ServletRequestUtils.getRequiredStringParameter(request, "caseNo");
		
		if (StringUtils.isNotBlank(caseNo)) {// Update the Fallout Case
			MobDsFalloutRecordDTO  recallDto = mobDsFalloutRecordService.getFalloutRecordALLByCaseNo(caseNo);
			form.setOrderId(orderId);
			form.setStaffId(user.getUsername());
			form.setCaseNo(caseNo);
			form.setFalloutStatus(recallDto.getFalloutStatus());
			form.setFalloutType(recallDto.getFalloutType());
			form.setFalloutCatOpt(recallDto.getFalloutCatOpt());
			form.setRemark(recallDto.getRemark());
			form.setCreateBy(recallDto.getCreateBy());
			form.setCreateDate(recallDto.getCreateDate());
			form.setLastUpdDate(recallDto.getLastUpdDate());
			form.setLastUpdBy(recallDto.getLastUpdBy());
			
			
		} else { //Create New Fallout Case
			if (StringUtils.isNotBlank(orderId)) {
				CustomerProfileDTO dto = this.customerProfileService.getCustomerProfile(orderId);
				if (dto != null) {
	
					form.setOrderId(orderId);
					form.setStaffId(user.getUsername());
				}
			}
			form.setCreateBy(user.getUsername());			
		}
		
		return form;
	}
	
	
	protected Map referenceData(HttpServletRequest request) throws Exception {
		//logger.info("MobDsFalloutRecordCreateController ReferenceData called");
		Map<String, Object> referenceData = new HashMap<String, Object>();
		
		String orderId = ServletRequestUtils.getRequiredStringParameter(request, "orderId");
		String caseNo = ServletRequestUtils.getRequiredStringParameter(request, "caseNo");
		
		CustomerProfileDTO customerProfile = customerProfileService.getCustomerProfile(orderId);
		referenceData.put("customerProfile", customerProfile);
		OrderDTO orderDTO = orderService.getOrderWithPaidAmount(orderId);
		referenceData.put("orderDTO", orderDTO);		

		/*Map<String,String> falloutStatusList = new LinkedHashMap<String, String>();		
		falloutStatusList.put("01", "New Create");
		falloutStatusList.put("02", "Cancel by Sales");
		falloutStatusList.put("03", "Wrong Fallout");
		falloutStatusList.put("04", "QC Problem");
		falloutStatusList.put("05", "Can’t Settle");
		falloutStatusList.put("06", "Settled");
		referenceData.put("falloutStatusList",falloutStatusList);
		
		Map<String,String> falloutCatOptList = new LinkedHashMap<String, String>();
		falloutCatOptList.put("01", "Account Information");
		falloutCatOptList.put("02", "Customer Information");
		falloutCatOptList.put("03", "Stock");
		falloutCatOptList.put("04", "Program Offer");
		falloutCatOptList.put("05", "Support Document");
		falloutCatOptList.put("06", "NT Case");
		falloutCatOptList.put("07", "Others");
		referenceData.put("falloutCatOptList",falloutCatOptList);*/
		
		referenceData.put("falloutStatusList", this.codeLkupService.getCodeLkupDTOALL("DS_FALLOUT_STATUS"));
		referenceData.put("falloutCatOptList", this.codeLkupService.getCodeLkupDTOALL("DS_FALLOUT_CAT_OPT"));
		if (caseNo!= null && StringUtils.isNotBlank(caseNo)){
			List<MobDsFalloutLogDTO> resultFalloutLogList = mobDsFalloutLogService.getFalloutLogDTOALLByCaseNo(caseNo,orderId);
			referenceData.put("resultFalloutLogList", resultFalloutLogList);
			
		}		
		
		BomSalesUserDTO salesUserDto = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		referenceData.put("user", salesUserDto);

		return referenceData;
	}
	
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws Exception {
		MobDsFalloutRecordDTO form = (MobDsFalloutRecordDTO) command;
		BomSalesUserDTO user = (BomSalesUserDTO) request.getSession().getAttribute("bomsalesuser");
		ModelAndView modelAndView = new ModelAndView(new RedirectView("mobdsfalloutrecord.html"));
		CustomerProfileDTO customerProfile = this.customerProfileService.getCustomerProfile(form.getOrderId());
		
		if (customerProfile != null) {
			MobDsFalloutRecordDTO dto = new MobDsFalloutRecordDTO();
			//New Case
			if (form.getCaseNo() == null || StringUtils.isBlank(form.getCaseNo())){
				dto.setOrderId(StringUtils.trim(form.getOrderId()));
				dto.setStaffId(user.getUsername());			
				dto.setFalloutStatus(StringUtils.trim(form.getFalloutStatus()));
				dto.setFalloutType(StringUtils.trim(form.getFalloutType()));
				dto.setFalloutCatOpt(StringUtils.trim(form.getFalloutCatOpt()));				
				dto.setRemark(StringUtils.trim(form.getRemark()));
				dto.setCreateBy(user.getUsername());
				dto.setLastUpdBy(user.getUsername());
				mobDsFalloutRecordService.insertFalloutRecordDTO(dto);
				
				//orderService.updateBomWebOrderStatus(form.getOrderId(), DSORDERSTATUS_FALLOUT );
				if (! (Integer.parseInt(dto.getFalloutStatus()) == 6 )){    //not settled
					String getCurrentStatus = orderService.getDsOrderStatus(form.getOrderId());
					if (!DSORDERSTATUS_FALLOUT.equals(getCurrentStatus)) {
						orderService.updateDsPrevOrderStatus(form.getOrderId(), getCurrentStatus);
						orderService.updateDsOrderStatus(form.getOrderId(), DSORDERSTATUS_FALLOUT);
					}
				} else {  //settled is choose, check if the current status is fallout 
					
					//unused code?
					if (DSORDERSTATUS_FALLOUT.equalsIgnoreCase(orderService.getDsOrderStatus(form.getOrderId()))){
						if (!mobDsFalloutRecordService.hasUnsettledFallout(form.getOrderId())) {
							String getPrevStatus = orderService.getDsPrevOrderStatus(form.getOrderId());
							orderService.updateDsPrevOrderStatus(form.getOrderId(), null);				
							orderService.updateDsOrderStatus(form.getOrderId(), getPrevStatus);
						}
					}
					//
				}
				
				//when the fallout record is created for an order, the order status will be changed to "fallout"
				modelAndView.addObject("caseNo", "");
				
				
				
			} else {
			//Recall & Update the case
				dto.setCaseNo(form.getCaseNo());
				dto.setStaffId(user.getUsername());	
				dto.setFalloutStatus(StringUtils.trim(form.getFalloutStatus()));
				dto.setFalloutType(StringUtils.trim(form.getFalloutType()));
				//System.out.print(StringUtils.trim(Integer.parseInt(dto.getFalloutStatus()));
				dto.setFalloutCatOpt(StringUtils.trim(form.getFalloutCatOpt()));	
				dto.setRemark(StringUtils.trim(form.getRemark()));
				dto.setLastUpdBy(user.getUsername());	
						
				mobDsFalloutRecordService.updateFalloutRecordDTO(dto);
				
				if (mobDsFalloutRecordService.getLatestCaseNoByOrderId(form.getOrderId())== Integer.parseInt(form.getCaseNo())){
					if (Integer.parseInt(dto.getFalloutStatus()) == 6 ){   //settled is chosen
						if (DSORDERSTATUS_FALLOUT.equalsIgnoreCase(orderService.getDsOrderStatus(form.getOrderId()))){
							if (!mobDsFalloutRecordService.hasUnsettledFallout(form.getOrderId())) {
								String getPrevStatus = orderService.getDsPrevOrderStatus(form.getOrderId());
								orderService.updateDsPrevOrderStatus(form.getOrderId(), null);				
								orderService.updateDsOrderStatus(form.getOrderId(), getPrevStatus);
							}
						}
						//System.out.println(dto.getFalloutStatus());
						//The status will back to normal when the fallout status is changed to settled.
					}else {
						if (!DSORDERSTATUS_FALLOUT.equalsIgnoreCase(orderService.getDsOrderStatus(form.getOrderId()))){
							String getCurrentStatus = orderService.getDsOrderStatus(form.getOrderId());
							if (!DSORDERSTATUS_FALLOUT.equals(getCurrentStatus)) {
								orderService.updateDsPrevOrderStatus(form.getOrderId(), getCurrentStatus);
								orderService.updateDsOrderStatus(form.getOrderId(), DSORDERSTATUS_FALLOUT);
							}
						}
					}
				}
				
				modelAndView.addObject("caseNo", form.getCaseNo());
			}
			modelAndView.addObject("recordCreated", true);
		}
		modelAndView.addObject("orderId", form.getOrderId());
		
		
		return modelAndView;
	}
	
}
