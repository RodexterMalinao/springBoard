package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class OrderLtsAmendDetailDAO extends DaoBaseImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5990339706812133835L;
	
	private String orderId; // BOMWEB_ORDER_LTS_AMEND_DTL.ORDER_ID
	private OraNumber dtlId; // BOMWEB_ORDER_LTS_AMEND_DTL.DTL_ID
	private OraNumber batchSeq; // BOMWEB_ORDER_LTS_AMEND_DTL.BATCH_SEQ	
	private OraNumber itemSeq; // BOMWEB_ORDER_LTS_AMEND_DTL.ITEM_SEQ
	private String amendCat; // BOMWEB_ORDER_LTS_AMEND_DTL.AMEND_CAT
	private String amendSubCat; // BOMWEB_ORDER_LTS_AMEND_DTL.AMEND_SUB_CAT
	private String amendItem; // BOMWEB_ORDER_LTS_AMEND_DTL.AMEND_ITEM
	private String amendValue; // BOMWEB_ORDER_LTS_AMEND_DTL.AMEND_VALUE	
	private String createBy; // BOMWEB_ORDER_LTS_AMEND_DTL.CREATE_BY	
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER_LTS_AMEND_DTL.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_LTS_AMEND_DTL.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateCreateDate(); // BOMWEB_ORDER_LTS_AMEND_DTL.LAST_UPD_DATE
	private OraNumber itemSubSeq; // BOMWEB_ORDER_LTS_AMEND_DTL.ITEM_SUB_SEQ
	
	public OrderLtsAmendDetailDAO() {
		primaryKeyFields = new String[] {"orderId", "dtlId"};
	}

	public String getTableName() {
		return "BOMWEB_ORDER_LTS_AMEND_DTL";
	}
	
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}


	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}
	
	/**
	 * @return the batchSeq
	 */
	public String getBatchSeq() {
		return this.batchSeq != null ? this.batchSeq.toString() : null;
	}
	/**
	 * @param batchSeq the batchSeq to set
	 */
	public void setBatchSeq(String batchSeq) {
		this.batchSeq = new OraNumber(batchSeq);
	}
	/**
	 * @return the itemSeq
	 */
	public String getItemSeq() {
		return this.itemSeq != null ? this.itemSeq.toString() : null;
	}
	/**
	 * @param itemSeq the itemSeq to set
	 */
	public void setItemSeq(String itemSeq) {
		this.itemSeq = new OraNumber(itemSeq);
	}
	/**
	 * @return the amendCat
	 */
	public String getAmendCat() {
		return amendCat;
	}
	/**
	 * @param amendCat the amendCat to set
	 */
	public void setAmendCat(String amendCat) {
		this.amendCat = amendCat;
	}
	/**
	 * @return the amendSubCat
	 */
	public String getAmendSubCat() {
		return amendSubCat;
	}
	/**
	 * @param amendSubCat the amendSubCat to set
	 */
	public void setAmendSubCat(String amendSubCat) {
		this.amendSubCat = amendSubCat;
	}
	/**
	 * @return the amendItem
	 */
	public String getAmendItem() {
		return amendItem;
	}
	/**
	 * @param amendItem the amendItem to set
	 */
	public void setAmendItem(String amendItem) {
		this.amendItem = amendItem;
	}
	/**
	 * @return the amendValue
	 */
	public String getAmendValue() {
		return amendValue;
	}
	/**
	 * @param amendValue the amendValue to set
	 */
	public void setAmendValue(String amendValue) {
		this.amendValue = amendValue;
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


	public String getLastUpdBy() {
		return lastUpdBy;
	}


	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
	
	public String getItemSubSeq() {
		return this.itemSubSeq != null ? this.itemSubSeq.toString() : null;
	}
	
	public void setItemSubSeq(String itemSubSeq) {
		this.itemSubSeq = new OraNumber(itemSubSeq);
	}
	
}
