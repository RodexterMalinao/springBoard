package com.pccw.rpt.schema.dto.eye.eyeAppForm;

import com.pccw.rpt.util.ReportUtil;

public class FixContentEyeResTelSectionRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2095838611176468504L;


	private String eyeContent;

	private String resTelContent;
	
	private String eyeResTelContent;

	public String getEyeContent() {
		return ReportUtil.defaultString(this.eyeContent);
	}

	public void setEyeContent(String pEyeContents) {
		this.eyeContent = pEyeContents;
	}

	public String getResTelContent() {
		return ReportUtil.defaultString(this.resTelContent);
	}

	public void setResTelContent(String pResTelContents) {
		this.resTelContent = pResTelContents;
	}
	
	public String getEyeResTelContent() {
		return ReportUtil.defaultString(this.eyeResTelContent);
	}

	public void setEyeResTelContent(String pEyeResTelContents) {
		this.eyeResTelContent = pEyeResTelContents;
	}
}
