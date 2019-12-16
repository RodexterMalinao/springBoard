package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;

public class MobCcsSupportDocUI implements Serializable{
	
	private String orderId;
	/*private boolean waiveIdDoCopyInd = false;*/
	
	private boolean authorized;
	
	/*public boolean isWaiveIdDoCopyInd() {
		return waiveIdDoCopyInd;
	}
	public void setWaiveIdDoCopyInd(boolean waiveIdDoCopyInd) {
		this.waiveIdDoCopyInd = waiveIdDoCopyInd;
	}*/
	private List<MobCcsSupportDocDTO> mobCcsSupportDocDTOList = LazyList
			.decorate(new ArrayList(),
					FactoryUtils.instantiateFactory(MobCcsSupportDocDTO.class));	
	
	private String mobCcsSupportDocError;
	
	public boolean isAuthorized() {
		return authorized;
	}
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}
	public String getMobCcsSupportDocError() {
		return mobCcsSupportDocError;
	}
	public void setMobCcsSupportDocError(String mobCcsSupportDocError) {
		this.mobCcsSupportDocError = mobCcsSupportDocError;
	}
	public List<MobCcsSupportDocDTO> getMobCcsSupportDocDTOList() {
		return mobCcsSupportDocDTOList;
	}
	public void setMobCcsSupportDocDTOList(
			List<MobCcsSupportDocDTO> mobCcsSupportDocDTOList) {
		this.mobCcsSupportDocDTOList = mobCcsSupportDocDTOList;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	
}
