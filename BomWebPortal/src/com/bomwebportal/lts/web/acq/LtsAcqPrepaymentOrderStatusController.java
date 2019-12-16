package com.bomwebportal.lts.web.acq;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.util.acq.LtsAcqSbOrderHelper;

public class LtsAcqPrepaymentOrderStatusController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	protected OrderStatusService orderStatusService;
	
	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		JSONObject jsonResult = new JSONObject();
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);				
		if (acqOrderCapture == null || acqOrderCapture.getSbOrder() == null) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", "Session timeout, please re-issue order again.");
			return new ModelAndView("ajax_ltsacqprepaymentlkup", jsonResult); 
		} 
		
		if (LtsAcqSbOrderHelper.isDummyCustomer(acqOrderCapture.getCustomerDetailProfileLtsDTO().getCustNum())) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", "Customer not assigned, please suspend and recall order after DRG downtime.");
			return new ModelAndView("ajax_ltsacqprepaymentlkup", jsonResult); 
		}
		
		if (LtsAcqSbOrderHelper.isDummyAccount(acqOrderCapture.getAccountDetailProfileLtsDTO()[0].getAcctNum())) {
			jsonResult.put("status", "false");
			jsonResult.put("errorMsg", "Account not assigned, please suspend and recall order after DRG downtime.");
			return new ModelAndView("ajax_ltsacqprepaymentlkup", jsonResult); 
		}
		
		String isPrepaymentRequired = request.getParameter("isPrepaymentRequired");
			    
		if (StringUtils.equals("Y", isPrepaymentRequired)) {
			ServiceDetailDTO[] serviceDetails = acqOrderCapture.getSbOrder().getSrvDtls();
			BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
			for (int i=0; serviceDetails!=null && i<serviceDetails.length; ++i) {
				// update order status = W
				orderStatusService.setAwaitPrepaymentStatus(acqOrderCapture.getSbOrder().getOrderId(), serviceDetails[i].getDtlId(), bomSalesUser.getUsername());
			}
 	    }		
				
		jsonResult.put("status", "true");
		return new ModelAndView("ajax_ltsacqprepaymentlkup", jsonResult);
		
	}

	/**
	 * @return the orderStatusService
	 */
	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	/**
	 * @param orderStatusService the orderStatusService to set
	 */
	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}	
	
}
