package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.WaiveDAO;
import com.bomwebportal.dto.WaiveLkupDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class WaiveServiceImpl implements WaiveService {

	protected final Log logger = LogFactory.getLog(getClass());

	private WaiveDAO waiveDAO;
	
	public WaiveDAO getWaiveDAO() {
		return waiveDAO;
	}

	public void setWaiveDAO(WaiveDAO waiveDAO) {
		this.waiveDAO = waiveDAO;
	}

	public WaiveLkupDTO getWaiveLkupDTO(String reasonType, String reasonCd) {
		try {
			logger.info("getWaiveLkupDTO() is called in WaiveServiceImpl");
			return waiveDAO.getWaiveLkupDTO(reasonType, reasonCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getWaiveLkupDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<WaiveLkupDTO> findWaiveLkupByReasonType(String reasonType,
			Date appDate) {
		try {
			logger.info("findWaiveLkupByReasonType() is called in WaiveServiceImpl");
			return waiveDAO.findWaiveLkupByReasonType(reasonType, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in findWaiveLkupByReasonType()", de);
			throw new AppRuntimeException(de);
		}
	}

}
