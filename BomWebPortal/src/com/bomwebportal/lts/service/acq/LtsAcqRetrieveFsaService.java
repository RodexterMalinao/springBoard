package com.bomwebportal.lts.service.acq;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;

public interface LtsAcqRetrieveFsaService {

	ValidationResultDTO retrieveFsaServiceByCustomer(String custNum, AcqOrderCaptureDTO orderCapture, String pUser);
	ValidationResultDTO retrieveFsaServiceByDocument(String docType, String docNum, AcqOrderCaptureDTO orderCapture, String pUser);
	ValidationResultDTO retrieveFsaServiceByLogin(String loginId, String domain, AcqOrderCaptureDTO orderCapture, String pUser);
	boolean isOtherFsaExist(String flat, String floor, String serviceBoundary);
	ValidationResultDTO retrieveFsaServiceByFsa(String fsa, AcqOrderCaptureDTO orderCapture, String pUser);
}
