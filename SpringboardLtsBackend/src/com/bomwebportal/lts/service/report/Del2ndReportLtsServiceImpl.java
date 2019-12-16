package com.bomwebportal.lts.service.report;

import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.util.LtsSbHelper;

public class Del2ndReportLtsServiceImpl extends DelReportLtsBaseServiceImpl {

	protected ServiceDetailLtsDTO getDelService(SbOrderDTO pSbOrder) {
		return LtsSbHelper.get2ndDelServices(pSbOrder.getSrvDtls());
	}
}
