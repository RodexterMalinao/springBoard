package com.bomwebportal.dto;

import javax.print.DocFlavor.STRING;

public class SalesmanDTO implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5687897554823423164L;

	private String salesType;
	
	private String salesName;
	
	private String errCode;
	
	private String errMsg;
	
	/*ADD~*/
	private String refSalesName;
	private String refSalesCentre;
	private String refSalesTeam;

	public String getRefSalesName() {
		return refSalesName;
	}

	public void setRefSalesName(String refSalesName) {
		this.refSalesName = refSalesName;
	}

	public String getRefSalesCentre() {
		return refSalesCentre;
	}

	public void setRefSalesCentre(String refSalesCentre) {
		this.refSalesCentre = refSalesCentre;
	}

	public String getRefSalesTeam() {
		return refSalesTeam;
	}

	public void setRefSalesTeam(String refSalesTeam) {
		this.refSalesTeam = refSalesTeam;
	}

	public String getSalesType() {
		return salesType;
	}

	public void setSalesType(String salseType) {
		this.salesType = salseType;
	}

	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	
}
