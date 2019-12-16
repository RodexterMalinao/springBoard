package com.bomwebportal.ims.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bomwebportal.dto.VasParmDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.BasketDetailsDTO;
import com.bomwebportal.ims.dto.NowTVCheckDTO;
import com.bomwebportal.ims.dto.ValidateOfferResultDTO;
import com.bomwebportal.ims.dto.ui.ChannelUI;
import com.bomwebportal.ims.dto.ui.GiftUI;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.dto.ui.VASDetailUI;


public interface ImsBasketDetailsService {

	public List<BasketDetailsDTO> getBasketDetailsList(String locale, String basketID);
	
	public List<BasketDetailsDTO> getBasketPaymentList (String basketID);

	public List<VASDetailUI> getBVASDetailList(String locale, String basketID,String appDate);
	
	public List<VASDetailUI> getVASDetailList(String locale, String basketID,String appDate,String bandwidth, String technology, String HousingType, Boolean sbFilterVas ,String sales_channel,String preInstallInd,String like100Ind,String mobileOfferInd,String preUseInd,String sbNo,String supremeFsInd,
			String channelTeamCd, String staffGroup, String serviceCdStr, String progOfferCd, String otChrgInd);

	public List<VASDetailUI> getExclusiveItemList (String locale, List<String> selectedList);
	
	public List<VASDetailUI> getExclusiveItemList(String locale, List<String> selectedVasList,List<String> selectedGiftList);
	
	public List<VASDetailUI> getGiftExclusiveProgItemList(String locale, List<String> selectedList, String progOfferCd);
	
	public List<VASDetailUI> getNowExclusiveItemList (String locale, List<String> selectedList);

	public List<VASDetailUI> getFilterItemList (String FilterType);
	
	public List<NowTVCheckDTO> getNowTVCheckList (String Serbdyno, String tech);
	
	public List<GiftUI> getGiftList(String housingType, String locale, String appDate,String SB,String technology, String channel, String like100Ind, String progOfferCd, String otChrgInd,
			String channelTeamCd, String staffGroup, String staffOfferInd, String nowTVGiftInd);
	
	public List<GiftUI> getPromoGiftList(String locale, String housingType, String SB, String channel, String technology, String like100Ind, String progOfferCd) throws Exception;
	
	public HashMap<String, HashMap<String, Object>> getGiftSelectCnt(String channel,String locale);
	
	public boolean checkEligibleForNowTV(String basketId);
	
	public List<SubscribedItemUI> addAutoTieItems(String pcdVasSelected);
	
	public ValidateOfferResultDTO validateOfferInBom(String[] inList);
	
	public List<VasParmDTO> getimsVasParmList(String locale);
	
	public List<VASDetailUI> getVasExclusiveGiftType(List<String> selectedVasList,List<String> selectedGiftList,String locale) throws DAOException;
	
	public List<SubscribedItemUI> getVASAutoTieDummyGiftList(String pcdVasSelected);
}
