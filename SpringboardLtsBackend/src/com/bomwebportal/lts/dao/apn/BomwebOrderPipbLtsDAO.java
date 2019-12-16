package com.bomwebportal.lts.dao.apn;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class BomwebOrderPipbLtsDAO extends DaoBaseImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1522446050622966072L;
	private String orderId; //BOMWEB_ORDER_PIPB_LTS.ORDER_ID
	private OraNumber dtlId; //BOMWEB_ORDER_PIPB_LTS.DTL_ID
	private String pipbDnStatus; //BOMWEB_ORDER_PIPB_LTS.PIPB_DN_STATUS
	private String isPortBack; //BOMWEB_ORDER_PIPB_LTS.IS_PORT_BACK
	private String srvNn; //BOMWEB_ORDER_PIPB_LTS.SRV_NN
	private String pipbAction; //BOMWEB_ORDER_PIPB_LTS.PIPB_ACTION
	private String operator2n; //BOMWEB_ORDER_PIPB_LTS.OPERATOR2N
	private String reuseExSocketInd; //BOMWEB_ORDER_PIPB_LTS.REUSE_EX_SOCKET_IND
	private String waiveDnChangeInd; //BOMWEB_ORDER_PIPB_LTS.WAIVE_DN_CHANGE_IND
	private String fromDiffCustInd; //BOMWEB_ORDER_PIPB_LTS.FROM_DIFF_CUST_IND
	private String idDocType; //BOMWEB_ORDER_PIPB_LTS.ID_DOC_TYPE
	private String idDocNum; //BOMWEB_ORDER_PIPB_LTS.ID_DOC_NUM
	private String title; //BOMWEB_ORDER_PIPB_LTS.TITLE
	private String firstName; //BOMWEB_ORDER_PIPB_LTS.FIRST_NAME
	private String lastName; //BOMWEB_ORDER_PIPB_LTS.LAST_NAME
	private String companyName; //BOMWEB_ORDER_PIPB_LTS.COMPANY_NAME
	private String fromDiffAddrInd; //BOMWEB_ORDER_PIPB_LTS.FROM_DIFF_ADDR_IND
	private String addrLine1; //BOMWEB_ORDER_PIPB_LTS.ADDR_LINE1
	private String addrLine2; //BOMWEB_ORDER_PIPB_LTS.ADDR_LINE2
	private String addrLine3; //BOMWEB_ORDER_PIPB_LTS.ADDR_LINE3
	private String addrLine4; //BOMWEB_ORDER_PIPB_LTS.ADDR_LINE4
	private String addrLine5; //BOMWEB_ORDER_PIPB_LTS.ADDR_LINE5
	private String addrLine6; //BOMWEB_ORDER_PIPB_LTS.ADDR_LINE6
	private String duplexInd; //BOMWEB_ORDER_PIPB_LTS.DUPLEX_IND
	private String duplexAction; //BOMWEB_ORDER_PIPB_LTS.DUPLEX_ACTION
	private String duplexDn; //BOMWEB_ORDER_PIPB_LTS.DUPLEX_DN
	private String createBy; //BOMWEB_ORDER_PIPB_LTS.CREATE_BY
	private OraDate createDate= new OraDateCreateDate(); //BOMWEB_ORDER_PIPB_LTS.CREATE_DATE
	private String lastUpdBy; //BOMWEB_ORDER_PIPB_LTS.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); //BOMWEB_ORDER_PIPB_LTS.LAST_UPD_DATE
	
	public BomwebOrderPipbLtsDAO() { 
		primaryKeyFields = new String[] {"orderId", "dtlId"}; 
	}	
	public String getTableName() {
		return "BOMWEB_ORDER_PIPB_LTS";
	}	
	public String getOrderId() {	
		 return this.orderId; 
	}
	public String getPipbDnStatus() {
		 return this.pipbDnStatus; 
	}
	public String getIsPortBack() {
		 return this.isPortBack; 
	}
	public String getSrvNn() {
		 return this.srvNn; 
	}
	public String getPipbAction() {
		 return this.pipbAction; 
	}
	public String getReuseExSocketInd() {
		 return this.reuseExSocketInd; 
	}
	public String getWaiveDnChangeInd() {
		 return this.waiveDnChangeInd; 
	}
	public String getFromDiffCustInd() {
		 return this.fromDiffCustInd; 
	}
	public String getIdDocType() {
		 return this.idDocType; 
	}
	public String getIdDocNum() {
		 return this.idDocNum; 
	}
	public String getTitle() {
		 return this.title; 
	}
	public String getFirstName() {
		 return this.firstName; 
	}
	public String getLastName() {
		 return this.lastName; 
	}
	public String getCompanyName() {
		 return this.companyName; 
	}
	public String getFromDiffAddrInd() {
		 return this.fromDiffAddrInd; 
	}
	public String getAddrLine1() {
		 return this.addrLine1; 
	}
	public String getAddrLine2() {
		 return this.addrLine2; 
	}
	public String getAddrLine3() {
		 return this.addrLine3; 
	}
	public String getAddrLine4() {
		 return this.addrLine4; 
	}
	public String getAddrLine5() {
		 return this.addrLine5; 
	}
	public String getAddrLine6() {
		 return this.addrLine6; 
	}
	public String getDuplexInd() {
		 return this.duplexInd; 
	}
	public String getDuplexAction() {
		 return this.duplexAction; 
	}
	public String getDuplexDn() {
		 return this.duplexDn; 
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
	public void setPipbDnStatus(String pPipbDnStatus) {
		 this.pipbDnStatus = pPipbDnStatus; 
	}
	public void setIsPortBack(String pIsPortBack) {
		 this.isPortBack = pIsPortBack; 
	}
	public void setSrvNn(String pSrvNn) {
		 this.srvNn = pSrvNn; 
	}
	public void setPipbAction(String pPipbAction) {
		 this.pipbAction = pPipbAction; 
	}
	public void setReuseExSocketInd(String pReuseExSocketInd) {
		 this.reuseExSocketInd = pReuseExSocketInd; 
	}
	public void setWaiveDnChangeInd(String pWaiveDnChangeInd) {
		 this.waiveDnChangeInd = pWaiveDnChangeInd; 
	}
	public void setFromDiffCustInd(String pFromDiffCustInd) {
		 this.fromDiffCustInd = pFromDiffCustInd; 
	}
	public void setIdDocType(String pIdDocType) {
		 this.idDocType = pIdDocType; 
	}
	public void setIdDocNum(String pIdDocNum) {
		 this.idDocNum = pIdDocNum; 
	}
	public void setTitle(String pTitle) {
		 this.title = pTitle; 
	}
	public void setFirstName(String pFirstName) {
		 this.firstName = pFirstName; 
	}
	public void setLastName(String pLastName) {
		 this.lastName = pLastName; 
	}
	public void setCompanyName(String pCompanyName) {
		 this.companyName = pCompanyName; 
	}
	public void setFromDiffAddrInd(String pFromDiffAddrInd) {
		 this.fromDiffAddrInd = pFromDiffAddrInd; 
	}
	public void setAddrLine1(String pAddrLine1) {
		 this.addrLine1 = pAddrLine1; 
	}
	public void setAddrLine2(String pAddrLine2) {
		 this.addrLine2 = pAddrLine2; 
	}
	public void setAddrLine3(String pAddrLine3) {
		 this.addrLine3 = pAddrLine3; 
	}
	public void setAddrLine4(String pAddrLine4) {
		 this.addrLine4 = pAddrLine4; 
	}
	public void setAddrLine5(String pAddrLine5) {
		 this.addrLine5 = pAddrLine5; 
	}
	public void setAddrLine6(String pAddrLine6) {
		 this.addrLine6 = pAddrLine6; 
	}
	public void setDuplexInd(String pDuplexInd) {
		 this.duplexInd = pDuplexInd; 
	}
	public void setDuplexAction(String pDuplexAction) {
		 this.duplexAction = pDuplexAction; 
	}
	public void setDuplexDn(String pDuplexDn) {
		 this.duplexDn = pDuplexDn; 
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
		 this.lastUpdDate = new OraDateLastUpdDate (pLastUpdDate); 
	}
	public String getOperator2n() {
		return operator2n;
	}
	public void setOperator2n(String operator2n) {
		this.operator2n = operator2n;
	}
	
}
