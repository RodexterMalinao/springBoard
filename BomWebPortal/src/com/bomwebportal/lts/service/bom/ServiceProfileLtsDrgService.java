package com.bomwebportal.lts.service.bom;

import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.ServiceProfileInventoryDTO;

public interface ServiceProfileLtsDrgService extends ServiceProfileLtsService {

	public abstract ServiceDetailProfileLtsDTO getServiceProfile(
			String pSrvNum, String pSrvType, String pUser);

	public abstract ServiceProfileInventoryDTO getServiceInventory(
			String pSrvNum, String pSrvType);

}