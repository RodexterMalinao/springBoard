package com.bomwebportal.lts.service.bom;

public interface EyeProfileCountService {
	
	int getEyeProfileCountByCust(String pDocType, String pDocNum);
	int getEyeProfileCountByAddr(String pFlat, String pFloor, String srvBdry, boolean isCheckPattern);

}
