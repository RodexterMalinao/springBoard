package com.bomwebportal.sbo.service;

import java.util.List;

import com.bomwebportal.sbo.dto.SboMobOrderDTO;

public interface SboOrderService {
	public List<SboMobOrderDTO> findSboMobOrder(String orderId,
			String idDocType, String idDocNum,
			String srvNum,
			String contactEmail,
			String contactNum);

}
