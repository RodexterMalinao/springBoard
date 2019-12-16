package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsUrgentDeliveryReportDAO;
import com.bomwebportal.mob.ccs.dto.UrgentDeliveryReportDTO;

@Transactional(readOnly=true)
public class MobCcsUrgentDeliveryReportServiceImpl implements MobCcsUrgentDeliveryReportService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private MobCcsUrgentDeliveryReportDAO mobCcsUrgentDeliveryReportDAO;
	
	public MobCcsUrgentDeliveryReportDAO getMobCcsUrgentDeliveryReportDAO() {
		return mobCcsUrgentDeliveryReportDAO;
	}

	public void setMobCcsUrgentDeliveryReportDAO(MobCcsUrgentDeliveryReportDAO mobCcsUrgentDeliveryReportDAO) {
		this.mobCcsUrgentDeliveryReportDAO = mobCcsUrgentDeliveryReportDAO;
	}

	public List<UrgentDeliveryReportDTO> getUrgentDeliveryReportDTOALL(String orderId, Date processingDate){
		try {
			logger.info("getUrgentDeliveryReportDTOALL() is called in MobCcsUrgentDeliveryReportServiceImpl");
			
			List <UrgentDeliveryReportDTO> result = mobCcsUrgentDeliveryReportDAO.getUrgentDeliveryReportDTOALL(orderId, processingDate);
			
			String temp_orderid = "-1";

			for (UrgentDeliveryReportDTO dto : result) {
				/*
					To prevent the duplicate values shown on the csv or table, 
					blank or null value will be given if the record 
					is same as the previous record.
				*/
				if (temp_orderid.equalsIgnoreCase(dto.getOrderId())) {
					dto.setCourier("");
					dto.setProcessDate(null);
					dto.setOrderId("");
					dto.setOcid("");
					dto.setMsisdn("");
					dto.setCustName("");
					dto.setStaffId("");
					dto.setDeliveryType("");
					dto.setDeliveryDate(null);
					dto.setDeliveryTimeSlot("");
					dto.setPaymentMethod("");
					dto.setPaymentAmt(null);
					dto.setContactName("");
					dto.setContactNum1("");
					dto.setContactNum2("");
					dto.setDeliveryAddress("");
					dto.setYahooCoupon("");
					dto.setTngCard("");
					dto.setTngSim("");
				}
				else {
					temp_orderid = dto.getOrderId();
				}
				
				
			}
			
			return result;
		} catch (DAOException de) {
			logger.error("Exception caught in getUrgentDeliveryReportDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}


}
