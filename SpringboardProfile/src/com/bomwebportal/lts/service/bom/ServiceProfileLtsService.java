package com.bomwebportal.lts.service.bom;

import com.bomwebportal.lts.dto.profile.AccountServiceLtsDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceGroupProfileDTO;

public interface ServiceProfileLtsService {

	public PendingOrdStatusDetailDTO getPendingOrder(String pSrvNum, String pSrvType);
	
	public ServiceDetailProfileLtsDTO getSimpleServiceProfile(String pCcSrvId, String pSystemId);

	public AccountServiceLtsDTO[] getServiceAccoutAssgn(String pCcSrvId, String pSystemId);
	
	public ServiceDetailProfileLtsDTO getTerminatedServiceProfile(String pCcSrvId, String pSystemId);
	
	public ServiceGroupProfileDTO getEyeGrp(String pCcSrvId);
	
	public ServiceDetailProfileLtsDTO[] getServiceProfileByCustomer(String pCustNum, String pSrvType);
	
	public ServiceDetailProfileLtsDTO getServiceProfileBySrvNum(String pSrvNum, String pSrvType);
}
