/*
 * Created on Dec 02, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.service;

public interface LtsJobs  {

	public void refreshParam() throws Exception;
	
	public void start() throws Exception;	
		
	public LtsJobsService getLtsJobsService();

	public void setLtsJobsService(LtsJobsService ltsJobsService);

}
