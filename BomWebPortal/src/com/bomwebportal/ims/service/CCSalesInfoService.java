package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;

public interface CCSalesInfoService {
	public List<String> getCCManagerTeamCds(String staffId) throws DAOException;
	
	public ImsServiceSrdDTO getPTServiceByStaffId (String staffId, String housingType, String PONOTAmt, String PONOTChrgType);
	
	public String getAllowMassprojByStaff (String staffId, String housingType);
}
