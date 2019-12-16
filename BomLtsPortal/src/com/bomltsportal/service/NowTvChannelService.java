package com.bomltsportal.service;

import java.util.List;

import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ExclusiveChannelDetailDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;

public interface NowTvChannelService {

	List<ExclusiveChannelDetailDTO> getExclusiveChannelList(
			List<ChannelGroupDetailDTO> channelGroupList, String locale);
	
	ValidationResultDTO validateExclusiveChannel(
			List<ChannelGroupDetailDTO> channelGroupList, String locale);
	
	 String getChannelDescription(String pChannelId, String pLocale);
	 
	 List<ChannelGroupDetailDTO> getChannelGroupList(String formType,
				String deviceType, String pLocale, boolean selectDefault, String credit);
}
