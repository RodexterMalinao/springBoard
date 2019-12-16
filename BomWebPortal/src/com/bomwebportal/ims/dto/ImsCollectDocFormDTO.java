package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.bomwebportal.ims.dto.ImsCollectDocDTO;

public class ImsCollectDocFormDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 915500864979890180L;

	private String orderId = null;
	private List<ImsCollectDocDTO> imsCollectDocDtoList = null;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<ImsCollectDocDTO> getImsCollectDocDtoList() {
		return imsCollectDocDtoList;
	}

	public void setImsCollectDocDtoList(List<ImsCollectDocDTO> imsCollectDocDtoList) {
		this.imsCollectDocDtoList = imsCollectDocDtoList;
	}

}
