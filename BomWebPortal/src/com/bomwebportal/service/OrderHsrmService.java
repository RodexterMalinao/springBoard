package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.HsrmDTO;
import com.bomwebportal.dto.OrderHsrmLogDTO;
import com.bomwebportal.dto.ResultDTO;

public interface OrderHsrmService {
	public int insertOrderHsrmLog(OrderHsrmLogDTO orderHsrmLogDTO);
	public ResultDTO updateHSRMOrder(List<ComponentDTO> components, String sbOrderId, String itemCode, String salesCd, boolean aoInd,HsrmDTO validateHSRMResult );
	public ResultDTO updateHSRMStatus(String sbOrderId, String appointmentNo, String itemCode, String salesCd,HsrmDTO validateHSRMResult );
	public ResultDTO completeHSRMOrder(List<ComponentDTO> components, String sbOrderId, String itemCode, String salesCd, boolean aoInd, HsrmDTO validateHSRMResult);
	public ResultDTO completeHSRMStatus(String sbOrderId, String appointmentNo, String itemCode, String salesCd,HsrmDTO validateHSRMResult);
	public List<OrderHsrmLogDTO> getOrderHsrmLog(String orderId, String refId);
	public HsrmDTO validateHSRM(List<ComponentDTO> components, String orderId, String channelId, String itemCode, String salesCd, String docId, Date appDate, String basketBrand);
	public OrderHsrmLogDTO getOrderConfirmedHsrmLog(String orderId);
	public boolean hsrmConfirmed(String orderId);
	public OrderHsrmLogDTO getOrderCompletedHsrmLog(String orderId);
	public boolean hsrmCompleted(String orderId);
	public boolean isOrderCompletedHsrmLogExist(String orderId);
	public boolean hsrmAllowRecreate();
	public boolean isPrj7AttbExists(List<ComponentDTO> components);
	public boolean checkLogAlreadyExists(String orderId, String attbVal, String action, String status);
}
