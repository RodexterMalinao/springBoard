package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class OrderLtsAmendDetailDTO extends ObjectActionBaseDTO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6356202442720368267L;
	private String orderId;
	private String amendCat;
	private String amendSubCat;
	private String amendItem;
	private String amendValue;
	private String itemSeq;
	private String itemSubSeq;
	
	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the amendCat
	 */
	public String getAmendCat() {
		return amendCat;
	}
	/**
	 * @param amendCat the amendCat to set
	 */
	public void setAmendCat(String amendCat) {
		this.amendCat = amendCat;
	}
	/**
	 * @return the amendSubCat
	 */
	public String getAmendSubCat() {
		return amendSubCat;
	}
	/**
	 * @param amendSubCat the amendSubCat to set
	 */
	public void setAmendSubCat(String amendSubCat) {
		this.amendSubCat = amendSubCat;
	}
	/**
	 * @return the amendItem
	 */
	public String getAmendItem() {
		return amendItem;
	}
	/**
	 * @param amendItem the amendItem to set
	 */
	public void setAmendItem(String amendItem) {
		this.amendItem = amendItem;
	}
	/**
	 * @return the amendValue
	 */
	public String getAmendValue() {
		return amendValue;
	}
	/**
	 * @param amendValue the amendValue to set
	 */
	public void setAmendValue(String amendValue) {
		this.amendValue = amendValue;
	}
	/**
	 * @return the itemSeq
	 */
	public String getItemSeq() {
		return itemSeq;
	}
	/**
	 * @param itemSeq the itemSeq to set
	 */
	public void setItemSeq(String itemSeq) {
		this.itemSeq = itemSeq;
	}
	/**
	 * @return the itemSubSeq
	 */
	public String getItemSubSeq() {
		return itemSubSeq;
	}
	/**
	 * @param itemSubSeq the itemSubSeq to set
	 */
	public void setItemSubSeq(String itemSubSeq) {
		this.itemSubSeq = itemSubSeq;
	}	

}
