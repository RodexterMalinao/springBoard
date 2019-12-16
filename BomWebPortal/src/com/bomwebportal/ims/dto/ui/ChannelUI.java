package com.bomwebportal.ims.dto.ui;

import java.util.List;


public class ChannelUI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5248514987697312232L;

	private String ChannelID;
	private String ChannelCD;
	private String Credit;
	private String IsAdult;
	private String GroupDesc;
	private String ChannelDesc;
	private String TVType;
	private String MDOInd;
	
	private String ChildChID;
	private String ChildChCD;
	private String ChildCredit;
	private String ChildIsAdult;
	private String ChildChDesc;
	private String ChildChTVType;
	private String ChildChMDOInd;
	
	private String ChannelID_B;
	private String ChannelCD_B;
	private String ChannelDesc_B;

	private String qualityConstraint;
	private String VasItemId;
	private String basketId;
	
	
	
	public String getVasItemId() {
		return VasItemId;
	}

	public void setVasItemId(String vasItemId) {
		VasItemId = vasItemId;
	}

	public String getBasketId() {
		return basketId;
	}

	public void setBasketId(String basketId) {
		this.basketId = basketId;
	}

	public String getQualityConstraint() {
		return qualityConstraint;
	}

	public void setQualityConstraint(String qualityConstraint) {
		this.qualityConstraint = qualityConstraint;
	}

	public ChannelUI(){
		
	}

	public String getChannelID() {
		return ChannelID;
	}

	public void setChannelID(String ChannelID) {
		this.ChannelID = ChannelID;
	}
	
	public String getChannelCD() {
		return ChannelCD;
	}

	public void setChannelCD(String ChannelCD) {
		this.ChannelCD = ChannelCD;
	}
	
	public String getCredit() {
		return Credit;
	}

	public void setCredit(String Credit) {
		this.Credit = Credit;
	}
	
	public String getIsAdult() {
		return IsAdult;
	}

	public void setIsAdult(String IsAdult) {
		this.IsAdult = IsAdult;
	}
	
	public String getGroupDesc() {
		return GroupDesc;
	}

	public void setGroupDesc(String GroupDesc) {
		this.GroupDesc = GroupDesc;
	}

	
	public String getChannelDesc() {
		return ChannelDesc;
	}

	public void setChannelDesc(String ChannelDesc) {
		this.ChannelDesc = ChannelDesc;
	}

	public String getTVType() {
		return TVType;
	}

	public void setTVType(String TVType) {
		this.TVType = TVType;
	}
	
	public String getMDOInd() {
		return MDOInd;
	}

	public void setMDOInd(String mDOInd) {
		MDOInd = mDOInd;
	}

	public String getChildChID() {
		return ChildChID;
	}

	public void setChildChID(String childChID) {
		ChildChID = childChID;
	}

	public String getChildChCD() {
		return ChildChCD;
	}

	public void setChildChCD(String childChCD) {
		ChildChCD = childChCD;
	}

	public String getChildCredit() {
		return ChildCredit;
	}

	public void setChildCredit(String childCredit) {
		ChildCredit = childCredit;
	}

	public String getChildIsAdult() {
		return ChildIsAdult;
	}

	public void setChildIsAdult(String childIsAdult) {
		ChildIsAdult = childIsAdult;
	}

	public String getChildChDesc() {
		return ChildChDesc;
	}

	public void setChildChDesc(String childChDesc) {
		ChildChDesc = childChDesc;
	}

	public String getChildChTVType() {
		return ChildChTVType;
	}

	public void setChildChTVType(String childChTVType) {
		ChildChTVType = childChTVType;
	}

	public String getChildChMDOInd() {
		return ChildChMDOInd;
	}

	public void setChildChMDOInd(String childChMDOInd) {
		ChildChMDOInd = childChMDOInd;
	}

	public String getChannelID_B() {
		return ChannelID_B;
	}

	public void setChannelID_B(String channelID_B) {
		ChannelID_B = channelID_B;
	}

	public String getChannelCD_B() {
		return ChannelCD_B;
	}

	public void setChannelCD_B(String channelCD_B) {
		ChannelCD_B = channelCD_B;
	}

	public String getChannelDesc_B() {
		return ChannelDesc_B;
	}

	public void setChannelDesc_B(String channelDesc_B) {
		ChannelDesc_B = channelDesc_B;
	}
}
