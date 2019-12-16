package com.bomwebportal.lts.dto.qc;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.ims.dto.ImsAlertMsgDTO;

public class LtsDsQcSearchCriteriaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5059860038393494316L;
	
	private String orderId;
	private String dateType;
	private String startDate;
	private String endDate;
	private String qcStatus;
	private String assignee;
	private String houseType;
	private String amendment;
	private String isThirdParty;
	private String payMethod;
	private String staffType;
	private String salesCode;
	private String teamCode;
	private String idDocNum;
	private String idDocType;
	private String isFilterAssigned;
	private List<String> orderStatusList;
	private List<String> salesChannel;
	private boolean isAssigned ;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDateType() {
		return dateType;
	}
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getQcStatus() {
		return qcStatus;
	}
	public void setQcStatus(String qcStatus) {
		this.qcStatus = qcStatus;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getHouseType() {
		return houseType;
	}
	public void setHouseType(String houseType) {
		this.houseType = houseType;
	}
	public String getAmendment() {
		return amendment;
	}
	public void setAmendment(String amendment) {
		this.amendment = amendment;
	}
	public String getIsThirdParty() {
		return isThirdParty;
	}
	public void setIsThirdParty(String isThirdParty) {
		this.isThirdParty = isThirdParty;
	}
	public String getPayMethod() {
		return payMethod;
	}
	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}
	public String getStaffType() {
		return staffType;
	}
	public void setStaffType(String staffType) {
		this.staffType = staffType;
	}
	public String getSalesCode() {
		return salesCode;
	}
	public void setSalesCode(String salesCode) {
		this.salesCode = salesCode;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	public String getIdDocType() {
		return idDocType;
	}
	public void setIdDocType(String idDocType) {
		this.idDocType = idDocType;
	}
	public String getIsFilterAssigned() {
		return isFilterAssigned;
	}
	public void setIsFilterAssigned(String isFilterAssigned) {
		this.isFilterAssigned = isFilterAssigned;
	}
	public List<String> getOrderStatusList() {
		return orderStatusList;
	}
	public void setOrderStatusList(List<String> orderStatusList) {
		this.orderStatusList = orderStatusList;
	}
	public List<String> getSalesChannel() {
		return salesChannel;
	}
	public void setSalesChannel(List<String> salesChannel) {
		this.salesChannel = salesChannel;
	}
	public boolean isAssigned() {
		return isAssigned;
	}
	public void setAssigned(boolean isAssigned) {
		this.isAssigned = isAssigned;
	}




}
