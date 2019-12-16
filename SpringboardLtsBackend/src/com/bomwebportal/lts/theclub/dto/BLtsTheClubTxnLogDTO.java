package com.bomwebportal.lts.theclub.dto;

import java.util.Date;

import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class BLtsTheClubTxnLogDTO {
	private Integer txnLogId;
	private Integer txnId;
	private String requestStr; // B_LTS_THE_CLUB_TXN_LOG.REQUEST_STR
	private String responseStr; // B_LTS_THE_CLUB_TXN_LOG.RESPONSE_STR
	private String rtnCode;
	private String rtnMsg;
	private Date createDate; // B_LTS_THE_CLUB_TXN_LOG.CREATE_DATE
	private Date lastUpdDate; // B_LTS_THE_CLUB_TXN_LOG.LAST_UPD_DATE
	public BLtsTheClubTxnLogDTO(){}

	public BLtsTheClubTxnLogDTO(Integer txnLogId, Integer txnId, String requestStr, String responseStr, String rtnCode,
			String rtnMsg) {
		super();
		this.txnLogId = txnLogId;
		this.txnId = txnId;
		this.requestStr = requestStr;
		this.responseStr = responseStr;
		this.rtnCode = rtnCode;
		this.rtnMsg = rtnMsg;
	}

	public BLtsTheClubTxnLogDTO(Integer txnLogId, Integer txnId, String requestStr, String responseStr, String rtnCode,
			String rtnMsg, Date createDate, Date lastUpdDate) {
		super();
		this.txnLogId = txnLogId;
		this.txnId = txnId;
		this.requestStr = requestStr;
		this.responseStr = responseStr;
		this.rtnCode = rtnCode;
		this.rtnMsg = rtnMsg;
		this.createDate = createDate;
		this.lastUpdDate = lastUpdDate;
	}

	public Integer getTxnLogId() {
		return txnLogId;
	}
	public Integer getTxnId() {
		return txnId;
	}
	public String getRequestStr() {
		return requestStr;
	}
	public String getResponseStr() {
		return responseStr;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setTxnLogId(Integer txnLogId) {
		this.txnLogId = txnLogId;
	}
	public void setTxnId(Integer txnId) {
		this.txnId = txnId;
	}
	public void setRequestStr(String requestStr) {
		this.requestStr = requestStr;
	}
	public void setResponseStr(String responseStr) {
		this.responseStr = responseStr;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getRtnCode() {
		return rtnCode;
	}
	public String getRtnMsg() {
		return rtnMsg;
	}
	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}
	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

}
