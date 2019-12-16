package com.activity.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.ObjectActionBaseDTO;
import com.bomwebportal.dto.RemarkDTO;
import com.bomwebportal.dto.StatusDTO;

public class ActivityDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -260926302982266631L;

	protected String actvId = null;
	private String actvType = null;
	protected String lob = null;
	protected String shopCd = null;
	protected String channelId = null;
	protected String orderId = null;
	protected String profileId = null;
	protected String keyA = null;
	protected String keyB = null;
	protected String keyC = null;
	protected String keyD = null;
	protected String keyE = null;
	protected String keyF = null;
	protected String keyG = null;
	protected String keyH = null;
	protected String keyI = null;
	protected String keyJ = null;
	private List<ActivityStatusDTO> statusList = null;
	private List<ActivityTaskDTO> taskList = null;
	private List<ActivityAttributeDTO> actvAttbList = null;
	private ActivityCustomerDtlDTO[] customerDtls = null;
	private ActivityAddressDTO[] address = null;
	
	private List<RemarkDTO> actvRmarkList = null;

	public ActivityDTO(String pActvType) {
		this.actvType = pActvType;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getActvId() {
		return actvId;
	}

	public void setActvId(String actvId) {
		this.actvId = actvId;
	}

	public String getActvType() {
		return actvType;
	}

	public void setActvType(String actvType) {
		this.actvType = actvType;
	}

	public ActivityTaskDTO[] getTasks() {
		return (this.taskList == null || this.taskList.size() == 0) ? null : this.taskList.toArray(new ActivityTaskDTO[this.taskList.size()]);
	}

	public void setTasks(ActivityTaskDTO[] tasks) {
		
		this.taskList = new ArrayList<ActivityTaskDTO>(1);
		
		if (ArrayUtils.isEmpty(tasks)) {			
			return;
		}
		this.taskList.addAll(Arrays.asList(tasks));
	}
	
	public void addTask(ActivityTaskDTO pTask) {
		
		if (this.taskList == null) {
			this.taskList = new ArrayList<ActivityTaskDTO>(1);
		}
		this.taskList.add(pTask);
	}
	
	public void addTasks(ActivityTaskDTO[] pTasks) {
		
		if (pTasks == null) {
			return;
		}
		for (int i=0; i<pTasks.length; ++i) {
			this.addTask(pTasks[i]);
		}
	}
	
	public void removeAllTasks() {
		if (this.taskList == null) {
			this.taskList = new ArrayList<ActivityTaskDTO>(1);
		}		
		this.taskList.clear();
	}	
	
	public ActivityTaskDTO getTaskBySeq(String pTaskSeq) {
		
		if (this.taskList == null || this.taskList.size() == 0) {
			return null;
		}
		for (int i=0; i<this.taskList.size(); ++i) {
			if (StringUtils.equals(pTaskSeq, this.taskList.get(i).getTaskSeq())) {
				return this.taskList.get(i);
			}
		}
		return null;
	}
	
	public ActivityTaskDTO[] getTasksByType(String pTaskType, boolean pIsPending) {
		
		if (this.taskList == null || this.taskList.size() == 0) {
			return new ActivityTaskDTO[0];
		}
		List<ActivityTaskDTO> taskList = new ArrayList<ActivityTaskDTO>();
		ActivityTaskDTO task = null;
		
		for (int i=0; i<this.taskList.size(); ++i) {
			task = this.taskList.get(i);
			if (StringUtils.equals(pTaskType, task.getTaskType()) && (!pIsPending || task.isPendingTask())) {
				taskList.add(task);
			}
		}
		return taskList.toArray(new ActivityTaskDTO[taskList.size()]);
	}
	
	public ActivityTaskDTO[] getTasksByTypeFilterOutStatus(String pTaskType, String[] pStatuses) {
		
		if (this.taskList == null || this.taskList.size() == 0) {
			return new ActivityTaskDTO[0];
		}
		List<ActivityTaskDTO> taskList = new ArrayList<ActivityTaskDTO>();
		ActivityTaskDTO task = null;
		
		for (int i=0; i<this.taskList.size(); ++i) {
			task = this.taskList.get(i);
			if (StringUtils.equals(pTaskType, task.getTaskType()) && !ArrayUtils.contains(pStatuses, task.getTaskStatus().getStatus())) {
				taskList.add(task);
			}
		}
		return taskList.toArray(new ActivityTaskDTO[taskList.size()]);
	}
	
	// ++ KC 20141203 added for PIPB
	public ActivityTaskDTO getTaskByWqWpAssgnId(String pWqWpAssgnId) {
		
		if (this.taskList == null || this.taskList.size() == 0) {
			return null;
		}
		for (int i=0; i<this.taskList.size(); ++i) {
			if (StringUtils.equals(pWqWpAssgnId, this.taskList.get(i).getWqWpAssgnId())) {
				return this.taskList.get(i);
			}
		}
		return null;
	}
	// --KC 20141203
	
	public void removeTaskByTaskType(String pTaskType){
		if (this.taskList == null || this.taskList.size() == 0) {
			return;
		}
		for (int i=0; i<this.taskList.size(); ++i) {
			if (StringUtils.equals(pTaskType, this.taskList.get(i).getTaskType())) {
				this.taskList.get(i).setObjectAction(ACTION_DELETE);
			}
		}			
	}
	
	public void removeTaskByTaskType(String pTaskType, boolean pIsPending){
		if (this.taskList == null || this.taskList.size() == 0) {
			return;
		}
		for (int i=0; i<this.taskList.size(); ++i) {
			if (StringUtils.equals(pTaskType, this.taskList.get(i).getTaskType()) && (!pIsPending || this.taskList.get(i).isPendingTask())) {
				this.taskList.get(i).setObjectAction(ACTION_DELETE);
			}
		}			
	}
	
	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getKeyA() {
		return keyA;
	}

	public void setKeyA(String keyA) {
		this.keyA = keyA;
	}

	public String getKeyB() {
		return keyB;
	}

	public void setKeyB(String keyB) {
		this.keyB = keyB;
	}

	public String getKeyC() {
		return keyC;
	}

	public void setKeyC(String keyC) {
		this.keyC = keyC;
	}

	public String getKeyD() {
		return keyD;
	}

	public void setKeyD(String keyD) {
		this.keyD = keyD;
	}

	public String getKeyE() {
		return keyE;
	}

	public void setKeyE(String keyE) {
		this.keyE = keyE;
	}

	public String getKeyF() {
		return keyF;
	}

	public void setKeyF(String keyF) {
		this.keyF = keyF;
	}

	public String getKeyG() {
		return keyG;
	}

	public void setKeyG(String keyG) {
		this.keyG = keyG;
	}

	public String getKeyH() {
		return keyH;
	}

	public void setKeyH(String keyH) {
		this.keyH = keyH;
	}

	public String getKeyI() {
		return keyI;
	}

	public void setKeyI(String keyI) {
		this.keyI = keyI;
	}

	public String getKeyJ() {
		return keyJ;
	}

	public void setKeyJ(String keyJ) {
		this.keyJ = keyJ;
	}

	public ActivityStatusDTO getActvStatus() {
		return (this.statusList == null || this.statusList.size() == 0) ? null : this.statusList.get(0);
	}

	public StatusDTO[] getStatusHistory() {
		return this.statusList.toArray(new StatusDTO[this.statusList.size()]);
	}

	public void setStatusHistory(ActivityStatusDTO[] statusHistory) {
		this.statusList = new ArrayList<ActivityStatusDTO>(Arrays.asList(statusHistory));
	}
	
	public boolean isPendingActv() {
		
		ActivityStatusDTO status = this.getActvStatus();
		
		return status == null || status.isPendingStatus();
	}
	
	public void addLatestStatus(ActivityStatusDTO status) {
		
		if (this.statusList == null) {
			this.statusList = new ArrayList<ActivityStatusDTO>(1);
		}
		this.statusList.add(status);
	}
	
	public ActivityAttributeDTO[] getActvAttbs() {
		
		if (this.actvAttbList == null || this.actvAttbList.size() == 0) {
			return null;
		}
		return this.actvAttbList.toArray(new ActivityAttributeDTO[this.actvAttbList.size()]);
	}

	public void setActvAttbs(ActivityAttributeDTO[] pAttbs) {
		
		if (ArrayUtils.isEmpty(pAttbs)) {
			return;
		}
		this.actvAttbList = new ArrayList<ActivityAttributeDTO>();
		this.actvAttbList.addAll(Arrays.asList(pAttbs));
	}
	
	public void appendActvAttb(ActivityAttributeDTO pAttb) {

		if (this.actvAttbList == null) {
			this.actvAttbList = new ArrayList<ActivityAttributeDTO>();
		}
		this.actvAttbList.add(pAttb);
	}
	
	public ActivityAttributeDTO[] getAttbByType(String pAttbType) {
		
		List<ActivityAttributeDTO> attbList = new ArrayList<ActivityAttributeDTO>();
		ActivityAttributeDTO attb = null;
		
		for (int i=0; this.actvAttbList!=null && i<this.actvAttbList.size(); ++i) {
			attb = this.actvAttbList.get(i);
			if (StringUtils.equals(pAttbType, attb.getAttbType())) {
				attbList.add(attb);
			}
		}
		return attbList.toArray(new ActivityAttributeDTO[attbList.size()]);
	}

	public ActivityAddressDTO[] getAddress() {
		return this.address;
	}

	public void setAddress(ActivityAddressDTO[] pAddress) {
		this.address = pAddress;
	}

	public ActivityCustomerDtlDTO[] getCustomerDtls() {
		return this.customerDtls;
	}

	public void setCustomerDtls(ActivityCustomerDtlDTO[] pCustomerDtls) {
		this.customerDtls = pCustomerDtls;
	}
	public RemarkDTO[] getActvRemarks() {
		
		if (this.actvRmarkList == null || this.actvRmarkList.size() == 0) {
			return null;
		}
		return this.actvRmarkList.toArray(new RemarkDTO[this.actvRmarkList.size()]);
	}
	
	public String getActvRemarkContent() {
		
		if (this.actvRmarkList == null || this.actvRmarkList.size() == 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		
		for (int i=0; i<this.actvRmarkList.size(); ++i) {
			sb.append(this.actvRmarkList.get(i).getRmkDtl());
		}
		return sb.toString();
	}

	public void setActvRemarks(RemarkDTO[] pRemarks) {
		
		if (ArrayUtils.isEmpty(pRemarks)) {
			return;
		}
		this.actvRmarkList = new ArrayList<RemarkDTO>();
		this.actvRmarkList.addAll(Arrays.asList(pRemarks));
	}
	
	public void appendActvRemark(RemarkDTO[] pRemarks) {
		
		if (ArrayUtils.isEmpty(pRemarks)) {
			return;
		}
		for (int i=0; i<pRemarks.length; ++i) {
			this.appendActvRemark(pRemarks[i]);
		}
	}
	
	public void appendActvRemark(RemarkDTO pRemark) {
		
		int lastIndex = 0;
		
		if (this.actvRmarkList == null) {
			this.actvRmarkList = new ArrayList<RemarkDTO>();
		} else {
			lastIndex = Integer.parseInt(this.actvRmarkList.get(this.actvRmarkList.size()-1).getRmkSeq());
		}
		pRemark.setRmkSeq(String.valueOf(lastIndex+1));
		this.actvRmarkList.add(pRemark);
	}
}
