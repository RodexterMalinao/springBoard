package com.bomwebportal.lts.service.acq;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerIdentificationFormDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.order.ImsSbOrderService;

public class LtsAcqRelatedPcdOrderServiceImpl implements LtsAcqRelatedPcdOrderService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected ImsSbOrderService imsSbOrderService;
	
	public ImsSbOrderService getImsSbOrderService() {
		return imsSbOrderService;
	}

	public void setImsSbOrderService(ImsSbOrderService imsSbOrderService) {
		this.imsSbOrderService = imsSbOrderService;
	}

	public ImsSbOrderDTO retrievePcdSbOrder (String orderId, AcqOrderCaptureDTO orderCapture, boolean isCustInfoConfirmed) {
		
		try {
			ImsSbOrderDTO imsSbOrder = imsSbOrderService.getPcdSbOrder(orderId);
			
			if (imsSbOrder == null) {
				return null;
			}
			setIaNotMatch(imsSbOrder, orderCapture);
			setCustNotMatch(imsSbOrder, orderCapture, isCustInfoConfirmed);
				
			return imsSbOrder;
			
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(ExceptionUtils.getMessage(e));
		}
	}
	
	public boolean isDelFreePcdSbOrder (String orderId, String pcdBundleFreeType){
		
		try {
			String tempStr = imsSbOrderService.getPcdSbOrderHasDel(orderId,pcdBundleFreeType);
			
			if (tempStr == null) {
				return false;
			}
				
			return tempStr.equalsIgnoreCase("Y")?true:false;
			
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(ExceptionUtils.getMessage(e));
		}
	}
	
	public boolean checkAnyActiveServiceWithinXMonths (String srvbdry_num, String floor_num, String unit_num, String prevSerTermMth){
		try {
			String tempStr = imsSbOrderService.checkAnyActiveServiceWithinXMonths(srvbdry_num, floor_num, unit_num, prevSerTermMth);
			
			if (tempStr == null) {
				return false;
			}
				
			return tempStr.equalsIgnoreCase("Y")?true:false;
			
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new RuntimeException(ExceptionUtils.getMessage(e));
		}
	}	
	
	private void setIaNotMatch(ImsSbOrderDTO relatedPcdOrder, AcqOrderCaptureDTO orderCapture) {
		
		LtsAddressRolloutFormDTO addressRolloutForm = orderCapture.getLtsAddressRolloutForm();
		
		if(addressRolloutForm == null) {
			return;
		}
		
		// Check under same I/A;		
		String targetIaSrvBdary = addressRolloutForm.getServiceBoundary();
		String targetIaFloor = addressRolloutForm.getFloor();
		String targetIaFlat = addressRolloutForm.getFlat();

		String targetDistrictCode = addressRolloutForm.getDistrictCode();
		
		String targetAreaCode = addressRolloutForm.getAreaCode();
				
		if (! (StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(targetIaFloor, null), StringUtils.defaultIfEmpty(relatedPcdOrder.getFloorNo(), null) ) 
				&& StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(targetIaFlat, null), StringUtils.defaultIfEmpty(relatedPcdOrder.getUnitNo(), null)) 
				&& StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(targetIaSrvBdary, null), StringUtils.defaultIfEmpty(relatedPcdOrder.getSerbdyno(), null)))) {
			relatedPcdOrder.setIaNotMatch(true);
		}
		
		if (StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(targetAreaCode, null), StringUtils.defaultIfEmpty(relatedPcdOrder.getAreaCd(), null))
				&& StringUtils.equalsIgnoreCase(StringUtils.defaultIfEmpty(targetDistrictCode, null), StringUtils.defaultIfEmpty(relatedPcdOrder.getDistNo(), null))) {
			relatedPcdOrder.setAllowConfirmSameIa(true);
		}
		
	}
	
	
	private void setCustNotMatch(ImsSbOrderDTO relatedPcdOrder, AcqOrderCaptureDTO orderCapture, boolean isCustInfoConfirmed) {
		
		String custDocType = orderCapture.getCustomerDetailProfileLtsDTO().getDocType();
		String custDocNum = orderCapture.getCustomerDetailProfileLtsDTO().getDocNum();
		
		// Check under same customer;
		if (!(StringUtils.equalsIgnoreCase(relatedPcdOrder.getIdDocType(), custDocType) 
				&& StringUtils.equalsIgnoreCase(relatedPcdOrder.getIdDocNum(), custDocNum))) {
			relatedPcdOrder.setCustNotMatch(true);
			relatedPcdOrder.setAllowConfirmSameCust(true);

//			// Not allow confirm same customer if both doc. numbers are valid HKID. 
//			if (StringUtils.equals(relatedPcdOrder.getIdDocType(), "HKID") &&  StringUtils.equals(custDocType, "HKID")) {
//				if (Utility.validateHKID(relatedPcdOrder.getIdDocNum()) && Utility.validateHKID(custDocNum)) {
//					relatedPcdOrder.setAllowConfirmSameCust(false);		
//				}
//			}
		}

	}
	
}
