package com.bomwebportal.lts.service.image;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.bomwebportal.lts.dao.image.WImageDisplayLkupDAO;
import com.bomwebportal.lts.dto.image.ImageDetailDTO;
import com.pccw.util.spring.SpringApplicationContext;

public class ImageDetailServiceImpl implements ImageDetailService {

	private final Log logger = LogFactory.getLog(getClass());
	
	
	public ImageDetailDTO getImageDetailByImageId(String pImgId, String pLocale) {
		
		WImageDisplayLkupDAO dao = SpringApplicationContext.getBean(WImageDisplayLkupDAO.class);
		
		try {
			dao.setImageId(pImgId);
			dao.setLocale(pLocale);
			dao.doSelect();
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		ImageDetailDTO imageDtl = null;
		
		if (StringUtils.isNotBlank(dao.getOracleRowID())) {
			imageDtl = new ImageDetailDTO();
			BeanUtils.copyProperties(dao, imageDtl);
		}
		return imageDtl;
	}	
}
