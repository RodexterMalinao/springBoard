package com.bomwebportal.lts.dto.order;

import java.io.Serializable;

public class ServiceActionTypeDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = -4870128083685629204L;

	private String wqType = null;
	private String wqSubtype = null;
	private String wqNatureId = null;
	private String suspendReaCdBom = null;
	private String offerGrpId = null;
	private String[] wqNatureRemarks = null;

	
	public String getWqType() {
		return wqType;
	}

	public void setWqType(String wqType) {
		this.wqType = wqType;
	}

	public String getWqSubtype() {
		return wqSubtype;
	}

	public void setWqSubtype(String wqSubtype) {
		this.wqSubtype = wqSubtype;
	}

	public String getWqNatureId() {
		return wqNatureId;
	}

	public void setWqNatureId(String wqNatureId) {
		this.wqNatureId = wqNatureId;
	}

	public String getSuspendReaCdBom() {
		return suspendReaCdBom;
	}

	public void setSuspendReaCdBom(String suspendReaCdBom) {
		this.suspendReaCdBom = suspendReaCdBom;
	}

	public String getOfferGrpId() {
		return offerGrpId;
	}

	public void setOfferGrpId(String offerGrpId) {
		this.offerGrpId = offerGrpId;
	}

	public String[] getWqNatureRemarks() {
		return this.wqNatureRemarks;
	}

	public void setWqNatureRemarks(String[] pWqNatureRemarks) {
		this.wqNatureRemarks = pWqNatureRemarks;
	}

	public Object clone() {
		try {
			return super.clone();
		} catch(CloneNotSupportedException ex) {
			return this;
		}
	}
}
