package com.pccw.rpt.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraCLOB;
import com.pccw.util.db.stringOracleType.OraNumber;

public class BomwebRptTemplateDAOImpl extends DaoBaseImpl implements BomwebRptTemplateDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2971744243187153457L;

	private String rptName; // BOMWEB_RPT_TEMPLATE.RPT_NAME
	private OraNumber itemId; // BOMWEB_RPT_TEMPLATE.RPT_NAME
	private String attribute; // BOMWEB_RPT_TEMPLATE.ATTRIBUTE
	private String language; // BOMWEB_RPT_TEMPLATE.LANGUAGE
	private OraCLOB contents; // BOMWEB_RPT_TEMPLATE.CONTENTS
	private String imgResource; // BOMWEB_RPT_TEMPLATE.IMG_RESOURCE
	private String refRptName; // BOMWEB_RPT_TEMPLATE.REF_RPT_NAME
	private OraNumber refItemId; // BOMWEB_RPT_TEMPLATE.REF_ITEM_ID
	private String refAttribute; // BOMWEB_RPT_TEMPLATE.REF_ATTRIBUTE

	public BomwebRptTemplateDAOImpl() {
		primaryKeyFields = new String[] { "rptName", "itemId", "attribute", "language" };
	}

	public String getTableName() {
		return "BOMWEB_RPT_TEMPLATE";
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
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#getRefRptName()
	 */
	@Override
	public String getRefRptName() {
		return this.refRptName;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#getRefAttribute()
	 */
	@Override
	public String getRefAttribute() {
		return this.refAttribute;
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
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#setRefRptName(java.lang.String)
	 */
	@Override
	public void setRefRptName(String pRefRptName) {
		this.refRptName = pRefRptName;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.BomwebRptTemplateDAO#setRefAttribute(java.lang.String)
	 */
	@Override
	public void setRefAttribute(String pRefAttribute) {
		this.refAttribute = pRefAttribute;
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
	
	@Override
	public String getRefItemId() {
		return this.refItemId == null ? null : this.refItemId.toString();
	}

	@Override
	public void setRefItemId(String pRefItemId) {
		this.refItemId = new OraNumber(pRefItemId);
	}

}
