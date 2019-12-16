package com.bomwebportal.sig.web;


import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dto.CustomerProfileDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.UserTimeoutException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.service.CustomerProfileService;
import com.bomwebportal.service.LoginService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.service.SupportDocService;
import com.bomwebportal.sig.dto.SignCaptureDTO;
import com.bomwebportal.sig.service.SignCaptureService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@Controller
public class SignCaptureController{

	protected final Log logger = LogFactory.getLog(getClass());

	private OrderService orderService;
	private LoginService loginService;
	private CustomerProfileService customerProfileService;
	private SignCaptureService signCaptureService;
	private SupportDocService supportDocService;

	public LoginService getLoginService() {
		return loginService;
	}

	public void setLoginService(LoginService loginService) {
		this.loginService = loginService;
	}
	
	public SignCaptureService getSignCaptureService() {
		return signCaptureService;
	}

	public void setSignCaptureService(SignCaptureService signCaptureService) {
		this.signCaptureService = signCaptureService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	

	public CustomerProfileService getCustomerProfileService() {
		return customerProfileService;
	}

	public void setCustomerProfileService(
			CustomerProfileService customerProfileService) {
		this.customerProfileService = customerProfileService;
	}

	public SupportDocService getSupportDocService() {
		return supportDocService;
	}

	public void setSupportDocService(SupportDocService supportDocService) {
		this.supportDocService = supportDocService;
	}

	@ModelAttribute("signCaptureModel")
	private SignCaptureDTO formBackingObject(String reqId) throws UserTimeoutException {
		
		SignCaptureDTO signCaptureDTO = signCaptureService.getSignCaptureDTO(reqId);
		if (signCaptureDTO != null){
			
			if (signCaptureDTO.getLastUpdDate() != null ){
				int timeout = 0;
				
				List<CodeLkupDTO> checkUidCdLkupList = LookupTableBean.getInstance().getCodeLkupList().get("MOB_SIGN_TIMEOUT");
					
					if (checkUidCdLkupList != null&& !checkUidCdLkupList.isEmpty()) {						
						timeout = Integer.parseInt(checkUidCdLkupList.get(0).getCodeId());	
					} 
				Date now = new Date();		
			    Date lastUpdDate = signCaptureDTO.getLastUpdDate();
				Calendar cal = Calendar.getInstance();
				cal.setTime(lastUpdDate);
				cal.add(Calendar.MINUTE, timeout);
				lastUpdDate = cal.getTime();
				
				if (now.after(lastUpdDate)){
					throw new com.bomwebportal.exception.UserTimeoutException("Fail to capture signature. Please rescan the QR code and try again.");
				}
			}
		}else{
			throw new com.bomwebportal.exception.UserTimeoutException("Fail to capture signature. Please rescan the QR code and try again.");
		}
		return signCaptureDTO;
		
	}
	
	@RequestMapping(value="/signcapture", method=RequestMethod.GET)
	public String showForm(@RequestParam(value="reqId") String reqId,HttpSession session, Model model) {
	
		return "signcapture";
	}
	
	@RequestMapping(value="/signcapture", method=RequestMethod.POST)
	public String submit(String reqId, @ModelAttribute("signCaptureModel")SignCaptureDTO signCaptureDTO, HttpSession session, Model model) throws UserTimeoutException {
		boolean success = false;
		int timeout = 0;
		
		List<CodeLkupDTO> checkUidCdLkupList = LookupTableBean.getInstance().getCodeLkupList().get("MOB_SIGN_TIMEOUT");
			
		if (checkUidCdLkupList != null&& !checkUidCdLkupList.isEmpty()) {						
			timeout = Integer.parseInt(checkUidCdLkupList.get(0).getCodeId());	
		}
		
		int result = signCaptureService.updateSignCaptureReq(signCaptureDTO,timeout);	
		if (result > 0){
			success = true;
		}else{
			throw new com.bomwebportal.exception.UserTimeoutException("Fail to capture signature. Please rescan the QR code and try again.");
		}

		model.addAttribute("success", success);

		return "signcapture";
	}
	
	
	@RequestMapping(value="/getsignature")
	public ModelAndView getSignature(String reqId, Model model) {
		SignCaptureDTO signCaptureDTO = signCaptureService.getSignCaptureDTO(reqId);
		JSONArray jsonArray = new JSONArray();
				
		if (signCaptureDTO != null) {
			JSONObject json = new JSONObject();
			json.put("id", signCaptureDTO.getReqId());
			json.put("signature", signCaptureDTO.getSignature());
			jsonArray.add(json);
	
		}
		return new ModelAndView("ajax_view", "jsonArray",	jsonArray);
	}
	
	
	@ModelAttribute
	public void referenceData(@RequestParam(value="reqId") String reqId,HttpSession session, Model model) {
	
		SignCaptureDTO signCaptureDTO = signCaptureService.getSignCaptureDTO(reqId);
		if (signCaptureDTO !=null){
			model.addAttribute("signCaptureDTO", signCaptureDTO);
			String dbSessionId = loginService.getDbRecordSessionId(signCaptureDTO.getCreateBy());
			model.addAttribute("currentSessionId", dbSessionId);
			
			OrderDTO orderDto = orderService.getOrder(signCaptureDTO.getOrderId());
			if (orderDto !=null){
				model.addAttribute("orderDto", orderDto);
				model.addAttribute("signCaptureAllOrdDocDTOList", supportDocService.retrieveAllOrdDocDTOList(orderDto.getOrderId(), orderDto.getAppInDate(), true));
			}
			CustomerProfileDTO customerInfoDto = customerProfileService.getCustomerProfile(signCaptureDTO.getOrderId());// (CustomerProfileDTO)request.getSession().getAttribute("customer");
			if (customerInfoDto !=null){
				String contactName = customerInfoDto.getContactName();
				if (!"BS".equals(customerInfoDto.getIdDocType())){
					contactName = customerInfoDto.getCustLastName()+ " " +customerInfoDto.getCustFirstName();
				}

				model.addAttribute("contactName", contactName);
			}
			
		}

	}

}