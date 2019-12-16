package com.bomwebportal.lts.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.UpgradePcdSrvDAO;
import com.bomwebportal.lts.dto.UpgradePcdSrvDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class UpgradePcdSrvServiceImpl implements UpgradePcdSrvService{

	private final Log logger = LogFactory.getLog(getClass());
	
	private UpgradePcdSrvDAO upgradePcdSrvDAO;
	
	
	
	public UpgradePcdSrvDAO getUpgradePcdSrvDAO() {
		return this.upgradePcdSrvDAO;
	}

	public void setUpgradePcdSrvDAO(UpgradePcdSrvDAO pUpgradePcdSrvDAO) {
		this.upgradePcdSrvDAO = pUpgradePcdSrvDAO;
	}



	public List<UpgradePcdSrvDTO> getUpgradePcdSrv(String pAddrCoverage,
			String pImsExitSrv, String pImsNewSrv) {
		try {
			return this.upgradePcdSrvDAO.getUpgradePcdSrv(getAddressCoverageKey(pAddrCoverage), pImsExitSrv, pImsNewSrv);
		} catch (DAOException de) {
			logger.error("Fail in upgradePcdSrvDAO.getUpgradePcdSrv()", de);
			throw new AppRuntimeException(de);
		}// TODO Auto-generated method stub
		//return null;
	}

	public String getModemArrangment(String pAddrCoverage, String pImsExitSrv, String pImsNewService) {
		List<UpgradePcdSrvDTO> upgradePcdSrvList = getUpgradePcdSrv(pAddrCoverage, pImsExitSrv, pImsNewService);
		if (upgradePcdSrvList == null || upgradePcdSrvList.isEmpty()) {
			return null;
		}
		
		for (UpgradePcdSrvDTO upgradePcdSrv : upgradePcdSrvList) {
			if (StringUtils.equals(upgradePcdSrv.getImsNewSrv(), pImsNewService)) {
				return  upgradePcdSrv.getModemArrangement();
			}
		}
		return null;
	}
	
	public String getAddressCoverageKey (String pAddrCoverage) {
		if (StringUtils.isNotBlank(pAddrCoverage)
				&& (int)Double.parseDouble(pAddrCoverage) < 6) {
			return null;
		}
		
		if (StringUtils.equals("6", pAddrCoverage)) {
			return LtsBackendConstant.ADDRESS_COVERAGE_6M;
		}
		else if (StringUtils.equals("8", pAddrCoverage)) {
			return LtsBackendConstant.ADDRESS_COVERAGE_8M;
		}
		else {
			return LtsBackendConstant.ADDRESS_COVERAGE_OVER_8M;
		}
	}
	
}
