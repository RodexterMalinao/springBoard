package com.pccw.wq.schema.form;

import java.io.Serializable;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class SbWqInquiryResultFormDTO extends WqInquiryResultFormDTO implements
		Serializable, Comparable<SbWqInquiryResultFormDTO> {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8436664697456279573L;
	
	private String remarksNatureDesc;
	
	private boolean allowChangeStatus;
	
	public String getDisplayDate() {
		if (ArrayUtils.isNotEmpty(this.getRemarks())) {
			return this.getRemarks()[0].getRemarkDate();
		} else if (StringUtils.isNotBlank(this.getWqReceiveDate())) {
			return this.getWqReceiveDate();
		}
		
		return "00000000000000";
	}

	public String getRemarksNatureDesc() {
		return this.remarksNatureDesc;
	}

	public void setRemarksNatureDesc(String pRemarksNatureDesc) {
		this.remarksNatureDesc = pRemarksNatureDesc;
	}

	private String getCompareKey() {
		return this.getDisplayDate() + "^" + (ArrayUtils.isNotEmpty(this.getRemarks()) ? this.getRemarks()[0].getRemarkSequence() : "00") + "^" + this.getWqWpAssgnId();
	}
	
	@Override
	public int compareTo(SbWqInquiryResultFormDTO pO) {
		return this.getCompareKey().compareTo(pO.getCompareKey());
	}

	public boolean isAllowChangeStatus() {
		return this.allowChangeStatus;
	}

	public void setAllowChangeStatus(boolean pAllowChangeStatus) {
		this.allowChangeStatus = pAllowChangeStatus;
	}
}