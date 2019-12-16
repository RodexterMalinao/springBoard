package com.bomwebportal.mob.ccs.dto.ui;

import java.util.Map;
import java.util.HashMap;

public class CancellationUI implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2517738761931826395L;

	public String getCodeId() {
		return codeId;
	}
	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	private String codeId;
	private String remark;
	private String orderId;
	private String lob;
	private String rowId;
	
	private String orderRecreateInd; //Y/N
	private String paymentTransferInd;//Y/N
	private String creditCardTrxInd;
	private String orderStatus;
	private String lockInd;
	
	private boolean multiSim;
	private String reserveMrtInd;
	private String preRegInd;
	
	public String getOrderRecreateInd() {
		return orderRecreateInd;
	}
	public void setOrderRecreateInd(String orderRecreateInd) {
		this.orderRecreateInd = orderRecreateInd;
	}
	public String getPaymentTransferInd() {
		return paymentTransferInd;
	}
	public void setPaymentTransferInd(String paymentTransferInd) {
		this.paymentTransferInd = paymentTransferInd;
	}
	public String getCreditCardTrxInd() {
		return creditCardTrxInd;
	}
	public void setCreditCardTrxInd(String creditCardTrxInd) {
		this.creditCardTrxInd = creditCardTrxInd;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getLockInd() {
		return lockInd;
	}
	public void setLockInd(String lockInd) {
		this.lockInd = lockInd;
	}
	public boolean isMultiSim() {
		return multiSim;
	}
	public void setMultiSim(boolean multiSim) {
		this.multiSim = multiSim;
	}
	
	public String getReserveMrtInd() {
		return reserveMrtInd;
	}
	public void setReserveMrtInd(String reserveMrtInd) {
		this.reserveMrtInd = reserveMrtInd;
	}
	/**
	 * An object map key to DTO value
	 */
	private Map<String, Object> objectsMap;

	public Map<String, Object> getObjectsMap() {
		return objectsMap;
	}

	public void setObjectsMap(Map<String, Object> objectsMap) {
		this.objectsMap = objectsMap;
	}

	/**
	 * Get specific DTO object value which map to certain key
	 * 
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
	 */
	public void setValue(String key, Object value) {
		if (this.objectsMap == null) {
			objectsMap = new HashMap<String, Object>();
		}

		objectsMap.put(key, value);
	}
	public String getPreRegInd() {
		return preRegInd;
	}
	public void setPreRegInd(String preRegInd) {
		this.preRegInd = preRegInd;
	}
	
	
}
