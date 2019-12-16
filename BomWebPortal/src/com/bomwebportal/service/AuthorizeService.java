package com.bomwebportal.service;

import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.AuthorizeDTO;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;

public interface AuthorizeService {
	
	Map<String, List<AuthorizeDTO>> getAuthorizeList(String staffId, String category, String channelId);
	public String getBomSalesRoleDTOByID(String staffId);
	public String getAuthorizeRight(String authorizedAction,String channelId, String sbCategory ,String bomCategory);
	public String getAuthorizeCategoryText(String authorizedAction, String channelId);
	public List<SalesInfoDTO> getSalesInfoDTOByID(String staffId);
	public int updateAdditionalApprovalLog(String authorizedId, String orderId, String orderNature, String rpGrossPlanFee, String basketId, double minVas);
}
