package com.bomwebportal.lts.dto.profile;

import java.io.Serializable;

public class RecontractRemarkDTO implements Serializable {

	private static final long serialVersionUID = -7920614541325372381L;

	private String srvNum = null;
	private String srcCustNum = null;
	private String srcCustName = null;
	private String srcAcctNum = null;
	private String srcDocType = null;
	private String srcDocNum = null;
	private String targetCustName = null;
	private String targetCustNum = null;
	private String targetAcctNum = null;
	private String targetDocType = null;
	private String targetDocNum = null;
	private String remark = null;
	private String lastUpdDate = null;
	private String lastUpdBy = null;

	
	public String getSrvNum() {
		return srvNum;
	}

	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	public String getSrcCustNum() {
		return srcCustNum;
	}

	public void setSrcCustNum(String srcCustNum) {
		this.srcCustNum = srcCustNum;
	}

	public String getSrcCustName() {
		return srcCustName;
	}

	public void setSrcCustName(String srcCustName) {
		this.srcCustName = srcCustName;
	}

	public String getSrcAcctNum() {
		return srcAcctNum;
	}

	public void setSrcAcctNum(String srcAcctNum) {
		this.srcAcctNum = srcAcctNum;
	}

	public String getSrcDocType() {
		return srcDocType;
	}

	public void setSrcDocType(String srcDocType) {
		this.srcDocType = srcDocType;
	}

	public String getSrcDocNum() {
		return srcDocNum;
	}

	public void setSrcDocNum(String srcDocNum) {
		this.srcDocNum = srcDocNum;
	}

	public String getTargetCustName() {
		return targetCustName;
	}

	public void setTargetCustName(String targetCustName) {
		this.targetCustName = targetCustName;
	}

	public String getTargetCustNum() {
		return targetCustNum;
	}

	public void setTargetCustNum(String targetCustNum) {
		this.targetCustNum = targetCustNum;
	}

	public String getTargetAcctNum() {
		return targetAcctNum;
	}

	public void setTargetAcctNum(String targetAcctNum) {
		this.targetAcctNum = targetAcctNum;
	}

	public String getTargetDocType() {
		return targetDocType;
	}

	public void setTargetDocType(String targetDocType) {
		this.targetDocType = targetDocType;
	}

	public String getTargetDocNum() {
		return targetDocNum;
	}

	public void setTargetDocNum(String targetDocNum) {
		this.targetDocNum = targetDocNum;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(String lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
}
