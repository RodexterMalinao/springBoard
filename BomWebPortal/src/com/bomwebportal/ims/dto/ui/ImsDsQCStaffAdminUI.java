package com.bomwebportal.ims.dto.ui;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ImsDsQCStaffAdminUI extends CcLtsImsOrderEnquiryUI implements Serializable{
	
	/**
	 * steven created this file 20131126
	 */
	private static final long serialVersionUID = 5096531629663008744L;
	protected final Log logger = LogFactory.getLog(getClass());
	
	private String lob;
	private String teamCode;
	private String dateType;
	private String startDate;
	private String endDate;
	private String orderId;
	private String docType;
	private String docNum;
	private String serNum;
	private String loginId;
	private String loginIdType;
	private String reset;
	private String orderStatus;
	private String teamSearch;
	private String salesNum;
	private String createStaff;
	private String searchTeamSelect;
	private String is3rdParty;
	
	
	private String controlStaffRatio;
	private String problemStaffRatio;
	private String cleanStaffRatio;
	
	
	public String getProblemStaffRatio() {
		return problemStaffRatio;
	}
	public void setProblemStaffRatio(String problemStaffRatio) {
		this.problemStaffRatio = problemStaffRatio;
	}
	public void setControlStaffRatio(String controlStaffRatio) {
		this.controlStaffRatio = controlStaffRatio;
	}
	public String getControlStaffRatio() {
		return controlStaffRatio;
	}
	
	
	
	
	public String getTeamSearch() {
		return teamSearch;
	}
	public void setTeamSearch(String teamSearch) {
		this.teamSearch = teamSearch;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
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
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDocType() {
		return docType;
	}
	public void setDocType(String docType) {
		this.docType = docType;
	}
	public String getDocNum() {
		return docNum;
	}
	public void setDocNum(String docNum) {
		this.docNum = docNum;
	}
	public String getSerNum() {
		return serNum;
	}
	public void setSerNum(String serNum) {
		this.serNum = serNum;
	}
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public String getLoginIdType() {
		return loginIdType;
	}
	public void setLoginIdType(String loginIdType) {
		this.loginIdType = loginIdType;
	}
	
	public void print(){
		logger.info("getLob:"+this.getLob());
		logger.info("getTeamCode:"+this.getTeamCode());
		logger.info("getDateType:"+this.getDateType());
		logger.info("getStartDate:"+this.getStartDate());
		logger.info("getEndDate:"+this.getEndDate());
		logger.info("getOrderId:"+this.getOrderId());
		logger.info("getDocType:"+this.getDocType());
		logger.info("getDocNum:"+this.getDocNum());
		logger.info("getSerNum:"+this.getSerNum());
		logger.info("getLoginId:"+this.getLoginId());
		logger.info("getLoginIdType:"+this.getLoginIdType());
		logger.info("getReset:"+this.getReset());
		logger.info("orderStatus:"+this.getOrderStatus());
	}
	public void setReset(String reset) {
		this.reset = reset;
	}
	public String getReset() {
		return reset;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setSearchTeamSelect(String searchTeamSelect) {
		this.searchTeamSelect = searchTeamSelect;
	}
	public String getSearchTeamSelect() {
		return searchTeamSelect;
	}
	public void setSalesNum(String salesNum) {
		this.salesNum = salesNum;
	}
	public String getSalesNum() {
		return salesNum;
	}
	public void setCreateStaff(String createStaff) {
		this.createStaff = createStaff;
	}
	public String getCreateStaff() {
		return createStaff;
	}
	public void setIs3rdParty(String is3rdParty) {
		this.is3rdParty = is3rdParty;
	}
	public String getIs3rdParty() {
		return is3rdParty;
	}
	public void setCleanStaffRatio(String cleanStaffRatio) {
		this.cleanStaffRatio = cleanStaffRatio;
	}
	public String getCleanStaffRatio() {
		return cleanStaffRatio;
	}

}
