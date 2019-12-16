package com.bomwebportal.mob.ccs.dto;

import java.io.Serializable;
import java.util.Date;

public class DeliveryDTO implements Serializable {

	/**
	 * BOMWEB_DELIVERY table DTO
	 */
	private static final long serialVersionUID = -5784367544104221558L;
	private String orderId;
	private String deliveryInd;
	private String urgentInd;
	private String thirdInd;
	private Date deliveryDate;
	private String deliveryTimeSlot;
	private String deliveryCentre;
	private String lockInd;
	private String createBy;
	private Date createDate;
	private String lastUpdBy;
	private Date lastUpdDate;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDeliveryInd() {
		return deliveryInd;
	}

	public void setDeliveryInd(String deliveryInd) {
		this.deliveryInd = deliveryInd;
	}

	public String getUrgentInd() {
		return urgentInd;
	}

	public void setUrgentInd(String urgentInd) {
		this.urgentInd = urgentInd;
	}

	public String getThirdInd() {
		return thirdInd;
	}

	public void setThirdInd(String thirdInd) {
		this.thirdInd = thirdInd;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDeliveryTimeSlot() {
		return deliveryTimeSlot;
	}

	public void setDeliveryTimeSlot(String deliveryTimeSlot) {
		this.deliveryTimeSlot = deliveryTimeSlot;
	}

	public String getDeliveryCentre() {
		return deliveryCentre;
	}

	public void setDeliveryCentre(String deliveryCentre) {
		this.deliveryCentre = deliveryCentre;
	}

	public String getLockInd() {
		return lockInd;
	}

	public void setLockInd(String lockInd) {
		this.lockInd = lockInd;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLastUpdBy() {
		return lastUpdBy;
	}

	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}

	public Date getLastUpdDate() {
		return lastUpdDate;
	}

	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}

}
