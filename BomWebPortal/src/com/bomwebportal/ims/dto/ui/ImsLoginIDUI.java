package com.bomwebportal.ims.dto.ui;

public class ImsLoginIDUI{
	private String docType;
	private String idDocNum;
	private String loginName;
	private String oldLoginName;
	private boolean valid;
	private boolean exist;
	
	public String getDocType() {
		return docType;
	}
	
	public void setDocType(String docType) {
		this.docType = docType;
	}
	
	public String getIdDocNum() {
		return idDocNum;
	}
	
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	
	public String getLoginName() {
		return loginName;
	}
	
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	public String getOldLoginName() {
		return oldLoginName;
	}
	
	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public boolean isExist() {
		return exist;
	}

	public void setExist(boolean exist) {
		this.exist = exist;
	}
	
}