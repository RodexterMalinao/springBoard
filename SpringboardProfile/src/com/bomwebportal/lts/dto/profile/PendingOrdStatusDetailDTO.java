package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class PendingOrdStatusDetailDTO implements Serializable {

	private static final long serialVersionUID = -945694225816197995L;
	
	private String ocid = null;
	private String dtlId = null;
	private String bomStatus = null;
	private String legacyStatus = null;
	private String srd = null;
	private String orderType = null;

	public String getOcid() {
		return this.ocid;
	}

	public void setOcid(String pOcid) {
		this.ocid = pOcid;
	}

	public String getDtlId() {
		return this.dtlId;
	}

	public void setDtlId(String pDtlId) {
		this.dtlId = pDtlId;
	}

	public String getBomStatus() {
		return this.bomStatus;
	}

	public void setBomStatus(String pBomStatus) {
		this.bomStatus = pBomStatus;
	}

	public String getLegacyStatus() {
		return this.legacyStatus;
	}

	public void setLegacyStatus(String pLegacyStatus) {
		this.legacyStatus = pLegacyStatus;
	}

	public String getSrd() {
		return this.srd;
	}

	public void setSrd(String pSrd) {
		this.srd = pSrd;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String pOrderType) {
		this.orderType = pOrderType;
	}
}
