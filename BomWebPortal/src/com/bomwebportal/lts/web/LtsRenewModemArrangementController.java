package com.bomwebportal.lts.web;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsModemArrangementFormDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;
import com.bomwebportal.lts.service.LtsOfferService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsRenewModemArrangementController extends SimpleFormController {

	private final String viewName = "ltsrenewmodemarrangement";
	private final String nextView = "ltscustomeridentification.html";
	private final String commandName = "ltsModemArrangementCmd";

	private LtsOfferService ltsOfferService;
	private CodeLkupCacheService ltsRentalRouterImsVasCodeLkupCacheService;
	
	public LtsRenewModemArrangementController() {
		setCommandClass(LtsModemArrangementFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null || orderCapture.getLtsModemArrangementForm() == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		
		if (!LtsConstant.ORDER_TYPE_SB_RETENTION.equals(orderCapture.getOrderType())) {
			orderCapture.setLtsCustomerIdentificationForm(null);
			return new ModelAndView(new RedirectView(nextView));
		}else if(!ltsOfferService.isRenewalWithNewDevice(orderCapture)){
			orderCapture.getLtsModemArrangementForm().setLostModem(false);
			orderCapture.setLtsCustomerIdentificationForm(null);
			return new ModelAndView(new RedirectView(nextView));
		}
		
		return super.handleRequestInternal(request, response);
	}
	
	public Object formBackingObject(HttpServletRequest request) throws ServletException {
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		LtsModemArrangementFormDTO form = orderCapture.getLtsModemArrangementForm();
		form.setRentalRouterExistFsaVas(checkImsVasContainsEyeRentalRouter(orderCapture));
		return form;
	}
	
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		Map<String, Object> referenceData = new HashMap<String, Object>();
		referenceData.put("isImsVasEyeRentalRouter", checkImsVasContainsEyeRentalRouter(orderCapture));

		return referenceData;
	}
	
	private boolean checkImsVasContainsEyeRentalRouter(OrderCaptureDTO orderCapture){
		OfferDetailProfileLtsDTO[] offers;
		try {
			offers = orderCapture.getLtsServiceProfile().getEyeFsaProfile().getOffers();
		}catch(Exception e){
			offers = null;
		}
		
		try {
			LookupItemDTO[] lkups = ltsRentalRouterImsVasCodeLkupCacheService.getCodeLkupDAO().getCodeLkup();
			if(lkups != null && offers != null){
				for(LookupItemDTO lkupItem : lkups){
					for(OfferDetailProfileLtsDTO offer : offers){
						if(StringUtils.equals(offer.getOfferId(), (String)lkupItem.getItemKey())){
							return true;
						}
					}
				}
			}
		} catch (DAOException e) {
			return false;
		}
		return false;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {

		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		LtsModemArrangementFormDTO form = (LtsModemArrangementFormDTO) command;
		
		validate(orderCapture, form, errors);
		
		if (errors.hasFieldErrors()) {
			ModelAndView mav = new ModelAndView(viewName);
			mav.addAllObjects(errors.getModel());
			mav.addObject(commandName, form);
			return mav;
		}
		
		//TODO lost modem function currently disabled
		form.setLostModem(false);
		orderCapture.setLtsModemArrangementForm(form);
		orderCapture.setLtsCustomerIdentificationForm(null);
		return new ModelAndView(new RedirectView(nextView));
	}
	
	private void validate(OrderCaptureDTO orderCapture, LtsModemArrangementFormDTO form, BindException errors){
		FsaDetailDTO selectedExistingFsa = getSelectedFsaDetail(form.getExistingFsaList());
		if(selectedExistingFsa != null
				&& (Arrays.asList(LtsConstant.ROUTER_RENTAL_ROUTER_GROUP_CODES).contains(selectedExistingFsa.getRouterGrp())
						|| selectedExistingFsa.isMeshRouterGrp())){
			if(StringUtils.isBlank(form.getRentalRouterInd())){
				errors.rejectValue("rentalRouterInd", "lts.modemArrangement.rentalRouter.error");
			}
		}
	}

	private FsaDetailDTO getSelectedFsaDetail(List<FsaDetailDTO> fsaDetailList) {
		if (fsaDetailList == null || fsaDetailList.isEmpty()) {
			return null;
		}
		
		for (FsaDetailDTO fsaDetail : fsaDetailList) {
			if (fsaDetail.isSelected()) {
				return fsaDetail;
			}
		}
		return null;
	}

	public LtsOfferService getLtsOfferService() {
		return ltsOfferService;
	}

	public void setLtsOfferService(LtsOfferService ltsOfferService) {
		this.ltsOfferService = ltsOfferService;
	}

	public CodeLkupCacheService getLtsRentalRouterImsVasCodeLkupCacheService() {
		return ltsRentalRouterImsVasCodeLkupCacheService;
	}

	public void setLtsRentalRouterImsVasCodeLkupCacheService(
			CodeLkupCacheService ltsRentalRouterImsVasCodeLkupCacheService) {
		this.ltsRentalRouterImsVasCodeLkupCacheService = ltsRentalRouterImsVasCodeLkupCacheService;
	}
}
