package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.SignatureDTO;
import com.bomwebportal.lts.dto.SignatureDTO.SignatureType;
import com.bomwebportal.lts.dto.order.SignatureLtsDTO;

public interface LtsSignatureService {

	SignatureDTO getSignature(String orderId, SignatureType signType);
	SignatureDTO createSignature(SignatureType signType);
	SignatureDTO getOrderSignature(String orderId, String signType);
	void insertOrderSignature(String orderId, List<SignatureDTO> signatureList, String userId);
	void insertOrderSignature(String orderId, SignatureDTO signature, String userId);
	void deleteOrderSignature(String orderId, String signType);
	SignatureLtsDTO[] getOrderSignatures(String orderId);
	List<SignatureDTO> getSignatureList(OrderCaptureDTO orderCapture);
	List<SignatureDTO> getAcqSignatureList(AcqOrderCaptureDTO orderCapture);
	boolean isAllSignatureSigned(OrderCaptureDTO orderCapture);
	boolean isAllAcqSignatureSigned(AcqOrderCaptureDTO orderCapture);
}
