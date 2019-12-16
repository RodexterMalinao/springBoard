package com.bomltsportal.dto;

import java.io.Serializable;

import com.bomltsportal.dto.form.AddressLookupFormDTO;
import com.bomltsportal.dto.form.AddressRolloutFormDTO;
import com.bomltsportal.dto.form.AmendmentFormDTO;
import com.bomltsportal.dto.form.ApplicantInfoFormDTO;
import com.bomltsportal.dto.form.BasketDetailFormDTO;
import com.bomltsportal.dto.form.BasketSelectFormDTO;
import com.bomltsportal.dto.form.CreditPaymentFormDTO;
import com.bomltsportal.dto.form.SalesLeadFormDTO;
import com.bomltsportal.dto.form.SummaryFormDTO;
import com.bomltsportal.dto.form.VasDetailFormDTO;
import com.bomwebportal.lts.dto.AddressDetailDTO;
import com.bomwebportal.lts.dto.AddressRolloutDTO;
import com.bomwebportal.lts.dto.order.SbOrderDTO;
import com.bomwebportal.lts.dto.profile.CustomerDetailProfileLtsDTO;
import com.bomwebportal.lts.dto.srvAccess.FsaServiceDetailOutputDTO;


public class OrderCaptureDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1379567012083365470L;
	
	/*form dto*/
	private AddressLookupFormDTO addressLookupForm;
	private AddressRolloutFormDTO addressRolloutForm;
	private ApplicantInfoFormDTO applicantInfoForm;
	private BasketSelectFormDTO basketSelectForm;
	private BasketDetailFormDTO basketDetailForm;
	private VasDetailFormDTO vasDetailForm;
	private CreditPaymentFormDTO creditPaymentForm;
	private SummaryFormDTO summaryForm;
	private AmendmentFormDTO amendmentForm;
	private SalesLeadFormDTO salesLeadForm;
	
	/*session dto*/
	private SbOrderDTO sbOrder;
	private AddressRolloutDTO addressRollout;
	private AddressDetailDTO addressDetail;
	private CustomerDetailProfileLtsDTO customerDetailProfile;
	private FsaServiceDetailOutputDTO imsServiceProfile;
	private FsaServiceAssgnDTO fsaServiceAssgn;
	private DefaultItemListDTO defaultItemList;
	
	/*session attributes*/
	private String lang;
	private String serviceTypeInd;
	private String topNavInd;
	private String channelId;
	private int paymentCounter;
	private String failPayment;
	
	private boolean isOrderSignoffed = false;
	
	public FsaServiceDetailOutputDTO getImsServiceProfile() {
		return imsServiceProfile;
	}
	public void setImsServiceProfile(FsaServiceDetailOutputDTO imsServiceProfile) {
		this.imsServiceProfile = imsServiceProfile;
	}
	public FsaServiceAssgnDTO getFsaServiceAssgn() {
		return fsaServiceAssgn;
	}
	public void setFsaServiceAssgn(FsaServiceAssgnDTO fsaServiceAssgn) {
		this.fsaServiceAssgn = fsaServiceAssgn;
	}
	public AddressLookupFormDTO getAddressLookupForm() {
		return addressLookupForm;
	}
	public void setAddressLookupForm(AddressLookupFormDTO addressLookupForm) {
		this.addressLookupForm = addressLookupForm;
	}
	public AddressRolloutFormDTO getAddressRolloutForm() {
		return addressRolloutForm;
	}
	public void setAddressRolloutForm(AddressRolloutFormDTO addressRolloutForm) {
		this.addressRolloutForm = addressRolloutForm;
	}
	public ApplicantInfoFormDTO getApplicantInfoForm() {
		return applicantInfoForm;
	}
	public void setApplicantInfoForm(ApplicantInfoFormDTO applicantInfoForm) {
		this.applicantInfoForm = applicantInfoForm;
	}
	public BasketSelectFormDTO getBasketSelectForm() {
		return basketSelectForm;
	}
	public void setBasketSelectForm(BasketSelectFormDTO basketSelectForm) {
		this.basketSelectForm = basketSelectForm;
	}
	public BasketDetailFormDTO getBasketDetailForm() {
		return basketDetailForm;
	}
	public void setBasketDetailForm(BasketDetailFormDTO basketDetailForm) {
		this.basketDetailForm = basketDetailForm;
	}
	public VasDetailFormDTO getVasDetailForm() {
		return vasDetailForm;
	}
	public void setVasDetailForm(VasDetailFormDTO vasDetailForm) {
		this.vasDetailForm = vasDetailForm;
	}
	public CreditPaymentFormDTO getCreditPaymentForm() {
		return creditPaymentForm;
	}
	public void setCreditPaymentForm(CreditPaymentFormDTO creditPaymentForm) {
		this.creditPaymentForm = creditPaymentForm;
	}
	public SummaryFormDTO getSummaryForm() {
		return summaryForm;
	}
	public void setSummaryForm(SummaryFormDTO summaryForm) {
		this.summaryForm = summaryForm;
	}
	public AmendmentFormDTO getAmendmentForm() {
		return amendmentForm;
	}
	public void setAmendmentForm(AmendmentFormDTO amendmentForm) {
		this.amendmentForm = amendmentForm;
	}
	public SalesLeadFormDTO getSalesLeadForm() {
		return salesLeadForm;
	}
	public void setSalesLeadForm(SalesLeadFormDTO salesLeadForm) {
		this.salesLeadForm = salesLeadForm;
	}
	public SbOrderDTO getSbOrder() {
		return sbOrder;
	}
	public void setSbOrder(SbOrderDTO sbOrder) {
		this.sbOrder = sbOrder;
	}
	public AddressRolloutDTO getAddressRollout() {
		return addressRollout;
	}
	public void setAddressRollout(AddressRolloutDTO addressRollout) {
		this.addressRollout = addressRollout;
	}
	public CustomerDetailProfileLtsDTO getCustomerDetailProfile() {
		return customerDetailProfile;
	}
	public void setCustomerDetailProfile(CustomerDetailProfileLtsDTO customerDetailProfile) {
		this.customerDetailProfile = customerDetailProfile;
	}
	public String getServiceTypeInd() {
		return serviceTypeInd;
	}
	public void setServiceTypeInd(String serviceTypeInd) {
		this.serviceTypeInd = serviceTypeInd;
	}
	public String getTopNavInd() {
		return topNavInd;
	}
	public void setTopNavInd(String topNavInd) {
		this.topNavInd = topNavInd;
	}
	public String getLang() {
		return lang;
	}
	public void setLang(String lang) {
		this.lang = lang;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public int getPaymentCounter() {
		return paymentCounter;
	}
	public void setPaymentCounter(int paymentCounter) {
		this.paymentCounter = paymentCounter;
	}
	public String getFailPayment() {
		return failPayment;
	}
	public void setFailPayment(String failPayment) {
		this.failPayment = failPayment;
	}
	public DefaultItemListDTO getDefaultItemList() {
		return defaultItemList;
	}
	public void setDefaultItemList(DefaultItemListDTO defaultItemList) {
		this.defaultItemList = defaultItemList;
	}
	public AddressDetailDTO getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(AddressDetailDTO addressDetail) {
		this.addressDetail = addressDetail;
	}
	public boolean isOrderSignoffed() {
		return isOrderSignoffed;
	}
	public void setOrderSignoffed(boolean isOrderSignoffed) {
		this.isOrderSignoffed = isOrderSignoffed;
	}
	
	
}
