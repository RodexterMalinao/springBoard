package com.bomwebportal.dto.report;

import java.util.Date;

public class RptTnGServiceFormDTO extends ReportDTO {

	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8904276838818240033L;


	public static final String JASPER_TEMPLATE = "TnGServiceForm";	
	
	private String appInDateStr;
	private String custName;
	private String idDocNum;
	private String customerCopyInd;
	private Date appInDate;
	private String orderId;
	public String getAppInDateStr() {
		return appInDateStr;
	}
	public void setAppInDateStr(String appInDateStr) {
		this.appInDateStr = appInDateStr;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getIdDocNum() {
		return idDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		this.idDocNum = idDocNum;
	}
	
	public String getCustomerCopyInd() {
		return customerCopyInd;
	}
	public void setCustomerCopyInd(String customerCopyInd) {
		this.customerCopyInd = customerCopyInd;
	}
	public Date getAppInDate() {
		return appInDate;
	}
	public void setAppInDate(Date appInDate) {
		this.appInDate = appInDate;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	

	

}
