package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsCcsChannelDAO;
import com.bomwebportal.mob.ccs.dto.CcsChannelDTO;

@Transactional(readOnly=true)
public class MobCcsCcsChannelServiceImpl implements MobCcsCcsChannelService {
	private Log logger = LogFactory.getLog(this.getClass());
	private MobCcsCcsChannelDAO mobCcsCcsChannelDAO;

	public MobCcsCcsChannelDAO getMobCcsCcsChannelDAO() {
		return mobCcsCcsChannelDAO;
	}

	public void setMobCcsCcsChannelDAO(MobCcsCcsChannelDAO mobCcsCcsChannelDAO) {
		this.mobCcsCcsChannelDAO = mobCcsCcsChannelDAO;
	}

	public List<String> getChannelCdALL() {
		try {
			logger.info("getChannelCdALL() is called @ MobCcsCcsChannelServiceImpl");
			return this.mobCcsCcsChannelDAO.getChannelCdALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getChannelCdALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<String> getCentreCdALL() {
		try {
			logger.info("getCentreCdALL() is called @ MobCcsCcsChannelServiceImpl");
			return this.mobCcsCcsChannelDAO.getCentreCdALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getCentreCdALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<String> getTeamCdALL() {
		try {
			logger.info("getTeamCdALL() is called @ MobCcsCcsChannelServiceImpl");
			return this.mobCcsCcsChannelDAO.getTeamCdALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getTeamCdALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<CcsChannelDTO> getCcsChannelDTOALL() {
		try {
			logger.info("getCcsChannelDTOALL() is called @ MobCcsCcsChannelServiceImpl");
			return this.mobCcsCcsChannelDAO.getCcsChannelDTOALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getCcsChannelDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

}
