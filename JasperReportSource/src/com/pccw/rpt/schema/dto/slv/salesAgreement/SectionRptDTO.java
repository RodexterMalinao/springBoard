package com.pccw.rpt.schema.dto.slv.salesAgreement;

import java.io.Serializable;

import com.pccw.rpt.util.ReportUtil;

public class SectionRptDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2465101483647516902L;

	private String sectionTitle;

	public String getSectionTitle() {
		return ReportUtil.defaultString(this.sectionTitle);
	}

	public void setSectionTitle(String pSectionTitle) {
		this.sectionTitle = pSectionTitle;
	}
}
