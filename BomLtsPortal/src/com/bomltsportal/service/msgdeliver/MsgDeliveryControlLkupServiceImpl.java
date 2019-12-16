package com.bomltsportal.service.msgdeliver;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomltsportal.dao.msgdeliver.MsgDeliveryControlLkupDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;

@Transactional(readOnly = true)
public class MsgDeliveryControlLkupServiceImpl implements MsgDeliveryControlLkupService {

	protected final Log logger = LogFactory.getLog(getClass());

	private MsgDeliveryControlLkupDAO constantLkupDao;

	public MsgDeliveryControlLkupDAO getConstantLkupDao() {
		return constantLkupDao;
	}

	public void setConstantLkupDao(MsgDeliveryControlLkupDAO constantLkupDao) {
		this.constantLkupDao = constantLkupDao;
	}

	public String getSendSMSorNot() {
		try{
			return constantLkupDao.getSendSMSorNot();
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}

	public String getSendEmailOrNot() {
		try{			
			return constantLkupDao.getSendEmailOrNot();
		}catch(DAOException de){
			logger.error(de);
			throw new AppRuntimeException(de);
		}
	}
}
