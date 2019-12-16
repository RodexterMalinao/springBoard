package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;

public class StaffInfoLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = 1080383332794123260L;
	
	private String orderId; // BOMWEB_STAFF_INFO.ORDER_ID
	private String salesType; // BOMWEB_STAFF_INFO.SALES_TYPE
	private String salesCd; // BOMWEB_STAFF_INFO.SALES_CD
	private String salesName; // BOMWEB_STAFF_INFO.SALES_NAME
	private String refSalesType; // BOMWEB_STAFF_INFO.REF_SALES_TYPE
	private String refSalesCd; // BOMWEB_STAFF_INFO.REF_SALES_CD
	private String refSalesName; // BOMWEB_STAFF_INFO.REF_SALES_NAME
	private String position; // BOMWEB_STAFF_INFO.POSITION
	private OraDate callDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_STAFF_INFO.CALL_DATE
	private String callList; // BOMWEB_STAFF_INFO.CALL_LIST
	private String createBy; // BOMWEB_STAFF_INFO.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_STAFF_INFO.CREATE_DATE
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_STAFF_INFO.LAST_UPD_DATE
	private String lastUpdBy; // BOMWEB_STAFF_INFO.LAST_UPD_BY
	private String callListDesc; // BOMWEB_STAFF_INFO.CALL_LIST_DESC
	private String contactPhone; // BOMWEB_STAFF_INFO.CONTACT_PHONE
	private String centreCd; // BOMWEB_STAFF_INFO.CENTRE_CD
	private String teamCd; // BOMWEB_STAFF_INFO.TEAM_CD
	private String refCentreCd; // BOMWEB_STAFF_INFO.REF_CENTRE_CD
	private String refTeamCd; // BOMWEB_STAFF_INFO.REF_TEAM_CD

	
	public StaffInfoLtsDAO() {
		primaryKeyFields = new String[] {"orderId"};
	}

	public String getTableName() {
		return "BOMWEB_STAFF_INFO";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getSalesType() {
		return this.salesType;
	}

	public String getSalesCd() {
		return this.salesCd;
	}

	public String getSalesName() {
		return this.salesName;
	}

	public String getRefSalesType() {
		return this.refSalesType;
	}

	public String getRefSalesCd() {
		return this.refSalesCd;
	}

	public String getRefSalesName() {
		return this.refSalesName;
	}

	public String getPosition() {
		return this.position;
	}

	public String getCallList() {
		return this.callList;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getCallListDesc() {
		return this.callListDesc;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public String getCentreCd() {
		return this.centreCd;
	}

	public String getTeamCd() {
		return this.teamCd;
	}

	public String getRefCentreCd() {
		return this.refCentreCd;
	}

	public String getRefTeamCd() {
		return this.refTeamCd;
	}

	public String getCallDate() {
		return this.callDate != null ? this.callDate.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getLastUpdDate() {
		return this.lastUpdDate != null ? this.lastUpdDate.toString() : null;
	}

	public OraDate getCallDateORACLE() {
		return this.callDate;
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

	public void setSalesType(String pSalesType) {
		this.salesType = pSalesType;
	}

	public void setSalesCd(String pSalesCd) {
		this.salesCd = pSalesCd;
	}

	public void setSalesName(String pSalesName) {
		this.salesName = pSalesName;
	}

	public void setRefSalesType(String pRefSalesType) {
		this.refSalesType = pRefSalesType;
	}

	public void setRefSalesCd(String pRefSalesCd) {
		this.refSalesCd = pRefSalesCd;
	}

	public void setRefSalesName(String pRefSalesName) {
		this.refSalesName = pRefSalesName;
	}

	public void setPosition(String pPosition) {
		this.position = pPosition;
	}

	public void setCallList(String pCallList) {
		this.callList = pCallList;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setCallListDesc(String pCallListDesc) {
		this.callListDesc = pCallListDesc;
	}

	public void setContactPhone(String pContactPhone) {
		this.contactPhone = pContactPhone;
	}

	public void setCentreCd(String pCentreCd) {
		this.centreCd = pCentreCd;
	}

	public void setTeamCd(String pTeamCd) {
		this.teamCd = pTeamCd;
	}

	public void setRefCentreCd(String pRefCentreCd) {
		this.refCentreCd = pRefCentreCd;
	}

	public void setRefTeamCd(String pRefTeamCd) {
		this.refTeamCd = pRefTeamCd;
	}

	public void setCallDate(String pCallDate) {
		this.callDate = new OraDateYYYYMMDDHH24MISS(pCallDate);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
