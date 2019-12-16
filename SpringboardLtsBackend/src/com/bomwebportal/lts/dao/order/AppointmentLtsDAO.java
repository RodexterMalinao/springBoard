package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class AppointmentLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -8945128798057011005L;

	private String orderId; // BOMWEB_APPOINTMENT.ORDER_ID
	private OraNumber dtlId; // BOMWEB_APPOINTMENT.DTL_ID
	private String serialNum; // BOMWEB_APPOINTMENT.SERIAL_NUM
	private OraDate appntStartTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.APPNT_START_TIME
	private OraDate appntEndTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.APPNT_END_TIME
	private String instContactName; // BOMWEB_APPOINTMENT.INST_CONTACT_NAME
	private String instContactNum; // BOMWEB_APPOINTMENT.INST_CONTACT_NUM
	private String instSmsNum; // BOMWEB_APPOINTMENT.INST_SMS_NUM
	private OraDate exactAppntTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.EXACT_APPNT_TIME
	private String forcedDelayInd; // BOMWEB_APPOINTMENT.FORCED_DELAY_IND
	private OraDate preWiringStartTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.PRE_WIRING_START_TIME
	private OraDate preWiringEndTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.PRE_WIRING_END_TIME
	private String preWiringType; // BOMWEB_APPOINTMENT.PRE_WIRING_TYPE
	private String tidInd; // BOMWEB_APPOINTMENT.TID_IND
	private String createBy; // BOMWEB_APPOINTMENT.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_APPOINTMENT.CREATE_DATE
	private String lastUpdBy; // BOMWEB_APPOINTMENT.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_APPOINTMENT.LAST_UPD_DATE
	private OraDate cutOverStartTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.CUT_OVER_START_TIME
	private OraDate cutOverEndTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.CUT_OVER_END_TIME
	private OraDate tidStartTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.TID_START_TIME
	private OraDate tidEndTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.TID_END_TIME
	private String instContactMobile; // BOMWEB_APPOINTMENT.INST_CONTACT_MOBILE
	private String custContactMobile; // BOMWEB_APPOINTMENT.CUST_CONTACT_MOBILE
	private String custContactFix;	// BOMWEB_APPOINTMENT.CUST_CONTACT_FIX
	private String appntType;	// BOMWEB_APPOINTMENT.APPNT_TYPE
	private String delayReaCd;  // BOMWEB_APPOINTMENT.DELAY_REA_CD
	private OraDate bomAppntStartTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.BOM_APPNT_START_TIME
	private OraDate bomAppntEndTime = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_APPOINTMENT.BOM_APPNT_END_TIME
	

	public AppointmentLtsDAO() {
		primaryKeyFields = new String[] { "orderId", "dtlId" };
	}

	public String getTableName() {
		return "BOMWEB_APPOINTMENT";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getSerialNum() {
		return this.serialNum;
	}

	public String getInstContactName() {
		return this.instContactName;
	}

	public String getInstContactNum() {
		return this.instContactNum;
	}

	public String getInstSmsNum() {
		return this.instSmsNum;
	}

	public String getForcedDelayInd() {
		return this.forcedDelayInd;
	}

	public String getPreWiringType() {
		return this.preWiringType;
	}

	public String getTidInd() {
		return this.tidInd;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}

	public String getAppntStartTime() {
		return this.appntStartTime != null ? this.appntStartTime.toString()
				: null;
	}
	
	public String getBomAppntStartTime() {
		return this.bomAppntStartTime != null ? this.bomAppntStartTime.toString()
				: null;
	}	
	
	public String getTidStartTime() {
		return this.tidStartTime != null ? this.tidStartTime.toString()
				: null;
	}
	
	public String getCutOverStartTime() {
		return this.cutOverStartTime != null ? this.cutOverStartTime.toString()
				: null;
	}

	public String getAppntEndTime() {
		return this.appntEndTime != null ? this.appntEndTime.toString() : null;
	}
	
	public String getBomAppntEndTime() {
		return this.bomAppntEndTime != null ? this.bomAppntEndTime.toString() : null;
	}	
	
	public String getTidEndTime() {
		return this.tidEndTime != null ? this.tidEndTime.toString()
				: null;
	}
	
	public String getCutOverEndTime() {
		return this.cutOverEndTime != null ? this.cutOverEndTime.toString() : null;
	}

	public String getExactAppntTime() {
		return this.exactAppntTime != null ? this.exactAppntTime.toString()
				: null;
	}

	public String getPreWiringStartTime() {
		return this.preWiringStartTime != null ? this.preWiringStartTime
				.toString() : null;
	}

	public String getPreWiringEndTime() {
		return this.preWiringEndTime != null ? this.preWiringEndTime.toString()
				: null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getAppntStartTimeORACLE() {
		return this.appntStartTime;
	}
	
	public OraDate getBomAppntStartTimeORACLE() {
		return this.bomAppntStartTime;
	}	
	
	public OraDate getCutOverStartTimeORACLE() {
		return this.cutOverStartTime;
	}
	
	public OraDate getTidStartTimeORACLE() {
		return this.tidStartTime;
	}

	public OraDate getAppntEndTimeORACLE() {
		return this.appntEndTime;
	}
	
	public OraDate getBomAppntEndTimeORACLE() {
		return this.bomAppntEndTime;
	}	
	
	public OraDate getCutOverEndTimeORACLE() {
		return this.cutOverEndTime;
	}
	
	public OraDate getTidEndTimeORACLE() {
		return this.tidEndTime;
	}

	public OraDate getExactAppntTimeORACLE() {
		return this.exactAppntTime;
	}

	public OraDate getPreWiringStartTimeORACLE() {
		return this.preWiringStartTime;
	}

	public OraDate getPreWiringEndTimeORACLE() {
		return this.preWiringEndTime;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setSerialNum(String pSerialNum) {
		this.serialNum = pSerialNum;
	}

	public void setInstContactName(String pInstContactName) {
		this.instContactName = pInstContactName;
	}

	public void setInstContactNum(String pInstContactNum) {
		this.instContactNum = pInstContactNum;
	}

	public void setInstSmsNum(String pInstSmsNum) {
		this.instSmsNum = pInstSmsNum;
	}

	public void setForcedDelayInd(String pForcedDelayInd) {
		this.forcedDelayInd = pForcedDelayInd;
	}

	public void setPreWiringType(String pPreWiringType) {
		this.preWiringType = pPreWiringType;
	}

	public void setTidInd(String pTidInd) {
		this.tidInd = pTidInd;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	public void setAppntStartTime(String pAppntStartTime) {
		this.appntStartTime = new OraDateYYYYMMDDHH24MISS(pAppntStartTime);
	}
	
	public void setBomAppntStartTime(String pBomAppntStartTime) {
		this.bomAppntStartTime = new OraDateYYYYMMDDHH24MISS(pBomAppntStartTime);
	}	
	
	public void setCutOverStartTime(String pCutOverStartTime) {
		this.cutOverStartTime = new OraDateYYYYMMDDHH24MISS(pCutOverStartTime);
	}
	
	public void setTidStartTime(String pTidStartTime) {
		this.tidStartTime = new OraDateYYYYMMDDHH24MISS(pTidStartTime);
	}

	public void setAppntEndTime(String pAppntEndTime) {
		this.appntEndTime = new OraDateYYYYMMDDHH24MISS(pAppntEndTime);
	}
	
	public void setBomAppntEndTime(String pBomAppntEndTime) {
		this.bomAppntEndTime = new OraDateYYYYMMDDHH24MISS(pBomAppntEndTime);
	}	
	
	public void setCutOverEndTime(String pCutOverEndTime) {
		this.cutOverEndTime = new OraDateYYYYMMDDHH24MISS(pCutOverEndTime);
	}
	
	public void setTidEndTime(String pTidEndTime) {
		this.tidEndTime = new OraDateYYYYMMDDHH24MISS(pTidEndTime);
	}

	public void setExactAppntTime(String pExactAppntTime) {
		this.exactAppntTime = new OraDateYYYYMMDDHH24MISS(pExactAppntTime);
	}

	public void setPreWiringStartTime(String pPreWiringStartTime) {
		this.preWiringStartTime = new OraDateYYYYMMDDHH24MISS(
				pPreWiringStartTime);
	}

	public void setPreWiringEndTime(String pPreWiringEndTime) {
		this.preWiringEndTime = new OraDateYYYYMMDDHH24MISS(pPreWiringEndTime);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getInstContactMobile() {
		return this.instContactMobile;
	}

	public void setInstContactMobile(String pInstContactMobile) {
		this.instContactMobile = pInstContactMobile;
	}

	public String getCustContactMobile() {
		return this.custContactMobile;
	}

	public void setCustContactMobile(String pCustContactMobile) {
		this.custContactMobile = pCustContactMobile;
	}

	public String getCustContactFix() {
		return this.custContactFix;
	}

	public void setCustContactFix(String pCustContactFix) {
		this.custContactFix = pCustContactFix;
	}

	public String getAppntType() {
		return this.appntType;
	}

	public void setAppntType(String pAppntType) {
		this.appntType = pAppntType;
	}

	/**
	 * @return the delayReaCd
	 */
	public String getDelayReaCd() {
		return delayReaCd;
	}

	/**
	 * @param delayReaCd the delayReaCd to set
	 */
	public void setDelayReaCd(String pDelayReaCd) {
		this.delayReaCd = pDelayReaCd;
	}
}
