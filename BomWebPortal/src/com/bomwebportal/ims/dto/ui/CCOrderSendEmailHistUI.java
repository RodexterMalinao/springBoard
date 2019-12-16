package com.bomwebportal.ims.dto.ui;

import com.bomwebportal.dto.OrderDTO.DisMode;
import com.bomwebportal.dto.ui.OrderEsignatureUI;

public class CCOrderSendEmailHistUI extends OrderEsignatureUI {
	private String SaleResendEmailAllowed;
	private DisMode method;
	private String teamCd;

	public void setSaleResendEmailAllowed(String saleResendEmailAllowed) {
		SaleResendEmailAllowed = saleResendEmailAllowed;
	}

	public String getSaleResendEmailAllowed() {
		return SaleResendEmailAllowed;
	}

	public void setMethod(DisMode method) {
		this.method = method;
	}

	public DisMode getMethod() {
		return method;
	}

	public void setTeamCd(String teamCd) {
		this.teamCd = teamCd;
	}

	public String getTeamCd() {
		return teamCd;
	}
}
