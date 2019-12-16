package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsSpecialMRTSummaryDAO;
import com.bomwebportal.mob.ccs.dto.MaintParmLkupDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;

public class MobCcsSpecialMRTSummayServiceImpl implements MobCcsSpecialMRTSummaryService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	MobCcsSpecialMRTSummaryDAO mobCcsSpecialMRTSummaryDao;

	public MobCcsSpecialMRTSummaryDAO getMobCcsSpecialMRTSummaryDao() {
		return mobCcsSpecialMRTSummaryDao;
	}

	public void setMobCcsSpecialMRTSummaryDao(
			MobCcsSpecialMRTSummaryDAO mobCcsSpecialMRTSummaryDao) {
		this.mobCcsSpecialMRTSummaryDao = mobCcsSpecialMRTSummaryDao;
	}

	public List<SpecialMrtRequestDTO> getSpecialMRTRequests(String requestStatus,
			String requestDateFrom, String requestDateTo, String channel,
			String requestedBy, String mobNum) {
		
		try {
			logger.info("specialMRTRequests() is called in MobCcsSpecialMRTSummayImpl");
			return mobCcsSpecialMRTSummaryDao.getSpecialMRTRequests(requestStatus, requestDateFrom, requestDateTo, channel, requestedBy, mobNum);
		} catch (DAOException de) {
			logger.error("Exception caught in specialMRTRequests()",de);
			throw new AppRuntimeException(de);
		}
		
		
	}
	
	public List<SpecialMrtRequestDTO> getSpecialMRTRequestsByManager(String requestStatus, String requestDateFrom, String requestDateTo, String channelCd, String teamCd) {
		
		try {
			logger.info("specialMRTRequests() is called in MobCcsSpecialMRTSummayImpl");
			return mobCcsSpecialMRTSummaryDao.getSpecialMRTRequestsByManager(requestStatus, requestDateFrom, requestDateTo, channelCd, teamCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getSpecialMRTRequestsByManager()",de);
			throw new AppRuntimeException(de);
		}
		
		
	}

	public List<MaintParmLkupDTO> getResultStatusTypes(String channelCd) {

		try {
			logger.info("getResultStatusTypes() is called in MobCcsSpecialMRTSummayImpl");
			return mobCcsSpecialMRTSummaryDao.getResultStatusTypes(channelCd);
		} catch (DAOException de) {
			logger.error("Exception caught in getResultStatusTypes()",de);
			throw new AppRuntimeException(de);
		}
		
	}

	public List<MaintParmLkupDTO> getChannelTypes() {

		try {
			logger.info("getChannelTypes() is called in MobCcsSpecialMRTSummayImpl");
			return mobCcsSpecialMRTSummaryDao.getChannelTypes();
		} catch (DAOException de) {
			logger.error("Exception caught in getChannelTypes()",de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	

	
	
	
}
