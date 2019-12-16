package com.bomwebportal.lts.service.order;

import java.util.ArrayList;
import java.util.Arrays;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.lts.dao.order.PaymentLtsDAO;
import com.bomwebportal.lts.dto.order.PaymentMethodDetailLtsDTO;
import com.bomwebportal.service.ServiceActionImplBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.db.DaoBase;

public class PaymentLtsServiceImpl extends ServiceActionImplBase {

	public PaymentLtsServiceImpl() {
		this.selectConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "acctNo"));
		this.updateDeleteConditionFieldList = new ArrayList<String>(Arrays.asList("orderId", "acctNo", "payMtdType"));
	}

	public ObjectActionBaseDTO[] generateSelectObjects(int pLength) {

		PaymentMethodDetailLtsDTO[] payments = new PaymentMethodDetailLtsDTO[pLength];
		
		for (int i=0; i<pLength; ++i) {
			payments[i] = new PaymentMethodDetailLtsDTO();
		}
		return payments;
	}

	public ObjectActionBaseDTO convertToDto(DaoBase pDaoBase) {
		
		PaymentMethodDetailLtsDTO payment = new PaymentMethodDetailLtsDTO();
		PaymentLtsDAO paymentDao = (PaymentLtsDAO)pDaoBase;
		payment.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
		this.DAO2DTO(paymentDao, payment);
		payment.setTermDate(DateFormatHelper.dateConvertFromDAO2DTO(paymentDao.getTermDate()));
		payment.setAutopayAppDate(DateFormatHelper.dateConvertFromDAO2DTO(paymentDao.getAutopayAppDate()));
		return payment;
	}
	
	protected void setDTO2DAODetails(ObjectActionBaseDTO pBaseDTO, Object... args) {
		
		PaymentMethodDetailLtsDTO payMethod = (PaymentMethodDetailLtsDTO)pBaseDTO;
		PaymentLtsDAO payDao = (PaymentLtsDAO)this.dao;
		this.DTO2DAO(payMethod, payDao);
		payDao.setOrderId((String)args[0]);
		payDao.setAcctNo((String)args[1]);
		payDao.setCustNo((String)args[2]);
		payDao.setTermDate(DateFormatHelper.dateConvertFromDTO2DAO(payMethod.getTermDate()));
		payDao.setAutopayAppDate(DateFormatHelper.dateConvertFromDTO2DAO(payMethod.getAutopayAppDate()));
	}

	protected void setDAO2DTODetails(Object... pArgs) {
		((PaymentLtsDAO)this.dao).setOrderId((String)pArgs[0]);
		((PaymentLtsDAO)this.dao).setAcctNo((String)pArgs[1]);
	}
}
