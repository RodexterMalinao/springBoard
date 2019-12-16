package com.bomltsportal.service;

import com.bomltsportal.dto.FsaServiceAssgnDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;

public interface ImsDetailService {
	
	FsaServiceDetailOutputDTO getFsaProfileToShare(
			String idDocType, String idDocNum, OrderCaptureDTO orderCapture);

	FsaServiceAssgnDTO getFsaServiceAssgn(
			AddressRolloutDTO addressRollout,
			FsaServiceDetailOutputDTO profileFsaService);
}
