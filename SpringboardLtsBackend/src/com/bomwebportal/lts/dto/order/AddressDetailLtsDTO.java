package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class AddressDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -45869385237228616L;

	private String addrUsage = null;
	private String areaCd = null;
	private String distNo = null;
	private String sectCd = null;
	private String strName = null;
	private String hiLotNo = null;
	private String strCatCd = null;
	private String buildNo = null;
	private String foreignAddrFlag = null;
	private String floorNo = null;
	private String unitNo = null;
	private String poBoxNo = null;
	private String addrType = null;
	private String strNo = null;
	private String sectDepInd = null;
	private String areaDesc = null;
	private String distDesc = null;
	private String sectDesc = null;
	private String strCatDesc = null;
	private String serbdyno = null;
	private String blacklistInd = null;
	private String ltsBlacklistInd = null;
	private String buildXy; //BUILD_XY
	private String disFullAddrDescEn; // DIS_FULL_ADDR_DESC_EN
	private String disFullAddrDescCh; // DIS_FULL_ADDR_DESC_CH
	
	private AddressInventoryDetailLtsDTO addrInventory = null;

	
	public String getAddrUsage() {
		return addrUsage;
	}

	public void setAddrUsage(String addrUsage) {
		this.addrUsage = addrUsage;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getDistNo() {
		return distNo;
	}

	public void setDistNo(String distNo) {
		this.distNo = distNo;
	}

	public String getSectCd() {
		return sectCd;
	}

	public void setSectCd(String sectCd) {
		this.sectCd = sectCd;
	}

	public String getStrName() {
		return strName;
	}

	public void setStrName(String strName) {
		this.strName = strName;
	}

	public String getHiLotNo() {
		return hiLotNo;
	}

	public void setHiLotNo(String hiLotNo) {
		this.hiLotNo = hiLotNo;
	}

	public String getStrCatCd() {
		return strCatCd;
	}

	public void setStrCatCd(String strCatCd) {
		this.strCatCd = strCatCd;
	}

	public String getBuildNo() {
		return buildNo;
	}

	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}

	public String getForeignAddrFlag() {
		return foreignAddrFlag;
	}

	public void setForeignAddrFlag(String foreignAddrFlag) {
		this.foreignAddrFlag = foreignAddrFlag;
	}

	public String getFloorNo() {
		return floorNo;
	}

	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getPoBoxNo() {
		return poBoxNo;
	}

	public void setPoBoxNo(String poBoxNo) {
		this.poBoxNo = poBoxNo;
	}

	public String getAddrType() {
		return addrType;
	}

	public void setAddrType(String addrType) {
		this.addrType = addrType;
	}

	public String getStrNo() {
		return strNo;
	}

	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}

	public String getSectDepInd() {
		return sectDepInd;
	}

	public void setSectDepInd(String sectDepInd) {
		this.sectDepInd = sectDepInd;
	}

	public String getAreaDesc() {
		return areaDesc;
	}

	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}

	public String getDistDesc() {
		return distDesc;
	}

	public void setDistDesc(String distDesc) {
		this.distDesc = distDesc;
	}

	public String getSectDesc() {
		return sectDesc;
	}

	public void setSectDesc(String sectDesc) {
		this.sectDesc = sectDesc;
	}

	public String getStrCatDesc() {
		return strCatDesc;
	}

	public void setStrCatDesc(String strCatDesc) {
		this.strCatDesc = strCatDesc;
	}

	public String getSerbdyno() {
		return serbdyno;
	}

	public void setSerbdyno(String serbdyno) {
		this.serbdyno = serbdyno;
	}

	public String getBlacklistInd() {
		return blacklistInd;
	}

	public void setBlacklistInd(String blacklistInd) {
		this.blacklistInd = blacklistInd;
	}

	public AddressInventoryDetailLtsDTO getAddrInventory() {
		return addrInventory;
	}

	public void setAddrInventory(AddressInventoryDetailLtsDTO addrInventory) {
		this.addrInventory = addrInventory;
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
