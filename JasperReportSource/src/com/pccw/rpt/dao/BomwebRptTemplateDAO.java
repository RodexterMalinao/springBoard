package com.pccw.rpt.dao;

import com.pccw.util.db.DaoBase;

public interface BomwebRptTemplateDAO extends DaoBase {

	public String getRptName();

	public String getItemId();

	public String getAttribute();

	public String getLanguage();

	public String getRefRptName();

	public String getRefItemId();

	public String getRefAttribute();

	public String getContents();
	
	public String getImgResource();

	public void setRptName(String pRptName);

	public void setItemId(String pItemId);

	public void setAttribute(String pAttribute);

	public void setLanguage(String pLanguage);

	public void setRefRptName(String pRefRptName);

	public void setRefAttribute(String pRefAttribute);

	public void setRefItemId(String pRefItemId);

	public void setContents(String pContents);

	public void setImgResource(String pImgResource);
}