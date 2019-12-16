package com.bomwebportal.lts.service.bom;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.BomOrderInfoDAO;
import com.bomwebportal.lts.dto.bom.OrderServiceDTO;

public class BomOrderInfoServiceImpl implements BomOrderInfoService {

	private final Log logger = LogFactory.getLog(getClass());
	
	private BomOrderInfoDAO bomOrderInfoDao = null;


	public OrderServiceDTO[] getLtsInstallPendingOrderByCust(String pCustNum, String pSystemId) {

		try {
			return bomOrderInfoDao.getLtsInstallPendingOrderByCust(pCustNum, pSystemId);
		} catch (DAOException de) {
			logger.error("Fail in BomOrderInfoService.getLtsInstallPendingOrderByCust()", de); 
			throw new AppRuntimeException(de);
		}
	}
	
	public BomOrderInfoDAO getBomOrderInfoDao() {
		return bomOrderInfoDao;
	}

	public void setBomOrderInfoDao(BomOrderInfoDAO bomOrderInfoDao) {
		this.bomOrderInfoDao = bomOrderInfoDao;
	}
}
