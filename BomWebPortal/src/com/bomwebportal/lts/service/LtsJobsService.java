/*
 * Created on Feb 29, 2012
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.bomwebportal.lts.service;

public interface LtsJobsService {
    public  void exec() throws Exception;
    
	public int getOrderAge();

	public void setOrderAge(int orderAge);
	
	public void setPipbOrderAge(int pipbOrderAge);
}
