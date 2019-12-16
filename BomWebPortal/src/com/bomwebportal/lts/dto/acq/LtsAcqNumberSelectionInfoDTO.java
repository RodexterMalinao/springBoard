package com.bomwebportal.lts.dto.acq;

import java.io.Serializable;

public class LtsAcqNumberSelectionInfoDTO implements Serializable{

	private static final long serialVersionUID = -6857551875253725036L;

	private boolean selected;
	private String srvNum;
	private String displaySrvNum;
	
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return the srvNum
	 */
	public String getSrvNum() {
		return srvNum;
	}

	/**
	 * @param srvNum the srvNum to set
	 */
	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}

	/**
	 * @return the displaySrvNum
	 */
	public String getDisplaySrvNum() {
		return displaySrvNum;
	}

	/**
	 * @param displaySrvNum the displaySrvNum to set
	 */
	public void setDisplaySrvNum(String displaySrvNum) {
		this.displaySrvNum = displaySrvNum;
	}

}
