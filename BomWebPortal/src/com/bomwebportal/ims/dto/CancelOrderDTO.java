package com.bomwebportal.ims.dto;

import java.io.Serializable;

public class CancelOrderDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8743761741232169953L;
	
	private String OrderId;
	private String LoginId;
	private String IdDocNum;
	private String IdDocType;
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getLoginId() {
		return LoginId;
	}
	public void setLoginId(String loginId) {
		LoginId = loginId;
	}
	public String getIdDocNum() {
		return IdDocNum;
	}
	public void setIdDocNum(String idDocNum) {
		IdDocNum = idDocNum;
	}
	public String getIdDocType() {
		return IdDocType;
	}
	public void setIdDocType(String idDocType) {
		IdDocType = idDocType;
	}	

}
