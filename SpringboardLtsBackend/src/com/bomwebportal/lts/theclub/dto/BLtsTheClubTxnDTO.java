package com.bomwebportal.lts.theclub.dto;

import java.util.Date;

public class BLtsTheClubTxnDTO {
	private Integer txnId;
	private String theClubOrderId;
	private String custNum;
	private String memberId;
	private Integer ocId;
	private Integer dtlId;
	private Integer dtlSeq;
	private String action;
	private String agreementNum;
	private String idDocNum;
	private String idDocType;
	private Integer itemId;
	private Integer offerId;
	private String psefCd;
	private String packageCode;
	private String reverseTransId;
	private String transId;
	private Integer retryCnt;
	private Integer retryLmt;
	private Integer clubPoints;
	private String transStatus;
	private String rtnCode;
	private String rtnMsg;
	private String channel;
	private Date createDate;
	private Date lastUpdDate;

	public BLtsTheClubTxnDTO(){
		
	}

	public BLtsTheClubTxnDTO(Integer txnId, String theClubOrderId, String custNum, String memberId, Integer ocId,
			Integer dtlId, Integer dtlSeq, String action, String agreementNum, String idDocNum, String idDocType,
			Integer itemId, Integer offerId, String psefCd, String packageCode, String reverseTransId, String transId, Integer retryCnt,
			Integer retryLmt, Integer clubPoints, String transStatus, String rtnCode, String rtnMsg, String channel) {
		super();
		this.txnId = txnId;
		this.theClubOrderId = theClubOrderId;
		this.custNum = custNum;
		this.memberId = memberId;
		this.ocId = ocId;
		this.dtlId = dtlId;
		this.dtlSeq = dtlSeq;
		this.action = action;
		this.agreementNum = agreementNum;
		this.idDocNum = idDocNum;
		this.idDocType = idDocType;
		this.itemId = itemId;
		this.offerId = offerId;
		this.psefCd = psefCd;
		this.packageCode = packageCode;
		this.reverseTransId = reverseTransId;
		this.transId = transId;
		this.retryCnt = retryCnt;
		this.retryLmt = retryLmt;
		this.clubPoints = clubPoints;
		this.transStatus = transStatus;
		this.rtnCode = rtnCode;
		this.rtnMsg = rtnMsg;
		this.channel = channel;
	}

	public BLtsTheClubTxnDTO(Integer txnId, String theClubOrderId, String custNum, String memberId, Integer ocId,
			Integer dtlId, Integer dtlSeq, String action, String agreementNum, String idDocNum, String idDocType,
			Integer itemId, Integer offerId, String psefCd, String packageCode, String reverseTransId, String transId, Integer retryCnt,
			Integer retryLmt, Integer clubPoints, String transStatus, String rtnCode, String rtnMsg, String channel,
			Date createDate, Date lastUpdDate) {
		super();
		this.txnId = txnId;
		this.theClubOrderId = theClubOrderId;
		this.custNum = custNum;
		this.memberId = memberId;
		this.ocId = ocId;
		this.dtlId = dtlId;
		this.dtlSeq = dtlSeq;
		this.action = action;
		this.agreementNum = agreementNum;
		this.idDocNum = idDocNum;
		this.idDocType = idDocType;
		this.itemId = itemId;
		this.offerId = offerId;
		this.psefCd = psefCd;
		this.packageCode = packageCode;
		this.reverseTransId = reverseTransId;
		this.transId = transId;
		this.retryCnt = retryCnt;
		this.retryLmt = retryLmt;
		this.clubPoints = clubPoints;
		this.transStatus = transStatus;
		this.rtnCode = rtnCode;
		this.rtnMsg = rtnMsg;
		this.channel = channel;
		this.createDate = createDate;
		this.lastUpdDate = lastUpdDate;
	}

	public Integer getTxnId() {
		return txnId;
	}

	public String getTheClubOrderId() {
		return theClubOrderId;
	}

	public String getCustNum() {
		return custNum;
	}

	public String getMemberId() {
		return memberId;
	}

	public Integer getOcId() {
		return ocId;
	}

	public Integer getDtlId() {
		return dtlId;
	}

	public Integer getDtlSeq() {
		return dtlSeq;
	}

	public String getAgreementNum() {
		return agreementNum;
	}

	public String getIdDocNum() {
		return idDocNum;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public Integer getItemId() {
		return itemId;
	}

	public Integer getOfferId() {
		return offerId;
	}

	public String getPsefCd() {
		return psefCd;
	}

	public String getTransId() {
		return transId;
	}

	public Integer getClubPoints() {
		return clubPoints;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
	}

	public void setTheClubOrderId(String theClubOrderId) {
		this.theClubOrderId = theClubOrderId;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public void setOcId(Integer ocId) {
		this.ocId = ocId;
	}

	public void setDtlId(Integer dtlId) {
		this.dtlId = dtlId;
	}

	public void setDtlSeq(Integer dtlSeq) {
		this.dtlSeq = dtlSeq;
	}

	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public void setOfferId(Integer offerId) {
		this.offerId = offerId;
	}

	public void setPsefCd(String psefCd) {
		this.psefCd = psefCd;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public void setClubPoints(Integer clubPoints) {
		this.clubPoints = clubPoints;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public Integer getRetryCnt() {
		return retryCnt;
	}

	public Integer getRetryLmt() {
		return retryLmt;
	}

	public void setRetryCnt(Integer retryCnt) {
		this.retryCnt = retryCnt;
	}

	public void setRetryLmt(Integer retryLmt) {
		this.retryLmt = retryLmt;
	}




	public String getAction() {
		return action;
	}




	public void setAction(String action) {
		this.action = action;
	}

	public String getRtnCode() {
		return rtnCode;
	}

	public String getRtnMsg() {
		return rtnMsg;
	}

	public String getChannel() {
		return channel;
	}

	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public String getReverseTransId() {
		return reverseTransId;
	}

	public void setReverseTransId(String reverseTransId) {
		this.reverseTransId = reverseTransId;
	}

}