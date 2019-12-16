package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsSbHelper;

public class Summary2ndDelServiceImpl extends SummaryDelBaseServiceImpl {

	protected ServiceDetailLtsDTO getDelService(SbOrderDTO pSbOrder) {
		return LtsSbHelper.get2ndDelServices(pSbOrder.getSrvDtls());
	}
}
