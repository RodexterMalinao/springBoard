package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;
import com.bomwebportal.mob.ccs.dto.UrgentDeliveryReportDTO;


public interface MobCcsUrgentDeliveryReportService {

	List<UrgentDeliveryReportDTO> getUrgentDeliveryReportDTOALL(String orderId, Date processingDate);
	
}
