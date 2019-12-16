package com.bomwebportal.lts.service.bom;

import com.bomwebportal.lts.dto.AddressDetailDTO;

public interface AddressDetailLtsService {

	public abstract boolean isBlacklistAddress(String pSrvBdy, String pFlat, String pFloor);
	
	public abstract AddressDetailDTO getAddressDetail(String sb);
	
	public abstract String getAddressBuildXy(String pSrvBdy);
	
}