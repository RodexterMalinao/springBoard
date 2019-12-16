package com.bomwebportal.lts.dto.order;

import java.io.Serializable;

public class ImsSbOrderDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5728259036734050928L;
	
	private String retVal = null;
	
	private String errCode = null;
	
	private String errText = null;
	
	private String orderId = null;
	
	private String idDocType = null;
	
	private String idDocNum = null;
	
	private String serbdyno = null;
	
	private String unitNo = null;
	
	private String floorNo = null;
	
	private String hiLotNo = null;
	
	private String buildNo = null;
	
	private String strNo = null; 
	
	private String strName = null; 
	
	private String strCatCd = null;
	
	private String strCatDesc = null;
	
    private String sectCd = null;
    
    private String sectDesc = null;
    
    private String distNo = null;
    
    private String distDesc = null;
    
    private String areaCd = null;
 
    private String areaDesc = null;
    
    private String serialNum = null;
    
    private String appntStartTime = null;
    
    private String appntEndTime = null;
    
    private String instContactName = null;
  
    private String instContactNum = null;
    
    private String instSmsNum = null;
    
    private String srvType = null;
    
    private String bandwidth = null;
    
    private String technology = null;
    
    private String fullAddress;
    
    private String srvReqDate = null;
    
    private String appntType = null;

    private String hasRentalRouter = null;
    
    private String hasMeshRouter = null;
    
    private String hasBrmWifi = null;
    
    // For Validation
    private boolean iaNotMatch = false;
    private boolean allowConfirmSameIa = false;
    
    private boolean custNotMatch = false;
    private boolean allowConfirmSameCust = false;
    private String preInstallInd = null;

    
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String pOrderId) {
		this.orderId = pOrderId;
	}

	public String getIdDocType() {
		return this.idDocType;
	}

	public void setIdDocType(String pIdDocType) {
		this.idDocType = pIdDocType;
	}

	public String getIdDocNum() {
		return this.idDocNum;
	}

	public void setIdDocNum(String pIdDocNum) {
		this.idDocNum = pIdDocNum;
	}

	public String getSerbdyno() {
		return this.serbdyno;
	}

	public void setSerbdyno(String pSerbdyno) {
		this.serbdyno = pSerbdyno;
	}

	public String getUnitNo() {
		return this.unitNo;
	}

	public void setUnitNo(String pUnitNo) {
		this.unitNo = pUnitNo;
	}

	public String getFloorNo() {
		return this.floorNo;
	}

	public void setFloorNo(String pFloorNo) {
		this.floorNo = pFloorNo;
	}

	public String getHiLotNo() {
		return this.hiLotNo;
	}

	public void setHiLotNo(String pHiLotNo) {
		this.hiLotNo = pHiLotNo;
	}

	public String getBuildNo() {
		return this.buildNo;
	}

	public void setBuildNo(String pBuildNo) {
		this.buildNo = pBuildNo;
	}

	public String getStrNo() {
		return this.strNo;
	}

	public void setStrNo(String pStrNo) {
		this.strNo = pStrNo;
	}

	public String getStrName() {
		return this.strName;
	}

	public void setStrName(String pStrName) {
		this.strName = pStrName;
	}

	public String getStrCatCd() {
		return this.strCatCd;
	}

	public void setStrCatCd(String pStrCatCd) {
		this.strCatCd = pStrCatCd;
	}

	public String getStrCatDesc() {
		return this.strCatDesc;
	}

	public void setStrCatDesc(String pStrCatDesc) {
		this.strCatDesc = pStrCatDesc;
	}

	public String getSectCd() {
		return this.sectCd;
	}

	public void setSectCd(String pSectCd) {
		this.sectCd = pSectCd;
	}

	public String getSectDesc() {
		return this.sectDesc;
	}

	public void setSectDesc(String pSectDesc) {
		this.sectDesc = pSectDesc;
	}

	public String getDistNo() {
		return this.distNo;
	}

	public void setDistNo(String pDistNo) {
		this.distNo = pDistNo;
	}

	public String getDistDesc() {
		return this.distDesc;
	}

	public void setDistDesc(String pDistDesc) {
		this.distDesc = pDistDesc;
	}

	public String getAreaCd() {
		return this.areaCd;
	}

	public void setAreaCd(String pAreaCd) {
		this.areaCd = pAreaCd;
	}

	public String getAreaDesc() {
		return this.areaDesc;
	}

	public void setAreaDesc(String pAreaDesc) {
		this.areaDesc = pAreaDesc;
	}

	public String getSerialNum() {
		return this.serialNum;
	}

	public void setSerialNum(String pSerialNum) {
		this.serialNum = pSerialNum;
	}

	public String getAppntStartTime() {
		return this.appntStartTime;
	}

	public void setAppntStartTime(String pAppntStartTime) {
		this.appntStartTime = pAppntStartTime;
	}

	public String getAppntEndTime() {
		return this.appntEndTime;
	}

	public void setAppntEndTime(String pAppntEndTime) {
		this.appntEndTime = pAppntEndTime;
	}

	public String getInstContactName() {
		return this.instContactName;
	}

	public void setInstContactName(String pInstContactName) {
		this.instContactName = pInstContactName;
	}

	public String getInstContactNum() {
		return this.instContactNum;
	}

	public void setInstContactNum(String pInstContactNum) {
		this.instContactNum = pInstContactNum;
	}

	public String getInstSmsNum() {
		return this.instSmsNum;
	}

	public void setInstSmsNum(String pInstSmsNum) {
		this.instSmsNum = pInstSmsNum;
	}

	public String getSrvType() {
		return this.srvType;
	}

	public void setSrvType(String pSrvType) {
		this.srvType = pSrvType;
	}

	public String getRetVal() {
		return this.retVal;
	}

	public void setRetVal(String pRetVal) {
		this.retVal = pRetVal;
	}

	public String getErrCode() {
		return this.errCode;
	}

	public void setErrCode(String pErrCode) {
		this.errCode = pErrCode;
	}

	public String getErrText() {
		return this.errText;
	}

	public void setErrText(String pErrText) {
		this.errText = pErrText;
	}

	public boolean isIaNotMatch() {
		return iaNotMatch;
	}

	public void setIaNotMatch(boolean iaNotMatch) {
		this.iaNotMatch = iaNotMatch;
	}

	public boolean isCustNotMatch() {
		return custNotMatch;
	}

	public void setCustNotMatch(boolean custNotMatch) {
		this.custNotMatch = custNotMatch;
	}

	public boolean isAllowConfirmSameIa() {
		return allowConfirmSameIa;
	}

	public void setAllowConfirmSameIa(boolean allowConfirmSameIa) {
		this.allowConfirmSameIa = allowConfirmSameIa;
	}

	public boolean isAllowConfirmSameCust() {
		return allowConfirmSameCust;
	}

	public void setAllowConfirmSameCust(boolean allowConfirmSameCust) {
		this.allowConfirmSameCust = allowConfirmSameCust;
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

	public String getFullAddress() {
		return fullAddress;
	}

	public void setFullAddress(String fullAddress) {
		this.fullAddress = fullAddress;
	}

	public String getSrvReqDate() {
		return srvReqDate;
	}

	public void setSrvReqDate(String srvReqDate) {
		this.srvReqDate = srvReqDate;
	}

	public String getAppntType() {
		return appntType;
	}

	public void setAppntType(String appntType) {
		this.appntType = appntType;
	}

	public String getPreInstallInd() {
		return preInstallInd;
	}

	public void setPreInstallInd(String preInstallInd) {
		this.preInstallInd = preInstallInd;
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
