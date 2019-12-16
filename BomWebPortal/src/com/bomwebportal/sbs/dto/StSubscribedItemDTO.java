package com.bomwebportal.sbs.dto;

import java.math.BigDecimal;
import java.util.Date;

public class StSubscribedItemDTO {
	private String orderId;
	private String seq;
	private String parentItemId;
	private String itemId;
	private String itemType;
	private String itemDesc;
	private String itemSerial;
	private String posItemCd;
	private BigDecimal onetimeAmt;
	
	private String chargeClass;
	private String promoCode;
	private String promoCodeMrt;

	private String doaOldItemSerial;
	private String doaOldItemCode;
	
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getParentItemId() {
		return parentItemId;
	}
	public void setParentItemId(String parentItemId) {
		this.parentItemId = parentItemId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getItemSerial() {
		return itemSerial;
	}
	public void setItemSerial(String itemSerial) {
		this.itemSerial = itemSerial;
	}
	public String getPosItemCd() {
		return posItemCd;
	}
	public void setPosItemCd(String posItemCd) {
		this.posItemCd = posItemCd;
	}
	public BigDecimal getOnetimeAmt() {
		return onetimeAmt;
	}
	public void setOnetimeAmt(BigDecimal onetimeAmt) {
		this.onetimeAmt = onetimeAmt;
	}
	public String getChargeClass() {
		return chargeClass;
	}
	public void setChargeClass(String chargeClass) {
		this.chargeClass = chargeClass;
	}
	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}	
	public String getPromoCodeMrt() {
		return promoCodeMrt;
	}
	public void setPromoCodeMrt(String promoCodeMrt) {
		this.promoCodeMrt = promoCodeMrt;
	}
	public String getDoaOldItemSerial() {
		return doaOldItemSerial;
	}
	public void setDoaOldItemSerial(String doaOldItemSerial) {
		this.doaOldItemSerial = doaOldItemSerial;
	}
	public String getDoaOldItemCode() {
		return doaOldItemCode;
	}
	public void setDoaOldItemCode(String doaOldItemCode) {
		this.doaOldItemCode = doaOldItemCode;
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
