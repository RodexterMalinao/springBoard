package com.bomwebportal.mob.cos.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.cos.dao.MobCosCopDAO;
import com.bomwebportal.mob.cos.dto.MobCosCopDTO;


public class MobCosCopServiceImpl implements MobCosCopService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	private MobCosCopDAO mobCosCopDAO;
	
	public MobCosCopDAO getMobCosCopDAO() {
		return mobCosCopDAO;
	}

	public void setMobCosCopDAO(MobCosCopDAO mobCosCopDAO) {
		this.mobCosCopDAO = mobCosCopDAO;
	}

	public MobCosCopDTO getCosCopProcessOrderDetail(String orderId) {
		try {
			logger.info("getCosCopProcessOrderDetail() is called in MobCosCopServiceImpl");
			return mobCosCopDAO.getCosCopProcessOrderDetail(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getCosCopProcessOrderDetail()", de);
			throw new AppRuntimeException(de);
		}
	}

}
