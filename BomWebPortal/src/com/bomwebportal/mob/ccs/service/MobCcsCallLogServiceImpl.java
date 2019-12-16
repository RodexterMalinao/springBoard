package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsCallLogDAO;
import com.bomwebportal.mob.ccs.dto.CallLogDTO;

@Transactional(readOnly=true)
public class MobCcsCallLogServiceImpl implements MobCcsCallLogService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobCcsCallLogDAO mobCcsCallLogDAO;
	
	public MobCcsCallLogDAO getMobCcsCallLogDAO() {
		return mobCcsCallLogDAO;
	}

	public void setMobCcsCallLogDAO(MobCcsCallLogDAO mobCcsCallLogDAO) {
		this.mobCcsCallLogDAO = mobCcsCallLogDAO;
	}

	public List<CallLogDTO> getCallLogDTOALLByOrderId(String orderId) {
		try {
			logger.info("getCallLogSearchDTOBySearch() is called in MobCcsCallLogServiceImpl");
			return this.mobCcsCallLogDAO.getCallLogDTOALLByOrderId(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getCallLogDTOALLByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertCalLogDTO(CallLogDTO dto) {
		try {
			logger.info("insertCalLogDTO() is called in MobCcsCallLogServiceImpl");
			return this.mobCcsCallLogDAO.insertCallLogDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertCalLogDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

}
