package com.bomwebportal.dto;

import java.util.List;

public class VasDetailDTO implements java.io.Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2451050374193828631L;
	String itemId;
	String itemLob;
	String itemMdoInd;
	String itemType;
	String itemHtml;
	String itemHtml2;	
	String itemLocale;
	String itemDisplayType;
	String itemOnetimeAmt;
	String itemRecurrentAmt;
	String itemRebateAmt;
	
	String [] vasitem;
	
	List<ProductInfoDTO> productList;

	public List<ProductInfoDTO> getProductList() {
		return productList;
	}
	public void setProductList(List<ProductInfoDTO> productList) {
		this.productList = productList;
	}
	public String[] getVasitem() {
		return vasitem;
	}
	public void setVasitem(String[] vasitem) {
		this.vasitem = vasitem;
	}
	public String getItemRebateAmt() {
		return itemRebateAmt;
	}
	public void setItemRebateAmt(String itemRebateAmt) {
		this.itemRebateAmt = itemRebateAmt;
	}
	public String getItemOnetimeAmt() {
		return itemOnetimeAmt;
	}
	public void setItemOnetimeAmt(String itemOnetimeAmt) {
		this.itemOnetimeAmt = itemOnetimeAmt;
	}
	public String getItemRecurrentAmt() {
		return itemRecurrentAmt;
	}
	public void setItemRecurrentAmt(String itemRecurrentAmt) {
		this.itemRecurrentAmt = itemRecurrentAmt;
	}

	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemLob() {
		return itemLob;
	}
	public void setItemLob(String itemLob) {
		this.itemLob = itemLob;
	}
	public String getItemMdoInd() {
		return itemMdoInd;
	}
	public void setItemMdoInd(String itemMdoInd) {
		this.itemMdoInd = itemMdoInd;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
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
	public String getItemLocale() {
		return itemLocale;
	}
	public void setItemLocale(String itemLocale) {
		this.itemLocale = itemLocale;
	}
	public String getItemDisplayType() {
		return itemDisplayType;
	}
	public void setItemDisplayType(String itemDisplayType) {
		this.itemDisplayType = itemDisplayType;
	}
	

}
