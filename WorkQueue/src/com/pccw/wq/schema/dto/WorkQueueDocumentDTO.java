package com.pccw.wq.schema.dto;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class WorkQueueDocumentDTO implements Serializable,
		Comparable<WorkQueueDocumentDTO> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6688089804631056578L;

	private String documentId;
	private String documentTypeId;
	private String reportName;
	private String attachmentHost;
	private String attachmentFullPath;
	private int printCount;
	private String printDate;
	private String downloadDate;

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String pDocumentId) {
		documentId = pDocumentId;
	}

	public String getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(String pDocumentTypeId) {
		documentTypeId = pDocumentTypeId;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String pReportName) {
		reportName = pReportName;
	}

	public String getAttachmentHost() {
		return attachmentHost;
	}

	public void setAttachmentHost(String pAttachmentHost) {
		attachmentHost = pAttachmentHost;
	}

	public String getAttachmentFullPath() {
		return attachmentFullPath;
	}

	public void setAttachmentFullPath(String pAttachmentFullPath) {
		attachmentFullPath = pAttachmentFullPath;
	}

	public int getPrintCount() {
		return printCount;
	}

	public void setPrintCount(int pPrintCount) {
		printCount = pPrintCount;
	}

	public String getPrintDate() {
		return printDate;
	}

	public void setPrintDate(String pPrintDate) {
		printDate = pPrintDate;
	}

	public String getDownloadDate() {
		return downloadDate;
	}

	public void setDownloadDate(String pDownloadDate) {
		downloadDate = pDownloadDate;
	}

	@Override
	public int compareTo(WorkQueueDocumentDTO pWorkQueueDocument) {
		return this.getCompareKey().compareTo(pWorkQueueDocument.getCompareKey());
	}
	
	public String getCompareKey(){
		return StringUtils.leftPad(this.documentId, 10) + "^" + this.documentTypeId + "^" + this.reportName;
	}
}