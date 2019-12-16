package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.ims.dto.CustInfoDTO;

public interface CustInfoService {
	
	public List<CustInfoDTO> searchCustInfo(String iddocno);

}
