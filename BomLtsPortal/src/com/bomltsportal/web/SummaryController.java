package com.bomltsportal.web;

import java.util.List;
import java.util.Locale;

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
import com.bomltsportal.dto.form.SummaryFormDTO;
import com.bomltsportal.service.SummaryService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsSbHelper;


public class SummaryController extends SimpleFormController{
	
	protected final Log logger = LogFactory.getLog(getClass());
	private SummaryService summaryService;
	private final String nextView = "creditpayment.html";
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if(SessionHelper.getOrderCapture(request) == null){
			return SessionHelper.getSessionTimeOutView();
		}
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException{
		

		logger.info("RegOrderSummaryController formBackingObject is called");

		Locale locale = RequestContextUtils.getLocale(request);
		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		SummaryFormDTO summaryFormDTO = new SummaryFormDTO();
		summaryService.setSummary(orderCaptureDTO, orderCaptureDTO.getSbOrder(), summaryFormDTO, locale.toString());
		
		for(OnlineBasketDTO basket : orderCaptureDTO.getBasketSelectForm().getOnlineBasketList()){
			if(orderCaptureDTO.getBasketSelectForm().getSelectedBasketId().equals(basket.getBasketDetail().getBasketId())){
				if(basket.getPremiumImageSetList() != null 
						&& basket.getPremiumImageSetList().size() > 0 ){
					summaryFormDTO.setShowReminder(true);
				}else{
					summaryFormDTO.setShowReminder(false);
				}
				break;
			}
		}
		
//		if(orderCaptureDTO.getBasketDetailForm().getPremiumItemSetList() != null 
//				&& orderCaptureDTO.getBasketDetailForm().getPremiumItemSetList().size() > 0){
//			summaryFormDTO.setShowReminder(true);
//		}else{
//			summaryFormDTO.setShowReminder(false);
//		}
		
		if(orderCaptureDTO.getCreditPaymentForm() != null){
			summaryFormDTO.setPaymentFailed("Y".equals(orderCaptureDTO.getCreditPaymentForm().getFailPayment()) && StringUtils.isNotEmpty(orderCaptureDTO.getCreditPaymentForm().getResponseCode()));
		}
		
		
		List<ItemDetailDTO> prepayItemList = orderCaptureDTO.getApplicantInfoForm().getPrepayItemList();
		if(prepayItemList != null && prepayItemList.size() > 0){
			summaryFormDTO.setPaymentAmount(Integer.parseInt(prepayItemList.get(0).getOneTimeAmt()));
		} else {
			ServiceDetailLtsDTO serviceDetails = LtsSbHelper.getLtsService(orderCaptureDTO.getSbOrder());
			summaryFormDTO.setPaymentAmount(0);
		}
//		summaryFormDTO.setPaymentAmount(summaryService.calculateItemPaymentAmount(serviceDetails.getItemDtls()));
		
		request.setAttribute(BomLtsConstant.TOP_NAV_IND_REGISTER, true);
		
		return summaryFormDTO;
	}
	
	public ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command, BindException errors) 
		throws ServletException {
		OrderCaptureDTO orderCaptureDTO = SessionHelper.getOrderCapture(request);
		SummaryFormDTO summaryFormDTO = (SummaryFormDTO) command;
		orderCaptureDTO.setSummaryForm(summaryFormDTO);
		
		return new ModelAndView(new RedirectView(nextView));
	}

	public SummaryService getSummaryService() {
		return summaryService;
	}

	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}

	
}
	
	