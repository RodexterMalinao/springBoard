package com.bomwebportal.mob.ccs.service;

import java.util.Date;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.mob.ccs.dto.ui.DeliveryUI;
import com.bomwebportal.mob.ccs.dto.ui.MRTUI;

public interface MobCcsDoaUpdateMrtService {
	public static enum MnpInd {
		Y // MNP
		, N // New number
		, A // New Number + MNP 
		;
	}
	
	public static enum AmendScenario {
		SALES_UPDATE_SRD_AFTER_DELIVERY_AND_BEFORE_ACTIVATION
		, MNP_REJECT
		, ONSITE_DOA
		, DOA
		, SRD_EXP
		, SALES_FOLLOW_UP // J014
		;
	}
	
	public static enum Team {
		SUPPORT
		, SALES
		;
	}
	
	public static final String ORDER_STATUS_COMPLETED = "02";
	public static final String ORDER_STATUS_FALLOUT = "99";
	public static final String BOM_ORDER_STATUS_PENDING = "Pending";
	public static final String BOM_ORDER_STATUS_COMPLETED = "Completed";
	
	AmendScenario getAmendScenario(OrderDTO orderDto);
	Team getTeam(String channelCd);
	// split allowUpdateMrt into allowUpdateSRD + allowUpdateMnp
	boolean allowUpdateSRD(OrderDTO orderDto, String channelCd);
	boolean allowUpdateMnp(OrderDTO orderDto, String channelCd);
	boolean allowFalloutToSales(OrderDTO orderDto, String channelCd);
	// combination of isMnpWindowFrozen + order status
	boolean allowMnpWindowChange(OrderDTO orderDto, String channelCd);
	// only calculate by date, not by order status
	boolean isMnpWindowFrozen(OrderDTO orderDto);
	
	MRTUI getMrtUI(String orderId);
	
	void updateDeliveryUI(DeliveryUI deliveryUI, String orderId, String username, String channelCd);
	
	void updateForFalloutToSales(String orderId, String username, String channelCd);
	
	void updateForNewNumber(DeliveryUI deliveryUI
			, String orderId
			, Date srvReqDate
			, String username, String channelCd);
	
	void updateForMnp(DeliveryUI deliveryUI
			, String orderId
			, String mnpMsisdn, String mnpTicketNum, Date cutOverDate, String cutOverTime
			, String custName, String docNo, String dno, String actDno
			, String username, String channelCd);
	
	void updateForNewNumberAndMnp(DeliveryUI deliveryUI
			, String orderId
			, Date srvReqDate
			, String mnpMsisdn, String mnpTicketNum, Date cutOverDate, String cutOverTime
			, String custName, String docNo, String dno, String actDno
			, String username, String channelCd);
	
	boolean isBomActivated(String msisdn);
}
