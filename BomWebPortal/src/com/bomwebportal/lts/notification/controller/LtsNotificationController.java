package com.bomwebportal.lts.notification.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractCommandController;

import com.bomwebportal.lts.dto.form.NotificationFormDTO;
import com.bomwebportal.lts.notification.dto.BomNotificationConstants;
import com.bomwebportal.lts.notification.dto.LtsNotification;
import com.bomwebportal.lts.notification.service.LtsNotificationService;

public class LtsNotificationController extends AbstractCommandController {
	private static final Logger logger = LoggerFactory.getLogger(LtsNotificationController.class);
	private LtsNotificationService bomNotificationService;
	private String testNotificationRecipient;
	private boolean debugSmsService;

	public boolean isDebugSmsService() {
		return debugSmsService;
	}

	public void setDebugSmsService(boolean debugSmsService) {
		this.debugSmsService = debugSmsService;
	}

	public String getTestNotificationRecipient() {
		return testNotificationRecipient;
	}

	public void setTestNotificationRecipient(String testNotificationRecipient) {
		this.testNotificationRecipient = testNotificationRecipient;
	}

	public LtsNotificationService getBomNotificationService() {
		return bomNotificationService;
	}

	public void setBomNotificationService(LtsNotificationService bomNotificationService) {
		this.bomNotificationService = bomNotificationService;
	}

	private final String formView = "json_ltsnotification";
	private final String commandName = "notificationcmd";

	public LtsNotificationController() {
		this.setCommandClass(NotificationFormDTO.class);
		this.setCommandName(commandName);
	}

	@Override
	protected ModelAndView handle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, BindException arg3)
			throws Exception {

		NotificationFormDTO form = (NotificationFormDTO) arg2;
		Map<String, Object> ret = new HashMap<String, Object>();
		Map<Integer, Object> recordDetails = new HashMap<Integer, Object>();
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat();

		// some variables for statistic
		Date startTIme = Calendar.getInstance().getTime();
		Date endTime = null;
		Integer totalNumberOfNotification = 0;
		Integer successNotification = 0;
		Integer failNotification = 0;

		List<String> messages = new ArrayList<String>();
		logger.info("Started to sending notification.");
		if (arg3.hasErrors()) { // has errors...
			String message = "Invalid parameters received. Aborting...";
			logger.info(message);
			messages.add(message);
		} else {

			try {
				String[] jobName = form.getJobName();
				Integer msgId = form.getMsgId();
				if (jobName == null && msgId == null) {
					jobName = new String[1];
					jobName[0] = BomNotificationConstants.JOBNAME_PTSALESMAN;
				}
				List<LtsNotification> notifications = null;

				// two way to send notification...
				if (jobName != null) { // by jobname defined in records...
					notifications = this.bomNotificationService.getNotificationsToSend(jobName);
				} else if (msgId != null) { // or by id, usually for re-sending
											// or debugging purpose
					notifications = this.bomNotificationService.getNotificationById(msgId);
				}
				totalNumberOfNotification = notifications.size();
				for (LtsNotification ltsNotification : notifications) {
					String status = ltsNotification.getStatus();

					// not checking status if debugging
					if (!StringUtils.equals(BomNotificationConstants.STATUS_COMPLETED, status) || form.isDebug()) {
						if (form.isDebug() || this.isDebugSmsService()) {// change to a number for debugging
												// purpose
							ltsNotification.setrecipient(this.testNotificationRecipient);
						}
						if(!form.isTestRun()){
							result = this.bomNotificationService.sendNotification(ltsNotification);
						}
						if (StringUtils.containsIgnoreCase(result, BomNotificationConstants.STATUS_FAILED)) {
							failNotification++;
						} else {
							successNotification++;
						}
						ltsNotification.setResult(result);
						if(form.isShowRecordDetails()){
							recordDetails.put(ltsNotification.getNotificationId() , ltsNotification);
						}
					}
				}
				String message = "Finished!";
				logger.info(message);
				messages.add(message);
			} catch (Exception e) {
				logger.info(e.getMessage());
				messages.add("Failed - Exception:"+e.getMessage());
			}
		}
		endTime = Calendar.getInstance().getTime();
		ret.put("startTime", sdf.format(startTIme));
		ret.put("endTime", sdf.format(endTime));
		ret.put("totalNumberOfNotification", totalNumberOfNotification);
		ret.put("successNotification", successNotification);
		ret.put("failNotification", failNotification);
		ret.put("requestForm",form);
		ret.put("isDebugSmsService", this.isDebugSmsService());
		ret.put("isDebug", form.isDebug());
		ret.put("testNotificationRecipient", this.testNotificationRecipient);
		ret.put("messages", messages);
		if(form.isShowRecordDetails()){
			ret.put("recordDetails", recordDetails);
		}
		logger.info("Sending notification ended.");
		return new ModelAndView(formView, ret);
	}

}
