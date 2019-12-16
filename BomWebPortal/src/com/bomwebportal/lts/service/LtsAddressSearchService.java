package com.bomwebportal.lts.service;

import java.util.List;
import com.bomwebportal.ims.dto.ImsAddressDTO;


public interface LtsAddressSearchService {
	
	public List<ImsAddressDTO> getAddressBySB(String[] sbList, boolean includeLtsOnly);

}
