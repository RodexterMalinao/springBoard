package com.bomwebportal.service;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dao.BomBOrdDtlDAO;
import com.bomwebportal.dao.BomwebDnPoolDAO;
import com.bomwebportal.dao.BomwebOrderLatestStatusDAO;
import com.bomwebportal.dao.BomwebOrderServiceLtsDAO;
import com.bomwebportal.dao.ImsBomBCustomerDAO;
import com.bomwebportal.dao.ImsWorkQueueStatusDAO;
import com.bomwebportal.dao.OfferActionDAO;
import com.bomwebportal.dto.ImsCustomerDTO;
import com.bomwebportal.dto.ImsWqDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.service.lts.activity.WorkQueueStatusChangeService;
import com.pccw.util.db.OracleSelectHelper;
import com.pccw.util.spring.SpringApplicationContext;

public class AcknowledgeSbServiceImpl implements AcknowledgeSbService {
	
	private static final String SB_STATUS_IMS_APPROVAL_APPROVED = "31";
	private static final String SB_STATUS_IMS_APPROVAL_REJECTED = "32";
	private static final String onlineSales_Diff_Name_Sign_Off = "00";
	private static final String onlineSales_Diff_Name_Cancel = "10";
	private static final String onlineSales_C_Order_Closed = "00";
	public static final String IMS_OnlineSales_C_Order = "R005";
	public static final String IMS_OnlineSales_diff_name = "R004";	
	public static final String IMS_OnlineSales_both_c_order_and_diff_name = "R006";	
//	private static final String SB_STATUS_LTS_PENDING_APPROVAL = "G";
	private static final String SB_STATUS_LTS_APPROVAL_APPROVED = "S";
	private static final String SB_STATUS_LTS_APPROVAL_APPROVED_SUSPEND_REASON = "APPRACT";
	private static final String SB_STATUS_LTS_APPROVAL_REJECTED = "R";
	private static final String SB_STATUS_LTS_CANCEL = "C";
	private static final String DN_POOL_STATUS_NORMAL = "N";
	private static final String LOB_IMS = "IMS";
	private static final String LOB_LTS = "LTS";
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private ImsWorkQueueStatusDAO imsdao;


	private BomwebDnPoolDAO bomwebDnPoolDAO;
	private OfferActionDAO offerActionDao;
	
	/* (non-Javadoc)
	 * @see com.bomwebportal.service.AcknowledgeSbService#sbOrderApprovalWqApproved(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void sbOrderApprovalWqApproved(String pSbId, String pSbDtlId) throws Exception {
		String lob = getSbLob(pSbId);
		logger.debug("sbOrderApprovalWqApproved");
		logger.debug(lob + " SB Order " + pSbId + " is approved");
		if (LOB_IMS.equals(lob)) {
			try {
				imsdao.updateApprovalStatus(pSbId, SB_STATUS_IMS_APPROVAL_APPROVED);
			} catch (DAOException de) {
				logger.error("Exception caught in sbOrderApprovalWqApproved()", de);
				throw new AppRuntimeException(de);
			}
		} else if (LOB_LTS.equals(lob)) {
			updateLtsApprovalStatus(pSbId, SB_STATUS_LTS_APPROVAL_APPROVED);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bomwebportal.service.AcknowledgeSbService#sbOrderApprovalWqRejected(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void sbOrderApprovalWqRejected(String pSbId, String pSbDtlId) throws Exception {
		String lob = getSbLob(pSbId);
		logger.debug("sbOrderApprovalWqRejected");
		logger.debug(lob + " SB Order " + pSbId + " is rejected");
		if (LOB_IMS.equals(lob)) {
			try {
				imsdao.updateApprovalStatus(pSbId, SB_STATUS_IMS_APPROVAL_REJECTED);
			} catch (DAOException de) {
				logger.error("Exception caught in sbOrderApprovalWqRejected()", de);
				throw new AppRuntimeException(de);
			}
		} else if (LOB_LTS.equals(lob)) {
			updateLtsApprovalStatus(pSbId, SB_STATUS_LTS_APPROVAL_REJECTED);
		}
	}

	/* (non-Javadoc)
	 * @see com.bomwebportal.service.AcknowledgeSbService#sbOrderFullWqCompleted(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void sbOrderFullWqCompleted(String pSbId, String pSbDtlId, String pOcId) throws Exception {
		logger.debug("sbOrderFullWqCompleted");
		logger.debug("sb order " + pSbId + " work queue completed with ocid " + pOcId);
		try {
			imsdao.updateBomOrderId(pSbId, pOcId);
		} catch (DAOException de) {
			logger.error("Exception caught in sbOrderFullWqCompleted()", de);
			throw new AppRuntimeException(de);
		}
		
		try {
			SpringApplicationContext.getBean(BomBOrdDtlDAO.class).updateSbIdToBom(pSbId, pOcId);
		} catch (DAOException de) {
			logger.error("Exception caught in sbOrderFullWqCompleted()", de);
			throw new AppRuntimeException(de);
		}
	}

	private String getSbLob(String pSbId) throws Exception {
		return OracleSelectHelper.getSqlFirstRowColumnString (
				"BomWebPortalDS",
				"select LOB from bomweb_order where order_id = ?",
				new Object[] { pSbId });
	}

	private void updateLtsApprovalStatus(String pSbId, String pApprovalStatus) throws Exception {
		BomwebOrderLatestStatusDAO criteria = SpringApplicationContext.getBean(BomwebOrderLatestStatusDAO.class);
		criteria.setSearchKey("orderId", pSbId);
		BomwebOrderLatestStatusDAO[] daos = (BomwebOrderLatestStatusDAO[]) criteria.doSelect(null, null);
		if (ArrayUtils.isEmpty(daos)) {
			return;
		}
		BomwebOrderServiceLtsDAO orderServiceLtsDAO = null;
		for (BomwebOrderLatestStatusDAO dao : daos) {
			dao.setSbStatus(pApprovalStatus);
			dao.setLastUpdBy("WQ");
			if (SB_STATUS_LTS_APPROVAL_APPROVED.equals(pApprovalStatus)) {
				dao.setReaCd(SB_STATUS_LTS_APPROVAL_APPROVED_SUSPEND_REASON);
			}
			dao.doUpdate();
			
			if (!SB_STATUS_LTS_APPROVAL_APPROVED.equals(pApprovalStatus)) {
				continue;
			}
			
			orderServiceLtsDAO = SpringApplicationContext.getBean(BomwebOrderServiceLtsDAO.class);
			orderServiceLtsDAO.setOrderId(dao.getOrderId());
			orderServiceLtsDAO.setDtlId(dao.getDtlId());
			orderServiceLtsDAO.doSelect();
			
			if (StringUtils.isBlank(orderServiceLtsDAO.getOracleRowID())) {
				continue;
			}
			
			orderServiceLtsDAO.setPendingApprovalCd(null);
			orderServiceLtsDAO.setLastUpdBy("WQ");
			orderServiceLtsDAO.doUpdate();
		}
		if (SB_STATUS_LTS_APPROVAL_APPROVED.equals(pApprovalStatus)) {
			this.offerActionDao.setLtsPenaltyHandling(pSbId, "MW", "Y", "WQ");
			this.offerActionDao.setImsPenaltyHandling(pSbId, "MW", "Y", "WQ");
			this.offerActionDao.setApprovedInd(pSbId, "Y", "WQ");
		} else {
			this.offerActionDao.setLtsPenaltyHandling(pSbId, "MG", null, "WQ");
			this.offerActionDao.setImsPenaltyHandling(pSbId, "MG", "N", "WQ");
			this.offerActionDao.setApprovedInd(pSbId, null, "WQ");
		}
	}
	

	
	/* (non-Javadoc)
	 * @see com.bomwebportal.service.AcknowledgeSbService#sbOrderApprovalWqApproved(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void onlineSalesDiffNameSignOff(String pSbId, String pSbDtlId) throws Exception {
		logger.info("SB Order " + pSbId + " onlineSales Diff Name is Signed Off");
		try {
			ImsCustomerDTO cust_info = new ImsCustomerDTO();
			cust_info = imsdao.getCustInfoByOrderID(pSbId);
			String cust_num = imsBomdao.getCustNumByCustInfo(cust_info);
			if(cust_num!=null){
				logger.info("cust_num:"+cust_num);
				imsdao.updateCustNo(pSbId, cust_num);
			}else{
				logger.info("cust_num is null for updateCustNo when DiffNameSignOff");
			}
			imsdao.updateonlineSalesDiffNameSignOff(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in onlineSalesDiffNameSignOff()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bomwebportal.service.AcknowledgeSbService#sbOrderApprovalWqApproved(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void onlineSalesDiffNameCancel(String pSbId, String pSbDtlId) throws Exception {
		logger.info("SB Order " + pSbId + " onlineSales Diff Name is Cancelled");
		try {
			imsdao.updateOnlineSalesDiffNameCancel(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in onlineSalesDiffNameCancel()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bomwebportal.service.AcknowledgeSbService#sbOrderApprovalWqApproved(java.lang.String, java.lang.String)
	 */
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void onlineSalesCOrderClosed(String pSbId, String pSbDtlId, String pOcId) throws Exception {
		logger.info("SB Order " + pSbId + " onlineSales C Order is Closed, with ocid " + pOcId);
		try {
			imsdao.updateonlineSalesCOrderClosed(pSbId);
			imsdao.updateBomOrderId(pSbId, pOcId);
		} catch (DAOException de) {
			logger.error("Exception caught in onlineSalesCOrderClosed()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void updateCOrderIgnored(String pSbId, String pSbDtlId, String pOcId) throws Exception {
		logger.info("SB Order " + pSbId + " onlineSales C Order is Closed, with ocid " + pOcId);
		try {
			imsdao.updateCOrderIgnored(pSbId);
			imsdao.updateBomOrderId(pSbId, pOcId);
		} catch (DAOException de) {
			logger.error("Exception caught in updateCOrderIgnored()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void imsDSWaiveQCAlertSettled(String pSbId) throws Exception {
		logger.info("SB Order " + pSbId + " ims DS Waive QC Alert Settled ");
		try {
			imsdao.imsDSWaiveQCAlertSettled(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in imsDSWaiveQCAlertSettled()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void imsDSCashPrepaySettled(String pSbId) throws Exception {
		logger.info("SB Order " + pSbId + " ims DS Cash Prepay Settled ");
		try {
			imsdao.imsDSCashPrepaySettled(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in imsDSCashPrepaySettled()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void imsDSWaiveQCAlertCancelled(String pSbId) throws Exception {
		logger.info("SB Order " + pSbId + " ims DS Waive QC Alert Cancelled ");
		try {
			imsdao.imsDSWaiveQCAlertCancelled(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in imsDSWaiveQCAlertCancelled()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void imsDSMisMatchSignoff(String pSbId) throws Exception {
		logger.info("SB Order " + pSbId + " ims DS MisMatch Signoff ");
		try {
			imsdao.imsDSMisMatchSignoff(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in imsDSMisMatchSignoff()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void imsDSMisMatchCancel(String pSbId) throws Exception {
		logger.info("SB Order " + pSbId + " ims DS MisMatch Cancel ");
		try {
			imsdao.imsDSMisMatchCancel(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in imsDSMisMatchCancel()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void imsVerDocApproved(String pSbId) throws Exception {
		logger.info("SB Order " + pSbId + " ims Ver Doc Approved ");
		try {
			imsdao.imsVerDocApproved(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in imsDSMisMatchCancel()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void imsVerDocFailed(String pSbId) throws Exception {
		logger.info("SB Order " + pSbId + " imsVerDocFailed ");
		try {
			imsdao.imsVerDocFailed(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in imsVerDocFailed()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void imsDSBlackListAddressSettled(String pSbId) throws Exception {
		logger.info("SB Order " + pSbId + " ims DS Black List Address Settled ");
		try {
			imsBomdao.imsDSBlackListAddressSettled(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in imsDSBlackListAddressSettled()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void onlineSalesLtsDiffNameSignOff(String pSbId) throws Exception {
		try {
			//OrderSignoffLtsService orderSignoffLtsService = SpringApplicationContext.getBean(OrderSignoffLtsService.class);
			//orderSignoffLtsService.signoffLtsOnlineSalesOrder(pSbId, "TLTSOLS", "SBO");
		} catch (Exception e) {
			logger.error("Exception caught in onlineSalesLtsDiffNameSignOff()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void onlineSalesLtsDiffNameCancel(String pSbId, String pSbDtlId) throws Exception {
		try {
			BomwebOrderLatestStatusDAO criteria = SpringApplicationContext.getBean(BomwebOrderLatestStatusDAO.class);
			criteria.setSearchKey("orderId", pSbId);
			BomwebOrderLatestStatusDAO[] daos = (BomwebOrderLatestStatusDAO[]) criteria.doSelect(null, null);
			if (ArrayUtils.isNotEmpty(daos)) {
				for (BomwebOrderLatestStatusDAO dao : daos) {
					dao.setSbStatus(SB_STATUS_LTS_CANCEL);
					dao.setLastUpdBy("WQ");
					dao.doUpdate();
				}
			}

			// Release DN
			bomwebDnPoolDAO.updateDnStatus(pSbId, pSbDtlId, DN_POOL_STATUS_NORMAL);
		} catch (Exception e) {
			logger.error("Exception caught in onlineSalesLtsDiffNameCancel()", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public ImsWorkQueueStatusDAO getImsdao() {
		return this.imsdao;
	}

	public void setImsdao(ImsWorkQueueStatusDAO pImsdao) {
		this.imsdao = pImsdao;
	}

	
	private ImsBomBCustomerDAO imsBomdao;
	
	
	public void setImsBomdao(ImsBomBCustomerDAO imsBomdao) {
		this.imsBomdao = imsBomdao;
	}

	public ImsBomBCustomerDAO getImsBomdao() {
		return imsBomdao;
	}

	public BomwebDnPoolDAO getBomwebDnPoolDAO() {
		return bomwebDnPoolDAO;
	}

	public void setBomwebDnPoolDAO(BomwebDnPoolDAO bomwebDnPoolDAO) {
		this.bomwebDnPoolDAO = bomwebDnPoolDAO;
	}

	public OfferActionDAO getOfferActionDao() {
		return offerActionDao;
	}

	public void setOfferActionDao(OfferActionDAO offerActionDao) {
		this.offerActionDao = offerActionDao;
	}	
	

	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void imsDSFSPendingSettled(String pSbId) throws Exception {
		logger.info("SB Order " + pSbId + " ims DS FS Pending Settled ");
		try {
			imsdao.imsDSFSPendingSettled(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in imsDSFSPendingSettled()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void imsDSFSPendingCancelled(String pSbId) throws Exception {
		logger.info("SB Order " + pSbId + " ims DS FS Pending Cancelled ");
		try {
			imsdao.imsDSFSPendingCancelled(pSbId);
		} catch (DAOException de) {
			logger.error("Exception caught in imsDSFSPendingCancelled()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	
	@Override
	@Transactional(rollbackFor={Exception.class})
	public void wqStatusChanged(String pWqWpAssgnId, String pSbId,
			String pSbDtlId, String pSbActvId, String pWqStatus,
			String[] pWqNatureIds, String pRemarks, String pUserId) throws Exception {
		try {
			WorkQueueStatusChangeService wqStatusChangeService = SpringApplicationContext.getBean(WorkQueueStatusChangeService.class);
			wqStatusChangeService.statusChangedAction(pWqWpAssgnId, pSbId, pSbDtlId, pSbActvId, pWqStatus, pWqNatureIds, pRemarks, pUserId);
		} catch (Exception e) {
			logger.error("Exception caught in wqStatusChanged()", e);
			throw new AppRuntimeException(e);
		}	
	}
	
//	@Override
//	@Transactional(rollbackFor={Exception.class})
//	public ImsWqDTO getImsCOrderInfo(String orderId) throws Exception {
//		logger.info("SB Order " + orderId + " get ims C Order Info");
//		try {
//			ImsWqDTO imsWqDto = imsdao.getImsCOrderInfo(orderId);
//			return imsWqDto;
//		} catch (DAOException de) {
//			logger.error("Exception caught in getImsCOrderInfo()", de);
//			throw new AppRuntimeException(de);
//		}
//	}
}
