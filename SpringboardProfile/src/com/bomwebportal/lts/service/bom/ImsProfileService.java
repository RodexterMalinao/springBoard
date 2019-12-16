package com.bomwebportal.lts.service.bom;

import java.util.List;

import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;

import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.dto.profile.TenureDTO;
import com.bomwebportal.lts.dto.srvAccess.AddrRolloutDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.UimBlockageDTO;

public interface ImsProfileService {

	public abstract FsaServiceDetailOutputDTO[] getServiceByCustomer(String pCustNum);

	public abstract FsaServiceDetailOutputDTO getServiceDetailByFSA(String pFsa);

	public abstract FsaServiceDetailOutputDTO[] getServiceByDocument(String pDocType, String pDocNum);

	public abstract FsaServiceDetailOutputDTO[] getServiceByLogin(String pLogin, String pDomainType);

	public abstract AddressDetailProfileLtsDTO getAddressDTLByAddressID(String pAddress);

	public abstract TenureDTO[] getImsTenureByAddress(String pFlat, String pFloor, String pSb);

	public abstract AddrRolloutDTO getAddrRolloutDtl(String pIFlat, String pIFloor, String pIServiceBoundary);

	public abstract CustomerDetailProfileLtsDTO getImsCustomerDetailByFsa(String pFsa);

	public abstract PendingOrdStatusDetailDTO getPendingOrder(String pFsa);
	
	boolean isStandaloneTv(String pFsa);
	
	public List<UimBlockageDTO> getUimBlockageList(String flat, String floor, String sb);
	
	public String[] getImsCustDocByParentCust(String pParentCustNum);
	
	public String checkProductParmByFsa(String fsa, String parmName);
}