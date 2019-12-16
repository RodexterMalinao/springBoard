package com.bomwebportal.mob.ds.service;

import java.util.List;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.StockUpdateDTO;
import com.bomwebportal.mob.ds.dao.MobDsQaRecordDAO;
import com.bomwebportal.mob.ds.dto.MobDsQaRecordDTO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MobDsQaRecordServiceImpl implements MobDsQaRecordService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobDsQaRecordDAO mobDsQaRecordDAO;
	
	public MobDsQaRecordDAO getMobDsQaRecordDAO() {
		return mobDsQaRecordDAO;
	}
	public void setMobDsQaRecordDAO(MobDsQaRecordDAO mobDsQaRecordDAO) {
		this.mobDsQaRecordDAO = mobDsQaRecordDAO;
	}

	public List<MobDsQaRecordDTO> getQARecord(String orderId) {
		try {
			logger.info("getQARecord() is called in MobDsQaRecordImpl");
			return mobDsQaRecordDAO.getQARecord(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in getQARecord()", e);
		}
		return null;
	}
	
	public MobDsQaRecordDTO getQARecordBySeq(List<MobDsQaRecordDTO> recordList, int seq) {
		try {
			logger.info("getQARecordBySeq() is called in MobDsQaRecordImpl");
				for (MobDsQaRecordDTO dto : recordList) {
					if (dto.getSeqNo() == seq) {
						return dto;
					}
				}
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in getQARecordBySeq()", e);
		}
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertQARecord(String orderId, int seqNo,String mustQ, 
			int qaOption, String remarks, String username) {
		try {
			logger.info("insertQARecord() is called in MobDsQaRecordImpl");
			return mobDsQaRecordDAO.insertQARecord(orderId, seqNo, mustQ, 
					qaOption, remarks, username);
		} catch (DAOException de) {
			logger.error("Exception caught in insertQARecord()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean isSupervisorApproved(String orderId) {
		try {
			logger.info("isSupervisorApproved() is called in MobDsQaRecordImpl");
			return mobDsQaRecordDAO.isSupervisorApproved(orderId);
		} catch (Exception e) {
			logger.error("Exception caught in getQARecord()", e);
		}
		return false;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int setMustQStatus(String orderId, String username) {
		try {
			logger.info("setMustQStatus() is called in MobDsQaRecordImpl");
			return mobDsQaRecordDAO.setMustQStatus(orderId, username);
		} catch (DAOException de) {
			logger.error("Exception caught in setMustQStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateQaOrderStatus(String orderId, String status, String username) {
		try {
			logger.info("insertQARecord() is called in MobDsQaRecordImpl");
			return mobDsQaRecordDAO.updateQaOrderStatus(orderId, status, username);
		} catch (DAOException de) {
			logger.error("Exception caught in insertQARecord()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateOrderApproveInd(String orderId, String username) {
		try {
			logger.info("insertQARecord() is called in MobDsQaRecordImpl");
			return mobDsQaRecordDAO.updateOrderApproveInd(orderId, username);
		} catch (DAOException de) {
			logger.error("Exception caught in insertQARecord()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
}
