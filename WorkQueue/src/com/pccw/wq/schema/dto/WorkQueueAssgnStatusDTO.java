/*
 * Created on Nov 1, 2011
 *
 * @author Alfredo P. Ricafort
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.pccw.wq.schema.dto;

public class WorkQueueAssgnStatusDTO extends WorkQueueAssgnStatusLogDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1317728588205562990L;
		
	private WorkQueueAssgnStatusLogDTO [] workQueueAssgnStatusLogDTO;

	public WorkQueueAssgnStatusLogDTO[] getWorkQueueStatusLogDTO() {
		return workQueueAssgnStatusLogDTO;
	}

	public void setWorkQueueStatusLogDTO(
			WorkQueueAssgnStatusLogDTO[] workQueueStatusLogDTO) {
		this.workQueueAssgnStatusLogDTO = workQueueStatusLogDTO;
	}
	
	
}
