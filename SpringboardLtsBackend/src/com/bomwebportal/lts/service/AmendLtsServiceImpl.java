package com.bomwebportal.lts.service;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.dto.report.ReportOutputDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dao.AmendLtsDAO;
import com.bomwebportal.lts.dao.PipbLtsWQServiceDAO;
import com.bomwebportal.lts.dao.order.FormPrintReqDAO;
import com.bomwebportal.lts.dto.order.AllOrdDocLtsDTO;
import com.bomwebportal.lts.dto.order.AmendAppointmentLtsDTO;
import com.bomwebportal.lts.dto.order.AmendCategoryLtsDTO;
import com.bomwebportal.lts.dto.order.AmendDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.AmendPaymentDTO;
import com.bomwebportal.lts.dto.order.AppointmentDetailLtsDTO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDTO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDetailDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailOtherLtsDTO;
import com.bomwebportal.lts.service.activity.PipbActivityLtsSubmissionService;
import com.bomwebportal.lts.service.order.AmendmentSubmitService;
import com.bomwebportal.lts.service.order.ImsSbOrderService;
import com.bomwebportal.lts.service.order.OrderCancelLtsService;
import com.bomwebportal.lts.service.order.OrderDetailService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.SaveSbOrderLtsService;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.util.LtsActvBackendConstant;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.wsClientLts.BomWsBackendClient;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.OrdEmailReqService;
import com.bomwebportal.service.ServiceActionBase;
import com.bomwebportal.service.ServiceActionImplBase;
import com.google.zxing.common.Collections;
import com.pccw.appendOrdRmk.ArrayOfString;
import com.pccw.appendOrdRmk.OrderRemarkDTO;
import com.pccw.updateSRD.ServiceResponseDTO;
import com.pccw.updateSRD.SrdDTO;
import com.pccw.util.spring.SpringApplicationContext;

public class AmendLtsServiceImpl implements AmendLtsService {

    private AmendLtsDAO amendLtsDao = null;
	private ImsSbOrderService imsSbOrderService;
	private AmendmentSubmitService amendmentSubmitService;
	private ReportCreationLtsService reportCreationLtsService;
	private CodeLkupCacheService reportSetLkupCacheService;
	private CodeLkupCacheService bomReadOnlyLkupCacheService;
	private CodeLkupCacheService autoAmendModeLkupCacheService;
	private ServiceActionImplBase allOrdDocLtsService;
	private OrderDetailService orderDetailService;
	private OrderStatusService orderStatusService;
	protected OrderRetrieveLtsService orderRetrieveLtsService;
	private SaveSbOrderLtsService saveSbOrderLtsService;
	private PipbActvLtsService pipbActvLtsService;
	private BomWsBackendClient bomWsBackendClient;
	private PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService;
	private OrderCancelLtsService orderCancelLtsService;
	private OrdEmailReqService ordEmailReqService;	
	private FormPrintReqDAO formPrintReqDao;
	private PipbLtsWQServiceDAO pipbLtsWQServiceDAO;
	
	private final Log logger = LogFactory.getLog(getClass());


	public String getNextBatchSeq(String orderId) throws DAOException {		
		try{
			return this.amendLtsDao.getNextBatchSeq(orderId);
		} catch (DAOException de) {
			logger.error("Fail in AmendLtsService.getNextBatchSeq()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	public String getNextItemSeq(String orderId, String dtlId, String batchSeq) throws DAOException {		
		try{
			return this.amendLtsDao.getNextItemSeq(orderId, dtlId, batchSeq);
		} catch (DAOException de) {
			logger.error("Fail in AmendLtsService.getNextItemSeq()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
    public void saveAmendmentAuditLog(OrderLtsAmendDTO orderLtsAmendDTO, String pOrderId, String pDtlId, String batchSeq, String pUser) {
		
		if (orderLtsAmendDTO == null) {
			return;
		}
		ServiceActionBase orderLtsAmendService = SpringApplicationContext.getBean("orderLtsAmendService");
		orderLtsAmendService.doSave(orderLtsAmendDTO, pUser, pOrderId, pDtlId, batchSeq);
		
	}
	
	@Transactional
    public void saveAmendmentDetailAuditLog(OrderLtsAmendDetailDTO orderLtsAmendDetailDTO, String pOrderId, String pDtlId, String batchSeq, String itemSeq, String itemSubSeq, String pUser) {
		
		if (orderLtsAmendDetailDTO == null) {
			return;
		}
		
		if(StringUtils.isBlank(itemSubSeq)){
			itemSubSeq = "0";
		}
		
		ServiceActionBase orderLtsAmendHistoryService = SpringApplicationContext.getBean("orderLtsAmendDetailService");
		orderLtsAmendHistoryService.doSave(orderLtsAmendDetailDTO, pUser, pOrderId, pDtlId, batchSeq, itemSeq, itemSubSeq);
		
	}

	@Transactional
    public OrderLtsAmendDTO retrieveApprovedAmendAuditLog(String pOrderId, String pDtlId/*, String batchSeq, String pUser*/) {
		try{
			return this.amendLtsDao.retrieveApprovedAmendLog(pOrderId, pDtlId);
		} catch (DAOException de) {
			logger.error("Fail in AmendLtsService.retrieveApprovedAmendmentAuditLog()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	@Transactional
    public List<OrderLtsAmendDetailDTO> retrieveApprovedAmendDetailAuditLog(String pOrderId, String pDtlId/*, String batchSeq, String itemSeq, String pUser*/) {
		try{
			return this.amendLtsDao.retrieveApprovedAmendDetailLog(pOrderId, pDtlId);
		} catch (DAOException de) {
			logger.error("Fail in AmendLtsService.retrieveApprovedAmendmentDetailAuditLog()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public void updateApprovalStatus(String orderId, String pUser, String status) throws DAOException {		
		try{
			this.amendLtsDao.updateApprovalStatus(orderId, pUser, status);
		} catch (DAOException de) {
			logger.error("Fail in AmendLtsService.updateApprovalStatus()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}

	private void updateErrorMsg(String orderId, String pUser, String errorMsg, String batchId, String dtlId) throws DAOException {		
		try{
			String subStrErrorMsg = StringUtils.substring(errorMsg, 0, 1000);
			this.amendLtsDao.updateErrorMsg(orderId, pUser, subStrErrorMsg, batchId, dtlId);
		} catch (DAOException de) {
			logger.error("Fail in AmendLtsService.updateApprovalStatus()", de);
			throw new RuntimeException(de.getCustomMessage());
		}
	}
	
	public String completeAmendment(String orderId) throws DAOException{
//		updateErrorMsg(orderId, "AmendSrv", "Called completeAmendmnet. OrderId = " +orderId, "0");
		
		SbOrderDTO sbOrder = orderRetrieveLtsService.retrieveSbOrder(orderId, true);
		
		ServiceDetailLtsDTO coreSrvDtl = LtsSbHelper.getLtsService(sbOrder);
		ServiceDetailLtsDTO pipbSrvDtl = LtsSbHelper.getAcqPipbService(sbOrder.getSrvDtls());
		ServiceDetailLtsDTO secDelSrvDtl = (ServiceDetailLtsDTO) LtsSbHelper.get2ndDelService(sbOrder);
		ServiceDetailOtherLtsDTO srvDtlIms = LtsSbHelper.getImsEyeService(sbOrder.getSrvDtls());
		ServiceDetailLtsDTO pipbPortLaterSrv = LtsSbHelper.getAcqPortLaterService(sbOrder);
		AmendLtsDTO amend = null;
		List<AmendDetailLtsDTO> amendDetailList = new ArrayList<AmendDetailLtsDTO>();
		
		OrderLtsAmendDTO coreSrvAmendLog = retrieveApprovedAmendAuditLog(sbOrder.getOrderId(), coreSrvDtl.getDtlId());
		OrderLtsAmendDTO secDelSrvAmendLog = null;
		Map<String, List<OrderLtsAmendDetailDTO>> coreSrvAmendCatLogMap = null;
		Map<String, List<OrderLtsAmendDetailDTO>> secDelSrvAmendCatLogMap = null;
		List<OrderLtsAmendDetailDTO> pipbPortLaterAmendDtlLogList = null;
		
		String selectedDtl = null;
		String noticeEmail = null;
		String noticeSmsNo = null;
		String requestDate = null;
		String batchSeq = null;
		
		if(coreSrvAmendLog != null){
			amend = new AmendLtsDTO();
			amend.setOcid(sbOrder.getOcid()); //TODO refer orderAmendCreateController getOcId
			amend.setOrderId(coreSrvAmendLog.getOrderId());
			amend.setSalesChannel(coreSrvAmendLog.getSalesChannel());
			amend.setSalesContactNum(coreSrvAmendLog.getSalesContactNum());
			amend.setShopCd(coreSrvAmendLog.getShopCd());
			amend.setStaffNum(coreSrvAmendLog.getStaffNum());
			amend.setSalesCd(coreSrvAmendLog.getSalesCd());
			amend.setName(coreSrvAmendLog.getAppName());
			amend.setRelationshipCd(coreSrvAmendLog.getAppRelationCd());
			amend.setContactNum(coreSrvAmendLog.getAppContactNum());
			noticeEmail = coreSrvAmendLog.getEmailAddr();
			noticeSmsNo = coreSrvAmendLog.getSmsNo();
			requestDate = coreSrvAmendLog.getCreateDate();
			batchSeq = coreSrvAmendLog.getBatchSeq();
		}

		List<OrderLtsAmendDetailDTO> coreSrvAmendDtlLogList = retrieveApprovedAmendDetailAuditLog(sbOrder.getOrderId(), coreSrvDtl.getDtlId());

		if(coreSrvAmendDtlLogList != null && coreSrvAmendDtlLogList.size() > 0){
			AmendDetailLtsDTO amendDetail = new AmendDetailLtsDTO();
			amendDetail.setSrvNum(coreSrvDtl.getSrvNum());
			amendDetail.setSrvType(coreSrvDtl.getTypeOfSrv());
			amendDetail.setDtlId(coreSrvDtl.getDtlId());
			amendDetail.setFromProd(coreSrvDtl.getFromProd());
			amendDetail.setToProd(coreSrvDtl.getToProd());
			selectedDtl = coreSrvDtl.getDtlId();
			
			if (srvDtlIms != null) {	
				if (StringUtils.isEmpty(srvDtlIms.getRelatedSbOrderId())) {
					amendDetail.setRelatedSrvNum(srvDtlIms.getSrvNum());	
				} else {
					amendDetail.setRelatedSrvNum(this.imsSbOrderService.getFsaNumOnImsSbOrder(srvDtlIms.getRelatedSbOrderId()));	
				}
				amendDetail.setRelatedSrvType(srvDtlIms.getTypeOfSrv());
			}
			
			if(pipbPortLaterSrv != null){
				if(StringUtils.equals(coreSrvDtl.getSbStatus(), LtsBackendConstant.ORDER_STATUS_CLOSED)){
					/*ACQ order already completed, amend port-later service only instead*/
					amendDetail.setSrvNum(pipbPortLaterSrv.getSrvNum());
					amendDetail.setSrvType(pipbPortLaterSrv.getTypeOfSrv());
					amendDetail.setDtlId(pipbPortLaterSrv.getDtlId());
				}
				
				amendDetail.setRelatedSrvNum(coreSrvDtl.getSrvNum());	
				amendDetail.setRelatedSrvType(coreSrvDtl.getTypeOfSrv());
				
				pipbPortLaterAmendDtlLogList = retrieveApprovedAmendDetailAuditLog(sbOrder.getOrderId(), pipbPortLaterSrv.getDtlId());
			}
			
			/*Create <category:amend detail> mapping for further process*/
			coreSrvAmendCatLogMap = createAmendCatLogMap(coreSrvAmendDtlLogList);
			
			/*Create Objects for amendmentSubmitService to process*/
			List<AmendCategoryLtsDTO> categoryList = createAmendCategoryList(coreSrvAmendCatLogMap, pipbPortLaterAmendDtlLogList,
					coreSrvDtl, pipbPortLaterSrv, coreSrvDtl.getSbStatus(), StringUtils.substring(coreSrvDtl.getAppointmentDtl().getAppntStartTime(), 0, 10));
			if(categoryList != null && !categoryList.isEmpty()){
				amendDetail.setCategoryList(categoryList);
				amendDetailList.add(amendDetail);
			}
		}
			
		
		if(secDelSrvDtl != null){
			secDelSrvAmendLog = retrieveApprovedAmendAuditLog(sbOrder.getOrderId(), secDelSrvDtl.getDtlId());
			if(secDelSrvAmendLog != null){
				requestDate = requestDate == null? secDelSrvAmendLog.getCreateDate() : requestDate;
				if(amend == null){
					amend = new AmendLtsDTO();
					amend.setOcid(sbOrder.getOcid());
					amend.setOrderId(secDelSrvAmendLog.getOrderId());
					amend.setSalesChannel(secDelSrvAmendLog.getSalesChannel());
					amend.setSalesContactNum(secDelSrvAmendLog.getSalesContactNum());
					amend.setShopCd(secDelSrvAmendLog.getShopCd());
					amend.setStaffNum(secDelSrvAmendLog.getStaffNum());
					amend.setSalesCd(secDelSrvAmendLog.getSalesCd());
					amend.setName(secDelSrvAmendLog.getAppName());
					amend.setRelationshipCd(secDelSrvAmendLog.getAppRelationCd());
					amend.setContactNum(secDelSrvAmendLog.getAppContactNum());
					noticeEmail = secDelSrvAmendLog.getEmailAddr();
					noticeSmsNo = secDelSrvAmendLog.getSmsNo();
					batchSeq = secDelSrvAmendLog.getBatchSeq();
				}
			}
			
			
			List<OrderLtsAmendDetailDTO> secDelSrvAmendLogList = retrieveApprovedAmendDetailAuditLog(sbOrder.getOrderId(), secDelSrvDtl.getDtlId());
			
			if(secDelSrvAmendLogList != null && secDelSrvAmendLogList.size() > 0){
				AmendDetailLtsDTO amendDetail = new AmendDetailLtsDTO();
				amendDetail.setSrvNum(secDelSrvDtl.getSrvNum());
				amendDetail.setSrvType(secDelSrvDtl.getTypeOfSrv());
				amendDetail.setDtlId(secDelSrvDtl.getDtlId());
				amendDetail.setFromProd(secDelSrvDtl.getFromProd());
				amendDetail.setToProd(secDelSrvDtl.getToProd());
				
				if(StringUtils.isBlank(selectedDtl)){
					selectedDtl = secDelSrvDtl.getDtlId();
				}
				
				if(coreSrvDtl != null){
					amendDetail.setRelatedSrvNum(coreSrvDtl.getSrvNum());	
					amendDetail.setRelatedSrvType(coreSrvDtl.getTypeOfSrv());
				}
				
				secDelSrvAmendCatLogMap = createAmendCatLogMap(secDelSrvAmendLogList);
				List<AmendCategoryLtsDTO> categoryList = createAmendCategoryList(secDelSrvAmendCatLogMap, null,
						secDelSrvDtl, null, coreSrvDtl.getSbStatus(), StringUtils.substring(secDelSrvDtl.getAppointmentDtl().getAppntStartTime(), 0, 10));
				
				if(categoryList != null && !categoryList.isEmpty()){
					amendDetail.setCategoryList(categoryList);
					amendDetailList.add(amendDetail);
				}
				
			}
			
		}
		
		/*Check amendment type*/
		Set<String> amendmentKeySet = new HashSet<String>();
		if(coreSrvAmendCatLogMap != null && !coreSrvAmendCatLogMap.isEmpty()){
			for(String key:coreSrvAmendCatLogMap.keySet()){
				if(coreSrvAmendCatLogMap.get(key) != null && !coreSrvAmendCatLogMap.get(key).isEmpty()){
					amendmentKeySet.add(key);
				}
			}
		}
		if(secDelSrvAmendCatLogMap != null && !secDelSrvAmendCatLogMap.isEmpty()){
			for(String key:secDelSrvAmendCatLogMap.keySet()){
				if(secDelSrvAmendCatLogMap.get(key) != null && !secDelSrvAmendCatLogMap.get(key).isEmpty()){
					amendmentKeySet.add(key);
				}
			}
		}
		
		boolean isCancelOrder = amendmentKeySet.contains(LtsBackendConstant.AMEND_CATEGORY_ORDER_CANCELLATION_VALUE);
		boolean isAmendAcqPipb = amendmentKeySet.contains(LtsBackendConstant.AMEND_CATEGORY_PIPB_INFO_VALUE);
		boolean isAmendAppnt = amendmentKeySet.contains(LtsBackendConstant.AMEND_CATEGORY_APPOINTMENT_VALUE);
		boolean isAmendDoc = amendmentKeySet.contains(LtsBackendConstant.AMEND_CATEGORY_DOCUMENT_VALUE);
		boolean isAmendByFreeInput = amendmentKeySet.contains(LtsBackendConstant.AMEND_CATEGORY_FREE_INPUT_VALUE);
		boolean isAmendOnlyAppnt = !isCancelOrder && !isAmendByFreeInput && !isAmendAcqPipb && !isAmendDoc && isAmendAppnt;
			
		if(amend != null){		
			if(pipbSrvDtl != null){
				amend.setPipbOrder(true);
			}
				
			amend.setAmendDtlList(amendDetailList);
			
			String username = amend.getStaffNum();
			String shopCd = amend.getShopCd();
			
			String batchUpdateStatus = LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_COMPLETE;
			int amendSubmitCheckPt = 0;
			if(StringUtils.equals(autoAmendModeLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey(), LtsBackendConstant.AUTO_AMEND_SWITCH_ON)){
				//Update SRD via WebService ---------Modified by LTS Max.R.Meng
				if(isAmendAppnt){
					if(StringUtils.equals(bomReadOnlyLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey(), LtsBackendConstant.BOM_READONLY_SWTICH_OFF)
							&& LtsSbHelper.getAcqPortLaterService(sbOrder) == null && coreSrvDtl.getAppointmentDtl().getPreWiringStartTime() == null){
							
						Boolean ffpWQInd_ini = new Boolean(true);
						Boolean[] ffpWQInd = new Boolean[]{ffpWQInd_ini};
						if(LtsSbHelper.getLtsEyeService(sbOrder) != null){
							if(LtsSbHelper.isRetentionOrder(sbOrder) && coreSrvDtl !=null && StringUtils.isNotEmpty(coreSrvDtl.getLegacyOrdNum())){
								updateSRDAuto(amend, sbOrder, coreSrvDtl, coreSrvAmendLog, batchUpdateStatus, new Boolean[] {false});
							}
						}else{
							if( coreSrvDtl !=null && StringUtils.isNotEmpty(coreSrvDtl.getLegacyOrdNum())){
								updateSRDAuto(amend, sbOrder, coreSrvDtl, coreSrvAmendLog, batchUpdateStatus, ffpWQInd);
						    }				
						}
						
						if(LtsSbHelper.getLtsEyeService(sbOrder) != null){
							if(LtsSbHelper.isRetentionOrder(sbOrder) && secDelSrvDtl !=null && StringUtils.isNotEmpty(secDelSrvDtl.getLegacyOrdNum())){
								updateSRDAuto(amend, sbOrder, secDelSrvDtl, secDelSrvAmendLog, batchUpdateStatus, new Boolean[] {false});
							}
							//EYE UPGRADE/EYE ACQ 2DEL AUTO AMENDMENT
							if((StringUtils.equals(sbOrder.getOrderType(), LtsBackendConstant.ORDER_TYPE_SB_ACQUISITION) || StringUtils.equals(sbOrder.getOrderType(), LtsBackendConstant.ORDER_TYPE_SB_UPGRADE)) 
									&& secDelSrvDtl !=null && StringUtils.isNotEmpty(secDelSrvDtl.getLegacyOrdNum())){
								updateSRDAuto(amend, sbOrder, secDelSrvDtl, secDelSrvAmendLog, batchUpdateStatus, new Boolean[] {false});
							}
						}else{
							if( secDelSrvDtl !=null && StringUtils.isNotEmpty(secDelSrvDtl.getLegacyOrdNum())){
								updateSRDAuto(amend, sbOrder, secDelSrvDtl, secDelSrvAmendLog, batchUpdateStatus, ffpWQInd);
						    }				
						}			
					}				  
				}
							
				//Auto Cancel Order ------------Modified by LTS Max.R.Meng
				if(isCancelOrder){				
					if(StringUtils.equals(bomReadOnlyLkupCacheService.getCodeLkupDAO().getCodeLkup()[0].getItemKey(), LtsBackendConstant.BOM_READONLY_SWTICH_OFF)
							&& LtsSbHelper.getAcqPortLaterService(sbOrder) == null && coreSrvDtl.getAppointmentDtl().getPreWiringStartTime() == null){
						if(LtsSbHelper.getLtsEyeService(sbOrder) == null){
								  if(secDelSrvDtl != null && StringUtils.equals(selectedDtl, secDelSrvDtl.getDtlId())){
									  if(StringUtils.isNotEmpty(amend.getOcid())){
											  cancelOrderAuto(amend, secDelSrvDtl, sbOrder, secDelSrvAmendLog ,batchUpdateStatus, true);  
									  }
								  }else{
									  if(coreSrvDtl != null && StringUtils.isNotEmpty(amend.getOcid())){
											  cancelOrderAuto(amend, coreSrvDtl, sbOrder, coreSrvAmendLog ,batchUpdateStatus, true);  
									  }
									  if(secDelSrvDtl != null && StringUtils.isNotEmpty(amend.getOcid())){
											  cancelOrderAuto(amend, secDelSrvDtl, sbOrder, secDelSrvAmendLog ,batchUpdateStatus, false);
									  }
								  } 	
						}else{
							if(LtsSbHelper.isRetentionOrder(sbOrder)){    //EYE Retention Case
									  if(secDelSrvDtl != null && StringUtils.equals(selectedDtl, secDelSrvDtl.getDtlId())){
										  if(StringUtils.isNotEmpty(amend.getOcid())){
												  cancelOrderAuto(amend, secDelSrvDtl, sbOrder, secDelSrvAmendLog ,batchUpdateStatus, false);  
										  }
									  }else{
										  if(coreSrvDtl != null && StringUtils.isNotEmpty(amend.getOcid())){
												  cancelOrderAuto(amend, coreSrvDtl, sbOrder, coreSrvAmendLog ,batchUpdateStatus, false);  
										  }
										  if(secDelSrvDtl != null && StringUtils.isNotEmpty(amend.getOcid())){
												  cancelOrderAuto(amend, secDelSrvDtl, sbOrder, secDelSrvAmendLog ,batchUpdateStatus, false);
										  }
									  } 
								 // }	
							}
							//EYE UPGRADE/EYE ACQ 2DEL AUTO CANCELATION
							if((StringUtils.equals(sbOrder.getOrderType(), LtsBackendConstant.ORDER_TYPE_SB_ACQUISITION) || StringUtils.equals(sbOrder.getOrderType(), LtsBackendConstant.ORDER_TYPE_SB_UPGRADE)) 
									&& secDelSrvDtl !=null && StringUtils.isNotEmpty(secDelSrvDtl.getLegacyOrdNum())){
								cancelOrderAuto(amend, secDelSrvDtl, sbOrder, secDelSrvAmendLog ,batchUpdateStatus, false);
							}
						}											
					}																	
				}
			}
			
			
			Exception catchedException = null;
			try{
				boolean submitAmendmentToWQInd = true;
				/* Will not submit to WQ if only amend pipb appointment but first order is not closed yet */
				if(isAmendOnlyAppnt 
						&& pipbPortLaterSrv != null
						&& isAmendPipbAppntOnly(amend)
						&& !StringUtils.equals(coreSrvDtl.getSbStatus(), LtsBackendConstant.ORDER_STATUS_CLOSED)){
					submitAmendmentToWQInd = false;
				}
				
				if(amendDetailList != null && amendDetailList.size() > 0){
					amend.setAmendDtlList(amendDetailList);
					if(!(StringUtils.equals(coreSrvDtl.getSbStatus(), LtsBackendConstant.ORDER_STATUS_FORCED_WQ) && isCancelOrder)){
						amendSubmitCheckPt = 10;
						if(submitAmendmentToWQInd){
							amendSubmitCheckPt = 11;
							amendmentSubmitService.submitAmendment(amend, username, shopCd);
//							amendLtsDao.updateItemUpdateStatus(sbOrder.getOrderId(), username, LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_COMPLETE, dtlId, batchSeq, amendCat)
							updateCompleteStatus(coreSrvAmendLog, secDelSrvAmendLog, pipbPortLaterSrv, sbOrder.getOrderId(), username, 
									LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_COMPLETE, 
									new String[]{LtsBackendConstant.AMEND_CATEGORY_APPOINTMENT_VALUE, 
													LtsBackendConstant.AMEND_CATEGORY_CREDIT_CARD_VALUE,
													LtsBackendConstant.AMEND_CATEGORY_FREE_INPUT_VALUE});
						}else{
							amendSubmitCheckPt = 12;
							amendmentSubmitService.saveAmendmentToOrder(amend, username);
						}
						amendSubmitCheckPt = 15;
					}
				}
				
				if(isCancelOrder){ 
					amendSubmitCheckPt = 20;
					
					/*If cancel PCD order only do not cancel SB order*/
					boolean isCancelPcdOrderOnly = false;
					if(coreSrvAmendCatLogMap != null){
						List<OrderLtsAmendDetailDTO> amendCancelList = coreSrvAmendCatLogMap.get(LtsBackendConstant.AMEND_CATEGORY_ORDER_CANCELLATION_VALUE);
						if(amendCancelList!= null){
							for(OrderLtsAmendDetailDTO amendDtl : amendCancelList){
								if(LtsBackendConstant.AMEND_CATEGORY_ORDER_CANCELLATION_VALUE.equals(amendDtl.getAmendCat())
										&& LtsBackendConstant.AMEND_ITEM_OF_ORDER_CANCEL_TYPE.equals(amendDtl.getAmendItem())
										&& LtsBackendConstant.WQ_NATURE_CANCEL_PCD_LIST.contains(amendDtl.getAmendValue())){
									isCancelPcdOrderOnly = true;
									break;
								}
							}
						}
					}
					
					if(!isCancelPcdOrderOnly){
						amendSubmitCheckPt = 23;
						if((secDelSrvDtl != null && StringUtils.equals(selectedDtl, secDelSrvDtl.getDtlId()))){
							//If secDel exist and secDel is selected
							orderStatusService.setCancelStatus(sbOrder.getOrderId(), secDelSrvDtl.getDtlId(), LtsBackendConstant.CANCEL_REASON_CD_AMEND, amend.getStaffNum(), secDelSrvDtl.getWorkQueueType());
						}else{
							orderCancelLtsService.cancelOrder(sbOrder, amend.getStaffNum(), LtsBackendConstant.CANCEL_REASON_CD_AMEND);
						}
						updateCompleteStatus(coreSrvAmendLog, secDelSrvAmendLog, null, sbOrder.getOrderId(), username, 
								LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_COMPLETE, new String[]{LtsBackendConstant.AMEND_CATEGORY_ORDER_CANCELLATION_VALUE});

					}
					amendSubmitCheckPt = 25;
				}
				

				//Update PIPB Info in SB
				if(isAmendAcqPipb && pipbSrvDtl.getPipb() != null && pipbSrvDtl.getPipb().getObjectAction() == ObjectActionBaseDTO.ACTION_UPDATED){
					amendSubmitCheckPt = 30;
					saveSbOrderLtsService.savePipb(pipbSrvDtl.getPipb(), sbOrder.getOrderId(), pipbSrvDtl.getDtlId(), username);
					updateCompleteStatus(coreSrvAmendLog, secDelSrvAmendLog, null, sbOrder.getOrderId(), username, 
							LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_COMPLETE, new String[]{LtsBackendConstant.AMEND_CATEGORY_PIPB_INFO_VALUE});

					amendSubmitCheckPt = 35;
				}
				
				if(pipbSrvDtl != null){
					if (((isAmendAppnt && !isAmendPrewiringOnly(amend)) || isAmendAcqPipb || isAmendDoc) 
							&& isCarrierTeamWqRequired(pipbSrvDtl, orderId, username)) {
						amendSubmitCheckPt = 40;
						pipbActvLtsService.updatePipbActivity(
								sbOrder, username, shopCd, "ORD_AMEND", "SUCCESS");

						amendSubmitCheckPt = 45;
					}
					if (isCancelOrder) {
						if(isCarrierTeamWqRequired(pipbSrvDtl, orderId, username)){
							if(secDelSrvDtl == null || !StringUtils.equals(selectedDtl, secDelSrvDtl.getDtlId())){
								amendSubmitCheckPt = 50;
								//If secDel not exist or secDel is not selected
								pipbActvLtsService.updatePipbActivity(
										sbOrder, username, shopCd, "ORD_CANCEL", "SUCCESS");
								amendSubmitCheckPt = 55;
							}
							
						}

						if (sbOrder != null && StringUtils.isBlank(sbOrder.getOcid())) {
							ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(sbOrder);
							if (ltsServiceDetail != null
									&& (LtsBackendConstant.DN_SOURCE_DN_RESERVED.equals(ltsServiceDetail.getDnSource()) 
											|| (LtsBackendConstant.DN_SOURCE_DN_PIPB.equals(ltsServiceDetail.getDnSource())))) {
								if (StringUtils.isNotBlank(ltsServiceDetail.getDnStatus())) {
									amendSubmitCheckPt = 60;
									bomWsBackendClient.dnAssignReq(
											ltsServiceDetail.getSrvNum(),
											ltsServiceDetail.getDnStatus(),
											LtsBackendConstant.DEFAULT_WS_ASSIGN_DN_RELEASE,
											ltsServiceDetail.getGatewayNum());
									amendSubmitCheckPt = 65;
								}
							}
						}

					}
					
				}
				amendSubmitCheckPt = 99;
			}catch(DAOException de){
				logger.error("Fail in AmendLtsService.completeApprovedAmendment(), OrderID = " + orderId + ", checkpoint = "+ amendSubmitCheckPt, de);
				de.setCustomMessage("Submit Amendment checkpoint = " + amendSubmitCheckPt + ".");
				de.printStackTrace();
				batchUpdateStatus = LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_PARTIAL;
				catchedException = de;
			}catch(Exception e){
				logger.error("Fail in AmendLtsService.completeApprovedAmendment(), OrderID = " + orderId + ", checkpoint = "+ amendSubmitCheckPt, e);
				e.printStackTrace();
				batchUpdateStatus = LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_PARTIAL;
				catchedException = e;
			}finally{
				try{
					generateAmendmentForm(amend, sbOrder, username);
				}catch(Exception e){
					logger.error("Fail in AmendLtsService.generateAmendmentForm(), OrderID = " + orderId, e);
					e.printStackTrace();
					batchUpdateStatus = LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_PARTIAL;
					catchedException = e;
				}
			}
					
			if(amendSubmitCheckPt <= 10){
				batchUpdateStatus = LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_FAILED;
			}

			if(coreSrvAmendLog != null){
				logResult(orderId, username, batchUpdateStatus, coreSrvAmendLog, amendSubmitCheckPt, catchedException);
			}
			if(secDelSrvAmendLog!= null){
				logResult(orderId, username, batchUpdateStatus, secDelSrvAmendLog, amendSubmitCheckPt, catchedException);
			}
			
			if(amendSubmitCheckPt != 99){
				return "Amendmend Failed!";
			}
			
			/*Create sms/email notice request for DS order*/
			if(LtsBackendConstant.ORDER_MODE_DIRECT_SALES.equals(sbOrder.getOrderMode())    	
			    	|| LtsBackendConstant.ORDER_MODE_DIRECT_SALES_NOW_TV_QA.equals(sbOrder.getOrderMode())){
				if(StringUtils.isNotBlank(noticeEmail)){
					Long printReqId = createFormPrintReq(sbOrder, username, LtsBackendConstant.SEND_METHOD_EMAIL, requestDate);
					boolean success = createAmendmentNoticeReq(sbOrder, requestDate, LtsBackendConstant.EMAIL_TEMPLATE_AMEND_NOTICE_EMAIL, noticeEmail, null, LtsBackendConstant.SEND_METHOD_EMAIL, username, printReqId);
					updateErrorMsg(orderId, username, 
							success? "Created notice email request." : "Failed to create notice email request.", batchSeq, null);
				}
				if(StringUtils.isNotBlank(noticeSmsNo)){
					Long printReqId = createFormPrintReq(sbOrder, username, LtsBackendConstant.SEND_METHOD_SMS, requestDate);
					boolean success = createAmendmentNoticeReq(sbOrder, requestDate, LtsBackendConstant.EMAIL_TEMPLATE_AMEND_NOTICE_SMS, null, noticeSmsNo, LtsBackendConstant.SEND_METHOD_SMS, username, printReqId);
					updateErrorMsg(orderId, username, 
							success? "Created notice SMS request." : "Failed to create notice SMS request.", batchSeq, null);
				}
			}
			
			
			return "Amendmend Success.";
		}
		return "Nothing is updated.";
		
	}
	
	private Map<String, Boolean> checkAmendAppntType(AmendLtsDTO amend){
		Map<String, Boolean> amendAppntType = new HashMap<String, Boolean>();
		amendAppntType.put("isAmendAppnt", false);
		amendAppntType.put("isAmendPrewiring", false);
		amendAppntType.put("isAmendPipbAppnt", false);
		
		for(AmendDetailLtsDTO amendDtl: amend.getAmendDtlList()){
			for(AmendCategoryLtsDTO amendCat: amendDtl.getCategoryList()){
				if (amendCat instanceof AmendAppointmentLtsDTO) {
					AmendAppointmentLtsDTO appntCat = (AmendAppointmentLtsDTO) amendCat;
					if(StringUtils.isNotBlank(appntCat.getAppntStartTime())){
						if(!StringUtils.equals(appntCat.getAppntStartTime(), appntCat.getFromAppntStartTime())){
							amendAppntType.put("isAmendAppnt", true);
						}
					}
					if(StringUtils.isNotBlank(appntCat.getPreWiringStartTime())){
						if(!StringUtils.equals(appntCat.getPreWiringStartTime(), appntCat.getFromPreWiringStartTime())){
							amendAppntType.put("isAmendPrewiring", true);
						}
					}
					if(StringUtils.isNotBlank(appntCat.getPipbAppntStartTime())){
						if(!StringUtils.equals(appntCat.getPipbAppntStartTime(), appntCat.getFromPipbAppntStartTime())){
							amendAppntType.put("isAmendPipbAppnt", true);
						}
					}
				}
			}
		}
		
		return amendAppntType;
	}
	
	private boolean isAmendPrewiringOnly(AmendLtsDTO amend){
		Map<String, Boolean> amendAppntType = checkAmendAppntType(amend);
		return amendAppntType.get("isAmendPrewiring") && !amendAppntType.get("isAmendAppnt") && !amendAppntType.get("isAmendPipbAppnt");
	}
	
	private boolean isAmendPipbAppntOnly(AmendLtsDTO amend){
		Map<String, Boolean> amendAppntType = checkAmendAppntType(amend);
		return !amendAppntType.get("isAmendPrewiring") && !amendAppntType.get("isAmendAppnt") && amendAppntType.get("isAmendPipbAppnt");
	}
	
	//auto update SRD-------------Modified by Max.R.MENG	
	private boolean cancelOrderAuto(AmendLtsDTO amend, ServiceDetailLtsDTO delSrvDtl ,SbOrderDTO sbOrder, OrderLtsAmendDTO delSrvAmendLog, String batchUpdateStatus, Boolean ffpWQInd) throws DAOException{		
		try{
			StringBuilder cancelRmk = new StringBuilder();			
			for(int i=0; i<amend.getAmendDtlList().size(); i++){
				for(AmendCategoryLtsDTO cat: amend.getAmendDtlList().get(i).getCategories()){	
					if(cat.getRemarks() != null){
						for(RemarkDetailLtsDTO rmk: cat.getRemarks()){
							cancelRmk.append(rmk.getRmkDtl());
							cancelRmk.append("\n");
						}
					}
				}
			}
		
			
			com.pccw.cancelOrder.ServiceResponseDTO response = bomWsBackendClient.cancelSRD(amend.getOcid(), delSrvDtl.getSrvNum(), delSrvDtl.getTypeOfSrv(), amend.getStaffNum(), sbOrder.getBoc(), delSrvDtl.getDiscReasonCode(), cancelRmk.toString());
			if(StringUtils.isNotBlank(response.getRejectMessage())){
				logger.error("OCID: " + amend.getOcid() + "Service Num: " + delSrvDtl.getSrvNum() + response.getRejectMessage());
				updateErrorMsg(amend.getOrderId(), amend.getStaffNum(), "Cancel Order WS reject" + response.getRejectMessage() + " remark: " + cancelRmk.toString(),  delSrvAmendLog.getBatchSeq(), delSrvDtl.getDtlId());
				return false;
			}
		}catch(Exception ex){
			logger.error("Web service error: " , ex);
			updateErrorMsg(amend.getOrderId(), amend.getStaffNum(), "Cancel Order WS reject", delSrvAmendLog.getBatchSeq(), delSrvDtl.getDtlId());
			return false;
		}
		
		
		for(int i=0; amend.getAmendDtlList() != null && i<amend.getAmendDtlList().size(); i++){
			if(StringUtils.equals(amend.getAmendDtlList().get(i).getDtlId(), delSrvDtl.getDtlId())){
				for(AmendCategoryLtsDTO cat: amend.getAmendDtlList().get(i).getCategories()){
					cat.setNature(LtsBackendConstant.WQ_NATURE_AMEND_AUTO_UPDATE);	
			}
		    }
		}
			
		//For FFP, WQ to F&S to remove FFP
		if(LtsSbHelper.isInstallFfpItem(sbOrder) && ffpWQInd){
			 for(int i1=0; amend.getAmendDtlList() != null && i1<amend.getAmendDtlList().size(); i1++){
				 if(StringUtils.equals(amend.getAmendDtlList().get(i1).getDtlId(), delSrvDtl.getDtlId())){
					 
					 //SET FFP HANDLING IND
					 boolean ffpEffectInd = false;
					 
					 for(AmendCategoryLtsDTO cat: amend.getAmendDtlList().get(i1).getCategories()){

						 AmendCategoryLtsDTO amendCat = new AmendCategoryLtsDTO();
						 amendCat.setNature(LtsBackendConstant.WQ_NATURE_AMEND_FFP_HANDLE);
						 amendCat.setSrd(cat.getSrd());
						 
						 RemarkDetailLtsDTO remark= new RemarkDetailLtsDTO();
						 
						 ServiceDetailLtsDTO primarySrv = LtsSbHelper.getLtsService(sbOrder);
						 
						 String latestSrd = StringUtils.substring(primarySrv.getAppointmentDtl().getAppntStartTime(), 0, 10);
						 
						 LocalDate latestSrdLocalDate = LocalDate.parse(latestSrd, DateTimeFormat.forPattern("dd/MM/yyyy"));						
						 LocalDate requestLocalDate = LocalDate.parse(delSrvAmendLog.getCreateDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));

						 if(latestSrdLocalDate.isBefore(requestLocalDate) || latestSrdLocalDate.isEqual(requestLocalDate)){
								ffpEffectInd = true;
						 }						 						
						 
						 if(ffpEffectInd){
							 remark.setRmkDtl("F&S Action: FFP subscribed, cancel FFP with waive penalty & handle rental adjustment."
									 + "\n" + "Cancel Order - System Automated.");
						 }else{
							 remark.setRmkDtl("F&S Action: FFP subscribed, cancel FFP."
									 + "\n" + "Cancel Order - System Automated.");
						 }
						 					
						 amendCat.appendRemark(remark); 
						 amend.getAmendDtlList().get(i1).appendCategory(amendCat);
						 break;
					 } 
				 }
				
			 }
		}
		
		 if(LtsSbHelper.isPenaltyWaivedPlan(sbOrder) && ffpWQInd){		
				
			 for(int i1=0; amend.getAmendDtlList() != null && i1<amend.getAmendDtlList().size(); i1++){
				 if(StringUtils.equals(amend.getAmendDtlList().get(i1).getDtlId(), delSrvDtl.getDtlId())){
					 for(AmendCategoryLtsDTO cat: amend.getAmendDtlList().get(i1).getCategories()){
						 AmendCategoryLtsDTO amendCat = new AmendCategoryLtsDTO();
						 amendCat.setNature(LtsBackendConstant.WQ_NATURE_AMEND_PENALTY_WAIVED);
						 amendCat.setSrd(cat.getSrd());
						 
						 RemarkDetailLtsDTO remark= new RemarkDetailLtsDTO();
						 remark.setRmkDtl("F&S Action: Change waive penalty indicator from Y to N."
								             + "\n" + "Cancel Order - System Automated.");
						 amendCat.appendRemark(remark);
						 amend.getAmendDtlList().get(i1).appendCategory(amendCat);
						 break;
					 } 
				 }
				
			 }
		}
	
		return true;		
	}
	
	private boolean updateSRDAuto(AmendLtsDTO amend, SbOrderDTO sbOrder, ServiceDetailLtsDTO delSrvDtl, OrderLtsAmendDTO delSrvAmendLog, String batchUpdateStatus, Boolean[] ffpWQInd) throws DAOException{
		
		for(int i=0; i<amend.getAmendDtlList().size(); i++){
			if(StringUtils.equals(amend.getAmendDtlList().get(i).getDtlId(),delSrvDtl.getDtlId())){
				SrdDTO srdDTO = new SrdDTO();
				srdDTO.setLegacyOrdNum(delSrvDtl.getLegacyOrdNum());
				srdDTO.setLegacyActvSeq(delSrvDtl.getLegacyActvSeq());
				srdDTO.setToVisitInd(delSrvDtl.getForceFieldVisitInd());
				srdDTO.setStaffNum(amend.getStaffNum());
				
				AmendAppointmentLtsDTO appntCat = null;
				//SET FFP HANDLING IND
				boolean ffpEffectInd = false;
				for(AmendCategoryLtsDTO cat: amend.getAmendDtlList().get(i).getCategories()){
					if(cat instanceof AmendAppointmentLtsDTO){
						appntCat = (AmendAppointmentLtsDTO) cat;
											
						SimpleDateFormat orignal = new SimpleDateFormat("dd/MM/yyyy HH:mm");
						SimpleDateFormat target = new SimpleDateFormat("yyyyMMdd HHmm");
						String date;
						String end_Date;
						try {							
							date = target.format(orignal.parse(appntCat.getAppntStartTime()));
							
							String[] parts_start = date.split(" ");							
						    srdDTO.setToApptDate(parts_start[0]);
							srdDTO.setToStartTime(parts_start[1]);
							srdDTO.setToReasonCode(appntCat.getDelayReasonCd());		
							end_Date = target.format(orignal.parse(appntCat.getAppntEndTime()));
							String[] parts_end = end_Date.split(" ");
							srdDTO.setToEndTime(parts_end[1]);
							srdDTO.setFrApptDate(parts_start[0]);
							srdDTO.setFrStartTime(parts_start[1]);
							srdDTO.setFrReasonCode(appntCat.getDelayReasonCd());
							srdDTO.setToEndTime(parts_end[1]);
							//CHECK IF THE SRD CHANGES
							
							String latestSrd = StringUtils.substring(appntCat.getFromAppntStartTime(), 0, 10);
							
							LocalDate latestSrdLocalDate = LocalDate.parse(latestSrd, DateTimeFormat.forPattern("dd/MM/yyyy"));
							LocalDate requestLocalDate = LocalDate.parse(delSrvAmendLog.getCreateDate(), DateTimeFormat.forPattern("dd/MM/yyyy"));
							
							if(latestSrdLocalDate.isBefore(requestLocalDate) || latestSrdLocalDate.isEqual(requestLocalDate)){
								ffpEffectInd = true;
							}
							if(StringUtils.equals(appntCat.getFromAppntEndTime(),appntCat.getAppntEndTime())
									&& StringUtils.equals(appntCat.getFromAppntStartTime(), appntCat.getAppntStartTime())){
								return false;								
							}
						} catch (ParseException e) {
							e.printStackTrace();
							return false;
						}
					}
				}
				
				if(appntCat != null){
					srdDTO.setToApptType(appntCat.getAppntType());
					try{
						ServiceResponseDTO response = bomWsBackendClient.updateSRD(srdDTO, ("Y".equals(delSrvDtl.getForceFieldVisitInd())? "Y":"N"));
						if(StringUtils.isNotBlank(response.getRejectMessage())){
							logger.error(response.getRejectMessage());
							updateErrorMsg(amend.getOrderId(), amend.getStaffNum(), "Update SRD WS reject" + response.getRejectMessage(), delSrvAmendLog.getBatchSeq(), delSrvAmendLog.getDtlId());
							return false;
						}
					}catch(Exception ex){
						logger.error("Web service error: " , ex);
						updateErrorMsg(amend.getOrderId(), amend.getStaffNum(), "Update SRD WS reject", delSrvAmendLog.getBatchSeq(), delSrvAmendLog.getDtlId());
						return false;
					}
					
					//Amend SRD remark update to BOM
					OrderRemarkDTO orderRemark = new OrderRemarkDTO();
					orderRemark.setLegacyOrdNum(delSrvDtl.getLegacyOrdNum());
					orderRemark.setLegacyActvSeq(delSrvDtl.getLegacyActvSeq());
					orderRemark.setDtlId(delSrvDtl.getDtlId());
					orderRemark.setStaffNum(amend.getStaffNum());
					orderRemark.setOcId(sbOrder.getOcid());

					DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
					Date date = new Date();
					ArrayOfString ordRemark = new ArrayOfString();
					ordRemark.getString().add("\n");
					ordRemark.getString().add("Order Modified by : " + amend.getStaffNum() + " " + dateFormat.format(date) + " " + amend.getShopCd() + " (" + amend.getSalesContactNum() + ")");
					ordRemark.getString().add("Change New SRD (by System) : " + srdDTO.getToApptDate() + " " + srdDTO.getToStartTime() + "-" + srdDTO.getToEndTime());
					
					orderRemark.setOrderRemark(ordRemark);
					try{
						com.pccw.appendOrdRmk.ServiceResponseDTO appendRmkresponse = bomWsBackendClient.appendOrdRmk(orderRemark);
						if(StringUtils.isNotBlank(appendRmkresponse.getRejectMessage())){
							logger.error(appendRmkresponse.getRejectMessage());
							updateErrorMsg(amend.getOrderId(), amend.getStaffNum(), "Append Order remark to BOM Fail : " + appendRmkresponse.getRejectMessage(), delSrvAmendLog.getBatchSeq(), delSrvAmendLog.getDtlId());
						}
					}catch(Exception ex){
						logger.error("Web service error: " , ex);
						updateErrorMsg(amend.getOrderId(), amend.getStaffNum(), "Append Order remark to BOM Exception : " + ex, delSrvAmendLog.getBatchSeq(), delSrvAmendLog.getDtlId());
					}					
					
					appntCat.setNature(LtsBackendConstant.WQ_NATURE_AMEND_AUTO_UPDATE);
					//For FFP, WQ to F&S to remove FFP
					//NO need to generate WQ when create amendment date > = latest SRD
					if(LtsSbHelper.isInstallFfpItem(sbOrder) && ffpWQInd[0] && !ffpEffectInd){
						 for(int i1=0; amend.getAmendDtlList() != null && i1<amend.getAmendDtlList().size(); i1++){
							 if(StringUtils.equals(amend.getAmendDtlList().get(i1).getDtlId(), delSrvDtl.getDtlId())){
								 for(AmendCategoryLtsDTO cat: amend.getAmendDtlList().get(i1).getCategories()){
									 AmendCategoryLtsDTO amendCat = new AmendCategoryLtsDTO();
									 amendCat.setNature(LtsBackendConstant.WQ_NATURE_AMEND_FFP_HANDLE);
									 amendCat.setSrd(cat.getSrd());
									 
									 RemarkDetailLtsDTO remark= new RemarkDetailLtsDTO();
									 remark.setRmkDtl("F&S Action: FFP subscribed, amend FFP Effective Date."
											                         + "\n" 
	                                                                 + "Amend SRD - System Automated."
											                         + "\n"
	                                                                 + "Target Installation Date: " + StringUtils.substring(appntCat.getAppntStartTime(), 0, 10));
									 amendCat.appendRemark(remark);
									 amend.getAmendDtlList().get(i1).appendCategory(amendCat);
									 ffpWQInd[0] = false;
									 break;
								 } 
							 }
							
						 }
					}
					return true;
				}
				
			}			
		}	
		return false;
	}
	private void logResult(String orderId, String username, String batchUpdateStatus, OrderLtsAmendDTO srvAmendLog, int amendSubmitCheckPt, Exception catchedException ) throws DAOException{
		amendLtsDao.updateBatchUpdateStatus(orderId, username, batchUpdateStatus, srvAmendLog.getDtlId(), srvAmendLog.getBatchSeq());

		StringBuilder sb = new StringBuilder();
		if(amendSubmitCheckPt != 99){
			sb.append("ExceptionCaught in CompleteAmendment, CheckPt = ");
			sb.append(amendSubmitCheckPt);
			sb.append(", OrderId = ");
			sb.append(orderId);
			sb.append("\n");
			if(catchedException != null){
				sb.append(catchedException.toString());
			}
			updateErrorMsg(orderId, username, sb.toString(), srvAmendLog.getBatchSeq(), srvAmendLog.getDtlId());
		}else{
			sb.append("Finished CompleteAmendment, OrderId = ");
			sb.append(orderId);
			updateErrorMsg(orderId, username, sb.toString(), srvAmendLog.getBatchSeq(), srvAmendLog.getDtlId());
		}
	}
	
	private boolean isCarrierTeamWqRequired(ServiceDetailLtsDTO pipbService, String orderId, String username){
		if(pipbService == null){
			return false;
		}
		
		String startDate = StringUtils.isNotBlank(pipbService.getAppointmentDtl().getCutOverStartTime())
				? pipbService.getAppointmentDtl().getCutOverStartTime()
						: pipbService.getAppointmentDtl().getAppntStartTime();
		DateTime startDateTime = DateTime.parse(startDate, DateTimeFormat.forPattern("dd/MM/yyyy HH:mm"));
		/* No need to send for SRD > T+60 case */
		if(Math.abs(Days.daysBetween(DateTime.now(), startDateTime).getDays()) > 60){
			return false;
		}
		
		/* No need to send for WQ trans Pending case*/
		try {
			int pendingCnt = pipbLtsWQServiceDAO.countBomwebWqTrans(orderId, pipbService.getDtlId(), LtsBackendConstant.LTS_PIPB_WQ_ACTION_PIPB_TO_D, LtsBackendConstant.LTS_PIPB_WQ_STATUS_PENDING);
			if(pendingCnt >= 1){
				return false;
			}
		} catch (DAOException e) {
			logger.error("Failed to count pending WQ trans. order id: " + orderId + " - " + e.getMessage());
		}
		
		
		boolean isPipbWqTaskExist = false;
		boolean isPipbWqRejected = false;
		
		try {
			isPipbWqTaskExist = pipbActivityLtsSubmissionService.isTaskExistByType(orderId, username, 
					LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
			
			isPipbWqRejected = pipbActivityLtsSubmissionService.isStatusRejectByType(orderId, username, 
					LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
		} catch (Exception e) {
			logger.error("Failed to check Actv status. order id: " + orderId + " - "  + e.getMessage());
		}
		boolean pipbAmendAfterCutOver = DateTime.now().isAfter(startDateTime);
		boolean pipbWqRejectedOrNotExist = !isPipbWqTaskExist || (isPipbWqTaskExist && isPipbWqRejected);
		
		return (!pipbAmendAfterCutOver || pipbWqRejectedOrNotExist);
	}
	
	private void updateCompleteStatus(OrderLtsAmendDTO coreSrvAmendLog, OrderLtsAmendDTO secDelSrvAmendLog, 
			ServiceDetailLtsDTO portLaterSrv, String orderId, String userId, String status, String[] amendCat) throws DAOException{
		if(coreSrvAmendLog != null){
			amendLtsDao.updateItemUpdateStatus(orderId, userId, LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_COMPLETE, 
					coreSrvAmendLog.getDtlId(), coreSrvAmendLog.getBatchSeq(), Arrays.asList(amendCat));
			if(portLaterSrv != null){
				amendLtsDao.updateItemUpdateStatus(orderId, userId, LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_COMPLETE, 
						portLaterSrv.getDtlId(), coreSrvAmendLog.getBatchSeq(), Arrays.asList(amendCat));
			}
		}
		if(secDelSrvAmendLog != null){
			amendLtsDao.updateItemUpdateStatus(orderId, userId, LtsBackendConstant.AMEND_LOG_UPDATE_STATUS_COMPLETE, 
					secDelSrvAmendLog.getDtlId(), secDelSrvAmendLog.getBatchSeq(), Arrays.asList(amendCat));
		}
	}
	
	private Map<String, List<OrderLtsAmendDetailDTO>> createAmendCatLogMap(List<OrderLtsAmendDetailDTO> amendDtlLogList){
		/*Map out all catergorys details and items by type
		 * eg. amendCatLogMap: <"A", {(StartDate: 20150101 10:00), (EndDate: 20150101 12:00, .... )},
		 * 						"F", {(Type: VAS), (Input: "REMKARS"), .... }>*/
		Map<String, List<OrderLtsAmendDetailDTO>> amendCatLogMap = new HashMap<String, List<OrderLtsAmendDetailDTO>>();
		for(String amendCat : LtsBackendConstant.AMEND_CATEGORYS){
			amendCatLogMap.put(amendCat, new ArrayList<OrderLtsAmendDetailDTO>());
		}
		for(OrderLtsAmendDetailDTO amendDtlLog: amendDtlLogList){
			amendCatLogMap.get(amendDtlLog.getAmendCat()).add(amendDtlLog);
		}
		
		return amendCatLogMap;
	}
	
	private List<AmendCategoryLtsDTO> createAmendCategoryList(Map<String, List<OrderLtsAmendDetailDTO>> amendCatLogMap, 
			List<OrderLtsAmendDetailDTO> pipbPortLaterAmendDtlLogList, ServiceDetailLtsDTO processSrvDtl, ServiceDetailLtsDTO portLaterSrv, 
			String primarySrvStatus, String srd){
		List<AmendCategoryLtsDTO> categoryList = new ArrayList<AmendCategoryLtsDTO>();
		String newSrd = srd;
		for(String amendCatCd: amendCatLogMap.keySet()){
			List<OrderLtsAmendDetailDTO> amendDtlList = amendCatLogMap.get(amendCatCd);
			if(amendDtlList == null || amendDtlList.isEmpty()){
				continue;
			}
			
			if(LtsBackendConstant.AMEND_CATEGORY_APPOINTMENT_VALUE.equals(amendCatCd)){
				/*Need to include pipb appointment if exist*/
				newSrd = createAppointmentCategory(amendDtlList, pipbPortLaterAmendDtlLogList, processSrvDtl.getAppointmentDtl(), portLaterSrv, 
						primarySrvStatus, categoryList);
			}
			if(LtsBackendConstant.AMEND_CATEGORY_CREDIT_CARD_VALUE.equals(amendCatCd)){
				createCreditCardCategory(amendDtlList, categoryList);
			}
			if(LtsBackendConstant.AMEND_CATEGORY_DOCUMENT_VALUE.equals(amendCatCd)){
				//
			}
			if(LtsBackendConstant.AMEND_CATEGORY_FREE_INPUT_VALUE.equals(amendCatCd)){
				createFreeInputCategoryList(amendDtlList, categoryList);
			}
			if(LtsBackendConstant.AMEND_CATEGORY_ORDER_CANCELLATION_VALUE.equals(amendCatCd)){
				createOrderCancelCategory(amendDtlList, categoryList);
			}
			if(LtsBackendConstant.AMEND_CATEGORY_PIPB_INFO_VALUE.equals(amendCatCd)){
				processPipbInfoAmend(amendDtlList, processSrvDtl);
			}
		}
		
		for(AmendCategoryLtsDTO amendCat: categoryList){
			amendCat.setSrd(newSrd);
		}
		return categoryList;
	}
	
	private void processPipbInfoAmend(List<OrderLtsAmendDetailDTO> amendDtlList, ServiceDetailLtsDTO srvDtl){
//		pipbSrvDtl;
		if(srvDtl != null && srvDtl.getPipb() != null){
			
			for(OrderLtsAmendDetailDTO amendDtl: amendDtlList){
				if(LtsBackendConstant.AMEND_ITEM_OF_FLAT.equals(amendDtl.getAmendItem())){
					srvDtl.getPipb().setUnitNo(amendDtl.getAmendValue());
				}
				if(LtsBackendConstant.AMEND_ITEM_OF_FLOOR.equals(amendDtl.getAmendItem())){
					srvDtl.getPipb().setFloorNo(amendDtl.getAmendValue());
				}
				if(LtsBackendConstant.AMEND_ITEM_OF_FIRST_NAME.equals(amendDtl.getAmendItem())){
					srvDtl.getPipb().setFirstName(amendDtl.getAmendValue());
				}
				if(LtsBackendConstant.AMEND_ITEM_OF_LAST_NAME.equals(amendDtl.getAmendItem())){
					srvDtl.getPipb().setLastName(amendDtl.getAmendValue());
				}
				if(LtsBackendConstant.AMEND_ITEM_OF_TITLE.equals(amendDtl.getAmendItem())){
					srvDtl.getPipb().setTitle(amendDtl.getAmendValue());
				}
			}
			
			srvDtl.getPipb().setObjectAction(ObjectActionBaseDTO.ACTION_UPDATED);
		}
	}
	
	
	private void createOrderCancelCategory(List<OrderLtsAmendDetailDTO> amendDtlList, List<AmendCategoryLtsDTO> categoryList){
		AmendCategoryLtsDTO cancelOrder = new AmendCategoryLtsDTO();
		
		List<OrderLtsAmendDetailDTO> amendRemarkDtlList = new ArrayList<OrderLtsAmendDetailDTO>();
		
		for(OrderLtsAmendDetailDTO amendDtl: amendDtlList){
			if(LtsBackendConstant.AMEND_ITEM_OF_ORDER_CANCEL_TYPE.equals(amendDtl.getAmendItem())){
				cancelOrder.setNature(amendDtl.getAmendValue());
			}
			if(LtsBackendConstant.AMEND_ITEM_OF_ORDER_CANCEL_REASON.equals(amendDtl.getAmendItem())){
//				amendDtl.getAmendValue();
			}
			if(LtsBackendConstant.AMEND_ITEM_OF_ORDER_CANCEL_REMARK.equals(amendDtl.getAmendItem())){
				amendRemarkDtlList.add(amendDtl);
			}
		}
		
		cancelOrder.appendRemarks(createRemarkDetailDTO(amendRemarkDtlList));
		categoryList.add(cancelOrder);
		
	}
	
	private void createFreeInputCategoryList(List<OrderLtsAmendDetailDTO> amendDtlList, List<AmendCategoryLtsDTO> categoryList){
		
		//<itemSeq, freeInputAmendDtlList>
		Map<String, List<OrderLtsAmendDetailDTO>> freeInputAmendDtlMap = new HashMap<String, List<OrderLtsAmendDetailDTO>>();
		for(OrderLtsAmendDetailDTO amendDtl: amendDtlList){
			List<OrderLtsAmendDetailDTO> tempAmendDtlList = freeInputAmendDtlMap.get(amendDtl.getItemSeq());
			if(tempAmendDtlList == null){
				tempAmendDtlList = new ArrayList<OrderLtsAmendDetailDTO>();
			}
			tempAmendDtlList.add(amendDtl);
			freeInputAmendDtlMap.put(amendDtl.getItemSeq(), tempAmendDtlList);
		}
		
		for(String itemSeq: freeInputAmendDtlMap.keySet()){
			AmendCategoryLtsDTO category = new AmendCategoryLtsDTO();
			
			String amendItemCode = freeInputAmendDtlMap.get(itemSeq).get(0).getAmendItem();
			String nature = LtsBackendConstant.AMEND_ITEM_OF_FREE_INPUT_MAP.inverse().get(amendItemCode);
			if(StringUtils.isBlank(nature) && StringUtils.isNumeric(amendItemCode)){
				//In case of nature that is not defined in constants 
				nature = amendItemCode;
			}
			category.setNature(nature);
			
//			List<RemarkDetailLtsDTO> remarks = new ArrayList<RemarkDetailLtsDTO>();
//			
//			for(OrderLtsAmendDetailDTO amendDtl: freeInputAmendDtlMap.get(itemSeq)){
//				RemarkDetailLtsDTO remark = new RemarkDetailLtsDTO();
//				remark.setRmkDtl(amendDtl.getAmendValue());
//				remark.setRmkSeq(amendDtl.getItemSubSeq());
//				remarks.add(remark);
//			}
			
			RemarkDetailLtsDTO[] remarks = createRemarkDetailDTO(freeInputAmendDtlMap.get(itemSeq));
			category.appendRemarks(remarks);
			categoryList.add(category);
		}
	}
	
	private RemarkDetailLtsDTO[] createRemarkDetailDTO(List<OrderLtsAmendDetailDTO> amendRemarkDtlList){
		List<RemarkDetailLtsDTO> remarks = new ArrayList<RemarkDetailLtsDTO>();
		
		for(OrderLtsAmendDetailDTO amendDtl: amendRemarkDtlList){
			RemarkDetailLtsDTO remark = new RemarkDetailLtsDTO();
			remark.setRmkDtl(amendDtl.getAmendValue());
			remark.setRmkSeq(amendDtl.getItemSubSeq());
			remarks.add(remark);
		}
		
		return remarks.toArray(new RemarkDetailLtsDTO[0]);
	}
	
	private void createCreditCardCategory(List<OrderLtsAmendDetailDTO> amendDtlList, List<AmendCategoryLtsDTO> categoryList){
		AmendPaymentDTO paymentOrder = new AmendPaymentDTO();
		paymentOrder.setCcVerifiedInd("Y");
		paymentOrder.setNature(LtsBackendConstant.WQ_NATURE_AMEND_PAY_METHOD);
		
		List<OrderLtsAmendDetailDTO> amendRemarkDtlList = new ArrayList<OrderLtsAmendDetailDTO>();
		
		for(OrderLtsAmendDetailDTO amendDtl: amendDtlList){
			if(LtsBackendConstant.AMEND_ITEM_OF_CREDIT_CARD_EXPIRY_DATE.equals(amendDtl.getAmendItem())){
				paymentOrder.setCcExpDate(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_CREDIT_CARD_HOLDER_NAME.equals(amendDtl.getAmendItem())){
				paymentOrder.setCcHoldName(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_CREDIT_CARD_HOLDER_DOC_NUM.equals(amendDtl.getAmendItem())){
				paymentOrder.setCcIdDocNo(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_CREDIT_CARD_HOLDER_DOC_TYPE.equals(amendDtl.getAmendItem())){
				paymentOrder.setCcIdDocType(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_CREDIT_CARD_NUMBER.equals(amendDtl.getAmendItem())){
				paymentOrder.setCcNum(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_CREDIT_CARD_TYPE.equals(amendDtl.getAmendItem())){
				paymentOrder.setCcType(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_THIRTY_PARTY_INDICATOR.equals(amendDtl.getAmendItem())){
				paymentOrder.setCcThirdPartyInd(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_FAX_SERIAL.equals(amendDtl.getAmendItem())){
				paymentOrder.setFaxSerialNum(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_CREDIT_CARD_REMARK.equals(amendDtl.getAmendItem())){
				amendRemarkDtlList.add(amendDtl);
			}
			
		}
		
		if(amendRemarkDtlList != null && !amendRemarkDtlList.isEmpty()){
			paymentOrder.appendRemarks(createRemarkDetailDTO(amendRemarkDtlList));
		}
		
		categoryList.add(paymentOrder);
	}
	
	private String createAppointmentCategory(List<OrderLtsAmendDetailDTO> amendDtlList, 
			List<OrderLtsAmendDetailDTO> pipbPortLaterAmendDtlLogList, AppointmentDetailLtsDTO ordAppntDtl, 
			ServiceDetailLtsDTO ordPipbSrvDtl,
			String primarySrvStatus, List<AmendCategoryLtsDTO> categoryList ){
		AmendAppointmentLtsDTO apptOrder = new AmendAppointmentLtsDTO();
		apptOrder.setPrimarySrvStatus(primarySrvStatus);
		apptOrder.setNature(LtsBackendConstant.WQ_NATURE_AMEND_SRD);
		
		String newSrd = null;
		List<OrderLtsAmendDetailDTO> amendRemarkDtlList = new ArrayList<OrderLtsAmendDetailDTO>();
		
		apptOrder.setReferenceOrdAppnt(ordAppntDtl);
		apptOrder.setFromAppntStartTime(ordAppntDtl.getAppntStartTime());
		apptOrder.setFromAppntEndTime(ordAppntDtl.getAppntEndTime());
		if(StringUtils.isNotBlank(ordAppntDtl.getPreWiringStartTime())){
			apptOrder.setFromPreWiringStartTime(ordAppntDtl.getPreWiringStartTime());
			apptOrder.setFromPreWiringEndTime(ordAppntDtl.getPreWiringEndTime());
		}
		
		for(OrderLtsAmendDetailDTO amendDtl: amendDtlList){
			if(LtsBackendConstant.AMEND_ITEM_OF_APPOINTMENT_END_TIME.equals(amendDtl.getAmendItem())){
				apptOrder.setAppntEndTime(amendDtl.getAmendValue());
				apptOrder.getReferenceOrdAppnt().setAppntEndTime(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_APPOINTMENT_START_TIME.equals(amendDtl.getAmendItem())){
				apptOrder.setAppntStartTime(amendDtl.getAmendValue());
				apptOrder.getReferenceOrdAppnt().setAppntStartTime(amendDtl.getAmendValue());
				newSrd = StringUtils.substring(amendDtl.getAmendValue(), 0, 10);
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_CUT_OVER_END_TIME.equals(amendDtl.getAmendItem())){
				apptOrder.setCutOverEndTime(amendDtl.getAmendValue());
				apptOrder.getReferenceOrdAppnt().setCutOverEndTime(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_CUT_OVER_START_TIME.equals(amendDtl.getAmendItem())){
				apptOrder.setCutOverStartTime(amendDtl.getAmendValue());
				apptOrder.getReferenceOrdAppnt().setCutOverStartTime(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_DELAY_REA_CD.equals(amendDtl.getAmendItem())){
				apptOrder.setDelayReasonCd(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_PRE_BOOK_SERIAL.equals(amendDtl.getAmendItem())){
				apptOrder.setSerialNum(amendDtl.getAmendValue());
				apptOrder.getReferenceOrdAppnt().setSerialNum(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_APPNT_REMARK.equals(amendDtl.getAmendItem())){
				amendRemarkDtlList.add(amendDtl);
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_APPNT_TYPE.equals(amendDtl.getAmendItem())){
				apptOrder.setAppntType(amendDtl.getAmendValue());
				apptOrder.getReferenceOrdAppnt().setAppntType(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_PREWIRING_START_TIME.equals(amendDtl.getAmendItem())){
				apptOrder.setPreWiringStartTime(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_PREWIRING_END_TIME.equals(amendDtl.getAmendItem())){
				apptOrder.setPreWiringEndTime(amendDtl.getAmendValue());
			}
			else if(LtsBackendConstant.AMEND_ITEM_OF_PREWIRING_TYPE.equals(amendDtl.getAmendItem())){
				apptOrder.setPreWiringType(amendDtl.getAmendValue());
			}
		}
		
		if(pipbPortLaterAmendDtlLogList != null && ordPipbSrvDtl != null){
			apptOrder.setReferenceOrdPipbAppnt(ordPipbSrvDtl.getAppointmentDtl());
			apptOrder.setPipbSrvDtlId(ordPipbSrvDtl.getDtlId());
			apptOrder.setFromPipbAppntStartTime(ordPipbSrvDtl.getAppointmentDtl().getAppntStartTime());
			apptOrder.setFromPipbAppntEndTime(ordPipbSrvDtl.getAppointmentDtl().getAppntEndTime());
			
			for(OrderLtsAmendDetailDTO amendDtl: pipbPortLaterAmendDtlLogList){
				if(LtsBackendConstant.AMEND_ITEM_OF_APPOINTMENT_END_TIME.equals(amendDtl.getAmendItem())){
					apptOrder.setPipbAppntEndTime(amendDtl.getAmendValue());
					apptOrder.getReferenceOrdPipbAppnt().setAppntEndTime(amendDtl.getAmendValue());
				}
				if(LtsBackendConstant.AMEND_ITEM_OF_APPOINTMENT_START_TIME.equals(amendDtl.getAmendItem())){
					apptOrder.setPipbAppntStartTime(amendDtl.getAmendValue());
					apptOrder.getReferenceOrdPipbAppnt().setAppntStartTime(amendDtl.getAmendValue());
					if(StringUtils.equals(primarySrvStatus, LtsBackendConstant.ORDER_STATUS_CLOSED)){
						newSrd = StringUtils.substring(amendDtl.getAmendValue(), 0, 10);
					}
				}
				if(LtsBackendConstant.AMEND_ITEM_OF_CUT_OVER_END_TIME.equals(amendDtl.getAmendItem())){
					apptOrder.setPipbCutOverEndTime(amendDtl.getAmendValue());
					apptOrder.getReferenceOrdPipbAppnt().setCutOverEndTime(amendDtl.getAmendValue());
				}
				if(LtsBackendConstant.AMEND_ITEM_OF_CUT_OVER_START_TIME.equals(amendDtl.getAmendItem())){
					apptOrder.setPipbCutOverStartTime(amendDtl.getAmendValue());
					apptOrder.getReferenceOrdPipbAppnt().setCutOverStartTime(amendDtl.getAmendValue());
				}
				if(LtsBackendConstant.AMEND_ITEM_OF_DELAY_REA_CD.equals(amendDtl.getAmendItem())){
					apptOrder.setDelayReasonCd(amendDtl.getAmendValue());
				}
				else if(LtsBackendConstant.AMEND_ITEM_OF_APPNT_TYPE.equals(amendDtl.getAmendItem())){
					apptOrder.setPipbAppntType(amendDtl.getAmendValue());
					apptOrder.getReferenceOrdPipbAppnt().setAppntType(amendDtl.getAmendValue());
				}
			}
		}
		
		if(amendRemarkDtlList != null && !amendRemarkDtlList.isEmpty()){
			apptOrder.appendRemarks(createRemarkDetailDTO(amendRemarkDtlList));
		}
		
		categoryList.add(apptOrder);
		
		return newSrd;
	}
	
	private void generateAmendmentForm(AmendLtsDTO pAmend, SbOrderDTO pSbOrder, String user){
		if(pAmend == null || pAmend.getAmendDtls() == null){
			return;
		}
		generateReport(LtsBackendConstant.RPT_SET_AMEND_AF, pAmend, pSbOrder, user);
	}
	
	protected ReportOutputDTO generateReport(String action, AmendLtsDTO pAmend, SbOrderDTO pSbOrder, String user ) {
    	Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(LtsBackendConstant.RPT_KEY_SBORDER, pSbOrder);
		inputMap.put(LtsBackendConstant.RPT_KEY_AMENDMENT, pAmend);
		inputMap.put(LtsBackendConstant.RPT_KEY_LOB, "LTS");
		inputMap.put(LtsBackendConstant.CONCAT_RPT_IND, "N");
		ReportSetDTO rptSet = new ReportSetDTO();
		rptSet.setLob("LTS");
		rptSet.setChannelId(pSbOrder.getOrderId().substring(0,1));
		rptSet.setRptSet(action);
		rptSet = (ReportSetDTO)this.reportSetLkupCacheService.get(rptSet.getLkupKey());
		if (StringUtils.equals(LtsBackendConstant.RPT_SET_AMEND_AF, action)) {
			int seqNum = orderDetailService.getNextDocSeq(pSbOrder.getOrderId(), LtsBackendConstant.ORDER_DOC_TYPE_ORDER_AMENDMENT_FORM);
		
			rptSet.setReGen(false);
			inputMap.put(LtsBackendConstant.FILE_SEQ, seqNum);
		
			ReportOutputDTO rptOutPut = this.reportCreationLtsService.generateReport(rptSet, inputMap);
						
			//int index = rptOutPut.getFileStoragePath().lastIndexOf("\\")+1;
			
			AllOrdDocLtsDTO doc = new AllOrdDocLtsDTO();
			doc.setOrderId(pSbOrder.getOrderId());
			doc.setDocType(LtsBackendConstant.ORDER_DOC_TYPE_ORDER_AMENDMENT_FORM);
			doc.setFilePathName(rptOutPut.getFileStoragePath());//.substring(index));
			doc.setSeqNum(String.valueOf(seqNum));
			allOrdDocLtsService.doSave(doc, user, new Object());
			
			return rptOutPut;
		} 
		return null;
    }
	
	public boolean createAmendmentNoticeReq(SbOrderDTO sbOrder, String requestDate, String templateId, String emailAddr, String smsNo, String method, String username, Long formPrintReqId){
		try {
			int seq = amendLtsDao.getNextSeq(sbOrder.getOrderId(), templateId);
			String formPrintReqIdString = formPrintReqId != null? formPrintReqId.toString(): null; 
			amendLtsDao.insertEmailReq(sbOrder.getOrderId(), templateId, requestDate, emailAddr, smsNo, sbOrder.getEsigEmailLang(), seq, username, method, formPrintReqIdString);
		} catch (DAOException e) {
			logger.error("Fail in AmendLtsService.createAmendmentNoticeReq()", e);
			return false; 
		}
		return true;
	}
	
	private Long createFormPrintReq(SbOrderDTO sbOrder, String username, String method, String reqDate){
		String orderId = sbOrder.getOrderId();
		String printReqType = null;
		String emailContent = null;
		String emailSubject = null;
		String emailFrom = "PCCW Shop";
		String smsContent = null;
//		String username = null;
		String templateId = null;
		String[] templates = null;
		String locale = sbOrder.getEsigEmailLang();
		
		try {

			if(LtsBackendConstant.SEND_METHOD_EMAIL.equals(method)){
				templateId = LtsBackendConstant.EMAIL_TEMPLATE_AMEND_NOTICE_EMAIL;
				templates = amendLtsDao.getEmailTemplate(templateId, locale);
				if(templates == null || templates.length < 3){
					return null;
				}
				emailSubject = templates[1];
				emailContent = templates[2];
				printReqType = "EMAIL";
			}else if(LtsBackendConstant.SEND_METHOD_SMS.equals(method)){
				templateId = LtsBackendConstant.EMAIL_TEMPLATE_AMEND_NOTICE_SMS;
				templates = amendLtsDao.getEmailTemplate(templateId, locale);
				if(templates == null || templates.length < 3){
					return null;
				}
				smsContent = templates[2];
				printReqType = "SMS";
			}
			

			if(StringUtils.isNotBlank(emailContent)){
				ServiceDetailLtsDTO ltsService = LtsSbHelper.getLtsService(sbOrder);
				String title = ltsService.getAccount().getCustomer().getTitle();
				String firstName = ltsService.getAccount().getCustomer().getFirstName();
				String lastName = ltsService.getAccount().getCustomer().getLastName();
				String salesContactNum = sbOrder.getSalesContactNum();
				emailContent = MessageFormat.format(emailContent, new Object[]{title, firstName, lastName, reqDate, salesContactNum});
			}
			if(StringUtils.isNotBlank(smsContent)){
				smsContent = smsContent.replace("{0}", reqDate);
				smsContent = smsContent.replace("{1}", sbOrder.getSalesContactNum());
			}
			
			
			return formPrintReqDao.insertRecord(orderId, printReqType, null, null, null, null, null, null, 
					emailContent, emailSubject, emailFrom, smsContent, username, username);
			
		} catch (DAOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	/**
	 * @return the amendLtsDao
	 */
	public AmendLtsDAO getAmendLtsDao() {
		return amendLtsDao;
	}


	/**
	 * @param amendLtsDao the amendLtsDao to set
	 */
	public void setAmendLtsDao(AmendLtsDAO amendLtsDao) {
		this.amendLtsDao = amendLtsDao;
	}

	/**
	 * @return the imsSbOrderService
	 */
	public ImsSbOrderService getImsSbOrderService() {
		return imsSbOrderService;
	}

	/**
	 * @param imsSbOrderService the imsSbOrderService to set
	 */
	public void setImsSbOrderService(ImsSbOrderService imsSbOrderService) {
		this.imsSbOrderService = imsSbOrderService;
	}

	/**
	 * @return the amendmentSubmitService
	 */
	public AmendmentSubmitService getAmendmentSubmitService() {
		return amendmentSubmitService;
	}

	/**
	 * @param amendmentSubmitService the amendmentSubmitService to set
	 */
	public void setAmendmentSubmitService(
			AmendmentSubmitService amendmentSubmitService) {
		this.amendmentSubmitService = amendmentSubmitService;
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
	 * @return the allOrdDocLtsService
	 */
	public ServiceActionImplBase getAllOrdDocLtsService() {
		return allOrdDocLtsService;
	}

	/**
	 * @param allOrdDocLtsService the allOrdDocLtsService to set
	 */
	public void setAllOrdDocLtsService(ServiceActionImplBase allOrdDocLtsService) {
		this.allOrdDocLtsService = allOrdDocLtsService;
	}

	/**
	 * @return the orderDetailService
	 */
	public OrderDetailService getOrderDetailService() {
		return orderDetailService;
	}

	/**
	 * @param orderDetailService the orderDetailService to set
	 */
	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	/**
	 * @return the orderStatusService
	 */
	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	/**
	 * @param orderStatusService the orderStatusService to set
	 */
	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}

	/**
	 * @return the saveSbOrderLtsService
	 */
	public SaveSbOrderLtsService getSaveSbOrderLtsService() {
		return saveSbOrderLtsService;
	}

	/**
	 * @param saveSbOrderLtsService the saveSbOrderLtsService to set
	 */
	public void setSaveSbOrderLtsService(SaveSbOrderLtsService saveSbOrderLtsService) {
		this.saveSbOrderLtsService = saveSbOrderLtsService;
	}

	/**
	 * @return the pipbActvLtsService
	 */
	public PipbActvLtsService getPipbActvLtsService() {
		return pipbActvLtsService;
	}

	/**
	 * @param pipbActvLtsService the pipbActvLtsService to set
	 */
	public void setPipbActvLtsService(PipbActvLtsService pipbActvLtsService) {
		this.pipbActvLtsService = pipbActvLtsService;
	}

	/**
	 * @return the bomWsBackendClient
	 */
	public BomWsBackendClient getBomWsBackendClient() {
		return bomWsBackendClient;
	}

	/**
	 * @param bomWsBackendClient the bomWsBackendClient to set
	 */
	public void setBomWsBackendClient(BomWsBackendClient bomWsBackendClient) {
		this.bomWsBackendClient = bomWsBackendClient;
	}

	/**
	 * @return the orderRetrieveLtsService
	 */
	public OrderRetrieveLtsService getOrderRetrieveLtsService() {
		return orderRetrieveLtsService;
	}

	/**
	 * @param orderRetrieveLtsService the orderRetrieveLtsService to set
	 */
	public void setOrderRetrieveLtsService(
			OrderRetrieveLtsService orderRetrieveLtsService) {
		this.orderRetrieveLtsService = orderRetrieveLtsService;
	}

	/**
	 * @return the pipbActivityLtsSubmissionService
	 */
	public PipbActivityLtsSubmissionService getPipbActivityLtsSubmissionService() {
		return pipbActivityLtsSubmissionService;
	}

	/**
	 * @param pipbActivityLtsSubmissionService the pipbActivityLtsSubmissionService to set
	 */
	public void setPipbActivityLtsSubmissionService(
			PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService) {
		this.pipbActivityLtsSubmissionService = pipbActivityLtsSubmissionService;
	}

	/**
	 * @return the orderCancelLtsService
	 */
	public OrderCancelLtsService getOrderCancelLtsService() {
		return orderCancelLtsService;
	}

	/**
	 * @param orderCancelLtsService the orderCancelLtsService to set
	 */
	public void setOrderCancelLtsService(OrderCancelLtsService orderCancelLtsService) {
		this.orderCancelLtsService = orderCancelLtsService;
	}

	public OrdEmailReqService getOrdEmailReqService() {
		return ordEmailReqService;
	}

	public void setOrdEmailReqService(OrdEmailReqService ordEmailReqService) {
		this.ordEmailReqService = ordEmailReqService;
	}

	public CodeLkupCacheService getBomReadOnlyLkupCacheService() {
		return bomReadOnlyLkupCacheService;
	}

	public void setBomReadOnlyLkupCacheService(
			CodeLkupCacheService bomReadOnlyLkupCacheService) {
		this.bomReadOnlyLkupCacheService = bomReadOnlyLkupCacheService;
	}

	public FormPrintReqDAO getFormPrintReqDao() {
		return formPrintReqDao;
	}

	public void setFormPrintReqDao(FormPrintReqDAO formPrintReqDao) {
		this.formPrintReqDao = formPrintReqDao;
	}

	public CodeLkupCacheService getAutoAmendModeLkupCacheService() {
		return autoAmendModeLkupCacheService;
	}

	public void setAutoAmendModeLkupCacheService(
			CodeLkupCacheService autoAmendModeLkupCacheService) {
		this.autoAmendModeLkupCacheService = autoAmendModeLkupCacheService;
	}

	public PipbLtsWQServiceDAO getPipbLtsWQServiceDAO() {
		return pipbLtsWQServiceDAO;
	}

	public void setPipbLtsWQServiceDAO(PipbLtsWQServiceDAO pipbLtsWQServiceDAO) {
		this.pipbLtsWQServiceDAO = pipbLtsWQServiceDAO;
	}

}
