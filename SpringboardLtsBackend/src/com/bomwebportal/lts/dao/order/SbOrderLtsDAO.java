package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateYYYYMMDDHH24MISS;

public class SbOrderLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -1461938956195341244L;
	
	private String orderId; // BOMWEB_ORDER.ORDER_ID
	private String ocid; // BOMWEB_ORDER.OCID
	private String orderType; // BOMWEB_ORDER.ORDER_TYPE
	private String orderSubType; // BOMWEB_ORDER.ORDER_SUB_TYPE
	private String shopCd; // BOMWEB_ORDER.SHOP_CD
	private OraDate appDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER.APP_DATE
	private String salesType; // BOMWEB_ORDER.SALES_TYPE
	private String salesCd; // BOMWEB_ORDER.SALES_CD
	private String salesTeam; // BOMWEB_ORDER.SALES_TEAM
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_ORDER.CREATE_DATE
	private String salesName; // BOMWEB_ORDER.SALES_NAME
	private String lastUpdateBy; // BOMWEB_ORDER.LAST_UPDATE_BY
	private String createBy; // BOMWEB_ORDER.CREATE_BY
	private String lob; // BOMWEB_ORDER.LOB
	private OraDate signOffDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER.SIGN_OFF_DATE
	private String staffNum; // BOMWEB_ORDER.STAFF_NUM
	private String salesChannel; // BOMWEB_ORDER.SALES_CHANNEL
	private String salesContactNum; // BOMWEB_ORDER.SALES_CONTACT_NUM
	private String boc; // BOMWEB_ORDER.BOC
	private String disMode; // BOMWEB_ORDER.DIS_MODE
	private String signoffMode ; // BOMWEB_ORDER.SIGNOFF_MODE
	private String collectMethod; // BOMWEB_ORDER.COLLECT_METHOD
	private String esigEmailAddr; // BOMWEB_ORDER.ESIG_EMAIL_ADDR
	private String esigEmailLang; // BOMWEB_ORDER.ESIG_EMAIL_LANG
	private String dmsInd; // BOMWEB_ORDER.DMS_IND
	private String salesPosition; // BOMWEB_ORDER.SALES_POSITION
	private String salesJob; // BOMWEB_ORDER.SALES_JOB
	private OraDate salesProcessDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER.SALES_PROCESS_DATE
	private String createChannel; // BOMWEB_ORDER.CREATE_CHANNEL
	private String smsNo; // BOMWEB_ORDER.CREATE_CHANNEL
	private String backDateInd; // BOMWEB_ORDER.BACK_DATE_IND
	private String lastServiceInd; //BOMWEB_ORDER.LAST_SERVICE_IND
	private OraDate srvReqDate = new OraDateYYYYMMDDHH24MISS(); // BOMWEB_ORDER.SRV_REQ_DATE

	public SbOrderLtsDAO() { 
		   primaryKeyFields = new String[] {"orderId"};
		}
		
	public String getTableName() {
		return "BOMWEB_ORDER";
	}

	public void setAppDate(OraDate appDate) {
		this.appDate = appDate;
	}

	public void setCreateDate(OraDate createDate) {
		this.createDate = createDate;
	}

	public void setSignOffDate(OraDate signOffDate) {
		this.signOffDate = signOffDate;
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getOcid() {
		return this.ocid;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public String getOrderSubType() {
		return orderSubType;
	}

	public void setOrderSubType(String orderSubType) {
		this.orderSubType = orderSubType;
	}

	public String getShopCd() {
		return this.shopCd;
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

	public String getLastUpdateBy() {
		return this.lastUpdateBy;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLob() {
		return this.lob;
	}

	public String getStaffNum() {
		return this.staffNum;
	}

	public String getSalesChannel() {
		return this.salesChannel;
	}

	public String getSalesContactNum() {
		return this.salesContactNum;
	}

	public String getAppDate() {
		return this.appDate != null ? this.appDate.toString() : null;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public String getSignOffDate() {
		return this.signOffDate != null ? this.signOffDate.toString() : null;
	}

	public OraDate getAppDateORACLE() {
		return this.appDate;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public OraDate getSignOffDateORACLE() {
		return this.signOffDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setOcid(String pOcid) {
		this.ocid = pOcid;
	}

	public void setOrderType(String pOrderType) {
		this.orderType = pOrderType;
	}

	public void setShopCd(String pShopCd) {
		this.shopCd = pShopCd;
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

	public void setLastUpdateBy(String pLastUpdateBy) {
		this.lastUpdateBy = pLastUpdateBy;
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLob(String pLob) {
		this.lob = pLob;
	}

	public void setStaffNum(String pStaffNum) {
		this.staffNum = pStaffNum;
	}

	public void setSalesChannel(String pSalesChannel) {
		this.salesChannel = pSalesChannel;
	}

	public void setSalesContactNum(String pSalesContactNum) {
		this.salesContactNum = pSalesContactNum;
	}

	public void setAppDate(String pAppDate) {
		this.appDate = new OraDateYYYYMMDDHH24MISS(pAppDate);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setSignOffDate(String pSignOffDate) {
		this.signOffDate = new OraDateYYYYMMDDHH24MISS(pSignOffDate);
	}

	public String getBoc() {
		return boc;
	}

	public void setBoc(String boc) {
		this.boc = boc;
	}

	public String getSalesTeam() {
		return salesTeam;
	}

	public void setSalesTeam(String salesTeam) {
		this.salesTeam = salesTeam;
	}

	public String getDisMode() {
		return disMode;
	}

	public void setDisMode(String disMode) {
		this.disMode = disMode;
	}

	public String getCollectMethod() {
		return collectMethod;
	}

	public void setCollectMethod(String collectMethod) {
		this.collectMethod = collectMethod;
	}

	public String getEsigEmailAddr() {
		return esigEmailAddr;
	}

	public void setEsigEmailAddr(String esigEmailAddr) {
		this.esigEmailAddr = esigEmailAddr;
	}

	public String getEsigEmailLang() {
		return esigEmailLang;
	}

	public void setEsigEmailLang(String esigEmailLang) {
		this.esigEmailLang = esigEmailLang;
	}

	public String getDmsInd() {
		return dmsInd;
	}

	public void setDmsInd(String dmsInd) {
		this.dmsInd = dmsInd;
	}
	
	public String getSalesPosition() {
		return salesPosition;
	}

	public void setSalesPosition(String salesPosition) {
		this.salesPosition = salesPosition;
	}

	public String getSalesJob() {
		return salesJob;
	}

	public void setSalesJob(String salesJob) {
		this.salesJob = salesJob;
	}

	public String getSalesProcessDate() {
		return this.salesProcessDate != null ? this.salesProcessDate.toString() : null;
	}
	
	public OraDate getSalesProcessDateORACLE() {
		return this.salesProcessDate;
	}

	public void setSalesProcessDate(OraDate salesProcessDate) {
		this.salesProcessDate = salesProcessDate;
	}
	
	public void setSalesProcessDate(String salesProcessDate) {
		this.salesProcessDate = new OraDateYYYYMMDDHH24MISS(salesProcessDate);
	}

	public String getCreateChannel() {
		return createChannel;
	}

	public void setCreateChannel(String createChannel) {
		this.createChannel = createChannel;
	}

	public String getSmsNo() {
		return smsNo;
	}

	public void setSmsNo(String smsNo) {
		this.smsNo = smsNo;
	}

	public String getBackDateInd() {
		return backDateInd;
	}

	public void setBackDateInd(String backDateInd) {
		this.backDateInd = backDateInd;
	}

	public String getLastServiceInd() {
		return lastServiceInd;
	}

	public void setLastServiceInd(String lastServiceInd) {
		this.lastServiceInd = lastServiceInd;
	}

	public OraDate getSrvReqDateORACLE() {
		return srvReqDate;
	}

	public String getSrvReqDate() {
		return this.srvReqDate != null ? this.srvReqDate.toString() : null;
	}

	public void setSrvReqDate(String pSrvReqDate) {
		this.srvReqDate = new OraDateYYYYMMDDHH24MISS(pSrvReqDate);
	}
	
	public void setSrvReqDate(OraDate srvReqDate) {
		this.srvReqDate = srvReqDate;
	}

	public String getSignoffMode() {
		return signoffMode;
	}

	public void setSignoffMode(String signoffMode) {
		this.signoffMode = signoffMode;
	}

	@Override
	public String toString() {
		return "SbOrderLtsDAO [orderId=" + orderId + ", ocid=" + ocid
				+ ", orderType=" + orderType + ", orderSubType=" + orderSubType + ", shopCd=" + shopCd
				+ ", appDate=" + appDate + ", salesType=" + salesType
				+ ", salesCd=" + salesCd + ", salesTeam=" + salesTeam
				+ ", createDate=" + createDate + ", salesName=" + salesName
				+ ", lastUpdateBy=" + lastUpdateBy + ", createBy=" + createBy
				+ ", lob=" + lob + ", signOffDate=" + signOffDate
				+ ", staffNum=" + staffNum + ", salesChannel=" + salesChannel
				+ ", salesContactNum=" + salesContactNum + ", boc=" + boc
				+ ", disMode=" + disMode + ", collectMethod=" + collectMethod
				+ ", esigEmailAddr=" + esigEmailAddr + ", esigEmailLang="
				+ esigEmailLang + ", dmsInd=" + dmsInd + ", salesPosition="
				+ salesPosition + ", salesJob=" + salesJob
				+ ", salesProcessDate=" + salesProcessDate + ", createChannel="
				+ createChannel + ", smsNo=" + smsNo + ", backDateInd="
				+ backDateInd + ", srvReqDate=" + srvReqDate + ", signoffMode=" 
				+ signoffMode + "]";
	}


	
}
