package com.bomltsportal.dto.form;

import java.util.List;

public class BasketDetailSlideFormDTO {
	private List<BasketDetailFormDTO> basketFormList;
	private String selectedBasketId;

	public List<BasketDetailFormDTO> getBasketFormList() {
		return basketFormList;
	}

	public void setBasketFormList(List<BasketDetailFormDTO> basketFormList) {
		this.basketFormList = basketFormList;
	}

	public String getSelectedBasketId() {
		return selectedBasketId;
	}

	public void setSelectedBasketId(String selectedBasketId) {
		this.selectedBasketId = selectedBasketId;
	}
	
}
