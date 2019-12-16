package com.pccw.rpt.schema.dto.slv.salesAgreement;

import com.pccw.rpt.util.ReportUtil;

public class FixContentSectionRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -753577915054174380L;
	private String content;

	public String getContent() {
		return ReportUtil.defaultString(this.content);
	}

	public void setContent(String pContents) {
		this.content = pContents;
	}
	
	
}
