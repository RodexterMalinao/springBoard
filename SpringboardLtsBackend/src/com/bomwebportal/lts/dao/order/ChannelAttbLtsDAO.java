package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ChannelAttbLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -6416927147095589055L;

	private String orderId; // BOMWEB_CHANNEL_ATTB.ORDER_ID
	private OraNumber dtlId; // BOMWEB_CHANNEL_ATTB.DTL_ID
	private String channelId; // BOMWEB_CHANNEL_ATTB.CHANNEL_ID
	private String attbId; // BOMWEB_CHANNEL_ATTB.ATTB_ID
	private String attbValue; // BOMWEB_CHANNEL_ATTB.ATTB_VALUE
	private String createBy; // BOMWEB_CHANNEL_ATTB.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_CHANNEL_ATTB.CREATE_DATE
	private String lastUpdBy; // BOMWEB_CHANNEL_ATTB.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_CHANNEL_ATTB.LAST_UPD_DATE

	
	public ChannelAttbLtsDAO() {
		primaryKeyFields = new String[] {"orderId", "dtlId", "attbId"};
	}

	public String getTableName() {
		return "BOMWEB_CHANNEL_ATTB";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getAttbId() {
		return attbId;
	}

	public void setAttbId(String attbId) {
		this.attbId = attbId;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public String getAttbValue() {
		return this.attbValue;
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

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
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

	public void setChannelId(String pChannelId) {
		this.channelId = pChannelId;
	}

	public void setAttbValue(String pAttbValue) {
		this.attbValue = pAttbValue;
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

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
