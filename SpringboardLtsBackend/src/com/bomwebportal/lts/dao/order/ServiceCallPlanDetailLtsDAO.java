package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ServiceCallPlanDetailLtsDAO extends DaoBaseImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1314722688720044553L;
	
	private String orderId; // BOMWEB_ORDER_CALL_PLAN.ORDER_ID
	private OraNumber dtlId; // BOMWEB_ORDER_CALL_PLAN.DTL_ID
	private String ioInd; // BOMWEB_ORDER_CALL_PLAN.IO_IND
	private String planCd; // BOMWEB_ORDER_CALL_PLAN.PLAN_CD
	private String planType; // BOMWEB_ORDER_CALL_PLAN.PLAN_TYPE
	private String planHolderType; // BOMWEB_ORDER_CALL_PLAN.PLAN_HOLDER_TYPE
	private String planHolder; // BOMWEB_ORDER_CALL_PLAN.PLAN_HOLDER
	private String dca; // BOMWEB_ORDER_CALL_PLAN.DCA
	private OraNumber itemId; // BOMWEB_ORDER_CALL_PLAN.ITEM_ID
	private OraNumber contractMonth; // BOMWEB_ORDER_CALL_PLAN.CONTRACT_MONTH
	private OraDate effStartDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER_CALL_PLAN.EFF_START_DATE
	private OraDate effEndDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER_CALL_PLAN.EFF_END_DATE
	private String createBy; // BOMWEB_ORDER_CALL_PLAN.CREATE_BY
	private OraDate createDate = new OraDateCreateDate();  // BOMWEB_ORDER_CALL_PLAN.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ORDER_CALL_PLAN.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ORDER_CALL_PLAN.LAST_UPD_DATE
	private String penaltyHandling; //BOMWEB_ORDER_CALL_PLAN.PENALTY_HANDLING
	private String chgDcaInd; //BOMWEB_ORDER_CALL_PLAN.CHG_DCA_IND
	private String newDca; //BOMWEB_ORDER_CALL_PLAN.NEW_DCA
	private OraDate contractStartDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER_CALL_PLAN.CONTRACT_START_DATE
	private OraDate contractEndDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER_CALL_PLAN.CONTRACT_END_DATE

	public ServiceCallPlanDetailLtsDAO() {
		primaryKeyFields = new String[] { "orderId", "dtlId", "planCd", "ioInd", "effStartDate" };
	}

	public String getTableName() {
		return "BOMWEB_ORDER_CALL_PLAN";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getIoInd() {
		return this.ioInd;
	}

	public String getPlanCd() {
		return this.planCd;
	}

	public String getPlanType() {
		return this.planType;
	}

	public String getPlanHolderType() {
		return this.planHolderType;
	}

	public String getPlanHolder() {
		return this.planHolder;
	}

	public String getDca() {
		return this.dca;
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

	public String getItemId() {
		return this.itemId != null ? this.itemId.toString() : null;
	}

	public String getContractMonth() {
		return this.contractMonth != null ? this.contractMonth.toString()
				: null;
	}

	public String getEffStartDate() {
		return this.effStartDate != null ? this.effStartDate.toString() : null;
	}

	public String getEffEndDate() {
		return this.effEndDate != null ? this.effEndDate.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getEffStartDateORACLE() {
		return this.effStartDate;
	}

	public OraDate getEffEndDateORACLE() {
		return this.effEndDate;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public String getPenaltyHandling() { 
		return this.penaltyHandling; 
	}
	
	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setIoInd(String pIoInd) {
		this.ioInd = pIoInd;
	}

	public void setPlanCd(String pPlanCd) {
		this.planCd = pPlanCd;
	}

	public void setPlanType(String pPlanType) {
		this.planType = pPlanType;
	}

	public void setPlanHolderType(String pPlanHolderType) {
		this.planHolderType = pPlanHolderType;
	}

	public void setPlanHolder(String pPlanHolder) {
		this.planHolder = pPlanHolder;
	}

	public void setDca(String pDca) {
		this.dca = pDca;
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

	public void setItemId(String pItemId) {
		this.itemId = new OraNumber(pItemId);
	}

	public void setContractMonth(String pContractMonth) {
		this.contractMonth = new OraNumber(pContractMonth);
	}

	public void setEffStartDate(String pEffStartDate) {
		this.effStartDate = new OraDateYYYYMMDDHH24MISS(pEffStartDate);
	}

	public void setEffEndDate(String pEffEndDate) {
		this.effEndDate = new OraDateYYYYMMDDHH24MISS(pEffEndDate);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
	
	public void setPenaltyHandling(String pPenaltyHandling) { 
		this.penaltyHandling = pPenaltyHandling; 
	}

	public String getChgDcaInd() {
		return chgDcaInd;
	}

	public void setChgDcaInd(String chgDcaInd) {
		this.chgDcaInd = chgDcaInd;
	}

	public String getNewDca() {
		return newDca;
	}

	public void setNewDca(String newDca) {
		this.newDca = newDca;
	}

	public String getContractStartDate() {
		return this.contractStartDate != null ? this.contractStartDate.toString() : null;
	}

	public String getContractEndDate() {
		return this.contractEndDate != null ? this.contractEndDate.toString() : null;
	}

	public OraDate getContractStartDateORACLE() {
		return this.contractStartDate;
	}

	public OraDate getContractEndDateORACLE() {
		return this.contractEndDate;
	}
	
	public void setContractStartDate(String pContractStartDate) {
		this.contractStartDate = new OraDateYYYYMMDDHH24MISS(pContractStartDate);
	}

	public void setContractEndDate(String pContractEndDate) {
		this.contractEndDate = new OraDateYYYYMMDDHH24MISS(pContractEndDate);
	}
	
}
