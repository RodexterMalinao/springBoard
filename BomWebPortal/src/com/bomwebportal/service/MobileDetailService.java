package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.BrandDTO;
import com.bomwebportal.dto.CustomerInformationQuotaDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.dto.HSTradeDescDTO;

public interface MobileDetailService {

	public List<BrandDTO> getBrandList(String locale, String channelId, String customerTier,String basketType,String appDate, String mipBrand);
	public List<ModelDTO> getModelList(String locale, String modelId, String channelId, String customerTier, String basketType,String appDate, String mipBrand);
	
	//not using?
	//public List<ModelDTO> getHandSetDisplayList(String locale, String brandId, String modelId, String channelId, String customerTier, String basketType);
	public ModelDTO getMobileDetail(String locale, String brandId, String modelId, String appDate);
	public List<BasketDTO> getBasketList(String locale, String brandId,
			String modelId, String colorId, String channelId,
			String customerTier, String basketType, String appDate,final List<CustomerInformationQuotaDTO> existQuotaDtoList,
			String mipBrand, String simType);
	public List<BasketDTO> getSimOnlyBasketList (String locale, String ratePlanType, String basketType , String customerTier, String channelId, String appDate,final List<CustomerInformationQuotaDTO> existQuotaDtoList, String mipBrand, String simType);
	
	//add by Eliot 26-5-2011
	public HSTradeDescDTO getHSTradeDesc(String orderId);
	public String getSSFormDesc (String id);
	//add by wilson 20120112
	public ModelDTO getCallListMobileDetail (String locale, String channelId, String callList, String basketType, String rpType, String brandId, String modelId , String appDate);
	public List<BasketDTO> getCallListBasketList (String locale, String brandId, String modelId , String colorId, String channelId , String customerTier, String basketType, String rpType, String callList, String appDate,  List<CustomerInformationQuotaDTO> existQuotaDtoList, String mipBrand, String simType);
	//public String checkQuota (String baksetId);
	public List<BasketDTO> getCallListSimOnlyBasketList (String locale, String ratePlanType, String basketType , String callList, String channelId, String appDate, List<CustomerInformationQuotaDTO> existQuotaDtoList, String mipBrand, String simType);
	public ModelDTO getMobileDetail(String locale, String basketId);
	// erichui 20120723

	boolean isTradeDescExist(String basketId, Date appDate);
}
