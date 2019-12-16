package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.mob.ccs.dto.ui.SpecialMRTReserveUI;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.SpecialMRTReserveDAO;
import com.bomwebportal.mob.ccs.dto.MrtReserveDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;


@Transactional (readOnly=true)
public class SpecialMRTReserveServiceImpl implements SpecialMRTReserveService{


	protected final Log logger = LogFactory.getLog(getClass());
	
	private SpecialMRTReserveDAO specialMRTReserveDao;
	

	public SpecialMRTReserveDAO getSpecialMRTReserveDao() {
		return specialMRTReserveDao;
	}

	public void setSpecialMRTReserveDao(SpecialMRTReserveDAO specialMRTReserveDao) {
		this.specialMRTReserveDao = specialMRTReserveDao;
	}
	
	public List<SpecialMRTReserveUI> getSpecialReserveMRTList(String staffId) {
		
		try {
			logger.info("getSpecialReserveMRTList() is called in SpecialMRTReserveServiceImpl");
			return specialMRTReserveDao.getSpecialReserveMRTList(staffId);
		} catch (DAOException de) {
			logger.error("Exception caught in getSpecialReserveMRTList()", de);
			throw new AppRuntimeException(de);
		}
		


	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertSpecialMrtReserve(SpecialMrtRequestDTO dto, String staffId, String srvNumType){
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertSpecialMrtReserve() is called @ SpecialMrtReserveServiceImpl");
			}
			return this.specialMRTReserveDao.insertSpecialMrtReserve(dto , staffId, srvNumType);
		} catch (DAOException de) {
			logger.error("Exception caught in insertSpecialMrtReserve()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSpecialMrtReserve(SpecialMrtRequestDTO dto, String staffId, String srvNumType) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("updateSpecialMrtReserve() is called @ SpecialMrtReserveServiceImpl");
			}
			return this.specialMRTReserveDao.updateSpecialMrtReserve(dto , staffId, srvNumType);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSpecialMrtReserve()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	public boolean getSpecialMrtReserveByRequestId(String requestId){
		try {
			logger.info("getSpecialMrtReserveByRequestId() is called in SpecialMRTReserveServiceImpl");
			return specialMRTReserveDao.getSpecialMrtReserveByRequestId(requestId);
		} catch (DAOException de) {
			logger.error("Exception caught in getSpecialMrtReserveByRequestId()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	public Integer noOfRequests(String staffId) {
		
		try {
			logger.info("noOfRequests() is called in SpecialMRTReserveServiceImpl");
			return specialMRTReserveDao.noOfRequests(staffId);
		} catch (DAOException de) {
			logger.error("Exception caught in noOfRequests()", de);
			throw new AppRuntimeException(de);			
		}
		
		
	}
	
	public List<MrtReserveDTO> getMrtReserveByMrt(String msisdn){
		try {
			logger.info("getMrtReserveByMrt() is called in SpecialMRTReserveServiceImpl");
			return specialMRTReserveDao.getMrtReserveByMrt(msisdn);
		} catch (DAOException de) {
			logger.error("Exception caught in getMrtReserveByMrt()", de);
			throw new AppRuntimeException(de);			
		}
	}

	/*public List<SpecialMRTReserveUI> getRejectedSpecialMRTRequest(String staffId) {
		
		try {
			logger.info("getRejectedSpecialMRTRequest() is called in SpecialMRTReserveServiceImpl");
			return specialMRTReserveDao.getRejectedSpecialMRTRequest(staffId);
		} catch (DAOException de) {
			logger.error("Exception caught in getRejectedSpecialMRTRequest()", de);
			throw new AppRuntimeException(de);			
		}
		
	}*/
	
	
}
