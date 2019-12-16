package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class CodeLkupDTO implements Serializable {

	/**
	 * BOMWEB_CODE_LKUP table DTO
	 */
	private static final long serialVersionUID = 5960798371330351260L;
	private String codeType;
	private String codeId;
	private String codeDesc;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;
	
	public CodeLkupDTO() {
		
	}

	public CodeLkupDTO(String codeId, String codeDesc) {
		this.codeId = codeId;
		this.codeDesc = codeDesc;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

}
