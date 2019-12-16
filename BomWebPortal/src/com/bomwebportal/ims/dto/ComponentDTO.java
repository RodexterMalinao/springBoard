package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class ComponentDTO implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5655005587337406019L;
	
	
	private String OrderId;
	private String AttbId;
	private String AttbValue;
	private String ItemId;
	private String ProductId;
	private Date CreateDate;
	private String CreateBy;
	private Date LastUpdDate;
	private String LastUpdBy;	
	
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	public String getAttbId() {
		return AttbId;
	}
	public void setAttbId(String attbId) {
		AttbId = attbId;
	}
	public String getAttbValue() {
		return AttbValue;
	}
	public void setAttbValue(String attbValue) {
		AttbValue = attbValue;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getLastUpdDate() {
		return LastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		LastUpdDate = lastUpdDate;
	}
	public String getLastUpdBy() {
		return LastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		LastUpdBy = lastUpdBy;
	}
	public String getItemId() {
		return ItemId;
	}
	public void setItemId(String itemId) {
		ItemId = itemId;
	}
	public String getProductId() {
		return ProductId;
	}
	public void setProductId(String productId) {
		ProductId = productId;
	}

	

}
