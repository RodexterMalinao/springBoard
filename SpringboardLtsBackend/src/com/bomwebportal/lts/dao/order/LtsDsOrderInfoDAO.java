package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;

public class LtsDsOrderInfoDAO  extends DaoBaseImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1959777462112558956L;

	private String orderId; //BOMWEB_ORDER_LTS.ORDER_ID
	private String peLink; //BOMWEB_ORDER_LTS.PE_LINK
	private String afStatus; //BOMWEB_ORDER_LTS.AF_STATUS
	private String dsType; //BOMWEB_ORDER_LTS.DS_TYPE
	private String dsLocation; //BOMWEB_ORDER_LTS.DS_LOCATION
	private String cooloff; //BOMWEB_ORDER_LTS.COOLOFF
	private String waiveCloff; //BOMWEB_ORDER_LTS.WAIVE_CLOFF
	private String qcCallTime; //BOMWEB_ORDER_LTS.QC_CALL_TIME
	private String mustQc; //BOMWEB_ORDER_LTS.MUST_QC
	private String mustQcReasonCd; //BOMWEB_ORDER_LTS.MUST_QC_REASON_CD
	private String waiveQcCd; //BOMWEB_ORDER_LTS.WAIVE_QC_CD
	private String waiveQcStatus; //BOMWEB_ORDER_LTS.WAIVE_QC_STATUS
	private String createBy; //BOMWEB_ORDER_LTS.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); //BOMWEB_ORDER_LTS.CREATE_DATE
	private String lastUpdBy; //BOMWEB_ORDER_LTS.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); //BOMWEB_ORDER_LTS.LAST_UPD_DATE
	private String nameMismatchStatus; //BOMWEB_ORDER_LTS.NAME_MISMATCH_STATUS
	
	public LtsDsOrderInfoDAO() {
		primaryKeyFields = new String[] { "orderId" };
	}
	public String getTableName() {
		return "BOMWEB_ORDER_LTS";
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPeLink() {
		return peLink;
	}
	public void setPeLink(String peLink) {
		this.peLink = peLink;
	}
	public String getAfStatus() {
		return afStatus;
	}
	public void setAfStatus(String afStatus) {
		this.afStatus = afStatus;
	}
	public String getDsType() {
		return dsType;
	}
	public void setDsType(String dsType) {
		this.dsType = dsType;
	}
	public String getDsLocation() {
		return dsLocation;
	}
	public void setDsLocation(String dsLocation) {
		this.dsLocation = dsLocation;
	}
	public String getCooloff() {
		return cooloff;
	}
	public void setCooloff(String cooloff) {
		this.cooloff = cooloff;
	}
	public String getWaiveCloff() {
		return waiveCloff;
	}
	public void setWaiveCloff(String waiveCloff) {
		this.waiveCloff = waiveCloff;
	}
	public String getQcCallTime() {
		return qcCallTime;
	}
	public void setQcCallTime(String qcCallTime) {
		this.qcCallTime = qcCallTime;
	}
	public String getMustQc() {
		return mustQc;
	}
	public void setMustQc(String mustQc) {
		this.mustQc = mustQc;
	}
	public String getMustQcReasonCd() {
		return mustQcReasonCd;
	}
	public void setMustQcReasonCd(String mustQcReasonCd) {
		this.mustQcReasonCd = mustQcReasonCd;
	}
	public String getWaiveQcCd() {
		return waiveQcCd;
	}
	public void setWaiveQcCd(String waiveQcCd) {
		this.waiveQcCd = waiveQcCd;
	}
	public String getWaiveQcStatus() {
		return waiveQcStatus;
	}
	public void setWaiveQcStatus(String waiveQcStatus) {
		this.waiveQcStatus = waiveQcStatus;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
	public void setCreateDate(OraDate createDate) {
		this.createDate = createDate;
	}

	public void setLastUpdDate(OraDate lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}
	public String getNameMismatchStatus() {
		return nameMismatchStatus;
	}
	public void setNameMismatchStatus(String nameMismatchStatus) {
		this.nameMismatchStatus = nameMismatchStatus;
	}
}
