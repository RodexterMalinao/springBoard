package com.bomwebportal.mob.cos.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.VasDetailDTO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignDtlDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignHdrDTO;
import com.bomwebportal.mob.cos.dto.MobCosCampaignVasDTO;

public interface MobCosCampaignService {
	/****** Campaign Page (header) ******/
	public List<MobCosCampaignHdrDTO> getCampaignTitle(String cpgTitle, String cpgName, String handsetDesc);
	public List<MobCosCampaignHdrDTO> getResultOption(String campId);
	public MobCosCampaignHdrDTO getCpgHdr(String cpgId);
	public int createCpgHdr(String cpgTitle, String cpgName, String cpgHS, String userId);
	public boolean createCpgChannelAssgn(String channelId, String cpgId, String customerTier, String userId);
	
	/****** Campaign Page (detail) ******/
	public List<MobCosCampaignDtlDTO> getCpgDtl(String cpgId);
	public List<CodeLkupDTO> getTierOption();
	public String genBasketId(String cpgId, String basketSeq, String basketId, String basketDesc, String userId);
	public boolean activeCpgBasket(String cpgId, String basketId, String userId);
	public boolean deactiveCpgBasket(String cpgId, String basketId, String userId);
	
	/****** Edit Campaign VAS Page******/
	public List<MobCosCampaignVasDTO> getCpgVasList(String locale);
	public List<MobCosCampaignVasDTO> getBasVasList(String basketId);
	
	/****** Add Campaign Basket Page ******/
	public List<CodeLkupDTO> getRpList();
	public List<CodeLkupDTO> getBundleList(String ratePlan);
	public List<CodeLkupDTO> getContractList(String ratePlan, String bundle);
	public List<CodeLkupDTO> getHandsetList(String ratePlan, String bundle, String contract);
	public List<CodeLkupDTO> getBasketList(String ratePlan, String bundle, String contract);
	public boolean createCpgBasket(String cpgId, String basketId, String basketDesc, String tier, String userId);
	
	/****** Update Campaign ******/
	public boolean updCpgHdr(String cpgId, String cpgTitle, String cpgName, String handset, Date effStartDate, Date effEndDate, String userId);
	public boolean updCpgDtl(String cpgId, String basketId, String basketDesc, String seq, String tier, 
			Date effStartDate, Date effEndDate, String userId);
	public boolean updCpgVas(String basketId, String itemId, String action, String userId);
	public boolean updCpgChannelAssgn(String channelId, String cpgId, String customerTier, String userId);
	
	/****** Preview Campaign Basket******/
	
	// MIP.P4 modification
	public List<VasDetailDTO> getPreviewBundleList(String basketId, String locale, String appDate, String channelCd, String offerNature);
	// MIP.P4 modification
	public List<VasDetailDTO> getPreviewSimList(String basketId, String locale, String appDate, String channelCd, String offerNature);
	// MIP.P4 modification
	public List<VasDetailDTO> getPreviewOptionList(String basketId, String locale, String appDate, String channelId, String channelCd, String offerNature);
	
	// MIP.P4 modification
	public BasketDTO getBasketAttribute(String basketId, String appDate);
}
