package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.ims.dto.ui.VASDetailUI;

public class ImsRptGiftDTO implements Serializable {

	private static final long serialVersionUID = 629210834742496125L;

	private String itemID;
	private String itemDetails;
	private String giftTitle;
	private String giftDetail;
	private String gift_start_date;
	private String gift_end_date;
	private String gift_type;
	List<AttbDTO> giftAttbList;
	private List<ImsRptBasketItemDTO> dediVASList;
	
	public List<AttbDTO> getGiftAttbList() {
		return giftAttbList;
	}

	public void setGiftAttbList(List<AttbDTO> giftAttbList) {
		this.giftAttbList = giftAttbList;
	}
	public String getGift_start_date() {
		return gift_start_date;
	}
	public void setGift_start_date(String gift_start_date) {
		this.gift_start_date = gift_start_date;
	}
	public String getGift_end_date() {
		return gift_end_date;
	}
	public void setGift_end_date(String gift_end_date) {
		this.gift_end_date = gift_end_date;
	}
	public void setItemDetails(String itemDetails) {
		this.itemDetails = itemDetails;
	}
	public String getItemDetails() {
		return itemDetails;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getItemID() {
		return itemID;
	}
	public void setGiftTitle(String giftTitle) {
		this.giftTitle = giftTitle;
	}
	public String getGiftTitle() {
		return giftTitle;
	}
	public void setGiftDetail(String giftDetail) {
		this.giftDetail = giftDetail;
	}
	public String getGiftDetail() {
		return giftDetail;
	}

	public void setGift_type(String gift_type) {
		this.gift_type = gift_type;
	}

	public String getGift_type() {
		return gift_type;
	}

	public void setDediVASList(List<ImsRptBasketItemDTO> dediVASList) {
		this.dediVASList = dediVASList;
	}

	public List<ImsRptBasketItemDTO> getDediVASList() {
		return dediVASList;
	}

}
