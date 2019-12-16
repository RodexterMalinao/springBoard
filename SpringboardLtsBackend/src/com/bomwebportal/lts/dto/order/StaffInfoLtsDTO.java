package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class StaffInfoLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -6914753330056819727L;

	private String salesType = null;
	private String salesCd = null;
	private String salesName = null;
	private String refSalesType = null;
	private String refSalesCd = null;
	private String refSalesName = null;
	private String position = null;
	private String callDate = null;
	private String callList = null;
	private String callListDesc = null;
	private String contactPhone = null;
	private String centreCd = null;
	private String teamCd = null;
	private String refCentreCd = null;
	private String refTeamCd = null;

	
	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salesType) {
		this.salesType = salesType;
	}

	public String getSalesCd() {
		return salesCd;
	}

	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getRefSalesType() {
		return refSalesType;
	}

	public void setRefSalesType(String refSalesType) {
		this.refSalesType = refSalesType;
	}

	public String getRefSalesCd() {
		return refSalesCd;
	}

	public void setRefSalesCd(String refSalesCd) {
		this.refSalesCd = refSalesCd;
	}

	public String getRefSalesName() {
		return refSalesName;
	}

	public void setRefSalesName(String refSalesName) {
		this.refSalesName = refSalesName;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCallDate() {
		return callDate;
	}

	public void setCallDate(String callDate) {
		this.callDate = callDate;
	}

	public String getCallList() {
		return callList;
	}

	public void setCallList(String callList) {
		this.callList = callList;
	}

	public String getCallListDesc() {
		return callListDesc;
	}

	public void setCallListDesc(String callListDesc) {
		this.callListDesc = callListDesc;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getCentreCd() {
		return centreCd;
	}

	public void setCentreCd(String centreCd) {
		this.centreCd = centreCd;
	}

	public String getTeamCd() {
		return teamCd;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}

	public String getRefCentreCd() {
		return refCentreCd;
	}

	public void setRefCentreCd(String refCentreCd) {
		this.refCentreCd = refCentreCd;
	}

	public String getRefTeamCd() {
		return refTeamCd;
	}

	public void setRefTeamCd(String refTeamCd) {
		this.refTeamCd = refTeamCd;
	}
}
