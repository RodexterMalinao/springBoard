package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.ims.dao.AddressIndex2DAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;


@Transactional(readOnly=true)
public class AddressIndex2ServiceImpl implements AddressIndex2Service{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private AddressIndex2DAO addressIndex2Dao;	

	public AddressIndex2DAO getAddressIndex2Dao() {
		return addressIndex2Dao;
	}
	public void setAddressIndex2Dao(AddressIndex2DAO addressIndex2Dao) {
		this.addressIndex2Dao = addressIndex2Dao;
	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public String validIPAddress(String ipAddress){
		
		logger.info("validIPAddress is called");
		
		try{
			return addressIndex2Dao.validIPAddress(ipAddress);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}
	}
	
}
