package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.Date;

public class ImsSbOrderDetailDTO implements Serializable{
//pkg_SB_IMS_LTS.get_pcd_sb_order
	/**
	 * 
	 */
	private static final long serialVersionUID = 4015529219894422066L;

	private String orderId;
	private String idDocType; 
	private String idDocNum; 
	private String serbdyno; 
	private String unitNo;
	private String floorNo;
	private String hiLotNo;
	private String buildNo;
	private String strNo;
	private String strName;
	private String strCatCd;
	private String strCatDesc;
	private String sectCd;
	private String sectDesc;
	private String distNo;
	private String distDesc;
	private String areaCd;
	private String areaDesc;
	private String serialNum;
	private Date appntStartTime;
	private Date appntEndTime;
	private String instContactName;
	private String instContactNum;
	private String instSmsNum;
	private String srvType;
	private String bandwidth;
	private String technology;
	private Date srvReqDate;
	private String appntType;
	private String hasDel;
	private String hasRentalRouter;
	private String hasMeshRouter;
	private String hasBrmWifi;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	public String getSerbdyno() {
		return serbdyno;
	}
	public void setSerbdyno(String serbdyno) {
		this.serbdyno = serbdyno;
	}
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	public String getFloorNo() {
		return floorNo;
	}
	public void setFloorNo(String floorNo) {
		this.floorNo = floorNo;
	}
	public String getHiLotNo() {
		return hiLotNo;
	}
	public void setHiLotNo(String hiLotNo) {
		this.hiLotNo = hiLotNo;
	}
	public String getBuildNo() {
		return buildNo;
	}
	public void setBuildNo(String buildNo) {
		this.buildNo = buildNo;
	}
	public String getStrNo() {
		return strNo;
	}
	public void setStrNo(String strNo) {
		this.strNo = strNo;
	}
	public String getStrName() {
		return strName;
	}
	public void setStrName(String strName) {
		this.strName = strName;
	}
	public String getStrCatCd() {
		return strCatCd;
	}
	public void setStrCatCd(String strCatCd) {
		this.strCatCd = strCatCd;
	}
	public String getStrCatDesc() {
		return strCatDesc;
	}
	public void setStrCatDesc(String strCatDesc) {
		this.strCatDesc = strCatDesc;
	}
	public String getSectCd() {
		return sectCd;
	}
	public void setSectCd(String sectCd) {
		this.sectCd = sectCd;
	}
	public String getSectDesc() {
		return sectDesc;
	}
	public void setSectDesc(String sectDesc) {
		this.sectDesc = sectDesc;
	}
	public String getDistNo() {
		return distNo;
	}
	public void setDistNo(String distNo) {
		this.distNo = distNo;
	}
	public String getDistDesc() {
		return distDesc;
	}
	public void setDistDesc(String distDesc) {
		this.distDesc = distDesc;
	}
	public String getAreaCd() {
		return areaCd;
	}
	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}
	public String getAreaDesc() {
		return areaDesc;
	}
	public void setAreaDesc(String areaDesc) {
		this.areaDesc = areaDesc;
	}
	public String getSerialNum() {
		return serialNum;
	}
	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}
	public Date getAppntStartTime() {
		return appntStartTime;
	}
	public void setAppntStartTime(Date appntStartTime) {
		this.appntStartTime = appntStartTime;
	}
	public Date getAppntEndTime() {
		return appntEndTime;
	}
	public void setAppntEndTime(Date appntEndTime) {
		this.appntEndTime = appntEndTime;
	}
	public String getInstContactName() {
		return instContactName;
	}
	public void setInstContactName(String instContactName) {
		this.instContactName = instContactName;
	}
	public String getInstContactNum() {
		return instContactNum;
	}
	public void setInstContactNum(String instContactNum) {
		this.instContactNum = instContactNum;
	}
	public String getInstSmsNum() {
		return instSmsNum;
	}
	public void setInstSmsNum(String instSmsNum) {
		this.instSmsNum = instSmsNum;
	}
	public String getSrvType() {
		return srvType;
	}
	public void setSrvType(String srvType) {
		this.srvType = srvType;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getTechnology() {
		return technology;
	}
	public void setTechnology(String technology) {
		this.technology = technology;
	}
	public Date getSrvReqDate() {
		return srvReqDate;
	}
	public void setSrvReqDate(Date srvReqDate) {
		this.srvReqDate = srvReqDate;
	}
	public String getAppntType() {
		return appntType;
	}
	public void setAppntType(String appntType) {
		this.appntType = appntType;
	}
	public String getHasDel() {
		return hasDel;
	}
	public void setHasDel(String hasDel) {
		this.hasDel = hasDel;
	}
	public String getHasRentalRouter() {
		return hasRentalRouter;
	}
	public void setHasRentalRouter(String hasRentalRouter) {
		this.hasRentalRouter = hasRentalRouter;
	}
	public String getHasMeshRouter() {
		return hasMeshRouter;
	}
	public void setHasMeshRouter(String hasMeshRouter) {
		this.hasMeshRouter = hasMeshRouter;
	}
	public String getHasBrmWifi() {
		return hasBrmWifi;
	}
	public void setHasBrmWifi(String hasBrmWifi) {
		this.hasBrmWifi = hasBrmWifi;
	}
	
	
}
