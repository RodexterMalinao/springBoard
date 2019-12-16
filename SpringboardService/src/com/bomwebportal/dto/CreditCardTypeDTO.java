package com.bomwebportal.dto;

import java.io.Serializable;

public class CreditCardTypeDTO implements Serializable {

	//SELECT BOM_GRP_ID, BOM_CODE, BOM_DESC FROM W_CREDITCARDTYPE;	CreditCardTypeDTO

	private static final long serialVersionUID = -7489584423574702942L;
	private String bomGrpId; // W_CREDITCARDTYPE.BOM_GRP_ID; 
	private String bomCode;  // W_CREDITCARDTYPE.BOM_CODE
	private String bomDesc; // W_CREDITCARDTYPE.BOM_DESC
	
	public String getBomGrpId() {
		return bomGrpId;
	}
	public void setBomGrpId(String bomGrpId) {
		this.bomGrpId = bomGrpId;
	}
	public String getBomCode() {
		return bomCode;
	}
	public void setBomCode(String bomCode) {
		this.bomCode = bomCode;
	}
	public String getBomDesc() {
		return bomDesc;
	}
	public void setBomDesc(String bomDesc) {
		this.bomDesc = bomDesc;
	}
	
	
}