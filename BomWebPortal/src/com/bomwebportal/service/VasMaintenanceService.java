package com.bomwebportal.service;

import java.util.List;

import com.bomwebportal.dto.VasMaintenanceDTO;
import com.bomwebportal.dto.ui.VasMaintenanceUI;

public interface VasMaintenanceService {
	
	List<VasMaintenanceDTO> getVasMaintenanceDTO (String offerCdSearch);
	Long createVasItem(VasMaintenanceUI vasMaintenanceUI);
	List<VasMaintenanceDTO> getRecurringAmt(String prodId);
	List<VasMaintenanceDTO> getOneTimeAmt(String prodId);	
	List<VasMaintenanceDTO> getOneTimeRecurringAmt(String offerCd);
	List<VasMaintenanceDTO> getParamsAlert(String prodId);

}
