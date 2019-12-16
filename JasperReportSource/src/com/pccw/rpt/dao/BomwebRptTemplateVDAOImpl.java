package com.pccw.rpt.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraCLOB;
import com.pccw.util.db.stringOracleType.OraNumber;

public class BomwebRptTemplateVDAOImpl extends DaoBaseImpl implements BomwebRptTemplateVDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1749257288096072275L;
	/**
	 * 
	 */

	private String rptName; // BOMWEB_RPT_TEMPLATE_V.RPT_NAME
	private OraNumber itemId; // BOMWEB_RPT_TEMPLATE_V.ITEM_ID
	private String attribute; // BOMWEB_RPT_TEMPLATE_V.ATTRIBUTE
	private String language; // BOMWEB_RPT_TEMPLATE_V.LANGUAGE
	private OraCLOB contents; // BOMWEB_RPT_TEMPLATE_V.CONTENTS
	private String imgResource; // BOMWEB_RPT_TEMPLATE_V.IMG_RESOURCE

	public BomwebRptTemplateVDAOImpl() {
		primaryKeyFields = new String[] { "rptName", "itemId", "attribute", "language" };
		this.addExcludeColumn("oracleRowID");
	}

	public String getTableName() {
		return "BOMWEB_RPT_TEMPLATE_V";
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#getRptName()
	 */
	@Override
	public String getRptName() {
		return this.rptName;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#getAttribute()
	 */
	@Override
	public String getAttribute() {
		return this.attribute;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#getLanguage()
	 */
	@Override
	public String getLanguage() {
		return this.language;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#getContents()
	 */
	@Override
	public String getContents() {
		return this.contents != null ? this.contents.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#getImgResource()
	 */
	@Override
	public String getImgResource() {
		return this.imgResource;
	}

	
	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#setRptName(java.lang.String)
	 */
	@Override
	public void setRptName(String pRptName) {
		this.rptName = pRptName;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#setAttribute(java.lang.String)
	 */
	@Override
	public void setAttribute(String pAttribute) {
		this.attribute = pAttribute;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#setLanguage(java.lang.String)
	 */
	@Override
	public void setLanguage(String pLanguage) {
		this.language = pLanguage;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#setContents(java.lang.String)
	 */
	@Override
	public void setContents(String pContents) {
		this.contents = new OraCLOB(pContents);
	}
	
	@Override
	public void setImgResource(String pImgResource) {
		this.imgResource = pImgResource;
	}

	@Override
	public String getItemId() {
		return this.itemId == null ? null : this.itemId.toString();
	}

	@Override
	public void setItemId(String pItemId) {
		this.itemId = new OraNumber(pItemId);
	}
}
