package com.bomwebportal.lts.service.order;

import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.lts.dto.order.AmendAppointmentLtsDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.AmendPaymentDTO;

public interface AmendmentSubmitService {

	@Transactional
	public abstract void submitAmendment(AmendLtsDTO pAmend, String pUser, String pShopCd);
	
	public void saveAmendmentToOrder(AmendLtsDTO pAmend, String pUser);
	public String generateAppointmentAmendRemark(AmendAppointmentLtsDTO pAmendDtl, boolean isGenerateAmendForm);
	public String generatePaymentAmendRemark(AmendPaymentDTO pAmendDtl);
	public String generateAmendmentRemark(AmendLtsDTO pAmend);

}