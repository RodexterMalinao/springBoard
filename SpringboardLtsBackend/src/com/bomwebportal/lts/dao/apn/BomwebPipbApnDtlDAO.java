package com.bomwebportal.lts.dao.apn;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class BomwebPipbApnDtlDAO extends DaoBaseImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3745574579872885035L;
	private OraNumber batchSeq; //BOMWEB_PIPB_APN_DTL.BATCH_SEQ
	private OraNumber dtlSeq; //BOMWEB_PIPB_APN_DTL.DTL_SEQ
	private String typeOfDoc; //BOMWEB_PIPB_APN_DTL.TYPE_OF_DOC
	private OraDate appDate = new OraDateYYYYMMDDHH24MISS(); //BOMWEB_PIPB_APN_DTL.APP_DATE
	private OraDate batchDate = new OraDateYYYYMMDDHH24MISS(); //BOMWEB_PIPB_APN_DTL.BATCH_DATE
	private String apnSerial; //BOMWEB_PIPB_APN_DTL.APN_SERIAL
	private String srvNum; //BOMWEB_PIPB_APN_DTL.SRV_NUM
	private String srvNn; //BOMWEB_PIPB_APN_DTL.SRV_NN
	private OraDate chgoverStartTime = new OraDateYYYYMMDDHH24MISS(); //BOMWEB_PIPB_APN_DTL.CHGOVER_START_TIME
	private OraDate chgoverEndTime = new OraDateYYYYMMDDHH24MISS(); //BOMWEB_PIPB_APN_DTL.CHGOVER_END_TIME
	private String orderId; //BOMWEB_PIPB_APN_DTL.ORDER_ID
	private OraNumber dtlId; //BOMWEB_PIPB_APN_DTL.DTL_ID
	private String isDnMatch; //BOMWEB_PIPB_APN_DTL.IS_DN_MATCH
	private String isNnMatch; //BOMWEB_PIPB_APN_DTL.IS_NN_MATCH
	private String isDateTimeMatch; //BOMWEB_PIPB_APN_DTL.IS_DATE_TIME_MATCH
	private String status; //BOMWEB_PIPB_APN_DTL.STATUS
	private String statusMessage; //BOMWEB_PIPB_APN_DTL.STATUS_MESSAGE
	private String createBy; //BOMWEB_PIPB_APN_DTL.CREATE_BY
	private OraDate createDate= new OraDateCreateDate(); //BOMWEB_PIPB_APN_DTL.CREATE_DATE
	private String lastUpdBy; //BOMWEB_PIPB_APN_DTL.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); //BOMWEB_PIPB_APN_DTL.LAST_UPD_DATE
	private String wqFailInd; //BOMWEB_PIPB_APN_DTL.WQ_FAIL_IND
	private String duplexAction; //BOMWEB_PIPB_APN_DTL.DUPLEX_ACTION
	private String wqStatus; //BOMWEB_PIPB_APN_DTL.WQ_STATUS
	
	public BomwebPipbApnDtlDAO() { 
		primaryKeyFields = new String[] {"batchSeq", "dtlSeq"}; 
	}
	public String getTableName() {
		return "BOMWEB_PIPB_APN_DTL";
	}	
	public String getTypeOfDoc() {
		 return this.typeOfDoc; 
	}
	public String getApnSerial() {
		 return this.apnSerial; 
	}
	public String getSrvNum() {
		 return this.srvNum; 
	}
	public String getSrvNn() {
		 return this.srvNn; 
	}
	public String getOrderId() {
		 return this.orderId; 
	}
	public String getIsDnMatch() {
		 return this.isDnMatch; 
	}
	public String getIsNnMatch() {
		 return this.isNnMatch; 
	}
	public String getIsDateTimeMatch() {
		 return this.isDateTimeMatch; 
	}
	public String getStatus() {
		 return this.status; 
	}
	public String getStatusMessage() {
		 return this.statusMessage; 
	}
	public String getCreateBy() {
		 return this.createBy; 
	}
	public String getLastUpdBy() {
		 return this.lastUpdBy; 
	}
	public String getBatchSeq() {
		 return this.batchSeq != null ? this.batchSeq.toString() : null; 
	}
	public String getDtlSeq() {
		 return this.dtlSeq != null ? this.dtlSeq.toString() : null; 
	}
	public String getDtlId() {
		 return this.dtlId != null ? this.dtlId.toString() : null; 
	}
	public String getAppDate() {
		 return this.appDate != null ? this.appDate.toString() : null; 
	}
	public String getBatchDate() {
		 return this.batchDate != null ? this.batchDate.toString() : null; 
	}
	public String getChgoverStartTime() {
		 return this.chgoverStartTime != null ? this.chgoverStartTime.toString() : null; 
	}
	public String getChgoverEndTime() {
		 return this.chgoverEndTime != null ? this.chgoverEndTime.toString() : null; 
	}
	public String getCreateDate() {
		 return this.createDate != null ? this.createDate.toString() : null; 
	}
	public String getLastUpdDate() {
		 return this.lastUpdDate != null ? this.lastUpdDate.toString() : null; 
	}
	public OraDate getAppDateORACLE() {
		 return this.appDate; 
	}
	public OraDate getBatchDateORACLE() {
		 return this.batchDate; 
	}
	public OraDate getChgoverStartTimeORACLE() {
		 return this.chgoverStartTime; 
	}
	public OraDate getChgoverEndTimeORACLE() {
		 return this.chgoverEndTime; 
	}
	public OraDate getCreateDateORACLE() {
		 return this.createDate; 
	}
	public OraDate getLastUpdDateORACLE() {
		 return this.lastUpdDate; 
	}
	public void setTypeOfDoc(String pTypeOfDoc) {
		 this.typeOfDoc = pTypeOfDoc; 
	}
	public void setApnSerial(String pApnSerial) {
		 this.apnSerial = pApnSerial; 
	}
	public void setSrvNum(String pSrvNum) {
		 this.srvNum = pSrvNum; 
	}
	public void setSrvNn(String pSrvNn) {
		 this.srvNn = pSrvNn; 
	}
	public void setOrderId(String pOrderId) {
		 this.orderId = pOrderId; 
	}
	public void setIsDnMatch(String pIsDnMatch) {
		 this.isDnMatch = pIsDnMatch; 
	}
	public void setIsNnMatch(String pIsNnMatch) {
		 this.isNnMatch = pIsNnMatch; 
	}
	public void setIsDateTimeMatch(String pIsDateTimeMatch) {
		 this.isDateTimeMatch = pIsDateTimeMatch; 
	}
	public void setStatus(String pStatus) {
		 this.status = pStatus; 
	}
	public void setStatusMessage(String pStatusMessage) {
		 this.statusMessage = pStatusMessage; 
	}
	public void setCreateBy(String pCreateBy) {
		 this.createBy = pCreateBy; 
	}
	public void setLastUpdBy(String pLastUpdBy) {
		 this.lastUpdBy = pLastUpdBy; 
	}
	public void setBatchSeq(String pBatchSeq) {
		 this.batchSeq = new OraNumber(pBatchSeq); 
	}
	public void setDtlSeq(String pDtlSeq) {
		 this.dtlSeq = new OraNumber(pDtlSeq); 
	}
	public void setDtlId(String pDtlId) {
		 this.dtlId = new OraNumber(pDtlId); 
	}
	public void setAppDate(String pAppDate) {
		 this.appDate = new OraDateYYYYMMDDHH24MISS (pAppDate); 
	}
	public void setBatchDate(String pBatchDate) {
		 this.batchDate = new OraDateYYYYMMDDHH24MISS (pBatchDate); 
	}
	public void setChgoverStartTime(String pChgoverStartTime) {
		 this.chgoverStartTime = new OraDateYYYYMMDDHH24MISS (pChgoverStartTime); 
	}
	public void setChgoverEndTime(String pChgoverEndTime) {
		 this.chgoverEndTime = new OraDateYYYYMMDDHH24MISS (pChgoverEndTime); 
	}
	public void setCreateDate(String pCreateDate) {
		 this.createDate = new OraDateCreateDate(pCreateDate); 
	}
	public void setLastUpdDate(String pLastUpdDate) {
		 this.lastUpdDate = new OraDateLastUpdDate (pLastUpdDate); 
	}
	public String getWqFailInd() {
		return wqFailInd;
	}
	public void setWqFailInd(String wqFailInd) {
		this.wqFailInd = wqFailInd;
	}
	public String getDuplexAction() {
		return duplexAction;
	}
	public void setDuplexAction(String duplexAction) {
		this.duplexAction = duplexAction;
	}
	public String getWqStatus() {
		return wqStatus;
	}
	public void setWqStatus(String wqStatus) {
		this.wqStatus = wqStatus;
	}	
}
