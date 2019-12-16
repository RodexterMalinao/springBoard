package com.bomwebportal.ims.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.ims.dto.AppointmentPermitPropertyDtlDTO;
import com.bomwebportal.ims.dto.AppointmentSubmitDTO;
import com.bomwebportal.ims.dto.AppointmentTimeSlotDTO;
import com.bomwebportal.ims.dto.ImsServiceSrdDTO;
import com.bomwebportal.ims.dto.ui.InstallAddrUI;
import com.bomwebportal.ims.dto.ui.OrderImsUI;

public interface ImsAppointmentService {
	
	public ImsServiceSrdDTO getServiceSrdComparedWithBMO(String isBlAddr, String isBlCust, String isPccwLn, Date appntDateTime, String sbno, String flat, String floor, String isCC, String isPT, String isDS, String i_has_bb_srv, String i_has_nowtv_srv, String mismatch, String fsPrepay, String fsInd, String i_must_qc_ind, String i_ride_on_fsa, String orderType, String supremeFsInd);

	// NOWTVSALES
	public ImsServiceSrdDTO getServiceSrdComparedWithBMO(Date appntDateTime, String isDS, String i_has_bb_srv, String i_has_nowtv_srv, String mismatch, String fsPrepay, String fsInd, OrderImsUI order);
	// NOWTVSALES (end)
	
	public AppointmentSubmitDTO reserveTimeSlot(InstallAddrUI instAddr, AppointmentTimeSlotDTO timeSlot, String supremeFsInd);
	
	public void cancelReservedTimeSlot(String SerialNum);
	
	public AppointmentTimeSlotDTO[] getTimeSlot(InstallAddrUI instAddr, Date apptDate, String supremeFsInd);
	
	public Date getResourceShortageSRD(String sbno, String Technology, String isPccwLn);
	
	public AppointmentPermitPropertyDtlDTO getPermitPropertyDtl(InstallAddrUI instAddr, Date apptDate, String supremeFsInd);

	public ImsServiceSrdDTO getServiceSrd(String isBlAddr,
			String isBlCust, String isPccwLn, Date appntDateTime,
			String sbno, String flat, String floor, String isCC, String isPT, String isDS, String i_has_bb_srv, String i_has_nowtv_srv, String mismatch, String fsPrepay, String fsInd, String i_ride_on_fsa,
			String orderType);

	public AppointmentPermitPropertyDtlDTO getPermitPropertyDtl(
			String sb, String prodSubTypeCd, String applicationDate);
	
	//for ret/term
	public ImsServiceSrdDTO getServiceSrdComparedWithBMO(String isBlAddr, String isBlCust, String isPccwLn, 
			Date appntDateTime, String sbno, String flat, String floor, String isCC, String isPT, OrderImsUI order, String bomLastestTech);
	
	public AppointmentTimeSlotDTO[] getTimeSlot(InstallAddrUI instAddr, Date apptDate,String toProdSubTypeCd, String frProdSubTypeCd, 
			String orderType, String changeAddr, String supremeFsInd);

	public AppointmentSubmitDTO reserveTimeSlot(OrderImsUI order, AppointmentTimeSlotDTO timeSlot);
	
	public List<String> getSbPopUp(String sbNum);
	
	public  List<String[]> getPreInstallParameter();
	
}
