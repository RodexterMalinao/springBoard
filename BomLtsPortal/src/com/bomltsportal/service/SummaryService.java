package com.bomltsportal.service;

import java.util.List;

import com.bomltsportal.dto.OrderCaptureDTO;
import com.bomltsportal.dto.form.SummaryFormDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.order.AddressDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface SummaryService {
	String getDisplayAddress(AddressDetailLtsDTO pAddress);
	String getDisplayAddressWithoutFlatFoor(AddressDetailLtsDTO pAddress);
	void setSummary(OrderCaptureDTO orderCapture, SbOrderDTO sbOrder, SummaryFormDTO summaryFormDTO, String locale);
	int calculateItemPaymentAmount(ItemDetailLtsDTO[] itemDetails);
	int calculateItemPaymentAmount(List<ItemDetailDTO> itemList);
//	int calculateItemPaymentAmount(ItemDetailLtsDTO[] itemDetails, boolean isRecurrentAmt);
//	int calculateItemPaymentAmount(List<ItemDetailDTO> itemList, boolean isRecurrentAmt);
}
