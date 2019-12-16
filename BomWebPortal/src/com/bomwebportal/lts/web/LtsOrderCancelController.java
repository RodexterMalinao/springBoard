package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.form.LtsOrderCancelFormDTO;
import com.bomwebportal.lts.dto.order.AmendCategoryLtsDTO;
import com.bomwebportal.lts.dto.order.AmendDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.bom.BomOrderStatusSynchService;
import com.bomwebportal.lts.service.order.AmendmentSubmitService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsOrderCancelController extends SimpleFormController {

	
	private final String viewName = "ltsordercancel";
	private final String orderInfoView = "ltsorderinfo.html";
	private final String commandName = "ltsOrderCancelCmd";
	private final String returnUrl = "sboordersearch.html";
	private final int REMARK_MAX_LENGTH = 4000;
	
	private CodeLkupCacheService onlineAmendNatureCancelCodeLkupCacheService;
	private AmendmentSubmitService amendmentSubmitService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private BomOrderStatusSynchService bomOrderStatusSynchService;
	
	private Locale locale;
	private MessageSource messageSource;
	
	public LtsOrderCancelController() {
		setCommandClass(LtsOrderCancelFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String orderId = (String)request.getParameter("orderId");
		setLocale(RequestContextUtils.getLocale(request));
		if (StringUtils.isEmpty(orderId)) {
			return getOrderInfoView(request, 
					messageSource.getMessage("lts.ltsOrdCancel.invalidId", new Object[] {}, this.locale), returnUrl);
		}
		SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(orderId, false);
		if (sbOrder == null) {
			return getOrderInfoView(request, 
					messageSource.getMessage("lts.ltsOrdCancel.invalidId", new Object[] {}, this.locale), returnUrl);
		}		
		
		for(ServiceDetailDTO srvDtl: sbOrder.getSrvDtls()){
			
			if (srvDtl instanceof ServiceDetailOtherLtsDTO) {
				continue;
			}
			
			OrderStatusSynchDTO[] orderStatuses = bomOrderStatusSynchService
					.getBomOrderStatus(sbOrder.getOrderId(),
							srvDtl.getTypeOfSrv(), srvDtl.getSrvNum(),
							srvDtl.getCcServiceId(),
							srvDtl.getCcServiceMemNum());
			
			if (ArrayUtils.isEmpty(orderStatuses)) {
				return getOrderInfoView(request, messageSource.getMessage("lts.ltsOrdCancel.invalidOrdStatus", new Object[] {}, this.locale), returnUrl);
			}
			
			for (OrderStatusSynchDTO orderStatus : orderStatuses) {
				if (StringUtils.equals("04", orderStatus.getBomStatus())
						|| StringUtils.equals("07", orderStatus.getBomStatus())
						|| StringUtils.equals("C", orderStatus.getBomLegacyStatus())) {
					
					return getOrderInfoView(request, messageSource.getMessage("lts.ltsOrdCancel.alrdyCancel", new Object[] {}, this.locale), returnUrl);	
				}
				
				if (StringUtils.equals("02", orderStatus.getBomStatus())
						|| StringUtils.equals("L", orderStatus.getBomLegacyStatus())) {
					return getOrderInfoView(request, messageSource.getMessage("lts.ltsOrdCancel.alrdyComp", new Object[] {}, this.locale), returnUrl);	
				}
			}
		}
		
		
		return super.handleRequestInternal(request, response);
	}
	
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		String orderId = (String)request.getParameter("orderId");
		LtsOrderCancelFormDTO form = new LtsOrderCancelFormDTO();
		form.setOrderId(orderId);
		return form;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		
		LtsOrderCancelFormDTO form = (LtsOrderCancelFormDTO)command;
		BomSalesUserDTO bomSalesUserDTO = (BomSalesUserDTO)request.getSession().getAttribute("bomsalesuser");
		SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(form.getOrderId(), false);
		if (sbOrder == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		AmendLtsDTO amendmentLts = new AmendLtsDTO();
		amendmentLts.setOrderId(sbOrder.getOrderId());
		amendmentLts.setOcid(sbOrder.getOcid());
		
		amendmentLts.setStaffNum(sbOrder.getStaffNum());
		amendmentLts.setSalesCd(sbOrder.getSalesCd());
		amendmentLts.setSalesChannel(sbOrder.getSalesChannel());
		amendmentLts.setSalesContactNum(sbOrder.getSalesContactNum());
		amendmentLts.setShopCd(sbOrder.getShopCd());
		
		ServiceDetailDTO serviceDetail = LtsSbHelper.getLtsService(sbOrder);

		List<AmendDetailLtsDTO> amendDetailLtsList = new ArrayList<AmendDetailLtsDTO>();
		List<AmendCategoryLtsDTO> categoryList = new ArrayList<AmendCategoryLtsDTO>();
		AmendDetailLtsDTO amendDetailLts = new AmendDetailLtsDTO();
		
		if(!StringUtils.isEmpty(form.getCancelReason())) {
			AmendCategoryLtsDTO cancelOrder = new AmendCategoryLtsDTO();
			cancelOrder.setNature(form.getCancelReason());
			cancelOrder.appendRemarks(getRemarks(splitEqually(form.getCancelRemark(), REMARK_MAX_LENGTH)));
			categoryList.add(cancelOrder);
		}
		amendDetailLts.setSrvNum(serviceDetail.getSrvNum());
		amendDetailLts.setSrvType(serviceDetail.getTypeOfSrv());
		amendDetailLts.setDtlId(serviceDetail.getDtlId());
		amendDetailLts.setCategoryList(categoryList);
		amendDetailLts.setGrpType(serviceDetail.getGrpType());
		amendDetailLtsList.add(amendDetailLts);
		
		amendmentLts.setAmendDtlList(amendDetailLtsList);
		amendmentSubmitService
				.submitAmendment(amendmentLts, bomSalesUserDTO.getUsername(),
						LtsSbHelper.isOnlineAcquistionOrder(sbOrder) ? LtsConstant.OLS_SHOP_CD : bomSalesUserDTO.getShopCd());
		
		return getOrderInfoView(request, messageSource.getMessage("lts.ltsOrdCancel.ordCancelReq", new Object[] {}, this.locale), returnUrl);
	}
	
	private RemarkDetailLtsDTO[] getRemarks(String[] s){
		List<RemarkDetailLtsDTO> remarks = new ArrayList<RemarkDetailLtsDTO>();
		for(int i = 0; i < s.length; i++){
			RemarkDetailLtsDTO remark = new RemarkDetailLtsDTO();
			remark.setRmkSeq(Integer.toString(i));
			remark.setRmkDtl(s[i]);
			remarks.add(remark);
		}
		return remarks.toArray(new RemarkDetailLtsDTO[0]);
	}
	
	private String[] splitEqually(String text, int size) {
	    List<String> ret = new ArrayList<String>((text.length() + size - 1) / size);

	    for (int start = 0; start < text.length(); start += size) {
	        ret.add(text.substring(start, Math.min(text.length(), start + size)));
	    }
	    return ret.toArray(new String[0]);
	}
	
	private ModelAndView getOrderInfoView(HttpServletRequest request, String msg, String returnUrl) {
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, msg);
		request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_RETURN_URL, returnUrl);
		return new ModelAndView(new RedirectView(orderInfoView));
	}
	
	
	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		Map<String, Object> referenceData = new HashMap<String, Object>();
		List<LookupItemDTO> cancelReasonList = Arrays.asList(onlineAmendNatureCancelCodeLkupCacheService.getCodeLkupDAO().getCodeLkup());
		referenceData.put("cancelReasonList", cancelReasonList);
		return referenceData;
	}
	

	public CodeLkupCacheService getOnlineAmendNatureCancelCodeLkupCacheService() {
		return onlineAmendNatureCancelCodeLkupCacheService;
	}

	public void setOnlineAmendNatureCancelCodeLkupCacheService(
			CodeLkupCacheService onlineAmendNatureCancelCodeLkupCacheService) {
		this.onlineAmendNatureCancelCodeLkupCacheService = onlineAmendNatureCancelCodeLkupCacheService;
	}

	public AmendmentSubmitService getAmendmentSubmitService() {
		return amendmentSubmitService;
	}


	public void setAmendmentSubmitService(
			AmendmentSubmitService amendmentSubmitService) {
		this.amendmentSubmitService = amendmentSubmitService;
	}


	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}


	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}


	public BomOrderStatusSynchService getBomOrderStatusSynchService() {
		return bomOrderStatusSynchService;
	}


	public void setBomOrderStatusSynchService(
			BomOrderStatusSynchService bomOrderStatusSynchService) {
		this.bomOrderStatusSynchService = bomOrderStatusSynchService;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
}
