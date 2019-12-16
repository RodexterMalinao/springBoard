package com.bomwebportal.lts.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dto.ImsPendingOrderDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsAddressRolloutFormDTO;
import com.bomwebportal.lts.dto.form.LtsCustomerIdentificationFormDTO;
import com.bomwebportal.lts.dto.order.ImsSbOrderDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;
import com.bomwebportal.lts.service.order.ImsSbOrderService;
import com.bomwebportal.lts.service.order.SbOrderInfoLtsService;

public class LtsRelatedPcdOrderServiceImpl implements LtsRelatedPcdOrderService{

	protected final Log logger = LogFactory.getLog(getClass());
	
	protected ImsSbOrderService imsSbOrderService;
	protected SbOrderInfoLtsService sbOrderInfoLtsService;
	
	public SbOrderInfoLtsService getSbOrderInfoLtsService() {
		return sbOrderInfoLtsService;
	}

	public void setSbOrderInfoLtsService(SbOrderInfoLtsService sbOrderInfoLtsService) {
		this.sbOrderInfoLtsService = sbOrderInfoLtsService;
	}

	public ImsSbOrderService getImsSbOrderService() {
		return imsSbOrderService;
	}

	public void setImsSbOrderService(ImsSbOrderService imsSbOrderService) {
		this.imsSbOrderService = imsSbOrderService;
	}

	
	public ImsPendingOrderDTO getImsLatestPendingOrder(String fsa)  {
		
		try {
			List<ImsPendingOrderDTO> imsPendingOrderList = sbOrderInfoLtsService.getSbImsLatestPendingOrderBySrvNum(fsa);
			
			if (imsPendingOrderList == null || imsPendingOrderList.isEmpty()) {
				return null;
			}

			// RETURN IF OCID EXIST
			for (ImsPendingOrderDTO imsPendingOrder : imsPendingOrderList) {
				if (StringUtils.isNotEmpty(imsPendingOrder.getOcId())) {
					return imsPendingOrder;
				}
			}
			
			return imsPendingOrderList.get(0);
		}
		catch (Exception e) {
			logger.error(ExceptionUtils.getFullStackTrace(e));
			throw new AppRuntimeException(e.getMessage());
		}
	}
	
	public ImsSbOrderDTO retrievePcdSbOrder (String orderId, OrderCaptureDTO orderCapture, boolean isCustInfoConfirmed) {
		
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
	
	
	
	private void setIaNotMatch(ImsSbOrderDTO relatedPcdOrder, OrderCaptureDTO orderCapture) {
		
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		
		LtsAddressRolloutFormDTO addressRolloutForm = orderCapture.getLtsAddressRolloutForm();
		
		if(serviceProfile == null || addressRolloutForm == null) {
			return;
		}
		
		// Check under same I/A;		
		String targetIaSrvBdary = addressRolloutForm.isExternalRelocate() ? addressRolloutForm
				.getServiceBoundary() : serviceProfile.getAddress().getSrvBdry();
		String targetIaFloor = addressRolloutForm.isExternalRelocate() ? addressRolloutForm
				.getFloor() : serviceProfile.getAddress().getFloorNum();
		String targetIaFlat = addressRolloutForm.isExternalRelocate() ? addressRolloutForm
				.getFlat() : serviceProfile.getAddress().getUnitNum();

		String targetDistrictCode = addressRolloutForm.isExternalRelocate() ? addressRolloutForm
				.getDistrictCode() : serviceProfile.getAddress().getDistrictCd();
		
		String targetAreaCode = addressRolloutForm.isExternalRelocate() ? addressRolloutForm
				.getAreaCode() : serviceProfile.getAddress().getAreaCd();
				
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
	
	
	private void setCustNotMatch(ImsSbOrderDTO relatedPcdOrder, OrderCaptureDTO orderCapture, boolean isCustInfoConfirmed) {
		
		ServiceDetailProfileLtsDTO serviceProfile = orderCapture.getLtsServiceProfile();
		
		if(serviceProfile == null) {
			return;
		}
		
		LtsCustomerIdentificationFormDTO custIdentForm = orderCapture.getLtsCustomerIdentificationForm();
		
		String custDocType = isCustInfoConfirmed ? custDocType = custIdentForm
				.getDocType() : serviceProfile.getPrimaryCust().getDocType();
		String custDocNum = isCustInfoConfirmed ? custDocNum = custIdentForm
				.getId() : serviceProfile.getPrimaryCust().getDocNum();
		
		
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
