package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ItemAttributeLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 7924466562981603690L;
	
	private String orderId; // BOMWEB_SUBC_ITEM_LTS_ATTB.ORDER_ID
	private OraNumber dtlId; // BOMWEB_SUBC_ITEM_LTS_ATTB.DTL_ID
	private OraNumber srvItemSeq; // BOMWEB_SUBC_ITEM_LTS_ATTB.SRV_ITEM_SEQ
	private String attbCd; // BOMWEB_SUBC_ITEM_LTS_ATTB.ATTB_CD
	private String attbValue; // BOMWEB_SUBC_ITEM_LTS_ATTB.ATTB_VALUE
	private OraNumber comptId; // BOMWEB_SUBC_ITEM_LTS_ATTB.COMPT_ID
	private OraNumber offerId; // BOMWEB_SUBC_ITEM_LTS_ATTB.OFFER_ID
	private OraNumber offerSubId ; // BOMWEB_SUBC_ITEM_LTS_ATTB.OFFER_SUB_ID
	private OraNumber productId; // BOMWEB_SUBC_ITEM_LTS_ATTB.PRODUCT_ID
	private String createBy; // BOMWEB_SUBC_ITEM_LTS_ATTB.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_SUBC_ITEM_LTS_ATTB.CREATE_DATE
	private String lastUpdBy; // BOMWEB_SUBC_ITEM_LTS_ATTB.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_SUBC_ITEM_LTS_ATTB.LAST_UPD_DATE

	
	public ItemAttributeLtsDAO() {
		primaryKeyFields = new String[] {"orderId", "dtlId", "srvItemSeq", "attbCd"};
	}

	public String getTableName() {
		return "BOMWEB_SUBC_ITEM_LTS_ATTB";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getAttbCd() {
		return this.attbCd;
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

	public String getSrvItemSeq() {
		return this.srvItemSeq != null ? this.srvItemSeq.toString() : null;
	}

	public String getComptId() {
		return this.comptId != null ? this.comptId.toString() : null;
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

	public void setAttbCd(String pAttbCd) {
		this.attbCd = pAttbCd;
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

	public void setSrvItemSeq(String pSrvItemSeq) {
		this.srvItemSeq = new OraNumber(pSrvItemSeq);
	}

	public void setComptId(String pComptId) {
		this.comptId = new OraNumber(pComptId);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getOfferId() {
		return this.offerId != null ? this.offerId.toString() : null;
	}

	public void setOfferId(String pOfferId) {
		this.offerId = new OraNumber(pOfferId);
	}

	public String getOfferSubId() {
		return this.offerSubId != null ? this.offerSubId.toString() : null;
	}

	public void setOfferSubId(String pOfferSubId) {
		this.offerSubId = new OraNumber(pOfferSubId);
	}

	public String getProductId() {
		return this.productId != null ? this.productId.toString() : null;
	}

	public void setProductId(String pProductId) {
		this.productId = new OraNumber(pProductId);
	}
}
