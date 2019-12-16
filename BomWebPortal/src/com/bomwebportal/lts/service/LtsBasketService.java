package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.dto.LookupItemDTO;
import com.bomwebportal.lts.dto.BasketCriteriaDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ret.RenewBasketPolicyDTO;

public interface LtsBasketService {

	List<RenewBasketPolicyDTO> getRenewBasketPolicyList(String basketChannelId, String existTpCatg, String remainContractMth);
	
	List<RenewBasketPolicyDTO> getRenewBasketPolicyList(String basketChannelId, String existTpCatg, String newBasketCatg, String remainContractMth);
	
	List<BasketDetailDTO> getBasketList(BasketCriteriaDTO basketCriteria);
	
	List<BasketDetailDTO> getAcqBasketList(String basketType,
			String paralleExtension, String locale, String deviceType,
			String basketCategory, String teamCode, String basketChannelId,  String[] bridgingMth,
			boolean contractPeriodGt24, boolean contractPeriodEq18, boolean contractPeriodLt12, boolean staffPlan, String housingType, boolean twoNCoverage,
			String[] filterProjectCds, String[] defaultProjectCds, String hktPremier, String pSrvBoundary, String pAreaCode, String pRole, String pOstype, String ltsHousingType);
	
	List<BasketDetailDTO> getStandaloneVasDummyBasketList(BasketCriteriaDTO basketCriteria);
	
	List<BasketDetailDTO> getRetBasketList(BasketCriteriaDTO basketCriteria);
	
	BasketDetailDTO getBasket(String basketId, String locale, String displayType);
	
	String getMaxIddFreeMin(String basketChannelId, String basketType,
				String iddFreeMin, boolean parallelExtension, String locale, String contractPeriod, boolean isNonNowTvCustOnly, String applicationDate);
	 
	public abstract String getBasketContractPeriod(String pBasketId);
	
	public boolean isPcdSbidValid(String PcdSbid);
	 
	public boolean isAllowArpuChange(String toProd,  String existArpu, String newArpu);
	 
	List<LookupItemDTO> getProjectCdList(String channelCd, String teamCd, String orderType);
	
	public List<String> getEligiblePsefList();
}
