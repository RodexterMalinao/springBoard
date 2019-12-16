package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class ItemActionLtsDTO implements Serializable {

	private static final long serialVersionUID = 2142891244576186796L;

	private String toProd = null;
	private String action = null;
	private String penaltyHandle = null;


	public String getToProd() {
		return toProd;
	}

	public void setToProd(String toProd) {
		this.toProd = toProd;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getPenaltyHandle() {
		return penaltyHandle;
	}

	public void setPenaltyHandle(String penaltyHandle) {
		this.penaltyHandle = penaltyHandle;
	}
}
