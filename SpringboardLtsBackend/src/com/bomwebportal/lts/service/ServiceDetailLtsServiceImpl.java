package com.bomwebportal.lts.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.ServiceDetailLtsDAO;

public class ServiceDetailLtsServiceImpl implements ServiceDetailLtsService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ServiceDetailLtsDAO serviceDetailLtsDAO;

	public void updateDnStatus(String serviceInventSts, String orderId, String dtlId, String user) {		
		try {
			serviceDetailLtsDAO.updateDnStatus(serviceInventSts, orderId, dtlId, user);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
	
	public void updateGatewayNum(String gatewayNum, String orderId, String dtlId, String user) {		
		try {
			serviceDetailLtsDAO.updateGatewayNum(gatewayNum, orderId, dtlId, user);
		} catch (DAOException de) {
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	/**
	 * @return the serviceDetailLtsDAO
	 */
	public ServiceDetailLtsDAO getServiceDetailLtsDAO() {
		return serviceDetailLtsDAO;
	}

	/**
	 * @param serviceDetailLtsDAO the serviceDetailLtsDAO to set
	 */
	public void setServiceDetailLtsDAO(ServiceDetailLtsDAO serviceDetailLtsDAO) {
		this.serviceDetailLtsDAO = serviceDetailLtsDAO;
	}

}
