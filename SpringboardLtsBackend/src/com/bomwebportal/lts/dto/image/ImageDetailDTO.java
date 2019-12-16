package com.bomwebportal.lts.dto.image;

import java.io.Serializable;

public class ImageDetailDTO implements Serializable {

	private static final long serialVersionUID = 4425172097078521418L;

	private String imageId = null;
	private String locale = null;
	private String imageName = null;
	private String imgPath = null;

	
	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImgPath() {
		return imgPath;
	}

	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
}
