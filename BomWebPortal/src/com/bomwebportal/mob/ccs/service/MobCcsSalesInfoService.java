package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;

public interface MobCcsSalesInfoService {
	List<SalesInfoDTO> getSalesInfoDTO(String channelCd, String category);
	List<SalesInfoDTO> getSalesInfoDTOByID(String staffId, String appDate); // add by Joyce 20120827
	public String getBomSalesRoleDTOByID(String staffId);
}
