package com.bomltsportal.service;

public interface ConnectionCheckService {
	
	public boolean IsBomWebPortalDSConnected();
	
	public boolean IsBomDSConnnected();
	
	public boolean IsUamsDSConnected();
	
	public boolean IsCeksReady();
	
	public boolean IsDataFileReady();
	
	public boolean IsCspReady();
}
