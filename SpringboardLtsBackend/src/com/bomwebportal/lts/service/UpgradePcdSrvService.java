package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.lts.dto.UpgradePcdSrvDTO;

public interface UpgradePcdSrvService {

	public List<UpgradePcdSrvDTO> getUpgradePcdSrv(String pAddrCoverage, String pImsExitSrv, String pImsNewSrv);
	public String getModemArrangment(String pAddrCoverage, String pImsExitSrv, String pImsNewService);
	public String getAddressCoverageKey (String pAddrCoverage);
}
