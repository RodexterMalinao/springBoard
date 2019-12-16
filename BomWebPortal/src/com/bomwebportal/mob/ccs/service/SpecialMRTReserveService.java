package com.bomwebportal.mob.ccs.service;

import java.util.List;


import com.bomwebportal.mob.ccs.dto.MrtReserveDTO;
import com.bomwebportal.mob.ccs.dto.SpecialMrtRequestDTO;
import com.bomwebportal.mob.ccs.dto.ui.SpecialMRTReserveUI;


public interface SpecialMRTReserveService {
	
	public List<SpecialMRTReserveUI> getSpecialReserveMRTList (String staffId);
	public int insertSpecialMrtReserve(SpecialMrtRequestDTO dto, String staffId, String srvNumType);
	public int updateSpecialMrtReserve(SpecialMrtRequestDTO dto, String staffId, String srvNumType);
	public boolean getSpecialMrtReserveByRequestId(String requestId);
	public Integer noOfRequests(String staffId);
	public List<MrtReserveDTO> getMrtReserveByMrt(String msisdn);
//	public List<SpecialMRTReserveUI> getRejectedSpecialMRTRequest(String staffId) ;
	
	
}
