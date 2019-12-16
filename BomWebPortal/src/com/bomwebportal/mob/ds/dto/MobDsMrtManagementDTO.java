package com.bomwebportal.mob.ds.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MobDsMrtManagementDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5025231986882875700L;
	
	private String msisdn;
	private String msisdnlvl;
	private String msisdnStatus;
	private String msisdnStatusDesc;
	private String channelCd;
	private String storeCode; //centreCd
	private String teamCode;
	private String staffId;
	private String numType;
	private String actionBy;
	private String reserveId;
	private String srcDeptCd;
	private Date targetReleaseDate;
	private Date stockInDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	private String createBy;
	private Date createDate;
	private String orderId;
	
	//for search function
	private String searchMrt;
	private List<String> searchMsisdnlvl;
	private String searchStoreCode;
	private String searchTeamCode;
	private String searchStaffId;
	private String searchStatus;
	private String searchNumType;
	
	private List<String> selectMsisdn;
	
	private String actionType;
	private List<MobDsMrtManagementDTO> mrtSummaryList;
	
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getMsisdnlvl() {
		return msisdnlvl;
	}
	public void setMsisdnlvl(String msisdnlvl) {
		this.msisdnlvl = msisdnlvl;
	}
	public String getMsisdnStatus() {
		return msisdnStatus;
	}
	public void setMsisdnStatus(String msisdnStatus) {
		this.msisdnStatus = msisdnStatus;
	}
	public String getMsisdnStatusDesc() {
		return msisdnStatusDesc;
	}
	public void setMsisdnStatusDesc(String msisdnStatusDesc) {
		this.msisdnStatusDesc = msisdnStatusDesc;
	}
	public String getChannelCd() {
		return channelCd;
	}
	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}
	public String getStoreCode() {
		return storeCode;
	}
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}
	public String getTeamCode() {
		return teamCode;
	}
	public void setTeamCode(String teamCode) {
		this.teamCode = teamCode;
	}
	public String getStaffId() {
		return staffId;
	}
	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}
	public String getNumType() {
		return numType;
	}
	public void setNumType(String numType) {
		this.numType = numType;
	}
	public String getActionBy() {
		return actionBy;
	}
	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}
	public String getReserveId() {
		return reserveId;
	}
	public void setReserveId(String reserveId) {
		this.reserveId = reserveId;
	}
	public String getSrcDeptCd() {
		return srcDeptCd;
	}
	public void setSrcDeptCd(String srcDeptCd) {
		this.srcDeptCd = srcDeptCd;
	}
	public Date getTargetReleaseDate() {
		return targetReleaseDate;
	}
	public void setTargetReleaseDate(Date targetReleaseDate) {
		this.targetReleaseDate = targetReleaseDate;
	}
	
	public Date getStockInDate() {
		return stockInDate;
	}
	public void setStockInDate(Date stockInDate) {
		this.stockInDate = stockInDate;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSearchMrt() {
		return searchMrt;
	}
	public void setSearchMrt(String searchMrt) {
		this.searchMrt = searchMrt;
	}
	public List<String> getSearchMsisdnlvl() {
		return searchMsisdnlvl;
	}
	public void setSearchMsisdnlvl(List<String> searchMsisdnlvl) {
		this.searchMsisdnlvl = searchMsisdnlvl;
	}
	public String getSearchStoreCode() {
		return searchStoreCode;
	}
	public void setSearchStoreCode(String searchStoreCode) {
		this.searchStoreCode = searchStoreCode;
	}
	public String getSearchTeamCode() {
		return searchTeamCode;
	}
	public void setSearchTeamCode(String searchTeamCode) {
		this.searchTeamCode = searchTeamCode;
	}
	public String getSearchStaffId() {
		return searchStaffId;
	}
	public void setSearchStaffId(String searchStaffId) {
		this.searchStaffId = searchStaffId;
	}
	public String getSearchStatus() {
		return searchStatus;
	}
	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}
	public String getSearchNumType() {
		return searchNumType;
	}
	public void setSearchNumType(String searchNumType) {
		this.searchNumType = searchNumType;
	}
	public List<String> getSelectMsisdn() {
		return selectMsisdn;
	}
	public void setSelectMsisdn(List<String> selectMsisdn) {
		this.selectMsisdn = selectMsisdn;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public List<MobDsMrtManagementDTO> getMrtSummaryList() {
		return mrtSummaryList;
	}
	public void setMrtSummaryList(List<MobDsMrtManagementDTO> mrtSummaryList) {
		this.mrtSummaryList = mrtSummaryList;
	}
}
