package com.bomwebportal.lts.dto.disconnect;

import com.bomwebportal.lts.dto.OrderCaptureDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateAppointmentFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateBillingInfoFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateCustomerInquiryFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateServiceSelectionFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateSummaryFormDTO;
import com.bomwebportal.lts.dto.form.disconnect.LtsTerminateSupportDocFormDTO;

public class TerminateOrderCaptureDTO extends OrderCaptureDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1003988189560346213L;
	
	//LTS Termination UI Form
	private LtsTerminateCustomerInquiryFormDTO ltsTerminateCustomerInquiryForm;
	private LtsTerminateServiceSelectionFormDTO ltsTerminateServiceSelectionForm;
	private LtsTerminateBillingInfoFormDTO ltsTerminateBillingInfoForm;
	private LtsTerminateAppointmentFormDTO ltsTerminateAppointmentForm;
	private LtsTerminateSupportDocFormDTO ltsTerminateSupportDocForm;
	private LtsTerminateSummaryFormDTO ltsTerminateSummaryForm;
	
	public LtsTerminateCustomerInquiryFormDTO getLtsTerminateCustomerInquiryForm() {
		return ltsTerminateCustomerInquiryForm;
	}
	public void setLtsTerminateCustomerInquiryForm(
			LtsTerminateCustomerInquiryFormDTO ltsTerminateCustomerInquiryForm) {
		this.ltsTerminateCustomerInquiryForm = ltsTerminateCustomerInquiryForm;
	}
	public LtsTerminateServiceSelectionFormDTO getLtsTerminateServiceSelectionForm() {
		return ltsTerminateServiceSelectionForm;
	}
	public void setLtsTerminateServiceSelectionForm(
			LtsTerminateServiceSelectionFormDTO ltsTerminateServiceSelectionForm) {
		this.ltsTerminateServiceSelectionForm = ltsTerminateServiceSelectionForm;
	}
	public LtsTerminateBillingInfoFormDTO getLtsTerminateBillingInfoForm() {
		return ltsTerminateBillingInfoForm;
	}
	public void setLtsTerminateBillingInfoForm(
			LtsTerminateBillingInfoFormDTO ltsTerminateBillingInfoForm) {
		this.ltsTerminateBillingInfoForm = ltsTerminateBillingInfoForm;
	}
	public LtsTerminateAppointmentFormDTO getLtsTerminateAppointmentForm() {
		return ltsTerminateAppointmentForm;
	}
	public void setLtsTerminateAppointmentForm(
			LtsTerminateAppointmentFormDTO ltsTerminateAppointmentForm) {
		this.ltsTerminateAppointmentForm = ltsTerminateAppointmentForm;
	}
	public LtsTerminateSummaryFormDTO getLtsTerminateSummaryForm() {
		return ltsTerminateSummaryForm;
	}
	public void setLtsTerminateSummaryForm(
			LtsTerminateSummaryFormDTO ltsTerminateSummaryForm) {
		this.ltsTerminateSummaryForm = ltsTerminateSummaryForm;
	}
	public LtsTerminateSupportDocFormDTO getLtsTerminateSupportDocForm() {
		return ltsTerminateSupportDocForm;
	}
	public void setLtsTerminateSupportDocForm(
			LtsTerminateSupportDocFormDTO ltsTerminateSupportDocForm) {
		this.ltsTerminateSupportDocForm = ltsTerminateSupportDocForm;
	}
	
}
