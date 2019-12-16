package com.bomwebportal.mob.ccs.dto.ui;

public class CallLogSearchUI {
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public String getOtherPhone() {
		return otherPhone;
	}
	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}
	public String getDelivery3rdPartyPhone() {
		return delivery3rdPartyPhone;
	}
	public void setDelivery3rdPartyPhone(String delivery3rdPartyPhone) {
		this.delivery3rdPartyPhone = delivery3rdPartyPhone;
	}
	private String firstName;
	private String lastName;
	private String address;
	private String contactPhone;
	private String otherPhone;
	private String delivery3rdPartyPhone;
}
