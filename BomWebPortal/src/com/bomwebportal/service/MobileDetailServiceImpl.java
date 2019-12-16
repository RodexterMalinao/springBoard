package com.bomwebportal.service;

import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import com.bomwebportal.dao.HSTradeDescDAO;
import com.bomwebportal.dao.HandsetModelDAO;
import com.bomwebportal.dto.BrandDTO;
import com.bomwebportal.dto.CustomerInformationQuotaDTO;
import com.bomwebportal.dto.ModelDTO;
import com.bomwebportal.dto.BasketDTO;
import com.bomwebportal.dto.HSTradeDescDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

@Transactional(readOnly=true)
public class MobileDetailServiceImpl implements MobileDetailService {
	
	private HandsetModelDAO handsetModelDao;
	
	//add by Eliot 26-5-2011
	private HSTradeDescDAO hsTradeDescDao;

	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<BrandDTO> getBrandList(String locale, String channelId, String customerTier, String basketType, String appDate, String mipBrand){
		List<BrandDTO> brandList=new ArrayList<BrandDTO>();
    	
		try{
			logger.info("getBrandList() is called in brand service");
			brandList= handsetModelDao.getBrandList(locale,  channelId,  customerTier,basketType, appDate, mipBrand);
		}catch (DAOException de) {
			logger.error("Exception caught in getBrandList()", de);
			throw new AppRuntimeException(de);
		}
		
		return brandList;
	}
	
	public List<ModelDTO> getModelList(String locale, String modelId, String channelId, String customerTier, String basketType, String appDate, String mipBrand){
		List<ModelDTO> modelList=new ArrayList<ModelDTO>();
		try{
			logger.info("modelList() is called in  service");
			modelList= handsetModelDao.getModelList(locale,modelId,  channelId,  customerTier,basketType, appDate, mipBrand);
		}catch (DAOException de) {
			logger.error("Exception caught in getBrandList()", de);
			throw new AppRuntimeException(de);
		}
		
		return modelList;
		
	}
	
	public List<BasketDTO> getBasketList(String locale, String brandId,
			String modelId, String colorId, String channelId,
			String customerTier, String basketType, String appDate,final List<CustomerInformationQuotaDTO> existQuotaDtoList, String mipBrand, String mipSimType){
		List<BasketDTO> basketList=new ArrayList<BasketDTO>();
		try{
			logger.info("getBasketList() is called in  service");
			basketList= handsetModelDao.getBasketList(locale, brandId, modelId, colorId,  channelId,  customerTier, basketType,appDate, existQuotaDtoList, mipBrand, mipSimType);
		}catch (DAOException de) {
			logger.error("Exception caught in getBasketList()", de);
			throw new AppRuntimeException(de);
		}
		
		return basketList;
		
	}
	
	//	add by wilson 20110113
	public List<BasketDTO> getSimOnlyBasketList (String locale, String ratePlanType, String basketType , String customerTier, String channelId, String appDate,final List<CustomerInformationQuotaDTO> existQuotaDtoList,
			String mipBrand, String mipSimType){
	List<BasketDTO> basketList=new ArrayList<BasketDTO>();
	try{
		logger.info("getSimOnlyBasketList() is called in  service");
		basketList= handsetModelDao.getSimOnlyBasketList(locale, ratePlanType, basketType, customerTier,channelId, appDate,existQuotaDtoList, mipBrand, mipSimType);
	}catch (DAOException de) {
		logger.error("Exception caught in getSimOnlyBasketList()", de);
		throw new AppRuntimeException(de);
	}
	
		return basketList;
		
	}
	//remove function 20120202 wilson
	/*public List<ModelDTO> getHandSetDisplayList(String locale, String brandId, String modelId, String channelId, String customerTier,String basketType){
		List<ModelDTO> modelList=new ArrayList<ModelDTO>();
		try{
			logger.info("getHandSetDisplayList(() is called in  service");
			modelList= handsetModelDao.getHandSetDisplayList(locale,brandId,modelId, channelId,  customerTier, basketType);
		}catch (DAOException de) {
			logger.error("Exception caught in getBrandList()", de);
			throw new AppRuntimeException(de);
		}
		
		return modelList;
		
	}*/

	public ModelDTO getMobileDetail(String locale, String brandId, String modelId, String appDate){
		ModelDTO mobileDetail=new ModelDTO();
		try{
			logger.info("getMobileDetail(() is called in  service");
			mobileDetail= handsetModelDao.getMobileDetail(locale,brandId,modelId ,appDate  );
		}catch (DAOException de) {
			logger.error("Exception caught in getBrandList()", de);
			throw new AppRuntimeException(de);
		}
		
		return mobileDetail;
		
	}

	public HandsetModelDAO getHandsetModelDao() {
		return handsetModelDao;
	}

	public void setHandsetModelDao(HandsetModelDAO handsetModelDao) {
		this.handsetModelDao = handsetModelDao;
	}	
	
	//add by Eliot 26-5-2011
	public HSTradeDescDTO getHSTradeDesc(String orderId){
		HSTradeDescDTO hsTradeDesc = new HSTradeDescDTO();
		try{
			logger.info("getHSTradeDesc() is called in  service");
			hsTradeDesc = hsTradeDescDao.getHSTradeDesc(orderId);
		}catch (DAOException de) {
			logger.error("Exception caught in getHSTradeDesc()", de);
			throw new AppRuntimeException(de);
		}
		
		return hsTradeDesc;		
	}
	
	//add by Eliot 26-5-2011
	public HSTradeDescDAO getHsTradeDescDao() {
		return hsTradeDescDao;
	}
	
	//add by Eliot 26-5-2011
	public void setHsTradeDescDao(HSTradeDescDAO hsTradeDescDao) {
		this.hsTradeDescDao = hsTradeDescDao;
	}
	
	public String getSSFormDesc (String id) {
	    
	    String ssFormDesc = "";
	    
	    try{
		logger.info("getSSFormDesc() is called in  service");
		ssFormDesc = handsetModelDao.getSSFormDesc(id);
    	    }catch (DAOException de) {
    		logger.error("Exception caught in getSSFormDesc()", de);
    		throw new AppRuntimeException(de);
    	    }
	    
	    return ssFormDesc;
	}
	
	
	
	public ModelDTO getCallListMobileDetail (String locale, String channelId, String callList, String basketType, String rpType, String brandId, String modelId , String appDate){
		ModelDTO mobileDetail=new ModelDTO();
		try{
			logger.info("getCallListMobileDetail(() is called in  service");
			mobileDetail= handsetModelDao.getCallListMobileDetail(locale,  channelId,  callList,  basketType,  rpType,  brandId,  modelId ,  appDate   );
		}catch (DAOException de) {
			logger.error("Exception caught in getCallListMobileDetail()", de);
			throw new AppRuntimeException(de);
		}
		
		return mobileDetail;
		
	}
	
	//public List<BasketDTO> getCallListBasketList (String locale, String brandId, String modelId , String colorId, String channelId , String customerTier, String basketType, String rpType, String callList, String appDate);
	
	public List<BasketDTO> getCallListBasketList (String locale, String brandId, String modelId , String colorId, String channelId , String customerTier, String basketType, String rpType, String callList, String appDate,  List<CustomerInformationQuotaDTO> existQuotaDtoList, String mipBrand, String mipSimType){
		List<BasketDTO> basketList=new ArrayList<BasketDTO>();
		try{
			logger.info("getCallListBasketList() is called in  service");
			basketList= handsetModelDao.getCallListBasketList(locale, brandId, modelId, colorId, channelId, customerTier, basketType, rpType, callList, appDate, existQuotaDtoList, mipBrand, mipSimType);
		}catch (DAOException de) {
			logger.error("Exception caught in getCallListBasketList()", de);
			throw new AppRuntimeException(de);
		}
		
		return basketList;
		
	}
	
	public List<BasketDTO> getCallListSimOnlyBasketList (String locale, String ratePlanType, String basketType , String callList, String channelId, String appDate,List<CustomerInformationQuotaDTO> existQuotaDtoList, String mipBrand, String mipSimType){
		List<BasketDTO> basketList=new ArrayList<BasketDTO>();
		try{
			logger.info("getCallListHandSetDisplayList(() is called in  service");
			basketList= handsetModelDao.getCallListSimOnlyBasketList(locale, ratePlanType, basketType, callList, channelId, appDate,existQuotaDtoList, mipBrand, mipSimType );
		}catch (DAOException de) {
			logger.error("Exception caught in getCallListHandSetDisplayList()", de);
			throw new AppRuntimeException(de);
		}
		
		return basketList;
		
	}
	
	public ModelDTO getMobileDetail(String locale, String basketId){
		ModelDTO mobileDetail=new ModelDTO();
		try{
			logger.info("getMobileDetail(() is called in  service");
			mobileDetail= handsetModelDao.getMobileDetail(locale,basketId  );
		}catch (DAOException de) {
			logger.error("Exception caught in getBrandList()", de);
			throw new AppRuntimeException(de);
		}
		
		return mobileDetail;
		
	}

	public boolean isTradeDescExist(String basketId, Date appDate) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("isTradeDescExist() is called");
			}
			return this.hsTradeDescDao.isTradeDescExist(basketId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in isTradeDescExist()", de);
			throw new AppRuntimeException(de);
		}
	}
}
