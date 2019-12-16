package com.bomwebportal.lts.service.order;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.order.CallPlanLtsDAO;
import com.bomwebportal.lts.dto.profile.IddCallPlanProfileLtsDTO;

public class CallPlanLtsServiceImpl implements CallPlanLtsService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private CallPlanLtsDAO callPlanLtsDao;
	
	public String[] getCallPlanCd (String itemId) {
		try {
			return this.callPlanLtsDao.getCallPlanCd(itemId);
		} catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException("Fail to getCallPlanCd.", e);
		}
	}
	
	public IddCallPlanProfileLtsDTO[] mapIddCallPlan(String[] pIddCallPlanCds) {
		try {
			return this.callPlanLtsDao.getIddCallPlan(pIddCallPlanCds);
		} catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException("Fail to map IDD call plan.", e);
		}
	}

	public String getCallPlanType (String callPlanCd) {
		try {
			return this.callPlanLtsDao.getCallPlanType(callPlanCd);
		} catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException("Fail to getCallPlanType.", e);
		}
	}
	
	public String getCallPlanContractPeriod(String callPlanCd) {
		try {
			return this.callPlanLtsDao.getCallPlanContractPeriod(callPlanCd);
		} catch (DAOException e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException("Fail to getCallPlanContractPeriod.", e);
		}
	}
	
	public CallPlanLtsDAO getCallPlanLtsDao() {
		return callPlanLtsDao;
	}

	public void setCallPlanLtsDao(CallPlanLtsDAO callPlanLtsDao) {
		this.callPlanLtsDao = callPlanLtsDao;
	}
}
