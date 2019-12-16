package com.bomwebportal.mob.ccs.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsBomOrderDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsBomOrderInfoDAO;
import com.bomwebportal.mob.ccs.dto.BomOrderPposDTO;
import com.bomwebportal.mob.dao.bom.BomOrderPposDAO;

public class BomOrderPposServiceImpl implements BomOrderPposService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	BomOrderPposDAO bomOrderPposDAO;
	
	MobCcsBomOrderInfoDAO mobCcsBomOrderInfoDAO;
	
	MobCcsBomOrderDAO mobCcsBomOrderDAO;
	
	/**
	 * @return the mobCcsBomOrderDAO
	 */
	public MobCcsBomOrderDAO getMobCcsBomOrderDAO() {
		return mobCcsBomOrderDAO;
	}

	/**
	 * @param mobCcsBomOrderDAO the mobCcsBomOrderDAO to set
	 */
	public void setMobCcsBomOrderDAO(MobCcsBomOrderDAO mobCcsBomOrderDAO) {
		this.mobCcsBomOrderDAO = mobCcsBomOrderDAO;
	}

	/**
	 * @return the mobCcsBomOrderInfoDAO
	 */
	public MobCcsBomOrderInfoDAO getMobCcsBomOrderInfoDAO() {
		return mobCcsBomOrderInfoDAO;
	}

	/**
	 * @param mobCcsBomOrderInfoDAO the mobCcsBomOrderInfoDAO to set
	 */
	public void setMobCcsBomOrderInfoDAO(MobCcsBomOrderInfoDAO mobCcsBomOrderInfoDAO) {
		this.mobCcsBomOrderInfoDAO = mobCcsBomOrderInfoDAO;
	}

	/**
	 * @return the bomOrderPposDAO
	 */
	public BomOrderPposDAO getBomOrderPposDAO() {
		return bomOrderPposDAO;
	}

	/**
	 * @param bomOrderPposDAO the bomOrderPposDAO to set
	 */
	public void setBomOrderPposDAO(BomOrderPposDAO bomOrderPposDAO) {
		this.bomOrderPposDAO = bomOrderPposDAO;
	}

	public List<BomOrderPposDTO> getBomOrderPpos(String ccsMode) {
				
		try {
			return bomOrderPposDAO.getBomOrderPpos(ccsMode);
		} catch (DAOException e) {
			logger.error("Exception caught in getBomOrderPpos()", e);
		}
		return null;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBomOrderInfo(BomOrderPposDTO dto) {
		try {
			mobCcsBomOrderInfoDAO.updateBomOrderInfo(dto);
		} catch (DAOException e) {
			logger.error("Exception caught in getOcidStatus()", e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertBomOrderInfo(BomOrderPposDTO dto) {
		try {
			mobCcsBomOrderInfoDAO.insertBomOrderInfo(dto);
		} catch (DAOException e) {
			logger.error("Exception caught in getOcidStatus()", e);
		}
	}

	public boolean isBomOrderPposInfoExist(String ocid) {
		try {
			return mobCcsBomOrderInfoDAO.isBomOrderPposInfoExist(ocid);
		} catch (DAOException e) {
			logger.error("Exception caught in isBomOrderPposInfoExis()", e);
			throw new AppRuntimeException(e);
		}
	}

	public boolean isBomOrderPposExist(String orderId) {
		try {
			return mobCcsBomOrderDAO.isBomOrderPposExist(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in isBomOrderPposExist()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<BomOrderPposDTO> getUpdatedBomOrder(String ccsMode) {
		try {
			return bomOrderPposDAO.getUpdatedBomOrder(ccsMode);
		} catch (DAOException e) {
			logger.error("Exception caught in getUpdatedBomOrder()", e);
		}
		return null;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateBomOrder(BomOrderPposDTO dto) {
		try {
			mobCcsBomOrderDAO.updateBomOrder(dto);
		} catch (DAOException e) {
			logger.error("Exception caught in updateBomOrder()", e);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertBomOrder(BomOrderPposDTO dto) {
		try {
			mobCcsBomOrderDAO.insertBomOrder(dto);
		} catch (DAOException e) {
			logger.error("Exception caught in insertBomOrder()", e);
		}
	}

	public String getEnvironmentCcsMode(String mode) {
		try {
			return mobCcsBomOrderDAO.getEnvironmentCcsMode(mode);
		} catch (DAOException e) {
			logger.error("Exception caught in insertBomOrder()", e);
		}
		return null;
	}

	public Map<String, BomOrderPposDTO> getBomOrderPposMap(
			List<String> orderIdList) {
		
		List<BomOrderPposDTO> bomList = new ArrayList<BomOrderPposDTO>();
		Map<String, BomOrderPposDTO> bomMap = new HashMap<String, BomOrderPposDTO>();
		
		try {
			bomList = mobCcsBomOrderDAO.getBomOrderPposList(orderIdList);
		} catch (DAOException e) {
			logger.error("Exception caught in insertBomOrder()", e);
		}
		
		for (BomOrderPposDTO dto : bomList) {
			bomMap.put(dto.getOrderId(), dto);
		}
		
		return bomMap;
	}

	public List<String> getCrossBomAndMobOrderStatus(String mobOrderSts,
			String bomOrderSts) {
		try {
			return mobCcsBomOrderDAO.getCrossBomAndMobOrderStatus(mobOrderSts, bomOrderSts);
		} catch (DAOException e) {
			logger.error("Exception caught in getCrossBomAndMobOrderStatus()", e);
		}
		
		return null;
	}
	
	public List<BomOrderPposDTO> getRetailShopBomOrderStatus(String todayString) {
		try {
			return bomOrderPposDAO.getYesterdayRetailShopBomOrderStatus(todayString);
		} catch (DAOException e) {
			logger.error("Exception caught in getRetailShopBomOrderStatus()", e);
		}
		return null;
	}

}
