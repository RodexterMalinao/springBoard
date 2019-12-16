package com.bomwebportal.ims.service;

public interface ValidateLoginIDService {
	
	public boolean validateLoginID(String loginName);
	public String validateLoginIDError(String loginName);
}