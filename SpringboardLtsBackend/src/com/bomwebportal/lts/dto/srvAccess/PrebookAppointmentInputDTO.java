package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class PrebookAppointmentInputDTO implements Serializable {

	private static final long serialVersionUID = 9213163548050067825L;
	
	private String prodSubTypeCd = null;
	private String area = null;
	private String district = null;
	private String exchBldg = null;
	private String gridId = null;
	private String apptDateStartDate = null;
	private String apptDateEndDate = null;
	private String apptType = null;
	private String systemId = null;
	private String user = null;

	
	public String getProdSubTypeCd() {
		return prodSubTypeCd;
	}

	public void setProdSubTypeCd(String prodSubTypeCd) {
		this.prodSubTypeCd = prodSubTypeCd;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getExchBldg() {
		return exchBldg;
	}

	public void setExchBldg(String exchBldg) {
		this.exchBldg = exchBldg;
	}

	public String getGridId() {
		return gridId;
	}

	public void setGridId(String gridId) {
		this.gridId = gridId;
	}

	public String getApptDateStartDate() {
		return apptDateStartDate;
	}

	public void setApptDateStartDate(String apptDateStartDate) {
		this.apptDateStartDate = apptDateStartDate;
	}

	public String getApptDateEndDate() {
		return apptDateEndDate;
	}

	public void setApptDateEndDate(String apptDateEndDate) {
		this.apptDateEndDate = apptDateEndDate;
	}

	public String getApptType() {
		return apptType;
	}

	public void setApptType(String apptType) {
		this.apptType = apptType;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("prodSubTypeCd = ");
		sb.append(this.prodSubTypeCd);
		sb.append(" area = ");
		sb.append(this.area);
		sb.append(" district = ");
		sb.append(this.district);
		sb.append(" exchBldg = ");
		sb.append(this.exchBldg);
		sb.append(" gridId = ");
		sb.append(this.gridId);
		sb.append(" apptDateStartDate = ");
		sb.append(this.apptDateStartDate);
		sb.append(" apptDateEndDate = ");
		sb.append(this.apptDateEndDate);
		sb.append(" apptType = ");
		sb.append(this.apptType);
		sb.append(" systemId = ");
		sb.append(this.systemId);
		sb.append(" user = ");
		sb.append(this.user);
		return sb.toString();
	}
}
