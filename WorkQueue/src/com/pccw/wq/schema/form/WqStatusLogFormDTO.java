/*
 * Created on Nov 3, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.schema.form;

import com.pccw.wq.schema.dto.WorkQueueAssgnStatusLogDTO;

public class WqStatusLogFormDTO extends WorkQueueAssgnStatusLogDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1768230647275700686L;
	
	private String latestStatusInd;

	public String getLatestStatusInd() {
		return latestStatusInd;
	}

	public void setLatestStatusInd(String latestStatusInd) {
		this.latestStatusInd = latestStatusInd;
	}

}
