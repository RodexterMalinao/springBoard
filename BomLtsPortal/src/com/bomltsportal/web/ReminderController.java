package com.bomltsportal.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.OnlineBasketDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.ReminderFormDTO;
import com.bomltsportal.util.SessionHelper;


public class ReminderController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		ReminderFormDTO dto = new ReminderFormDTO();
		for(OnlineBasketDTO basket: orderCapture.getBasketSelectForm().getOnlineBasketList()){
			if(basket.isSelected()){
				dto.setPremItemSetList(basket.getPremiumImageSetList());
				break;
			}
		}
		return dto;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException {
		
		return new ModelAndView(new RedirectView(getSuccessView()));
	}
}
	
	