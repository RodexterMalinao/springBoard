package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class ResourceDetailInputDTO implements Serializable {

	private static final long serialVersionUID = 7701072258334462509L;
	
	private ResourceTypeDTO[] rscTypes = null; 
	private String area = null;
	private String district = null;
	private String exchBldg = null;
	private String gridId = null;
	private String apptDate = null;
	private String systemId = null;
	private String user = null;
	private String srvBoundary = null;
	private String addrChangeInd = null;
	private String drgPermitDays = null;


	public ResourceTypeDTO[] getRscTypes() {
		return rscTypes;
	}

	public void setRscTypes(ResourceTypeDTO[] rscTypes) {
		this.rscTypes = rscTypes;
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

	public String getApptDate() {
		return apptDate;
	}

	public void setApptDate(String apptDate) {
		this.apptDate = apptDate;
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

	public String getSrvBoundary() {
		return srvBoundary;
	}

	public void setSrvBoundary(String srvBoundary) {
		this.srvBoundary = srvBoundary;
	}

	public String getAddrChangeInd() {
		return addrChangeInd;
	}

	public void setAddrChangeInd(String addrChangeInd) {
		this.addrChangeInd = addrChangeInd;
	}

	public String getDrgPermitDays() {
		return this.drgPermitDays;
	}

	public void setDrgPermitDays(String pDrgPermitDays) {
		this.drgPermitDays = pDrgPermitDays;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" area = ");
		sb.append(this.area);
		sb.append(" district = ");
		sb.append(this.district);
		sb.append(" exchBldg = ");
		sb.append(this.exchBldg);
		sb.append(" gridId = ");
		sb.append(this.gridId);
		sb.append(" apptDate = ");
		sb.append(this.apptDate);
		sb.append(" systemId = ");
		sb.append(this.systemId);
		sb.append(" srvBoundary = ");
		sb.append(this.srvBoundary);
		sb.append(" addrChangeInd = ");
		sb.append(this.addrChangeInd);
		sb.append(" drgPermitDays = ");
		sb.append(this.drgPermitDays);

		for (ResourceTypeDTO rscType : this.rscTypes) {
			if (rscType != null) {
				sb.append(rscType.toString());
			}
		}
		return sb.toString();
	}
}
