package com.bomltsportal.service.email;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomltsportal.dao.email.OrderDAO;
import com.bomltsportal.dto.email.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

	protected final Log logger = LogFactory.getLog(getClass());
	private OrderDAO orderDao;
	
	public OrderDAO getOrderDao() {
		return orderDao;
	}

	public void setOrderDao(OrderDAO orderDao) {
		this.orderDao = orderDao;
	}

	public OrderDTO getOrder (String orderId )
	{
		try {
			return orderDao.getOrder(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getOrder()", de);
			throw new AppRuntimeException(de);
		}

	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int updateEsignEmailAddr(String orderId, String esigEmailAddr, String lastUpdateBy) {
		try {
			return orderDao.updateEsigEmailAddr(orderId, esigEmailAddr, lastUpdateBy);
		} catch (DAOException de) {
			logger.error("Exception caught in updateEsignEmailAddr()", de);
			throw new AppRuntimeException(de);
		}
	}
}
