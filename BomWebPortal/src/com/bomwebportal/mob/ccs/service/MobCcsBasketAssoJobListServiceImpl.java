package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsBasketAssoJobListDAO;
import com.bomwebportal.mob.ccs.dto.BasketAssoJobListDTO;

@Transactional(readOnly=true)
public class MobCcsBasketAssoJobListServiceImpl implements MobCcsBasketAssoJobListService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	private MobCcsBasketAssoJobListDAO mobCcsBasketAssoJobListDAO;
	
	public MobCcsBasketAssoJobListDAO getMobCcsBasketAssoJobListDAO() {
		return mobCcsBasketAssoJobListDAO;
	}

	public void setMobCcsBasketAssoJobListDAO(MobCcsBasketAssoJobListDAO mobCcsBasketAssoJobListDAO) {
		this.mobCcsBasketAssoJobListDAO = mobCcsBasketAssoJobListDAO;
	}

	public List<String> getExistJobListALL() {
		try {
			logger.info("getExistJobListALL() is called @ MobCcsBasketAssoJobListServiceImpl");
			return this.mobCcsBasketAssoJobListDAO.getExistJobListALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getExistJobListALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<String> getExistChannelCdALL() {
		try {
			logger.info("getExistChannelCdALL() is called @ MobCcsBasketAssoJobListServiceImpl");
			return this.mobCcsBasketAssoJobListDAO.getExistChannelCdALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getExistChannelCdALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public BasketAssoJobListDTO getBasketAssoJobListDTO(String rowId) {
		try {
			logger.info("getBasketAssoJobListDTO() is called @ MobCcsBasketAssoJobListServiceImpl");
			return this.mobCcsBasketAssoJobListDAO.getBasketAssoJobListDTO(rowId);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	public BasketAssoJobListDTO getBasketAssoJobListDTOByJobList(String jobList) {
		try {
			logger.info("getBasketAssoJobListDTOByJobList() is called @ MobCcsBasketAssoJobListServiceImpl");
			return this.mobCcsBasketAssoJobListDAO.getBasketAssoJobListDTOByJobList(jobList);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListDTOByJobList()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoJobListDTO> getBasketAssoJobListDTOByJobListAndChannelCd(String jobList, String channelCd) {
		try {
			logger.info("getBasketAssoJobListDTOByJobListAndChannelCd() is called @ MobCcsBasketAssoJobListServiceImpl");
			return this.mobCcsBasketAssoJobListDAO.getBasketAssoJobListDTOByJobListAndChannelCd(jobList, channelCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoJobListDTOByJobListAndChannelCd()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertBasketAssoJobListDTO(BasketAssoJobListDTO dto) {
		try {
			logger.info("insertBasketAssoJobListDTO() is called @ MobCcsBasketAssoJobListServiceImpl");
			this.mobCcsBasketAssoJobListDAO.insertBasketAssoJobListDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertBasketAssoJobListDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void deleteBasketAssoJobListDTO(String rowId) {
		try {
			logger.info("deleteBasketAssoJobListDTO() is called @ MobCcsBasketAssoJobListServiceImpl");
			this.mobCcsBasketAssoJobListDAO.deleteBasketAssoJobListDTO(rowId);
		} catch (DAOException de) {
			logger.error("Exception caught in deleteBasketAssoJobListDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateBasketAssoJobListDTODesc(BasketAssoJobListDTO dto) {
		try {
			logger.info("updateBasketAssoJobListDTODesc() is called @ MobCcsBasketAssoJobListServiceImpl");
			return this.mobCcsBasketAssoJobListDAO.updateBasketAssoJobListDTODesc(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in updateBasketAssoJobListDTODesc()", de);
			throw new AppRuntimeException(de);
		}
	}

	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
