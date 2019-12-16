package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ImsOfferDetailDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -6167622244282310549L;

	private String offerId = null;
	private String productId = null;
	private String discIncInd = null;
	private String itemId = null;
	private String ioInd = null;
	private String penaltyHandling = null;
	private String itemType = null;

	
	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getDiscIncInd() {
		return this.discIncInd;
	}

	public void setDiscIncInd(String pDiscIncInd) {
		this.discIncInd = pDiscIncInd;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getIoInd() {
		return ioInd;
	}

	public void setIoInd(String ioInd) {
		this.ioInd = ioInd;
	}

	public String getPenaltyHandling() {
		return penaltyHandling;
	}

	public void setPenaltyHandling(String penaltyHandling) {
		this.penaltyHandling = penaltyHandling;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
}
