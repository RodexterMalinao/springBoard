package com.bomwebportal.dto.report;

import java.io.Serializable;

import com.bomwebportal.util.FastByteArrayOutputStream;

public class ReportOutputDTO implements Serializable {

	private static final long serialVersionUID = -8756314152160637896L;

	private FastByteArrayOutputStream outputFileStream = null;
	private String fileStoragePath = null;


	public FastByteArrayOutputStream getOutputFileStream() {
		return outputFileStream;
	}

	public void setOutputFileStream(FastByteArrayOutputStream outputFileStream) {
		this.outputFileStream = outputFileStream;
	}

	public String getFileStoragePath() {
		return fileStoragePath;
	}

	public void setFileStoragePath(String fileStoragePath) {
		this.fileStoragePath = fileStoragePath;
	}
}
