package com.bomltsportal.dto.form;

import java.io.Serializable;

import com.bomltsportal.dto.BuildingMarkerDTO;

public class AddressRolloutFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8154340334700300846L;
	private String address;
	private String flat;
	private String floor;
	private String captchaInput;
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getFlat() {
		return flat;
	}
	public void setFlat(String flat) {
		this.flat = flat;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public String getCaptchaInput() {
		return captchaInput;
	}
	public void setCaptchaInput(String captchaInput) {
		this.captchaInput = captchaInput;
	}
	
}
