package com.bomwebportal.lts.service.image;

import java.io.IOException;

import com.bomwebportal.lts.dto.image.ImageDTO;

public interface DownloadImageService {

	public abstract ImageDTO downloadImage(String pImageId, String pLocale) throws IOException;

}