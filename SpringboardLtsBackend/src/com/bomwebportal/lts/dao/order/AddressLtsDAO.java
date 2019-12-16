package com.bomwebportal.lts.dao.order;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;

public class AddressLtsDAO extends DaoBaseImpl {

	private static final long serialVersionUID = -2229740334036736558L;
	
	private String orderId; // BOMWEB_CUST_ADDR.ORDER_ID
	private String addrUsage; // BOMWEB_CUST_ADDR.ADDR_USAGE
	private String areaCd; // BOMWEB_CUST_ADDR.AREA_CD
	private String distNo; // BOMWEB_CUST_ADDR.DIST_NO
	private String sectCd; // BOMWEB_CUST_ADDR.SECT_CD
	private String strName; // BOMWEB_CUST_ADDR.STR_NAME
	private String hiLotNo; // BOMWEB_CUST_ADDR.HI_LOT_NO
	private String strCatCd; // BOMWEB_CUST_ADDR.STR_CAT_CD
	private String buildNo; // BOMWEB_CUST_ADDR.BUILD_NO
	private String foreignAddrFlag; // BOMWEB_CUST_ADDR.FOREIGN_ADDR_FLAG
	private String floorNo; // BOMWEB_CUST_ADDR.FLOOR_NO
	private String unitNo; // BOMWEB_CUST_ADDR.UNIT_NO
	private String poBoxNo; // BOMWEB_CUST_ADDR.PO_BOX_NO
	private String addrType; // BOMWEB_CUST_ADDR.ADDR_TYPE
	private String strNo; // BOMWEB_CUST_ADDR.STR_NO
	private String sectDepInd; // BOMWEB_CUST_ADDR.SECT_DEP_IND
	private OraDate createDate = new OraDateCreateDate(); // BOMWEB_CUST_ADDR.CREATE_DATE
	private String areaDesc; // BOMWEB_CUST_ADDR.AREA_DESC
	private String distDesc; // BOMWEB_CUST_ADDR.DIST_DESC
	private String sectDesc; // BOMWEB_CUST_ADDR.SECT_DESC
	private String strCatDesc; // BOMWEB_CUST_ADDR.STR_CAT_DESC
	private String serbdyno; // BOMWEB_CUST_ADDR.SERBDYNO
	private String blacklistInd; // BOMWEB_CUST_ADDR.BLACKLIST_IND
	private String ltsBlacklistInd; // BOMWEB_CUST_ADDR.LTS_BLACKLIST_IND
	private String buildXy; //BUILD_XY
	private String disFullAddrDescEn; // DIS_FULL_ADDR_DESC_EN
	private String disFullAddrDescCh; // DIS_FULL_ADDR_DESC_CH
	
	public AddressLtsDAO() {
		primaryKeyFields = new String[] {"orderId"};
	}

	public String getTableName() {
		return "BOMWEB_CUST_ADDR";
	}

	public String getOrderId() {
		return this.orderId;
	}

	public String getAddrUsage() {
		return this.addrUsage;
	}

	public String getAreaCd() {
		return this.areaCd;
	}

	public String getDistNo() {
		return this.distNo;
	}

	public String getSectCd() {
		return this.sectCd;
	}

	public String getStrName() {
		return this.strName;
	}

	public String getHiLotNo() {
		return this.hiLotNo;
	}

	public String getStrCatCd() {
		return this.strCatCd;
	}

	public String getBuildNo() {
		return this.buildNo;
	}

	public String getFloorNo() {
		return this.floorNo;
	}

	public String getUnitNo() {
		return this.unitNo;
	}

	public String getPoBoxNo() {
		return this.poBoxNo;
	}

	public String getStrNo() {
		return this.strNo;
	}

	public String getAreaDesc() {
		return this.areaDesc;
	}

	public String getDistDesc() {
		return this.distDesc;
	}

	public String getSectDesc() {
		return this.sectDesc;
	}

	public String getStrCatDesc() {
		return this.strCatDesc;
	}

	public String getSerbdyno() {
		return this.serbdyno;
	}

	public String getBlacklistInd() {
		return this.blacklistInd;
	}

	public String getCreateDate() {
		return this.createDate != null ? this.createDate.toString() : null;
	}

	public OraDate getCreateDateORACLE() {
		return this.createDate;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public void setAddrUsage(String pAddrUsage) {
		this.addrUsage = pAddrUsage;
	}

	public void setAreaCd(String pAreaCd) {
		this.areaCd = pAreaCd;
	}

	public void setDistNo(String pDistNo) {
		this.distNo = pDistNo;
	}

	public void setSectCd(String pSectCd) {
		this.sectCd = pSectCd;
	}

	public void setStrName(String pStrName) {
		this.strName = pStrName;
	}

	public void setHiLotNo(String pHiLotNo) {
		this.hiLotNo = pHiLotNo;
	}

	public void setStrCatCd(String pStrCatCd) {
		this.strCatCd = pStrCatCd;
	}

	public void setBuildNo(String pBuildNo) {
		this.buildNo = pBuildNo;
	}

	public void setFloorNo(String pFloorNo) {
		this.floorNo = pFloorNo;
	}

	public void setUnitNo(String pUnitNo) {
		this.unitNo = pUnitNo;
	}

	public void setPoBoxNo(String pPoBoxNo) {
		this.poBoxNo = pPoBoxNo;
	}

	public void setStrNo(String pStrNo) {
		this.strNo = pStrNo;
	}

	public void setAreaDesc(String pAreaDesc) {
		this.areaDesc = pAreaDesc;
	}

	public void setDistDesc(String pDistDesc) {
		this.distDesc = pDistDesc;
	}

	public void setSectDesc(String pSectDesc) {
		this.sectDesc = pSectDesc;
	}

	public void setStrCatDesc(String pStrCatDesc) {
		this.strCatDesc = pStrCatDesc;
	}

	public void setSerbdyno(String pSerbdyno) {
		this.serbdyno = pSerbdyno;
	}

	public void setBlacklistInd(String pBlacklistInd) {
		this.blacklistInd = pBlacklistInd;
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public String getForeignAddrFlag() {
		return foreignAddrFlag;
	}

	public void setForeignAddrFlag(String foreignAddrFlag) {
		this.foreignAddrFlag = foreignAddrFlag;
	}

	public String getAddrType() {
		return addrType;
	}

	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	public String getSectDepInd() {
		return sectDepInd;
	}

	public void setSectDepInd(String sectDepInd) {
		this.sectDepInd = sectDepInd;
	}

	public void setCreateDate(OraDate createDate) {
		this.createDate = createDate;
	}

	public String getLtsBlacklistInd() {
		return this.ltsBlacklistInd;
	}

	public void setLtsBlacklistInd(String pLtsBlacklistInd) {
		this.ltsBlacklistInd = pLtsBlacklistInd;
	}

	public String getBuildXy() {
		return buildXy;
	}

	public void setBuildXy(String buildXy) {
		this.buildXy = buildXy;
	}

	public String getDisFullAddrDescEn() {
		return disFullAddrDescEn;
	}

	public void setDisFullAddrDescEn(String disFullAddrDescEn) {
		this.disFullAddrDescEn = disFullAddrDescEn;
	}

	public String getDisFullAddrDescCh() {
		return disFullAddrDescCh;
	}

	public void setDisFullAddrDescCh(String disFullAddrDescCh) {
		this.disFullAddrDescCh = disFullAddrDescCh;
	}
	
}
