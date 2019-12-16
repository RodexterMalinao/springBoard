package com.bomwebportal.sbo.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.sbo.dao.SboOrderDAO;
import com.bomwebportal.sbo.dto.SboMobOrderDTO;

public class SboOrderServiceImpl implements SboOrderService {
	protected final Log logger = LogFactory.getLog(getClass());

	private SboOrderDAO sboOrderDAO;
	
	
	public SboOrderDAO getSboOrderDAO() {
		return sboOrderDAO;
	}


	public void setSboOrderDAO(SboOrderDAO sboOrderDAO) {
		this.sboOrderDAO = sboOrderDAO;
	}


	public List<SboMobOrderDTO> findSboMobOrder(
			String orderId,
			String idDocType, String idDocNum,
			String srvNum,
			String contactEmail,
			String contactNum) {
		logger.info("findSboMobOrder()");
		
		try {
			return sboOrderDAO.findSboMobOrder(orderId, idDocType, idDocNum, srvNum, contactEmail, contactNum);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}

}
