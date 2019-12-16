package com.bomwebportal.mob.ds.dto;

import java.util.List;

public class MobDsPaymentUpfrontDTO implements java.io.Serializable{

	private static final long serialVersionUID = 4575112537243709581L;

	private double totalPaidAmount;
	private double totalUpfrontAmount;
	private double hsUpfrontAmount;
	private String ceksSubmit;
	
	private List<MobDsPaymentTransDTO> paymentTransList;
	
	private String uid;
	String actionType;
	
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public double getTotalPaidAmount() {
		return totalPaidAmount;
	}
	public void setTotalPaidAmount(double totalPaidAmount) {
		this.totalPaidAmount = totalPaidAmount;
	}
	public double getTotalUpfrontAmount() {
		return totalUpfrontAmount;
	}
	public void setTotalUpfrontAmount(double totalUpfrontAmount) {
		this.totalUpfrontAmount = totalUpfrontAmount;
	}
	public double getHsUpfrontAmount() {
		return hsUpfrontAmount;
	}
	public void setHsUpfrontAmount(double hsUpfrontAmount) {
		this.hsUpfrontAmount = hsUpfrontAmount;
	}
	public String getCeksSubmit() {
		return ceksSubmit;
	}
	public void setCeksSubmit(String ceksSubmit) {
		this.ceksSubmit = ceksSubmit;
	}
	public List<MobDsPaymentTransDTO> getPaymentTransList() {
		return paymentTransList;
	}
	public void setPaymentTransList(List<MobDsPaymentTransDTO> paymentTransList) {
		this.paymentTransList = paymentTransList;
	}
}
