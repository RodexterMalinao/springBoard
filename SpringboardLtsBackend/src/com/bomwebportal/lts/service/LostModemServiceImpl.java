package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.LostModemDAO;

public class LostModemServiceImpl implements LostModemService {
	
	private LostModemDAO lostModemDAO;
	
	private final Log logger = LogFactory.getLog(getClass());

	@Override
	public List<String> getL2Cd(String pOrderId) {
		try {
			return lostModemDAO.getL2Cd(pOrderId);
		} catch (DAOException e) {
			return new ArrayList<String>();
		}
	}

	@Override
	public void insertL2Cd(String pOrderId, String pDtlId, String pL2Cd) {
		try {
			lostModemDAO.insertL2Cd(pOrderId, pDtlId, pL2Cd, "T", "1", "I");
		} catch (DAOException de) {
			logger.error("Fail in LostModemService.insertL2Cd()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Override
	public void insertL2Cd(String pOrderId, String pDtlId, String pL2Cd, String pFTInd, String pL2Qty, String pActInd) {
		try {
			lostModemDAO.insertL2Cd(pOrderId, pDtlId, pL2Cd, pFTInd, pL2Qty, pActInd);
		} catch (DAOException de) {
			logger.error("Fail in LostModemService.insertL2Cd()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	
	@Override
	/* return {staffId, staffName} */
	public List<String[]> getLostModemApproverName(String pOrderId){
		try {
			return lostModemDAO.getLostModemApproverName(pOrderId);
		} catch (DAOException de) {
			logger.error("Fail in LostModemService.getLostModemApproverName()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public LostModemDAO getLostModemDAO() {
		return lostModemDAO;
	}

	public void setLostModemDAO(LostModemDAO lostModemDAO) {
		this.lostModemDAO = lostModemDAO;
	}


}
