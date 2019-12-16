package com.bomwebportal.dto.report;

import java.util.List;


public class RptMultiSimDTO {
	//MultiSim Athena 20140128
	private String memberNum;
	private String multiMsisdn;
	private String multiSimIccid;
	private String multiSimType;
	private String multiNfcInd;
	private List<RptVasDetailDTO> multiSimMainServDtls;
	private List<RptVasDetailDTO> multiSimvasDtls;
	private List<RptVasDetailDTO> multiSimvasOptionalDtls;
	private String memberOrderType;
	private String curSimIccid;
	private String csimInd;
	
	public String getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(String memberNum) {
		this.memberNum = memberNum;
	}
	public String getMultiMsisdn() {
		return multiMsisdn;
	}
	public void setMultiMsisdn(String multiMsisdn) {
		this.multiMsisdn = multiMsisdn;
	}
	public String getMultiSimIccid() {
		return multiSimIccid;
	}
	public void setMultiSimIccid(String multiSimIccid) {
		this.multiSimIccid = multiSimIccid;
	}
	public String getMultiSimType() {
		return multiSimType;
	}
	public void setMultiSimType(String multiSimType) {
		this.multiSimType = multiSimType;
	}
	public String getMultiNfcInd() {
		return multiNfcInd;
	}
	public void setMultiNfcInd(String multiNfcInd) {
		this.multiNfcInd = multiNfcInd;
	}
	public List<RptVasDetailDTO> getMultiSimMainServDtls() {
		return multiSimMainServDtls;
	}
	public void setMultiSimMainServDtls(
			List<RptVasDetailDTO> multiSimMainServDtls) {
		this.multiSimMainServDtls = multiSimMainServDtls;
	}
	public List<RptVasDetailDTO> getMultiSimvasDtls() {
		return multiSimvasDtls;
	}
	public void setMultiSimvasDtls(List<RptVasDetailDTO> multiSimvasDtls) {
		this.multiSimvasDtls = multiSimvasDtls;
	}
	public List<RptVasDetailDTO> getMultiSimvasOptionalDtls() {
		return multiSimvasOptionalDtls;
	}
	public void setMultiSimvasOptionalDtls(
			List<RptVasDetailDTO> multiSimvasOptionalDtls) {
		this.multiSimvasOptionalDtls = multiSimvasOptionalDtls;
	}
	public String getMemberOrderType() {
		return memberOrderType;
	}
	public void setMemberOrderType(String memberOrderType) {
		this.memberOrderType = memberOrderType;
	}
	public String getCurSimIccid() {
		return curSimIccid;
	}
	public void setCurSimIccid(String curSimIccid) {
		this.curSimIccid = curSimIccid;
	}
	public String getCsimInd() {
		return csimInd;
	}
	public void setCsimInd(String csimInd) {
		this.csimInd = csimInd;
	}

}
