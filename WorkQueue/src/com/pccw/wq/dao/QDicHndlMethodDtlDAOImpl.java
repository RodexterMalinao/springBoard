package com.pccw.wq.dao;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class QDicHndlMethodDtlDAOImpl extends DaoBaseImpl implements QDicHndlMethodDtlDAO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2044911236700782648L;

	private OraNumber handleMethodId; // Q_DIC_HNDL_METHOD_DTL.HANDLE_METHOD_ID
	private String attbName; // Q_DIC_HNDL_METHOD_DTL.ATTB_NAME
	private String attbValue; // Q_DIC_HNDL_METHOD_DTL.ATTB_VALUE
	private String createBy; // Q_DIC_HNDL_METHOD_DTL.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // Q_DIC_HNDL_METHOD_DTL.CREATE_DATE
	private String lastUpdBy; // Q_DIC_HNDL_METHOD_DTL.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // Q_DIC_HNDL_METHOD_DTL.LAST_UPD_DATE

	public QDicHndlMethodDtlDAOImpl() {
		primaryKeyFields = new String[] { "handleMethodId", "attbName" };
	}

	@Override
	public String getTableName() {
		return "Q_DIC_HNDL_METHOD_DTL";
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#getAttbName()
	 */
	@Override
	public String getAttbName() {
		return this.attbName;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#getAttbValue()
	 */
	@Override
	public String getAttbValue() {
		return this.attbValue;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#getCreateBy()
	 */
	@Override
	public String getCreateBy() {
		return this.createBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#getLastUpdBy()
	 */
	@Override
	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#getHandleMethodId()
	 */
	@Override
	public String getHandleMethodId() {
		return this.handleMethodId != null ? this.handleMethodId.toString()
				: null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#getCreateDate()
	 */
	@Override
	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#getLastUpdDate()
	 */
	@Override
	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#getCreateDateORACLE()
	 */
	@Override
	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#getLastUpdDateORACLE()
	 */
	@Override
	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#setAttbName(java.lang.String)
	 */
	@Override
	public void setAttbName(String pAttbName) {
		this.attbName = pAttbName;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#setAttbValue(java.lang.String)
	 */
	@Override
	public void setAttbValue(String pAttbValue) {
		this.attbValue = pAttbValue;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#setCreateBy(java.lang.String)
	 */
	@Override
	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#setLastUpdBy(java.lang.String)
	 */
	@Override
	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#setHandleMethodId(java.lang.String)
	 */
	@Override
	public void setHandleMethodId(String pHandleMethodId) {
		this.handleMethodId = new OraNumber(pHandleMethodId);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#setCreateDate(java.lang.String)
	 */
	@Override
	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	/* (non-Javadoc)
	 * @see com.pccw.wq.dao.QDicHndlMethodDtlDAO#setLastUpdDate(java.lang.String)
	 */
	@Override
	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}