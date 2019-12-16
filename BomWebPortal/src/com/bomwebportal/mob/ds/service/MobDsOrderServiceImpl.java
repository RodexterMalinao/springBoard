package com.bomwebportal.mob.ds.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dao.OrderDAO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ds.dao.MobDsOrderDAO;
import com.bomwebportal.mob.ds.ui.MobDsCancelOrderUI;
import com.bomwebportal.service.OrderHsrmService;
import com.bomwebportal.service.OrderService;
import com.bomwebportal.util.BomWebPortalConstant;

public class MobDsOrderServiceImpl implements MobDsOrderService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobDsOrderDAO mobDsOrderDAO;
	private OrderDAO orderDAO;
	private OrderHsrmService orderHsrmService;
	private OrderService orderService;
	
	public MobDsOrderDAO getMobDsOrderDAO() {
		return mobDsOrderDAO;
	}

	public void setMobDsOrderDAO(MobDsOrderDAO mobDsOrderDAO) {
		this.mobDsOrderDAO = mobDsOrderDAO;
	}

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}

	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}
	

	public OrderHsrmService getOrderHsrmService() {
		return orderHsrmService;
	}

	public void setOrderHsrmService(OrderHsrmService orderHsrmService) {
		this.orderHsrmService = orderHsrmService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public List<OrderDTO> findOrderEnquiry(String startDate, String endDate, String orderId, 
			String orderStatus, String variousDateType, String channel, String staffId, 
			String orderType, String category, String areaCd, String shopCd, String group, 
			String mrt, String channelCd, String aoInd, String aoItemCode, String orderNature) {
		try {
			return mobDsOrderDAO.findOrderEnquiry(startDate, endDate, orderId, 
					orderStatus, variousDateType, channel, staffId, orderType, 
					category, areaCd, shopCd, group, mrt, channelCd, aoInd, aoItemCode, orderNature);
		} catch (DAOException e) {
			logger.error("Exception caught in findOrderEnquiry()", e);
		}
		
		return null;
	}

	public List<OrderDTO> findOrderReview(String staffId, String channelId, String channelCd, String category) {
		try {
			return mobDsOrderDAO.findOrderReview(staffId, channelId, channelCd, category);
		} catch (DAOException e) {
			logger.error("Exception caught in findOrderReview()", e);
		}
		
		return null;
	}
	
	public MobDsCancelOrderUI getMobDsCancelOrderUI(String orderId) {
		try {
			MobDsCancelOrderUI result = mobDsOrderDAO.getMobDsCancelOrderUI(orderId);
			result.setMultiSim(orderDAO.isMultiSim(orderId));
			List<ComponentDTO> componentList = orderService.getComponentList(orderId);
			if (orderHsrmService.isPrj7AttbExists(componentList)){
				if (orderHsrmService.isOrderCompletedHsrmLogExist(orderId) && !orderHsrmService.hsrmAllowRecreate()){
					result.setPreRegInd( "Y" );
				}
			}
			/*if (orderHsrmService.hsrmCompleted(orderId) || orderHsrmService.hsrmConfirmed(orderId)){
				result.setPreRegInd( "Y" );
			}*/
			return result;
		} catch (DAOException e) {
			logger.error("Exception caught in getMobDsCancelOrderUI()", e);
		}
		return null;
	}

	public int approveOrderReview(String orderId, String channelId, String category, String staffId) {
		try {
			return mobDsOrderDAO.approveOrderReview(orderId, channelId, category, staffId);
		} catch (DAOException e) {
			logger.error("Exception caught in approveOrderReview()", e);
		}
		return 0;
	}
	
	public int rejectOrderReview(String orderId, String reason, String category, String staffId) {
		try {
			return mobDsOrderDAO.rejectOrderReview(orderId, reason, category, staffId);
		} catch (DAOException e) {
			logger.error("Exception caught in rejectOrderReview()", e);
		}
		return 0;
	}
	
	public int updateOrderReviewStatus() {
		return mobDsOrderDAO.updateOrderReviewStatus();
	}

	public String getSalesChannelId(String orderId) {
		try {
			return mobDsOrderDAO.getSalesChannelId(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getSalesChannelId()", e);
		}
		return null;
	}
	
	public String isOrderSupportApprovable(String orderId){
		String flg = "N";
		String sts = null;
		try{
			sts = mobDsOrderDAO.getCloneOrderSts(orderId);
		}catch (DAOException e) {
			logger.error("Exception caught in getCloneOrderSts()", e);
		}
		if (sts == null) {
			flg = "Y";
		} else if(BomWebPortalConstant.BWP_ORDER_CANCELLED.equals(sts) || BomWebPortalConstant.BWP_ORDER_VOID.equals(sts)) {
			flg = "Y";
		}
		return flg;
	}
	
	public String getAllDocAssgn(String orderId) {
		try {
			return mobDsOrderDAO.getAllDocAssgn(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getAllDocAssgn()", e);
		}
		return null;
	}
}
