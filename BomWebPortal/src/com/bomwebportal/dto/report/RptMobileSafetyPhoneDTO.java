package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

public class RptMobileSafetyPhoneDTO extends ReportDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static enum ServiceAttb {
		USER_TITLE("serviceUserTitle", "0110908")
		, USER_NAME("serviceUserName", "0110909")
		, INSTALL_ADDR1("serviceInstallAddr1", "0110910")
		, INSTALL_ADDR2("serviceInstallAddr2", "0110911")
		, INSTALL_ADDR3("serviceInstallAddr3", "0110912")
		, INSTALL_ADDR4("serviceInstallAddr4", "0110913")
		, INSTALL_ADDR5("serviceInstallAddr5", "0110914")
		, CONTACT_PERSON("serviceContactPerson", "0110915")
		, CONTACT_PHONE("serviceContactPhone", "0110916")
		;
		ServiceAttb(String field, String attbId) {
			this.field = field;
			this.attbId = attbId;
		}
		public String getField() {
			return field;
		}
		public String getAttbId() {
			return attbId;
		}
		private String field;
		private String attbId;
	}

	public static final String JASPER_TEMPLATE = "MobileSafetyPhone";

	private String title;
	private String custLastName;
	private String custFirstName;
	private String msisdn;
	private String mnpInd;
	private String orderId;
	private Date targetCommencementDate;
	private Date appInDate;
	
	// installation information
	private String serviceUserTitle;
	private String serviceUserName;
	private String serviceInstallAddr1;
	private String serviceInstallAddr2;
	private String serviceInstallAddr3;
	private String serviceInstallAddr4;
	private String serviceInstallAddr5;
	private String serviceContactPerson;
	private String serviceContactPhone;
	
	private InputStream custSignature;
	
	// logo
	private String mobileSafetyLogo;
	
	public RptMobileSafetyPhoneDTO() {
		this.setJasperName(JASPER_TEMPLATE);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustLastName() {
		return custLastName;
	}

	public void setCustLastName(String custLastName) {
		this.custLastName = custLastName;
	}

	public String getCustFirstName() {
		return custFirstName;
	}

	public void setCustFirstName(String custFirstName) {
		this.custFirstName = custFirstName;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getMnpInd() {
		return mnpInd;
	}

	public void setMnpInd(String mnpInd) {
		this.mnpInd = mnpInd;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getTargetCommencementDate() {
		return targetCommencementDate;
	}

	public void setTargetCommencementDate(Date targetCommencementDate) {
		this.targetCommencementDate = targetCommencementDate;
	}

	public Date getAppInDate() {
		return appInDate;
	}

	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
	}

	public String getServiceUserTitle() {
		return serviceUserTitle;
	}

	public void setServiceUserTitle(String serviceUserTitle) {
		this.serviceUserTitle = serviceUserTitle;
	}

	public String getServiceUserName() {
		return serviceUserName;
	}

	public void setServiceUserName(String serviceUserName) {
		this.serviceUserName = serviceUserName;
	}

	public String getServiceInstallAddr1() {
		return serviceInstallAddr1;
	}

	public void setServiceInstallAddr1(String serviceInstallAddr1) {
		this.serviceInstallAddr1 = serviceInstallAddr1;
	}

	public String getServiceInstallAddr2() {
		return serviceInstallAddr2;
	}

	public void setServiceInstallAddr2(String serviceInstallAddr2) {
		this.serviceInstallAddr2 = serviceInstallAddr2;
	}

	public String getServiceInstallAddr3() {
		return serviceInstallAddr3;
	}

	public void setServiceInstallAddr3(String serviceInstallAddr3) {
		this.serviceInstallAddr3 = serviceInstallAddr3;
	}

	public String getServiceInstallAddr4() {
		return serviceInstallAddr4;
	}

	public void setServiceInstallAddr4(String serviceInstallAddr4) {
		this.serviceInstallAddr4 = serviceInstallAddr4;
	}

	public String getServiceInstallAddr5() {
		return serviceInstallAddr5;
	}

	public void setServiceInstallAddr5(String serviceInstallAddr5) {
		this.serviceInstallAddr5 = serviceInstallAddr5;
	}

	public String getServiceContactPerson() {
		return serviceContactPerson;
	}

	public void setServiceContactPerson(String serviceContactPerson) {
		this.serviceContactPerson = serviceContactPerson;
	}

	public String getServiceContactPhone() {
		return serviceContactPhone;
	}

	public void setServiceContactPhone(String serviceContactPhone) {
		this.serviceContactPhone = serviceContactPhone;
	}

	public InputStream getCustSignature() {
		return custSignature;
	}

	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}

	public String getMobileSafetyLogo() {
		return mobileSafetyLogo;
	}

	public void setMobileSafetyLogo(String mobileSafetyLogo) {
		this.mobileSafetyLogo = mobileSafetyLogo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
