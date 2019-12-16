package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.ImsSupportDocDAO;


@Transactional(readOnly = true)
public class ImsSupportDocServiceImpl implements ImsSupportDocService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsSupportDocDAO imsSupportDocDAO;		
	
	public ImsSupportDocDAO getImsSupportDocDAO() {
		return imsSupportDocDAO;
	}

	public void setImsSupportDocDAO(ImsSupportDocDAO imsSupportDocDAO) {
		this.imsSupportDocDAO = imsSupportDocDAO;
	}
	
	
	public String isSupportDocCollected(String orderId, String docType){
		
		String isCollected = "N";
		
		try {
			isCollected = imsSupportDocDAO.isSupportDocCollected(orderId, docType);
			return isCollected;
		} catch (DAOException e) {
			logger.error("Exception catched in isSupportDocCollected()");
			return "N";
		}
		
	}
	
}
