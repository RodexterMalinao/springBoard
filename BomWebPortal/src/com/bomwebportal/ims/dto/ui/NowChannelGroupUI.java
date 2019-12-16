package com.bomwebportal.ims.dto.ui;

import java.util.List;



public class NowChannelGroupUI {
	private String itemId;
	private String productId;
	private String credit;
	private String itemType;
	private String groupId;
	private String groupCd;
	private String groupDesc;
	private String genreId;
	private String recurrentAmt;  
	private String mthToMthRate;   
	private String MthFixText;
	private String MthToMthText;
	private GenreUI genre;
	
	//for hd activation
	private String hditemId;
	private String hdDesc;
	
	private String checked;
	private String hdchecked;
	
	private List<NowChannelUI> channelList;
	private List<GenreUI> genreList;
	
	private String basketId;
	
	public void setGroupDesc(String groupDesc) {
		this.groupDesc = groupDesc;
	}
	public String getGroupDesc() {
		return groupDesc;
	}

	public void setGroupCd(String groupCd) {
		this.groupCd = groupCd;
	}
	public String getGroupCd() {
		return groupCd;
	}
	public void setGenreId(String genreId) {
		this.genreId = genreId;
	}
	public String getGenreId() {
		return genreId;
	}
	public void setHdDesc(String hdDesc) {
		this.hdDesc = hdDesc;
	}
	public String getHdDesc() {
		return hdDesc;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setCredit(String credit) {
		this.credit = credit;
	}
	public String getCredit() {
		return credit;
	}
	public void setRecurrentAmt(String recurrentAmt) {
		this.recurrentAmt = recurrentAmt;
	}
	public String getRecurrentAmt() {
		return recurrentAmt;
	}
	public void setMthToMthRate(String mthToMthRate) {
		this.mthToMthRate = mthToMthRate;
	}
	public String getMthToMthRate() {
		return mthToMthRate;
	}
	public void setMthFixText(String mthFixText) {
		MthFixText = mthFixText;
	}
	public String getMthFixText() {
		return MthFixText;
	}
	public void setMthToMthText(String mthToMthText) {
		MthToMthText = mthToMthText;
	}
	public String getMthToMthText() {
		return MthToMthText;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemType() {
		return itemType;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductId() {
		return productId;
	}
	public void setHditemId(String hditemId) {
		this.hditemId = hditemId;
	}
	public String getHditemId() {
		return hditemId;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getChecked() {
		return checked;
	}
	public void setHdchecked(String hdchecked) {
		this.hdchecked = hdchecked;
	}
	public String getHdchecked() {
		return hdchecked;
	}
	public void setChannelList(List<NowChannelUI> channelList) {
		this.channelList = channelList;
	}
	public List<NowChannelUI> getChannelList() {
		return channelList;
	}
	public void setGenreList(List<GenreUI> genreList) {
		this.genreList = genreList;
	}
	public List<GenreUI> getGenreList() {
		return genreList;
	}
	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}
	public String getBasketId() {
		return basketId;
	}
	public void setGenre(GenreUI genre) {
		this.genre = genre;
	}
	public GenreUI getGenre() {
		return genre;
	}
	
//	public IconUI getIcon(String iconId){
//		
//		for(NowChannelUI channel:channelList){
//			for(IconUI icon:channel.getIconList()){
//				if(icon.getIconId().equals(iconId)) return icon; 
//			}
//		}
//		
//		return null;
//	}
	
	public NowChannelUI getIcon(String iconId){
	
		for(NowChannelUI channel:channelList){
				if(channel.getIconId().equals(iconId)) return channel; 
		}
		
		return null;
	}
}
