package com.bomwebportal.lts.util.acq;


import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.AcqOrderCaptureDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.acq.LtsAcqPaymentMethodFormDTO.PaymentMethodDtl;
import com.bomwebportal.lts.util.LtsConstant;

public class LtsAcqSbOrderHelper {
	
	private static final String IDD_ITEM = "IDD";

	public static boolean containIddItem(ArrayList<ItemDetailDTO> pItemList){
		boolean containIddItem = false;
		for(ItemDetailDTO item : pItemList){
			if(item.isSelected()){
				if((StringUtils.isNotBlank(item.getItemType()) && item.getItemType().contains(IDD_ITEM))
						|| (StringUtils.isNotBlank(item.getItemDesc()) && item.getItemDesc().contains(IDD_ITEM))
						|| (StringUtils.isNotBlank(item.getNature()) && item.getNature().contains(IDD_ITEM))
						|| (StringUtils.isNotBlank(item.getItemDisplayHtml()) && item.getItemDisplayHtml().contains(IDD_ITEM))){
					containIddItem = true;
				}
			}
		}
		
		return containIddItem;
	}
	
	public static boolean isCreditCardPrepayment(AcqOrderCaptureDTO orderCapture){	
		if(orderCapture != null && orderCapture.getLtsAcqPaymentMethodFormDTO() != null 
			&& orderCapture.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList() != null){
			for(PaymentMethodDtl payInfo : orderCapture.getLtsAcqPaymentMethodFormDTO().getPaymentMethodDtlList()){
				if(LtsConstant.PAYMENT_TYPE_CREDIT_CARD.equals(payInfo.getNewPayMethodType())){
					return true;
				}
			}
		}			

		return false;
	}
	
	public static boolean isDummyCustomer(String custNum) {
		return custNum.startsWith(LtsConstant.DUMMY_CUST_NUM_PREFIX);
	}
	
	public static boolean isDummyAccount(String acctNum) {
		return acctNum.startsWith(LtsConstant.DUMMY_ACCT_NUM_PREFIX);
	}
	
}
