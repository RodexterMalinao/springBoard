package com.bomwebportal.mob.ccs.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.StockDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.StockManualDetailDTO;
import com.bomwebportal.mob.ccs.dto.ui.StockManualAssgnUI;
import com.bomwebportal.mob.ds.dao.MobDsStockManualDAO;
import com.bomwebportal.service.OrderService;

@Transactional(readOnly=true)
public class StockManualDetailServiceImpl implements StockManualDetailService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private StockDAO stockdao;
	private MobDsStockManualDAO mobDsStockManualDAO;
	private OrderService orderService;

	public void setStockdao(StockDAO stockdao) {
		this.stockdao = stockdao;
	}
	public StockDAO getStockdao() {
		return stockdao;
	}
	public MobDsStockManualDAO getMobDsStockManualDAO() {
		return mobDsStockManualDAO;
	}
	public void setMobDsStockManualDAO(MobDsStockManualDAO mobDsStockManualDAO) {
		this.mobDsStockManualDAO = mobDsStockManualDAO;
	}
	public OrderService getOrderService() {
		return orderService;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	
	public List<StockManualDetailDTO> getStockManualDetail(String orderId) {
		try {
			logger.info("getStockManualDetail() is called in StockManualDetailServiceImpl");
			return stockdao.getStockManualDetail(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getStockManualDetail()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean deassignAllItemByOrder(String orderId) {
		try {
			logger.info("deassignAllItemByOrder() is called in StockManualDetailServiceImpl");
			return stockdao.deassignAllItemByOrder(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in deassignAllItemByOrder()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	// add by Joyce 20120507
	public boolean deassignPerItem(String orderId, String itemCode, String itemSerial) {
		try {
			logger.info("deassignPerItem() is called in StockManualDetailServiceImpl");
			return stockdao.deassignPerItem(orderId, itemCode, itemSerial);
		} catch (DAOException de) {
			logger.error("Exception caught in deassignPerItem()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int dsDeassignPerItem(StockManualAssgnUI dto, String orderId, String username, String action) {
		try {
			logger.info("dsDeassignPerItem() is called in StockManualDetailServiceImpl");
			StockManualAssgnUI tempDto = new StockManualAssgnUI();
			tempDto.setOrderId(dto.getOrderId());
			tempDto.setItemCode(dto.getItemCode());
			return mobDsStockManualDAO.manualDSStockAssgn(tempDto, dto, orderId, username, action);
		} catch (DAOException de) {
			logger.error("Exception caught in dsDeassignPerItem()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean manualOrderStatusProcess(String orderId) {
		try {
			logger.info("manualOrderStatusProcess() is called in StockManualDetailServiceImpl");
			String orderType = orderService.getOrderType(orderId).get("order_type");
			if ("COS".equalsIgnoreCase(orderType) || "BRM".equalsIgnoreCase(orderType) || "TOO1".equalsIgnoreCase(orderType)) {
				return stockdao.manualCosOrderStatusProcess(orderId);
			} else {
				return stockdao.manualOrderStatusProcess(orderId);
			}
		} catch (DAOException de) {
			logger.error("Exception caught in manualOrderStatusProcess()", de);
			throw new AppRuntimeException(de);
		}
	}
	public String doaDeassignItem(String orderId, String itemCode,
			String itemSerial, String oldItemCode) {
		try {
			return stockdao.doaDeassignItem(orderId, itemCode, itemSerial, oldItemCode);
		} catch (DAOException e) {
			logger.error("Exception caught in doaDeassignItem()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<StockManualDetailDTO> getDoaDetail(String orderId) {
		try {
			return stockdao.getDoaDetail(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getStockResultDTOByOrderId()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public StockManualAssgnUI getAssignedItemSerialStatus(String orderId, String itemCode) {
		try {
			return mobDsStockManualDAO.getAssignedItemSerialStatus(orderId, itemCode);
		} catch (DAOException e) {
			logger.error("Exception caught in getStockResultDTOByOrderId()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public StockManualDetailDTO getPreviousItem(String orderId, String itemCode) {
		try {
			return mobDsStockManualDAO.getPreviousItem(orderId, itemCode);
		} catch (DAOException e) {
			logger.error("Exception caught in getStockResultDTOByOrderId()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public StockManualDetailDTO getPreviousItemSingleItemCode(String orderId, String itemCode) {
		try {
			return mobDsStockManualDAO.getPreviousItemSingleItemCode(orderId, itemCode);
		} catch (DAOException e) {
			logger.error("Exception caught in getPreviousItemSingleItemCode()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public List<CodeLkupDTO> getItemColorlist(String itemCode) {
		try {
			return stockdao.getItemColorlist(itemCode);
		} catch (DAOException e) {
			logger.error("Exception caught in getItemType()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public String getStockPool(String orderId) {
		try {
			return stockdao.getStockPool(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getStockPool()", e);
			throw new AppRuntimeException(e);
		}
	}
	public String getCCSStockPool(String channelCd) {
		try {
			return stockdao.getCCSStockPool(channelCd);
		} catch (DAOException e) {
			logger.error("Exception caught in getCCSStockPool()", e);
			throw new AppRuntimeException(e);
		}
	}
}
