package com.bomltsportal.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.OnlineBasketDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.BasketDetailFormDTO;
import com.bomltsportal.dto.form.BasketSelectFormDTO;
import com.bomltsportal.service.BasketDetailService;
import com.bomltsportal.service.OnlineSalesLogService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;


public class BasketSelectController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());

	private final String nextView = "vasdetail.html";
	
	BasketDetailService basketDetailService;
	OnlineSalesLogService onlineSalesLogService;


	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		if(orderCapture == null || orderCapture.isOrderSignoffed()){
			return SessionHelper.getSessionTimeOutView();
		}		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		BasketSelectFormDTO form = new BasketSelectFormDTO();
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		String locale = RequestContextUtils.getLocale(request).toString();
		String housingType = orderCapture.getAddressRollout().getHousingType();
		String srvBoundary = "";	
		if(orderCapture != null && orderCapture.getAddressRollout() != null){
			srvBoundary = orderCapture.getAddressRollout().getSrvBdary();
		}

		orderCapture.setTopNavInd(BomLtsConstant.TOP_NAV_IND_SELECT_SERVICE);

		//if(orderCapture.getBasketSelectForm() == null){
			
			boolean parallelExtension = StringUtils.equals(
					BomLtsConstant.SERVICE_TYPE_DEL,
					orderCapture.getServiceTypeInd()) ? false : orderCapture
					.getAddressRollout().isPeCoverage();
			
			logger.debug("OSTEST"+parallelExtension+" "+BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC+" "+locale+" "+ housingType+" "+ srvBoundary);
			
			List<OnlineBasketDTO> onlineBasketList = this.basketDetailService
					.getOnlineBasketHousingList(orderCapture,
							parallelExtension,
							BomLtsConstant.DISPLAY_TYPE_ONLINE_DESC, locale, housingType, srvBoundary);
			
			Calendar calendar = Calendar.getInstance(); 
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)); 
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");  
			form.setOfferValidDate(sdf.format(calendar.getTime()));
			
			form.setOnlineBasketList(onlineBasketList);
			orderCapture.setBasketSelectForm(form);
		/*}
		
		else{
			form = orderCapture.getBasketSelectForm();
		}
		*/
		
		return form;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException {
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		BasketSelectFormDTO form = (BasketSelectFormDTO) command;
		boolean isDel = StringUtils.equals(BomLtsConstant.SERVICE_TYPE_DEL, orderCapture.getServiceTypeInd());
		String originalBasketId = null;
		//check if selected basket is changed
		for(OnlineBasketDTO onlineBasket : orderCapture.getBasketSelectForm().getOnlineBasketList()){
			if(onlineBasket.isSelected()){
				originalBasketId = onlineBasket.getBasketDetail().getBasketId();
				break;
			}
		}

		if(originalBasketId != null && 
				!StringUtils.equals(form.getSelectedBasketId(), originalBasketId))
		{
			if(isDel){
				orderCapture.setBasketDetailForm(null);
			}
			orderCapture.setVasDetailForm(null);
		}

		setSelectedBasket(form);
		orderCapture.setBasketSelectForm(form);
		if(isDel){
			if(orderCapture.getBasketDetailForm() == null){
				BasketDetailFormDTO basketDetailForm = new BasketDetailFormDTO();
				basketDetailService.setBasketDetailForm(orderCapture, basketDetailForm, orderCapture.getLang(), form.getSelectedBasketId());
				orderCapture.setBasketDetailForm(basketDetailForm);
			}
		}
		
		onlineSalesLogService.logOnlineDetailTrack(
				SessionHelper.getRequestId(request), 
				SessionHelper.getCurrentPage(request), 
				BomLtsConstant.LOG_TRACK_ITEM_CD_BASKET_ID, 
				form.getSelectedBasketId(), 
				SessionHelper.getRequestSeq(request));
		
		return new ModelAndView(new RedirectView(nextView));
	}
	
	
	private void setSelectedBasket(BasketSelectFormDTO form) {
		List<OnlineBasketDTO> onlineBasketList = form.getOnlineBasketList();
		
		for (OnlineBasketDTO onlineBasket : onlineBasketList) {
			onlineBasket.setSelected(StringUtils.equals(form
					.getSelectedBasketId(), onlineBasket.getBasketDetail()
					.getBasketId()));
		}
		
	}
	

	public BasketDetailService getBasketDetailService() {
		return basketDetailService;
	}

	public void setBasketDetailService(BasketDetailService basketDetailService) {
		this.basketDetailService = basketDetailService;
	}

	public OnlineSalesLogService getOnlineSalesLogService() {
		return onlineSalesLogService;
	}

	public void setOnlineSalesLogService(OnlineSalesLogService onlineSalesLogService) {
		this.onlineSalesLogService = onlineSalesLogService;
	}
}
	
	