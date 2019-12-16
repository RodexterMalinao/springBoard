package com.activity.dao.dataAccess;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class DocumentDetailDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 6456208634859399697L;

	private String orderId; // BOMWEB_ALL_ORD_DOC.ORDER_ID
	private String docType; // BOMWEB_ALL_ORD_DOC.DOC_TYPE
	private OraNumber seqNum; // BOMWEB_ALL_ORD_DOC.SEQ_NUM
	private String filePathName; // BOMWEB_ALL_ORD_DOC.FILE_PATH_NAME
	private OraDate processDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ALL_ORD_DOC.PROCESS_DATE
	private String createBy; // BOMWEB_ALL_ORD_DOC.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ALL_ORD_DOC.CREATE_DATE
	private String lastUpdBy; // BOMWEB_ALL_ORD_DOC.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_ALL_ORD_DOC.LAST_UPD_DATE
	private String outdatedInd; // BOMWEB_ALL_ORD_DOC.OUTDATED_IND

	public DocumentDetailDAO() {
		primaryKeyFields = new String[] {"orderId", "docType", "seqNum"};
	}

	public String getTableName() {
		return "BOMWEB_ALL_ORD_DOC";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getDocType() {
		return this.docType;
	}

	public String getFilePathName() {
		return this.filePathName;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getOutdatedInd() {
		return this.outdatedInd;
	}

	public String getSeqNum() {
		return this.seqNum != null ? this.seqNum.toString() : null;
	}

	public String getProcessDate() {
		return this.processDate != null ? this.processDate.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getProcessDateORACLE() {
		return this.processDate;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getLastUpdDateORACLE() {
		return this.lastUpdDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setDocType(String pDocType) {
		this.docType = pDocType;
	}

	public void setFilePathName(String pFilePathName) {
		this.filePathName = pFilePathName;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setOutdatedInd(String pOutdatedInd) {
		this.outdatedInd = pOutdatedInd;
	}

	public void setSeqNum(String pSeqNum) {
		this.seqNum = new OraNumber(pSeqNum);
	}

	public void setProcessDate(String pProcessDate) {
		this.processDate = new OraDateYYYYMMDDHH24MISS(pProcessDate);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
