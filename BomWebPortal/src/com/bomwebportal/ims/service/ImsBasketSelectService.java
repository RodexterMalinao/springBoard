package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.ims.dto.BandwidthDTO;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ImsBasketDTO;

public interface ImsBasketSelectService {

	public List<ImsBasketDTO> getImsBasketList(String locale,
											   String bandwidthStr,
											   String housingTypeStr,
											   String technologyStr,
											   String IsPon,
											   String appDate,
											   String Sales_channel,
											   String teamCd,
											   Boolean sbFilterVas,
											   String mobileOfferInd,
											   String sbNo,
											   String ltsServiceInd,
											   String channelTeamCd,
											   String staffGroup,
											   String serviceCdStr);
	
	public List<String> getImsPlanTypeList(String locale);
	
	public List<String> getImsPreTickPlanTypeList(String locale);
	
	public List<String> getServiceCodeList(String sb) ;
	
	public String getStaffGroup(String staffId);
}
