package com.bomwebportal.lts.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.order.OrderStatusDAO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.activity.PipbActivityLtsSubmissionService;
import com.bomwebportal.lts.util.LtsActvBackendConstant;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsActvBackendConstant.ActvAction;
import com.bomwebportal.lts.dao.PipbLtsServiceDAO;
import com.bomwebportal.lts.dao.PipbLtsWQServiceDAO;

public class PipbActvLtsServiceImpl implements PipbActvLtsService {

	private final Log logger = LogFactory.getLog(getClass());
	
	OrderStatusDAO orderStatusDao;
	PipbLtsServiceDAO pipbLtsServiceDAO;
	PipbLtsWQServiceDAO pipbLtsWQServiceDAO;
	PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService;
	
	
	@Override
	public void submitPipbActivity(SbOrderDTO pSbOrder,
			ServiceDetailDTO pSrvDtl, String pUser, String pShopCd,
			boolean isStatusChange, boolean isSrdChange, String pTargetBomStatus) {
		
		
		String pipbAction = null;
		String dnSource = null;
		String duplexAction = null;
		String sbStatus = null;
		String pipbSrvLegacyStatus = null;
		String secDelLegacyStatus = null;
		boolean submitActv = false;
		boolean submitChgDn = false;
		
				
		ServiceDetailLtsDTO sbPipbSrvDtl = LtsSbHelper.getAcqPipbService(pSbOrder.getSrvDtls());
		ServiceDetailLtsDTO secDelSrvDtl = LtsSbHelper.get2ndDelServices(pSbOrder.getSrvDtls());
		ServiceDetailLtsDTO coreSrvDtl = LtsSbHelper.getLtsService(pSbOrder);
		OrderStatusDTO[] orderStatusDTO = getStatus(pSbOrder);
		
		pipbAction = sbPipbSrvDtl.getPipb().getPipbAction();
		
		ServiceDetailLtsDTO srvDtl = (ServiceDetailLtsDTO)pSrvDtl;
		
		if(pSrvDtl.getPipb() != null){
			duplexAction = pSrvDtl.getPipb().getDuplexAction();
		}
		
		sbStatus = srvDtl.getSbStatus();		
		dnSource = srvDtl.getDnSource();		
	    
	    for (OrderStatusDTO ordStatus : orderStatusDTO){
	    	if(StringUtils.equals(ordStatus.getDtlId(), sbPipbSrvDtl.getDtlId())){
	    		pipbSrvLegacyStatus = ordStatus.getLegacyStatus();
	    	}
	    	if(secDelSrvDtl!=null){
	    		if(StringUtils.equals(ordStatus.getDtlId(), secDelSrvDtl.getDtlId())){
		    		secDelLegacyStatus = ordStatus.getLegacyStatus();
	    		}
	    	}
	    }
		
		if(StringUtils.equals(pipbAction, LtsBackendConstant.LTS_PIPB_ACTION_PIPB)){
		
			if(StringUtils.equals(dnSource, LtsBackendConstant.DN_SOURCE_DN_PIPB) 
					&& !StringUtils.equals(duplexAction, LtsBackendConstant.LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER)){
				if(StringUtils.equals(pTargetBomStatus, LtsBackendConstant.BOM_STATUS_DISTRIBUTED)
						&& !StringUtils.equals(sbStatus, LtsBackendConstant.ORDER_STATUS_CANCELLED)){
					submitActv = true;
				}			
			}else if(StringUtils.equals(dnSource, LtsBackendConstant.DN_SOURCE_DN_PIPB) 
					&& StringUtils.equals(duplexAction, LtsBackendConstant.LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER)){
				if(StringUtils.equals(pTargetBomStatus, LtsBackendConstant.BOM_STATUS_DISTRIBUTED)
						&& !StringUtils.equals(sbStatus, LtsBackendConstant.ORDER_STATUS_CANCELLED)){
					if(StringUtils.equals(secDelLegacyStatus, LtsBackendConstant.BOM_STATUS_DISTRIBUTED)){
						submitActv = true;
					}
				}
			}else if(LtsSbHelper.is2ndDelService(pSrvDtl)){
				if(StringUtils.equals(dnSource, LtsBackendConstant.DN_SOURCE_DN_PIPB)){
					if(StringUtils.equals(pipbSrvLegacyStatus, LtsBackendConstant.BOM_STATUS_DISTRIBUTED)
							&& StringUtils.equals(sbPipbSrvDtl.getPipb().getDuplexAction(), LtsBackendConstant.LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER)){
						submitActv = true;
					}
				}
			}	
		}else if(StringUtils.equals(pipbAction, LtsBackendConstant.LTS_PIPB_ACTION_NEW)){
			if(StringUtils.equals(dnSource, LtsBackendConstant.DN_SOURCE_DN_PIPB)){
				if(StringUtils.equals(pTargetBomStatus, LtsBackendConstant.BOM_STATUS_DISTRIBUTED)
						&& !StringUtils.equals(sbStatus, LtsBackendConstant.ORDER_STATUS_CANCELLED)){
					submitActv = true;
				}			
			}else if(!StringUtils.equals(dnSource, LtsBackendConstant.DN_SOURCE_DN_PIPB)
					&& StringUtils.equals(coreSrvDtl.getDtlId(), pSrvDtl.getDtlId())){
				if(StringUtils.equals(pTargetBomStatus, LtsBackendConstant.BOM_STATUS_COMPLETED)
						&& !StringUtils.equals(sbStatus, LtsBackendConstant.ORDER_STATUS_CANCELLED)){
					// CASE 2 NEW_DN WQ8
					submitChgDn = true;
				}			
			}
		}
		
		
		if(submitActv){
			try {
				//int i = pipbLtsWQServiceDAO.countBomwebWqTrans(pSbOrder.getOrderId(), pSrvDtl.getDtlId(), LtsBackendConstant.LTS_PIPB_WQ_ACTION_PIPB_TO_D, LtsBackendConstant.LTS_PIPB_WQ_STATUS_COMPLETED);
				if(pipbActivityLtsSubmissionService.isTaskExistByType(pSbOrder.getOrderId(), pUser, LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST) &&
						pipbLtsWQServiceDAO.countBomwebWqTrans(pSbOrder.getOrderId(), pSrvDtl.getDtlId(), LtsBackendConstant.LTS_PIPB_WQ_ACTION_PIPB_TO_D, LtsBackendConstant.LTS_PIPB_WQ_STATUS_COMPLETED) != 0){
					logger.info("SubmitPipbActv - WQ exist order id: " + pSbOrder.getOrderId());	
				}else{
					if (!LtsSbHelper.isValidPipbDay(pSbOrder.getSrvDtls())) {
						logger.info("SubmitPipbActv - Submit pipb amendment begin. order id: " + pSbOrder.getOrderId());	
						pipbActivityLtsSubmissionService.createPipbAmendment(pSbOrder.getOrderId(), pUser);
						pipbActivityLtsSubmissionService.createPipbRejectWithDeferSRD(pSbOrder.getOrderId(), pUser);
					} else if (!LtsSbHelper.isValidPipbAppointment(pSbOrder.getSrvDtls())) {
						logger.info("SubmitPipbActv - Submit pipb reject invalid appointment begin. order id: " + pSbOrder.getOrderId());	
						pipbActivityLtsSubmissionService.createPipbRejectWithInvalidAppointment(pSbOrder.getOrderId(), pUser);
					} else {
						logger.info("SubmitPipbActv - Submit pipb activity begin. order id: " + pSbOrder.getOrderId());
						pipbActivityLtsSubmissionService.submitPipbActivity(pSbOrder, pSrvDtl, pUser, ActvAction.PIPB_REQUEST);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("SubmitPipbActv - Submit pipb activity failed. order id: " + pSbOrder.getOrderId() + " - " + e.getMessage());
				throw new AppRuntimeException("Fail to submit activity.", e);
			}
		}
		
		if(submitChgDn){
			try {
//				if (!LtsSbHelper.isValidPipbDay(pSbOrder.getSrvDtls())) {
//					logger.info("Submit pipb amendment begin. order id: " + pSbOrder.getOrderId());	
//					pipbActivityLtsSubmissionService.createPipbAmendment(pSbOrder.getOrderId(), pUser);
//					pipbActivityLtsSubmissionService.createPipbRejectWithDeferSRD(pSbOrder.getOrderId(), pUser);
//				} else if (!LtsSbHelper.isValidPipbAppointment(pSbOrder.getSrvDtls())) {
//					logger.info("Submit pipb invalid appointment begin. order id: " + pSbOrder.getOrderId());	
//					pipbActivityLtsSubmissionService.createPipbRejectWithInvalidAppointment(pSbOrder.getOrderId(), pUser);
//				} else {
				if(pipbActivityLtsSubmissionService.isTaskExistByType(pSbOrder.getOrderId(), pUser, LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST)&&
						pipbLtsWQServiceDAO.countBomwebWqTrans(pSbOrder.getOrderId(), pSrvDtl.getDtlId(), LtsBackendConstant.LTS_PIPB_WQ_ACTION_PIPB_TO_D, LtsBackendConstant.LTS_PIPB_WQ_STATUS_COMPLETED) != 0){
					logger.info("SubmitChgDn - WQ exist order id: " + pSbOrder.getOrderId());	
				}else{ 
					logger.info("SubmitChgDn - Submit pipb activity begin. order id: " + pSbOrder.getOrderId());
					pipbActivityLtsSubmissionService.submitPipbActivity(pSbOrder, pSrvDtl, pUser, ActvAction.CHANGE_DN);
				}
//				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("SubmitChgDn - Submit pipb activity failed. order id: " + pSbOrder.getOrderId() + " - " + e.getMessage());
				throw new AppRuntimeException("Fail to submit activity.", e);
			}
		}
						
		
	}
	
	public void insertDummyRecordToBInvPreassgn(SbOrderDTO pSbOrder){
		logger.info("insertDummyRecordToBInvPreassgn [START]");
		if(pipbLtsServiceDAO != null){
			try {
				pipbLtsServiceDAO.insertDummyForPipbDnAndReservedDn(pSbOrder);
			} catch (DAOException e) {
				// TODO Auto-generated catch block
				logger.error("Insert dummy record to B_INV_PREASSGN failed. order id: " + pSbOrder.getOrderId() + " - " + e.getMessage());
				e.printStackTrace();
			}
		}
		logger.info("insertDummyRecordToBInvPreassgn [END]");
	}	
	
	public void updatePipbActivity(SbOrderDTO pSbOrder, String pUser, String pShopCd, String pAction, String pStatusCd){
		ServiceDetailLtsDTO sbPipbSrvDtl = LtsSbHelper.getAcqPipbService(pSbOrder.getSrvDtls());
		ServiceDetailLtsDTO secDelSrvDtl = LtsSbHelper.get2ndDelServices(pSbOrder.getSrvDtls());
		String duplexAction = null;
		OrderStatusDTO[] orderStatusDTO = getStatus(pSbOrder);
		String pipbSrvLegacyStatus = null;
		String secDelLegacyStatus = null;
		boolean srdChg = false;
		if(StringUtils.equals(pAction, LtsActvBackendConstant.ACTV_ACTION_ORD_AMEND_WITH_SRD_CHG)){
			srdChg = true;
		}
	    for (OrderStatusDTO ordStatus : orderStatusDTO){
	    	if(StringUtils.equals(ordStatus.getDtlId(), sbPipbSrvDtl.getDtlId())){
	    		pipbSrvLegacyStatus = ordStatus.getLegacyStatus();
	    	}
	    	if(secDelSrvDtl!=null){
	    		if(StringUtils.equals(ordStatus.getDtlId(), secDelSrvDtl.getDtlId())){
		    		secDelLegacyStatus = ordStatus.getLegacyStatus();
	    		}
	    	}
	    }
		if(sbPipbSrvDtl.getPipb() != null){
			duplexAction = sbPipbSrvDtl.getPipb().getDuplexAction();
		}
		try {
			if(StringUtils.equals(pAction, LtsActvBackendConstant.ACTV_ACTION_ORD_CANCEL)){
				submitActvCancelOrder(pSbOrder, pUser);
			}else if (!LtsSbHelper.isValidPipbDay(pSbOrder.getSrvDtls())) {
				logger.info("UPD_PIPB_ACTV - PIPB SRD Not valid, submit pipb amendment and Reject With Defer SRD order id: " + pSbOrder.getOrderId());	
				pipbActivityLtsSubmissionService.createPipbAmendment(pSbOrder.getOrderId(), pUser);
				pipbActivityLtsSubmissionService.createPipbRejectWithDeferSRD(pSbOrder.getOrderId(), pUser);
			}else{
				
				if(StringUtils.equals(pAction, LtsActvBackendConstant.ACTV_ACTION_ORD_AMEND)
						|| StringUtils.equals(pAction, LtsActvBackendConstant.ACTV_ACTION_ORD_AMEND_WITH_SRD_CHG)){
					
					if(pipbActivityLtsSubmissionService.isTaskExistByType(pSbOrder.getOrderId(), pUser, LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST)){
							submitActvAmendOrder(pSbOrder, pUser, srdChg);
					}else if(StringUtils.equals(duplexAction, LtsBackendConstant.LTS_PIPB_DUPLEX_ACTION_PORT_IN_TOGETHER)){
						if(StringUtils.equals(pipbSrvLegacyStatus, LtsBackendConstant.BOM_STATUS_DISTRIBUTED)
								&& StringUtils.equals(secDelLegacyStatus, LtsBackendConstant.BOM_STATUS_DISTRIBUTED)){
							submitActvAmendOrder(pSbOrder, pUser, srdChg);
						}
					}else if(StringUtils.equals(pipbSrvLegacyStatus, LtsBackendConstant.BOM_STATUS_DISTRIBUTED)){
						submitActvAmendOrder(pSbOrder, pUser, srdChg);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("UPD_PIPB_ACTV - Update pipb activity failed. order id: " + pSbOrder.getOrderId() + " - " + e.getMessage());
			throw new AppRuntimeException("Fail to update activity.", e);
		}
	}
	
	public void submitActvAmendOrder(SbOrderDTO pSbOrder, String pUser, boolean pSrdChg){
		try {
			logger.info("ACTV_ACTION_ORD_AMEND - Cancel pipb activity begin. order id: " + pSbOrder.getOrderId());
			pipbActivityLtsSubmissionService.createPipbCancelWithAcknowledge(pSbOrder.getOrderId(), pUser);
			logger.info("ACTV_ACTION_ORD_AMEND - Submit pipb activity begin. order id: " + pSbOrder.getOrderId());
			pipbActivityLtsSubmissionService.submitPipbActivity(pSbOrder, null, pUser, ActvAction.PIPB_REQUEST);
			if(pSrdChg){
				logger.info("ACTV_ACTION_ORD_AMEND_SRD - Update WQ SRD begin. order id: " + pSbOrder.getOrderId());
				pipbActivityLtsSubmissionService.updateWorkQueueWithSRD(pSbOrder, pUser);
			}
		} catch (Exception e) {
			logger.error("ACTV_ACTION_ORD_AMEND - submitActvAmendOrder failed. order id: " + pSbOrder.getOrderId() + " - " + e.getMessage());
			throw new AppRuntimeException("Fail to update activity.", e);
		}
	}
	
	public void submitActvCancelOrder(SbOrderDTO pSbOrder, String pUser){
		try {
			logger.info("ACTV_ACTION_ORD_CANCEL - Submit pipb activity begin. order id: " + pSbOrder.getOrderId());
			pipbActivityLtsSubmissionService.createPipbCancel(pSbOrder.getOrderId(), pUser);
		} catch (Exception e) {
			logger.error("ACTV_ACTION_ORD_CANCEL - submitActvCancelOrder failed. order id: " + pSbOrder.getOrderId() + " - " + e.getMessage());
			throw new AppRuntimeException("Fail to update activity.", e);
		}
	}
	
	public void updatePipbActivityStatus(String pSbOrderId, String pUser, String pStatusCd, String pAction){
		
		
		try {
			if(StringUtils.equals(pAction, LtsActvBackendConstant.ACTV_ACTION_APN_UPLOAD)){
				logger.info("UpdPipbActvStatus - Update pipb activity begin. order id: " + pSbOrderId);
				if(StringUtils.equals(pStatusCd, LtsActvBackendConstant.ACTV_STATUS_UPLOAD_SUCCESS)){
					pipbActivityLtsSubmissionService.createAPNUpdate(pSbOrderId, pUser, LtsActvBackendConstant.ACTV_TASK_STATUS_SUCCESS);
				}else if(StringUtils.equals(pStatusCd, LtsActvBackendConstant.ACTV_STATUS_SRD_EXPIRED)){
					pipbActivityLtsSubmissionService.createAPNUpdate(pSbOrderId, pUser, LtsActvBackendConstant.ACTV_TASK_STATUS_CANCELLED);
				}else if(StringUtils.equals(pStatusCd, LtsActvBackendConstant.ACTV_STATUS_NO_OCID)){
					pipbActivityLtsSubmissionService.createAPNUpdate(pSbOrderId, pUser, LtsActvBackendConstant.ACTV_TASK_STATUS_FAIL);
				}
			}
		} catch (Exception e) {
			logger.error("UpdPipbActvStatus - Update pipb activity failed. order id: " + pSbOrderId + " - " + e.getMessage());
			throw new AppRuntimeException("Fail to submit activity.", e);
		}
	
	}
	
	public OrderStatusDTO[] getStatus(SbOrderDTO pSbOrder){
	    try {
	    	return orderStatusDao.getStatus(pSbOrder.getOrderId());
		} catch (DAOException e1) {
			// TODO Auto-generated catch block
			logger.error("Get OrderStatus failed. order id: " + pSbOrder.getOrderId() + " - " + e1.getMessage());
			throw new AppRuntimeException("Fail to get OrderStatus.", e1);
		}
	}

	public PipbActivityLtsSubmissionService getPipbActivityLtsSubmissionService() {
		return pipbActivityLtsSubmissionService;
	}

	public void setPipbActivityLtsSubmissionService(
			PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService) {
		this.pipbActivityLtsSubmissionService = pipbActivityLtsSubmissionService;
	}

	public OrderStatusDAO getOrderStatusDao() {
		return orderStatusDao;
	}

	public void setOrderStatusDao(OrderStatusDAO orderStatusDao) {
		this.orderStatusDao = orderStatusDao;
	}

	public PipbLtsServiceDAO getPipbLtsServiceDAO() {
		return pipbLtsServiceDAO;
	}

	public void setPipbLtsServiceDAO(PipbLtsServiceDAO pipbLtsServiceDAO) {
		this.pipbLtsServiceDAO = pipbLtsServiceDAO;
	}

	public PipbLtsWQServiceDAO getPipbLtsWQServiceDAO() {
		return pipbLtsWQServiceDAO;
	}

	public void setPipbLtsWQServiceDAO(PipbLtsWQServiceDAO pipbLtsWQServiceDAO) {
		this.pipbLtsWQServiceDAO = pipbLtsWQServiceDAO;
	}
	
}
