package com.bomwebportal.lts.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.util.LtsBackendConstant;

public class ItemSetAttbDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7743612633068030456L;
	
	private boolean isAttbValueCreditCard = false;
	
	private String attbCode;
	private String attbDesc;
	private String attbValue;
	
	public String getAttbCode() {
		return attbCode;
	}
	public void setAttbCode(String attbCode) {
		this.attbCode = attbCode;
	}
	public String getAttbDesc() {
		return attbDesc;
	}
	public void setAttbDesc(String attbDesc) {
		this.attbDesc = attbDesc;
	}
	public String getAttbValue() {
		return attbValue;
	}
	public void setAttbValue(String attbValue) {
		if (this.isAttbValueCreditCard) {
			return;
		}
		this.attbValue = attbValue;
	}
	
	public void setAttbValueCcPrefix(String pCcPrefix) {
		if (StringUtils.isBlank(pCcPrefix)) {
			return;
		}
		this.isAttbValueCreditCard = true;
		String ccSuffix = null;
		
		int pos = StringUtils.indexOf(this.attbValue, LtsBackendConstant.CC_MASK); 
		if (pos != -1) {
			ccSuffix = StringUtils.substring(this.attbValue, pos + LtsBackendConstant.CC_MASK.length());
		}
		this.attbValue = pCcPrefix + LtsBackendConstant.CC_MASK + StringUtils.defaultIfEmpty(ccSuffix, "");
	}
	
	public String getAttbValueCcPrefix() {
		int pos = StringUtils.indexOf(this.attbValue, LtsBackendConstant.CC_MASK); 
		if (pos == -1) {
			return null;
		} 
		return StringUtils.defaultIfEmpty(StringUtils.left(this.attbValue, pos), "");
	}

	public void setAttbValueCcSuffix(String pCcSuffix) {
		if (StringUtils.isBlank(pCcSuffix)) {
			return;
		}
		String ccPrefix = null;
		
		int pos = StringUtils.indexOf(this.attbValue, LtsBackendConstant.CC_MASK); 
		if (pos != -1) {
			ccPrefix = StringUtils.left(this.attbValue, pos);
		}
		this.attbValue =  StringUtils.defaultIfEmpty(ccPrefix, "") + LtsBackendConstant.CC_MASK + pCcSuffix;
	}
	
	public String getAttbValueCcSuffix() {
		int pos = StringUtils.indexOf(this.attbValue, LtsBackendConstant.CC_MASK); 
		if (pos == -1) {
			return null;
		}
		return StringUtils.defaultIfEmpty(StringUtils.substring(this.attbValue, pos + LtsBackendConstant.CC_MASK.length()), "");
	}
}