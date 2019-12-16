package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsBasketAssoJobListBskAssoDAO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListBskAssoDTO;

@Transactional(readOnly=true)
public class MobCcsBasketAssoJobListBskAssoServiceImpl implements MobCcsBasketAssoJobListBskAssoService {
	private Log logger = LogFactory.getLog(getClass());
	
	private MobCcsBasketAssoJobListBskAssoDAO mobCcsBasketAssoJobListBskAssoDAO;
	
	public MobCcsBasketAssoJobListBskAssoDAO getMobCcsBasketAssoJobListBskAssoDAO() {
		return mobCcsBasketAssoJobListBskAssoDAO;
	}

	public void setMobCcsBasketAssoJobListBskAssoDAO(
			MobCcsBasketAssoJobListBskAssoDAO mobCcsBasketAssoJobListBskAssoDAO) {
		this.mobCcsBasketAssoJobListBskAssoDAO = mobCcsBasketAssoJobListBskAssoDAO;
	}

	public List<String> getExistJobListALL() {
		try {
			logger.info("getExistJobListALL() is called @ MobCcsBasketAssoJobListBskAssoServiceImpl");
			return this.mobCcsBasketAssoJobListBskAssoDAO.getExistJobListALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getExistJobListALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public BasketAssoJobListBskAssoDTO getBasketAssoJobListBskAssoDTO(String rowId) {
		try {
			logger.info("getBasketAssoJobListBskAssoDTO() is called @ MobCcsBasketAssoJobListBskAssoServiceImpl");
			return this.mobCcsBasketAssoJobListBskAssoDAO.getBasketAssoJobListBskAssoDTO(rowId);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListBskAssoDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<BasketAssoJobListBskAssoDTO> getBasketAssoJobListBskAssoDTOByJobList(String jobList) {
		try {
			logger.info("getBasketAssoJobListBskAssoDTOByJobList() is called @ MobCcsBasketAssoJobListBskAssoServiceImpl");
			return this.mobCcsBasketAssoJobListBskAssoDAO.getBasketAssoJobListBskAssoDTOByJobList(jobList);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListBskAssoDTOByJobList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<BasketAssoJobListBskAssoDTO> getBasketAssoJobListBskAssoInRunningDTOByJobList(String jobList) {
		try {
			logger.info("getBasketAssoJobListBskAssoInRunningDTOByJobList() is called @ MobCcsBasketAssoJobListBskAssoServiceImpl");
			return this.mobCcsBasketAssoJobListBskAssoDAO.getBasketAssoJobListBskAssoInRunningDTOByJobList(jobList);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListBskAssoInRunningDTOByJobList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<BasketAssoJobListBskAssoDTO> getBasketAssoJobListBskAssoDTOBySearch(
			BasketAssoJobListBskAssoDTO dto) {
		try {
			logger.info("getBasketAssoJobListBskAssoDTOBySearch() is called @ MobCcsBasketAssoJobListBskAssoServiceImpl");
			return this.mobCcsBasketAssoJobListBskAssoDAO.getBasketAssoJobListBskAssoDTOBySearch(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListBskAssoDTOBySearch()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertBasketAssoJobListBskAssoDTO(
			BasketAssoJobListBskAssoDTO dto) {
		try {
			logger.info("insertBasketAssoJobListBskAssoDTO() is called @ MobCcsBasketAssoJobListBskAssoServiceImpl");
			this.mobCcsBasketAssoJobListBskAssoDAO.insertBasketAssoJobListBskAssoDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertBasketAssoJobListBskAssoDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBasketAssoJobListBskAssoDTOForEndAsso(
			BasketAssoJobListBskAssoDTO dto) {
		try {
			logger.info("updateBasketAssoJobListBskAssoDTOForEndAsso() is called @ MobCcsBasketAssoJobListBskAssoServiceImpl");
			this.mobCcsBasketAssoJobListBskAssoDAO.updateBasketAssoJobListBskAssoDTOForEndAsso(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateBasketAssoJobListBskAssoDTOForEndAsso()", de);
			throw new AppRuntimeException(de);
		}
	}
}
