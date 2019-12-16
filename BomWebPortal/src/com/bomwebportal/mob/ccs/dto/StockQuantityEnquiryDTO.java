package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;

public class StockQuantityEnquiryDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 755286073841138699L;
	
	private String type;
	private String itemDesc;
	private String itemCode;
	private String free;
	private String reserve;
	private String assign;
	private String allocate;
	private String transfer;
	private String returnField;
	private String doa;
	
	//for Direct Sales
	private String sos;
	private String rss;
	private String rwh;
	//reserve
	private String sold;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}	
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	public String getReserve() {
		return reserve;
	}
	public void setReserve(String reserve) {
		this.reserve = reserve;
	}
	public String getAssign() {
		return assign;
	}
	public void setAssign(String assign) {
		this.assign = assign;
	}
	public String getAllocate() {
		return allocate;
	}
	public void setAllocate(String allocate) {
		this.allocate = allocate;
	}
	public String getTransfer() {
		return transfer;
	}
	public void setTransfer(String transfer) {
		this.transfer = transfer;
	}
	public String getReturnField() {
		return returnField;
	}
	public void setReturnField(String returnField) {
		this.returnField = returnField;
	}
	public String getDoa() {
		return doa;
	}
	public void setDoa(String doa) {
		this.doa = doa;
	}
	public String getSos() {
		return sos;
	}
	public void setSos(String sos) {
		this.sos = sos;
	}
	public String getRss() {
		return rss;
	}
	public void setRss(String rss) {
		this.rss = rss;
	}
	public String getRwh() {
		return rwh;
	}
	public void setRwh(String rwh) {
		this.rwh = rwh;
	}
	public String getSold() {
		return sold;
	}
	public void setSold(String sold) {
		this.sold = sold;
	}
	
}
