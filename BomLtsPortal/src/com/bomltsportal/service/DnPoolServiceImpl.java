package com.bomltsportal.service;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomltsportal.dao.DnPoolDAO;
import com.bomltsportal.util.BomLtsConstant;
import com.bomwebportal.exception.DAOException;

public class DnPoolServiceImpl implements DnPoolService {

	
	protected final Log logger = LogFactory.getLog(getClass());
	
	DnPoolDAO dnPoolDAO;
	
	public DnPoolDAO getDnPoolDAO() {
		return dnPoolDAO;
	}

	public void setDnPoolDAO(DnPoolDAO dnPoolDAO) {
		this.dnPoolDAO = dnPoolDAO;
	}

	public List<String> getDnListFromPool(String sessionId) {
		return getDnListFromPool(BomLtsConstant.IDEN_SRV_NUM_DISPLAY_AMOUNT, sessionId);
	}
	
	@Transactional
	public List<String> getDnListFromPool(int qty, String sessionId) {
		try {
			List<String> dnList = dnPoolDAO.getDnListFromPool();
			
			if (dnList != null && !dnList.isEmpty()) {
//				updateDnStatus(dnList, BomLtsConstant.DN_POOL_STATUS_PRE_ASSIGN);
				dnPoolDAO.lockDnList(dnList, BomLtsConstant.DN_POOL_STATUS_LOCKED, sessionId);
			}
			return dnList;
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	@Transactional
	public void updateDnStatus(List<String> dnList, String status) {
		try {
			dnPoolDAO.updateDnStatus(dnList, status);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public void assignDn(String dn) {
		try {
			dnPoolDAO.assignDn(dn);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public void releaseDnStatus(String sessionId) {
		try {
			dnPoolDAO.releaseDnStatus(sessionId);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}	

	@Transactional
	public void lockDnList(List<String> dnList, String status, String sessionId) {
		try {
			dnPoolDAO.lockDnList(dnList, status, sessionId);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
}
