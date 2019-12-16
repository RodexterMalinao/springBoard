package com.bomwebportal.lts.dto;

import java.io.Serializable;
import java.util.List;

public class BasketSelectionInfoDTO implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6039054061559794050L;
	
	List<BasketDetailDTO> hotBasketList;
	List<BasketDetailDTO> regularBasketList;
	// Retention Basket
	private List<BasketDetailDTO> premiumBasketList;
	private List<BasketDetailDTO> rebateBasketList;
	private List<BasketDetailDTO> rebateCouponBasketList;
	private List<BasketDetailDTO> otherBasketList;
	private List<BasketDetailDTO> couponIddBasketList;
	private List<BasketDetailDTO> iddBasketList;
	private List<BasketDetailDTO> rebateIddBasketList;
	
	
	DeviceDetailDTO selectedDevice;
	
	private String warningMsg;
	
	public List<BasketDetailDTO> getHotBasketList() {
		return hotBasketList;
	}
	public void setHotBasketList(List<BasketDetailDTO> hotBasketList) {
		this.hotBasketList = hotBasketList;
	}
	public List<BasketDetailDTO> getRegularBasketList() {
		return regularBasketList;
	}
	public void setRegularBasketList(List<BasketDetailDTO> regularBasketList) {
		this.regularBasketList = regularBasketList;
	}
	public DeviceDetailDTO getSelectedDevice() {
		return selectedDevice;
	}
	public void setSelectedDevice(DeviceDetailDTO selectedDevice) {
		this.selectedDevice = selectedDevice;
	}
	public List<BasketDetailDTO> getPremiumBasketList() {
		return premiumBasketList;
	}
	public void setPremiumBasketList(List<BasketDetailDTO> premiumBasketList) {
		this.premiumBasketList = premiumBasketList;
	}
	public List<BasketDetailDTO> getRebateBasketList() {
		return rebateBasketList;
	}
	public void setRebateBasketList(List<BasketDetailDTO> rebateBasketList) {
		this.rebateBasketList = rebateBasketList;
	}
	public List<BasketDetailDTO> getRebateCouponBasketList() {
		return rebateCouponBasketList;
	}
	public void setRebateCouponBasketList(
			List<BasketDetailDTO> rebateCouponBasketList) {
		this.rebateCouponBasketList = rebateCouponBasketList;
	}
	public List<BasketDetailDTO> getOtherBasketList() {
		return otherBasketList;
	}
	public void setOtherBasketList(List<BasketDetailDTO> otherBasketList) {
		this.otherBasketList = otherBasketList;
	}
	public String getWarningMsg() {
		return warningMsg;
	}
	public void setWarningMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}
	public List<BasketDetailDTO> getCouponIddBasketList() {
		return couponIddBasketList;
	}
	public void setCouponIddBasketList(List<BasketDetailDTO> couponIddBasketList) {
		this.couponIddBasketList = couponIddBasketList;
	}
	public List<BasketDetailDTO> getIddBasketList() {
		return iddBasketList;
	}
	public void setIddBasketList(List<BasketDetailDTO> iddBasketList) {
		this.iddBasketList = iddBasketList;
	}
	public List<BasketDetailDTO> getRebateIddBasketList() {
		return rebateIddBasketList;
	}
	public void setRebateIddBasketList(List<BasketDetailDTO> rebateIddBasketList) {
		this.rebateIddBasketList = rebateIddBasketList;
	}
	
}
