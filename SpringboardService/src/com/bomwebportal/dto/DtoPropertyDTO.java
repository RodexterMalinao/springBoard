package com.bomwebportal.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;

public class DtoPropertyDTO implements Serializable  {
	
	private static final long serialVersionUID = -4859271190991819676L;

	private String className = null;
	
	private Map<String,FieldPropertyDTO> fieldPropertyMap = null;
	

	public DtoPropertyDTO(String pClassName) {
		this.className = pClassName;;
	}
	
	public String getClassName() {
		return className;
	}

	public void setFieldProperty(String pFieldName, FieldPropertyDTO pProperty) {
		
		if (this.fieldPropertyMap == null) {
			this.fieldPropertyMap = new TreeMap<String,FieldPropertyDTO>();
		} else if (this.fieldPropertyMap.containsKey(pFieldName)) {
			return;
		}
		this.fieldPropertyMap.put(pFieldName, pProperty);
	}
	
	public FieldPropertyDTO getFieldProperty(String pFieldName) {
		
		if (this.fieldPropertyMap == null) {
			return null;
		}
		return this.getFieldProperty(pFieldName);
	}
	
	public FieldPropertyDTO[] getAllFieldProperty() {
		
		if (this.fieldPropertyMap == null) {
			return null;
		}
		return this.fieldPropertyMap.values().toArray(new FieldPropertyDTO[this.fieldPropertyMap.size()]);
	}
}
