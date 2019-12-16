package com.bomwebportal.mob.ccs.dto;

import java.util.HashMap;
import java.util.Map;

public class PreviewDTO {
	/**
	 * An object map key to DTO value
	 */
	private Map<String, Object> objectsMap;
	
	/**
	 * Error path. Error message binds to error path on jsp page.
	 */
	private String topErrorPath;
	/**
	 * Store session of Order ID for data retrieving purpose
	 */
	private String orderId;

	/**
	 * @return the orderId
	 */
	public String getOrderId() {
		return orderId;
	}
	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	/**
	 * @return the topErrorPath
	 */
	public String getTopErrorPath() {
		return topErrorPath;
	}
	/**
	 * @param topErrorPath the topErrorPath to set
	 */
	public void setTopErrorPath(String topErrorPath) {
		this.topErrorPath = topErrorPath;
	}
	
	/**
	 * Getter method
	 * @return 
	 */
	public Map<String, Object> getObjectsMap() {
		return objectsMap;
	}
	/**
	 * Setter method
	 * @param objectsMap
	 */
	public void setObjectsMap(Map<String, Object> objectsMap) {
		this.objectsMap = objectsMap;
	}
	/**
	 * Get specific DTO object value which map to certain key
	 * @param key
	 * @return DTO object
	 */
	public Object getValue(String key) {
		if (this.objectsMap == null || this.objectsMap.isEmpty()) {
			return null;
		} else {
			return this.objectsMap.get(key);
		}
	}
	/**
	 * Set specific DTO object value into map which match with a unique key
	 * @param key
	 * @param value DTO object
	 */
	public void setValue(String key, Object value) {
		if (this.objectsMap == null) {
			objectsMap = new HashMap<String, Object>();
		}
		
		objectsMap.put(key, value);
	}
	
	private String orderType;

	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	
	private boolean ignoreQuotaCheck = false;

	public boolean isIgnoreQuotaCheck() {
		return ignoreQuotaCheck;
	}
	
	public void setIgnoreQuotaCheck(boolean ignoreQuotaCheck) {
		this.ignoreQuotaCheck = ignoreQuotaCheck;
	}
	
	private boolean ignoreHSRMCheck = false;

	public boolean isIgnoreHSRMCheck() {
		return ignoreHSRMCheck;
	}
	public void setIgnoreHSRMCheck(boolean ignoreHSRMCheck) {
		this.ignoreHSRMCheck = ignoreHSRMCheck;
	}
	
	
}
