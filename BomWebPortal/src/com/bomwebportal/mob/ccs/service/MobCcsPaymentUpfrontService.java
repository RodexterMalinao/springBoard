package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.mob.ccs.dto.CreditCardInstDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMagentoCouponDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentTransDTO;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;

public interface MobCcsPaymentUpfrontService {
	
	public void insertBomPaymentUpfront(PaymentUpFrontUI dto);
	
	/*public void deleteBomPaymentUpfront(String orderId);*/
	
	public PaymentUpFrontUI getPaymentUpfront (String orderId );
	
	public List<CreditCardInstDTO> getCreditCardInstList (String bankCode, String upfrontAmt, int channelId);
	
	public List<IssueBankDTO> getCreditCardInstBankList (String upfrontAmt, int channelId);
	
	public int getMobCcsPaymentTransCCCntByOrderId (String orderId);
	
	public void insertPaymentTrans (MobCcsPaymentTransDTO dto);
	
	public Double getPaidAmtStsDelivery (String orderId);//Athena 20131111 online sales report
	
	public Double getTotalPaidAmtStsDelivery (String orderId);//Athena 20131111 online sales report
	
	public Double getStsDeliveryChargeByOrderId (String orderId, String posItemCd);
	
	public String getStandaloneHandSetPaymentUpfront (String itemId, Date appDate);
	
	public MobCcsMagentoCouponDTO getOrderCouponInfo(String orderId);
	
}
