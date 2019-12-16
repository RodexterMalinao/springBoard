package com.bomwebportal.dto.report;

import java.io.Serializable;

public class RptVasDetailDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6035699782813738781L;
	private String itemHtml ;
	private String itemHtml2 ;
	private String itemRecurrentAmt;
	private String itemType;
	private String itemRebateAmt;
	
	
	public RptVasDetailDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public RptVasDetailDTO(String pItemHtml, String pItemRecurrentAmt) {
		itemHtml = pItemHtml;
		itemRecurrentAmt = pItemRecurrentAmt;
	}

	public String getItemHtml() {
		return itemHtml;
	}

	public void setItemHtml(String itemHtml) {
		this.itemHtml = itemHtml;
	}
	public String getItemHtml2() {
		return itemHtml2;
	}

	public void setItemHtml2(String itemHtml2) {
		this.itemHtml2 = itemHtml2;
	}

	public String getItemRecurrentAmt() {
		return itemRecurrentAmt;
	}

	public void setItemRecurrentAmt(String itemRecurrentAmt) {
		this.itemRecurrentAmt = itemRecurrentAmt;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemRebateAmt() {
		return itemRebateAmt;
	}

	public void setItemRebateAmt(String itemRebateAmt) {
		this.itemRebateAmt = itemRebateAmt;
	}
	

	
}
