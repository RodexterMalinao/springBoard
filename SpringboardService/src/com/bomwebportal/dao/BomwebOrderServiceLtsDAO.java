package com.bomwebportal.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;

public interface BomwebOrderServiceLtsDAO extends DaoBase {

	public abstract String getOrderId();

	public abstract String getDatCd();

	public abstract String getTwoNInd();

	public abstract String getDuplexInd();

	public abstract String getRedeemPremiumInd();

	public abstract String getPendingApprovalCd();

	public abstract String getPaperBillInd();

	public abstract String getCallPlanDowngradeInd();

	public abstract String getCreateBy();

	public abstract String getLastUpdBy();

	public abstract String getCancelVasInd();

	public abstract String getFrozenExchInd();

	public abstract String getReservedDnInd();

	public abstract String getDtlId();

	public abstract String getCreateDate();

	public abstract String getLastUpdDate();

	public abstract OraDate getCreateDateORACLE();

	public abstract OraDate getLastUpdDateORACLE();

	public abstract void setOrderId(String pOrderId);

	public abstract void setDatCd(String pDatCd);

	public abstract void setTwoNInd(String pTwoNInd);

	public abstract void setDuplexInd(String pDuplexInd);

	public abstract void setRedeemPremiumInd(String pRedeemPremiumInd);

	public abstract void setPendingApprovalCd(String pPendingApprovalCd);

	public abstract void setPaperBillInd(String pPaperBillInd);

	public abstract void setCallPlanDowngradeInd(String pCallPlanDowngradeInd);

	public abstract void setCreateBy(String pCreateBy);

	public abstract void setLastUpdBy(String pLastUpdBy);

	public abstract void setCancelVasInd(String pCancelVasInd);

	public abstract void setFrozenExchInd(String pFrozenExchInd);

	public abstract void setReservedDnInd(String pReservedDnInd);

	public abstract void setDtlId(String pDtlId);

	public abstract void setCreateDate(String pCreateDate);

	public abstract void setLastUpdDate(String pLastUpdDate);

}