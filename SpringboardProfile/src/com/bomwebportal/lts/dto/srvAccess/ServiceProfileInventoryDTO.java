package com.bomwebportal.lts.dto.srvAccess;

import java.io.Serializable;

public class ServiceProfileInventoryDTO implements Serializable {

	private static final long serialVersionUID = -2392040515196731322L;

	private String dnExchangeId = null;

	private String networkInd = null;
	
	private String inventStatus = null;
	
	private boolean isSharedBsn = false;
	
	private boolean frozenExchInd = false;

	
	public String getDnExchangeId() {
		return dnExchangeId;
	}

	public void setDnExchangeId(String dnExchangeId) {
		this.dnExchangeId = dnExchangeId;
	}

	public String getNetworkInd() {
		return networkInd;
	}

	public void setNetworkInd(String networkInd) {
		this.networkInd = networkInd;
	}

	public String getInventStatus() {
		return inventStatus;
	}

	public void setInventStatus(String inventStatus) {
		this.inventStatus = inventStatus;
	}

	public boolean isFrozenExchInd() {
		return frozenExchInd;
	}

	public void setFrozenExchInd(boolean frozenExchInd) {
		this.frozenExchInd = frozenExchInd;
	}

	public boolean isSharedBsn() {
		return isSharedBsn;
	}

	public void setSharedBsn(boolean isSharedBsn) {
		this.isSharedBsn = isSharedBsn;
	}
}

