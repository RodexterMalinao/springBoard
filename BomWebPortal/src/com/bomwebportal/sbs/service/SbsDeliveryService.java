package com.bomwebportal.sbs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.DeliveryDAO;
import com.bomwebportal.sbs.dao.SbsDeliveryTimeSlotDAO;
import com.bomwebportal.sbs.dto.DeliveryDateRangeDTO;
import com.bomwebportal.sbs.dto.DeliveryTimeSlotDTO;

public class SbsDeliveryService {
	protected final Log logger = LogFactory.getLog(getClass());

	@Autowired
	private SbsDeliveryTimeSlotDAO sbsDeliveryTimeSlotDAO;
	@Autowired
	private DeliveryDAO deliveryDAO;
	
	public List<DeliveryTimeSlotDTO> getDeliveryTimeSlotList (String distNo, Date appDate) {
		logger.debug("invoking SBS getDeliveryTimeSlotList()");
		try {
			return sbsDeliveryTimeSlotDAO.getDeliveryTimeSlotList(distNo,appDate);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	public DeliveryDateRangeDTO getDeliveryDate( Date appDate) {
		logger.debug("invoking SBS getDeliveryDate()");
		try {
			return sbsDeliveryTimeSlotDAO.getDeliveryDate(appDate);
		} catch (DAOException e) {
			throw new AppRuntimeException(e);
		}
	}
	
	public String getTimeSlotDesc(String timeSlot) {
		try {
			String timeSlotDesc[] = deliveryDAO.getTimeSlotDesc(timeSlot);
			if (timeSlotDesc != null) {
				return timeSlotDesc[0] + "-" + timeSlotDesc[1];
			}
		} catch (DAOException e) {
			logger.error("Exception caught in getTimeSlotDesc(String timeSlot)", e);
		}
		return null;
	}
}
