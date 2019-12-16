package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBase;

public interface WItemChargesRptTemplateVDAO extends DaoBase {

	public abstract String getLob();

	public abstract String getItemType();

	public abstract String getDescription();

	public abstract String getLocale();

	public abstract String getItemId();

	public abstract String getChargeDesc();

	public abstract String getCharge();

	public abstract void setLob(String pLob);

	public abstract void setItemType(String pItemType);

	public abstract void setDescription(String pDescription);

	public abstract void setLocale(String pLocale);

	public abstract void setItemId(String pItemId);

	public abstract void setChargeDesc(String pChargeDesc);

	public abstract void setCharge(String pCharge);

}