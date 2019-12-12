package com.pccw.wq.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;
import com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect;

public class QWqDocumentDAOImpl extends DaoBaseImpl implements QWqDocumentDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4713571053615869269L;

	private OraNumberInsertValueFromSelect wqDocumentId; // Q_WQ_DOCUMENT.WQ_DOCUMENT_ID
	private OraNumber wqId; // Q_WQ_DOCUMENT.WQ_ID
	private OraNumber documentTypeId; // Q_WQ_DOCUMENT.DOCUMENT_TYPE_ID
	private String attachmentHost; // Q_WQ_DOCUMENT.ATTACHMENT_HOST
	private String attachmentPath; // Q_WQ_DOCUMENT.ATTACHMENT_PATH
	private String createBy; // Q_WQ_DOCUMENT.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // Q_WQ_DOCUMENT.CREATE_DATE
	private String lastUpdBy; // Q_WQ_DOCUMENT.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // Q_WQ_DOCUMENT.LAST_UPD_DATE

	public QWqDocumentDAOImpl() {
		primaryKeyFields = new String[] { "wqDocumentId" };
	}

	public String getTableName() {
		return "Q_WQ_DOCUMENT";
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getAttachmentHost()
	 */
	@Override
	public String getAttachmentHost() {
		return this.attachmentHost;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getAttachmentPath()
	 */
	@Override
	public String getAttachmentPath() {
		return this.attachmentPath;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getCreateBy()
	 */
	@Override
	public String getCreateBy() {
		return this.createBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getLastUpdBy()
	 */
	@Override
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getWqDocumentId()
	 */
	@Override
	public String getWqDocumentId() {
		return this.wqDocumentId != null ? this.wqDocumentId.toString() : null;
	}
	
	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getWqDocumentIdORACLE()
	 */
	@Override
	public OraNumberInsertValueFromSelect getWqDocumentIdORACLE() {
		return this.wqDocumentId;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getWqId()
	 */
	@Override
	public String getWqId() {
		return this.wqId != null ? this.wqId.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getDocumentTypeId()
	 */
	@Override
	public String getDocumentTypeId() {
		return this.documentTypeId != null ? this.documentTypeId.toString()
				: null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getCreateDate()
	 */
	@Override
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getLastUpdDate()
	 */
	@Override
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getCreateDateORACLE()
	 */
	@Override
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#getLastUpdDateORACLE()
	 */
	@Override
	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#setAttachmentHost(java.lang.String)
	 */
	@Override
	public void setAttachmentHost(String pAttachmentHost) {
		this.attachmentHost = pAttachmentHost;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#setAttachmentPath(java.lang.String)
	 */
	@Override
	public void setAttachmentPath(String pAttachmentPath) {
		this.attachmentPath = pAttachmentPath;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#setCreateBy(java.lang.String)
	 */
	@Override
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#setLastUpdBy(java.lang.String)
	 */
	@Override
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#setWqDocumentIdORACLE(com.pccw.util.db.stringOracleType.OraNumberInsertValueFromSelect)
	 */
	@Override
	public void setWqDocumentIdORACLE(OraNumberInsertValueFromSelect pWqDocumentId) {
		this.wqDocumentId = pWqDocumentId;
	}
	
	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#setWqDocumentId(java.lang.String)
	 */
	@Override
	public void setWqDocumentId(String pWqDocumentId) {
		this.wqDocumentId.setValue(pWqDocumentId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#setWqId(java.lang.String)
	 */
	@Override
	public void setWqId(String pWqId) {
		this.wqId = new OraNumber(pWqId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#setDocumentTypeId(java.lang.String)
	 */
	@Override
	public void setDocumentTypeId(String pDocumentTypeId) {
		this.documentTypeId = new OraNumber(pDocumentTypeId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#setCreateDate(java.lang.String)
	 */
	@Override
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QWqDocumentDAO#setLastUpdDate(java.lang.String)
	 */
	@Override
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
