package com.bomwebportal.lts.service.order.acq;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsAcqOrderQcSubmitLtsService {
	
	public abstract String signOffOrder(SbOrderDTO pSbOrder, BomSalesUserDTO bomSalesUser);

}
