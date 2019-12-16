package com.bomwebportal.ims.dto.ui;

import java.util.List;


public class NowTVVasUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5248514987697312232L;

	private String ItemID;
	private String ItemDesc;
	private String VasType;
	private String ItemType;
	private String RecurrentAmt;
	private String MthToMthRate;
	private String Credit;
	private String TVType;
	private String GrpDesc;
	private String ChannelDesc;
	private String ChannelID;
	private String CouponDesc;

	private String TVList;
	
	private String VASTitle;  
	private String VASDetail; 
	
	private String MDOInd;
	
	private String topUpChecked;
	private String product_id;
	
	  
	
	public String getTopUpChecked() {
		return topUpChecked;
	}

	public void setTopUpChecked(String topUpChecked) {
		this.topUpChecked = topUpChecked;
	}

	public String getProduct_id() {
		return product_id;
	}

	public void setProduct_id(String product_id) {
		this.product_id = product_id;
	}

	public String getCouponDesc() {
		return CouponDesc;
	}

	public void setCouponDesc(String couponDesc) {
		CouponDesc = couponDesc;
	}

	public NowTVVasUI(){
		
	}

	public String getItemID() {
		return ItemID;
	}

	public void setItemID(String ItemID) {
		this.ItemID = ItemID;
	}
	
	public String getItemDesc() {
		return ItemDesc;
	}

	public void setItemDesc(String ItemDesc) {
		this.ItemDesc = ItemDesc;
	}
	
	public String getVasType() {
		return VasType;
	}

	public void setVasType(String VasType) {
		this.VasType = VasType;
	}

	public String getItemType() {
		return ItemType;
	}

	public void setItemType(String ItemType) {
		this.ItemType = ItemType;
	}
	
	public String getRecurrentAmt() {
		return RecurrentAmt;
	}

	public void setRecurrentAmt(String RecurrentAmt) {
		this.RecurrentAmt = RecurrentAmt;
	}
	
	public String getMthToMthRate() {
		return MthToMthRate;
	}

	public void setMthToMthRate(String MthToMthRate) {
		this.MthToMthRate = MthToMthRate;
	}
	
	public String getCredit() {
		return Credit;
	}

	public void setCredit(String Credit) {
		this.Credit = Credit;
	}
	
	public String getTVType() {
		return TVType;
	}

	public void setTVType(String tVType) {
		TVType = tVType;
	}

	public String getGrpDesc() {
		return GrpDesc;
	}

	public void setGrpDesc(String grpDesc) {
		GrpDesc = grpDesc;
	}

	public String getChannelDesc() {
		return ChannelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		ChannelDesc = channelDesc;
	}
	
	public String getChannelID() {
		return ChannelID;
	}

	public void setChannelID(String channelID) {
		ChannelID = channelID;
	}

	public String getTVList() {
		return TVList;
	}

	public void setTVList(String tVList) {
		TVList = tVList;
	}

	public String getVASTitle() {
		return VASTitle;
	}

	public void setVASTitle(String vASTitle) {
		VASTitle = vASTitle;
	}

	public String getVASDetail() {
		return VASDetail;
	}

	public void setVASDetail(String vASDetail) {
		VASDetail = vASDetail;
	}

	public String getMDOInd() {
		return MDOInd;
	}

	public void setMDOInd(String mDOInd) {
		MDOInd = mDOInd;
	}


	
}
