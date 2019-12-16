package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;

public class ContactLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -6655530082136583697L;
	
	private String orderId; // BOMWEB_CONTACT.ORDER_ID
	private String title; // BOMWEB_CONTACT.TITLE
	private String contactName; // BOMWEB_CONTACT.CONTACT_NAME
	private String contactPhone; // BOMWEB_CONTACT.CONTACT_PHONE
	private String emailAddr; // BOMWEB_CONTACT.EMAIL_ADDR
	private String actionInd; // BOMWEB_CONTACT.ACTION_IND
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_CONTACT.CREATE_DATE
	private String otherPhone; // BOMWEB_CONTACT.OTHER_PHONE
	private String createBy; // BOMWEB_CONTACT.CREATE_BY
	private String lastUpdBy; // BOMWEB_CONTACT.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // BOMWEB_CONTACT.LAST_UPD_DATE
	private String contactType; // BOMWEB_CONTACT.CONTACT_TYPE
	private String lockInd; // BOMWEB_CONTACT.LOCK_IND
	private String idDocType; // BOMWEB_CONTACT.ID_DOC_TYPE
	private String idDocNum; // BOMWEB_CONTACT.ID_DOC_NUM
	private String contactMobile; // BOMWEB_CONTACT.CONTACT_MOBILE
	private String contactFixedLine; // BOMWEB_CONTACT.CONTACT_FIXED_LINE
	private String contactRelationship; // BOMWEB_CONTACT.CONTACT_RELATIONSHIP
	private String custNo; //BOMWEB_CONTACT.CUST_NO
	private String oldContactMobile; //BOMWEB_CONTACT.OLD_CONTACT_MOBILE
	private String oldEmailAddr; //BOMWEB_CONTACT.OLD_EMAIL_ADDR
	private OraDate oldContactMobileDate = new OraDateYYYYMMDDHH24MISS(); //BOMWEB_CONTACT.OLD_CONTACT_MOBILE_DATE
	private OraDate oldEmailAddrDate = new OraDateYYYYMMDDHH24MISS(); //BOMWEB_CONTACT.OLD_EMAIL_ADDR_DATE
	private String contactUpdAlert;	// BOMWEB_CONTACT.CONTACT_UPD_ALERT

	
	public ContactLtsDAO() {
		primaryKeyFields = new String[] {"orderId"};
	}

	public String getTableName() {
		return "BOMWEB_CONTACT";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getTitle() {
		return this.title;
	}

	public String getContactName() {
		return this.contactName;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public String getEmailAddr() {
		return this.emailAddr;
	}
	
	public String getOldEmailAddr() {
		return this.oldEmailAddr;
	}

	public String getOtherPhone() {
		return this.otherPhone;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getContactType() {
		return this.contactType;
	}

	public String getIdDocType() {
		return this.idDocType;
	}

	public String getIdDocNum() {
		return this.idDocNum;
	}

	public String getContactMobile() {
		return this.contactMobile;
	}
	
	public String getOldContactMobile() {
		return this.oldContactMobile;
	}

	public String getContactFixedLine() {
		return this.contactFixedLine;
	}

	public String getContactRelationship() {
		return this.contactRelationship;
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

	public void setTitle(String pTitle) {
		this.title = pTitle;
	}

	public void setContactName(String pContactName) {
		this.contactName = pContactName;
	}

	public void setContactPhone(String pContactPhone) {
		this.contactPhone = pContactPhone;
	}

	public void setEmailAddr(String pEmailAddr) {
		this.emailAddr = pEmailAddr;
	}
	
	public void setOldEmailAddr(String pOldEmailAddr) {
		this.oldEmailAddr = pOldEmailAddr;
	}

	public void setOtherPhone(String pOtherPhone) {
		this.otherPhone = pOtherPhone;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setContactType(String pContactType) {
		this.contactType = pContactType;
	}

	public void setIdDocType(String pIdDocType) {
		this.idDocType = pIdDocType;
	}

	public void setIdDocNum(String pIdDocNum) {
		this.idDocNum = pIdDocNum;
	}

	public void setContactMobile(String pContactMobile) {
		this.contactMobile = pContactMobile;
	}
	
	public void setOldContactMobile(String pOldContactMobile) {
		this.oldContactMobile = pOldContactMobile;
	}

	public void setContactFixedLine(String pContactFixedLine) {
		this.contactFixedLine = pContactFixedLine;
	}

	public void setContactRelationship(String pContactRelationship) {
		this.contactRelationship = pContactRelationship;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}

	public String getActionInd() {
		return actionInd;
	}

	public void setActionInd(String actionInd) {
		this.actionInd = actionInd;
	}

	public String getLockInd() {
		return lockInd;
	}

	public void setLockInd(String lockInd) {
		this.lockInd = lockInd;
	}

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getOldContactMobileDate() {
		return this.oldContactMobileDate != null ? this.oldContactMobileDate.toString()
				: null;
	}
	
	public OraDate getOldContactMobileDateORACLE() {
		return this.oldContactMobileDate;
	}	
	
	public void setOldContactMobileDate(String pOldContactMobileDate) {
		this.oldContactMobileDate = new OraDateYYYYMMDDHH24MISS(pOldContactMobileDate);
	}	

	public String getOldEmailAddrDate() {
		return this.oldEmailAddrDate != null ? this.oldEmailAddrDate.toString()
				: null;
	}
	
	public OraDate getOldEmailAddrDateORACLE() {
		return this.oldEmailAddrDate;
	}	
	
	public void setOldEmailAddrDate(String pOldEmailAddrDate) {
		this.oldEmailAddrDate = new OraDateYYYYMMDDHH24MISS(pOldEmailAddrDate);
	}

	public String getContactUpdAlert() {
		return contactUpdAlert;
	}

	public void setContactUpdAlert(String contactUpdAlert) {
		this.contactUpdAlert = contactUpdAlert;
	}	

	
}
