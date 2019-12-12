package com.pccw.wq.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class QWqWpDocumentDAOImpl extends DaoBaseImpl implements QWqWpDocumentDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1574799206616416037L;

	private OraNumber wqWpAssgnId; // Q_WQ_WP_DOCUMENT.WQ_WP_ASSGN_ID
	private OraNumber wqWpAssgnDocId; // Q_WQ_WP_DOCUMENT.WQ_WP_ASSGN_DOC_ID
	private OraNumber wqDocumentId; // Q_WQ_WP_DOCUMENT.WQ_DOCUMENT_ID
	private OraNumber printCount; // Q_WQ_WP_DOCUMENT.PRINT_COUNT
	private OraDate printDate = new OraDateYYYYMMDDHH24MISS(); // Q_WQ_WP_DOCUMENT.PRINT_DATE
	private OraDate downloadDate = new OraDateYYYYMMDDHH24MISS(); // Q_WQ_WP_DOCUMENT.DOWNLOAD_DATE
	private String createBy; // Q_WQ_WP_DOCUMENT.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // Q_WQ_WP_DOCUMENT.CREATE_DATE
	private String lastUpdBy; // Q_WQ_WP_DOCUMENT.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // Q_WQ_WP_DOCUMENT.LAST_UPD_DATE

	public QWqWpDocumentDAOImpl() {
		primaryKeyFields = new String[] { "wqWpAssgnId", "wqWpAssgnDocId" };
	}

	public String getTableName() {
		return "Q_WQ_WP_DOCUMENT";
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getCreateBy()
	 */
	@Override
	public String getCreateBy() {
		return this.createBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getLastUpdBy()
	 */
	@Override
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getWqWpAssgnId()
	 */
	@Override
	public String getWqWpAssgnId() {
		return this.wqWpAssgnId != null ? this.wqWpAssgnId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getWqWpAssgnDocId()
	 */
	@Override
	public String getWqWpAssgnDocId() {
		return this.wqWpAssgnDocId != null ? this.wqWpAssgnDocId.toString()
				: null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getWqDocumentId()
	 */
	@Override
	public String getWqDocumentId() {
		return this.wqDocumentId != null ? this.wqDocumentId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getPrintCount()
	 */
	@Override
	public String getPrintCount() {
		return this.printCount != null ? this.printCount.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getPrintDate()
	 */
	@Override
	public String getPrintDate() {
		return this.printDate != null ? this.printDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getDownloadDate()
	 */
	@Override
	public String getDownloadDate() {
		return this.downloadDate != null ? this.downloadDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getCreateDate()
	 */
	@Override
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getLastUpdDate()
	 */
	@Override
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getPrintDateORACLE()
	 */
	@Override
	public OraDate getPrintDateORACLE() {
		return this.printDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getDownloadDateORACLE()
	 */
	@Override
	public OraDate getDownloadDateORACLE() {
		return this.downloadDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getCreateDateORACLE()
	 */
	@Override
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#getLastUpdDateORACLE()
	 */
	@Override
	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#setCreateBy(java.lang.String)
	 */
	@Override
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#setLastUpdBy(java.lang.String)
	 */
	@Override
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#setWqWpAssgnId(java.lang.String)
	 */
	@Override
	public void setWqWpAssgnId(String pWqWpAssgnId) {
		this.wqWpAssgnId = new OraNumber(pWqWpAssgnId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#setWqWpAssgnDocId(java.lang.String)
	 */
	@Override
	public void setWqWpAssgnDocId(String pWqWpAssgnDocId) {
		this.wqWpAssgnDocId = new OraNumber(pWqWpAssgnDocId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#setWqDocumentId(java.lang.String)
	 */
	@Override
	public void setWqDocumentId(String pWqDocumentId) {
		this.wqDocumentId = new OraNumber(pWqDocumentId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#setPrintCount(java.lang.String)
	 */
	@Override
	public void setPrintCount(String pPrintCount) {
		this.printCount = new OraNumber(pPrintCount);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#setPrintDate(java.lang.String)
	 */
	@Override
	public void setPrintDate(String pPrintDate) {
		this.printDate = new OraDateYYYYMMDDHH24MISS(pPrintDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#setDownloadDate(java.lang.String)
	 */
	@Override
	public void setDownloadDate(String pDownloadDate) {
		this.downloadDate = new OraDateYYYYMMDDHH24MISS(pDownloadDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#setCreateDate(java.lang.String)
	 */
	@Override
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqWpDocumentDAO#setLastUpdDate(java.lang.String)
	 */
	@Override
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
	
	@Override
	public boolean doInsert() throws Exception {
		throw new Exception("doInsert in QWqWpDocumentDAO NOT SUPPORTED"); 
	}
}
