package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

import com.bomwebportal.lts.dto.profile.AddressDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.ItemDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.profile.OfferDetailProfileLtsDTO;

public class FsaServiceDetailOutputDTO implements Serializable {

	private static final long serialVersionUID = -2564997365751830415L;

	private String errorCode = null;
	private String errorDesc = null;
	private String fsa = null;
	private String existingSrv = null;
	private String loginID = null;
	private String effStratDate = null;
	private String addressID = null;
	private String bandwidth = null;
	private String pendingOcid = null;
	private String pendingOrderType = null;
	private String pendingOrderSrd = null;
	private String docNum = null;
	private String docType = null;
	private String custLastName = null;
	private String custFirstName = null;
	private String isEye = null;
	private String Technology = null;
	private String isTos = null;
	private AddressDetailProfileLtsDTO AddressDtl = null;
	private String bomCustNum = null;
	private String serviceId = null;
	private String tenure = null;
	private String DeactNowtvInd = null;
	private String existModem = null;
	private String srvType = null;
	private String existMirrorInd = null; 
	private OfferDetailProfileLtsDTO[] offers = null;
	private ItemDetailProfileLtsDTO[] items = null;
	private boolean hdReady = false;
	private String noEyeInd = null; // Cannot share with eye service

	public String getDeactNowtvInd() {
		return this.DeactNowtvInd;
	}

	public void setDeactNowtvInd(String pDeactNowtvInd) {
		this.DeactNowtvInd = pDeactNowtvInd;
	}

	public AddressDetailProfileLtsDTO getAddressDtl() {
		return this.AddressDtl;
	}

	public void setAddressDtl(AddressDetailProfileLtsDTO pAddressDtl) {
		this.AddressDtl = pAddressDtl;
	}

	public String getBomCustNum() {
		return this.bomCustNum;
	}

	public void setBomCustNum(String pBomCustNum) {
		this.bomCustNum = pBomCustNum;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String pServiceId) {
		this.serviceId = pServiceId;
	}

	public String getTenure() {
		return this.tenure;
	}

	public void setTenure(String pTenure) {
		this.tenure = pTenure;
	}

	public String getFsa() {
		return this.fsa;
	}

	public void setFsa(String pFsa) {
		this.fsa = pFsa;
	}

	public String getExistingSrv() {
		return this.existingSrv;
	}

	public void setExistingSrv(String pExistingSrv) {
		this.existingSrv = pExistingSrv;
	}

	public String getLoginID() {
		return this.loginID;
	}

	public void setLoginID(String pLoginID) {
		this.loginID = pLoginID;
	}

	public String getEffStratDate() {
		return this.effStratDate;
	}

	public void setEffStratDate(String pEffStratDate) {
		this.effStratDate = pEffStratDate;
	}

	public String getAddressID() {
		return this.addressID;
	}

	public void setAddressID(String pAddressID) {
		this.addressID = pAddressID;
	}

	public String getBandwidth() {
		return this.bandwidth;
	}

	public void setBandwidth(String pBandwidth) {
		this.bandwidth = pBandwidth;
	}

	public String getPendingOcid() {
		return this.pendingOcid;
	}

	public void setPendingOcid(String pPendingOcid) {
		this.pendingOcid = pPendingOcid;
	}

	public String getPendingOrderType() {
		return this.pendingOrderType;
	}

	public void setPendingOrderType(String pPendingOrderType) {
		this.pendingOrderType = pPendingOrderType;
	}

	public String getDocNum() {
		return this.docNum;
	}

	public void setDocNum(String pDocNum) {
		this.docNum = pDocNum;
	}

	public String getDocType() {
		return this.docType;
	}

	public void setDocType(String pDocType) {
		this.docType = pDocType;
	}

	public String getCustLastName() {
		return this.custLastName;
	}

	public void setCustLastName(String pCustLastName) {
		this.custLastName = pCustLastName;
	}

	public String getCustFirstName() {
		return this.custFirstName;
	}

	public void setCustFirstName(String pCustFirstName) {
		this.custFirstName = pCustFirstName;
	}

	public String getIsEye() {
		return this.isEye;
	}

	public void setIsEye(String pIsEye) {
		this.isEye = pIsEye;
	}

	public String getTechnology() {
		return this.Technology;
	}

	public void setTechnology(String pTechnology) {
		this.Technology = pTechnology;
	}

	public String getIsTos() {
		return this.isTos;
	}

	public void setIsTos(String pIsTos) {
		this.isTos = pIsTos;
	}

	public String getExistModem() {
		return existModem;
	}

	public void setExistModem(String existModem) {
		this.existModem = existModem;
	}

	public String getSrvType() {
		return srvType;
	}

	public void setSrvType(String srvType) {
		this.srvType = srvType;
	}

	public String getErrorCode() {
		return this.errorCode;
	}

	public void setErrorCode(String pErrorCode) {
		this.errorCode = pErrorCode;
	}

	public String getErrorDesc() {
		return this.errorDesc;
	}

	public void setErrorDesc(String pErrorDesc) {
		this.errorDesc = pErrorDesc;
	}

	public String getPendingOrderSrd() {
		return pendingOrderSrd;
	}

	public void setPendingOrderSrd(String pendingOrderSrd) {
		this.pendingOrderSrd = pendingOrderSrd;
	}

	public OfferDetailProfileLtsDTO[] getOffers() {
		return offers;
	}

	public void setOffers(OfferDetailProfileLtsDTO[] offers) {
		this.offers = offers;
	}

	public ItemDetailProfileLtsDTO[] getItems() {
		return items;
	}

	public void setItems(ItemDetailProfileLtsDTO[] items) {
		this.items = items;
	}

	public String getExistMirrorInd() {
		return existMirrorInd;
	}

	public void setExistMirrorInd(String existMirrorInd) {
		this.existMirrorInd = existMirrorInd;
	}

	public boolean isHdReady() {
		return hdReady;
	}

	public void setHdReady(boolean hdReady) {
		this.hdReady = hdReady;
	}

	public String getNoEyeInd() {
		return noEyeInd;
	}

	public void setNoEyeInd(String noEyeInd) {
		this.noEyeInd = noEyeInd;
	}

	
}
