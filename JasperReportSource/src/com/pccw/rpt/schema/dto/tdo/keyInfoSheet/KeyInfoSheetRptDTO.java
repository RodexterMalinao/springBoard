package com.pccw.rpt.schema.dto.tdo.keyInfoSheet;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.util.FastByteArrayInputStream;
import com.pccw.rpt.schema.dto.ReportDTO;

public class KeyInfoSheetRptDTO extends ReportDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9108910599929466727L;
	private String applicationDate;
	private String applicationNo;
	private String custName;
	private String packageTitle;
	private byte[] custSignature;
	private String formTitle;
	private String formSubject;
	private String formText;
	private String formCustSignText;
	private String formLogo;
	private boolean customerCopy;
	private String customerCopyText = "";
	private String companyBottomBar;
	
	private keyInfo[] keyInfos = new keyInfo[50];
	
	public KeyInfoSheetRptDTO() {
		for (int i = 0; i < this.keyInfos.length; i++) {
			this.keyInfos[i] = new keyInfo();
		}
	}

	private void appendToArrayList(ArrayList<keyInfo> pList, keyInfo[] pKeyInfos) {
		for (int i = 0; i < pKeyInfos.length; i++) {
			if (StringUtils.isEmpty(pKeyInfos[i].getInfoSubj()) 
					&& StringUtils.isEmpty(pKeyInfos[i].getInfoDtl())) {
				continue;
			}
			pList.add(pKeyInfos[i]);
		}
	}
	
	public ArrayList<keyInfo> getKeyInfoList() {
		ArrayList<keyInfo> imptInfoList = new ArrayList<keyInfo>();
		appendToArrayList(imptInfoList, this.keyInfos);
		return imptInfoList;
	}
	

	public keyInfo[] getKeyInfos() {
		return this.keyInfos;
	}

	public void setKeyInfos(keyInfo[] pKeyInfos) {
		this.keyInfos = pKeyInfos;
	}

	public String getApplicationDate() {
		return this.applicationDate;
	}

	public void setApplicationDate(String pApplicationDate) {
		this.applicationDate = pApplicationDate;
	}

	public String getApplicationNo() {
		return this.applicationNo;
	}

	public void setApplicationNo(String pApplicationNo) {
		this.applicationNo = pApplicationNo;
	}

	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String pCustName) {
		this.custName = pCustName;
	}

	public String getPackageTitle() {
		return this.packageTitle;
	}

	public void setPackageTitle(String pPackageTitle) {
		this.packageTitle = pPackageTitle;
	}
	
	public byte[] getCustSignature() {
		return this.custSignature;
	}

	public void setCustSignature(byte[] custSignature) {
		this.custSignature = custSignature;
	}
	
	public InputStream getCustSignatureStream() {
		return new FastByteArrayInputStream(this.custSignature, ArrayUtils.isEmpty(this.custSignature) ? 0 : this.custSignature.length);
	}

	public String getFormTitle() {
		return this.formTitle;
	}

	public void setFormTitle(String pFormTitle) {
		this.formTitle = pFormTitle;
	}

	public String getFormSubject() {
		return this.formSubject;
	}

	public void setFormSubject(String pFormSubject) {
		this.formSubject = pFormSubject;
	}

	public String getFormText() {
		return this.formText;
	}

	public void setFormText(String pFormText) {
		this.formText = pFormText;
	}

	public String getFormCustSignText() {
		return this.formCustSignText;
	}

	public void setFormCustSignText(String pFormCustSignText) {
		this.formCustSignText = pFormCustSignText;
	}
	
	public String getFormLogo() {
		return this.formLogo;
	}

	public void setFormLogo(String pFormLogo) {
		this.formLogo = pFormLogo;
	}

	public boolean isCustomerCopy() {
		return this.customerCopy;
	}

	public void setCustomerCopy(boolean pCustomerCopy) {
		this.customerCopy = pCustomerCopy;
	}

	public String getCustomerCopyText() {
		return this.customerCopyText;
	}

	public void setCustomerCopyText(String pCustomerCopyText) {
		this.customerCopyText = pCustomerCopyText;
	}

	public String getCompanyBottomBar() {
		return this.companyBottomBar;
	}

	public void setCompanyBottomBar(String pCompanyBottomBar) {
		this.companyBottomBar = pCompanyBottomBar;
	}

	public static class keyInfo implements Serializable {

		/**
		 * 
		 */
		private static final long serialVersionUID = -414527317343410986L;
		
		private String infoDtl;
		private String infoSubj;
		
		public keyInfo() {
			
		}
		
		public keyInfo(String pInfoSubj, String pInfoDtl) {
			super();
			this.infoDtl = pInfoDtl;
			this.infoSubj = pInfoSubj;
		}

		public String getInfoSubj() {
			return this.infoSubj;
		}

		public void setInfoSubj(String pInfoSubj) {
			this.infoSubj = pInfoSubj;
		}

		public String getInfoDtl() {
			return this.infoDtl;
		}

		public void setInfoDtl(String pInfoDtl) {
			this.infoDtl = pInfoDtl;
		}
	}

}