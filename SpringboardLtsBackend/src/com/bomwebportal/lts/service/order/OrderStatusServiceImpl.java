package com.bomwebportal.lts.service.order;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.order.OrderStatusDAO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class OrderStatusServiceImpl implements OrderStatusService {

	private OrderStatusDAO orderStatusDao;

	
	public void initializeStatus(String pOrderId, String pDtlId, String pUser) {
		
		try {
			this.orderStatusDao.insertStatus(pOrderId, pDtlId, pUser);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to initialize status dtl_id " + pDtlId, ex);
		}
	}
	
	public void setClosedStatus(String pOrderId, String pDtlId, String pUser) {
		
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_CLOSED, null, pUser, null);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update signoff dtl_id " + pDtlId, ex);
		}
	}
	
	public void setSubmissionStatus(String pOrderId, String pDtlId, String pUser, String pWqType) {
		
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_SUBMITTED, null, pUser, pWqType);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update status after submission dtl_id " + pDtlId, ex);
		}
	}
	
	public void setCancelStatus(String pOrderId, String pDtlId, String pReaCd, String pUser, String pWqType) {
		
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_CANCELLED, pReaCd, pUser, pWqType);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update status after cancellation dtl_id " + pDtlId, ex);
		}
	}
	
	public void setSuspendStatus(String pOrderId, String pDtlId, String pReaCd, String pUser, String pWqType) {
		
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_SUSPENDED, pReaCd, pUser, pWqType);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update status after suspend dtl_id " + pDtlId, ex);
		}
	}
	
	public void setPendingBomStatus(String pOrderId, String pDtlId, String pUser, String pWqType) {
		
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_PENDING_BOM, null, pUser, pWqType);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update status after suspend dtl_id " + pDtlId, ex);
		}
	}
	
	public void setPendingApprovalStatus(String pOrderId, String pDtlId, String pUser, String pWqType) {
		
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_PENDING_APPROVAL, null, pUser, pWqType);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update approval status dtl_id " + pDtlId, ex);
		}
	}
	
	public void setPendingSignoffStatus(String pOrderId, String pDtlId, String pUser) {
		
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_PENDING_SIGNOFF, null, pUser, null);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update signoff dtl_id " + pDtlId, ex);
		}
	}
	
	public OrderStatusDTO[] getStatus(String pOrderId) {

		try {
			return this.orderStatusDao.getStatus(pOrderId);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to get status order id " + pOrderId, ex);
		}
	}
	
	public OrderStatusDTO[] getStatusHistory(String pOrderId, String pDtlId) {
		
		try {
			return this.orderStatusDao.getStatusHistory(pOrderId, pDtlId);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to get status hist order id " + pOrderId + " dtl_id " +  pDtlId, ex);
		}
	}
		
	public void setAwaitPrepaymentStatus(String pOrderId, String pDtlId, String pUser) {
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, 
					LtsBackendConstant.ORDER_STATUS_AWAIT_PREPAYMENT, null, pUser, null);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update setAwaitPrepaymentStatus dtl_id " + pDtlId, ex);
		}		
	}
	
	public void setAwaitQCStatus(String pOrderId, String pDtlId, String pUser, String pReaCd) {
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, 
					LtsBackendConstant.ORDER_STATUS_AWAIT_QC, null, pUser, null);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update setAwaitQCStatus dtl_id " + pDtlId, ex);
		}		
	}
	
	public String getSignoffDate(String pOrderId) {
		return this.orderStatusDao.getSignoffDate(pOrderId);		
	}
	
	public String getPrepaymentSettleDate(String pOrderId) {
		return this.orderStatusDao.getPrepaymentSettledDate(pOrderId);		
	}
	
	public OrderStatusDAO getOrderStatusDao() {
		return orderStatusDao;
	}

	public void setOrderStatusDao(OrderStatusDAO orderStatusDao) {
		this.orderStatusDao = orderStatusDao;
	}
	
	public void setPendingPrepaymentStatus(String pOrderId, String pDtlId, String pUser, String pWqType) {
		
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_AWAIT_PREPAYMENT, LtsBackendConstant.ORDER_STATUS_REASON_AWAIT_PREPAY, pUser, pWqType);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update setPendingPrepaymentStatus", ex);
		}
	}

	public void setPendingNameNotMatchApprovalStatus(String pOrderId, String pDtlId, String pUser, String pWqType) {
		
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_AWAIT_PREPAYMENT, LtsBackendConstant.ORDER_STATUS_REASON_AWAIT_APPROVAL, pUser, pWqType);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update setPendingNameNotMatchApprovalStatus", ex);
		}
	}
	
	public void setPendingQcStatus(String pOrderId, String pDtlId, String pUser, String pWqType) {
		
		try {
			this.orderStatusDao.updateSbStatus(pOrderId, pDtlId, LtsBackendConstant.ORDER_STATUS_AWAIT_QC, null, pUser, pWqType);
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to update setPendingQcStatus", ex);
		}
	}

	public boolean noSuspendReason(String pOrderId, String pDtlId){
		try {
			return this.orderStatusDao.getNumOfReasonCd(pOrderId, pDtlId,"S") == 0;
		} catch (DAOException ex) {
			throw new AppRuntimeException("Fail to get noSuspendReason", ex);
		}
	}
	
}
