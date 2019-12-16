package com.bomwebportal.lts.dto;

import com.bomwebportal.lts.dto.srvAccess.TechnologyDTO;

public class LineTypeSelectionDTO extends TechnologyDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5676111318849688359L;
	
	private boolean selected;
	private String status;
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
