package com.bomwebportal.lts.service.order;

import com.bomwebportal.lts.dto.srvAccess.BmoDetailOutput;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialInputDTO;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;

public interface AppointmentDwfmService {

	public abstract ResourceDetailOutputDTO getResourceDetail(
			ResourceDetailInputDTO pResourceInput);

	public abstract PrebookAppointmentOutputDTO getPrebookAppointment(
			PrebookAppointmentInputDTO pPrebookInput);
	
	public abstract CancelPrebookSerialOutputDTO cancelPrebookSerial(
			CancelPrebookSerialInputDTO pCancelPrebookInput);
	
	public abstract BmoDetailOutput getBmoPermits(
			ResourceDetailInputDTO pResourceInput);
}