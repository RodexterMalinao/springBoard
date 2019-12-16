package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.OrderRemarkDTO;

public interface MobCcsOrderRemarkService {
	OrderRemarkDTO getOrderRemarkDTO(String rowId);
	List<OrderRemarkDTO> getOrderRemarkDTOALL(String orderId);
	List<OrderRemarkDTO> getPTOrderRemarkDTO(String orderId);
	int insertOrderRemarkDTO(OrderRemarkDTO dto);
	int insertOrderRemark(String userName,String orderId, String message);
	int insertPTOrderRemark(String userName,String orderId, String message);
}
