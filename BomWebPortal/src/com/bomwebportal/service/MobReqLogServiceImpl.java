package com.bomwebportal.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.dao.MobReqLogDAO;
import com.bomwebportal.dto.MobReqLogDTO;


public class MobReqLogServiceImpl implements MobReqLogService{

	protected final Log logger = LogFactory.getLog(getClass());
	private MobReqLogDAO mobReqLogDAO;
	
	public MobReqLogDAO getMobReqLogDAO() {
		return mobReqLogDAO;
	}

	public void setMobReqLogDAO(MobReqLogDAO mobReqLogDAO) {
		this.mobReqLogDAO = mobReqLogDAO;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertMobReqLog(MobReqLogDTO dto) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertMobReqLog() is called @ MobReqLogServiceImpl");
			}
			return this.mobReqLogDAO.insertMobReqLog(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertMobReqLog()", de);
			throw new AppRuntimeException(de);
		}
	}
}
