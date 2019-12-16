package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsSupportDocUI;

public interface MobCcsSupportDocService {

	public List<MobCcsSupportDocDTO> getMobCcsSupportDocDTOInitialList(
			String locale);

	public List<MobCcsSupportDocDTO> getMobCcsSupportDocDTOAdditionalList(
			List<MobCcsSupportDocDTO> initialList, String orderId, String locale);

	public int insertMobCcsSupportDocUI(MobCcsSupportDocUI dto);

	public int updateMobCcsSupportDocUI(MobCcsSupportDocUI dto);
	
	public MobCcsSupportDocUI getMobCcsSupportDocUI(String orderId, String locale);
	
	public List<MobCcsSupportDocDTO> getMobCcsSupportDocDTOForCourierGuid(String orderId);
	
	public boolean isSupportDocRequired(String docType, String basketID, String[] vasItemList, String channel);
	
/*	public List<MobCcsSupportDocDTO> compareMobCcsSupportDoc(List<MobCcsSupportDocDTO> historyList,
			List<MobCcsSupportDocDTO> verifiedList);*/
}
