package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class NelgninvDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8678853936027004414L;
	
	
	private String NETLOGID;
	private String IDDOCNB; 
	private String IDDOCTY;
	
	
	public String getNETLOGID() {
		return NETLOGID;
	}
	public void setNETLOGID(String nETLOGID) {
		NETLOGID = nETLOGID;
	}
	public String getIDDOCNB() {
		return IDDOCNB;
	}
	public void setIDDOCNB(String iDDOCNB) {
		IDDOCNB = iDDOCNB;
	}
	public String getIDDOCTY() {
		return IDDOCTY;
	}
	public void setIDDOCTY(String iDDOCTY) {
		IDDOCTY = iDDOCTY;
	}
	
	

}
