package com.bomwebportal.lts.service.acq;

import java.util.List;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.acq.LtsAcqDnPoolDAO;

public class LtsAcqDnPoolServiceImpl implements LtsAcqDnPoolService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	LtsAcqDnPoolDAO ltsAcqDnPoolDAO;	
	
	/**
	 * @return the ltsAcqDnPoolDAO
	 */
	public LtsAcqDnPoolDAO getLtsAcqDnPoolDAO() {
		return ltsAcqDnPoolDAO;
	}

	/**
	 * @param ltsAcqDnPoolDAO the ltsAcqDnPoolDAO to set
	 */
	public void setLtsAcqDnPoolDAO(LtsAcqDnPoolDAO ltsAcqDnPoolDAO) {
		this.ltsAcqDnPoolDAO = ltsAcqDnPoolDAO;
	}

	@Transactional
	public List<String> searchDnListFromPoolByNoCriteria(String sessionId) {
		try {
			List<String> dnList = ltsAcqDnPoolDAO.searchDnListFromPoolByNoCriteria();
			
			if (dnList != null && !dnList.isEmpty()) {
				ltsAcqDnPoolDAO.updDnStatusToLock(dnList, sessionId);
			}
			return dnList;
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public List<String> searchDnListFromPoolByFirst8Digits(String sessionId, String searchingKey) {
		try {
			List<String> dnList = ltsAcqDnPoolDAO.searchDnListFromPoolByFirst8Digits(searchingKey);
			
			if (dnList != null && !dnList.isEmpty()) {
				ltsAcqDnPoolDAO.updDnStatusToLock(dnList, sessionId);
			}
			return dnList;
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public List<String> searchDnListFromPoolByLast3Digits(String sessionId, String searchingKey) {
		try {
			List<String> dnList = ltsAcqDnPoolDAO.searchDnListFromPoolByLast3Digits(searchingKey);
			
			if (dnList != null && !dnList.isEmpty()) {
				ltsAcqDnPoolDAO.updDnStatusToLock(dnList, sessionId);
			}
			return dnList;
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public List<String> getLockedDnList(String sessionId) {
		try {
			List<String> rDnList = ltsAcqDnPoolDAO.getLockedDnList(sessionId);			
			return rDnList;
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public List<String> getReservedDnList(String sessionId) {
		return getReservedDnList(sessionId, null);
	}
	
	@Transactional
	public List<String> getReservedDnList(String sessionId, List<String> dnList) {
		try {
			List<String> rDnList = ltsAcqDnPoolDAO.getReservedDnList(sessionId, dnList);			
			return rDnList;
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	@Transactional
	public void updDnStatus(List<String> dnList, String nStatus, String oStatus, String sessionId) {
		try {
			ltsAcqDnPoolDAO.updDnStatus(dnList, nStatus, oStatus, sessionId);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public void updDnStatusToLock(List<String> dnList, String sessionId) {
		try {
			ltsAcqDnPoolDAO.updDnStatusToLock(dnList, sessionId);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public void updDnStatusToReserve(List<String> dnList, String oStatus, String sessionId) {
		try {
			ltsAcqDnPoolDAO.updDnStatusToReserve(dnList, oStatus, sessionId);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public void updDnStatusToConfirm(String srvNum, String sessionId) {
		try {
			ltsAcqDnPoolDAO.updDnStatusToConfirm(srvNum, sessionId);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public void updDnStatusToNormal(String oStatus, String sessionId) {
		try {
			ltsAcqDnPoolDAO.updDnStatusToNormal(oStatus, sessionId);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public void updDnStatusToNormalBySrvNum(List<String> dnList, String oStatus, String sessionId) {
		try {
			ltsAcqDnPoolDAO.updDnStatusToNormalBySrvNum(dnList, oStatus, sessionId);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public void releaseDnStatusToLockedByReservedDn(List<String> dnList, String sessionId) {
		try {
			ltsAcqDnPoolDAO.releaseDnStatusToLockedByReservedDn(dnList, sessionId);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public void releaseDnStatusToNormalByConfirmedDn(String srvNum) {
		try {
			ltsAcqDnPoolDAO.releaseDnStatusToNormalByConfirmedDn(srvNum);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public void assignDn(String dn) {
		try {
			ltsAcqDnPoolDAO.assignDn(dn);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
	public boolean isDnAssigned(String dn) {
		try {
			return ltsAcqDnPoolDAO.isDnAssigned(dn);
		}
		catch (DAOException de) {
			logger.error(ExceptionUtils.getFullStackTrace(de));
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
}
