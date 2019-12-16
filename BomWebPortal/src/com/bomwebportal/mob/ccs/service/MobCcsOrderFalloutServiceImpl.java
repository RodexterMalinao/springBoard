package com.bomwebportal.mob.ccs.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderFalloutDAO;
import com.bomwebportal.mob.ccs.dto.MobCcsOrderFalloutDTO;

@Transactional(readOnly=true)
public class MobCcsOrderFalloutServiceImpl implements MobCcsOrderFalloutService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	private MobCcsOrderFalloutDAO mobCcsOrderFalloutDAO;
	
	public MobCcsOrderFalloutDAO getMobCcsOrderFalloutDAO() {
		return mobCcsOrderFalloutDAO;
	}

	public void setMobCcsOrderFalloutDAO(MobCcsOrderFalloutDAO mobCcsOrderFalloutDAO) {
		this.mobCcsOrderFalloutDAO = mobCcsOrderFalloutDAO;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrderFalloutDTO(MobCcsOrderFalloutDTO dto) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertOrderFalloutDTO() is called @ MobCcsOrderFalloutServiceImpl");
			}
			return this.mobCcsOrderFalloutDAO.insertOrderFalloutDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertOrderFalloutDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public MobCcsOrderFalloutDTO getOrderFalloutByCat(String orderId, String falloutCat) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getOrderFalloutByCat() is called @ MobCcsOrderFalloutServiceImpl");
			}
			return this.mobCcsOrderFalloutDAO.getOrderFalloutByCat(orderId, falloutCat);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderFalloutByCat()", de);
			throw new AppRuntimeException(de);
		}
	}

}
