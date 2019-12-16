package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class OfferActionLtsDTO implements Serializable, Cloneable {

	private static final long serialVersionUID = 7714668728941262866L;

	private String lob = null;
	private String fromProd = null;
	private String toProd = null;
	private String code = null;
	private String action = null;
	private String type = null;
	private String description = null;
	

	public String getLob() {
		return lob;
	}

	public void setLob(String lob) {
		this.lob = lob;
	}

	public String getFromProd() {
		return fromProd;
	}

	public void setFromProd(String fromProd) {
		this.fromProd = fromProd;
	}

	public String getToProd() {
		return toProd;
	}

	public void setToProd(String toProd) {
		this.toProd = toProd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
