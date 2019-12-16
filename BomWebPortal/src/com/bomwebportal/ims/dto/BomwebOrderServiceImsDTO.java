package com.bomwebportal.ims.dto;

import java.io.Serializable;
import java.util.Date;

public class BomwebOrderServiceImsDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8003268053359290017L;
	
	private String OrderId;
	private String fsa;
	private String BomOrderType;
	private String LineType;
	private String AppLineType;
	private String PcdType;
	private String NowTvType;
	private String EyeType;
	private String NowTvInd;
	private String PcdInd;
	private String RemoveWithEye;
	private String ErInd;
	private String ProRata;
	private String CreateBy;
	private Date CreateDate;
	private String UpdateBy;
	private Date UpdateDate;
	private String discReasonCode;
	private String nowTvPayChInd;
	private String bandwidth;
	
	
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getNowTvPayChInd() {
		return nowTvPayChInd;
	}
	public void setNowTvPayChInd(String nowTvPayChInd) {
		this.nowTvPayChInd = nowTvPayChInd;
	}
	public String getDiscReasonCode() {
		return discReasonCode;
	}
	public void setDiscReasonCode(String discReasonCode) {
		this.discReasonCode = discReasonCode;
	}
	public String getOrderId() {
		return OrderId;
	}
	public void setOrderId(String orderId) {
		OrderId = orderId;
	}
	
	public String getFsa() {
		return fsa;
	}
	public void setFsa(String fsa) {
		this.fsa = fsa;
	}
	public String getBomOrderType() {
		return BomOrderType;
	}
	public void setBomOrderType(String bomOrderType) {
		BomOrderType = bomOrderType;
	}
	public String getLineType() {
		return LineType;
	}
	public void setLineType(String lineType) {
		LineType = lineType;
	}
	public String getAppLineType() {
		return AppLineType;
	}
	public void setAppLineType(String appLineType) {
		AppLineType = appLineType;
	}
	public String getPcdType() {
		return PcdType;
	}
	public void setPcdType(String pcdType) {
		PcdType = pcdType;
	}
	public String getNowTvType() {
		return NowTvType;
	}
	public void setNowTvType(String nowTvType) {
		NowTvType = nowTvType;
	}
	public String getEyeType() {
		return EyeType;
	}
	public void setEyeType(String eyeType) {
		EyeType = eyeType;
	}
	public String getNowTvInd() {
		return NowTvInd;
	}
	public void setNowTvInd(String nowTvInd) {
		NowTvInd = nowTvInd;
	}
	public String getPcdInd() {
		return PcdInd;
	}
	public void setPcdInd(String pcdInd) {
		PcdInd = pcdInd;
	}
	public String getRemoveWithEye() {
		return RemoveWithEye;
	}
	public void setRemoveWithEye(String removeWithEye) {
		RemoveWithEye = removeWithEye;
	}
	public String getErInd() {
		return ErInd;
	}
	public void setErInd(String erInd) {
		ErInd = erInd;
	}
	public String getProRata() {
		return ProRata;
	}
	public void setProRata(String proRata) {
		ProRata = proRata;
	}
	public String getCreateBy() {
		return CreateBy;
	}
	public void setCreateBy(String createBy) {
		CreateBy = createBy;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public void setCreateDate(Date createDate) {
		CreateDate = createDate;
	}
	public String getUpdateBy() {
		return UpdateBy;
	}
	public void setUpdateBy(String updateBy) {
		UpdateBy = updateBy;
	}
	public Date getUpdateDate() {
		return UpdateDate;
	}
	public void setUpdateDate(Date updateDate) {
		UpdateDate = updateDate;
	}

	
}
