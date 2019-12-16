package com.bomwebportal.mob.ds.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ds.dto.MobDsMrtManagementDTO;

public interface MobDsMrtManagementService {
	public List<MobDsMrtManagementDTO> getMrtSummaryList(MobDsMrtManagementDTO searchDto, String staffId, String category, String channelCd);
	public String getSalesTeamCd(String staffId, Date appDate);
	public String getSalesChannelCd(String staffId, Date appDate);
	public String getSalesCentreCd (String staffId, Date appDate);
	public String getStoreChannelCd(String storeCd);
	public boolean isValidMrtStore(List<String> msisdn, String teamCode);
	public int updateMrtInventory(MobDsMrtManagementDTO dto, String updateStaffId);
	public List<MaintParmLkupDTO> getMsisdnlvlList();
	public boolean allowUpdateMrtStatus(List<String> msisdn);
	public List<String> getMdvTeamList();
	public int deleteDsReuseMrtInventory(String msisdn);
}
