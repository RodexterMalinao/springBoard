package com.bomwebportal.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.bean.LookupTableBean;
import com.bomwebportal.dao.OrderHsrmDAO;
import com.bomwebportal.dto.ComponentDTO;
import com.bomwebportal.dto.HsrmDTO;
import com.bomwebportal.dto.OrderHsrmLogDTO;
import com.bomwebportal.dto.ResultDTO;
import com.bomwebportal.exception.AppRuntimeException;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.mob.ccs.dto.CodeLkupDTO;
import com.hkcsl.hsrm.service.HSRMService;
import com.hkcsl.hsrm.service.ResultResponse;
import com.hkcsl.hsrm.service.SQPCOrderDetailResponse;
import com.hkcsl.hsrm.service.SQPCService;


public class OrderHsrmServiceImpl implements OrderHsrmService {
private final static Log logger = LogFactory.getLog(OrderService.class);
	
	private OrderHsrmDAO orderHsrmDAO;
	
	private HSRMService hsrmService;

	private QueueRefService queueRefService;

	private SQPCService sqpcService;
	
	public OrderHsrmDAO getOrderHsrmDAO() {
		return orderHsrmDAO;
	}

	public void setOrderHsrmDAO(OrderHsrmDAO orderHsrmDAO) {
		this.orderHsrmDAO = orderHsrmDAO;
	}

	
	public HSRMService getHsrmService() {
		return hsrmService;
	}

	public void setHsrmService(HSRMService hsrmService) {
		this.hsrmService = hsrmService;
	}
	
	public QueueRefService getQueueRefService() {
		return queueRefService;
	}

	public void setQueueRefService(QueueRefService queueRefService) {
		this.queueRefService = queueRefService;
	}
	
	public SQPCService getSqpcService() {
		return sqpcService;
	}

	public void setSqpcService(SQPCService sqpcService) {
		this.sqpcService = sqpcService;
	}
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public int insertOrderHsrmLog(OrderHsrmLogDTO orderHsrmLogDTO) {
		logger.info("insertOrderHsrmLog() is called in OrderHsrmService");
		try {
			return orderHsrmDAO.insertOrderHsrmLog(orderHsrmLogDTO);
		} catch (DAOException e) {
			logger.error("Exception caught in insertOrderHsrmLog", e);
			throw new AppRuntimeException(e);
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public ResultDTO updateHSRMOrder(List<ComponentDTO> components, String sbOrderId, String itemCode, String salesCd, boolean aoInd,HsrmDTO validateHSRMResult ){
		ResultDTO result = new ResultDTO();
		result.setReturnBool(true);
		logger.info("updateHSRMOrder() is called in OrderHsrmService");
		if (CollectionUtils.isNotEmpty(components)){
			List<CodeLkupDTO> preReigstrationComponentList= LookupTableBean.getInstance().getCodeLkupList().get("PRJ_7_ATTB");	
			if (CollectionUtils.isNotEmpty(preReigstrationComponentList)){
				for (ComponentDTO component: components){
					for (CodeLkupDTO dto: preReigstrationComponentList) {
						if (component!=null && component.getCompAttbId()!= null && component.getCompAttbId().equalsIgnoreCase(dto.getCodeId())) {
							
							//String attbId = component.getAttbId();
							String attbValue = component.getCompAttbVal();
							
							if (queueRefService.validateVipNumberPattern(attbValue)){
								if (!checkLogAlreadyExists(sbOrderId,attbValue,"Order Confirmed","Skipped")){
									generateHSRMLog(sbOrderId, attbValue, itemCode, salesCd, "Order Confirmed", "Skipped", null, null,  validateHSRMResult);
								}
							}else if (queueRefService.validateWalkInNumberPattern(attbValue)){
								if (!checkLogAlreadyExists(sbOrderId,attbValue,"Order Confirmed","Skipped")){
									generateHSRMLog(sbOrderId, attbValue, itemCode, salesCd, "Order Confirmed", "Skipped", null, null, validateHSRMResult);
								}
							}else{
								ResultDTO updateResult = updateHSRMStatus(sbOrderId, attbValue, itemCode, salesCd,validateHSRMResult);
								result.setReturnBool(updateResult.getReturnBool());
								result.setReturnMessage(updateResult.getReturnMessage());

							}
						}
					}
				}

			}
		}
		return result;
	}
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public ResultDTO updateHSRMStatus(String sbOrderId, String appointmentNo, String itemCode, String salesCd, HsrmDTO validateHSRMResult){
		ResultDTO result = new ResultDTO();
		result.setReturnBool(true);
		logger.info("updateHSRMStatus() is called in OrderHsrmService");
		com.hkcsl.hsrm.service.SingleQueueOnlineUpdateRequest singleQueueOnlineUpdateRequest = new com.hkcsl.hsrm.service.SingleQueueOnlineUpdateRequest();
		com.hkcsl.hsrm.service.SingleQueueOnlineUpdateRequest.Parameters param = new com.hkcsl.hsrm.service.SingleQueueOnlineUpdateRequest.Parameters();
		param.setAppointmentno(appointmentNo);
		param.setLastUpdateBy(salesCd);
		param.setOnlineStatus("U|Order Confirmed");

		
		singleQueueOnlineUpdateRequest.setParameters(param);
		
		ResultResponse response =  new ResultResponse();

		try {
			response = hsrmService.singleQueueOnlineUpdate(singleQueueOnlineUpdateRequest);
		} catch (Exception e) {
			result.setReturnMessage("System cannot call \"HSRM Pre-reg number status update\" temporary.");
			result.setReturnBool(true);
			generateHSRMLog(sbOrderId, appointmentNo, itemCode, salesCd, "Order Confirmed", "Failed", "-1", result.getReturnMessage(),  validateHSRMResult);
			return result;
		}
		
		if (response.getData()==null){
			result.setReturnMessage("Invalid return while calling single queue online update.");
			result.setReturnBool(false);
			generateHSRMLog(sbOrderId, appointmentNo, itemCode, salesCd, "Order Confirmed", "Failed", "-1", result.getReturnMessage(),  validateHSRMResult);
		}else{
			logger.info("updateHSRMStatus Return Status:"+response.getData().getStatus());
			logger.info("updateHSRMStatus Return Message:"+response.getData().getMessage());
			if ("Success".equals(response.getData().getStatus())){
				result.setReturnBool(true);
				generateHSRMLog(sbOrderId, appointmentNo, itemCode, salesCd, "Order Confirmed", "Succeed", null, null, validateHSRMResult);
			}else{
				result.setReturnMessage(response.getData().getMessage());
				result.setReturnBool(false);
				generateHSRMLog(sbOrderId, appointmentNo, itemCode, salesCd, "Order Confirmed", "Failed", "-1", response.getData().getMessage(), validateHSRMResult);
			}
		}
		
		return result;
		
	}
	
	
	
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public ResultDTO completeHSRMOrder(List<ComponentDTO> components, String sbOrderId, String itemCode, String salesCd, boolean aoInd, HsrmDTO validateHSRMResult){
		ResultDTO result = new ResultDTO();
		result.setReturnBool(true);
		
		logger.info("completeHSRMOrder() is called in OrderHsrmService");
		if (CollectionUtils.isNotEmpty(components)){
			List<CodeLkupDTO> preReigstrationComponentList= LookupTableBean.getInstance().getCodeLkupList().get("PRJ_7_ATTB");	
			if (CollectionUtils.isNotEmpty(preReigstrationComponentList)){
				for (ComponentDTO component: components){
					for (CodeLkupDTO dto: preReigstrationComponentList) {
						if (component!=null && component.getCompAttbId()!= null && component.getCompAttbId().equalsIgnoreCase(dto.getCodeId())) {
							
							//String attbId = component.getAttbId();
							String attbValue = component.getCompAttbVal();
							
							if (aoInd){
								if (!checkLogAlreadyExists(sbOrderId,attbValue,"Completed","Skipped")){
									generateHSRMLog(sbOrderId, attbValue, itemCode, salesCd, "Completed", "Skipped", null, null, validateHSRMResult);
								}
							}else if (queueRefService.validateVipNumberPattern(attbValue)){
								if (!checkLogAlreadyExists(sbOrderId,attbValue,"Completed","Skipped")){
									generateHSRMLog(sbOrderId, attbValue, itemCode, salesCd, "Completed", "Skipped", null, null, validateHSRMResult);
								}
							}else if (queueRefService.validateWalkInNumberPattern(attbValue)){
								if (!checkLogAlreadyExists(sbOrderId,attbValue,"Completed","Skipped")){
									generateHSRMLog(sbOrderId, attbValue, itemCode, salesCd, "Completed", "Skipped", null, null, validateHSRMResult);
								}
							}else{
								ResultDTO completeResult = completeHSRMStatus(sbOrderId, attbValue, itemCode, salesCd,validateHSRMResult);
								result.setReturnBool(completeResult.getReturnBool());
								result.setReturnMessage(completeResult.getReturnMessage());
							}
						}
					}
				}

			}
		}
		return result;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = AppRuntimeException.class)
	public ResultDTO completeHSRMStatus(String sbOrderId, String appointmentNo, String itemCode, String salesCd,HsrmDTO validateHSRMResult){
		logger.info("completeHSRMStatus() is called in OrderHsrmService");
		
		ResultDTO result = new ResultDTO();
		result.setReturnBool(true);
		com.hkcsl.hsrm.service.SingleQueueOnlineCompleteRequest singleQueueOnlineCompleteRequest = new com.hkcsl.hsrm.service.SingleQueueOnlineCompleteRequest();
		com.hkcsl.hsrm.service.SingleQueueOnlineCompleteRequest.Parameters param = new com.hkcsl.hsrm.service.SingleQueueOnlineCompleteRequest.Parameters();
		param.setAppointmentno(appointmentNo);
		param.setCompleteOrderNo(sbOrderId);
		param.setCollectItem(itemCode);
		param.setSystemSource("SBM");
		param.setLastUpdateBy(salesCd);
		singleQueueOnlineCompleteRequest.setParameters(param);
		
		
		ResultResponse response =  new ResultResponse();

		try {
			response = hsrmService.singleQueueOnlineComplete(singleQueueOnlineCompleteRequest);
		} catch (Exception e) {
			result.setReturnMessage("System cannot call \"HSRM Pre-reg number status complete\" temporary.");
			result.setReturnBool(true);
			generateHSRMLog(sbOrderId, appointmentNo, itemCode, salesCd, "Completed", "Failed", "-1", result.getReturnMessage(), validateHSRMResult);
			return result;
		}
		
		if (response.getData()==null){
			result.setReturnMessage("Invalid return while calling single queue online complete.");
			result.setReturnBool(false);
			generateHSRMLog(sbOrderId, appointmentNo, itemCode, salesCd, "Completed", "Failed", "-1", result.getReturnMessage(), validateHSRMResult);
		}else{
			logger.info("completeHSRMStatus Return Status:"+response.getData().getStatus());
			logger.info("completeHSRMStatus Return Message:"+response.getData().getMessage());
			
			if ("Success".equals(response.getData().getStatus())){
				result.setReturnBool(true);
				generateHSRMLog(sbOrderId, appointmentNo, itemCode, salesCd, "Completed", "Succeed", null, null, validateHSRMResult);
			}else{
				result.setReturnMessage(response.getData().getMessage());
				result.setReturnBool(false);
				generateHSRMLog(sbOrderId, appointmentNo, itemCode, salesCd, "Completed", "Failed", "-1", response.getData().getMessage(),validateHSRMResult);
			}
		}
		
		return result;
		
	}
	
	private void generateHSRMLog(String sbOrderId, String refId, String itemCode, String salesCd, String actionInd, String status, String errCd, String errMsg, HsrmDTO hsrmDTO){
		//, String queueStatus,String queueBrand,String basketBrand
		OrderHsrmLogDTO log = new OrderHsrmLogDTO();
		log.setOrderId(sbOrderId);
		log.setRefId(refId);
		log.setItemCode(itemCode);
		log.setSalesCd(salesCd);
		log.setActionInd(actionInd);
		log.setStatus(status);
		log.setErrCd(errCd);  //when fail
		log.setErrMsg(errMsg);  //when fail
		if (hsrmDTO!=null){
			log.setBasketBrand(hsrmDTO.getBasketBrand());
			log.setQueueBrand(hsrmDTO.getQueueBrand());
			log.setQueueStatus(hsrmDTO.getQueueStatus());
		}
		insertOrderHsrmLog(log);
	}
	
	
	public List<OrderHsrmLogDTO> getOrderHsrmLog(String orderId, String refId) {
		logger.info("getOrderHsrmLog() is called in OrderHsrmService");
		try {
			return orderHsrmDAO.getOrderHsrmLog(orderId, refId);
		} catch (DAOException e) {
			logger.error("Exception caught in getOrderHsrmLog", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public HsrmDTO validateHSRM(List<ComponentDTO> components,String orderId, String channelId, String itemCode, String salesCd, String docId, Date appDate, String basketBrand){
		logger.info("validateHSRM() is called in OrderHsrmService");
		HsrmDTO result = new HsrmDTO();
		result.setReturnBool(true);
		if (components != null && components.size()>0){
			List<CodeLkupDTO> preReigstrationComponentList= LookupTableBean.getInstance().getCodeLkupList().get("PRJ_7_ATTB");	
			if (CollectionUtils.isNotEmpty(preReigstrationComponentList)){
				for (CodeLkupDTO dto: preReigstrationComponentList) {
					for (ComponentDTO compo :components){
						if (compo!=null && compo.getCompAttbId()!= null && compo.getCompAttbId().equalsIgnoreCase(dto.getCodeId())) {
							OrderHsrmLogDTO existOrderConfirmedLogForOrder= null;
							OrderHsrmLogDTO existOrderCompletedLogForOrder= null;
							if ("2".equals(channelId) &&  StringUtils.isNotEmpty(orderId)){
								existOrderConfirmedLogForOrder = this.getOrderConfirmedHsrmLog(orderId);
							}
							
							if ( (!"2".equals(channelId)) &&  StringUtils.isNotEmpty(orderId)) {
								existOrderCompletedLogForOrder = this.getOrderCompletedHsrmLog(orderId);
							}
							
							//Matched with pre-defined VIP number pattern?													
							//Matched with pre-defined Walk-in number pattern?							
							//Validation Pre-registration number status from HSRM
							if ("2".equals(channelId) && existOrderConfirmedLogForOrder != null ){
								if (compo.getCompAttbVal()!=null && !compo.getCompAttbVal().equals(existOrderConfirmedLogForOrder.getRefId())){
									result.setReturnMessage("Pre-Registration Number ("+existOrderConfirmedLogForOrder.getRefId()+") has been attached to order. Not allowed to change.");
									result.setReturnBool(false);
									return result;
								}
							}else if ( (!"2".equals(channelId)) && existOrderCompletedLogForOrder != null ){
								if (compo.getCompAttbVal()!=null && !compo.getCompAttbVal().equals(existOrderCompletedLogForOrder.getRefId())){
									result.setReturnMessage("Pre-Registration Number ("+existOrderCompletedLogForOrder.getRefId()+") has been attached to order and is in Completed Status. Not allowed to change.");
									result.setReturnBool(false);
									return result;
								}
							}else if (queueRefService.validateVipNumberPattern(compo.getCompAttbVal())){
								
							}else if (queueRefService.validateWalkInNumberPattern(compo.getCompAttbVal()) ){
								//verify Walk-in number plus item code bypass control
																
								if (!queueRefService.verifyWalkInByPassControl(itemCode,compo.getCompAttbVal(),appDate)){
									result.setReturnMessage("This hero product CANNOT offer to walk-in customer");
									result.setReturnBool(false);
									return result;
								}
		
							}else{
								
								SQPCOrderDetailResponse sqpcOrder = null;
								
								try {
									sqpcOrder = sqpcService.getOrderDetailByRef(compo.getCompAttbVal());
								} catch (Exception e) {
									result.setReturnMessage("System cannot call \"HSRM Pre-reg number validation\" temporary.");
									result.setReturnBool(true);
									return result;
								}
								
								if (sqpcOrder == null || "-1".equals(sqpcOrder.getRefNo())){
									if (sqpcOrder == null){
										result.setReturnMessage("Pre-registration Number not found.");
										result.setReturnBool(false);
									}else{
										if ("Pre-registration Number not found.".equalsIgnoreCase(sqpcOrder.getMessage())){
											result.setReturnMessage(sqpcOrder.getMessage());
											result.setReturnBool(false);
										}else{
											result.setReturnBool(true);
											if ("Exception found.".equalsIgnoreCase(sqpcOrder.getMessage())){
												result.setReturnMessage("System cannot call \"HSRM Pre-reg number validation\" temporary.");
											}else{
												result.setReturnMessage(sqpcOrder.getMessage());
											}
										}
										
									}
									
									return result;
								}else{
									
									String brand = null;
									List<CodeLkupDTO> mobBrandList= LookupTableBean.getInstance().getCodeLkupList().get("MOB_BRAND");
									for (CodeLkupDTO mobBrand:mobBrandList ){
										if (mobBrand.getCodeId().equalsIgnoreCase(basketBrand)){
											brand = mobBrand.getCodeDesc();
										}
									}
									result.setBasketBrand(brand);
									result.setQueueBrand(sqpcOrder.getBrand());
									result.setQueueStatus(sqpcOrder.getQueueStatus());
									
									
									ResultDTO validateResult = queueRefService.validatePreRegStatusFromHSRM(sqpcOrder, compo.getCompAttbVal(), docId,basketBrand);
									if (!validateResult.getReturnBool() && (!"Brand not match".equalsIgnoreCase(validateResult.getReturnMessage()) && !"Stock allocated status".equalsIgnoreCase(validateResult.getReturnMessage()))){
										String returnErrMsg = "";
										result.setReturnBool(false);
										if (StringUtils.isEmpty(sqpcOrder.getMessage())){
											sqpcOrder.setMessage(validateResult.getReturnMessage());
										}
										
										String returnDocId ="";
										if (StringUtils.isNotEmpty(sqpcOrder.getDocId())&& sqpcOrder.getDocId().length()>=4 ){
											returnDocId = "****"+sqpcOrder.getDocId().substring(4, sqpcOrder.getDocId().length());
										}
										
										returnErrMsg = "Pre-registration Number:"+sqpcOrder.getRefNo()+"<br>Registered ID Doc Num: "+returnDocId+"<br>Pre-registration Number Status: "+sqpcOrder.getQueueStatus()+"<br>Order ID Doc Num: "+docId+"<br>Error Message: "+sqpcOrder.getMessage();
										result.setReturnMessage(returnErrMsg);
										return result;
									}
								}
								
								
							}
						}
					}
				}
			}
		}
		
		
		return result;
	}
	
	
	public OrderHsrmLogDTO getOrderConfirmedHsrmLog(String orderId) {
		logger.info("getOrderConfirmedHsrmLog() is called in OrderHsrmService");
		try {
			return orderHsrmDAO.getOrderConfirmedHsrmLog(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getOrderConfirmedHsrmLog", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public boolean hsrmConfirmed(String orderId){
		boolean hsrmConfirmed = false;
		OrderHsrmLogDTO existOrderConfirmedLogForOrder = null;
		if (StringUtils.isNotEmpty(orderId)) {
			existOrderConfirmedLogForOrder = this.getOrderConfirmedHsrmLog(orderId);
		}
		
		if (existOrderConfirmedLogForOrder!=null){
			hsrmConfirmed = true;
		}
		
		return hsrmConfirmed;
	}
	
	public OrderHsrmLogDTO getOrderCompletedHsrmLog(String orderId) {
		logger.info("getOrderCompletedHsrmLog() is called in OrderHsrmService");
		try {
			return orderHsrmDAO.getOrderCompletedHsrmLog(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in getOrderCompletedHsrmLog", e);
			throw new AppRuntimeException(e);
		}
	}
	
	public boolean hsrmCompleted(String orderId){
		boolean hsrmCompleted = false;
		OrderHsrmLogDTO existOrderCompletedLogForOrder = null;
		if (StringUtils.isNotEmpty(orderId)) {
			existOrderCompletedLogForOrder = this.getOrderCompletedHsrmLog(orderId);
		}
		
		if (existOrderCompletedLogForOrder!=null){
			hsrmCompleted = true;
		}
		
		return hsrmCompleted;
	}
	
	public boolean isOrderCompletedHsrmLogExist(String orderId) {
		logger.info("isOrderCompletedHsrmLogExist() is called in OrderHsrmService");
		try {
			return orderHsrmDAO.isOrderCompletedHsrmLogExist(orderId);
		} catch (DAOException e) {
			logger.error("Exception caught in isOrderCompletedHsrmLogExist", e);
			throw new AppRuntimeException(e);
		}
	}
	
	
	public boolean hsrmAllowRecreate(){
		boolean hsrmAllowRecreate = true;
		List<CodeLkupDTO> hsrmAllowRecreateList= LookupTableBean.getInstance().getCodeLkupList().get("HSRM_ALLOW_RECREATE");	
		if (CollectionUtils.isNotEmpty(hsrmAllowRecreateList)){
			if ("N".equalsIgnoreCase(hsrmAllowRecreateList.get(0).getCodeId())){
				hsrmAllowRecreate = false;
			}
		}
		
		return hsrmAllowRecreate;
	}
	
	public boolean isPrj7AttbExists(List<ComponentDTO> components){
		boolean exist = false;
		
		if (components != null && components.size()>0){
			List<CodeLkupDTO> preReigstrationComponentList= LookupTableBean.getInstance().getCodeLkupList().get("PRJ_7_ATTB");	
			if (CollectionUtils.isNotEmpty(preReigstrationComponentList)){
				for (CodeLkupDTO dto: preReigstrationComponentList) {
					for (ComponentDTO compo :components){
						if (compo!=null && compo.getCompAttbId()!= null && compo.getCompAttbId().equalsIgnoreCase(dto.getCodeId())) {
							exist  = true;
							break;
						}
					}
				}
			}
		}
		
		return exist;
		
	}
	
	public boolean checkLogAlreadyExists(String orderId, String attbVal, String action, String status){
		boolean exist = false;
		List<OrderHsrmLogDTO> hsrmLogList = getOrderHsrmLog(orderId, attbVal);

        if (CollectionUtils.isNotEmpty(hsrmLogList)) {
        	for (OrderHsrmLogDTO log : hsrmLogList) {
        		if (action != null & status != null){
	        		if (action.equalsIgnoreCase(log.getActionInd()) && status.equalsIgnoreCase(log.getStatus())) {
	        			exist = true;
	        		}
        		}
        	}
        }
        return exist;
	}
	
	

}
