package com.bomwebportal.mob.ccs.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentNewTransUI;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentTransDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentUpfrontDTO;
import com.bomwebportal.mob.ccs.dto.OnlinePaymentTxn;

public interface MobCcsPaymentAdminService {
	public List<CodeLkupDTO> getCodeLkupDTOALL (String codeType);
	public List<MobCcsPaymentTransDTO> getMobCcsPaymentTransDTOList(
			Date startDate, Date endDate, String paymentMethod, String mrt);
	public List<MobCcsPaymentTransDTO> getMobCcsPaymentTransDTOByOrderId(String orderId);
	public List<MobCcsPaymentUpfrontDTO> getMobCcsUpfrontPaymentDTOList(
			String order_id);
	public void insertMobCcsPaymentNewTransUI(MobCcsPaymentNewTransUI dto);
	public List<OnlinePaymentTxn> getOnlinePaymentTransDTOByOrderId(String orderId);
	public Double getVasHSAmt(String orderId);
	public BigDecimal getChangeSimChargeForOrder(String orderId);
	public BigDecimal getPrePaymentForOrder(String orderId) ;
	public BigDecimal getBasketHsAmtForOrder(String orderId);
}
