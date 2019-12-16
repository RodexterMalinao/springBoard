package com.pccw.rpt.dao;

import com.pccw.util.db.DaoBase;

public interface BomwebRptTemplateVDAO extends DaoBase {

	public String getRptName();

	public String getItemId();

	public String getAttribute();

	public String getLanguage();

	public String getContents();

	public String getImgResource();

	public void setRptName(String pRptName);

	public void setAttribute(String pAttribute);

	public void setLanguage(String pLanguage);

	public void setContents(String pContents);

	public void setImgResource(String pImgResource);

	public void setItemId(String pItemId);
}