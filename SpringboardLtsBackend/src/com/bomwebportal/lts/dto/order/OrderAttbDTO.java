package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class OrderAttbDTO extends ObjectActionBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7539158190134183960L;
	private String orderId; // BOMWEB_ORDER_SERVICE_ATTB.ORDER_ID
	private String dtlId; // BOMWEB_ORDER_SERVICE_ATTB.DTL_ID
	private String attbId; // BOMWEB_ORDER_SERVICE_ATTB.ATTB_ID
	private String attbValue; // BOMWEB_ORDER_SERVICE_ATTB.ATTB_VALUE
	private String attbName; // BOMWEB_ORDER_SERVICE_ATTB.ATTB_NAME
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDtlId() {
		return dtlId;
	}
	public void setDtlId(String dtlId) {
		this.dtlId = dtlId;
	}
	public String getAttbId() {
		return attbId;
	}
	public void setAttbId(String attbId) {
		this.attbId = attbId;
	}
	public String getAttbValue() {
		return attbValue;
	}
	public void setAttbValue(String attbValue) {
		this.attbValue = attbValue;
	}
	public String getAttbName() {
		return attbName;
	}
	public void setAttbName(String attbName) {
		this.attbName = attbName;
	}
	
	
}
