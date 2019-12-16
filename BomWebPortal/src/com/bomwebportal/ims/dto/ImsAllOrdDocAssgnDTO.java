package com.bomwebportal.ims.dto;

import java.util.List;

import com.bomwebportal.dto.*;

public class ImsAllOrdDocAssgnDTO extends AllOrdDocAssgnDTO
{
	public String filePathName;
	private String faxSerial;
	private String imsDocType;
	private List<String> pdfPaths;

	public String getFilePathName() {
		return filePathName;
	}

	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}

	public String getFaxSerial() {
		return faxSerial;
	}

	public void setFaxSerial(String faxSerial) {
		this.faxSerial = faxSerial;
	}

	public void setImsDocType(String imsDocType) {
		this.imsDocType = imsDocType;
	}

	public String getImsDocType() {
		return imsDocType;
	}

	public void setPdfPaths(List<String> pdfPaths) {
		this.pdfPaths = pdfPaths;
	}

	public List<String> getPdfPaths() {
		return pdfPaths;
	}
}
