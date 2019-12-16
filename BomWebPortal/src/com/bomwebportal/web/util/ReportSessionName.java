package com.bomwebportal.web.util;

public enum ReportSessionName {
	ORDER_DETAIL("orderdetailOrderDto", "orderdetailCustomer",
			"orderdetailServicePlanReport", "orderdetailPayment",
			"orderdetailMNP", "orderdetailMobileSimInfo",
			"orderdetailBomsalesuser", "orderdetailHsTradeDescByRecall", "orderdetailBasket",
			"orderdetailSupportDoc"
			, "orderdetailComponentList")
	, SIGNOFF("orderDto", "customer", "servicePlanReport",
			"payment", "MNP", "MobileSimInfo", "bomsalesuser",
			"hsTradeDescSummary", "basket", "supportDoc",
			"componentList", 
			"customerSignSession", "bankSignSession", "mnpSignSession",
			"conciergeSignSession", "iGuardSignSession", "tdoSignSession", "travelInsuranceSignSession", "helperCareInsuranceSignSession", "projectEagleInsuranceSignSession");


	ReportSessionName(String orderDtoName, String customerDtoName,
			String servicePlanReportDtoName, String paymentDtoName,
			String mnpDtoName, String mobileSimInfoDtoName,
			String bomSalesUserDtoName, String hsTradeDescDtoName,
			String basketDtoName, String supportDocDtoName,
			String componentListName) {
		this(orderDtoName, customerDtoName, servicePlanReportDtoName,
				paymentDtoName, mnpDtoName, mobileSimInfoDtoName,
				bomSalesUserDtoName, hsTradeDescDtoName, basketDtoName,
				supportDocDtoName, componentListName, null, null, null, null, null, null,null, null, null);

	}

	ReportSessionName(String orderDtoName, String customerDtoName,
			String servicePlanReportDtoName, String paymentDtoName,
			String mnpDtoName, String mobileSimInfoDtoName,
			String bomSalesUserDtoName, String hsTradeDescDtoName,
			String basketDtoName, String supportDocDtoName,
			String componentListName,
			String custSignDtoName, String bankSignDtoName,
			String mnpSignDtoName, String conciSignDtoName,
			String iGuardSignDtoName, String tdoSignDtoName,
			String travelInsuranceSignDtoName, String helperCareInsuranceSignDtoName,
			String projectEagleInsuranceSignDtoName) {

		this.orderDtoName = orderDtoName;
		this.customerDtoName = customerDtoName;
		this.servicePlanReportDtoName = servicePlanReportDtoName;
		this.paymentDtoName = paymentDtoName;

		this.mnpDtoName = mnpDtoName;
		this.mobileSimInfoDtoName = mobileSimInfoDtoName;
		this.bomSalesUserDtoName = bomSalesUserDtoName;
		this.hsTradeDescDtoName = hsTradeDescDtoName;

		this.basketDtoName = basketDtoName;
		this.supportDocDtoName = supportDocDtoName;
		this.componentListName = componentListName;

		this.custSignDtoName = custSignDtoName;
		this.bankSignDtoName = bankSignDtoName;
		this.mnpSignDtoName = mnpSignDtoName;
		this.conciSignDtoName = conciSignDtoName;
		this.iGuardSignDtoName = iGuardSignDtoName;
		this.tdoSignDtoName = tdoSignDtoName;
		this.travelInsuranceSignDtoName = travelInsuranceSignDtoName;
		this.helperCareInsuranceSignDtoName = helperCareInsuranceSignDtoName;
		this.projectEagleInsuranceSignDtoName = projectEagleInsuranceSignDtoName;
	}

	private String orderDtoName;
	private String customerDtoName;
	private String servicePlanReportDtoName;
	private String paymentDtoName;

	private String mnpDtoName;
	private String mobileSimInfoDtoName;
	private String bomSalesUserDtoName;
	private String hsTradeDescDtoName;

	private String basketDtoName;
	private String supportDocDtoName;
	private String componentListName;

	private String custSignDtoName;
	private String bankSignDtoName;
	private String mnpSignDtoName;
	private String conciSignDtoName;
	private String iGuardSignDtoName;
	private String tdoSignDtoName;  //20130709
	private String travelInsuranceSignDtoName;
	private String helperCareInsuranceSignDtoName;
	private String projectEagleInsuranceSignDtoName;
	
	public String getOrderDtoName() {
		return orderDtoName;
	}

	public String getCustomerDtoName() {
		return customerDtoName;
	}

	public String getServicePlanReportDtoName() {
		return servicePlanReportDtoName;
	}

	public String getPaymentDtoName() {
		return paymentDtoName;
	}

	public String getMnpDtoName() {
		return mnpDtoName;
	}

	public String getMobileSimInfoDtoName() {
		return mobileSimInfoDtoName;
	}

	public String getBomSalesUserDtoName() {
		return bomSalesUserDtoName;
	}

	public String getHsTradeDescDtoName() {
		return hsTradeDescDtoName;
	}

	public String getBasketDtoName() {
		return basketDtoName;
	}

	public String getSupportDocDtoName() {
		return supportDocDtoName;
	}

	public String getCustSignDtoName() {
		return custSignDtoName;
	}

	public String getBankSignDtoName() {
		return bankSignDtoName;
	}

	public String getMnpSignDtoName() {
		return mnpSignDtoName;
	}

	public String getConciSignDtoName() {
		return conciSignDtoName;
	}
	
	public String getiGuardSignDtoName() {
		return iGuardSignDtoName;
	}

	public String getTdoSignDtoName() {
		return tdoSignDtoName;
	}
	
	public String getComponentListName() {
		return componentListName;
	}

	public void setComponentListName(String componentListName) {
		this.componentListName = componentListName;
	}

	public String getTravelInsuranceSignDtoName() {
		return travelInsuranceSignDtoName;
	}
	
	public String getHelperCareInsuranceSignDtoName() {
		return helperCareInsuranceSignDtoName;
	}

	public String getProjectEagleInsuranceSignDtoName() {
		return projectEagleInsuranceSignDtoName;
	}

}
