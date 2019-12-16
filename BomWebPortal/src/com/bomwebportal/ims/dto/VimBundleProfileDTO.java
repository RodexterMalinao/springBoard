package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class VimBundleProfileDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8865864058708136962L;
	
	private String OrderId;
	private Date AppDate;
	private String WorkQueueSeq;
	private Date ReceivedDate;
	private String ShopCd;
	private String SalesCd;
	private String SalesChannel;
	private String FSA;
	private String LoginID;
	private String Title;
	private String FirstName;
	private String LastName;
	private String IDDocType;
	private String IDDocNo;
	private Date SRD;
	private String CampaignCd;
	private String CommitPeriod;
	private String ServiceType;
	private String TimeSlot;
	
	private List<Map<String, String>> Channels;
	private List<Map<String, String>> FreeHDChannels;

	public String getOrderId() {
		return OrderId;
	}

	public void setOrderId(String orderId) {
		OrderId = orderId;
	}

	public Date getAppDate() {
		return AppDate;
	}

	public void setAppDate(Date appDate) {
		AppDate = appDate;
	}

	public String getWorkQueueSeq() {
		return WorkQueueSeq;
	}

	public void setWorkQueueSeq(String workQueueSeq) {
		WorkQueueSeq = workQueueSeq;
	}

	public Date getReceivedDate() {
		return ReceivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		ReceivedDate = receivedDate;
	}

	public String getShopCd() {
		return ShopCd;
	}

	public void setShopCd(String shopCd) {
		ShopCd = shopCd;
	}

	public String getSalesCd() {
		return SalesCd;
	}

	public void setSalesCd(String salesCd) {
		SalesCd = salesCd;
	}

	public String getFSA() {
		return FSA;
	}

	public void setFSA(String fSA) {
		FSA = fSA;
	}

	public String getLoginID() {
		return LoginID;
	}

	public void setLoginID(String loginID) {
		LoginID = loginID;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getIDDocType() {
		return IDDocType;
	}

	public void setIDDocType(String iDDocType) {
		IDDocType = iDDocType;
	}

	public String getIDDocNo() {
		return IDDocNo;
	}

	public void setIDDocNo(String iDDocNo) {
		IDDocNo = iDDocNo;
	}

	public Date getSRD() {
		return SRD;
	}

	public void setSRD(Date sRD) {
		SRD = sRD;
	}

	public String getCampaignCd() {
		return CampaignCd;
	}

	public void setCampaignCd(String campaignCd) {
		CampaignCd = campaignCd;
	}

	public String getCommitPeriod() {
		return CommitPeriod;
	}

	public void setCommitPeriod(String commitPeriod) {
		CommitPeriod = commitPeriod;
	}

	public String getServiceType() {
		return ServiceType;
	}

	public void setServiceType(String serviceType) {
		ServiceType = serviceType;
	}

	public List<Map<String, String>> getChannels() {
		return Channels;
	}

	public void setChannels(List<Map<String, String>> channels) {
		Channels = channels;
	}

	public List<Map<String, String>> getFreeHDChannels() {
		return FreeHDChannels;
	}

	public void setFreeHDChannels(List<Map<String, String>> freeHDChannels) {
		FreeHDChannels = freeHDChannels;
	}

	public String getTimeSlot() {
		return TimeSlot;
	}

	public void setTimeSlot(String timeSlot) {
		TimeSlot = timeSlot;
	}

	public String getSalesChannel() {
		return SalesChannel;
	}

	public void setSalesChannel(String salesChannel) {
		SalesChannel = salesChannel;
	}
	
	
	
	

}
