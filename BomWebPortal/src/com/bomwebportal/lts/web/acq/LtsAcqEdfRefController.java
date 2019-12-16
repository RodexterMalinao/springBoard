package com.bomwebportal.lts.web.acq;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsEdfRefFormDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.order.OrderModifyService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAcqEdfRefController extends SimpleFormController {
	
	
	private final String commandName = "ltsEdfRefCmd";
	private final String viewName = "/lts/acq/ltsacqedfref";
	private final String nextView = "ltsacqedfref.html?submit=true";

	protected OrderModifyService orderModifyService;
	
	public OrderModifyService getOrderModifyService() {
		return orderModifyService;
	}

	public void setOrderModifyService(OrderModifyService orderModifyService) {
		this.orderModifyService = orderModifyService;
	}

	public LtsAcqEdfRefController() {
		setCommandClass(LtsEdfRefFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		if (acqOrderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	
	@Override
	public Object formBackingObject(HttpServletRequest request)	throws ServletException {
		
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		LtsEdfRefFormDTO form = new LtsEdfRefFormDTO();
		if (sbOrder == null) {
			return form;
		}
		ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		
		if (imsService != null) {
			form.setEdfRef(((ServiceDetailOtherLtsDTO)imsService).getEdfRef());
		}
		
		return form;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request); 
		LtsEdfRefFormDTO form = (LtsEdfRefFormDTO)command;
		
		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		
		if (imsService != null) {
			orderModifyService.updateEdfRef(sbOrder.getOrderId(), imsService.getDtlId(), form.getEdfRef(), bomSalesUser.getUsername());
		}
		((ServiceDetailOtherLtsDTO)imsService).setEdfRef(form.getEdfRef());
		return new ModelAndView(new RedirectView(nextView)); 
	}
}
