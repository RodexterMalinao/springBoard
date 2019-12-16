package com.bomwebportal.lts.notification.dto;

import java.util.Date;

public class BomNotificationMsgLog {
	private Integer msgId; // B_OC_NOTIFY_MSG.MSG_ID
	private String msgType; // B_OC_NOTIFY_MSG.MSG_TYPE
	private String msgGrp; // B_OC_NOTIFY_MSG.MSG_GRP
	private Integer msgSeq; // B_OC_NOTIFY_MSG.MSG_SEQ
	private String msgText; // B_OC_NOTIFY_MSG.MSG_TEXT
	private String msgCharSet; // B_OC_NOTIFY_MSG.MSG_CHAR_SET
	private String msgFrom; // B_OC_NOTIFY_MSG.MSG_FROM
	private String msgTo; // B_OC_NOTIFY_MSG.MSG_TO
	private String jobname; // B_OC_NOTIFY_MSG.JOBNAME
	private String cksumBase64; // B_OC_NOTIFY_MSG.CKSUM_BASE64
	private String status; // B_OC_NOTIFY_MSG.STATUS
	private Integer retryCnt; // B_OC_NOTIFY_MSG.RETRY_CNT
	private Integer retryLimit; // B_OC_NOTIFY_MSG.RETRY_LIMIT
	private Date sendAfterDate; // B_OC_NOTIFY_MSG.SEND_AFTER_DATE
	public Integer getMsgId() {
		return msgId;
	}
	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}
	public String getMsgType() {
		return msgType;
	}
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	public String getMsgGrp() {
		return msgGrp;
	}
	public void setMsgGrp(String msgGrp) {
		this.msgGrp = msgGrp;
	}
	public Integer getMsgSeq() {
		return msgSeq;
	}
	public void setMsgSeq(Integer msgSeq) {
		this.msgSeq = msgSeq;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public String getMsgCharSet() {
		return msgCharSet;
	}
	public void setMsgCharSet(String msgCharSet) {
		this.msgCharSet = msgCharSet;
	}
	public String getMsgFrom() {
		return msgFrom;
	}
	public void setMsgFrom(String msgFrom) {
		this.msgFrom = msgFrom;
	}
	public String getMsgTo() {
		return msgTo;
	}
	public void setMsgTo(String msgTo) {
		this.msgTo = msgTo;
	}
	public String getJobname() {
		return jobname;
	}
	public void setJobname(String jobname) {
		this.jobname = jobname;
	}
	public String getCksumBase64() {
		return cksumBase64;
	}
	public void setCksumBase64(String cksumBase64) {
		this.cksumBase64 = cksumBase64;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getRetryCnt() {
		return retryCnt;
	}
	public void setRetryCnt(Integer retryCnt) {
		this.retryCnt = retryCnt;
	}
	public Integer getRetryLimit() {
		return retryLimit;
	}
	public void setRetryLimit(Integer retryLimit) {
		this.retryLimit = retryLimit;
	}
	public Date getSendAfterDate() {
		return sendAfterDate;
	}
	public void setSendAfterDate(Date sendAfterDate) {
		this.sendAfterDate = sendAfterDate;
	}

}
