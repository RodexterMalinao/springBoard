package com.bomwebportal.ims.service;

import java.util.List;
import com.bomwebportal.ims.dto.ImsAddressDTO;


public interface ImsAddressSearchService {
	
	public List<ImsAddressDTO> getAddressBySB(String[] sbList);

}
