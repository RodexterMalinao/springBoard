package com.bomwebportal.mob.ccs.service;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.MrtReserveDTO;
import com.bomwebportal.mob.ccs.dto.ui.MRTReserveUI;

public interface MrtReserveService {
	
	public int insertMrtReserve(MrtReserveDTO dto);
	
	public List<MRTReserveUI> getReserveMRTList(String staffId);
}
