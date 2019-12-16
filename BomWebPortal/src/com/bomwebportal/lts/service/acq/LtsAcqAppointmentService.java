package com.bomwebportal.lts.service.acq;

import java.util.List;

import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.LtsSRDDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqAppointmentFormDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;

public interface LtsAcqAppointmentService {
	public LtsSRDDTO getEarliestSRD(AcqOrderCaptureDTO pOrder, String pAppDate);
	public LtsSRDDTO getEarliestSRD(AcqOrderCaptureDTO pOrder, String pAppDate, boolean ignore2NBW);
	public LtsSRDDTO getSecDelEarliestSRD(AcqOrderCaptureDTO order, String pAppDate, String pOCID, LtsSRDDTO primaryEarliestSrd);
	public List<LtsSRDDTO> getNormalLeadTime(String pAppDate, boolean isAmendment, AcqOrderCaptureDTO orderCaptureDTO);
	public List<String> getPublicHolidayList();
	public boolean isBBShortage(AcqOrderCaptureDTO pOrder);
	public ResourceDetailInputDTO getResourceDetailInput(AcqOrderCaptureDTO orderCaptureDTO, String date, String type);
	public PrebookAppointmentInputDTO getPrebookAppointmentInput(AcqOrderCaptureDTO orderCaptureDTO, String date, String timeSlot, String staffId, String timeSlotType);
	public String[] getAreaDistrictCd(LtsAddressRolloutFormDTO form);
	public int getFieldPermitRequired(AcqOrderCaptureDTO orderCaptureDTO);
	public boolean getChangePonTimeSlotInd(AcqOrderCaptureDTO orderCaptureDTO);
	public boolean isUsePipbDirectly(AcqOrderCaptureDTO orderCaptureDTO);
	public LtsSRDDTO getPipbEarlisetSRD(AcqOrderCaptureDTO pOrder, String pAppDate);
	public boolean isEyeOrderAgeOver(AcqOrderCaptureDTO pOrder);
	public boolean isAgeOver(String dob);
	public boolean isMustQc(AcqOrderCaptureDTO pOrder, LtsAcqAppointmentFormDTO form);
	public String getMustQcReasonCd(AcqOrderCaptureDTO pOrder, LtsAcqAppointmentFormDTO form);
	public boolean isResourceShortage(String pcdsbid);
}
