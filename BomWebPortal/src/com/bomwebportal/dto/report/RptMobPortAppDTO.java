package com.bomwebportal.dto.report;

import java.io.InputStream;
import java.util.Date;

public class RptMobPortAppDTO extends ReportDTO {
	private static final long serialVersionUID = 4648404968065252453L;

	public static final String JASPER_TEMPLATE = "MobileNumPortAppForm";
	
	private String custName;
	private String title;
	private String msisdn;
	private String agreementNum;
	private Date cutoverDate;
	private String cutoverTime;
	private Date appInDate;
	private String dno;
	private String rno;
	private String custIdDocNum;
	private String shopCd;
	private String salesCd;
	private String mnpTicketNum;
	
	//add eliot 20110819
	private InputStream custSignature;
	
	private String custNameChi;
	private boolean prePaidSimDocWithCert;
	private boolean prePaidSimDocWithoutCert;
		
	public String getMnpTicketNum() {
		return mnpTicketNum;
	}

	public void setMnpTicketNum(String mnpTicketNum) {
		this.mnpTicketNum = mnpTicketNum;
	}

	public RptMobPortAppDTO() {
		super(JASPER_TEMPLATE);
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getAgreementNum() {
		return agreementNum;
	}

	public void setAgreementNum(String agreementNum) {
		this.agreementNum = agreementNum;
	}

	public Date getCutoverDate() {
		return cutoverDate;
	}

	public void setCutoverDate(Date cutoverDate) {
		this.cutoverDate = cutoverDate;
	}

	public String getCutoverTime() {
		return cutoverTime;
	}

	public void setCutoverTime(String cutoverTime) {
		this.cutoverTime = cutoverTime;
	}

	public Date getAppInDate() {
		return appInDate;
	}

	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
	}

	public String getDno() {
		return dno;
	}

	public String getRno() {
		return rno;
	}

	public void setRno(String rno) {
		this.rno = rno;
	}

	public void setDno(String dno) {
		this.dno = dno;
	}

	public String getCustIdDocNum() {
		return custIdDocNum;
	}

	public void setCustIdDocNum(String custIdDocNum) {
		this.custIdDocNum = custIdDocNum;
	}

	public String getShopCd() {
		return shopCd;
	}

	public void setShopCd(String shopCd) {
		this.shopCd = shopCd;
	}

	public String getSalesCd() {
		return salesCd;
	}

	public void setSalesCd(String salesCd) {
		this.salesCd = salesCd;
	}

	public InputStream getCustSignature() {
		return custSignature;
	}

	public void setCustSignature(InputStream custSignature) {
		this.custSignature = custSignature;
	}

	/**
	 * @return the custNameChi
	 */
	public String getCustNameChi() {
		return custNameChi;
	}

	/**
	 * @param custNameChi the custNameChi to set
	 */
	public void setCustNameChi(String custNameChi) {
		this.custNameChi = custNameChi;
	}

	/**
	 * @return the prePaidSimDocWithCert
	 */
	public boolean isPrePaidSimDocWithCert() {
		return prePaidSimDocWithCert;
	}

	/**
	 * @param prePaidSimDocWithCert the prePaidSimDocWithCert to set
	 */
	public void setPrePaidSimDocWithCert(boolean prePaidSimDocWithCert) {
		this.prePaidSimDocWithCert = prePaidSimDocWithCert;
	}

	/**
	 * @return the prePaidSimDocWithoutCert
	 */
	public boolean isPrePaidSimDocWithoutCert() {
		return prePaidSimDocWithoutCert;
	}

	/**
	 * @param prePaidSimDocWithoutCert the prePaidSimDocWithoutCert to set
	 */
	public void setPrePaidSimDocWithoutCert(boolean prePaidSimDocWithoutCert) {
		this.prePaidSimDocWithoutCert = prePaidSimDocWithoutCert;
	}
	
}
