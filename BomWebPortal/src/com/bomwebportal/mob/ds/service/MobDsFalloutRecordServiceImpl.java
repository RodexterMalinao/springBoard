package com.bomwebportal.mob.ds.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ds.dao.MobDsFalloutRecordDAO;
import com.bomwebportal.mob.ds.dao.MobDsOrderDAO;
import com.bomwebportal.mob.ds.dto.MobDsFalloutRecordDTO;

@Transactional(readOnly=true)
public class MobDsFalloutRecordServiceImpl implements MobDsFalloutRecordService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobDsFalloutRecordDAO mobDsFalloutRecordDAO;
	
	public MobDsFalloutRecordDAO getMobDsFalloutRecordDAO() {
		return mobDsFalloutRecordDAO;
	}

	public void setMobDsFalloutRecordDAO(MobDsFalloutRecordDAO mobDsFalloutRecordDAO) {
		this.mobDsFalloutRecordDAO = mobDsFalloutRecordDAO;
	}
	

	public List<MobDsFalloutRecordDTO> getFalloutRecordALLByOrderId(
			String orderId) {
		// TODO Auto-generated method stub
		try {
			logger.info("getFalloutRecordALLByOrderId() is called in MobDsFalloutRecordServiceImpl");
			return this.mobDsFalloutRecordDAO.getFalloutRecordALLByOrderId(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getFalloutRecordALLByOrderId()", de);
			throw new AppRuntimeException(de);
		}
		
	}

	public MobDsFalloutRecordDTO getFalloutRecordALLByCaseNo(String caseNo) {
		// TODO Auto-generated method stub
		try {
			logger.info("getFalloutRecordALLByCaseNo() is called in MobDsFalloutRecordServiceImpl");
			return this.mobDsFalloutRecordDAO.getFalloutRecordALLByCaseNo(caseNo);
		} catch (DAOException de) {
			logger.error("Exception caught in getFalloutRecordALLByCaseNo()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertFalloutRecordDTO(MobDsFalloutRecordDTO dto) {
		// TODO Auto-generated method stub
		
		try {
			logger.info("insertMobDsFalloutRecordDTO() is called in MobDsFalloutRecordServiceImpl");
			return this.mobDsFalloutRecordDAO.insertFalloutRecordDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertMobDsFalloutRecordDTO", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateFalloutRecordDTO(MobDsFalloutRecordDTO dto) {
		// TODO Auto-generated method stub
		
		try {
			logger.info("updateMobDsFalloutRecordDTO() is called in MobDsFalloutRecordServiceImpl");
			return this.mobDsFalloutRecordDAO.updateFalloutRecordDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateMobDsFalloutRecordDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	public int getLatestCaseNoByOrderId(String orderId) {
		// TODO Auto-generated method stub
		try {
			logger.info("getLatestCaseNoByOrderId() is called in MobDsFalloutRecordServiceImpl");
			return this.mobDsFalloutRecordDAO.getLatestCaseNoByOrderId(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getLatestCaseNoByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	
	}
	
	public boolean hasUnsettledFallout(String orderId) {
		// TODO Auto-generated method stub
		try {
			logger.info("hasUnsettledFallout() is called in MobDsFalloutRecordServiceImpl");
			return this.mobDsFalloutRecordDAO.hasUnsettledFallout(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in hasUnsettledFallout()", de);
			throw new AppRuntimeException(de);
		}
	
	}
	
	
	
	
}
