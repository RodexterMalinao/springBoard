package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bomwebportal.mob.ccs.dto.MobCcsBulkPrintDTO;

public interface MobCcsBulkPrintService {
	Date getNextWorkingDay(Date jobDate);
	List<MobCcsBulkPrintDTO> getBulkPrintDTOALLBySearch(Date processDate, List<Map<String, String>> statuses, String location);
}
