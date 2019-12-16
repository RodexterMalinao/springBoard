package com.bomwebportal.mob.ccs.service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderRescueDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsIncidentCauseDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsOrderRescueDTO;

@Transactional(readOnly=true)
public class MobCcsOrderRescueServiceImpl implements MobCcsOrderRescueService {
	private Log logger = LogFactory.getLog(this.getClass());

	private MobCcsIncidentCauseService mobCcsIncidentCauseService;
	
	private MobCcsOrderRescueDAO mobCcsOrderRescueDAO;
	
	public MobCcsIncidentCauseService getMobCcsIncidentCauseService() {
		return mobCcsIncidentCauseService;
	}

	public void setMobCcsIncidentCauseService(
			MobCcsIncidentCauseService mobCcsIncidentCauseService) {
		this.mobCcsIncidentCauseService = mobCcsIncidentCauseService;
	}

	public MobCcsOrderRescueDAO getMobCcsOrderRescueDAO() {
		return mobCcsOrderRescueDAO;
	}

	public void setMobCcsOrderRescueDAO(MobCcsOrderRescueDAO mobCcsOrderRescueDAO) {
		this.mobCcsOrderRescueDAO = mobCcsOrderRescueDAO;
	}

	public MobCcsOrderRescueDTO getMobCcsOrderRescueDTO(String rowId) {
		try {
			logger.info("getMobCcsOrderRescueDTO() is called @ MobCcsOrderRescueServiceImpl");
			MobCcsOrderRescueDTO dto = this.mobCcsOrderRescueDAO.getMobCcsOrderRescueDTO(rowId);
			this.updateSalesFallout(dto);
			return dto;
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsOrderRescueDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	public MobCcsOrderRescueDTO getMobCcsOrderRescueDTOByIncidentNo(String incidentNo) {
		try {
			logger.info("getMobCcsOrderRescueDTOByIncidentNo() is called @ MobCcsOrderRescueServiceImpl");
			MobCcsOrderRescueDTO dto =  this.mobCcsOrderRescueDAO.getMobCcsOrderRescueDTOByIncidentNo(incidentNo);
			this.updateSalesFallout(dto);
			return dto;
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsOrderRescueDTOByIncidentNo()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<MobCcsOrderRescueDTO> getMobCcsOrderRescueDTOByOrderId(String orderId) {
		try {
			logger.info("getMobCcsOrderRescueDTOByOrderId() is called @ MobCcsOrderRescueServiceImpl");
			List<MobCcsOrderRescueDTO> list = this.mobCcsOrderRescueDAO.getMobCcsOrderRescueDTOByOrderId(orderId);
			this.updateSalesFallout(list);
			return list;
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsOrderRescueDTOByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<MobCcsOrderRescueDTO> getMobCcsOrderRescueDTOBySearch(
			String orderId, String incidentNo, String msisdn) {
		try {
			logger.info("getMobCcsOrderRescueDTOBySearch() is called @ MobCcsOrderRescueServiceImpl");
			List<MobCcsOrderRescueDTO> list =  this.mobCcsOrderRescueDAO.getMobCcsOrderRescueDTOBySearch(orderId, incidentNo, msisdn);
			this.updateSalesFallout(list);
			return list;
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsOrderRescueDTOBySearch()", de);
			throw new AppRuntimeException(de);
		}
	}


	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertMobCcsOrderRescueDTO(MobCcsOrderRescueDTO dto) {
		try {
			logger.info("insertMobCcsOrderRescueDTO() is called @ MobCcsOrderRescueServiceImpl");
			String incidentNo = this.mobCcsOrderRescueDAO.getNextIncidentNo();
			if (logger.isDebugEnabled()) {
				logger.debug("incidentNo: " + incidentNo);
			}
			dto.setIncidentNo(incidentNo);
			this.mobCcsOrderRescueDAO.insertMobCcsOrderRescueDTO(dto);
			for (MobCcsIncidentCauseDTO icDto : dto.getIncidentCauses()) {
				icDto.setIncidentNo(dto.getIncidentNo());
				this.mobCcsIncidentCauseService.insertMobCcsIncidentCause(icDto);
			}
		} catch (DAOException de) {
			logger.error("Exception caught in insertMobCcsOrderRescueDTO()", de);
			throw new AppRuntimeException(de);
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateMobCcsOrderRescueDTO(MobCcsOrderRescueDTO dto) {
		try {
			logger.info("updateMobCcsOrderRescueDTO() is called @ MobCcsOrderRescueServiceImpl");
			this.mobCcsOrderRescueDAO.updateMobCcsOrderRescueDTO(dto);
			this.mobCcsIncidentCauseService.deleteMobCcsIncidentCause(dto.getIncidentNo());
			for (MobCcsIncidentCauseDTO icDto : dto.getIncidentCauses()) {
				icDto.setIncidentNo(dto.getIncidentNo());
				this.mobCcsIncidentCauseService.insertMobCcsIncidentCause(icDto);
			}
		} catch (DAOException de) {
			logger.error("Exception caught in updateMobCcsOrderRescueDTO()", de);
			throw new AppRuntimeException(de);
		}

	}

	private void updateSalesFallout(MobCcsOrderRescueDTO orderRescuesDto) {
		this.updateSalesFallout(Arrays.asList(orderRescuesDto));
	}

	private void updateSalesFallout(List<MobCcsOrderRescueDTO> orderRescuesDtos) {
		if (this.isEmpty(orderRescuesDtos)) {
			return;
		}
		Map<String, List<CodeLkupDTO>> entityCodeMap = LookupTableBean.getInstance().getCodeLkupList();
		List<CodeLkupDTO> salesFalloutCds = entityCodeMap.get("MOB_SALES_FALLOUT_CD");
		for (MobCcsOrderRescueDTO orderRescueDto : orderRescuesDtos) {
			if (StringUtils.isNotBlank(orderRescueDto.getReasonCd())) {
				for (CodeLkupDTO salesFalloutCd : salesFalloutCds) {
					if (orderRescueDto.getReasonCd().equals(salesFalloutCd.getCodeId())) {
						orderRescueDto.setSalesFalloutFlag(true);
						break;
					}
				}
			}
		}
	}
	
	private boolean isEmpty(List<?> list) {
		return list == null || list.isEmpty();
	}
}
