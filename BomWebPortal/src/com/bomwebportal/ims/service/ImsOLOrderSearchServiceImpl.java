package com.bomwebportal.ims.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ImsOLOrderSearchDAO;
import com.bomwebportal.ims.dto.ImsCustomerOrderHistoryDTO;

public class ImsOLOrderSearchServiceImpl implements ImsOLOrderSearchService{

	protected final Log logger = LogFactory.getLog(getClass());

	private ImsOLOrderSearchDAO imsOrderSearchDao;

	public List<OrderDTO> getOrderList(String shopCode, String lob,
			String orderStatus, String startDate, String endDate,
			String saleCd, String orderIdStr) {
		try {
			return imsOrderSearchDao.getOrderList(shopCode, lob, orderStatus, startDate, endDate, saleCd, orderIdStr);

		} catch (DAOException de) {
			logger.error("Exception caught in getOrderList()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public List<ImsCustomerOrderHistoryDTO> getCustomerOrderHistoryDTOALL(String idDocNum, String serviceNum, String idDocType, 
			String loginId, String serviceType, String emailAddress, String orderId, String orderType, String contactNum){
		try {
			return imsOrderSearchDao.getCustomerOrderHistoryDTOALL(idDocNum, serviceNum, idDocType, 
					loginId, serviceType, emailAddress, 
					orderId, orderType, contactNum);

		} catch (DAOException de) {
			logger.error("Exception caught in getCustomerProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public ImsOLOrderSearchDAO getImsOrderSearchDao() {
		return imsOrderSearchDao;
	}

	public void setImsOrderSearchDao(ImsOLOrderSearchDAO imsOrderSearchDao) {
		this.imsOrderSearchDao = imsOrderSearchDao;
	}

	public boolean IsSaleResendEmailAllowed(String SalesCategory) {
		
		try {
			return imsOrderSearchDao.IsSaleResendEmailAllowed(SalesCategory);
		} catch (DAOException e) {
			logger.error("Exception caught in IsSaleResendEmailAllowed()", e);
			throw new AppRuntimeException(e);
		}
		
		
	}
}
