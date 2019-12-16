package com.bomltsportal.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.dao.CustomerDetailDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;

public class CustomerDetailServiceImpl implements CustomerDetailService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustomerDetailDAO customerDetailDao;
	

	public CustomerDetailProfileLtsDTO getLtsCustomerDetailByDocId(String pDocId, String pDocType) {
		
		try {
			return this.customerDetailDao.getLtsCustomerDetailByDocId(pDocId, pDocType);
		} catch (DAOException de) {
			logger.error("Fail in CustomerDetailService.getLtsCustomerDetailByDocId()", de);
			throw new AppRuntimeException(de);
		}
	}

	public CustomerDetailDAO getCustomerDetailDao() {
		return customerDetailDao;
	}

	public void setCustomerDetailDao(CustomerDetailDAO customerDetailDao) {
		this.customerDetailDao = customerDetailDao;
	}
}
