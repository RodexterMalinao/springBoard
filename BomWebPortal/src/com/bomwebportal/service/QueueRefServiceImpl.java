package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dao.BomSubscriberDAO;
import com.bomwebportal.dao.QueueRefDAO;
import com.bomwebportal.dto.ResultDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.hkcsl.hsrm.service.HSRMService;
import com.hkcsl.hsrm.service.SQPCOrderDetailResponse;
import com.hkcsl.hsrm.service.SQPCService;

public class QueueRefServiceImpl implements QueueRefService{
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private SQPCService sqpcService;

	private HSRMService hsrmService;
	
	private QueueRefDAO queueRefDAO;
	
	private BomSubscriberDAO bomSubscriberDAO;
		
	public SQPCService getSqpcService() {
		return sqpcService;
	}

	public void setSqpcService(SQPCService sqpcService) {
		this.sqpcService = sqpcService;
	}

	public HSRMService getHsrmService() {
		return hsrmService;
	}

	public void setHsrmService(HSRMService hsrmService) {
		this.hsrmService = hsrmService;
	}

	public QueueRefDAO getQueueRefDAO() {
		return queueRefDAO;
	}

	public void setQueueRefDAO(QueueRefDAO queueRefDAO) {
		this.queueRefDAO = queueRefDAO;
	}

	public boolean validateVipNumberPattern(String attbValue){
		
		List<CodeLkupDTO> preReigstrationComponentList= LookupTableBean.getInstance().getCodeLkupList().get("PRJ_7_VIP");
		
		if (!CollectionUtils.isEmpty( preReigstrationComponentList)) {
			for (CodeLkupDTO dto : preReigstrationComponentList) {
				if (attbValue!=null){
					if (attbValue.equals(dto.getCodeId())) {
						return true;
					}
				}
			}
		}	
		return false;
	}
	
	public boolean validateWalkInNumberPattern(String attbValue){
		
		List<CodeLkupDTO> preReigstrationComponentList= LookupTableBean.getInstance().getCodeLkupList().get("PRJ_7_WLK");	
		if (!CollectionUtils.isEmpty( preReigstrationComponentList)) {
			for (CodeLkupDTO dto : preReigstrationComponentList) {
				if (attbValue!=null){
					if (attbValue.equals(dto.getCodeId())) {
						return true;
					}
				}
			}
		}	
		return false;
	}

	
	public ResultDTO validatePreRegStatusFromHSRM(SQPCOrderDetailResponse sqpcOrder, String attbValue, String docId,String basketBrand){
		ResultDTO result = new ResultDTO();
		result.setReturnBool(true);
		
		String brand = null;
		List<CodeLkupDTO> mobBrandList= LookupTableBean.getInstance().getCodeLkupList().get("MOB_BRAND");
		List<CodeLkupDTO> notAllowProceedStatusList= LookupTableBean.getInstance().getCodeLkupList().get("QUEUE_REF_STATUS_NOT_ALLOW_PROCEED");
		List<CodeLkupDTO> allowProceedStatusAlertList= LookupTableBean.getInstance().getCodeLkupList().get("QUEUE_REF_STATUS_ALLOW_PROCEED_ALERT");
		
		for (CodeLkupDTO mobBrand:mobBrandList ){
			if (mobBrand.getCodeId().equalsIgnoreCase(basketBrand)){
				brand = mobBrand.getCodeDesc();
			}
		}
		
		
		if (!attbValue.equals(sqpcOrder.getRefNo())){
			result.setReturnBool(false);
			result.setReturnMessage("Queue Ref. No not match.");
		}
		
		else if (!docId.equals(sqpcOrder.getDocId())){
			result.setReturnBool(false);
			result.setReturnMessage("Register ID Doc Num not match.");
		}
		
		else if (!brand.equals(sqpcOrder.getBrand()) && "1010".equalsIgnoreCase(sqpcOrder.getBrand())){
			result.setReturnBool(false);
			result.setReturnMessage("Brand not match");
		}
		
		else if (CollectionUtils.isNotEmpty(notAllowProceedStatusList)){			
			for (CodeLkupDTO dto: notAllowProceedStatusList){
				if (dto.getCodeDesc()!=null && dto.getCodeDesc().equals(sqpcOrder.getQueueStatus())){
					result.setReturnBool(false);
					result.setReturnMessage("Invalid Queue Status.");
					break;
				}
			}		
		}
		if (CollectionUtils.isNotEmpty(allowProceedStatusAlertList) && result.getReturnBool()){			
			for (CodeLkupDTO dto: allowProceedStatusAlertList){
				if (dto.getCodeDesc()!=null && dto.getCodeDesc().equals(sqpcOrder.getQueueStatus())){
					result.setReturnBool(false);
					result.setReturnMessage("Stock allocated status");
					break;
				}
			}		
		}
			
		return result;
		
	}
	
	public boolean verifyWalkInByPassControl(String itemCode,String refId, Date appDate) {
		try {
			logger.info("verifyWalkInByPassControl() is called @ QueueRefService");
			return queueRefDAO.verifyWalkInByPassControl(itemCode, refId, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in verifyWalkInByPassControl()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public boolean isWalkInByPassProduct(String itemCode, Date appDate) {
		try {
			logger.info("isWalkInByPassProduct() is called @ QueueRefService");
			return queueRefDAO.isWalkInByPassProduct(itemCode, appDate);
		} catch (DAOException de) {
			logger.error("Exception caught in isWalkInByPassProduct()", de);
			throw new AppRuntimeException(de);
		}
	}
	
	public int getCouponAccountInfo(String msisdn, String idDocType, String idDocNum) {
		try {
			return bomSubscriberDAO.getCouponAccountInfo(msisdn, idDocType, idDocNum);
		} catch (DAOException de) {
			throw new AppRuntimeException(de);
		}
	}
}
