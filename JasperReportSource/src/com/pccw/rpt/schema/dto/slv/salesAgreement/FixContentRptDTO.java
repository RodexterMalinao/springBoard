package com.pccw.rpt.schema.dto.slv.salesAgreement;

import com.pccw.rpt.util.ReportUtil;

public class FixContentRptDTO extends SectionRptDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1685981358551440683L;

	private String slvContent;
	private String svcSpecificContent;

	public String getSlvContent() {
		return ReportUtil.defaultString(this.slvContent);
	}

	public void setSlvContent(String pSlvContents) {
		this.slvContent = pSlvContents;
	}

	public String getSvcSpecificContent() {
		return svcSpecificContent;
	}

	public void setSvcSpecificContent(String svcSpecificContent) {
		this.svcSpecificContent = svcSpecificContent;
	}
}
