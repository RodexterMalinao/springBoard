package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsBasketAssoJobListTeamAssoDAO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListTeamAssoDTO;

@Transactional(readOnly=true)
public class MobCcsBasketAssoJobListTeamAssoServiceImpl implements
		MobCcsBasketAssoJobListTeamAssoService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	private MobCcsBasketAssoJobListTeamAssoDAO mobCcsBasketAssoJobListTeamAssoDAO;
	
	public MobCcsBasketAssoJobListTeamAssoDAO getMobCcsBasketAssoJobListTeamAssoDAO() {
		return mobCcsBasketAssoJobListTeamAssoDAO;
	}

	public void setMobCcsBasketAssoJobListTeamAssoDAO(
			MobCcsBasketAssoJobListTeamAssoDAO mobCcsBasketAssoJobListTeamAssoDAO) {
		this.mobCcsBasketAssoJobListTeamAssoDAO = mobCcsBasketAssoJobListTeamAssoDAO;
	}

	public List<String> getExistJobListALL() {
		try {
			logger.info("getExistJobListALL() is called @ MobCcsBasketAssoJobListTeamAssoServiceImpl");
			return this.mobCcsBasketAssoJobListTeamAssoDAO.getExistJobListALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getExistJobListALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public BasketAssoJobListTeamAssoDTO getBasketAssoJobListTeamAssoDTO(String rowId) {
		try {
			logger.info("getBasketAssoJobListTeamAssoDTO() is called @ MobCcsBasketAssoJobListTeamAssoServiceImpl");
			return this.mobCcsBasketAssoJobListTeamAssoDAO.getBasketAssoJobListTeamAssoDTO(rowId);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListTeamAssoDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOByJobList(String jobList) {
		try {
			logger.info("getBasketAssoJobListTeamAssoDTOByJobList() is called @ MobCcsBasketAssoJobListTeamAssoServiceImpl");
			return this.mobCcsBasketAssoJobListTeamAssoDAO.getBasketAssoJobListTeamAssoDTOByJobList(jobList);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListTeamAssoDTOByJobList()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoInRunningDTOByJobList(String jobList) {
		try {
			logger.info("getBasketAssoJobListTeamAssoInRunningDTOByJobList() is called @ MobCcsBasketAssoJobListTeamAssoServiceImpl");
			return this.mobCcsBasketAssoJobListTeamAssoDAO.getBasketAssoJobListTeamAssoInRunningDTOByJobList(jobList);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListTeamAssoInRunningDTOByJobList()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOBySearch(
			BasketAssoJobListTeamAssoDTO dto) {
		return this.getBasketAssoJobListTeamAssoDTOBySearch(dto, false);
	}

	public List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOBySearch(
			BasketAssoJobListTeamAssoDTO dto, boolean showEnded) {
		try {
			logger.info("getBasketAssoJobListTeamAssoDTOBySearch() is called @ MobCcsBasketAssoJobListTeamAssoServiceImpl");
			return this.mobCcsBasketAssoJobListTeamAssoDAO.getBasketAssoJobListTeamAssoDTOBySearch(dto, showEnded);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListTeamAssoDTOBySearch()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoJobListTeamAssoDTO> getBasketAssoJobListTeamAssoDTOByRange(String jobList, String channelCd
			, String centreCd, String teamCd
			, Date startDate, Date endDate) {
		try {
			logger.info("getBasketAssoJobListTeamAssoDTOByRange() is called @ MobCcsBasketAssoJobListTeamAssoServiceImpl");
			return this.mobCcsBasketAssoJobListTeamAssoDAO.getBasketAssoJobListTeamAssoDTOByRange(jobList, channelCd, centreCd, teamCd, startDate, endDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListTeamAssoDTOByRange()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertBasketAssoJobListTeamAssoDTO(
			BasketAssoJobListTeamAssoDTO dto) {
		try {
			logger.info("insertBasketAssoJobListTeamAssoDTO() is called @ MobCcsBasketAssoJobListServiceImpl");
			this.mobCcsBasketAssoJobListTeamAssoDAO.insertBasketAssoJobListTeamAssoDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertBasketAssoJobListTeamAssoDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBasketAssoJobListTeamAssoDTOForEndAsso(BasketAssoJobListTeamAssoDTO dto) {
		try {
			logger.info("updateBasketAssoJobListTeamAssoDTOForEndAsso() is called @ MobCcsBasketAssoJobListServiceImpl");
			this.mobCcsBasketAssoJobListTeamAssoDAO.updateBasketAssoJobListTeamAssoDTOForEndAsso(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateBasketAssoJobListTeamAssoDTOForEndAsso()", de);
			throw new AppRuntimeException(de);
		}
	}

}
