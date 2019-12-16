package com.bomwebportal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.HandsetModelDAO;
import com.bomwebportal.dto.BrandDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

@Transactional(readOnly=true)
public class ModelListServiceImpl implements ModelListService {
	
	private HandsetModelDAO handsetModelDao;

	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<BrandDTO> getBrandList(String locale, String channelId, String customerTier, String basketType, String appDate, String mipBrand){
		List<BrandDTO> brandList=new ArrayList<BrandDTO>();
    	
		try{
			logger.info("getBrandList() is called in brand service");
			brandList= handsetModelDao.getBrandList(locale,  channelId,  customerTier, basketType, appDate, mipBrand);
		}catch (DAOException de) {
			logger.error("Exception caught in getBrandList()", de);
			throw new AppRuntimeException(de);
		}
		
		return brandList;
	}
	
	public List<ModelDTO> getModelList(String locale, String modelId,  String channelId, String customerTier,String basketType,String appDate, String mipBrand){
		List<ModelDTO> modelList=new ArrayList<ModelDTO>();
		try{
			logger.info("modelList() is called in  service");
			modelList= handsetModelDao.getModelList(locale,modelId,  channelId,  customerTier, basketType, appDate, mipBrand);
		}catch (DAOException de) {
			logger.error("Exception caught in getModelList()", de);
			throw new AppRuntimeException(de);
		}
		
		return modelList;
		
	}
	
	public List<ModelDTO> getHandSetDisplayList(String locale, String brandId, String modelId, String channelId, String customerTier, String basketType, String appDate, String mipBrand){
		List<ModelDTO> modelList=new ArrayList<ModelDTO>();
		try{
			logger.info("getHandSetDisplayList(() is called in  service");
			modelList= handsetModelDao.getHandSetDisplayList(locale,brandId,modelId,  channelId,  customerTier, basketType, appDate, mipBrand);
		}catch (DAOException de) {
			logger.error("Exception caught in getHandSetDisplayList()", de);
			throw new AppRuntimeException(de);
		}
		
		return modelList;
		
	}

	public HandsetModelDAO getHandsetModelDao() {
		return handsetModelDao;
	}

	public void setHandsetModelDao(HandsetModelDAO handsetModelDao) {
		this.handsetModelDao = handsetModelDao;
	}
	
	
	public List<BrandDTO> getCallListBrandList (String locale, String channelId, String jobList , String basketType, String rpType, String appDate){
		List<BrandDTO> brandList=new ArrayList<BrandDTO>();
    	
		try{
			logger.info("getCallListBrandList() is called in brand service");
			brandList= handsetModelDao.getCallListBrandList(locale, channelId, jobList, basketType, rpType, appDate);
		}catch (DAOException de) {
			logger.error("Exception caught in getCallListBrandList()", de);
			throw new AppRuntimeException(de);
		}
		
		return brandList;
	}
	public List<ModelDTO> getCallListModelList (String locale, String channelId, String jobList , String basketType, String rpType, String appDate){
		List<ModelDTO> modelList=new ArrayList<ModelDTO>();
		try{
			logger.info("getCallListModelList() is called in  service");
			modelList= handsetModelDao.getCallListModelList(locale, channelId, jobList, basketType, rpType, appDate);
		}catch (DAOException de) {
			logger.error("Exception caught in getCallListModelList()", de);
			throw new AppRuntimeException(de);
		}
		
		return modelList;
	}
	
	public List<ModelDTO> getCallListHandSetDisplayList (String locale, String brandId, String modelId ,  String channelId, String customerTier, String basketType, String rpType, String callList, String appDate){
		List<ModelDTO> modelList=new ArrayList<ModelDTO>();
		try{
			logger.info("getCallListHandSetDisplayList(() is called in  service");
			modelList= handsetModelDao.getCallListHandSetDisplayList(locale, brandId, modelId, channelId, customerTier, basketType, rpType, callList, appDate);
		}catch (DAOException de) {
			logger.error("Exception caught in getCallListHandSetDisplayList()", de);
			throw new AppRuntimeException(de);
		}
		
		return modelList;
		
	}
	
	
	
}
