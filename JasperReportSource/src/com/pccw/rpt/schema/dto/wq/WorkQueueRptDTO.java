package com.pccw.rpt.schema.dto.wq;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

import com.pccw.rpt.schema.dto.ReportDTO;
import com.pccw.rpt.util.ReportUtil;

public class WorkQueueRptDTO extends ReportDTO {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2695118625959352009L;
	private String wqId;
	private String sbId;
	private String sbDtlId;
	private String sbShopCode;
	private String typeOfService;
	private String serviceId;
	private String srd;
	private String bomOcId;
	private String bomDtlId;
	private String bomDtlSeq;
	private String bomStatus;
	private String bomLegacyOrderStatus;
	private String wqSerial;
	private String wqReceiveDate;
	private String wqStatus;
	private String wqStatusDesc;
	private String wqStatusDate;
	private String assignee;
	private String wqNatureDesc;
	private String wqType;
	private String wqTypeDesc;
	private String wqSubType;
	private String printDate;
	private String wqSubTypeDesc;
	private String wqNature;
	private String relatedSrvType;
	private String relatedSrvNum;
	private ArrayList<WqRemarkRptDTO> remarksList = new ArrayList<WqRemarkRptDTO>();

	public void addRemarks(String pRemarkSequence, String pRemarkDate,
			String pCreateBy, String pRemarkNatureId, String pRemarkNatureDesc,
			String pRemarkContent) {
		this.remarksList.add(new WqRemarkRptDTO(pRemarkSequence, pRemarkDate,
				pCreateBy, pRemarkNatureId, pRemarkNatureDesc, pRemarkContent));
	}

	public String getWqId() {
		return ReportUtil.defaultString(this.wqId);
	}

	public void setWqId(String pWqId) {
		this.wqId = pWqId;
	}

	public String getSbId() {
		return ReportUtil.defaultString(this.sbId);
	}

	public void setSbId(String pSbId) {
		this.sbId = pSbId;
	}

	public String getSbDtlId() {
		return ReportUtil.defaultString(this.sbDtlId);
	}

	public void setSbDtlId(String pSbDtlId) {
		this.sbDtlId = pSbDtlId;
	}

	public String getSbShopCode() {
		return ReportUtil.defaultString(this.sbShopCode);
	}

	public void setSbShopCode(String pSbShopCode) {
		this.sbShopCode = pSbShopCode;
	}

	public String getTypeOfService() {
		return ReportUtil.defaultString(this.typeOfService);
	}

	public void setTypeOfService(String pTypeOfService) {
		this.typeOfService = pTypeOfService;
	}

	public String getServiceId() {
		return ReportUtil.defaultString(this.serviceId);
	}

	public void setServiceId(String pServiceId) {
		this.serviceId = pServiceId;
	}

	public String getSrd() {
		return ReportUtil.defaultString(this.srd);
	}

	public void setSrd(String pSrd) {
		this.srd = pSrd;
	}

	public String getBomOcId() {
		return ReportUtil.defaultString(this.bomOcId);
	}

	public void setBomOcId(String pBomOcId) {
		this.bomOcId = pBomOcId;
	}

	public String getBomDtlId() {
		return ReportUtil.defaultString(this.bomDtlId);
	}

	public void setBomDtlId(String pBomDtlId) {
		this.bomDtlId = pBomDtlId;
	}

	public String getBomDtlSeq() {
		return ReportUtil.defaultString(this.bomDtlSeq);
	}

	public void setBomDtlSeq(String pBomDtlSeq) {
		this.bomDtlSeq = pBomDtlSeq;
	}

	public String getBomStatus() {
		return ReportUtil.defaultString(this.bomStatus);
	}

	public void setBomStatus(String pBomStatus) {
		this.bomStatus = pBomStatus;
	}

	public String getBomLegacyOrderStatus() {
		return ReportUtil.defaultString(this.bomLegacyOrderStatus);
	}

	public void setBomLegacyOrderStatus(String pBomLegacyOrderStatus) {
		this.bomLegacyOrderStatus = pBomLegacyOrderStatus;
	}

	public String getWqSerial() {
		return ReportUtil.defaultString(this.wqSerial);
	}

	public void setWqSerial(String pWqSerial) {
		this.wqSerial = pWqSerial;
	}

	public String getWqReceiveDate() {
		return ReportUtil.defaultString(this.wqReceiveDate);
	}

	public void setWqReceiveDate(String pWqReceiveDate) {
		this.wqReceiveDate = pWqReceiveDate;
	}

	public String getWqStatus() {
		return ReportUtil.defaultString(this.wqStatus);
	}

	public void setWqStatus(String pWqStatus) {
		this.wqStatus = pWqStatus;
	}

	public String getWqStatusDesc() {
		return ReportUtil.defaultString(this.wqStatusDesc);
	}

	public void setWqStatusDesc(String pWqStatusDesc) {
		this.wqStatusDesc = pWqStatusDesc;
	}

	public String getWqStatusDate() {
		return ReportUtil.defaultString(this.wqStatusDate);
	}

	public void setWqStatusDate(String pWqStatusDate) {
		this.wqStatusDate = pWqStatusDate;
	}

	public String getAssignee() {
		return ReportUtil.defaultString(this.assignee);
	}

	public void setAssignee(String pAssignee) {
		this.assignee = pAssignee;
	}

	public String getWqNatureDesc() {
		return StringUtils.replace(ReportUtil.defaultString(this.wqNatureDesc), "\n", "<br />");
	}

	public void setWqNatureDesc(String pWqNatureDesc) {
		this.wqNatureDesc = pWqNatureDesc;
	}

	public String getWqType() {
		return ReportUtil.defaultString(this.wqType);
	}

	public void setWqType(String pWqType) {
		this.wqType = pWqType;
	}

	public String getWqTypeDesc() {
		return ReportUtil.defaultString(this.wqTypeDesc);
	}

	public void setWqTypeDesc(String pWqTypeDesc) {
		this.wqTypeDesc = pWqTypeDesc;
	}

	public String getWqSubType() {
		return ReportUtil.defaultString(this.wqSubType);
	}

	public void setWqSubType(String pWqSubType) {
		this.wqSubType = pWqSubType;
	}

	public String getPrintDate() {
		return ReportUtil.defaultString(this.printDate);
	}

	public void setPrintDate(String pPrintDate) {
		this.printDate = pPrintDate;
	}

	public String getWqSubTypeDesc() {
		return ReportUtil.defaultString(this.wqSubTypeDesc);
	}

	public void setWqSubTypeDesc(String pWqSubTypeDesc) {
		this.wqSubTypeDesc = pWqSubTypeDesc;
	}

	public String getWqNature() {
		return ReportUtil.defaultString(this.wqNature);
	}

	public void setWqNature(String pWqNature) {
		this.wqNature = pWqNature;
	}

	public ArrayList<WqRemarkRptDTO> getRemarksList() {
		return this.remarksList;
	}

	public void setRemarksList(ArrayList<WqRemarkRptDTO> pRemarksList) {
		this.remarksList = pRemarksList;
	}

	public String getRelatedSrvType() {
		return ReportUtil.defaultString(this.relatedSrvType);
	}

	public void setRelatedSrvType(String pRelatedSrvType) {
		this.relatedSrvType = pRelatedSrvType;
	}

	public String getRelatedSrvNum() {
		return ReportUtil.defaultString(this.relatedSrvNum);
	}

	public void setRelatedSrvNum(String pRelatedSrvNum) {
		this.relatedSrvNum = pRelatedSrvNum;
	}
}