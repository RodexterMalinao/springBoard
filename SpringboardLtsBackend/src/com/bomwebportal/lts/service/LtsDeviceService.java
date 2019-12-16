package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.lts.dto.DeviceDetailDTO;

public interface LtsDeviceService {
	
	DeviceDetailDTO getDevice(String deviceType, String channelId, String locale);
	
	DeviceDetailDTO getEyeDevice(String deviceType, String locale);

	List<DeviceDetailDTO> getEyeDeviceList(String locale);
}
