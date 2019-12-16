package com.pccw.rpt.dao;

import com.pccw.util.db.DaoBaseReadonlyImpl;
import com.pccw.util.db.stringOracleType.OraCLOB;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class WItemRptTemplateVDAOImpl extends DaoBaseReadonlyImpl implements
		WItemRptTemplateVDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4709715998300746041L;

	private OraNumber itemId; // W_ITEM_RPT_TEMPLATE_V.ITEM_ID
	private String lob; // W_ITEM_RPT_TEMPLATE_V.LOB
	private String itemType; // W_ITEM_RPT_TEMPLATE_V.ITEM_TYPE
	private String description; // W_ITEM_RPT_TEMPLATE_V.DESCRIPTION
	private String mdoInd; // W_ITEM_RPT_TEMPLATE_V.MDO_IND
	private String displayType; // W_ITEM_RPT_TEMPLATE_V.DISPLAY_TYPE
	private String locale; // W_ITEM_RPT_TEMPLATE_V.LOCALE
	private OraCLOB html; // W_ITEM_RPT_TEMPLATE_V.HTML
	private String imagePath; // W_ITEM_RPT_TEMPLATE_V.IMAGE_PATH
	private OraDate prcEffStartDate = new OraDateYYYYMMDDHH24MISS(); // W_ITEM_RPT_TEMPLATE_V.PRC_EFF_START_DATE
	private OraDate prcEffEndDate = new OraDateYYYYMMDDHH24MISS(); // W_ITEM_RPT_TEMPLATE_V.PRC_EFF_END_DATE
	private String fixTermRate; // W_ITEM_RPT_TEMPLATE_V.FIX_TERM_RATE
	private String mthToMthRate; // W_ITEM_RPT_TEMPLATE_V.MTH_TO_MTH_RATE
	private String onetime; // W_ITEM_RPT_TEMPLATE_V.ONETIME
	private String penaltyAmt; // W_ITEM_RPT_TEMPLATE_V.PENALTY_AMT

	public WItemRptTemplateVDAOImpl() {
		primaryKeyFields = new String[] {};
		this.addExcludeColumn("oracleRowID");
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getTableName()
	 */
	@Override
	public String getTableName() {
		return "W_ITEM_RPT_TEMPLATE_V";
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getLob()
	 */
	@Override
	public String getLob() {
		return this.lob;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getItemType()
	 */
	@Override
	public String getItemType() {
		return this.itemType;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getDescription()
	 */
	@Override
	public String getDescription() {
		return this.description;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getMdoInd()
	 */
	@Override
	public String getMdoInd() {
		return this.mdoInd;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getDisplayType()
	 */
	@Override
	public String getDisplayType() {
		return this.displayType;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getLocale()
	 */
	@Override
	public String getLocale() {
		return this.locale;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getImagePath()
	 */
	@Override
	public String getImagePath() {
		return this.imagePath;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getFixTermRate()
	 */
	@Override
	public String getFixTermRate() {
		return this.fixTermRate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getMthToMthRate()
	 */
	@Override
	public String getMthToMthRate() {
		return this.mthToMthRate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getOnetime()
	 */
	@Override
	public String getOnetime() {
		return this.onetime;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getItemId()
	 */
	@Override
	public String getItemId() {
		return this.itemId != null ? this.itemId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getHtml()
	 */
	@Override
	public String getHtml() {
		return this.html != null ? this.html.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getPrcEffStartDate()
	 */
	@Override
	public String getPrcEffStartDate() {
		return this.prcEffStartDate != null ? this.prcEffStartDate.toString()
				: null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getPrcEffEndDate()
	 */
	@Override
	public String getPrcEffEndDate() {
		return this.prcEffEndDate != null ? this.prcEffEndDate.toString()
				: null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getPrcEffStartDateORACLE()
	 */
	@Override
	public OraDate getPrcEffStartDateORACLE() {
		return this.prcEffStartDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#getPrcEffEndDateORACLE()
	 */
	@Override
	public OraDate getPrcEffEndDateORACLE() {
		return this.prcEffEndDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setLob(java.lang.String)
	 */
	@Override
	public void setLob(String pLob) {
		this.lob = pLob;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setItemType(java.lang.String)
	 */
	@Override
	public void setItemType(String pItemType) {
		this.itemType = pItemType;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String pDescription) {
		this.description = pDescription;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setMdoInd(java.lang.String)
	 */
	@Override
	public void setMdoInd(String pMdoInd) {
		this.mdoInd = pMdoInd;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setDisplayType(java.lang.String)
	 */
	@Override
	public void setDisplayType(String pDisplayType) {
		this.displayType = pDisplayType;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setLocale(java.lang.String)
	 */
	@Override
	public void setLocale(String pLocale) {
		this.locale = pLocale;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setImagePath(java.lang.String)
	 */
	@Override
	public void setImagePath(String pImagePath) {
		this.imagePath = pImagePath;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setFixTermRate(java.lang.String)
	 */
	@Override
	public void setFixTermRate(String pFixTermRate) {
		this.fixTermRate = pFixTermRate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setMthToMthRate(java.lang.String)
	 */
	@Override
	public void setMthToMthRate(String pMthToMthRate) {
		this.mthToMthRate = pMthToMthRate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setOnetime(java.lang.String)
	 */
	@Override
	public void setOnetime(String pOnetime) {
		this.onetime = pOnetime;
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setItemId(java.lang.String)
	 */
	@Override
	public void setItemId(String pItemId) {
		this.itemId = new OraNumber(pItemId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setHtml(java.lang.String)
	 */
	@Override
	public void setHtml(String pHtml) {
		this.html = new OraCLOB(pHtml);
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setPrcEffStartDate(java.lang.String)
	 */
	@Override
	public void setPrcEffStartDate(String pPrcEffStartDate) {
		this.prcEffStartDate = new OraDateYYYYMMDDHH24MISS(pPrcEffStartDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.rpt.dao.WItemRptTemplateVDAO#setPrcEffEndDate(java.lang.String)
	 */
	@Override
	public void setPrcEffEndDate(String pPrcEffEndDate) {
		this.prcEffEndDate = new OraDateYYYYMMDDHH24MISS(pPrcEffEndDate);
	}

	@Override
	public String getPenaltyAmt() {
		return penaltyAmt;
	}

	@Override
	public void setPenaltyAmt(String penaltyAmt) {
		this.penaltyAmt = penaltyAmt;
	}
}
