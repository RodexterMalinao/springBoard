package com.bomwebportal.lts.service.order;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.order.AppointmentDwfmDAO;
import com.bomwebportal.lts.dto.srvAccess.BmoDetailOutput;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialInputDTO;
import com.bomwebportal.lts.dto.srvAccess.CancelPrebookSerialOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentInputDTO;
import com.bomwebportal.lts.dto.srvAccess.PrebookAppointmentOutputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailInputDTO;
import com.bomwebportal.lts.dto.srvAccess.ResourceDetailOutputDTO;

public class AppointmentDwfmServiceImpl implements AppointmentDwfmService {

	private final Log logger = LogFactory.getLog(getClass());
	
	private AppointmentDwfmDAO appointmentDwfmDao;


	public ResourceDetailOutputDTO getResourceDetail(ResourceDetailInputDTO pResourceInput) {
		try {
			return this.appointmentDwfmDao.getResourceDetail(pResourceInput);
		} catch (DAOException de) {
			logger.error("Fail in AppointmentDwfmService.getResourceDetail()", de);
			throw new AppRuntimeException(ExceptionUtils.getFullStackTrace(de), de);
		}
	}

	public PrebookAppointmentOutputDTO getPrebookAppointment(
			PrebookAppointmentInputDTO pPrebookInput) {
		try {
			return this.appointmentDwfmDao.getPrebookAppointment(pPrebookInput);
		} catch (DAOException de) {
			logger.error("Fail in AppointmentDwfmService.getPrebookAppointment()", de);
			throw new AppRuntimeException(de);
		}
	}

	public CancelPrebookSerialOutputDTO cancelPrebookSerial(CancelPrebookSerialInputDTO pCancelPrebookInput) {
		
		try {
			return this.appointmentDwfmDao.cancelPrebookSerial(pCancelPrebookInput);
		} catch (DAOException de) {
			logger.error("Fail in AppointmentDwfmService.getCancelPrebookSerial()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public BmoDetailOutput getBmoPermits(ResourceDetailInputDTO pResourceInput) {
		 
		try {
			return this.appointmentDwfmDao.getBmoPermits(pResourceInput);
		} catch (DAOException de) {
			logger.error("Fail in AppointmentDwfmService.getBmoPermits()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public AppointmentDwfmDAO getAppointmentDwfmDao() {
		return this.appointmentDwfmDao;
	}

	public void setAppointmentDwfmDao(AppointmentDwfmDAO pAppointmentDwfmDao) {
		this.appointmentDwfmDao = pAppointmentDwfmDao;
	}
}
