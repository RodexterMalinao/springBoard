package com.bomwebportal.service;

import com.bomwebportal.dto.DtoPropertyDTO;

public interface DtoPropertyService {

	public abstract DtoPropertyDTO getDtoProperty(String pClassName);

	public abstract void setFormDefaultValues(Object pForm);

}