package com.bomwebportal.lts.dto.order;

import com.bomwebportal.dto.ObjectActionBaseDTO;

public class ChannelAttbLtsDTO extends ObjectActionBaseDTO {

	private static final long serialVersionUID = -6822048084425855583L;

	private String attbId;
	private String attbValue = null;

	
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
}
