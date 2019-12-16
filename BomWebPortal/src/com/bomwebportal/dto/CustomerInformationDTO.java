package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.openuri.www.CustomerBasicInfoDTO;
import org.openuri.www.ServiceLineDTO;

import com.bomwebportal.ims.dto.ChannelDetailDTO;
import com.bomwebportal.ims.dto.Ims1AMSInfoWithoutPendingDTO;
import com.bomwebportal.ims.dto.NtvInfo;

public class CustomerInformationDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8833455545126437745L;
	private String idDocType;
	private String idDocNum;
	private String serviceNumberType;
	private String serviceNumber;
	private String loginIdPrefix;
	private String loginIdSuffix;
	
	private String imsLtsButtonInd;
	private String ltsButtonInd;
	private String selectedNum; // for selected service in use dto
	private String selectedCust; // for selected basic customer info
	private String mobInd; // whether disable ims/lts options, mob exists, only mob options (in Basic Customer Information)
	private String srvLnMobInd; // in Service Line Information
	
	//IMS Direct Sales
	private String companyName;
	private String familyName;
	private String givenName;
	
	private List<Ims1AMSInfoWithoutPendingDTO> serviceLine1amsList;
	private Map<String, List<ChannelDetailDTO>> channelDetailList;
	private NtvInfo ntvInfo;
	private Boolean isDS;
	
	public String getSelectedNum() {
		return selectedNum;
	}
	public void setSelectedNum(String selectedNum) {
		this.selectedNum = selectedNum;
	}
	
	private String customerInformationError;
	
	private String actionType;
	private List<CustomerBasicInfoDTO> custSearchResultList;
	private List<ServiceLineDTO> serviceLineDTOList;
	private List<CustomerOrderHistoryDTO> custOrderHistoryList;
	
	private String errorSeverity;
	
	// Add by Felix SF Cheung 20150521 for LTS DS
	private BomCustomerVerificationDTO bomVerifiedCustomerResult;
	// Add by Felix SF Cheung 20150521 for LTS DS
	
	
//	private List<String> fullAddressList;
//	
//	public List<String> getFullAddressList() {
//		return fullAddressList;
//	}
//	public void setFullAddressList(List<String> fullAddressList) {
//		this.fullAddressList = fullAddressList;
//	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public List<CustomerBasicInfoDTO> getCustSearchResultList() {
		return custSearchResultList;
	}
	public void setCustSearchResultList(
			List<CustomerBasicInfoDTO> custSearchResultList) {
		this.custSearchResultList = custSearchResultList;
	}
	public List<ServiceLineDTO> getServiceLineDTOList() {
		return serviceLineDTOList;
	}
	public void setServiceLineDTOList(List<ServiceLineDTO> serviceLineDTOList) {
		this.serviceLineDTOList = serviceLineDTOList;
	}
	public List<CustomerOrderHistoryDTO> getCustOrderHistoryList() {
		return custOrderHistoryList;
	}
	public void setCustOrderHistoryList(
			List<CustomerOrderHistoryDTO> custOrderHistoryList) {
		this.custOrderHistoryList = custOrderHistoryList;
	}
	public String getCustomerInformationError() {
		return customerInformationError;
	}
	public void setCustomerInformationError(String customerInformationError) {
		this.customerInformationError = customerInformationError;
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
	public String getServiceNumberType() {
		return serviceNumberType;
	}
	public void setServiceNumberType(String serviceNumberType) {
		this.serviceNumberType = serviceNumberType;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getLoginIdPrefix() {
		return loginIdPrefix;
	}
	public void setLoginIdPrefix(String loginIdPrefix) {
		this.loginIdPrefix = loginIdPrefix;
	}
	public String getLoginIdSuffix() {
		return loginIdSuffix;
	}
	public void setLoginIdSuffix(String loginIdSuffix) {
		this.loginIdSuffix = loginIdSuffix;
	}
	public void setImsLtsButtonInd(String imsLtsButtonInd) {
		this.imsLtsButtonInd = imsLtsButtonInd;
	}
	public String getImsLtsButtonInd() {
		return imsLtsButtonInd;
	}
	public String getLtsButtonInd() {
		return ltsButtonInd;
	}
	public void setLtsButtonInd(String ltsButtonInd) {
		this.ltsButtonInd = ltsButtonInd;
	}
	public void setErrorSeverity(String errorSeverity) {
		this.errorSeverity = errorSeverity;
	}
	public String getErrorSeverity() {
		return errorSeverity;
	}
	public void setSelectedCust(String selectedCust) {
		this.selectedCust = selectedCust;
	}
	public String getSelectedCust() {
		return selectedCust;
	}
	public void setMobInd(String mobInd) {
		this.mobInd = mobInd;
	}
	public String getMobInd() {
		return mobInd;
	}
	public String getSrvLnMobInd() {
		return srvLnMobInd;
	}
	public void setSrvLnMobInd(String srvLnMobInd) {
		this.srvLnMobInd = srvLnMobInd;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getFamilyName() {
		return familyName;
	}
	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}
	public String getGivenName() {
		return givenName;
	}
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}
	public BomCustomerVerificationDTO getBomVerifiedCustomerResult() {
		return bomVerifiedCustomerResult;
	}
	public void setBomVerifiedCustomerResult(
			BomCustomerVerificationDTO bomVerifiedCustomerResult) {
		this.bomVerifiedCustomerResult = bomVerifiedCustomerResult;
	}
	public void setServiceLine1amsList(List<Ims1AMSInfoWithoutPendingDTO> serviceLine1amsList) {
		this.serviceLine1amsList = serviceLine1amsList;
	}
	public List<Ims1AMSInfoWithoutPendingDTO> getServiceLine1amsList() {
		return serviceLine1amsList;
	}
	public void setChannelDetailList(Map<String, List<ChannelDetailDTO>> channelDetailList) {
		this.channelDetailList = channelDetailList;
	}
	public Map<String, List<ChannelDetailDTO>> getChannelDetailList() {
		return channelDetailList;
	}
	public void setNtvInfo(NtvInfo ntvInfo) {
		this.ntvInfo = ntvInfo;
	}
	public NtvInfo getNtvInfo() {
		return ntvInfo;
	}
	public void setIsDS(Boolean isDS) {
		this.isDS = isDS;
	}
	public Boolean getIsDS() {
		return isDS;
	}
}
