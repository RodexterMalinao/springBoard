package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsSalesInfoDAO;
import com.bomwebportal.mob.ccs.dto.SalesInfoDTO;
import com.bomwebportal.mob.dao.bom.BomSalesInfoDAO;

@Transactional(readOnly=true)
public class MobCcsSalesInfoServiceImpl implements MobCcsSalesInfoService {
	private Log logger = LogFactory.getLog(getClass());
	
	private MobCcsSalesInfoDAO mobCcsSalesInfoDAO;
	private BomSalesInfoDAO bomSalesInfoDAO;
	
	public MobCcsSalesInfoDAO getMobCcsSalesInfoDAO() {
		return mobCcsSalesInfoDAO;
	}

	public void setMobCcsSalesInfoDAO(MobCcsSalesInfoDAO mobCcsSalesInfoDAO) {
		this.mobCcsSalesInfoDAO = mobCcsSalesInfoDAO;
	}

	
	public BomSalesInfoDAO getBomSalesInfoDAO() {
		return bomSalesInfoDAO;
	}

	public void setBomSalesInfoDAO(BomSalesInfoDAO bomSalesInfoDAO) {
		this.bomSalesInfoDAO = bomSalesInfoDAO;
	}

	public List<SalesInfoDTO> getSalesInfoDTO(String channelCd, String category) {
		try {
			logger.info("getSalesInfoDTO() is called @ MobCcsSalesInfoServiceImpl");
			return this.mobCcsSalesInfoDAO.getSalesInfoDTO(channelCd, category);
		} catch (DAOException de) {
			logger.error("Exception caught in getSalesInfoDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	// add by Joyce 20120827
	public List<SalesInfoDTO> getSalesInfoDTOByID(String staffId, String appDate) {
		try {
			logger.info("getSalesInfoDTOByID() is called @ MobCcsSalesInfoServiceImpl");
			return this.mobCcsSalesInfoDAO.getSalesInfoDTOByID(staffId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getSalesInfoDTOByID()", de);
			throw new AppRuntimeException(de);
		}
	}

	
	// add by Dennis 20140204
	public String getBomSalesRoleDTOByID(String staffId){
		try {
			logger.info("getBomSalesInfoDTOByID() is called @ MobCcsSalesInfoServiceImpl");
			return this.bomSalesInfoDAO.getBomSalesRoleDTOByID(staffId);
		} catch (DAOException de) {
			logger.error("Exception caught in getBomSalesInfoDTOByID()", de);
			throw new AppRuntimeException(de);
		}
	}
}
