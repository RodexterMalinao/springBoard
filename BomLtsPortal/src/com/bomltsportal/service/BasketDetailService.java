package com.bomltsportal.service;

import java.util.List;

import com.bomltsportal.dto.OnlineBasketDTO;
import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.BasketDetailFormDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;

public interface BasketDetailService {

	List<OnlineBasketDTO> getOnlineBasketList(OrderCaptureDTO orderCapture, 
			boolean parallelExtension, String displayType, String locale);
	
	List<OnlineBasketDTO> getOnlineBasketHousingList(OrderCaptureDTO orderCapture, 
			boolean parallelExtension, String displayType, String locale, String housingType, String pServiceBoundary);
	
	BasketDetailDTO getBasket(String basketId, String locale, String displayType);
	
	String getSelectedDeviceType(OrderCaptureDTO orderCapture, String basketId);
	
	void setBasketDetailForm(OrderCaptureDTO orderCapture, BasketDetailFormDTO form, String locale, String basketId);
}
