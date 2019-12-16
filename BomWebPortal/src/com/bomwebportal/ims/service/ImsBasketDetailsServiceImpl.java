package com.bomwebportal.ims.service;

import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.*;

import com.bomwebportal.dto.VasParmDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.BasketDetailsBomDAO;
import com.bomwebportal.ims.dao.BasketDetailsDAO;
import com.bomwebportal.ims.dao.BasketSelectDAO;
import com.bomwebportal.ims.dao.GiftDetailDAO;
import com.bomwebportal.ims.dao.NowTVCheckDAO;
import com.bomwebportal.ims.dao.VASDetailDAO;
import com.bomwebportal.ims.dto.AttbDTO;
import com.bomwebportal.ims.dto.BasketDetailsDTO;
import com.bomwebportal.ims.dto.ImsBasketDTO;
import com.bomwebportal.ims.dto.NowTVCheckDTO;
import com.bomwebportal.ims.dto.SubscribedCslOfferDTO;
import com.bomwebportal.ims.dto.ValidateOfferResultDTO;
import com.bomwebportal.ims.dto.ui.GiftUI;
import com.bomwebportal.ims.dto.ui.NowTVCampaignCdDTO;
import com.bomwebportal.ims.dto.ui.SubscribedItemUI;
import com.bomwebportal.ims.dto.ui.VASDetailUI;

@Transactional(readOnly=true)
public class ImsBasketDetailsServiceImpl implements ImsBasketDetailsService {
	
	private BasketDetailsDAO basketDetailsDao;
	private VASDetailDAO BVASDetailDao;
	private VASDetailDAO VASDetailDao;
	private NowTVCheckDAO NowTVCheckDao;
	private GiftDetailDAO GiftDetailDao;
	private BasketDetailsBomDAO basketDetailsBomDao;
	

	protected final Log logger = LogFactory.getLog(getClass());
	

	
	public List<BasketDetailsDTO> getBasketDetailsList(String locale, String basketID)
	{
		List<BasketDetailsDTO> basketDetailsList=new ArrayList<BasketDetailsDTO>();
		
		try{
			logger.info("getBasketDetailsList() is called in service");
			basketDetailsList = basketDetailsDao.getBasketDetailsList(locale, basketID);
		}catch (DAOException de) {
			logger.error("Exception caught in getBasketDetailsList()", de);
			throw new AppRuntimeException(de);
		}
		
		return basketDetailsList;
		
	}
	
	public List<BasketDetailsDTO> getBasketPaymentList (String basketID)
	{
		List<BasketDetailsDTO> basketPaymentList=new ArrayList<BasketDetailsDTO>();
		
		try{
			logger.info("getBasketPaymentList() is called in service");
			basketPaymentList = basketDetailsDao.getBasketPaymentList(basketID);
		}catch (DAOException de) {
			logger.error("Exception caught in getBasketPaymentList()", de);
			throw new AppRuntimeException(de);
		}
		
		return basketPaymentList;
		
	}
	
	public BasketDetailsDAO getBasketDetailsDao() {
		return basketDetailsDao;
	}

	public void setBasketDetailsDao(BasketDetailsDAO basketDetailsDao) {
		this.basketDetailsDao = basketDetailsDao;
	}	
	
	public List<VASDetailUI> getBVASDetailList(String locale, String basketID, String appDate)
	{
		List<VASDetailUI> BVASDetailList=new ArrayList<VASDetailUI>();
		
		try{
			logger.info("getBVASDetailList() is called in service");
			logger.info("locale, basketID: " + locale +","+ basketID);
			BVASDetailList = BVASDetailDao.getBVASDetailList(locale, basketID, appDate);
		}catch (DAOException de) {
			logger.error("Exception caught in getBVASDetailList()", de);
			throw new AppRuntimeException(de);
		}
		
		return BVASDetailList;
		
	}

	public List<VASDetailUI> getVASDetailList(String locale, String basketID, String appDate, String bandwidth, String technology, 
			String housingType, Boolean sbFilterVas, String sales_channel,String preInstallInd,String like100Ind, String mobileOfferInd, String preUseInd, String sbNo, String supremeFsInd,
			String channelTeamCd, String staffGroup, String serviceCdStr, String progOfferCd, String otChrgInd)
	{
		List<VASDetailUI> VASDetailList=new ArrayList<VASDetailUI>();
		List<VasParmDTO> imsVasParmList=new ArrayList<VasParmDTO>();
		
		try{
			logger.info("getVASDetailList() is called in service");
			logger.info("locale, basketID:" + locale + ","+ basketID +"," +appDate);
			VASDetailList = VASDetailDao.getVASDetailList(locale, basketID, appDate, bandwidth, technology, housingType, sbFilterVas,sales_channel, preInstallInd, like100Ind, mobileOfferInd, preUseInd, sbNo, supremeFsInd,
					channelTeamCd, staffGroup, serviceCdStr, progOfferCd, otChrgInd);
		}catch (DAOException de) {
			logger.error("Exception caught in getVASDetailList()", de);
			throw new AppRuntimeException(de);
		}
		
		return VASDetailList;
		
	}
	
	public List<VasParmDTO> getimsVasParmList(String locale)
	{
		List<VasParmDTO> imsVasParmList=new ArrayList<VasParmDTO>();
		
		try{
			logger.info("getimsVasParmList() is called in service");
			logger.info("locale:" + locale );
			
			imsVasParmList = VASDetailDao.getImsVasParmList(locale);
			
			
		}catch (DAOException de) {
			logger.error("Exception caught in getimsVasParmList()", de);
			throw new AppRuntimeException(de);
		}
		
		return imsVasParmList;
		
	}
	
	public List<VASDetailUI> getExclusiveItemList(String locale, List<String> selectedList){
		return getExclusiveItemList(locale,selectedList,selectedList);
	}
	
	public List<VASDetailUI> getExclusiveItemList(String locale, List<String> selectedVasList,List<String> selectedGiftList)
	{
		List<VASDetailUI> VASExclusiveList=new ArrayList<VASDetailUI>();
		
		try{
			logger.info("getExclusiveItemList() is called in service");
			logger.info("locale, selectedVasList,selectedGiftList: " + locale +","+ selectedVasList+"-///-"+selectedGiftList);
			VASExclusiveList = VASDetailDao.getExclusiveItemList(locale, selectedVasList,selectedGiftList);
		}catch (DAOException de) {
			logger.error("Exception caught in getExclusiveItemList()", de);
			throw new AppRuntimeException(de);
		}
		
		return VASExclusiveList;
		
	}
	
	public List<VASDetailUI> getGiftExclusiveProgItemList(String locale, List<String> selectedList, String progOfferCd)
	{
		List<VASDetailUI> VASExclusiveList=new ArrayList<VASDetailUI>();
		
		try{
			logger.info("getGiftExclusiveProgItemList() is called in service");
			logger.info("locale, selectedList: " + locale +","+ selectedList);
			VASExclusiveList = GiftDetailDao.getGiftExclusiveProgItemList(locale, selectedList, progOfferCd);
		}catch (DAOException de) {
			logger.error("Exception caught in getGiftExclusiveProgItemList()", de);
			throw new AppRuntimeException(de);
		}
		
		return VASExclusiveList;
		
	}
	
	public List<VASDetailUI> getNowExclusiveItemList(String locale, List<String> selectedList)
	{
		List<VASDetailUI> nowVASExclusiveList=new ArrayList<VASDetailUI>();
		
		try{
			logger.info("getNowExclusiveItemList() is called in service");
			logger.info("locale, selectedList: " + locale +","+ selectedList);
			nowVASExclusiveList = VASDetailDao.getNowExclusiveItemList(locale, selectedList);
		}catch (DAOException de) {
			logger.error("Exception caught in getNowExclusiveItemList()", de);
			throw new AppRuntimeException(de);
		}
		
		return nowVASExclusiveList;
		
	}
	
	public List<VASDetailUI> getFilterItemList(String FilterType)
	{
		List<VASDetailUI> FilterItemList=new ArrayList<VASDetailUI>();
		
		try{
			logger.info("getFilterItemList() is called in service");
			logger.info("FilterType: " + FilterType);
			FilterItemList = VASDetailDao.getFilterItemList(FilterType);
		}catch (DAOException de) {
			logger.error("Exception caught in FilterItemList()", de);
			throw new AppRuntimeException(de);
		}	
		return FilterItemList;
		
	}
	
	public VASDetailDAO getVASDetailDao() {
		return VASDetailDao;
	}

	public void setVASDetailDao(VASDetailDAO VASDetailDao) {
		this.VASDetailDao = VASDetailDao;
	}	

	
	public VASDetailDAO getBVASDetailDao() {
		return BVASDetailDao;
	}

	public void setBVASDetailDao(VASDetailDAO BVASDetailDao) {
		this.BVASDetailDao = BVASDetailDao;
	}	
		
	public List<NowTVCheckDTO> getNowTVCheckList(String Serbdyno, String tech)
	{
		List<NowTVCheckDTO> NowTVCheckList=new ArrayList<NowTVCheckDTO>();
		
		try{
			logger.info("getFilterItemList() is called in service");
			logger.info("Serbdyno: " + Serbdyno);
			NowTVCheckList = NowTVCheckDao.getNowTVCheckList(Serbdyno, tech);
		}catch (DAOException de) {
			logger.error("Exception caught in NowTVCheckList()", de);
			throw new AppRuntimeException(de);
		}	
		return NowTVCheckList;
		
	}

	public NowTVCheckDAO getNowTVCheckDao() {
		return NowTVCheckDao;
	}

	public void setNowTVCheckDao(NowTVCheckDAO nowTVCheckDao) {
		NowTVCheckDao = nowTVCheckDao;
	}
	


	public List<GiftUI> getGiftList(String housingType, String locale, String appDate,String SB,String technology, String channel, String like100Ind, String progOfferCd, String otChrgInd,
			String channelTeamCd, String staffGroup, String staffOfferInd, String nowTVGiftInd)
	{
		List<GiftUI> giftList=new ArrayList<GiftUI>();
		
		try{
			logger.info("getGiftList() is called in service");
			logger.info("locale, housingType: " + locale +","+ housingType);
			giftList = GiftDetailDao.getGiftList(housingType, locale, appDate, SB, technology, channel, like100Ind, progOfferCd, otChrgInd, channelTeamCd, staffGroup, staffOfferInd, nowTVGiftInd);
		}catch (DAOException de) {
			logger.error("Exception caught in getGiftList()", de);
			throw new AppRuntimeException(de);
		}
		
		String itemIdStr = "";
		String comma = "";
		if(giftList.size()>0){
			for(GiftUI gift:giftList){
				
				gift.setGiftTitle(this.splitString(gift.getItemDetail())[0]);
				gift.setGiftDetail(this.splitString(gift.getItemDetail())[1].replaceAll(((char)10)+"", "<br>"));
				
				itemIdStr += comma + gift.getId();
				comma = ",";
				
			}
			
			List<AttbDTO> giftAttbList = new ArrayList<AttbDTO>();
			
			try {
				giftAttbList = GiftDetailDao.getAttb(itemIdStr, locale);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				logger.error("Exception caught in setGiftAttbList()", e);
				e.printStackTrace();
			}
			
			if(giftAttbList!=null&&giftAttbList.size()>0){
				for(GiftUI gift:giftList){
					for(AttbDTO attb:giftAttbList){
						if(gift.getId().equalsIgnoreCase(attb.getItemId())){
							if(gift.getGiftAttbList()==null){
								gift.setGiftAttbList(new ArrayList<AttbDTO>());
								gift.getGiftAttbList().add(attb);
							}else{
								gift.getGiftAttbList().add(attb);
							}
						}
					}
				}
			}
			
			List<NowTVCampaignCdDTO> sophieSubList=new ArrayList<NowTVCampaignCdDTO>();
			
			try {
				sophieSubList = GiftDetailDao.getGiftSophieSub(itemIdStr);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				logger.error("Exception caught in getGiftSophieSub()", e);
				e.printStackTrace();
			}
			
			if(sophieSubList!=null&&sophieSubList.size()>0){
				for(GiftUI gift:giftList){
					for(NowTVCampaignCdDTO dto:sophieSubList){
						if(gift.getId().equalsIgnoreCase(dto.getItemId())){
							if(gift.getCampaignCdList()==null){
								gift.setCampaignCdList(new ArrayList<NowTVCampaignCdDTO>());
								gift.getCampaignCdList().add(dto);
							}else{
								gift.getCampaignCdList().add(dto);
							}
						}
					}
				}
			}
			
			List<VASDetailUI> dediVASList = new ArrayList<VASDetailUI>();
			
			try {
				dediVASList = GiftDetailDao.getDediVASList(itemIdStr);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				logger.error("Exception caught in getDediVASList()", e);
				e.printStackTrace();
			}
			
			if(dediVASList!=null&&dediVASList.size()>0){
				for(GiftUI gift:giftList){
					for(VASDetailUI vas:dediVASList){
						if(gift.getId().equalsIgnoreCase(vas.getGiftItemId())){
							if(gift.getDediVASList()==null){
								gift.setDediVASList(new ArrayList<VASDetailUI>());
								gift.getDediVASList().add(vas);
							}else{
								gift.getDediVASList().add(vas);
							}
						}
					}
				}
			}
			
			
			
			
		}
		
		return giftList;
		
	}
	
	//Tony promo gift
	public List<GiftUI> getPromoGiftList(String locale, String housingType, String SB, String channel, String technology, String like100Ind, String progOfferCd) throws Exception {
		List<GiftUI> promoGiftList=new ArrayList<GiftUI>();

		try {
			promoGiftList = GiftDetailDao.getPromoGiftList(locale, housingType, SB, channel, technology, like100Ind, progOfferCd);
		} catch (DAOException e) {
			throw new Exception(e.getMessage(), e);
		}

		return promoGiftList;
	}
	
	public HashMap<String, HashMap<String, Object>> getGiftSelectCnt(String channel,String locale){
		HashMap<String, HashMap<String, Object>> giftSelectCnt = new HashMap<String, HashMap<String, Object>>();
		
		try {
			giftSelectCnt = GiftDetailDao.getGiftSelectCnt(channel,locale);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return giftSelectCnt;
		
	}

	public boolean checkEligibleForNowTV(String basketId){
		
		try {
			return basketDetailsDao.checkEligibleForNowTV(basketId);
		} catch (DAOException e) {
			e.printStackTrace();
			return true;
		}
		
	}

	public ValidateOfferResultDTO validateOfferInBom(String[] inList) {
		
		ValidateOfferResultDTO result = new ValidateOfferResultDTO();
		
		try {
			result = basketDetailsBomDao.validateOffers(inList); 
		} catch (Exception e){
			
		}
		return result;
	}
	

	private String[] splitString(String pString) {
		
		if(pString!=null){
			String[] strArray = new String[2];
			int newlineInx = pString.indexOf((char)10+"");
			int newlineInx2 = pString.indexOf("<br/>");
			
			if(newlineInx>0){
				strArray[0] = pString.substring(0, newlineInx);
				strArray[1] = pString.substring(newlineInx+1);
			}
			else if (newlineInx2>0){
				strArray[0] = pString.substring(0, newlineInx2);
				strArray[1] = pString.substring(newlineInx2+5);
			}
			else {
				strArray[0] = pString;
				strArray[1] = "";
			}
			
			return strArray;
		}
		else return null;
	}

	public void setGiftDetailDao(GiftDetailDAO giftDetailDao) {
		GiftDetailDao = giftDetailDao;
	}

	public GiftDetailDAO getGiftDetailDao() {
		return GiftDetailDao;
	}
	
	public BasketDetailsBomDAO getBasketDetailsBomDao() {
		return basketDetailsBomDao;
	}

	public void setBasketDetailsBomDao(BasketDetailsBomDAO basketDetailsBomDao) {
		this.basketDetailsBomDao = basketDetailsBomDao;
	}	
	public List<SubscribedItemUI> addAutoTieItems(String pcdVasSelected){
		List<SubscribedItemUI> AutoTieItemList=new ArrayList<SubscribedItemUI>();
		
		try{
			logger.info("addAutoTieItems() is called in service");
			logger.info("pcdVasSelected:" + pcdVasSelected );
			AutoTieItemList = VASDetailDao.getAutoTieItemList(pcdVasSelected);
		}catch (DAOException de) {
			logger.error("Exception caught in getAutoTieItemList()", de);
			throw new AppRuntimeException(de);
		}
		
		return AutoTieItemList;
	}
	
	public List<VASDetailUI> getVasExclusiveGiftType (List<String> selectedVasList,List<String> selectedGiftList, String locale) throws DAOException{
		
		List<VASDetailUI>  list= VASDetailDao.getVasExclusiveGiftType(selectedVasList,selectedGiftList,locale);
		
		return list;
		
	}	
	
	public List<SubscribedItemUI> getVASAutoTieDummyGiftList(String pcdVasSelected){
		List<SubscribedItemUI> AutoTieItemList=new ArrayList<SubscribedItemUI>();
		
		try{
			logger.info("getVASAutoTieDummyGiftList() is called in service");
			logger.info("pcdVasSelected:" + pcdVasSelected );
			AutoTieItemList = VASDetailDao.getVASAutoTieDummyGiftList(pcdVasSelected);
		}catch (DAOException de) {
			logger.error("Exception caught in getVASAutoTieDummyGiftList()", de);
			throw new AppRuntimeException(de);
		}
		
		return AutoTieItemList;
	}


}
