
package com.bomwebportal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.ServiceSelectionDAO;
import com.bomwebportal.dto.*;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

@Transactional(readOnly=true)
public class ServiceSelectionServiceImpl implements ServiceSelectionService {
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ServiceSelectionDAO serviceSelectionDao;
	public ServiceSelectionDAO getServiceSelectionDao() {
		return serviceSelectionDao;
	}

	public void setServiceSelectionDao(ServiceSelectionDAO serviceSelectionDao) {
		this.serviceSelectionDao = serviceSelectionDao;
	}

	
	
	public List<String[]> getSelectList(String locale, String type){
		List<String[]> brandList=new ArrayList<String[]>();
    	
		try{
			logger.info("getBrandList() is called in brand service");
			brandList= serviceSelectionDao.getSelectList(locale, type);
		}catch (DAOException de) {
			logger.error("Exception caught in getBrandList()", de);
			throw new AppRuntimeException(de);
		}
		
		return brandList;
	}

	
	
	public List<String[]> getCallList(String appDate, String userName) {
		List<String[]> callList = new ArrayList<String[]>();
		
		try {
			logger.info("getCallList() is called in brand service");
			callList = serviceSelectionDao.getCallList(appDate, userName);
		}catch (DAOException de) {
			logger.error("Exception caught in getCallList()", de);
			throw new AppRuntimeException(de);
		}
		return callList;
	}

	public List<String[]> getRatePlan2(String jobList, String appDate) {
		
		List<String[]> ratePlanList = new ArrayList<String[]>();
		
		try {
			logger.info("getRatePlan2() is called in service selection");
			ratePlanList = serviceSelectionDao.getRatePlan2(jobList, appDate);
		}catch (DAOException de) {
			logger.error("Exception caught in getRatePlan2()", de);
			throw new AppRuntimeException(de);
		}
		
		return ratePlanList;
	}

	public List<String[]> getEligibilityCustomerTierList(String locale,
			List<EligibilityDTO> eligibilityDto, int channelId) {
		
		List<String[]> ectList = new ArrayList<String[]>();
		
		try {
			logger.info("getEligibilityCustomerTierList() is called in service selection");
			ectList = serviceSelectionDao.getEligibilityCustomerTierList(locale, eligibilityDto, channelId);
		}catch (DAOException de) {
			logger.error("Exception caught in getEligibilityCustomerTierList()", de);
			throw new AppRuntimeException(de);
		}
		
		return ectList;
	}
	
	
	public ChannalBasketDTO getChannelBasketStatus(String channelId, String basketId, String appDate) {
		ChannalBasketDTO dto= new ChannalBasketDTO();

		try {
			logger.info("getChannelBasketStatus() is called in service selection");
			dto.setChannelId(channelId);
			dto.setChannelBasketStatus( serviceSelectionDao.getChannelBasketStatus( channelId,  basketId,  appDate));
		}catch (DAOException de) {
			logger.error("Exception caught in getChannelBasketStatus()", de);
			throw new AppRuntimeException(de);
		}
		
		return dto;
		
	}
	
	public List<String[]> getEligibilityCustomerTierListByPeriorty(String locale,
			List<EligibilityDTO> eligibilityDto, int channelId) {
		
		List<String[]> ectList = new ArrayList<String[]>();
		
		try {
			logger.info("getEligibilityCustomerTierListByPeriorty() is called in service selection");
			ectList = serviceSelectionDao.getEligibilityCustomerTierListByPeriorty(locale, eligibilityDto, channelId);
		}catch (DAOException de) {
			logger.error("Exception caught in getEligibilityCustomerTierListByPeriorty()", de);
			throw new AppRuntimeException(de);
		}
		
		return ectList;
	}
	
	
}
