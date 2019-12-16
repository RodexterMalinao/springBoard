package com.bomwebportal.ims.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.ims.constant.ImsConstants;
import com.bomwebportal.ims.dto.ImsEdfRefFormDTO;
import com.bomwebportal.ims.dto.ui.OrderImsUI;
import com.bomwebportal.ims.service.ImsOrderDocumentService;

public class ImsEdfRefController extends SimpleFormController {

	private final String commandName = "imsEdfRefCmd";
	private final String viewName = "imsedfref";


	private final String nextView = "imsedfref.html?submit=true";

	private ImsOrderDocumentService imsOrderDocumentService;

	public ImsEdfRefController() {
		setCommandClass(ImsEdfRefFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}


	@Override
	public Object formBackingObject(HttpServletRequest request)	throws ServletException {
		logger.info("formBackingObject");
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		
		ImsEdfRefFormDTO form = new ImsEdfRefFormDTO();
		if (sessionOrder == null) {
			return form;
		}
		
		form.setEdfRef("");
		
		//ServiceDetailDTO imsService = LtsSbOrderHelper.getImsService(sbOrder);
		
		//if (imsService != null) {
			//form.setEdfRef(((ServiceDetailOtherLtsDTO)imsService).getEdfRef());
		//}
		
		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		OrderImsUI sessionOrder = (OrderImsUI)request.getSession().getAttribute(ImsConstants.IMS_ORDER);
		ImsEdfRefFormDTO form = (ImsEdfRefFormDTO)command;
		
		logger.info("ImsEdfRef onSubmit");
		logger.info("Order ID: "+sessionOrder.getOrderId());
		
		String user = ((BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser")).getUsername();
		
		boolean isExist = false;
		
		logger.info("edfref: "+form.getEdfRef());
		
		isExist = imsOrderDocumentService.checkImsEdfRef(sessionOrder.getOrderId(),"1");
		
		if (isExist){
			
			imsOrderDocumentService.updateImsEdfRef(sessionOrder.getOrderId(),"1",form.getEdfRef(), user);
			
		}else{
			
			imsOrderDocumentService.insertImsEdfRef(sessionOrder.getOrderId(), form.getEdfRef(), user);
		}
		
		sessionOrder.setEdfRef(form.getEdfRef());
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	
	public ImsOrderDocumentService getImsOrderDocumentService() {
		return imsOrderDocumentService;
	}

	public void setImsOrderDocumentService(ImsOrderDocumentService imsOrderDocumentService) {
		this.imsOrderDocumentService = imsOrderDocumentService;
	}
}
