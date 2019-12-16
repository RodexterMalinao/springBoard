package com.bomwebportal.dto.report;

public class RptMobileSafetyPhoneTnCDTO extends RptMobileSafetyPhoneDTO {
	private static final long serialVersionUID = 1L;
	
	public static final String JASPER_TEMPLATE = "MobileSafetyPhone_TnC";
	
	private String monthlyRate;
	private String commitmentPeriod;
	private String monthlyRateAfter;
	
	public String getMonthlyRate() {
		return monthlyRate;
	}

	public void setMonthlyRate(String monthlyRate) {
		this.monthlyRate = monthlyRate;
	}

	public String getCommitmentPeriod() {
		return commitmentPeriod;
	}

	public void setCommitmentPeriod(String commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}

	public String getMonthlyRateAfter() {
		return monthlyRateAfter;
	}

	public void setMonthlyRateAfter(String monthlyRateAfter) {
		this.monthlyRateAfter = monthlyRateAfter;
	}

	public RptMobileSafetyPhoneTnCDTO() {
		this.setJasperName(JASPER_TEMPLATE);
	}
}
