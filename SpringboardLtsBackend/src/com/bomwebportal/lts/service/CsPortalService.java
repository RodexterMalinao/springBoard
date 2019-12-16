package com.bomwebportal.lts.service;

import com.bomwebportal.lts.dto.CsPortalIdInqArqDTO;
import com.bomwebportal.lts.dto.CsPortalIdRegrArqDTO;
import com.bomwebportal.lts.dto.CsPortalResponseDTO;
import com.bomwebportal.lts.dto.CsPortalTxnDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface CsPortalService {
	
	public enum CsPortalRes{
		SUCCESS, ALDY_REG, DOCNUM_6_DIGIT, EMAIL_ERROR, SYSTEM_ERROR
	}
	
	public void createCsPortalTxn(CsPortalTxnDTO csPortalTxn, String userId);
	public CsPortalRes checkCsPortalCustExist(String docType, String docNum, String loginId, String userId);
	public CsPortalRes regCsPortal(SbOrderDTO sbOrder, String userId, boolean isPremier, boolean isPro);
	
	public CsPortalResponseDTO checkCsIdForTheClubAndHkt(String docType, String docNum, String hktLoginId, String clubLoginId, String userId);
	public CsPortalResponseDTO checkCsIdForTheClubAndHkt(CsPortalIdInqArqDTO arq, String userId);
		
	public CsPortalResponseDTO regCsIdForTheClubAndHkt(SbOrderDTO sbOrder, String staffId, String srcSys, boolean isPremier, String targetAcct);
	public CsPortalResponseDTO regCsIdForTheClubAndHkt(CsPortalIdRegrArqDTO arq, String sbOrderId, String userId);
	
	public boolean isCsldReplyFail(String pReplyCd);
}
