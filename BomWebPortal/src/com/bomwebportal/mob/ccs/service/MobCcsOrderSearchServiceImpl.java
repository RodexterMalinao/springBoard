package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.CodeLkupDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;
import com.bomwebportal.mob.dao.bom.BomOrderDAO;

public class MobCcsOrderSearchServiceImpl implements MobCcsOrderSearchService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	MobCcsOrderDAO mobCcsOrderDao;
	
	BomOrderDAO bomOrderDAO;	
	
	CodeLkupDAO codeLkupDAO;
	
	public CodeLkupDAO getCodeLkupDAO() {
		return codeLkupDAO;
	}


	public void setCodeLkupDAO(CodeLkupDAO codeLkupDAO) {
		this.codeLkupDAO = codeLkupDAO;
	}


	/**
	 * @return the bomOrderDAO
	 */
	public BomOrderDAO getBomOrderDAO() {
		return bomOrderDAO;
	}


	/**
	 * @param bomOrderDAO the bomOrderDAO to set
	 */
	public void setBomOrderDAO(BomOrderDAO bomOrderDAO) {
		this.bomOrderDAO = bomOrderDAO;
	}


	/**
	 * @return the mobCcsOrderDao
	 */
	public MobCcsOrderDAO getMobCcsOrderDao() {
		return mobCcsOrderDao;
	}


	/**
	 * @param mobCcsOrderDao the mobCcsOrderDao to set
	 */
	public void setMobCcsOrderDao(MobCcsOrderDAO mobCcsOrderDao) {
		this.mobCcsOrderDao = mobCcsOrderDao;
	}


	public List<OrderDTO> findOrderEnquiry(String startDate, String endDate, String orderId, 
			String orderStatus, String variousDateType, String channel, String staffId, 
			String orderType, String category, String areaCd, String shopCd, String group, String mrt) {
		try {
			
			String enableAlertMessageForceCancellationRemainingDayInd = "Y";
			
			try {
				enableAlertMessageForceCancellationRemainingDayInd = codeLkupDAO.findCodeLkupByType("ENABLE_ALERT_MSG_FORCE_CANCEL_REMAIN_DAY").get(0).getCodeId();
			} catch (DAOException daoException) {
				// use to ignore any exception thrown from dao
			} catch (Exception exception) {
				// use to ignore any exception thrown from dao
			}
			
			boolean enableAlertMessageForceCancellationRemainingDay = "Y".equalsIgnoreCase(enableAlertMessageForceCancellationRemainingDayInd) ? true : false;
			
			return mobCcsOrderDao.findOrderEnquiry(startDate, endDate, orderId, 
					orderStatus, variousDateType, channel, staffId, orderType, 
					category, areaCd, shopCd, group, mrt, enableAlertMessageForceCancellationRemainingDay);
		} catch (DAOException e) {
			logger.error("Exception caught in findOrderEnquiry()", e);
		}
		
		return null;
	}


	public String getOcidStatus(String ocid) {
		
		try {
			return bomOrderDAO.getBomOrderStaus(ocid);
		} catch (DAOException e) {
			logger.error("Exception caught in getOcidStatus()", e);
		}
		
		return null;
	}

}
