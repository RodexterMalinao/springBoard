package com.pccw.rpt.schema.dto.wq;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.util.ReportUtil;

public class WqRemarkRptDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4053655145586952918L;

	private String remarkSequence;
	private String remarkContent;
	private String remarkDate;
	private String remarkNatureId;
	private String remarkNatureDesc;
	private String createBy;

	public WqRemarkRptDTO(String pRemarkSequence, String pRemarkDate,
			String pCreateBy, String pRemarkNatureId, String pRemarkNatureDesc,
			String pRemarkContent) {
		remarkSequence = pRemarkSequence;
		remarkContent = pRemarkContent;
		remarkDate = pRemarkDate;
		remarkNatureId = pRemarkNatureId;
		remarkNatureDesc = pRemarkNatureDesc;
		createBy = pCreateBy;
	}

	public String getRemarkSequence() {
		return ReportUtil.defaultString(this.remarkSequence);
	}

	public void setRemarkSequence(String pRemarkSequence) {
		this.remarkSequence = pRemarkSequence;
	}

	public String getRemarkContent() {
		return StringUtils.replace(ReportUtil.defaultString(this.remarkContent), "\n", "<br />");
	}

	public void setRemarkContent(String pRemarkContent) {
		this.remarkContent = pRemarkContent;
	}

	public String getRemarkDate() {
		return ReportUtil.defaultString(this.remarkDate);
	}

	public void setRemarkDate(String pRemarkDate) {
		this.remarkDate = pRemarkDate;
	}

	public String getRemarkNatureId() {
		return ReportUtil.defaultString(this.remarkNatureId);
	}

	public void setRemarkNatureId(String pRemarkNatureId) {
		this.remarkNatureId = pRemarkNatureId;
	}

	public String getRemarkNatureDesc() {
		return ReportUtil.defaultString(this.remarkNatureDesc);
	}

	public void setRemarkNatureDesc(String pRemarkNatureDesc) {
		this.remarkNatureDesc = pRemarkNatureDesc;
	}

	public String getCreateBy() {
		return ReportUtil.defaultString(this.createBy);
	}

	public void setCreateBy(String pCreateBy) {
		this.createBy = pCreateBy;
	}
}
