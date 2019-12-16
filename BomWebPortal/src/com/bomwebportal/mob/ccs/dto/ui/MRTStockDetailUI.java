package com.bomwebportal.mob.ccs.dto.ui;

import java.util.List;

import com.bomwebportal.mob.ccs.dto.MrtStatusDTO;

public class MRTStockDetailUI extends MRTStockUI {
	private boolean override;
	private List<MrtStatusDTO> msisdnReserveRecords;

	public boolean isOverride() {
		return override;
	}

	public void setOverride(boolean override) {
		this.override = override;
	}

	public List<MrtStatusDTO> getMsisdnReserveRecords() {
		return msisdnReserveRecords;
	}

	public void setMsisdnReserveRecords(List<MrtStatusDTO> msisdnReserveRecords) {
		this.msisdnReserveRecords = msisdnReserveRecords;
	}
}
