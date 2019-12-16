package com.bomltsportal.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.BasketDetailDTO;
import com.bomwebportal.lts.dto.ChannelDetailDTO;
import com.bomwebportal.lts.dto.ItemDetailDTO;

public class ShoppingCartFormDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5277152979246601489L;
	
	/*plan*/
	private BasketDetailDTO basketDetail;
	private List<ItemDetailDTO> contentItemList;
	
	/*premium*/
	private List<ItemDetailDTO> premiumItemList;

	/*optional service*/
//	private List<ItemDetailDTO> vasItemList;
	private List<ChannelDetailDTO> vasNowTvSpecItems;
	private List<ChannelDetailDTO> vasNowTvPayItems;
	private List<ItemDetailDTO> vasMoovItems;
	private List<ItemDetailDTO> vasOtherItems;
	
	/*charge*/
	private String serviceCharge;
	private String totalAmount;
	private String installFee;
	
	public BasketDetailDTO getBasketDetail() {
		return basketDetail;
	}
	public void setBasketDetail(BasketDetailDTO basketDetail) {
		this.basketDetail = basketDetail;
	}
//	public List<ItemDetailDTO> getVasItemList() {
//		return vasItemList;
//	}
//	public void setVasItemList(List<ItemDetailDTO> vasItemList) {
//		this.vasItemList = vasItemList;
//	}
	public List<ItemDetailDTO> getPremiumItemList() {
		return premiumItemList;
	}
	public void setPremiumItemList(List<ItemDetailDTO> premiumItemList) {
		this.premiumItemList = premiumItemList;
	}
	public List<ItemDetailDTO> getContentItemList() {
		return contentItemList;
	}
	public void setContentItemList(List<ItemDetailDTO> contentItemList) {
		this.contentItemList = contentItemList;
	}
	public List<ChannelDetailDTO> getVasNowTvSpecItems() {
		return vasNowTvSpecItems;
	}
	public void setVasNowTvSpecItems(List<ChannelDetailDTO> vasNowTvSpecItems) {
		this.vasNowTvSpecItems = vasNowTvSpecItems;
	}
	public List<ChannelDetailDTO> getVasNowTvPayItems() {
		return vasNowTvPayItems;
	}
	public void setVasNowTvPayItems(List<ChannelDetailDTO> vasNowTvPayItems) {
		this.vasNowTvPayItems = vasNowTvPayItems;
	}
	public List<ItemDetailDTO> getVasMoovItems() {
		return vasMoovItems;
	}
	public void setVasMoovItems(List<ItemDetailDTO> vasMoovItems) {
		this.vasMoovItems = vasMoovItems;
	}
	public List<ItemDetailDTO> getVasOtherItems() {
		return vasOtherItems;
	}
	public void setVasOtherItems(List<ItemDetailDTO> vasOtherItems) {
		this.vasOtherItems = vasOtherItems;
	}
	public String getServiceCharge() {
		return serviceCharge;
	}
	public void setServiceCharge(String serviceCharge) {
		this.serviceCharge = serviceCharge;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getInstallFee() {
		return installFee;
	}
	public void setInstallFee(String installFee) {
		this.installFee = installFee;
	}
	
	
}
