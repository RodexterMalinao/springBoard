package com.bomltsportal.service;

import com.bomltsportal.dto.BuildingMarkerDTO;

public interface LivechatpathService {

	public String generateLiveChatUrl(BuildingMarkerDTO marker, String channel, boolean isChi, String srv_Id, String addressEn, String addressCh,String reqPage, String flat, String floor);
	
	public String generateLiveChatUrl(BuildingMarkerDTO marker, String channel, boolean isChi, String srv_Id, String addressEn, String addressCh,String reqPage, String flat, String floor, boolean isWip, boolean isSpecialHandleCust);
}