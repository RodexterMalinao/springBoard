package com.bomwebportal.lts.dto.form;

import java.io.Serializable;
import java.util.List;

import com.bomwebportal.dto.AlertOrderDTO;

public class LtsAlertMessageFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1399892486603506780L;
	private List<AlertOrderDTO> orderList;
	
	
	public List<AlertOrderDTO> getOrderList() {
		return this.orderList;
	}
	public void setOrderList(List<AlertOrderDTO> pOrderList) {
		this.orderList = pOrderList;
	}
	
}
