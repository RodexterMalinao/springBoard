package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.OrderSignatureDAO;
import com.bomwebportal.lts.dao.ReportTemplateDAO;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.SignatureDTO;
import com.bomwebportal.lts.dto.SignatureDTO.SignatureType;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.SignatureLtsDTO;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;

public class LtsSignatureServiceImpl implements LtsSignatureService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ReportTemplateDAO reportTemplateDao;
	private OrderSignatureDAO orderSignatureDao;

	public ReportTemplateDAO getReportTemplateDao() {
		return reportTemplateDao;
	}

	public void setReportTemplateDao(ReportTemplateDAO reportTemplateDao) {
		this.reportTemplateDao = reportTemplateDao;
	}

	public OrderSignatureDAO getOrderSignatureDao() {
		return orderSignatureDao;
	}

	public void setOrderSignatureDao(OrderSignatureDAO orderSignatureDao) {
		this.orderSignatureDao = orderSignatureDao;
	}

	public SignatureDTO getSignature(String orderId, SignatureType signType) {
		SignatureDTO orderSignature = getOrderSignature(orderId, signType.name());
		if (orderSignature != null) {
			return orderSignature;
		}
		return createSignature(signType);
	}
	
	public SignatureDTO createSignature(SignatureType signType) {

		if (signType == null) {
			return null;
		}
		
		SignatureDTO signature = new SignatureDTO();
		signature.setSignType(signType);
		
		try {
			
			List<LookupItemDTO> signatureTitleList = reportTemplateDao.getReportTemplate(LtsConstant.REPORT_TEMPLATE_SIGNATURE_TITLE, signType.name());
			
			if (signatureTitleList == null || signatureTitleList.isEmpty()) {
				return signature;
			}
			
			for (LookupItemDTO signatureTemplate : signatureTitleList) {
				if (StringUtils.equals(LtsConstant.LOCALE_ENG, signatureTemplate.getItemKey())) {
					signature.setTitleEng((String)signatureTemplate.getItemValue());
				}
				
				if (StringUtils.equals(LtsConstant.LOCALE_CHI, signatureTemplate.getItemKey())) {
					signature.setTitleChi((String)signatureTemplate.getItemValue());
				}
			}
			
			List<LookupItemDTO> signatureNoteList = reportTemplateDao.getReportTemplate(LtsConstant.REPORT_TEMPLATE_SIGNATURE_NOTE, signType.name());
			
			if (signatureNoteList == null || signatureNoteList.isEmpty()) {
				return signature;
			}
			
			for (LookupItemDTO signatureTemplate : signatureNoteList) {
				if (StringUtils.equals(LtsConstant.LOCALE_ENG, signatureTemplate.getItemKey())) {
					signature.setNoteEng((String)signatureTemplate.getItemValue());
				}
				
				if (StringUtils.equals(LtsConstant.LOCALE_CHI, signatureTemplate.getItemKey())) {
					signature.setNoteChi((String)signatureTemplate.getItemValue());
				}
			}
		}
		catch (DAOException de) {
			logger.error("Fail in LtsSignatureServiceImpl.createSignature", de);
			throw new RuntimeException(de.getCustomMessage());
		}

		return signature;
	}
	
	public void insertOrderSignature(String orderId, List<SignatureDTO> signatureList, String userId) {
		for (SignatureDTO signature : signatureList) {
			insertOrderSignature(orderId, signature, userId);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED) 
	public void insertOrderSignature(String orderId, SignatureDTO signature, String userId) {
		try {
			orderSignatureDao.insertOrderSignature(orderId, signature, userId);
		}
		catch (DAOException de) {
			logger.error("Fail in LtsSignatureServiceImpl.insertOrderSignature", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteOrderSignature(String orderId, String signType) {
		try {
			orderSignatureDao.deleteOrderSignature(orderId, signType);
		}
		catch (DAOException de) {
			logger.error("Fail in LtsSignatureServiceImpl.deleteOrderSignature", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public SignatureDTO getOrderSignature(String orderId, String signType) {
		try {
			return orderSignatureDao.getOrderSignature(orderId, signType);
		}
		catch (DAOException de) {
			logger.error("Fail in LtsSignatureServiceImpl.getOrderSignature", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public SignatureLtsDTO[] getOrderSignatures(String orderId) {
		try {
			return orderSignatureDao.getOrderSignature(orderId);
		}
		catch (DAOException de) {
			logger.error("Fail in LtsSignatureServiceImpl.getOrderSignature", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public boolean isAllAcqSignatureSigned(AcqOrderCaptureDTO orderCapture) {
		List<SignatureDTO> signatureList = getAcqSignatureList(orderCapture);
		
		if (signatureList == null || signatureList.isEmpty()) {
			return true;
		}
		
		for (SignatureDTO signature : signatureList) {
			if (ArrayUtils.isEmpty(signature.getSignatureBytes())) {
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isAllSignatureSigned(OrderCaptureDTO orderCapture) {
		List<SignatureDTO> signatureList = getSignatureList(orderCapture);
		
		if (signatureList == null || signatureList.isEmpty()) {
			return true;
		}
		
		for (SignatureDTO signature : signatureList) {
			if (ArrayUtils.isEmpty(signature.getSignatureBytes())) {
				return false;
			}
		}
		
		return true;
	}
	
	public List<SignatureDTO> getSignatureList(OrderCaptureDTO orderCapture) {
		
		List<SignatureDTO> signatureList = new ArrayList<SignatureDTO>();

		SbOrderDTO sbOrder = orderCapture.getSbOrder();
		ServiceDetailDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder); 
		boolean isCoreSrvEye = LtsSbOrderHelper.getLtsEyeService(sbOrder) != null;
		
		if(isCoreSrvEye){
			if (orderCapture.getLtsCustomerIdentificationForm().isThirdPartyApplication()) {
				signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.EYEX_THIRD_PARTY_SIGN));
			}
			else {
				signatureList.add(getSignature(sbOrder.getOrderId(),SignatureType.EYEX_CUST_SIGN));
			}
		}else{
			if (orderCapture.getLtsCustomerIdentificationForm().isThirdPartyApplication()) {
				signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.REL_DEL_THIRD_PARTY_SIGN));
			}
			else {
				signatureList.add(getSignature(sbOrder.getOrderId(),SignatureType.REL_DEL_CUST_SIGN));
			}
		}
		
		if (orderCapture.getLtsOtherVoiceServiceForm().getCreate2ndDel()) {
			if (orderCapture.getLtsCustomerIdentificationForm().isSecDelThirdPartyApplication()) {
				signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.SEC_DEL_THIRD_PARTY_SIGN));
			}
			else {
				signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.SEC_DEL_CUST_SIGN));
			}	
		}
		
		if (!StringUtils.equals(orderCapture.getLtsPaymentForm()
				.getExistingPayMethodType(), orderCapture.getLtsPaymentForm().getNewPayMethodType())) {
			if (StringUtils.equals(orderCapture.getLtsPaymentForm().getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
				signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.CCARD_SIGN));
			}
			
			if (StringUtils.equals(orderCapture.getLtsPaymentForm().getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
				signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.BANK_SIGN));
			}
		}
		
		if (StringUtils.equals("Y", ltsServiceDetail.getRecontractInd()) && ltsServiceDetail.getRecontract() != null) {
			signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.RECONTRACT_TO_SIGN));
			if(LtsConstant.RECONTRACT_MODE_BOTH.equals(ltsServiceDetail.getRecontract().getTransMode())) {
				signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.RECONTRACT_FROM_SIGN));			
			}
		}
		
		return signatureList;
		
	}
	
	public List<SignatureDTO> getAcqSignatureList(AcqOrderCaptureDTO acqOrderCapture) {		
		List<SignatureDTO> signatureList = new ArrayList<SignatureDTO>();
		SbOrderDTO sbOrder = acqOrderCapture.getSbOrder();
		boolean isThirdParty = acqOrderCapture.getLtsAcqPersonalInfoForm().isThirdParty();

		if (LtsSbOrderHelper.getLtsEyeService(sbOrder)!=null) {
			signatureList.add(getSignature(sbOrder.getOrderId(), 
					isThirdParty? SignatureType.EYEX_THIRD_PARTY_SIGN : SignatureType.EYEX_CUST_SIGN));
		}
		
		if (LtsSbOrderHelper.getDelServices(sbOrder)!=null) {
			signatureList.add(getSignature(sbOrder.getOrderId(), 
					isThirdParty? SignatureType.REL_DEL_THIRD_PARTY_SIGN : SignatureType.REL_DEL_CUST_SIGN));
		}
		
		if (LtsSbOrderHelper.get2ndDelService(sbOrder)!=null) {
			signatureList.add(getSignature(sbOrder.getOrderId(), 
					isThirdParty? SignatureType.SEC_DEL_THIRD_PARTY_SIGN : SignatureType.SEC_DEL_CUST_SIGN));
		}
		
		for(PaymentMethodDtl payMtdDtl: acqOrderCapture.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList()){
			if (!StringUtils.equals(payMtdDtl.getExistingPayMethodType(), payMtdDtl.getNewPayMethodType())) {
				if (StringUtils.equals(payMtdDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_CREDIT_CARD)) {
					signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.CCARD_SIGN));
				}
				
				if (StringUtils.equals(payMtdDtl.getNewPayMethodType(), LtsConstant.PAYMENT_TYPE_AUTO_PAY)) {
					signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.BANK_SIGN));
				}
			}
		}
		
		if(LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls())!=null){
			if (StringUtils.equals(LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls()).getPipb().getFromDiffCustInd(), "Y")) {
				signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.RECONTRACT_TO_SIGN));
				signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.RECONTRACT_FROM_SIGN));			
			}
		}
		
		if(LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls())!=null){
			signatureList.add(getSignature(sbOrder.getOrderId(), SignatureType.PIPB_NSD_SIGN));
		}
		
		return signatureList;
		
	}
	
}
