package com.bomwebportal.dto.report;


import java.text.SimpleDateFormat;
import java.util.Date;


public class RptRetConfirmForCopDTO extends ReportDTO {
	private static final long serialVersionUID = -3311684335459264341L;
	
	public static final String JASPER_TEMPLATE = "MobRetConfirmForCop";	
	

	private String locale;
	
	private String custName;
	private String postingInd;
	private String addrLn1;
	private String addrLn2;
	private String addrLn3;
	private String addrLn4;
	private String addrLn5;
	private String msisdn;

	
	private String orderId;
	private Date appInDate;

	public RptRetConfirmForCopDTO() {
		super(JASPER_TEMPLATE);		
	}
	public RptRetConfirmForCopDTO(String pLang) {
		super(JASPER_TEMPLATE, pLang);
	}
	



	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}




	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	


	public Date getAppInDate() {
		return appInDate;
	}

	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getPostingInd() {
		return postingInd;
	}
	public void setPostingInd(String postingInd) {
		this.postingInd = postingInd;
	}
	public String getAddrLn1() {
		return addrLn1;
	}
	public void setAddrLn1(String addrLn1) {
		this.addrLn1 = addrLn1;
	}
	public String getAddrLn2() {
		return addrLn2;
	}
	public void setAddrLn2(String addrLn2) {
		this.addrLn2 = addrLn2;
	}
	public String getAddrLn3() {
		return addrLn3;
	}
	public void setAddrLn3(String addrLn3) {
		this.addrLn3 = addrLn3;
	}
	public String getAddrLn4() {
		return addrLn4;
	}
	public void setAddrLn4(String addrLn4) {
		this.addrLn4 = addrLn4;
	}
	public String getAddrLn5() {
		return addrLn5;
	}
	public void setAddrLn5(String addrLn5) {
		this.addrLn5 = addrLn5;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getPrintDateNTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(new Date());
	}


}
