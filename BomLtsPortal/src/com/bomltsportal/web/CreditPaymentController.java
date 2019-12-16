package com.bomltsportal.web;

import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.CreditPaymentFormDTO;
import com.bomltsportal.service.CeksService;
import com.bomltsportal.service.ItemDetailService;
import com.bomltsportal.service.OrderSubmitService;
import com.bomltsportal.service.SummaryService;
import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.SessionHelper;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.service.bom.CustomerProfileLtsService;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.service.CodeLkupCacheService;

public class CreditPaymentController extends SimpleFormController {
	protected final Log logger = LogFactory.getLog(getClass());

	private CeksService ceksService;
	private OrderSubmitService orderSubmitService;
	private ItemDetailService itemDetailService;
	private SummaryService summaryService;
	private String ceksLoc;

	private int counter;
	private String counterString;
	private String environment;

	@Override
	public ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		System.out.println("MTD: " + request.getMethod());
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		if(orderCapture == null || orderCapture.isOrderSignoffed()){
			return SessionHelper.getSessionTimeOutView();
		}		
		if (orderCapture.getCreditPaymentForm() != null) {
			List<String> ctr = ceksService.checkCTR(orderCapture.getSbOrder()
					.getOrderId(), "Y", null);

			if ((ctr != null && ctr.size() > 0)
					|| CreditPaymentFormDTO.PaymentStatus.FINISH.equals(orderCapture.getCreditPaymentForm().getPayStatus())) {
				return new ModelAndView(
						new RedirectView("acknowledgement.html"));
			}

		}
		return super.handleRequestInternal(request, response);
	}

	public Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		String locale = RequestContextUtils.getLocale(request).toString();

		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		CreditPaymentFormDTO form = new CreditPaymentFormDTO();
		form.setPayStatus(CreditPaymentFormDTO.PaymentStatus.INITIAL);
		orderCapture.setTopNavInd(BomLtsConstant.TOP_NAV_IND_REGISTER);

		// Rex TODO get ItemDetailDTO
		String prepayInd = "N";
		if(orderCapture.getApplicantInfoForm().getPrepayInd() != null){
			prepayInd = orderCapture.getApplicantInfoForm().getPrepayInd() ? "Y" : "N";
			request.setAttribute("prepayInd", orderCapture.getApplicantInfoForm().getPrepayInd());
		} else {
			prepayInd = "N";
			request.setAttribute("prepayInd", false);
		};
		
		String queryString = request.getQueryString();
		if(StringUtils.isBlank(queryString)){
			counter = orderCapture.getPaymentCounter() + 1;
			orderCapture.setPaymentCounter(counter);

			counterString = String.valueOf(counter);
			while (counterString.length() < 5) {
				counterString = '0' + counterString;
			}

			logger.info("start init Ceks: ceksLoc=" + ceksLoc);

			String custNameNotMatchInd = "";
			String resourceShortageInd = orderCapture.getAddressRollout()
					.getPcdResourceShortage();
			String onpInd = "N";
			// TODO
			String wipInd = "N";

			if (orderCapture.getApplicantInfoForm().getNewNum() != null) {
				onpInd = orderCapture.getApplicantInfoForm().getNewNum()
						.booleanValue() ? "N" : "Y";
			}

			if (orderCapture.getApplicantInfoForm().isCustExist()) {
				if (orderCapture.getApplicantInfoForm().isCustNameMatch()) {
					custNameNotMatchInd = "N";
				} else {
					custNameNotMatchInd = "Y";
				}
			}

			if (StringUtils.isBlank(resourceShortageInd)) {
				resourceShortageInd = "N";
			}

			int amt = summaryService.calculateItemPaymentAmount(
					LtsSbHelper.getLtsService(orderCapture.getSbOrder()).getItemDtls());
			
			if(orderCapture.getSummaryForm().isNoPay()){
				request.setAttribute("nopay", true);
			}
			
			List<ItemDetailDTO> prepayItemList = orderCapture.getApplicantInfoForm().getPrepayItemList();
			if(prepayItemList != null && prepayItemList.size() > 0){
				amt = Integer.parseInt(prepayItemList.get(0).getOneTimeAmt());
			} 
			
			
			
			String ceksUrl = ceksService.initCeks(orderCapture.getServiceTypeInd(),
					locale, custNameNotMatchInd, resourceShortageInd, wipInd,
					onpInd, prepayInd, Integer.toString(amt), orderCapture.getSbOrder()
							.getOrderId() + counterString, orderCapture
							.getSbOrder().getOrderId(), environment);

			
			logger.info("ceks url:" + ceksUrl);

			request.setAttribute("ceksurl", ceksUrl);
			request.setAttribute("CeksLoc", ceksLoc);
			request.getSession().setAttribute("paymentCounter", counter);
		}else{
			logger.info("queryString=" + queryString);
		}

		form.setPayStatus(CreditPaymentFormDTO.PaymentStatus.READY);
		return form;
	}


	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException {

		
		OrderCaptureDTO orderCapture = SessionHelper.getOrderCapture(request);
		CreditPaymentFormDTO payment = (CreditPaymentFormDTO) command;

		logger.info("CEKS return result: "+ payment.getQueryString());
		
		String ccType = "";
		String status = "";

		// payment.setCcExpDate(payment.getCcExpDate().substring(0, 2) + '/' +
		// payment.getCcExpDate().substring(2));
		if (payment.getCcNum() != null && !("".equals(payment.getCcNum()))) {
			Character c = payment.getCcNum().charAt(0);
			ccType = c.toString();
			if ("4".equals(ccType)) {
				payment.setCcType("V");
				payment.setBomCcType("01");
			} else if ("3".equals(ccType)) {
				payment.setCcType("A");
				payment.setBomCcType("04");
			} else {
				payment.setCcType("M");
				payment.setBomCcType("02");
			}
		}

		if ("PC".equals(payment.getPaymentInd())) {
			status = "Y";
			if (payment.getResponseCode() != null
					&& !"".equals(payment.getResponseCode())) {
				if ("000000".equals(payment.getResponseCode())) {
					status = "Y";
					payment.setFailPayment("N");
				} else {
					status = "E";
					payment.setFailPayment("Y");
				}
			} else {
				status = "C";
				payment.setFailPayment("Y");
			}
		} else { // CC
			payment.setFailPayment("N");
			if (payment.getCcNum() != null && !("".equals(payment.getCcNum()))) {
				status = "Y";
			} else {
				status = "N";
			}
		}

		orderCapture.setCreditPaymentForm(payment);

		try {
			if ("PC".equals(payment.getPaymentInd())) {
				ceksService.updateCTR(orderCapture.getSbOrder().getOrderId(),
						payment.getReferenceNum(), payment.getResponseCode(),
						status, payment.getCcNum(), payment.getQueryString(),
						payment.getCcExpDate());
			}

			if (!"Y".equals(payment.getFailPayment())) {
				orderSubmitService.submitOrderPayment(orderCapture);
			}

			if (!"Y".equals(status)) {
				return new ModelAndView(new RedirectView("summary.html"));
			}

			payment.setPayStatus(CreditPaymentFormDTO.PaymentStatus.FINISH);
			return new ModelAndView(new RedirectView("ordersignoff.html"));

		} catch (DAOException e) {
			logger.error("", e);
			throw new ServletException(e);
		}

	}

	
	public CeksService getCeksService() {
		return ceksService;
	}

	public void setCeksService(CeksService ceksService) {
		this.ceksService = ceksService;
	}

	public OrderSubmitService getOrderSubmitService() {
		return orderSubmitService;
	}

	public void setOrderSubmitService(OrderSubmitService orderSubmitService) {
		this.orderSubmitService = orderSubmitService;
	}

	public ItemDetailService getItemDetailService() {
		return itemDetailService;
	}

	public void setItemDetailService(ItemDetailService itemDetailService) {
		this.itemDetailService = itemDetailService;
	}

	public SummaryService getSummaryService() {
		return summaryService;
	}

	public void setSummaryService(SummaryService summaryService) {
		this.summaryService = summaryService;
	}

	public void setCeksLoc(String ceksLoc) {
		this.ceksLoc = ceksLoc;
	}

	public String getCeksLoc() {
		return ceksLoc;
	}

	public String getEnvironment() {
		return environment;
	}

	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	

}
