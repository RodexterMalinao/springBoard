package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class NowtvDetailLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 5598332957321597668L;

	private String orderId; // BOMWEB_LTS_NOWTV_DETAIL.ORDER_ID
	private OraNumber dtlId; // BOMWEB_LTS_NOWTV_DETAIL.DTL_ID
	private String viewAvInd; // BOMWEB_LTS_NOWTV_DETAIL.VIEW_AV_IND
	private OraDate dob = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_LTS_NOWTV_DETAIL.DOB
	private String emailAddr; // BOMWEB_LTS_NOWTV_DETAIL.EMAIL_ADDR
	private String billMedia; // BOMWEB_LTS_NOWTV_DETAIL.BILL_MEDIA
	private String createBy; // BOMWEB_LTS_NOWTV_DETAIL.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_LTS_NOWTV_DETAIL.CREATE_DATE
	private String lastUpdBy; // BOMWEB_LTS_NOWTV_DETAIL.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_LTS_NOWTV_DETAIL.LAST_UPD_DATE

	
	public NowtvDetailLtsDAO() {
		primaryKeyFields = new String[] {"orderId", "dtlId"};
	}

	public String getTableName() {
		return "BOMWEB_LTS_NOWTV_DETAIL";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getViewAvInd() {
		return this.viewAvInd;
	}

	public String getEmailAddr() {
		return this.emailAddr;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getDtlId() {
		return this.dtlId != null ? this.dtlId.toString() : null;
	}

	public String getDob() {
		return this.dob != null ? this.dob.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getDobORACLE() {
		return this.dob;
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

	public void setViewAvInd(String pViewAvInd) {
		this.viewAvInd = pViewAvInd;
	}

	public void setEmailAddr(String pEmailAddr) {
		this.emailAddr = pEmailAddr;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setDtlId(String pDtlId) {
		this.dtlId = new OraNumber(pDtlId);
	}

	public void setDob(String pDob) {
		this.dob = new OraDateYYYYMMDDHH24MISS(pDob);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getBillMedia() {
		return this.billMedia;
	}

	public void setBillMedia(String pBillMedia) {
		this.billMedia = pBillMedia;
	}
}
