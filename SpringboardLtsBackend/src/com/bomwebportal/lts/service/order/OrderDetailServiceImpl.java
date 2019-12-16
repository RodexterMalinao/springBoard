package com.bomwebportal.lts.service.order;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.lts.dao.order.OrderDetailDAO;
import com.bomwebportal.lts.dto.order.OrderIdDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;
import com.bomwebportal.lts.service.bom.ImsProfileService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.service.ServiceActionBase;
import com.pccw.util.spring.SpringApplicationContext;

public class OrderDetailServiceImpl implements OrderDetailService   {

	private final Log logger = LogFactory.getLog(getClass());
	
	private OrderDetailDAO orderDetailDao = null;
	private ServiceProfileLtsService serviceProfileLtsService = null;
	private ImsProfileService imsProfileService = null;
	
	public String getOrderType(String pOrderId) {
		try {
			return this.orderDetailDao.getOrderType(pOrderId);
		} catch (Exception ex) {
			logger.error("Fail in OrderDetailService.getOrderType()", ex);
			throw new AppRuntimeException(ex);
		}
	}
	
	
	public OrderIdDTO generateOrderId(String pShopCd, String pUser, String pMode, String pSrvType) {
		
		String orderPrefix = null;
		String orderSeq = null;
		String boc = null;
		try {
			if (StringUtils.equals(LtsBackendConstant.ORDER_MODE_STAFF, pMode)) {

				orderPrefix = this.orderDetailDao.orderIdPrefix(pUser);

			} else if (StringUtils.equals(LtsBackendConstant.ORDER_MODE_ONLINE_SALES, pMode)) {
				orderPrefix = "O";
			}
			if (StringUtils.equals(LtsBackendConstant.ORDER_PREFIX_CALL_CENTRE, orderPrefix)) {
				orderSeq = this.orderDetailDao.getCallCentreOrderSeq();
				boc = this.orderDetailDao.getUserBoc(pUser);
			} else if (StringUtils.equals(LtsBackendConstant.ORDER_PREFIX_PREMIER_TEAM, orderPrefix)) {
				orderSeq = this.orderDetailDao.getPremierTeamOrderSeq();
				boc = this.orderDetailDao.getUserBoc(pUser);
			} else if (StringUtils.equals(LtsBackendConstant.ORDER_PREFIX_DIRECT_SALES, orderPrefix)
					|| StringUtils.equals(LtsBackendConstant.ORDER_PREFIX_DIRECT_SALES_NOW_TV_QA, orderPrefix)) {
				orderSeq = this.orderDetailDao.getDirectSalesOrderSeq();
				boc = this.orderDetailDao.getShopBoc(pShopCd);
			} else if (StringUtils.equals(LtsBackendConstant.ORDER_PREFIX_RETAIL, orderPrefix) 
					|| StringUtils.equals(LtsBackendConstant.ORDER_PREFIX_ONLINE_SALES, orderPrefix)
					|| StringUtils.equals(LtsBackendConstant.ORDER_PREFIX_SERVICE_CENTRE, orderPrefix)) {
				orderSeq = this.orderDetailDao.getShopNextOrderSeq(pShopCd, pUser);
				boc = this.orderDetailDao.getShopBoc(pShopCd);
			}
			OrderIdDTO orderId = new OrderIdDTO();
			StringBuilder orderIdSb = new StringBuilder();
			orderIdSb.append(orderPrefix);
			orderIdSb.append(pShopCd);
			orderIdSb.append(pSrvType);
			orderIdSb.append(orderSeq);
			orderId.setOrderId(orderIdSb.toString());
			orderId.setBoc(boc);
			return orderId;
		} catch (Exception ex) {
			throw new AppRuntimeException(ex);
		}
	}	
	
	public String updateSignoffDate(String pOrderId) {
		try {
			return this.orderDetailDao.updateSignoffDate(pOrderId);
		} catch (Exception ex) {
			logger.error("Fail in OrderDetailService.updateSignoffDate()", ex);
			throw new AppRuntimeException(ex);
		}
	}
	
	public String updatePaymentTermDate(String pOrderId) {
		try {
			return this.orderDetailDao.updateTermDate(pOrderId);
		} catch (Exception ex) {
			logger.error("Fail in OrderDetailService.updateTermDate()", ex);
			throw new AppRuntimeException(ex);
		}
	}
	
	public int getNextDocSeq(String pOrderId, String pDocType) {
		try {
			return this.orderDetailDao.getMaxDocSeq(pOrderId, pDocType) + 1;
		} catch (Exception ex) {
			logger.error("Fail in OrderDetailService.getMaxDocSeqO()", ex);
			throw new AppRuntimeException(ex);
		}
	}
	
	public void clearCustNotMatchInd(String pOrderId) {
		try {
			this.orderDetailDao.clearCustNotMatchInd(pOrderId);
		} catch (Exception ex) {
			logger.error("Fail in OrderDetailService.clearCustNotMatchInd()", ex);
			throw new AppRuntimeException(ex);
		}
	}
	
	public void updateServicePendingBomOrder(String pSbOrderId, ServiceDetailDTO pSrvDtl) {
		
		PendingOrdStatusDetailDTO status = null;
		
		if (pSrvDtl instanceof ServiceDetailLtsDTO) {
			status = this.serviceProfileLtsService.getPendingOrder(pSrvDtl.getSrvNum(), pSrvDtl.getTypeOfSrv());
		} else if (pSrvDtl instanceof ServiceDetailOtherLtsDTO) {
			status = this.imsProfileService.getPendingOrder(pSrvDtl.getSrvNum());
		}
		if (status == null) {
			pSrvDtl.setPendingOcid(null);
			pSrvDtl.setPendingOcidSrd(null);
		} else {
			pSrvDtl.setPendingOcid(status.getOcid());
			pSrvDtl.setPendingOcidSrd(status.getSrd());
		}
//		String accountNum = null;
//		
//		if (pSrvDtl.getAccount() != null) {
//			accountNum = pSrvDtl.getAccount().getAcctNo();
//		}
		pSrvDtl.setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		ServiceActionBase serviceDetailService = SpringApplicationContext.getBean("serviceDetailService");
		serviceDetailService.doSave(pSrvDtl, pSrvDtl.getLastUpdBy(), pSbOrderId, null);
		pSrvDtl.setObjectAction(ObjectActionBaseDTO.ACTION_NO_CHANGE);
	}
	
	public void updateMustQCInd(String pOrderId, String mustQcInd, String pUserId) {
		try {
			this.orderDetailDao.updateMustQCInd(pOrderId, mustQcInd, pUserId);
		} catch (Exception ex) {
			logger.error("Fail in OrderDetailService.updateMustQCInd()", ex);
			throw new AppRuntimeException(ex);
		}
	}
	
	public String getWaiveQcStatus(String pOrderId) {
		try {
			return this.orderDetailDao.getWaiveQcStatus(pOrderId);
		} catch (Exception ex) {
			logger.error("Fail in OrderDetailService.getWaiveQcStatus()", ex);
			throw new AppRuntimeException(ex);
		}
	}
	
	public void updateWaiveQcStatus(String pOrderId, String waiveQcStatus, String pUserId) {
		try {
			this.orderDetailDao.updateWaiveQcStatus(pOrderId, waiveQcStatus, pUserId);
		} catch (Exception ex) {
			logger.error("Fail in OrderDetailService.updateWaiveQcStatus()", ex);
			throw new AppRuntimeException(ex);
		}
	}
	
	public String getWaiveQcCode(String pOrderId) {
		try {
			return this.orderDetailDao.getWaiveQcCode(pOrderId);
		} catch (Exception ex) {
			logger.error("Fail in OrderDetailService.getWaiveQcCode()", ex);
			throw new AppRuntimeException(ex);
		}
	}
	
	public OrderDetailDAO getOrderDetailDao() {
		return orderDetailDao;
	}

	public void setOrderDetailDao(OrderDetailDAO orderDetailDao) {
		this.orderDetailDao = orderDetailDao;
	}
	
	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
	}
	
	public ImsProfileService getImsProfileService() {
		return imsProfileService;
	}

	public void setImsProfileService(ImsProfileService imsProfileService) {
		this.imsProfileService = imsProfileService;
	}

}
