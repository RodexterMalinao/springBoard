/*
 * Created on Nov 10, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.dao;

public interface SequencerDAO {
	public String getSequenceName();
	
	public String getNextVal() throws Exception;
	
	public String getCurrVal() throws Exception;
}
