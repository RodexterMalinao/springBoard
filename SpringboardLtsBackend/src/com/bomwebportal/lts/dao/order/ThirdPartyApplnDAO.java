package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ThirdPartyApplnDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 4119304285665180498L;
	
	private String orderId; // BOMWEB_THIRD_PARTY_APPLN.ORDER_ID
	private OraNumber dtlId; // BOMWEB_THIRD_PARTY_APPLN.DTL_ID
	private String appntFirstName; // BOMWEB_THIRD_PARTY_APPLN.APPNT_FIRST_NAME
	private String appntLastName; // BOMWEB_THIRD_PARTY_APPLN.APPNT_LAST_NAME
	private String appntDocType; // BOMWEB_THIRD_PARTY_APPLN.APPNT_DOC_TYPE
	private String appntDocId; // BOMWEB_THIRD_PARTY_APPLN.APPNT_DOC_ID
	private String relationshipCd; // BOMWEB_THIRD_PARTY_APPLN.RELATIONSHIP_CD
	private String appntIdVerifiedInd; // BOMWEB_THIRD_PARTY_APPLN.APPNT_ID_VERIFIED_IND
	private String appntContactNum; // BOMWEB_THIRD_PARTY_APPLN.APPNT_CONTACT_NUM
	private String title; // BOMWEB_THIRD_PARTY_APPLN.TITLE
	private String remarks; // BOMWEB_THIRD_PARTY_APPLN.REMARKS
	private String createBy; // BOMWEB_THIRD_PARTY_APPLN.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_THIRD_PARTY_APPLN.CREATE_DATE
	private String lastUpdBy; // BOMWEB_THIRD_PARTY_APPLN.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_THIRD_PARTY_APPLN.LAST_UPD_DATE
	private OraDate dob = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_THIRD_PARTY_APPLN.DOB

	
	public ThirdPartyApplnDAO() {
		primaryKeyFields = new String[] { "orderId", "dtlId" };
	}

	public String getTableName() {
		return "BOMWEB_THIRD_PARTY_APPLN";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getAppntFirstName() {
		return this.appntFirstName;
	}

	public String getAppntLastName() {
		return this.appntLastName;
	}

	public String getAppntDocType() {
		return this.appntDocType;
	}

	public String getAppntDocId() {
		return this.appntDocId;
	}

	public String getRelationshipCd() {
		return this.relationshipCd;
	}

	public String getAppntIdVerifiedInd() {
		return this.appntIdVerifiedInd;
	}

	public String getAppntContactNum() {
		return this.appntContactNum;
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

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
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

	public void setAppntFirstName(String pAppntFirstName) {
		this.appntFirstName = pAppntFirstName;
	}

	public void setAppntLastName(String pAppntLastName) {
		this.appntLastName = pAppntLastName;
	}

	public void setAppntDocType(String pAppntDocType) {
		this.appntDocType = pAppntDocType;
	}

	public void setAppntDocId(String pAppntDocId) {
		this.appntDocId = pAppntDocId;
	}

	public void setRelationshipCd(String pRelationshipCd) {
		this.relationshipCd = pRelationshipCd;
	}

	public void setAppntIdVerifiedInd(String pAppntIdVerifiedInd) {
		this.appntIdVerifiedInd = pAppntIdVerifiedInd;
	}

	public void setAppntContactNum(String pAppntContactNum) {
		this.appntContactNum = pAppntContactNum;
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

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String pTitle) {
		this.title = pTitle;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public void setDob(OraDate dob) {
		this.dob = dob;
	}
	
	public String getDob() {
		return this.dob != null ? this.dob.toString() : null;
	}
	
	public OraDate getDobORACLE() {
		return this.dob;
	}
	
	public void setDob(String pDob) {
		this.dob = new OraDateYYYYMMDDHH24MISS(pDob);
	}
}
