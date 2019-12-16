package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ItemLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -4046097963196590097L;
	
	private String orderId; // BOMWEB_SUBC_ITEM_LTS.ORDER_ID
	private OraNumber dtlId; // BOMWEB_SUBC_ITEM_LTS.DTL_ID
	private OraNumber srvItemSeq; // BOMWEB_SUBC_ITEM_LTS.SRV_ITEM_SEQ
	private String ioInd; // BOMWEB_SUBC_ITEM_LTS.IO_IND
	private OraNumber basketId; // BOMWEB_SUBC_ITEM_LTS.BASKET_ID
	private OraNumber itemId; // BOMWEB_SUBC_ITEM_LTS.ITEM_ID
	private String itemType; // BOMWEB_SUBC_ITEM_LTS.ITEM_TYPE
	private OraNumber relatedItemId; // BOMWEB_SUBC_ITEM_LTS.RELATED_ITEM_ID
	private String relatedItemType; // BOMWEB_SUBC_ITEM_LTS.RELATED_ITEM_TYPE
	private String membershipId; // BOMWEB_SUBC_ITEM_LTS.MEMBERSHIP_ID
	private OraDate existStartDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_SUBC_ITEM_LTS.EXIST_START_DATE
	private OraDate existEndDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_SUBC_ITEM_LTS.EXIST_END_DATE
	private OraNumber existGrossPrice; // BOMWEB_SUBC_ITEM_LTS.EXIST_GROSS_PRICE
	private OraNumber existEffectivePrice; // BOMWEB_SUBC_ITEM_LTS.EXIST_EFFECTIVE_PRICE
	private String createBy; // BOMWEB_SUBC_ITEM_LTS.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_SUBC_ITEM_LTS.CREATE_DATE
	private String lastUpdBy; // BOMWEB_SUBC_ITEM_LTS.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_SUBC_ITEM_LTS.LAST_UPD_DATE
	private OraNumber itemQty; // BOMWEB_SUBC_ITEM_LTS.ITEM_QTY
	private String penaltyWaiveInd; // BOMWEB_SUBC_ITEM_LTS.PENALTY_WAIVE_IND
	private String paidVasInd;	// BOMWEB_SUBC_ITEM_LTS.PAID_VAS_IND
	private String penaltyHandling; // BOMWEB_SUBC_ITEM_LTS.PENALTY_HANDLING
	private String profileItemType; // BOMWEB_SUBC_ITEM_LTS.PROFILE_ITEM_TYPE
	private String bundlePcdOrderId; // BOMWEB_SUBC_ITEM_LTS.BUNDLE_PCD_ORDER_ID
	private String pcdBundleFree; // BOWMWEB_SUBC_ITEM_LTS.PCD_BUNDLE_FREE 
	private OraNumber earnedClubPts; // BOMWEB_SUBC_ITEM_LTS.EARNED_CLUB_PTS 
	private String forceRetain; //BOMWEB_SUBC_ITEM_LTS.FORCE_RETAIN
	private String afType; //BOMWEB_SUBC_ITEM_LTS.AF_TYPE
	
	
	public ItemLtsDAO() {
		primaryKeyFields = new String[] { "orderId", "dtlId", "srvItemSeq" };
	}

	public String getTableName() {
		return "BOMWEB_SUBC_ITEM_LTS";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getIoInd() {
		return this.ioInd;
	}

	public String getItemType() {
		return this.itemType;
	}

	public String getRelatedItemType() {
		return this.relatedItemType;
	}

	public String getMembershipId() {
		return this.membershipId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getPenaltyWaiveInd() {
		return this.penaltyWaiveInd;
	}

	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}

	public String getSrvItemSeq() {
		return this.srvItemSeq != null ? this.srvItemSeq.toString() : null;
	}

	public String getBasketId() {
		return this.basketId != null ? this.basketId.toString() : null;
	}

	public String getItemId() {
		return this.itemId != null ? this.itemId.toString() : null;
	}

	public String getRelatedItemId() {
		return this.relatedItemId != null ? this.relatedItemId.toString()
				: null;
	}

	public String getExistGrossPrice() {
		return this.existGrossPrice != null ? this.existGrossPrice.toString()
				: null;
	}

	public String getExistEffectivePrice() {
		return this.existEffectivePrice != null ? this.existEffectivePrice
				.toString() : null;
	}

	public String getItemQty() {
		return this.itemQty != null ? this.itemQty.toString() : null;
	}

	public String getExistStartDate() {
		return this.existStartDate != null ? this.existStartDate.toString()
				: null;
	}

	public String getExistEndDate() {
		return this.existEndDate != null ? this.existEndDate.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getExistStartDateORACLE() {
		return this.existStartDate;
	}

	public OraDate getExistEndDateORACLE() {
		return this.existEndDate;
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

	public void setIoInd(String pIoInd) {
		this.ioInd = pIoInd;
	}

	public void setItemType(String pItemType) {
		this.itemType = pItemType;
	}

	public void setRelatedItemType(String pRelatedItemType) {
		this.relatedItemType = pRelatedItemType;
	}

	public void setMembershipId(String pMembershipId) {
		this.membershipId = pMembershipId;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setPenaltyWaiveInd(String pPenaltyWaiveInd) {
		this.penaltyWaiveInd = pPenaltyWaiveInd;
	}

	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	public void setSrvItemSeq(String pSrvItemSeq) {
		this.srvItemSeq = new OraNumber(pSrvItemSeq);
	}

	public void setBasketId(String pBasketId) {
		this.basketId = new OraNumber(pBasketId);
	}

	public void setItemId(String pItemId) {
		this.itemId = new OraNumber(pItemId);
	}

	public void setRelatedItemId(String pRelatedItemId) {
		this.relatedItemId = new OraNumber(pRelatedItemId);
	}

	public void setExistGrossPrice(String pExistGrossPrice) {
		this.existGrossPrice = new OraNumber(pExistGrossPrice);
	}

	public void setExistEffectivePrice(String pExistEffectivePrice) {
		this.existEffectivePrice = new OraNumber(pExistEffectivePrice);
	}

	public void setItemQty(String pItemQty) {
		this.itemQty = new OraNumber(pItemQty);
	}

	public void setExistStartDate(String pExistStartDate) {
		this.existStartDate = new OraDateYYYYMMDDHH24MISS(pExistStartDate);
	}

	public void setExistEndDate(String pExistEndDate) {
		this.existEndDate = new OraDateYYYYMMDDHH24MISS(pExistEndDate);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getPaidVasInd() {
		return this.paidVasInd;
	}

	public void setPaidVasInd(String pPaidVasInd) {
		this.paidVasInd = pPaidVasInd;
	}

	public String getPenaltyHandling() {
		return penaltyHandling;
	}

	public void setPenaltyHandling(String penaltyHandling) {
		this.penaltyHandling = penaltyHandling;
	}

	public String getProfileItemType() {
		return profileItemType;
	}

	public void setProfileItemType(String profileItemType) {
		this.profileItemType = profileItemType;
	}

	public String getBundlePcdOrderId() {
		return bundlePcdOrderId;
	}

	public void setBundlePcdOrderId(String bundlePcdOrderId) {
		this.bundlePcdOrderId = bundlePcdOrderId;
	}

	public String getPcdBundleFree() {
		return pcdBundleFree;
	}

	public void setPcdBundleFree(String pcdBundleFree) {
		this.pcdBundleFree = pcdBundleFree;
	}

	public String getEarnedClubPts() {
		return this.earnedClubPts != null ? this.earnedClubPts.toString() : null;
	}

	public void setEarnedClubPts(String earnedClubPts) {
		this.earnedClubPts = new OraNumber(earnedClubPts);
	}

	public String getForceRetain() {
		return forceRetain;
	}

	public void setForceRetain(String forceRetain) {
		this.forceRetain = forceRetain;
	}

	public String getAfType() {
		return afType;
	}

	public void setAfType(String afType) {
		this.afType = afType;
	}
	
}
