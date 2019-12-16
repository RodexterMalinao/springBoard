package com.bomwebportal.mob.ccs.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsApprovalLogDAO;
import com.bomwebportal.mob.ccs.dto.ApprovalLogDTO;

@Transactional(readOnly=true)
public class MobCcsApprovalLogServiceImpl implements MobCcsApprovalLogService {
	private Log logger = LogFactory.getLog(getClass());
	private MobCcsApprovalLogDAO mobCcsApprovalLogDAO;
	
	public MobCcsApprovalLogDAO getMobCcsApprovalLogDAO() {
		return mobCcsApprovalLogDAO;
	}

	public void setMobCcsApprovalLogDAO(MobCcsApprovalLogDAO mobCcsApprovalLogDAO) {
		this.mobCcsApprovalLogDAO = mobCcsApprovalLogDAO;
	}

	public String getNextAuthorizedId() {
		try {
			logger.info("getNextAuthorizedId() is called @ MobCcsApprovalLogServiceImpl");
			return mobCcsApprovalLogDAO.getNextAuthorizedId();
		} catch (DAOException de) {
			logger.error("Exception caught in getNextAuthorizedId()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertApprovalLogDTO(ApprovalLogDTO dto) {
		try {
			logger.info("insertApprovalLogDTO() is called @ MobCcsApprovalLogServiceImpl");
			return mobCcsApprovalLogDAO.insertApprovalLogDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertApprovalLogDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateApprovalLog(String orderId, String authorizedId, String lastUpdBy)  {
		try {
			logger.info("updateApprovalLog() is called @ MobCcsApprovalLogServiceImpl");
			return mobCcsApprovalLogDAO.updateApprovalLog( orderId,  authorizedId,  lastUpdBy);
		} catch (DAOException de) {
			logger.error("Exception caught in updateApprovalLog()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	//add by Eliot 20120515
	public boolean isApproval(String orderId, String actionType){
		try {
			logger.info("isApproval() is called @ MobCcsApprovalLogServiceImpl");
			return mobCcsApprovalLogDAO.isApproval(orderId, actionType);
		} catch (DAOException de) {
			logger.error("Exception caught in isApproval()", de);
			throw new AppRuntimeException(de);
		}
	}

	public boolean isApprovedBasket(String orderId, String basketId, String authorizedAction){
		try {
			logger.info("isApprovedBasket() is called @ MobCcsApprovalLogServiceImpl");
			return mobCcsApprovalLogDAO.isApprovedBasket(orderId, basketId, authorizedAction);
		} catch (DAOException de) {
			logger.error("Exception caught in authorizedAction()", de);
			throw new AppRuntimeException(de);
		}
	}
}
