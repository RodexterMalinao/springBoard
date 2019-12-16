package com.pccw.rpt.dao;

import com.pccw.util.db.DaoBase;

public interface WItemDisplayLkupDAO extends DaoBase {

	public String getLocale();

	public String getDisplayType();

	public String getDescription();

	public String getImagePath();

	public String getHtml2();

//	public String getCreateBy();
//
//	public String getLastUpdBy();

	public String getItemId();

	public String getHtml();

//	public String getCreateDate();
//
//	public String getLastUpdDate();

//	public OraDate getCreateDateORACLE();
//
//	public OraDate getLastUpdDateORACLE();

	public void setLocale(String pLocale);

	public void setDisplayType(String pDisplayType);

	public void setDescription(String pDescription);

	public void setImagePath(String pImagePath);

	public void setHtml2(String pHtml2);

//	public void setCreateBy(String pCreateBy);
//
//	public void setLastUpdBy(String pLastUpdBy);

	public void setItemId(String pItemId);

	public void setHtml(String pHtml);

//	public void setCreateDate(String pCreateDate);
//
//	public void setLastUpdDate(String pLastUpdDate);

}