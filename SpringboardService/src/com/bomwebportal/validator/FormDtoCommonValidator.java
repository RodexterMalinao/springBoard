package com.bomwebportal.validator;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bomwebportal.dto.DtoPropertyDTO;
import com.bomwebportal.dto.FieldPropertyDTO;
import com.bomwebportal.service.DtoPropertyService;
import com.pccw.dto.AbstractSecuritySupportFormDTO;

public class FormDtoCommonValidator implements Validator {

	private DtoPropertyService dtoPropertyService = null;
	
	@SuppressWarnings({ "rawtypes"}) 
	public boolean supports(Class pClass) {
		return AbstractSecuritySupportFormDTO.class.isAssignableFrom(pClass);		
	}

	public void validate(Object pForm, Errors pErrors) {
		
		AbstractSecuritySupportFormDTO form = (AbstractSecuritySupportFormDTO)pForm;
		DtoPropertyDTO property = this.dtoPropertyService.getDtoProperty(form.getClass().getName());
		
		if (property == null) {
			return;
		}
		FieldPropertyDTO[] fieldProperties = property.getAllFieldProperty();
		
		if (fieldProperties == null) {
			return;
		}
		Object field = null;
		String fieldValue = null;
		
		for (int i=0; i<fieldProperties.length; ++i) {
			try {
				field = PropertyUtils.getNestedProperty(form, fieldProperties[i].getFieldName());
				
				if (field == null) {
					continue;
				}
				fieldValue = field.toString();
				
				if (fieldProperties[i].isMandatory() && StringUtils.isBlank(fieldValue)) {
					pErrors.rejectValue(fieldProperties[i].getFieldName(), "must_not_blank",fieldProperties[i].getDescription() + " must not blank");			
				} else if (StringUtils.length(fieldValue) > fieldProperties[i].getFieldLength()) {
					pErrors.rejectValue(fieldProperties[i].getFieldName(), "exceeds_maximum_length", fieldProperties[i].getDescription() + " exceeds maximum length " + fieldProperties[i].getFieldLength());
				} else if (StringUtils.equalsIgnoreCase("String", fieldProperties[i].getFieldType()) && !StringUtils.isAlphanumeric(fieldValue)) {
					pErrors.rejectValue(fieldProperties[i].getFieldName(), "string_type_not_match", fieldProperties[i].getDescription() + " must be type " + fieldProperties[i].getFieldType());
				} else if (StringUtils.equalsIgnoreCase("Integer", fieldProperties[i].getFieldType()) && !this.isInteger(fieldValue)) {
					pErrors.rejectValue(fieldProperties[i].getFieldName(), "int_type_not_match", fieldProperties[i].getDescription() + " must be type " + fieldProperties[i].getFieldType());
				} else if (StringUtils.equalsIgnoreCase("Double", fieldProperties[i].getFieldType()) && !this.isDouble(fieldValue)) {
					pErrors.rejectValue(fieldProperties[i].getFieldName(), "double_type_not_match", fieldProperties[i].getDescription() + " must be type " + fieldProperties[i].getFieldType());
				}
			} catch (IllegalAccessException e) {
			} catch (IllegalArgumentException e) {
			} catch (InvocationTargetException e) {
			} catch (NoSuchMethodException e) {}  
		}
	}
	
	private boolean isInteger(String pValue) {
		return StringUtils.isNumeric(pValue);
	}
	
	private boolean isDouble(String pValue) {
		try {
			Double.parseDouble(pValue);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
	}

	public DtoPropertyService getDtoPropertyService() {
		return dtoPropertyService;
	}

	public void setDtoPropertyService(DtoPropertyService dtoPropertyService) {
		this.dtoPropertyService = dtoPropertyService;
	}
}
