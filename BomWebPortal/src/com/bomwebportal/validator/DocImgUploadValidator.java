package com.bomwebportal.validator;

import java.util.Arrays;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.bomwebportal.dto.DocImgUploadDTO;

public class DocImgUploadValidator implements Validator {

	private String[] allowedExtensions;
	
	public String[] getAllowedExtensions() {
		return allowedExtensions;
	}

	public void setAllowedExtensions(String[] allowedExtensions) {
		this.allowedExtensions = allowedExtensions;
		if (this.allowedExtensions != null) {
			for (int i=0; i < this.allowedExtensions.length; i++) {
				this.allowedExtensions[i] = StringUtils.lowerCase(this.allowedExtensions[i]);
			}
		}
	}
	
	public boolean supports(Class clazz) {
		return DocImgUploadDTO.class.isAssignableFrom(clazz);
	}

	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "orderId", "required.orderId", "Missing order ID");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "docType", "required.docType", "Missing doc type");
		ValidationUtils.rejectIfEmpty(errors, "username", "required.username", "Missing username");
		
		DocImgUploadDTO dto = (DocImgUploadDTO) target;
		
		MultipartFile mpFile = dto.getFile();
		
		if (mpFile == null || mpFile.getSize() <= 0) {
			errors.rejectValue("file", "required.file", "Missing file or zero filesize");
		}
		
		if (mpFile != null) {
			String filename = mpFile.getOriginalFilename();
			String ext = getFileExtension(filename);
			
			if (getAllowedExtensions() != null) {
				if (!Arrays.asList(getAllowedExtensions()).contains(ext)) {
					errors.rejectValue("file", "file.extension", "This file type is not allowed.");
				}
			}
				
		}
		

	}
	
	
	protected String getFileExtension(String filename) {
		if (StringUtils.isBlank(filename)) return "";
		String ext = "";
		int dot = filename.lastIndexOf('.');
		if (dot >= 0 && dot < filename.length()-1)
			ext = filename.substring(dot+1);
		return StringUtils.lowerCase(ext);
	}

}
