package com.bomwebportal.lts.service;

import java.util.List;

public interface LtsAcqDnPoolService {

	List<String> searchDnListFromPoolByNoCriteria(String sessionId);
	List<String> searchDnListFromPoolByFirst8Digits(String sessionId, String searchingKey);
	List<String> searchDnListFromPoolByLast3Digits(String sessionId, String searchingKey);
	List<String> getLockedDnList(String sessionId);
	List<String> getReservedDnList(String sessionId);
	List<String> getReservedDnList(String sessionId, List<String> dnLis);
	void updDnStatus(List<String> dnList, String nStatus, String oStatus, String sessionId);
	void updDnStatusToLock(List<String> dnList, String sessionId);
	void updDnStatusToReserve(List<String> dnList, String oStatus, String sessionId);
	void updDnStatusToReserve(List<String> dnList, String oStatus, String sessionId, String staffId);
	void updDnStatusToConfirm(String srvNum, String sessionId);
	void updDnStatusToConfirm(String srvNum, String sessionId, String staffId);
	void updDnStatusToNormal(String oStatus, String sessionId);
	void updDnStatusToNormal(String oStatus, String sessionId, String staffId);
	void updDnStatusToNormalBySrvNum(List<String> dnList, String oStatus, String sessionId);
	void updDnStatusToNormalBySrvNum(List<String> dnList, String oStatus, String sessionId, String staffId);
	void releaseDnStatusToLockedByReservedDn(List<String> dnList, String sessionId);
	void releaseDnStatusToLockedByReservedDn(List<String> dnList, String sessionId, String staffId);
	void releaseDnStatusToNormalByConfirmedDn(String srvNum);
	void assignDn(String dn);
	boolean isDnAssigned(String dn);
}
