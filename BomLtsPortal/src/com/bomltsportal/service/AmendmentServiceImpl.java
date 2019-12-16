package com.bomltsportal.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;

import com.bomltsportal.util.BomLtsConstant;
import com.bomltsportal.util.LtsDateFormatHelper;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.bom.BomOrderAppointmentDAO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.BomOrderAppntDTO;
import com.bomwebportal.lts.dto.order.OrderStatusSynchDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.service.bom.BomOrderStatusSynchService;
import com.bomwebportal.util.DateFormatHelper;

public class AmendmentServiceImpl implements AmendmentService {

	protected final Log logger = LogFactory.getLog(getClass());
	private BomOrderAppointmentDAO bomOrderAppointmentDAO;
	private BomOrderStatusSynchService bomOrderStatusSynchService = null;
	private final String validBomStatus = "01";
	private final String validBomLegacyStatus = "D";
	private final String validFsaStatus = "01";
	private final String validFsaLegacyStatus = "01";

	private MessageSource messageSource;
	
	public boolean isAmendmentAllow(String pOrderId, OrderStatusSynchDTO pSrvStatus, OrderStatusSynchDTO pSrvImsStatus, String pSrvType, StringBuilder pMsgSb, Locale locale) {
	
		boolean isEyeSrv = BomLtsConstant.SERVICE_TYPE_EYE.equals(pSrvType);
		
		if (pSrvStatus != null) {
			if (!isEyeSrv || (isEyeSrv && pSrvImsStatus != null)) {
				String[]  bomAppntDate = getBomOrderAppointmentDateTime(pSrvStatus.getOcId(), pSrvStatus.getBomDtlId());
				boolean bomOrderApptDateValid = bomAppntDate != null? checkBomOrderAppointmentDate(bomAppntDate[0]): false;
				if (bomOrderApptDateValid) {
					//if status is not null and BOM appointment date is valid
					return true;
				}
			}
		}
		pMsgSb.append(messageSource.getMessage("amend.warn1", new Object[] {pOrderId}, locale));
		return false;
	}
	
	public OrderStatusSynchDTO getValidBomOrderStatus(String pSbOrderId, String pTypeOfSrv, String pSrvNum, String pCcServiceId, String pCcServiceMemNum) {
		
		OrderStatusSynchDTO[] status = getBomOrderStatus(pSbOrderId, pTypeOfSrv, pSrvNum, pCcServiceId, pCcServiceMemNum);
		
		for (int i=0; status!=null && i<status.length; ++i) {
			if(validBomStatus.equals(status[i].getBomStatus()) && validBomLegacyStatus.equals(status[i].getBomLegacyStatus())) {
				return status[i];
			}
		}
		return null;
	}

	public OrderStatusSynchDTO getValidFsaOrderStatus(String pSbOrderId, String pFsa, String pOcid, String pGrpId) {
		
		OrderStatusSynchDTO[] status = getBomOrderStatus(pSbOrderId, BomLtsConstant.SERVICE_TYPE_IMS, pFsa, pFsa, pFsa, pOcid, pGrpId);
		
		for (int i=0; status!=null && i<status.length; ++i) {
			if (validFsaStatus.equals(status[i].getBomStatus()) && validFsaLegacyStatus.equals(status[i].getBomLegacyStatus())) {
				return status[i];
			}
		}
		return null;
	}
	
	public OrderStatusSynchDTO[] getBomOrderStatus(String pSbOrderId, String pTypeOfSrv, String pSrvNum, String pCcServiceId, String pCcServiceMemNum){
		
		OrderStatusSynchDTO[] status = this.bomOrderStatusSynchService.getBomOrderStatus(pSbOrderId, pTypeOfSrv, pSrvNum, pCcServiceId, pCcServiceMemNum);
		return status == null ? new OrderStatusSynchDTO[0] : status;
	}
	
	public OrderStatusSynchDTO[] getBomOrderStatus(String pSbOrderId, String pTypeOfSrv, String pSrvNum, String pCcServiceId, String pCcServiceMemNum, String pOcid, String pGrpId){
		
		OrderStatusSynchDTO[] status = this.bomOrderStatusSynchService.getBomOrderStatus(pSbOrderId, pTypeOfSrv, pSrvNum, pCcServiceId, pCcServiceMemNum, pOcid, pGrpId, null);
		return status == null ? new OrderStatusSynchDTO[0] : status;
	}

	public String[] getLastestAmendApptTime(ServiceDetailDTO pSrvDtl){
			
		if(pSrvDtl != null){
			AppointmentDetailLtsDTO apptDTO = pSrvDtl.getAppointmentDtl();	
			String[] returnString = {apptDTO.getAppntStartTime(), apptDTO.getAppntEndTime()};
			return returnString;
		}
		return null;
	}
	
	public String[] getBomOrderAppointmentDateTime(String ocId, String dtlId){
		List<BomOrderAppntDTO> apptDtoList = null;
		try {
			apptDtoList = bomOrderAppointmentDAO.getBomAppointment(ocId, dtlId);
			
			if(apptDtoList != null && apptDtoList.size() > 0){
				String startDateTime = apptDtoList.get(0).getAppntStartTimeAsStr();
				String endDateTime = apptDtoList.get(0).getAppntEndTimeAsStr();
				String apptType = apptDtoList.get(0).getAppntType();
				
				String dateString = LtsDateFormatHelper.reformatDate(startDateTime, "yyyyMMddHHmmss", "dd/MM/yyyy");
				String timeSlotString = LtsDateFormatHelper.convertToSBTimeSlot(startDateTime.substring(8,12)+"-"+endDateTime.substring(8,12));
				
				String[] dateTime = {dateString, timeSlotString, apptType};
				return dateTime;
			}
		} catch (DAOException e) {
			logger.error("Fail in ServiceProfileLtsService.checkBomOrderAppointmentDate()", e);
		} catch (Exception e) {
			logger.error("Fail in ServiceProfileLtsService.checkBomOrderAppointmentDate()", e);
		} 
		
		return null;
	}
	
	private boolean checkBomOrderAppointmentDate(String bomAppntDateString){
		
		SimpleDateFormat df = new SimpleDateFormat();
		
		if(StringUtils.isNotBlank(bomAppntDateString)){
			String currDateString = (DateFormatHelper.getSysDate().split(" "))[0];
			try { 
				df.applyPattern("dd/MM/yyyy");
				Date bomAppntDate = df.parse(bomAppntDateString);
				Date currDate = df.parse(currDateString);
				Date calDate = DateUtils.addDays(currDate, 2);
				if(calDate.before(bomAppntDate)){
					return true;
				}
			} catch (ParseException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public BomOrderStatusSynchService getBomOrderStatusSynchService() {
		return bomOrderStatusSynchService;
	}

	public void setBomOrderStatusSynchService(
			BomOrderStatusSynchService bomOrderStatusSynchService) {
		this.bomOrderStatusSynchService = bomOrderStatusSynchService;
	}

	public BomOrderAppointmentDAO getBomOrderAppointmentDAO() {
		return bomOrderAppointmentDAO;
	}

	public void setBomOrderAppointmentDAO(BomOrderAppointmentDAO bomOrderAppointmentDAO) {
		this.bomOrderAppointmentDAO = bomOrderAppointmentDAO;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
