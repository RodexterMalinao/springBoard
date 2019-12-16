package com.bomwebportal.lts.dto.image;

import java.io.Serializable;

public class ImageDTO implements Serializable {

	private static final long serialVersionUID = 7266576050886753168L;

	private String imageType = null;
	private String contentType = null;
	private String contentDisposition = null;
	private String inputName = null;
	private int bufferSize;
	private byte[] imageBytes = null;

	
	public ImageDTO(String pImageType, String pContentType, String pContentDisposition, String pInputName, int pBufferSize) {
		this.imageType = pImageType;
		this.contentType = pContentType;
		this.contentDisposition = pContentDisposition;
		this.inputName = pInputName;
		this.bufferSize = pBufferSize;
	}

	public String getImageType() {
		return imageType;
	}

	public String getContentType() {
		return contentType;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public String getInputName() {
		return inputName;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public byte[] getImageBytes() {
		return imageBytes;
	}

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}
}
