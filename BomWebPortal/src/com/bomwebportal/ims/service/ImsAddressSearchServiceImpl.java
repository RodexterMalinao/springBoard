package com.bomwebportal.ims.service;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

import com.bomwebportal.ims.dao.ImsAddressSearchDAO;
import com.bomwebportal.ims.dto.ImsAddressDTO;


@Transactional(readOnly=true)
public class ImsAddressSearchServiceImpl implements ImsAddressSearchService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsAddressSearchDAO imsAddressSearchDao;	

	public ImsAddressSearchDAO getImsAddressSearchDao() {
		return imsAddressSearchDao;
	}
	public void setImsAddressSearchDao(ImsAddressSearchDAO imsAddressSearchDao) {
		this.imsAddressSearchDao = imsAddressSearchDao;
	}
	

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsAddressDTO> getAddressBySB(String[] sbList){
		
		logger.debug("getFlatFloorBySB is called");
		List<ImsAddressDTO> addressSearchList = new ArrayList<ImsAddressDTO>();;
		
		try{			
			addressSearchList = imsAddressSearchDao.getFlatFloorBySB(sbList);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		logger.debug("output count=" + addressSearchList.size());
		return addressSearchList;
		
	}

}
