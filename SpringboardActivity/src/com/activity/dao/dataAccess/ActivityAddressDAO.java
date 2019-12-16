package com.activity.dao.dataAccess;

import com.pccw.util.db.DaoBaseImpl;
import com.pccw.util.db.stringOracleType.OraDate;
import com.pccw.util.db.stringOracleType.OraDateCreateDate;
import com.pccw.util.db.stringOracleType.OraDateLastUpdDate;
import com.pccw.util.db.stringOracleType.OraNumber;

public class ActivityAddressDAO extends DaoBaseImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2096878927998564166L;
	private OraNumber actvId; // SB_ACTV_ADDRESS.ACTV_ID
	private String areaCd; // SB_ACTV_ADDRESS.AREA_CD
	private String distNo; // SB_ACTV_ADDRESS.DIST_NO
	private String sectCd; // SB_ACTV_ADDRESS.SECT_CD
	private String strName; // SB_ACTV_ADDRESS.STR_NAME
	private String hiLotNo; // SB_ACTV_ADDRESS.HI_LOT_NO
	private String strCatCd; // SB_ACTV_ADDRESS.STR_CAT_CD
	private String buildNo; // SB_ACTV_ADDRESS.BUILD_NO
	private String floorNo; // SB_ACTV_ADDRESS.FLOOR_NO
	private String unitNo; // SB_ACTV_ADDRESS.UNIT_NO
	private String poBoxNo; // SB_ACTV_ADDRESS.PO_BOX_NO
	private String strNo; // SB_ACTV_ADDRESS.STR_NO
	private String areaDesc; // SB_ACTV_ADDRESS.AREA_DESC
	private String distDesc; // SB_ACTV_ADDRESS.DIST_DESC
	private String sectDesc; // SB_ACTV_ADDRESS.SECT_DESC
	private String strCatDesc; // SB_ACTV_ADDRESS.STR_CAT_DESC
	private String serbdyno; // SB_ACTV_ADDRESS.SERBDYNO
	private String hktPreInd; // SB_ACTV_ADDRESS.HKT_PRE_IND
	private String buildXy; //SB_ACTV_ADDRESS.BUILD_XY;
	private String addrUsage;// SB_ACTV_ADDRESS.ADDR_USAGE
	private String unitType;// SB_ACTV_ADDRESS.UNIT_TYPE
	private String createBy; // SB_ACTV_ADDRESS.CREATE_BY
	private OraDate createDate = new OraDateCreateDate(); // SB_ACTV_ADDRESS.CREATE_DATE
	private String lastUpdBy; // SB_ACTV_ADDRESS.LAST_UPD_BY
	private OraDate lastUpdDate = new OraDateLastUpdDate(); // SB_ACTV_ADDRESS.LAST_UPD_DATE
	
	public ActivityAddressDAO() {
		primaryKeyFields = new String[] {};
	}

	public String getTableName() {
		return "SB_ACTV_ADDRESS";
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

	public String getCreateBy() {
		return this.createBy;
	}

	public String getLastUpdBy() {
		return this.lastUpdBy;
	}

	public String getBuildXy() {
		return this.buildXy;
	}

	public String getUnitType() {
		return this.unitType;
	}

	public String getActvId() {
		return this.actvId != null ? this.actvId.toString() : null;
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

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}

	public void setLastUpdBy(String pLastUpdBy) {
		this.lastUpdBy = pLastUpdBy;
	}

	public void setBuildXy(String pBuildXy) {
		this.buildXy = pBuildXy;
	}

	public String getHktPreInd() {
		return this.hktPreInd;
	}

	public void setHktPreInd(String pHktPreInd) {
		this.hktPreInd = pHktPreInd;
	}

	public void setUnitType(String pUnitType) {
		this.unitType = pUnitType;
	}

	public void setActvId(String pActvId) {
		this.actvId = new OraNumber(pActvId);
	}

	public void setCreateDate(String pCreateDate) {
		this.createDate = new OraDateCreateDate(pCreateDate);
	}

	public void setLastUpdDate(String pLastUpdDate) {
		this.lastUpdDate = new OraDateLastUpdDate(pLastUpdDate);
	}
}
