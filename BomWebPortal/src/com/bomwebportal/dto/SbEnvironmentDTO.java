package com.bomwebportal.dto;


/*
 * Created on May 16, 2012
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */


public class SbEnvironmentDTO  {

	private String applName;

	private String appEnv;

	private String appBuildVersion;
	
	private String appBuildDate;

	private String appServer;

	private String appJVMVendor;

	private String appJavaVersion;

	
	protected SbEnvironmentDTO(){};
	
	private static class InstanceHolder {
		private static final SbEnvironmentDTO _instance = new SbEnvironmentDTO();
	}
		 
	public static SbEnvironmentDTO getInstance() {
		   return InstanceHolder._instance;
	}	
	
	public String getApplName() {
		return applName;
	}

	public void setApplName(String applName) {
		this.applName = applName;
	}

	public String getAppBuildVersion() {
		return appBuildVersion;
	}

	public void setAppBuildVersion(String appBuildVersion) {
		this.appBuildVersion = appBuildVersion;
	}

	public String getAppServer() {
		return appServer;
	}

	public void setAppServer(String appServer) {
		this.appServer = appServer;
	}

	public String getAppJVMVendor() {
		return appJVMVendor;
	}

	public void setAppJVMVendor(String appJVMVendor) {
		this.appJVMVendor = appJVMVendor;
	}

	public String getAppJavaVersion() {
		return appJavaVersion;
	}

	public void setAppJavaVersion(String appJavaVersion) {
		this.appJavaVersion = appJavaVersion;
	}

	public String getAppBuildDate() {
		return appBuildDate;
	}

	public void setAppBuildDate(String appBuildDate) {
		this.appBuildDate = appBuildDate;
	}

	public String getAppEnv() {
		return appEnv;
	}

	public void setAppEnv(String appEnv) {
		this.appEnv = appEnv;
	}

}
