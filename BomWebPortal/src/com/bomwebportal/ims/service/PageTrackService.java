package com.bomwebportal.ims.service;

import com.bomwebportal.dto.CustomerInformationDTO;

public interface PageTrackService {
	
	public int insertPageTrack(String ipAddress, String staffId, String pageId, String func) throws Exception;
	
	public void doPageTrackCustSearch(String ipAddress, String staffId, String pageId, String func, CustomerInformationDTO customerInformationDTO);
	
	public void doPageTrackRolloutSearch(String ipAddress, String staffId, String pageId, String func, String address);

}
