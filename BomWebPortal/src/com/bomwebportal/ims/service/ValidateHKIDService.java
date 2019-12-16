package com.bomwebportal.ims.service;

public interface ValidateHKIDService {
	
	public boolean validateHKID(String hkid);
	public String validateHKIDError(String hkid);
}