package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;

public class DsDedicatedStaffDTO implements Serializable {

	private static final long serialVersionUID = -3803437696670122841L;

	private String staffListName;

	private List<String> staffIdList;

	private List<String> offerDescList;
	private List<String> vasDescList;
	private List<String> giftDescList;

	private String createDate;
	private String createBy;
	private String lastUpDate;
	private String lastUpBy;

	public String getStaffListName() {
		return staffListName;
	}

	public void setStaffListName(String staffListName) {
		this.staffListName = staffListName;
	}

	public List<String> getStaffIdList() {
		return staffIdList;
	}

	public void setStaffIdList(List<String> staffIdList) {
		this.staffIdList = staffIdList;
	}

	public List<String> getOfferDescList() {
		return offerDescList;
	}

	public void setOfferDescList(List<String> offerDescList) {
		this.offerDescList = offerDescList;
	}

	public List<String> getVasDescList() {
		return vasDescList;
	}

	public void setVasDescList(List<String> vasDescList) {
		this.vasDescList = vasDescList;
	}

	public List<String> getGiftDescList() {
		return giftDescList;
	}

	public void setGiftDescList(List<String> giftDescList) {
		this.giftDescList = giftDescList;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getLastUpDate() {
		return lastUpDate;
	}

	public void setLastUpDate(String lastUpDate) {
		this.lastUpDate = lastUpDate;
	}

	public String getLastUpBy() {
		return lastUpBy;
	}

	public void setLastUpBy(String lastUpBy) {
		this.lastUpBy = lastUpBy;
	}
}
