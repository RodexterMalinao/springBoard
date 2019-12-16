package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsCsiCaseDAO;
import com.bomwebportal.mob.ccs.dto.CsiCaseDTO;

@Transactional(readOnly=true)
public class MobCcsCsiCaseServiceImpl implements MobCcsCsiCaseService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobCcsCsiCaseDAO mobCcsCsiCaseDAO;
	
	public MobCcsCsiCaseDAO getMobCcsCsiCaseDAO() {
		return mobCcsCsiCaseDAO;
	}

	public void setMobCcsCsiCaseDAO(MobCcsCsiCaseDAO mobCcsCsiCaseDAO) {
		this.mobCcsCsiCaseDAO = mobCcsCsiCaseDAO;
	}

	public List<CsiCaseDTO> getCsiCaseALLByOrderId(String orderId) {
		try {
			logger.info("getCsiCaseALLByOrderId() is called in MobCcsCsiCaseServiceImpl");
			return this.mobCcsCsiCaseDAO.getCsiCaseALLByOrderId(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getCsiCaseALLByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public CsiCaseDTO getCsiCaseALLByCaseNo(String caseNo) {
		try {
			logger.info("getCsiCaseALLByCaseNo() is called in MobCcsCsiCaseServiceImpl");
			return this.mobCcsCsiCaseDAO.getCsiCaseALLByCaseNo(caseNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getCsiCaseALLByCaseNo()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertCsiCaseDTO(CsiCaseDTO dto) {
		try {
			logger.info("insertCsiCaseDTO() is called in MobCcsCsiCaseServiceImpl");
			return this.mobCcsCsiCaseDAO.insertCsiCaseDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertCsiCaseDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateCsiCaseDTO(CsiCaseDTO dto) {
		try {
			logger.info("updateCsiCaseDTO() is called in MobCcsCsiCaseServiceImpl");
			return this.mobCcsCsiCaseDAO.updateCsiCaseDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateCsiCaseDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSmsCount(String caseNo) {
		try {
			logger.info("updateSmsCount() is called in MobCcsCsiCaseServiceImpl");
			return this.mobCcsCsiCaseDAO.updateSmsCount(caseNo);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSmsCount()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateContactCount(String caseNo) {
		try {
			logger.info("updateContactCount() is called in MobCcsCsiCaseServiceImpl");
			return this.mobCcsCsiCaseDAO.updateContactCount(caseNo);
		} catch (DAOException de) {
			logger.error("Exception caught in updateContactCount()", de);
			throw new AppRuntimeException(de);
		}
	}
}
