package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import com.bomwebportal.util.Utility;
import com.bomwebportal.web.util.GenericReportHelper;

public class RptGenericFormTemplateDTO extends ReportDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2098237357507184396L;
	
	// start - for travel 30 / dynamic variables
	// create variable and put into template params
	private String travelInsuranceDays;
	
	public Map<String, Object> getTemplateParams() {
		Map<String, Object> templateParams = new HashMap<String, Object>();
		templateParams.put("travelInsuranceDays", travelInsuranceDays);
		return templateParams;
	}

	public String getTravelTnC() {
		return Utility.fillInVariables(GenericReportHelper.TRAVEL_INSURANCE_FORM_TNC_PDF, getTemplateParams());
	}
	// end - for travel 30 / dynamic variables
	
	private String section1;
	private String section2;
	private String section3;
	private String section4;
	private String section51;
	private String section52;
	private String section53;
	private String section54;
	private String section55;
	private String section56;
	private String section6;
	private String section71;
	private String section72;
	private String section73;
	private String section74;
	private String section75;
	private String section76;
	private String section8;
	private String section9;
	private String section10;
	private String section11;
	private String section12;
	private String section13;
	private String section14;
	private String section15;
	private InputStream section16;
	private String section17;
	private String section18;
	private String section61;
	private String section62;
	private String section63;
	private String section64;

	public String getSection1() {
		return section1;
	}

	public void setSection1(String section1) {
		this.section1 = section1;
	}

	public String getSection2() {
		return section2;
	}

	public void setSection2(String section2) {
		this.section2 = section2;
	}
	
	public String getImageFilePath() {
		return imageFilePath;
	}

	public String getSection3() {
		return section3;
	}

	public void setSection3(String section3) {
		this.section3 = section3;
	}

	public String getSection4() {
		return section4;
	}

	public void setSection4(String section4) {
		this.section4 = section4;
	}

	public String getSection6() {
		return section6;
	}

	public void setSection6(String section6) {
		this.section6 = section6;
	}

	public String getSection8() {
		return section8;
	}

	public void setSection8(String section8) {
		this.section8 = section8;
	}

	public String getSection9() {
		return section9;
	}

	public void setSection9(String section9) {
		this.section9 = section9;
	}

	public String getSection10() {
		return section10;
	}

	public void setSection10(String section10) {
		this.section10 = section10;
	}

	public String getSection11() {
		return section11;
	}

	public void setSection11(String section11) {
		this.section11 = section11;
	}

	public String getSection12() {
		return section12;
	}

	public void setSection12(String section12) {
		this.section12 = section12;
	}

	public String getSection13() {
		return section13;
	}

	public void setSection13(String section13) {
		this.section13 = section13;
	}

	public String getSection14() {
		return section14;
	}

	public void setSection14(String section14) {
		this.section14 = section14;
	}

	public String getSection15() {
		return section15;
	}

	public void setSection15(String section15) {
		this.section15 = section15;
	}

	public InputStream getSection16() {
		return section16;
	}

	public void setSection16(InputStream section16) {
		this.section16 = section16;
	}

	public String getSection17() {
		return section17;
	}

	public void setSection17(String section17) {
		this.section17 = section17;
	}

	public String getSection18() {
		return section18;
	}

	public void setSection18(String section18) {
		this.section18 = section18;
	}

	public String getSection51() {
		return section51;
	}

	public void setSection51(String section51) {
		this.section51 = section51;
	}

	public String getSection52() {
		return section52;
	}

	public void setSection52(String section52) {
		this.section52 = section52;
	}

	public String getSection53() {
		return section53;
	}

	public void setSection53(String section53) {
		this.section53 = section53;
	}

	public String getSection54() {
		return section54;
	}

	public void setSection54(String section54) {
		this.section54 = section54;
	}

	public String getSection55() {
		return section55;
	}

	public void setSection55(String section55) {
		this.section55 = section55;
	}

	public String getSection56() {
		return section56;
	}

	public void setSection56(String section56) {
		this.section56 = section56;
	}

	public String getSection71() {
		return section71;
	}

	public void setSection71(String section71) {
		this.section71 = section71;
	}

	public String getSection72() {
		return section72;
	}

	public void setSection72(String section72) {
		this.section72 = section72;
	}

	public String getSection73() {
		return section73;
	}

	public void setSection73(String section73) {
		this.section73 = section73;
	}

	public String getSection74() {
		return section74;
	}

	public void setSection74(String section74) {
		this.section74 = section74;
	}

	public String getSection75() {
		return section75;
	}

	public void setSection75(String section75) {
		this.section75 = section75;
	}

	public String getSection76() {
		return section76;
	}

	public void setSection76(String section76) {
		this.section76 = section76;
	}

	public String getSection61() {
		return section61;
	}

	public void setSection61(String section61) {
		this.section61 = section61;
	}

	public String getSection62() {
		return section62;
	}

	public void setSection62(String section62) {
		this.section62 = section62;
	}

	public String getSection63() {
		return section63;
	}

	public void setSection63(String section63) {
		this.section63 = section63;
	}

	public String getSection64() {
		return section64;
	}

	public void setSection64(String section64) {
		this.section64 = section64;
	}

	public String getTravelInsuranceDays() {
		return travelInsuranceDays;
	}

	public void setTravelInsuranceDays(String travelInsuranceDays) {
		this.travelInsuranceDays = travelInsuranceDays;
	}
	
}