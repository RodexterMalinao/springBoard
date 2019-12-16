package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderRemarkDAO;
import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;

@Transactional(readOnly=true)
public class MobCcsOrderRemarkServiceImpl implements MobCcsOrderRemarkService {
	private Log logger = LogFactory.getLog(this.getClass());
	
	private MobCcsOrderRemarkDAO mobCcsOrderRemarkDAO;

	public MobCcsOrderRemarkDAO getMobCcsOrderRemarkDAO() {
		return mobCcsOrderRemarkDAO;
	}

	public void setMobCcsOrderRemarkDAO(MobCcsOrderRemarkDAO mobCcsOrderRemarkDAO) {
		this.mobCcsOrderRemarkDAO = mobCcsOrderRemarkDAO;
	}

	public OrderRemarkDTO getOrderRemarkDTO(String rowId) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getOrderRemarkDTO() is called @ MobCcsOrderRemarkServiceImpl");
			}
			return this.mobCcsOrderRemarkDAO.getOrderRemarkDTO(rowId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderRemarkDTO()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<OrderRemarkDTO> getOrderRemarkDTOALL(String orderId) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getOrderRemarkDTOALL() is called @ MobCcsOrderRemarkServiceImpl");
			}
			return this.mobCcsOrderRemarkDAO.getOrderRemarkDTOALL(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOrderRemarkDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	public List<OrderRemarkDTO> getPTOrderRemarkDTO(String orderId) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("getPTOrderRemarkDTO() is called @ MobCcsOrderRemarkServiceImpl");
			}
			return this.mobCcsOrderRemarkDAO.getPTOrderRemarkDTO(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getPTOrderRemarkDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrderRemarkDTO(OrderRemarkDTO dto) {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("insertOrderRemarkDTO() is called @ MobCcsOrderRemarkServiceImpl");
			}
			return this.mobCcsOrderRemarkDAO.insertOrderRemarkDTO(dto);
		} catch (DAOException de) {
			logger.error("Exception caught in insertOrderRemarkDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrderRemark(String userName,String orderId, String message) {
		try {
			OrderRemarkDTO orderRemarkDTO = new OrderRemarkDTO(); //add by wilson 20120402
		    Date now = new Date();
			orderRemarkDTO.setOrderId(orderId);
			orderRemarkDTO.setRemark(userName+" "+message);
			orderRemarkDTO.setCreateBy(userName);
			orderRemarkDTO.setCreateDate(now);
			orderRemarkDTO.setLastUpdBy(userName);
			orderRemarkDTO.setLastUpdDate(now);
			//orderRemarkDTO.setType("S");
			if (logger.isInfoEnabled()) {
				logger.info("insertOrderRemarkDTO() is called @ MobCcsOrderRemarkServiceImpl");
			}
			return this.mobCcsOrderRemarkDAO.insertOrderRemarkDTO(orderRemarkDTO);
		} catch (DAOException de) {
			logger.error("Exception caught in insertOrderRemarkDTO()", de);
			throw new AppRuntimeException(de);
		}
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertPTOrderRemark(String userName,String orderId, String message) {
		try {
			OrderRemarkDTO orderRemarkDTO = new OrderRemarkDTO(); //add by wilson 20120402
		    Date now = new Date();
			orderRemarkDTO.setOrderId(orderId);
			orderRemarkDTO.setRemark(message);
			orderRemarkDTO.setCreateBy(userName);
			orderRemarkDTO.setCreateDate(now);
			orderRemarkDTO.setLastUpdBy(userName);
			orderRemarkDTO.setLastUpdDate(now);
			orderRemarkDTO.setType("M");
			if (logger.isInfoEnabled()) {
				logger.info("insertPTOrderRemark() is called @ MobCcsOrderRemarkServiceImpl");
			}
			return this.mobCcsOrderRemarkDAO.insertOrderRemarkDTO(orderRemarkDTO);
		} catch (DAOException de) {
			logger.error("Exception caught in insertPTOrderRemark()", de);
			throw new AppRuntimeException(de);
		}
	}
}
