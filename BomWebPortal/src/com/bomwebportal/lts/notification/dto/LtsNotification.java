package com.bomwebportal.lts.notification.dto;

public abstract class LtsNotification {
	public abstract Integer getNotificationId();
	public abstract String getSender();
	public abstract String getrecipient();
	public abstract String getMessage();
	public abstract String getStatus();
	public abstract Integer getRetryCnt();
	
	public abstract void setNotificationId(Integer notificationId);
	public abstract void setSender(String sender);
	public abstract void setrecipient(String recipient);
	public abstract void setMessage(String message);
	public abstract void setStatus(String status);
	public abstract void setRetryCnt(Integer retryCnt);
	
	private String result;

	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
