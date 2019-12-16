package com.bomwebportal.dto.report;

import java.util.ArrayList;
import java.util.List;

import com.bomwebportal.dto.report.ReportDTO;

public class MobAppFormDTO {
		
	private int reportSeq;
	private String channelId;
	private String appFormId;
	private String appFormDesc;
	private String jasperTemplateName;
	private boolean isMandatory;
	private String nature;
	private String companyCopyInd;
	private String copInd;
	private String delInd;
	private String dmsInd;
	private List<ReportDTO> pRptDtoArrList;
	
	public MobAppFormDTO(){
	}
	
	public String getAppFormId() {
		return appFormId;
	}
	public void setAppFormId(String appFormId) {
		this.appFormId = appFormId;
	}
	public String getAppFormDesc() {
		return appFormDesc;
	}
	public void setAppFormDesc(String appFormDesc) {
		this.appFormDesc = appFormDesc;
	}
	public int getReportSeq() {
		return reportSeq;
	}
	public void setReportSeq(int reportSeq) {
		this.reportSeq = reportSeq;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	public boolean isMandatory() {
		return isMandatory;
	}
	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	public String getNature() {
		return nature;
	}
	public void setNature(String nature) {
		this.nature = nature;
	}
	public String getCompanyCopyInd() {
		return companyCopyInd;
	}
	public void setCompanyCopyInd(String companyCopyInd) {
		this.companyCopyInd = companyCopyInd;
	}
	public String getCopInd() {
		return copInd;
	}
	public void setCopInd(String copInd) {
		this.copInd = copInd;
	}
	public String getDelInd() {
		return delInd;
	}
	public void setDelInd(String delInd) {
		this.delInd = delInd;
	}
	public String getDmsInd() {
		return dmsInd;
	}
	public void setDmsInd(String dmsInd) {
		this.dmsInd = dmsInd;
	}
	public String getJasperTemplateName() {
		return jasperTemplateName;
	}
	public void setJasperTemplateName(String jasperTemplateName) {
		this.jasperTemplateName = jasperTemplateName;
	}
	public List<ReportDTO> getpRptDtoArrList() {
		return pRptDtoArrList;
	}
	public void setpRptDtoArrList(List<ReportDTO> pRptDtoArrList) {
		this.pRptDtoArrList = pRptDtoArrList;
	}
}
