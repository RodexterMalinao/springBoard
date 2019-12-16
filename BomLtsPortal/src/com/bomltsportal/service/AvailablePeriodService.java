package com.bomltsportal.service;


public interface AvailablePeriodService {
	
	public boolean IsServiceInMaintenance();
	
	public String getMaintenanceDetail(String lang);

}
