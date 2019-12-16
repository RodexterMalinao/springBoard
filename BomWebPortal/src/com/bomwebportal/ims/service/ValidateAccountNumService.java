package com.bomwebportal.ims.service;

public interface ValidateAccountNumService {
	public int validateAccountNum(long acctnb, String srccode);
	public String validateAccountNumError(long acctnb, String srccode);
	public String validateEmailAvailable(String loginID);
}