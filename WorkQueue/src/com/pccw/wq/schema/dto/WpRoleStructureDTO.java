package com.pccw.wq.schema.dto;

import java.io.Serializable;

public class WpRoleStructureDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6816587430417795338L;
    private String channelId;
	private String channelCd;
	private String salesChannel;
	private String centreCd;
	private String teamCd;
	private String role;
	private String roleId;

	public String getChannelCd() {
		return this.channelCd;
	}

	public void setChannelCd(String pChannelCd) {
		this.channelCd = pChannelCd;
	}

	public String getSalesChannel() {
		return this.salesChannel;
	}

	public void setSalesChannel(String pSalesChannel) {
		this.salesChannel = pSalesChannel;
	}

	public String getCentreCd() {
		return this.centreCd;
	}

	public void setCentreCd(String pCentreCd) {
		this.centreCd = pCentreCd;
	}

	public String getTeamCd() {
		return this.teamCd;
	}

	public void setTeamCd(String pTeamCd) {
		this.teamCd = pTeamCd;
	}

	public String getRole() {
		return this.role;
	}

	public void setRole(String pRole) {
		this.role = pRole;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String pRoleId) {
		this.roleId = pRoleId;
	}

	public String getChannelId() {
		return this.channelId;
	}

	public void setChannelId(String pChannelId) {
		this.channelId = pChannelId;
	}
}
