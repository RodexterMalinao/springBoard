package com.bomwebportal.mob.ccs.service;

import java.util.List;

public interface MobCcsBasketAssoSalesAssignService {
	List<String> getExistSalesAssignChannelCdALL();
	List<String> getExistSalesAssignCentreCdALL(String channelCd);
	List<String> getExistSalesAssignTeamCdALL(String channelCd, String centreCd);
}
