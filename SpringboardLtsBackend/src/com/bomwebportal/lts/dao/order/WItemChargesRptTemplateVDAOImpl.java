package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseReadonlyImpl;
import com.pccw.util.db.stringOracleType.OraCLOB;
import com.pccw.util.db.stringOracleType.OraNumber;

public class WItemChargesRptTemplateVDAOImpl extends DaoBaseReadonlyImpl implements WItemChargesRptTemplateVDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5180227435029497030L;

	private OraNumber itemId; // W_ITEM_CHARGES_RPT_TEMPLATE_V.ITEM_ID
	private String lob; // W_ITEM_CHARGES_RPT_TEMPLATE_V.LOB
	private String itemType; // W_ITEM_CHARGES_RPT_TEMPLATE_V.ITEM_TYPE
	private String description; // W_ITEM_CHARGES_RPT_TEMPLATE_V.DESCRIPTION
	private String locale; // W_ITEM_CHARGES_RPT_TEMPLATE_V.LOCALE
	private OraCLOB chargeDesc; // W_ITEM_CHARGES_RPT_TEMPLATE_V.CHARGE_DESC
	private OraCLOB charge; // W_ITEM_CHARGES_RPT_TEMPLATE_V.CHARGE

	public WItemChargesRptTemplateVDAOImpl() {
		primaryKeyFields = new String[] {};
		this.addExcludeColumn("oracleRowID");
	}

	public String getTableName() {
		return "W_ITEM_CHARGES_RPT_TEMPLATE_V";
	}

	public String getLob() {
		return this.lob;
	}

	public String getItemType() {
		return this.itemType;
	}

	public String getDescription() {
		return this.description;
	}

	public String getLocale() {
		return this.locale;
	}

	public String getItemId() {
		return this.itemId != null ? this.itemId.toString() : null;
	}

	public String getChargeDesc() {
		return this.chargeDesc != null ? this.chargeDesc.toString() : null;
	}

	public String getCharge() {
		return this.charge != null ? this.charge.toString() : null;
	}

	public void setLob(String pLob) {
		this.lob = pLob;
	}

	public void setItemType(String pItemType) {
		this.itemType = pItemType;
	}

	public void setDescription(String pDescription) {
		this.description = pDescription;
	}

	public void setLocale(String pLocale) {
		this.locale = pLocale;
	}

	public void setItemId(String pItemId) {
		this.itemId = new OraNumber(pItemId);
	}

	public void setChargeDesc(String pChargeDesc) {
		this.chargeDesc = new OraCLOB(pChargeDesc);
	}

	public void setCharge(String pCharge) {
		this.charge = new OraCLOB(pCharge);
	}
}