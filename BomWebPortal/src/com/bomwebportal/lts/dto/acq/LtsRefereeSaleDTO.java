package com.bomwebportal.lts.dto.acq;

import java.io.Serializable;

public class LtsRefereeSaleDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7777199301187942439L;

	private String refSalesCode;
	private String refSalesName;
	private String refSalesTeam;
	private String refSalesCentre;
	
	public String getRefSalesCode() {
		return refSalesCode;
	}
	public void setRefSalesCode(String refSalesCode) {
		this.refSalesCode = refSalesCode;
	}
	public String getRefSalesName() {
		return refSalesName;
	}
	public void setRefSalesName(String refSalesName) {
		this.refSalesName = refSalesName;
	}
	public String getRefSalesTeam() {
		return refSalesTeam;
	}
	public void setRefSalesTeam(String refSalesTeam) {
		this.refSalesTeam = refSalesTeam;
	}
	public String getRefSalesCentre() {
		return refSalesCentre;
	}
	public void setRefSalesCentre(String refSalesCentre) {
		this.refSalesCentre = refSalesCentre;
	}
}
