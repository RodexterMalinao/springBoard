package com.bomwebportal.lts.service.order.acq;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.LtsAcqDnPoolService;
import com.bomwebportal.lts.service.LtsEmailService;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.LtsProfileService;
import com.bomwebportal.lts.service.LtsRemarkService;
import com.bomwebportal.lts.service.acq.LtsAcq0060DetailService;
import com.bomwebportal.lts.service.bom.ServiceProfileLtsService;
import com.bomwebportal.lts.service.order.PipbOrderSignoffLtsServiceImpl;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.service.sms.LtsSmsService;
import com.bomwebportal.lts.util.LtsActvBackendConstant.ActvAction;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.service.CodeLkupCacheService;

public class LtsAcqOrderQcSubmitLtsServiceImpl extends PipbOrderSignoffLtsServiceImpl implements LtsAcqOrderQcSubmitLtsService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LtsAcqDnPoolService ltsAcqDnPoolService;
	private LtsAcq0060DetailService ltsAcq0060DetailService;
	private ServiceProfileLtsService serviceProfileLtsService;
	private LtsProfileService ltsProfileService;
	private LtsRemarkService ltsRemarkService;
	private ReportCreationLtsService reportCreationLtsService;
	private CodeLkupCacheService reportSetLkupCacheService;
	private LtsSmsService ltsSmsService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private LtsEmailService ltsEmailService;
	
	public String signOffOrder(SbOrderDTO pSbOrder, BomSalesUserDTO bomSalesUser) {
		try {	
			if(isMustQcStatus(pSbOrder)){
				this.clearFollowupWorkQueue(pSbOrder, bomSalesUser.getUsername());
				this.getServiceActions(pSbOrder, bomSalesUser.getUsername());
				setDnAssignedFromDnPool(pSbOrder);
				submitDsAllWorkQueue(pSbOrder, bomSalesUser);
				updateServiceStatusAfterSignoff(pSbOrder, bomSalesUser.getUsername());
				return null;
			}
			
			String errStr = verifyQC(pSbOrder, bomSalesUser);
			if (errStr!=null) {
				return errStr;
			}			
			String pUserId = bomSalesUser.getUsername();
			String pShopCd = bomSalesUser.getShopCd();
			if (LtsSbHelper.isLtsOrderCancelled(pSbOrder.getSrvDtls())) {
				return null;
			}
			if (!validatePipbDnExists(pSbOrder, pUserId)) {
				createDnFollowup(ActvAction.CREATE_DN, null, pSbOrder.getOrderId(), pUserId);
				return null;
			}
			setDnAssignedFromDnPool(pSbOrder);
			List<String> errList = changeDnToPreAssigned(pSbOrder, pUserId);
			if (errList!=null && errList.size()>0) {
				createDnFollowup(ActvAction.DN_FOLLOWUP, errList, pSbOrder.getOrderId(), pUserId);
				return null;
			}
			preSubmitSignoff(pSbOrder, pUserId);
			
			boolean mustQc = false;
			boolean updateSignoffStatus = false;
			if(pSbOrder != null && pSbOrder.getLtsDsOrderInfo() != null && "Y".equals(pSbOrder.getLtsDsOrderInfo().getMustQc())){
				mustQc = true;
			}
			
			if((!mustQc)){
				updateSignoffStatus = true;
				determineWorkQueueNatureForSignoff(pSbOrder); // determine work queue and submit
				setWqNatureRemark(pSbOrder);
				submitToWorkQueue(pSbOrder, bomSalesUser.getUsername(), pShopCd);
			}
			
			updateInvPreassgnJunctionMsg(pSbOrder, pUserId);
			postSubmitSignoff(pSbOrder, pUserId, updateSignoffStatus);
			
			return null;
		} catch (Exception e) {
			logger.error("Exception caught in Signoff Order " + pSbOrder.getOrderId() + " - " + e.getMessage());
			logger.error(ExceptionUtils.getFullStackTrace(e));
			return "Exception caught in Signoff Order";
		}
	}
	
	@Transactional
	private void submitDsAllWorkQueue(SbOrderDTO pSbOrder, BomSalesUserDTO bomSalesUser) {
		determineWorkQueueNatureForSignoff(pSbOrder); // determine work queue and submit
		setWqNatureRemark(pSbOrder);
		submitToWorkQueue(pSbOrder, bomSalesUser.getUsername(), bomSalesUser.getShopCd());
	}
	
	private boolean isMustQcStatus(SbOrderDTO sbOrder) {
    	OrderStatusDTO[] status = orderStatusService.getStatus(sbOrder.getOrderId());
    	for (int i=0; status!=null && i<status.length; ++i) {
 		   if (StringUtils.equals(LtsBackendConstant.ORDER_STATUS_AWAIT_QC, status[i].getSbStatus())) {
 			   return true;
 		   }
        }
 	    return false;
    }
	
	private void preSubmitSignoff(SbOrderDTO sbOrder, String userId) {
		clearFollowupWorkQueue(sbOrder, userId);
		getServiceActions(sbOrder, userId);
		updateSuspendBomOrder(sbOrder, userId);
    }
   
    private void postSubmitSignoff(SbOrderDTO sbOrder, String userId, boolean pUpdateSignoffStatus) {
		createImsEyeOrder(sbOrder, userId); // create IMS order if needed
		if(pUpdateSignoffStatus){
			updateServiceStatusAfterSignoff(sbOrder, userId); // update table BOMWEB_ORDER_LATEST_STATUS
		}
    }
	
    private String verifyQC(SbOrderDTO sbOrder, BomSalesUserDTO bomSalesUser) {
//    	if (bomSalesUser.getChannelId()!=Integer.parseInt(LtsConstant.CHANNEL_ID_DIRECT_SALES_NOW_TV_QA)) {
    	if(!LtsSessionHelper.isChannelDirectSales(bomSalesUser.getChannelId())){
    		return "Must be channel direct sales QC, sign off is not allowed";
	    }
	    if (!isAwaitQcStatus(sbOrder)) {
	    	return "Error in Signoff Order - Order status is not QC";
	    }
	    return null;
    } 

    private boolean isAwaitQcStatus(SbOrderDTO sbOrder) {
    	OrderStatusDTO[] status = orderStatusService.getStatus(sbOrder.getOrderId());
    	for (int i=0; status!=null && i<status.length; ++i) {
 		   if (StringUtils.equals(LtsBackendConstant.ORDER_STATUS_AWAIT_QC, status[i].getSbStatus())) {
 			   return true;
 		   }
        }
 	    return false;
    }
    
    private boolean setDnAssignedFromDnPool(SbOrderDTO sbOrder) {
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbOrderHelper.getLtsService(sbOrder);
		if (ltsServiceDetail != null
				&& LtsConstant.DN_SOURCE_DN_POOL.equals(ltsServiceDetail.getDnSource())) {
			ltsAcqDnPoolService.assignDn(ltsServiceDetail.getSrvNum());
		}
		ServiceDetailLtsDTO lts2ndDelServiceDetail = LtsSbOrderHelper.get2ndDelService(sbOrder);
		if (lts2ndDelServiceDetail != null
				&& LtsConstant.DN_SOURCE_DN_POOL.equals(lts2ndDelServiceDetail.getDnSource())) {
			ltsAcqDnPoolService.assignDn(lts2ndDelServiceDetail.getSrvNum());
		}
		return true;
	}
    
	/**
	 * @return the ltsAcqDnPoolService
	 */
	public LtsAcqDnPoolService getLtsAcqDnPoolService() {
		return ltsAcqDnPoolService;
	}

	/**
	 * @param ltsAcqDnPoolService the ltsAcqDnPoolService to set
	 */
	public void setLtsAcqDnPoolService(LtsAcqDnPoolService ltsAcqDnPoolService) {
		this.ltsAcqDnPoolService = ltsAcqDnPoolService;
	}

	/**
	 * @return the ltsAcq0060DetailService
	 */
	public LtsAcq0060DetailService getLtsAcq0060DetailService() {
		return ltsAcq0060DetailService;
	}

	/**
	 * @param ltsAcq0060DetailService the ltsAcq0060DetailService to set
	 */
	public void setLtsAcq0060DetailService(
			LtsAcq0060DetailService ltsAcq0060DetailService) {
		this.ltsAcq0060DetailService = ltsAcq0060DetailService;
	}

	/**
	 * @return the serviceProfileLtsService
	 */
	public ServiceProfileLtsService getServiceProfileLtsService() {
		return serviceProfileLtsService;
	}

	/**
	 * @param serviceProfileLtsService the serviceProfileLtsService to set
	 */
	public void setServiceProfileLtsService(
			ServiceProfileLtsService serviceProfileLtsService) {
		this.serviceProfileLtsService = serviceProfileLtsService;
	}

	/**
	 * @return the ltsProfileService
	 */
	public LtsProfileService getLtsProfileService() {
		return ltsProfileService;
	}

	/**
	 * @param ltsProfileService the ltsProfileService to set
	 */
	public void setLtsProfileService(LtsProfileService ltsProfileService) {
		this.ltsProfileService = ltsProfileService;
	}

	/**
	 * @return the ltsRemarkService
	 */
	public LtsRemarkService getLtsRemarkService() {
		return ltsRemarkService;
	}

	/**
	 * @param ltsRemarkService the ltsRemarkService to set
	 */
	public void setLtsRemarkService(LtsRemarkService ltsRemarkService) {
		this.ltsRemarkService = ltsRemarkService;
	}

	/**
	 * @return the reportCreationLtsService
	 */
	public ReportCreationLtsService getReportCreationLtsService() {
		return reportCreationLtsService;
	}

	/**
	 * @param reportCreationLtsService the reportCreationLtsService to set
	 */
	public void setReportCreationLtsService(
			ReportCreationLtsService reportCreationLtsService) {
		this.reportCreationLtsService = reportCreationLtsService;
	}

	/**
	 * @return the reportSetLkupCacheService
	 */
	public CodeLkupCacheService getReportSetLkupCacheService() {
		return reportSetLkupCacheService;
	}

	/**
	 * @param reportSetLkupCacheService the reportSetLkupCacheService to set
	 */
	public void setReportSetLkupCacheService(
			CodeLkupCacheService reportSetLkupCacheService) {
		this.reportSetLkupCacheService = reportSetLkupCacheService;
	}

	/**
	 * @return the ltsSmsService
	 */
	public LtsSmsService getLtsSmsService() {
		return ltsSmsService;
	}

	/**
	 * @param ltsSmsService the ltsSmsService to set
	 */
	public void setLtsSmsService(LtsSmsService ltsSmsService) {
		this.ltsSmsService = ltsSmsService;
	}

	/**
	 * @return the ltsOrderDocumentService
	 */
	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	/**
	 * @param ltsOrderDocumentService the ltsOrderDocumentService to set
	 */
	public void setLtsOrderDocumentService(
			LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}

	/**
	 * @return the ltsEmailService
	 */
	public LtsEmailService getLtsEmailService() {
		return ltsEmailService;
	}

	/**
	 * @param ltsEmailService the ltsEmailService to set
	 */
	public void setLtsEmailService(LtsEmailService ltsEmailService) {
		this.ltsEmailService = ltsEmailService;
	}

}
