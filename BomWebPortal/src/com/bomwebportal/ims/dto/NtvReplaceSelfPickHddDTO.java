package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class NtvReplaceSelfPickHddDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5096531629663008744L;
	
	
	private String id;
	private String type;
	private String prodCd;
	private String offerCode;
	private String bw;
	private String replaceId;
	private String replaceType;
	private String replaceProdCd;	
	private String replaceOfferCode;
	private String replaceBw;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getOfferCode() {
		return offerCode;
	}
	public void setOfferCode(String offerCode) {
		this.offerCode = offerCode;
	}
	public String getBw() {
		return bw;
	}
	public void setBw(String bw) {
		this.bw = bw;
	}
	public String getReplaceId() {
		return replaceId;
	}
	public void setReplaceId(String replaceId) {
		this.replaceId = replaceId;
	}
	public String getReplaceType() {
		return replaceType;
	}
	public void setReplaceType(String replaceType) {
		this.replaceType = replaceType;
	}
	public String getReplaceProdCd() {
		return replaceProdCd;
	}
	public void setReplaceProdCd(String replaceProdCd) {
		this.replaceProdCd = replaceProdCd;
	}
	public String getReplaceOfferCode() {
		return replaceOfferCode;
	}
	public void setReplaceOfferCode(String replaceOfferCode) {
		this.replaceOfferCode = replaceOfferCode;
	}
	public String getReplaceBw() {
		return replaceBw;
	}
	public void setReplaceBw(String replaceBw) {
		this.replaceBw = replaceBw;
	}
	public String getProdCd() {
		return prodCd;
	}
	public void setProdCd(String prodCd) {
		this.prodCd = prodCd;
	}

	

}
