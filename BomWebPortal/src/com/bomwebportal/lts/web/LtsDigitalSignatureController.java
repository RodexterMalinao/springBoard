package com.bomwebportal.lts.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.SignatureDTO;
import com.bomwebportal.lts.dto.SignatureDTO.SignatureType;
import com.bomwebportal.lts.dto.form.LtsDigitalSignatureFormDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsDigitalSignatureController extends SimpleFormController {


	private final String commandName = "ltsDigitalSignatureCmd";
	private final String viewName = "ltsdigitalsignature";
	private final String nextView = "ltsdigitalsignature.html";
	private final String closePopupPage = "closepopup.jsp";
	
	private LtsSignatureService ltsSignatureService;
	
	public LtsSignatureService getLtsSignatureService() {
		return ltsSignatureService;
	}

	public void setLtsSignatureService(LtsSignatureService ltsSignatureService) {
		this.ltsSignatureService = ltsSignatureService;
	}

	public LtsDigitalSignatureController() {
		setCommandClass(LtsDigitalSignatureFormDTO.class);
		setCommandName(commandName);
		setFormView(viewName);
	}
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		if (orderCapture == null) {
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}
		return super.handleRequestInternal(request, response);
	}
	
	@Override
	public Object formBackingObject(HttpServletRequest request)	throws ServletException {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		
		LtsDigitalSignatureFormDTO form = orderCapture.getLtsDigitalSignatureForm();
		
		if (form == null) {
			form = new LtsDigitalSignatureFormDTO();
			initialize(orderCapture, form);
		}
		return form;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		OrderCaptureDTO orderCapture = LtsSessionHelper.getOrderCapture(request);
		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		LtsDigitalSignatureFormDTO form = (LtsDigitalSignatureFormDTO) command;
		
		List<SignatureDTO> signatureList = form.getSignatureList();
		SignatureDTO currentSignature = signatureList.get(form.getSignatureIndex());
		saveSignature(currentSignature, sbOrder, bomSalesUser);
		replicateSignature(form, sbOrder, bomSalesUser);
		orderCapture.setLtsDigitalSignatureForm(form);
		
		if (hasNextSignature(form)) {
			return new ModelAndView(new RedirectView(nextView));
		}
		form.setSignatureIndex(0);
		return new ModelAndView(new RedirectView(closePopupPage));
	}
	
	private boolean hasNextSignature(LtsDigitalSignatureFormDTO form) {
		for (int i = form.getSignatureIndex() + 1; i < form.getSignatureList().size(); i++) {
			SignatureDTO nextSignature = form.getSignatureList().get(i);
			if (!nextSignature.isReplicated()) {
				form.setSignatureIndex(i);
				return true;
			}
		}
		return false;
	}
	
	private List<SignatureDTO> getSignatureList(OrderCaptureDTO orderCapture) {
		
		List<SignatureDTO> signatureList = new ArrayList<SignatureDTO>();

		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		ServiceDetailDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder); 
		boolean isCoreSrvEye = LtsSbOrderHelper.getLtsEyeService(sbOrder) != null;
		
		if(isCoreSrvEye){
			if (orderCapture.getLtsCustomerIdentificationForm().isThirdPartyApplication()) {
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.EYEX_THIRD_PARTY_SIGN));
			}
			else {
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(),SignatureType.EYEX_CUST_SIGN));
			}
		}else{
			if (orderCapture.getLtsCustomerIdentificationForm().isThirdPartyApplication()) {
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.REL_DEL_THIRD_PARTY_SIGN));
			}
			else {
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(),SignatureType.REL_DEL_CUST_SIGN));
			}
		}
		
		if (orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel()) {
			if (orderCapture.getLtsCustomerIdentificationForm().isSecDelThirdPartyApplication()) {
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.SEC_DEL_THIRD_PARTY_SIGN));
			}
			else {
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.SEC_DEL_CUST_SIGN));
			}	
		}
		
		if (!StringUtils.equals(orderCapture.getLtsPaymentForm()
				.getExistingPayMethodType(), orderCapture.getLtsPaymentForm().getNewPayMethodType())) {
			if (StringUtils.equals(orderCapture.getLtsPaymentForm().getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.CCARD_SIGN));
			}
			
			if (StringUtils.equals(orderCapture.getLtsPaymentForm().getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.BANK_SIGN));
			}
		}
		
		//TODO: 
		if (StringUtils.equals("Y", ltsServiceDetail.getRecontractInd()) && ltsServiceDetail.getRecontract() != null 
			&& StringUtils.isEmpty(orderCapture.getLtsRecontractForm().getUpdateDnProfile())) {
			signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.RECONTRACT_TO_SIGN));
			if(LtsConstant.RECONTRACT_MODE_BOTH.equals(ltsServiceDetail.getRecontract().getTransMode())) {
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.RECONTRACT_FROM_SIGN));			
			}
		}
		
		return signatureList;
		
	}
	
	private void replicateSignature(LtsDigitalSignatureFormDTO form, SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
		List<SignatureDTO> signatureList = form.getSignatureList();
		
		if (signatureList == null || signatureList.isEmpty()) {
			return;
		}
		
		SignatureDTO currentSignature = signatureList.get(form.getSignatureIndex());
		
		if (SignatureType.EYEX_CUST_SIGN ==  currentSignature.getSignType() 
				|| SignatureType.EYEX_THIRD_PARTY_SIGN == currentSignature.getSignType()
				|| SignatureType.REL_DEL_CUST_SIGN ==  currentSignature.getSignType()
				|| SignatureType.REL_DEL_THIRD_PARTY_SIGN ==  currentSignature.getSignType()) {
			
			for (SignatureDTO replicateSignature : form.getSignatureList()) {
				
				switch (replicateSignature.getSignType() ) {
				case BANK_SIGN:
				case CCARD_SIGN:
					replicateSignature.setReplicatedSignType(form.isSameAsBankSign() ? currentSignature.getSignType() : null);
					if (form.isSameAsBankSign()) {
						replicateSignature.setSignatureString(new String(currentSignature.getSignatureString()));
						saveSignature(replicateSignature, sbOrder, bomSalesUser);	
					}
					break;
				case SEC_DEL_CUST_SIGN:
				case SEC_DEL_THIRD_PARTY_SIGN:
					replicateSignature.setReplicatedSignType(form.isSameAsSecDelCustSign() ? currentSignature.getSignType() : null);
					if (form.isSameAsSecDelCustSign()) {
						replicateSignature.setSignatureString(new String(currentSignature.getSignatureString()));
						saveSignature(replicateSignature, sbOrder, bomSalesUser);	
					}
				case RECONTRACT_TO_SIGN:
					replicateSignature.setReplicatedSignType(form.isSameAsRecontractToSign() ? currentSignature.getSignType() : null);
					if (form.isSameAsRecontractToSign()) {
						replicateSignature.setSignatureString(new String(currentSignature.getSignatureString()));
						saveSignature(replicateSignature, sbOrder, bomSalesUser);	
					}
				default:
					break;
				}
			}
		}
	}
	
	private void saveSignature(SignatureDTO signature, SbOrderDTO sbOrder,BomSalesUserDTO bomSalesUser) {
		ltsSignatureService.deleteOrderSignature(sbOrder.getOrderId(), signature.getSignType().name());
		ltsSignatureService.insertOrderSignature(sbOrder.getOrderId(), signature, bomSalesUser.getUsername());
	}
	
	private void initialize(OrderCaptureDTO orderCapture, LtsDigitalSignatureFormDTO form) {
		
		List<SignatureDTO> requiredSignatureList = getSignatureList(orderCapture);
		form.setSignatureList(requiredSignatureList);
		
		if (requiredSignatureList == null || requiredSignatureList.isEmpty()) {
			return;
		}

		for (SignatureDTO signature : requiredSignatureList) {
			if (SignatureType.BANK_SIGN == signature.getSignType()
					|| SignatureType.CCARD_SIGN == signature.getSignType()) {
				form.setBankSignRequired(true);
				if (signature.isReplicated()) {
					form.setSameAsBankSign(true);
				}
			}
			if (SignatureType.SEC_DEL_CUST_SIGN == signature.getSignType()
					|| SignatureType.SEC_DEL_THIRD_PARTY_SIGN == signature.getSignType()) {
				form.setSecDelCustSignRequired(true);
				if (signature.isReplicated()) {
					form.setSameAsSecDelCustSign(true);
				}
			}
			if (SignatureType.RECONTRACT_TO_SIGN == signature.getSignType()) {
				form.setRecontractToSignRequired(true);
				if (signature.isReplicated()) {
					form.setSameAsRecontractToSign(true);
				}
			}
		}
	}
	
}
