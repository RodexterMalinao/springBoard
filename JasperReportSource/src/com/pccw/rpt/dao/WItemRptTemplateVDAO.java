package com.pccw.rpt.dao;

import com.pccw.util.db.DaoBase;
import com.pccw.util.db.stringOracleType.OraDate;

public interface WItemRptTemplateVDAO extends DaoBase {

	public String getLob();

	public String getItemType();

	public String getDescription();

	public String getMdoInd();

	public String getDisplayType();

	public String getLocale();

	public String getImagePath();

	public String getFixTermRate();

	public String getMthToMthRate();

	public String getOnetime();

	public String getItemId();

	public String getHtml();

	public String getPrcEffStartDate();

	public String getPrcEffEndDate();

	public OraDate getPrcEffStartDateORACLE();

	public OraDate getPrcEffEndDateORACLE();

	public void setLob(String pLob);

	public void setItemType(String pItemType);

	public void setDescription(String pDescription);

	public void setMdoInd(String pMdoInd);

	public void setDisplayType(String pDisplayType);

	public void setLocale(String pLocale);

	public void setImagePath(String pImagePath);

	public void setFixTermRate(String pFixTermRate);

	public void setMthToMthRate(String pMthToMthRate);

	public void setOnetime(String pOnetime);

	public void setItemId(String pItemId);

	public void setHtml(String pHtml);

	public void setPrcEffStartDate(String pPrcEffStartDate);

	public void setPrcEffEndDate(String pPrcEffEndDate);

	public String getPenaltyAmt();

	public void setPenaltyAmt(String penaltyAmt);

}