package com.bomwebportal.ims.service;

import java.util.List;

import com.bomwebportal.dto.OrderDTO;
import com.bomwebportal.ims.dto.ImsCustomerOrderHistoryDTO;


public interface ImsOLOrderSearchService {
	public List<OrderDTO> getOrderList(String shopCode, String lob, String orderStatus, String startDate, String endDate, String saleCd, String orderIdStr);
	
	public List<ImsCustomerOrderHistoryDTO> getCustomerOrderHistoryDTOALL(String idDocNum, String serviceNum, String idDocType, 
			String loginId, String serviceType, String emailAddress, String orderId, String orderType, String contactNum);
	
	
	public boolean IsSaleResendEmailAllowed(String SalesCategory);
	
}
