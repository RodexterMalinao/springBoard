package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class CustAddrDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5641197112008026382L;
	
	private String OrderId;
	private String AddrUsage;
	private String Flat;
	private String AreaCd;
	private String DistNo;
	private String SectCd;
	private String StrName;
	private String HiLotNo;
	private String StrCatCd;
	private String BuildNo;
	private String ForeignAddrFlag;
	private String FloorNo;
	private String UnitNo;
	private String PoBoxNo;
	private String AddrType;
	private String StrNo;
	private String SectDepInd;	
	private String AreaDesc;
	private String DistDesc;
	private String SectDesc;
	private String StrCatDesc;
	private String Serbdyno;
	private String BlacklistInd;
	private String DtlId;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;

	
	private AddrInventoryDTO addrInventory;
	
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getAddrUsage() {
		return AddrUsage;
	}
	public void setAddrUsage(String addrUsage) {
		AddrUsage = addrUsage;
	}
	public String getFlat() {
		return Flat;
	}
	public void setFlat(String flat) {
		Flat = flat;
	}
	public String getAreaCd() {
		return AreaCd;
	}
	public void setAreaCd(String areaCd) {
		AreaCd = areaCd;
	}
	public String getDistNo() {
		return DistNo;
	}
	public void setDistNo(String distNo) {
		DistNo = distNo;
	}
	public String getSectCd() {
		return SectCd;
	}
	public void setSectCd(String sectCd) {
		SectCd = sectCd;
	}
	public String getStrName() {
		return StrName;
	}
	public void setStrName(String strName) {
		StrName = strName;
	}
	public String getHiLotNo() {
		return HiLotNo;
	}
	public void setHiLotNo(String hiLotNo) {
		HiLotNo = hiLotNo;
	}
	public String getStrCatCd() {
		return StrCatCd;
	}
	public void setStrCatCd(String strCatCd) {
		StrCatCd = strCatCd;
	}
	public String getBuildNo() {
		return BuildNo;
	}
	public void setBuildNo(String buildNo) {
		BuildNo = buildNo;
	}
	public String getForeignAddrFlag() {
		return ForeignAddrFlag;
	}
	public void setForeignAddrFlag(String foreignAddrFlag) {
		ForeignAddrFlag = foreignAddrFlag;
	}
	public String getFloorNo() {
		return FloorNo;
	}
	public void setFloorNo(String floorNo) {
		FloorNo = floorNo;
	}
	public String getUnitNo() {
		return UnitNo;
	}
	public void setUnitNo(String unitNo) {
		UnitNo = unitNo;
	}
	public String getPoBoxNo() {
		return PoBoxNo;
	}
	public void setPoBoxNo(String poBoxNo) {
		PoBoxNo = poBoxNo;
	}
	public String getAddrType() {
		return AddrType;
	}
	public void setAddrType(String addrType) {
		AddrType = addrType;
	}
	public String getStrNo() {
		return StrNo;
	}
	public void setStrNo(String strNo) {
		StrNo = strNo;
	}
	public String getSectDepInd() {
		return SectDepInd;
	}
	public void setSectDepInd(String sectDepInd) {
		SectDepInd = sectDepInd;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getAreaDesc() {
		return AreaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		AreaDesc = areaDesc;
	}
	public String getDistDesc() {
		return DistDesc;
	}
	public void setDistDesc(String distDesc) {
		DistDesc = distDesc;
	}
	public String getSectDesc() {
		return SectDesc;
	}
	public void setSectDesc(String sectDesc) {
		SectDesc = sectDesc;
	}
	public String getStrCatDesc() {
		return StrCatDesc;
	}
	public void setStrCatDesc(String strCatDesc) {
		StrCatDesc = strCatDesc;
	}
	public String getSerbdyno() {
		return Serbdyno;
	}
	public void setSerbdyno(String serbdyno) {
		Serbdyno = serbdyno;
	}
	public String getBlacklistInd() {
		return BlacklistInd;
	}
	public void setBlacklistInd(String blacklistInd) {
		BlacklistInd = blacklistInd;
	}
	public AddrInventoryDTO getAddrInventory() {
		return addrInventory;
	}
	public void setAddrInventory(AddrInventoryDTO addrInventory) {
		this.addrInventory = addrInventory;
	}
	public String getDtlId() {
		return DtlId;
	}
	public void setDtlId(String dtlId) {
		DtlId = dtlId;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}


	
}
