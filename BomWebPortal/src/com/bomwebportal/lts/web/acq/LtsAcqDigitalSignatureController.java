package com.bomwebportal.lts.web.acq;

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
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.SignatureDTO;
import com.bomwebportal.lts.dto.SignatureDTO.SignatureType;
import com.bomwebportal.lts.dto.form.LtsDigitalSignatureFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.service.LtsSignatureService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;

public class LtsAcqDigitalSignatureController extends SimpleFormController {


	private final String commandName = "ltsDigitalSignatureCmd";
	private final String viewName = "/lts/acq/ltsacqdigitalsignature";
	private final String nextView = "ltsacqdigitalsignature.html";
	private final String closePopupPage = "closepopup.jsp";
	
	private LtsSignatureService ltsSignatureService;
	
	String melolo = "A";  //MB2016081 TC   dummy checking
	
	public LtsSignatureService getLtsSignatureService() {
		return ltsSignatureService;
	}

	public void setLtsSignatureService(LtsSignatureService ltsSignatureService) {
		this.ltsSignatureService = ltsSignatureService;
	}

	public LtsAcqDigitalSignatureController() {
		setCommandClass(LtsDigitalSignatureFormDTO.class);
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
		
		LtsDigitalSignatureFormDTO form = acqOrderCapture.getLtsDigitalSignatureForm();
		
		if (form == null) {
			form = new LtsDigitalSignatureFormDTO();
			initialize(acqOrderCapture, form);			
		}
		return form;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws ServletException, AppRuntimeException {
		
		AcqOrderCaptureDTO acqOrderCapture = LtsSessionHelper.getAcqOrderCapture(request);
		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		BomSalesUserDTO bomSalesUser = (BomSalesUserDTO)request.getSession().getAttribute(LtsConstant.SESSION_BOM_SALES_USER);
		LtsDigitalSignatureFormDTO form = (LtsDigitalSignatureFormDTO) command;
		
		List<SignatureDTO> signatureList = form.getSignatureList();
		SignatureDTO currentSignature = signatureList.get(form.getSignatureIndex());
		saveSignature(currentSignature, sbOrder, bomSalesUser);
		replicateSignature(form, sbOrder, bomSalesUser);
		acqOrderCapture.setLtsDigitalSignatureForm(form);
		
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
	
	private List<SignatureDTO> getSignatureList(AcqOrderCaptureDTO acqOrderCapture) {		
		List<SignatureDTO> signatureList = new ArrayList<SignatureDTO>();
		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		boolean isThirdParty = acqOrderCapture.getLtsAcqPersonalInfoForm().isThirdParty();

		if (LtsSbOrderHelper.getLtsEyeService(sbOrder)!=null) {
			signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), 
					isThirdParty? SignatureType.EYEX_THIRD_PARTY_SIGN : SignatureType.EYEX_CUST_SIGN));
		}
		
		if (LtsSbOrderHelper.getDelServices(sbOrder)!=null) {
			signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), 
					isThirdParty? SignatureType.REL_DEL_THIRD_PARTY_SIGN : SignatureType.REL_DEL_CUST_SIGN));
		}
		
		if (LtsSbOrderHelper.get2ndDelService(sbOrder)!=null) {
			signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), 
					isThirdParty? SignatureType.SEC_DEL_THIRD_PARTY_SIGN : SignatureType.SEC_DEL_CUST_SIGN));
		}
		
		for(PaymentMethodDtl payMtdDtl: acqOrderCapture.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList()){
			if (!StringUtils.equals(payMtdDtl.getExistingPayMethodType(), payMtdDtl.getNewPayMethodType())) {
				if (StringUtils.equals(payMtdDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
					signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.CCARD_SIGN));
				}
				
				if (StringUtils.equals(payMtdDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
					signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.BANK_SIGN));
				}
			}
		}
		
		if(LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls())!=null){
			if (StringUtils.equals(LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls()).getPipb().getFromDiffCustInd(), "Y")) {
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.RECONTRACT_TO_SIGN));
				signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.RECONTRACT_FROM_SIGN));			
			}
		}
		
		if(LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls())!=null){
			signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.PIPB_NSD_SIGN));
		}
		
		//MB2016081 TC++
		if(LtsSbHelper.getIguardCareCashService(sbOrder)!=null){
			if(StringUtils.isNotBlank(LtsSbHelper.getIguardCareCashService(sbOrder).getCarecashInd())){
				if("I".equals(LtsSbHelper.getIguardCareCashService(sbOrder).getCarecashInd())){
					signatureList.add(ltsSignatureService.getSignature(sbOrder.getOrderId(), SignatureType.IGUARD_CARECASH));
				}		
			}
		}
		//MB2016081 TC--
		
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
				case PIPB_NSD_SIGN:
					replicateSignature.setReplicatedSignType(form.isSameAsPipbNsdSign() ? currentSignature.getSignType() : null);
					if (form.isSameAsPipbNsdSign()) {
						replicateSignature.setSignatureString(new String(currentSignature.getSignatureString()));
						saveSignature(replicateSignature, sbOrder, bomSalesUser);	
					}
				//MB2016081 TC++					
				case IGUARD_CARECASH:
					replicateSignature.setReplicatedSignType(form.isSameAsRegIguardCareCash() ? currentSignature.getSignType() : null);
					if (form.isSameAsRegIguardCareCash()) {
						replicateSignature.setSignatureString(new String(currentSignature.getSignatureString()));
						saveSignature(replicateSignature, sbOrder, bomSalesUser);	
					}
				//MB2016081 TC--		
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
	
	private void initialize(AcqOrderCaptureDTO acqOrderCapture, LtsDigitalSignatureFormDTO form) {
		
		List<SignatureDTO> requiredSignatureList = getSignatureList(acqOrderCapture);
		form.setSignatureList(requiredSignatureList);
		form.setAllowReplicate(true);
		
		if (requiredSignatureList == null || requiredSignatureList.isEmpty()) {
			return;
		}
		
		if(acqOrderCapture.isChannelDirectSales()){
			form.setAllowReplicate(false);
		}
		
		for (SignatureDTO signature : requiredSignatureList) {
			if (SignatureType.BANK_SIGN == signature.getSignType()
					|| SignatureType.CCARD_SIGN == signature.getSignType()) {
				form.setBankSignRequired(true);
				
				if(SignatureType.CCARD_SIGN == signature.getSignType()){
					for(PaymentMethodDtl payMtdDtl: acqOrderCapture.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList()){
						if (!StringUtils.equals(payMtdDtl.getExistingPayMethodType(), payMtdDtl.getNewPayMethodType())) {
							if (StringUtils.equals(payMtdDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)
									&& Boolean.valueOf(payMtdDtl.getThirdPartyCard())) {
								form.setThirdPartyCard(true);
								form.setAllowReplicate(false);
							}
						}
					}
				}
				
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
			if (SignatureType.PIPB_NSD_SIGN == signature.getSignType()) {
				form.setPipbNsdSignRequired(true);
				if (signature.isReplicated()) {
					form.setSameAsPipbNsdSign(true);
				}
			}
			//MB2016081 TC++	
			if (SignatureType.IGUARD_CARECASH == signature.getSignType()) {
				form.setRegIguardCareCash(true);
				if (signature.isReplicated()) {
					form.setSameAsRegIguardCareCash(true);
				}
			}
			//MB2016081 TC--
		}
	}
	
}
