package com.bomwebportal.sbs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsOrderDAO;
import com.bomwebportal.sbs.dao.SbsOrderDAO;
import com.bomwebportal.sbs.dto.SbsContactInfoDTO;
import com.bomwebportal.sbs.dto.SbsDeliveryInfoDTO;
import com.bomwebportal.sbs.dto.StOrderPosSmDTO;
import com.bomwebportal.sbs.dto.StSubscribedItemDTO;
import com.bomwebportal.sbs.dto.StSubscribedVkkItemDTO;
import com.bomwebportal.service.OrderService;

@Transactional
public class SbsOrderService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private SbsOrderDAO sbsOrderDAO;
	@Autowired
	private MobCcsOrderDAO mobCcsOrderDAO;
	@Autowired
	private OrderService orderService;
	
	
	public SbsContactInfoDTO getContactInfo(String orderId) {
		logger.debug("Getting SBS contactInfo for order: " + orderId);
		try {
			return sbsOrderDAO.getContactInfo(orderId);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public SbsDeliveryInfoDTO getDeliveryInfo(String orderId) {
		logger.debug("Getting SBS delieryInfo for order: " + orderId);
		try {
			return sbsOrderDAO.getDeliveryInfo(orderId);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public List<? extends StSubscribedItemDTO> getSubscribedItems(String orderId) {
		logger.debug("getSubscribedItems: " + orderId);
		try {
			return sbsOrderDAO.getSubscribedItems(orderId);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public StSubscribedVkkItemDTO getSubscribedVkkItem(String orderId) {
		List<? extends StSubscribedItemDTO> list = getSubscribedItems(orderId);
		if (CollectionUtils.isNotEmpty(list)) {
			for (StSubscribedItemDTO item: list) {
				if ( "SVAS".equals(item.getItemType()) ) {
					return (StSubscribedVkkItemDTO)item;
				}
			}
		}
		return null;
	}
	
	public void updateContactInfo(SbsContactInfoDTO dto) {
		logger.debug("Updating SBS contactInfo for order: " + dto.getOrderId());
		try {
			dto.setContactName(dto.getLastName() + " " + dto.getFirstName());
			
			sbsOrderDAO.updateBomwebCustomer(dto);
			sbsOrderDAO.updateBomwebContactAll(dto);
			orderService.updateEsignEmailAddr(dto.getOrderId(), dto.getEmailAddr(), dto.getLastUpdBy());
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public void updateDeliveryInfo(SbsDeliveryInfoDTO dto) {
		logger.debug("Updating SBS deliveryInfo for order: " + dto.getOrderId());
		try {
			sbsOrderDAO.updateBomwebCustAddrAll(dto);
			sbsOrderDAO.updateBomwebDelivery(dto);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public List<StOrderPosSmDTO> getOrderPosSms(String orderId) {
		logger.debug("getOrderPosSms: " + orderId);
		try {
			return sbsOrderDAO.getOrderPosSms(orderId);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public int insertStOrderPosSm(StOrderPosSmDTO dto) {
		logger.debug("insertStOrderPosSm() ");
		try {
			return sbsOrderDAO.insertStOrderPosSm(dto);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	
	
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrderRemark(String orderId, String remark, String createBy) {
		
		try {
			return sbsOrderDAO.insertOrderRemark(orderId, remark, createBy);
			
		} catch (DAOException de) {
			logger.error("Exception caught in insertOrderRemark()", de);
			throw new AppRuntimeException(de);
		}
		
	}	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateOrderStatus(String orderId, String status, String updateBy) {
		try {
			sbsOrderDAO.updateOrderStatus(orderId, status, updateBy);		
		} catch (DAOException de) {
			logger.error("Exception caught in updateOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateOrderStatus(String orderId, String status, String reasonCode, String updateBy) {
		try {
			sbsOrderDAO.updateOrderStatus(orderId, status, reasonCode, updateBy);
			
		} catch (DAOException de) {
			logger.error("Exception caught in updateOrderStatus()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void updateOrderCheckPoint(String orderId, String checkPoint, String updateBy) {
		try {
			sbsOrderDAO.updateOrderCheckPoint(orderId, checkPoint, updateBy);
			
		} catch (DAOException de) {
			logger.error("Exception caught in updateOrderCheckPoint()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public String releaseStOrderStockProcess(String orderId) {
		
		try {
			return sbsOrderDAO.releaseStOrderStockProcess(orderId);
			
		} catch (DAOException de) {
			logger.error("Exception caught in cancelOrderStockProcess()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void cancelOrder(String orderId, String reasonCode, String updateBy) {
		if (StringUtils.isEmpty(orderId)) return;
		try {

			String remark = updateBy + " CANCEL ORDER" ;//updateBy + " ONLINE SALES CANCEL ORDER " + desc;
			releaseStOrderStockProcess(orderId);
			updateOrderStatus(orderId, "04", updateBy);
			updateOrderCheckPoint(orderId, "000", updateBy);
			//mobCcsOrderDAO.cancelOrderStockProcess(orderId);
			//cancelOrderStockProcess(orderId);
			insertOrderRemark(orderId, remark, updateBy);
		} catch (AppRuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int changeDoaRequestStatus(String orderId, String toStatus, String fromStatus, String updateBy) {
		try {
			return sbsOrderDAO.changeDoaRequestStatus(orderId, toStatus, fromStatus, updateBy);
			
		} catch (DAOException de) {
			logger.error("Exception caught in changeDoaRequestStatus()", de);
			throw new AppRuntimeException(de);
		}
		
	}
	
	
	
}
