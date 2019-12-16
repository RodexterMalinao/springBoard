package com.bomwebportal.ims.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.ims.dao.PTDAO;

public class PTServiceImpl implements PTService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private PTDAO dao;

	public PTDAO getDao() {
		return dao;
	}

	public void setDao(PTDAO dao) {
		this.dao = dao;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int getSalesNum(String code)
	{
		try{
			return dao.getSalesNum(code);
		}catch (DAOException de) {
			logger.error("Exception caught in getSalesNum()", de);
			throw new AppRuntimeException(de);
		}	
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<Map<String, String>> getNowTVPTList()
	{
		try{
			return dao.getNowTVPTList();
		}catch (DAOException de) {
			logger.error("Exception caught in getNowTVPTList()", de);
			throw new AppRuntimeException(de);
		}	
	}
}
