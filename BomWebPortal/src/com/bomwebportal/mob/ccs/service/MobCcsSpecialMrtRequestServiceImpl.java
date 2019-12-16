package com.bomwebportal.mob.ccs.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MrtInventoryDAO;
import com.bomwebportal.mob.ccs.dao.SpecialMrtRequestDAO;
import com.bomwebportal.mob.ccs.dto.MrtInventoryDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;

public class MobCcsSpecialMrtRequestServiceImpl implements MobCcsSpecialMrtRequestService{

	private Log logger = LogFactory.getLog(getClass());
	private SpecialMrtRequestDAO specialMrtRequestDAO;
	private MrtInventoryDAO mrtInventoryDAO;
	
	public SpecialMrtRequestDAO getSpecialMrtRequestDAO() {
		return specialMrtRequestDAO;
	}

	public void setSpecialMrtRequestDAO(SpecialMrtRequestDAO specialMrtRequestDAO) {
		this.specialMrtRequestDAO = specialMrtRequestDAO;
	}

	public MrtInventoryDAO getMrtInventoryDAO() {
		return mrtInventoryDAO;
	}

	public void setMrtInventoryDAO(MrtInventoryDAO mrtInventoryDAO) {
		this.mrtInventoryDAO = mrtInventoryDAO;
	}

	public SpecialMrtRequestDTO getSpecialMrtRequestDTO(String requestId) {
		
		try {
			logger.info("getSpecialMrtRequestDTO() is called @ MobCcsSpecialMrtRequestServiceImpl");
			return this.specialMrtRequestDAO.getSpecialMrtRequestDTO(requestId);
		} catch (DAOException de) {
			logger.error("Exception caught in getSpecialMrtRequestDTO()", de);
			throw new AppRuntimeException(de);
		}
	
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSpecialMrtRequestDTO(SpecialMrtRequestDTO dto) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("updateSpecialMrtRequestDTO() is called @ MobCcsSpecialMrtRequestServiceImpl");
			}
			return this.specialMrtRequestDAO.updateSpecialMrtRequestDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSpecialMrtRequestDTO()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String insertSpecialMrtRequest(SpecialMrtRequestDTO dto) {
		
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertSpecialMrtRequest() is called @ MobCcsSpecialMrtRequestServiceImpl");
			}
			return this.specialMrtRequestDAO.insertSpecialMrtRequest(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertSpecialMrtRequest()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateSpecialMrtRequestStatus(SpecialMrtRequestDTO dto) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("updateSpecialMrtRequestStatus() is called @ MobCcsSpecialMrtRequestServiceImpl");
			}
			return this.specialMrtRequestDAO.updateSpecialMrtRequestStatus(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateSpecialMrtRequestStatus()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	
	public MrtInventoryDTO getMrtInventoryByRequestId(String requestId) {
		try {
			logger.info("getMrtInventoryByRequestId() is called @ MobCcsSpecialMrtRequestServiceImpl");
			return this.mrtInventoryDAO.getMrtInventoryByRequestId(requestId);
		} catch (DAOException de) {
			logger.error("Exception caught in getMrtInventoryByRequestId()", de);
			throw new AppRuntimeException(de);
		}
	
	}
	
	public MrtInventoryDTO getMrtInventoryByMrt(String msisdn){
		try {
			logger.info("getMrtInventoryByMrt() is called @ MobCcsSpecialMrtRequestServiceImpl");
			return this.mrtInventoryDAO.getMrtInventoryByMrt(msisdn);
		} catch (DAOException de) {
			logger.error("Exception caught in getMrtInventoryByMrt()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	 public int updateMrtInventoryByMrt(MrtInventoryDTO dto){
		 try {
				if (logger.isInfoEnabled()) {
					logger.info("updateMrtInventoryByMrt() is called @ MobCcsSpecialMrtRequestServiceImpl");
				}
				return this.mrtInventoryDAO.updateMrtInventoryByMrt(dto);
			} catch (DAOException de) {
				logger.error("Exception caught in updateMrtInventoryByMrt()", de);
				throw new AppRuntimeException(de);
			}
	 }
	 
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	 public int updateMrtInventoryStatusByMrt(String msisdnStatus, String staffId, String msisdn, String requestId) {
		 try {
				if (logger.isInfoEnabled()) {
					logger.info("updateMrtInventoryStatusByMrt() is called @ MobCcsSpecialMrtRequestServiceImpl");
				}
				return this.mrtInventoryDAO.updateMrtInventoryStatusByMrt(msisdnStatus, staffId, msisdn, requestId);
			} catch (DAOException de) {
				logger.error("Exception caught in updateMrtInventoryStatusByMrt()", de);
				throw new AppRuntimeException(de);
			}
	 }
	 
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public int updateMrtInventoryByRequestId(MrtInventoryDTO dto){
		logger.info("updateMrtInventoryByRequestId() is called in MrtInventoryServiceImpl");
		try {
				/*List<MrtStatusDTO> msisdnRecords = this.mobCcsMrtStatusService.getMrtStatusDTOByMsisdnAndStatus(dto.getMsisdn(), MsisdnStatus.RESERVE);
				if (!this.isEmpty(msisdnRecords)) {
					// we expect no reserve record
					if (logger.isInfoEnabled()) {
						logger.info("mrtStatus record exists: " + dto.getMsisdn() + " in " + MsisdnStatus.RESERVE);
					}
					return -1;
				}*/
			return this.mrtInventoryDAO.updateMrtInventoryByRequestId(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateMrtInventoryByRequestId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getSpecialMrtRequestByApprovalSerial(String approvalSerial){
		try {
			logger.info("getSpecialMrtRequestByApprovalSerial() is called @ MobCcsSpecialMrtRequestServiceImpl");
			return this.specialMrtRequestDAO.getSpecialMrtRequestByApprovalSerial(approvalSerial);
		} catch (DAOException de) {
			logger.error("Exception caught in getSpecialMrtRequestByApprovalSerial()", de);
			throw new AppRuntimeException(de);
		}
	}
}
