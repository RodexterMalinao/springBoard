package com.bomwebportal.lts.dto;

public class ChannelAttbDTO extends ItemAttbBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5544655543042346L;

	private int displaySeq;
	
	private int displaySubSeq;
	
	private boolean selected;
	
	public int getDisplaySeq() {
		return displaySeq;
	}

	public void setDisplaySeq(int displaySeq) {
		this.displaySeq = displaySeq;
	}

	public int getDisplaySubSeq() {
		return displaySubSeq;
	}

	public void setDisplaySubSeq(int displaySubSeq) {
		this.displaySubSeq = displaySubSeq;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
