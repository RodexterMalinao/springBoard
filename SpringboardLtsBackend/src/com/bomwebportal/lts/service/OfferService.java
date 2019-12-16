package com.bomwebportal.lts.service;

import com.bomwebportal.lts.dto.ChannelAttbDTO;
import com.bomwebportal.lts.dto.ItemAttbDTO;

public interface OfferService extends OfferProfileService {

	
	public String getServicePlanCode(String itemId);
	
	public String getVasPlanCode(String itemId);
	
	public String[] getItemOfferCodes(String itemId);
	
	public String[] getItemPsefCodes(String itemId);
	
	public abstract ItemAttbDTO[] getItemAttb(String pItemId);

	public abstract String getItemContractPeriod(String pItemId);

	public abstract String getChannelDescription(String pChannelId,
			String pLocale);

	public abstract ChannelAttbDTO[][] getChannelAttbDisplay(String pChannelId,
			String pLocale);

	public abstract ItemAttbDTO[] getItemAttbByItemAttbAssign(String pItemId);
	
	public String getItemDisplayDesc(String itemId, String locale);
}