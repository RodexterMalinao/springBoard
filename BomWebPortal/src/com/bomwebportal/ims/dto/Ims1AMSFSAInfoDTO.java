package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class Ims1AMSFSAInfoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6972162628098821996L;

	private String addrId;
	private String Flat;
	private String Floor;
	private String HLotNO;
	private String FSA;
	private String IS1L1B;
	private String CustName;	
	private String PID;
	private String Status;
	private String LineType;
	private String fakeLineType;
	public String getAddrId() {
		return addrId;
	}
	public void setAddrId(String addrId) {
		this.addrId = addrId;
	}
	public String getFlat() {
		return Flat;
	}
	public void setFlat(String flat) {
		Flat = flat;
	}
	public String getFloor() {
		return Floor;
	}
	public void setFloor(String floor) {
		Floor = floor;
	}
	public String getHLotNO() {
		return HLotNO;
	}
	public void setHLotNO(String hLotNO) {
		HLotNO = hLotNO;
	}
	public String getFSA() {
		return FSA;
	}
	public void setFSA(String fSA) {
		FSA = fSA;
	}
	public String getIS1L1B() {
		return IS1L1B;
	}
	public void setIS1L1B(String iS1L1B) {
		IS1L1B = iS1L1B;
	}
	public String getCustName() {
		return CustName;
	}
	public void setCustName(String custName) {
		CustName = custName;
	}
	public String getPID() {
		return PID;
	}
	public void setPID(String pID) {
		PID = pID;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getLineType() {
		return LineType;
	}
	public void setLineType(String lineType) {
		LineType = lineType;
	}
	public String getFakeLineType() {
		return fakeLineType;
	}
	public void setFakeLineType(String fakeLineType) {
		this.fakeLineType = fakeLineType;
	}
	
	
	
}