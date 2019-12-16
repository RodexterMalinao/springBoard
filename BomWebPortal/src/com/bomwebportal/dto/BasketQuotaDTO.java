package com.bomwebportal.dto;

public class BasketQuotaDTO implements java.io.Serializable {

	private static final long serialVersionUID = 8251441191075809431L;

	String basketId;
	String quotaId;
	String quotaDesc;
	String quotaCeiling;
	String validityMth;

	public String getBasketId() {
		return basketId;
	}

	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}

	public String getQuotaId() {
		return quotaId;
	}

	public void setQuotaId(String quotaId) {
		this.quotaId = quotaId;
	}

	public String getQuotaDesc() {
		return quotaDesc;
	}

	public void setQuotaDesc(String quotaDesc) {
		this.quotaDesc = quotaDesc;
	}

	public String getQuotaCeiling() {
		return quotaCeiling;
	}

	public void setQuotaCeiling(String quotaCeiling) {
		this.quotaCeiling = quotaCeiling;
	}

	public String getValidityMth() {
		return validityMth;
	}

	public void setValidityMth(String validityMth) {
		this.validityMth = validityMth;
	}

}
