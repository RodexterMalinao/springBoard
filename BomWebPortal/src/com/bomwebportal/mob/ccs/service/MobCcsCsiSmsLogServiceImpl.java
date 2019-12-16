package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsCsiSmsLogDAO;
import com.bomwebportal.mob.ccs.dto.CsiSmsLogDTO;

@Transactional(readOnly=true)
public class MobCcsCsiSmsLogServiceImpl implements MobCcsCsiSmsLogService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobCcsCsiSmsLogDAO mobCcsCsiSmsLogDAO;
	public MobCcsCsiSmsLogDAO getMobCcsCsiSmsLogDAO() {
		return mobCcsCsiSmsLogDAO;
	}

	public void setMobCcsCsiSmsLogDAO(MobCcsCsiSmsLogDAO mobCcsCsiSmsLogDAO) {
		this.mobCcsCsiSmsLogDAO = mobCcsCsiSmsLogDAO;
	}

	public List<CsiSmsLogDTO> getCsiSmsLogALLByCaseId(String caseNo) {
		try {
			logger.info("getCsiSmsLogALLByCaseId() is called in MobCcsCsiSmsLogServiceImpl");
			return this.mobCcsCsiSmsLogDAO.getCsiSmsLogALLByCaseId(caseNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getCsiSmsLogALLByCaseId()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertCsiSmsLogDTO(CsiSmsLogDTO dto) {
		try {
			logger.info("insertCsiSmsLogDTO() is called in MobCcsCsiSmsLogServiceImpl");
			return this.mobCcsCsiSmsLogDAO.insertCsiSmsLogDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertCsiSmsLogDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

}
