package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.lts.dto.CollectDocDto;

public class LtsCollectDocFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 915500864979890180L;

	private boolean isAmend;
	private String submitType;
	private String orderId = null;
	private String orderType = null;
	private List<CollectDocDto> collectDocDtoList = null;
	private String warningMsg;
	

	public boolean isAmend() {
		return isAmend;
	}

	public void setAmend(boolean isAmend) {
		this.isAmend = isAmend;
	}

	public String getSubmitType() {
		return submitType;
	}

	public void setSubmitType(String submitType) {
		this.submitType = submitType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public List<CollectDocDto> getCollectDocDtoList() {
		return collectDocDtoList;
	}

	public void setCollectDocDtoList(List<CollectDocDto> collectDocDtoList) {
		this.collectDocDtoList = collectDocDtoList;
	}

	public String getWarningMsg() {
		return warningMsg;
	}

	public void setWarningMsg(String warningMsg) {
		this.warningMsg = warningMsg;
	}

}
