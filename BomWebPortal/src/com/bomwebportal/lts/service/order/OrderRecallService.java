package com.bomwebportal.lts.service.order;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.FsaDetailDTO;
import com.bomwebportal.lts.dto.ModemTechnologyAissgnDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.LtsPaymentFormDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.ServiceDetailProfileLtsDTO;

public interface OrderRecallService {
	
	OrderCaptureDTO recallOrder(String sbOrderId, boolean pIsEquiry, BomSalesUserDTO bomSalesUser);
	ServiceDetailProfileLtsDTO createDummyServiceDetailProfile(SbOrderDTO sbOrder);
	ModemTechnologyAissgnDTO createModemTechnologyAssign(SbOrderDTO sbOrder);
	LtsPaymentFormDTO createLtsPaymentForm(SbOrderDTO sbOrder);
}
