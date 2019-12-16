package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ResDnLtsDTO extends ObjectActionBaseDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5558113217160501737L;
	
	private String orderId = null;
	private String srvNum = null;
	private String sessionId = null; 
	private String dnSource = null;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getSrvNum() {
		return srvNum;
	}
	public void setSrvNum(String srvNum) {
		this.srvNum = srvNum;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getDnSource() {
		return dnSource;
	}
	public void setDnSource(String dnSource) {
		this.dnSource = dnSource;
	} 	

	

}
