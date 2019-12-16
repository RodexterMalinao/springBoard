package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.bomwebportal.dto.AlertOrderDTO;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.form.LtsAlertMessageFormDTO;
import com.bomwebportal.lts.service.LtsAlertMessageService;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsAlertMessageController extends SimpleFormController {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private LtsAlertMessageService ltsAlertMessageService;
	
	/*	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCaptureDTO = (OrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
		if(orderCaptureDTO == null){
			request.getSession().setAttribute("orderAmendDTO", null);
		}
		return super.handleRequestInternal(request, response);
	}*/
	
	@Override
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		LtsAlertMessageFormDTO formDTO = new LtsAlertMessageFormDTO();
		String srvType = request.getParameter(LtsConstant.REQUEST_PARAM_SRV_TYPE);
		try{
			AlertOrderDTO[] orderList = ltsAlertMessageService.getOrderListWithOutstandingWq(srvType, bomSalesUserDTO.getUsername());
			List<AlertOrderDTO> orderArrayList = new ArrayList<AlertOrderDTO>(Arrays.asList(orderList));
			formDTO.setOrderList(orderArrayList);
		}catch (Exception e){
			e.printStackTrace();
		}
		return formDTO;
	}

	public LtsAlertMessageService getLtsAlertMessageService() {
		return this.ltsAlertMessageService;
	}

	public void setLtsAlertMessageService(
			LtsAlertMessageService pLtsAlertMessageService) {
		this.ltsAlertMessageService = pLtsAlertMessageService;
	}
	
	
/*	@Override
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) throws ServletException {
		OrderCaptureDTO orderCaptureDTO = (OrderCaptureDTO)request.getSession().getAttribute(LtsConstant.SESSION_LTS_ORDER_CAPTURE);
		


	}*/
	
}
