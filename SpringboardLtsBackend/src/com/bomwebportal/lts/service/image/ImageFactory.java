package com.bomwebportal.lts.service.image;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.lts.dto.image.ImageDTO;
import com.bomwebportal.lts.util.LtsBackendConstant;

public class ImageFactory {

	private static final String[] JPEG_IMAGE_DETAIL = {"image/jpeg", "inline", "fileStream", "1024"};
	private static final String[] PNG_IMAGE_DETAIL = {"image/png", "inline", "fileStream", "1024"};
	private static final String[] GIF_IMAGE_DETAIL = {"image/gif", "inline", "fileStream", "1024"};
	
	
	public static ImageDTO createImage(File pImageFile) throws IOException {
		
		byte[] imageBytes = FileUtils.readFileToByteArray(pImageFile);
		String imageType = StringUtils.substring(pImageFile.getName(), StringUtils.lastIndexOf(pImageFile.getName(), '.')+1);
		ImageDTO image = null;
		
		if (StringUtils.equalsIgnoreCase(LtsBackendConstant.IMAGE_TYPE_JPEG, imageType)) {
			image = new ImageDTO(LtsBackendConstant.IMAGE_TYPE_JPEG, JPEG_IMAGE_DETAIL[0], JPEG_IMAGE_DETAIL[1], JPEG_IMAGE_DETAIL[2], Integer.parseInt(JPEG_IMAGE_DETAIL[3]));
		} else if (StringUtils.equalsIgnoreCase(LtsBackendConstant.IMAGE_TYPE_PNG, imageType)) {
			image = new ImageDTO(LtsBackendConstant.IMAGE_TYPE_PNG, PNG_IMAGE_DETAIL[0], PNG_IMAGE_DETAIL[1], PNG_IMAGE_DETAIL[2], Integer.parseInt(PNG_IMAGE_DETAIL[3]));
		} else if (StringUtils.equalsIgnoreCase(LtsBackendConstant.IMAGE_TYPE_GIF, imageType)) {
			image = new ImageDTO(LtsBackendConstant.IMAGE_TYPE_GIF, GIF_IMAGE_DETAIL[0], GIF_IMAGE_DETAIL[1], GIF_IMAGE_DETAIL[2], Integer.parseInt(GIF_IMAGE_DETAIL[3]));
		} else {
			return null;
		}
		image.setImageBytes(imageBytes);
		return image;
	}
}
