package com.bomwebportal.lts.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.context.MessageSource;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.dto.BomSalesUserDTO;
import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.exception.DAOException;
import com.bomwebportal.lts.dto.form.LtsOrderAmendmentFormDTO;
import com.bomwebportal.lts.dto.order.AmendAppointmentLtsDTO;
import com.bomwebportal.lts.dto.order.AmendCategoryLtsDTO;
import com.bomwebportal.lts.dto.order.AmendDetailLtsDTO;
import com.bomwebportal.lts.dto.order.AmendLtsDTO;
import com.bomwebportal.lts.dto.order.AmendPaymentDTO;
import com.bomwebportal.lts.dto.order.AmendPipbInfoDTO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDTO;
import com.bomwebportal.lts.dto.order.OrderLtsAmendDetailDTO;
import com.bomwebportal.lts.dto.order.OrderStatusDTO;
import com.bomwebportal.lts.dto.order.RemarkDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.AmendLtsService;
import com.bomwebportal.lts.service.PipbActvLtsService;
import com.bomwebportal.lts.service.activity.PipbActivityLtsSubmissionService;
import com.bomwebportal.lts.service.order.AmendmentSubmitService;
import com.bomwebportal.lts.service.order.OrderCancelLtsService;
import com.bomwebportal.lts.service.order.OrderDetailService;
import com.bomwebportal.lts.service.order.OrderStatusService;
import com.bomwebportal.lts.service.order.SaveSbOrderLtsService;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.util.LtsConstant;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.lts.util.LtsSbOrderHelper;
import com.bomwebportal.lts.util.LtsSessionHelper;
import com.bomwebportal.lts.wsClientLts.BomWsBackendClient;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.ServiceActionImplBase;

public class OrderAmendSubmitController extends AbstractController {

	private AmendmentSubmitService amendmentSubmitService;
	private OrderCancelLtsService orderCancelLtsService;
	private SaveSbOrderLtsService saveSbOrderLtsService;
	private PipbActvLtsService pipbActvLtsService;
	private BomWsBackendClient bomWsBackendClient;
	private OrderStatusService orderStatusService;
	private ReportCreationLtsService reportCreationLtsService;
	private CodeLkupCacheService reportSetLkupCacheService;
	private ServiceActionImplBase allOrdDocLtsService;
	private OrderDetailService orderDetailService;
	private AmendLtsService amendLtsService;
	private PipbActivityLtsSubmissionService pipbActivityLtsSubmissionService;
	private CodeLkupCacheService wqNatureCodeLkupCacheService;

	private final String parentView ="orderamend.html?orderId=";
	private Locale locale;
	private MessageSource messageSource;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		SbOrderDTO sbOrder = LtsSessionHelper.getDummySbOrder(request);
		LtsOrderAmendmentFormDTO orderAmendDTO = (LtsOrderAmendmentFormDTO) LtsSessionHelper.getOrderAmendForm(request);
		BomSalesUserDTO userInfo = LtsSessionHelper.getBomSalesUser(request);
		setLocale(RequestContextUtils.getLocale(request));
		
		if(orderAmendDTO == null){
			return new ModelAndView(LtsConstant.ERROR_VIEW);
		}

		if(orderStatusValid(orderAmendDTO.getSbOrderNum(), sbOrder)){
			AmendLtsDTO amend = orderAmendDTO.getAmendLtsDTO(); 
			/*Could be null if user selected upload document ONLY*/
			
            saveAmendmentActionDetail(sbOrder, orderAmendDTO, userInfo);
            
			if (isDsApprovalRequired(sbOrder, orderAmendDTO, userInfo)) {
				// send approval WQ here...
				StringBuilder remarkSb = new StringBuilder();
				if(amend != null){
					amendmentSubmitService.generateAmendmentRemark(amend);
					remarkSb.append(amend.remarkToString());
					remarkSb.append("\n");
					for(AmendDetailLtsDTO amendDtl: amend.getAmendDtls()){
						for(AmendCategoryLtsDTO amendCat : amendDtl.getCategories()){
							if(StringUtils.isNotBlank(amendCat.getNature())){
								if(StringUtils.isNumeric(amendCat.getNature())){
									remarkSb.append("<"+wqNatureCodeLkupCacheService.get(amendCat.getNature())+">");
									remarkSb.append("\n");
								}else if(StringUtils.isNotBlank(amendCat.getNatureDescription())){
									remarkSb.append("<"+amendCat.getNatureDescription()+">");
									remarkSb.append("\n");
								}
							}
							remarkSb.append(StringUtils.join(amendCat.remarkToStringArray(), "\n"));
							remarkSb.append("\n\n");
						}
					}
				}
				pipbActivityLtsSubmissionService.createDsAmendment(sbOrder.getOrderId(), userInfo.getUsername(), remarkSb.toString());
			} else {
				amendLtsService.completeAmendment(sbOrder.getOrderId());
			
			}
			
			request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, messageSource.getMessage("lts.ltsOrdAmendSubmit.amendSuc", new Object[] {}, this.locale));
			request.getSession().setAttribute("AmendSuccessInd", true);
		}else{
			request.getSession().setAttribute(LtsConstant.SESSION_LTS_ORDER_INFO_MSG, messageSource.getMessage("lts.ltsOrdAmendSubmit.amendFail", new Object[] {}, this.locale));
			request.getSession().setAttribute("AmendSuccessInd", false);
		}
		
		
		LtsSessionHelper.setOrderAmendForm(request, null);
		request.getSession().setAttribute("sessionAmendDocCapture", null);
		request.getSession().setAttribute("sessionAmendFaxSerialForm", null);
		/*return new ModelAndView(new RedirectView(orderInfoView));*/
		ModelAndView successView = new ModelAndView();
		successView.setView(new RedirectView(parentView.concat(orderAmendDTO.getSbOrderNum())));
		return successView;
	}
	
	private boolean orderStatusValid(String orderId, SbOrderDTO sbOrder){

		OrderStatusDTO[] orderStatus = orderStatusService.getStatus(orderId);
		if(orderStatus != null && orderStatus.length > 0){
			for(OrderStatusDTO status: orderStatus){
				if(LtsConstant.ORDER_STATUS_PENDING_BOM.equals(status.getSbStatus())){
					return true;
				}
				
				for(ServiceDetailDTO srvDtl : sbOrder.getSrvDtls()){
					if(StringUtils.equals(srvDtl.getDtlId(), status.getDtlId() )){
						if(StringUtils.equals(srvDtl.getTypeOfSrv(), LtsConstant.SERVICE_TYPE_TEL)){
							if (!StringUtils.equals(LtsConstant.DRG_ORDER_STATUS_COMPLETED, status.getLegacyStatus())
									&& !StringUtils.equals(LtsConstant.DRG_ORDER_STATUS_NUMBER_INVESTIGATE, status.getLegacyStatus())
									&& !StringUtils.equals(LtsConstant.BOM_ORDER_STATUS_CANCELLED_W_ORDER, status.getBomStatus())
									&& !StringUtils.equals(LtsConstant.BOM_ORDER_STATUS_CANCELLED_WO_ORDER, status.getBomStatus())
									&& !StringUtils.equals(LtsConstant.BOM_ORDER_STATUS_COMPLETED, status.getBomStatus())) {
								return true;
							}
						}else if(StringUtils.equals(srvDtl.getTypeOfSrv(), LtsConstant.SERVICE_TYPE_IMS)){
							if(!StringUtils.equals(status.getBomStatus(), LtsConstant.IMS_ORDER_STATUS_CANCELLED)
									&& !StringUtils.equals(status.getBomStatus(), LtsConstant.IMS_ORDER_STATUS_COMPLETED)){
								return true;
							}
						}
					}
				}
				
//				if(!LtsConstant.ORDER_STATUS_CANCELLED.equals(status.getSbStatus())
//						&& !LtsConstant.ORDER_STATUS_CLOSED.equals(status.getSbStatus())){
//					statusValid = true;
//				}
			}
		}
		
		return false;
	}

	protected void saveAmendmentActionDetail(SbOrderDTO sbOrder, LtsOrderAmendmentFormDTO orderAmendDTO, BomSalesUserDTO userInfo) throws DAOException {		
		AmendLtsDTO amend = orderAmendDTO.getAmendLtsDTO();
		AmendDetailLtsDTO[] amendDtls = amend!=null?amend.getAmendDtls():null;
		OrderLtsAmendDTO orderLtsAmendDTO = null;
		OrderLtsAmendDetailDTO orderLtsAmendDetailDTO = null;
		AmendCategoryLtsDTO[] categories = null;		
		String nextBatchSeq = getNextBatchSeq(sbOrder.getOrderId());
		String userId = userInfo.getUsername();
		
		for (int i=0; amendDtls!=null && i<amendDtls.length; i++) {
			
			// logging to bomweb_order_lts_amend
			orderLtsAmendDTO = new OrderLtsAmendDTO();
			orderLtsAmendDTO.setOrderId(sbOrder.getOrderId());
			orderLtsAmendDTO.setStaffNum(amend.getStaffNum());
			orderLtsAmendDTO.setSalesCd(amend.getSalesCd());
			orderLtsAmendDTO.setSalesChannel(amend.getSalesChannel());
			orderLtsAmendDTO.setShopCd(amend.getShopCd());
			orderLtsAmendDTO.setSalesContactNum(amend.getSalesContactNum());
			orderLtsAmendDTO.setAppName(amend.getName());
			orderLtsAmendDTO.setAppContactNum(amend.getContactNum());
			orderLtsAmendDTO.setAppRelationCd(amend.getRelationshipCd());
			orderLtsAmendDTO.setRequireApproval(isDsApprovalRequired(sbOrder, orderAmendDTO, userInfo)?"Y":"N");		
			orderLtsAmendDTO.setObjectAction(ObjectActionBaseDTO.ACTION_ADD);				
			orderLtsAmendDTO.setEmailAddr(orderAmendDTO.getNoticeEmailAddr());
			orderLtsAmendDTO.setSmsNo(orderAmendDTO.getNoticeSmsNum());
			saveAmendmentAuditLog(orderLtsAmendDTO, sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq, userId);				
			
			// logging to bomweb_order_lts_amend_dtl				
			categories = amendDtls[i].getCategories();					
			for (int j=0; categories!=null && j<categories.length; ++j) {
				orderLtsAmendDetailDTO = new OrderLtsAmendDetailDTO();
				orderLtsAmendDetailDTO.setOrderId(sbOrder.getOrderId());
				orderLtsAmendDetailDTO.setItemSubSeq("0");
				if (categories[j] instanceof AmendCategoryLtsDTO) {
					AmendCategoryLtsDTO amendCategoryLtsDTO = (AmendCategoryLtsDTO)categories[j];
					if (StringUtils.equals(amendCategoryLtsDTO.getNature(), orderAmendDTO.getCancelType())) {
						// C - Order Cancellation
						orderLtsAmendDetailDTO.setAmendCat(LtsConstant.AMEND_CATEGORY_ORDER_CANCELLATION_VALUE);
						for (int itemSeq=0; itemSeq<LtsConstant.AMEND_ITEM_ORDER_CANCEL.length; itemSeq++) {								
							if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_ORDER_CANCEL_TYPE, LtsConstant.AMEND_ITEM_ORDER_CANCEL[itemSeq])) {
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_ORDER_CANCEL_TYPE);
								orderLtsAmendDetailDTO.setAmendValue(orderAmendDTO.getCancelType());
								saveAmendmentDetailAuditLog(orderLtsAmendDetailDTO, 
										sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq, Integer.toString(itemSeq), "0", userId);
							}
							if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_ORDER_CANCEL_REASON, LtsConstant.AMEND_ITEM_ORDER_CANCEL[itemSeq])) {
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_ORDER_CANCEL_REASON);
								orderLtsAmendDetailDTO.setAmendValue(orderAmendDTO.getCancelReason());
								saveAmendmentDetailAuditLog(orderLtsAmendDetailDTO, 
										sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq, Integer.toString(itemSeq), "0", userId);
							}
							if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_ORDER_CANCEL_REMARK, LtsConstant.AMEND_ITEM_ORDER_CANCEL[itemSeq])) {
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_ORDER_CANCEL_REMARK);
								orderLtsAmendDetailDTO.setAmendValue(orderAmendDTO.getCancelRemark());

								saveAmendmentRemarks(amendCategoryLtsDTO, orderLtsAmendDetailDTO, sbOrder.getOrderId(), 
										amendDtls[i].getDtlId(), nextBatchSeq, userId);
							}
							
						}
					} else {
						// F - Free Input						
						orderLtsAmendDetailDTO.setAmendCat(LtsConstant.AMEND_CATEGORY_FREE_INPUT_VALUE);						
						if (StringUtils.equals(amendCategoryLtsDTO.getNature(), orderAmendDTO.getCategory1())){
							//get amend item by nature from constant map, will input nature if nothing is returned.
							String amendItem = LtsConstant.AMEND_ITEM_OF_FREE_INPUT_MAP.get(orderAmendDTO.getCategory1());
							orderLtsAmendDetailDTO.setAmendItem(StringUtils.isEmpty(amendItem)?orderAmendDTO.getCategory1():amendItem);
							orderLtsAmendDetailDTO.setAmendValue(orderAmendDTO.getContent1());
							saveAmendmentRemarks(amendCategoryLtsDTO, orderLtsAmendDetailDTO, sbOrder.getOrderId(), 
									amendDtls[i].getDtlId(), nextBatchSeq, userId);
						}
						if (StringUtils.equals(amendCategoryLtsDTO.getNature(), orderAmendDTO.getCategory2())){								
							String amendItem = LtsConstant.AMEND_ITEM_OF_FREE_INPUT_MAP.get(orderAmendDTO.getCategory2());
							orderLtsAmendDetailDTO.setAmendItem(StringUtils.isEmpty(amendItem)?orderAmendDTO.getCategory2():amendItem);
							orderLtsAmendDetailDTO.setAmendValue(orderAmendDTO.getContent2());
							saveAmendmentRemarks(amendCategoryLtsDTO, orderLtsAmendDetailDTO, sbOrder.getOrderId(), 
									amendDtls[i].getDtlId(), nextBatchSeq, userId);
						}
						if (StringUtils.equals(amendCategoryLtsDTO.getNature(), orderAmendDTO.getCategory3())){								
							String amendItem = LtsConstant.AMEND_ITEM_OF_FREE_INPUT_MAP.get(orderAmendDTO.getCategory3());
							orderLtsAmendDetailDTO.setAmendItem(StringUtils.isEmpty(amendItem)?orderAmendDTO.getCategory3():amendItem);
							orderLtsAmendDetailDTO.setAmendValue(orderAmendDTO.getContent3());
							saveAmendmentRemarks(amendCategoryLtsDTO, orderLtsAmendDetailDTO, sbOrder.getOrderId(), 
									amendDtls[i].getDtlId(), nextBatchSeq, userId);
							
						}						
					}
				}
				if (categories[j] instanceof AmendAppointmentLtsDTO) {
					// A - Appointment
					AmendAppointmentLtsDTO amendAppnt = (AmendAppointmentLtsDTO)categories[j];
					
					for (int itemSeq=0; itemSeq<LtsConstant.AMEND_ITEM_APPOINTMNET.length; itemSeq++) {
						orderLtsAmendDetailDTO.setAmendItem(null);
						if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_APPOINTMENT_START_TIME, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_APPOINTMENT_START_TIME);
							orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getAppntStartTime());
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_APPOINTMENT_END_TIME, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_APPOINTMENT_END_TIME);
							orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getAppntEndTime());
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_PREWIRING_START_TIME, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							if(StringUtils.isNotBlank(amendAppnt.getPreWiringStartTime())){
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_PREWIRING_START_TIME);
								orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getPreWiringStartTime());
							}
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_PREWIRING_END_TIME, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							if(StringUtils.isNotBlank(amendAppnt.getPreWiringStartTime())){
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_PREWIRING_END_TIME);
								orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getPreWiringEndTime());
							}
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_PREWIRING_TYPE, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							if(StringUtils.isNotBlank(amendAppnt.getPreWiringType())){
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_PREWIRING_TYPE);
								orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getPreWiringType());
							}
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CUT_OVER_START_TIME, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CUT_OVER_START_TIME);
							orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getCutOverStartTime());
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CUT_OVER_END_TIME, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CUT_OVER_END_TIME);
							orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getCutOverEndTime());
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_DELAY_REA_CD, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_DELAY_REA_CD);
							orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getDelayReasonCd());
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_PRE_BOOK_SERIAL, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_PRE_BOOK_SERIAL);
							orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getSerialNum());
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_APPNT_TYPE, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_APPNT_TYPE);
							orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getAppntType());
						}else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_APPNT_REMARK, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_APPNT_REMARK);
							//special handling for remarks
							saveAmendmentRemarks(amendAppnt, orderLtsAmendDetailDTO, sbOrder.getOrderId(), 
									amendDtls[i].getDtlId(), nextBatchSeq, userId);
							continue; 
						}
						if(orderLtsAmendDetailDTO.getAmendItem() == null){
							continue;
						}
						orderLtsAmendDetailDTO.setAmendCat(LtsConstant.AMEND_CATEGORY_APPOINTMENT_VALUE);
						saveAmendmentDetailAuditLog(orderLtsAmendDetailDTO, 
								sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq, 
								getNextItemSeq(sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq), "0", userId);		
					}
					/*PortLater Service Appointment*/
					ServiceDetailLtsDTO portLaterSrv = LtsSbOrderHelper.getAcqPortLaterService(sbOrder);
					if(portLaterSrv != null && StringUtils.isNotBlank(amendAppnt.getPipbAppntStartTime())){
						for (int itemSeq=0; itemSeq<LtsConstant.AMEND_ITEM_APPOINTMNET.length; itemSeq++) {
							orderLtsAmendDetailDTO.setAmendItem(null);
							if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_APPOINTMENT_START_TIME, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_APPOINTMENT_START_TIME);
								orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getPipbAppntStartTime());
							}
							else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_APPOINTMENT_END_TIME, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_APPOINTMENT_END_TIME);
								orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getPipbAppntEndTime());
							}
							else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CUT_OVER_START_TIME, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CUT_OVER_START_TIME);
								orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getPipbCutOverStartTime());
							}
							else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CUT_OVER_END_TIME, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CUT_OVER_END_TIME);
								orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getPipbCutOverEndTime());
							}
							else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_DELAY_REA_CD, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
								continue;
							}
							else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_PRE_BOOK_SERIAL, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
								continue;
							}
							else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_APPNT_TYPE, LtsConstant.AMEND_ITEM_APPOINTMNET[itemSeq])) {
								orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_APPNT_TYPE);
								orderLtsAmendDetailDTO.setAmendValue(amendAppnt.getPipbAppntType());
							}
							if(orderLtsAmendDetailDTO.getAmendItem() == null){
								continue;
							}
							orderLtsAmendDetailDTO.setAmendCat(LtsConstant.AMEND_CATEGORY_APPOINTMENT_VALUE);
							saveAmendmentDetailAuditLog(orderLtsAmendDetailDTO, 
									sbOrder.getOrderId(), portLaterSrv.getDtlId(), nextBatchSeq, 
									getNextItemSeq(sbOrder.getOrderId(), portLaterSrv.getDtlId(), nextBatchSeq), "0", userId);		
						}
					}
				}
				if (categories[j] instanceof AmendPipbInfoDTO) {
					// P -PIPB info
					AmendPipbInfoDTO amendPipb = (AmendPipbInfoDTO)categories[j];
					for (int itemSeq=0; itemSeq<LtsConstant.AMEND_ITEM_PIPB_INFO.length; itemSeq++) {
						orderLtsAmendDetailDTO.setAmendItem(null);
						if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_TITLE, LtsConstant.AMEND_ITEM_PIPB_INFO[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_TITLE);
						    orderLtsAmendDetailDTO.setAmendValue(amendPipb.getTitle());
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_FIRST_NAME, LtsConstant.AMEND_ITEM_PIPB_INFO[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_FIRST_NAME);
						    orderLtsAmendDetailDTO.setAmendValue(amendPipb.getFirstName());
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_LAST_NAME, LtsConstant.AMEND_ITEM_PIPB_INFO[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_LAST_NAME);
						    orderLtsAmendDetailDTO.setAmendValue(amendPipb.getLastName());
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_FLAT, LtsConstant.AMEND_ITEM_PIPB_INFO[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_FLAT);
						    orderLtsAmendDetailDTO.setAmendValue(amendPipb.getFlat());
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_FLOOR, LtsConstant.AMEND_ITEM_PIPB_INFO[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_FLOOR);
						    orderLtsAmendDetailDTO.setAmendValue(amendPipb.getFloor());
						}else{
							continue;
						}
					    orderLtsAmendDetailDTO.setAmendCat(LtsConstant.AMEND_CATEGORY_PIPB_INFO_VALUE);
					    
					    /*If value is blank = did not amend*/
						if(orderLtsAmendDetailDTO.getAmendItem() == null){
							continue;
						}
					    if(StringUtils.isNotBlank(orderLtsAmendDetailDTO.getAmendValue())){
							saveAmendmentDetailAuditLog(orderLtsAmendDetailDTO, 
									sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq, 
									getNextItemSeq(sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq), "0", userId);
					    }
					}

					// D - Document
					if(orderAmendDTO.isAmendDocInd()){
						orderLtsAmendDetailDTO.setAmendCat(LtsConstant.AMEND_CATEGORY_DOCUMENT_VALUE);
						orderLtsAmendDetailDTO.setAmendItem(null);
					    orderLtsAmendDetailDTO.setAmendValue(messageSource.getMessage("lts.ltsOrdAmendSubmit.updDocSel", new Object[] {}, this.locale));
						saveAmendmentDetailAuditLog(orderLtsAmendDetailDTO, 
								sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq, 
								getNextItemSeq(sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq), "0", userId);
					}
					
				}
				if (categories[j] instanceof AmendPaymentDTO) {
					// R - Credit Card
					AmendPaymentDTO amendPayment = (AmendPaymentDTO)categories[j];
					for (int itemSeq=0; itemSeq<LtsConstant.AMEND_ITEM_CREDIT_CARD.length; itemSeq++) {
						orderLtsAmendDetailDTO.setAmendItem(null);
						if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_THIRTY_PARTY_INDICATOR, LtsConstant.AMEND_ITEM_CREDIT_CARD[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_THIRTY_PARTY_INDICATOR);
						    orderLtsAmendDetailDTO.setAmendValue(amendPayment.getCcThirdPartyInd());							    
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_FAX_SERIAL, LtsConstant.AMEND_ITEM_CREDIT_CARD[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_FAX_SERIAL);
						    orderLtsAmendDetailDTO.setAmendValue(amendPayment.getFaxSerialNum());							    
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_HOLDER_NAME, LtsConstant.AMEND_ITEM_CREDIT_CARD[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_HOLDER_NAME);
						    orderLtsAmendDetailDTO.setAmendValue(amendPayment.getCcHoldName());							    
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_NUMBER, LtsConstant.AMEND_ITEM_CREDIT_CARD[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_NUMBER);
						    orderLtsAmendDetailDTO.setAmendValue(amendPayment.getCcNum());							    
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_TYPE, LtsConstant.AMEND_ITEM_CREDIT_CARD[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_TYPE);
						    orderLtsAmendDetailDTO.setAmendValue(amendPayment.getCcType());							    
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_EXPIRY_DATE, LtsConstant.AMEND_ITEM_CREDIT_CARD[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_EXPIRY_DATE);
						    orderLtsAmendDetailDTO.setAmendValue(amendPayment.getCcExpDate());							    
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_HOLDER_DOC_TYPE, LtsConstant.AMEND_ITEM_CREDIT_CARD[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_HOLDER_DOC_TYPE);
						    orderLtsAmendDetailDTO.setAmendValue(amendPayment.getCcIdDocType());							    
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_HOLDER_DOC_NUM, LtsConstant.AMEND_ITEM_CREDIT_CARD[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_HOLDER_DOC_NUM);
						    orderLtsAmendDetailDTO.setAmendValue(amendPayment.getCcIdDocNo());							    
						}
						else if (StringUtils.equals(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_REMARK, LtsConstant.AMEND_ITEM_CREDIT_CARD[itemSeq])) {
							orderLtsAmendDetailDTO.setAmendItem(LtsConstant.AMEND_ITEM_OF_CREDIT_CARD_REMARK);
							//special handling for remarks
							saveAmendmentRemarks(amendPayment, orderLtsAmendDetailDTO, sbOrder.getOrderId(), 
									amendDtls[i].getDtlId(), nextBatchSeq, userId);
							continue;
						}

						if(orderLtsAmendDetailDTO.getAmendItem() == null){
							continue;
						}
						
						orderLtsAmendDetailDTO.setAmendCat(LtsConstant.AMEND_CATEGORY_CREDIT_CARD_VALUE);
						saveAmendmentDetailAuditLog(orderLtsAmendDetailDTO, 
								sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq, 
								getNextItemSeq(sbOrder.getOrderId(), amendDtls[i].getDtlId(), nextBatchSeq), "0", userId);
					}
				}			
			}
		}		
	}
	
	private void saveAmendmentRemarks(AmendCategoryLtsDTO amendCategoryLtsDTO, OrderLtsAmendDetailDTO orderLtsAmendDetailDTO, 
			String orderId, String dtlId, String nextBatchSeq, String userId) throws DAOException{
		
		if(amendCategoryLtsDTO.getRemarks() == null){
			return;
		}
		
		String itemSeq = getNextItemSeq(orderId, dtlId, nextBatchSeq);
		for(RemarkDetailLtsDTO rmk :amendCategoryLtsDTO.getRemarks()){
			String dtl = rmk.getRmkDtl();
			String itemSubSeq = rmk.getRmkSeq();
			orderLtsAmendDetailDTO.setAmendValue(dtl);
			saveAmendmentDetailAuditLog(orderLtsAmendDetailDTO, 
					orderId, dtlId, nextBatchSeq, 
					itemSeq, itemSubSeq, userId);
		}
	}
	
	private boolean isDsApprovalRequired(SbOrderDTO sbOrder, LtsOrderAmendmentFormDTO amend, BomSalesUserDTO userInfo){
		if(isOrderDS(sbOrder)){
			/*No need to approve for cancel case*/
			if(amend.isCancelOrderInd()){
				return false;
			}
			/*No need to approve for amend appointment only case*/
			if(amend.isAmendAppointmentInd()
					&& !amend.isAmendAcqPipbInd()
					&& !amend.isAmendCreditCardInd()
					&& !amend.isAmendDocInd()
					&& !amend.isCategory1Ind()
					&& !amend.isCategory2Ind()
					&& !amend.isCategory3Ind()){
				return false;
			}
			/*No need to approve for DS Support team*/
			if(LtsSessionHelper.isDirectSalesSupport(userInfo.getChannelId(), userInfo.getChannelCd())){
				return false;
			}
			return true;
		}
		return false;
	}
	
	protected String getNextBatchSeq(String orderId) throws DAOException {
		return amendLtsService.getNextBatchSeq(orderId);
	}
	
	protected String getNextItemSeq(String orderId, String dtlId, String batchSeq) throws DAOException {
		return amendLtsService.getNextItemSeq(orderId, dtlId, batchSeq);
	}
	
	protected boolean isOrderDS(SbOrderDTO sbOrder) {
    	return LtsConstant.ORDER_MODE_DIRECT_SALES.equals(sbOrder.getOrderMode())    	
    	|| LtsConstant.ORDER_MODE_DIRECT_SALES_NOW_TV_QA.equals(sbOrder.getOrderMode());
    }
	
	protected void saveAmendmentAuditLog(OrderLtsAmendDTO obj, String orderId, 
			String dtlId, String batchSeq, String userId) {
		amendLtsService.saveAmendmentAuditLog(obj, orderId, dtlId, batchSeq, userId);
	}
	
	protected void saveAmendmentDetailAuditLog(OrderLtsAmendDetailDTO obj, String orderId, 
			String dtlId, String batchSeq, String itemSeq, String itemSubSeq, String userId) {
		amendLtsService.saveAmendmentDetailAuditLog(obj, orderId, dtlId, batchSeq, itemSeq, itemSubSeq, userId);
	}
	
	public AmendmentSubmitService getAmendmentSubmitService() {
		return amendmentSubmitService;
	}

	public void setAmendmentSubmitService(
			AmendmentSubmitService amendmentSubmitService) {
		this.amendmentSubmitService = amendmentSubmitService;
	}

	public OrderCancelLtsService getOrderCancelLtsService() {
		return orderCancelLtsService;
	}

	public void setOrderCancelLtsService(OrderCancelLtsService orderCancelLtsService) {
		this.orderCancelLtsService = orderCancelLtsService;
	}

	public SaveSbOrderLtsService getSaveSbOrderLtsService() {
		return saveSbOrderLtsService;
	}

	public void setSaveSbOrderLtsService(SaveSbOrderLtsService saveSbOrderLtsService) {
		this.saveSbOrderLtsService = saveSbOrderLtsService;
	}

	public PipbActvLtsService getPipbActvLtsService() {
		return pipbActvLtsService;
	}

	public void setPipbActvLtsService(PipbActvLtsService pipbActvLtsService) {
		this.pipbActvLtsService = pipbActvLtsService;
	}

	public BomWsBackendClient getBomWsBackendClient() {
		return bomWsBackendClient;
	}

	public void setBomWsBackendClient(BomWsBackendClient bomWsBackendClient) {
		this.bomWsBackendClient = bomWsBackendClient;
	}

	public OrderStatusService getOrderStatusService() {
		return orderStatusService;
	}

	public void setOrderStatusService(OrderStatusService orderStatusService) {
		this.orderStatusService = orderStatusService;
	}
	public ReportCreationLtsService getReportCreationLtsService() {
		return reportCreationLtsService;
	}

	public void setReportCreationLtsService(
			ReportCreationLtsService reportCreationLtsService) {
		this.reportCreationLtsService = reportCreationLtsService;
	}

	public CodeLkupCacheService getReportSetLkupCacheService() {
		return reportSetLkupCacheService;
	}

	public void setReportSetLkupCacheService(
			CodeLkupCacheService reportSetLkupCacheService) {
		this.reportSetLkupCacheService = reportSetLkupCacheService;
	}

	public ServiceActionImplBase getAllOrdDocLtsService() {
		return allOrdDocLtsService;
	}

	public void setAllOrdDocLtsService(ServiceActionImplBase allOrdDocLtsService) {
		this.allOrdDocLtsService = allOrdDocLtsService;
	}

	public OrderDetailService getOrderDetailService() {
		return orderDetailService;
	}

	public void setOrderDetailService(OrderDetailService orderDetailService) {
		this.orderDetailService = orderDetailService;
	}

	/**
	 * @return the amendLtsService
	 */
	public AmendLtsService getAmendLtsService() {
		return amendLtsService;
	}

	/**
	 * @param amendLtsService the amendLtsService to set
	 */
	public void setAmendLtsService(AmendLtsService amendLtsService) {
		this.amendLtsService = amendLtsService;
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

	public CodeLkupCacheService getWqNatureCodeLkupCacheService() {
		return wqNatureCodeLkupCacheService;
	}

	public void setWqNatureCodeLkupCacheService(
			CodeLkupCacheService wqNatureCodeLkupCacheService) {
		this.wqNatureCodeLkupCacheService = wqNatureCodeLkupCacheService;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

}
