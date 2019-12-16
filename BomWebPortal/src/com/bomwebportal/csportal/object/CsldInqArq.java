package com.bomwebportal.csportal.object;

public class CsldInqArq {
	public String apiTy;
	public String reply; //RC_SUCC
	public String clnVer;

	public String sysId;
	public String sysPwd;

	public String userId;
	public String psnTy;

	public String iDocTy;
	public String iDocNum;
	public String iLi4MyHkt;
	public String iLi4Club;

	public String oIdStatus;	// RC_NO_CUSTOMER, RC_CUS_ALDY_REG, RC_CUST_EXIST
	public String oCorrMyHktLi;	// MyHKT login if RC_CUS_ALDY_REG
	public String oCorrClubLi;	// Club Login ID if Club API return SUCCESS
	public String oCorrClubMbrId; // Club Membership ID if Club API return SUCCESS
	public String oMyHktLiStatus;	//RC_IN_USE if iLi4MyHkt is found, 
	public String oClubLiStatus; //RC_IN_USE if Club API return SUCCESS
	
	public String oBiptStatus;//CareCash Register Ind
	public String oCareVisit;
	
	public String oPhantomAcc;
}
