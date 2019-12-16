package com.bomwebportal.lts.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dto.ImsAddressDTO;
import com.bomwebportal.lts.dao.LtsAddressSearchDAO;


@Transactional(readOnly=true)
public class LtsAddressSearchServiceImpl implements LtsAddressSearchService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LtsAddressSearchDAO ltsAddressSearchDao;	

	public LtsAddressSearchDAO getLtsAddressSearchDao() {
		return ltsAddressSearchDao;
	}

	public void setLtsAddressSearchDao(LtsAddressSearchDAO ltsAddressSearchDao) {
		this.ltsAddressSearchDao = ltsAddressSearchDao;
	}

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<ImsAddressDTO> getAddressBySB(String[] sbList, boolean includeLtsOnly){
		
		logger.info("getFlatFloorBySB is called");
		List<ImsAddressDTO> addressSearchList = new ArrayList<ImsAddressDTO>();;
		
		try{			
			addressSearchList = ltsAddressSearchDao.getFlatFloorBySB(sbList, includeLtsOnly);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		logger.debug("output count=" + addressSearchList.size());
		return addressSearchList;
		
	}

}
