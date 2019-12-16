package com.bomwebportal.dto.report;

import java.io.Serializable;

public class ReportDTO implements Serializable {
	
	private static final long serialVersionUID = -1396245864910707562L;

	private String jasperName = null;
	
	private String companyLogo = null;	
	
	public ReportDTO() {}
	
	public ReportDTO(String pJasperName) {
		this.setJasperName(pJasperName);
	}
	
	public String getJasperName() {
		return jasperName;
	}
	
	public void setJasperName(String pJasperName) {
		jasperName = pJasperName;
	}

	public String getCompanyLogo() {
		return companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
}
