package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.ims.dto.ui.BasketSumUI;
import com.bomwebportal.ims.dto.ui.ChannelSumUI;
import com.bomwebportal.ims.dto.ui.ImsSummaryUI;

public interface BasketSummaryService {
	
	public List<ImsSummaryUI> basketTitleDetail(String basketId, String locale);
	public List<BasketSumUI> getBasketSumList(String itemId, String locale);
	public List<ChannelSumUI> getChannelSumList(String channelId, String locale);
}
