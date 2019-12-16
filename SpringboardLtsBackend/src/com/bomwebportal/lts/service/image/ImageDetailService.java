package com.bomwebportal.lts.service.image;

import com.bomwebportal.lts.dto.image.ImageDetailDTO;

public interface ImageDetailService {

	public abstract ImageDetailDTO getImageDetailByImageId(String pImgId,
			String pLocale);

}