package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsBasketAssoSalesAssignDAO;

@Transactional(readOnly=true)
public class MobCcsBasketAssoSalesAssignServiceImpl implements
		MobCcsBasketAssoSalesAssignService {
	private Log logger = LogFactory.getLog(this.getClass());
	private MobCcsBasketAssoSalesAssignDAO mobCcsBasketAssoSalesAssignDAO;
	
	public MobCcsBasketAssoSalesAssignDAO getMobCcsBasketAssoSalesAssignDAO() {
		return mobCcsBasketAssoSalesAssignDAO;
	}

	public void setMobCcsBasketAssoSalesAssignDAO(
			MobCcsBasketAssoSalesAssignDAO mobCcsBasketAssoSalesAssignDAO) {
		this.mobCcsBasketAssoSalesAssignDAO = mobCcsBasketAssoSalesAssignDAO;
	}

	public List<String> getExistSalesAssignChannelCdALL() {
		try {
			logger.info("getExistSalesAssignChannelCdALL() is called @ MobCcsBasketAssoJobListTeamAssoServiceImpl");
			return this.mobCcsBasketAssoSalesAssignDAO.getExistSalesAssignChannelCdALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getExistSalesAssignChannelCdALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<String> getExistSalesAssignCentreCdALL(String channelCd) {
		try {
			logger.info("getExistSalesAssignCentreCdALL() is called @ MobCcsBasketAssoJobListTeamAssoServiceImpl");
			return this.mobCcsBasketAssoSalesAssignDAO.getExistSalesAssignCentreCdALL(channelCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getExistSalesAssignCentreCdALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<String> getExistSalesAssignTeamCdALL(String channelCd, String centreCd) {
		try {
			logger.info("getExistSalesAssignTeamCdALL() is called @ MobCcsBasketAssoJobListTeamAssoServiceImpl");
			return this.mobCcsBasketAssoSalesAssignDAO.getExistSalesAssignTeamCdALL(channelCd, centreCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getExistSalesAssignTeamCdALL()", de);
			throw new AppRuntimeException(de);
		}
	}

}
