package com.bomwebportal.lts.service;

import java.util.List;

import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.disconnect.TerminateOrderCaptureDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.srvAccess.BmoPermitDetail;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;

public interface LtsAppointmentService {

	public LtsSRDDTO getEarliestSRD(OrderCaptureDTO orderCaptureDTO);
	public LtsSRDDTO getEarliestSRD(OrderCaptureDTO orderCaptureDTO, String appDate, boolean isWorkQueue);
	public LtsSRDDTO getEarliestSRD(OrderCaptureDTO orderCaptureDTO, boolean erInd);
	public LtsSRDDTO getSecDelEarliestSRD(OrderCaptureDTO orderCaptureDTO, String pAppDate, String pOCID);
	public boolean isBBShortage(OrderCaptureDTO orderCaptureDTO);
	public ResourceDetailInputDTO getResourceDetailInput (OrderCaptureDTO orderCaptureDTO, String date, String type);
	public PrebookAppointmentInputDTO getPrebookAppointmentInput(OrderCaptureDTO orderCaptureDTO, String date, String timeSlot, String staffId, String timeSlotType);
	public String[] getAreaDistrictCd(OrderCaptureDTO orderCaptureDTO);
	public String[] getTermAreaDistrictCd(TerminateOrderCaptureDTO orderCaptureDTO);
	public String[] reformatDateTimeSlot(String date, String timeSlot);
	public List<String> getPublicHolidayList();
	public boolean getChangePonTimeSlotInd(OrderCaptureDTO orderCaptureDTO);
	public boolean getTermChangePonTimeSlotInd(TerminateOrderCaptureDTO orderCaptureDTO);
	public List<LtsSRDDTO> getNormalLeadTime(String pAppDate, String deviceType, boolean isAmendment, boolean isCCMode);
	public PrebookAppointmentInputDTO getTermPrebookAppointmentInput(TerminateOrderCaptureDTO orderCaptureDTO, String date, String timeSlot, String staffId, String timeSlotType);
	public ResourceDetailInputDTO getTermResourceDetailInput(TerminateOrderCaptureDTO orderCaptureDTO, String date);
	public LtsSRDDTO getRetEarliestSRD(OrderCaptureDTO orderCapture, boolean isFieldVisitRequired);
	public LtsSRDDTO getStandaloneVasEarliestSRD(OrderCaptureDTO orderCapture);
	
	public BmoPermitDetail getBmoPermitLeadDays(ResourceDetailInputDTO input, String appDate);
	public ResourceDetailInputDTO getResourceDetailInput(
			SbOrderDTO sbOrder, String type, String date, String userId);
	public boolean isInstallUpgradePon(SbOrderDTO sbOrder);
	public PrebookAppointmentInputDTO getPrebookAppointmentInput(SbOrderDTO sbOrder, String date, String timeSlot, String staffId, String timeSlotType);
	public ResourceDetailInputDTO getTermResourceDetailInput(SbOrderDTO sbOrder, String type, String date, String userId);
	
	public int getEpdLeadTime();
	public List<ItemDetailDTO> getNewEpdItemList(String basketChannelId, String applicationDate, String locale, List<ItemDetailDTO> oldEpdItemList);
	public List<ItemDetailDTO> getEpdItemList(String basketChannelId, String applicationDate, String locale);
}
