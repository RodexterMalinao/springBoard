package com.bomltsportal.service;

import java.util.List;

public interface DnPoolService {

	List<String> getDnListFromPool(String sessionId);
	List<String> getDnListFromPool(int qty, String sessionId);
	void updateDnStatus(List<String> dnList, String status);
	void lockDnList(List<String> dnList, String status, String sessionId);
	void releaseDnStatus(String sessionId);
	void assignDn(String dn);
	
}
