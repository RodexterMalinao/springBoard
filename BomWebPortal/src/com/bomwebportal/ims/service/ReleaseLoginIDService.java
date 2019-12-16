package com.bomwebportal.ims.service;



public interface ReleaseLoginIDService {
	public int releaseLoginID(String loginName, String idNo, String idType);
	public void releaseUselessLoginID();
}