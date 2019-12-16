package com.bomwebportal.web;

import java.util.Arrays;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.bomwebportal.dto.DocImgUploadDTO;

public class DocImgUploadAppController extends DocImgUploadController {

	protected final Log logger = LogFactory.getLog(getClass());

	
	public ModelAndView onSubmit(HttpServletRequest req,
			HttpServletResponse res, Object command, BindException errors)
			throws Exception 
	{


		DocImgUploadDTO file = (DocImgUploadDTO)command;
		
		boolean success = true;
		String reason = "";
		String orderId = StringUtils.chomp(file.getOrderId());
		String docType = StringUtils.chomp(file.getDocType());

		
		MultipartFile mpFile = file.getFile();

		
		try {
			
			if (StringUtils.isBlank(orderId)) {
				success = false;
				reason = "Missing Order ID";
			}
			
			if (success && StringUtils.isBlank(docType)) {
				success = false;
				reason = "Missing doc type";
			}
			
			if (success && (mpFile == null || mpFile.getSize() <= 0)) {
				success = false;
				reason = "Missing file or zero filesize";
			}
			
			if (success && mpFile != null && !validateFileExtension(mpFile.getOriginalFilename())) {
				success = false;
				reason = "File type is not allowed";
			}
			
			if (success) {
				super.onSubmit(req, res, command, errors);

			}
		} catch (Exception e) {
			success = false;
			reason = e.getMessage();
			if (reason == null) reason = "Unknown error :" + e.getClass().getSimpleName();
		}

		HashMap map = new HashMap();
		map.put("orderId", orderId);
		map.put("success", success);
		map.put("reason", reason);
		map.put("filename", file.getFileName());
		map.put("size", file.getSize());
		map.put("docName", file.getDocName());
		map.put("docType", file.getDocType());

		
		logger.debug("upload result=" + map);
		
		return new ModelAndView(getSuccessView(), map);
	}
	

	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception exception) {

		if (exception instanceof MaxUploadSizeExceededException) {
			
			MaxUploadSizeExceededException musee = (MaxUploadSizeExceededException)exception;
			long maxSize = musee.getMaxUploadSize();
			
			HashMap map = new HashMap();
			map.put("success", false);
			map.put("reason", "File over size limit of " + (maxSize/1024) + " kbytes.");
			return  new ModelAndView(getSuccessView(), map);

		}
		return null;
		
	}
	
	private boolean validateFileExtension(String filename) {
		if (filename == null) return false;
		String ext = getFileExtension(filename).replace(".", "");

		if (getAllowedExtensions() != null) {
			return Arrays.asList(getAllowedExtensions()).contains(ext);
		} else {
			return true;
		}
	}



}
