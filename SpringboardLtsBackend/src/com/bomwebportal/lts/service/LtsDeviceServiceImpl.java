package com.bomwebportal.lts.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.DeviceDetailDAO;
import com.bomwebportal.lts.dto.DeviceDetailDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class LtsDeviceServiceImpl implements LtsDeviceService {
	
	
	protected DeviceDetailDAO deviceDetailDao;
	
	public DeviceDetailDAO getDeviceDetailDao() {
		return deviceDetailDao;
	}

	public void setDeviceDetailDao(DeviceDetailDAO deviceDetailDao) {
		this.deviceDetailDao = deviceDetailDao;
	}

	@Transactional(readOnly=false)
	public DeviceDetailDTO getEyeDevice(String deviceType, String locale) {
		try{
			return deviceDetailDao.getDevice(deviceType, LtsBackendConstant.CHANNEL_ID_SPRINGBOARD_RETAIL, locale);
		}catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional(readOnly=false)
	public DeviceDetailDTO getDevice(String deviceType, String channelId, String locale) {
		try{
			return deviceDetailDao.getDevice(deviceType, channelId, locale);
		}catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional(readOnly=false)
	public List<DeviceDetailDTO> getEyeDeviceList(String locale) {
		try{
			return deviceDetailDao.getEyeDeviceList(locale);
		}catch (DAOException de) {
			throw new RuntimeException(de.getCustomMessage());
		}
	}

}
