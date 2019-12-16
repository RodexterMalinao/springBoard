package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;

public class CustomerIguardRegDAO extends DaoBaseImpl {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6423614845868320373L;
		
	private String orderId; // BOMWEB_CUST_IGUARD_REG.ORDER_ID   
	private String carecashInd;    // BOMWEB_CUST_IGUARD_REG.CARECASH_IND 
	private String carecashDmInd;     // BOMWEB_CUST_IGUARD_REG.CARECASH_DM_IND    
	private String emailAddr;     // BOMWEB_CUST_IGUARD_REG.EMAIL_ADDR 
	private String contactNum;	// BOMWEB_CUST_IGUARD_REG.CONTACT_NUM 
	private String createBy; //BOMWEB_CUST_IGUARD_REG.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_CUST_IGUARD_REG.CREATE_DATE
	private String lastUpdBy; // BOMWEB_CUST_IGUARD_REG.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_CUST_IGUARD_REG.LAST_UPD_DATE
	private String status;     // BOMWEB_CUST_IGUARD_REG.STATUS  
	private String rtnMsg;		// BOMWEB_CUST_IGUARD_REG.RTN_MSG 
		
	public CustomerIguardRegDAO() {
		primaryKeyFields = new String[] { "orderId"};
	}

	public String getTableName() {
		return "BOMWEB_CUST_IGUARD_REG";
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCarecashInd() {
		return carecashInd;
	}

	public void setCarecashInd(String carecashInd) {
		this.carecashInd = carecashInd;
	}

	public String getCarecashDmInd() {
		return carecashDmInd;
	}

	public void setCarecashDmInd(String carecashDmInd) {
		this.carecashDmInd = carecashDmInd;
	}
	
	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getContactNum() {
		return contactNum;
	}

	public void setContactNum(String contactNum) {
		this.contactNum = contactNum;
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

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRtnMsg() {
		return rtnMsg;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}
	
}
