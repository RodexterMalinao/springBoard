package com.bomwebportal.mob.ccs.service;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsEligibilityDAO;
import com.bomwebportal.mob.ccs.dto.MobCcsEligibilityDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsEligibilityDTO.IdDocType;
import com.bomwebportal.mob.ccs.dto.MobCcsValuePropAssgnDTO;

@Transactional(readOnly=true)
public class MobCcsEligibilityServiceImpl implements MobCcsEligibilityService {
	private Log logger = LogFactory.getLog(getClass());
	private MobCcsEligibilityDAO mobCcsEligibilityDAO;
	
	public MobCcsEligibilityDAO getMobCcsEligibilityDAO() {
		return mobCcsEligibilityDAO;
	}

	public void setMobCcsEligibilityDAO(MobCcsEligibilityDAO mobCcsEligibilityDAO) {
		this.mobCcsEligibilityDAO = mobCcsEligibilityDAO;
	}
	
	public List<MobCcsValuePropAssgnDTO> getMobCcsValuePropAssgnDTOALL() {
		try {
			return this.mobCcsEligibilityDAO.getMobCcsValuePropAssgnDTOALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsValuePropAssgnDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<MobCcsEligibilityDTO> getMobCcsEligibilityDTOALL(IdDocType idDocType, String idDocNum) {
		if (idDocType == null || StringUtils.isBlank(idDocNum)) {
			return Collections.emptyList();
		}
		try {
			return this.mobCcsEligibilityDAO.getMobCcsEligibilityDTOALL(idDocType, idDocNum);
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsEligibilityDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public MobCcsEligibilityDTO getMobCcsEligibilityDTO(IdDocType idDocType,
			String idDocNum, String valuePropId) {
		if (idDocType == null || StringUtils.isBlank(idDocNum) || StringUtils.isBlank(valuePropId)) {
			return null;
		}
		try {
			return this.mobCcsEligibilityDAO.getMobCcsEligibilityDTO(idDocType, idDocNum, valuePropId);
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsEligibilityDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertMobCcsEligibilityDTO(MobCcsEligibilityDTO dto) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("idDocType: " + dto.getIdDocType() + ", idDocNum: " + dto.getIdDocNum() + ", eligibility: " + dto.getValuePropId() + ", createBy: " + dto.getCreateBy());
			}
			return this.mobCcsEligibilityDAO.insertMobCcsEligibilityDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertMobCcsEligibilityDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

}
