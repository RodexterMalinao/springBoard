package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class ServiceGroupMemberProfileDTO implements Serializable {

	private static final long serialVersionUID = -7196827443697419477L;
	
	private String datCd = null;
	private String srvNum = null;
	private String ccSrvId = null;

	
	public String getDatCd() {
		return datCd;
	}

	public void setDatCd(String datCd) {
		this.datCd = datCd;
	}

	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getCcSrvId() {
		return ccSrvId;
	}

	public void setCcSrvId(String ccSrvId) {
		this.ccSrvId = ccSrvId;
	}
}
