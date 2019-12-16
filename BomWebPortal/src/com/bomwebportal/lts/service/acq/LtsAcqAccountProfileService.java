package com.bomwebportal.lts.service.acq;

import java.util.List;

import com.bomwebportal.lts.dto.profile.AccountDetailProfileAcqDTO;


public interface LtsAcqAccountProfileService {

	public List<AccountDetailProfileAcqDTO> getAcctListByCustNum(String pCustNum); 
	
}
