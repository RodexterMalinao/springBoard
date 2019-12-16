package com.bomwebportal.mobquota.dto;

import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MobQuotaUsageDTO extends MobQuotaInfoDTO {

	private int used;
	
	private List<MobQuotaUsageDetailDTO> usageDetails;
	
	//private MobQuotaAuthLogDTO authLog;
	private String orderType;
	private String orderId;
	private String authBy;



	public int getUsed() {
		return used;
	}
	
	public void setUsed(int used) {
		this.used = used;
	}
	
	public List<MobQuotaUsageDetailDTO> getUsageDetails() {
		return usageDetails;
	}

	public void setUsageDetails(List<MobQuotaUsageDetailDTO> usage) {
		this.usageDetails = usage;
		used = 0;
		if (usageDetails != null) {
			for (MobQuotaUsageDetailDTO dto: usageDetails) {
				used += dto.getQty();
			}
		}
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getAuthBy() {
		return authBy;
	}

	public void setAuthBy(String authBy) {
		this.authBy = authBy;
	}

	public boolean isOverQuota() {
		return (getUsed() > getCeilCnt() && StringUtils.isEmpty(getAuthBy()));
	}

}
