package com.bomwebportal.dto.report;

import java.io.Serializable;

import com.bomwebportal.util.BomWebPortalConstant;
import com.bomwebportal.util.ConfigProperties;

public class ReportDTO implements Serializable {
	
	private static final long serialVersionUID = -1396245864910707562L;
	
	private String jasperName = null;
	
	private String companyLogo = null;	
	
	private String companyBottomLogo = null;

	private String companyBottomLeftLogo = null;	
	
	public String getCompanyBottomLeftLogo() {
		return companyBottomLeftLogo;
	}

	public void setCompanyBottomLeftLogo(String companyBottomLeftLogo) {
		this.companyBottomLeftLogo = companyBottomLeftLogo;
	}

	protected String imageFilePath = ConfigProperties.getPropertyByEnvironment("imageFilePath");

	public ReportDTO() {}
	
	public ReportDTO(String pJasperName) {
		this.setJasperName(pJasperName);
	}
	
	public ReportDTO(String pJasperName, String pLang){
		this.setJasperName(pJasperName);
		if(BomWebPortalConstant.RPT_LANG_EN.equals(pLang)){
			this.setCompanyLogo(BomWebPortalConstant.COMPANY_LOGO_FILE_ENG);
			this.setCompanyBottomLogo(BomWebPortalConstant.COMPANY_BOTTOM_LOGO_FILE_ENG);
		}else{
			this.setCompanyLogo(BomWebPortalConstant.COMPANY_LOGO_FILE_CHI);
			this.setCompanyBottomLogo(BomWebPortalConstant.COMPANY_BOTTOM_LOGO_FILE_CHI);
		}
	}
	
	/*public void setLogo(String pLang){
		this.setCompanyLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_LOGO_TOP_M2);
		//this.setCompanyBottomLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_LOGO_BOTTOM_RT_M2);
		
		if(BomWebPortalConstant.RPT_LANG_EN.equals(pLang)){
			this.setCompanyBottomLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_LOGO_BOTTOM_LT_ENG_M2);
		}else{
			this.setCompanyBottomLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_LOGO_BOTTOM_LT_CHI_M2);
		}
	}*/
	
	public void setLogo(String pLang, boolean isM2Ind, String brand) {
		if (!isM2Ind) {
			//HKT
			if (BomWebPortalConstant.RPT_LANG_EN.equals(pLang)) {
				this.setCompanyLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_LOGO_FILE_ENG);
				this.setCompanyBottomLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_BOTTOM_LOGO_FILE_CHI);
			} else {
				this.setCompanyLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_LOGO_FILE_CHI);
				this.setCompanyBottomLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_BOTTOM_LOGO_FILE_ENG);
			}
		} else {
			if (BomWebPortalConstant.BRAND_1010.equals(brand)) {
				//1010
				this.setCompanyLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_LOGO_TOP_1010);
			} else {
				//CSL
				this.setCompanyLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_LOGO_TOP_M2);
				//rpt.setCompanyBottomLogo(imageFilePath + "/" + COMPANY_LOGO_BOTTOM_RT_M2);
			}

			if (BomWebPortalConstant.RPT_LANG_EN.equals(pLang)) {
				this.setCompanyBottomLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_LOGO_BOTTOM_LT_ENG_M2);
			} else {
				this.setCompanyBottomLogo(imageFilePath + "/" + BomWebPortalConstant.COMPANY_LOGO_BOTTOM_LT_CHI_M2);
			}
		}
	}
	
	public String getJasperName() {
		return jasperName;
	}
	
	public void setJasperName(String pJasperName) {
		jasperName = pJasperName;
	}

	public String getCompanyLogo() {
		return this.companyLogo;
	}

	public void setCompanyLogo(String companyLogo) {
		this.companyLogo = companyLogo;
	}
	
	public String getCompanyBottomLogo() {
		return this.companyBottomLogo;
	}

	public void setCompanyBottomLogo(String companyBottomLogo) {
		this.companyBottomLogo = companyBottomLogo;
	}
	
}
