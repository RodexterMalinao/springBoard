package com.bomwebportal.lts.service.acq;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;

public interface LtsAcqRelatedPcdOrderService {

	public ImsSbOrderDTO retrievePcdSbOrder (String orderId, AcqOrderCaptureDTO orderCapture, boolean isCustInfoConfirmed);
	
	public boolean isDelFreePcdSbOrder (String orderId, String pcdBundleFreeType);
	
	public boolean checkAnyActiveServiceWithinXMonths (String srvbdry_num, String floor_num, String unit_num, String prevSerTermMth);
}
