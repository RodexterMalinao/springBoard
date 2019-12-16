package com.bomwebportal.lts.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.BankCodeLkupDAO;

public class PaymentServiceImpl implements PaymentService {

	private BankCodeLkupDAO paymentDao = null;
	  
	protected final Log logger = LogFactory.getLog(getClass());

	public String getBankNameByCode(String pBankCd) {
		
		try {
			return paymentDao.getBankNameByCode(pBankCd);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getBranchNameByCode(String pBankCd, String pBranchCd) {
		
		try {
			return paymentDao.getBranchNameByCode(pBankCd, pBranchCd);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public BankCodeLkupDAO getPaymentDao() {
		return paymentDao;
	}

	public void setPaymentDao(BankCodeLkupDAO paymentDao) {
		this.paymentDao = paymentDao;
	}
}
