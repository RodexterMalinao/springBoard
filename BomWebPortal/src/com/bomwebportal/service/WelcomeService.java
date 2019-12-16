package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.MobCcsMaintFuncDTO;

public interface WelcomeService {

//	public List<String> getUsableMaintId(String channelId, String category);
	public String getOrderEnquiryCount (String startDate, String endDate, 
			String orderId, String orderStatus, String variousDateType, String channel, 
			String staffId, String orderType, String category, String areaCd, 
			String shopCd, String group);
	public String getDsOrderReviewCount (String staffId, String channelId, String channelCd, String category);

	public List<MobCcsMaintFuncDTO> getUsableMaintFuncInfo(int channelId, String channelCd, String category);
	
	//For Direct Sales
	public BomSalesUserDTO getDSinfo(BomSalesUserDTO user) throws DAOException;
	
}
