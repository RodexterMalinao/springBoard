package com.bomwebportal.mob.ccs.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.VasDetailDAO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dao.CodeLkupDAO;
import com.bomwebportal.mob.ccs.dao.MobCcsPaymentTransDAO;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentNewTransUI;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentTransDTO;
import com.bomwebportal.mob.ccs.dto.MobCcsPaymentUpfrontDTO;
import com.bomwebportal.mob.ccs.dto.OnlinePaymentTxn;

@Transactional(readOnly = true)
public class MobCcsPaymentAdminServiceImpl implements MobCcsPaymentAdminService {
	protected final Log logger = LogFactory.getLog(getClass());
	private CodeLkupDAO codeLkupDao;
	private MobCcsPaymentTransDAO mobCcsPaymentTransDAO;
	private VasDetailDAO vasDetailDao;
	
	public CodeLkupDAO getCodeLkupDao() {
		return codeLkupDao;
	}

	public void setCodeLkupDao(CodeLkupDAO codeLkupDao) {
		this.codeLkupDao = codeLkupDao;
	}

	public MobCcsPaymentTransDAO getMobCcsPaymentTransDAO() {
		return mobCcsPaymentTransDAO;
	}

	public void setMobCcsPaymentTransDAO(
			MobCcsPaymentTransDAO mobCcsPaymentTransDAO) {
		this.mobCcsPaymentTransDAO = mobCcsPaymentTransDAO;
	}
	public VasDetailDAO getVasDetailDao() {
		return vasDetailDao;
	}

	public void setVasDetailDao(VasDetailDAO vasDetailDao) {
		this.vasDetailDao = vasDetailDao;
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<MobCcsPaymentUpfrontDTO> getMobCcsUpfrontPaymentDTOList(
			String order_id) {
		// TODO Auto-generated method stub
		try {
			logger.info("getMobCcsUpfrontPaymentDTOList() is called @ MobCcsPaymentAdminServiceImpl");
			return mobCcsPaymentTransDAO.getMobCcsUpfrontPaymentDTOList(order_id);

		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsUpfrontPaymentDTOList()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<CodeLkupDTO> getCodeLkupDTOALL(String codeType) {
		// TODO Auto-generated method stub
		try {
			logger.info("getCodeLkupDTOALL() is called @ MobCcsPaymentAdminServiceImpl");
			return codeLkupDao.getCodeLkupDTOALL(codeType);

		} catch (DAOException de) {
			logger.error("Exception caught in getCodeLkupDTOALL()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<MobCcsPaymentTransDTO> getMobCcsPaymentTransDTOList(
			Date startDate, Date endDate, String paymentMethod, String mrt) {
		// TODO Auto-generated method stub
		try {
			logger.info("getMobCcsPaymentTransDTOList() is called @ MobCcsPaymentAdminServiceImpl");
			return mobCcsPaymentTransDAO.getMobCcsPaymentTransDTOList(
					startDate, endDate, paymentMethod, mrt);

		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsPaymentTransDTOList()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<MobCcsPaymentTransDTO> getMobCcsPaymentTransDTOByOrderId(String orderId) {
		try {
			logger.info("getMobCcsPaymentTransDTOByOrderId() is called @ MobCcsPaymentAdminServiceImpl");
			return mobCcsPaymentTransDAO.getMobCcsPaymentTransDTOByOrderId(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getMobCcsPaymentTransDTOByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public void insertMobCcsPaymentNewTransUI(MobCcsPaymentNewTransUI dto) {
		// TODO Auto-generated method stub
		try {
			logger.info("insertMobCcsPaymentNewTransUI() is called @ MobCcsPaymentAdminServiceImpl");
			mobCcsPaymentTransDAO.insertMobCcsPaymentNewTransUI(dto);

		} catch (DAOException de) {
			logger.error("Exception caught in insertMobCcsPaymentNewTransUI()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public List<OnlinePaymentTxn> getOnlinePaymentTransDTOByOrderId(String orderId) {
		try {
			logger.info("getOnlinePaymentTransDTOByOrderId() is called @ MobCcsPaymentAdminServiceImpl");
			return mobCcsPaymentTransDAO.getOnlinePaymentTransDTOByOrderId(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getOnlinePaymentTransDTOByOrderId()", de);
			throw new AppRuntimeException(de);
		}
	}

	public Double getVasHSAmt(String orderId) {
		try {
			Double result = vasDetailDao.getVasHSAmt(orderId);
			if (result == null) {
				return new Double(0);
			} else {
				return result;
			}
		} catch (DAOException e) {
			logger.error("Exception caught in getVasHSAmt()", e);
			throw new AppRuntimeException(e);
		}
	}

	public BigDecimal getChangeSimChargeForOrder(String orderId) {
		try {
			logger.info("getDepositAmountForOrder() is called in MobCcsPaymentAdminServiceImpl");
			return vasDetailDao.getChangeSimChargeForOrder(orderId);
		} catch (DAOException de) {
			logger.error("Exception caught in getChangeSimChargeForOrder()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public BigDecimal getPrePaymentForOrder(String orderId) {
		try {
			BigDecimal amount = vasDetailDao.getPrePaymentForOrder(orderId);
			return amount;
		} catch (DAOException e) {
			logger.error("Exception caught in getPrePaymentForOrder(String orderId)", e);
		}
		return null;
	}
	
	public BigDecimal getBasketHsAmtForOrder(String orderId) {
		try {
			BigDecimal amount = vasDetailDao.getBasketHsAmtForOrder(orderId);
			return amount;
		} catch (DAOException e) {
			logger.error("Exception caught in getBasketHsAmtForOrder(String orderId)", e);
		}
		return null;
	}


}
