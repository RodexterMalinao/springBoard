package com.bomwebportal.ims.service;


public interface CsPortalService {
	
	public String[] isCsPortalReg(String docType, String docNum, String hktId, String ClubId);
	public void RegCsPortalReg(String orderId, String docType, String docNum, String loginId, String mobileNum, String lang, String nickname);
	public String RetrieveLoginId(String docType, String docNum);
	public void RegrPro(String orderId, String docType, String docNum, String loginId, String mobileNum, String lang, String nickname, String salesChannel, String shopCd, String staffNum);
	public void RegMyHKTClubReg(String orderId, String docType, String docNum, String hktId, String clubId, String mobileNum, String lang, String nickname, String salesChannel, String shopCd, String staffNum);

		
}
