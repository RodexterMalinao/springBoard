package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.OrderDocDTO;

public class LtsSupportDocFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4915758705387008215L;
	
	private String distributionMode;
	private String collectMethod;
	private String distributeEmail;
	private String distributeLang;
	private String distributeSms;
	private boolean sendSms;
	private boolean sendEmail;
	private String signoffMode;
	
	private List<OrderDocDTO> generatedFormList;
	private List<OrderDocDTO> supportDocumentList;
	
	private String suspendReason;
	private FormAction formAction = FormAction.SUBMIT;
	
	private boolean allowDistributePaper;
	
	public static enum FormAction {
		SUBMIT, SUSPEND, CHANGE_DIS_MODE;
	}
	
	public String getDistributionMode() {
		return distributionMode;
	}

	public void setDistributionMode(String distributionMode) {
		this.distributionMode = distributionMode;
	}

	public String getCollectMethod() {
		return collectMethod;
	}

	public void setCollectMethod(String collectMethod) {
		this.collectMethod = collectMethod;
	}

	public String getDistributeEmail() {
		return distributeEmail;
	}

	public void setDistributeEmail(String distributeEmail) {
		this.distributeEmail = distributeEmail;
	}

	public String getDistributeLang() {
		return distributeLang;
	}

	public void setDistributeLang(String distributeLang) {
		this.distributeLang = distributeLang;
	}

	public List<OrderDocDTO> getGeneratedFormList() {
		return generatedFormList;
	}

	public void setGeneratedFormList(List<OrderDocDTO> generatedFormList) {
		this.generatedFormList = generatedFormList;
	}

	public List<OrderDocDTO> getSupportDocumentList() {
		return supportDocumentList;
	}

	public void setSupportDocumentList(List<OrderDocDTO> supportDocumentList) {
		this.supportDocumentList = supportDocumentList;
	}

	public String getSuspendReason() {
		return suspendReason;
	}

	public void setSuspendReason(String suspendReason) {
		this.suspendReason = suspendReason;
	}

	public FormAction getFormAction() {
		return formAction;
	}

	public void setFormAction(FormAction formAction) {
		this.formAction = formAction;
	}

	public String getDistributeSms() {
		return distributeSms;
	}

	public void setDistributeSms(String distributeSms) {
		this.distributeSms = distributeSms;
	}

	public boolean isSendSms() {
		return sendSms;
	}

	public void setSendSms(boolean sendSms) {
		this.sendSms = sendSms;
	}

	public boolean isSendEmail() {
		return sendEmail;
	}

	public void setSendEmail(boolean sendEmail) {
		this.sendEmail = sendEmail;
	}

	public String getSignoffMode() {
		return signoffMode;
	}

	public void setSignoffMode(String signoffMode) {
		this.signoffMode = signoffMode;
	}

	public boolean isAllowDistributePaper() {
		return allowDistributePaper;
	}

	public void setAllowDistributePaper(boolean allowDistributePaper) {
		this.allowDistributePaper = allowDistributePaper;
	}
	

}

