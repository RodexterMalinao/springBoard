package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class AppointmentDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -2061582494527951561L;

	private String serialNum = null;
	private String appntStartTime = null;
	private String appntEndTime = null;
	private String exactAppntTime = null;
	private String forcedDelayInd = null;
	private String preWiringStartTime = null;
	private String preWiringEndTime = null;
	private String preWiringType = null;
	private String instContactName = null;
	private String instContactNum = null;
	private String instSmsNum = null;
	private String tidInd = null;
	private String cutOverStartTime = null;
	private String cutOverEndTime = null;
	private String tidStartTime = null;
	private String tidEndTime = null;
	private String instContactMobile = null; 
	private String custContactMobile = null;
	private String custContactFix = null;
	private String appntType = null;

	
	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public String getAppntStartTime() {
		return appntStartTime;
	}

	public void setAppntStartTime(String appntStartTime) {
		this.appntStartTime = appntStartTime;
	}

	public String getAppntEndTime() {
		return appntEndTime;
	}

	public void setAppntEndTime(String appntEndTime) {
		this.appntEndTime = appntEndTime;
	}

	public String getExactAppntTime() {
		return exactAppntTime;
	}

	public void setExactAppntTime(String exactAppntTime) {
		this.exactAppntTime = exactAppntTime;
	}

	public String getForcedDelayInd() {
		return forcedDelayInd;
	}

	public void setForcedDelayInd(String forcedDelayInd) {
		this.forcedDelayInd = forcedDelayInd;
	}

	public String getPreWiringStartTime() {
		return preWiringStartTime;
	}

	public void setPreWiringStartTime(String preWiringStartTime) {
		this.preWiringStartTime = preWiringStartTime;
	}

	public String getPreWiringEndTime() {
		return preWiringEndTime;
	}

	public void setPreWiringEndTime(String preWiringEndTime) {
		this.preWiringEndTime = preWiringEndTime;
	}

	public String getPreWiringType() {
		return preWiringType;
	}

	public void setPreWiringType(String preWiringType) {
		this.preWiringType = preWiringType;
	}

	public String getTidInd() {
		return tidInd;
	}

	public void setTidInd(String tidInd) {
		this.tidInd = tidInd;
	}

	public String getInstContactName() {
		return instContactName;
	}

	public void setInstContactName(String instContactName) {
		this.instContactName = instContactName;
	}

	public String getInstContactNum() {
		return instContactNum;
	}

	public void setInstContactNum(String instContactNum) {
		this.instContactNum = instContactNum;
	}

	public String getInstSmsNum() {
		return instSmsNum;
	}

	public void setInstSmsNum(String instSmsNum) {
		this.instSmsNum = instSmsNum;
	}

	public String getCutOverStartTime() {
		return cutOverStartTime;
	}

	public void setCutOverStartTime(String cutOverStartTime) {
		this.cutOverStartTime = cutOverStartTime;
	}

	public String getCutOverEndTime() {
		return cutOverEndTime;
	}

	public void setCutOverEndTime(String cutOverEndTime) {
		this.cutOverEndTime = cutOverEndTime;
	}

	public String getTidStartTime() {
		return this.tidStartTime;
	}

	public void setTidStartTime(String pTidStartTime) {
		this.tidStartTime = pTidStartTime;
	}

	public String getTidEndTime() {
		return this.tidEndTime;
	}

	public void setTidEndTime(String pTidEndTime) {
		this.tidEndTime = pTidEndTime;
	}

	public String getInstContactMobile() {
		return this.instContactMobile;
	}

	public void setInstContactMobile(String pInstContactMobile) {
		this.instContactMobile = pInstContactMobile;
	}

	public String getCustContactMobile() {
		return this.custContactMobile;
	}

	public void setCustContactMobile(String pCustContactMobile) {
		this.custContactMobile = pCustContactMobile;
	}

	public String getCustContactFix() {
		return this.custContactFix;
	}

	public void setCustContactFix(String pCustContactFix) {
		this.custContactFix = pCustContactFix;
	}

	public String getAppntType() {
		return this.appntType;
	}

	public void setAppntType(String pAppntType) {
		this.appntType = pAppntType;
	}
}
