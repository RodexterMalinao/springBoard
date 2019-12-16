package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.lts.dto.BasketPremiumDTO;
import com.bomwebportal.lts.dto.ItemSetCriteriaDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;



public interface PremiumItemSetDetailService {
	
	public ItemSetDetailDTO getGiftItemSet(String pGiftCode, String locale, String channelId, String applDate, String basketId, String teamCd, String srvBdry);
	
	public List<ItemSetDetailDTO> getPcdBundleGiftItemSet(String pPcdSbid, String locale, String channelId, String applDate, String basketId, String teamCd, String srvBdry);
	
	public List<ItemSetDetailDTO> getBasketItemSet(ItemSetCriteriaDTO itemSetCriteria);
	
	public List<BasketPremiumDTO> getBasketPremiumList(String[] pBasketId, String pLocale);
	
	public ItemSetDetailDTO getItemSetDetailDTO(ItemSetCriteriaDTO itemSetCriteria) ;
	
	public String getExtraPremiumQty(String toProd, String newApru, String apruDifferent);
	
	public String getArpuChangePremiumQty(String toProd, String existArpu, String newApru);
		
	public List<ItemSetDetailDTO> getPremiumSetList(ItemSetCriteriaDTO itemSetCriteria);
	
	public List<ItemSetDetailDTO> getAllPremiumSetList(ItemSetCriteriaDTO itemSetCriteria);
	
	
}
