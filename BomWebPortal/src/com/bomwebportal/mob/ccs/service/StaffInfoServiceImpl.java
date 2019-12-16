package com.bomwebportal.mob.ccs.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StaffInfoDAO;
import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;

@Transactional(readOnly = true)
public class StaffInfoServiceImpl implements StaffInfoService {

	protected final Log logger = LogFactory.getLog(getClass());

	private StaffInfoDAO staffInfoDao;

	public void setStaffInfoDao(StaffInfoDAO staffInfoDao) {
		this.staffInfoDao = staffInfoDao;
	}
	public StaffInfoDAO getStaffInfoDao() {
		return staffInfoDao;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String getStaffName(String staffId, String appDate) {
		try {
			logger.info("getStaffName() is called in StaffInfoServiceImpl");
			return staffInfoDao.getStaffName(staffId, appDate);

		} catch (DAOException de) {
			logger.error("Exception caught in getStaffName()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertBomwebStaffInfo(StaffInfoUI dto) {
		try {
			logger.info("insertBomwebStaffInfo() is called in StaffInfoServiceImpl");
			return staffInfoDao.insertBomwebStaffInfo(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in insertBomwebStaffInfo()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public StaffInfoUI getStaffInfoDTO(String orderId) {
		try {
			logger.info("getStaffInfoDTO() is called in StaffInfoServiceImpl");
			return staffInfoDao.getStaffInfoDTO(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getStaffInfoDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String getChannelCd(String staffId, String appDate) {
		try {
			logger.info("getChannelCd() is called in StaffInfoServiceImpl");
			return staffInfoDao.getChannelCd(staffId, appDate);

		} catch (DAOException de) {
			logger.error("Exception caught in getChannelCd()", de);
			throw new AppRuntimeException(de);
		}
	}
}
