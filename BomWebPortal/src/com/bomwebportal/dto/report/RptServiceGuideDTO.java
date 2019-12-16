package com.bomwebportal.dto.report;


public class RptServiceGuideDTO extends ReportDTO {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7747101086051423890L;

	public static final String JASPER_TEMPLATE = "ServiceGuide";	
	// for NE
	public static final String JASPER_TEMPLATE_NE = "ServiceGuideNE";
	public static final String JASPER_TEMPLATE_CY = "M2ServiceGuideCY";	
	public static final String JASPER_TEMPLATE_Uni = "M2ServiceGuideUni";	
	
	private String agreementNum;
	private String contentHtml1;
	private String contentHtml2;
	private String contentHtml3;
	private String contentHtml4;
	private String contentHtml5;
	private Boolean hasVas;

	/**
	 * same as order id
	 * @return the agreementNum
	 */
	public String getAgreementNum() {
		return agreementNum;
	}

	/**
	 * same as order id
	 * @param agreementNum the agreementNum to set
	 */
	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}

	public String getContentHtml1() {
		return contentHtml1;
	}

	public void setContentHtml1(String contentHtml1) {
		this.contentHtml1 = contentHtml1;
	}

	public String getContentHtml2() {
		return contentHtml2;
	}

	public void setContentHtml2(String contentHtml2) {
		this.contentHtml2 = contentHtml2;
	}

	public String getContentHtml3() {
		return contentHtml3;
	}

	public void setContentHtml3(String contentHtml3) {
		this.contentHtml3 = contentHtml3;
	}

	public String getContentHtml4() {
		return contentHtml4;
	}

	public void setContentHtml4(String contentHtml4) {
		this.contentHtml4 = contentHtml4;
	}

	public String getContentHtml5() {
		return contentHtml5;
	}

	public void setContentHtml5(String contentHtml5) {
		this.contentHtml5 = contentHtml5;
	}

	public Boolean getHasVas() {
		return hasVas;
	}

	public void setHasVas(Boolean hasVas) {
		this.hasVas = hasVas;
	}
}
