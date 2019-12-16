package com.bomwebportal.ims.dto.ui;


public class NowChannelUI extends ChannelUI{
	
	private GenreUI genre;
	private String channelName;
	private String isNowSelect;
	private String isNowPlayer;
	private String supportHD;
	private String supportSPHD;
	private String isNowPRecord;
	
	//for pricing 2.0
	private String key;
	private String iconId;
	private String channelId;
	private String imagePath;
	private String imageDesc;
	private String url;
	private String isFoxPlus; // martin, 20171116, BOM2017154
	
	public String getIconId() {
		return iconId;
	}
	public void setIconId(String iconId) {
		this.iconId = iconId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getImageDesc() {
		return imageDesc;
	}
	public void setImageDesc(String imageDesc) {
		this.imageDesc = imageDesc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setIsNowSelect(String isNowSelect) {
		this.isNowSelect = isNowSelect;
	}
	public String getIsNowSelect() {
		return isNowSelect;
	}
	public void setIsNowPlayer(String isNowPlayer) {
		this.isNowPlayer = isNowPlayer;
	}
	public String getIsNowPlayer() {
		return isNowPlayer;
	}
	public void setGenre(GenreUI genre) {
		this.genre = genre;
	}
	public GenreUI getGenre() {
		return genre;
	}
	public void setSupportHD(String supportHD) {
		this.supportHD = supportHD;
	}
	public String getSupportHD() {
		return supportHD;
	}
	public void setSupportSPHD(String supportSPHD) {
		this.supportSPHD = supportSPHD;
	}
	public String getSupportSPHD() {
		return supportSPHD;
	}
	public void setIsNowPRecord(String isNowPRecord) {
		this.isNowPRecord = isNowPRecord;
	}
	public String getIsNowPRecord() {
		return isNowPRecord;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getKey() {
		return key;
	}
	public String getIsFoxPlus() {
		return isFoxPlus;
	}
	public void setIsFoxPlus(String isFoxPlus) {
		this.isFoxPlus = isFoxPlus;
	}

}
