package com.bomwebportal.lts.service;

import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.ModemAssignDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;

public interface ModemAssignService {
	
	ModemAssignDTO createModemAssign(AddressRolloutDTO addressRollout,
			FsaServiceDetailOutputDTO selectedFsa, String newService, String existService, 
			String newBandWidth, boolean isExternalRelocate, String selectedLineType);
	
	ModemAssignDTO createRenewalModemAssign(AddressRolloutDTO addressRollout,
			FsaServiceDetailOutputDTO selectedFsa, String newService);
	
	void autoUpgradeVDSL(FsaServiceDetailOutputDTO selectedFsa, 
			AddressRolloutDTO addressRollout, ModemAssignDTO modemAssign, String targetBandwidth);

}
