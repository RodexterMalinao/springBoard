package com.bomwebportal.ims.service;

import org.springframework.transaction.annotation.Transactional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.BasketSelectDAO;
import com.bomwebportal.ims.dao.ImsAddressInfoDAO;
import com.bomwebportal.ims.dto.BandwidthDTO;
import com.bomwebportal.ims.dto.HousingTypeDTO;
import com.bomwebportal.ims.dto.ImsBasketDTO;
import com.google.gson.Gson;

@Transactional(readOnly=true)
public class ImsBasketSelectServiceImpl implements ImsBasketSelectService {
	
	private BasketSelectDAO basketSelectDao;
	
	private ImsAddressInfoDAO imsAddressInfoDao;


	protected final Log logger = LogFactory.getLog(getClass());
	
	public List<ImsBasketDTO> getImsBasketList(String locale,
											   String bandwidthStr,
											   String housingTypeStr,
											   String technologyStr,
											   String IsPon,
											   String appDate,
											   String Sales_channel,
											   String teamCd,
											   Boolean sbFilterVas,
											   String mobileOfferInd,
											   String sbNo,
											   String ltsServiceInd,
											   String channelTeamCd,
											   String staffGroup,
											   String serviceCdStr
											   )
	{
		List<ImsBasketDTO> ImsbasketList=new ArrayList<ImsBasketDTO>();
		try{
			logger.info("getImsBasketList() is called in  service");
			ImsbasketList= basketSelectDao.getImsBasketList(locale,
															bandwidthStr,
															housingTypeStr,
															technologyStr,
															IsPon,
															appDate,
															Sales_channel,
															teamCd,
															sbFilterVas,
															mobileOfferInd,
															sbNo,
															ltsServiceInd,
															serviceCdStr,
															channelTeamCd,
															staffGroup
															);
		}catch (DAOException de) {
			logger.error("Exception caught in getImsBasketList()", de);
			throw new AppRuntimeException(de);
		}
		
		return ImsbasketList;
		
	}
	
	public List<String> getImsPlanTypeList(String locale) {
		List<String> ImsPlanTypeList = new ArrayList<String>();
		try {
			logger.info("getImsPlanTypeList() is called in  service");
			ImsPlanTypeList = basketSelectDao
					.getImsPlanTypeList(locale);
		} catch (DAOException de) {
			logger.error("Exception caught in getImsBasketList()", de);
			throw new AppRuntimeException(de);
		}

		return ImsPlanTypeList;

	}
	
	public List<String> getImsPreTickPlanTypeList(String locale) {
		List<String> ImsPlanTypeList = new ArrayList<String>();
		try {
			logger.info("getImsPlanTypeList() is called in  service");
			ImsPlanTypeList = basketSelectDao
					.getImsPreTickPlanTypeList(locale);
		} catch (DAOException de) {
			logger.error("Exception caught in getImsBasketList()", de);
			throw new AppRuntimeException(de);
		}

		return ImsPlanTypeList;

	}
	public List<String> getServiceCodeList(String sb) {
		List<String> serviceCodeList = new ArrayList<String>();
		try {
			logger.info("getServiceCodeList() is called in  service");
			serviceCodeList = imsAddressInfoDao
					.getServiceCodeList(sb);
		} catch (DAOException de) {
			logger.error("Exception caught in getServiceCodeList()", de);
			throw new AppRuntimeException(de);
		}

		return serviceCodeList;

	}
	
	public String getStaffGroup(String staffId) {
		String staffGroup = "";
		try {
			logger.debug("staffGroup() is called in  service");
			staffGroup = basketSelectDao
					.getStaffGroup(staffId);
		} catch (DAOException de) {
			logger.error("Exception caught in staffGroup()", de);
			throw new AppRuntimeException(de);
		}

		return staffGroup;

	}
	
	public BasketSelectDAO getBasketSelectDao() {
		return basketSelectDao;
	}

	public void setBasketSelectDao(BasketSelectDAO basketSelectDao) {
		this.basketSelectDao = basketSelectDao;
	}

	
	public ImsAddressInfoDAO getImsAddressInfoDao() {
		return imsAddressInfoDao;
	}

	public void setImsAddressInfoDao(ImsAddressInfoDAO imsAddressInfoDao) {
		this.imsAddressInfoDao = imsAddressInfoDao;
	}
}
