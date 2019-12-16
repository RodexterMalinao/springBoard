package com.bomltsportal.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.bomwebportal.lts.dto.image.ImageDTO;
import com.bomwebportal.lts.service.image.DownloadImageService;

public class GetImageController implements Controller {
	
	protected final Log logger = LogFactory.getLog(getClass());

	protected DownloadImageService downloadImageService;
	
	public DownloadImageService getDownloadImageService() {
		return downloadImageService;
	}

	public void setDownloadImageService(DownloadImageService downloadImageService) {
		this.downloadImageService = downloadImageService;
	}

	public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String id = request.getParameter("id");
		String locale = RequestContextUtils.getLocale(request).toString();
		
		try {
			ImageDTO image = downloadImageService.downloadImage(id, locale);
			
			if (image == null) {
				return null;
			}
			
			response.setContentType(image.getContentType());
			response.addHeader("Content-disposition","attachment; filename=" + image.getContentDisposition());
			response.setHeader("Cache-Control", "private");			
			response.getOutputStream().write(image.getImageBytes());
			response.getOutputStream().flush();		
			
		}
		catch (Exception e) {
			response.resetBuffer();
			response.setContentType("text/html");
			response.setHeader("Content-Disposition", null);
			response.getWriter().println("Exception in GetImageController: " + ExceptionUtils.getFullStackTrace(e));
		}
		
		return null;
	}

}
