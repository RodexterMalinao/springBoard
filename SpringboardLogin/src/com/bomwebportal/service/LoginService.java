package com.bomwebportal.service;

import java.util.List;
import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.LoginLogDTO;

public interface LoginService {
	
	public boolean validateLogin (BomSalesUserDTO bomSalesUserDTO);
	public List<String> getShopList();
	// add by Joyce 20111026
	public BomSalesUserDTO getSalesAssign(String username);
	// add by Joyce 20111108
	// modified by Joyce 20111215, add pilot status to distinguish active shop(s) for IMS
	public BomSalesUserDTO getCentreCdFromTeamCd(String teamCd);
	// add by wilson , single login
	/**
	 * @param staffId
	 * @param sessionId
	 * @param ipAddress
	 */
	public void updateSessionId(String staffId, String sessionId, String ipAddress);
	/**
	 * @param staffId
	 * @return database save sessionID
	 */
	public String getDbRecordSessionId(String staffId);
	
	LoginLogDTO getLoginLogDTO(String staffId, String sessionId);
}
