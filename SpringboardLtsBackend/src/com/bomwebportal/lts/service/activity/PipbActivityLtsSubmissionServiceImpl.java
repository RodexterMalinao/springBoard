package com.bomwebportal.lts.service.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.activity.dto.ActivityTaskDTO;
import com.activity.service.UploadActvDocumentService;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.report.ReportOutputDTO;
import com.bomwebportal.dto.report.ReportSetDTO;
import com.bomwebportal.lts.dao.order.ItemAttributeLtsDAO;
import com.bomwebportal.lts.dto.ItemAttbDTO;
import com.bomwebportal.lts.dto.activity.LtsActivityDTO;
import com.bomwebportal.lts.dto.activity.LtsActivityTaskDTO;
import com.bomwebportal.lts.dto.order.AllOrdDocDTO;
import com.bomwebportal.lts.dto.order.ItemAttributeDetailLtsDTO;
import com.bomwebportal.lts.dto.order.ItemDetailLtsDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailDTO;
import com.bomwebportal.lts.dto.order.ServiceDetailLtsDTO;
import com.bomwebportal.lts.service.LtsOrderDocumentService;
import com.bomwebportal.lts.service.OfferService;
import com.bomwebportal.lts.service.order.OrderRetrieveLtsService;
import com.bomwebportal.lts.service.report.ReportCreationLtsService;
import com.bomwebportal.lts.util.LtsActvBackendConstant;
import com.bomwebportal.lts.util.LtsActvBackendConstant.ActvAction;
import com.bomwebportal.lts.util.LtsBackendConstant;
import com.bomwebportal.lts.util.LtsDateFormatHelper;
import com.bomwebportal.lts.util.LtsSbHelper;
import com.bomwebportal.service.CodeLkupCacheService;
import com.bomwebportal.service.ServiceActionBase;
import com.bomwebportal.util.DateFormatHelper;
import com.pccw.util.spring.SpringApplicationContext;
import com.pccw.wq.schema.dto.WorkQueueDTO;
import com.pccw.wq.service.WorkQueueService;


public class PipbActivityLtsSubmissionServiceImpl extends LtsActivitySubmissionServiceImpl implements PipbActivityLtsSubmissionService {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	private LtsActivityDTO ltsActivityDTO;
	private LtsActivityTaskDTO ltsActivityTaskDTO;
	private ActivityTaskDTO reqTask;
	private ActivityTaskDTO relatedTask;
	private RetrieveLtsActivityService retrieveLtsActivityService;
	private ReportCreationLtsService reportCreationLtsService;
	private CodeLkupCacheService reportSetLkupCacheService;
	private OrderRetrieveLtsService orderRetrieveLtsService;
	private UploadActvDocumentService uploadActvDocumentService;
	private LtsOrderDocumentService ltsOrderDocumentService;
	private WorkQueueService workQueueService;
	protected OfferService offerService = null;
	private String sbOrderId;
	private SbOrderDTO sbOrderDTO;
	private String user;
	private String otherRemark;
	
	private void submitPipbActivity(ActvAction actvAction) throws Exception {
		switch (actvAction) {
		case CREATE_DN:		
		case RESTORE_DEFERRED_SRD:
		case DN_FOLLOWUP:	
			createActivity(LtsActvBackendConstant.ACTV_TYPE_PIPB_PROC);
			saveActivity(actvAction);
		break;
		
		case CHANGE_DN:
		case CHANGE_SRD:
		case CHANGE_SRD_BY_PIPB_REJ:	
			createActivity(LtsActvBackendConstant.ACTV_TYPE_PIPB_PROC);
			saveActivity(actvAction, generateAllPipbRequiredReports());
		break;
		
		case PIPB_REQUEST:
			logger.info("submitPipbActivity - case PIPB_REQUEST - createActivity order id: " + this.sbOrderId + " actvType: " + LtsActvBackendConstant.ACTV_TYPE_PIPB_PROC);
			createActivity(LtsActvBackendConstant.ACTV_TYPE_PIPB_PROC);
			logger.info("submitPipbActivity - case PIPB_REQUEST - saveActivity order id: " + this.sbOrderId + " actvAction: " + actvAction.toString());
			saveActivity(actvAction);
			logger.info("submitPipbActivity - case PIPB_REQUEST - createActivity order id: " + this.sbOrderId + " actvType: " + LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ);
			createActivity(LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ);
			logger.info("submitPipbActivity - case PIPB_REQUEST - saveActivity order id: " + this.sbOrderId + " actvAction: " + actvAction.toString());
			saveActivity(actvAction, generateAllPipbRequiredReports());
			logger.info("submitPipbActivity - case PIPB_REQUEST - saveActivity completed order id: " + this.sbOrderId);
		break;

		case PIPB_CANCEL:
			createActivity(LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ);
			saveActivity(actvAction, generateAllPipbRequiredReports());
		break;
		
		case PIPB_CANCEL_WITH_ACKNOWLEDGE:	
		case PIPB_REJECT:
		case PIPB_REJECT_WITH_DEFER_SRD:
		case PIPB_REJECT_WITH_INVALID_APPT:
		case UPDATE_APN:
		case PIPB_CANCEL_SALES_ALERT:
			createActivity(LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ);
			saveActivity(actvAction);
		break;
		case WAIVE_QC:
			createActivityForDs(LtsActvBackendConstant.ACTV_TYPE_WAIVE_QC);
			saveActivity(actvAction);
		break;
		case DS_AMEND_APPROVAL:
			createActivityForDs(LtsActvBackendConstant.ACTV_TYPE_DS_AMEND_APPROVAL);
			saveActivity(actvAction);
		break;
		case CUST_NAME_NOT_MATCH:
			createActivityForDs(LtsActvBackendConstant.ACTV_TYPE_DS_CUST_NAME_APPROVAL);
			saveActivity(actvAction, generateAllRequiredReports(LtsBackendConstant.RPT_TYPE_SUPPORT_DOC));
		break;
		default:
		break;
		}
	}
	
	private void submitAmendActivity(ActvAction actvAction) throws Exception {
		switch (actvAction) {
		case AMEND_APPROVAL:
			createActivity(LtsActvBackendConstant.ACTV_TYPE_AMEND_APPROVAL);
			saveActivity(actvAction);
		break;
		
		default:
			break;
		}
	}	
	
	private void saveActivity(ActvAction actvAction) throws Exception {
		saveActivity(actvAction, null);
	}
	
	private void saveActivity(ActvAction actvAction, OrdDocDTO[] pDocList) throws Exception {
		logger.info("saveActivity - submitActivity order id: " + this.sbOrderId + " actv Id: " + this.ltsActivityDTO.getActvId() + " actvAction: " + actvAction.toString());
		this.submitActivity(this.ltsActivityDTO, actvAction.toString(), pDocList, this.user, new StringBuffer());
	}
	
	public void submitPipbActivity(SbOrderDTO sbOrderDTO, ServiceDetailDTO srvDtls, String pUser, ActvAction actvAction) throws Exception {
		initialize(sbOrderDTO, sbOrderDTO.getOrderId(), pUser, null);
		logger.info("submitPipbActivity - submitPipbActivity begin. order id: " + sbOrderDTO.getOrderId());
		submitPipbActivity(actvAction);
	}
		
	public void createPipbRequest(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		if (isValidPipbDayAndAppointment(this.sbOrderDTO.getSrvDtls(), ActvAction.PIPB_REQUEST)) {
			submitPipbActivity(ActvAction.PIPB_REQUEST);
		}
	}
	
	public void createCancelSalesAlert(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		submitPipbActivity(ActvAction.PIPB_CANCEL_SALES_ALERT);
	}
	
	public void createPipbReject(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		if (isValidPipbDayAndAppointment(this.sbOrderDTO.getSrvDtls(), ActvAction.PIPB_REJECT)) {
			submitPipbActivity(ActvAction.PIPB_REJECT);
		}
	}
		
	public void createPipbCancel(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		if (isTaskExistByType(sbOrderId, pUser, LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST)) {
			submitPipbActivity(ActvAction.PIPB_CANCEL);
		}
	}
	
	public void createPipbCancelWithAcknowledge(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		if (isTaskExistByType(sbOrderId, pUser, LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST)) {
			submitPipbActivity(ActvAction.PIPB_CANCEL_WITH_ACKNOWLEDGE);
		}
	}
		
	public void createAPNUpdate(String sbOrderId, String pUser, String statusCd) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		if (StringUtils.equals(statusCd, LtsActvBackendConstant.ACTV_TASK_STATUS_SUCCESS)
				|| StringUtils.equals(statusCd, LtsActvBackendConstant.ACTV_TASK_STATUS_FAIL)) {
			submitPipbActivity(ActvAction.UPDATE_APN);
			updateTaskStatus(statusCd);
		}
		if (StringUtils.equals(statusCd, LtsActvBackendConstant.ACTV_TASK_STATUS_CANCELLED)) {
			this.ltsActivityDTO = retrievePendingActivity(this.sbOrderId, this.user, LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ);
			if (this.ltsActivityDTO!=null) {				
				ActivityTaskDTO[] actvTasks = getTasksByTypeFilterOutStatus(this.ltsActivityDTO, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
				if (actvTasks!=null) {
					this.actv = this.ltsActivityDTO;
					this.reqTask = this.ltsActivityDTO.getTaskBySeq(getMaxTaskSeqFromTaskList(actvTasks));
					updateTaskStatus(statusCd);
					updateActvStatus(statusCd);
				}
			}
		}		
	}
	
	public void createDsAmendment(String sbOrderId, String pUser, String remarks) throws Exception {
		initialize(null, sbOrderId, pUser, remarks);
		submitPipbActivity(ActvAction.DS_AMEND_APPROVAL);
	}
	
	public void createDsWaiveQc(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		submitPipbActivity(ActvAction.WAIVE_QC);
	}
		
	public void createPipbAmendment(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		submitPipbActivity(ActvAction.CHANGE_SRD);
	}

	public void restoreDeferredSRD(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		submitPipbActivity(ActvAction.RESTORE_DEFERRED_SRD);
	}
	
	public void createDnFollowup(String sbOrderId, String pUser, String remark, ActvAction actvAction) throws Exception {
		initialize(null, sbOrderId, pUser, remark);
		submitPipbActivity(actvAction);
	}
	
	public void createPipbRejectWithDeferSRD(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		submitPipbActivity(ActvAction.PIPB_REJECT_WITH_DEFER_SRD);
	}
	
	public void createPipbRejectWithInvalidAppointment(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		submitPipbActivity(ActvAction.PIPB_REJECT_WITH_INVALID_APPT);
	}
	
	public void createAmendmentApproval(String sbOrderId, String pUser) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		submitAmendActivity(ActvAction.AMEND_APPROVAL);
	}
	
	public void createCustNameNotMatchApproval(String sbOrderId, String pUser, String remarks) throws Exception {
		initialize(null, sbOrderId, pUser, null);
		if(isTaskExistByType(sbOrderId, pUser, LtsActvBackendConstant.ACTV_TYPE_DS_CUST_NAME_APPROVAL, LtsActvBackendConstant.TASK_TYPE_DS_CUST_NAME_APPROVAL)){
			createActivityForDs(LtsActvBackendConstant.ACTV_TYPE_DS_CUST_NAME_APPROVAL);
			this.actv = this.ltsActivityDTO;
			cancelActivity("New document uploaded.");
		}
		submitPipbActivity(ActvAction.CUST_NAME_NOT_MATCH);
	}
	
	public boolean isTaskExistByType(String sbOrderId, String pUser, String actvType, String taskType) throws Exception {
		LtsActivityDTO activityDTO = retrievePendingActivity(sbOrderId, pUser, actvType);
		if (activityDTO!=null) {
			ActivityTaskDTO[] activityTaskDTO = retrieveTaskByType(activityDTO, taskType, false);
			if (activityTaskDTO!=null) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isStatusRejectByType(String sbOrderId, String pUser, String actvType, String taskType) throws Exception {
		LtsActivityDTO activityDTO = retrievePendingActivity(sbOrderId, pUser, actvType);
		if (activityDTO!=null) {
			ActivityTaskDTO[] actvTasks = getTasksByTypeFilterOutStatus(activityDTO, taskType);			
			if (actvTasks!=null) {
				ActivityTaskDTO activityTaskDTO = activityDTO.getTaskBySeq(getMaxTaskSeqFromTaskList(actvTasks));
				if (StringUtils.equals(LtsActvBackendConstant.ACTV_TASK_STATUS_REJECTED, activityTaskDTO.getTaskStatus().getStatus())) {
					return true;
				}
			}
		}
		return false;
	}	
	
	public void updateWorkQueueWithSRD(SbOrderDTO sbOrderDTO, String pUser) throws Exception {
		String[] actvTypes = {LtsActvBackendConstant.ACTV_TYPE_PIPB_PROC, LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ};
		for (int i=0; i<actvTypes.length; i++) {
			updateWorkQueueWithSRDByType(sbOrderDTO, pUser, actvTypes[i]);
		}
	}
	
	public void updateWorkQueueWithSRDByType(SbOrderDTO sbOrderDTO, String pUser, String actvType) throws Exception {		
		LtsActivityDTO activityDTO = retrievePendingActivity(sbOrderDTO.getOrderId(), pUser, actvType);
		if (activityDTO!=null) {
			WorkQueueDTO pWorkQueue = new WorkQueueDTO();
			pWorkQueue.setSbActvId(activityDTO.getActvId());
			DateFormat dateFormat = new SimpleDateFormat(LtsActvBackendConstant.ACTV_SRD_DATE_FORMAT);
			ServiceDetailLtsDTO pipbServiceLts = LtsSbHelper.getAcqPipbService(sbOrderDTO.getSrvDtls());
			String srdStr = StringUtils.equals(actvType, LtsActvBackendConstant.ACTV_TYPE_PIPB_REQ)?
					pipbServiceLts.getAppointmentDtl().getCutOverStartTime():
						pipbServiceLts.getAppointmentDtl().getAppntStartTime();
			Calendar srdDate = DateFormatHelper.getCalenderDateFromDTOFormat(srdStr);			
			pWorkQueue.setSrd(dateFormat.format(srdDate.getTime()));
			updateWorkQueue(pWorkQueue, pUser);
		}		
	}
	
	@Override
	protected boolean determineTasks() {
		
		// set PIPB task
		if (StringUtils.equals(this.action, ActvAction.CREATE_DN.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_PIPB_DN_CREATE);
		}
		
		if (StringUtils.equals(this.action, ActvAction.DN_FOLLOWUP.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_PIPB_DN_CREATE);
		}
		
		if (StringUtils.equals(this.action, ActvAction.CHANGE_DN.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_PIPB_DN_CHANGE);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_REQUEST.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
		}		
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_REJECT.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REJECT);
			setRelatedTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
		}
				
		if (StringUtils.equals(this.action, ActvAction.PIPB_REJECT_WITH_DEFER_SRD.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REJECT);
			setRelatedTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_REJECT_WITH_INVALID_APPT.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REJECT);
			setRelatedTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_CANCEL.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_PIPB_CANCEL);
			setRelatedTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_CANCEL_WITH_ACKNOWLEDGE.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_PIPB_CANCEL);
			setRelatedTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_CANCEL_SALES_ALERT.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_PIPB_CANCEL_SALES_ALERT);
			setRelatedTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
		}
		
		if (StringUtils.equals(this.action, ActvAction.UPDATE_APN.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_APN_UPDATE);
			setRelatedTask(LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST);
		}	
		
		if (StringUtils.equals(this.action, ActvAction.CHANGE_SRD.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_SRD_CHANGE);
		}
		
		if (StringUtils.equals(this.action, ActvAction.CHANGE_SRD_BY_PIPB_REJ.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_SRD_CHANGE);
		}
						
		if (StringUtils.equals(this.action, ActvAction.RESTORE_DEFERRED_SRD.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_SRD_CHANGE);
		}
		
		if (StringUtils.equals(this.action, ActvAction.AMEND_APPROVAL.toString())) {
			this.reqTask = this.createPipbTask(LtsActvBackendConstant.TASK_TYPE_AMEND_APPROVAL);
		}
		
		if (StringUtils.equals(this.action, ActvAction.WAIVE_QC.toString())) {
			this.reqTask = this.createDsTask(LtsActvBackendConstant.TASK_TYPE_WAIVE_QC);
		}
		
		if (StringUtils.equals(this.action, ActvAction.DS_AMEND_APPROVAL.toString())) {
			this.reqTask = this.createDsTask(LtsActvBackendConstant.TASK_TYPE_DS_AMEND_APPROVAL);
		}
		
		if (StringUtils.equals(this.action, ActvAction.CUST_NAME_NOT_MATCH.toString())) {
			this.reqTask = this.createDsTask(LtsActvBackendConstant.TASK_TYPE_DS_CUST_NAME_APPROVAL);
		}
		
		this.actv.addTask(this.reqTask);		
		return true;
	}
	
	@Override
	protected boolean preSubmit() throws Exception {
		return true;
	}
	
	@Override
	protected boolean postSubmit() throws Exception {
		
		if (StringUtils.equals(this.action, ActvAction.CREATE_DN.toString())){
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_CREATE_DN);
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING);
		}
		
		if (StringUtils.equals(this.action, ActvAction.DN_FOLLOWUP.toString())){
			updateActvRemarks(appendOtherRemark(LtsActvBackendConstant.ACTV_REMAKRS_FOR_DN_FOLLOWUP));			
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING);
		}
		
		if (StringUtils.equals(this.action, ActvAction.CHANGE_DN.toString())){
			updateActvRemarks(generateChangeDNRemarks());
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_REQUEST.toString())){
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PIPB_REQ);
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PREINSTALL_PIPB_REQUEST.toString())){
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_PIPB_REQ);
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_REJECT.toString())){
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PIPB_REJ);
			updateOtherActvRemarks();
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING_ACTION);
		}
				
		if (StringUtils.equals(this.action, ActvAction.PIPB_REJECT_WITH_DEFER_SRD.toString())){
			int minPipbDay = Integer.parseInt(LtsSbHelper.getPipbDay("MINDAY"));;
			String ActvRmkForPipbRejWithDeferSrd = "F&S defer 30 calendar days with SRD < " + minPipbDay + " days.";
			updateActvRemarks(ActvRmkForPipbRejWithDeferSrd);

			updateOtherActvRemarks();
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING_ACTION);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_REJECT_WITH_INVALID_APPT.toString())){
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PIPB_REJ_WITH_INVALID_APPT);
			updateOtherActvRemarks();
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING_ACTION);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_CANCEL.toString())){
			addWqWaterMarkToReports();
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PIPB_CANCEL);
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_INITIAL);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_CANCEL_WITH_ACKNOWLEDGE.toString())){
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PIPB_CANCEL);
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_INITIAL);
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_ACKNOWLEDGE);
			updateActvStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_CANCELLED);	
		}
		
		if (StringUtils.equals(this.action, ActvAction.CHANGE_SRD.toString())){
			int minPipbDay = Integer.parseInt(LtsSbHelper.getPipbDay("MINDAY"));;
			String ActvRmkForChgSrd = "SRD < " + minPipbDay + " days. Defer 30 calendar days in BOM.";
			updateActvRemarks(ActvRmkForChgSrd);

			updateOtherActvRemarks();
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_INITIAL);
		}
		
		if (StringUtils.equals(this.action, ActvAction.CHANGE_SRD_BY_PIPB_REJ.toString())){
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_CHANGE_SRD_BY_PIPB_REJ);
			updateOtherActvRemarks();
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_INITIAL);
		}
		
		if (StringUtils.equals(this.action, ActvAction.RESTORE_DEFERRED_SRD.toString())){
			updateActvRemarks(generateChangeBackSRDRemarks());
			updateOtherActvRemarks();
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_INITIAL);
		}
		
		if (StringUtils.equals(this.action, ActvAction.UPDATE_APN.toString())){
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_UPD_APN);
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_INITIAL);
		}
		
		if (StringUtils.equals(this.action, ActvAction.PIPB_CANCEL_SALES_ALERT.toString())){
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_CANCEL_SALES_ALERT);
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_INITIAL);
		}
		
		if (StringUtils.equals(this.action, ActvAction.AMEND_APPROVAL.toString())){
			updateActvRemarks(generateAmendApprovalRemarks());
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING);
		}
		
		if (StringUtils.equals(this.action, ActvAction.WAIVE_QC.toString())){
			updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_WAIVE_QC);
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING);
		}
		
		if (StringUtils.equals(this.action, ActvAction.DS_AMEND_APPROVAL.toString())){
			updateActvRemarks(appendOtherRemark(LtsActvBackendConstant.ACTV_REMAKRS_FOR_DS_AMEND_APPR));
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING);
		}
		
		if (StringUtils.equals(this.action, ActvAction.CUST_NAME_NOT_MATCH.toString())){
			updateActvRemarks(appendOtherRemark(LtsActvBackendConstant.ACTV_REMAKRS_FOR_CUST_NAME_NOT_MATCH));
			updateTaskStatus(LtsActvBackendConstant.ACTV_TASK_STATUS_PENDING);
		}
		
		return true;
	}
	
	protected void createActivityForDs(String actvType) {
		// retrieve existing activity
		this.ltsActivityDTO = retrievePendingActivity(this.sbOrderId, this.user, actvType);
		// if not found, create new
		if (this.ltsActivityDTO==null) {
			ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(this.sbOrderDTO);
			this.ltsActivityDTO = new LtsActivityDTO(actvType);
			this.ltsActivityDTO.setLob(LtsActvBackendConstant.ACTV_LTS_LOB);
			this.ltsActivityDTO.setOrderId(this.sbOrderId);
			this.ltsActivityDTO.setSrvNum(ltsServiceDetail.getSrvNum());
			this.ltsActivityDTO.setSrvType(ltsServiceDetail.getTypeOfSrv());
			this.ltsActivityDTO.setShopCd(this.sbOrderDTO.getShopCd());
		}
	}
	
	protected void createActivity(String actvType) {
		// retrieve existing activity
		logger.info("createActivity - retrieve existing activity order id: " + this.sbOrderId);
		this.ltsActivityDTO = retrievePendingActivity(this.sbOrderId, this.user, actvType);
		// if not found, create new
		logger.info("createActivity - create new activity order id: " + this.sbOrderId);
		if (this.ltsActivityDTO==null) {
			ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getAcqPipbService(this.sbOrderDTO.getSrvDtls());
			this.ltsActivityDTO = new LtsActivityDTO(actvType);
			this.ltsActivityDTO.setLob(LtsActvBackendConstant.ACTV_LTS_LOB);
			this.ltsActivityDTO.setOrderId(this.sbOrderId);
			this.ltsActivityDTO.setSrvNum(ltsServiceDetail.getSrvNum());
			this.ltsActivityDTO.setSrvType(ltsServiceDetail.getTypeOfSrv());
			this.ltsActivityDTO.setShopCd(this.sbOrderDTO.getShopCd());
		}
	}
	
	protected boolean isValidPipbDayAndAppointment(ServiceDetailDTO[] ltsServiceDetail) throws Exception {
		return isValidPipbDayAndAppointment(ltsServiceDetail, null);
	}
	
	protected boolean isValidPipbDayAndAppointment(ServiceDetailDTO[] ltsServiceDetail, ActvAction actvAction) throws Exception {
		if (!LtsSbHelper.isValidPipbDay(ltsServiceDetail)) {
			if (actvAction!=null && StringUtils.equals(actvAction.toString(), ActvAction.PIPB_REJECT.toString())){
				submitPipbActivity(ActvAction.CHANGE_SRD_BY_PIPB_REJ);
			} else {
				submitPipbActivity(ActvAction.CHANGE_SRD);
			}
			submitPipbActivity(ActvAction.PIPB_REJECT_WITH_DEFER_SRD);
			return false;
		} else if (!LtsSbHelper.isValidPipbAppointment(ltsServiceDetail)) {
			submitPipbActivity(ActvAction.PIPB_REJECT_WITH_INVALID_APPT);
			return false;
		}
		return true;
	}
	
	protected ActivityTaskDTO[] retrieveTaskByType(LtsActivityDTO ltsActivityDTO, String taskType, boolean isPending) {
		ActivityTaskDTO[] activityTaskDTO = ltsActivityDTO.getTasksByType(taskType, isPending);
		if (activityTaskDTO!=null && activityTaskDTO.length>0) {
			return activityTaskDTO;
		}
		return null;
	}
	
	protected ActivityTaskDTO[] getTasksByTypeFilterOutStatus(LtsActivityDTO ltsActivityDTO, String taskType) {
		String[] filterOutStatus = {LtsActvBackendConstant.ACTV_TASK_STATUS_COMPLETED, LtsActvBackendConstant.ACTV_TASK_STATUS_CANCELLED};
		ActivityTaskDTO[] activityTaskDTO = ltsActivityDTO.getTasksByTypeFilterOutStatus(taskType, filterOutStatus); 
		if (activityTaskDTO!=null && activityTaskDTO.length>0) {
			return activityTaskDTO;
		}
		return null;
	}
	
	protected String getMaxTaskSeqFromTaskList(ActivityTaskDTO[] taskList) {
		ArrayList<Integer> taskSeqList = new ArrayList<Integer>();
		for (int i=0; i<taskList.length; i++) {
			taskSeqList.add(Integer.parseInt(taskList[i].getTaskSeq()));
		}
		Integer maxTaskSeq = Collections.max(taskSeqList);
		return Integer.toString(maxTaskSeq);
	}
	
	protected LtsActivityDTO retrievePendingActivity(String sbOrderId, String pUser, String actvType) {
		LtsActivityDTO actvDTO = this.retrieveLtsActivityService.retrievePendingLtsActivityByOrderIdActvType(
				sbOrderId, pUser, actvType);
		if (StringUtils.isNotBlank(actvDTO.getActvId())) {
			return actvDTO;
		}
		return null;
	}
		
	protected LtsActivityTaskDTO createPipbTask(String pTaskType) {
		this.ltsActivityTaskDTO = new LtsActivityTaskDTO();
		this.ltsActivityTaskDTO.setTaskType(pTaskType);
		if (this.actv instanceof LtsActivityDTO) {
			this.ltsActivityTaskDTO.setSrvType(((LtsActivityDTO)this.actv).getSrvType());
			this.ltsActivityTaskDTO.setSrvNum(((LtsActivityDTO)this.actv).getSrvNum());
			DateFormat dateFormat = new SimpleDateFormat(LtsActvBackendConstant.ACTV_SRD_DATE_FORMAT);
			ServiceDetailLtsDTO pipbServiceLts = LtsSbHelper.getAcqPipbService(this.sbOrderDTO.getSrvDtls());
			String srdStr = StringUtils.equals(pTaskType, LtsActvBackendConstant.TASK_TYPE_PIPB_REQUEST)?
					pipbServiceLts.getAppointmentDtl().getCutOverStartTime():
						pipbServiceLts.getAppointmentDtl().getAppntStartTime();
			Calendar srdDate = DateFormatHelper.getCalenderDateFromDTOFormat(srdStr);			
			this.ltsActivityTaskDTO.setSrd(dateFormat.format(srdDate.getTime()));
			if (StringUtils.equals(pTaskType, LtsActvBackendConstant.TASK_TYPE_PIPB_DN_CHANGE)) {
				ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(this.sbOrderDTO);
				this.ltsActivityTaskDTO.setRelatedSrvNum(ltsServiceDetail.getSrvNum());
				this.ltsActivityTaskDTO.setRelatedSrvType(ltsServiceDetail.getTypeOfSrv());
			} else {
				ServiceDetailLtsDTO lts2ndDelServiceDetail = LtsSbHelper.get2ndDelServices(this.sbOrderDTO.getSrvDtls());
				if (lts2ndDelServiceDetail!=null && LtsBackendConstant.DN_SOURCE_DN_PIPB.equals(lts2ndDelServiceDetail.getDnSource())) {
					this.ltsActivityTaskDTO.setRelatedSrvNum(lts2ndDelServiceDetail.getSrvNum());
					this.ltsActivityTaskDTO.setRelatedSrvType(lts2ndDelServiceDetail.getTypeOfSrv());
				}
			}
		}
		return this.ltsActivityTaskDTO;
	}
	
	protected LtsActivityTaskDTO createDsTask(String pTaskType) {
		this.ltsActivityTaskDTO = new LtsActivityTaskDTO();
		this.ltsActivityTaskDTO.setTaskType(pTaskType);
		if (this.actv instanceof LtsActivityDTO) {
			this.ltsActivityTaskDTO.setSrvType(((LtsActivityDTO)this.actv).getSrvType());
			this.ltsActivityTaskDTO.setSrvNum(((LtsActivityDTO)this.actv).getSrvNum());
			DateFormat dateFormat = new SimpleDateFormat(LtsActvBackendConstant.ACTV_SRD_DATE_FORMAT);
			ServiceDetailLtsDTO serviceLts = LtsSbHelper.getLtsService(this.sbOrderDTO);
			String srdStr = serviceLts.getAppointmentDtl().getAppntStartTime();
			Calendar srdDate = DateFormatHelper.getCalenderDateFromDTOFormat(srdStr);			
			this.ltsActivityTaskDTO.setSrd(dateFormat.format(srdDate.getTime()));
		}
		return this.ltsActivityTaskDTO;
	}
	
	protected void setRelatedTask(String taskType) {
		if (this.ltsActivityDTO!=null) {
			ActivityTaskDTO[] actvTasks = getTasksByTypeFilterOutStatus(this.ltsActivityDTO, taskType);
			if (actvTasks!=null) {
				this.relatedTask = this.ltsActivityDTO.getTaskBySeq(getMaxTaskSeqFromTaskList(actvTasks));
				this.reqTask.setRelatedTask(this.relatedTask);
			}
		}		
	}
	
	protected OrdDocDTO[] generateAllPipbRequiredReports() {
		return generateAllRequiredReports(LtsBackendConstant.LTS_PIPB_RPT_SETS);  // 2 forms (CRF & NSD)
	}
	
	@SuppressWarnings("unchecked")
	protected OrdDocDTO[] generateAllRequiredReports(String... pRptSets) {
		try {
			ReportOutputDTO rptDTO;
			Map<String, Object> rptMap = new HashMap<String, Object>();
			for (int n=0; n<pRptSets.length; n++) {			
				rptDTO = generateReport(pRptSets[n]);
				if (rptDTO!=null && StringUtils.isNotBlank(rptDTO.getFileStoragePath())) {
					rptMap.put(LtsBackendConstant.LTS_PIPB_FORM_DOC_TYPE_MAP.get(
							pRptSets[n]), rptDTO);
				}
			}
			if (rptMap.size()>0) {
				OrdDocDTO[] pDocList = new OrdDocDTO[rptMap.size()];
				Set<String> keySet = rptMap.keySet();
				int i=0;
				String key;
				for (Iterator iter = keySet.iterator(); iter.hasNext(); i++) {	
					pDocList[i] = new OrdDocDTO();
					key = (String)iter.next();
					pDocList[i].setDocType(key);
		    		rptDTO = (ReportOutputDTO)rptMap.get(key);
		    		pDocList[i].setFilePathName(rptDTO.getFileStoragePath());
				}
				return pDocList;
			}
	    	return null;			
		} catch(Exception e) {
			logger.error("Exception caught in generate reports - " + e.getMessage());
			return null;
		}
	}
	    
	protected ReportOutputDTO generateReport(String action) {
    	Map<String, Object> inputMap = new HashMap<String, Object>();
		inputMap.put(LtsBackendConstant.RPT_KEY_SBORDER, this.sbOrderDTO);
		inputMap.put(LtsBackendConstant.RPT_KEY_LOB, LtsActvBackendConstant.ACTV_LTS_LOB);
		ReportSetDTO rptSet = new ReportSetDTO();
		rptSet.setLob(LtsActvBackendConstant.ACTV_LTS_LOB);
		rptSet.setChannelId(this.sbOrderId.substring(0,1));
		rptSet.setRptSet(action);
		rptSet = (ReportSetDTO)this.reportSetLkupCacheService.get(rptSet.getLkupKey());
		if(Arrays.asList(LtsBackendConstant.LTS_PIPB_REGENERATE_RPT_SETS).contains(action)){
			rptSet.setReGen(true); // force to re-generate
			return this.reportCreationLtsService.generateReport(rptSet, inputMap);
		} else if (StringUtils.equals(LtsBackendConstant.RPT_SET_NSD_AF, action)) {
			return this.reportCreationLtsService.getSavedReport(rptSet, inputMap);	
		}
		return null;
    }
	
	protected void addWqWaterMarkToReports() {
		try {
			uploadActvDocumentService = SpringApplicationContext.getBean(UploadActvDocumentService.class);
			// 2 forms (CRF & NSD), stamp with 'CANCEL'
			for (int n=0; n<LtsBackendConstant.LTS_PIPB_RPT_SETS.length; n++) {
				uploadActvDocumentService.addWqWaterMark(this.actv.getActvId(), null,
						LtsBackendConstant.LTS_PIPB_FORM_DOC_TYPE_MAP.get(LtsBackendConstant.LTS_PIPB_RPT_SETS[n]),
						LtsActvBackendConstant.WQ_WATER_MARK_FOR_PIPB_CANCEL);
			}	
		} catch(Exception e) {
			logger.error("Exception caught in add WQ water mark - " + e.getMessage());
		}	
	}
	
	protected void updateActvRemarks(String remarks) {
		this.addActvTaskRemarks(remarks, this.user, this.actv, this.reqTask.getTaskSeq());		
	}
	
	protected String appendOtherRemark(String remarks) {
		StringBuffer s = new StringBuffer();
		s.append(remarks);
		if (StringUtils.isNotBlank(this.otherRemark)) {
			s.append("\n"+this.otherRemark);
		}
		return s.toString();
	}
	
	protected String generateChangeDNRemarks() {
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(this.sbOrderDTO);
		ServiceDetailLtsDTO portLaterSrv = LtsSbHelper.getAcqPortLaterService(this.sbOrderDTO);
		boolean isPreInstall = LtsSbHelper.isPreInstall(this.sbOrderDTO);
		
		StringBuilder remarks = new StringBuilder();
		if (portLaterSrv!=null) {
			//if(isPreInstall)
			//{
				//remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_WQ_NATURE1+"\n");
				//remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_WQ_NATURE2+"\n\n");
				
				//remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ1+"\n");
				//remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ2+" "+(ltsServiceDetail.getSrvNum()!=null?ltsServiceDetail.getSrvNum().substring(4):"")+"\n");
				//remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ3+"\n");
				//remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ4+" "+this.sbOrderDTO.getAppDate()+"\n");
				
				//remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ5+" "+getDisplayInstallationDate(ltsServiceDetail)+"\n\n");
				
				//remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ5+"\n\n");
				
				//remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ6+" ");
			//}
			
			if (StringUtils.isBlank(portLaterSrv.getDnStatus())) {
				remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_CHANGE_DN+"\n");
				remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_CREATE_DN+"\n\n");
			} else {
				remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_CHANGE_DN+"\n\n");
			}
			
			if(isPreInstall)
			{
				remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ8+"\n");
				remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ9+"\n\n");
				remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_PREINSTALL_FULL_WQ7+" "+getNonPreInstallVasCode(this.sbOrderDTO)+"\n");
			}
			
			String addOnRemark = portLaterSrv.remarkToString(LtsBackendConstant.REMARK_TYPE_ADD_ON_REMARK);
			String orderRemark = portLaterSrv.remarkToString(LtsBackendConstant.REMARK_TYPE_ORDER_REMARK);
			String custRemark = portLaterSrv.remarkToString(LtsBackendConstant.REMARK_TYPE_CUST_REMARK);
			if (StringUtils.isNotBlank(addOnRemark)) {				
				addOnRemark = StringUtils.replace(addOnRemark, LtsBackendConstant.VAR_CUSTOMER_NAME, getApplicationName(ltsServiceDetail));
				addOnRemark = StringUtils.replace(addOnRemark, LtsBackendConstant.VAR_INSTALLATION_DATE, getDisplayInstallationDate(portLaterSrv));
				addOnRemark = StringUtils.replace(addOnRemark, LtsBackendConstant.VAR_SWITCHING_DATE, getDisplaySwitchingDate(portLaterSrv));
				remarks.append(addOnRemark+"\n\n");
			}
			if (StringUtils.isNotBlank(orderRemark)) {
				remarks.append(LtsActvBackendConstant.ORDER_REMARK+"\n");
				orderRemark = StringUtils.replace(orderRemark, LtsBackendConstant.VAR_INSTALLATION_DATE, getDisplayInstallationDate(portLaterSrv));
				orderRemark = StringUtils.replace(orderRemark, LtsBackendConstant.VAR_SWITCHING_DATE, getDisplaySwitchingDate(portLaterSrv));
				remarks.append(orderRemark+"\n\n");			
			}
			if (StringUtils.isNotBlank(custRemark)) {
				remarks.append(LtsActvBackendConstant.CUSTOMER_REMARK+"\n");				
				remarks.append(custRemark+"\n\n");
			}
		}
		return remarks.toString();		
	}
	
	protected String generateChangeBackSRDRemarks() {
		StringBuilder remarks = new StringBuilder();
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getAcqPipbService(this.sbOrderDTO.getSrvDtls());
		remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_RESTORE_DEFERRED_SRD+"\n");
		remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_INSTALLATION_DATE+" "+getDisplayInstallationDate(ltsServiceDetail)+"\n");
		remarks.append(LtsActvBackendConstant.ACTV_REMAKRS_FOR_SWITCHING_DATE+" "+getDisplaySwitchingDate(ltsServiceDetail));
		return remarks.toString();
	}
	
	protected String generateAmendApprovalRemarks() {
		StringBuilder remarks = new StringBuilder();
		// add remarks here..
		return remarks.toString();
	}
	
	protected void updateTaskStatus(String status) throws Exception {
		this.activityStatusService.updateStatus(this.actv, this.reqTask, status, this.user, this.getActivityWorkQueueService());
	}
	
	protected void updateActvStatus(String status) throws Exception {
		this.activityStatusService.updateStatus(this.actv, status, this.user, this.getActivityWorkQueueService());
	}
	
	protected void updateWorkQueue(WorkQueueDTO pWorkQueue, String pUser) throws Exception {
		this.workQueueService.updateWorkQueue(pWorkQueue, pUser);
	}
	
	private void updateOtherActvRemarks() {
		ServiceDetailLtsDTO ltsServiceDetail = LtsSbHelper.getLtsService(this.sbOrderDTO);
		ItemDetailLtsDTO[] items = ltsServiceDetail.getItemDtls();
		for (int i=0; items!=null && i<items.length; ++i) {
			if (StringUtils.equals(LtsBackendConstant.IO_IND_INSTALL, items[i].getIoInd()) 
					&& (StringUtils.equals(LtsBackendConstant.ITEM_TYPE_FFP_OTHER, items[i].getItemType()) 
					|| StringUtils.equals(LtsBackendConstant.ITEM_TYPE_FFP_STAFF, items[i].getItemType())
					|| StringUtils.equals(LtsBackendConstant.ITEM_TYPE_FFP_HOT, items[i].getItemType())
					|| StringUtils.equals(LtsBackendConstant.ITEM_TYPE_VAS_FFP, items[i].getItemType()))) {
				updateActvRemarks(LtsActvBackendConstant.ACTV_REMAKRS_FOR_FIXED_FEE_PLAN_ALERT);
			}
		}
	}
	
	private String getApplicationName(ServiceDetailLtsDTO ltsServiceDetail) {
		String applicantName = null;
		if (StringUtils.equals(ltsServiceDetail.getRecontractInd(), "Y") && ltsServiceDetail.getRecontract() != null) {
			if(LtsBackendConstant.DOC_TYPE_HKBR.equals(ltsServiceDetail.getRecontract().getIdDocType())) {
				applicantName = ltsServiceDetail.getRecontract().getCompanyName();
			} else {
				applicantName = ltsServiceDetail.getRecontract().getTitle() + " "
				+ ltsServiceDetail.getRecontract().getCustLastName() + " "
				+ ltsServiceDetail.getRecontract().getCustFirstName();	
			}
		} else {
			if(LtsBackendConstant.DOC_TYPE_HKBR.equals(ltsServiceDetail.getAccount().getCustomer().getIdDocType())) {
				applicantName = ltsServiceDetail.getAccount().getCustomer().getCompanyName();
			} else {
				applicantName = ltsServiceDetail.getAccount().getCustomer().getTitle() + " "
						+ ltsServiceDetail.getAccount().getCustomer().getLastName() + " "
						+ ltsServiceDetail.getAccount().getCustomer().getFirstName();
			}
		}
		return applicantName;
	}
	
	private String getDisplayInstallationDate(ServiceDetailLtsDTO ltsServiceDetail) {
		if (ltsServiceDetail!=null && ltsServiceDetail.getAppointmentDtl().getAppntStartTime()!=null
				&& ltsServiceDetail.getAppointmentDtl().getAppntEndTime()!=null) {
			return LtsDateFormatHelper.getDateFromDTOFormat(ltsServiceDetail.getAppointmentDtl().getAppntStartTime())
			+ " (" + LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(
					ltsServiceDetail.getAppointmentDtl().getAppntStartTime(),
					ltsServiceDetail.getAppointmentDtl().getAppntEndTime())) + ")";
		}
		return null;
	}
	
	private String getDisplaySwitchingDate(ServiceDetailLtsDTO ltsServiceDetail) {
		if (ltsServiceDetail!=null && ltsServiceDetail.getAppointmentDtl().getCutOverStartTime()!=null
				&& ltsServiceDetail.getAppointmentDtl().getCutOverEndTime()!=null) {
			return LtsDateFormatHelper.getDateFromDTOFormat(ltsServiceDetail.getAppointmentDtl().getCutOverStartTime())
			+ " (" + LtsDateFormatHelper.convertToSBTime(LtsDateFormatHelper.getFromToTimeFormat(
					ltsServiceDetail.getAppointmentDtl().getCutOverStartTime(), 
					ltsServiceDetail.getAppointmentDtl().getCutOverEndTime())) + ")";
		}
		return null;
	}
	
    private void initialize(SbOrderDTO sbOrderDTO, String sbOrderId, String pUser, String remark) {
		this.sbOrderId = sbOrderId;
		this.user = pUser;
		this.otherRemark = remark;
		this.sbOrderDTO = sbOrderDTO;
		if (this.sbOrderDTO==null) {
			retrieveSbOrder(sbOrderId);
		}
	}
	
	private void retrieveSbOrder(String sbOrderId) {
		this.sbOrderDTO = orderRetrieveLtsService.retrieveSbOrder(sbOrderId, true);
	}
    
	/**
	 * @return the retrieveLtsActivityService
	 */
	public RetrieveLtsActivityService getRetrieveLtsActivityService() {
		return retrieveLtsActivityService;
	}

	/**
	 * @param retrieveLtsActivityService the retrieveLtsActivityService to set
	 */
	public void setRetrieveLtsActivityService(
			RetrieveLtsActivityService retrieveLtsActivityService) {
		this.retrieveLtsActivityService = retrieveLtsActivityService;
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
	 * @return the workQueueService
	 */
	public WorkQueueService getWorkQueueService() {
		return workQueueService;
	}

	/**
	 * @param workQueueService the workQueueService to set
	 */
	public void setWorkQueueService(WorkQueueService workQueueService) {
		this.workQueueService = workQueueService;
	}

	public LtsOrderDocumentService getLtsOrderDocumentService() {
		return ltsOrderDocumentService;
	}

	public void setLtsOrderDocumentService(LtsOrderDocumentService ltsOrderDocumentService) {
		this.ltsOrderDocumentService = ltsOrderDocumentService;
	}
	
	public OfferService getOfferService() {
		return offerService;
	}

	public void setOfferService(OfferService offerService) {
		this.offerService = offerService;
	}

	public String getNonPreInstallVasCode(SbOrderDTO sbOrder) {
		
		StringBuilder psefCd = new StringBuilder();
		
    	if(sbOrder != null && sbOrder.getSrvDtls() != null)
		{
			ServiceDetailDTO[] tempSrvDtls = sbOrder.getSrvDtls();
			for (int i=0; i<tempSrvDtls.length; i++)
			{
				if(tempSrvDtls[i] instanceof ServiceDetailLtsDTO)
				{
					ServiceDetailLtsDTO tempSrvLtsDtls = (ServiceDetailLtsDTO) tempSrvDtls[i];					
					if(tempSrvLtsDtls.getItemDtls() != null)
					{
						ItemDetailLtsDTO[] tempItemDetails = tempSrvLtsDtls.getItemDtls();
						for (int j=0; j<tempItemDetails.length; j++)
						{
							if(!tempItemDetails[j].getItemType().equalsIgnoreCase(LtsBackendConstant.ITEM_TYPE_VAS_PRE_INSTALL))
							{
								String[] itemCds =  this.offerService.getItemOfferCodes(tempItemDetails[j].getItemId());
								
								for(String itemCd : itemCds){					
									psefCd.append(itemCd.substring(2, 6) + " ");
								}
								
								ItemAttbDTO[] itemAttbs =  this.offerService.getItemAttb(tempItemDetails[j].getItemId());
								if (ArrayUtils.isNotEmpty(itemAttbs)) {
									for (ItemAttbDTO itemAttb : itemAttbs) {
										//if (StringUtils.containsIgnoreCase("Password", itemAttb.getAttbDesc())) {
											String tempAttbDesc = itemAttb.getAttbDesc();
											String tempAttbValue = "";
											ServiceActionBase itemAttributeLtsService = SpringApplicationContext.getBean("itemAttributeLtsService");
											ItemAttributeLtsDAO[] itemAttbDaos = (ItemAttributeLtsDAO[])itemAttributeLtsService.doRetrieve(sbOrder.getOrderId());
											for (int k = 0; k < itemAttbDaos.length; k++) {
												if(itemAttbDaos[k].getAttbCd().equalsIgnoreCase(itemAttb.getAttbID()))
												{
													tempAttbValue=itemAttbDaos[k].getAttbValue();
													break;
												}
											}
											psefCd.append("("+tempAttbDesc+": "+tempAttbValue + ") ");
										//}
									}
								}
							}							
						}
					}
				}
			}				
		}
		
		return psefCd.toString();		
	}
}
