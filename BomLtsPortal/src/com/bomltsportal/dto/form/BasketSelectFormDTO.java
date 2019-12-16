package com.bomltsportal.dto.form;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.bomltsportal.dto.OnlineBasketDTO;
import com.bomwebportal.lts.dto.BasketDetailDTO;

public class BasketSelectFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1014826685507522597L;

	private String selectedBasketId; 
	private String offerValidDate;
	
	private List<OnlineBasketDTO> onlineBasketList;

	public BasketDetailDTO getSelectedBasket(){
		for(OnlineBasketDTO bsk: this.onlineBasketList){
			if(StringUtils.equals(bsk.getBasketDetail().getBasketId(),this.selectedBasketId)){
				return bsk.getBasketDetail();
			}
		}
		return null;
	}
	
	public List<OnlineBasketDTO> getOnlineBasketList() {
		return onlineBasketList;
	}

	public void setOnlineBasketList(List<OnlineBasketDTO> onlineBasketList) {
		this.onlineBasketList = onlineBasketList;
	}

	public String getSelectedBasketId() {
		return selectedBasketId;
	}

	public void setSelectedBasketId(String selectedBasketId) {
		this.selectedBasketId = selectedBasketId;
	}

	public String getOfferValidDate() {
		return offerValidDate;
	}

	public void setOfferValidDate(String offerValidDate) {
		this.offerValidDate = offerValidDate;
	}
	

}
