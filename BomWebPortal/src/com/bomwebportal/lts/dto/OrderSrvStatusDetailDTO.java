package com.bomwebportal.lts.dto;

import java.io.Serializable;

import com.bomwebportal.lts.dto.profile.PendingOrdStatusDetailDTO;

public class OrderSrvStatusDetailDTO extends PendingOrdStatusDetailDTO
		implements Serializable {

	private static final long serialVersionUID = -2785625815601014782L;
	
	private String srvId;
	private String typeOfSrv;

	public String getSrvId() {
		return srvId;
	}

	public void setSrvId(String srvId) {
		this.srvId = srvId;
	}

	public String getTypeOfSrv() {
		return typeOfSrv;
	}

	public void setTypeOfSrv(String typeOfSrv) {
		this.typeOfSrv = typeOfSrv;
	}

}
