package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ImsOfferDetailDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 7085840424968913639L;
	
	private String orderId; // BOMWEB_SUBC_OFFER_IMS.ORDER_ID
	private OraNumber offerId; // BOMWEB_SUBC_OFFER_IMS.OFFER_ID
	private OraNumber offerSubId; // BOMWEB_SUBC_OFFER_IMS.OFFER_SUB_ID
	private OraNumber productId; // BOMWEB_SUBC_OFFER_IMS.PRODUCT_ID
	private OraNumber programId; // BOMWEB_SUBC_OFFER_IMS.PROGRAM_ID
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_SUBC_OFFER_IMS.CREATE_DATE
	private String createBy; // BOMWEB_SUBC_OFFER_IMS.CREATE_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_SUBC_OFFER_IMS.LAST_UPD_DATE
	private String lastUpdBy; // BOMWEB_SUBC_OFFER_IMS.LAST_UPD_BY
	private OraNumber dtlId; // BOMWEB_SUBC_OFFER_IMS.DTL_ID
	private String discIncInd;	// BOMWEB_SUBC_OFFER_IMS.DISC_INC_IND
	private String itemId;	// BOMWEB_SUBC_OFFER_IMS.ITEM_ID
	private String ioInd;	// BOMWEB_SUBC_OFFER_IMS.IO_IND
	private String penaltyWaiveInd;	// BOMWEB_SUBC_OFFER_IMS.PENALTY_WAIVE_IND
	private String penaltyHandling; // BOMWEB_SUBC_OFFER_IMS.PENALTY_HANDLING
	private String itemType; // BOMWEB_SUBC_OFFER_IMS.ITEM_TYPE

	
	public ImsOfferDetailDAO() {
		primaryKeyFields = new String[] {"orderId", "dtlId", "offerId"};
	}

	public String getTableName() {
		return "BOMWEB_SUBC_OFFER_IMS";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getOfferId() {
		return this.offerId != null ? this.offerId.toString() : null;
	}

	public String getOfferSubId() {
		return this.offerSubId != null ? this.offerSubId.toString() : null;
	}

	public String getProductId() {
		return this.productId != null ? this.productId.toString() : null;
	}

	public String getProgramId() {
		return this.programId != null ? this.programId.toString() : null;
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

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setOfferId(String pOfferId) {
		this.offerId = new OraNumber(pOfferId);
	}

	public void setOfferSubId(String pOfferSubId) {
		this.offerSubId = new OraNumber(pOfferSubId);
	}

	public void setProductId(String pProductId) {
		this.productId = new OraNumber(pProductId);
	}

	public void setProgramId(String pProgramId) {
		this.programId = new OraNumber(pProgramId);
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

	public String getDiscIncInd() {
		return this.discIncInd;
	}

	public void setDiscIncInd(String pDiscIncInd) {
		this.discIncInd = pDiscIncInd;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getIoInd() {
		return ioInd;
	}

	public void setIoInd(String ioInd) {
		this.ioInd = ioInd;
	}

	public String getPenaltyWaiveInd() {
		return penaltyWaiveInd;
	}

	public void setPenaltyWaiveInd(String penaltyWaiveInd) {
		this.penaltyWaiveInd = penaltyWaiveInd;
	}

	public String getPenaltyHandling() {
		return penaltyHandling;
	}

	public void setPenaltyHandling(String penaltyHandling) {
		this.penaltyHandling = penaltyHandling;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
}
