package com.bomwebportal.sig.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.sig.dao.SignCaptureDAO;
import com.bomwebportal.sig.dto.SignCaptureDTO;

@Transactional
public class SignCaptureService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SignCaptureDAO signCaptureDAO;

	public SignCaptureDAO getSignCaptureDAO() {
		return signCaptureDAO;
	}

	public void setSignCaptureDAO(SignCaptureDAO signCaptureDAO) {
		this.signCaptureDAO = signCaptureDAO;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int createSignCaptureReq(SignCaptureDTO dto) {
		logger.debug("Getting SignCaptureDTO");
		try {
			return signCaptureDAO.createSignCaptureReq(dto);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public SignCaptureDTO getSignCaptureDTO(String reqId) {
		return getSignCaptureDTO(reqId, null);
	}
	
	public SignCaptureDTO getSignCaptureDTO(String reqId, String orderId) {
		logger.debug("[" + orderId + "] Getting SignCaptureDTO for reqId: " + reqId);
		try {
			return signCaptureDAO.getSignCaptureDTO(reqId, orderId);
		} catch (DAOException e) {
			logger.error("[" + orderId + "] getSignCaptureDTO@SignCaptureService", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSignCaptureReq(SignCaptureDTO dto, int timeout) {
		logger.debug("Updating SignCaptureReq for reqId: " + dto.getReqId());
		
		try {
			return signCaptureDAO.updateSignCaptureReq(dto, timeout);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void deleteSignCaptureReq(String reqId) {
		logger.debug("Deleting SignCaptureReq for reqId: " +reqId);
		try {
			signCaptureDAO.deleteSignCaptureReq(reqId);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	
	
}
