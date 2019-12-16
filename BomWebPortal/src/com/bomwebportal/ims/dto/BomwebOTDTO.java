package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class BomwebOTDTO implements Serializable{
	
	
	private static final long serialVersionUID = 2979291345367175289L;
	
	private int id;
	private String installOTAmt;
	private String installOTCode;
	private String activateOTAmt;
	private String activateOTCode;
	private String installChrgDescEn;
	private String installChrgDescCn;
	private String installWaiveInd;
	private String installOTQty;
	private String installInstalmentCode;
	private String activateWaiveInd;
	private String activateOTQty;
	private String activateInstalmentCode;
	private String installInstalmentAmt;
	private String installInstalmentMth;
	private String hideOriginalFee;
	private boolean waiveSelected =false;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getInstallOTAmt() {
		return installOTAmt;
	}
	public void setInstallOTAmt(String installOTAmt) {
		this.installOTAmt = installOTAmt;
	}
	public String getInstallOTCode() {
		return installOTCode;
	}
	public void setInstallOTCode(String installOTCode) {
		this.installOTCode = installOTCode;
	}
	public String getInstallChrgDescEn() {
		return installChrgDescEn;
	}
	public void setInstallChrgDescEn(String installChrgDescEn) {
		this.installChrgDescEn = installChrgDescEn;
	}
	public String getInstallChrgDescCn() {
		return installChrgDescCn;
	}
	public void setInstallChrgDescCn(String installChrgDescCn) {
		this.installChrgDescCn = installChrgDescCn;
	}
	public void setActivateOTAmt(String activateOTAmt) {
		this.activateOTAmt = activateOTAmt;
	}
	public String getActivateOTAmt() {
		return activateOTAmt;
	}
	public void setActivateOTCode(String activateOTCode) {
		this.activateOTCode = activateOTCode;
	}
	public String getActivateOTCode() {
		return activateOTCode;
	}
	public void setInstallWaiveInd(String installWaiveInd) {
		this.installWaiveInd = installWaiveInd;
	}
	public String getInstallWaiveInd() {
		return installWaiveInd;
	}
	public void setInstallOTQty(String installOTQty) {
		this.installOTQty = installOTQty;
	}
	public String getInstallOTQty() {
		return installOTQty;
	}
	public void setInstallInstalmentCode(String installInstalmentCode) {
		this.installInstalmentCode = installInstalmentCode;
	}
	public String getInstallInstalmentCode() {
		return installInstalmentCode;
	}
	public void setActivateWaiveInd(String activateWaiveInd) {
		this.activateWaiveInd = activateWaiveInd;
	}
	public String getActivateWaiveInd() {
		return activateWaiveInd;
	}
	public void setActivateOTQty(String activateOTQty) {
		this.activateOTQty = activateOTQty;
	}
	public String getActivateOTQty() {
		return activateOTQty;
	}
	public void setActivateInstalmentCode(String activateInstalmentCode) {
		this.activateInstalmentCode = activateInstalmentCode;
	}
	public String getActivateInstalmentCode() {
		return activateInstalmentCode;
	}
	public void setInstallInstalmentAmt(String installInstalmentAmt) {
		this.installInstalmentAmt = installInstalmentAmt;
	}
	public String getInstallInstalmentAmt() {
		return installInstalmentAmt;
	}
	public void setInstallInstalmentMth(String installInstalmentMth) {
		this.installInstalmentMth = installInstalmentMth;
	}
	public String getInstallInstalmentMth() {
		return installInstalmentMth;
	}
	public void setHideOriginalFee(String hideOriginalFee) {
		this.hideOriginalFee = hideOriginalFee;
	}
	public String getHideOriginalFee() {
		return hideOriginalFee;
	}
	public void setWaiveSelected(boolean waiveSelected) {
		this.waiveSelected = waiveSelected;
	}
	public boolean isWaiveSelected() {
		return waiveSelected;
	}

}
