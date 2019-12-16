package com.bomwebportal.lts.service.acq;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.CustomerProfileLtsDAO;
import com.bomwebportal.lts.dto.profile.AccountDetailProfileAcqDTO;

public class LtsAcqAccountProfileServiceImpl implements LtsAcqAccountProfileService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private CustomerProfileLtsDAO customerProfileLtsDao;
	
	
	public CustomerProfileLtsDAO getCustomerProfileLtsDao() {
		return customerProfileLtsDao;
	}

	public void setCustomerProfileLtsDao(CustomerProfileLtsDAO customerProfileLtsDao) {
		this.customerProfileLtsDao = customerProfileLtsDao;
	}


	public List<AccountDetailProfileAcqDTO> getAcctListByCustNum(String pCustNum) {
		try {
			return this.customerProfileLtsDao.getAcctListByCustNum(pCustNum);
		} catch (DAOException de) {
			logger.error("Fail in CustomerProfileLtsService.getAcctListByCustNum()", de);
			throw new AppRuntimeException(de);
		}
	}
	

}
