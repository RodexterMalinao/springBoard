package com.bomwebportal.ims.dto.ui;

import java.util.List;

import com.bomwebportal.ims.dto.ImsInstallationInstallmentPlanDTO;;


public class InstallFeeUI{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1004668401000796461L;
	private String onetimeAmount;
	private int InstallInstalmentAmt;
	private int InstallInstalmentMth;
	private int ErrorCode;
	private String ErrorText;
	
	
	private List<ImsInstallationInstallmentPlanDTO> ImsInstallationInstallmentPlanList;
	
	public List<ImsInstallationInstallmentPlanDTO> getImsInstallationInstallmentPlanList() {
		return ImsInstallationInstallmentPlanList;
	}
	public void setImsInstallationInstallmentPlanList(
			List<ImsInstallationInstallmentPlanDTO> imsInstallationInstallmentPlanList) {
		ImsInstallationInstallmentPlanList = imsInstallationInstallmentPlanList;
	}
	public String getOnetimeAmount() {
		return onetimeAmount;
	}
	public void setOnetimeAmount(String onetimeAmount) {
		this.onetimeAmount = onetimeAmount;
	}
	public int getInstallInstalmentAmt() {
		return InstallInstalmentAmt;
	}
	public void setInstallInstalmentAmt(int installInstalmentAmt) {
		InstallInstalmentAmt = installInstalmentAmt;
	}
	public int getInstallInstalmentMth() {
		return InstallInstalmentMth;
	}
	public void setInstallInstalmentMth(int installInstalmentMth) {
		InstallInstalmentMth = installInstalmentMth;
	}
	
	public int getErrorCode() {
		return ErrorCode;
	}
	public void setErrorCode(int errorCode) {
		ErrorCode = errorCode;
	}
	public String getErrorText() {
		return ErrorText;
	}
	public void setErrorText(String errorText) {
		ErrorText = errorText;
	}
	
	

}
