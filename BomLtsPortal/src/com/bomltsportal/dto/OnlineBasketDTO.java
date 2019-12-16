package com.bomltsportal.dto;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;

public class OnlineBasketDTO implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8055180161862833935L;
	
	
	private boolean selected;
	private BasketDetailDTO basketDetail;
	private List<ItemDetailDTO> imageItemList;
	private List<ItemSetDetailDTO> contentImageSetList;
	private List<ItemSetDetailDTO> premiumImageSetList;
	private List<ItemSetDetailDTO> premiumDelItemSetList;
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public BasketDetailDTO getBasketDetail() {
		return basketDetail;
	}
	public void setBasketDetail(BasketDetailDTO basketDetail) {
		this.basketDetail = basketDetail;
	}
	public List<ItemDetailDTO> getImageItemList() {
		return imageItemList;
	}
	public void setImageItemList(List<ItemDetailDTO> imageItemList) {
		this.imageItemList = imageItemList;
	}
	public List<ItemSetDetailDTO> getContentImageSetList() {
		return contentImageSetList;
	}
	public void setContentImageSetList(List<ItemSetDetailDTO> contentImageSetList) {
		this.contentImageSetList = contentImageSetList;
	}
	public List<ItemSetDetailDTO> getPremiumImageSetList() {
		return premiumImageSetList;
	}
	public void setPremiumImageSetList(List<ItemSetDetailDTO> premiumImageSetList) {
		this.premiumImageSetList = premiumImageSetList;
	}
	public List<ItemSetDetailDTO> getPremiumDelItemSetList() {
		return premiumDelItemSetList;
	}
	public void setPremiumDelItemSetList(
			List<ItemSetDetailDTO> premiumDelItemSetList) {
		this.premiumDelItemSetList = premiumDelItemSetList;
	}
	
	
}
