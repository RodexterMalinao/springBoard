package com.bomwebportal.lts.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.DsServiceLtsDAO;

public class DsServiceLtsServiceImpl implements DsServiceLtsService{

	private DsServiceLtsDAO dsServiceLtsDAO;
	private final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public void updateWaiveQcApprovalStatus(String orderId, String pUser,
			String status) throws DAOException {
		try{
			this.dsServiceLtsDAO.updateWaiveQcApprovalStatus(orderId, pUser, status);
		} catch (DAOException de) {
			logger.error("Fail in DsServiceLtsService.updateWaiveQcApprovalStatus()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	@Override
	public void updateDsAmendCancelApprovalStatus(String orderId, String pUser,
			String status) throws DAOException {
		try{
			this.dsServiceLtsDAO.updateDsAmendCancelApprovalStatus(orderId, pUser, status);
		} catch (DAOException de) {
			logger.error("Fail in DsServiceLtsService.updateDsAmendCancelApprovalStatus()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	@Override
	public void updateDsNameNotMatchApprovalStatus(String orderId, String pUser,
			String status) throws DAOException {
		try{
			this.dsServiceLtsDAO.updateDsNameNotMatchApprovalStatus(orderId, pUser, status);
		} catch (DAOException de) {
			logger.error("Fail in DsServiceLtsService.updateDsNameNotMatchApprovalStatus()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public DsServiceLtsDAO getDsServiceLtsDAO() {
		return dsServiceLtsDAO;
	}

	public void setDsServiceLtsDAO(DsServiceLtsDAO dsServiceLtsDAO) {
		this.dsServiceLtsDAO = dsServiceLtsDAO;
	}

}
