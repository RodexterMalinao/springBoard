package com.bomwebportal.lts.service.bom;

import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;

public interface ImsServiceProfileAccessService {

	public FsaServiceDetailOutputDTO[] getServiceByCustomer(String pCustNum, String pUser);
	
	public FsaServiceDetailOutputDTO getServiceDetailByFSA(String pFsa, String pUser);
	
	public FsaServiceDetailOutputDTO[] getServiceByDocument(String pDocType, String pDocNum, String pUser);
	
	public FsaServiceDetailOutputDTO[] getServiceByLogin(String pLogin, String pDomainType, String pUser);
	
	public void getFsaOfferProfile(FsaServiceDetailOutputDTO pFsa, String pExistEyeType);
	
	public String checkProductParmByFsa(String fsa, String parmName);

}
