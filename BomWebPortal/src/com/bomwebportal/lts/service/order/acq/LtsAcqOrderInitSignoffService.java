package com.bomwebportal.lts.service.order.acq;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.ValidationResultDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;

public interface LtsAcqOrderInitSignoffService {

	public ValidationResultDTO initialSignoff(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser);
	public String saveFormsAndEmailAndSms(SbOrderDTO sbOrder, String userId);
	public boolean checkAndRegCspForSbOrder(SbOrderDTO sbOrder, String username);
	public boolean regIguardCarecashForSbOrder(SbOrderDTO sbOrder, String username);
	public void setSignOffDate(SbOrderDTO sbOrder);
	public String updateCustDataPrivayInfo(SbOrderDTO sbOrder, String userId);
}
