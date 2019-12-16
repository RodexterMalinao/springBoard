package com.bomwebportal.lts.web.disconnect;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateBillingInfoFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsTerminateBillingInfoController extends SimpleFormController {
	
	private final String viewName="lts/disconnect/ltsterminatebillinginfo";
	private final String nextView="ltsterminateappointment.html";
	private final String commandName="ltsterminatebillinginfoCmd";
	
	public LtsTerminateBillingInfoController() {
		setCommandName(commandName);
		setCommandClass(LtsTerminateBillingInfoFormDTO.class);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TerminateOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getTerminateOrderCapture(request);
		if (orderCaptureDTO == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		TerminateOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getTerminateOrderCapture(request);
		LtsTerminateBillingInfoFormDTO form = (LtsTerminateBillingInfoFormDTO) command;
		appendDeceaseCaseHandlingOnBa(orderCaptureDTO, form);
		orderCaptureDTO.setLtsTerminateBillingInfoForm(form);
		return new ModelAndView(new RedirectView(nextView));
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request) throws Exception {
		
		TerminateOrderCaptureDTO orderCaptureDTO = LtsSessionHelper.getTerminateOrderCapture(request);
		
		LtsTerminateBillingInfoFormDTO form = orderCaptureDTO.getLtsTerminateBillingInfoForm();
		
		if(form != null) {
			return form;
		}
		
		form = new LtsTerminateBillingInfoFormDTO();
		
		AccountDetailProfileLtsDTO primaryAcct = LtsSbOrderHelper.getPrimaryAccount(orderCaptureDTO.getLtsServiceProfile());
		String existBillingAddr = "";
		if (primaryAcct != null) {
			existBillingAddr = StringUtils.defaultIfEmpty(primaryAcct.getBillAddr(), "");
			form.setExistingPayMethodType(primaryAcct.getPayMethod());
			form.setBillingAddress(existBillingAddr);
			form.setExistingBillingName(primaryAcct.getAcctName());
			form.setNewBillingAddress(existBillingAddr);
			form.setExistingBillMedia(primaryAcct.getBillMedia());
			form.setExistingBillingEmail(primaryAcct.getEmailAddr());
		}
		
		
		
		LtsTerminateServiceSelectionFormDTO termServiceForm = orderCaptureDTO.getLtsTerminateServiceSelectionForm();
		if(LtsSbOrderHelper.isDeceasedCase(termServiceForm)) {
//			if (LtsConstant.DISC_DECEASE_UNREACHED.equals(termServiceForm.getDeceasedType())) {
//				form.setNewBillingAddress(existBillingAddr + "\n" + "REFER L002");
//			}
//			else {
//				form.setNewBillingAddress(existBillingAddr + "\n" + "REFER L001");	
//			}
			
			if (LtsConstant.DISC_DECEASE_UNREACHED.equals(termServiceForm.getDeceasedType()) 
					|| LtsConstant.DISC_DECEASE_NORMAL.equals(termServiceForm.getDeceasedType())) {
				form.setChangeBillingNameInd(LtsTerminateBillingInfoFormDTO.CHANGE_BILLING_NAME_IND_DISALLOW);
			}
			if (LtsConstant.DISC_DECEASE_SPECIAL.equals(termServiceForm.getDeceasedType()) 
					|| LtsConstant.DISC_DECEASE_INHERIT.equals(termServiceForm.getDeceasedType())) {
				form.setChangeBillingNameInd(LtsTerminateBillingInfoFormDTO.CHANGE_BILLING_NAME_IND_FORCE);
			} 
			
			form.setChangeBa(true);
			form.setInstantUpdateBa(true);			
		}

		return form;
	}
	
	private void appendDeceaseCaseHandlingOnBa(TerminateOrderCaptureDTO orderCaptureDTO, LtsTerminateBillingInfoFormDTO form) {
		if (StringUtils.isNotBlank(form.getNewBillingAddress())) {
			LtsTerminateServiceSelectionFormDTO termServiceForm = orderCaptureDTO.getLtsTerminateServiceSelectionForm();
			if(LtsSbOrderHelper.isDeceasedCase(termServiceForm)) {
				String[] newBillingAddrs = StringUtils.split(form.getNewBillingAddress(), "\n");
				
				if (LtsConstant.DISC_DECEASE_UNREACHED.equals(termServiceForm.getDeceasedType())) {
					if (newBillingAddrs.length >= 6) {
						form.setNewBillingAddress(form.getNewBillingAddress() + " " + "REFER L002");	
					}
					else {
						form.setNewBillingAddress(form.getNewBillingAddress() + "\n" + "REFER L002");
					}
				}
				else {
					if (newBillingAddrs.length >= 6) {
						form.setNewBillingAddress(form.getNewBillingAddress() + " " + "REFER L001");	
					}
					else {
						form.setNewBillingAddress(form.getNewBillingAddress() + "\n" + "REFER L001");
					}
				}
			}
		}
	}
	
}
