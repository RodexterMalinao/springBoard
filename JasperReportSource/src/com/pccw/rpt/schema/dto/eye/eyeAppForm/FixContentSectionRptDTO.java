package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import com.pccw.rpt.util.ReportUtil;

public class FixContentSectionRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2095838611176468504L;

	private String content;

	public String getContent() {
		return ReportUtil.defaultString(this.content);
	}

	public void setContent(String pContents) {
		this.content = pContents;
	}
}
