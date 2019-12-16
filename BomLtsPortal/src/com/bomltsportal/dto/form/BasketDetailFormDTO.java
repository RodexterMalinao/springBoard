package com.bomltsportal.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ChannelGroupDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;
import com.bomwebportal.lts.dto.ItemSetDetailDTO;

public class BasketDetailFormDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8449347992096584547L;
	
	private boolean selectedAdultChannel;
	private String selectedNowTvGroupId;
	private String selectedNowTvChannelId;
	private List<ItemDetailDTO> planItemList;
	private List<ItemSetDetailDTO> contItemSetList;
	private List<ItemSetDetailDTO> premiumItemSetList;
	
	private List<ChannelGroupDetailDTO> channelGroupList;
	
	private List<ItemDetailDTO> bbRentalItemList;
	private List<ItemDetailDTO> installFeeItemList;
	
	private String displayBasketId;
	private BasketDetailDTO basketDetail;
	
	public List<ItemDetailDTO> getPlanItemList() {
		return planItemList;
	}
	public void setPlanItemList(List<ItemDetailDTO> planItemList) {
		this.planItemList = planItemList;
	}
	public List<ItemSetDetailDTO> getContItemSetList() {
		return contItemSetList;
	}
	public void setContItemSetList(List<ItemSetDetailDTO> contItemSetList) {
		this.contItemSetList = contItemSetList;
	}
	public List<ItemSetDetailDTO> getPremiumItemSetList() {
		return premiumItemSetList;
	}
	public void setPremiumItemSetList(List<ItemSetDetailDTO> premiumItemSetList) {
		this.premiumItemSetList = premiumItemSetList;
	}
	public List<ChannelGroupDetailDTO> getChannelGroupList() {
		return channelGroupList;
	}
	public void setChannelGroupList(List<ChannelGroupDetailDTO> channelGroupList) {
		this.channelGroupList = channelGroupList;
	}
	public boolean isSelectedAdultChannel() {
		return selectedAdultChannel;
	}
	public String getSelectedNowTvGroupId() {
		return selectedNowTvGroupId;
	}
	public void setSelectedAdultChannel(boolean selectedAdultChannel) {
		this.selectedAdultChannel = selectedAdultChannel;
	}
	public void setSelectedNowTvGroupId(String selectedNowTvGroupId) {
		this.selectedNowTvGroupId = selectedNowTvGroupId;
	}
	public String getSelectedNowTvChannelId() {
		return selectedNowTvChannelId;
	}
	public void setSelectedNowTvChannelId(String selectedNowTvChannelId) {
		this.selectedNowTvChannelId = selectedNowTvChannelId;
	}
	public List<ItemDetailDTO> getBbRentalItemList() {
		return bbRentalItemList;
	}
	public void setBbRentalItemList(List<ItemDetailDTO> bbRentalItemList) {
		this.bbRentalItemList = bbRentalItemList;
	}
	public List<ItemDetailDTO> getInstallFeeItemList() {
		return installFeeItemList;
	}
	public void setInstallFeeItemList(List<ItemDetailDTO> installFeeItemList) {
		this.installFeeItemList = installFeeItemList;
	}
	public String getDisplayBasketId() {
		return displayBasketId;
	}
	public void setDisplayBasketId(String displayBasketId) {
		this.displayBasketId = displayBasketId;
	}
	public BasketDetailDTO getBasketDetail() {
		return basketDetail;
	}
	public void setBasketDetail(BasketDetailDTO basketDetail) {
		this.basketDetail = basketDetail;
	} 
	
	
	

}
