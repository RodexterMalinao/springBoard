package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class CccLkupDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6078210665127480682L;
	
	private String ccc;
	private String cccDesc;
	private int rowSeqNb;
	
	public String getCcc() {
		return ccc;
	}
	public void setCcc(String ccc) {
		this.ccc = ccc;
	}
	public String getCccDesc() {
		return cccDesc;
	}
	public void setCccDesc(String cccDesc) {
		this.cccDesc = cccDesc;
	}
	public int getRowSeqNb() {
		return rowSeqNb;
	}
	public void setRowSeqNb(int rowSeqNb) {
		this.rowSeqNb = rowSeqNb;
	}
	
	
}
