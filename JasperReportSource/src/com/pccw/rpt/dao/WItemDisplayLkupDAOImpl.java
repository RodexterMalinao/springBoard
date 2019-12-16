package com.pccw.rpt.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraNumber;

public class WItemDisplayLkupDAOImpl extends DaoBaseImpl implements WItemDisplayLkupDAO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5192502641484479938L;
	private OraNumber itemId; // W_ITEM_DISPLAY_LKUP.ITEM_ID
	private String locale; // W_ITEM_DISPLAY_LKUP.LOCALE
	private String displayType; // W_ITEM_DISPLAY_LKUP.DISPLAY_TYPE
	private String description; // W_ITEM_DISPLAY_LKUP.DESCRIPTION
	private String html; // W_ITEM_DISPLAY_LKUP.HTML
	private String imagePath; // W_ITEM_DISPLAY_LKUP.IMAGE_PATH
	private String html2; // W_ITEM_DISPLAY_LKUP.HTML2
//	private String createBy; // W_ITEM_DISPLAY_LKUP.CREATE_BY
//	private OraDate createDate = new OraDateCreateDate(); // W_ITEM_DISPLAY_LKUP.CREATE_DATE
//	private String lastUpdBy; // W_ITEM_DISPLAY_LKUP.LAST_UPD_BY
//	private OraDate lastUpdDate = new OraDateLastUpdDate(); // W_ITEM_DISPLAY_LKUP.LAST_UPD_DATE

	public WItemDisplayLkupDAOImpl() {
		primaryKeyFields = new String[] { "itemId", "locale", "displayType" };
	}

	public String getTableName() {
		return "W_ITEM_DISPLAY_LKUP";
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getLocale()
	 */
	@Override
	public String getLocale() {
		return this.locale;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getDisplayType()
	 */
	@Override
	public String getDisplayType() {
		return this.displayType;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getImagePath()
	 */
	@Override
	public String getImagePath() {
		return this.imagePath;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getHtml2()
	 */
	@Override
	public String getHtml2() {
		return this.html2;
	}

//	/* (non-Javadoc)
//	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getCreateBy()
//	 */
//	@Override
//	public String getCreateBy() {
//		return this.createBy;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getLastUpdBy()
//	 */
//	@Override
//	public String getLastUpdBy() {
//		return this.lastUpdBy;
//	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getItemId()
	 */
	@Override
	public String getItemId() {
		return this.itemId != null ? this.itemId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getHtml()
	 */
	@Override
	public String getHtml() {
		return this.html != null ? this.html.toString() : null;
	}

//	/* (non-Javadoc)
//	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getCreateDate()
//	 */
//	@Override
//	public String getCreateDate() {
//		return this.createDate != null ? this.createDate.toString() : null;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getLastUpdDate()
//	 */
//	@Override
//	public String getLastUpdDate() {
//		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getCreateDateORACLE()
//	 */
//	@Override
//	public OraDate getCreateDateORACLE() {
//		return this.createDate;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#getLastUpdDateORACLE()
//	 */
//	@Override
//	public OraDate getLastUpdDateORACLE() {
//		return this.lastUpdDate;
//	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setLocale(java.lang.String)
	 */
	@Override
	public void setLocale(String pLocale) {
		this.locale = pLocale;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setDisplayType(java.lang.String)
	 */
	@Override
	public void setDisplayType(String pDisplayType) {
		this.displayType = pDisplayType;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String pDescription) {
		this.description = pDescription;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setImagePath(java.lang.String)
	 */
	@Override
	public void setImagePath(String pImagePath) {
		this.imagePath = pImagePath;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setHtml2(java.lang.String)
	 */
	@Override
	public void setHtml2(String pHtml2) {
		this.html2 = pHtml2;
	}

//	/* (non-Javadoc)
//	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setCreateBy(java.lang.String)
//	 */
//	@Override
//	public void setCreateBy(String pCreateBy) {
//		this.createBy = pCreateBy;
//	}
//
//	/* (non-Javadoc)
//	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setLastUpdBy(java.lang.String)
//	 */
//	@Override
//	public void setLastUpdBy(String pLastUpdBy) {
//		this.lastUpdBy = pLastUpdBy;
//	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setItemId(java.lang.String)
	 */
	@Override
	public void setItemId(String pItemId) {
		this.itemId = new OraNumber(pItemId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setHtml(java.lang.String)
	 */
	@Override
	public void setHtml(String pHtml) {
		this.html = pHtml;
	}

//	/* (non-Javadoc)
//	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setCreateDate(java.lang.String)
//	 */
//	@Override
//	public void setCreateDate(String pCreateDate) {
//		this.createDate = new OraDateCreateDate(pCreateDate);
//	}
//
//	/* (non-Javadoc)
//	 * @see com.pccw.rpt.dao.WItemDisplayLkupDAO#setLastUpdDate(java.lang.String)
//	 */
//	@Override
//	public void setLastUpdDate(String pLastUpdDate) {
//		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
//	}
}
