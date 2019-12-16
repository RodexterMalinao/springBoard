package com.bomwebportal.mob.ds.service;

import java.util.List;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ds.ui.MobDsCancelOrderUI;

public interface MobDsOrderService {

	public List<OrderDTO> findOrderEnquiry(String startDate, String endDate, String orderId, 
			String orderStatus, String variousDateType, String channel, String staffId, 
			String orderType, String category, String areaCd, String shopCd, String group, 
			String mrt,String channelCd, String aoInd, String aoItemCode, String orderNature);
	public List<OrderDTO> findOrderReview(String staffId, String channelId, String channelCd, String category);
	public String getSalesChannelId(String orderId);
	public int approveOrderReview(String orderId, String channelId, String category, String staffId);
	public int rejectOrderReview(String orderId, String reason, String category, String staffId);
	public int updateOrderReviewStatus();
	public MobDsCancelOrderUI getMobDsCancelOrderUI (String orderId);
	public String isOrderSupportApprovable(String orderId);
	public String getAllDocAssgn(String orderId);
}
