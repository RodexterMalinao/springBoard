package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MappingLkupDAO;
import com.bomwebportal.mob.ccs.dto.MappingLkupDTO;

@Transactional(readOnly=true)
public class MappingLkupServiceImpl implements MappingLkupService {
	private Log logger = LogFactory.getLog(getClass());
	private MappingLkupDAO mappingLkupDAO;

	public MappingLkupDAO getMappingLkupDAO() {
		return mappingLkupDAO;
	}

	public void setMappingLkupDAO(MappingLkupDAO mappingLkupDAO) {
		this.mappingLkupDAO = mappingLkupDAO;
	}

	public List<MappingLkupDTO> getMappingLkupDTOALL(String mapType) {
		try {
			logger.info("getMappingLkupDTOALL() is called @ MappingLkupServiceImpl");
			return mappingLkupDAO.getMappingLkupDTOALL(mapType);
		} catch (DAOException de) {
			logger.error("Exception caught in getMappingLkupDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public MappingLkupDTO getMappingLkupDTO(String mapType, String mapFrom) {
		try {
			logger.info("getMappingLkupDTO() is called @ MappingLkupServiceImpl");
			return mappingLkupDAO.getMappingLkupDTO(mapType, mapFrom);
		} catch (DAOException de) {
			logger.error("Exception caught in getMappingLkupDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
}
