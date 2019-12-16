package com.bomwebportal.lts.notification.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bomwebportal.lts.notification.dao.BomNotificationTaskDAO;
import com.bomwebportal.lts.notification.dto.BomNotificationConstants;
import com.bomwebportal.lts.notification.dto.LtsNotification;
import com.bomwebportal.lts.service.sms.LtsSmsService;

public class BomNotificationServiceSmsImpl implements LtsNotificationService{
	private static final Logger logger = LoggerFactory.getLogger(BomNotificationServiceSmsImpl.class);
	private String ref;
	
	private BomNotificationTaskDAO bomNotificationTaskDAO;
	private LtsSmsService ltsSmsService;

	public LtsSmsService getLtsSmsService() {
		return ltsSmsService;
	}

	public void setLtsSmsService(LtsSmsService ltsSmsService) {
		this.ltsSmsService = ltsSmsService;
	}

	public BomNotificationTaskDAO getBomNotificationTaskDAO() {
		return bomNotificationTaskDAO;
	}

	public void setBomNotificationTaskDAO(BomNotificationTaskDAO bomNotificationTaskDAO) {
		this.bomNotificationTaskDAO = bomNotificationTaskDAO;
	}

	public List<LtsNotification> getNotificationsToSend(String[] jobName) {
		return this.bomNotificationTaskDAO.getNotificationsNotInStatus(BomNotificationConstants.STATUS_COMPLETED, 
				BomNotificationConstants.MSG_TYPE_SMS, jobName);
	}

	public List<LtsNotification> getNotificationById(Integer msgId) {
		return this.bomNotificationTaskDAO.getNotificationsByMsgId(msgId);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRES_NEW)
	public String sendNotification(LtsNotification ltsNotification) {
		String ret = "";
		String msgContent = ltsNotification.getMessage();
		String smsNo = ltsNotification.getrecipient();
		
		Integer msgId = ltsNotification.getNotificationId();
		Integer retryCnt = ltsNotification.getRetryCnt();
		
		if(StringUtils.equalsIgnoreCase(ltsNotification.getStatus(), BomNotificationConstants.STATUS_FAILED)){
			retryCnt = retryCnt +1; 
		}
		ret = this.ltsSmsService.sendSms(smsNo, msgContent, this.ref);
		if(StringUtils.contains(ret, LtsSmsService.STATUS_FAIL)){
			if(logger.isDebugEnabled()){
				logger.debug("Sending sms fail, result: "+ret+". msgContent:"+msgContent+", smsNo:"+smsNo+", ref:"+this.ref);
			}
			ret = BomNotificationConstants.STATUS_FAILED;
			this.bomNotificationTaskDAO.updateNotificationStatus(BomNotificationConstants.STATUS_FAILED, 
					BomNotificationConstants.USER_OCMSENDSMS, retryCnt, msgId);
		}else if(StringUtils.contains(ret, LtsSmsService.STATUS_SUCCESS)){
			if(logger.isDebugEnabled()){
				logger.debug("Sending sms successfully, result: "+ret+". msgContent:"+msgContent+", smsNo:"+smsNo+", ref:"+this.ref);
			}
			ret = BomNotificationConstants.STATUS_SUCCESS;
			this.bomNotificationTaskDAO.updateNotificationStatus(BomNotificationConstants.STATUS_COMPLETED, 
					BomNotificationConstants.USER_OCMSENDSMS, retryCnt, msgId);
		}
		return ret;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}
	
	
}
