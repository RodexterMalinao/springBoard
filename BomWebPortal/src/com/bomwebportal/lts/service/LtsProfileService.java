package com.bomwebportal.lts.service;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.UpgradeEyeInfoDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerInquiryFormDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;

public interface LtsProfileService {

	ServiceDetailProfileLtsDTO retrieveServiceProfile(String serviceNum, String pUser);
	
	ServiceDetailProfileLtsDTO retrieveServiceProfile(String serviceNum, String pUser, String pLocale);

	ServiceDetailProfileLtsDTO retrieve2ndDelServiceProfile(String srvNum, String pUser);
	
	ValidationResultDTO retrieveAndValidateServiceProfile(LtsCustomerInquiryFormDTO form, BomSalesUserDTO bomSalesUser, boolean isChannelCs);
	
	UpgradeEyeInfoDTO retrieveUpgradeEyeInfo(ServiceDetailProfileLtsDTO pServiceProfile, String pLocale, OrderCaptureDTO pOrderCapture, boolean isUpgradeToStaffPlan);
	
	String getServiceInventoryStatus(String pSrvNum, String pSrvType);
	
	void setTenure(ServiceDetailProfileLtsDTO serviceProfile, String flat, String floor, String srvBdry);
	
	int getMaxTenure(TenureDTO[] pTenures);
	
	void getOfferDetails(ServiceDetailProfileLtsDTO pSrvDtlProfile, String pApplnDate);
	
	void getFsaOfferProfile(ServiceDetailProfileLtsDTO serviceProfile, FsaServiceDetailOutputDTO pFsa, String pExistEyeType, String locale);
	
}
