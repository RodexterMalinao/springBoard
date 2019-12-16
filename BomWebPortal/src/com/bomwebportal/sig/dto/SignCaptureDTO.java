package com.bomwebportal.sig.dto;

import java.util.Date;
import java.util.List;

import com.bomwebportal.dto.AllOrdDocTempDTO;

public class SignCaptureDTO {

	private String reqId;
	private String orderId;
	private String remark;
	private String signature;
	
	private String createBy;
	private String lastUpdBy;
	private Date createDate;
	private Date lastUpdDate;
	
	private List<AllOrdDocTempDTO> allOrdDocDTOList;
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getLastUpdBy() {
		return lastUpdBy;
	}
	public void setLastUpdBy(String lastUpdBy) {
		this.lastUpdBy = lastUpdBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdDate() {
		return lastUpdDate;
	}
	public void setLastUpdDate(Date lastUpdDate) {
		this.lastUpdDate = lastUpdDate;
	}
	public List<AllOrdDocTempDTO> getAllOrdDocDTOList() {
		return allOrdDocDTOList;
	}
	public void setAllOrdDocDTOList(List<AllOrdDocTempDTO> allOrdDocDTOList) {
		this.allOrdDocDTOList = allOrdDocDTOList;
	}
	
}