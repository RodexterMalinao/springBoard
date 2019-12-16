package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ChannelLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 8776359390582842047L;
	
	private String orderId; // BOMWEB_SUBSCRIBED_CHANNEL.ORDER_ID
	private OraNumber channelId; // BOMWEB_SUBSCRIBED_CHANNEL.CHANNEL_ID
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_SUBSCRIBED_CHANNEL.CREATE_DATE
	private OraNumber dtlId; // BOMWEB_SUBSCRIBED_CHANNEL.DTL_ID

	
	public ChannelLtsDAO() {
		primaryKeyFields = new String[] {"orderId", "dtlId", "channelId"};
	}

	public String getTableName() {
		return "BOMWEB_SUBSCRIBED_CHANNEL";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getChannelId() {
		return this.channelId != null ? this.channelId.toString() : null;
	}

	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setChannelId(String pChannelId) {
		this.channelId = new OraNumber(pChannelId);
	}

	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}
}
