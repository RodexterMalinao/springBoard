package com.bomwebportal.lts.theclub.dto;

import java.util.Date;

public class LtsTheClubTxnDTO {

	private Integer txnId;
	private String custNum;
	private String memberId;
	private Integer ocId;
	private Integer itemId;
	private Integer offerId;
	private String psefCd;
	private String transId;
	private String requestStr;
	private String responseStr;
	private Integer clubPoint;
	private String transStatus;
	private Date createDate;
	private Date lastUpdDate;
	private String idDocNum;
	private String idDocType;

	public LtsTheClubTxnDTO() {
	}

	public LtsTheClubTxnDTO(Integer txnId, String custNum, String memberId, Integer ocId, Integer itemId,
			Integer offerId, String psefCd, String transId, String requestStr, String responseStr, Integer clubPoint,
			String transStatus, String idDocNum, String idDocType) {
		super();
		this.txnId = txnId;
		this.custNum = custNum;
		this.memberId = memberId;
		this.ocId = ocId;
		this.itemId = itemId;
		this.offerId = offerId;
		this.psefCd = psefCd;
		this.transId = transId;
		this.requestStr = requestStr;
		this.responseStr = responseStr;
		this.clubPoint = clubPoint;
		this.transStatus = transStatus;
		this.idDocNum = idDocNum;
		this.idDocType = idDocType;
	}

	public Integer getTxnId() {
		return txnId;
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

	public String getRequestStr() {
		return requestStr;
	}

	public String getResponseStr() {
		return responseStr;
	}

	public Integer getClubPoint() {
		return clubPoint;
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

	public String getIdDocNum() {
		return idDocNum;
	}

	public String getIdDocType() {
		return idDocType;
	}

	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
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

	public void setRequestStr(String requestStr) {
		this.requestStr = requestStr;
	}

	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}

	public void setClubPoint(Integer clubPoint) {
		this.clubPoint = clubPoint;
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

	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}

	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
}