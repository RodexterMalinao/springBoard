package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsSbHelper;

public class SummaryDelServiceImpl extends SummaryDelBaseServiceImpl {

	protected ServiceDetailLtsDTO getDelService(SbOrderDTO pSbOrder) {
		return LtsSbHelper.getDelServices(pSbOrder);
	}
}
