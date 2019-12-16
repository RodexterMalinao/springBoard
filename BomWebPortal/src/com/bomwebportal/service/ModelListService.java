package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.BrandDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.exception.DAOException;

public interface ModelListService {

	public List<BrandDTO> getBrandList(String locale, String channelId, String customerTier,String basketType, String appDate, String mipBrand);
	public List<ModelDTO> getModelList(String locale, String modelId, String channelId, String customerTier,String basketType, String appDate, String mipBrand);
	public List<ModelDTO> getHandSetDisplayList(String locale, String brandId, String modelId,String channelId, String customerTier,String basketType, String appDate, String mipBrand);
	
	public List<BrandDTO> getCallListBrandList (String locale, String channelId, String jobList , String basketType, String rpType, String appDate);
	public List<ModelDTO> getCallListModelList (String locale, String channelId, String jobList , String basketType, String rpType, String appDate);
	public List<ModelDTO> getCallListHandSetDisplayList (String locale, String brandId, String modelId ,  String channelId, String customerTier, String basketType, String rpType, String callList, String appDate);
		
}
