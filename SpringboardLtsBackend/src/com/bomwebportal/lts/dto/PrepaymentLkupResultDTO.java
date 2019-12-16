package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.Set;

public class PrepaymentLkupResultDTO implements Serializable, Comparable<PrepaymentLkupResultDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8552547867663935684L;
	
	private String psefCd;
	private int priority;
	private Set<String> psefCdSet;
	private String paymentMethod;
	private String prepayItemId;
	
	public String getPsefCd() {
		return psefCd;
	}
	public void setPsefCd(String psefCd) {
		this.psefCd = psefCd;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPrepayItemId() {
		return prepayItemId;
	}
	public void setPrepayItemId(String prepayItemId) {
		this.prepayItemId = prepayItemId;
	}
	public Set<String> getPsefCdSet() {
		return psefCdSet;
	}
	public void setPsefCdSet(Set<String> psefCdSet) {
		this.psefCdSet = psefCdSet;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	@Override
	public int compareTo(PrepaymentLkupResultDTO o) {
		return (this.priority - o.priority);
	}
	

}
