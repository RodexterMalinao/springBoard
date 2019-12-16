package com.bomwebportal.lts.dto;

import java.io.Serializable;

public class ExclusiveItemDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8461471151815357765L;
	
	private String itemAId;
	private String itemAType;
	private String itemADesc;
	
	private String itemBId;
	private String itemBType;
	private String itemBDesc;
	
	public String getItemAId() {
		return itemAId;
	}
	public void setItemAId(String itemAId) {
		this.itemAId = itemAId;
	}
	public String getItemAType() {
		return itemAType;
	}
	public void setItemAType(String itemAType) {
		this.itemAType = itemAType;
	}
	public String getItemADesc() {
		return itemADesc;
	}
	public void setItemADesc(String itemADesc) {
		this.itemADesc = itemADesc;
	}
	public String getItemBId() {
		return itemBId;
	}
	public void setItemBId(String itemBId) {
		this.itemBId = itemBId;
	}
	public String getItemBType() {
		return itemBType;
	}
	public void setItemBType(String itemBType) {
		this.itemBType = itemBType;
	}
	public String getItemBDesc() {
		return itemBDesc;
	}
	public void setItemBDesc(String itemBDesc) {
		this.itemBDesc = itemBDesc;
	}
	
}
