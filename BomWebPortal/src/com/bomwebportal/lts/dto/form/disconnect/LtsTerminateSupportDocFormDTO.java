package com.bomwebportal.lts.dto.form.disconnect;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.OrderDocDTO;

public class LtsTerminateSupportDocFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6445363182136188559L;

	private String distributionMode;
	private String collectMethod;
	private String distributeEmail;
	private String distributeLang;
	private String distributeSms;
	
	private List<OrderDocDTO> generatedFormList;
	private List<OrderDocDTO> supportDocumentList;
	
	private String suspendReason;
	private FormAction formAction;
	
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

}

