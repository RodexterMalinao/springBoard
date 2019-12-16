package com.bomwebportal.mob.ccs.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.IssueBankDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.MobCcsPaymentTransDAO;
import com.bomwebportal.mob.ccs.dao.PaymentUpfrontDAO;
import com.bomwebportal.mob.ccs.dto.CreditCardInstDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsMagentoCouponDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentTransDTO;
import com.bomwebportal.mob.ccs.dto.ui.PaymentUpFrontUI;

@Transactional(readOnly=true)
public class MobCcsPaymentUpfrontServiceImpl implements MobCcsPaymentUpfrontService {

	protected final Log logger = LogFactory.getLog(getClass());
	
	private PaymentUpfrontDAO paymentUpfrontDao;
	
	private MobCcsPaymentTransDAO mobCcsPaymentTransDAO;
	
	public MobCcsPaymentTransDAO getMobCcsPaymentTransDAO() {
		return mobCcsPaymentTransDAO;
	}

	public void setMobCcsPaymentTransDAO(MobCcsPaymentTransDAO mobCcsPaymentTransDAO) {
		this.mobCcsPaymentTransDAO = mobCcsPaymentTransDAO;
	}

	public PaymentUpfrontDAO getPaymentUpfrontDao() {
		return paymentUpfrontDao;
	}

	public void setPaymentUpfrontDao(PaymentUpfrontDAO paymentUpfrontDao) {
		this.paymentUpfrontDao = paymentUpfrontDao;
	}


	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public void insertBomPaymentUpfront(PaymentUpFrontUI dto){
		
		try{
			logger.info("insertBomPaymentUpfront() is called in MobCcsPaymentUpfrontServiceImpl");
			paymentUpfrontDao.insertBomwebUpfrontPayment(dto);
		}catch (DAOException de) {
			logger.error("Exception caught in insertBomPaymentUpfront()", de);
			throw new AppRuntimeException(de);
		}
	}
	/*
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public void deleteBomPaymentUpfront(String orderId){
		
		try{
			logger.info("deleteBomCustomerProfile() is called in CustomerProfileServiceImpl");
			customerProfileDao.deleteBomOrderContact(orderId);
		}catch (DAOException de) {
			logger.error("Exception caught in deleteBomCustomerProfile()", de);
			throw new AppRuntimeException(de);
		}
	}
	*/
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public PaymentUpFrontUI getPaymentUpfront (String orderId ){
		try {
			return paymentUpfrontDao.getBomwebUpfrontPayment(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getPaymentUpfront()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<CreditCardInstDTO> getCreditCardInstList (String bankCode, String upfrontAmt, int channelId){
		try {
			return paymentUpfrontDao.getCreditCardInstList(bankCode, upfrontAmt, channelId);

		} catch (DAOException de) {
			logger.error("Exception caught in getCreditCardInstList()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	@Transactional(readOnly=false, propagation=Propagation.REQUIRED, rollbackFor=AppRuntimeException.class)
	public List<IssueBankDTO> getCreditCardInstBankList (String upfrontAmt, int channelId){
		try {
			return paymentUpfrontDao.getCreditCardInstBankList(upfrontAmt, channelId);

		} catch (DAOException de) {
			logger.error("Exception caught in getCreditCardInstList()", de);
			throw new AppRuntimeException(de);
		}

	}
	
	
	public int getMobCcsPaymentTransCCCntByOrderId (String orderId ){
		try {
			return mobCcsPaymentTransDAO.getMobCcsPaymentTransCCCntByOrderId(orderId);

		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsPaymentTransCCCntByOrderId()", de);
			throw new AppRuntimeException(de);
		}

	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)	
	public void insertPaymentTrans (MobCcsPaymentTransDTO dto) {
		try {
			mobCcsPaymentTransDAO.insertPaymentTrans(dto);
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}
	public Double getPaidAmtStsDelivery (String orderId){//Athena 20131111 online sales report
		try {
			return mobCcsPaymentTransDAO.getPaidAmtStsDeliveryByOrderId(orderId);

		} catch (Exception de) {
			logger.error("Exception caught in getMobCcsPaymentTransCCCntByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}
	public Double getTotalPaidAmtStsDelivery (String orderId){//Athena 20131111 online sales report
		try {
			return mobCcsPaymentTransDAO.getTotalPaidAmtStsDeliveryByOrderId(orderId);

		} catch (Exception de) {
			logger.error("Exception caught in getMobCcsPaymentTransCCCntByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public Double getStsDeliveryChargeByOrderId (String orderId, String posItemCd){
		try {
			return mobCcsPaymentTransDAO.getStsDeliveryChargeByOrderId(orderId,posItemCd);
		} catch (Exception de) {
			logger.error("Exception caught in getStsDeliveryChargeByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public String getStandaloneHandSetPaymentUpfront (String itemId, Date appDate) {
		try {
			return paymentUpfrontDao.getStandaloneHandSetPaymentUpfront(itemId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in getPaymentUpfront()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public MobCcsMagentoCouponDTO getOrderCouponInfo(String orderId) {
		try {
			return mobCcsPaymentTransDAO.getOrderCouponInfo(orderId);
		} catch (Exception de) {
			logger.error("Exception caught in getPaymentUpfront()", de);
			throw new AppRuntimeException(de);
		}	
		
	}
	
}

