package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;

import org.openuri.www.CustomerBasicInfoDTO;
import org.openuri.www.ServiceLineDTO;

import com.bomwebportal.dto.CustomerOrderHistoryDTO;

public class ImsCustomerInformationDTO implements Serializable{

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
	private String emailAddress;
	
	private String imsLtsButtonInd;
	private String selectedNum; // for selected service in use dto
	private String selectedCust; // for selected basic customer info
	private String mobInd; // whether disable ims/lts options, mob exists, only mob options (in Basic Customer Information)
	private String srvLnMobInd; // in Service Line Information
	
	public String getSelectedNum() {
		return selectedNum;
	}
	public void setSelectedNum(String selectedNum) {
		this.selectedNum = selectedNum;
	}
	
	private String customerInformationError;
	
	// add by Joyce 20111031
	private String actionType;
	private List<CustomerBasicInfoDTO> custSearchResultList;
	private List<ServiceLineDTO> serviceLineDTOList;
	private List<ImsCustomerOrderHistoryDTO> custOrderHistoryList;
	
	private String errorSeverity;
	
	private String orderType;
	
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	// add by Joyce 20111123
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
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setCustOrderHistoryList(List<ImsCustomerOrderHistoryDTO> custOrderHistoryList) {
		this.custOrderHistoryList = custOrderHistoryList;
	}
	public List<ImsCustomerOrderHistoryDTO> getCustOrderHistoryList() {
		return custOrderHistoryList;
	}

}

