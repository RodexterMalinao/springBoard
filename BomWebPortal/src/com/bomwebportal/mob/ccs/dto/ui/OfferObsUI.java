package com.bomwebportal.mob.ccs.dto.ui;

import java.util.List;

import com.bomwebportal.dto.BasketDTO;

public class OfferObsUI implements java.io.Serializable {


	private static final long serialVersionUID = -6366551893741243261L;
	
	private String orderId;
	private String outBoundCallList;
	private String outBoundCallListDesc;
	List<BasketDTO> BasketList;
	
	

	public String getOutBoundCallList() {
		return outBoundCallList;
	}

	public void setOutBoundCallList(String outBoundCallList) {
		this.outBoundCallList = outBoundCallList;
	}

	public String getOutBoundCallListDesc() {
		return outBoundCallListDesc;
	}

	public void setOutBoundCallListDesc(String outBoundCallListDesc) {
		this.outBoundCallListDesc = outBoundCallListDesc;
	}

	public List<BasketDTO> getBasketList() {
		return BasketList;
	}

	public void setBasketList(List<BasketDTO> basketList) {
		BasketList = basketList;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
