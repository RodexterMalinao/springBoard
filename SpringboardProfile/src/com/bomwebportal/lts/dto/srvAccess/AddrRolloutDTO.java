package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;

public class AddrRolloutDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 72614897874921146L;

	
	private String iFlat = null;
	private String iFloor = null;
	private String iServiceBoundary = null;     
	private String oCoverEyex = null;           
	private String oCoverPe = null;            
	private String oIsPh = null;                
	private String oN2Building = null;         
	private String oFieldPermitDay = null;    
	private String oIsFiberBlockage = null;    
	private String oIsFttcInd = null;          
	private String oIsBlacklistAddr = null;
	private String oPonVillaInd = null;
	private String oAddrTagCd = null;
	private TechnologyDTO[] technologyDTO= null;      
	private String gnRetVal = null;               
	private String gnErrCode = null;              
	private String gsErrText = null;
	
	private String oHousingType = null;
	private String oHousingTypeCd = null;
	private String oHousingTypeDesc = null;
	
	public String getoAddrTagCd() {
		return oAddrTagCd;
	}
	public void setoAddrTagCd(String oAddrTagCd) {
		this.oAddrTagCd = oAddrTagCd;
	}
	public TechnologyDTO[] getTechnologyDTO() {
		return this.technologyDTO;
	}
	public void setTechnologyDTO(TechnologyDTO[] pTechnologyDTO) {
		this.technologyDTO = pTechnologyDTO;
	}
	public String getiFlat() {
		return this.iFlat;
	}
	public void setiFlat(String pIFlat) {
		this.iFlat = pIFlat;
	}
	public String getiFloor() {
		return this.iFloor;
	}
	public void setiFloor(String pIFloor) {
		this.iFloor = pIFloor;
	}
	public String getiServiceBoundary() {
		return this.iServiceBoundary;
	}
	public void setiServiceBoundary(String pIServiceBoundary) {
		this.iServiceBoundary = pIServiceBoundary;
	}
	public String getoCoverEyex() {
		return this.oCoverEyex;
	}
	public void setoCoverEyex(String pOCoverEyex) {
		this.oCoverEyex = pOCoverEyex;
	}
	public String getoCoverPe() {
		return this.oCoverPe;
	}
	public void setoCoverPe(String pOCoverPe) {
		this.oCoverPe = pOCoverPe;
	}
	public String getoIsPh() {
		return this.oIsPh;
	}
	public void setoIsPh(String pOIsPh) {
		this.oIsPh = pOIsPh;
	}
	public String getoN2Building() {
		return this.oN2Building;
	}
	public void setoN2Building(String pON2Building) {
		this.oN2Building = pON2Building;
	}
	public String getoFieldPermitDay() {
		return this.oFieldPermitDay;
	}
	public void setoFieldPermitDay(String pOFieldPermitDay) {
		this.oFieldPermitDay = pOFieldPermitDay;
	}
	public String getoIsFiberBlockage() {
		return this.oIsFiberBlockage;
	}
	public void setoIsFiberBlockage(String pOIsFiberBlockage) {
		this.oIsFiberBlockage = pOIsFiberBlockage;
	}
	public String getoIsFttcInd() {
		return this.oIsFttcInd;
	}
	public void setoIsFttcInd(String pOIsFttcInd) {
		this.oIsFttcInd = pOIsFttcInd;
	}
	public String getoIsBlacklistAddr() {
		return this.oIsBlacklistAddr;
	}
	public void setoIsBlacklistAddr(String pOIsBlacklistAddr) {
		this.oIsBlacklistAddr = pOIsBlacklistAddr;
	}
	public String getoPonVillaInd() {
		return oPonVillaInd;
	}
	public void setoPonVillaInd(String oPonVillaInd) {
		this.oPonVillaInd = oPonVillaInd;
	}
	public String getGnRetVal() {
		return this.gnRetVal;
	}
	public void setGnRetVal(String pGnRetVal) {
		this.gnRetVal = pGnRetVal;
	}
	public String getGnErrCode() {
		return this.gnErrCode;
	}
	public void setGnErrCode(String pGnErrCode) {
		this.gnErrCode = pGnErrCode;
	}
	public String getGsErrText() {
		return this.gsErrText;
	}
	public void setGsErrText(String pGsErrText) {
		this.gsErrText = pGsErrText;
	}
	public String getoHousingType() {
		return oHousingType;
	}
	public void setoHousingType(String oHousingType) {
		this.oHousingType = oHousingType;
	}
	public String getoHousingTypeCd() {
		return oHousingTypeCd;
	}
	public void setoHousingTypeCd(String oHousingTypeCd) {
		this.oHousingTypeCd = oHousingTypeCd;
	}
	public String getoHousingTypeDesc() {
		return oHousingTypeDesc;
	}
	public void setoHousingTypeDesc(String oHousingTypeDesc) {
		this.oHousingTypeDesc = oHousingTypeDesc;
	}
	
	
	
}
