package com.bomwebportal.bean;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bomwebportal.dao.ConstantLkupDAO;

@Component("initTestConnectionBean")
public class InitTestConnectionBean implements InitializingBean {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private ConstantLkupDAO constantLkupDao;
	

	public ConstantLkupDAO getConstantLkupDao() {
		return constantLkupDao;
	}


	public void setConstantLkupDao(ConstantLkupDAO constantLkupDao) {
		this.constantLkupDao = constantLkupDao;
	}


	public void afterPropertiesSet() throws Exception {
		try {
			logger.info("Testing connection during initialization");
			constantLkupDao.testConnection();
			logger.info("Testing connection during initialization is done");
		} catch (Throwable e) {
			logger.error("Connection test failure !!! 'MAYBE' it is expected if during initialization when using UCP", e);
		}
	}
}
