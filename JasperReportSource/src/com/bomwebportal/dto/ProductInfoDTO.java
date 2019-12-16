package com.bomwebportal.dto;
import com.pccw.bom.mob.schemas.ProductDTO;

public class  ProductInfoDTO extends ProductDTO{

	
	private static final long serialVersionUID = 2812532781852363814L;
	String featureDisplay;//FEATURE_DISPLAY
	String pcmProduct;//PCM_PRODUCT;
	String pcmOffer;//PCM_OFFER;
	
	String posItemCode;//ippa.pos_item_cd,add by wilson 20110426
	String itemId;//for hidden Item display,add by wilson 20110426
	String basketId;//for hidden Item display,add by wilson 20110426
	
	public String getItemId() {
		return itemId;
	}
	public String getBasketId() {
		return basketId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public String getFeatureDisplay() {
		return featureDisplay;
	}
	public void setFeatureDisplay(String featureDisplay) {
		this.featureDisplay = featureDisplay;
	}
	public String getPcmProduct() {
		return pcmProduct;
	}
	public void setPcmProduct(String pcmProduct) {
		this.pcmProduct = pcmProduct;
	}
	public String getPcmOffer() {
		return pcmOffer;
	}
	public void setPcmOffer(String pcmOffer) {
		this.pcmOffer = pcmOffer;
	}
	public String getPosItemCode() {
		return posItemCode;
	}
	public void setPosItemCode(String posItemCode) {
		this.posItemCode = posItemCode;
	}
	
	
}
