package com.bomwebportal.ims.service;

import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class ResourceLoaderService implements ResourceLoaderAware{
	
	
	private ResourceLoader resourceLoader;
 
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}
 
	public Resource getResource(String location){
		return resourceLoader.getResource(location);
	}
	
	
}