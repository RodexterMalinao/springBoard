package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class UimBlockageDTO implements Serializable {

	private String serbdyno;
	private String floornb;
	private String unitnb;
	private String blockage_code;
	private String blockage_desc;
	private Date blockage_date;
	
	public String getSerbdyno() {
		return serbdyno;
	}
	public void setSerbdyno(String serbdyno) {
		this.serbdyno = serbdyno;
	}

	public String getFloornb() {
		return floornb;
	}
	public void setFloornb(String floornb) {
		this.floornb = floornb;
	}

	public String getUnitnb() {
		return unitnb;
	}
	public void setUnitnb(String unitnb) {
		this.unitnb = unitnb;
	}

	public String getBlockageCode() {
		return blockage_code;
	}
	public void setBlockageCode(String blockage_code) {
		this.blockage_code = blockage_code;
	}

	public String getBlockageDesc() {
		return blockage_desc;
	}
	public void setBlockageDesc(String blockage_desc) {
		this.blockage_desc = blockage_desc;
	}

	public Date getBlockageDate() {
		return blockage_date;
	}
	public void setBlockageDate(Date blockage_date) {
		this.blockage_date = blockage_date;
	}
	
	@Override
	public String toString() {
		return "UimBlockageDTO [serbdyno=" + serbdyno + ", floornb=" + floornb
				+ ", unitnb=" + unitnb + ", blockage_code=" + blockage_code
				+ ", blockage_desc=" + blockage_desc + ", blockage_date="
				+ blockage_date + "]";
	}

}