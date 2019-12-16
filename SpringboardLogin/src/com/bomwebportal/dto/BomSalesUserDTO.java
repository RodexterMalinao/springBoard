package com.bomwebportal.dto;

public class BomSalesUserDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 402310538607012795L;

	private String username;
	private String password;
	private String shopCd;

	// add by Joyce 20111026
	private String areaCd;
	private String channelCd;
	private String reportLine;
	private String shopEmailAddr;
	private String shopContactPhone;

	// add by Joyce 20111108
	// for change shop code page use
	private String actionType;

	// add by Joyce 20111111
	// use ssm error text as error message prompt to users
	private String errMsg;

	// add by Joyce 20111122
	// for distinguish Retail & CCS
	private int channelId;
	private String staffName;
	private String salesCd;
	private String category;
	private String boc;

	// add by Joyce 20111215
	private String pilotStatus; // for IMS test shop

	private String ltsPilotStatus;

	private String mobPilotStatus; // for MOBCCS

	// add by nancy 20131111
	private String bomShopCd;

	private String orgStaffId;
	
	//added by Andy 20141007
	private String salesType;
	private String location;
	

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if (username != null) {
			this.username = username.toUpperCase();
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAreaCd() {
		return areaCd;
	}

	public void setAreaCd(String areaCd) {
		this.areaCd = areaCd;
	}

	public String getChannelCd() {
		return channelCd;
	}

	public void setChannelCd(String channelCd) {
		this.channelCd = channelCd;
	}

	public String getReportLine() {
		return reportLine;
	}

	public void setReportLine(String reportLine) {
		this.reportLine = reportLine;
	}

	public String getShopEmailAddr() {
		return shopEmailAddr;
	}

	public void setShopEmailAddr(String emailAddr) {
		this.shopEmailAddr = emailAddr;
	}

	public String getShopContactPhone() {
		return shopContactPhone;
	}

	public void setShopContactPhone(String contactPhone) {
		this.shopContactPhone = contactPhone;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public void setChannelId(int channelId) {
		this.channelId = channelId;
	}

	public int getChannelId() {
		return channelId;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}

	public String getSalesCd() {
		return salesCd;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return category;
	}

	public void setBoc(String boc) {
		this.boc = boc;
	}

	public String getBoc() {
		return boc;
	}

	public void setPilotStatus(String pilotStatus) {
		this.pilotStatus = pilotStatus;
	}

	public String getPilotStatus() {
		return pilotStatus;
	}

	public String getLtsPilotStatus() {
		return ltsPilotStatus;
	}

	public void setLtsPilotStatus(String ltsPilotStatus) {
		this.ltsPilotStatus = ltsPilotStatus;
	}

	public String getMobPilotStatus() {
		return mobPilotStatus;
	}

	public void setMobPilotStatus(String mobPilotStatus) {
		this.mobPilotStatus = mobPilotStatus;
	}

	public String getBomShopCd() {
		return bomShopCd;
	}

	public void setBomShopCd(String bomShopCd) {
		this.bomShopCd = bomShopCd;
	}

	public String getOrgStaffId() {
		return this.orgStaffId;
	}

	public void setOrgStaffId(String pOrgStaffId) {
		this.orgStaffId = pOrgStaffId;
	}


	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocation() {
		return location;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public String getSalesType() {
		return salesType;
	}

	
}
