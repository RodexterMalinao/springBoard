package com.bomltsportal.service;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomltsportal.dao.OnlineSalesRequestDAO;
import com.bomltsportal.dto.OnlineSalesRequestDTO;
import com.bomwebportal.exception.AppRuntimeException;

public class OnlineSalesRequestServiceImpl implements OnlineSalesRequestService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private OnlineSalesRequestDAO onlineSalesRequestDAO;
	
	public OnlineSalesRequestDAO getOnlineSalesRequestDAO() {
		return onlineSalesRequestDAO;
	}

	public void setOnlineSalesRequestDAO(OnlineSalesRequestDAO onlineSalesRequestDAO) {
		this.onlineSalesRequestDAO = onlineSalesRequestDAO;
	}

	public void insertOnlineSalesRequest(OnlineSalesRequestDTO onlineSalesRequest) {
		try {
			this.onlineSalesRequestDAO.insertOnlineSalesRequest(onlineSalesRequest);
		} catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	
	
}
