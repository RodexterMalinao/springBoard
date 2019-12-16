package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsIncidentCauseDAO;
import com.bomwebportal.mob.ccs.dto.MobCcsIncidentCauseDTO;

@Transactional(readOnly=true)
public class MobCcsIncidentCauseServiceImpl implements
		MobCcsIncidentCauseService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	private MobCcsIncidentCauseDAO mobCcsIncidentCauseDAO;
	
	public MobCcsIncidentCauseDAO getMobCcsIncidentCauseDAO() {
		return mobCcsIncidentCauseDAO;
	}

	public void setMobCcsIncidentCauseDAO(
			MobCcsIncidentCauseDAO mobCcsIncidentCauseDAO) {
		this.mobCcsIncidentCauseDAO = mobCcsIncidentCauseDAO;
	}

	public List<MobCcsIncidentCauseDTO> getMobCcsIncidentCause(String incidentNo) {
		try {
			logger.info("getMobCcsIncidentCause() is called @ MobCcsIncidentCauseServiceImpl");
			return this.mobCcsIncidentCauseDAO.getMobCcsIncidentCauseDTO(incidentNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsOrderRescueDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertMobCcsIncidentCause(MobCcsIncidentCauseDTO dto) {
		try {
			logger.info("insertMobCcsIncidentCause() is called @ MobCcsIncidentCauseServiceImpl");
			this.mobCcsIncidentCauseDAO.insertMobCcsIncidentCauseDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertMobCcsIncidentCause()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void deleteMobCcsIncidentCause(String incidentNo) {
		try {
			logger.info("deleteMobCcsIncidentCause() is called @ MobCcsIncidentCauseServiceImpl");
			this.mobCcsIncidentCauseDAO.deleteMobCcsIncidentCauseDTO(incidentNo);
		} catch (DAOException de) {
			logger.error("Exception caught in deleteMobCcsIncidentCause()", de);
			throw new AppRuntimeException(de);
		}
	}

}
