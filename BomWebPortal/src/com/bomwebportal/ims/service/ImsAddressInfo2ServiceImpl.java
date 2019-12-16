package com.bomwebportal.ims.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

import com.bomwebportal.ims.dao.ImsAddressInfo2DAO;

@Transactional(readOnly=true)
public class ImsAddressInfo2ServiceImpl implements ImsAddressInfo2Service{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsAddressInfo2DAO imsAddressInfo2Dao;	

	public ImsAddressInfo2DAO getImsAddressInfo2Dao() {
		return imsAddressInfo2Dao;
	}
	public void setImsAddressInfo2Dao(ImsAddressInfo2DAO imsAddressInfo2Dao) {
		this.imsAddressInfo2Dao = imsAddressInfo2Dao;
	}
	

	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<String> getOsOrderSB(String sbNum, String floor, String unitNo){
		
		logger.debug("getOsOrderSB is called");
		List<String> osOrderList = new ArrayList<String>();
		
		try{			
			osOrderList = imsAddressInfo2Dao.getOsOrderSB(sbNum, floor, unitNo);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return osOrderList;
	}

}
