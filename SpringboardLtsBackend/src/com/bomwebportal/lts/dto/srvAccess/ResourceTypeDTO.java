package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class ResourceTypeDTO implements Serializable {

	private static final long serialVersionUID = -3364492334821922183L;
	
	private String prodSubTypeCd = null;
	private String prodType = null;
	private String srvType = null;
	private String fromProdSubTypeCd = null;
	private String orderType = null;
	

	public String getProdSubTypeCd() {
		return prodSubTypeCd;
	}

	public void setProdSubTypeCd(String prodSubTypeCd) {
		this.prodSubTypeCd = prodSubTypeCd;
	}

	public String getProdType() {
		return prodType;
	}

	public void setProdType(String prodType) {
		this.prodType = prodType;
	}

	public String getSrvType() {
		return srvType;
	}

	public void setSrvType(String srvType) {
		this.srvType = srvType;
	}

	public String getFromProdSubTypeCd() {
		return fromProdSubTypeCd;
	}

	public void setFromProdSubTypeCd(String fromProdSubTypeCd) {
		this.fromProdSubTypeCd = fromProdSubTypeCd;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public void setOrderType(String pOrderType) {
		this.orderType = pOrderType;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(" prodSubTypeCd = ");
		sb.append(this.prodSubTypeCd);
		sb.append(" prodType = ");
		sb.append(this.prodType);
		sb.append(" srvType = ");
		sb.append(this.srvType);
		sb.append(" fromProdSubTypeCd = ");
		sb.append(this.fromProdSubTypeCd);
		sb.append(" orderType = ");
		sb.append(this.orderType);
		return sb.toString();
	}
}
