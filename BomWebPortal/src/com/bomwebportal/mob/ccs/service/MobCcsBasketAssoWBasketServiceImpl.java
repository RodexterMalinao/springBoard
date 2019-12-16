package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsBasketAssoWBasketDAO;
import com.bomwebportal.mob.ccs.dto.BasketAssoWBasketDTO;

@Transactional(readOnly=true)
public class MobCcsBasketAssoWBasketServiceImpl implements
		MobCcsBasketAssoWBasketService {
	private Log logger = LogFactory.getLog(this.getClass());
	private MobCcsBasketAssoWBasketDAO mobCcsBasketAssoWBasketDAO;
	
	public MobCcsBasketAssoWBasketDAO getMobCcsBasketAssoWBasketDAO() {
		return mobCcsBasketAssoWBasketDAO;
	}
	public void setMobCcsBasketAssoWBasketDAO(
			MobCcsBasketAssoWBasketDAO mobCcsBasketAssoWBasketDAO) {
		this.mobCcsBasketAssoWBasketDAO = mobCcsBasketAssoWBasketDAO;
	}
	
	public BasketAssoWBasketDTO getMobCcsBasketAssoWBasketDTO(String basketId) {
		try {
			logger.info("getMobCcsBasketAssoWBasketDTO() is called @ MobCcsBasketAssoWBasketServiceImpl");
			return this.mobCcsBasketAssoWBasketDAO.getWBasket(basketId);
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsBasketAssoWBasketDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	public List<BasketAssoWBasketDTO> getMobCcsBasketAssoWBasketDTOALL() {
		try {
			logger.info("getMobCcsBasketAssoWBasketDTOALL() is called @ MobCcsBasketAssoWBasketServiceImpl");
			return this.mobCcsBasketAssoWBasketDAO.getWBasketALL();
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsBasketAssoWBasketDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}
}
