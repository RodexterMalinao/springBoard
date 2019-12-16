package com.bomwebportal.dto;

import java.io.Serializable;

public class VasOnetimeAmtDTO implements Serializable{

	private static final long serialVersionUID = -120224126962566033L;
	
	private String itemDesc;
	private String vasOnetimeAmt;
	
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	
	public String getVasOnetimeAmt() {
		return vasOnetimeAmt;
	}
	public void setVasOnetimeAmt(String vasOnetimeAmt) {
		this.vasOnetimeAmt = vasOnetimeAmt;
	}
	
}
