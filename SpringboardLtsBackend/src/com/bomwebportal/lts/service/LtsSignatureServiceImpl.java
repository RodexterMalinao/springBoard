package com.bomwebportal.lts.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.OrderSignatureDAO;

public class LtsSignatureServiceImpl implements LtsSignatureService {
	
	protected final Log logger = LogFactory.getLog(getClass());
		
	private OrderSignatureDAO orderSignatureDao;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteOrderSignature(String orderId, String signType) {
		try {
			orderSignatureDao.deleteOrderSignature(orderId, signType);
		}
		catch (DAOException de) {
			logger.error("Fail in LtsSignatureServiceImpl.deleteOrderSignature", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	/**
	 * @return the orderSignatureDao
	 */
	public OrderSignatureDAO getOrderSignatureDao() {
		return orderSignatureDao;
	}

	/**
	 * @param orderSignatureDao the orderSignatureDao to set
	 */
	public void setOrderSignatureDao(OrderSignatureDAO orderSignatureDao) {
		this.orderSignatureDao = orderSignatureDao;
	}
	
}
