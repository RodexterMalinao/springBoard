package com.activity.service;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.activity.dto.ActivityDTO;
import com.activity.dto.ActivityTaskDTO;
import com.activity.dto.DocumentDTO;
import com.activity.service.dataAccess.ActivityRemarkService;
import com.activity.service.dataAccess.ActivityStatusService;
import com.activity.service.dataAccess.ActivityTaskServiceImpl;
import com.activity.service.dataAccess.SaveActivityService;
import com.activity.util.ActivityConstants;
import com.activity.util.ActivityIdFactory;
import com.bomwebportal.dto.OrdDocDTO;
import com.bomwebportal.dto.RemarkDTO;
import com.pccw.util.spring.SpringApplicationContext;

public abstract class ActivitySubmissionServiceImpl implements ActivitySubmissionService {

	protected SaveActivityService saveActivityService = null;
	private ActivityIdFactory activityIdFactory = null;
	protected ActivityStatusService activityStatusService = null;
	protected ActivityRemarkService activityRemarkService = null;
	protected ActivityTaskFactory activityTaskFactory = null;
	protected DocumentSubmissionService documentSubmissionService = null;
	protected ActivityTaskServiceImpl activityTaskService = null;
	protected ActivityWorkQueueService activityWorkQueueService = null;
	
	protected ActivityDTO actv = null;
	protected String user = null;
	protected String action = null;
	protected StringBuffer msgSb = null;
	protected int maxTaskSeq = -1;
	protected String remark;

	protected abstract boolean determineTasks();
	protected abstract boolean preSubmit() throws Exception;
	protected abstract boolean postSubmit() throws Exception;
		

	@Transactional
	@Override
	public boolean submitActivity(ActivityDTO pActv, String pAction, String pUser, StringBuffer pMsgSb) throws Exception {
		return submitActivity(pActv, pAction, null, pUser, pMsgSb);
	}

	public boolean submitActivity(ActivityDTO pActv, String pAction, OrdDocDTO[] pDocList, String pUser, StringBuffer pMsgSb) throws Exception {
		
		this.actv = pActv;
		this.action = pAction;
		this.user = pUser;
		this.maxTaskSeq = -1;
		this.msgSb = pMsgSb;
		
		if (!this.determineTasks()) {
			return false;
		}
		this.initialize();
		
		for (int i = 0; pDocList != null && i < pDocList.length; i++) {
			pDocList[i].setOrderId(pActv.getActvId());
			SpringApplicationContext.getBean(UploadActvDocumentService.class).uploadActvDocument(pDocList[i], pUser);
		}
		
		if (!this.preSubmit()) {
			return false;
		}
		this.saveSLVActivity();
		
		if (!this.postSubmit()) {
			return false;
		}
		return true;
	}
	
	@Transactional
	@Override
	public void createTask(ActivityDTO pActv, ActivityTaskDTO pTask, String pUser, StringBuffer pMsgSb) throws Exception {
		this.actv = pActv;		
		this.user = pUser;
		this.maxTaskSeq = -1;
		this.msgSb = pMsgSb;
		this.createTask(pTask);
	}

	@Transactional
	@Override
	public boolean completeActivity(ActivityDTO pActv, String pUser, String pRemark, StringBuffer pMsgSb) throws Exception {
		this.actv = pActv;
		this.user = pUser;
		this.maxTaskSeq = -1;
		this.msgSb = pMsgSb;
		return this.completeActivity(pRemark);
	}

	@Transactional
	@Override
	public void requestApproval(ActivityDTO pActv, ActivityTaskDTO pApprovalTask, String pApprovalTaskSystemRemark, String pUser, StringBuffer pMsgSb) throws Exception{
		this.actv = pActv;
		this.user = pUser;
		this.maxTaskSeq = -1;
		this.msgSb = pMsgSb;
//		this.action = SLVBackendConstants.ORDER_ACTION_PENDING_REQ_APPROVAL;
		this.determineTasks();
		this.saveSLVActivity();
		this.activityRemarkService.saveCurrentActvStatusRemark(pApprovalTaskSystemRemark, this.user, this.actv.getActvId(), pApprovalTask.getTaskSeq());
		this.activityStatusService.updateStatus(this.actv, pApprovalTask, ActivityConstants.ACTV_TASK_STATUS_UNDER_APPROVAL, this.user, this.getActivityWorkQueueService());
		this.postRequestApproval();
	}
	
	@Transactional
	@Override
	public void submitApproval(ActivityDTO pActv, String pApprovalTaskType, boolean pIsApprove, String pRemark, String pUser, StringBuffer pSb) throws Exception {
		
		this.actv = pActv;
		this.action = null;
		this.user = pUser;
		this.maxTaskSeq = -1;
		this.msgSb = pSb;

		ActivityTaskDTO[] approvalTasks = this.actv.getTasksByType(pApprovalTaskType, true);
		
		if (ArrayUtils.isEmpty(approvalTasks)) {
			this.msgSb.append("Approval task does not exist.\n");			
			return;
		} else if (!approvalTasks[0].isPendingTask()) {
			this.msgSb.append("Approval task no longer active.\n");			
			return;
		}
		String status = null;
		
		
		if (pIsApprove) {
			status = ActivityConstants.ACTV_TASK_STATUS_APPROVED;
			remark = "Approved.\n" + pRemark;
		} else {
			status = ActivityConstants.ACTV_TASK_STATUS_REJECTED;
			remark = "Rejected.\n" + pRemark;
		}
		this.activityRemarkService.saveCurrentActvStatusRemark(remark, this.user, this.actv.getActvId(), approvalTasks[0].getTaskSeq());
		this.activityStatusService.updateStatus(this.actv, approvalTasks[0], status, this.user, this.getActivityWorkQueueService());
		
//		this.orderTask = this.actv.getTasksByType(SLVBackendConstants.TASK_TYPE_ORDER, true)[0];
//		this.activityRemarkService.saveCurrentActvStatusRemark(remark, this.user, this.actv.getActvId(), this.orderTask.getTaskSeq());
//		this.activityStatusService.updateStatus(this.actv, this.orderTask, ActivityConstants.ACTV_TASK_STATUS_PENDING, this.user, this.getActivityWorkQueueService());
		this.postSubmitApproval(pApprovalTaskType, pIsApprove);
	}
	
	protected boolean postRequestApproval() throws Exception{
		return true;
	}
	protected boolean postSubmitApproval(String pApprovalTaskType, boolean pIsApprove) throws Exception{
		return true;
	}
	
	@Transactional
	public boolean cancelActivity(String pRemark) throws Exception{
		ActivityTaskDTO[] tasks = this.actv.getTasks();
		for (int i=0; tasks!=null && i<tasks.length; ++i) {
			if (this.cancelTask(tasks[i], null)){
			}
		}		
		this.activityRemarkService.saveCurrentActvStatusRemark(pRemark, this.user, this.actv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ);
		this.activityStatusService.updateStatus(this.actv, ActivityConstants.ACTV_TASK_STATUS_CANCELLED, this.user);
		return true;
	}

	public boolean cancelTask(ActivityTaskDTO pTask, String pRemark) throws Exception{
		this.activityRemarkService.saveCurrentActvStatusRemark(pRemark, this.user, this.actv.getActvId(), pTask.getTaskSeq());
		this.activityStatusService.updateStatus(this.actv, pTask, ActivityConstants.ACTV_TASK_STATUS_CANCELLED, this.user, this.activityWorkQueueService);
		return true;
	}	
	
	protected void createTask(ActivityTaskDTO pTask) throws Exception {
		this.initializeTasks(this.actv.getActvId(), pTask);
		this.saveTask(this.actv.getActvId(), pTask);
		this.getActivityWorkQueueService().invokeWorkQueue(this.actv, pTask, this.user);
	}
	
	protected boolean completeActivity(String pRemark) throws Exception{
		
		if (!this.actv.isPendingActv()) {
			this.msgSb.append("Activity is not active.\n");
			return false;
		}
		ActivityTaskDTO[] tasks = this.actv.getTasks();
		boolean toCompleteActv = true;
		
		for (int i=0; tasks!=null && i<tasks.length; ++i) {
			if (tasks[i].isPendingTask()) {
				this.msgSb.append(tasks[i].getTaskType());
				this.msgSb.append(" task incomplete.\n");
				toCompleteActv = false;
			}
		}
		if (!toCompleteActv) {
			this.msgSb.append("Complete activity not allow.\n");
			return false;
		}
		this.activityRemarkService.saveCurrentActvStatusRemark(pRemark, this.user, this.actv.getActvId(), ActivityConstants.ACTV_LEVEL_SEQ);
		this.activityStatusService.updateStatus(this.actv, ActivityConstants.ACTV_TASK_STATUS_COMPLETED, this.user);
		return true;
	}
	
	protected boolean completeTask(ActivityTaskDTO pTask, String pRemark) throws Exception {
		
		if (!pTask.isPendingTask()) {
			this.msgSb.append("Task is not active.\n");
			return false;
		}
		DocumentDTO[] docs = pTask.getDocuments();
		boolean toCompleteTask = true;
		
		for (int i=0; docs!=null && i<docs.length; ++i) {
			if (!StringUtils.equals("Y", docs[i].getCollectedInd())) {
				this.msgSb.append("Missing document ");
				this.msgSb.append(docs[i].getDocType());
				this.msgSb.append("\n");
			}
		}
		if (!toCompleteTask) {
			this.msgSb.append("Complete task not allow.\n");
			return false;
		}
		this.activityRemarkService.saveCurrentActvStatusRemark(pRemark, this.user, this.actv.getActvId(), pTask.getTaskSeq());
		this.activityStatusService.updateStatus(this.actv, pTask, ActivityConstants.ACTV_TASK_STATUS_COMPLETED, this.user, this.activityWorkQueueService);
		return true;
	}
	
	protected DocumentDTO createDocumentOnTask(String pActvId, ActivityTaskDTO pTask, String pDocType) {
		
		DocumentDTO doc = pTask.getDocumentByType(pDocType);
		
		if (doc != null) {
			return doc;
		}
		doc = this.documentSubmissionService.createDocument(pActvId+pTask.getTaskSeq(), pDocType, this.user);
		pTask.appendDocument(doc);
		return doc;
	}
	
	protected void removeDocumentOnTask(String pActvId, ActivityTaskDTO pTask, String pDocType) {
		
		DocumentDTO doc = pTask.getDocumentByType(pDocType);
		
		if (doc == null) {
			return;
		}
		this.documentSubmissionService.clearDocument(pActvId+pTask.getTaskSeq(), doc, this.user);
		pTask.removeDocument(doc);
	}
	
	private void initialize() {
		this.initializeActivity();
		this.initializeTasks();
	}
	
	protected void saveSLVActivity() {
		this.saveActivityService.saveActivity(this.actv, this.user);
	}
	
	private void saveTask(String pActvId, ActivityTaskDTO pTask) throws Exception {
		this.saveActivityService.saveActivityTask(pActvId, pTask, this.user);
		if (ArrayUtils.isEmpty(pTask.getStatusHistory())) {
			this.activityStatusService.updateStatus(this.actv, pTask, ActivityConstants.ACTV_TASK_STATUS_INITIAL, this.user);
		}
	}
	
	private void initializeActivity() {
		
		if (StringUtils.isBlank(this.actv.getActvId())) {
			this.actv.setActvId(this.activityIdFactory.generateActvId());
			this.activityStatusService.initializeStatus(this.actv, this.user);
		}
	}
	
	private void initializeTasks() {
		
		ActivityTaskDTO[] tasks = this.actv.getTasks();
		
		for (int i=0; tasks!=null && i<tasks.length; ++i) {
			this.initializeTasks(this.actv.getActvId(), tasks[i]);
		}
	}
	
	private void initializeTasks(String pActvId, ActivityTaskDTO pTask) {
		
		if (StringUtils.isBlank(pTask.getTaskSeq())) {
			pTask.setTaskSeq(this.getNextTaskSeq(pActvId));
// Avoid duplicate calling WQ Trigger, will be calling in saveTask(String pActvId, ActivityTaskDTO pTask)		
//			this.activityStatusService.initializeStatus(this.actv.getActvId(), pTask, this.user);
		}
	}

	private String getNextTaskSeq(String pActvId) {
		
		if (this.maxTaskSeq <= 0) {
			this.maxTaskSeq = this.activityIdFactory.getMaxTaskSeq(pActvId);
			ActivityTaskDTO[] tasks = this.actv.getTasks();
		
			if (ArrayUtils.isNotEmpty(tasks)) {
				int curTaskId = 0;
			
				for (int i=0; i<tasks.length; ++i) {
					if (StringUtils.isBlank(tasks[i].getTaskSeq())) {
						continue;
					}
					curTaskId = Integer.parseInt(tasks[i].getTaskSeq());
					if (this.maxTaskSeq < curTaskId) {
						this.maxTaskSeq = curTaskId;
					}
				}
			}
		}
		this.maxTaskSeq++;
		return String.valueOf(this.maxTaskSeq);
	}
	@Override
	public void addActvTaskRemarks(String pRemarkContent, String pUser, ActivityDTO pActvDTO, String pTaskSeq){
		RemarkDTO[] remarks;
		ActivityTaskDTO task;
		activityRemarkService = SpringApplicationContext.getBean(ActivityRemarkService.class);
		this.activityRemarkService.saveCurrentActvStatusRemark(pRemarkContent, pUser, pActvDTO.getActvId(), pTaskSeq);
		
		if (! ActivityConstants.ACTV_LEVEL_SEQ.equals(pTaskSeq)){
			remarks = activityRemarkService.getRemarksByTaskSeq(pActvDTO.getActvId(), pTaskSeq);
			task = pActvDTO.getTaskBySeq(pTaskSeq);
			task.setTaskRemarks(remarks);
		}
	}

	public SaveActivityService getSaveActivityService() {
		return saveActivityService;
	}
	
	public void setSaveActivityService(SaveActivityService saveActivityService) {
		this.saveActivityService = saveActivityService;
	}
	
	public ActivityIdFactory getActivityIdFactory() {
		return activityIdFactory;
	}
	
	public void setActivityIdFactory(ActivityIdFactory activityIdFactory) {
		this.activityIdFactory = activityIdFactory;
	}
	
	public ActivityStatusService getActivityStatusService() {
		return activityStatusService;
	}
	
	public void setActivityStatusService(ActivityStatusService activityStatusService) {
		this.activityStatusService = activityStatusService;
	}
	
	public ActivityRemarkService getActivityRemarkService() {
		return activityRemarkService;
	}
	
	public void setActivityRemarkService(ActivityRemarkService activityRemarkService) {
		this.activityRemarkService = activityRemarkService;
	}

	public ActivityTaskFactory getActivityTaskFactory() {
		return activityTaskFactory;
	}

	public void setActivityTaskFactory(ActivityTaskFactory activityTaskFactory) {
		this.activityTaskFactory = activityTaskFactory;
	}
	
	public DocumentSubmissionService getDocumentSubmissionService() {
		return documentSubmissionService;
	}
	
	public void setDocumentSubmissionService(DocumentSubmissionService documentSubmissionService) {
		this.documentSubmissionService = documentSubmissionService;
	}

	public ActivityTaskServiceImpl getActivityTaskService() {
		return this.activityTaskService;
	}
	
	public void setActivityTaskService(ActivityTaskServiceImpl pActivityTaskService) {
		this.activityTaskService = pActivityTaskService;
	}
	public ActivityWorkQueueService getActivityWorkQueueService() {
		if (this.activityWorkQueueService == null) {
			return SpringApplicationContext.getBean(ActivityWorkQueueService.class);
		}
		return this.activityWorkQueueService;
	}
	public void setActivityWorkQueueService(
			ActivityWorkQueueService pActivityWorkQueueService) {
		this.activityWorkQueueService = pActivityWorkQueueService;
	}
}
