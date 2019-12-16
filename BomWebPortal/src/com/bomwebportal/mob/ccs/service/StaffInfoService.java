package com.bomwebportal.mob.ccs.service;

import com.bomwebportal.mob.ccs.dto.ui.StaffInfoUI;


public interface StaffInfoService {

	public String getStaffName(String staffId, String appDate);
	
	public int insertBomwebStaffInfo(StaffInfoUI dto);
	
	public StaffInfoUI getStaffInfoDTO(String orderId);
	
	public String getChannelCd(String staffId, String appDate);

}
