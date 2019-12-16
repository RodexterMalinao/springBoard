package com.bomwebportal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.QuotaPlanInfoDAO;
import com.bomwebportal.dto.MobBuoQuotaDTO;
import com.bomwebportal.dto.QuotaPlanInfoDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

public class QuotaPlanInfoServiceImpl implements QuotaPlanInfoService {

	private QuotaPlanInfoDAO quotaPlanInfoDAO;

	public QuotaPlanInfoDAO getQuotaPlanInfoDAO() {
		return quotaPlanInfoDAO;
	}

	public void setQuotaPlanInfoDAO(QuotaPlanInfoDAO quotaPlanInfoDAO) {
		this.quotaPlanInfoDAO = quotaPlanInfoDAO;
	}

	protected final Log logger = LogFactory.getLog(getClass());

	public List<QuotaPlanInfoDTO> getQuotaPlanOptions(String itemId, String appDate) {
		try {
			return quotaPlanInfoDAO.getQuotaPlanOptions(itemId, appDate);
		} catch (DAOException e) {
			logger.error("Exception caught in getting quotaPlanOptions()", e);
			throw new AppRuntimeException(e);
		}

	}

	public List<MobBuoQuotaDTO> getBomWebBuoQuota(String orderId) {

		try {
			return quotaPlanInfoDAO.getBomWebBuoQuota(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getting quotaPlan()", e);
			throw new AppRuntimeException(e);
		}
	}
}
