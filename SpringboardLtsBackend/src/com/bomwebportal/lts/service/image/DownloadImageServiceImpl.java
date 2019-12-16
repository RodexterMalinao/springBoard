package com.bomwebportal.lts.service.image;

import java.io.File;
import java.io.IOException;

import com.bomwebportal.lts.dto.image.ImageDTO;
import com.bomwebportal.lts.dto.image.ImageDetailDTO;
import com.pccw.util.spring.SpringApplicationContext;

public class DownloadImageServiceImpl implements DownloadImageService {
	
	private String fileStoragePath = null;
	
	
	public ImageDTO downloadImage(String pImageId, String pLocale) throws IOException {
		
		ImageDetailDTO imgInfo = SpringApplicationContext.getBean(ImageDetailService.class).getImageDetailByImageId(pImageId, pLocale);
		
		if (imgInfo == null) {
			return null;
		}
		return ImageFactory.createImage(new File(this.fileStoragePath + imgInfo.getImgPath()));
	}

	public String getFileStoragePath() {
		return fileStoragePath;
	}


	public void setFileStoragePath(String fileStoragePath) {
		this.fileStoragePath = fileStoragePath;
	}
}
