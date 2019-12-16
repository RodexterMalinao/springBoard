package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsCsiCaseLogDAO;
import com.bomwebportal.mob.ccs.dto.CsiCaseLogDTO;

@Transactional(readOnly=true)
public class MobCcsCsiCaseLogServiceImpl implements MobCcsCsiCaseLogService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobCcsCsiCaseLogDAO mobCcsCsiCaseLogDAO;
	
	public MobCcsCsiCaseLogDAO getMobCcsCsiCaseLogDAO() {
		return mobCcsCsiCaseLogDAO;
	}

	public void setMobCcsCsiCaseLogDAO(MobCcsCsiCaseLogDAO mobCcsCsiCaseLogDAO) {
		this.mobCcsCsiCaseLogDAO = mobCcsCsiCaseLogDAO;
	}

	public List<CsiCaseLogDTO> getCsiCaseLogALLByOrderId(String orderId) {
		try {
			logger.info("getCsiCaseLogALLByOrderId() is called in MobCcsCsiCaseLogServiceImpl");
			return this.mobCcsCsiCaseLogDAO.getCsiCaseLogALLByOrderId(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getCsiCaseLogALLByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	public List<CsiCaseLogDTO> getCsiCaseLogALLByCaseNo(String caseNo) {
		try {
			logger.info("getCsiCaseLogALLByCaseNo() is called in MobCcsCsiCaseLogServiceImpl");
			return this.mobCcsCsiCaseLogDAO.getCsiCaseLogALLByCaseNo(caseNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getCsiCaseLogALLByCaseNo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertCsiCaseLogDTO(CsiCaseLogDTO dto) {
		try {
			logger.info("insertCsiCaseLogDTO() is called in MobCcsCsiCaseLogServiceImpl");
			return this.mobCcsCsiCaseLogDAO.insertCsiCaseLogDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertCsiCaseLogDTO()", de);
			throw new AppRuntimeException(de);
		}
	}



}
