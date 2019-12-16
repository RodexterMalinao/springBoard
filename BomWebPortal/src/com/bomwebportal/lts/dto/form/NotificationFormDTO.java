package com.bomwebportal.lts.dto.form;

import java.io.Serializable;

public class NotificationFormDTO implements Serializable {
	boolean debug;
	boolean testRun;
	String[] jobName;
	Integer msgId;
	boolean showRecordDetails;
	boolean runJob;
	
	public boolean isTestRun() {
		return testRun;
	}

	public void setTestRun(boolean testRun) {
		this.testRun = testRun;
	}
	
	public boolean isRunJob() {
		return runJob;
	}

	public void setRunJob(boolean runJob) {
		this.runJob = runJob;
	}

	public boolean isShowRecordDetails() {
		return showRecordDetails;
	}

	public void setShowRecordDetails(boolean showRecordDetails) {
		this.showRecordDetails = showRecordDetails;
	}

	public Integer getMsgId() {
		return msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}

	public String[] getJobName() {
		return jobName;
	}

	public void setJobName(String[] jobName) {
		this.jobName = jobName;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}
}
