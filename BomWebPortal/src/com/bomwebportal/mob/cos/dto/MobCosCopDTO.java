package com.bomwebportal.mob.cos.dto;

public class MobCosCopDTO {
	private String orderId;
	private String ocid;
	private String msisdn;
	private String custName;
	private String postingInd;

	private String addrLn1;
	private String addrLn2;
	private String addrLn3;
	private String addrLn4;
	private String addrLn5;
	
	private String reqFilename; //batch in file
	private String outFileName; // generate output file
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOcid() {
		return ocid;
	}
	public void setOcid(String ocid) {
		this.ocid = ocid;
	}
	public String getMsisdn() {
		return msisdn;
	}
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPostingInd() {
		return postingInd;
	}
	public boolean isPostingInd() {
		return "Y".equalsIgnoreCase(postingInd);
	}
	public void setPostingInd(String postingInd) {
		this.postingInd = postingInd;
	}
	public String getAddrLn1() {
		return addrLn1;
	}
	public void setAddrLn1(String addrLn1) {
		this.addrLn1 = addrLn1;
	}
	public String getAddrLn2() {
		return addrLn2;
	}
	public void setAddrLn2(String addrLn2) {
		this.addrLn2 = addrLn2;
	}
	public String getAddrLn3() {
		return addrLn3;
	}
	public void setAddrLn3(String addrLn3) {
		this.addrLn3 = addrLn3;
	}
	public String getAddrLn4() {
		return addrLn4;
	}
	public void setAddrLn4(String addrLn4) {
		this.addrLn4 = addrLn4;
	}
	public String getAddrLn5() {
		return addrLn5;
	}
	public void setAddrLn5(String addrLn5) {
		this.addrLn5 = addrLn5;
	}
	public String getReqFilename() {
		return reqFilename;
	}
	public void setReqFilename(String reqFilename) {
		this.reqFilename = reqFilename;
	}
	public String getOutFileName() {
		return outFileName;
	}
	public void setOutFileName(String outFileName) {
		this.outFileName = outFileName;
	}
	
}
