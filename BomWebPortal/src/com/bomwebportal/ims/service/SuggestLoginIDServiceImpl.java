package com.bomwebportal.ims.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.SuggestLoginIDDAO;
import com.bomwebportal.ims.dto.ui.ImsInstallationUI;

@Transactional(readOnly=true)
public class SuggestLoginIDServiceImpl implements SuggestLoginIDService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private SuggestLoginIDDAO suggestLoginIDDao;

	public SuggestLoginIDDAO getSuggestLoginIDDao() {
		return suggestLoginIDDao;
	}

	public void setSuggestLoginIDDao(SuggestLoginIDDAO suggestLoginIDDao) {
		this.suggestLoginIDDao = suggestLoginIDDao;
	}
	
	public ImsInstallationUI suggestLoginID(String loginName){
		
		logger.info("suggestLoginID["+loginName+"]");
		ImsInstallationUI result = new ImsInstallationUI();
		
		try{			
			result = suggestLoginIDDao.suggestLoginID(loginName);
		}catch (DAOException de) {			
			throw new AppRuntimeException(de);
		}

		return result;
	}

}
