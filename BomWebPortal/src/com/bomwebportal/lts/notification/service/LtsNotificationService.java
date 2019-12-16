package com.bomwebportal.lts.notification.service;

import java.util.List;

import com.bomwebportal.lts.notification.dto.LtsNotification;

public interface LtsNotificationService {
	public List<LtsNotification> getNotificationsToSend(String[] jobName);
	public List<LtsNotification> getNotificationById(Integer msgId);
	public String sendNotification(LtsNotification ltsNotification);
}
