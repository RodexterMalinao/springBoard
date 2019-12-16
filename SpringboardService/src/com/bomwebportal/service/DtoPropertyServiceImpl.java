package com.bomwebportal.service;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import com.bomwebportal.dto.DtoPropertyDTO;
import com.bomwebportal.dto.FieldPropertyDTO;

public class DtoPropertyServiceImpl implements DtoPropertyService {

	private CodeLkupCacheService fieldPropertyCache = null;
	
	
	public DtoPropertyDTO getDtoProperty(String pClassName) {
		
		try {
			return (DtoPropertyDTO)this.fieldPropertyCache.get(pClassName);
		} catch (Exception e) {
			return null;
		}
	}

	public void setFormDefaultValues(Object pForm) {
		
		DtoPropertyDTO propertry = this.getDtoProperty(pForm.getClass().getName());
		
		if (propertry == null) {
			return;
		}
		FieldPropertyDTO[] fieldProperties = propertry.getAllFieldProperty();
		
		if (fieldProperties == null) {
			return;
		}		
		for (int i=0; i<fieldProperties.length; ++i) {
			
			if (StringUtils.isBlank(fieldProperties[i].getDefaultVaule())) {
				continue;
			}
			try {
				PropertyUtils.setNestedProperty(pForm, fieldProperties[i].getFieldName(), fieldProperties[i].getDefaultVaule());
			} catch (IllegalAccessException e) {
			} catch (InvocationTargetException e) {
			} catch (NoSuchMethodException e) {}
		}
	}

	public CodeLkupCacheService getFieldPropertyCache() {
		return fieldPropertyCache;
	}

	public void setFieldPropertyCache(CodeLkupCacheService fieldPropertyCache) {
		this.fieldPropertyCache = fieldPropertyCache;
	}
}
