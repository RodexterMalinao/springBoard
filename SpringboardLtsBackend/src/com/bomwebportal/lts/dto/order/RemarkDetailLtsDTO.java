package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class RemarkDetailLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -1522288898077962756L;

	private String rmkType = null;
	private String rmkSeq = null;
	private String rmkDtl = null;

	
	public String getRmkType() {
		return rmkType;
	}

	public void setRmkType(String rmkType) {
		this.rmkType = rmkType;
	}

	public String getRmkSeq() {
		return rmkSeq;
	}

	public void setRmkSeq(String rmkSeq) {
		this.rmkSeq = rmkSeq;
	}

	public String getRmkDtl() {
		return rmkDtl;
	}

	public void setRmkDtl(String rmkDtl) {
		this.rmkDtl = rmkDtl;
	}
}
