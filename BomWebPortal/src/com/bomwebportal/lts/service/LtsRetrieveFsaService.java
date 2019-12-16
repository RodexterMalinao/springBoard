package com.bomwebportal.lts.service;

import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO.FsaServiceType;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;

public interface LtsRetrieveFsaService {
	ValidationResultDTO retrieveFsaServiceByCustomer(String custNum, OrderCaptureDTO orderCapture, String pUser);
	ValidationResultDTO retrieveFsaServiceByTerminateCustomer(String custNum, TerminateOrderCaptureDTO orderCapture, String pUser);
	ValidationResultDTO retrieveFsaServiceByDocument(String docType, String docNum, OrderCaptureDTO orderCapture, String pUser);
	ValidationResultDTO retrieveFsaServiceByLogin(String loginId, String domain, OrderCaptureDTO orderCapture, String pUser);
	boolean isOtherFsaExist(String flat, String floor, String serviceBoundary);
	ValidationResultDTO createTerminateFsaDetailList(FsaServiceDetailOutputDTO[] fsaServiceDetailOutputs, TerminateOrderCaptureDTO orderCapture);
	ValidationResultDTO retrieveFsaServiceByFsa(String fsa, OrderCaptureDTO orderCapture, String pUser);
	ValidationResultDTO createFsaDetailList(FsaServiceDetailOutputDTO[] fsaServiceDetailOutputs, OrderCaptureDTO orderCapture);
	FsaServiceType getExistSrvType(FsaServiceDetailOutputDTO fsaServiceDetailOutput);
	ModemTechnologyAissgnDTO createModemTechnologyAissgn(String existingService, String newService,	
			AddressRolloutDTO addressRollout, FsaDetailDTO selectedFsaDetail,
			boolean channelPremier, boolean b);
	String getFsaRouterGrp(String fsa);
	boolean checkMeshRouterGrp(String fsa);
	boolean checkBrmWifiOffer(String fsa);
}
