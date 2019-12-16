package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ServiceCallPlanDetailLtsDTO  extends ObjectActionBaseDTO {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5159453950126745405L;
	
	private String ioInd; // BOMWEB_ORDER_CALL_PLAN.IO_IND
	private String planCd; // BOMWEB_ORDER_CALL_PLAN.PLAN_CD
	private String planType; // BOMWEB_ORDER_CALL_PLAN.PLAN_TYPE
	private String planHolderType; // BOMWEB_ORDER_CALL_PLAN.PLAN_HOLDER_TYPE
	private String planHolder; // BOMWEB_ORDER_CALL_PLAN.PLAN_HOLDER
	private String dca; // BOMWEB_ORDER_CALL_PLAN.DCA
	private String itemId; // BOMWEB_ORDER_CALL_PLAN.ITEM_ID
	private String contractMonth; // BOMWEB_ORDER_CALL_PLAN.CONTRACT_MONTH
	private String effStartDate = null; // BOMWEB_ORDER_CALL_PLAN.EFF_START_DATE
	private String effEndDate = null; // BOMWEB_ORDER_CALL_PLAN.EFF_END_DATE
	private String penaltyHandling; //BOMWEB_ORDER_CALL_PLAN.PENALTY_HANDLING
	private String chgDcaInd; //BOMWEB_ORDER_CALL_PLAN.CHG_DCA_IND
	private String newDca; //BOMWEB_ORDER_CALL_PLAN.NEW_DCA
	private String contractStartDate = null; // BOMWEB_ORDER_CALL_PLAN.CONTRACT_START_DATE
	private String contractEndDate = null; // BOMWEB_ORDER_CALL_PLAN.CONTRACT_END_DATE
	private String planCharge; // BOMWEB_ORDER_CALL_PLAN.PLAN_CHARGE

	public String getIoInd() {
		return ioInd;
	}
	public void setIoInd(String ioInd) {
		this.ioInd = ioInd;
	}
	public String getPlanCd() {
		return planCd;
	}
	public void setPlanCd(String planCd) {
		this.planCd = planCd;
	}
	public String getPlanType() {
		return planType;
	}
	public void setPlanType(String planType) {
		this.planType = planType;
	}
	public String getPlanHolderType() {
		return planHolderType;
	}
	public void setPlanHolderType(String planHolderType) {
		this.planHolderType = planHolderType;
	}
	public String getPlanHolder() {
		return planHolder;
	}
	public void setPlanHolder(String planHolder) {
		this.planHolder = planHolder;
	}
	public String getDca() {
		return dca;
	}
	public void setDca(String dca) {
		this.dca = dca;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getContractMonth() {
		return contractMonth;
	}
	public void setContractMonth(String contractMonth) {
		this.contractMonth = contractMonth;
	}
	public String getEffStartDate() {
		return effStartDate;
	}
	public void setEffStartDate(String effStartDate) {
		this.effStartDate = effStartDate;
	}
	public String getEffEndDate() {
		return effEndDate;
	}
	public void setEffEndDate(String effEndDate) {
		this.effEndDate = effEndDate;
	}
	public String getPenaltyHandling() {
		return penaltyHandling;
	}
	public void setPenaltyHandling(String penaltyHandling) {
		this.penaltyHandling = penaltyHandling;
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
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getPlanCharge() {
		return planCharge;
	}
	public void setPlanCharge(String planCharge) {
		this.planCharge = planCharge;
	}
	
}
