package com.bomwebportal.lts.service.bom;

import com.bomwebportal.lts.dto.bom.OrderServiceDTO;

public interface BomOrderInfoService {

	public abstract OrderServiceDTO[] getLtsInstallPendingOrderByCust(
			String pCustNum, String pSystemId);

}