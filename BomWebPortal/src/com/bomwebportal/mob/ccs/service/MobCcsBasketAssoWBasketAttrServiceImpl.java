package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsBasketAssoWBasketAttrDAO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrBasketTypeDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrBrandModelDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrCustomerTierDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrDTO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketAttrRatePlanDTO;

@Transactional(readOnly=true)
public class MobCcsBasketAssoWBasketAttrServiceImpl implements
		MobCcsBasketAssoWBasketAttrService {
	private Log logger = LogFactory.getLog(this.getClass());
	private MobCcsBasketAssoWBasketAttrDAO mobCcsBasketAssoWBasketAttrDAO;

	public MobCcsBasketAssoWBasketAttrDAO getMobCcsBasketAssoWBasketAttrDAO() {
		return mobCcsBasketAssoWBasketAttrDAO;
	}

	public void setMobCcsBasketAssoWBasketAttrDAO(
			MobCcsBasketAssoWBasketAttrDAO mobCcsBasketAssoWBasketAttrDAO) {
		this.mobCcsBasketAssoWBasketAttrDAO = mobCcsBasketAssoWBasketAttrDAO;
	}
	
	public List<BasketAssoWBasketAttrCustomerTierDTO> getWBasketAttrCustomerTierDTOALL() {
		try {
			logger.info("getWBasketAttrCustomerTierDTOALL() is called @ MobCcsBasketAssoWBasketAttrServiceImpl");
			return this.mobCcsBasketAssoWBasketAttrDAO.getWBasketAttrCustomerTierDTOALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getWBasketAttrCustomerTierDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoWBasketAttrBasketTypeDTO> getWBasketAttrBasketTypeDTOALL() {
		try {
			logger.info("getWBasketAttrBasketTypeDTOALL() is called @ MobCcsBasketAssoWBasketAttrServiceImpl");
			return this.mobCcsBasketAssoWBasketAttrDAO.getWBasketAttrBasketTypeDTOALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getWBasketAttrBasketTypeDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoWBasketAttrRatePlanDTO> getWBasketAttrRatePlanDTOALL() {
		try {
			logger.info("getWBasketAttrRatePlanDTOALL() is called @ MobCcsBasketAssoWBasketAttrServiceImpl");
			return this.mobCcsBasketAssoWBasketAttrDAO.getWBasketAttrRatePlanDTOALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getWBasketAttrRatePlanDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoWBasketAttrRatePlanDTO> getWBasketAttrRatePlanDTOBySearch(BasketAssoWBasketAttrDTO dto) {
		try {
			logger.info("getWBasketAttrRatePlanDTOBySearch() is called @ MobCcsBasketAssoWBasketAttrServiceImpl");
			return this.mobCcsBasketAssoWBasketAttrDAO.getWBasketAttrRatePlanDTOBySearch(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in getWBasketAttrRatePlanDTOBySearch()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoWBasketAttrBrandModelDTO> getWBasketAttrBrandModelDTOALL() {
		try {
			logger.info("getWBasketAttrBrandModelDTOALL() is called @ MobCcsBasketAssoWBasketAttrServiceImpl");
			return this.mobCcsBasketAssoWBasketAttrDAO.getWBasketAttrBrandModelDTOALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getWBasketAttrBrandModelDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoWBasketAttrBrandModelDTO> getWBasketAttrBrandModelDTOBySearch(BasketAssoWBasketAttrDTO dto) {
		try {
			logger.info("getWBasketAttrBrandModelDTOBySearch() is called @ MobCcsBasketAssoWBasketAttrServiceImpl");
			return this.mobCcsBasketAssoWBasketAttrDAO.getWBasketAttrBrandModelDTOBySearch(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in getWBasketAttrBrandModelDTOBySearch()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<BasketAssoWBasketAttrDTO> getBasketAssoWBasketAttrDTOBySearch(
			BasketAssoWBasketAttrDTO dto) {
		return this.getBasketAssoWBasketAttrDTOBySearch(dto, null);
	}

	public List<BasketAssoWBasketAttrDTO> getBasketAssoWBasketAttrDTOBySearch(
			String basketDesc) {
		return this.getBasketAssoWBasketAttrDTOBySearch(null, basketDesc);
	}

	public List<BasketAssoWBasketAttrDTO> getBasketAssoWBasketAttrDTOBySearch(
			BasketAssoWBasketAttrDTO dto, String basketDesc) {
		try {
			logger.info("getBasketAssoWBasketAttrDTOBySearch() is called @ MobCcsBasketAssoWBasketAttrServiceImpl");
			return this.mobCcsBasketAssoWBasketAttrDAO.getWBasketAttrDTOBySearch(dto, basketDesc);
		} catch (DAOException de) {
			logger.error("Exception caught in getBasketAssoWBasketAttrDTOBySearch()", de);
			throw new AppRuntimeException(de);
		}
	}

}
